const $divCalendarioTmp = document.querySelector('#divCalendarioTmp');
let semanasIxs = [];
let diasSeleccionados = [];

$(function () {
    $divCalendarioTmp.addEventListener('click', principalesEventosCalendario);

    Date.prototype.addDays = function(days) {
        var date = new Date(this.valueOf());
        date.setDate(date.getDate() + days);
        return date;
    }

    $("#demo-setting").click(function () {
        generarDiasEnviados();
    });

    $("#reset-smart-widget").click(function () {
        guardarDiasEnviados();
    });

    $("#smart-fixed-header").click(function () {
        if($("#smart-fixed-header").prop("checked"))
        {
            $(".fechas-calendar-tmp").addClass("day-selected");
            $.each($rutina.semanas, function (i, item) {
                $.each(item.dias, function (u, aux) {
                    $diasSeleccionados.push(aux.fecha);
                });
            });

            var valormesseleccionado = parseInt($("#anioMesActual").attr("data-dia"));
            var valoranio = parseInt($("#anioMesActual").attr("data-mes"));
            if($(".event-calendar").length == $(".day-selected").length) {
                $("#mesanio" + valormesseleccionado + "" + valoranio).prop("checked", true);
            }
        }
        else{
            $(".fechas-calendar-tmp").removeClass("day-selected");
            $diasSeleccionados = [];
        }
    });

});

function calendarioTmp() {

    String.prototype.replaceAll = function(searchStr, replaceStr) {
        var str = this;
        // escape regexp special characters in search string
        searchStr = searchStr.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
        return str.replace(new RegExp(searchStr, 'gi'), replaceStr);
    }

    const qFechaInicio = getFechaInicioSemanaEdicionTmp();
    abrirCalendariotmp(RutinaGet.getCalendarioSemanaIxs(qFechaInicio.anio, qFechaInicio.mes), false, qFechaInicio.mes);

  // $('.mes-calendar-tmp').click(function () {
  //     const mes = $(this).attr('data-mes');
  //     const anio = $(this).parent().attr('data-anio');
  //     abrirCalendariotmp(RutinaGet.getCalendarioSemanaIxs(anio, mes), true);
  //     generarDiasEnviados();
  // });
}

