
const $semanario = document.querySelector('#RutinaSemana');

function mostrarSemana(sem, sIndex, diasemanaactual){
    //Inicialmente generamos solo la primera semana de todo el plan de entrenamiento
    let semana = sem;
    let rawDias = '';
    let diasRestantes = [];
    let rawDiasRestantes = '';

    if (!semana.flagFull) {
        let iter = 7 - semana.lstDia.length;
        if(sIndex == 0){
            for (let i = 0; i < iter; i++) {
                let dt = parseFromStringToDate(moment(new Date(semana.lstDia[0].fecha)).subtract(i + 1, 'days').format('YYYY-MM-DD'));
                diasRestantes.push(dt);
            }
            //Invertimos el arreglo para obtenerlo correctamente de menor a mayor fecha
            diasRestantes.reverse();
        }else{
            for (var i = 0; i < vacios; i++) {
                let dt = parseFromStringToDate(moment(new Date(semana.lstDia[semana.lstDia.length - 1].fecha)).add(i + 1, 'd').format('YYYY-MM-DD'));
                diasRestantes.push(dt);
            }
        }

        diasRestantes.forEach((v) => {
            v.literal = dias[v.getDay()];//Constante from jsfuente.js
            v.dia = v.getDate();
            rawDiasRestantes +=
                `<article class="col-xs-12 col-sm-3 col-md-3 col-lg-3 rf-dia-off" >            
									<div class="jarviswidget jarviswidget-color-blueLight margin-bottom-0">
										<header role="heading">
								            <h2>${v.literal} ${v.dia}</h2>
								            ${!semana.flagFull ?
                    `<div class="widget-toolbar borderless" onclick="focoARutina();">
								            			<a href="javascript:void(0);" rel="tooltip" data-placement="bottom" data-original-title="Avanzar hasta la rutina del primer día"><i class="fa fa-arrow-right fa-15x"></i></a>
								            		 </div>` : ''}
                                                     </header>
                                                     <!-- widget div-->
                                                     <div class="rf-colum-diff">
                                                         <!-- widget content -->
                                                         <div class="widget-body">
                                                             <!-- smart-accordion-default -->
                                                             <div class="panel-group smart-accordion-default">
                                                             <!-- DIAS DE SEMANA -->
                                                                 <h1 class="text-align-center">${sIndex != 0 ? `<img class="max-wd" src="${_ctx}img/finish_strong.png"/>` : `<img class="max-wd" src="${_ctx}img/empieza_reto.jpg"/>`}</h1>
                                                                 <!-- END DIAS DE SEMANA -->
                                                             </div>													
                                                         </div>
                                                         <!-- end widget content -->											
                                                     </div>
                                                     <!-- end widget div -->
									</div>
								</article>`;
        });
    }
    rawDiasRestantes = '';

    var dayEncontrado = [];
    $.each(semana.lstDia ,function (i,item) {
        if(diasemanaactual == item.dia){
            dayEncontrado.push(item);
        }
    });

    //semana.lstDia = dayEncontrado;

    rawDias = armarHtmlDayEncontrado(dayEncontrado);

    let RutinaSemanaDiv = document.createElement('div');
    //Ccn esta condición nos aseguramos que siempre que la primera semana de toda la rutina empiece por un día diferente a martes, los primeros días vacíos vayan al inicio
    if (!semana.flagFull && sIndex == 0){
        rawDiasRestantes += rawDias;
        RutinaSemanaDiv.innerHTML = rawDiasRestantes;
    }else{
        rawDias += rawDiasRestantes;
        RutinaSemanaDiv.innerHTML = rawDias;
    }

    $semanario.innerHTML = '';
    $semanario.append(RutinaSemanaDiv);

    if(semana.metricas != null && semana.metricas != "") {
        $("#Metricas").html("");
        let strMetricas = Metricas(JSON.parse(semana.metricas));
        $("#stMetricas").append(strMetricas);
    }


    //Seteamos un ancho especifico de acuerdo a los 7 días de la semana para poder hacer un scrolling en X
    //$(".of_carousel").width($(".of_carousel article").length * $(".of_carousel article")[0].clientWidth) + $(".of_carousel article").length * $(".of_carousel article")[0].clientWidth * 2;
    //$('#RutinaSemana').attr('data-index', sIndex);
    $('[data-toggle="popover"]').popover();
}

