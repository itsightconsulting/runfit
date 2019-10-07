const body = document.getElementsByTagName("body")[0];
const owlCategoria = $('section#listaCategoria .owl-carousel');
const owlSubcategoria = $('section#listaSubCategoria .owl-carousel');
const cbox = $('input[type="radio"][name="cbNuevaCategoria"]');
const dvListaCategoria = document.querySelector("section#listaCategoria div");
const dvListaSubCategoria = document.querySelector("section#listaSubCategoria div");
const dvListaRutinas = document.querySelector("section#info div");
const dvArrows = document.querySelector(".flechas");
const mdlEditarCat = document.getElementById("modalEditarCategoria");
const mdlEditarSubCat = document.getElementById("modalEditarSubCategoria");
const mdlEditarRutina = document.getElementById("modalEditarRutina");
const mdlAgregarCat = document.getElementById("modalNuevaCategoria");
const mdlAgregarSubCat = document.getElementById("modalNuevaSubCategoria");
const btnEditarCat = document.getElementById("btnEditarCategoria");
const btnEliminarCat = document.getElementById("btnEliminarCategoria");
const btnEditarSubCat = document.getElementById("btnEditarSubCategoria");
const btnEliminarSubCat = document.getElementById("btnEliminarSubCategoria");
const mdlAgregarRutina = document.getElementById("modalNuevaRutina");
const btnEditarRutina = document.getElementById("btnEditarRutina");
const btnNuevaRutina = document.getElementById("btnNuevaRutina");
const btnNuevaSubCategoria = document.getElementById("btnNuevaSubCategoria");
const ulListRutina = $('.list-plan');
const anchorItemBody = $('.item-body a');
const inpSearchRutina = document.getElementById('inpSearch');
let currentRutinasData;
const favoritos = [];
let dataCategorias;
let dataSubCategorias;
$(function(){
    onInit();
})
function onInit(){
    checkBoxTipoChangeEvent();
    checkBoxasRadioButtons();
    obtenerCategorias();
    ulListRutina.on('click', 'li', rutinaClickListenerEvent);
    inpSearchRutina.addEventListener('input', searchRutinaEvent);
    owlCategoria.on('click', '.item-header', categoriaClickEvent);
    owlCategoria.on('click', '.item-body a', seleccionCategoriaEvent);
    owlSubcategoria.on('click', '.item-header', subCategoriaClickEvent);
    owlSubcategoria.on('click', '.item-body a', seleccionSubCategoriaEvent);
    body.addEventListener('focusout', bodyFocusOutEventListener);
    constraintsValidation();
    scrollEvent();
}
function seleccionCategoriaEvent(e){

    const input = e.currentTarget;
    let categoriaId;
    let catNombre;
    inpSearchRutina.value = '';
    categoriaId = input.getAttribute('data-categoria-id');
    catNombre = input.getAttribute('data-nombre');
    $('section#listaSubCategoria .title_big').text(catNombre);
    btnNuevaSubCategoria.setAttribute('data-categoria-id' , categoriaId);
    obtenerSubCategorias(categoriaId);

    $("section#listaSubCategoria").slideDown(200);
    $("section#info").slideUp(200);

    $( 'html, body' ).stop().animate({
        scrollTop: $("section#listaSubCategoria").offset().top
    });
    return false;
}

function seleccionSubCategoriaEvent(e){

    const input = e.currentTarget;
    inpSearchRutina.value = '';
    let subcategoriaId = input.getAttribute('data-subcategoria-id');
    let subcatNombre = input.getAttribute('data-nombre');
    $('section#info .title_big').text(subcatNombre);
    btnNuevaRutina.setAttribute('data-subcategoria-id' , subcategoriaId);

    obtenerRutinas(subcategoriaId);


    $("section#info").slideDown(200);

    $( 'html, body' ).stop().animate({
        scrollTop: $("section#info").offset().top
    });

    $(".scroll").click(function() {
        $("section#info").slideUp(200);
        $('html, body').animate({
            scrollTop: 600
        }, 800);
    });
    return false;



}


function bodyFocusOutEventListener(e){
    const input = e.target;
    if(input.tagName === "INPUT"){
        if(input.type==="text"){
            input.value = input.value.trim();
        }
    }
    if(input.tagName === "TEXTAREA"){
        input.value = input.value.trim();
    }
}


