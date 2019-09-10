const $tabla = $("#tbVideoAudio");
const $tablatop = $("#tbTop");
const body2 = $('body');
const ulConsejos = $('section.consejos .resultados ul');
const ulConsejosFavoritos = $('section.consejos-favs .resultados ul');
const btnAudiosList = $('#btnAudiosList');
const btnPostsList = $('#btnPostsList');
const btnGuardarConsejos = $('#btnGuardarConsejo');
const btnTipoConsejoRegistro = $('#mdlAgregarConsejo a');
const btnEditarConsejoTxt = $('#btnEditarConsejoTxt');
const btnEditarConsejoAudio = $('#btnEditarConsejoAudio');
const btnActualizarConsejoTxt = $('#btnActualizarConsejoTxt');
const btnActualizarConsejoAudio = $('#btnActualizarConsejoAudio');
const btnBuscarConsejo = $('#btnSearch');
let LISTA_FAVORITOS_VIDEO = [];
let LISTA_FAVORITOS_AUDIO = [];
let LISTA_FAVORITOS_TEXTO = [];
let TIEMPO = "";
let PESO = "";
let tipoConsejoRegistro = 1;
let currentTipoConsejoConsulta = 1;

$(function () {

     obtenerData(1);
     body2.on('click', 'svg.material', ejecutarContenidoMultimediaEvent);
     body2.on('click', 'a.delete svg', desactivarConsejoEvent);
     body2.on('click', 'a.edit-audio' , abrirMdlVerDatosConsejoAudio);
     body2.on('click', 'a.edit-text' , abrirMdlVerConsejoTxt);
     btnEditarConsejoTxt.click(editarConsejoTxtEvent);
     btnEditarConsejoAudio.click(editarConsejoAudioEvent);
     btnTipoConsejoRegistro.click(seleccionTipoConsejoEvent);
     btnAudiosList.click(function(){

            currentTipoConsejoConsulta = 2
            generarListaAudios(LISTA_FAVORITOS_AUDIO);
     });
     btnPostsList.click(function(){
            currentTipoConsejoConsulta = 1
            generarListaTextos(LISTA_FAVORITOS_TEXTO);
     });
     btnActualizarConsejoTxt.click(actualizarConsejoTextoEvent);
     btnActualizarConsejoAudio.click(actualizarConsejoAudioEvent);
     btnBuscarConsejo.click(buscarConsejoEvent);

     btnGuardarConsejos.click(guardarConsejoEvent);
     validacionFormsConsejos();


    $(".abtn-multimedia").click(function () {

        if ($(this).hasClass("btn-success")) {

        }else{
            $(".abtn-multimedia").removeClass("btn-success");
            $(".abtn-multimedia").removeClass("btn-danger");
            $(".abtn-multimedia").addClass("btn-danger");


            $(".spanbtn-multimedia").removeClass("text-success");
            $(".spanbtn-multimedia").removeClass("text-danger");
            $(".spanbtn-multimedia").addClass("text-danger");


            $(this).removeClass("btn-danger");
            $(this).addClass("btn-success");


            $(this).children().removeClass("text-danger");
            $(this).children().addClass("text-success");
        }

        //     $("#subirArchivo").show();
        //    $("#registroTexto").hide();
        $(".input-ghost").val(null);
        $("#inputFile").val("");
        $("#tTitulo").val("");
        $("#tDescripcion").val("");

        $(".input-ghost").attr("accept",".mp3");
    /*     if($(this).attr("data-id") == "1"){ // 1 = TIPO AUDIO
            $(".input-ghost").attr("accept","audio/mp3");
        }else if($(this).attr("data-id") == "2"){ // 2 = TIPO VIDEO
            $(".input-ghost").attr("accept","video/mp4,video/x-m4v,video/*");
        }else if($(this).attr("data-id") == "3"){ // 3 = TIPO TEXTO
            $("#registroTexto").show();
            $("#registrar-audio-frm").hide();
            $(".input-ghost").attr("accept","text/plain");
        }
    */

    });

    bs_input_file();

    $('#Tiempo').on('canplaythrough', function (e) {
        TIEMPO = String(Math.round(e.currentTarget.duration)).toHHMMSS();
    });
    $('#Tiempo2').on('canplaythrough', function (e) {
        TIEMPO = String(Math.round(e.currentTarget.duration)).toHHMMSS();
    });
    subirChangeEvent();

});

