let rutinas_ = [];
let indexGlobal = 0;
const $dia = document.querySelector('#rutinaDiaSemana');
const $divcategoria = $(".btn-group-vertical");
const $dia_ = $('#rutinaDiaSemana');

$(function () {
    listarSubCategorias();
});

function listarSubCategorias() {

    $.ajax({
        type: 'GET',
        url: _ctx + 'cliente/get/subcategorias',
        dataType: "json",
        success: function (data, textStatus) {
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                } else {
                    $.each(data,function(i,item){
                       let html = `<div id="box${item.subCategoriaId}" data-id="${item.subCategoriaId}" class="icon-box">
                                    <div class="icon" data-class="runner">
                                        <div class="active-window"><i class="icofont-runner"></i>
                                        <h5 class="icon__name">`+ item.nombreSubCategoria +`</h5></div>
                                        <span class="add-to-collection-plus-btn icofont-plus-square"></span>
                                    </div></div>`;
                        $(".subcategoria-icons-box").append(html);
                    });
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
            $(".icon").click(function () {
                if($(this).hasClass("active")){
                    $divcategoria.html("");
                    $dia_.html("");
                }else{
                    $divcategoria.html("");
                    $dia_.html("");
                    $(".icon").removeClass("active");
                    $(this).addClass("active");
                    listarSubCategoriasEspecificacion(parseInt($(this).parent().attr("data-id")));
                }
            });
        }
    });
}

function listarSubCategoriasEspecificacion(id) {
    const params = {};
    params.id = id;
    $.ajax({
        type: 'GET',
        url: _ctx + 'cliente/get/subcategoriasespecificacion',
        dataType: "json",
        data: params,
        success: function (data, textStatus) {
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                } else {
                    console.log(data);
                    $.each(data,function(i,item){
                        let cant = item.nivel;
                        let span = "";
                        for (let j = 0; j < cant; j++) {
                            span += `<span style="color: yellow" class="fa fa-star"></span>`;
                        }

                        $divcategoria.append(`<a href="javascript:listarMultimediaEntrenadores(${item.id});" class="btn btn-success txt-color-white">${span} ${item.nombre}</a>`);
                    })
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () { }
    });
}

function listarMultimediaEntrenadores(id) {

    $dia_.html("");
    const params = {};
    params.id = id;
    $.ajax({
        type: 'GET',
        url: _ctx + 'cliente/get/miniplantillasentrenador',
        dataType: "json",
        data: params,
        success: function (data, textStatus) {
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                } else {
                    //Semana
                    rutinas_ = data;

                    $.each(data,function(i,item){
                        item.literal = "";
                        item.dia = "";
                        item.flagDescanso = false;
                    });
                    GenerarDiaRutinaHtml(data);
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
            $('[rel="tooltip"]').tooltip();
            $(".reprod-audio").click(function () {
                verAudio(this);
            });
            $(".reprod-video").click(function () {
                verVideo(this);
            })
        }
    });
}

function GenerarDiaRutinaHtml(data){
    let rawDias = armarHtmlDayEncontrado(data);

    let RutinaSemanaDiv = document.createElement('div');
    RutinaSemanaDiv.innerHTML = rawDias;

    $dia.innerHTML = '';
    $dia.append(RutinaSemanaDiv);

}

function verVideo(val) {
    let id = 0;
    let input = $(val);
    input.html("");

    $(".divVideo").remove();

    const mediaVideo = input.attr("data-media");
    let html =`<div id="divvideos${id}"  class="divVideo" style="text-align: center;     width: 86%;padding-left: 8%;position: absolute;z-index: 9;">
               <button id="btnCerrarVideo" data-id="${id}" type="button" style="margin: -16px;" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <video id="somevid" preload="none" controls="controls" autoplay="" controlslist="nodownload" width="100%" height="100%" style="margin: -6px;border: 5px solid #e8f2f3; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                            <source id="VideoReproduccion" src="${mediaVideo}" type="video/mp4">
                        </video>
                </div>`;

    input.after(html);

    var x = input.position();
    $('.divVideo').css({'top' : (x-100) + 'px'});

    $("#btnCerrarVideo").click(function (){
        $(".divVideo").remove();
    });

}

function verAudio(val) {
    let id = 0;
    let input = $(val);
    input.html("");
    $(".divAudio").remove();

    const mediaAudio = input.attr("data-media");
    let html = `<div id="divaudio${id}" class="divAudio" style="text-align: center;     width: 50%;padding-left: 8%;position: absolute;z-index: 9;">
               <button id="btnCerrarAudio" data-id="${id}" type="button" style="margin: -19px -160px 0 0;" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                       <audio id="someaud" preload="none" controls="" controlsList="nodownload" autoplay="" width="100%" height="100%">
                       <source id="AudioReproduccion" src="${mediaAudio}" type="audio/mpeg"/></audio>
                </div>`;

    input.after(html);


    var x = input.position();
    $('.divAudio').css({'top' : (x-52) + 'px'});

    $("#btnCerrarAudio").click(function (){
        $(".divAudio").remove();
    });

}