function scrollEvent(){
    $(".scroll").click(function() {
        ocultarSeccionRutinas();
        return false;
    });
}


function ocultarSeccionRutinas(){
    $("section#info").slideUp(200);
    $('html, body').animate({
        scrollTop: 0
    }, 800);
}
function agregarCategoria() {
    if ($('#nueva_categoria_frm').valid()) {
        const nombre = $('#nombreCategoria').val()
        const tipoRutina = $('input[type="checkbox"][name="cbNuevaCategoria"]:checked').val();

        $.ajax({
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            url: _ctx + 'gestion/categoria-plantilla/agregar',
            blockLoading: false,
            noOne: true,
            data: {
                nombre: nombre,
                tipo: tipoRutina,
                favorito : false
            },
            dataType: 'json',
            success: (data) => {
                $(mdlAgregarCat).modal('hide');
                $('#nueva_categoria_frm')[0].reset();
                obtenerCategorias();
                $('input[name="cbSeleccionTipo"]').prop('checked', false);
            },
            error: (xhr) => {
                const mensaje = xhr.responseJSON.message;

                $.smallBox({
                    title: "RUNFIT",
                    content: '<p>'+mensaje+'</p>',
                    timeout: 4500,
                    color:  '#cc4d4d',
                    icon: "fa fa-exclamation-circle"
                })

            },
            complete: (d) => {

            }
        })
    }
}
function obtenerCategorias(){
    //     const nombre = $('#nombreCategoria').val()
    //     const tipoRutina = $('input[type="checkbox"][name="cbSeleccionTipo"]:checked').val();
    $.ajax({
        type: 'GET',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        url: _ctx+'gestion/categoria-plantilla/obtener',
        dataType: 'json',
        blockLoading: false,
        noOne: true,
        success: (data) =>  {
            dataCategorias = data;
            if(dataCategorias.length <= 5){
                const dvSlider = document.querySelector('.range-slider').parentElement;
                const clasesSlider = dvSlider.classList;
                if(!clasesSlider.contains('hidden')){
                    clasesSlider.add('hidden');
                }
            }


            generarCategoriasPlantillas(dataCategorias);
        } ,
        error: (d) =>{
        },
        complete: (d) =>{
        }
    })
}
function actualizarCategoriaPlantilla() {
    if ($('#editar_categoria_frm').valid()) {
        const nombre = $('#nombreCategoriaEdit').val()
        const tipoRutina = $('input[type="checkbox"][name="cbEditarCategoria"]:checked').val();
        const categoriaId = Number(btnEditarCat.getAttribute("data-categoria-id"));
        $.ajax({
            type: 'PUT',
            url: _ctx + "gestion/categoria-plantilla/actualizar",
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            blockLoading: false,
            noOne: true,
            data: {id: categoriaId, nombre: nombre, tipo: tipoRutina},
            dataType: 'json',
            success: (d) => {
                console.log(d);
            },
            error: (e) => {
            },
            complete: (c) => {
                obtenerCategorias();
                $(mdlEditarCat).modal('hide');
            }
        })
    }
}
function generarCategoriasPlantillas(data){
    if(data.length > 0) {
        data = data.sort(function (a, b) {
            if (a.favorito > b.favorito) return -1;
            if (a.favorito < b.favorito) return 1;
            if (a.id < b.id) return -1;
            if (a.id > b.id) return 1;
            return 0;
        });
        let itemsCategoria = "";
        data.length <= 6 ? document.querySelector('.range-slider').max = 1 :  document.querySelector('.range-slider').max = data.length - 6 ;

        data.forEach(function(element) {
            let claseCategoria = "title-categoria";
            let tamanio = element.nombre.length <= 4 && 1 <= (element.nombre).split("").length <= 2  ? "lg" :
                (element.nombre.length < 18 && 1 <= (element.nombre).split("").length <= 3  ?  "md" : "sm");
            claseCategoria += " "+ tamanio;
            if(element.favorito){
                favoritos.push(element.id);
                itemsCategoria += `
                      <div class="item" >
                       <div class="item-header"><a class="star selected" data-categoria-id = "${element.id}"><img class="svg" src="/img/iconos-trainers/icon_star.svg"></a><a data-toggle="modal" class="icon-edit" data-target="#modalEditarCategoria" type="button" href="#"  data-categoria-id ="${element.id}"  data-nombre="${element.nombre}" data-tipo ="${element.tipo}"><img class="svg" src="/img/iconos-trainers/icon_edit.svg"  ></a><div class="${claseCategoria}"> <p> ${element.nombre} </> </div> </div>
                       <div class="item-body"><img class="svg" src="/img/iconos-trainers/icon_tablero.svg"><a data-categoria-id ="${element.id}"  data-nombre="${element.nombre}"><img class="svg" src="/img/iconos-trainers/icon_add.svg">Agregar</a></div>
                      </div>
                    `;
            }else{
                itemsCategoria += `
                    <div class="item" >
                      <div class="item-header"><a class="star" data-categoria-id = "${element.id}"><img class="svg" src="/img/iconos-trainers/icon_star.svg"></a><a data-toggle="modal" class="icon-edit" data-target="#modalEditarCategoria" type="button" href="#" data-categoria-id ="${element.id}"  data-nombre="${element.nombre}" data-tipo ="${element.tipo}"><img class="svg" src="/img/iconos-trainers/icon_edit.svg" ></a><div class="${claseCategoria}"> <p> ${element.nombre} </p> </div> </div>
                      <div class="item-body"><img class="svg" src="/img/iconos-trainers/icon_tablero.svg"><a data-categoria-id ="${element.id}"  data-nombre="${element.nombre}"><img class="svg" src="/img/iconos-trainers/icon_add.svg">Agregar</a></div>
                    </div>
                    `;
            }
        });
        $('.owl-carousel').trigger('replace.owl.carousel', itemsCategoria).trigger('refresh.owl.carousel');
        $('.owl-stage').css('display','inline-flex');
        $('.owl-item').css('width','229.8px');
        $('.owl-item').css('margin-right','10px');
        imgtoSvgEvent();
        visualizarElementosOcultos();
    }else{
        let mensajeNoData;
        if(dataCategorias.length === 0){
            mensajeNoData = htmlStringToElement(`
                    <p class="mensaje-no-data"> No cuenta con alguna categoría de plantillas de rutinas. Dele click al botón de Agregar para
                       iniciar su colección </p>
            `) ;
            dvListaCategoria.appendChild(mensajeNoData);
            ocultarElementos(1);
        }else{
            let mensajeNoData = htmlStringToElement(`
                    <p class="mensaje-no-data"> No cuenta con registros para el tipo de categoría consultado. Dele click al botón de Agregar para
                       iniciar su colección </p>
            `) ;
            dvListaCategoria.appendChild(mensajeNoData);
            ocultarElementos(2);
        }
        owl.css('display' , 'show');
    }
    ocultarSeccionRutinas();
}
function eliminarCategoria(){

    const obj ={id :  Number(btnEliminarCat.getAttribute('data-categoria-id'))};
    const nombre = btnEliminarCat.getAttribute('data-nombre');

    $.SmartMessageBox({
        title: "<i class='fa fa-2x fa-trash' style='display:flex; align-items: center; margin-right:0.7em;'></i> ¿Está seguro que desea eliminar la categoría "+ nombre + " ( Se eliminarán todas las rutinas asociadas a esta )?",
        buttons: "[No][Si]"
    }, function(e) {
        "Si" == e &&
        $.ajax({
            url: _ctx + "gestion/categoria-plantilla/eliminar",
            type: "DELETE",
            dataType: 'json',
            blockLoading: false,
            noOne: true,
            data: obj,
            success: function(e) {
                $(mdlEditarCat).modal('hide');
                obtenerCategorias();
            },
            error: function(e) {
                console.log(e)
            }
        })
    })
    $('.MsgTitle').css('display','flex');
}

