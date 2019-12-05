let indexGlobal = 0;
let $body = $("html,body");
let $semActual = $("#SemanaActual");
let semanasIxs = [];
let $refIxsSemCalendar = [];
let $fechasCompetencia = [];
const tabRutina = document.querySelector('#myTabRutina');
const divContent = document.querySelector('#content');


$(function () {
    tabRutina.addEventListener('click', principalesEventosCalendario);
    divContent.addEventListener('click', principalesEventos);
});

function principalesEventosCalendario(e) {
    const input = e.target;
    const clases = input.classList;

    if(clases.contains('mes-calendar')){
        const mes = input.getAttribute('data-mes');
        const anio = input.parentElement.getAttribute('data-anio');
        abrirCalendario(getCalendarioSemanaIxs(anio, mes),true);

    } else if(clases.contains('cal-retroceder-sem')){
        const qFechaInicio = getFechaInicioSemanaEspecifica($refIxsSemCalendar[0] - 1);
        abrirCalendario(getCalendarioSemanaIxs(qFechaInicio.anio, qFechaInicio.mes), true, qFechaInicio.mes);

    } else if(clases.contains('cal-adelantar-sem')){
        const semIxRef = $refIxsSemCalendar[$refIxsSemCalendar.length-1];
        let qFecha;
        if($rutina.semanas[semIxRef+1] != undefined) {
            qFecha = getFechaInicioSemanaEspecifica(semIxRef + 1);
        }
        else {
            qFecha = getFechaFinSemanaEspecifica(semIxRef);
        }
        abrirCalendario(getCalendarioSemanaIxs(qFecha.anio, qFecha.mes), true, qFecha.mes);

    } else if(clases.contains('fechas-calendar')){
        const mes = input.getAttribute('data-mes');
        const day = input.getAttribute('data-dia');
        if(mes != null && day != null) {
            getDayOfWeek(day, (parseInt(mes)-1));
        }
    }

    setTimeout(() => buscaDiasHabilitados(), 100);
}

function principalesEventos(e) {
    const input = e.target;
    const clases = input.classList;

    if(clases.contains('reprod-video')){
        e.stopPropagation();
        e.preventDefault();
        VerVideo($(input).attr("data-media"));
    } else if(clases.contains('reprod-audio')){
        e.stopPropagation();
        e.preventDefault();
        VerAudio($(input).attr("data-media"));
    }
}

function _init() {
    if($semActual.text() != "") {
        var aniomesActual = getFechaInicioSemanaEdicion();
        semanasIxs = getCalendarioSemanaIxs(aniomesActual.anio, aniomesActual.mes);
        abrirCalendario(semanasIxs, false, aniomesActual.mes);
        var diasemanaactual = new Date().getUTCDate();
        mostrarSemana(semanaEncontrada, 0, diasemanaactual);
        buscaDiasHabilitados();
    }
}

function getFechaInicioSemanaEdicion(){
    const semIndex = Number($semActual.text())-1;
    const objSem = $rutina.semanas[semIndex].fechaInicio;
    return {fechaInicio: objSem, anio: objSem.getFullYear(), mes: objSem.getMonth()}
}

function getCalendarioSemanaIxs (anio, mes){
    const coincidencias = $rutina.semanas.map((v,i)=>{
        if ((v.fechaInicio.getFullYear() == anio && v.fechaInicio.getMonth()== mes) || (v.fechaFin.getFullYear()== anio && v.fechaFin.getMonth()== mes))
            return i;
    })
    return coincidencias.filter(v=>{return v!=undefined});
}

function obtenerDiasByMes(y, m){
    return new Date(y, m+1, 0).getDate();
}