function seleccionTipoConsejoEvent(e){
    const input = e.currentTarget;
    const tipoSeleccionado = parseInt(input.getAttribute('data-tipo'));
    tipoConsejoRegistro =  tipoSeleccionado;
}

function ejecutarContenidoMultimediaEvent(e){

    const input = e.currentTarget;
    const inpClasses = input.classList;
    
    if(inpClasses.contains("voice"))
    { mostrarReproductorAudio(input); // muestra y reproduce audio
    }else if(inpClasses.contains("text")){
      abrirMdlVerConsejoTxt(e);
    }


}


function guardarConsejoEvent(){

  if(tipoConsejoRegistro === 1){
     if($('#registrar-texto-frm').valid()){
         registrarMultimedia(0);
     }
  }else{
      if($('#registrar-audio-frm').valid()){
          registrarMultimedia(0);
      }
  }
}

function desactivarConsejoEvent(e){

    const input = e.currentTarget;
    const id = parseInt(input.getAttribute("data-id"));
    desactivarConsejo(id);
}

function editarConsejoTxtEvent(e){

    btnEditarConsejoTxt.addClass('hidden');
    $('#dvBtnsActualizar').removeClass('hidden');
    $('#mdlTxtTitulo').prop('readonly',false);
    $('#mdlTxtDescripcion').prop('readonly',false)

}

function editarConsejoAudioEvent(e){

     btnEditarConsejoAudio.addClass('hidden');
    $('#dvBtnsActualizarAudio').removeClass('hidden');
    $('#mdlAudioTitulo').prop('readonly',false);
    $('#mdlAudioFile').prop('readonly',false)
    $('#mdlVerConsejoAudio  .input-file').closest('.form-group').removeClass('hidden')


}

function abrirMdlVerConsejoTxt(e){

    const input =e.currentTarget;
    const titulo = input.getAttribute("data-titulo");
    const descripcion = input.getAttribute("data-descripcion");
    const id = input.getAttribute("data-id");
    $('#mdlTxtTitulo').val(titulo);
    $('#mdlTxtDescripcion').val(descripcion);
    btnActualizarConsejoTxt.attr('data-id', id);
    btnEditarConsejoTxt.removeClass('hidden');
    $('#dvBtnsActualizar').addClass('hidden');
    $('#mdlTxtTitulo').prop('readonly',true);
    $('#mdlTxtDescripcion').prop('readonly',true);
    $('#mdlVerConsejoTexto').modal('show'); //lanza modal
}

function abrirMdlVerDatosConsejoAudio(e){

    const input =e.currentTarget;
    const id = input.getAttribute('data-id');
    const titulo = input.getAttribute('data-titulo');
    btnEditarConsejoAudio.removeClass('hidden');
    $('#dvBtnsActualizarAudio').addClass('hidden');
    $('#mdlAudioTitulo').prop('readonly',true);
    $('#mdlAudioTitulo').val(titulo);
    $('#mdlAudioFile').val('');
    btnActualizarConsejoAudio.attr('data-id',id);
    $('#mdlVerConsejoAudio  .input-file').closest('.form-group').addClass('hidden');
    $('#mdlVerConsejoAudio').modal('show'); //lanza modal

}

function actualizarConsejoAudioEvent(e){

   const input = e.target;
   const id = input.getAttribute('data-id');

   if($("#actualizar-audio-frm").valid()){

       registrarMultimedia(id);
   }

}

function actualizarConsejoTextoEvent(e){

    const input = e.target;
    const id = input.getAttribute('data-id');

    if($("#actualizar-texto-frm").valid()){

        registrarMultimedia(id);
    }

}

