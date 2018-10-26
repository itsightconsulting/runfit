const tabPrincipal = document.querySelector('#myTabPrincipal');
const semanas = [];
var semanaEncontrada = {};
let $rutina = {};
const monthNames = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
];

$(function () {
    Date.prototype.addDays = function(days) {
        var dat = new Date(this.valueOf())
        dat.setDate(dat.getDate() + days);
        return dat;
    }

    const d = new Date();
    $("#MesAnio").text(monthNames[d.getMonth()] +" "+ d.getFullYear());

    getRutinas();

    String.prototype.replaceAll = function(searchStr, replaceStr) {
        var str = this;

        // escape regexp special characters in search string
        searchStr = searchStr.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');

        return str.replace(new RegExp(searchStr, 'gi'), replaceStr);
    }

    if (!String.Format) {
        Semana  = function(format) {
            var args = Array.prototype.slice.call(arguments, 1);
            return format.replace(/{(\d+)}/g, function(match, number) {
                return typeof args[number] != 'undefined'
                    ? args[number]
                    : match
                    ;
            });
        };
    }



});

function getRutinas(){

    $.ajax({
        type: 'GET',
        url: _ctx + 'cliente/get/rutinas',
        dataType: "json",
        success: function (data, textStatus) {
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                } else {
                    //Semana
                    if(data.length>0) {
                        getSemanas(data[0].id);
                        $rutina.fechaInicio = parseFromStringToDate2(data[0].fechaInicio);
                        $rutina.fechaFin = parseFromStringToDate2(data[0].fechaFin);
                    }
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {

        }
    });
}

function getSemanas(id) {
    const params = {};
    params.idrutina = id;

    $.ajax({
        type: 'GET',
        url: _ctx + 'cliente/get/obtenerSemanasPorRutina',
        dataType: "json",
        data: params,
        success: function (data, textStatus) {
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                } else {
                    //Semana
                    $.each(data,function (i,item) {
                        item.fechaInicio = parseFromStringToDate2(item.fechaInicio);
                        item.fechaFin = parseFromStringToDate2(item.fechaFin);
                        semanas.push(item);
                    });
                    $rutina.semanas = semanas;
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function (data) {
            generarDias();
        }
    });
}

function generarDias() {
    var FechaActual = new Date();
    var flagEncontrado = false;
    var semanaEncontradaSiguiente = {};
    $("#SemanaActual").text("{0}");
    var auxsemana = 0;
    $.each(semanas, function (i, item) {
        if (flagEncontrado == false && item.fechaInicio <= FechaActual && FechaActual <= item.fechaFin) {
            semanaEncontrada = item;
            semanaEncontradaSiguiente = semanas[i + 1];
            flagEncontrado = true;
            auxsemana +=1;
        }
    });

    if (semanaEncontrada != null && semanaEncontrada.fechaInicio != null && semanaEncontrada.fechaFin != null) {
        generarSemana(semanaEncontradaSiguiente,auxsemana);
    } else {
        generarGraficoEmpty();
    }
    _init();
}

function generarSemana(semanaEncontradaSiguiente,auxsemana) {
    $("#semanaRutina").html("");
    $("#semanaRutinaSiguiente").html("");
    $("#graphCumplimiento").html("");
    $("#graphKilometraje").html("");

    var dateTmpdet = semanaEncontrada.objetivos != "" && semanaEncontrada.objetivos != null ? semanaEncontrada.objetivos.split(",") : [];
    var auxdet = 0
    $("#SemanaActual").text(auxsemana);
    var dateArraySemana = getDates(semanaEncontrada.fechaInicio, semanaEncontrada.fechaFin);
    var fechaActual = new Date();

    var arrayDias = [1,2,3,4,5,6,0];
    var arrayDiasFaltan = [];
    var flagDiasAgregados = false;
    if(dateArraySemana.length != 7) {
        $.each(dateArraySemana, function (i, item) {
            arrayDiasFaltan.push(item.getDay());
        });
        var arrayEncontrados = compare(arrayDias,arrayDiasFaltan);
        $.each(arrayEncontrados, function (i, item) {
            var day = semanaEncontrada.fechaInicio.addDays(- parseInt(item));
            dateArraySemana.push(day);
            flagDiasAgregados = true;
        });
        dateArraySemana.sort((a,b)=> a-b);
    }

    $.each(dateArraySemana, function (i, item) {
        var daystr = item.getDay();
        var day = item.getUTCDate();
        var smonth = item.getMonth();

        var cssSection = fechaActual.getMonth() == item.getMonth() && fechaActual.getUTCDate() == day ? "section-dia-back" : "";
        var dayPast = fechaActual.getMonth() == item.getMonth() && fechaActual.getUTCDate() > day ? "section-dia-gray" : "section-dia";
        var dayhtml = fechaActual.getMonth() == item.getMonth() && fechaActual.getUTCDate() > day ? '<a class="a-sinclick" href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>' : '<a href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>';

        var section = "";
        if(flagDiasAgregados && semanaEncontrada.fechaInicio.getUTCDate() > day) {
            section = GenerarSection(dias[daystr], day, "", "section-dia-gray", "");
        }else{
            section = GenerarSection(dias[daystr], dayhtml, (dateTmpdet.length > 1 ? dateTmpdet[auxdet] : ""), dayPast, cssSection);
            auxdet += 1;
        }
        $("#semanaRutina").append(section);
    });

    if (dateArraySemana.length < 7) {
        const dayfirst = dateArraySemana.length == 0 ?  new Date() : dateArraySemana[dateArraySemana.length-1];
        var dateTmp = [];
        for (var i = 0; i < 7 - dateArraySemana.length; i++) {
            let result = new Date();
            result.setDate(dayfirst.getDate() + (i + 1));
            dateTmp.push(result);
        }

        dateTmp.sort((a, b) => a - b);
        $.each(dateTmp, function (i, item) {
            var daystr = item.getDay();
            var day = item.getUTCDate();
            var detalle = "";
            var cssSection =  "";
            var dayPast = "section-dia-gray";
            var section = GenerarSection(dias[daystr],day,detalle,dayPast,cssSection);
            $("#semanaRutina").append(section);
            auxdet += 1;
        });
        dateArraySemana.sort((a,b)=> a-b);
    }

    if (semanaEncontradaSiguiente != null && semanaEncontradaSiguiente.fechaInicio != null && semanaEncontradaSiguiente.fechaFin != null) {
        var auxdetsig = 0;
        var dateTmpdetSiguiente = semanaEncontradaSiguiente.objetivos != "" && semanaEncontradaSiguiente.objetivos != null ? semanaEncontradaSiguiente.objetivos.split(",") : [];
        var dateArraySemanaSiguiente = getDates(semanaEncontradaSiguiente.fechaInicio, semanaEncontradaSiguiente.fechaFin);
        $.each(dateArraySemanaSiguiente, function (i, item) {
            var daystr = item.getDay();
            var day = item.getUTCDate();
            var smonth = item.getMonth();


            var cssSection = fechaActual.getMonth() == item.getMonth() && fechaActual.getUTCDate() == day ? "section-dia-back" : "";
            var dayPast = fechaActual.getMonth() == item.getMonth() && fechaActual.getUTCDate() > day ? "section-dia-gray" : "section-dia";
            var dayhtml = fechaActual.getMonth() == item.getMonth() && fechaActual.getUTCDate() > day ? '<a class="a-sinclick" href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>' : '<a href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>';
            var section = GenerarSection(dias[daystr],dayhtml,(dateTmpdetSiguiente.length>0 ? dateTmpdetSiguiente[auxdetsig] : ""),dayPast,cssSection);
            $("#semanaRutinaSiguiente").append(section);
            auxdetsig+=1;
        });

        if (dateArraySemanaSiguiente.length < 7) {
            const dayfirst = dateArraySemanaSiguiente[dateArraySemanaSiguiente.length-1];
            var dateTmp = [];
            for (var i = 0; i < 7 - dateArraySemanaSiguiente.length; i++) {
                let result = new Date();
                result.setDate(dayfirst.getDate() + (i + 1));
                dateTmp.push(result);
            }

            dateTmp.sort((a, b) => a - b);
            $.each(dateTmp, function (i, item) {
                var daystr = item.getDay();
                var day = item.getUTCDate();
                var detalle = "";
                var cssSection =  "";
                var dayPast = "section-dia-gray";
                var section = GenerarSection(dias[daystr],day,detalle,dayPast,cssSection);
                $("#semanaRutinaSiguiente").append(section);
                auxdet += 1;
            });
            dateArraySemanaSiguiente.sort((a,b)=> a-b);
        }
    } else {
        const daylast = dateArraySemana[dateArraySemana.length - 1];
        var dateTmpS = [];
        for (var i = 0; i < 7; i++) {
            let result = new Date();
            result.setDate(daylast.getDate() + (i + 1));
            dateTmpS.push(result);
        }
        dateTmpS.sort((a, b) => a - b);
        $.each(dateTmpS, function (i, item) {
            var daystr = item.getDay();
            var day = item.getUTCDate();
            var detalle = "";
            var cssSection =  "";
            var dayPast = "section-dia-gray";
            var section = GenerarSection(dias[daystr],day,detalle,dayPast,cssSection);

            $("#semanaRutinaSiguiente").append(section);
        });
    }

    var ka = parseInt(semanaEncontrada.kilometrajeActual);
    $("#spanKilometrajeSemanal").text(ka + "Km");
    $("#spanHorasSemanales").text("0h");
    $("#spanKilometrajeCalorico").text("0Km");
    $("#spanCaloriasSemanales").text("0");
    generarGrafico(semanaEncontrada);
    //setTimeout(() => {
    generarMetricas(semanaEncontrada);
    //}, 2000);
}

function getDates(startDate, stopDate) {
    var dateArray = new Array();
    var currentDate = startDate;
    while (currentDate <= stopDate) {
        dateArray.push(currentDate)
        currentDate = currentDate.addDays(1);
    }
    return dateArray;
}

function generarGrafico(datos) {

    var actual = ((datos.kilometrajeActual / (datos.kilometrajeTotal == 0 ? 100 : datos.kilometrajeTotal))* 100).toFixed(0);
    var total = 100 - actual;

    Morris.Donut({
        element: 'graphCumplimiento',
        data: [
            {value: actual, label: 'Cumplido'},
            {value: total, label: 'Total'},
        ],
        colors : color_array,
        formatter: function (x) { return x + "%"}
    }).on('click', function(i, row){
        console.log(i, row);
    });

    Morris.Donut({
        element: 'graphKilometraje',
        data: [
            {value: actual, label: 'Cumplido'},
            {value: total, label: 'Restante'},
        ],
        colors : color_array,
        formatter: function (x) { return x + "%"}
    }).on('click', function(i, row){
        console.log(i, row);
    });

}

function generarGraficoEmpty() {

    Morris.Donut({
        element: 'graphCumplimiento',
        data: [
            {value: 0, label: 'Cumplido'},
            {value: 100, label: 'Total'},
        ],
        colors : color_array,
        formatter: function (x) { return x + "%"}
    }).on('click', function(i, row){
        console.log(i, row);
    });

    Morris.Donut({
        element: 'graphKilometraje',
        data: [
            {value: 0, label: 'Cumplido'},
            {value: 100, label: 'Restante'},
        ],
        colors : color_array,
        formatter: function (x) { return x + "%"}
    }).on('click', function(i, row){
        console.log(i, row);
    });

}

function GenerarSection(strDia, dia , detalle, dayPast,cssSection ){
return '<div class="col col-sm-2 ' + dayPast  +' '+ cssSection  +'" >\
        <div ><span >'+strDia+'</span><span class="label-dia">\
        <span style="margin: 0;">'+dia+'</span></span>\
        <span style="font-size: 16px;">'+detalle+'</span>\
        </div></div>';
}


function IrSemanaSiguiente(){
    if(semanas[semanas.length-1] != semanaEncontrada) {
        var day = semanaEncontrada.fechaFin.addDays(1).getDate();
        var month = semanaEncontrada.fechaFin.addDays(1).getMonth();
        var semanaEncontradaSiguiente = {};
        var auxsemana = 0;
        var flagEncontrado = false;
        $.each(semanas, function (i, item) {
            if (!flagEncontrado) {
                if (item.fechaInicio.getDate() == day && item.fechaInicio.getMonth() == month) {
                    semanaEncontrada = item;
                    semanaEncontradaSiguiente = semanas[i + 1];
                    flagEncontrado = true;
                }
                auxsemana += 1;
            }
        });

        if (semanaEncontrada != null) {
            generarSemana(semanaEncontradaSiguiente, auxsemana);
        } else {
            generarGraficoEmpty();
        }
    }
}

function IrSemanaAnterior(){
    if(semanas[0] != semanaEncontrada) {
        var day = semanaEncontrada.fechaInicio.addDays(-1).getDate();
        var month = semanaEncontrada.fechaInicio.addDays(-1).getMonth();
        var semanaEncontradaSiguiente = {};
        var auxsemana = 0;
        var flagEncontrado = false;
        $.each(semanas, function (i, item) {
            if (!flagEncontrado) {
                if (item.fechaFin.getDate() == day && item.fechaFin.getMonth() == month) {
                    semanaEncontrada = item;
                    semanaEncontradaSiguiente = semanas[i + 1];
                    flagEncontrado = true;
                }
                auxsemana += 1;
            }
        });

        if (semanaEncontrada != null) {
            generarSemana(semanaEncontradaSiguiente, auxsemana);
        } else {
            generarGraficoEmpty();
        }
    }
}

function IrSemanaDiaHoy(){
    var fechaActual = new Date();
    var day = fechaActual.getDate();
    var month = fechaActual.getMonth();
    var semanaEncontradaSiguiente= {};
    var auxsemana = 0;
    var flagEncontrado = false;
    $.each(semanas,function(i,item){
        if(!flagEncontrado) {
            var arryaDias = getDates(item.fechaInicio, item.fechaFin);
            $.each(arryaDias,function(u,aux){
                if (aux.getDate() == day && aux.getMonth() == month) {
                    semanaEncontrada = item;
                    semanaEncontradaSiguiente = semanas[i + 1];
                    flagEncontrado = true;
                }
            });
            auxsemana += 1;
        }
    });

    if (semanaEncontrada != null) {
        generarSemana(semanaEncontradaSiguiente,auxsemana);
    } else {
        generarGraficoEmpty();
    }
}

function getDayOfWeek(day, month){

    var semanaEncontradaSiguiente= {};
    var auxsemana = 0;
    var flagEncontrado = false;
    var auxSemanaencontrada = null;
    $.each(semanas,function(i,item){
        if(!flagEncontrado) {
            var arryaDias = getDates(item.fechaInicio, item.fechaFin);
            $.each(arryaDias,function(u,aux){
                if (aux.getDate() == day && aux.getMonth() == month) {
                    auxSemanaencontrada = semanas[i];
                    flagEncontrado = true;
                }
            });
            auxsemana += 1;
        }
    });

    if (auxSemanaencontrada != null) {
        mostrarSemana(auxSemanaencontrada,0,day);
    }

}

function compare(a1, a2) {
    var a = [], diff = [];
    for (var i = 0; i < a1.length; i++) {
        a[a1[i]] = true;
    }
    for (var i = 0; i < a2.length; i++) {
        if (a[a2[i]]) {
            delete a[a2[i]];
        } else {
            a[a2[i]] = true;
        }
    }
    for (var k in a) {
        diff.push(k);
    }
    return diff;
}

function generarMetricas(semana) {
    $('.trdelete').remove();
    if (semana.metricasVelocidad != null && semana.metricasVelocidad != "") {
        var metricas = JSON.parse(semana.metricasVelocidad);
        var TR ='<tr class="trdelete"><td class="td-table" colspan="4"><span>Ritmos y tiempos proyectados</span></td></tr>';
        TR += '<tr class="trdelete">' +
            '<td class="td-table" style="padding: 5px;"><span class=""> </span></td>' +
            '<td class="td-table" style="padding: 5px;"><span class=""> </span></td>' +
            '<td class="td-table" style="padding: 5px;"><span class=""> </span></td>' +
            '<td class="td-table" style="padding: 5px;"><span class=""> </span></td></tr>';

        var METROS = ["200mt","400mt","800mt","1km"];
        var KILOMETROS = ["10km","15km","21km","42km"];

        const wex = ["200m", "400m", "800m", "1KM","10KM","15KM","21KM","42KM"];
        metricas = metricas.map((v,i)=>{return {p: v.parcial, s: v.parcial.toSeconds(), m: wex[i]}});
        metricas.forEach((v,i)=>{
            if(i>3)
                v.tt = String(v.s*Number(v.m.slice(0, -2))).toHHMMSSM()
            else
                v.tt = v.p
        });

        for(let i=0; i<4 ;i++){
            var tds = '<tr class="trdelete">' +
                '<td class="td-table"><span class="span-time-header">'+( METROS[i] )+'</span></td>' +
                '<td class="td-table"><span class="spantime spantime-height"> '+ (i == 0 ? '<i class="fa fa-clock-o"></i>' :'' )+ '</span></td>' +
                '<td class="td-table"><span class="span-time-header">'+( KILOMETROS[i] )+'</span></td>' +
                '<td class="td-table"><span class="spantime spantime-height"> '+ (i == 0 ? '<i class="fa fa-clock-o"></i>' :'' )+ '</span></td>' +
                '<tr class="trdelete"><td class="td-table td-abajo"><span class="">'+(metricas[i].p)+'</span></td>' +
                '<td class="td-table td-abajo"><span class="span-time">'+(metricas[i].tt)+'</span></td>' +
                '<td class="td-table td-abajo"><span class="">'+(metricas[i+4].p)+'</span></td>' +
                '<td class="td-table td-abajo"><span class="span-time">'+(metricas[i+4].tt)+'</span></td></tr>';
            TR += tds;
        }

        $("#tbMetricas").append(TR);
    }
}





















