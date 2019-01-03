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

/*function filtrarCoincidencias(value){
    const coincidencias = $minis.filter(v=>{return v.nvl === $nvl && v.nombre.toLowerCase().includes(value.toLowerCase())});
    if(coincidencias.length > 0) {
        divRegistros.innerHTML = coincidencias.map((v, i) => `<div class="col padding-o-bottom-5"><a href="javascript:void(0);"><span class="mini-rutina" data-index="${v.hash}"><i class="fa fa-fw"><b>${++i}.</b></i> ${v.nombre}</span></a></div>`).join('');
        $(document.querySelector('#view_register .i-btn')).tooltip();
    } else
        divRegistros.innerHTML = '<div class="col" style="padding-top: 10px;"><span class="font-md txt-color-blueLight"><i class="fa fa-fw fa-info-circle"></i> No se encontraron coincidencias...</span></div>';
}*/

function obtenerMiniRutinaByHash(hash, input){
    const titulo = input.textContent.slice(input.textContent.indexOf(" ")+1,input.textContent.trim().length);
    irRegistro('wildcard');
    $.ajax({
        type: 'GET',
        url: _ctx + 'gestion/mini-rutina/obtener/by/hash/'+hash,
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
        url: _ctx + 'gestion/mini-rutina/obtener-rutinas/by/categoria/'+catId,
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
        url: _ctx + 'gestion/mini-rutina/obtenerCategoriasId/ByUsuario',
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