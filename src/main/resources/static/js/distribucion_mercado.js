
let $chartMiniPorc = {};
let $chartMiniPorc2 = {};
let $chartMiniPorcCondFisM1 = {};
let $chartMiniPorcCondFisM2 = {};
let $chartMiniPorcCondFisM3 = {};
let $chartMiniPorcCondFisF1 = {};
let $chartMiniPorcCondFisF2 = {};
let $chartMiniPorcCondFisF3 = {};

var dataClientes;


let canalesVenta = ["Recomendación", "Vía correo", "Google", "Facebook", "Twitter", "Instagram", "Otro"];
let serviciosTipos = ["Running", "General"];
let arrColorFem = ['#FF00EB', '#c42bba', '#bc49b3', '#8c4b86', '#6b5269','#665e5e'];
let arrColorMasc = ['#00b5f7','#358dea','#0667ce','#537daa','#6c84b2','#665e5e'];
const selectYear = document.getElementById('graphYearFilter');
let cantidadUsuarios;

$( function(){

    init();

})


function init(){

    obtenerDataEstadisticas();
    obtenerInformacionDistribucionDepartamentos();


    if(selectYear) {
        selectYear.addEventListener('change', selectYearEventListener);
    }

}


function selectYearEventListener(){

  const anioFechaTemporada = (dataClientes.map( ({fechaCreacion}) =>  moment(parseFromStringToDate2(fechaCreacion)).format("YYYY")));

    const dataFechaTemporadaFem = (dataClientes.filter(e => e.sexo === 2).map( ({fechaCreacion}) =>  moment(parseFromStringToDate2(fechaCreacion)).format("MM/YYYY")));
    const dataFechaTemporadaMasc = (dataClientes.filter(e => e.sexo === 1).map( ({fechaCreacion}) =>  moment(parseFromStringToDate2(fechaCreacion)).format("MM/YYYY")));


    let aniosArr = Array.from(new Set(anioFechaTemporada));

    aniosArr = aniosArr.map( e => Number(e)).sort( (a,b) => b - a);


  //  generarSelectYearFilter(aniosArr);

    const dataGraficoFem = getDataServicioPorTemporada(dataFechaTemporadaFem,this.value);
    const dataGraficoMasc = getDataServicioPorTemporada(dataFechaTemporadaMasc,this.value);

   // console.log(dataGraficoFem, dataGraficoMasc);

    graficoBarraVentaServiciosTemporada(dataGraficoFem,dataGraficoMasc);
}

function obtenerDataEstadisticas(){



    $.ajax({
        type: "GET",
        url: _ctx + "gestion/trainer/distribucion-mercado/obtener",
        success: function (data) {
            //Masculino

            dataClientes = data;

            const dataNoDuplicados = quitarDuplicados(data, 'id');
            const dataNoDuplMasc = dataNoDuplicados.filter( e => e.sexo === 1);
            const dataNoDuplFem = dataNoDuplicados.filter( e => e.sexo === 2);
            cantidadUsuarios = dataNoDuplicados.length;
            if(dataNoDuplFem.length > 0){
                setGraficoFem(dataNoDuplFem);
            }

            if(dataNoDuplMasc.length > 0){
                setGraficoMasc(dataNoDuplMasc);
            }

            let arrTipoServicio = dataNoDuplicados .map( ({predeterminadaFichaId}) => predeterminadaFichaId);
            let graphTipoServicioData =  getDataGraficoTipoServicio(arrTipoServicio);

            graficoServiciosUsados(graphTipoServicioData);
            generarNombreServiciosDOM();
            setDataServicioDistrSexo(dataNoDuplicados,graphTipoServicioData);

            let arrTipoCanalVenta = dataNoDuplicados .map( ({tipoCanalVentaId}) => tipoCanalVentaId);
            let graphCanalVentaData =  getDataGraficoTipoCanal(arrTipoCanalVenta);
            setDataCanalDistrSexo(dataNoDuplicados,graphCanalVentaData);
            graficoCanalesUsados(graphCanalVentaData);
            generarNombreCanalesDOM();


            const condAnatomicaMasc = dataNoDuplMasc.map( ({condicionAnatomica}) => JSON.parse(condicionAnatomica));  //.condicionAnatomica);
            const condAnatomicaFem =  dataNoDuplFem.map( ({condicionAnatomica}) => JSON.parse(condicionAnatomica));
            console.log(condAnatomicaMasc);
            const arrCondFisicMasc =  getDataGraficoCondFisica(condAnatomicaMasc);

            console.log("awsss", arrCondFisicMasc);
            const arrCondFisicFem =    getDataGraficoCondFisica(condAnatomicaFem);

            graficoCondFisicaBasicaMasc(arrCondFisicMasc);
            graficoCondFisicaMedioMasc(arrCondFisicMasc);
            graficoCondFisicaAvanzadoMasc(arrCondFisicMasc);
            graficoCondFisicaBasicaFem(arrCondFisicFem);
            graficoCondFisicaMedioFem(arrCondFisicFem);
            graficoCondFisicaAvanzadoFem(arrCondFisicFem);

            generarPorcentajeCondFisica(arrCondFisicMasc, 'masc');
            generarPorcentajeCondFisica(arrCondFisicFem, 'fem');


            const anioFechaTemporada = (data.map( ({fechaCreacion}) =>  moment(parseFromStringToDate2(fechaCreacion)).format("YYYY")));

            const dataFechaTemporadaFem = (data.filter(e => e.sexo === 2).map( ({fechaCreacion}) =>  moment(parseFromStringToDate2(fechaCreacion)).format("MM/YYYY")));
            const dataFechaTemporadaMasc = (data.filter(e => e.sexo === 1).map( ({fechaCreacion}) =>  moment(parseFromStringToDate2(fechaCreacion)).format("MM/YYYY")));


            let aniosArr = Array.from(new Set(anioFechaTemporada));

            aniosArr = aniosArr.map( e => Number(e)).sort( (a,b) => b - a);


            generarSelectYearFilter(aniosArr);

            const dataGraficoFem = getDataServicioPorTemporada(dataFechaTemporadaFem,2019);
            const dataGraficoMasc = getDataServicioPorTemporada(dataFechaTemporadaMasc,2019);

            graficoBarraVentaServiciosTemporada(dataGraficoFem,dataGraficoMasc);



        }

        , error: (xhr) => {
        }, complete: () => {

        }
    });
}

function obtenerInformacionDistribucionDepartamentos(){
    $.ajax({
        type: "GET",
        url: _ctx + "gestion/cliente/distribucion-departamento",
        success: function(data){
            let arrGraf= [];
            let arrGraf2= [];
            data.forEach( element => arrGraf.push({id: Number(element.departamentoUb) , qty : element.qtyClientesByDepartamento , dpto :""}) );
            const arrDep = new Array(25);
            const arrIdDeps = arrGraf.map(({id}) => id);

            for(let i = 1 ; i<= arrDep.length; i++){
                if(!arrIdDeps.includes(i)) {
                    arrGraf2.push({id: i, qty: 0, dpto: _departamentos[i - 1]})
                }
            }
            for( let i = 1; i <= arrGraf.length; i++ ){
                arrGraf[i-1].dpto = _departamentos[arrGraf[i-1].id - 1]  ;
            }

            let dataGrafico = arrGraf.concat(arrGraf2);
            dataGrafico = dataGrafico.sort( (a,b) => b.qty - a.qty);
            graficoDistribucionDepartamento(dataGrafico);
            generarPorcentajesDepartamentoDOM(dataGrafico);
        },
        error : (xhr) => {

        },
        complete: ()=>{
        }

    });
}

function getEdad(fechaNacimiento){

    console.log(fechaNacimiento);
  const fechaNac = parseFromStringToDate2(fechaNacimiento) ;
  return new Date().getFullYear() - fechaNac.getFullYear();
}



function quitarDuplicados(arr, attribute){


        const uniqueArr = arr
            .map(e => e[attribute])
            .map((e, i, final) => final.indexOf(e) === i && i)
            .filter(e => arr[e]).map(e => arr[e]);


        return uniqueArr;
}

function getAgeRangesValues(arr){

    let rang18_23 = arr.filter( e =>  e>=18 && e<=24).length;
    let rang24_29 = arr.filter( e =>  e>=25 && e<=29).length;
    let rang30_39 = arr.filter( e =>  e>=30 && e<=39).length;
    let rang40_49= arr.filter( e =>  e>=40 && e<=49).length;
    let rang50_59 = arr.filter( e =>  e>=50 && e<=59).length;
    let rangMayor60 = arr.filter( e =>  e>=60).length;
    let ageRangesByQty = [rang18_23,rang24_29,rang30_39,rang40_49,rang50_59,rangMayor60];

    return ageRangesByQty;
}


function getDataGraficoTipoCanal(arr){

    let val1= arr.filter( e =>  e === 1).length;
    let val2 = arr.filter( e =>  e === 2).length;
    let val3 = arr.filter( e =>  e === 3).length;
    let val4= arr.filter( e =>  e === 4).length;
    let val5 = arr.filter( e =>  e === 5).length;
    let val6 = arr.filter( e =>  e === 6).length;
    let val7 = arr.filter( e =>  e === 7).length;

    let canalVentavalues = [val1,val2,val3,val4,val5,val6,val7];

    return canalVentavalues;
}




function getDataGraficoTipoCanal(arr){

    let val1= arr.filter( e =>  e === 1).length;
    let val2 = arr.filter( e =>  e === 2).length;
    let val3 = arr.filter( e =>  e === 3).length;
    let val4= arr.filter( e =>  e === 4).length;
    let val5 = arr.filter( e =>  e === 5).length;
    let val6 = arr.filter( e =>  e === 6).length;
    let val7 = arr.filter( e =>  e === 7).length;

    let canalVentavalues = [val1,val2,val3,val4,val5,val6,val7];

    return canalVentavalues;
}


function getDataGraficoTipoServicio(arr){

    let val1= arr.filter( e =>  e === 0).length;
    let val2 = arr.filter( e =>  e === 1).length;
    let servicioValues = [val1,val2];

    return servicioValues;
}