function principalesEventosCalendario(e) {
    const input = e.target;
    const clases = input.classList;

    if(clases.contains('mes-calendar-tmp')){
        const mes = input.getAttribute('data-mes');
        const anio = input.parentElement.getAttribute('data-anio');
        abrirCalendariotmp(RutinaGet.getCalendarioSemanaIxs(anio, mes),true);
        generarDiasEnviados2();
    } else if(clases.contains('cal-retroceder-sem')) {
        $(".fechas-calendar-tmp").addClass("day-selected");

            const qFechaInicio = getFechaInicioSemanaEspecifica($refIxsSemCalendar[0] - 1);
            abrirCalendariotmp(RutinaGet.getCalendarioSemanaIxs(qFechaInicio.anio, qFechaInicio.mes), true, qFechaInicio.mes);
            generarDiasEnviados2();

    } else if(clases.contains('cal-adelantar-sem')){

            const semIxRef = $refIxsSemCalendar[$refIxsSemCalendar.length - 1];
            let qFecha;
            if ($rutina.semanas[semIxRef + 1] != undefined) {
                qFecha = getFechaInicioSemanaEspecifica(semIxRef + 1);
            }
            else {
                qFecha = getFechaFinSemanaEspecifica(semIxRef);
            }
            abrirCalendariotmp(RutinaGet.getCalendarioSemanaIxs(qFecha.anio, qFecha.mes), true, qFecha.mes);
            generarDiasEnviados2();

    } else if(clases.contains('fechas-calendar-tmp')) {
        var valoranio = parseInt($("#anioMesActual").attr("data-anio"));
        var valordiaseleccionado = parseInt(input.getAttribute("data-dia"));
        var valormesseleccionado = parseInt(input.getAttribute("data-mes"));

        if (input.classList.contains('day-selected')) {
            input.classList.remove('day-selected');
            var cant = $divCalendarioTmp.getElementsByClassName("day-selected").length;

            $diasSeleccionados = $.grep($diasSeleccionados, function(o,x) {
                return o.getUTCDate() === valordiaseleccionado && (o.getMonth() + 1) === valormesseleccionado && o.getFullYear() === valoranio;},true);
            var tmpArray = [];
            $.each($diasSeleccionados ,function (i,o) {

                if(o.getUTCDate() > valordiaseleccionado && (o.getMonth() + 1) === valormesseleccionado && o.getFullYear() === valoranio){
                    o = null;
                }

                if(o != null && (o.getMonth() + 1) > valormesseleccionado && o.getFullYear() === valoranio){
                    o = null;
                }

                if(o != null && o.getFullYear() > valoranio){
                    o = null;
                }
                tmpArray.push(o);
            });

            $diasSeleccionados = $.grep(tmpArray, function(o,x) {
                                    return o === null;},true);


            for (let i = 0; i < cant ; i++) {
                $("#dia"+(valordiaseleccionado+(i+1))+"_"+valormesseleccionado+"_"+valoranio).removeClass("day-selected");
            }

        }
        else {
            if ($diasSeleccionados.length == 0) {
                input.classList.add('day-selected');

                var nuevafecha = parseFromStringToDate2(valordiaseleccionado+"/"+valormesseleccionado+"/"+valoranio);
                $diasSeleccionados.push(nuevafecha);

            }else {
                var ultimodia = $diasSeleccionados[$diasSeleccionados.length - 1].getUTCDate();
                var ultimomes = $diasSeleccionados[$diasSeleccionados.length - 1].getMonth() + 1;
                var ultimoanio = $diasSeleccionados[$diasSeleccionados.length - 1].getFullYear();

                var nuevafecha = parseFromStringToDate2(ultimodia+"/"+ultimomes+"/"+ultimoanio);
                var lastDayofMonth = new Date(nuevafecha.getFullYear(), nuevafecha.getMonth()+1, 0).getUTCDate();

                if ((ultimodia + 1) == valordiaseleccionado && ultimomes == valormesseleccionado && ultimoanio == valoranio) {
                    input.classList.add('day-selected');

                    var nuevafecha = parseFromStringToDate2(valordiaseleccionado + "/" + valormesseleccionado + "/" + valoranio);
                    $diasSeleccionados.push(nuevafecha);
                }

                var dayEncontrado = $diasSeleccionados.find(function(element) {
                    return element.getUTCDate() == lastDayofMonth && (element.getMonth()+1) == ultimomes && element.getFullYear() == ultimoanio
                });

                if (dayEncontrado != null && ultimomes < valormesseleccionado && ultimoanio == valoranio) {
                    if (1 == valordiaseleccionado) {
                        input.classList.add('day-selected');
                        var nuevafecha = parseFromStringToDate2(valordiaseleccionado + "/" + valormesseleccionado + "/" + valoranio);
                        $diasSeleccionados.push(nuevafecha);
                    }
                }

                var dayEncontrado2 = $diasSeleccionados.find(function(element) {
                    return element.getUTCDate() == 31 && (element.getMonth()+1) == 12 && element.getFullYear() == ultimoanio
                });

                if (dayEncontrado2 != null && ultimomes > valormesseleccionado && ultimoanio < valoranio) {
                    if (1 == valordiaseleccionado) {
                        input.classList.add('day-selected');
                        var nuevafecha = parseFromStringToDate2(valordiaseleccionado + "/" + valormesseleccionado + "/" + valoranio);
                        $diasSeleccionados.push(nuevafecha);
                    }
                }
            }
        }

        if($(".event-calendar").length == $(".day-selected").length) {
            $("#mesanio" + valormesseleccionado + "" + valoranio).prop("checked", true);
        }

    } else if(clases.contains('selectedAllMonth'))
    {
        var valoranioseleccionado = parseInt(input.getAttribute("data-anio"));
        var valormesseleccionado = parseInt(input.getAttribute("data-mes"));

        var nuevafecha = parseFromStringToDate2(1 + "/" + (valormesseleccionado == 1 ? 12  : valormesseleccionado-1 ) + "/" + (valormesseleccionado == 1 ? valoranioseleccionado-1 :valoranioseleccionado) );
        var lastDayofMonth = new Date(nuevafecha.getFullYear(), nuevafecha.getMonth()+1, 0).getUTCDate();

        if($diasSeleccionados.length>0) {
            var dayEncontrado = $diasSeleccionados.find(function (element) {
                return element.getUTCDate() == lastDayofMonth && (element.getMonth() + 1) == (valormesseleccionado == 1 ? 12 : valormesseleccionado - 1) && element.getFullYear() == (valormesseleccionado == 1 ? valoranioseleccionado - 1 : valoranioseleccionado)
            });

            $diasSeleccionados = $.grep($diasSeleccionados, function (o, x) {
                return (o.getMonth() + 1) === valormesseleccionado && o.getFullYear() === valoranioseleccionado;
            }, true);

            var tmpArray = [];
            $.each($diasSeleccionados, function (i, o) {

                if ((o.getMonth() + 1) > valormesseleccionado && o.getFullYear() === valoranioseleccionado) {
                    o = null;
                }

                if (o != null && o.getFullYear() > valoranioseleccionado) {
                    o = null;
                }
                tmpArray.push(o);
            });

            $diasSeleccionados = $.grep(tmpArray, function (o, x) {
                return o === null;
            }, true);

            $(".fechas-calendar-tmp").removeClass("day-selected");

            if (dayEncontrado != null) {
                if (input.checked) {
                    $(".fechas-calendar-tmp").addClass("day-selected");

                    $.each($rutina.semanas, function (i, item) {
                        $.each(item.dias, function (u, aux) {
                            if ((aux.fecha.getMonth() + 1) == valormesseleccionado && aux.fecha.getFullYear() == valoranioseleccionado) {
                                $diasSeleccionados.push(aux.fecha);
                            }
                        });
                    });
                }
            } else {
                $("#mesanio" + valormesseleccionado + "" + valoranioseleccionado).prop("checked", false);
            }
        }else{
            if (input.checked) {
                $(".fechas-calendar-tmp").addClass("day-selected");

                $.each($rutina.semanas, function (i, item) {
                    $.each(item.dias, function (u, aux) {
                        if ((aux.fecha.getMonth() + 1) == valormesseleccionado && aux.fecha.getFullYear() == valoranioseleccionado) {
                            $diasSeleccionados.push(aux.fecha);
                        }
                    });
                });
            }
        }

    }
}

