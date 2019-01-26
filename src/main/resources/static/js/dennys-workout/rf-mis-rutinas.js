const $semanario = document.querySelector('#RutinaSemana');
const diaIndex = 0;
let $miniId;
let indexGlobal = 0;

MiniRutina = (function(){
    return {
        full: (elementos)=>{//init se usa para cuando se recrea la semana desde la instancia del objeto Rutina
                return `<div class="widget-body padding-o-bottom-40">
                            <!-- smart-accordion-default -->
                            <div class="panel-group smart-accordion-default rf-listas padding-5" style="border: 2px solid deepskyblue; border-radius: 10px">
                                <!-- LISTAS DEL DIA DE SEMANA -->
                                    ${newElementosFromMiniPlantilla(elementos)}
                                <!-- END LISTAS DEL DIA DE SEMANA -->
                            </div>
                        </div>`
        }
    }
})();
function newElementosFromMiniPlantilla(elementos){
    let elementosHTML = '';
    if(elementos != null) {
        elementos.forEach((ele, i)=> {
            let ix = ++indexGlobal;
            elementosHTML += ele.tipo == 1 ? elementoSimplePaste(ele, ix, i) : elementoCompuestoPaste(ele, ix, i);
        });
    }
    return elementosHTML;
}
function elementoSimplePaste(ele, ix, numeracion){
    const ess = obtenerEstilos(ele.estilos);
    return `<div class="panel panel-default rf-dia-elemento ${ess.margen}" data-index="${ix}" data-type="${ele.tipo}" data-kms="${ele.distancia}" style="border: none;">
                        <div class="panel-heading">
                            <h4 class="panel-title txt-color-blue">
                                <a href="javascrip:void(0);" class="${ess.header}">
                                    <i class="fa fa-lg fa-angle-down pull-right invisible"></i>
                                    <i class="fa fa-lg fa-angle-up pull-right invisible"></i>  
                                    <span class="txt-color-black lista-title">
                                        <span data-index="${numeracion+1}" class="badge badge-custom-enum font-md">${numeracion+1}</span>
                                        <span class="">
                                            ${ele.mediaVideo != undefined?iconoVideo(ele.mediaVideo):''}
                                            ${ele.mediaAudio != undefined?iconoAudio(ele.mediaAudio):''}
                                        </span>
                                        <span class="rf-dia-elemento-nombre padding-10 ${ess.base}" data-dia-index="${diaIndex}" contenteditable="true" data-index="${ix}" data-placement="bottom" data-toggle="popover" data-content="${ele.nota != undefined? ele.nota : ''}" data-trigger="hover">${ele.nombre}</span>
                                        <input value="${ele.minutos}" type="number" maxlength="3" class="pull-right agregar-tiempo" data-index="${ix}" data-placement="top" rel="tooltip" data-original-title="Añadir tiempo en minutos"/>  
                                    </span>
                                </a>
                                ${iconoNota(ele.nota)}
                            </h4>
                        </div>
                    </div>`;
}

