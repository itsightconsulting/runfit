//const posts = document.querySelector('#dvPosts');
//const clienteId = Number(document.querySelector('head[data-id]').getAttribute('data-id'));
let allPosts = [];
let listaConsejosTextos = [];
let listaConsejosAudios = [];
let listaConsejosFavoritos = [];
const body2 = $('body');
const btnAudiosList = $('#btnAudiosList');
const btnPostsList = $('#btnPostsList');
const btnFavsList = $('#btnFavsList');
const ulConsejos = $('section.consejos .resultados ul');
let currentTipo = 1;
const btnBuscarConsejo = $('#btnSearch');


$(function () {

    
    listarConsejoEntrenador();
    btnAudiosList.addClass("selected")
    btnAudiosList.click(function(){
        currentTipo  !== 1 ? ( currentTipo = 1 , btnAudiosList.addClass("selected")
            , btnPostsList.removeClass("selected")  ): null ;
        generarListaAudios(listaConsejosAudios);

    });
    btnPostsList.click(function(){
        currentTipo  !== 2 ? ( currentTipo = 2 , btnPostsList.addClass("selected")
            , btnAudiosList.removeClass("selected")  ): null;
        generarListaTextos(listaConsejosTextos);
    });

    btnFavsList.click(function(){
        currentTipo = 0;
        generarListaFavoritos(listaConsejosFavoritos);
    });
    body2.on('click', 'svg.material', ejecutarContenidoMultimediaEvent);
    body2.on('click', 'a.fav', actualizarFavoritoEvent )
    btnBuscarConsejo.click(buscarConsejoEvent);

});

function buscarConsejoEvent(){

    let resultado;
    const filterValue = $('#inpSearch').val().toLowerCase();

    if(currentTipo === 1){
        resultado = buscarConsejosxFiltro(listaConsejosAudios, filterValue);

        if(resultado.length > 0) {
            generarListaAudios(resultado);
        }
    }else if(currentTipo === 3){ // tipoConsejo = 3 (texto)
        resultado = buscarConsejosxFiltro(listaConsejosTextos, filterValue);
        if(resultado.length > 0) {
            generarListaTextos(resultado);
        }
    }else{ // tipoConsejo =  0 (favoritos)
        resultado = buscarConsejosxFiltro(listaConsejosFavoritos, filterValue);
        if(resultado.length > 0){
            generarListaFavoritos(resultado);
        }
   }
}

function buscarConsejosxFiltro(arr, filterValue){
    const filteredData =   arr.filter( ({titulo}) => titulo.toLowerCase().indexOf(filterValue) > -1);

    if(filteredData.length ===0){
        ulConsejos.html("");
        const mensaje = htmlStringToElement(`<span class="msj-no-data" style="color:#ffffff"> No se encontraron resultados para su búsqueda </span>`);
        ulConsejos.parent().append(mensaje);
    }
    return filteredData;
}

function ejecutarContenidoMultimediaEvent(e){

    const input = e.currentTarget;
    const inpClasses = input.classList;

    if(inpClasses.contains("voice"))
    {   mostrarReproductorAudio(input); // muestra y reproduce audio
    }else if(inpClasses.contains("text")){
        const titulo = input.getAttribute("data-titulo");
        const descripcion = input.getAttribute("data-descripcion");
        const id = input.getAttribute("data-id");
        $('#mdlTxtTitulo').val(titulo);
        $('#mdlTxtDescripcion').val(descripcion);
          abrirMdlVerConsejoTxt();
    }
}

function mostrarReproductorAudio(input) {
    //$("#span"+id).html("");
    // let input = $("#span"+id);
    // input.html("");
    $(".divAudio").remove();

    const mediaAudio = input.getAttribute("data-media");
    const id = input.getAttribute("data-id");
    const listItemElement = $(input).parent();
    var ruta = `https://s3-us-west-2.amazonaws.com/rf-trainer-post/audio/${id}/${mediaAudio}`;
    console.log(ruta);
    let html =htmlStringToElement(`<div id="divaudio${id}" class="divAudio" style="text-align: center;     width: unset;padding-left: unset;position: absolute;z-index: 9;">
               <button id="btnCerrarAudio" data-id="${id}" type="button" style="color: #ffffffff; opacity: .8;" class="close" data-dismiss="modal" aria-label="Close" ><span aria-hidden="true">&times;</span></button>
                       <audio id="someaud" preload="none" controls="" controlsList="nodownload" autoplay="" width="100%" height="100%">
                       <source id="AudioReproduccion" src="${ruta}" type="audio/mpeg"/></audio>
                </div>`);
    listItemElement.prepend(html);

    var x = $(input).position().top;
    $('.divAudio').css({'top' : (x + 50) + 'px'});

    $("#btnCerrarAudio").click(function (){
        $(".divAudio").remove();
    });

}