function abrirCalendariotmp(semanasIxs,edicion,mes) {

    semanasIxs.sort((a, b) => a - b);
    const primeraSemana = $rutina.semanas[semanasIxs[0]];
    let ixUltimaSemana = semanasIxs[semanasIxs.length-1];
    let ultimaSemana = $rutina.semanas[ixUltimaSemana];
    let fechaReferencial = primeraSemana.fechaInicio.getMonth() == mes ? primeraSemana.fechaInicio: primeraSemana.fechaFin;
    const anioFechaReferencial = fechaReferencial.getFullYear();
    const mesFechaReferencial = fechaReferencial.getMonth();

    const diasMes = obtenerDiasByMes(anioFechaReferencial, mesFechaReferencial);
    const mesArray = new Array(diasMes);

    const calendarSem = []; let calendarBody = "";
    let diaFor = new Date(anioFechaReferencial, mesFechaReferencial, 1);

    //Obteniendo los dias que tendran cada semana como arreglo(Ejemplo de arreglo mes 31 días: 7|7|7|7|3)
    for(let i=0; i<mesArray.length;i++){
        if(i==0){
            calendarSem[i] = diaFor.getDay() == 0? 1 :7 - diaFor.getDay() + 1;//Cantidad de iteraciones
        }else{
            if(diaFor.getDay()==1){//Para considerar unicamente cuando el día en iteración sea Lunes
                const disRestantes = diasMes - calendarSem[0] - 7 * (calendarSem.length - 1) > 6 ? Math.abs((i%7)-calendarSem[0]) : Math.abs(diasMes - calendarSem[0] - 7*(calendarSem.length-1));
                calendarSem[calendarSem.length] = disRestantes == 0?7:disRestantes;//Cantidad de iteraciones
            }
        }
        diaFor = new Date(anioFechaReferencial, mesFechaReferencial, i+2)//+2 para que no se guarde como el valor inicial y aumente siempre en un día
    }

    let min = primeraSemana.fechaInicio.getMonth() != mesFechaReferencial ? 1 : primeraSemana.fechaInicio.getDate();
    let max = 0;
    if (ixUltimaSemana == $rutina.totalSemanas-1) {
        if(ultimaSemana.fechaInicio.getMonth() != ultimaSemana.fechaFin.getMonth()){
            if(ultimaSemana.fechaInicio.getMonth() == mesFechaReferencial){
                max = diasMes;
            }else{
                max = ultimaSemana.fechaFin.getDate();
            }
        }else{
            max = ultimaSemana.fechaFin.getDate();
        }
    }else{
        if(ultimaSemana.fechaInicio.getMonth() != ultimaSemana.fechaFin.getMonth()){
            if(ultimaSemana.fechaInicio.getMonth() == mesFechaReferencial){
                max = diasMes;
            }else{
                max = ultimaSemana.fechaFin.getDate();
            }
        }else{
            max = ultimaSemana.fechaFin.getDate();
        }
    }

    let primSem = 0;
    let d = 1, diaAnteriorAlMes = new Date(anioFechaReferencial, mesFechaReferencial, 0).getDate();
    //RECREACION DE LOS DIAS DEL MES
    //BODY DEL CALENDARIO
    calendarSem.forEach((v,i)=>{
        calendarBody += `<div class="row seven-cols">`;
        //Cuando la semana inicial no es completa y requiere crear dias iniciales para completarla
        if(i==0 && v < 7) {
            for (let z = 0; z < 7-v; z++) {
                calendarBody+=`<div class="col-md-1 col-sm-1 col-xs-1 col-lg-1  font-md mini txt-color-grayDark disabled-tmp text-align-center">${diaAnteriorAlMes-(6-v)+z}</div>`
            }
        }
        //Solo cumple para el primer mes/primeras semanas
        if(semanasIxs[0] == 0){
            for(let x=0; x < v; x++){
                if(d>= min && d<=max) {
                    calendarBody += `<div class="col-lg-1 col-md-1 col-xs-1 col-sm-1 font-md mini fechas-calendar-tmp text-align-center" id="dia${d}_messelected_anioselected" data-mes data-dia="${d}" data-index="${primSem}">${d++}<i class="fa fa-circle event-calendar"></i></div>`;
                }else{
                    calendarBody+=`<div class="col-lg-1 col-md-1 col-xs-1 col-sm-1 font-md mini txt-color-grayDark disabled-tmp text-align-center">${d++}</div>`;
                }
            }
            if(d> min && d<=max)
                primSem++;
            //Solo cumple para el último mes/últimas semana
        }else if(semanasIxs[semanasIxs.length-1] == $rutina.totalSemanas-1) {
            for(let x=0; x < v; x++) {
                if (d <= max) {
                    calendarBody += `<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 font-md mini fechas-calendar-tmp text-align-center" id="dia${d}_messelected_anioselected"  data-mes data-dia="${d}" data-index="${i}">${d++}<i class="fa fa-circle event-calendar"></i></div>`;
                } else {
                    calendarBody += `<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 font-md mini txt-color-grayDark disabled-tmp text-align-center" data-index="${i}">${d++}</div>`;
                }
            }
        }
        //Los meses intermedios o full
        else{
            for (let x = 0; x < v; x++) {
                calendarBody += `<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 font-md mini fechas-calendar-tmp text-align-center" id="dia${d}_messelected_anioselected"  data-mes data-dia="${d}" data-index="${i}">
                                            ${d++}
                                            <i class="fa fa-circle event-calendar"></i>
                                         </div>`;
            }
        }

        //Cuando la semana final no esta completa y requiere crear dias iniciales para completarla
        if(i==calendarSem.length-1 && v < 7) {
            for (let z = 0; z < 7-v; z++) {
                calendarBody+=`<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 font-md mini txt-color-grayDark disabled-tmp text-align-center">${1+z}</div>`
            }
        }
        calendarBody += `</div>`;
    })

    const iconCalendar = document.querySelector('#CalendarioTmp');
    $("#CalendarioTmp").html(construirCalendarioTmp(calendarBody, anioFechaReferencial, mesFechaReferencial, meses[mesFechaReferencial]));
    //if(edicion){
    //    if(iconCalendar.getAttribute('data-content') == ''){
    //
    //        $('#CalendarioTmp').popover('show');
    //    }else{
    //        const popContent = iconCalendar.nextElementSibling.querySelector('.popover-content');
    //        popContent.appendChild(htmlStringToElement(construirCalendarioTmp(calendarBody, anioFechaReferencial, mesFechaReferencial, meses[mesFechaReferencial])));
    //        setTimeout(() => popContent.children[0].remove(), 1);
    //    }
    //}else{
    //    iconCalendar.setAttribute('data-content', construirCalendarioTmp(calendarBody, anioFechaReferencial, mesFechaReferencial, meses[mesFechaReferencial]));
    //}


    //Guardando las semanasIxs del mes que se muestra en el calendario
    $refIxsSemCalendar = semanasIxs;
    //Pitando en el calendario de otro color los días que el atleta tendrá una competicion
    const matches =  $fechasCompetencia.filter(v=>{
        return v.fecha.getMonth() == mesFechaReferencial && v.fecha.getFullYear() == anioFechaReferencial;
    });


    //Se espera 100ms para que se renderice completamente el calendario y así las queries del for no fallen
    //if(matches.length>0) {
    //    setTimeout(() => {
    //        matches.forEach(v => {
    //            const diaCalendario = document.querySelector('#CalendarioTmp').nextElementSibling.querySelector(`div[data-dia="${v.fecha.getDate()}"]`);
    //            diaCalendario.children[0].classList.toggle('event-calendar');
    //            diaCalendario.children[0].classList.toggle('event-calendar-c');
    //        })
    //    }, 100);
    //}

    //setTimeout(() => {
    //    pintarDiaHoyCalendar(anioFechaReferencial, mesFechaReferencial, max);
    //}, 2150);
}

