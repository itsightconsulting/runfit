const posts = document.querySelector('#dvPosts');
const clienteId = Number(document.querySelector('head[data-id]').getAttribute('data-id'));
let allPosts = [];
$(function () {
    listarMultimediaEntrenadores();

    setInterval(function(){ listarMultimediaEntrenadores(); }, 135000);
    posts.addEventListener('mouseover', principalesMouseOverPost);
    posts.addEventListener('click', principalesClickPost);
});
function principalesMouseOverPost(e){
    const input = e.target;
    const clases = e.target.classList;

    if(clases.contains('btn-like')){
        const nomsLike = input.getAttribute('data-original-title');
        if(nomsLike.length==0){
            const postId = input.getAttribute('id').substr(5);
            input.setAttribute('data-original-title', getNombresCompletosQuienesTieneLikeEnPost(postId, clases));
            $(input).tooltip('show');
        }
    }
}

function principalesClickPost(e){
    const input = e.target;
    const clases = e.target.classList;
    if(clases.contains('btn-like')){
        const postId = input.getAttribute('data-id');
        const spnTot = input.querySelector('span.post-totlikes');
        irMeGusta(postId, clases, spnTot);
    }

    else if (clases.contains('btn-fav')){
        const postId = input.getAttribute('data-id');
        irFavorito(postId, clases);
    }
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

function listarMultimediaEntrenadores() {

    $.ajax({
        type: 'GET',
        url: _ctx + 'cliente/get/publicaciones',
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
                        allPosts = data;//Global
                        $.each(data.sort((a, b)=>{return parseFromStringToDateTime(b.fechaCreacion).getTime() - parseFromStringToDateTime(a.fechaCreacion).getTime();}),(i, item)=>{
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
    let estilo;

    var ruta = `${_ctx}`+`workout/multimedia/`;

    if(item.tipo == "1"){
        estado = "publicó";
        divestado = '<h4 class="padding-10">'+item.titulo+'</h4><audio id=" " preload="none" controls="" controlslist="nodownload" autostart="false" width="100%" height="100%"><source id="AudioReproduccion" ' +
                    'src="'+ ruta +"audio" + item.rutaWeb +'" type="audio/mpeg"/></audio>';
    }else if(item.tipo == "2"){
        estado = "publicó";
        divestado = '<h4 class="padding-10">'+item.titulo+'</h4><video id="somevid" controls="controls" controlslist="nodownload" autostart="false" width="100%" height="100%"><source id="VideoReproduccion" ' +
                    'src="'+ ruta +'video'+ item.rutaWeb +'" type="video/mp4"/></video>';
    }else{
        estado = "<span>escribió </span><span> "+item.titulo+"</span>";
        divestado = "<h5>"+item.descripcion+"</h5>";
    }
    const mylike = item.lstDetalle != undefined && item.lstDetalle.length > 0 ? item.lstDetalle.find(v=>v.flgLiked && v.cliId==clienteId)!= undefined : false;
    const sz = item.lstDetalle != undefined ? item.lstDetalle.filter(v=>v.flgLiked).length : 0;
    const szToCheckFav = item.lstDetalle != undefined ? item.lstDetalle.find(v=>v.cliId==clienteId && v.flgFav) != undefined ? true : false : false;
    estilo = mylike ? "primary" : "muted";

    return  `<div class="card">
                <div class="card-block">
                    <div class="media m-b-1">
                        <div class="media-left">
                            <a href="#">
                                <img src="/img/avatars/sunny.png" alt="avatar" class="img-circle" width="45" />
                            </a>
                        </div>
                        <div class="media-body media-middle">
                            <i title="Agregar a favs o quitar de favs" class="i-btn fa fa-star pull-right btn-fav txt-color-blue ${szToCheckFav ? 'is-fav': ''}" data-id="${item.id}"></i>
                            <div><a href="#">${item.trainer.nombreCompleto}</a> ${estado}</div>
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
                            <a id="alike${item.id}" rel="tooltip" data-html="true" data-trigger="hover" data-original-title="" class="btn-like btn-aborder btn icon-btn btn-${estilo}" data-id="${item.id}" href="javascript:void(0)"><span id="spanlike${item.id}" class="glyphicon btn-glyphicon glyphicon-thumbs-up img-circle text-${estilo}"></span><span class="post-totlikes">${sz > 0 ? sz + ' ' : ''}</span>Me gusta</a>
                        </div>
                    </div>
                </div>`;
}

function irMeGusta(id, clases, spnTot) {
    let params = {};
    params.postId = id;
    params.estado = clases.contains('btn-primary') ? 0 : 1;
    params.tipoFlag = 1;

    $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "cliente/post/updateFlag",
        dataType: "json",
        data: params,
        success: function (res) {
            let totLikes = Number(spnTot.textContent.trim());
            if(clases.contains('btn-primary'))
                spnTot.textContent = --totLikes + " ";
            else
                spnTot.textContent = ++totLikes + " ";
            clases.toggle('btn-primary');
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function irFavorito(id, clases) {
    let params = {};
    params.postId = id;
    params.estado = clases.contains('is-fav') ? 0 : 1;
    params.tipoFlag = 2;

    $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "cliente/post/updateFlag",
        dataType: "json",
        data: params,
        success: function () {
            clases.toggle('is-fav');
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
        }
    })
}