function agregarSubCategoria() {
    if ($('#nueva_sub_categoria_frm').valid()) {
        const nombre = $('#nombreSubCategoria').val();
        const catId = btnNuevaSubCategoria.getAttribute('data-categoria-id');
        $.ajax({
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            url: _ctx + 'gestion/subcategoria-plantilla/agregar',
            blockLoading: false,
            noOne: true,
            data: {
                nombre: nombre,
                favorito : false,
                catPlantillaId : catId
            },
            dataType: 'json',
            success: (data) => {
                $(mdlAgregarSubCat).modal('hide');
                $('#nueva_sub_categoria_frm')[0].reset();
                obtenerSubCategorias(catId);
            },
            error: (xhr) => {
                const mensaje = xhr.responseJSON.message;

                $.smallBox({
                    title: "RUNFIT",
                    content: '<p>'+mensaje+'</p>',
                    timeout: 4500,
                    color:  '#cc4d4d',
                    icon: "fa fa-exclamation-circle"
                })

            },
            complete: (d) => {

            }
        })
    }
}

function obtenerSubCategorias(categoriaId){

    $.ajax({
        type: 'GET',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        url: _ctx+'gestion/subcategoria-plantilla/obtener',
        dataType: 'json',
        blockLoading: false,
        noOne: true,
        data: {
            categoriaId: categoriaId
        },
        success: (data) =>  {

            dataSubCategorias = data;

            if(dataSubCategorias.length <= 5){
                const dvSlider = document.querySelector('section#listaSubCategoria .range-slider').parentElement;
                const clasesSlider = dvSlider.classList;
                if(!clasesSlider.contains('hidden')){
                    clasesSlider.add('hidden');
                }
            }


            generarSubCategoriasPlantillas(dataSubCategorias);
        } ,
        error: (d) =>{
        },
        complete: (d) =>{
        }
    })
}