function construirCalendarioTmp(dias, anio, mesInt, mesString){
    const mesFechaInicio = $rutina.fechaInicio.getMonth(), anioFi = $rutina.fechaInicio.getFullYear();
    const mesFechaFin = $rutina.fechaFin.getMonth(), anioFf = $rutina.fechaFin.getFullYear();
    let classFini = "", classFfin = "";

    dias = dias.replaceAll('data-mes', 'data-mes="'+(mesInt+1)+'"');
    dias = dias.replaceAll('_messelected', '_'+(mesInt+1));
    dias = dias.replaceAll('_anioselected', '_'+(anio));


    //Para la ocultación de las opciones de adelanto y atras en 1 mes del calendario
    if(mesInt == mesFechaInicio && mesInt == mesFechaFin && anio == anioFi && anio == anioFf ){
        classFini = "hidden", classFfin = "hidden";
    }else if(mesInt == mesFechaInicio && anio == anioFi){
        classFini = "hidden"
    }else if(mesInt == mesFechaFin &&  anio == anioFf){
        classFfin = "hidden";
    }

    return `<div class="container-fluid padding-0 its-calendar">
                                    <div class="container-fluid padding-0 padding-bottom-10">                                           
                                        <h6 class="">
                                            <span class="pull-left padding-bottom-10">
                                            <input id="mesanio${(mesInt+1)}${anio}" type="checkbox" style="margin-right:5px;" data-mes="${(mesInt+1)}"  data-anio="${anio}"  class="selectedAllMonth"/>
                                            ${mesString} <span>${anio}</span></span>
                                            <i id="anioMesActual" class="fa fa-caret-down fa-fw" style="font-size: 0.8em" data-mes="${(mesInt+1)}"  data-anio="${anio}" onclick="javascript:buscadorCalendarioTmp(this)"></i>
                                            <span class="pull-right padding-bottom-10"><i class="fa fa-arrow-circle-left fa-fw cal-retroceder-sem ${classFini}" title="Mes anterior"></i><i class="fa fa-arrow-circle-right fa-fw cal-adelantar-sem ${classFfin}" title="Mes siguiente"></i></span>
                                        </h6>
                                    </div>
                                    <div>
                                        <div class="container-fluid padding-0">
                                            <div class="row seven-cols">
                                                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1 mini cat-video">Lun</div>
                                                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1 mini cat-video">Mar</div>
                                                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1 mini cat-video">Mie</div>
                                                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1 mini cat-video">Jue</div>
                                                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1 mini cat-video">Vie</div>
                                                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1 mini cat-video">Sab</div>
                                                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1 mini cat-video">Dom</div>
                                            </div>
                                        </div>
                                        <div class="container-fluid padding-0">
                                            ${dias}
                                        </div>
                                    </div>
                            </div>`;
}

