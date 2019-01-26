const viewList = document.querySelector('#view_list');
const viewRegister = document.querySelector('#view_register');
const divRutina = document.querySelector('#divRutina');
const divRegistros = document.querySelector('#dvMisRutinas');
let $nombreActualizar = '';
let $minis;
let $nvl = 1;
$(function () {
    init();
})

function init() {

    obtenerCategoriasIdsByUsuario();
    viewList.addEventListener('click', principalesViewList);
    viewRegister.addEventListener('click', principalesViewRegister);
    viewRegister.addEventListener('focusout', principalesFocusOutViewRegister);

}

function principalesViewList(e){
    const input = e.target;
    const clases = e.target.classList;

    if(clases.contains('img-cat-rutina')){
        const catId = input.getAttribute('data-index');
        const nomCat = input.nextElementSibling.textContent.trim();
        obtenerMisRutinasByCategoria(catId, nomCat);
    }else if(clases.contains('span-center-on-img') && !clases.contains('span-disabled')){
        const catId = input.previousElementSibling.getAttribute('data-index');
        const nomCat = input.textContent.trim();
        obtenerMisRutinasByCategoria(catId, nomCat);
    }
}

function principalesViewRegister(e){
    const input = e.target;
    const clases = e.target.classList;

    if(clases.contains('mini-rutina')){
        const hash = input.getAttribute('data-index');
        obtenerMiniRutinaByHash(hash, input);
    }
    else if(clases.contains('lbl-btn-buscar-mini')){
        const queryValue = input.previousElementSibling.value;
        const tempMinis = JSON.parse(JSON.stringify($minis));
        customPaginacion(tempMinis.filter(v => v.nvl == $nvl && v.nombre.toLowerCase().includes(queryValue.toLowerCase())), divRegistros, 0, 0);
    }
    else if(clases.contains('badge-custom-nvl')){
        input.parentElement.parentElement.querySelectorAll('.badge-custom-nvl').forEach(v=>v.classList.remove('opacity'));
        clases.add('opacity');
        $nvl = Number(input.getAttribute('data-index'));
        const tempMinis = JSON.parse(JSON.stringify($minis));
        customPaginacion(tempMinis.filter(v => v.nvl == $nvl), divRegistros, 0, 0);
    }
    else if(clases.contains('back-listado')){
        $nvl = 1;
    }
    else if(clases.contains('page-number')){
        Number(input.textContent.trim());
        const tempMinis = JSON.parse(JSON.stringify($minis));
        customPaginacion(tempMinis.filter(v => v.nvl == $nvl), divRegistros, Number(input.textContent.trim())-1, Number(input.textContent.trim())-1);
    }
    else if(clases.contains('rf-dia-elemento-nombre')){
        e.preventDefault();
        e.stopPropagation();
        const valor = input.textContent.trim();
        //Sirve para despues de guardar el valor del input en el onclick validar que este haya sido o no modificado para conforme a eso actualizar en el focusout
        //o evitar actualizaciones innecesarias
        $nombreActualizar = valor;
    }
    else if(clases.contains('rf-sub-elemento-nombre')){
        e.preventDefault();
        e.stopPropagation();
        const valor = input.textContent.trim();
        //Sirve para despues de guardar el valor del input en el onclick validar que este haya sido o no modificado para conforme a eso actualizar en el focusout
        //o evitar actualizaciones innecesarias
        $nombreActualizar = valor;
    }
    else if(clases.contains('reprod-audio')){
        e.preventDefault();
        e.stopPropagation();
        if(clases.contains('fa-pause-circle')){
            document.querySelector('#someaud').pause();
            e.target.setAttribute('data-original-title','Reproducir');
        }else{
            const route = e.target.getAttribute('data-media');
            $('#AudioReproduccion').get(0).src = `${_ctx}workout/media/audio${route}`;
            $("#AudioReproduccion").parent().get(0).load();
            e.target.setAttribute('data-original-title','Pausar');
        }
        clases.toggle('fa-music');
        clases.toggle('fa-pause-circle');
    }
    else if(clases.contains('reprod-video')){
        e.stopPropagation();
        e.preventDefault();
        ElementoOpc.reproducirVideo(input);
    }
}