function getDataGraficoCondFisica(arr){

    let valBasico= arr.filter( e => e.formaInicial> 0 & e.formaInicial<=33).length;
    let valMedio = arr.filter( e =>  e.formaInicial> 34 & e.formaInicial<=66).length;
    let valAvanzado = arr.filter( e => e.formaInicial >= 67).length;

    let condFisicaValues = [valBasico,valMedio,valAvanzado];

    return condFisicaValues;
}




function graficoCondFisicaBasicaMasc(arr) {

    let total = arr.reduce((a, b) => a + b);
    let canvas = document.getElementById('GraficoCondFisMascBas');
    let ctx = document.getElementById('GraficoCondFisMascBas').getContext('2d');

     Chart.defaults.doughnutCenterElement = Chart.helpers.clone(Chart.defaults.doughnut);

    var helpers = Chart.helpers;
    var defaults = Chart.defaults;

    Chart.controllers.doughnutCenterElement = Chart.controllers.doughnut.extend({
        updateElement: function(arc, index, reset) {
            var _this = this;
            var chart = _this.chart,
                chartArea = chart.chartArea,
                opts = chart.options,
                animationOpts = opts.animation,
                arcOpts = opts.elements.arc,
                centerX = (chartArea.left + chartArea.right) / 2,
                centerY = (chartArea.top + chartArea.bottom) / 2,
                startAngle = opts.rotation, // non reset case handled later
                endAngle = opts.rotation, // non reset case handled later
                dataset = _this.getDataset(),
                circumference = reset && animationOpts.animateRotate ? 0 : arc.hidden ? 0 : _this.calculateCircumference(dataset.data[index]) * (opts.circumference / (2.0 * Math.PI)),
                innerRadius = reset && animationOpts.animateScale ? 0 : _this.innerRadius,
                outerRadius = reset && animationOpts.animateScale ? 0 : _this.outerRadius,
                custom = arc.custom || {},
                valueAtIndexOrDefault = helpers.getValueAtIndexOrDefault;

            helpers.extend(arc, {
                // Utility
                _datasetIndex: _this.index,
                _index: index,

                // Desired view properties
                _model: {
                    x: centerX + chart.offsetX,
                    y: centerY + chart.offsetY,
                    startAngle: startAngle,
                    endAngle: endAngle,
                    circumference: circumference,
                    outerRadius: outerRadius,
                    innerRadius: innerRadius,
                    label: valueAtIndexOrDefault(dataset.label, index, chart.data.labels[index])
                },

                draw: function () {
                    var ctx = this._chart.ctx,
                        vm = this._view,
                        sA = vm.startAngle,
                        eA = vm.endAngle,
                        opts = this._chart.config.options;

                    var img = new Image();
                    img.src = '/img/iconos/icon_male.svg';
                    ctx.drawImage(img,centerX -10,centerY - 25, 20,50);

                    ctx.beginPath();

                    ctx.arc(vm.x, vm.y, vm.outerRadius, sA, eA);
                    ctx.arc(vm.x, vm.y, vm.innerRadius, eA, sA, true);

                    ctx.closePath();
                    ctx.strokeStyle = vm.borderColor;
                    ctx.lineWidth = vm.borderWidth;

                    ctx.fillStyle = vm.backgroundColor;

                    ctx.fill();
                    ctx.lineJoin = 'bevel';

                    if (vm.borderWidth) {
                        ctx.stroke();
                    }

                 }
            });

            var model = arc._model;
            model.backgroundColor = custom.backgroundColor ? custom.backgroundColor : valueAtIndexOrDefault(dataset.backgroundColor, index, arcOpts.backgroundColor);
            model.hoverBackgroundColor = custom.hoverBackgroundColor ? custom.hoverBackgroundColor : valueAtIndexOrDefault(dataset.hoverBackgroundColor, index, arcOpts.hoverBackgroundColor);
            model.borderWidth = custom.borderWidth ? custom.borderWidth : valueAtIndexOrDefault(dataset.borderWidth, index, arcOpts.borderWidth);
            model.borderColor = custom.borderColor ? custom.borderColor : valueAtIndexOrDefault(dataset.borderColor, index, arcOpts.borderColor);

            // Set correct angles if not resetting
            if (!reset || !animationOpts.animateRotate) {
                if (index === 0) {
                    model.startAngle = opts.rotation;
                } else {
                    model.startAngle = _this.getMeta().data[index - 1]._model.endAngle;
                }

                model.endAngle = model.startAngle + model.circumference;
            }

            arc.pivot();
        }
    });


    var config = {
        type: 'doughnutCenterElement',
        data: {
            labels: ["Básica","Otros"],
            datasets: [{
                data: [arr[0] , total - arr[0]],
                backgroundColor: ['#00b5f7', '#c2ccac'],
                borderColor: 'transparent',
            }],
        },
        options: {
            responsive: false,
            cutoutPercentage: 95,
            legend: {
                display: false,
                labels: {
                    // This more specific font property overrides the global property
                    fontColor: 'white',
                    fontFamily: 'GothamHTF-Book',
                }
            },
            title: {
                display: false
            }

        }
    };

    new Chart(ctx, config);



}

    function graficoCondFisicaMedioMasc(arr){

    let total = arr.reduce( (a,b) => a+b);
    let canvas = document.getElementById('GraficoCondFisMascMed');
    let ctx = document.getElementById('GraficoCondFisMascMed').getContext('2d');

        Chart.defaults.doughnutCenterElement = Chart.helpers.clone(Chart.defaults.doughnut);

        var helpers = Chart.helpers;
        var defaults = Chart.defaults;

        Chart.controllers.doughnutCenterElement = Chart.controllers.doughnut.extend({
            updateElement: function(arc, index, reset) {
                var _this = this;
                var chart = _this.chart,
                    chartArea = chart.chartArea,
                    opts = chart.options,
                    animationOpts = opts.animation,
                    arcOpts = opts.elements.arc,
                    centerX = (chartArea.left + chartArea.right) / 2,
                    centerY = (chartArea.top + chartArea.bottom) / 2,
                    startAngle = opts.rotation, // non reset case handled later
                    endAngle = opts.rotation, // non reset case handled later
                    dataset = _this.getDataset(),
                    circumference = reset && animationOpts.animateRotate ? 0 : arc.hidden ? 0 : _this.calculateCircumference(dataset.data[index]) * (opts.circumference / (2.0 * Math.PI)),
                    innerRadius = reset && animationOpts.animateScale ? 0 : _this.innerRadius,
                    outerRadius = reset && animationOpts.animateScale ? 0 : _this.outerRadius,
                    custom = arc.custom || {},
                    valueAtIndexOrDefault = helpers.getValueAtIndexOrDefault;

                helpers.extend(arc, {
                    // Utility
                    _datasetIndex: _this.index,
                    _index: index,

                    // Desired view properties
                    _model: {
                        x: centerX + chart.offsetX,
                        y: centerY + chart.offsetY,
                        startAngle: startAngle,
                        endAngle: endAngle,
                        circumference: circumference,
                        outerRadius: outerRadius,
                        innerRadius: innerRadius,
                        label: valueAtIndexOrDefault(dataset.label, index, chart.data.labels[index])
                    },

                    draw: function () {
                        var ctx = this._chart.ctx,
                            vm = this._view,
                            sA = vm.startAngle,
                            eA = vm.endAngle,
                            opts = this._chart.config.options;


                        var img = new Image();
                        img.src = '/img/iconos/icon_male.svg';
                        ctx.drawImage(img,centerX -10,centerY - 25, 20,50);

                        ctx.beginPath();

                        ctx.arc(vm.x, vm.y, vm.outerRadius, sA, eA);
                        ctx.arc(vm.x, vm.y, vm.innerRadius, eA, sA, true);

                        ctx.closePath();
                        ctx.strokeStyle = vm.borderColor;
                        ctx.lineWidth = vm.borderWidth;

                        ctx.fillStyle = vm.backgroundColor;

                        ctx.fill();
                        ctx.lineJoin = 'bevel';

                        if (vm.borderWidth) {
                            ctx.stroke();
                        }

                    }
                });

                var model = arc._model;
                model.backgroundColor = custom.backgroundColor ? custom.backgroundColor : valueAtIndexOrDefault(dataset.backgroundColor, index, arcOpts.backgroundColor);
                model.hoverBackgroundColor = custom.hoverBackgroundColor ? custom.hoverBackgroundColor : valueAtIndexOrDefault(dataset.hoverBackgroundColor, index, arcOpts.hoverBackgroundColor);
                model.borderWidth = custom.borderWidth ? custom.borderWidth : valueAtIndexOrDefault(dataset.borderWidth, index, arcOpts.borderWidth);
                model.borderColor = custom.borderColor ? custom.borderColor : valueAtIndexOrDefault(dataset.borderColor, index, arcOpts.borderColor);

                // Set correct angles if not resetting
                if (!reset || !animationOpts.animateRotate) {
                    if (index === 0) {
                        model.startAngle = opts.rotation;
                    } else {
                        model.startAngle = _this.getMeta().data[index - 1]._model.endAngle;
                    }

                    model.endAngle = model.startAngle + model.circumference;
                }

                arc.pivot();
            }
        });


        var config = {
            type: 'doughnutCenterElement',
            data: {
                labels: ["Medio","Otros"],
                datasets: [{
                    data: [arr[1] , total - arr[1]],
                    backgroundColor: ['#00b5f7', '#c2ccac'],
                    hoverBackgroundColor:  ["#2C42CA", "#7A6D64"],
                    borderColor: 'transparent',
                }],
            },
            options: {
                responsive: false,
                cutoutPercentage: 95,
                legend: {
                    display: false,
                    labels: {
                        // This more specific font property overrides the global property
                        fontColor: 'white',
                        fontFamily: 'GothamHTF-Book',
                    }
                },
                title: {
                    display: false
                }

            }
        };

        new Chart(ctx, config);


}



