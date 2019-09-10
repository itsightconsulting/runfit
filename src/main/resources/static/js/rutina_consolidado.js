
const bgBarMainGraph = ["#ed8989c7","#4fd46bc4","#87ceebbd","#519da4a6"];
const bgMantaIntensidad = ["gold", "gray", "skyblue", "gray"];
let $chartTemporada = {};
let $chartTemporadaKM = {};
let $chartEsfuerzoSemanal = {};
let $chartAvanceSemanal = {};
let periodizacionRutina;
let rutinaId;

let $chartMiniPorc ={};

$(function() {
    init();
})

function init(){

   const favRutinaId = Number(getCookie("GLL_FAV_RUTINA"));
   obtenerRutinaConsolidado(favRutinaId);
}


function obtenerRutinaConsolidado(rutinaId) {
    const body = new Object();
    body.rutinaId = rutinaId;
    if (document.getElementById('InpClienteId')) {
        body.clienteId = document.getElementById('InpClienteId').value;
    }

    let hrefFinal = "";

    if (body.clienteId) {//Ruta para entrenadores from red fitness listado

        hrefFinal = _ctx + "cliente/rutina/consolidado/obtener/by";
    } else {//Ruta personal de clientes
        hrefFinal = _ctx + "cliente/rutina/consolidado/obtener";
    }

    $.ajax({
        type: "GET",
        data: body,
        url: hrefFinal,
        success: function (data) {

            cargarTablaVelocidad(data);
            cargarTablaCadencia(data);
            cargarTablaLongitudPaso(data);
            cargarTablaTCS(data);
            graficoTemporadaIntensidad(data.dtGrafico);

            graficoTemporadaKilometraje(data.dtGrafico);
            periodizacionRutina = getSemanasEtapas(data);
            const etapasPorc = calcularPorcentajesEtapas(periodizacionRutina);


            graficoDistribucionEtapa(etapasPorc);
            cargarInfoPeriodizacion(periodizacionRutina);
            cargarInfoKmEtapas(data);
            cargarProyeccionCompetencia(data);

             rutinaId = data.rutina.id;
            const semTr = Number(data.general.distancia == 10 ? 1 : data.general.distancia == 21 ? 2 : 3);
            const totalSemanas = Number(data.rutina.totalSemanas);
            const semanaIx = totalSemanas - semTr;

            obtenerMetricaIntensidad(rutinaId, semanaIx);

        }
        , error: (xhr) => {
        }, complete: () => {
            obtenerMetricasAvanceSemanal(rutinaId);
        }
    });
}

function obtenerMetricasAvanceSemanal(rutinaId) {

    const body = new Object();
    body.rutinaId = rutinaId;
    if (document.getElementById('InpClienteId')) {
        body.clienteId = document.getElementById('InpClienteId').value;
    }


    $.ajax({
            type: "GET",
            data: body,
            url: _ctx + "cliente/rutina/consolidado/metricas-semanal/obtener",
            success: function (data) {

                if (data) {
                    const avanceSemanal = data.avanceSemanal.split(',').map(e => Number(e));
                    const esfuerzoSemanal = data.esfuerzoSemanal.split(',').map(e => Number(e));

                    graficoTemporadaCumplimientoAvance(avanceSemanal);
                    generarMetricaTitulo(avanceSemanal, 'dvCumplimiento');
                    generarMetricaTitulo(esfuerzoSemanal, 'dvEsfuerzo');

                    graficoTemporadaEsfuerzo(esfuerzoSemanal);

                    let inc = 0;
                    let indexAs;

                    periodizacionRutina.forEach(function (element, index) {

                       if(index + 1 < periodizacionRutina.length) {
                          console.log(index);
                           let sumAvances = 0,
                               sumEsfuerzos = 0;
                           inc += element;
                           for (let i = inc - element; i < inc; i++) {
                               //console.log(element, "x", index, i, avanceSemanal[i]);
                               sumAvances += avanceSemanal[i];
                               sumEsfuerzos += esfuerzoSemanal[i];
                           }
                           let promedioAvances = Math.round(sumAvances / element);
                           let promedioEsfuerzos = Math.round(sumEsfuerzos / element);
                           GraficoCumplimientoPeriodo(promedioAvances,index);
                           GraficoEsfuerzoPeriodo(promedioEsfuerzos,index);

                       }
                    })

                }

            }
            , error: (xhr) => {
            }, complete: () => {

            }
        });
}