function principalesFocusOutViewRegister(e){
    const input = e.target;
    const clases = e.target.classList;

    if(clases.contains('rf-dia-elemento-nombre')){
        const valor = input.textContent.trim();
        if($nombreActualizar != valor){
            const ixs = RutinaIx.getIxsForElemento(input);
            if(valor.length == 0){
               ElementoOpc.confirmarEliminarElemento(ixs.numSem, ixs.diaIndex, ixs.eleIndex);
            }else{
                //1. Buscamos la posicion de la lista y con ello cambiamos el nombre en el programa general
                let tempEle = RutinaDOMQueries.getElementoByIxs(ixs), i=0;
                while((tempEle = tempEle.previousElementSibling) != null) i++;
                RutinaSet.setElementoNombre(ixs.numSem, ixs.diaIndex, (posEle = i), valor);
                //Indices con respecto a la posicion en la que se encuentran con respecto a su contenedor padre y sus hermanos
                DiaOpc.validPreActualizarFromNomEle(valor, ixs, (posElemento = i));
            }
        }
        $nombreActualizar = valor;
    }
    else if(clases.contains('rf-sub-elemento-nombre')){
        const valor = input.textContent.trim();
        if($nombreActualizar != valor) {
            let ixs = RutinaIx.getIxsForSubElemento(input);
            if(valor.length == 0){
                SubEleOpc.eliminarSubElemento(ixs.numSem, ixs.diaIndex, ixs.eleIndex, ixs.subEleIndex);
            }else {
                let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
                let initEle = tempElemento;
                let i = 0, k = 0;
                //
                while ((tempElemento = tempElemento.previousElementSibling) != null) i++;
                let tempSubElemento = RutinaDOMQueries.getSubElementoByIxs(ixs);

                while ((tempSubElemento = tempSubElemento.previousElementSibling) != null) k++;
                RutinaSet.setSubElementoNombre(ixs.numSem, ixs.diaIndex, i, k, valor);
                DiaOpc.validPreActualizarFromNomSubEle2(initEle, valor, ixs, i, k);//Siempre va ser el primero por eso se deja la posicion como 0
            }
        }
    }
}