function abrirCalendario(semanasIxs,edicion,mes) {

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
                calendarBody+=`<div class="col-md-1 col-sm-1 col-xs-1 col-lg-1  font-md mini txt-color-grayDark text-align-center">${diaAnteriorAlMes-(6-v)+z}</div>`
            }
        }
        //Solo cumple para el primer mes/primeras semanas
        if(semanasIxs[0] == 0){
            for(let x=0; x < v; x++){
                if(d>= min && d<=max) {
                    calendarBody += `<div class="col-lg-1 col-md-1 col-xs-1 col-sm-1 font-md mini txt-color-grayDark text-align-center" id="${d}mesactual-anioactual-" data-mes data-dia="${d}" data-index="${primSem}">${d++}</div>`;
                }else{
                    calendarBody+=`<div class="col-lg-1 col-md-1 col-xs-1 col-sm-1 font-md mini txt-color-grayDark text-align-center">${d++}</div>`;
                }
            }
            if(d> min && d<=max)
                primSem++;
            //Solo cumple para el último mes/últimas semana
        }else if(semanasIxs[semanasIxs.length-1] == $rutina.totalSemanas-1) {
            for(let x=0; x < v; x++) {
                if (d <= max) {
                    calendarBody += `<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 font-md mini txt-color-grayDark text-align-center" id="${d}mesactual-anioactual-" data-mes data-dia="${d}" data-index="${i}">${d++}</div>`;
                } else {
                    calendarBody += `<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 font-md mini txt-color-grayDark text-align-center" data-index="${i}">${d++}</div>`;
                }
            }
        }
        //Los meses intermedios o full
        else{
            for (let x = 0; x < v; x++) {
                calendarBody += `<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 font-md mini txt-color-grayDark text-align-center" id="${d}mesactual-anioactual-" data-mes data-dia="${d}" data-index="${i}">
                                            ${d++}
                                            
                                         </div>`;
            }
        }

        //Cuando la semana final no esta completa y requiere crear dias iniciales para completarla
        if(i==calendarSem.length-1 && v < 7) {
            for (let z = 0; z < 7-v; z++) {
                calendarBody+=`<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 font-md mini txt-color-grayDark text-align-center">${1+z}</div>`
            }
        }
        calendarBody += `</div>`;
    })

    const iconCalendar = document.querySelector('#CalasasasendarioRf');
    if(edicion){
        if(iconCalendar.getAttribute('data-content') == ''){
            iconCalendar.setAttribute('data-content', reconstruirCalendario(calendarBody, anioFechaReferencial, mesFechaReferencial, meses[mesFechaReferencial]));
            $('#CalendarioRf').popover('show');
        }else{
            const popContent = iconCalendar.nextElementSibling.querySelector('.popover-content');
            popContent.appendChild(htmlStringToElement(reconstruirCalendario(calendarBody, anioFechaReferencial, mesFechaReferencial, meses[mesFechaReferencial])));
            setTimeout(() => popContent.children[0].remove(), 1);
        }
    }else{
        iconCalendar.setAttribute('data-content', reconstruirCalendario(calendarBody, anioFechaReferencial, mesFechaReferencial, meses[mesFechaReferencial]));

    }

    //Guardando las semanasIxs del mes que se muestra en el calendario
    $refIxsSemCalendar = semanasIxs;
    //Pitando en el calendario de otro color los días que el atleta tendrá una competicion
    const matches =  $fechasCompetencia.filter(v=>{
        return v.fecha.getMonth() == mesFechaReferencial && v.fecha.getFullYear() == anioFechaReferencial;
    });


    //Se espera 100ms para que se renderice completamente el calendario y así las queries del for no fallen
    if(matches.length>0) {
        setTimeout(() => {
            matches.forEach(v => {
                const diaCalendario = document.querySelector('#CalendarioRf').nextElementSibling.querySelector(`div[data-dia="${v.fecha.getDate()}"]`);
                diaCalendario.children[0].classList.toggle('event-calendar');
                diaCalendario.children[0].classList.toggle('event-calendar-c');
            })
        }, 100);
    }

    //setTimeout(() => {
    //    pintarDiaHoyCalendar(anioFechaReferencial, mesFechaReferencial, max);
    //}, 2150);
}