function eliminarSubCategoria(){

    const obj ={id :  Number(btnEliminarSubCat.getAttribute('data-subcategoria-id'))};
    const nombre = btnEliminarSubCat.getAttribute('data-nombre');

    $.SmartMessageBox({
        title: "<i class='fa fa-2x fa-trash' style='display:flex; align-items: center; margin-right:0.7em;'></i> ¿Está seguro que desea eliminar la subcategoría "+ nombre + " ( Se eliminarán todas las rutinas asociadas a esta )?",
        buttons: "[No][Si]"
    }, function(e) {
        "Si" == e &&
        $.ajax({
            url: _ctx + "gestion/subcategoria-plantilla/eliminar",
            type: "DELETE",
            dataType: 'json',
            blockLoading: false,
            noOne: true,
            data: obj,
            success: function(e) {
                $(mdlEditarSubCat).modal('hide');
                const catId = btnNuevaSubCategoria.getAttribute('data-categoria-id');
                obtenerSubCategorias(catId);
            },
            error: function(e) {
                console.log(e)
            }
        })
    })
    $('.MsgTitle').css('display','flex');
}

function generarSubCategoriasPlantillas(data){


    if(data.length > 0) {
        data = data.sort(function (a, b) {
            if (a.favorito > b.favorito) return -1;
            if (a.favorito < b.favorito) return 1;
            if (a.id < b.id) return -1;
            if (a.id > b.id) return 1;
            return 0;
        });
        let itemsSubCategoria = "";
        data.length <= 6 ? document.querySelector('section#listaSubCategoria .range-slider').max = 1 :  document.querySelector('section#listaSubCategoria .range-slider').max = data.length - 6 ;

        data.forEach(function(element) {
            let claseSubCategoria = "title-subcategoria";
            let tamanio = element.nombre.length <= 4 && 1 <= (element.nombre).split("").length <= 2  ? "lg" :
                (element.nombre.length < 18 && 1 <= (element.nombre).split("").length <= 3  ?  "md" : "sm");
            claseSubCategoria += " "+ tamanio;
            if(element.favorito){
                favoritos.push(element.id);
                itemsSubCategoria += `
                      <div class="item" >
                       <div class="item-header"><a class="star selected" data-subcategoria-id = "${element.id}"><img class="svg" src="/img/iconos-trainers/icon_star.svg"></a><a data-toggle="modal" class="icon-edit" data-target="#modalEditarSubCategoria" type="button" href="#"  data-subcategoria-id ="${element.id}"  data-nombre="${element.nombre}" ><img class="svg" src="/img/iconos-trainers/icon_edit.svg"  ></a><div class="${claseSubCategoria}"> <p> ${element.nombre} </> </div> </div>
                       <div class="item-body"><img class="svg" src="/img/iconos-trainers/icon_tablero.svg"><a data-subcategoria-id ="${element.id}"  data-nombre="${element.nombre}"><img class="svg" src="/img/iconos-trainers/icon_add.svg">Agregar</a></div>
                      </div>
                    `;
            }else{
                itemsSubCategoria += `
                    <div class="item" >
                      <div class="item-header"><a class="star" data-subcategoria-id = "${element.id}"><img class="svg" src="/img/iconos-trainers/icon_star.svg"></a><a data-toggle="modal" class="icon-edit" data-target="#modalEditarSubCategoria" type="button" href="#" data-subcategoria-id ="${element.id}"  data-nombre="${element.nombre}"><img class="svg" src="/img/iconos-trainers/icon_edit.svg" ></a><div class="${claseSubCategoria}"> <p> ${element.nombre} </p> </div> </div>
                      <div class="item-body"><img class="svg" src="/img/iconos-trainers/icon_tablero.svg"><a data-subcategoria-id ="${element.id}"  data-nombre="${element.nombre}"><img class="svg" src="/img/iconos-trainers/icon_add.svg">Agregar</a></div>
                    </div>
                    `;
            }
        });
        $('#subCategoriaPlantillaCarousel').trigger('replace.owl.carousel', itemsSubCategoria).trigger('refresh.owl.carousel');
        $('section#listaSubCategoria .owl-stage').css('display','inline-flex');
        $('section#listaSubCategoria .owl-item').css('width','229.8px');
        $('section#listaSubCategoria .owl-item').css('margin-right','10px');

        imgtoSvgEvent();
        visualizarElementosOcultos();
    }else{
        let mensajeNoData;
        if(dataCategorias.length === 0){
            mensajeNoData = htmlStringToElement(`
                    <p class="mensaje-no-data"> No cuenta con alguna subcategoría de plantillas de rutinas. Dele click al botón de Agregar para
                       iniciar su colección </p>
            `) ;
            dvListaSubCategoria.appendChild(mensajeNoData);
            ocultarElementos(1);
        }
        $('section#listaSubCategoria .owl-carousel').css('display' , 'show');
    }
    //   ocultarSeccionRutinas();
}

