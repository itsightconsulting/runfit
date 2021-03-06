const body_ = document.querySelector('body');
const formRecuperacion = document.getElementById('recover-pass-form');
const btnRecuperar = document.getElementById('btnRecuperar');
const btnCambiar = document.getElementById('btnCambiar');
const btnRegistrar = document.getElementById('btn-register');
const btnNuevo = document.getElementById('btn-nuevo');
const btnStartUpIniciarSesion = document.getElementById('login_link');
const btnHideIniciarSesion = document.getElementById('hide_login_link');
const loginForm = document.getElementById('login-form');


(function () {
    //Solo para login temporal
    if(body_.querySelector('.login')){
        init();
    }
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

    if(btnRegistrar){//Finalizar registro
        btnRegistrar.addEventListener('click', registro);
    }

    if(btnNuevo){// Disparar validaciones en formulario de registro
        btnNuevo.addEventListener('click', validacionFormularioVisitante);
    }

    if(btnStartUpIniciarSesion){// Disparar login
        btnStartUpIniciarSesion.addEventListener('click', inicializarLoginForm);
    }


    if(btnHideIniciarSesion){// Disparar login
        btnHideIniciarSesion.addEventListener('click', ocultarLoginForm);
    }

    if(loginForm){
        loginForm.addEventListener('keyup', keyupLoginEventListener);
    }

    body_.querySelector('.login').addEventListener('focusout', bodyFocusOutEventListener);
    body_.querySelector('.login').addEventListener('keyup', bodyKeyupEventListener);

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
                    title: "<i style='color: #a8fa00'> RUNFIT</i>",
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
                    title: "<i style='color: #a8fa00'> RUNFIT</i>",
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
    $(".login.active .help-block").remove();
    $('#login-form')[0].reset();
}

function goLogin() {
    $(".login-register").fadeOut();
    $(".login-sesion").fadeIn();
    $(".login.active .help-block").remove();
    $('#register-form')[0].reset();
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

function registro() {

    if ($("#register-form").valid()) {
        const params = getFormData($('#register-form'));
        params.password = $('#PasswordRegistro').val();
        delete params.passwordRegistro;

        let registroParams ={
            nombres : params.nombresRegistro,
            apellidos : params.apellidosRegistro,
            correo :  params.correoRegistro,
            password: params.password

        }

        $.ajax({
            type: 'POST',
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            url: _ctx+'p/visitante/registro',
            dataType: "json",
            blockLoading: true,
            data: registroParams,
            success: function (data, textStatus) {

                setTimeout(()=>{
                    $('.actions').addClass('hidden');
                    $.SmartMessageBox({
                        title: "<i style='color: #a8fa00'> RUNFIT</i>",
                        content: "" +
                            "<br/><i style='font-size: 1.2em;'>Se le ha enviado un correo al e-mail asociado a esta cuenta. Por favor revise su bandeja para culminar el registro</i><br/>",
                        buttons: '[SALIR]'
                    }, function (ButtonPressed) {
                        if(ButtonPressed){
                            window.location.href = _ctx + "login";
                        }
                    })
                }, 700)
            },
            error: function (xhr) {

                const mensaje = xhr.responseJSON.message;

                $('#divSmallBoxes').css('z-index','100000');
                $.smallBox({
                    title: "RUNFIT",
                    content: '<p>'+mensaje+'</p>',
                    timeout: 4500,
                    color:  '#cc4d4d',
                    icon: "fa fa-exclamation-circle"
                })
            },
            complete: function () {
            }
        });



    }


}

function validacionFormularioVisitante(){

    $("#register-form").validate({
        // Rules for form validation}
        errorClass: 'help-block',
        highlight: function (element) {
            $(element).parent().removeClass('state-success').addClass("state-error");
            $(element).removeClass('valid');
        },
        unhighlight: function (element) {
            $(element).parent().removeClass("state-error").addClass('state-success');
            $(element).addClass('valid');
        },
        rules: {
            NombresRegistro: {
                required: true,
                rangelength: [1, 30],
                lettersonly:true
            },
            ApellidosRegistro: {
                required: true,
                rangelength: [3, 30],
                lettersonly: true
            },
            CorreoRegistro: {
                required: true,
                rangelength: [7, 30],
                emailValid:true
            },
            PasswordRegistro: {
                required: true,
                rangelength: [8, 30],
                pwcheck: true
            },
            PasswordConfirmacion: {
                required: true,
                equalTo: "#PasswordRegistro"
            },
        },
        // Messages for form validation
        messages: {
            PasswordConfirmacion : {
                equalTo: 'Repite la misma contraseña'
            }
        },
        // Do not change code below
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }
    });
}



function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};
    $.map(unindexed_array, function (n, i) {
        indexed_array[n['name'][0].toLowerCase() + n['name'].slice(1)] = n['value'];
    });
    return indexed_array;
}


function inicializarLoginForm(){

    $('.login').addClass('active');
    $('html, body').css('overflowY', 'hidden');

    goLogin();

}

function ocultarLoginForm(){
    $('.login').removeClass('active');
    $('html, body').css('overflowY', 'auto');
}

function keyupLoginEventListener(e){
    const input = e.target;

    if(input.name === 'username' || input.name === 'password'){
        if(e.keyCode !== 13){
            return;
        }
        submitReuseLogin();
    }
}

function loginByFacebook(){
    const base = document.location.origin;
    window.location.href = `${base.replace('http:','https:')+':8081'}/oauth2/authorize/facebook?redirect_uri=${base}/oauth2/redirect`;
}

