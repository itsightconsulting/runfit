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
                    console.log(data);
                    //if(data.length >0){
                    //    $rutina = data[1];
                    //    $.each(data,function (i,item) {
                    //        item.fechaInicio = parseFromStringToDate2(item.fechaInicio);
                    //        item.fechaFin = parseFromStringToDate2(item.fechaFin);
                    //        var listmp = getSemanas(item.id);
                    //        item.ListaSemanas = listmp;
                    //    });
                    //    getSemanas(datos.id);
                    //}
                    getSemanas(data[0].id);
                    $rutina.fechaInicio = parseFromStringToDate2(data[0].fechaInicio);
                    $rutina.fechaFin  = parseFromStringToDate2(data[0].fechaFin);
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
                    console.log(data);
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
        complete: function () {
            generarDias();
        }
    });
}

function generarDias() {
    var FechaActual = new Date();
    var flagEncontrado = false;
    var semanaEncontradaSiguiente = {};
    $("#SemanaActual").text("Semana {0}");
    var auxsemana = 1;
    $.each(semanas, function (i, item) {
        if (flagEncontrado == false && item.fechaInicio <= FechaActual && FechaActual <= item.fechaFin) {
            semanaEncontrada = item;
            semanaEncontradaSiguiente = semanas[i + 1];
            flagEncontrado = true;
            auxsemana +=1;
        }
    });

    if (semanaEncontrada != null) {
        var dateTmpdet = semanaEncontrada.objetivos.split(",");
        var auxdet = 0
        $("#SemanaActual").text(auxsemana);
        var dateArraySemana = getDates(semanaEncontrada.fechaInicio, semanaEncontrada.fechaFin);
        if (dateArraySemana.length > 0) {
            //const dayfirst = dateArraySemana[0];
            //var dateTmp = [];
            //for (var i = 0; i < 7 - dateArraySemana.length; i++) {
            //    let result = new Date();
            //    result.setDate(dayfirst.getDate() - (i + 1));
            //    dateTmp.push(result);
            //}
//
            //dateTmp.sort((a, b) => a - b);
            //$.each(dateTmp, function (i, item) {
            //    var daystr = item.getDay();
            //    var day = item.getUTCDate();
//
            //    var section = '<div class="col col-sm-2 section-dia-gray">\
            //                <span class="span-dia"><span id="dia1">' + dias[daystr] + '</span></span>\
            //                <span class="span-dia-detalle"><span id="dia1">' + dateTmpdet[auxdet] + '</span></span>\
            //                <div class="container-fluid">\
            //                    <label class="label-dia"><b>' + day + '</b></label>\
            //                </div>\
            //            </div>';
            //    $("#semanaRutina").append(section);
            //    auxdet += 1;
            //});
            //console.log(dateTmp);
            //dateArraySemana.sort((a,b)=> a-b);
        }

        var fechaActual = new Date();
        $.each(dateArraySemana, function (i, item) {
            var daystr = item.getDay();
            var day = item.getUTCDate();

            var cssSection = fechaActual.getUTCDate() == day ? "section-dia-back" : "";
            var dayPast = fechaActual.getUTCDate() > day ? "section-dia-gray" : "section-dia";
            var dayhtml = fechaActual.getUTCDate() > day ? '<b>' + day + '</b>' : '<a href="javascript:getDayOfWeek('+daystr+')">' + day + '</a>';

            var section = '<div class="col col-sm-2 ' + dayPast + '">\
                            <span class="span-dia"><span id="dia1">' + dias[daystr] + '</span></span>\
                            <span class="span-dia-detalle"><span id="dia1">' + dateTmpdet[auxdet] + '</span></span>\
                            <div class="container-fluid ' + cssSection + '">\
                                <label class="label-dia">' + dayhtml + '</label>\
                            </div>\
                        </div>';

            $("#semanaRutina").append(section);
            auxdet += 1;
        });

        if (semanaEncontradaSiguiente != null) {
            var dateArraySemanaSiguiente = getDates(semanaEncontradaSiguiente.fechaInicio, semanaEncontradaSiguiente.fechaFin);
            $.each(dateArraySemanaSiguiente, function (i, item) {
                var daystr = item.getDay();
                var day = item.getUTCDate();
                var detalle = "";
                var section = '<div class="col col-sm-2 section-dia">\
                            <span class="span-dia"><span id="dia1">' + dias[daystr] + '</span></span>\
                            <span class="span-dia-detalle"><span id="dia1">' + detalle + '</span></span>\
                            <div class="container-fluid">\
                                <label class="label-dia"><a>' + day + '</a></label>\
                            </div>\
                        </div>';

                $("#semanaRutinaSiguiente").append(section);
            });
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
                var section = '<div class="col col-sm-2 section-dia">\
                            <span class="span-dia"><span id="dia1">' + dias[daystr] + '</span></span>\
                            <span class="span-dia-detalle"><span id="dia1">' + detalle + '</span></span>\
                            <div class="container-fluid">\
                                <label class="label-dia"><a>' + day + '</a></label>\
                            </div>\
                        </div>';

                $("#semanaRutinaSiguiente").append(section);
            });
        }

        var ka = parseInt(semanaEncontrada.kilometrajeActual);
        $("#spanKilometrajeSemanal").text(ka + "Km");
        $("#spanHorasSemanales").text("0h");
        $("#spanKilometrajeCalorico").text("0Km");
        $("#spanCaloriasSemanales").text("0");
        generarGrafico(semanaEncontrada);

    } else {
        generarGraficoEmpty();
    }
    _init();
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

function getDayOfWeek(idsemana) {
    var dayEncontrado = {};

    $.each(semanaEncontrada.lstDia ,function (i,item) {
        if(idsemana == item.dia){
            console.log(idsemana);
        }
    });
}