function visualizarElementosOcultos(){
    $('.slider-container').css('display' , 'block');
    $('section#listaCategoria .checks').css('display' , 'block');
    $('.mensaje-no-data').remove();
    //   $('.flechas').toggleClass('hidden');
}
function ocultarElementos(tipo){
    const item = document.querySelectorAll('body .item')
    switch(tipo) {
        case 1 :
            $('section#listaCategoria .checks').css('display' , 'none');
            $('.slider-container').css('display' , 'none');
            $('.flechas').css('visibility' , 'false');
            break;
        case 2 :
            $('.slider-container').css('display' , 'none');
            $('.flechas').css('visibility' , 'true');
            for (let i=0; i<item.length; i++) {
                owl.trigger('remove.owl.carousel', [i])
                    .trigger('refresh.owl.carousel');
            }
            break;
        default:
            null;
    }
}

function actualizarFavoritoCategoria(categoriaId) {
    $.ajax({
        type: 'PUT',
        url: _ctx + "gestion/categoria-plantilla/actualizar-favorito",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: {id: categoriaId},
        dataType: 'json',
        success: (d) => {
        },
        error: (e) => {
        },
        complete: (c) => {
        }
    })
}

function actualizarFavoritoSubCategoria(categoriaId) {
    $.ajax({
        type: 'PUT',
        url: _ctx + "gestion/categoria-plantilla/actualizar-favorito",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: {id: categoriaId},
        dataType: 'json',
        success: (d) => {
        },
        error: (e) => {
        },
        complete: (c) => {
        }
    })
}