function GraficoCumplimientoPeriodo(data, periodoIx){

    let periodoArr = ['General' ,'Especifico', 'Competitivo'];
    let colores = [ 'c9ea83','#9bbd4f','#a8fa00']

    let contenedor = 'GraficoCumplimientoPer'+periodoArr[periodoIx];
    console.log(contenedor);
    let canvas = document.getElementById(contenedor);
    let ctx = document.getElementById(contenedor).getContext('2d');

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

                    ctx.font = "30px GothamHTF-Book";
                    ctx.fillStyle = colores[periodoIx];
                    ctx.textAlign = "center";
                   ctx.fillText(data + '%', canvas.width/2 + 10, canvas.height/2 + 10);
                  //  ctx.fillText("100%",centerX -10,centerY - 25, 20,50);

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
            labels: ["General","Otros"],
            datasets: [{
                data: [data , 100 - data],
                backgroundColor: [colores[periodoIx], '#B0B0B0'],
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
                    fontFamily: 'GothamHTF-Book'
                }
            },
            title: {
                display: false
            },
            tooltips: {
                enabled: false
            }

        }
    };

    new Chart(ctx, config);

}

function GraficoEsfuerzoPeriodo(data, periodoIx){


    let periodoArr = ['General' ,'Especifico', 'Competitivo'];
    let colores = [ '#ea7500','#C33846','#ff0c0c']

    let contenedor = 'GraficoEsfuerzoPer'+periodoArr[periodoIx];
    console.log(contenedor);
    let canvas = document.getElementById(contenedor);
    let ctx = document.getElementById(contenedor).getContext('2d');

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

                    ctx.font = "30px GothamHTF-Book";
                    ctx.fillStyle = colores[periodoIx];
                    ctx.textAlign = "center";
                    ctx.fillText(data, canvas.width/2 + 10, canvas.height/2 + 10);
                    //  ctx.fillText("100%",centerX -10,centerY - 25, 20,50);

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
            labels: ["General","Otros"],
            datasets: [{
                data: [data , 100 - data],
                backgroundColor: [colores[periodoIx], '#c2ccac'],
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
                    fontFamily: 'GothamHTF-Book'
                }
            },
            title: {
                display: false
            },
            tooltips: {
                enabled: false
            }

        }
    };

    new Chart(ctx, config);

}


function generarMetricaTitulo( data , nombreContenedor){

    const suma = data.reduce( (a,b) => a+b);
    const promedio = Math.round(suma / data.length );
    const titulo = document.querySelector('#'+nombreContenedor+' h3.metric-title span');

    console.log(titulo);

    titulo.textContent = promedio + (nombreContenedor === 'dvCumplimiento' ? '%' : '');

}


function obtenerMetricaIntensidad(rutinaId, semanaIx){

    $.ajax({
        type: "GET",
        url: _ctx + "cliente/rutina/consolidado/intensidad",
        dataType: 'json',
        data:{
            rutinaId: rutinaId,
            semanaIx: semanaIx
        },
        success: function (data) {

         const metricas = JSON.parse(data.metricas);
         const Z4 = metricas.find( function(element){
                                    return element.nombre === "Z4"
            });



          $('#intensidadValue').html(Z4.min + "-" + Z4.max);

        }
        , error: (xhr) => {
        }, complete: () => {

        }
    });
}