function buscarConsejoEvent(){

    let resultado;
    const filterValue = $('#inpSearch').val().toLowerCase();

    if(tipoConsejoRegistro === 1){
     resultado = buscarConsejosxFiltro(LISTA_FAVORITOS_TEXTO, filterValue);
     generarListaTextos(resultado);

   }else{ // tipoConsejoRegistro = 2
     resultado = buscarConsejosxFiltro(LISTA_FAVORITOS_AUDIO, filterValue);
     generarListaAudios(resultado);
   }



}

function buscarConsejosxFiltro(arr, filterValue){

  const filteredData =   arr.filter( ({titulo}) => titulo.toLowerCase().indexOf(filterValue) > -1);

  if(filteredData.length ===0){

    ulConsejos.html("");

    const mensaje = htmlStringToElement(`<span class="msj-busqueda"> No se encontraron resultados para su búsqueda </span>`);

    ulConsejos.parent().append(mensaje);

  }

  return filteredData;
}


function subirChangeEvent() {
    $(".input-ghost").change(function () {
        let file;
        if ((file = this.files[0])) {
            let obUrl;
            if (file.name === 'mp3') {
                objUrl = URL.createObjectURL(file);
                document.getElementById('Tiempo').setAttribute('src', objUrl);
                document.getElementById('Tiempo2').setAttribute('src', objUrl);
                PESO = formatBytes(file.size, 2);
            }
        }
    });
}


function agregarMultimedia() {
    $('#mdlAgregarConsejo').modal("show")
}

function obtenerData(flag) {
    LISTA_FAVORITOS_VIDEO = [];
    LISTA_FAVORITOS_AUDIO = [];
    LISTA_FAVORITOS_TEXTO = [];
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: _ctx + "gestion/trainer/obtenerListadoPost",
        dataType: "json",
        success: function (data) {

            console.log(data);
            if(data != null){
                $.each(data,function (i,item) {
                    if(item.tipo === 1){
                        LISTA_FAVORITOS_AUDIO.push(item);
                    } else if(item.tipo === 3) {
                        LISTA_FAVORITOS_TEXTO.push(item);
                    }
                });
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function (res) {

            if(flag == 1){
               generarListaTextos(LISTA_FAVORITOS_TEXTO);
            } else if(flag == 2){
                generarListaAudios(LISTA_FAVORITOS_AUDIO);
            }

            $('[rel="tooltip"]').tooltip();

            listarTopFavs(res.responseJSON);

        }

    });
}

function generarVideo(item,i) {

    var html = "<span> Duración : "+ item.duracion +"</span><br>";
    html += "<span> Peso : "+ item.peso +"</span>";

    return `<a style="font-size: 20px" href="javascript:verVideo(${item.id})" 
               data-placement="top" title="${html}" data-trigger="hover"
               data-html="true" rel="tooltip">
                <i data-placement="bottom" rel="tooltip"  class="reprod-video fa fa-video-camera fa-fw padding-top-3 rf-media" id="livideo${item.id}" data-media="${item.rutaWeb}"></i>
            </a>
            <span class="spvideo" id="span${item.id}" data-index="${i}" data-media="${item.rutaWeb}"></span>`;
}

function verVideo(id) {
    $("#span"+id).html("");
    let input = $("#span"+id);
    input.html("");
    $(".divVideo").remove();
    const mediaVideo = input.attr("data-media");
    var ruta = `${_ctx}workout/multimedia/video${mediaVideo}`;
    let html =`<div id="divvideos${id}"  class="divVideo" style="text-align: center;     width: 50%;padding-left: 8%;position: absolute;z-index: 9;">
               <button id="btnCerrarVideo" data-id="${id}" type="button" style="margin: -19px;" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <video id="somevid" preload="none" controls="controls" autoplay="" controlslist="nodownload" width="100%" height="100%" style="margin: -6px;border: 5px solid #e8f2f3; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                            <source id="VideoReproduccion" src="${ruta}" type="video/mp4">
                        </video>
                </div>`;

    input.append(html);

    var x = $('#span'+id).position().top;
    $('.divVideo').css({'top' : (x-100) + 'px'});

    //$("#btnCerrarVideo"+id).off("click");
    //$("#btnCerrarVideo"+id).click(function (){
    //    const idVideo = $("#btnCerrarVideo"+id).attr("data-id");
    //    let input = $("#divvideos"+idVideo);
    //    input.remove();
    //});

    $("#btnCerrarVideo").click(function (){
        $(".divVideo").remove();
    });

}

