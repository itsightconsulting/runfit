var _ctx = $('meta[name="_ctx"]').attr('content');
let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");
const formRecuperacion = document.getElementById('recover-pass-form');
const btnRecuperar = document.getElementById('btnRecuperar');
const btnCambiar = document.getElementById('btnCambiar');

(function () {
    init();
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    })
})();

function init(){
    eventos();
}

function eventos(){
    if(btnRecuperar){
        btnRecuperar.addEventListener('click', preSendFormRecuperacion);
    }
    if(btnCambiar){
        btnCambiar.addEventListener('click', preSendFormCambiar);
    }
}

function preSendFormRecuperacion(){
    if(!$(formRecuperacion).valid()){
        return;
    }
    sendFormRecuperacion();
}

function preSendFormCambiar(){
    if(!$(formRecuperacion).valid()){
        return;
    }
    sendFormCambiar();
}

function sendFormRecuperacion(){
    const params = {username: $('#username').val()};

    $.ajax({
        type: 'POST',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'p/recuperacion/password/check/username',
        dataType: "json",
        data: params,
        success: function (data) {
            console.log(data);
        },
        error: function (xhr) {
            console.log(xhr);
            exception(xhr);
        },
        complete: function () {
        }
    });
}

function sendFormCambiar(){

    const arrUrl = window.location.pathname.split("/");
    const hshId = arrUrl[arrUrl.length-1];

    const params = {
        nuevaPassword: $('#NuevaPassword').val(),
        nuevaPasswordRe: $('#NuevaPasswordRe').val(),
        userId: hshId,
        schema: new URLSearchParams(window.location.search).get('sc')
    };

    $.ajax({
        type: 'POST',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'p/recuperacion/password/cambiar',
        dataType: "json",
        data: params,
        success: function (data) {
            console.log(data);
        },
        error: function (xhr) {
            console.log(xhr);
            exception(xhr);
        },
        complete: function () {
        }
    });
}

function goRegister(){
    $(".login-sesion").fadeOut();
    $(".login-register").fadeIn();
    $(".login-register").removeClass('hidden');
}

function goLogin() {
    $(".login-register").fadeOut();
    $(".login-sesion").fadeIn();
}