function cargarTablaVelocidad(data){

  const mVC = JSON.parse(data.matrizMejoraVelocidades);

    const porcMejora = parseNumberToDecimal(mVC.map((v,i)=>{
       return Number(parseNumberToDecimal((((((timeStringtoSeconds(mVC[i].ind[0]))*Number(data.general.distancia))/((timeStringtoSeconds(mVC[i].ind[(mVC[i].ind.length)-1]))*data.general.distancia)))-1)*100,1))
    }).reduce((a,b)=>a+b, 1)/mVC.length,1);

    console.log(porcMejora);

    const tabla =  htmlStringToElement(`<div style="margin-right: 10px;">
                    ${mVC[0].ind.map((v,i)=>{
           return (i+1)%4 == 0 ?`<div class="col col-md-1 col-sm-1 sems-o-mes-det-veloc"><div class="container-fluid text-align-center margin-o-bottom-10-w-bb"> <div class="padding-bottom-5 hd-column">${'MES '+(i+1)/4}</div> </div> </div> <div class="col col-md-11 col-sm-11"> <div class="container-fluid text-align-center margin-o-bottom-10-w-bb">
                                ${mVC.map((v,ii)=>{
            return (i+1)%4 == 0 ?`${ii==0?'<div class="col-md-6 col-sm-6 padding-bottom-5">':''}<div class="col col-md-3 col-sm-3 dt-ix${ii+1}">${v.ind[i]}</div>${ii==3?'</div><div class="col-md-6 col-sm-6 padding-bottom-5">':''}${ii==7?'</div>':''}`:'';
        }).join('')}</div></div>`:`<i class="hidden col-md-11">${mVC.map((v,ii)=>{ return `<i class="col-md-3 dt-ix${ii+1}">${v.ind[i]}</i>`}).join('')}</i>`;
    }).join('')}</div>`);

    const dvPorcMejora = htmlStringToElement(`<p class="porc-mejora-vel"> MEJORAS DE VELOCIDAD PROYECTADA: <strong>${porcMejora}%</strong> </p>`);

    console.log(dvPorcMejora);


    $('#mejora-velocidad').append(tabla);

    $('.dv-mejora-vel').append(dvPorcMejora);


}

function cargarTablaCadencia(data){

    const mMC = JSON.parse(data.matrizMejoraCadencia);
    const dataRutinaGeneral = data.rutina;
    const numSemanas =  dataRutinaGeneral.totalSemanas;

    let infoCadencia;
    const porcMejora = parseNumberToDecimal((((( Number(mMC[mMC.length - 1])) / Number(mMC[0])-1))*100), 1) + " %";


    if(numSemanas >=12){
        infoCadencia = htmlStringToElement('<div class="container-fluid">'+mMC.map((v,i)=>{
            return `<div class="row"><div class="col col-md-6 col-sm-6 periodo-cadencia">MES ${(i+1)}</div><div class="col col-md-6 col-sm-6 dato-cadencia">${v}</div></div>`}).join('') + `<div class="porcentaje-mejora"><p class="padding-10 text-align-right"> MEJORA TOTAL <strong>${porcMejora}</strong></p></div></div>`);
    }else{
        infoCadencia = htmlStringToElement('<div class="container-fluid">'+mMC.map((v,i)=>{
            return (i+1)%4 == 0 ? `<div class="row"><div class="col col-md-6 col-sm-6  periodo-cadencia">SEMANA ${(i+1)}</div><div class="col col-md-6 col-sm-6 dato-cadencia">${v}</div></div>`:''}).join('') + `<div class="porcentaje-mejora"><p class="padding-10 text-align-right"> MEJORA TOTAL <strong>${porcMejora}</strong></p></div></div>`);
    }

    $('#mejora-cadencia').append(infoCadencia);
}

function cargarTablaTCS(data){

    const mTCS = JSON.parse(data.matrizMejoraTcs);
    const dataRutinaGeneral = data.rutina;
    const numSemanas =  dataRutinaGeneral.totalSemanas;

    let infoTCS;
    const porcMejora = parseNumberToDecimal((((( Number(mTCS[mTCS.length - 1])) / Number(mTCS[0])-1))*100), 1) + " %";


    if(numSemanas >=12){
        infoTCS = htmlStringToElement('<div class="container-fluid">'+mTCS.map((v,i)=>{
            return `<div class="row"><div class="col col-md-6 col-sm-6 periodo-cadencia">MES ${(i+1)}</div><div class="col col-md-6 col-sm-6 dato-cadencia">${v}</div></div>`}).join('') + `<div class="porcentaje-mejora"><p class="padding-10 text-align-right"> MEJORA TOTAL <strong>${porcMejora}</strong></p></div></div>`);
    }else{
        infoTCS = htmlStringToElement('<div class="container-fluid">'+mTCS.map((v,i)=>{
            return (i+1)%4 == 0 ? `<div class="row"><div class="col col-md-6 col-sm-6  periodo-cadencia">SEMANA ${(i+1)}</div><div class="col col-md-6 col-sm-6 dato-cadencia">${v}</div></div>`:''}).join('') + `<div class="porcentaje-mejora"><p class="padding-10 text-align-right"> MEJORA TOTAL <strong>${porcMejora}</strong></p></div></div>`);
    }

    $('#mejora-tcs').append(infoTCS);
}