function reconstruirCalendario(dias, anio, mesInt, mesString){
    const mesFechaInicio = $rutina.fechaInicio.getMonth(), anioFi = $rutina.fechaInicio.getFullYear();
    const mesFechaFin = $rutina.fechaFin.getMonth(), anioFf = $rutina.fechaFin.getFullYear();
    let classFini = "", classFfin = "";

    dias = dias.replaceAll('data-mes', 'data-mes="'+(mesInt+1)+'"');
    dias = dias.replaceAll('mesactual-', (mesInt+1));
    dias = dias.replaceAll('anioactual-', (anio));

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
                                            <span class="pull-left padding-bottom-10">${mesString} <span>${anio}</span></span>
                                            <i class="fa fa-caret-down fa-fw" style="font-size: 0.8em" data-anio="${anio}" onclick="javascript:buscadorCalendario(this)"></i>
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

function buscadorCalendario(input) {
    const anioBase = input.getAttribute('data-anio');
    input.classList.toggle('fa-caret-down');
    input.classList.toggle('fa-caret-up');

    const contenedorPadreBase = input.parentElement.parentElement;
    contenedorPadreBase.nextElementSibling.classList.toggle('hidden');
    if(contenedorPadreBase.parentElement.children.length == 3){
        contenedorPadreBase.parentElement.children[2].remove();
    }else{
        //1.
        const y1 = $rutina.fechaInicio.getFullYear(), y2 = $rutina.fechaFin.getFullYear(), f1 = $rutina.fechaInicio, f2 = $rutina.fechaFin;
        const finals = [];
        for(let i=0; i<y2-y1;i++){
            finals.push([y1 + i, []]);
        }
        finals.push([y2,[]]);
        //2.
        finals.forEach((v,i,t)=>{
            //Primer y último año
            if(i== 0 || i==t.length-1)
                if(i==0)
                    if(y1 == y2)
                        for(let ii=0;ii<f2.getMonth()-f1.getMonth()+1;ii++) {
                            finals[i][1].push(f1.getMonth() + ii);
                        }
                    else
                        for(let ii=0;ii<11-f1.getMonth()+1;ii++){
                            finals[i][1].push(f1.getMonth() + ii);
                        }
                else
                    for(let ii=0;ii<f2.getMonth()+1;ii++){
                        finals[i][1].push(ii);
                    }
            else//Intermedios
                finals[i][1] = [0,1,2,3,4,5,6,7,8,9,10,11];
        })

        let filtros =   `<div class="container-fluid padding-0 cal-acordeon-query">
                                    <div class="panel-group smart-accordion-default smart-form" id="accordionX1">
                                    ${finals.map((v,i,k)=>{
            const claseIn = v[0]==anioBase?'in':'', claseCollap = v[0]==anioBase?'':'class="collapsed"';
            return `<div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h4 class="panel-title"><a class="txt-color-blue" data-toggle="collapse" data-parent="#accordionX1" href="#collapCal${i}" ${claseCollap}> <i class="fa fa-lg fa-angle-down pull-right"></i> <i class="fa fa-lg fa-angle-up pull-right"></i> ${v[0]} </a></h4>
                                                    </div>
                                                    <div id="collapCal${i}" class="panel-collapse collapse ${claseIn}">
                                                        <div class="panel-body" data-anio="${Number(k[0][0])+i}" style="padding: 0px !important;">
                                                        ${v[1].map(z=>{
                return `<div class="col col-md-3 bg-color-white txt-color-blue bordered mes-calendar" data-mes="${z}">${meses[z].substr(0,3).toUpperCase()}</div>`
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

function colapsarAll() {
    Array.from(getAllElementosCollapse()).forEach(e=>{
        e.classList.remove('collapsed')
        e.setAttribute('aria-expanded', "true");
    });

    Array.from(getAllPanelElementosCollapse()).forEach(e=>{
        e.classList.add('in')
        e.setAttribute('aria-expanded', "false");
        e.style = '';
    });
}

function getAllElementosCollapse() {
    return document.querySelectorAll('#myTabRutina #RutinaSemana a[data-toggle="collapse"]');
}

function getAllPanelElementosCollapse() {
    return document.querySelectorAll('#myTabRutina #RutinaSemana .panel-collapse');
}

function getFechaInicioSemanaEspecifica(semIx) {
    const objSem = $rutina.semanas[semIx].fechaInicio;
    return {fechaInicio: objSem, anio: objSem.getFullYear(), mes: objSem.getMonth()}
}

function getFechaFinSemanaEspecifica(semIx) {
    const objSem = $rutina.semanas[semIx].fechaInicio;
    return {fechaInicio: objSem, anio: objSem.getFullYear(), mes: objSem.getMonth()}
}

function pintarDiaHoyCalendar (anio, mes, max){
    const hoy = new Date();
    if(hoy.getMonth() == mes && hoy.getFullYear() == anio && hoy.getDate()<=max){
        setTimeout(() => {
            //$('div[data-dia ="'+hoy.getDate()+'"]').addClass('its-calendar-hoy');
            document.querySelector('#CalendarioRf').nextElementSibling.querySelector(`.its-calendar div[data-dia="${hoy.getDate()}"]`).classList.add('its-calendar-hoy');
        }, 100);
    }
}

function VerVideo(video){
    $('.divVideo').html("");

    var src = `${_ctx}`+`workout/media/file/video/gt/1`+ video;
    var divvideo =  '<button id="btnCerrarVideo" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
    '<video id="somevid" controls="controls" autoplay="autoplay" controlsList="nodownload" width="100%" height="100%">' +
    '<source id="VideoReproduccion" src="'+src+'" type="video/mp4"/></video>';

    $('.divVideo').append(divvideo);

    $("#btnCerrarVideo").click(function (){
        $('.divVideo').html("");
        $('.divVideo').hide();
    });

    $('.divVideo').show();
}
function VerAudio(audio){
    $('.divAudio').html("");

    var src = `${_ctx}`+`workout/media/audio`+ audio;
    var divAudio =  '<button id="btnCerrarAudio" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
                    '<audio id="someaud" preload="none" controls="" controlsList="nodownload" autoplay="" width="100%" height="100%">' +
                    '<source id="AudioReproduccion" src="'+src+'" type="audio/mpeg"/></audio>';

    $('.divAudio').append(divAudio);

    $("#btnCerrarAudio").click(function (){
        $('.divAudio').html("");
        $('.divAudio').hide();
    });

    $('.divAudio').show();

}

function buscaDiasHabilitados() {
    $.each(semanas, function (i, item) {
        $.each(item.lstDia,function(o,day){
            $("#"+day.fecha.getUTCDate()+""+(day.fecha.getMonth()+1)+""+day.fecha.getFullYear()).removeClass("txt-color-grayDark");
            $("#"+day.fecha.getUTCDate()+""+(day.fecha.getMonth()+1)+""+day.fecha.getFullYear()).addClass("fechas-calendar");
            $("#"+day.fecha.getUTCDate()+""+(day.fecha.getMonth()+1)+""+day.fecha.getFullYear()).append('<i class="fa fa-circle event-calendar"></i>');
        });
    });
}