function elementoCompuestoPaste(ele, ix, numeracion){
    const ess = obtenerEstilos(ele.estilos);
    let classInputsInitSubEle = ele.subElementos.length == 0?'':'hidden';

    return `<div class="panel panel-default rf-dia-elemento ${ess.margen}" data-index="${ix}" data-type="${ele.tipo}" data-kms="${ele.distancia}" style="border: none">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${ix}" class="collapsed ${ess.header}" aria-expanded="false">
                                        <i class="fa fa-lg fa-angle-down pull-right text-primary"></i> 
                                        <i class="fa fa-lg fa-angle-up pull-right text-primary"></i>
                                        <span class="txt-color-blue lista-title">
                                            <span data-index="${numeracion+1}" class="badge badge-custom-enum font-md">${numeracion+1}</span>
                                            <span class="">                                         
                                                ${ele.mediaVideo != undefined? iconoVideo(ele.mediaVideo):''}
                                                ${ele.mediaAudio != undefined? iconoAudio(ele.mediaAudio):''}                                                
                                            </span>
                                            <span class="rf-dia-elemento-nombre padding-10 ${ess.base}" data-dia-index="${diaIndex}" data-index="${ix}" contenteditable="true" data-placement="bottom" data-toggle="popover" data-content="${ele.nota != undefined? ele.nota :''}" data-trigger="hover">${ele.nombre}</span>
                                            <input value="${ele.minutos}" type="number" maxlength="3" class="pull-right agregar-tiempo" data-index="${ix}" data-placement="top" rel="tooltip" data-original-title="Añadir tiempo en minutos"/>
                                        </span>
                                    </a>
                                    ${iconoNota(ele.nota)}
                            </h4>
                        </div>
                        <div id="collapse${ix}" class="panel-collapse collapse" aria-expanded="false">
                            <div class="panel-body">
                                <div class="smart-form"><label class="input padding-5"><input disabled="disabled" data-ele-index="${ix}" class="in-sub-elemento in-init-sub-ele ${classInputsInitSubEle}" type="text" maxlength="44" placeholder=""></label></div>
                                <div class="modulo-detalle">
                                    <div class="dd nestable">
                                        <ol class="dd-list detalle-lista">
                                            ${subElementosPaste(ele.subElementos, ix)}
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

function iconoVideo (mediaVideo){
    return `<i data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-video i-btn fa fa-video-camera fa-fw padding-top-3 rf-media" data-media="${mediaVideo}"></i>`;
}
function iconoAudio(mediaAudio){
    return `<i data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-audio i-btn fa fa-play fa-fw padding-top-3 rf-media" data-media="${mediaAudio}"></i>`;
}
function iconoNota(nota){
    return `${nota != '' && nota != undefined?'<i class="fa fa-minus check-nota agregar-nota" data-index="${ixs.eleIndex}" data-dia-index="${ixs.diaIndex}"></i>':''}`;
}

function subElementosPaste(subElementos, eleIndex){
    let subElementosHTML = '';

    subElementos.forEach(sEle=>{
        let ix = ++indexGlobal;
        subElementosHTML += `
                    <li class="dd-item rf-sub-elemento" data-index="${ix}" data-type="${sEle.tipo}">
                       <div class="col-md-12">
                           <span class="">
                                ${sEle.mediaVideo != undefined && sEle.mediaVideo != null && sEle.mediaVideo != "" ?iconoVideo(sEle.mediaVideo):''}
                                ${sEle.mediaAudio != undefined && sEle.mediaAudio != null && sEle.mediaAudio != "" ?iconoAudio(sEle.mediaAudio):''}
                           </span>
                           <span class="rf-sub-elemento-nombre padding-10" data-ele-index="${eleIndex}" data-index="${ix}" contenteditable="true" data-placement="bottom" data-toggle="popover" data-content="${sEle.nota != undefined? sEle.nota :''}" data-trigger="hover">${sEle.nombre}</span>
                           
                           ${iconoNota(sEle.nota)}
                       </div>																			
                    </li>
                    `;
    });
    return subElementosHTML;
}

function instanciarMiniRutina(mini, titulo) {
    return html =
            `<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12 rf-dia" data-index="0" id="MainMini">            
									<div class="jarviswidget jarviswidget-color-blueLight margin-bottom-0" tabindex='0'>
										<header role="heading" class="heading-dia">
					                        <span class="widget-icon pull-right" style="padding-right: 35px;"><i class="fa fa-fw badge badge"><b>${mini.nivel}</b></i></span>
								            <span class="widget-icon pull-right" style="padding-right: 35px;"><i class="fa fa-heart txt-color-red" data-index="0"></i></span>
								            <h2 class="titulo-dia">${capitalizeFirstLetter(titulo)}</h2>							             
								        </header>
								        <div role="heading" class="padding-10 header-mini" style="border-bottom: 0px; padding-bottom: 0px !important;">
								            <div class="col-sm-12">
								                <div class="row"><i class="fa fa-fw fa-15x fa-child"></i><span class="header-title">Carrera</span></div>
								                <div class="row"><i class="fa fa-fw fa-15x fa-line-chart"></i><span class="header-title">Fuerza</span></div>
								                <div class="row"><i class="fa fa-fw fa-15x fa-clock-o"></i><span class="header-title">${parseNumberToHours(mini.minutos)}</span></div>
								                <div class="row"><i class="fa fa-fw fa-15x txt-color-blue" style="margin-right: 13px">KM</i><span class="header-title">${parseNumberToDecimal(mini.distancia, 1)}</span></div>
								            </div>					    		
								        </div>
								        <!-- widget div-->
								        <div class="padding-0">
								            <!-- widget content -->	
								            ${MiniRutina.full(mini.elementos)}
								            <!-- end widget content -->											
								        </div>
								        <!-- end widget div -->
									</div>
								</article>`;
}