function cargarTablaLongitudPaso(data){

    const mLP = JSON.parse(data.matrizMejoraLonPaso);
    const dataRutinaGeneral = data.rutina;
    const numSemanas =  dataRutinaGeneral.totalSemanas;

    let infoLongitudPaso;
    const porcMejora = parseNumberToDecimal((((( Number(mLP[mLP.length - 1])) / Number(mLP[0])-1))*100), 1) + " %";


    if(numSemanas >=12){
        infoLongitudPaso = htmlStringToElement('<div class="container-fluid">'+mLP.map((v,i)=>{
            return `<div class="row"><div class="col col-md-6 col-sm-6 periodo-cadencia">MES ${(i+1)}</div><div class="col col-md-6 col-sm-6 dato-cadencia">${v}</div></div>`}).join('') + `<div class="porcentaje-mejora"><p class="padding-10 text-align-right"> MEJORA TOTAL <strong>${porcMejora}</strong></p></div></div>`);
    }else{
        infoLongitudPaso = htmlStringToElement('<div class="container-fluid">'+mLP.map((v,i)=>{
            return (i+1)%4 == 0 ? `<div class="row"><div class="col col-md-6 col-sm-6  periodo-cadencia">SEMANA ${(i+1)}</div><div class="col col-md-6 col-sm-6 dato-cadencia">${v}</div></div>`:''}).join('') + `<div class="porcentaje-mejora"><p class="padding-10 text-align-right"> MEJORA TOTAL <strong>${porcMejora}</strong></p></div></div>`);
    }

    $('#mejora-longitud').append(infoLongitudPaso);
}

function timeStringtoSeconds(time){

    const a = time.split(':');
    const seconds = (+a[0]) * 60 * 60 + (+a[1]) * 60 + (+a[2]);

    return seconds;
}


