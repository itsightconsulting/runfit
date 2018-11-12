const tabPrincipal = document.querySelector('#myTabPrincipal');
const semanas = [];
var semanaEncontrada = {};
let $rutina = {};
let indexSemanaActual = 0;
const monthNames = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];

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
    $("#divQuincena").hide();
    $(".divMes").hide();

    $('#dayAllSelected').click(function () {
        if ($(this).prop('checked')) {
            $(".span-border-dia").removeClass("bg-green-rf");
            $(".span-border-dia").removeClass("bg-black-rf");
            $(".span-border-dia").addClass("bg-green-rf");
        }
        else {
            $(".span-border-dia").removeClass("bg-green-rf");
            $(".span-border-dia").addClass("bg-black-rf");
            $(".span-lunes").removeClass("bg-black-rf");
            $(".span-lunes").addClass("bg-green-rf");
        }
    });

    $(".span-border").click(function () {
        $(".span-border").removeClass("bg-green-rf");
        $(".span-border").addClass("bg-black-rf");
        $(this).removeClass("bg-black-rf");
        $(this).addClass("bg-green-rf");

        $(".divMes").hide();
        $("#divAlldays").hide();
        $("#divDiario").hide();
        $("#divQuincena").hide();
        if($(this).attr("data-id") == 1){
            $("#divAlldays").show();
            $("#divDiario").show();
        }else if($(this).attr("data-id") == 2) {
            $("#divQuincena").show();
        }else if($(this).attr("data-id") == 3) {
            $(".divMes").show();
            $("#divAlldays").show();
            $("#divDiario").show();
        }
        $(".span-border-dia").removeClass("bg-green-rf");
        $(".span-border-dia").addClass("bg-black-rf");
        $(".span-lunes").removeClass("bg-black-rf");
        $(".span-lunes").addClass("bg-green-rf");
    });
    $(".span-border-dia").click(function () {
        //var $span = $("span.span-border.bg-green-rf");

        //if($span.attr("data-id") == "1" || $span.attr("data-id") == "3") {
            if ($(this).hasClass("bg-green-rf")) {
                $(this).removeClass("bg-green-rf");
                $(this).addClass("bg-black-rf");
            } else {
                $(this).removeClass("bg-black-rf");
                $(this).addClass("bg-green-rf");
            }

            if($("span.span-border-dia.bg-green-rf").length == 0){
                $(".span-lunes").removeClass("bg-black-rf");
                $(".span-lunes").addClass("bg-green-rf");
            }

        //}else{
        //    $(".span-border-dia").removeClass("bg-green-rf");
        //    $(".span-border-dia").addClass("bg-black-rf");
        //    $(this).removeClass("bg-black-rf");
        //    $(this).addClass("bg-green-rf");
        //}
    });

    $(".span-border-semana").click(function () {
        $(".span-border-semana").removeClass("bg-green-rf");
        $(".span-border-semana").addClass("bg-black-rf");
        $(this).removeClass("bg-black-rf");
        $(this).addClass("bg-green-rf");
    });

    $('#modalrendimiento').on('hidden.bs.modal', function () {
        var result = {};
        var $dias = [];

        $.each($(".span-border-dia.bg-green-rf"),function(i,item){
            $dias.push($(this).attr("data-id"));
        });

        result.dias = $dias;

        //console.log(JSON.stringify(result));
        //console.log($('#dayAvance').val().replace("%",""));
        GuardarAvanceSemanal(JSON.stringify(result),$('#dayAvance').val().replace("%",""));

    });

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
                    if(data.length>0) {
                        getSemanas(data[0].id);
                        $rutina.fechaInicio = parseFromStringToDate2(data[0].fechaInicio);
                        $rutina.fechaFin = parseFromStringToDate2(data[0].fechaFin);
                        $rutina.control = data[0].control;
                        $rutina.id = data[0].id;
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
                    semanas.sort(function(a, b){return a.id - b.id});
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
        if (flagEncontrado == false ) {
            if (item.fechaInicio <= FechaActual && FechaActual <= item.fechaFin) {
                semanaEncontrada = item;
                semanaEncontradaSiguiente = semanas[i + 1];
                flagEncontrado = true;

            }
            auxsemana += 1;
            indexSemanaActual+=1;
        }
    });

    if (semanaEncontrada != null && semanaEncontrada.fechaInicio != null && semanaEncontrada.fechaFin != null) {
        generarSemana(semanaEncontradaSiguiente,auxsemana);
    } else {
        generarGraficoEmpty();
    }
    _init();
    mostrarModal();
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

    var arraySemanaActual = [];
    var arrayDias = [1,2,3,4,5,6,0];
    var arrayDiasFaltan = [];
    var flagDiasAgregados = false;
    if(dateArraySemana.length != 7) {
        $.each(dateArraySemana, function (i, item) {
            arrayDiasFaltan.push(item.getDay());
        });
        var arrayEncontrados = compare(arrayDias,arrayDiasFaltan);
        $.each(arrayEncontrados, function (i, item) {
            var day = null;

            if(semanas.length == auxsemana){
                day = semanaEncontrada.fechaFin.addDays(+ parseInt(i+1));
            }else{
                day = semanaEncontrada.fechaInicio.addDays(- parseInt(item));
            }

            dateArraySemana.push(day);
            flagDiasAgregados = true;
        });
        dateArraySemana.sort((a,b)=> a-b);

        $.each(dateArraySemana, function (i, item) {
            var objday = {};
            var valor =  arrayDiasFaltan.find(function(element) {
                return element == dateArraySemana[i].getDay();
            });
           if(valor != null){
               objday.flagEncontrado = true;
           }else{
               objday.flagEncontrado = false;
           }
            objday.day = item;
            arraySemanaActual.push(objday);
        });

        dateArraySemana = [];
        $.each(arraySemanaActual, function (i, item) {
            dateArraySemana.push(item.day);
            var daystr = item.day.getDay();
            var day = item.day.getUTCDate();
            var smonth = item.day.getMonth();
            var cssSection = "";
            var dayPast = "";
            var dayhtml = "";
            var section = "";

            cssSection = fechaActual.getFullYear() == item.day.getFullYear() && fechaActual.getMonth() == item.day.getMonth() && fechaActual.getUTCDate() == day ? "section-dia-back" : "";

            dayPast = (fechaActual.getFullYear() == item.day.getFullYear() && fechaActual.getMonth() > item.day.getMonth() ? "section-dia-gray"
                : (fechaActual.getFullYear() == item.day.getFullYear() &&  fechaActual.getMonth() == item.day.getMonth() && fechaActual.getUTCDate() > day  ? "section-dia-gray"
                    : fechaActual.getFullYear() == item.day.getFullYear() &&  fechaActual.getMonth() == item.day.getMonth() && fechaActual.getUTCDate() <= day ? "section-dia"
                        : "section-dia"));

            dayhtml = (fechaActual.getFullYear() == item.day.getFullYear() && fechaActual.getMonth() > item.day.getMonth() ? '<a class="a-sinclick" href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>'
                : (fechaActual.getFullYear() == item.day.getFullYear() &&  fechaActual.getMonth() == item.day.getMonth() && fechaActual.getUTCDate() > day  ? '<a class="a-sinclick" href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>'
                    : fechaActual.getFullYear() == item.day.getFullYear() &&  fechaActual.getMonth() == item.day.getMonth() && fechaActual.getUTCDate() <= day ? '<a href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>'
                        : '<a href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>'));


            if(item.flagEncontrado){
                section = GenerarSection(dias[daystr], dayhtml, (dateTmpdet.length > 1 ? dateTmpdet[auxdet] : ""), dayPast, cssSection);
                auxdet += 1;
            }else{
                section = GenerarSection(dias[daystr], day, "", "section-dia-gray", "");
            }

            $("#semanaRutina").append(section);
        });
    }else {
        $.each(dateArraySemana, function (i, item) {
            var daystr = item.getDay();
            var day = item.getUTCDate();
            var smonth = item.getMonth();

            var cssSection = fechaActual.getFullYear() == item.getFullYear() && fechaActual.getMonth() == item.getMonth() && fechaActual.getUTCDate() == day ? "section-dia-back" : "";

            var dayPast = (fechaActual.getFullYear() == item.getFullYear() && fechaActual.getMonth() > item.getMonth() ? "section-dia-gray"
                : (fechaActual.getFullYear() == item.getFullYear() && fechaActual.getMonth() == item.getMonth() && fechaActual.getUTCDate() > day ? "section-dia-gray"
                    : fechaActual.getFullYear() == item.getFullYear() && fechaActual.getMonth() == item.getMonth() && fechaActual.getUTCDate() <= day ? "section-dia"
                        : "section-dia"));

            var dayhtml = (fechaActual.getFullYear() == item.getFullYear() && fechaActual.getMonth() > item.getMonth() ? '<a class="a-sinclick" href="javascript:getDayOfWeek(' + day + ',' + smonth + ')">' + day + '</a>'
                : (fechaActual.getFullYear() == item.getFullYear() && fechaActual.getMonth() == item.getMonth() && fechaActual.getUTCDate() > day ? '<a class="a-sinclick" href="javascript:getDayOfWeek(' + day + ',' + smonth + ')">' + day + '</a>'
                    : fechaActual.getFullYear() == item.getFullYear() && fechaActual.getMonth() == item.getMonth() && fechaActual.getUTCDate() <= day ? '<a href="javascript:getDayOfWeek(' + day + ',' + smonth + ')">' + day + '</a>'
                        : '<a href="javascript:getDayOfWeek(' + day + ',' + smonth + ')">' + day + '</a>'));


            section = GenerarSection(dias[daystr], dayhtml, (dateTmpdet.length > 1 ? dateTmpdet[auxdet] : ""), dayPast, cssSection);
            auxdet += 1;

            $("#semanaRutina").append(section);
        });
    }

    if (semanaEncontradaSiguiente != null && semanaEncontradaSiguiente.fechaInicio != null && semanaEncontradaSiguiente.fechaFin != null) {
        var auxdetsig = 0;
        var dateTmpdetSiguiente = semanaEncontradaSiguiente.objetivos != "" && semanaEncontradaSiguiente.objetivos != null ? semanaEncontradaSiguiente.objetivos.split(",") : [];
        var dateArraySemanaSiguiente = getDates(semanaEncontradaSiguiente.fechaInicio, semanaEncontradaSiguiente.fechaFin);

         arraySemanaActual = [];
         arrayDias = [1,2,3,4,5,6,0];
         arrayDiasFaltan = [];
         flagDiasAgregados = false;
        if(dateArraySemanaSiguiente.length != 7){

            $.each(dateArraySemanaSiguiente, function (i, item) {
                arrayDiasFaltan.push(item.getDay());
            });
            var arrayEncontrados = compare(arrayDias,arrayDiasFaltan);
            $.each(arrayEncontrados, function (i, item) {
                var day = semanaEncontradaSiguiente.fechaFin.addDays(+ parseInt(i+1));
                dateArraySemanaSiguiente.push(day);
                flagDiasAgregados = true;
            });
            dateArraySemanaSiguiente.sort((a,b)=> a-b);

            $.each(dateArraySemanaSiguiente, function (i, item) {
                var objday = {};
                var valor =  arrayDiasFaltan.find(function(element) {
                    return element == dateArraySemanaSiguiente[i].getDay();
                });
                if(valor != null){
                    objday.flagEncontrado = true;
                }else{
                    objday.flagEncontrado = false;
                }
                objday.day = item;
                arraySemanaActual.push(objday);
            });


            $.each(arraySemanaActual, function (i, item) {

                var daystr = item.day.getDay();
                var day = item.day.getUTCDate();
                var smonth = item.day.getMonth();
                var cssSection = "";
                var dayPast = "";
                var dayhtml = "";
                var section = "";

                cssSection = fechaActual.getFullYear() == item.day.getFullYear() && fechaActual.getMonth() == item.day.getMonth() && fechaActual.getUTCDate() == day ? "section-dia-back" : "";

                dayPast = (fechaActual.getFullYear() == item.day.getFullYear() && fechaActual.getMonth() > item.day.getMonth() ? "section-dia-gray"
                    : (fechaActual.getFullYear() == item.day.getFullYear() &&  fechaActual.getMonth() == item.day.getMonth() && fechaActual.getUTCDate() > day  ? "section-dia-gray"
                        : fechaActual.getFullYear() == item.day.getFullYear() &&  fechaActual.getMonth() == item.day.getMonth() && fechaActual.getUTCDate() <= day ? "section-dia"
                            : "section-dia"));

                dayhtml = (fechaActual.getFullYear() == item.day.getFullYear() && fechaActual.getMonth() > item.day.getMonth() ? '<a class="a-sinclick" href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>'
                    : (fechaActual.getFullYear() == item.day.getFullYear() &&  fechaActual.getMonth() == item.day.getMonth() && fechaActual.getUTCDate() > day  ? '<a class="a-sinclick" href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>'
                        : fechaActual.getFullYear() == item.day.getFullYear() &&  fechaActual.getMonth() == item.day.getMonth() && fechaActual.getUTCDate() <= day ? '<a href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>'
                            : '<a href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>'));


                if(item.flagEncontrado){
                    section = GenerarSection(dias[daystr], dayhtml, (dateTmpdetSiguiente.length > 1 ? dateTmpdetSiguiente[auxdetsig] : ""), dayPast, cssSection);
                    auxdetsig += 1;
                }else{
                    section = GenerarSection(dias[daystr], day, "", "section-dia-gray", "");
                }

                $("#semanaRutinaSiguiente").append(section);
            });


        }else{
            $.each(dateArraySemanaSiguiente, function (i, item) {
                var daystr = item.getDay();
                var day = item.getUTCDate();
                var smonth = item.getMonth();


                var cssSection = fechaActual.getFullYear() == item.getFullYear() && fechaActual.getMonth() >= item.getMonth() && fechaActual.getUTCDate() == day ? "section-dia-back" : "";

                //var dayPast = fechaActual.getFullYear() == item.getFullYear() && fechaActual.getMonth() < item.getMonth() ? "section-dia-gray" : (fechaActual.getMonth() == item.getMonth() && fechaActual.getUTCDate() <= day  ? "section-dia" : "section-dia-gray");

                var dayPast = (fechaActual.getFullYear() == item.getFullYear() && fechaActual.getMonth() > item.getMonth() ? "section-dia-gray" :
                    (fechaActual.getFullYear() == item.getFullYear() &&  fechaActual.getMonth() == item.getMonth() && fechaActual.getUTCDate() <= day  ? "section-dia" : "section-dia"));

                // var dayhtml = fechaActual.getFullYear() == item.getFullYear() && fechaActual.getMonth() >= item.getMonth() && fechaActual.getUTCDate() > day ? '<a class="a-sinclick" href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>' : '<a href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>';

                var dayhtml = (fechaActual.getFullYear() == item.getFullYear() && fechaActual.getMonth() > item.getMonth() ? '<a class="a-sinclick" href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>':
                    (fechaActual.getFullYear() == item.getFullYear() &&  fechaActual.getMonth() == item.getMonth() && fechaActual.getUTCDate() <= day  ? '<a href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>' : '<a href="javascript:getDayOfWeek('+day+','+smonth+')">' + day + '</a>'));


                var section = GenerarSection(dias[daystr],dayhtml,(dateTmpdetSiguiente.length>0 ? dateTmpdetSiguiente[auxdetsig] : ""),dayPast,cssSection);
                $("#semanaRutinaSiguiente").append(section);
                auxdetsig+=1;
            });
        }
    } else {
        const daylast = dateArraySemana[dateArraySemana.length - 1];
        var dateTmpS = [];
        for (var i = 0; i < 7; i++) {
            //let result = new Date();
            //result.setDate(daylast.getDate() + (i + 1));

            dateTmpS.push(daylast.addDays((i+1)));
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
            $("#MesAnio").text(monthNames[semanaEncontrada.fechaInicio.getMonth()] +" "+ semanaEncontrada.fechaInicio.getFullYear());
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
            $("#MesAnio").text(monthNames[semanaEncontrada.fechaInicio.getMonth()] +" "+ semanaEncontrada.fechaInicio.getFullYear());
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
        $("#MesAnio").text(monthNames[semanaEncontrada.fechaInicio.getMonth()] +" "+ semanaEncontrada.fechaInicio.getFullYear());
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

function GuardarAvanceSemanal(strAvance, porcentaje){
    let params = {};
    params.idrutina = $rutina.id;
    params.indexsemana = indexSemanaActual;
    params.stravance = JSON.stringify(strAvance);
    params.porcentaje = porcentaje;

    $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina/elemento/updateAvance",
        dataType: "json",
        data: params,
        success: function (data) {
        },
        error: function (xhr) {
            console.log(xhr);
        },
        complete: function () {}
    })
}

function mostrarModal() {

    var avance = indexSemanaActual == 0 ? 0 : ($rutina.control.avanceSemanas[(indexSemanaActual-1)] == "" ? 0 : $rutina.control.avanceSemanas[(indexSemanaActual-1)]);
    var valor = JSON.parse($rutina.control.actualizarAvance);
    if (valor != null) {
        $("#dayAvance").val(avance);
        $("#dayAvance").knob({
            'format': function (value) {
                return value + '%';
            }
        });

        var fechaactual = new Date();

        var flagencontrado = false;
        $.each(valor.dias, function (i, item) {
            if (fechaactual.getUTCDay() == parseInt(item)) {
                flagencontrado = true;
            }
            $("#spandia" + parseInt(item)).removeClass("bg-black-rf");
            $("#spandia" + parseInt(item)).addClass("bg-green-rf");
        });

        if (flagencontrado) {
            $("#modalrendimiento").modal('show');
        }
    } else {
        $("#dayAvance").val(0);
        $("#dayAvance").knob({
            'format': function (value) {
                return value + '%';
            }
        });

        $("#modalrendimiento").modal('show');
    }
}

function IrActualizarAvance() {
    $("#modalrendimiento").modal('show');
}