function actualizarFavoritoEvent(e) {

    const input = e.currentTarget;
    const consejoId = Number(input.getAttribute('data-id'));
    const inpClasses  = input.classList;
    const favStatus = inpClasses.contains('selected') ? 1 : 0;
    actualizarEstadoFavorito(consejoId , favStatus, input);
}

function getNombresCompletosQuienesTieneLikeEnPost(postId, clases){
    const post = allPosts.find(v=>v.id == postId);
    if(post != undefined){
        if(clases.contains('btn-primary'))
            return post.lstDetalle.filter(v=>v.cliId!=clienteId).map(v=>{if(v.flgLiked) return v.nomFull}).sort().join('<br/>');
            return post.lstDetalle.map(v=>{if(v.flgLiked) return v.nomFull}).sort().join('<br/>');
    }
    return "";
}

function listarConsejoEntrenador() {

    $.ajax({
        type: 'GET',
        url: _ctx + 'cliente/get/consejos',
        dataType: "json",
        success: function (data, textStatus) {
            debugger
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                } else {
                   // if(data.length>0) {
                        //var $div = $(".list-cards");
                        //$div.html("");
                           const infoConsejos = data.sort( (a,b) =>  b.id - a.id);
                           listaConsejosAudios  = infoConsejos.filter( e => e.tipo === 2);
                           listaConsejosTextos  = infoConsejos.filter( e => e.tipo === 3);
                           const arrIdsPostsFavoritos = $('#arrPostsFavs').val().split(",").map( elem => Number(elem));
                           const postsFavoritos = infoConsejos.filter( e =>  arrIdsPostsFavoritos.includes(e.id));
                           listaConsejosFavoritos = postsFavoritos;
                          // generarListaFavoritos(postsFavoritos);

                           if(currentTipo === 1){
                               generarListaAudios(listaConsejosAudios);
                           }else if(currentTipo === 2){
                               generarListaTextos(listaConsejosTextos);
                           }else{
                               generarListaFavoritos(listaConsejosFavoritos);
                           }
                           // allPosts = data ;//Global
                       /* $.each(data.sort((a, b)=>{return parseFromStringToDateTime(b.fechaCreacion).getTime() - parseFromStringToDateTime(a.fechaCreacion).getTime();}),(i, item)=>{
                            $div.append(GenerarDiv(item));
                        });*/
              //      }
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () { }
    });
}

function actualizarEstadoFavorito(id, favStatus,input) {
    let params = {};
    params.postId = id;
    params.estado = favStatus;

    console.log(params);
    $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "cliente/post/updateFavorito",
        dataType: "json",
        data: params,
        success: function (data) {
         debugger
          if( data === -1 || data === -2){

              if(favStatus  === 1){
                  const ix = listaConsejosFavoritos.findIndex( e => e.id === id );
                  listaConsejosFavoritos.splice(ix,1);
                  input.classList.remove('selected');
              }else{
                    if(currentTipo === 1){
                        const consejo = listaConsejosAudios.find( e => e.id === id);
                        listaConsejosFavoritos.push(consejo);
                    }else{
                        const consejo = listaConsejosTextos.find( e => e.id === id);
                        listaConsejosFavoritos.push(consejo);
                    }

                    input.classList.add('selected');
              }
          }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
        }
    })
}

function generarListaAudios(listaAudios) {

    debugger
    $('.msj-busqueda').remove();
    $('#inpSearch').val("");
    ulConsejos.html("");
    $('.msj-no-data').remove();

    if(listaAudios.length > 0) {


        const idsFavoritos = listaConsejosFavoritos.length > 0 ? listaConsejosFavoritos.map(e => e.id) : '';
       $.each(listaAudios, function (i, item) {
            let itemAudio;
            const duracionArr = item.duracion.split(':');

            if (idsFavoritos.includes(item.id)) {

                itemAudio = `  <li> <img class="material voice svg" data-id="${item.id}" data-media="${item.rutaWeb}" src="/img/iconos/icon_microfono.svg"> 
                              <span>${item.titulo}</span> 
                              <div class="pull-right">
                                <div class="time"><img class="svg" src="/img/iconos/icon_time.svg"><span>${duracionArr[1] + "'"} ${duracionArr[2] + '"'} </span></div><a class="fav selected" data-id="${item.id}"><img class="svg" src="/img/iconos/icon_heart.svg"></a>
                             </div>
                           </li>
                         `;
            } else {

                itemAudio = `  <li> <img class="material voice svg" data-id="${item.id}" data-media="${item.rutaWeb}" src="/img/iconos/icon_microfono.svg"> 
                              <span>${item.titulo}</span> 
                              <div class="pull-right">
                                <div class="time"><img class="svg" src="/img/iconos/icon_time.svg"><span>${duracionArr[1] + "'"} ${duracionArr[2] + '"'} </span></div><a class="fav" data-id="${item.id}"><img class="svg" src="/img/iconos/icon_heart.svg"></a>
                             </div>
                           </li>
                         `;
            }
            ulConsejos.append(itemAudio);

        });

    }else{
        debugger
        const mensajeNoData = htmlStringToElement(`<span class="msj-no-data"> El entrenador no cuenta con clips de audio para consultar. </span> `);
        ulConsejos.parent().append(mensajeNoData);
    }

    imgtoSvgEvent()
    $('[rel="tooltip"]').tooltip();
}