function graficoTemporadaIntensidad(data){
    data = data.map(v=>{return {kms: v.kms, color: v.color, perc: v.percInts, bullet: v.bullet, avance: v.avance}});

    const avances =  data.filter(v=>{//Provisional
        return (v.avance != undefined)
    }).map(({avance})=>avance);
    avances.push(0);//Por conveniencia(estética) */
 //   document.querySelector('#ContainerVarVolumen').classList.remove('hidden');
  //  document.querySelector('#InicialMacro').classList.remove('hidden');
    let draw = Chart.controllers.line.prototype.draw;
    Chart.controllers.LineNoOffset = Chart.controllers.line.extend({
        updateElement: function(point, index, reset) {
            Chart.controllers.line.prototype.updateElement.call(this, point, index, reset);
            const meta = this.getMeta();
            const xScale = this.getScaleForId(meta.xAxisID);
            point._model.x = xScale.getPixelForValue(undefined, index-0.5);
        }, draw: function() {
            draw.apply(this, arguments);
            let ctx = this.chart.chart.ctx;
            let _stroke = ctx.stroke;
            ctx.stroke = function() {
                ctx.save();
                ctx.shadowColor = '#23314591';
                ctx.shadowBlur = 10;
                ctx.shadowOffsetX = 0;
                ctx.shadowOffsetY = 10;
                _stroke.apply(this, arguments)
                ctx.restore();
            }
        }
    });
    if($chartTemporada.ctx != undefined){
        $chartTemporada.destroy();
    }else{
        Chart.pluginService.register({
            /*afterUpdate: function(chart) {
                $idsComp = [];
                if(chart.canvas.id === "GraficoTemporada")
                    data.forEach((v,i)=>{
                        if(v.bullet != undefined){
                            let img = new Image();
                            img.src = v.bullet;
                            Object.values(chart.config.data.datasets[1]._meta)[0].data[i]._model.pointStyle = img;
                            $idsComp.push(i);
                        }
                    })
            },*/
            afterDatasetsDraw: function(chart) {
                if(chart.canvas.id === "GraficoTemporada"){
                    let ctx = chart.ctx;
                    chart.data.datasets.forEach(function (dataset, i) {
                        if (i == 1) {
                            let meta = chart.getDatasetMeta(i);
                            if (!meta.hidden) {
                                meta.data.forEach(function (element, index) {
                                    let fontSize = 11;
                                    let fontStyle = 'normal';
                                    ctx.font = Chart.helpers.fontString(fontSize, fontStyle);
                                    let dataString = "";
                                    // Just naively convert to string for now
                                    $idsComp.filter(v => {
                                        return v == index
                                    }).length == 0 ? dataString = dataset.data[index].toString() + "%" : "";
                                    // Make sure alignment settings are correct
                                    ctx.textAlign = 'center';
                                    ctx.textBaseline = 'middle';
                                    ctx.fillStyle = '#f3eeee94';// Draw the specified text color, with the specified font for data in line

                                    let padding = 5;
                                    let position = element.tooltipPosition();
                                //    ctx.fillText(dataString, position.x, position.y - (fontSize / 2) - padding);

                                });
                            }
                        }
                    });
                }else{
                    let ctx = chart.ctx;
                    chart.data.datasets.forEach(function (dataset, i) {
                        if (i == 0) {
                            let meta = chart.getDatasetMeta(i);
                            if (!meta.hidden) {
                                meta.data.forEach(function (element, index) {
                                    // Draw the text in black, with the specified font
                                    ctx.fillStyle = 'rgb(0, 0, 0)';

                                    let fontSize = 14;
                                    let fontStyle = 'normal';
                                    ctx.font = Chart.helpers.fontString(fontSize, fontStyle);
                                    let dataString;
                                    // Just naively convert to string for now
                                    dataString = dataset.data[index].toString() + "%";
                                    // Make sure alignment settings are correct
                                    ctx.textAlign = 'center';
                                    ctx.textBaseline = 'middle';
                                    ctx.fillStyle = 'white';

                                    let padding = 0;
                                    let position = element.tooltipPosition();
                                //    ctx.fillText(dataString, position.x, position.y - (fontSize / 2) - padding);
                                });
                            }
                        }
                    });
                }
            },
            beforeDraw: function(chartInstance) {
                // check and see if the plugin is active (its active if the option exists)
                if (chartInstance.config.options.tooltips.onlyShowForDatasetIndex) {
                    // get the plugin configuration
                    var tooltipsToDisplay = chartInstance.config.options.tooltips.onlyShowForDatasetIndex;

                    // get the active tooltip (if there is one)
                    var active = chartInstance.tooltip._active || [];

                    // only manipulate the tooltip if its just about to be drawn
                    if (active.length > 0) {
                        // first check if the tooltip relates to a dataset index we don't want to show
                        if (tooltipsToDisplay.indexOf(active[0]._datasetIndex) === -1) {
                            // we don't want to show this tooltip so set it's opacity back to 0
                            // which causes the tooltip draw method to do nothing
                            chartInstance.tooltip._model.opacity = 0;
                        }
                    }
                }
            }
        });
    }

    Chart.Legend.prototype.afterFit = function() {
       this.height = this.height + 12;
      // this.height = this.height + 35;
    };

    let ctx = document.getElementById('GraficoTemporada').getContext('2d');
    /*var gradientFill = ctx.createLinearGradient((Math.round(data.length*0.75)*100), 0, 100, 0);
    gradientFill.addColorStop(0, "#4e4a6d");//Last
    gradientFill.addColorStop(0.13, "gray");
    gradientFill.addColorStop(0.46, "#4e4a6d");
    gradientFill.addColorStop(1, "gray");//First*/

    var gradientFill = ctx.createLinearGradient(0, 0, 0, (Math.round(data.length*0.75)*100));
    gradientFill.addColorStop(0, bgMantaIntensidad[0]);//Last
    gradientFill.addColorStop(0.13, bgMantaIntensidad[1]);
    gradientFill.addColorStop(0.46, bgMantaIntensidad[2]);
    gradientFill.addColorStop(0.76, bgMantaIntensidad[3]);//First
    //Temp
    const len = data.length;
    data[len-1].perc = "100";
    data[len-1].kms = 40;
    data[len-2].perc = "90";
    data[len-2].kms = 40;
    data[len-3].perc = "80";
    data[len-3].kms = 40;

    //End temp
    $chartTemporada = new Chart(ctx, {
        type: 'line',
        data: {
            labels: data.map((v, i)=>i+1),
            datasets: [{
                label: 'Intensidad',
                data: data.map(({perc})=>perc),
                yAxisID: 'y-axis-1',
                // Changes this dataset to become a line
                type: 'line',
                borderColor: '#a8fa00',
                backgroundColor: gradientFill,
                pointBorderColor: '#a8fa00',
                pointBackgroundColor: '#a8fa00',
                pointHoverBackgroundColor: 'a8fa00',
                pointHoverBorderColor: '#a8fa00',
                borderWidth: 1,
                fill: true
            }]
        },
        borderWidth: 1,
        options: {
            layout: {
                padding: {
                    top: 40  //set that fits the best
                }
            },
            maintainAspectRatio: false,
            tooltips: {
                yAlign: 'bottom',
                xAlign: 'center',
                onlyShowForDatasetIndex: [0, 1],
                /*callbacks: {
                    title: function(tooltipItem) {
                        const fix = $fechasCompetencia.length - $idsComp.length;
                        let mIx;
                        return $idsComp.filter((v,i) => {
                            if(v == tooltipItem[0].index)
                                return String(mIx = i);//Se convierte en String para evitar problemas cuando el indice sea 0, ya que js considera en el caso de filter el 0 como undef/null
                        }).length == 0 ? this._data.labels[tooltipItem[0].index] : $fechasCompetencia[mIx+fix].nombre;
                    },
                }*/
            },
            scales: {
                yAxes: [{
                    id: 'y-axis-1',
                    type: 'linear',
                    position: 'left',
                    gridLines: {
                        color: "rgba(72,68,118,0.2)",
                        display:false,
                        drawBorder: false
                    },
                    barPercentage: 1.0,
                    categoryPercentage: 1.0,
                    ticks: {
                       // display: false,
                        suggestedMin: 0,
                        suggestedMax: 100,   // minimum will be 0, unless there is a lower value.
                        // OR //
                        beginAtZero: false,   // minimum value will be 0.
                        fontColor: "#f3eeee94",
                        fontSize: 15
                        /*callback: function(label) {
                            return label + "%";
                        }*/
                    }
                }],
                xAxes: [{
                    ticks: {
                        /*userCallback: function(item, index) {
                            if (!(index % 4)) return item;
                        },*/
                        autoSkip: false,
                        fontColor: "#f3eeee94",
                        fontSize: 15,
                        beginAtZero:true,
                        maxRotation: 0,
                        minRotation: 0
                    },
                     barPercentage: 1.0,
                   // categoryPercentage: 1.0,

                }],
            },
            elements:{
                line: {
             //       beginAtZero: true
                }
            },
            legend: {/*
                        labels: {
                            filter: function(legendItem) {
                                if (legendItem.datasetIndex === 0) {
                                    return false;
                                }
                                return true;
                            },
                            fontColor: 'transparent'
                        },*/
                display: false
            },
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

            ctx.moveTo(x + radius, y);
            ctx.lineTo(x + width - radius, y);
            ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
            ctx.lineTo(x + width, y + height);
            ctx.quadraticCurveTo(x + width, y + height, x + width, y + height);
            ctx.lineTo(x, y + height);
            ctx.quadraticCurveTo(x, y + height, x, y + height);
            ctx.lineTo(x, y + radius);
            ctx.quadraticCurveTo(x, y, x + radius, y);

        }

        ctx.fill();
        if (borderWidth) {
            ctx.stroke();
        }
    };
}


function graficoTemporadaKilometraje(data)
{
    data = data.map(v=>{return {kms: v.kms, color: v.color, perc: v.percInts, bullet: v.bullet, avance: v.avance}});

    var data = {
        labels: data.map((v, i)=>i+1),
        datasets: [
            {
                data:  data.map(({kms})=>kms),
                backgroundColor: "skyblue"
            }
        ]
    };


    let ctx = document.getElementById('GraficoTemporadaKM').getContext('2d');

    //End temp

        $chartTemporadaKM = new Chart(ctx, {
        type: 'bar',
        data: data,
        options: {
            responsive:false,
            barValueSpacing: 20,
            layout: {
                padding: {
                    top: 40  //set that fits the best
                }
            },
            scales: {
                xAxes: [{
                    ticks: {
                        display: true,
                        fontColor: "#f3eeee94",
                        fontSize: 15,
                        maxRotation: 0,
                        minRotation: 0
                    },
                    gridLines: {
                        display:false,
                        drawBorder: false
                    },
                }],
                yAxes: [{
                    barThickness : 15,
                    ticks:{
                        fontColor: "#f3eeee94",
                        fontSize: 15,
                        suggestedMin: 0,
                        suggestedMax: 50,
                        },
                    gridLines: {
                        display:false,
                        drawBorder: false
                    }
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
                ctx.lineTo(x + width -  radius, y);
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


function graficoTemporadaCumplimientoAvance(data)
{


    var data = {
        labels: data.map((v, i)=>i+1),
        datasets: [
            {
                data:  data,
                backgroundColor: "#a8fa00"
            }
        ]
    };


    let ctx = document.getElementById('GraficoAvanceSemanal').getContext('2d');

    //End temp

    $chartAvanceSemanal = new Chart(ctx, {
        type: 'bar',
        data: data,
        options: {
            responsive:false,
            barValueSpacing: 20,
            scales: {
                xAxes: [{
                    ticks: {
                        display: true,
                        fontColor: "#f3eeee94",
                        fontSize: 15,
                        maxRotation: 0,
                      minRotation: 0
},
gridLines: {
    display:false,
        drawBorder: false
},
}],
yAxes: [{
    barThickness : 15,
    ticks:{
        fontColor: "#f3eeee94",
        fontSize: 15,
        suggestedMin: 0,
        suggestedMax: 50,
    },
    gridLines: {
        display:false,
        drawBorder: false
    }
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

function graficoTemporadaEsfuerzo(data)
{
    var data = {
        labels: data.map((v, i)=>i+1),
        datasets: [
            {
                data:  data,
                backgroundColor: "#C33846"
            }
        ]
    };


    let ctx = document.getElementById('GraficoEsfuerzoSemanal').getContext('2d');

    //End temp

    $chartAvanceSemanal = new Chart(ctx, {
        type: 'bar',
        data: data,
        options: {
            responsive:false,
            barValueSpacing: 20,
            scales: {
                xAxes: [{
                    ticks: {
                        display: true,
                        fontColor: "#f3eeee94",
                        fontSize: 15,
                        maxRotation: 0,
                        minRotation: 0
                    },
                    gridLines: {
                        display:false,
                        drawBorder: false
                    },
                }],
                yAxes: [{
                    barThickness : 15,
                    ticks:{
                        fontColor: "#f3eeee94",
                        fontSize: 15,
                        suggestedMin: 0,
                        suggestedMax: 50,
                    },
                    gridLines: {
                        display:false,
                        drawBorder: false
                    }
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





function graficoDistribucionEtapa(etapasPorc){

    //  MCGrafico.cesDemoCircularGraphs();

    let canvas = document.getElementById('MiniGraficoDistribucion');
    let ctx = document.getElementById('MiniGraficoDistribucion').getContext('2d');

    if($chartMiniPorc.ctx != undefined){
        $chartMiniPorc.destroy();
    }


    $chartMiniPorc = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ["General","Específica","Competitiva","Tránsito"],
            datasets: [{
                data: etapasPorc,
                backgroundColor: ['#c9ea83', '#9bbd4f', '#a8fa00', '#c2ccac'],
                hoverBackgroundColor:  ["#E6F3FF", "#FAE1CE", "#EDFDE9", "#4A4E3B5E"],
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
            animation: {
                onComplete: function () {
                    var xCenter = (canvas.width / 2);
                    var yCenter = canvas.height / 2;
                    var progressLabel = '%';
                    ctx.font = '25px Helvetica';
                    ctx.fillStyle = 'white';
                    ctx.fillText(progressLabel, xCenter, yCenter);
                }
            }
            /*segmentShowStroke: false*/
            //Boolean - Whether we should show a stroke on each segment
            // set to false to hide the space/line between segments

        }


    });


}


function getSemanasEtapas(data){

    const semGe = Number(data.mejoras.semGe);
    const semEs = Number(data.mejoras.semEs);
    const semPr = Number(data.mejoras.semPr);
    const semTr = data.general.distancia == 10 ? 1 : data.general.distancia == 21 ? 2 : 3;

    const semanasArr = [semGe,semEs,semPr,semTr];

    return semanasArr;
}


function calcularPorcentajesEtapas(semanasArr){

    const totalSemanas = semanasArr.reduce((a, b) => {
        return a + b
    });

    const porcentajes=[];

    semanasArr.forEach(function(el){
        porcentajes.push(((el/totalSemanas)*100).toFixed(2));
    });


    return porcentajes;

}

function cargarInfoKmEtapas(data){
    const allKms = data.dtGrafico.map(({kms})=>kms);

    const sumKms = allKms.reduce((a,b)=>{return a+b});

    const periodizacion = getSemanasEtapas(data);

    const kmsParts = periodizacion.map((v)=>{
        return allKms.splice(0, v);//Cada vez el arreglo va perdiendo elementos y por eso siempre hacemos que se corte desde 0
    });

    const kmsEsp = parseFloat(kmsParts[1].reduce((a, b) => {
        return a + b
    }))
    document.querySelectorAll('#consolidado .etapa-km')
        .forEach((v,i)=>{

            if(periodizacion) {
                const kmsEsp = parseFloat(kmsParts[i].reduce((a, b) => {
                    return a + b
                }))
                v.querySelector('strong.km-value').textContent = kmsEsp.toFixed(1);
                //   v.querySelector('span').textContent = base.periodizacion[i] + " semanas";
                // base.porcentajesKms.push(((kmsEsp * 100) / sumKms).toFixed(2));
            }
        });

    document.querySelector('.total-km .km-value').textContent = sumKms.toFixed(1);

}

function cargarInfoPeriodizacion(periodizacion){

    document.querySelectorAll('#consolidado .etapa-rutina')
        .forEach((v,i)=>{
            v.querySelector('strong.etapa-sem').textContent = periodizacion[i];

        })


}


function cargarProyeccionCompetencia(data){

    const distancia = data.general.disCompetencia ;
    const pasoSubida = data.stats.pasoSubida;
    const pasoBajada = data.stats.pasoBajada;
    const pasoPlano = data.stats.pasoPlano;
    const pasoPromedioSegundos = ((timeStringtoSeconds(pasoSubida) + timeStringtoSeconds(pasoBajada) + timeStringtoSeconds(pasoPlano))/3 );
    const pasoPromedio = new Date(pasoPromedioSegundos * 1000).toISOString().substr(11, 8);
    const cadencia = data.general.cadCompetencia;
    const tcs = data.general.tcsCompetencia;
    const longitudPaso = data.stats.lonPasoCompActual;
    const tiempoProyectado = data.general.tieCompetencia;
    const datosProyeccion =
        `<div class="row"><div class="col col-md-8 col-sm-8 factor-proyeccion"> DISTANCIA </div><div class="col col-md-4 col-sm-4 dato-proyeccion">${distancia}</div></div>
                            <div class="row"><div class="col col-md-8 col-sm-8 factor-proyeccion"> PASO PROMEDIO </div><div class="col col-md-4 col-sm-4 dato-proyeccion">${pasoPromedio}</div></div>
                            <div class="row"><div class="col col-md-8 col-sm-8 factor-proyeccion"> PASO EN SUBIDA </div><div class="col col-md-4 col-sm-4 dato-proyeccion">${pasoSubida}</div></div>
                            <div class="row"><div class="col col-md-8 col-sm-8 factor-proyeccion"> PASO EN BAJADA </div><div class="col col-md-4 col-sm-4 dato-proyeccion">${pasoBajada}</div></div>
                            <div class="row"><div class="col col-md-8 col-sm-8 factor-proyeccion"> PASO EN PLANO </div><div class="col col-md-4 col-sm-4 dato-proyeccion">${pasoPlano}</div></div>
                            <div class="row"><div class="col col-md-8 col-sm-8 factor-proyeccion"> CADENCIA </div><div class="col col-md-4 col-sm-4 dato-proyeccion">${cadencia}</div></div>
                            <div class="row"><div class="col col-md-8 col-sm-8 factor-proyeccion"> T.C.S </div><div class="col col-md-4 col-sm-4 dato-proyeccion">${tcs}</div></div>
                            <div class="row"><div class="col col-md-8 col-sm-8 factor-proyeccion"> LONGITUD DEL PASO  </div><div class="col col-md-4 col-sm-4 dato-proyeccion">${longitudPaso}</div></div>
                            <div class="row"><div class="col col-md-8 col-sm-8 factor-proyeccion"> TIEMPO PROYECTADO</div><div class="col col-md-4 col-sm-4 dato-proyeccion">${tiempoProyectado}</div></div>
                            <div class="row"><div class="col col-md-8 col-sm-8 factor-proyeccion"> INTENSIDAD</div><div id="intensidadValue" class="col col-md-4 col-sm-4 dato-proyeccion"></div></div> `;

    $('#proyeccionCompetencia').append(datosProyeccion) ;

}