function graficoCondFisicaAvanzadoMasc(arr){
    let total = arr.reduce( (a,b) => a+b);
    let canvas = document.getElementById('GraficoCondFisMascAvanz');
    let ctx = document.getElementById('GraficoCondFisMascAvanz').getContext('2d');

    Chart.defaults.doughnutCenterElement = Chart.helpers.clone(Chart.defaults.doughnut);

    var helpers = Chart.helpers;
    var defaults = Chart.defaults;

    Chart.controllers.doughnutCenterElement = Chart.controllers.doughnut.extend({
        updateElement: function(arc, index, reset) {
            var _this = this;
            var chart = _this.chart,
                chartArea = chart.chartArea,
                opts = chart.options,
                animationOpts = opts.animation,
                arcOpts = opts.elements.arc,
                centerX = (chartArea.left + chartArea.right) / 2,
                centerY = (chartArea.top + chartArea.bottom) / 2,
                startAngle = opts.rotation, // non reset case handled later
                endAngle = opts.rotation, // non reset case handled later
                dataset = _this.getDataset(),
                circumference = reset && animationOpts.animateRotate ? 0 : arc.hidden ? 0 : _this.calculateCircumference(dataset.data[index]) * (opts.circumference / (2.0 * Math.PI)),
                innerRadius = reset && animationOpts.animateScale ? 0 : _this.innerRadius,
                outerRadius = reset && animationOpts.animateScale ? 0 : _this.outerRadius,
                custom = arc.custom || {},
                valueAtIndexOrDefault = helpers.getValueAtIndexOrDefault;

            helpers.extend(arc, {
                // Utility
                _datasetIndex: _this.index,
                _index: index,

                // Desired view properties
                _model: {
                    x: centerX + chart.offsetX,
                    y: centerY + chart.offsetY,
                    startAngle: startAngle,
                    endAngle: endAngle,
                    circumference: circumference,
                    outerRadius: outerRadius,
                    innerRadius: innerRadius,
                    label: valueAtIndexOrDefault(dataset.label, index, chart.data.labels[index])
                },

                draw: function () {
                    var ctx = this._chart.ctx,
                        vm = this._view,
                        sA = vm.startAngle,
                        eA = vm.endAngle,
                        opts = this._chart.config.options;


                    var img = new Image();
                    img.src = '/img/iconos/icon_male.svg';
                    ctx.drawImage(img,centerX -10,centerY - 25, 20,50);

                    ctx.beginPath();

                    ctx.arc(vm.x, vm.y, vm.outerRadius, sA, eA);
                    ctx.arc(vm.x, vm.y, vm.innerRadius, eA, sA, true);

                    ctx.closePath();
                    ctx.strokeStyle = vm.borderColor;
                    ctx.lineWidth = vm.borderWidth;

                    ctx.fillStyle = vm.backgroundColor;

                    ctx.fill();
                    ctx.lineJoin = 'bevel';

                    if (vm.borderWidth) {
                        ctx.stroke();
                    }

                }
            });

            var model = arc._model;
            model.backgroundColor = custom.backgroundColor ? custom.backgroundColor : valueAtIndexOrDefault(dataset.backgroundColor, index, arcOpts.backgroundColor);
            model.hoverBackgroundColor = custom.hoverBackgroundColor ? custom.hoverBackgroundColor : valueAtIndexOrDefault(dataset.hoverBackgroundColor, index, arcOpts.hoverBackgroundColor);
            model.borderWidth = custom.borderWidth ? custom.borderWidth : valueAtIndexOrDefault(dataset.borderWidth, index, arcOpts.borderWidth);
            model.borderColor = custom.borderColor ? custom.borderColor : valueAtIndexOrDefault(dataset.borderColor, index, arcOpts.borderColor);

            // Set correct angles if not resetting
            if (!reset || !animationOpts.animateRotate) {
                if (index === 0) {
                    model.startAngle = opts.rotation;
                } else {
                    model.startAngle = _this.getMeta().data[index - 1]._model.endAngle;
                }

                model.endAngle = model.startAngle + model.circumference;
            }

            arc.pivot();
        }
    });


    var config = {
        type: 'doughnutCenterElement',
        data: {
            labels: ["Avanzado","Otros"],
            datasets: [{
                data: [arr[2] , total - arr[2]],
                backgroundColor: ['#00b5f7', '#c2ccac'],
                hoverBackgroundColor:  ["#2C42CA", "#7A6D64"],
                borderColor: 'transparent',
            }],
        },
        options: {
            responsive: false,
            cutoutPercentage: 95,
            legend: {
                display: false,
                labels: {
                    // This more specific font property overrides the global property
                    fontColor: 'white',
                    fontFamily: 'GothamHTF-Book',
                }
            },
            title: {
                display: false
            }

        }
    };

    new Chart(ctx, config);
}



function graficoCondFisicaBasicaFem(arr){

    let total = arr.reduce( (a,b) => a+b);
    let canvas = document.getElementById('GraficoCondFisFemBas');
    let ctx = document.getElementById('GraficoCondFisFemBas').getContext('2d');


    Chart.defaults.doughnutCenterElement = Chart.helpers.clone(Chart.defaults.doughnut);

    var helpers = Chart.helpers;
    var defaults = Chart.defaults;

    Chart.controllers.doughnutCenterElement = Chart.controllers.doughnut.extend({
        updateElement: function(arc, index, reset) {
            var _this = this;
            var chart = _this.chart,
                chartArea = chart.chartArea,
                opts = chart.options,
                animationOpts = opts.animation,
                arcOpts = opts.elements.arc,
                centerX = (chartArea.left + chartArea.right) / 2,
                centerY = (chartArea.top + chartArea.bottom) / 2,
                startAngle = opts.rotation, // non reset case handled later
                endAngle = opts.rotation, // non reset case handled later
                dataset = _this.getDataset(),
                circumference = reset && animationOpts.animateRotate ? 0 : arc.hidden ? 0 : _this.calculateCircumference(dataset.data[index]) * (opts.circumference / (2.0 * Math.PI)),
                innerRadius = reset && animationOpts.animateScale ? 0 : _this.innerRadius,
                outerRadius = reset && animationOpts.animateScale ? 0 : _this.outerRadius,
                custom = arc.custom || {},
                valueAtIndexOrDefault = helpers.getValueAtIndexOrDefault;

            helpers.extend(arc, {
                // Utility
                _datasetIndex: _this.index,
                _index: index,

                // Desired view properties
                _model: {
                    x: centerX + chart.offsetX,
                    y: centerY + chart.offsetY,
                    startAngle: startAngle,
                    endAngle: endAngle,
                    circumference: circumference,
                    outerRadius: outerRadius,
                    innerRadius: innerRadius,
                    label: valueAtIndexOrDefault(dataset.label, index, chart.data.labels[index])
                },

                draw: function () {
                    var ctx = this._chart.ctx,
                        vm = this._view,
                        sA = vm.startAngle,
                        eA = vm.endAngle,
                        opts = this._chart.config.options;


                    var img = new Image();
                    img.src = '/img/iconos/icon_female.svg';
                    ctx.drawImage(img,centerX -15,centerY - 25, 30,50);


                    ctx.beginPath();

                    ctx.arc(vm.x, vm.y, vm.outerRadius, sA, eA);
                    ctx.arc(vm.x, vm.y, vm.innerRadius, eA, sA, true);

                    ctx.closePath();
                    ctx.strokeStyle = vm.borderColor;
                    ctx.lineWidth = vm.borderWidth;

                    ctx.fillStyle = vm.backgroundColor;

                    ctx.fill();
                    ctx.lineJoin = 'bevel';

                    if (vm.borderWidth) {
                        ctx.stroke();
                    }

                }
            });

            var model = arc._model;
            model.backgroundColor = custom.backgroundColor ? custom.backgroundColor : valueAtIndexOrDefault(dataset.backgroundColor, index, arcOpts.backgroundColor);
            model.hoverBackgroundColor = custom.hoverBackgroundColor ? custom.hoverBackgroundColor : valueAtIndexOrDefault(dataset.hoverBackgroundColor, index, arcOpts.hoverBackgroundColor);
            model.borderWidth = custom.borderWidth ? custom.borderWidth : valueAtIndexOrDefault(dataset.borderWidth, index, arcOpts.borderWidth);
            model.borderColor = custom.borderColor ? custom.borderColor : valueAtIndexOrDefault(dataset.borderColor, index, arcOpts.borderColor);

            // Set correct angles if not resetting
            if (!reset || !animationOpts.animateRotate) {
                if (index === 0) {
                    model.startAngle = opts.rotation;
                } else {
                    model.startAngle = _this.getMeta().data[index - 1]._model.endAngle;
                }

                model.endAngle = model.startAngle + model.circumference;
            }

            arc.pivot();
        }
    });

    var config = {
        type: 'doughnutCenterElement',
        data: {
            labels: ["Básica","Otros"],
            datasets: [{
                data: [arr[0] , total - arr[0]],
                backgroundColor: ['#FF00EB', '#c2ccac'],
                hoverBackgroundColor:  ["#EB0955", "#7A6D64"],
                borderColor: 'transparent',
            }],
        },
        options: {
            responsive: false,
            cutoutPercentage: 95,
            legend: {
                display: false,
                labels: {
                    // This more specific font property overrides the global property
                    fontColor: 'white',
                    fontFamily: 'GothamHTF-Book',
                }
            },
            title: {
                display: false
            }

        }
    };

    new Chart(ctx, config);


}