RutinaDiaHTML = (function(){
    return {
        full: (elementos, diaIndex, init, flagDescanso)=>{//init se usa para cuando se recrea la semana desde la instancia del objeto Rutina
            if(flagDescanso){
                return `<div class="widget-body padding-o-bottom-40"><img src="${_ctx}img/dia-libre.png" style="max-width: 90%; text-align: center;"></div>`;
            }else {
                return `<div class="widget-body padding-o-bottom-40">
                            <div class="container-fluid form-group margin-bottom-10 padding-5 inputs-init ${init == undefined ? 'hidden' : ''}">
                                <div class="smart-form">
                                    <div class="form-group">
                                            <label class="input col-md-6 col-sm-12 col-xs-12">
                                                <input class="in-ele-dia-1 in-init-ele" type="text" maxlength="121" placeholder="" data-dia-index="${diaIndex}">
                                                </label>
                                            <label class="input col-md-6 col-sm-12 col-xs-12">
                                                <input class="in-ele-dia-2 in-init-ele" type="text" maxlength="121" placeholder="" data-dia-index="${diaIndex}">
                                                </label>
                                            <em class="txt-color-redLight" id="msg-val-${diaIndex}"></em>
                                    </div>
                                </div>
                            </div>
                            <!-- smart-accordion-default -->
                            <div class="panel-group smart-accordion-default rf-listas padding-5">
                                <!-- LISTAS DEL DIA DE SEMANA -->
                                    ${newElementosFromMiniPlantilla(elementos, diaIndex)}
                                <!-- END LISTAS DEL DIA DE SEMANA -->
                            </div> 
                            <div class="panel-group smart-accordion-default rf-listas padding-5" id="stMetricas">
                                <!-- LISTAS DEL DIA DE SEMANA -->
                                    
                                <!-- END LISTAS DEL DIA DE SEMANA -->
                            </div> 
                        </div>`
            }
        },
    }
})();
function newElementosFromMiniPlantilla(elementos, diaIndex){
    let elementosHTML = '';
    if(elementos != null) {
        elementos.forEach(ele => {
            elementosHTML += ele.tipo == 1 ? elementoSimplePaste(ele, diaIndex) : elementoCompuestoPaste(ele, diaIndex);
        });

    }
    return elementosHTML;
}
function elementoSimplePaste(ele, diaIndex){
    const ess = obtenerEstilos(ele.estilos);
    let posPopover = positionPopoverByDiaIndex(diaIndex);
    let ix = ++indexGlobal;

    return `<div class="panel panel-default rf-dia-elemento ${ess.margen}" data-index="${ix}" data-type="${ele.tipo}" data-kms="${ele.distancia}">
                        <div class="panel-heading">
                            <h4 class="panel-title txt-color-blue">
                                <a href="javascrip:void(0);" class="${ess.header}">
                                    <i class="fa fa-lg fa-angle-down pull-right text-primary"></i> 
                                    <span class="txt-color-black lista-title">
                                        <span class="pull-left">
                                            ${ele.mediaVideo != undefined?iconoVideo(ele.mediaVideo):''}
                                            ${ele.mediaAudio != undefined?iconoAudio(ele.mediaAudio):''}
                                            
                                            
                                        </span>
                                        <span class="rf-dia-elemento-nombre padding-10 ${ess.base}" data-index="${ix}" data-dia-index="${diaIndex}" data-placement="bottom" data-toggle="popover" data-content="${ele.nota != undefined? ele.nota : ''}" data-trigger="hover">${ele.nombre}</span>
                                        <input disabled="disabled" value="${ele.minutos}" type="number" maxlength="3" class="pull-right agregar-tiempo" data-index="${ix}" data-dia-index="${diaIndex}" data-placement="top" rel="tooltip" data-original-title="Añadir tiempo en minutos"/>  
                                    </span>
                                </a>
                                ${iconoNota(ele.nota)}
                            </h4>
                        </div>
                    </div>`;

}
function elementoCompuestoPaste(ele, diaIndex){
    const ess = obtenerEstilos(ele.estilos);
    let classInputsInitSubEle = ele.subElementos.length == 0?'':'hidden';
    let ix = ++indexGlobal;

    let posPopover = positionPopoverByDiaIndex(diaIndex);
    return `<div class="panel panel-default rf-dia-elemento ${ess.margen}" data-index="${ix}" data-type="${ele.tipo}" data-kms="${ele.distancia}">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${ix}" class="${ess.header}">
                                        <i class="fa fa-lg fa-angle-down pull-right text-primary"></i> 
                                        <i class="fa fa-lg fa-angle-up pull-right text-primary"></i>
                                        <span class="txt-color-blue lista-title">
                                            <span class="pull-left">                                         
                                                ${ele.mediaVideo != undefined? iconoVideo(ele.mediaVideo):''}
                                                ${ele.mediaAudio != undefined? iconoAudio(ele.mediaAudio):''}                                                
                                            </span>
                                            <span class="rf-dia-elemento-nombre padding-10 ${ess.base}" data-index="${ix}" data-dia-index="${diaIndex}" data-placement="bottom" data-toggle="popover" data-content="${ele.nota != undefined? ele.nota :''}" data-trigger="hover">${ele.nombre}</span>
                                            <input disabled="disabled" value="${ele.minutos}" type="number" maxlength="3" class="pull-right agregar-tiempo" data-index="${ix}" data-dia-index="${diaIndex}" data-placement="top" rel="tooltip" data-original-title="Añadir tiempo en minutos"/>
                                        </span>
                                    </a>
                                    ${iconoNota(ele.nota)}
                            </h4>
                        </div>
                        <div id="collapse${ix}" class="panel-collapse collapse in">
                            <div class="panel-body">
                                <div class="smart-form"><label class="input padding-5"><input disabled="disabled" data-ele-index="${ix}" data-dia-index="${diaIndex}" class="in-sub-elemento in-init-sub-ele ${classInputsInitSubEle}" type="text" maxlength="44" placeholder=""></label></div>
                                <div class="modulo-detalle">
                                    <div class="dd nestable">
                                        <ol class="dd-list detalle-lista">
                                            ${ subElementosPaste(ele.subElementos, diaIndex, ix)}
                                        </ol>
                                    </div>
                                </div>
                            </div>
                        </div>
                     </div>`;
}
function obtenerEstilos(ess){
    let  estHeader = "", estElem = "", margen = "";
    for(let i=0; i<ess.length;i++){
        switch(ess[i].tipo) {
            case 1:
                estElem += ess[i].clase + " ";
                break;
            case 2:
                estHeader += ess[i].clase + " ";
                break;
            default:
                margen += ess[i].clase + " "
        }
    }
    return {header: estHeader, base: estElem, margen: margen};
}
function positionPopoverByDiaIndex(dIx){
    return dIx == "0" ? "right" : "bottom";
}
function iconoVideo (mediaVideo){
    //return `<a style="padding: 0px !important;" href="javascript:VerVideo('${mediaVideo}')" data-video="${mediaVideo}"><i  data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-video fa fa-video-camera fa-fw padding-top-3 rf-media" data-media="${mediaVideo}"></i></a>`
    return `<i data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-video fa fa-video-camera fa-fw padding-top-3 rf-media" data-media="${mediaVideo}"></i>`;
}
function iconoAudio(mediaAudio){
    //return `<a style="padding: 0px !important;" href="javascript:VerAudio('${mediaAudio}')" data-audio="${mediaAudio}"><i  data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-audio fa fa-play fa-fw padding-top-3 rf-media" data-media="${mediaAudio}"></i></a>`
    return `<i data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-audio fa fa-play fa-fw padding-top-3 rf-media" data-media="${mediaAudio}"></i>`;
}
function iconoNota(nota){
    return `${nota != '' && nota != undefined?'<i class="fa fa-minus check-nota agregar-nota" data-index="${ixs.eleIndex}" data-dia-index="${ixs.diaIndex}"></i>':''}`;
}
function opsPopoverElemento(diaIndex, eleIndex){
    return `
                <div class='row ops-r'>
                    <i rel='tooltip' data-original-title='Agregar nota' data-trigger='hover' class='fa fa-sticky-note agregar-nota txt-color-yellow padding-5' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Eliminar audio' data-trigger='hover' class='fa fa-music txt-color-red padding-5 trash-audio' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Eliminar video' data-trigger='hover' class='fa fa-video-camera txt-color-red padding-5 trash-video' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Insertar encima del elemento' data-trigger='hover' class='fa fa-arrow-up txt-color-black padding-5 insertar-encima' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Reemplazar elemento' data-trigger='hover' class='fa fa-refresh txt-color-green padding-5' data-index='${eleIndex}'></i>
                    <span rel='tooltip' data-original-title='Agregar KM's data-trigger='hover' class='txt-color-black padding-5 agregar-kms' data-index='${eleIndex}' data-dia-index='${diaIndex}'>KM</span>
                    <i rel='tooltip' data-original-title='Eliminar elemento' data-trigger='hover' class='fa fa-trash-o txt-color-redLight padding-5 trash-elemento' data-dia-index='${diaIndex}' data-index='${eleIndex}'></i>
                </div>
            `;
    }