function generarListaAudios(listaAudios) {
    tipoConsejoRegistro = 2;
    $('.msj-no-data').remove();
    $('#inpSearch').val("");
    ulConsejos.html("");
    if(listaAudios.length > 0) {

       $.each(listaAudios, function (i, item) {

            const duracionArr = item.duracion.split(':');
            let itemAudio = `  <li> <img class="material voice svg" data-id="${item.id}" data-media="${item.rutaWeb}" src="/img/iconos/icon_microfono.svg"> 
                                <a class="edit-audio" data-id="${item.id}" data-titulo="${item.titulo}"> <span>${item.titulo}</span></a> 
                              <div class="pull-right">
                                <div class="time"><img class="svg" src="/img/iconos/icon_time.svg"><span>${duracionArr[1] + "'"} ${duracionArr[2] + '"'} </span></div><a class="delete"><img class="svg" src="/img/iconos/icon_trash.svg" data-id="${item.id}"></a>
                             </div>
                          </li>
                         `;
            ulConsejos.append(itemAudio);

        });

    }else{
        const mensajeNoData = htmlStringToElement(`<span class="msj-no-data"> Su lista de consejos en formato clip de audio se encuentra vacía. </span> `);
        ulConsejos.parent().append(mensajeNoData);

    }
    imgtoSvgEvent()
    $('[rel="tooltip"]').tooltip();
}

function generarListaTextos(listaTextos) {
    debugger
    tipoConsejoRegistro = 1;
    ulConsejos.html("");
    $('#inpSearch').val("");
    $('.msj-no-data').remove();

    if(listaTextos.length>0) {
        $.each(listaTextos, function (i, item) {
            const id = item.id;
            const titulo = item.titulo;
            const descripcion = item.descripcion;
            const descripcionResumen = descripcion.length > 120 ? (descripcion.substr(0, 120) + ' ...') : descripcion;
            let itemTexto = `  <li> <img class="material text svg" src="/img/iconos/icon_edit_file.svg"   data-id="${item.id}" data-titulo = "${titulo}" data-descripcion ="${descripcion}" >
                                 <a class="edit-text" data-id="${item.id}" data-titulo="${item.titulo}" data-descripcion = "${descripcion}">
                                  <span style="width: 13.7em; display: inline-block;">${titulo}</span>
                                 </a> 
                                 <div class="txt-descripcion">
                                  <span> ${descripcionResumen}</span>
                                 </div>
                                <div class="pull-right" style="display: flex;">
                               
                                <a class="delete" style ="display: flex; align-items: center;"><img class="svg" style="top:unset;" src="/img/iconos/icon_trash.svg" data-id="${id}"></a>
                             </div>
                </li>     
        
                         `;
            ulConsejos.append(itemTexto);
        });
    }else{
        const mensajeNoData = htmlStringToElement(`<span class="msj-no-data"> Su lista de consejos en formato nota de texto se encuentra vacía. </span> `);
        ulConsejos.parent().append(mensajeNoData);
    }
    imgtoSvgEvent();
    $('[rel="tooltip"]').tooltip();
}