function graficoCondFisicaMedioFem(arr){



    let total = arr.reduce( (a,b) => a+b);
    let canvas = document.getElementById('GraficoCondFisFemMed');
    let ctx = document.getElementById('GraficoCondFisFemMed').getContext('2d');

    Chart.defaults.doughnutCenterElement = Chart.helpers.clone(Chart.defaults.doughnut);

    var helpers = Chart.helpers;
    var defaults = Chart.defaults;

    Chart.controllers.doughnutCenterElement = Chart.controllers.doughnut.extend({
        updateElement: function(arc, index, reset) {
            var _this = this;
            var chart = _this.chart,
                chartArea = chart.chartArea,
                opts = chart.options,
                animationOpts = opts.animation,
                arcOpts = opts.elements.arc,
                centerX = (chartArea.left + chartArea.right) / 2,
                centerY = (chartArea.top + chartArea.bottom) / 2,
                startAngle = opts.rotation, // non reset case handled later
                endAngle = opts.rotation, // non reset case handled later
                dataset = _this.getDataset(),
                circumference = reset && animationOpts.animateRotate ? 0 : arc.hidden ? 0 : _this.calculateCircumference(dataset.data[index]) * (opts.circumference / (2.0 * Math.PI)),
                innerRadius = reset && animationOpts.animateScale ? 0 : _this.innerRadius,
                outerRadius = reset && animationOpts.animateScale ? 0 : _this.outerRadius,
                custom = arc.custom || {},
                valueAtIndexOrDefault = helpers.getValueAtIndexOrDefault;

            helpers.extend(arc, {
                // Utility
                _datasetIndex: _this.index,
                _index: index,

                // Desired view properties
                _model: {
                    x: centerX + chart.offsetX,
                    y: centerY + chart.offsetY,
                    startAngle: startAngle,
                    endAngle: endAngle,
                    circumference: circumference,
                    outerRadius: outerRadius,
                    innerRadius: innerRadius,
                    label: valueAtIndexOrDefault(dataset.label, index, chart.data.labels[index])
                },

                draw: function () {
                    var ctx = this._chart.ctx,
                        vm = this._view,
                        sA = vm.startAngle,
                        eA = vm.endAngle,
                        opts = this._chart.config.options;



                    var img = new Image();
                    img.src = '/img/iconos/icon_female.svg';
                    ctx.drawImage(img,centerX -15,centerY - 25, 30,50);


                    ctx.beginPath();

                    ctx.arc(vm.x, vm.y, vm.outerRadius, sA, eA);
                    ctx.arc(vm.x, vm.y, vm.innerRadius, eA, sA, true);

                    ctx.closePath();
                    ctx.strokeStyle = vm.borderColor;
                    ctx.lineWidth = vm.borderWidth;

                    ctx.fillStyle = vm.backgroundColor;

                    ctx.fill();
                    ctx.lineJoin = 'bevel';

                    if (vm.borderWidth) {
                        ctx.stroke();
                    }

                }
            });

            var model = arc._model;
            model.backgroundColor = custom.backgroundColor ? custom.backgroundColor : valueAtIndexOrDefault(dataset.backgroundColor, index, arcOpts.backgroundColor);
            model.hoverBackgroundColor = custom.hoverBackgroundColor ? custom.hoverBackgroundColor : valueAtIndexOrDefault(dataset.hoverBackgroundColor, index, arcOpts.hoverBackgroundColor);
            model.borderWidth = custom.borderWidth ? custom.borderWidth : valueAtIndexOrDefault(dataset.borderWidth, index, arcOpts.borderWidth);
            model.borderColor = custom.borderColor ? custom.borderColor : valueAtIndexOrDefault(dataset.borderColor, index, arcOpts.borderColor);

            // Set correct angles if not resetting
            if (!reset || !animationOpts.animateRotate) {
                if (index === 0) {
                    model.startAngle = opts.rotation;
                } else {
                    model.startAngle = _this.getMeta().data[index - 1]._model.endAngle;
                }

                model.endAngle = model.startAngle + model.circumference;
            }

            arc.pivot();
        }
    });

    var config = {
        type: 'doughnutCenterElement',
        data: {
            labels: ["Medio","Otros"],
            datasets: [{
                data: [arr[1] , total - arr[1]],
                backgroundColor: ['#FF00EB', '#c2ccac'],
                hoverBackgroundColor:  ["#EB0955", "#7A6D64"],
                borderColor: 'transparent',
            }],
        },
        options: {
            responsive: false,
            cutoutPercentage: 95,
            legend: {
                display: false,
                labels: {
                    // This more specific font property overrides the global property
                    fontColor: 'white',
                    fontFamily: 'GothamHTF-Book',
                }
            },
            title: {
                display: false
            }

        }
    };

    new Chart(ctx, config);



}



function graficoCondFisicaAvanzadoFem(arr){



    let total = arr.reduce( (a,b) => a+b);
    let canvas = document.getElementById('GraficoCondFisFemAvanz');
    let ctx = document.getElementById('GraficoCondFisFemAvanz').getContext('2d');

    Chart.defaults.doughnutCenterElement = Chart.helpers.clone(Chart.defaults.doughnut);

    var helpers = Chart.helpers;
    var defaults = Chart.defaults;

    Chart.controllers.doughnutCenterElement = Chart.controllers.doughnut.extend({
        updateElement: function(arc, index, reset) {
            var _this = this;
            var chart = _this.chart,
                chartArea = chart.chartArea,
                opts = chart.options,
                animationOpts = opts.animation,
                arcOpts = opts.elements.arc,
                centerX = (chartArea.left + chartArea.right) / 2,
                centerY = (chartArea.top + chartArea.bottom) / 2,
                startAngle = opts.rotation, // non reset case handled later
                endAngle = opts.rotation, // non reset case handled later
                dataset = _this.getDataset(),
                circumference = reset && animationOpts.animateRotate ? 0 : arc.hidden ? 0 : _this.calculateCircumference(dataset.data[index]) * (opts.circumference / (2.0 * Math.PI)),
                innerRadius = reset && animationOpts.animateScale ? 0 : _this.innerRadius,
                outerRadius = reset && animationOpts.animateScale ? 0 : _this.outerRadius,
                custom = arc.custom || {},
                valueAtIndexOrDefault = helpers.getValueAtIndexOrDefault;

            helpers.extend(arc, {
                // Utility
                _datasetIndex: _this.index,
                _index: index,

                // Desired view properties
                _model: {
                    x: centerX + chart.offsetX,
                    y: centerY + chart.offsetY,
                    startAngle: startAngle,
                    endAngle: endAngle,
                    circumference: circumference,
                    outerRadius: outerRadius,
                    innerRadius: innerRadius,
                    label: valueAtIndexOrDefault(dataset.label, index, chart.data.labels[index])
                },

                draw: function () {
                    var ctx = this._chart.ctx,
                        vm = this._view,
                        sA = vm.startAngle,
                        eA = vm.endAngle,
                        opts = this._chart.config.options;


                    var img = new Image();
                    img.src = '/img/iconos/icon_female.svg';
                    ctx.drawImage(img,centerX -15,centerY - 25, 30,50);

                    ctx.beginPath();

                    ctx.arc(vm.x, vm.y, vm.outerRadius, sA, eA);
                    ctx.arc(vm.x, vm.y, vm.innerRadius, eA, sA, true);

                    ctx.closePath();
                    ctx.strokeStyle = vm.borderColor;
                    ctx.lineWidth = vm.borderWidth;

                    ctx.fillStyle = vm.backgroundColor;

                    ctx.fill();
                    ctx.lineJoin = 'bevel';

                    if (vm.borderWidth) {
                        ctx.stroke();
                    }

                }
            });

            var model = arc._model;
            model.backgroundColor = custom.backgroundColor ? custom.backgroundColor : valueAtIndexOrDefault(dataset.backgroundColor, index, arcOpts.backgroundColor);
            model.hoverBackgroundColor = custom.hoverBackgroundColor ? custom.hoverBackgroundColor : valueAtIndexOrDefault(dataset.hoverBackgroundColor, index, arcOpts.hoverBackgroundColor);
            model.borderWidth = custom.borderWidth ? custom.borderWidth : valueAtIndexOrDefault(dataset.borderWidth, index, arcOpts.borderWidth);
            model.borderColor = custom.borderColor ? custom.borderColor : valueAtIndexOrDefault(dataset.borderColor, index, arcOpts.borderColor);

            // Set correct angles if not resetting
            if (!reset || !animationOpts.animateRotate) {
                if (index === 0) {
                    model.startAngle = opts.rotation;
                } else {
                    model.startAngle = _this.getMeta().data[index - 1]._model.endAngle;
                }

                model.endAngle = model.startAngle + model.circumference;
            }

            arc.pivot();
        }
    });

    var config = {
        type: 'doughnutCenterElement',
        data: {
            labels: ["Avanzado","Otros"],
            datasets: [{
                data: [arr[2] , total - arr[2]],
                backgroundColor: ['#FF00EB', '#c2ccac'],
                hoverBackgroundColor:  ["#EB0955", "#7A6D64"],
                borderColor: 'transparent',
            }],
        },
        options: {
            responsive: false,
            cutoutPercentage: 95,
            legend: {
                display: false,
                labels: {
                    // This more specific font property overrides the global property
                    fontColor: 'white',
                    fontFamily: 'GothamHTF-Book',
                }
            },
            title: {
                display: false
            }

        }
    };

    new Chart(ctx, config);



}



