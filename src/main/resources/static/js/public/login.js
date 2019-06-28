const body = document.querySelector('body');
const formRecuperacion = document.getElementById('recover-pass-form');
const btnRecuperar = document.getElementById('btnRecuperar');
const btnCambiar = document.getElementById('btnCambiar');
const btnRegistrar = document.getElementById('btn-register');

(function () {
    init();



})();

function init(){
    eventos();
    validacionFormularioVisitante();



}

function eventos(){
    if(btnRecuperar){//Iniciar recuperación password
        btnRecuperar.addEventListener('click', preSendFormRecuperacion);
    }
    if(btnCambiar){//Finalizar recuperación password con el cambio
        btnCambiar.addEventListener('click', preSendFormCambiar);
    }

    if(btnRegistrar){//Finalizar recuperación password con el cambio
        btnRegistrar.addEventListener('click', registro);
    }

    body.addEventListener('focusout', bodyClickEventListener);
    body.addEventListener('keyup', bodyKeyupEventListener);
}

function bodyClickEventListener(e){
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

function registro() {
    if ($("#register-form").valid()) {
        const params = getFormData($('#register-form'));

        params.password = $('#passwordRegister').val();

       delete params.passwordRegister;


       console.log(params);

       $.ajax({
                type: 'POST',
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                url: _ctx+'p/visitante/registro',
                dataType: "json",
                data: params,
                success: function (data, textStatus) {
                    if (textStatus == "success") {
                        if (data == "-9") {
                        }
                    }
                },
                error: function (xhr) {
                   //exception(xhr);
                },
                complete: function () {
                }
            });



        }
}

function validacionFormularioVisitante(){

    $("#register-form").validate({
        // Rules for form validation}
        ignore: ".ignore",
        highlight: function (element) {
            $(element).parent().removeClass('state-success').addClass("state-error");
            $(element).removeClass('valid');
        },
        unhighlight: function (element) {
            $(element).parent().removeClass("state-error").addClass('state-success');
            $(element).addClass('valid');
        },
        rules: {
            nombres: {
                required: true,
                rangelength: [1, 30],
                lettersonly:true
            },
            apellidos: {
                required: true,
                rangelength: [3, 30],
                lettersonly: true
            },
            email: {
                required: true,
                rangelength: [7, 30],
                emailValid:true
            },
            passwordRegister: {
                required: true,
                rangelength: [8, 30]
            },
            passwordConfirmation: {
                required: true,
                equalTo: "#passwordRegister"
            },
        },
        // Messages for form validation
        messages: {
            passwordConfirmation : {
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