function generarListaTopFavoritos(listaConsejos) {

    ulConsejosFavoritos.html("");
    $('section.consejos-favs .msj-no-data').remove();
    const sumaCantidadFavs = listaConsejos.reduce((a, b) => a + (b['cantidadFavs'] || 0), 0);

    if(listaConsejos.length > 0 && sumaCantidadFavs > 0) {
        $.each(listaConsejos, function (i, item) {
            const id = item.id;
            const titulo = item.titulo;
            const tipo = item.tipo;
            let itemTop;

            if (i < 9 && item.cantidadFavs > 0) {
                if (tipo === 1) { //audio

                    const duracionArr = item.duracion.split(':');
                    itemTop = `  <li> <img class="material voice svg" data-id="${item.id}" data-media="${item.rutaWeb}" src="/img/iconos/icon_microfono.svg"> 
                               <span style="width: 13.7em; display: inline-block;">${titulo}</span>
                               <div class="pull-right">
                                 <a class="fav selected"><img class="svg" src="/img/iconos/icon_heart.svg" data-id="${item.id}"></a>
                                <span class="cantidad-favs"> ${item.cantidadFavs} </span>    
                               </div>
                          </li>
                         `;

                } else {   //texto
                    const descripcion = item.descripcion;
                    const descripcionResumen = descripcion.length > 120 ? (descripcion.substr(0, 120) + ' ...') : descripcion;
                    itemTop = ` <li> <img class="material text svg" src="/img/iconos/icon_edit_file.svg"   data-id="${item.id}" data-titulo = "${titulo}" data-descripcion ="${descripcion}" >
                               <span style="width: 13.7em; display: inline-block;">${titulo}</span>
                               <div class="pull-right">
                                 <a class="fav selected"><img class="svg" src="/img/iconos/icon_heart.svg" data-id="${item.id}"></a>
                                <span class="cantidad-favs"> ${item.cantidadFavs} </span>    
                               </div>
                         </li>`;
                }
            } else {
                return false;
            }
            ulConsejosFavoritos.append(itemTop);
        });
    }else{
        const mensajeNoData = htmlStringToElement(`<span class="msj-no-data"> Aún no cuenta con consejos dentro de la lista de favoritos de sus clientes. Es cuestión de tiempo :). </span> `);
        ulConsejosFavoritos.parent().append(mensajeNoData);
    }
    imgtoSvgEvent();
   // $('[rel="tooltip"]').tooltip();
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

function eliminarVideoAudio(item,item2, item3) {
    if(item3 == "0") {
        return '<a style="font-size: 20px" data-placement="top" title="Eliminar." data-trigger="hover" data-html="true" rel="tooltip" href="javascript:eliminarAudioVideoFavorito(' + item + ',' + item2 + ',0)"><i class="fa fa-trash"></i></a>';
    }else{
        return '<a style="font-size: 20px" data-placement="top" title="No se puede eliminar porque está siendo usado." data-trigger="hover" data-html="true" rel="tooltip"><i class="fa fa-trash disabled"></i></a>';
    }
}

function desactivarConsejo(id) {


    let params ={};
    params.id =id;


    $.SmartMessageBox({
        title: "<i class='fa fa-2x fa-trash' style='display:flex; align-items: center; margin-right:0.7em;'></i>¿Está seguro que desea eliminar este consejo?",
        buttons: "[No][Si]"
    }, function(e) {
        "Si" == e &&
        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            url: _ctx + "gestion/consejo/desactivar",
            dataType: "json",
            data: params,
            success: function (data, textStatus) {
                obtenerData(currentTipoConsejoConsulta);
            },
            error: function (xhr) {
                exception(xhr);
            },
            complete: function () {
                    $.smallBox({content: "<i class='fa fa-child'></i> <i>Se actualizó satisfactoriamente...!</i>",});
            }
        });

    })


}

function formatterTexto(id,item2) {
    if(item2 == "0") {
        return '<a style="font-size: 20px" data-placement="top" title="Eliminar." data-trigger="hover" data-html="true" rel="tooltip" href="javascript:eliminarAudioVideoFavorito(0,0,' + id + ')"><i class="fa fa-trash"></i></a>';
    }else{
        return '<a style="font-size: 20px" data-placement="top" title="No se puede eliminar porque está siendo usado." data-trigger="hover" data-html="true" rel="tooltip"><i class="fa fa-trash disabled"></i></a>';
    }
}