function graficoDistribucionEdadFemenino(ageRangesValues){


    let canvas = document.getElementById('GraficoDistribucionMujer');
    let ctx = document.getElementById('GraficoDistribucionMujer').getContext('2d');

    if($chartMiniPorc.ctx != undefined){
        $chartMiniPorc.destroy();
    }


    Chart.defaults.doughnutCenterElement = Chart.helpers.clone(Chart.defaults.doughnut);

    var helpers = Chart.helpers;
    var defaults = Chart.defaults;

    Chart.controllers.doughnutCenterElement = Chart.controllers.doughnut.extend({
        updateElement: function(arc, index, reset) {
            var _this = this;
            var chart = _this.chart,
                chartArea = chart.chartArea,
                opts = chart.options,
                animationOpts = opts.animation,
                arcOpts = opts.elements.arc,
                centerX = (chartArea.left + chartArea.right) / 2,
                centerY = (chartArea.top + chartArea.bottom) / 2,
                startAngle = opts.rotation, // non reset case handled later
                endAngle = opts.rotation, // non reset case handled later
                dataset = _this.getDataset(),
                circumference = reset && animationOpts.animateRotate ? 0 : arc.hidden ? 0 : _this.calculateCircumference(dataset.data[index]) * (opts.circumference / (2.0 * Math.PI)),
                innerRadius = reset && animationOpts.animateScale ? 0 : _this.innerRadius,
                outerRadius = reset && animationOpts.animateScale ? 0 : _this.outerRadius,
                custom = arc.custom || {},
                valueAtIndexOrDefault = helpers.getValueAtIndexOrDefault;

            helpers.extend(arc, {
                // Utility
                _datasetIndex: _this.index,
                _index: index,

                // Desired view properties
                _model: {
                    x: centerX + chart.offsetX,
                    y: centerY + chart.offsetY,
                    startAngle: startAngle,
                    endAngle: endAngle,
                    circumference: circumference,
                    outerRadius: outerRadius,
                    innerRadius: innerRadius,
                    label: valueAtIndexOrDefault(dataset.label, index, chart.data.labels[index])
                },

                draw: function () {
                    var ctx = this._chart.ctx,
                        vm = this._view,
                        sA = vm.startAngle,
                        eA = vm.endAngle,
                        opts = this._chart.config.options;


                    var img = new Image();
                    img.src = '/img/iconos/icon_female.svg';
                    ctx.drawImage(img,centerX -15,centerY - 25, 30,50);

                    ctx.beginPath();

                    ctx.arc(vm.x, vm.y, vm.outerRadius, sA, eA);
                    ctx.arc(vm.x, vm.y, vm.innerRadius, eA, sA, true);

                    ctx.closePath();
                    ctx.strokeStyle = vm.borderColor;
                    ctx.lineWidth = vm.borderWidth;

                    ctx.fillStyle = vm.backgroundColor;

                    ctx.fill();
                    ctx.lineJoin = 'bevel';

                    if (vm.borderWidth) {
                        ctx.stroke();
                    }

                }
            });

            var model = arc._model;
            model.backgroundColor = custom.backgroundColor ? custom.backgroundColor : valueAtIndexOrDefault(dataset.backgroundColor, index, arcOpts.backgroundColor);
            model.hoverBackgroundColor = custom.hoverBackgroundColor ? custom.hoverBackgroundColor : valueAtIndexOrDefault(dataset.hoverBackgroundColor, index, arcOpts.hoverBackgroundColor);
            model.borderWidth = custom.borderWidth ? custom.borderWidth : valueAtIndexOrDefault(dataset.borderWidth, index, arcOpts.borderWidth);
            model.borderColor = custom.borderColor ? custom.borderColor : valueAtIndexOrDefault(dataset.borderColor, index, arcOpts.borderColor);

            // Set correct angles if not resetting
            if (!reset || !animationOpts.animateRotate) {
                if (index === 0) {
                    model.startAngle = opts.rotation;
                } else {
                    model.startAngle = _this.getMeta().data[index - 1]._model.endAngle;
                }

                model.endAngle = model.startAngle + model.circumference;
            }

            arc.pivot();
        }
    });


    var config = {
        type: 'doughnutCenterElement',
        data: {
            labels: ["18-24 edad" , "25-29 edad","30-39 edad","40-49 edad","50-59 edad","Mayor de 60 edad" ],
            datasets: [{
                data: ageRangesValues,
                backgroundColor: arrColorFem,
                borderColor: 'transparent',
            }],
        },
        options: {
            responsive:false,
            cutoutPercentage: 95,
            legend: {
                display: false,
                labels: {
                    // This more specific font property overrides the global property
                    fontColor : 'white',
                    fontFamily : 'GothamHTF-Book',
                }
            },
            title: {
                display: false
            },
            tooltips: {
                bodyFontSize: 15
            }
            /*segmentShowStroke: false*/
            //Boolean - Whether we should show a stroke on each segment
            // set to false to hide the space/line between segments

        }


    }

     new Chart(ctx,config);


}

function graficoDistribucionEdadMasculino(ageRangesValues){


    let canvas = document.getElementById('GraficoDistribucionHombre');
    let ctx = document.getElementById('GraficoDistribucionHombre').getContext('2d');

    Chart.defaults.doughnutCenterElement = Chart.helpers.clone(Chart.defaults.doughnut);

    var helpers = Chart.helpers;
    var defaults = Chart.defaults;

    Chart.controllers.doughnutCenterElement = Chart.controllers.doughnut.extend({
        updateElement: function(arc, index, reset) {
            var _this = this;
            var chart = _this.chart,
                chartArea = chart.chartArea,
                opts = chart.options,
                animationOpts = opts.animation,
                arcOpts = opts.elements.arc,
                centerX = (chartArea.left + chartArea.right) / 2,
                centerY = (chartArea.top + chartArea.bottom) / 2,
                startAngle = opts.rotation, // non reset case handled later
                endAngle = opts.rotation, // non reset case handled later
                dataset = _this.getDataset(),
                circumference = reset && animationOpts.animateRotate ? 0 : arc.hidden ? 0 : _this.calculateCircumference(dataset.data[index]) * (opts.circumference / (2.0 * Math.PI)),
                innerRadius = reset && animationOpts.animateScale ? 0 : _this.innerRadius,
                outerRadius = reset && animationOpts.animateScale ? 0 : _this.outerRadius,
                custom = arc.custom || {},
                valueAtIndexOrDefault = helpers.getValueAtIndexOrDefault;

            helpers.extend(arc, {
                // Utility
                _datasetIndex: _this.index,
                _index: index,

                // Desired view properties
                _model: {
                    x: centerX + chart.offsetX,
                    y: centerY + chart.offsetY,
                    startAngle: startAngle,
                    endAngle: endAngle,
                    circumference: circumference,
                    outerRadius: outerRadius,
                    innerRadius: innerRadius,
                    label: valueAtIndexOrDefault(dataset.label, index, chart.data.labels[index])
                },

                draw: function () {
                    var ctx = this._chart.ctx,
                        vm = this._view,
                        sA = vm.startAngle,
                        eA = vm.endAngle,
                        opts = this._chart.config.options;


                    var img = new Image();
                    img.src = '/img/iconos/icon_male.svg';
                    ctx.drawImage(img,centerX -10,centerY - 25, 25,50);

                    ctx.beginPath();

                    ctx.arc(vm.x, vm.y, vm.outerRadius, sA, eA);
                    ctx.arc(vm.x, vm.y, vm.innerRadius, eA, sA, true);

                    ctx.closePath();
                    ctx.strokeStyle = vm.borderColor;
                    ctx.lineWidth = vm.borderWidth;

                    ctx.fillStyle = vm.backgroundColor;

                    ctx.fill();
                    ctx.lineJoin = 'bevel';

                    if (vm.borderWidth) {
                        ctx.stroke();
                    }

                }
            });

            var model = arc._model;
            model.backgroundColor = custom.backgroundColor ? custom.backgroundColor : valueAtIndexOrDefault(dataset.backgroundColor, index, arcOpts.backgroundColor);
            model.hoverBackgroundColor = custom.hoverBackgroundColor ? custom.hoverBackgroundColor : valueAtIndexOrDefault(dataset.hoverBackgroundColor, index, arcOpts.hoverBackgroundColor);
            model.borderWidth = custom.borderWidth ? custom.borderWidth : valueAtIndexOrDefault(dataset.borderWidth, index, arcOpts.borderWidth);
            model.borderColor = custom.borderColor ? custom.borderColor : valueAtIndexOrDefault(dataset.borderColor, index, arcOpts.borderColor);

            // Set correct angles if not resetting
            if (!reset || !animationOpts.animateRotate) {
                if (index === 0) {
                    model.startAngle = opts.rotation;
                } else {
                    model.startAngle = _this.getMeta().data[index - 1]._model.endAngle;
                }

                model.endAngle = model.startAngle + model.circumference;
            }

            arc.pivot();
        }
    });




   let config =  {
        type: 'doughnutCenterElement',
        data: {
            labels: ["18-24 edad" , "25-29 edad","30-39 edad","40-49 edad","50-59 edad","Mayor de 60 edad" ],
            datasets: [{
                data: ageRangesValues,
                backgroundColor: arrColorMasc,
                borderColor: 'transparent',
            }],
        },
        options: {
            responsive:false,
            cutoutPercentage: 95,
            legend: {
                display: false,
                labels: {
                    // This more specific font property overrides the global property
                    fontColor : 'white',
                    fontFamily : 'GothamHTF-Book',
                }
            },
            title: {
                display: false
            },
            tooltips: {
                bodyFontSize: 15
            }
            /*segmentShowStroke: false*/
            //Boolean - Whether we should show a stroke on each segment
            // set to false to hide the space/line between segments

        }


    }

    new Chart(ctx,config);


}


function generarInfoDistribucionEdadMascDOM(dataMasc){

    console.log(dataMasc);

    $('.masc-porc').text(`${dataMasc.porcentajeMasc}%`);
    $('.masc-age-prom').text(`${dataMasc.edadPromedioMasc} años`);

    $('.graph-porc .masc-range-porc').each( function(index) {$(this).html(`${dataMasc.porcentajeRangosMasc[index] }%`)
                                                             $(this).css("color", arrColorMasc[index])});

    $('.graph-porc svg.masc').each( function(index) {$(this).css("color", arrColorMasc[index])});


}

function generarInfoDistribucionEdadFemDOM(dataFem){

    $('.fem-porc').text(`${dataFem.porcentajeFem}%`);
    $('.fem-age-prom').text(`${dataFem.edadPromedioFem} años`);


    $('.graph-porc .fem-range-porc').each( function(index) {$(this).html(`${dataFem.porcentajeRangosFem[index] }%`);
                                                          $(this).css("color", arrColorFem[index])});

    $('.graph-porc svg.fem').each( function(index) {$(this).css("color", arrColorFem[index])});


}


function setGraficoMasc(data){

    const dataMasc = {};
    const edadClientesMasc = data.filter(element => element.sexo === 1)
         .map(({fechaNacimiento}) => getEdad(fechaNacimiento));


    if(edadClientesMasc.length > 0){
        dataMasc.edadPromedioMasc = Math.round((edadClientesMasc.reduce((acc, val) => acc + val)) / edadClientesMasc.length);
        dataMasc.porcentajeMasc = Math.round(edadClientesMasc.length * 100 / cantidadUsuarios);

        const dataGraphMasculino = getAgeRangesValues(edadClientesMasc);

        dataMasc.porcentajeRangosMasc = dataGraphMasculino.map(e => ((e / edadClientesMasc.length) * 100));
        dataMasc.porcentajeRangosMasc = roundedPercentage(dataMasc.porcentajeRangosMasc , 100);
        graficoDistribucionEdadMasculino(dataGraphMasculino);
        generarInfoDistribucionEdadMascDOM(dataMasc);
    }else{
        let dataGraphMasculino = [0,0,0,0,0,0];
        let dataMasc= {edadPromedioMasc: [0] ,porcentajeRangosMasc : [0] , porcentajeMasc : [0]}
        graficoDistribucionEdadMasculino(dataGraphMasculino);
        generarInfoDistribucionEdadMascDOM(dataMasc);
    }

}