function checkBoxasRadioButtons(){
    $("input:checkbox").on('click', function() {
        let $box = $(this);
        if ($box.is(":checked")) {
            let group = "input:checkbox[name='" + $box.attr("name") + "']";
            $(group).prop("checked", false);
            $box.prop("checked", true);
        } else {
            $box.prop("checked", false);
        }
    });
}
function checkBoxTipoChangeEvent() {
    $("section#listaCategoria input:checkbox").on('change', function () {
        let cbSeleccionado = $(this)[0].value;
        if($("input:checkbox[name='cbSeleccionTipo']:checked").length === 0){
            generarCategoriasPlantillas(dataCategorias);
        }else {
            console.log(dataCategorias);
            if (dataCategorias.length > 0) {
                let dataCategoriasTipo = dataCategorias.filter((e) => e.tipo === Number(cbSeleccionado));
                generarCategoriasPlantillas(dataCategoriasTipo);
            }
        }
        inpSearchRutina.value = '';
        ocultarSeccionRutinas();
    })
}
function categoriaClickEvent(e){
    const input = e.target;
    const parentStarDOM = ($(input).parent().closest('.star'));
    const parentEditDOM = ($(input).parent().closest('.icon-edit'));
    const starElement = parentStarDOM[0];
    const editElement = parentEditDOM[0];
    if(starElement){
        const starClases = starElement.classList;
        const categoriaId =  Number(starElement.getAttribute('data-categoria-id'));
        actualizarFavoritoCategoria(categoriaId);
        let dataCategoriaIx = dataCategorias.findIndex( e => e.id === categoriaId );
        if(starClases.contains('selected')){
            //  const categoriaId =  Number(starElement.getAttribute('data-categoria-id');
            starClases.remove('selected');
            let index = favoritos.findIndex( e => e === categoriaId);
            favoritos.splice(index,1);
            dataCategorias[dataCategoriaIx].favorito =  false;
        }else{
            starClases.add('selected');
            const categoriaId =  starElement.getAttribute('data-categoria-id');
            !favoritos.includes('categoriaId') ? favoritos.push(Number(categoriaId)) : null;
            dataCategorias[dataCategoriaIx].favorito =  true;
        }
        generarCategoriasPlantillas(dataCategorias);
    }
    else if(editElement){

        debugger
        const nombre = editElement.getAttribute("data-nombre");
        const categoriaId = editElement.getAttribute("data-categoria-id");
        const tipoCategoria = Number(editElement.getAttribute("data-tipo"));
        const txtNombre = mdlEditarCat.querySelector('#nombreCategoriaEdit');
        const cbTipoCategoria = mdlEditarCat.querySelectorAll('input[name="cbEditarCategoria"][value="'+ tipoCategoria+'"]');
        txtNombre.value = nombre;

        $('input[name="cbEditarCategoria"]').prop('checked',false)
        $(cbTipoCategoria).prop('checked','true');
        btnEditarCat.setAttribute("data-categoria-id",categoriaId);
        btnEliminarCat.setAttribute("data-categoria-id",categoriaId);
        btnEliminarCat.setAttribute("data-nombre",nombre);
    }
}
function rutinaClickListenerEvent(e){
    const input = e.target;
    const parentEditDOM = ($(input).parent().closest('.edit'));
    const parentDeleteDOM = ($(input).parent().closest('.delete'));
    const editElement = parentEditDOM[0];
    const deleteElement = parentDeleteDOM[0];
    const txtNombre = mdlEditarRutina.querySelector('#nombreRutinaEdit');
    let id,nombre,semanas;
    let categoriaId = btnNuevaRutina.getAttribute('data-categoria-id');
    if(editElement) {
        console.log(editElement);
        nombre = editElement.getAttribute('data-rutina-nombre');
        id = editElement.getAttribute('data-rutina-id');
        txtNombre.value = nombre;
        btnEditarRutina.setAttribute('data-rutina-id',id);
        $(mdlEditarRutina).modal('show');
    }
    if(deleteElement){
        id = deleteElement.getAttribute('data-rutina-id');
        nombre = deleteElement.getAttribute('data-rutina-nombre');
        const obj = {
            id: id
        }
        $.SmartMessageBox({
            title: "<i class='fa fa-2x fa-trash' style='display:flex; align-items: center; margin-right:0.7em;'></i> ¿Está seguro que desea eliminar la rutina "+ nombre + "?",
            buttons: "[No][Si]"
        }, function(e) {
            "Si" == e &&
            $.ajax({
                url: _ctx + "gestion/rutina-plantilla/eliminar",
                type: "DELETE",
                dataType: 'json',
                blockLoading: false,
                noOne: true,
                data: obj,
                success: function(e) {
                    obtenerRutinas(categoriaId);
                },
                error: function(e) {
                    console.log(e)
                }
            })
        })
        $('.MsgTitle').css('display','flex');
    }
}


