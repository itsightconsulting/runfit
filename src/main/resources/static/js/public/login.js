const body = document.querySelector('body');
const formRecuperacion = document.getElementById('recover-pass-form');
const btnRecuperar = document.getElementById('btnRecuperar');
const btnCambiar = document.getElementById('btnCambiar');

(function () {
    init();
})();

function init(){
    eventos();
}

function eventos(){
    if(btnRecuperar){//Iniciar recuperación password
        btnRecuperar.addEventListener('click', preSendFormRecuperacion);
    }
    if(btnCambiar){//Finalizar recuperación password con el cambio
        btnCambiar.addEventListener('click', preSendFormCambiar);
    }

    body.addEventListener('focusout', bodyClickEventListener);
    body.addEventListener('keyup', bodyKeyupEventListener);
}

function bodyClickEventListener(e){
    const input = e.target;
    if(input.tagName === "INPUT"){
        if(input.type==="text" || input.type==="number"){
            input.value = input.value.trim();
        }
    }
    if(input.tagName === "TEXTAREA"){
        input.value = input.value.trim();
    }
}

function bodyKeyupEventListener(e){
    const input = e.target;
    if(input.tagName === "INPUT"){
        if(input.type==="text" || input.type==="number"){
            if(input.nextElementSibling){
                input.nextElementSibling.remove();
            }
        }
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
    const username = document.getElementById('username');
    const params = {username: $(username).val()};

    $.ajax({
        type: 'POST',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'p/recuperacion/password/check/username',
        blockLoading: true,
        dataType: "json",
        data: params,
        success: function () {
            $('.actions').addClass('hidden');
            setTimeout(()=>{
                $.SmartMessageBox({
                    title: "<i style='color: #a8fa00'> Notificaciones Runfit</i>",
                    content: "" +
                        "<br/><i style='font-size: 1.2em;'>Se le ha enviado un correo al e-mail asociado a esta cuenta. Por favor revise su bandeja para continuar con los próximos pasos</i><br/>",
                    buttons: '[SALIR]'
                }, function (ButtonPressed) {
                    if(ButtonPressed){
                        window.location.href = _ctx + "login";
                    }
                })
            }, 500)
        },
        error: function (xhr) {
            customErrorHandler(xhr, username);
        },
        complete: function () {
        }
    });
}

function sendFormCambiar(){

    const arrUrl = window.location.pathname.split("/");
    const hshId = arrUrl[arrUrl.length-1];
    const passwordRe = $('#NuevaPasswordRe')[0];

    const params = {
        nuevaPassword: $('#NuevaPassword').val(),
        nuevaPasswordRe: $(passwordRe).val(),
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
            $('.actions').addClass('hidden');
            setTimeout(()=>{
                $.SmartMessageBox({
                    title: "<i style='color: #a8fa00'> Notificaciones Runfit</i>",
                    content: "" +
                        "<br/><i style='font-size: 1.2em;'>Su contraseña se ha actualizado satisfactoriamente</i><br/>",
                    buttons: '[IR AL LOGIN]'
                }, function (ButtonPressed) {
                    if(ButtonPressed){
                        window.location.href = _ctx + "login";
                    }
                })
            }, 500)
        },
        error: function (xhr) {
            customErrorHandler(xhr, passwordRe);
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

function customErrorHandler(xhr, input){
    const messageError = xhr.responseJSON.message;
    const sibling = input.nextElementSibling;
    if(sibling){
        if(sibling.tagName === "EM"){
            sibling.innerText = messageError;
        }
    } else{
        const em = document.createElement('em');
        em.innerText = messageError;
        em.classList.remove('help-block');
        em.classList.add('help-block');
        input.insertAdjacentElement('afterend', em);
    }
}