function setGraficoFem(data){

    const dataFem = {};
    //Femenino
    const edadClientesFem = data.filter(element => element.sexo === 2)
        .map(({fechaNacimiento})=> getEdad(fechaNacimiento)  );


    dataFem.porcentajeFem = Math.round(edadClientesFem.length * 100 / cantidadUsuarios );
    dataFem.edadPromedioFem =Math.round((edadClientesFem.reduce((acc,val) => acc +val))/edadClientesFem.length);

    const dataGraphFemenino =  (getAgeRangesValues(edadClientesFem));

    dataFem.porcentajeRangosFem = dataGraphFemenino.map( e => Math.round((e/edadClientesFem.length)*100));

    console.log(dataFem);

    graficoDistribucionEdadFemenino(dataGraphFemenino);
    generarInfoDistribucionEdadFemDOM(dataFem);
}


function setDataCanalDistrSexo(data, graphGeneralData){
    const canalVentaFem = data.filter(element => element.sexo === 2)
                              .map(({tipoCanalVentaId})=> tipoCanalVentaId);
    const canalVentaMasc = data.filter(element => element.sexo === 1)
        .map(({tipoCanalVentaId})=> tipoCanalVentaId);


    let datosMasc = getDataGraficoTipoCanal(canalVentaMasc);
    let datosFem = getDataGraficoTipoCanal(canalVentaFem);
    let porcentajeMasc= [];
    let porcentajeFem = [];

    for( let i = 0 ; i <graphGeneralData.length; i++){

        if(graphGeneralData[i]> 0){
         let porcValorMasc = ( datosMasc[i] * 100 ) / graphGeneralData[i];
         let porcValorFem = ( datosFem[i] * 100 ) / graphGeneralData[i];
         let porcentajeDistrSexo = [porcValorMasc, porcValorFem];
         porcentajeDistrSexo = roundedPercentage(porcentajeDistrSexo, 100);
         porcentajeMasc.push(porcentajeDistrSexo[0]);
         porcentajeFem.push(porcentajeDistrSexo[1]);
        }else{
            porcentajeMasc.push(0);
            porcentajeFem.push(0);
        }
    }
    generarPorcentajeSexoCanalDOM(porcentajeFem, 0); // 0 - mujer 1-hombre
    generarPorcentajeSexoCanalDOM(porcentajeMasc, 1); // 0 - mujer 1-hombre
}

function graficoCanalesUsados(dataCanales){

    let dataLength = dataCanales.reduce( (a,b) => a+b);
    let porcentajes = dataCanales.map( e => Math.round((e/dataLength)*100));


    //GraficoCanalesUsados
    var ctx = document.getElementById("GraficoCanalesUsados").getContext("2d");
    var data = {
        labels: canalesVenta,
        datasets: [
            {
                data: dataCanales,
                backgroundColor: "#ff8402"
            }
          ]
    };

    const myBarChartHoriz = new Chart(ctx, {
        type: 'horizontalBar',
        data: data,
        options: {
            barValueSpacing: 20,
            scales: {
                xAxes: [{
                    ticks: {
                        display: false
                    },
                    gridLines: {
                        display:false,
                        drawBorder: false
                    },
                }],
                yAxes: [{
                    barThickness : 15,
                    ticks:{
                      callback: function(value,index){return porcentajes[index]+ " %"},
                      fontSize: 13,
                      fontColor: "#ff8402",
                      fontFamily: "GothamHTF-Book"
                    },
                    gridLines: {
                        display:false,
                        drawBorder: false
                    },
                }]
            },
            hover: {
                mode: 'nearest',
                intersect: true
            },
            tooltips: {
                mode: 'nearest'
            },
            legend:{
                position: 'bottom',
                display: false
            }

        }
    });
    Chart.elements.Rectangle.prototype.draw = function() {

        var ctx = this._chart.ctx;
        var vm = this._view;
        var left, right, top, bottom, signX, signY, borderSkipped, radius;
        var borderWidth = vm.borderWidth;
        // Set Radius Here
        // If radius is large enough to cause drawing errors a max radius is imposed
        var cornerRadius = 20;

        if (!vm.horizontal) {
            // bar
            left = vm.x - vm.width / 2;
            right = vm.x + vm.width / 2;
            top = vm.y;
            bottom = vm.base;
            signX = 1;
            signY = bottom > top? 1: -1;
            borderSkipped = vm.borderSkipped || 'bottom';
        } else {
            // horizontal bar
            left = vm.base;
            right = vm.x;
            top = vm.y - vm.height / 2;
            bottom = vm.y + vm.height / 2;
            signX = right > left? 1: -1;
            signY = 1;
            borderSkipped = vm.borderSkipped || 'left';
        }

        // Canvas doesn't allow us to stroke inside the width so we can
        // adjust the sizes to fit if we're setting a stroke on the line
        if (borderWidth) {
            // borderWidth shold be less than bar width and bar height.
            var barSize = Math.min(Math.abs(left - right), Math.abs(top - bottom));
            borderWidth = borderWidth > barSize? barSize: borderWidth;
            var halfStroke = borderWidth / 2;
            // Adjust borderWidth when bar top position is near vm.base(zero).
            var borderLeft = left + (borderSkipped !== 'left'? halfStroke * signX: 0);
            var borderRight = right + (borderSkipped !== 'right'? -halfStroke * signX: 0);
            var borderTop = top + (borderSkipped !== 'top'? halfStroke * signY: 0);
            var borderBottom = bottom + (borderSkipped !== 'bottom'? -halfStroke * signY: 0);
            // not become a vertical line?
            if (borderLeft !== borderRight) {
                top = borderTop;
                bottom = borderBottom;
            }
            // not become a horizontal line?
            if (borderTop !== borderBottom) {
                left = borderLeft;
                right = borderRight;
            }
        }

        ctx.beginPath();
        ctx.fillStyle = vm.backgroundColor;
        ctx.strokeStyle = vm.borderColor;
        ctx.lineWidth = borderWidth;

        // Corner points, from bottom-left to bottom-right clockwise
        // | 1 2 |
        // | 0 3 |
        //bottom = bottom + 20;
        var corners = [
            [left, bottom],
            [left, top],
            [right, top],
            [right, bottom]
        ];

        // Find first (starting) corner with fallback to 'bottom'
        var borders = ['bottom', 'left', 'top', 'right'];
        var startCorner = borders.indexOf(borderSkipped, 0);
        if (startCorner === -1) {
            startCorner = 0;
        }

        function cornerAt(index) {
            return corners[(startCorner + index) % 4];
        }

        // Draw rectangle from 'startCorner'
        var corner = cornerAt(0);
        ctx.moveTo(corner[0], corner[1]);

        for (var i = 1; i < 4; i++) {
            corner = cornerAt(i);
            nextCornerId = i+1;
            if(nextCornerId == 4){
                nextCornerId = 0
            }

            nextCorner = cornerAt(nextCornerId);

            width = corners[2][0] - corners[1][0];
            height = corners[0][1] - corners[1][1];
            x = corners[1][0];
            y = corners[1][1];

            var radius = cornerRadius;

            // Fix radius being too large
            if(radius > height/2){
                radius = height/2;
            }if(radius > width/2){
                radius = width/2;
            }

            if(!vm.horizontal){
                ctx.moveTo(x + radius, y);
                ctx.lineTo(x + width - radius, y);
                ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
                ctx.lineTo(x + width, y + height);
                ctx.quadraticCurveTo(x + width, y + height, x + width, y + height);
                ctx.lineTo(x, y + height);
                ctx.quadraticCurveTo(x, y + height, x, y + height);
                ctx.lineTo(x, y + radius);
                ctx.quadraticCurveTo(x, y, x + radius, y);
            }else{
                ctx.moveTo(x + radius, y);
                ctx.lineTo(x + width - radius, y);
                ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
                ctx.lineTo(x + width, y + height - radius);
                ctx.quadraticCurveTo(x + width, y + height, x + width - radius, y + height);
                ctx.lineTo(x, y + height);
                ctx.quadraticCurveTo(x, y + height, x, y + height);
                ctx.lineTo(x, y);
                ctx.quadraticCurveTo(x, y, x, y);
            }
        }

        ctx.fill();
        if (borderWidth) {
            ctx.stroke();
        }
    };


}


function setDataServicioDistrSexo(data, graphGeneralData){
    const tipoServicioFem = data.filter(element => element.sexo === 2)
        .map(({predeterminadaFichaId})=> predeterminadaFichaId);
    const tipoServicioMasc = data.filter(element => element.sexo === 1)
        .map(({predeterminadaFichaId})=> predeterminadaFichaId);

    let datosMasc = getDataGraficoTipoServicio(tipoServicioMasc);
    let datosFem = getDataGraficoTipoServicio(tipoServicioFem);
    let porcentajeMasc= [];
    let porcentajeFem = [];

    for( let i = 0 ; i <graphGeneralData.length; i++){

        if(graphGeneralData[i]> 0){
            let porcValorMasc = ( datosMasc[i] * 100 ) / graphGeneralData[i];
            let porcValorFem = ( datosFem[i] * 100 ) / graphGeneralData[i];
            let porcentajeDistrSexo = [porcValorMasc, porcValorFem];
            porcentajeDistrSexo = roundedPercentage(porcentajeDistrSexo, 100);
            porcentajeMasc.push(porcentajeDistrSexo[0]);
            porcentajeFem.push(porcentajeDistrSexo[1]);
        }else{
            porcentajeMasc.push(0);
            porcentajeFem.push(0);
        }
    }
    generarPorcentajeTipoServicioSexoDOM(porcentajeFem, 0); // 0 - mujer 1-hombre
    generarPorcentajeTipoServicioSexoDOM(porcentajeMasc, 1); // 0 - mujer 1-hombre
}