function subCategoriaClickEvent(e){
    const input = e.target;
    const parentStarDOM = ($(input).parent().closest('.star'));
    const parentEditDOM = ($(input).parent().closest('.icon-edit'));
    const starElement = parentStarDOM[0];
    const editElement = parentEditDOM[0];
    if(starElement){

        const starClases = starElement.classList;
        const subcategoriaId =  Number(starElement.getAttribute('data-subcategoria-id'));
        actualizarFavoritoSubCategoria(subcategoriaId);

    /*    let dataCategoriaIx = dataCategorias.findIndex( e => e.id === categoriaId );
        if(starClases.contains('selected')){
            //  const categoriaId =  Number(starElement.getAttribute('data-categoria-id');
            starClases.remove('selected');
            let index = favoritos.findIndex( e => e === categoriaId);
            favoritos.splice(index,1);
            dataCategorias[dataCategoriaIx].favorito =  false;
        }else{
            starClases.add('selected');
            const categoriaId =  starElement.getAttribute('data-categoria-id');
            !favoritos.includes('categoriaId') ? favoritos.push(Number(categoriaId)) : null;
            dataCategorias[dataCategoriaIx].favorito =  true;
        }
        generarCategoriasPlantillas(dataCategorias); */
    }
    else if(editElement){

        const nombre = editElement.getAttribute("data-nombre");
        const subcategoriaId = editElement.getAttribute("data-subcategoria-id");
        const txtNombre = mdlEditarSubCat.querySelector('#nombreSubCategoriaEdit');
        txtNombre.value = nombre;

        console.log(nombre, subcategoriaId, txtNombre);

        btnEditarSubCat.setAttribute("data-subcategoria-id", subcategoriaId);
        btnEliminarSubCat.setAttribute("data-subcategoria-id", subcategoriaId);
        btnEliminarSubCat.setAttribute("data-nombre",nombre);

    }
}




