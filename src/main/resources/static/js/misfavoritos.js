const $tabla = $("#tbVideoAudio");
var LISTA_FAVORITOS_VIDEO = [];
var LISTA_FAVORITOS_AUDIO = [];
var LISTA_FAVORITOS_TEXTO = [];

$(function () {
    ObtenerData(0);
    $("#btnAdd").hide();
    $("#btnVideos").click(GenerarListaVideos);
    $("#btnAudios").click(GenerarListaAudios);
    $("#btnTextos").click(GenerarListaTextos);
    $("#btnAdd").click(MostrarListaTextos);
    $("#btnAgregar").click(RegistrarTextos);
    $("#btnCancelar").click(OcultarTextos);

});

function ObtenerData(flag) {
    LISTA_FAVORITOS_VIDEO = [];
    LISTA_FAVORITOS_AUDIO = [];
    LISTA_FAVORITOS_TEXTO = [];
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/elemento/obtenermisfavoritos",
        dataType: "json",
        success: function (data) {
            if(data != null){
                $.each(data,function (i,item) {
                    if(item.audio != null){
                        LISTA_FAVORITOS_AUDIO.push(item.audio);
                    }else if(item.video != null){
                        LISTA_FAVORITOS_VIDEO.push(item.video);
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
        }
    })
}

function generarVideo(item,i) {
    return `<a style="font-size: 20px" href="javascript:verVideo(${item.id})">
                <i data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-video fa fa-video-camera fa-fw padding-top-3 rf-media" id="livideo${item.id}" data-media="${item.rutaWeb}"></i>
            </a><span class="spvideo" id="span${item.id}" data-index="${i}" ></span>`
}

function verVideo(id) {
    $(".spvideo").html("");
    let input = $("#span"+id);
    $(".btnCerrar").off("click");
    input.html("");

    const mediaVideo = input.attr("data-media");
    let html =`<div class="divVideo" style="text-align: center;     width: 50%;padding-left: 8%;position: absolute;z-index: 9;">
               <button id="btnCerrarVideo" data-id="${id}" type="button" style="margin: -19px;" class="close btnCerrar" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <video id="somevid" preload="none" controls="controls" autoplay="" controlslist="nodownload" width="100%" height="100%" style="margin: -6px;border: 5px solid #e8f2f3; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                            <source id="VideoReproduccion" src="${_ctx}workout/media/file/video/gt/1${mediaVideo}" type="video/mp4">
                        </video>
                </div>`;

    input.append(html);

    var x = $('#span'+id).position().top;
    $('.divVideo').css({'top' : (x-100) + 'px'});

    $("#btnCerrarVideo").click(function (){
        const idVideo = $("#btnCerrarVideo").attr("data-id");
        let input = $("#span"+idVideo);
        input.html("");
        $(".spvideo").html("");
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
                                <td>`+item.nombre+`</td>
                                <td>`+eliminarVideoAudio(item.id,0)+`</td>
                            </tr>`;
        $tabla.append(TR);
    });
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
                                <td>`+item.nombre+`</td>
                                <td>`+eliminarVideoAudio(0,item.id)+`</td>
                            </tr>`;
        $tabla.append(TR);
    });
}

function GenerarListaTextos() {
    $(".btn").removeClass("btn-info");
    $("#btnTextos").removeClass("btn-light");
    $("#btnTextos").addClass("btn-info");
    $tabla.html("");
    $("#btnAdd").show();
    $("#thTitulo").text("Título");
    $("#thTitulo2").text("Descripción");

    $.each(LISTA_FAVORITOS_TEXTO,function (i,item) {
        let TR = `<tr id="trtexto${item.id}">
                                <td></td>
                                <td>`+(i+1)+`</td>
                                <td>`+item.titulo+`</td>
                                <td>`+item.descripcion+`</td>
                                <td>`+formatterTexto(item.id)+`</td>
                            </tr>`;
        $tabla.append(TR);
    });
}

function generarAudio(item,i) {
    return `<a style="font-size: 20px" href="javascript:verAudio(${item.id})">
                <i data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-video fa fa-music fa-fw padding-top-3 rf-media" id="liaudio${item.id}" data-media="${item.rutaWeb}"></i>
            </a><span class="spaudio" id="span${item.id}" data-index="${i}" ></span>`
}

function verAudio(id) {
    $(".spaudio").html("");
    let input = $("#span"+id);
    $(".btnCerrar").off("click");
    input.html("");

    const mediaAudio = input.attr("data-media");
    let html =`<div class="divAudio" style="text-align: center;     width: 50%;padding-left: 8%;position: absolute;z-index: 9;">
               <button id="btnCerrarAudio" data-id="${id}" type="button" style="margin: -19px -60px 0 0;" class="close btnCerrar" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                       <audio id="someaud" preload="none" controls="" controlsList="nodownload" autoplay="" width="100%" height="100%">
                       <source id="AudioReproduccion" src="${_ctx}workout/media/audio${mediaAudio}" type="audio/mpeg"/></audio>
                </div>`;

    input.append(html);

    var x = $('#span'+id).position().top;
    $('.divAudio').css({'top' : (x-52) + 'px'});

    $("#btnCerrarAudio").click(function (){
        const idAudio = $("#btnCerrarAudio").attr("data-id");
        let input = $("#span"+idAudio);
        input.html("");
        $(".spaudio").html("");
    });
}

function eliminarVideoAudio(item,item2) {
    return '<a style="font-size: 20px" href="javascript:eliminarAudioVideoFavorito('+ item +','+ item2 +')"><i class="fa fa-trash"></i></a>';
}

function eliminarAudioVideoFavorito(idvideo,idaudio) {

    let texto = idvideo == 0 ? "audio? ": "vídeo?";

    let params ={};
    params.videoid = parseInt(idvideo);
    params.audioid = parseInt(idaudio);
    params.addedit = 0;

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
                    url: _ctx + "gestion/rutina/elemento/adddeletefavorito",
                    dataType: "json",
                    data: params,
                    success: function (data, textStatus) {
                        if(idvideo != 0) {
                            ObtenerData(0);
                        }else{
                            ObtenerData(1);
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

function MostrarListaTextos() {
    $("#registroTexto").show();
}

function RegistrarTextos() {
    if($("#tTitulo").val() == ""){
        bootbox.alert("Debe ingresar un título");
        return;
    } else if($("#tDescripion").val() == "") {
        bootbox.alert("Debe ingresar una descripción");
        return;
    } else {
        $("#btnAgregar").button('loading');
        let params ={};
        params.titulo = $("#tTitulo").val();
        params.descripcion = $("#tDescripcion").val();
        params.addedit = 0;

        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            url: _ctx + "gestion/rutina/elemento/adddeletetexto",
            dataType: "json",
            data: params,
            success: function (data, textStatus) {
                bootbox.alert("Se registró satisfactoriamente.", function () {
                    $("#tTitulo").val("");
                    $("#tDescripcion").val("");
                    $("#registroTexto").hide();
                    $("#btnAdd").hide();
                    ObtenerData(2);
                });
            },
            error: function (xhr) {
                exception(xhr);
            },
            complete: function () {
                $("#btnAgregar").button('reset');
            }
        });
    }
}

function formatterTexto(id) {
    return '<a style="font-size: 20px" href="javascript:eliminarTexto('+ id +')"><i class="fa fa-trash"></i></a>';
}

function eliminarTexto(id) {
    bootbox.confirm({
        message: "¿Está seguro de eliminar el texto",
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
                $("#btnAgregar").button('loading');
                let params ={};
                params.titulo = $("#tTitulo").val();
                params.descripcion = $("#tDescripcion").val();
                params.addedit = id;

                $.ajax({
                    type: "POST",
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    url: _ctx + "gestion/rutina/elemento/adddeletetexto",
                    dataType: "json",
                    data: params,
                    success: function (data, textStatus) {
                        bootbox.alert("Se " + (id == 0 ? "registró" : "eliminó") + " satisfactoriamente.", function () {
                            $("#tTitulo").val("");
                            $("#tDescripcion").val("");
                            $("#registroTexto").hide();
                            $("#btnAdd").hide();
                            ObtenerData(2);
                        });
                    },
                    error: function (xhr) {
                        exception(xhr);
                    },
                    complete: function () {
                        $("#btnAgregar").button('reset');
                    }
                });
            }
        }
    });
}

function OcultarTextos() {
    $("#tTitulo").val("");
    $("#tDescripcion").val("");
    $("#registroTexto").hide();
}






