function opsPopoverElemento2(diaIndex, eleIndex){
    return `
                <div class='row ops-r'>
                    <i rel='tooltip' data-original-title='Agregar nota' data-trigger='hover' class='fa fa-sticky-note agregar-nota txt-color-yellow padding-5' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Eliminar audio' data-trigger='hover' class='fa fa-music txt-color-red padding-5 trash-audio' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Eliminar video' data-trigger='hover' class='fa fa-video-camera txt-color-red padding-5 trash-video' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Insertar encima del elemento' data-trigger='hover' class='fa fa-arrow-up txt-color-black padding-5 insertar-encima' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Reemplazar elemento' data-trigger='hover' class='fa fa-refresh txt-color-green padding-5' data-index='${eleIndex}'></i>
                    <i rel='tooltip' data-original-title='Agregar varios' data-trigger='hover' class='fa fa-angle-double-down padding-5 varios-media' data-dia-index='${diaIndex}' data-index='${eleIndex}'></i>
                    <span rel='tooltip' data-original-title='Agregar KM's data-trigger='hover' class='txt-color-black padding-5 agregar-kms' data-index='${eleIndex}' data-dia-index='${diaIndex}'>KM</span>
                    <i rel='tooltip' data-original-title='Eliminar elemento' data-placement='top' data-dia-index='${diaIndex}' data-index='${eleIndex}' class='fa fa-trash padding-5 txt-color-redLight trash-elemento'></i>
                </div>
            `;
}
function subElementosPaste(subElementos, diaIndex, eleIndex){
    let posPopover = positionPopoverByDiaIndex(diaIndex);
    let subElementosHTML = '';

    subElementos.forEach(sEle=>{
        let ix = ++indexGlobal;
        subElementosHTML += `
                    <li class="dd-item rf-sub-elemento" data-index="${ix}" data-type="${sEle.tipo}">
                       <div class="col-md-12">
                           <span class="pull-left">
                                ${sEle.mediaVideo != undefined && sEle.mediaVideo != null && sEle.mediaVideo != "" ?iconoVideo(sEle.mediaVideo):''}
                                ${sEle.mediaAudio != undefined && sEle.mediaAudio != null && sEle.mediaAudio != "" ?iconoAudio(sEle.mediaAudio):''}
                           </span>
                           <span class="rf-sub-elemento-nombre padding-10" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}" data-placement="bottom" data-toggle="popover" data-content="${sEle.nota != undefined? sEle.nota :''}" data-trigger="hover">${sEle.nombre}</span>
                           
                           ${iconoNota(sEle.nota)}
                       </div>																			
                    </li>
                    `;
    });
    return subElementosHTML;
}
function positionPopoverByDiaIndex(dIx){
    return dIx == "0"?"right":"right";
}
function opsPopoverSubElemento(diaIndex, eleIndex, subEleIndex){
    return `
                <div class='row'>
                    <i rel='tooltip' data-original-title='Agregar nota' data-trigger='hover' class='fa fa-sticky-note agregar-nota-sbe txt-color-yellow padding-5' data-dia-index='${diaIndex}' data-ele-index='${eleIndex}' data-index='${subEleIndex}'></i>
                    <i rel='tooltip' data-original-title='Eliminar audio' data-trigger='hover' class='fa fa-music txt-color-red padding-5 trash-audio-sub' data-dia-index='${diaIndex}' data-ele-index='${eleIndex}' data-index='${subEleIndex}'></i>
                    <i rel='tooltip' data-original-title='Eliminar video' data-trigger='hover' class='fa fa-video-camera txt-color-red padding-5 trash-video-sub' data-dia-index='${diaIndex}' data-ele-index='${eleIndex}' data-index='${subEleIndex}'></i>
                    <i rel='tooltip' data-original-title='Insertar encima del sub elemento' data-trigger='hover' class='fa fa-arrow-up txt-color-black padding-5 insertar-encima-sub' data-dia-index='${diaIndex}' data-ele-index='${eleIndex}' data-index='${subEleIndex}'></i>
                    <i rel='tooltip' data-original-title='Reemplazar sub elemento' data-trigger='hover' class='fa fa-refresh txt-color-green padding-5' data-dia-index='${diaIndex}' data-ele-index='${eleIndex}' data-index='${subEleIndex}'></i>
                    <i rel='tooltip' data-original-title='Eliminar sub elemento' data-trigger='hover' class='fa fa-trash-o txt-color-redLight padding-5 trash-sub-elemento' data-dia-index='${diaIndex}' data-ele-index='${eleIndex}' data-index='${subEleIndex}'></i>
                </div>
            `
}
function Metricas(metricas){
    const raw = `
                <div class="container-fluid padding-0 its-indicador-1">
                    <div class="col-md-6 col-sm-6 col-xs-6">
                        <div class="row padding-5 text-align-center">
                            <span class="txt-color-blue"><b>Paso</b></span>
                        </div>
                        ${indicador1Body(metricas, 1)}
                    </div>
                    <div class="col-md-6 col-sm-6 col-xs-6">
                        <div class="row padding-5 text-align-center">
                            <span class="txt-color-red"><b>Pulso</b></span>
                        </div>
                        ${indicador1Body(metricas, 2)}
                    </div>
                </div>
            `

    return raw;
}
function indicador1Body(metricas, t) {
    const iteraciones = metricas.length;
    let raw = '';
    const claseTipo = t == 2 ? 'txt-color-red':'';
    const bOpacidad = 1/iteraciones;
    for(let i=0; i<iteraciones;i++){
        raw += `<div class="col-md-11 col-sm-11 col-xs-11 padding-o-bottom-5 text-align-center">
                            <div class="col-md-1 col-sm-1 col-xs-1 padding-0 rf-n">
                                <a href="javascript:void(0);">${metricas[i].nombre}</a>
                            </div>
                            <div class="col-md-2 col-sm-2 col-xs-2 padding-0">
                                <a href="javascript:void(0);"><i class="fa fa-long-arrow-up ${claseTipo}" style="opacity: ${bOpacidad*(i+1)}"></i></a>
                            </div>
                            <div class="col-md-9 col-sm-9 col-xs-9 padding-0">
                                ${t == 1 ?
            `<b> ${i == 0 ? metricas[i].indicadores.max.substr(3) : i == 6 ? metricas[i].indicadores.min.substr(3): metricas[i].indicadores.max.substr(3) +' - '+ metricas[i].indicadores.min.substr(3)}</b>`
            :`<b> ${metricas[i].min} - ${metricas[i].max}</b>`
            }
                            </div>                            
                        </div>`
    }
    return raw;
}

