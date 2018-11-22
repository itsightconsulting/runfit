
$(function () {
    listarMultimediaEntrenadores();

    setInterval(function(){ listarMultimediaEntrenadores(); }, 135000);
});

function listarMultimediaEntrenadores() {

    $.ajax({
        type: 'GET',
        url: _ctx + 'cliente/get/publicacionesentrenador',
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
                    //Semana
                    if(data.length>0) {
                        var $div = $(".list-cards");
                        $div.html("");
                        $.each(data,function (i,item) {
                            $div.append(GenerarDiv(item));
                        });
                    }
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () { }
    });
}

function GenerarDiv(item){
    var estado = "";
    var divestado = "";
    var estilo = "";


    var ruta = `${_ctx}`+`workout/multimedia/`;

    if(item.tipo == "1"){
        estado = "público";
        divestado = '<audio id=" " preload="none" controls="" controlslist="nodownload" autoplay="none" width="100%" height="100%"><source id="AudioReproduccion" ' +
                    'src="'+ ruta +"audio/" + item.rutaWeb +'" type="audio/mpeg"/></audio>';
    }else if(item.tipo == "2"){
        estado = "público";
        divestado = '<video id="somevid" controls="controls" autoplay="none" controlslist="nodownload" width="100%" height="100%"><source id="VideoReproduccion" ' +
                    'src="'+ ruta +'video/'+ item.rutaWeb +'" type="video/mp4"/></video>';
    }else{
        estado = "<span>escribió </span><span> "+item.titulo+"</span>";
        divestado = "<h5>"+item.descripcion+"</h5>";
    }

    estilo = item.mylike ? "primary" : "muted";

    return  `<div class="card">
                <div class="card-block">
                    <div class="media m-b-1">
                        <div class="media-left">
                            <a href="#">
                                <img src="/img/avatars/sunny.png" alt="avatar" class="img-circle" width="45" />
                            </a>
                        </div>
                        <div class="media-body media-middle">
                            <div><a href="#">${item.usuario.nombreCompleto}</a> ${estado}</div>
                                <small class="text-muted">publicado ${item.fechaCreacion}</small>
                            </div>
                        </div>
                        <div class="embed-responsive embed-responsive-16by9 m-b-1">
                            `+ divestado +`
                        </div>
                        <div class="col-md-3"></div>
                        <div class="col-md-3" style="width: 23%;"></div>
                        <div class="col-md-3"></div>
                        <div>
                            <a id="alike${item.id}" class="btn-aborder btn icon-btn btn-${estilo}" href="javascript:irMeGusta(${item.id},${item.mylike ? 1: 0})"><span id="spanlike${item.id}" class="glyphicon btn-glyphicon glyphicon-thumbs-up img-circle text-${estilo}"></span>Me gusta</a>
                        </div>
                    </div>
                </div>`;
}

function irMeGusta(id,estado) {
    let params = {};
    params.id = id;
    params.estado = estado;

    $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "cliente/updateMyLike",
        dataType: "json",
        data: params,
        success: function (data) {
            if(estado == 1) {
                $("#alike" + id).removeClass("btn-primary");
                $("#spanlike" + id).removeClass("text-primary");

                $("#alike" + id).addClass("btn-muted");
                $("#spanlike" + id).addClass("text-muted");
            }else{
                $("#alike" + id).removeClass("btn-muted");
                $("#spanlike" + id).removeClass("text-muted");

                $("#alike" + id).addClass("btn-primary");
                $("#spanlike" + id).addClass("text-primary");
            }
        },
        error: function (xhr) {
            console.log(xhr);
        },
        complete: function () {}
    })
}