function getFechaInicioSemanaEdicionTmp(){
    const semIndex = 0;
    const objSem = $rutina.semanas[semIndex].fechaInicio;
    return {fechaInicio: objSem, anio: objSem.getFullYear(), mes: objSem.getMonth()}
}

function obtenerDiasByMes(y, m){
    return new Date(y, m+1, 0).getDate();
}

function buscadorCalendarioTmp(input) {

        const anioBase = input.getAttribute('data-anio');
        input.classList.toggle('fa-caret-down');
        input.classList.toggle('fa-caret-up');

        const contenedorPadreBase = input.parentElement.parentElement;
        contenedorPadreBase.nextElementSibling.classList.toggle('hidden');
        if (contenedorPadreBase.parentElement.children.length == 3) {
            contenedorPadreBase.parentElement.children[2].remove();
        }
        else {
            //1.
            const y1 = $rutina.fechaInicio.getFullYear(), y2 = $rutina.fechaFin.getFullYear(), f1 = $rutina.fechaInicio,
                f2 = $rutina.fechaFin;
            const finals = [];
            for (let i = 0; i < y2 - y1; i++) {
                finals.push([y1 + i, []]);
            }
            finals.push([y2, []]);
            //2.
            finals.forEach((v, i, t) => {
                //Primer y último año
                if (i == 0 || i == t.length - 1)
                    if (i == 0)
                        if (y1 == y2)
                            for (let ii = 0; ii < f2.getMonth() - f1.getMonth() + 1; ii++) {
                                finals[i][1].push(f1.getMonth() + ii);
                            }
                        else
                            for (let ii = 0; ii < 11 - f1.getMonth() + 1; ii++) {
                                finals[i][1].push(f1.getMonth() + ii);
                            }
                    else
                        for (let ii = 0; ii < f2.getMonth() + 1; ii++) {
                            finals[i][1].push(ii);
                        }
                else//Intermedios
                    finals[i][1] = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
            })

            let filtros = `<div class="container-fluid padding-0 cal-acordeon-query">
                                    <div class="panel-group smart-accordion-default smart-form" id="accordionX1">
                                    ${finals.map((v, i, k) => {
                const claseIn = v[0] == anioBase ? 'in' : '', claseCollap = v[0] == anioBase ? '' : 'class="collapsed"';
                return `<div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h4 class="panel-title"><a class="txt-color-blue" data-toggle="collapse" data-parent="#accordionX1" href="#collapCal${i}" ${claseCollap}> <i class="fa fa-lg fa-angle-down pull-right"></i> <i class="fa fa-lg fa-angle-up pull-right"></i> ${v[0]} </a></h4>
                                                    </div>
                                                    <div id="collapCal${i}" class="panel-collapse collapse ${claseIn}">
                                                        <div class="panel-body" data-anio="${Number(k[0][0]) + i}" style="padding: 0px !important;">
                                                        ${v[1].map(z => {
                    return `<div class="col col-md-3 bg-color-white txt-color-blue bordered mes-calendar-tmp" data-mes="${z}">${meses[z].substr(0, 3).toUpperCase()}</div>`
                }).join('')}
                                                        </div>
                                                    </div>
                                                </div>`
            }).join('')}
                                    </div>
                                 </div>`;
            contenedorPadreBase.parentElement.appendChild(htmlStringToElement(filtros));
        }

}