function bs_input_file() {
    $(".input-file").before(
        function() {
            if (!$(this).prev().hasClass('input-ghost') ) {
                let element = $("<input type='file' accept='.mp3' class='input-ghost' style='visibility:hidden; height:0'>");
                element.attr("name",$(this).attr("name"));
                element.change(function(){
                    element.next(element).find('input').val((element.val()).split('\\').pop());
                });
                $(this).find("button.btn-choose").click(function(){
                    element.click();
                    console.log("aaa");
                });
                $(this).find("button.btn-reset").click(function(){
                    element.val(null);
                    $(this).parents(".input-file").find('input').val('');
                });
                $(this).find('input').css("cursor","pointer");
                $(this).find('input').mousedown(function() {
                    $(this).parents('.input-file').prev().click();
                    return false;
                });
                return element;
            }
        }
    );
}

function demo(id){
    $.ajax({
        type: "GET",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/consejo/get?id="+id,
        dataType: "json",
        success: function (res) {
            console.log(res)
        },
        error: function (xhr) {
            console.log("ERROR", xhr);
            //exception(xhr);
        },
        complete: function () {}
    });
}

function registrarMultimedia(id) {
    const multimedia = id > 0 ? $(".input-ghost").get(1) : $(".input-ghost").get(0) ;
    const files = multimedia.files.length == 0 ? null : multimedia.files[0];
    //const tipo = $(".abtn-multimedia.btn-success").attr("data-id");

    if (files != null) {
        debugger
        const inpTitulo = id > 0 ? $('#mdlAudioTitulo').val()  : $('#inpTitulo').val();
        var data = new FormData();
        data.append("multimedia", files);
        data.append("id", id);
        let params = {};
        params.id = id;
        params.tipo = tipoConsejoRegistro;
        params.peso = PESO;
        params.duracion = TIEMPO;
        params.titulo = inpTitulo;
        params.descripcion = inpTitulo;
        data.append("postString", JSON.stringify(params));

      $.ajax({
            type: 'POST',
            url: _ctx + 'gestion/consejo/upload/audio',
            data: data,
            contentType: false,
            processData: false,
            xhr: function () {
                var myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) {
                    myXhr.upload.addEventListener('progress', progress, false);
                }
                return myXhr;
            },
            success: function (data, textStatus) {

                console.log("lalaal",tipoConsejoRegistro);
                subirAudio(data,id);


            },
            error: function (xhr) {
                exception(xhr);
            },
            complete: function () {
               $('#registrar-audio-frm').reset();
               $('#actualizar-audio-frm').reset();
               obtenerData(tipoConsejoRegistro);

                if(id > 0){
                    $("#mdlTxtTitulo").val("");
                    $("#mdlTxtDescripcion").val("");
                    $('#mdlVerConsejoTexto').modal("hide");
                    $.smallBox({content: "<i class='fa fa-child'></i> <i>Se actualizó satisfactoriamente...!</i>",});

                }else{
                    $("#tTitulo").val("");
                    $("#tDescripcion").val("");
                    $('#mdlAgregarConsejo').modal("hide");
                    $.smallBox({content: "<i class='fa fa-child'></i> <i>Se registró satisfactoriamente...!</i>",});

                }

            }
        });

    }else{
        if(tipoConsejoRegistro == 1) {

            let params = {};

            params.id = id;

            if(id > 0){
                params.titulo = $("#mdlTxtTitulo").val();
                params.descripcion = $("#mdlTxtDescripcion").val();

            }else{
                params.titulo = $("#tTitulo").val();
                params.descripcion = $("#tDescripcion").val();
            }

            $.ajax({
                type: "POST",
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                url: _ctx + 'gestion/consejo/upload/text',
                dataType: "json",
                data: params,
                success: function (data, textStatus) {

                    if(id > 0){
                       $("#mdlTxtTitulo").val("");
                       $("#mdlTxtDescripcion").val("");
                       $('#mdlVerConsejoTexto').modal("hide");
                       $.smallBox({content: "<i class='fa fa-child'></i> <i>Se actualizó satisfactoriamente...!</i>",});

                    }else{
                       $("#tTitulo").val("");
                       $("#tDescripcion").val("");
                       $('#mdlAgregarConsejo').modal("hide");
                        $.smallBox({content: "<i class='fa fa-child'></i> <i>Se registró satisfactoriamente...!</i>",});

                    }

                },
                error: function (xhr) {
                    exception(xhr);
                },
                complete: function () {
                    obtenerData(tipoConsejoRegistro);
                    $('#registrar-texto-frm').reset();
                    $('#actualizar-texto-frm').reset();

                }
            });
        }

    }
}