function armarHtmlDayEncontrado(dayEncontrado) {
    let rawDias = '';
    dayEncontrado.forEach((v, i) => {
        const val = v.elementos == null ? undefined : (v.elementos.length>0?undefined:'showInputsInit');//La validacion requiere un null;
        const flagDescanso = v.flagDescanso;
        rawDias +=
            `<article class="col-xs-12 col-sm-3 col-md-3 col-lg-3 rf-dia" data-index="${i}" data-fecha="${v.fecha}">            
									<div class="jarviswidget jarviswidget-color-blueLight margin-bottom-0" tabindex='${i}'>
										<header role="heading" class="heading-dia">
								            <span class="widget-icon"><i class="fa fa-calendar-o txt-color-blue copiar-dia" data-index="${i}"></i></span>
								            <h2 class="titulo-dia">${v.literal} ${v.dia}</h2>								             
								        </header>
								        <div role="heading">
								            <div class="col-sm-12">
								            	<i class="fa fa-clock-o fa-fw pull-left fa-15x"></i><span class="pull-left horas-totales padding-bottom-10">${parseNumberToHours(v.minutos)}</span>
								            	<span class="pull-right fa fa-fw txt-color-blue fa-15x margin-right-13" style="font-family: 'Open Sans',Arial,Helvetica,Sans-Serif">KM</span><span class="pull-right distancia-total padding-bottom-10 margin-right-5">${parseNumberToDecimal(v.distancia, 2)}</span>
								            </div>					    		
								        </div>
								        <!-- widget div-->
								        <div class="padding-0">
								            <!-- widget content -->	
								            ${RutinaDiaHTML.full(v.elementos, i, val, flagDescanso)}
								            <!-- end widget content -->											
								        </div>
								        <!-- end widget div -->
									</div>
								</article>`;
    });

    return rawDias;
}