function getFechaInicioSemanaEspecifica(semIx) {
    const objSem = $rutina.semanas[semIx].fechaInicio;
    return {fechaInicio: objSem, anio: objSem.getFullYear(), mes: objSem.getMonth()}
}

function getDates(startDate, stopDate) {
    var dateArray = new Array();
    var currentDate2 = startDate;
    while (currentDate2 <= stopDate) {
        dateArray.push(currentDate2)
        currentDate2 = currentDate2.addDays(1);
    }
    return dateArray;
}

function generarDiasEnviados() {
    //if ($("#smart-fixed-header").prop("checked")) {
    //    $(".fechas-calendar-tmp").addClass("day-selected");
//
    //
    //    $.each(diasSeleccionados, function (u, aux) {
    //        $("#cdia" + aux.fecha.getUTCDate() + "_" + (aux.fecha.getMonth() + 1)).prop("checked", true);
    //    });
    //
    //}
    //else {
        $(".fechas-calendar-tmp").removeClass("day-selected");

        var mes = parseInt($("#anioMesActual").attr("data-mes"));
        var anio = parseInt($("#anioMesActual").attr("data-anio"));
        $diasSeleccionados = [];
        $.each($semanasEnviadas, function (i, item) {
            $.each(item.lstDia, function (u, aux) {
                if ((aux.fecha.getMonth() + 1) == mes && aux.fecha.getFullYear() == anio) {
                    $("#dia" + aux.fecha.getUTCDate() + "_" + mes + "_" + anio).addClass("day-selected");
                    $("#cdia" + aux.fecha.getUTCDate() + "_" + mes + "_" + anio).prop("checked", true);
                }
                $diasSeleccionados.push(aux.fecha);
            });
        });

    if($(".event-calendar").length == $(".day-selected").length) {
        $("#mesanio" + mes + "" + anio).prop("checked", true);
    }
}
function generarDiasEnviados2() {
    $(".fechas-calendar-tmp").removeClass("day-selected");

    var mes = parseInt($("#anioMesActual").attr("data-mes"));
    var anio = parseInt($("#anioMesActual").attr("data-anio"));

    $.each($diasSeleccionados, function (u, aux) {
        if ((aux.getMonth() + 1) == mes && aux.getFullYear() == anio) {
            $("#dia" + aux.getUTCDate() + "_" + mes + "_" + anio).addClass("day-selected");
            $("#cdia" + aux.getUTCDate() + "_" + mes + "_" + anio).prop("checked", true);
        }
    });

    if($(".event-calendar").length == $(".day-selected").length) {
        $("#mesanio" + mes + "" + anio).prop("checked", true);
    }
}