function subirAudio(d,id) {

    const dataRes = JSON.parse(d);
    const rdmUUID = dataRes.rdm;
    const audioId = dataRes.res;
    const audio = id > 0 ? $(".input-ghost").get(1) : $(".input-ghost").get(0);

    var data = new FormData();
    data.append("audio", audio.files[0]);
    data.append("audioId", audioId);
    data.append("randomUUID", rdmUUID);

    $.ajax({
        type: 'POST',
        url: _ctx + 'gestion/consejo/upload/aws/'+rdmUUID,
        data: data,
        contentType: false,
        processData: false,
        blockLoading: true,
        noOne: false,
        xhr: function () {
            var myXhr = $.ajaxSettings.xhr();
            if (myXhr.upload) {
                myXhr.upload.addEventListener('progress', progress, false);
            }
            return myXhr;
        },
        success: function (data) {
            console.log(data);
            // $.smallBox({});
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function (xhr) {
            /*      if(xhr.status === 200){
                      listarRegistros();
                      irListado($index);
                  }
                  $('#frm_registro input').removeAttr('readonly', true);
                  $('#frm_registro button').removeAttr('disabled', true);
      */
        }

    });
}



function listarTopFavs(data) {
  //  $tablatop.html("");

    if(data != null){
        data = data.filter(v=>v.lstDetalle != undefined && v.lstDetalle.length > 0).map(v=>{
            v.cantidadFavs = v.lstDetalle.map(v=>v.flgFav).filter(v=>v).length;
            return v;
        });
        data.sort((a, b)=> b.cantidadFavs - a.cantidadFavs);

        generarListaTopFavoritos(data);

    }
}

function sortByKeyDesc(array, key) {
    return array.sort(function (a, b) {
        var x = a[key]; var y = b[key];
        return ((x > y) ? -1 : ((x < y) ? 1 : 0));
    });
}
function sortByKeyAsc(array, key) {
    return array.sort(function (a, b) {
        var x = a[key]; var y = b[key];
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    });
}


function validacionFormsConsejos(){

    $("#registrar-audio-frm").validate({
        // Rules for form validation}
        rules: {
            titulo:{
                required: true,
                rangelength: [1, 30]
            },
            file:{
                required: true,
                extension: "mp3"
            }
        },
        // Messages for form validation
        messages: {
            file:{
                extension: "Por favor ingresa un archivo con extensión .mp3"
            }

        },
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }

    });

    $("#actualizar-audio-frm").validate({
        // Rules for form validation}
        rules: {
            titulo:{
                required: true,
                rangelength: [1, 30]
            },
            file:{
                required: true,
                extension: "mp3"
            }
        },
        // Messages for form validation
        messages: {
            file:{
                extension: "Por favor ingresa un archivo con extensión .mp3"
            }

        },
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }

    });

    //registrar-texto-frm

    $("#registrar-texto-frm").validate({
        // Rules for form validation}
        rules: {
            titulo:{
                required: true,
                rangelength: [1, 30]
            },
            descripcion:{
                required: true,
                rangelength: [1, 200]
            }
        },
        // Messages for form validation
        messages: {

        }

    });

    $("#actualizar-texto-frm").validate({
        // Rules for form validation}
        rules: {
            titulo:{
                required: true,
                rangelength: [1, 30]
            },
            descripcion:{
                required: true,
                rangelength: [1, 200]
            }
        },
        // Messages for form validation
        messages: {

        }

    });
}