function graficoServiciosUsados(dataServicio){

    let dataLength = dataServicio.reduce( (a,b) => a+b);
    let porcentajes = dataServicio.map( e => Math.round((e/dataLength)*100));


    //GraficoCanalesUsados
    var ctx = document.getElementById("GraficoServiciosUsados").getContext("2d");
    var data = {
        labels: serviciosTipos,
        datasets: [
            {
                data: dataServicio,
                backgroundColor: "#e04c51"
            }
        ]
    };

    const myBarChartHoriz = new Chart(ctx, {
        type: 'horizontalBar',
        data: data,
        options: {
            barValueSpacing: 5,
            barDatasetSpacing: 5,

            scales: {
                xAxes: [{
                    ticks: {
                        display: false
                    },
                    gridLines: {
                        display:false,
                        drawBorder: false
                    },
                    categoryPercentage: 1.0,
                    barPercentage: 1.0
                }],
                yAxes: [{
                    barThickness : 15,
                    ticks:{
                        callback: function(value,index){return porcentajes[index]+ " %"},
                        fontSize: 13,
                        fontColor: "#e04c51",
                        fontFamily: "GothamHTF-Book"
                    },
                    gridLines: {
                        display:false,
                        drawBorder: false
                    },
                    categoryPercentage: 1.0,
                    barPercentage: 1.0
                }]
            },
            hover: {
                mode: 'nearest',
                intersect: true
            },
            tooltips: {
                mode: 'nearest'
            },
            legend:{
                position: 'bottom',
                display: false
            }

        }
    });
    Chart.elements.Rectangle.prototype.draw = function() {

        var ctx = this._chart.ctx;
        var vm = this._view;
        var left, right, top, bottom, signX, signY, borderSkipped, radius;
        var borderWidth = vm.borderWidth;
        // Set Radius Here
        // If radius is large enough to cause drawing errors a max radius is imposed
        var cornerRadius = 20;

        if (!vm.horizontal) {
            // bar
            left = vm.x - vm.width / 2;
            right = vm.x + vm.width / 2;
            top = vm.y;
            bottom = vm.base;
            signX = 1;
            signY = bottom > top? 1: -1;
            borderSkipped = vm.borderSkipped || 'bottom';
        } else {
            // horizontal bar
            left = vm.base;
            right = vm.x;
            top = vm.y - vm.height / 2;
            bottom = vm.y + vm.height / 2;
            signX = right > left? 1: -1;
            signY = 1;
            borderSkipped = vm.borderSkipped || 'left';
        }

        // Canvas doesn't allow us to stroke inside the width so we can
        // adjust the sizes to fit if we're setting a stroke on the line
        if (borderWidth) {
            // borderWidth shold be less than bar width and bar height.
            var barSize = Math.min(Math.abs(left - right), Math.abs(top - bottom));
            borderWidth = borderWidth > barSize? barSize: borderWidth;
            var halfStroke = borderWidth / 2;
            // Adjust borderWidth when bar top position is near vm.base(zero).
            var borderLeft = left + (borderSkipped !== 'left'? halfStroke * signX: 0);
            var borderRight = right + (borderSkipped !== 'right'? -halfStroke * signX: 0);
            var borderTop = top + (borderSkipped !== 'top'? halfStroke * signY: 0);
            var borderBottom = bottom + (borderSkipped !== 'bottom'? -halfStroke * signY: 0);
            // not become a vertical line?
            if (borderLeft !== borderRight) {
                top = borderTop;
                bottom = borderBottom;
            }
            // not become a horizontal line?
            if (borderTop !== borderBottom) {
                left = borderLeft;
                right = borderRight;
            }
        }

        ctx.beginPath();
        ctx.fillStyle = vm.backgroundColor;
        ctx.strokeStyle = vm.borderColor;
        ctx.lineWidth = borderWidth;

        // Corner points, from bottom-left to bottom-right clockwise
        // | 1 2 |
        // | 0 3 |
        //bottom = bottom + 20;
        var corners = [
            [left, bottom],
            [left, top],
            [right, top],
            [right, bottom]
        ];

        // Find first (starting) corner with fallback to 'bottom'
        var borders = ['bottom', 'left', 'top', 'right'];
        var startCorner = borders.indexOf(borderSkipped, 0);
        if (startCorner === -1) {
            startCorner = 0;
        }

        function cornerAt(index) {
            return corners[(startCorner + index) % 4];
        }

        // Draw rectangle from 'startCorner'
        var corner = cornerAt(0);
        ctx.moveTo(corner[0], corner[1]);

        for (var i = 1; i < 4; i++) {
            corner = cornerAt(i);
            nextCornerId = i+1;
            if(nextCornerId == 4){
                nextCornerId = 0
            }

            nextCorner = cornerAt(nextCornerId);

            width = corners[2][0] - corners[1][0];
            height = corners[0][1] - corners[1][1];
            x = corners[1][0];
            y = corners[1][1];

            var radius = cornerRadius;

            // Fix radius being too large
            if(radius > height/2){
                radius = height/2;
            }if(radius > width/2){
                radius = width/2;
            }

            if(!vm.horizontal){
                ctx.moveTo(x + radius, y);
                ctx.lineTo(x + width - radius, y);
                ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
                ctx.lineTo(x + width, y + height);
                ctx.quadraticCurveTo(x + width, y + height, x + width, y + height);
                ctx.lineTo(x, y + height);
                ctx.quadraticCurveTo(x, y + height, x, y + height);
                ctx.lineTo(x, y + radius);
                ctx.quadraticCurveTo(x, y, x + radius, y);
            }else{
                ctx.moveTo(x + radius, y);
                ctx.lineTo(x + width - radius, y);
                ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
                ctx.lineTo(x + width, y + height - radius);
                ctx.quadraticCurveTo(x + width, y + height, x + width - radius, y + height);
                ctx.lineTo(x, y + height);
                ctx.quadraticCurveTo(x, y + height, x, y + height);
                ctx.lineTo(x, y);
                ctx.quadraticCurveTo(x, y, x, y);
            }
        }

        ctx.fill();
        if (borderWidth) {
            ctx.stroke();
        }
    };


}

function graficoDistribucionDepartamento(dataDepartamento){

    //let dataLength = dataDepartamento.reduce( (a,b) => a+b);
    let qtyData = dataDepartamento.map( e => e.qty);
    let labelsData = dataDepartamento.map( e => e.dpto);

    //GraficoCanalesUsados
    var ctx = document.getElementById("GraficoDistribucionDepartamento").getContext("2d");
    var data = {
        labels: labelsData,
        datasets: [
            {
                data: qtyData ,
                backgroundColor: "#a8fa00"
            }
        ]
    };

    const myBarChartHoriz = new Chart(ctx, {
        type: 'horizontalBar',
        data: data,
        options: {
            barValueSpacing: 20,
            scales: {
                xAxes: [{
                    ticks: {
                        display: false,
                        drawBorder: false
                    },
                    gridLines: {
                        display:false,
                        drawBorder: false
                    },
                }],
                yAxes: [{
                    barThickness : 15,
                    ticks:{
                        fontSize: 13,
                        fontColor: "#c2ccac",
                        fontFamily: "GothamHTF-Book"
                    },
                    gridLines: {
                        display:false,
                        drawBorder: false
                    },
                }]
            },
            hover: {
                mode: 'nearest',
                intersect: true
            },
            tooltips: {
                mode: 'nearest'
            },
            legend:{
                position: 'bottom',
                display: false
            }

        }
    });
    Chart.elements.Rectangle.prototype.draw = function() {

        var ctx = this._chart.ctx;
        var vm = this._view;
        var left, right, top, bottom, signX, signY, borderSkipped, radius;
        var borderWidth = vm.borderWidth;
        // Set Radius Here
        // If radius is large enough to cause drawing errors a max radius is imposed
        var cornerRadius = 20;

        if (!vm.horizontal) {
            // bar
            left = vm.x - vm.width / 2;
            right = vm.x + vm.width / 2;
            top = vm.y;
            bottom = vm.base;
            signX = 1;
            signY = bottom > top? 1: -1;
            borderSkipped = vm.borderSkipped || 'bottom';
        } else {
            // horizontal bar
            left = vm.base;
            right = vm.x;
            top = vm.y - vm.height / 2;
            bottom = vm.y + vm.height / 2;
            signX = right > left? 1: -1;
            signY = 1;
            borderSkipped = vm.borderSkipped || 'left';
        }

        // Canvas doesn't allow us to stroke inside the width so we can
        // adjust the sizes to fit if we're setting a stroke on the line
        if (borderWidth) {
            // borderWidth shold be less than bar width and bar height.
            var barSize = Math.min(Math.abs(left - right), Math.abs(top - bottom));
            borderWidth = borderWidth > barSize? barSize: borderWidth;
            var halfStroke = borderWidth / 2;
            // Adjust borderWidth when bar top position is near vm.base(zero).
            var borderLeft = left + (borderSkipped !== 'left'? halfStroke * signX: 0);
            var borderRight = right + (borderSkipped !== 'right'? -halfStroke * signX: 0);
            var borderTop = top + (borderSkipped !== 'top'? halfStroke * signY: 0);
            var borderBottom = bottom + (borderSkipped !== 'bottom'? -halfStroke * signY: 0);
            // not become a vertical line?
            if (borderLeft !== borderRight) {
                top = borderTop;
                bottom = borderBottom;
            }
            // not become a horizontal line?
            if (borderTop !== borderBottom) {
                left = borderLeft;
                right = borderRight;
            }
        }

        ctx.beginPath();
        ctx.fillStyle = vm.backgroundColor;
        ctx.strokeStyle = vm.borderColor;
        ctx.lineWidth = borderWidth;

        // Corner points, from bottom-left to bottom-right clockwise
        // | 1 2 |
        // | 0 3 |
        //bottom = bottom + 20;
        var corners = [
            [left, bottom],
            [left, top],
            [right, top],
            [right, bottom]
        ];

        // Find first (starting) corner with fallback to 'bottom'
        var borders = ['bottom', 'left', 'top', 'right'];
        var startCorner = borders.indexOf(borderSkipped, 0);
        if (startCorner === -1) {
            startCorner = 0;
        }

        function cornerAt(index) {
            return corners[(startCorner + index) % 4];
        }

        // Draw rectangle from 'startCorner'
        var corner = cornerAt(0);
        ctx.moveTo(corner[0], corner[1]);

        for (var i = 1; i < 4; i++) {
            corner = cornerAt(i);
            nextCornerId = i+1;
            if(nextCornerId == 4){
                nextCornerId = 0
            }

            nextCorner = cornerAt(nextCornerId);

            width = corners[2][0] - corners[1][0];
            height = corners[0][1] - corners[1][1];
            x = corners[1][0];
            y = corners[1][1];

            var radius = cornerRadius;

            // Fix radius being too large
            if(radius > height/2){
                radius = height/2;
            }if(radius > width/2){
                radius = width/2;
            }

            if(!vm.horizontal){
                ctx.moveTo(x + radius, y);
                ctx.lineTo(x + width - radius, y);
                ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
                ctx.lineTo(x + width, y + height);
                ctx.quadraticCurveTo(x + width, y + height, x + width, y + height);
                ctx.lineTo(x, y + height);
                ctx.quadraticCurveTo(x, y + height, x, y + height);
                ctx.lineTo(x, y + radius);
                ctx.quadraticCurveTo(x, y, x + radius, y);
            }else{
                ctx.moveTo(x + radius, y);
                ctx.lineTo(x + width - radius, y);
                ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
                ctx.lineTo(x + width, y + height - radius);
                ctx.quadraticCurveTo(x + width, y + height, x + width - radius, y + height);
                ctx.lineTo(x, y + height);
                ctx.quadraticCurveTo(x, y + height, x, y + height);
                ctx.lineTo(x, y);
                ctx.quadraticCurveTo(x, y, x, y);
            }
        }

        ctx.fill();
        if (borderWidth) {
            ctx.stroke();
        }
    };


}