function guardarDiasEnviados() {
    var arrselected = [];
    var arraydias = [6, 0, 1, 2, 3, 4, 5];
    var auxsemana = 0;
    var cantidad = $diasSeleccionados.length;

    if(cantidad == 0)
    {
        $.smallBox({content: '<i>Debe de seleccionar al menos 1 día...</i>'});
    }
    else {

        // $diasSeleccionados

        $.each($rutina.semanas, function (i, item) {
            $.each(item.dias, function (u, aux) {
                var dayEncontrado = $diasSeleccionados.find(function(element) {
                    return element.getUTCDate() == aux.fecha.getUTCDate() && (element.getMonth()+1) == (aux.fecha.getMonth()+1) && element.getFullYear() == aux.fecha.getFullYear()
                });

                if (dayEncontrado != null) {
                    let objs = {};
                    objs.dia = aux.fecha.getUTCDate();
                    objs.semana = auxsemana;
                    arrselected.push(objs);
                }
            });
            auxsemana += 1;
        });

        guardarDiasSeleccionados(arrselected)
    }
}

function guardarDiasSeleccionados(arrselected) {
    let params = {};
    params.listjson = JSON.stringify(arrselected);
    params.anio = parseInt($("#anioMesActual").attr("data-anio"));
    params.mes = parseInt($("#anioMesActual").attr("data-mes"));

    $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina/elemento/updateDiasSeleccionados",
        dataType: "json",
        data: params,
        success: function (data) {
            $.smallBox({content: '<i>Se han enviado la rutina al Cliente...</i>'});
        },
        error: function (xhr) {
            console.log(xhr);
        },
        complete: function () {
            obtenerSemanasEnviadas();
        }
    })

}