function searchSvg(path){
    let svg = path;
    while(svg.tagName.toUpperCase() !== "SVG"){
        svg = svg.parentElement;
    }
    return svg;
}
function agregarRutina(){
    if( $('#nueva_rutina_pred_frm').valid()){
        const nombreRotulo = $('#nombreRutina').val();
        const semanas = $('#semanasRutina').val();
        const dias = Number($('#diasRutina').val())*7;
        const id = btnNuevaRutina.getAttribute('data-subcategoria-id');
        const obj = {
            nombre : nombreRotulo,
            totalSemanas : semanas,
            dias : semanas*7,
            anios : Math.floor(semanas*7/365),
            meses : Math.floor(semanas/4),
            subCategoriaPlantilla : id
        }
        console.log(obj);
        $.ajax({
            type: 'POST',
            url: _ctx + 'gestion/rutina-plantilla/agregar',
            dataType:'json',
            contentType: 'application/json',
            blockLoading: false,
            noOne: true,
            data : JSON.stringify(obj),
            success: (s) =>{
                obtenerRutinas(id);
                $(mdlAgregarRutina).modal('hide');
                $("html, body").animate({ scrollTop: $(document).height()}, 1000);
                document.getElementById("nueva_rutina_pred_frm").reset();
            },
            error: (xhr) =>{

                const mensaje = xhr.responseJSON.message;
                $.smallBox({
                    title: "RUNFIT",
                    content: '<p>'+mensaje+'</p>',
                    timeout: 4500,
                    color:  '#cc4d4d',
                    icon: "fa fa-exclamation-circle"
                })

            },
            complete: (c) =>{
            }
        });
    }
}
function actualizarRutina() {
    if ($('#editar_rutina_pred_frm').valid()) {
        const nombreRotulo = $('#nombreRutinaEdit').val();
        const id = btnEditarRutina.getAttribute('data-rutina-id');
        const categoriaId = btnNuevaRutina.getAttribute('data-categoria-id');
        const obj = {
            id: id,
            nombre: nombreRotulo,
            categoriaPlantilla: categoriaId
        }
        $.ajax({
            type: 'PUT',
            url: _ctx + 'gestion/rutina-plantilla/actualizar',
            dataType: 'json',
            contentType: 'application/json',
            blockLoading: false,
            noOne: true,
            data: JSON.stringify(obj),
            success: (s) => {
                obtenerRutinas(categoriaId);
                $(mdlEditarRutina).modal('hide');
            },
            error: (e) => {
            },
            complete: (c) => {
            }
        });
    }
}
function obtenerRutinas(catId){
    $.ajax({
        type: 'GET',
        url: _ctx + 'gestion/rutina-plantilla/listar',
        data: { catId: catId
        },
        dataType:'json',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        success: (data) => {
            generarRutinasCategoriaDOM(data);
            currentRutinasData = data;
        },
        error: (e) =>{
        },
        complete: (c) =>{
        }
    });
}
function generarRutinasCategoriaDOM(dataRutina){
    const ulListadoRutinas = document.getElementsByClassName('list-plan')[0];
    const mensaje = document.querySelector('section#info .mensaje-no-data');
    mensaje ? mensaje.parentNode.removeChild(mensaje) : null;
    ulListadoRutinas.innerHTML ="";
    if(dataRutina.length > 0) {
        dataRutina.forEach( (element) =>{
            const rutina = htmlStringToElement(`
                                       <li class="col-md-4">
                                        <img class="plantilla svg" src="/img/iconos-trainers/icon_leyenda.svg"> ${element.nombre.toUpperCase()}
                                        <a class="edit" data-rutina-id="${element.id}" data-rutina-sem="${element.totalSemanas}" data-rutina-nombre="${element.nombre}"><img class="svg" src="/img/iconos-trainers/icon_edit.svg"></a>
                                        <a class="delete" data-rutina-id="${element.id}" data-rutina-sem="${element.totalSemanas}" data-rutina-nombre="${element.nombre}"><img class="svg" src="/img/iconos-trainers/icon_trash.svg"></a>
                                       </li>
          `);
            ulListadoRutinas.appendChild(rutina);
        })
        imgtoSvgEvent();
    }else{
        let buscador = $('#inpSearch').val();
        let mensaje;
        if(buscador.length > 0)
        {
            mensaje = htmlStringToElement(`
                                    <p class="mensaje-no-data"> No se encontró resultados para su búsqueda
                                    </p>
                   `);
        }else{
            mensaje = htmlStringToElement(`
                                    <p class="mensaje-no-data"> No cuenta con alguna plantilla asociada a esta subcategoría.
                                       Dele click al botón de Agregar para comenzar a crearla.
                                    </p>
                   `);
        }
        dvListaRutinas.appendChild(mensaje);
    }
}
function constraintsValidation()
{
    $('#nueva_categoria_frm').validate({
        rules:{
            nombreCategoria : {
                required: true,
                rangelength: [1, 30]
            },
            cbNuevaCategoria :{
                required: true
            }
        }

    });
    $('#editar_categoria_frm').validate({
        rules:{
            nombreCategoriaEdit : {
                required: true,
                rangelength: [1, 30]

            } , cbEditarCategoria :{
                required: true
            }
        }
    });
    $('#nueva_rutina_pred_frm').validate({
        rules:{
            nombreRutina : {
                required: true,
                maxlength : 30
            },
            semanasRutina : {
                required: true,
                range: [6, 84]
            }
        }
    });
    $('#editar_rutina_pred_frm').validate({
        rules:{
            nombreRutinaEdit :{
                required: true,
                maxlength : 30
            }
        }


    });
}

function searchRutinaEvent(filterInp){
    if(filterInp.data) {
        let filterText = $('#inpSearch').val().toUpperCase();
        const filteredData = currentRutinasData.filter( (el,index) => el.nombre.toUpperCase().indexOf(filterText) > -1);
        generarRutinasCategoriaDOM(filteredData);
    }else{
        generarRutinasCategoriaDOM(currentRutinasData);
    }
}