function generarNombreCanalesDOM(){


    const dvCanal = $('.nombre-canal');

    canalesVenta.map( function(element) {
        let nombreCanal  = htmlStringToElement(`<p>${element}</p>`)  ;
        dvCanal.append(nombreCanal);

        }
    );
}

function generarNombreServiciosDOM(){


    const dvServicio = $('.nombre-servicio');

    serviciosTipos.map( function(element) {
        let nombreServicio  = htmlStringToElement(`<p>${element}</p>`)  ;
        dvServicio.append(nombreServicio);

        }
    );
}




function generarPorcentajeSexoCanalDOM(arr, index){

    const dvPorcCanalxSexo =   $('.canal-porc-sexo div div')[index];

    arr.map( function(element) {
            let porcCanal  = htmlStringToElement(`<p class="text-left">${element} %</p>`)  ;
           dvPorcCanalxSexo.append(porcCanal);

        }
    );

}



function generarPorcentajeTipoServicioSexoDOM(arr, index){

    const dvPorcServicioxSexo =   $('.tipo-servicio-porc div div')[index];
    console.log(dvPorcServicioxSexo);
    arr.map( function(element) {
            let porcServicio  = htmlStringToElement(`<p class="text-left">${element} %</p>`)  ;
        dvPorcServicioxSexo.append(porcServicio);

        }
    );

}

function generarPorcentajeCondFisica(arr, sexo){
    const porcCondFisic =  getPorcentaje(arr);

    $(`.dv-cond-fisica .${sexo} .cond-fisica-porc`).each(
                 function(index){
                      $(this).html(`${porcCondFisic[index]}%`)
                 });
}

function getPorcentaje(arr){

 const total = arr.reduce( (a,b) => a+b);

 const porcArr = arr.map( e =>  (e/total)*100);

 return roundedPercentage(porcArr,100);

}


function getDataServicioPorTemporada( arr, año){

    const values =[];

   values.push(arr.filter(e => e === `01/${año}`).length);
   values.push(arr.filter(e => e === `02/${año}`).length);
   values.push(arr.filter(e => e === `03/${año}`).length);
   values.push(arr.filter(e => e === `04/${año}`).length);
   values.push(arr.filter(e => e === `05/${año}`).length);
   values.push(arr.filter(e => e === `06/${año}`).length);
   values.push(arr.filter(e => e === `07/${año}`).length);
   values.push(arr.filter(e => e === `08/${año}`).length);
   values.push(arr.filter(e => e === `09/${año}`).length);
   values.push(arr.filter(e => e === `10/${año}`).length);
   values.push(arr.filter(e => e === `11/${año}`).length);
   values.push(arr.filter(e => e === `12/${año}`).length);

   return values;
}

function graficoBarraVentaServiciosTemporada(dataFem,dataMasc){
    var ctx = document.getElementById("GraficoVentasServicioTemporada").getContext("2d");

    var data = {
        labels: ["Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Set", "Oct", "Nov", "Dic"],
        datasets: [
            {
                label: "Hombres",
                backgroundColor: "#00b5f7",
                data: dataMasc
            }, {
                label: "Mujeres",
                backgroundColor: "#FF00EB",
                data: dataFem
            }
        ]
    };

    const myBarChart = new Chart(ctx, {
        type: 'bar',
        data: data,
        options: {
            barValueSpacing: 20,
            scales: {
                yAxes: [{
                      display: false
                }],
                xAxes: [{
                   gridLines: {
                       display:false,
                       drawBorder: false
                   }
                }]
            },
            hover:{
                mode: 'nearest',
                intersect: true
            },
            legend: {
                display: true,
                position: "bottom",
                align: "middle",
                labels: {
                    fontColor: 'white',
                    usePointStyle: true,
                }
            },
            responsive: false

        }
    });


    Chart.elements.Rectangle.prototype.draw = function() {

        var ctx = this._chart.ctx;
        var vm = this._view;
        var left, right, top, bottom, signX, signY, borderSkipped, radius;
        var borderWidth = vm.borderWidth;
        // Set Radius Here
        // If radius is large enough to cause drawing errors a max radius is imposed
        var cornerRadius = 20;

        if (!vm.horizontal) {
            // bar
            left = vm.x - vm.width / 2;
            right = vm.x + vm.width / 2;
            top = vm.y;
            bottom = vm.base;
            signX = 1;
            signY = bottom > top? 1: -1;
            borderSkipped = vm.borderSkipped || 'bottom';
        } else {
            // horizontal bar
            left = vm.base;
            right = vm.x;
            top = vm.y - vm.height / 2;
            bottom = vm.y + vm.height / 2;
            signX = right > left? 1: -1;
            signY = 1;
            borderSkipped = vm.borderSkipped || 'left';
        }

        // Canvas doesn't allow us to stroke inside the width so we can
        // adjust the sizes to fit if we're setting a stroke on the line
        if (borderWidth) {
            // borderWidth shold be less than bar width and bar height.
            var barSize = Math.min(Math.abs(left - right), Math.abs(top - bottom));
            borderWidth = borderWidth > barSize? barSize: borderWidth;
            var halfStroke = borderWidth / 2;
            // Adjust borderWidth when bar top position is near vm.base(zero).
            var borderLeft = left + (borderSkipped !== 'left'? halfStroke * signX: 0);
            var borderRight = right + (borderSkipped !== 'right'? -halfStroke * signX: 0);
            var borderTop = top + (borderSkipped !== 'top'? halfStroke * signY: 0);
            var borderBottom = bottom + (borderSkipped !== 'bottom'? -halfStroke * signY: 0);
            // not become a vertical line?
            if (borderLeft !== borderRight) {
                top = borderTop;
                bottom = borderBottom;
            }
            // not become a horizontal line?
            if (borderTop !== borderBottom) {
                left = borderLeft;
                right = borderRight;
            }
        }

        ctx.beginPath();
        ctx.fillStyle = vm.backgroundColor;
        ctx.strokeStyle = vm.borderColor;
        ctx.lineWidth = borderWidth;

        // Corner points, from bottom-left to bottom-right clockwise
        // | 1 2 |
        // | 0 3 |
        //bottom = bottom + 20;
        var corners = [
            [left, bottom],
            [left, top],
            [right, top],
            [right, bottom]
        ];

        // Find first (starting) corner with fallback to 'bottom'
        var borders = ['bottom', 'left', 'top', 'right'];
        var startCorner = borders.indexOf(borderSkipped, 0);
        if (startCorner === -1) {
            startCorner = 0;
        }

        function cornerAt(index) {
            return corners[(startCorner + index) % 4];
        }

        // Draw rectangle from 'startCorner'
        var corner = cornerAt(0);
        ctx.moveTo(corner[0], corner[1]);

        for (var i = 1; i < 4; i++) {
            corner = cornerAt(i);
            nextCornerId = i+1;
            if(nextCornerId == 4){
                nextCornerId = 0
            }

            nextCorner = cornerAt(nextCornerId);

            width = corners[2][0] - corners[1][0];
            height = corners[0][1] - corners[1][1];
            x = corners[1][0];
            y = corners[1][1];

            var radius = cornerRadius;

            // Fix radius being too large
            if(radius > height/2){
                radius = height/2;
            }if(radius > width/2){
                radius = width/2;
            }

            if(!vm.horizontal){
                ctx.moveTo(x + radius, y);
                ctx.lineTo(x + width - radius, y);
                ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
                ctx.lineTo(x + width, y + height);
                ctx.quadraticCurveTo(x + width, y + height, x + width, y + height);
                ctx.lineTo(x, y + height);
                ctx.quadraticCurveTo(x, y + height, x, y + height);
                ctx.lineTo(x, y + radius);
                ctx.quadraticCurveTo(x, y, x + radius, y);
            }else{
                ctx.moveTo(x + radius, y);
                ctx.lineTo(x + width - radius, y);
                ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
                ctx.lineTo(x + width, y + height - radius);
                ctx.quadraticCurveTo(x + width, y + height, x + width - radius, y + height);
                ctx.lineTo(x, y + height);
                ctx.quadraticCurveTo(x, y + height, x, y + height);
                ctx.lineTo(x, y);
                ctx.quadraticCurveTo(x, y, x, y);
            }
        }

        ctx.fill();
        if (borderWidth) {
            ctx.stroke();
        }
    };

}

function generarSelectYearFilter(arr){

    $.each(arr, function(val, text) {
        $('#graphYearFilter').append( $('<option></option>').val(text).html(text) )
    });
}

function roundedPercentage( l , target){
     let off = target - _.reduce(l, function(acc, x) { return acc + Math.round(x) }, 0);
     return _.chain(l).
     sortBy(function(x) { return Math.round(x) - x }).
     map(function(x, i) { return Math.round(x) + (off > i) - (i >= (l.length + off)) }).
      value();
    }

function generarPorcentajesDepartamentoDOM(dataGrafico){

  let dataValores= dataGrafico.map( e => e.qty).sort( (a,b) => b-a);
  const clientesLength =  dataValores.reduce( (a,b) => a+b );
  let porcentajes = dataValores.map( e => ((e/clientesLength)*100).toFixed(2));
//  porcentajes = roundedPercentage(porcentajes , 100);
  const dvDepartamentosPorc = $('.distr-dpto-porcentajes');
   const porcentajesDOM =  porcentajes.map( function(element) {
           let porcDepto  = htmlStringToElement(`<p>${element}%</p>`)  ;
       dvDepartamentosPorc.append(porcDepto);

       }
   );

}

