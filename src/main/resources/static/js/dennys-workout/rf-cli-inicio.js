//Variables requeridas
let indexGlobal = 0;
let $body = document.querySelector('body');
let $rutina;
let $inFocus;
let $diaPlantilla;
let $diaPlantillas;
let $yelmo = '';
let $nombreActualizar = '';
let $memoriaAudio = '';
let $mediaAudio = '';
let $mediaNombre = '';
let $mediaVideo = '';
let $tiempoActualizar = '';
let $kmsActualizar = '';
let $gIndex = '';
let $tipoMedia = '';
let $refIxsSemCalendar = [];
let $fechasCompetencia = [];
let $eleGenerico;
let $estilosCopiados = [];
let $kilometrajeBase = [];
let $semCalculoMacro = {};
let $objetivos = [];
let $ruConsolidado;
let $chartTemporada = {};
let $chartMiniPorc = {};
let $idsComp = [];
let $semanasEnviadas = [];
let $diasSeleccionados = [];
let isDg = "n";//Importante iniciarlo con n
let $gSemanaIx = 0;
let $numSem = document.getElementById('NumSemana');
let $relativeCalendarDate;

(function () {
    init();
})();

function init(){
    getDatosDeLaUltimaRutina().then((rutina)=>{
        instanciandoIndicadoresCirculo();

        const ix = getSemanaIndice(rutina.fechaInicio, rutina.fechaFin);

        getSemanasDeLaUltimaRutinaGenerada(ix).then((sem)=>{
            //Importante mantener el orden para el correcto funcionamiento
            $rutina = new Rutina(rutina);
            $rutina.init(sem, ix);
            vistaSemana(sem, ix);
            setTimeout(  () => {
                imgToSvg();
                document.querySelector('a[data-parent="#panel_days"]').click();
            }, 400);
        }).catch((xhr)=>{
            exception(xhr)
        });

        $body.addEventListener('click', principalesEventosClickRutina);
        $body.querySelector('.nav-tabs').addEventListener('click', clickEventListenerNavTabs);
        /*
        validators();
        instanciarDatosFitnessCliente();

        tabRutina.addEventListener('click', principalesEventosTabRutina);
        cboEspSubCategoriaIdSec.addEventListener('change', cargarReferenciasMiniPlantilla);
        mainTabs.addEventListener('click', principalesAlCambiarTab);
        btnVerDetSemanas.addEventListener('click', FichaSeccion.newAlertaInfoSemanas);
        btnGenerarRutinaCompleta.addEventListener('click', MacroCiclo.generarRutinaCompleta);
        window.addEventListener('scroll', scrollGlobal);//Scroll event para regresar al techo del container
        instanciaMediaBD();
        instanciarTooltips();
        modalEventos();*/
    }).catch((err)=>{
        console.log(err);
        sinRutina()
    });
    eventTab();
    pulsosRitmos();
    //Check cambios en responsive
    if (window.innerWidth <= 415) {
        $('#range').attr('max', 6);
    }
}

function principalesEventosClickRutina(e){
    const input = e.target;
    const clases = input.classList;
    if(input.tagName === "path"){
        let svg = searchSvgTraversing(input);
        svgIconsEvents(svg);
    }
    else if(input.tagName === "svg"){
        svgIconsEvents(input);
    }
    else if (clases.contains('owl-prev')) {
        const inpRange = document.getElementById('range');
        const actualValue = inpRange.value;
        inpRange.value = Number(actualValue) - 1;
    }
    else if (clases.contains('owl-next')) {
        const inpRange = document.getElementById('range');
        const actualValue = inpRange.value;
        inpRange.value = Number(actualValue) + 1;
    }
    else if(clases.contains('title')){
        const parent = input.parentElement;
        if (parent && parent.classList.contains('item')) {
            const itemSelected = document.getElementById('carousel-semanal').querySelector('.owl-carousel .item.selected');
            if (itemSelected) {
                itemSelected.classList.remove('selected');
            }
            parent.classList.add('selected');
        }
    }
    else if(clases.contains('mini-panel')){
        const diaIndex = Number(input.getAttribute('data-dia-index'));
        visualizarElementoDiaInVistaSemana(input, diaIndex);
    }
}

