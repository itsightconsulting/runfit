const viewList = document.querySelector('#view_list');
const viewRegister = document.querySelector('#view_register');
const divRutina = document.querySelector('#divRutina');
let $minis;
let $nvl = 1;
$(function () {
    init();
})

function init() {

    obtenerCategoriasIdsByUsuario();
    viewList.addEventListener('click', principalesViewList);
    viewRegister.addEventListener('click', principalesViewRegister);

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
        obtenerMiniRutinaByHash(hash);
    }
    else if(clases.contains('lbl-btn-buscar-mini')){
        const queryValue = input.previousElementSibling.value;
        /*if(queryValue != undefined && queryValue.length > 2){*/
            filtrarCoincidencias(queryValue);
        /*}else
            $.smallBox({color: "alert",content: "<i>Las consultas deben tener mínimo 3 letras...</i>"})*/
    }
    else if(clases.contains('badge-custom-nvl')){
        input.parentElement.parentElement.querySelectorAll('.badge-custom-nvl').forEach(v=>v.classList.remove('opacity'));
        clases.add('opacity');
        $nvl = Number(input.getAttribute('data-index'));
        filtrarCoincidencias('');
    }
    else if(clases.contains('back-listado')){
        $nvl = 1;
    }
}

function filtrarCoincidencias(value){
    const divRegistros = document.querySelector('#dvMisRutinas');
    const coincidencias = $minis.filter(v=>{return v.nvl === $nvl && v.nombre.toLowerCase().includes(value.toLowerCase())});
    if(coincidencias.length > 0) {
        divRegistros.innerHTML = coincidencias.map((v, i) => `<a href="javascript:void(0);"><h6 class="mini-rutina" data-index="${v.hash}"><i class="fa fa-fw"><b>${++i}.</b></i> ${v.nombre}</h6></a>`).join('');
        $(document.querySelector('#view_register .i-btn')).tooltip();
    }else
        divRegistros.innerHTML = '<div class="col" style="padding-top: 10px;"><span class="font-md txt-color-blueLight"><i class="fa fa-fw fa-info-circle"></i> No se encontraron coincidencias...</span></div>';
}

function obtenerMiniRutinaByHash(hash){
    irRegistro('wildcard');
    $.ajax({
        type: 'GET',
        url: _ctx + 'gestion/mini-rutina/obtener/by/hash/'+hash,
        dataType: "json",
        success: function (d) {
            $yelmo = d.data;
            if(d.data != undefined){
                divRutina.innerHTML = d.data.elementos.map(v => `<h1>${v.nombre}</h1>`).join('');
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
                const divRegistros = view_register.querySelector('#dvMisRutinas');
                view_register.querySelector('h1').innerHTML = '<i class="i-btn fa fa-fw fa-angle-left back-listado" rel="tooltip" data-original-title="Regresar" data-toggle="hover" onclick="javascript:irListado([{\'defaultValue\':0}])"></i>'+nombreCat;
                const coincidencias = minis.filter(v=>v.nvl === $nvl);
                if(coincidencias.length >0) {
                    if( 0 && coincidencias.length < 10){
                        divRegistros.innerHTML = coincidencias.map((v, i) => `<a href="javascript:void(0);"><h6 class="mini-rutina" data-index="${v.hash}"><i class="fa fa-fw"><b>${++i}.</b></i> ${v.nombre}</h6></a>`).join('');
                        $(document.querySelector('#view_register .i-btn')).tooltip();
                    }else
                        customPaginacion(coincidencias, divRegistros, 0);
                }else
                    divRegistros.innerHTML = '<div class="col" style="padding-top: 10px;"><span class="font-md txt-color-blueLight"><i class="fa fa-fw fa-info-circle"></i> No se encontraron coincidencias...</span></div>';
            }else
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

function customPaginacion(arrMiniRutinas, divRegistros, activeIx){
    const pags = Math.ceil(arrMiniRutinas.length/10);
    let pagsHTML = '<div class="row"><div class="col-md-12 text-align-right">';
    pagsHTML += `<div class="fixed-table-pagination" style="">
                    <div class="pull-left pagination-detail">
                        <span class="pagination-info"><i>Mostrando de 1 a 10 de un total de ${arrMiniRutinas.length} rutinas </i></span>
                    </div>
                    <div class="pull-right pagination">
                        <ul class="pagination">
                            <li class="page-pre">
                                <a href="javascript:void(0)">‹</a>
                            </li>
                            ${Array(pags).fill().map((v,i)=>i+1).map((v,ii)=>`<li class="page-number ${ii == activeIx? 'active':''}"><a href="javascript:void(0)">${ii+1}</a></li>`).join('')}
                            <li class="page-next">
                                <a href="javascript:void(0)">›</a>
                            </li>
                        </ul>
                    </div>
                 </div>`;
    pagsHTML += '</div></div>';
    divRegistros.innerHTML = arrMiniRutinas.splice(0,10).map((v, i) => `<a href="javascript:void(0);"><h6 class="mini-rutina" data-index="${v.hash}"><i class="fa fa-fw"><b>${++i}.</b></i> ${v.nombre}</h6></a>`).join('') + pagsHTML;
}