function generarListaTextos(listaTextos) {

     ulConsejos.html("");

     $('.msj-no-data').remove();

     $('#inpSearch').val("");

    if(listaTextos.length >0) {

        const idsFavoritos = listaConsejosFavoritos.length > 0 ? listaConsejosFavoritos.map(e => e.id) : ''
        $.each(listaTextos, function (i, item) {
            const id = item.id;
            const titulo = item.titulo;
            const descripcion = item.descripcion;

            let itemTexto;
            if (idsFavoritos.includes(id)) {
                itemTexto = `  <li> <img class="material text svg" src="/img/iconos/icon_edit_file.svg"   data-id="${item.id}" data-titulo = "${titulo}" data-descripcion ="${descripcion}" >
                                  <span style="width: 13.7em; display: inline-block;">${titulo}</span>
                                <div class="pull-right" >
                                  <a class="fav selected"  data-id="${id}"><img class="svg" src="/img/iconos/icon_heart.svg" ></a>
                                </div>
                          </li>  `;
            } else {
                itemTexto = `  <li> <img class="material text svg" src="/img/iconos/icon_edit_file.svg"   data-id="${item.id}" data-titulo = "${titulo}" data-descripcion ="${descripcion}" >
                                  <span style="width: 13.7em; display: inline-block;">${titulo}</span>
                                                               
                                <div class="pull-right" >
                                  <a class="fav"  data-id="${id}"><img class="svg" src="/img/iconos/icon_heart.svg" ></a>
                                </div>
                              </li>  `;
            }
            ulConsejos.append(itemTexto);

        });
    }else{
        const mensajeNoData = htmlStringToElement(`<span class="msj-no-data"> El entrenador no cuenta con  notas de texto para consultar. </span> `);
        ulConsejos.parent().append(mensajeNoData);
    }
       imgtoSvgEvent();
       $('[rel="tooltip"]').tooltip();
}


function generarListaFavoritos(listaConsejosFavs) {

    $('#inpSearch').val("");
    ulConsejos.html("");
    $('.msj-no-data').remove();

    if(listaConsejosFavs.length > 0) {

        $.each(listaConsejosFavs,function (i,item) {
            let itemFav;

            if(item.tipo === 1){
                const duracionArr = item.duracion.split(':');
                itemFav = `<li> <img class="material voice svg" data-id="${item.id}" data-media="${item.rutaWeb}" src="/img/iconos/icon_microfono.svg"> 
                              <span>${item.titulo}</span> 
                              <div class="pull-right">
                                <div class="time"><img class="svg" src="/img/iconos/icon_time.svg"><span>${duracionArr[1]+"'"} ${duracionArr[2] + '"'} </span></div><a class="fav selected" data-id="${item.id}"><img class="svg"  src="/img/iconos/icon_heart.svg" ></a>
                             </div>
                            </li>
                         `;
            }else{

                const id = item.id;
                const titulo = item.titulo;
                const descripcion = item.descripcion;
                itemFav = `  <li> <img class="material text svg" src="/img/iconos/icon_edit_file.svg"   data-id="${item.id}" data-titulo = "${titulo}" data-descripcion ="${descripcion}" >
                                  <span style="width: 13.7em; display: inline-block;">${titulo}</span>
                                                               
                                <div class="pull-right" >
                                  <a class="fav selected" data-id = "${item.id}"><img class="svg" src="/img/iconos/icon_heart.svg"></a>
                                </div>
                             </li>     
                          `;
            }

            ulConsejos.append(itemFav);

        });

    }else{
        const mensajeNoData = htmlStringToElement(`<span class="msj-no-data"> Su lista de consejos favoritos se encuentra vacía. </span> `);
        ulConsejos.parent().append(mensajeNoData);
    }

    imgtoSvgEvent()
    $('[rel="tooltip"]').tooltip();
}



function abrirMdlVerConsejoTxt(){

    $('#mdlTxtTitulo').prop('readonly',true);
    $('#mdlTxtDescripcion').prop('readonly',true);
    $('#mdlVerConsejoTexto').modal('show'); //lanza modal
}