function visualizarElementoDiaInVistaSemana(divEle, dIx){
    let i=0;
    let tempEle = divEle;
    while((tempEle = tempEle.previousElementSibling) && tempEle.classList.contains('mini-panel')) i++;
    const sIx = Number($('#NumSemana').text())-1;
    const elementos = $rutina.semanas[sIx].dias[dIx].elementos;
    const divElementosDia = document.getElementById('ElementosDia');
    divElementosDia.innerHTML = "";
    elementos.forEach(e=>{
        divElementosDia.appendChild(htmlStringToElement(`
                <div class="panel-group">
                  <div class="panel panel-default">
                    <div class="panel-heading">
                      <h3>${e.nombre}
                        <div class="mas_menos"><img class="svg" src="${_ctx}img/iconos/icon_menos.svg"><img class="svg" src="${_ctx}img/iconos/icon_mas.svg"></div>
                      </h3>
                      <div class="icons"><a data-toggle="collapse" data-parent="#accordion" href="#diario1"><img class="svg arrow" src="${_ctx}img/iconos/icon_flecha2.svg"></a></div>
                    </div>
                    <div class="panel-collapse collapse in" id="diario${sIx}-${dIx}-${i}">
                      ${!e.subElementos ? 
                           '':
                           `<div class="panel-body">
                                      <span class="text_green">1 serie x 20" de recuperación
                                          <div class="icons no_mostrar_mobile">
                                            <img class="svg" src="${_ctx}img/iconos/icon_microfono.svg">
                                            <img class="svg" src="${_ctx}img/iconos/icon_leyenda.svg">
                                            <span>
                                                <img class="svg" src="${_ctx}img/iconos/icon_tiempo2.svg">20
                                            </span>
                                          </div>
                          </span>`+e.subElementos.map(se=>{
                                          const tsubEle = se.tipo;
                                          const mediaVideo = se.mediaVideo;
                                          const checkMediaVideo = mediaVideo ? true : false;
                                          let thumbnail = "";
                                          let checkThumbnail = false;
                                          if(checkMediaVideo){
                                              thumbnail = mediaVideo.split("&tn=")[1];
                                              checkThumbnail = thumbnail.length > 36;
                                              if(checkThumbnail){
                                                  thumbnail = `https://s3-us-west-2.amazonaws.com/rf-media-rutina/video/${mediaVideo.split("/")[1]+'/'+thumbnail}`
                                              }
                                          }
                                          if(tsubEle !=  2){
                                              return ``
                                          }
                                          return `<div class="material-diario">
                                                    <div class="material">
                                                      <div class="content_img" style="${!checkThumbnail ? '' : 'background: url('+thumbnail+') no-repeat center;background-size: cover;'}"></div>
                                                      <div class="content_text">
                                                        <div class="title">
                                                          <h4><img class="svg" src="${_ctx}img/iconos/icon_microfono.svg">${se.nombre}<span>Alternar</span></h4>
                                                        </div>
                                                      </div>
                                                    </div>
                                                  </div>`
                          }).join('')
                      }
                    </div>
                  </div>
                </div>
        `));
    });
    //Instanciando svg elementos
    imgToSvg();
}

function clickEventListenerNavTabs(e){

    const input = e.target;

    if(input.tagName === "path"){
        e.stopPropagation();
        let svg = searchSvgTraversing(input);
        const ahref = svg.getAttribute('data-tab');
        if(!ahref){
            return;
        }

        if(ahref === "mensual"){
            //$('.datepicker_inline').data("DateTimePicker").date(sem.fechaInicio);
            console.log()
            datepicker_init($rutina.fechaInicio, $rutina.fechaFinPt);
        }
        svg.parentElement.click();
        if(ahref === "semanal"){
            setTimeout(()=>{
                heightCard();
            }, 500);

        }
    }
    else if(input.tagName === "svg"){
        e.stopPropagation();
        const ahref = input.getAttribute('data-tab');
        if(!ahref){
            return;
        }

        if(ahref === "mensual"){
            //$('.datepicker_inline').data("DateTimePicker").date(sem.fechaInicio);
            datepicker_init($rutina.fechaInicio, $rutina.fechaFinPt);
        }
        input.parentElement.click();
        if(ahref === "semanal"){
            setTimeout(()=>{
                heightCard();
            }, 500);

        }
    }
}

function searchSvgTraversing(path){
    let svg = path;
    while(svg.tagName.toUpperCase() !== "SVG"){
        svg = svg.parentElement;
    }
    return svg;
}

function searchClassTraversing(ele){
    let temp = ele;
    while(!temp.classList.contains('panel-heading')){
        temp = temp.parentElement;
    }
    return temp;
}

function svgIconsEvents(svg){
    const clases = svg.classList;
    if (clases.contains('ico-video')) {
        //svg.parentElement.parentElement.querySelectorAll('[data-fancybox]').forEach(e => $(e).fancybox());
    }else if (clases.contains('ele-plus')) {
        actionCollapsableIconPlusOrLess(svg, 1);
        svg.style.fill = "#a8fa00";
        svg.previousElementSibling.style.fill = "";
    } else if (clases.contains('ele-less')) {
        actionCollapsableIconPlusOrLess(svg, 0);
        svg.style.fill = "#a8fa00";
        svg.nextElementSibling.style.fill = "";
    }
}

function actionCollapsableIconPlusOrLess(svg, isPlus){
    const collapserElement = searchClassTraversing(svg).querySelector('a[data-toggle="collapse"]');
    if(collapserElement.hasAttribute('aria-expanded')){
        if(collapserElement.getAttribute('aria-expanded') === (isPlus ? 'false' : 'true')){
            collapserElement.click();
        }
    } else{
        if(!isPlus){
            return;
        }
        collapserElement.click();
    }
}

function instanciandoIndicadoresCirculo(){
    $("#indicadorCirculo1").knob({
        'format': function (value) {
            return value + '%';
        }
    });
    $("#indicadorCirculo2").knob({
        'format': function (value) {
            return value;
        }
    });

}

function eventTab() {
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        $("#indicadorCirculo3").knob({
            'format': function (value) {
                return value + '%';
            }
        });
        $("#indicadorCirculo4").knob({
            'format': function (value) {
                return value;
            }
        });
    });
}

async function getSemanasDeLaUltimaRutinaGenerada(semanaIx){
    return new Promise((resolve, reject)=>{
        $.ajax({
            type: 'GET',
            url: _ctx + 'cliente/get/semana/ix?semanaIx='+semanaIx,
            dataType: "json",
            blockLoading: false,
            noOne: true,
            success: function (data) {
                resolve(data);
            },
            error: function (xhr) {
                reject(xhr);
            },
            complete: function () {}
        });
    })
}
let timessemit = 0;
function vistaSemana(data, sIx) {
    ++timessemit;
    const objSemRutina = data;
    const fecha = objSemRutina.fechaInicio.split('/');
    const firstFecha = parseInt(objSemRutina.fechaInicio.split('/')[1]);
    const week = parseInt(fecha[1]);
    const semanaFechaInicio = parseFromStringToDate2(objSemRutina.fechaInicio);
    const ruFfin = $rutina.fechaFin;
    const remainDays = moment(ruFfin).diff(semanaFechaInicio, 'days');

    if(week <= firstFecha) {
        //Limpiando elementos carusel de vista semana
        const itemsCaruselSemana = $('#carousel-semanal .item').length;
        for (var i=0; i < itemsCaruselSemana; i++) {
            $("#carousel-semanal").trigger('remove.owl.carousel', [0]).trigger('refresh.owl.carousel');
        }

        //Reseteando el slider
        document.getElementById('range').value = 0;

        const day = objSemRutina.lstDia;

        $.each(day, function (i, dato) {
            const finalRemainDays = remainDays-i;
            const finalRemainMessage = finalRemainDays > 0 ? `Faltan ${finalRemainDays} días` : 'Llego el día!';
            const data = elementosWeek(dato, (diaIx = i));

            if(timessemit>1){
                $('#carousel-semanal').trigger('add.owl.carousel',
                    htmlStringToElement(
                           `<div class="item item-carusel-semana mCustomScrollbar" data-six="${i}">
                                      <div class="title">${dato.diaLiteral}</div>
                                      <div class="body-card">
                                        <div class="dias">${finalRemainMessage}<img class="svg" src="img/iconos/icon_ayuda.svg"></div>
                                        <ul>
                                          <li><img class="svg" src="${_ctx}img/iconos/icon_programas.svg">Carrera<img class="svg help" src="${_ctx}img/iconos/icon_ayuda.svg"></li>
                                          <li><img class="svg" src="${_ctx}img/iconos/icon_temporada.svg">Fuerza<img class="svg help" src="${_ctx}img/iconos/icon_ayuda.svg"></li>
                                          <li><img class="svg" src="${_ctx}img/iconos/icon_cronometro.svg">${parseNumberToHoursNoExcedent(dato.minutos, 0)}<img class="svg help" src="${_ctx}img/iconos/icon_ayuda.svg"></li>
                                          <li><img class="svg" src="${_ctx}img/iconos/icon_km.svg">${dato.distancia}<img class="svg help" src="${_ctx}img/iconos/icon_ayuda.svg"></li>
                                        </ul>
                                        ${data}
                                      </div>
                                    </div>`
                    )
                );
            } else {
                $("#carousel-semanal").append(
                    `<div class="item item-carusel-semana" data-six="${i}">
                      <div class="title">${dato.diaLiteral}</div>
                      <div class="body-card">
                        <div class="dias">${finalRemainMessage}<img class="svg" src="img/iconos/icon_ayuda.svg"></div>
                        <ul>
                          <li><img class="svg" src="img/iconos/icon_programas.svg">Carrera<img class="svg help" src="img/iconos/icon_ayuda.svg"></li>
                          <li><img class="svg" src="img/iconos/icon_temporada.svg">Fuerza<img class="svg help" src="img/iconos/icon_ayuda.svg"></li>
                          <li><img class="svg" src="img/iconos/icon_cronometro.svg">${parseNumberToHoursNoExcedent(dato.minutos, 0)}<img class="svg help" src="img/iconos/icon_ayuda.svg"></li>
                          <li><img class="svg" src="img/iconos/icon_km.svg">${dato.distancia}<img class="svg help" src="img/iconos/icon_ayuda.svg"></li>
                        </ul>
                        ${data}
                      </div>
                    </div>`
                );
            }
        });
    }
    if(timessemit==1){
        owlCarouselSemanal();
    }
    if (document.querySelector('.owl-dots')) {
        document.querySelector('.owl-dots').classList.add('hidden');
    }

    //Seteando num Semana y Mes en la vista semana
    $('#weekMonth').html(`${'Semana '+(Number(sIx)+1)} - <span>${meses[semanaFechaInicio.getMonth()]}</span>`);
    //Event swipe's owl carousel
    const owl = $('.owl-carousel');
    owl.owlCarousel();
    // Listen to owl events:
    owl.on('changed.owl.carousel', function(event) {
        setTimeout(()=>{
            if(!event.target.querySelector('.owl-item.active')){
                return;
            }
            if(!event.target.querySelector('.owl-item.active').firstElementChild){
                return;
            }
            $('#range').val(
                Number(event.target.querySelector('.owl-item.active').firstElementChild.getAttribute('data-six'))
            )
        }, 100)
    });
    miniPanelActive();
}

function elementosWeek(dato, diaIndex) {
    var elementoDia = dato.elementos;
    var texto = "";

    if (elementoDia != null && elementoDia.length > 0){
        $.each(elementoDia, function (i, dato) {
            texto= texto.concat(`
            <div class="mini-panel" data-dia-index="${diaIndex}"><span>${dato.nombre}<img class="svg help" src="${_ctx}img/iconos/icon_flecha2.svg"></span>
              <div class="time"><img class="svg help" src="${_ctx}img/iconos/icon_horas.svg">${parseNumberToHoursNoExcedent(dato.minutos, 0)}</div>
            </div>
            `);
        });
    }
    return texto;
}

//@DEPRECATED
function showSubElementos(dato) {
    var subElemento = dato.subElementos;
    var texto = "";

    if (subElemento != null && subElemento.length > 0){
        $.each(subElemento, function (i, dato) {

            if(dato.mediaVideo != null) {
                texto= texto.concat(`
                    <li>
                        <img class="svg" src="img/iconos/icon_videoteca.svg">
                      <a data-fancybox="gallery" href="https://s3-us-west-2.amazonaws.com/rf-media-rutina/video${dato.mediaVideo}">
                        <p class="title">${dato.nombre}<span>${dato.nota ? dato.nota : ""}</span></p>
                      </a>
                    </li>
                `);
            } else {
                texto= texto.concat(
                    `<li>
                        <img class="svg" src="img/iconos/icon_microfono.svg">
                      <p class="title">${dato.nombre}<span>${dato.nota ? dato.nota : ""}</span></p>
                    </li>`);
            }
        });
    }
    return texto;
}

function pulsosRitmos() {
    $("#panel_days .panel:first .panel-heading.day a").trigger("click");
}

async function getDatosDeLaUltimaRutina(){

    return new Promise((resolve, reject)=>{
        $.ajax({
            type: 'GET',
            url: _ctx + 'cliente/get/ultima-rutina',
            dataType: "json",
            success: function (data, textStatus) {
                resolve(data);
                if (data.totalSemanas === 0) {
                    sinRutina()
                    return;
                }
                if (textStatus == "success") {
                    if (data == "-9") {
                        $.smallBox({
                            content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                            timeout: 4500,
                            color: "alert",
                        });
                    }
                }
            },
            error: function (xhr) {
                reject(xhr.status);
            },
            complete: function () {

            }
        });
    })
}

function getSemanaIndice(fechaInicio, fechaFin){
    const today = parseFromStringToDate(getFechaFormatoString(new Date()));
    const fin = parseFromStringToDate2(fechaFin);
    const diffDaysFfin = moment(fin).diff(today, 'days');
    if(diffDaysFfin<0){
        return 0;//Si es negativo quiere decir que la rutina ya ha culminado por ende siempre se devolverá la primera semana
    }
    const init = parseFromStringToDate2(fechaInicio);
    const diffDays = moment(today).diff(init, 'days');
    let weekIndex = 0;
    if(diffDays > 0){
        weekIndex = Math.ceil(diffDays/7);
        if(diffDays%7 === 0){
            weekIndex +=1;//representa el ceil para los lunes
        }else{
            weekIndex -=1;
        }
    }
    $gSemanaIx = weekIndex;
    return weekIndex;
}

function sinRutina(){
    document.querySelector('.rutina-cli').classList.add('hidden');
    $(document.querySelector('.wildcard-message')).show(300);
    document.querySelector('.nav-tabs').style.visibility = 'hidden';

}
