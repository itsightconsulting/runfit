const $tabla = $("#tbVideoAudio");
const $tablatop = $("#tbTop");

var LISTA_FAVORITOS_VIDEO = [];
var LISTA_FAVORITOS_AUDIO = [];
var LISTA_FAVORITOS_TEXTO = [];
var TIEMPO = "";
var PESO = "";

$(function () {
    ObtenerData(0);

    $("#btnVideos").click(GenerarListaVideos);
    $("#btnAudios").click(GenerarListaAudios);
    $("#btnTextos").click(GenerarListaTextos);

    $("#btnGuardarArchivo").click(function(){
        RegistrarMultimedia(0);
    });

    $("#btnTexto").click(function(){
        RegistrarMultimedia(0);
    });

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

        $("#subirArchivo").show();
        $("#registroTexto").hide();
        $(".input-ghost").val(null);
        $("#inputFile").val("");
        $("#tTitulo").val("");
        $("#tDescripcion").val("");

        if($(this).attr("data-id") == "1"){ // 1 = TIPO AUDIO
            $(".input-ghost").attr("accept","audio/*");
        }else if($(this).attr("data-id") == "2"){ // 2 = TIPO VIDEO
            $(".input-ghost").attr("accept","video/mp4,video/x-m4v,video/*");
        }else if($(this).attr("data-id") == "3"){ // 3 = TIPO TEXTO
            $("#registroTexto").show();
            $("#subirArchivo").hide();
            $(".input-ghost").attr("accept","text/plain");
        }


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

function subirChangeEvent() {
    $(".input-ghost").change(function () {
        var file;
        if ((file = this.files[0])) {
            var obUrl;
            if (file.name.match(/\.(avi|mp3|mp4|mpeg|ogg|mpeg4|mpg|wmv)$/i)) {
                objUrl = URL.createObjectURL(file);
                document.getElementById('Tiempo').setAttribute('src', objUrl);
                document.getElementById('Tiempo2').setAttribute('src', objUrl);
                PESO = formatBytes(file.size, 2);
            }
        }
    });
}


function AgregarMultimedia() {
    $('#modalmultimedia').modal("show")
}

function ObtenerData(flag) {
    LISTA_FAVORITOS_VIDEO = [];
    LISTA_FAVORITOS_AUDIO = [];
    LISTA_FAVORITOS_TEXTO = [];
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: _ctx + "gestion/multimedia/obtenerListadoMultimedia",
        dataType: "json",
        success: function (data) {
            if(data != null){
                console.log(data);
                $.each(data,function (i,item) {
                    if(item.tipo == 1){
                        LISTA_FAVORITOS_AUDIO.push(item);
                    }else if(item.tipo == 2){
                        LISTA_FAVORITOS_VIDEO.push(item);
                    } else {
                        LISTA_FAVORITOS_TEXTO.push(item);
                    }
                });
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
            if(flag == 0){
                GenerarListaVideos();
            } else if(flag == 1){
                GenerarListaAudios();
            } else if(flag == 2){
                GenerarListaTextos();
            }
            $('[rel="tooltip"]').tooltip();
            ListarTop();
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
    let html =`<div id="divvideos${id}"  class="divVideo" style="text-align: center;     width: 50%;padding-left: 8%;position: absolute;z-index: 9;">
               <button id="btnCerrarVideo" data-id="${id}" type="button" style="margin: -19px;" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <video id="somevid" preload="none" controls="controls" autoplay="" controlslist="nodownload" width="100%" height="100%" style="margin: -6px;border: 5px solid #e8f2f3; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                            <source id="VideoReproduccion" src="${mediaVideo}" type="video/mp4">
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

function GenerarListaVideos() {
    $(".btn").removeClass("btn-info");
    $("#registroTexto").hide();
    $("#btnVideos").removeClass("btn-light");
    $("#btnVideos").addClass("btn-info");
    $("#btnAdd").hide();
    $tabla.html("");
    $("#thTitulo").text("Vídeo");
    $("#thTitulo2").text("Nombre");
    $.each(LISTA_FAVORITOS_VIDEO,function (i,item) {
        let TR = `<tr id="trvideo${item.id}">
                                <td></td>
                                <td>`+(i+1)+`</td>
                                <td style="font-size: 20px; text-align: center;">`+generarVideo(item,i)+`</td>
                                <td>`+item.titulo+`</td>
                                <td>`+eliminarVideoAudio(item.id,0,item.cantidadLikes)+`</td>
                            </tr>`;
        $tabla.append(TR);
    });

    $('[rel="tooltip"]').tooltip();
}

function GenerarListaAudios() {
    $(".btn").removeClass("btn-info");
    $("#registroTexto").hide();
    $("#btnAudios").removeClass("btn-light");
    $("#btnAudios").addClass("btn-info");
    $("#btnAdd").hide();
    $tabla.html("");
    $("#thTitulo").text("Audio");
    $("#thTitulo2").text("Nombre");
    $.each(LISTA_FAVORITOS_AUDIO,function (i,item) {
        let TR = `<tr id="trvideo${item.id}">
                                <td></td>
                                <td>`+(i+1)+`</td>
                                <td style="font-size: 20px; text-align: center;">`+generarAudio(item,i)+`</td>
                                <td>`+item.titulo+`</td>
                                <td>`+eliminarVideoAudio(0,item.id,item.cantidadLikes)+`</td>
                            </tr>`;
        $tabla.append(TR);
    });

    $('[rel="tooltip"]').tooltip();
}

function GenerarListaTextos() {
    $(".btn").removeClass("btn-info");
    $("#btnTextos").removeClass("btn-light");
    $("#btnTextos").addClass("btn-info");
    $tabla.html("");
    $("#thTitulo").text("Título");
    $("#thTitulo2").text("Descripción");

    $.each(LISTA_FAVORITOS_TEXTO,function (i,item) {
        let TR = `<tr id="trtexto${item.id}">
                                <td></td>
                                <td>`+(i+1)+`</td>
                                <td>`+item.titulo+`</td>
                                <td>`+item.descripcion+`</td>
                                <td>`+formatterTexto(item.id,item.cantidadLikes)+`</td>
                            </tr>`;
        $tabla.append(TR);
    });

    $('[rel="tooltip"]').tooltip();
}

function generarAudio(item,i) {

    var html = "<span> Duración : "+ item.duracion +"</span><br>";
    html += "<span> Peso : "+ item.peso +"</span>";

    return `<a style="font-size: 20px" href="javascript:verAudio(${item.id})"
               data-placement="top" title="${html}" data-trigger="hover"
               data-html="true" rel="tooltip">
                <i data-placement="bottom" rel="tooltip" class="reprod-video fa fa-music fa-fw padding-top-3 rf-media" id="liaudio${item.id}" data-media="${item.rutaWeb}"></i>
            </a>
            <span class="spaudio" id="span${item.id}" data-index="${i}" data-media="${item.rutaWeb}" ></span>`
}

function verAudio(id) {
    $("#span"+id).html("");
    let input = $("#span"+id);
    input.html("");
    $(".divAudio").remove();

    const mediaAudio = input.attr("data-media");
    let html =`<div id="divaudio${id}" class="divAudio" style="text-align: center;     width: 50%;padding-left: 8%;position: absolute;z-index: 9;">
               <button id="btnCerrarAudio" data-id="${id}" type="button" style="margin: -19px -60px 0 0;" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                       <audio id="someaud" preload="none" controls="" controlsList="nodownload" autoplay="" width="100%" height="100%">
                       <source id="AudioReproduccion" src="${mediaAudio}" type="audio/mpeg"/></audio>
                </div>`;

    input.append(html);

    var x = $('#span'+id).position().top;
    $('.divAudio').css({'top' : (x-52) + 'px'});

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

function eliminarAudioVideoFavorito(idvideo,idaudio,valor_texto) {

    let texto = valor_texto == 0 ? idvideo == 0 ? "audio? ": "vídeo?" : "texto";

    let params ={};
    params.id = valor_texto == 0 ? idvideo == 0 ? parseInt(idaudio) : parseInt(idvideo) : valor_texto;

    bootbox.confirm({
        message: "¿Está seguro de eliminar el "+texto,
        buttons: {
            confirm: {
                label: 'Si',
                className: 'btn-success'
            },
            cancel: {
                label: 'No',
                className: 'btn-danger'
            }
        },
        callback: function (result) {
            if(result){
                $.ajax({
                    type: "POST",
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    url: _ctx + "gestion/multimedia/deleteaudiovideo",
                    dataType: "json",
                    data: params,
                    success: function (data, textStatus) {
                        if(valor_texto == 0) {
                            if (idvideo != 0) {
                                ObtenerData(0);
                            } else {
                                ObtenerData(1);
                            }
                        }else{
                            ObtenerData(2);
                        }

                    },
                    error: function (xhr) {
                        exception(xhr);
                    },
                    complete: function () {}
                });
            }
        }
    });
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
                var element = $("<input type='file' accept='video/*' class='input-ghost' style='visibility:hidden; height:0'>");
                element.attr("name",$(this).attr("name"));
                element.change(function(){
                    element.next(element).find('input').val((element.val()).split('\\').pop());
                });
                $(this).find("button.btn-choose").click(function(){
                    element.click();
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

function RegistrarMultimedia(id) {
    var multimedia = $(".input-ghost").get(0);
    var files = multimedia.files.length == 0 ? null : multimedia.files[0];
    var tipo = $(".abtn-multimedia.btn-success").attr("data-id");

    if (files != null) {

        var data = new FormData();
        data.append("multimedia", files);
        data.append("id", id);
        data.append("titulo", $("#tTitulo").val());
        data.append("descripcion", $("#tDescripcion").val());
        data.append("tipo", tipo);
        data.append("duracion", TIEMPO);
        data.append("peso", PESO);

        $.ajax({
            type: 'POST',
            url: _ctx + 'gestion/multimedia/upload',
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
                if (textStatus == "success") {
                    if (data == "-99") {
                        $.smallBox({content: "El archivo no ha podido ser guardado debido a que excede los límites permitidos de la aplicación, que son 5MB en caso sea un solo archivo y si son más de 2, como máximo en conjunto no deben exceder a 10MB. Para mayor información comunicarse con el administrador."});
                    }
                    else {
                        $.smallBox({content: "<i class='fa fa-child'></i> <i>Se registró con éxito...!</i>",});
                        $(".input-ghost").val(null);
                        $("#inputFile").val("");
                        $('#modalmultimedia').modal("hide");
                        TIEMPO = "";
                        PESO = "";
                        $("#Tiempo").attr("src","");
                        $("#Tiempo2").attr("src","");
                    }
                }
            },
            error: function (xhr) {
                exception(xhr);
            },
            complete: function () {
                ObtenerData(1);
            }
        });
    }else{
        if(tipo == "3") {
            if($("#tTitulo").val() == ""){
                $.smallBox({content: "<i class='fa fa-child'></i> <i>Debe ingresar un título...!</i>",});
                return;
            } else if($("#tDescripion").val() == "") {
                $.smallBox({content: "<i class='fa fa-child'></i> <i>Debe ingresar una descripción...!</i>",});
                return;
            }else {
                let params = {};
                params.id = id;
                params.titulo = $("#tTitulo").val();
                params.descripcion = $("#tDescripcion").val();

                $.ajax({
                    type: "POST",
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    url: _ctx + 'gestion/multimedia/uploadtext',
                    dataType: "json",
                    data: params,
                    success: function (data, textStatus) {
                        $("#tTitulo").val("");
                        $("#tDescripcion").val("");
                        $('#modalmultimedia').modal("hide");
                        $.smallBox({content: "<i class='fa fa-child'></i> <i>Se registró satisfactoriamente...!</i>",});
                    },
                    error: function (xhr) {
                        exception(xhr);
                    },
                    complete: function () {
                        ObtenerData(2);
                    }
                });
            }
        }else{
            $.smallBox({content: "<i class='fa fa-child'></i> <i>Debe de agregar un archivo...!</i>",});
        }
    }
}

function ListarTop() {
    $tablatop.html("");
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: _ctx + "gestion/multimedia/obtenerListadoTop",
        dataType: "json",
        success: function (data) {
            if(data != null){
                sortByKeyDesc(data,"cantidadLikes");

                for (let i = 0; i < 10 ; i++) {
                    let item = data[i];
                    if(item!=null){

                        item.id = (item.id +100);
                        let TR = "";
                        if(item.tipo == "1") {
                            TR = `<tr id="trvideo${item.id}">
                                <td></td>
                                <td>`+(i+1)+`</td>
                                <td style="font-size: 20px; text-align: center;">`+generarAudio(item,i)+`</td>
                                <td>`+item.titulo+`</td>
                                <td><i class="fa fa-heart"></i> `+item.cantidadLikes+`</td>                                
                                
                            </tr>`;
                        } else if(item.tipo == "2") {
                            TR = `<tr id="trvideo${item.id}">
                                <td></td>
                                <td>` + (i + 1) + `</td>
                                <td style="font-size: 20px; text-align: center;">` + generarVideo(item, i) + `</td>
                                <td>` + item.titulo + `</td>
                                <td><i class="fa fa-heart"></i> `+item.cantidadLikes+`</td>
                                
                            </tr>`;
                        }else{
                            TR = `<tr id="trtexto${item.id}">
                                <td></td>
                                <td>`+(i+1)+`</td>
                                <td>`+item.titulo+`</td>
                                <td>`+item.descripcion+`</td>
                                <td><i class="fa fa-heart"></i> `+item.cantidadLikes+`</td>
                                
                            </tr>`;
                        }

                        $tablatop.append(TR);
                    }
                }

            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
            $('[rel="tooltip"]').tooltip();
        }
    });
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