function obtenerMiniRutinaByHash(hash, input){
    const titulo = input.textContent.slice(input.textContent.indexOf(" ")+1,input.textContent.trim().length);
    irRegistro('wildcard');
    $.ajax({
        type: 'GET',
        url: _ctx + 'ilc/mini-rutinas/obtener/by/hash/'+hash,
        dataType: "json",
        success: function (d) {
            if(d.data != undefined) {
                $miniId = hash;
                divRutina.innerHTML = instanciarMiniRutina(d.data, titulo);
                const mainMini = document.querySelector('#MainMini');
                mainMini.querySelectorAll('.rf-dia-elemento-nombre').forEach(v=>$(v).popover());
                mainMini.querySelectorAll('.rf-sub-elemento-nombre').forEach(v=>$(v).popover());
                mainMini.querySelectorAll('.reprod-video').forEach(v=>$(v).tooltip());
                mainMini.querySelectorAll('.reprod-audio').forEach(v=>$(v).tooltip());
            }else
                notificacionesRutinaSegunResponseCode(d.responseCode);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    });
}

function obtenerMisRutinasByCategoria(catId, nombreCat){
    document.querySelector('#inptBusqueda').value = "";
    divRutina.innerHTML = '';
    $.ajax({
        type: 'GET',
        url: _ctx + 'ilc/mini-rutinas/obtener-rutinas/by/categoria/'+catId,
        dataType: "json",
        success: function (d) {
            if(d.data != undefined){
                irRegistro('wildcard');
                const minis = d.data.sort((a,b)=>{return a.nombre.localeCompare(b.nombre)})
                $minis = minis;
                const view_register = document.querySelector('#view_register');
                view_register.querySelector('h1').innerHTML = '<i class="i-btn fa fa-fw fa-angle-left back-listado" rel="tooltip" data-original-title="Regresar" data-toggle="hover" onclick="javascript:irListado([{\'defaultValue\':0}])"></i>'+nombreCat;
                const coincidencias = minis.filter(v=>v.nvl === $nvl);
                customPaginacion(coincidencias, divRegistros, 0, 0);
            } else
                notificacionesRutinaSegunResponseCode(d.responseCode);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    });
}

function obtenerCategoriasIdsByUsuario(){
    $.ajax({
        type: 'GET',
        url: _ctx + 'ilc/mini-rutinas/obtener-categorias-id/by/trainer',
        dataType: "json",
        success: function (d) {
            if(typeof d === 'object'){
                const catIds = d.sort();
                const base = document.querySelector('#view_list');
                catIds.forEach(v=>{
                    const cat = base.querySelector(`.img-cat-rutina-empty[data-index="${v}"]`);
                    cat.classList.remove('img-cat-rutina-empty');
                    cat.classList.add('img-cat-rutina');
                    base.querySelector(`.span-center-on-img[data-index="${v}"]`).classList.remove('span-disabled');
                })
            }else{
                notificacionesRutinaSegunResponseCode(d);
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
        }
    });
}

function customPaginacion(arrMiniRutinas, divRegistros, activeIx, pagina){
    if(arrMiniRutinas.length > 10){
        const desde = (pagina*10)+1;
        let hasta = ((pagina+1)*10);
        hasta = hasta>arrMiniRutinas.length ? arrMiniRutinas.length : hasta;
        const pags = Math.ceil(arrMiniRutinas.length/10);
        let pagsHTML = '<div class="row"><div class="col-md-12 text-align-right">';
        pagsHTML += `<div class="fixed-table-pagination" style="">
                    <div class="pull-left pagination-detail">
                        <span class="pagination-info"><i>Mostrando de ${desde} a ${hasta} de un total de ${arrMiniRutinas.length} rutinas </i></span>
                    </div>
                    <div class="pull-right pagination">
                        <ul class="pagination">
                            <li class="page-pre">
                                <a href="javascript:void(0)">‹</a>
                            </li>
                            ${Array(pags).fill().map((v,i)=>i+1).map((v,ii)=>`<li class="${ii == activeIx? 'active':''}"><a class="page-number" href="javascript:void(0)">${ii+1}</a></li>`).join('')}
                            <li class="page-next">
                                <a href="javascript:void(0)">›</a>
                            </li>
                        </ul>
                    </div>
                 </div>`;
        pagsHTML += '</div></div>';
        const minisCopy = JSON.parse(JSON.stringify(arrMiniRutinas));
        divRegistros.innerHTML = minisCopy.splice(pagina*10, 10).map((v, i) => `<div class="col padding-o-bottom-5"><a href="javascript:void(0);"><span class="mini-rutina" data-index="${v.hash}"><i class="fa fa-fw"><b>${++i}.</b></i> ${v.nombre}</span></a></div>`).join('') + pagsHTML;
    } else if(arrMiniRutinas.length == 0){
        divRegistros.innerHTML = '<div class="col" style="padding-top: 10px;"><span class="font-md txt-color-blueLight"><i class="fa fa-fw fa-info-circle"></i> No se encontraron coincidencias...</span></div>';
    } else {
        divRegistros.innerHTML = arrMiniRutinas.map((v, i) => `<div class="col padding-o-bottom-5"><a href="javascript:void(0);"><span class="mini-rutina" data-index="${v.hash}"><i class="fa fa-fw"><b>${++i}.</b></i> ${v.nombre}</span></a></div>`).join('');
        $(document.querySelector('#view_register .i-btn')).tooltip();
    }
}


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
                                    <div class="panel panel-default rf-dia-elemento" style="border: none;">
                                        <div class="panel-heading">
                                            <h4 class="panel-title txt-color-blue">
                                                <a href="javascrip:void(0);">
                                                    <i class="fa fa-lg fa-angle-down pull-right invisible"></i>
                                                    <i class="fa fa-lg fa-angle-up pull-right invisible"></i> 
                                                    <input id="InputIcon" disabled="" value="" type="number" class="pull-right agregar-tiempo">
                                                    <span class="lista-title">
                                                        <span class="rf-dia-elemento-nombre padding-10"></span>
                                                    </span>
                                                </a>
                                            </h4>
                                        </div>
                                    </div>
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
                                    <input disabled value="${ele.minutos}" type="number" maxlength="3" class="pull-right agregar-tiempo" data-index="${ix}" data-placement="top" rel="tooltip" data-original-title="AÃ±adir tiempo en minutos"/> 
                                    <span class="txt-color-black lista-title">
                                        <span data-index="${numeracion+1}" class="badge badge-custom-enum font-md">${numeracion+1}</span>
                                        <span class="">
                                            ${ele.mediaVideo != undefined?iconoVideo(ele.mediaVideo):''}
                                            ${ele.mediaAudio != undefined?iconoAudio(ele.mediaAudio):''}
                                        </span>
                                        <span class="rf-dia-elemento-nombre padding-10 ${ess.base}" data-dia-index="${diaIndex}" data-index="${ix}" data-placement="bottom" data-toggle="popover" data-content="${ele.nota != undefined? ele.nota : ''}" data-trigger="hover">${ele.nombre}</span>  
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
                            <h4 class="panel-title txt-color-blue">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${ix}" class="collapsed ${ess.header}" aria-expanded="false">
                                        <i class="fa fa-lg fa-angle-down pull-right text-primary"></i> 
                                        <i class="fa fa-lg fa-angle-up pull-right text-primary"></i>
                                        <input disabled value="${ele.minutos}" type="number" maxlength="3" class="pull-right agregar-tiempo" data-index="${ix}" data-placement="top" rel="tooltip" data-original-title="AÃ±adir tiempo en minutos"/>
                                        <span class="lista-title">
                                            <span data-index="${numeracion+1}" class="badge badge-custom-enum font-md">${numeracion+1}</span>
                                            <span class="">                                         
                                                ${ele.mediaVideo != undefined? iconoVideo(ele.mediaVideo):''}
                                                ${ele.mediaAudio != undefined? iconoAudio(ele.mediaAudio):''}                                                
                                            </span>
                                            <span class="rf-dia-elemento-nombre padding-10 ${ess.base}" data-dia-index="${diaIndex}" data-index="${ix}" data-placement="bottom" data-toggle="popover" data-content="${ele.nota != undefined? ele.nota :''}" data-trigger="hover">${ele.nombre}</span>
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
                           <span class="rf-sub-elemento-nombre padding-10" data-ele-index="${eleIndex}" data-index="${ix}" data-placement="bottom" data-toggle="popover" data-content="${sEle.nota != undefined? sEle.nota :''}" data-trigger="hover">${sEle.nombre}</span>
                           
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



