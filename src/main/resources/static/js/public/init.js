var _ctx = $('meta[name="_ctx"]').attr('content');
var skip_validation = 1 == 1;
var flag_form_populate = 1 == 1;
var initPageActive = 1;
var hiddenHeaderBar = 0 == 1;
try {
    //Remarcar página visitada
    const pubMenu = document.querySelector(`a[href="${window.location.pathname}"]`)
    if(pubMenu != undefined){
        pubMenu.parentElement.classList.add('active');
    }
    document.querySelector('.step-0'+initPageActive).classList.toggle('active');
    document.querySelector('.inpts-'+initPageActive).classList.toggle('active');
    a = document.querySelector(`a[href="${window.location.pathname}"]`);
}catch (e) {}

function time_line() {
    var total = $(".steps ol").length;
    var estilos;

    for (var i = 0; i < total; i++) {
        var altura_total = $(".steps ol")[i].clientHeight;
        var altura_rest = $(".steps ol > li:last-child")[i].clientHeight;
        $(".steps ol")[i].className += " cbp_tmtimeline_" + i + "";
        estilos = ".cbp_tmtimeline_" + i + ":before{height: " + (altura_total - altura_rest) + "px}";
        $("html").append("<style>" + estilos + "</style>");
    }
}

function HorizontalEnum_EntrenaCorrectamente() {
    var ancho = $(".entrena_correctamente ol").width();
    var item = $(".entrena_correctamente ol li").width();

    var cadena = '.entrena_correctamente ol::before { width: ' + (ancho - item) + 'px ; left: ' + item / 2 + 'px }'
    var estilo = '<style>' + cadena + '</style>'

    $('body').append(estilo);
}

function SlashBanner() {
    var alto = $(".banner h1").height();
    var cadena = '.banner h1::before { width: ' + alto + 'px ;}'
    var estilo = '<style>' + cadena + '</style>'
    $('body').append(estilo);
}

function checkBoxes() {
    $(".chk-content").click(function() {
        var _self = $(this).find('input')
        var clase = _self.attr("data-body");
        if (_self.is(':checked')) {
            $("." + clase + "").fadeOut();
            _self.prop('checked', false)
        } else {
            _self.prop('checked', true)
            $("." + clase + "").fadeIn();
        }
    })
}

function activeItems() {
    $(".list_items .chk-content").click(function() {
        var _self = $(this).find('input')
        var parent = $(this).parent().parent()
        if (_self.is(':checked')) {
            parent.addClass('active')
        } else {
            parent.removeClass('active')
        }
    })
}

function carousel() {

    $('.owl-carousel').owlCarousel({
        loop: true,
        margin: 15,
        nav: true,
        rtl: true,
        autoWidth: true,
        responsive: {
            0: {
                items: 1
            },
            600: {
                items: 3
            },
            1000: {
                items: 4
            }
        }
    })
    $(".owl-prev").empty()
    $(".owl-prev").append('<span class="fa fa-chevron-right"></span>')
    $(".owl-next").empty()
    $(".owl-next").append('<span class="fa fa-chevron-left"></span>')
}

function day_of_week() {
    $(".days-of-week li").click(function() {
        $(".days-of-week li").removeClass("active");
        $(this).addClass("active");

    })
}

var map;

function initMap() {
    var mapa = document.getElementById('map');
    if(mapa != null) {
        map = new google.maps.Map(document.getElementById('map'), {
            center: { lat: -34.397, lng: 150.644 },
            zoom: 8
        });
    }
}

function changecolor() {
    var scroll = $(window).scrollTop();
    if (scroll > 0) {
        $("nav").css({ "background-color": "#000" });
    } else {
        $("nav").css({ "background-color": "transparent" });
    }
}

function tabColaboradores() {
    $(".deportistas .item").click(function() {
        var data = $(this).attr('data-info')
        $('.description_collaborator').fadeOut();
        //change image
        var img = $(".deportistas .item");
        img.each(function(x, item) {
            var name = $(this).attr('data-info')
            if (name != data) {
                $(this).find('img').attr('src', _ctx+'img/public/' + data + '-bn.png')
            }
        })
        $('.' + data + '-tab').fadeIn();
        $(this).find('img').attr('src', _ctx+'img/public/' + data + '.png')
    })
}

function next_step(sheetNumber) {
    const checkList = validationByNumSheet(sheetNumber);
    if(checkList.isValid){
        if ($(".ficha-01").hasClass("active")) {
            $(".ficha-01").removeClass("active")
            $('.step-01').removeClass("active")
            $(".ficha-02").addClass("active")
            $('.step-02').addClass("active")
            $("body" ).scrollTop( 0 );
            time_line();
        } else if ($(".ficha-02").hasClass("active")) {
            $(".ficha-02").removeClass("active")
            $('.step-02').removeClass("active")
            $(".ficha-03").addClass("active")
            $('.step-03').addClass("active")
            $("body" ).scrollTop( 0 );
            time_line();
        }
    }else{
        smallBoxAlertValidation(checkList.inputs);
    }
}

function smallBoxAlertValidation(inputsNotPassed){
    const tout = (2000*(inputsNotPassed.length)) + 4000;
    const strCamps = inputsNotPassed.map(v=>`<i class="fa fa-dot-circle-o fa-fw"></i>
                                            ${v.getAttribute('data-aka')}<br>`).join('');
    $.smallBox(
        {
            color: '#cc4d4d',
            content: `Aún tiene pendiente completar los siguientes campos:<br>
                      <span style="padding-bottom: 3px"></span>
                      <p style="height: 80px; overflow-y: auto;">${strCamps}</p>`,
            timeout: tout,
            icon: "fa fa-exclamation-circle"
        }
    )
}
d  = [];

function smallBoxAlertValidation2(inputsNotPassed){
    const tout = (2000*(inputsNotPassed.length)) + 4000;
    const strCamps = inputsNotPassed.map(v=>`<i class="fa fa-dot-circle-o fa-fw"></i>
                                            ${v}<br>`).join('');
    $.smallBox(
        {
            color: '#cc4d4d',
            content: `Los valores de los campos no son apropiados:<br>
                      <span style="padding-bottom: 3px"></span>
                      <p style="height: 80px; overflow-y: auto;">${strCamps}</p>`,
            timeout: tout,
            icon: "fa fa-exclamation-circle"
        }
    )
}

function validationByNumSheet(numSheet){
    const sheetContainer = document.querySelector(`div.row.inpts-${numSheet}`);
    const inputs = Array.from(sheetContainer.querySelectorAll(`input.form-control`));
    const rgsInputs = Array.from(sheetContainer.querySelectorAll(`input[type="range"]`));
    const txtAreas = Array.from(sheetContainer.querySelectorAll(`textarea.form-control`));
    const selects = Array.from(sheetContainer.querySelectorAll(`select.form-control`));
    const rdsButtons = Array.from(sheetContainer.querySelectorAll(`input[type="radio"]`));
    const fRdsButtons = Array.from(
                                new Set(rdsButtons.map(v=>v.name))
                                ).map(v=>document.querySelector(`input[name="${v}"]`));

    const chkbuttons = Array.from(sheetContainer.querySelectorAll(`input[type="checkbox"]`));
    const fChkbuttons = Array.from(
                                new Set(chkbuttons.map(v=>v.name))
                                ).map(v=>document.querySelector(`input[name="${v}"]`));
    const alls = inputs.concat(selects).concat(fRdsButtons).concat(fChkbuttons).concat(txtAreas).concat(rgsInputs);
    let continuosValidator = true;
    const inptsNoPassed = [];
    alls.forEach(v=>{
        const isValid = $("#frm_registro").validate().element(v);
        if(!isValid){
            continuosValidator = false;
            inptsNoPassed.push(v);
        }
    });
    return {isValid: continuosValidator, inputs: inptsNoPassed};
}

function next_step_cs(i){
    const activeNumSheet = document.querySelector('.step.active').getAttribute('data-num-sheet');

    const checkList = activeNumSheet == 3 || activeNumSheet > i ? {isValid: true} : validationByNumSheet(activeNumSheet);
    if(skip_validation || checkList.isValid){
        const all = document.querySelectorAll('.fade-ficha');
        const sels = document.querySelectorAll('.step');
        all.forEach((v,ii)=>{
            v.classList.remove('active');
            sels[ii].classList.remove('active')
        });
        all[i-1].classList.add('active');
        sels[i-1].classList.add('active');
    }
    else {
        smallBoxAlertValidation(checkList.inputs);
    }
    time_line();
}

function openMenuMobile() {
    $("span.hamburguer").click(function () {
        var menu = $(".menu_mobile")
        if (menu.hasClass('active')) {
            menu.removeClass('active')
        } else {
            menu.addClass('active')
        }
    })
}

function openLogin() {
        var login = $(".login")
        if (login.hasClass('active')) {
            login.removeClass('active')
        } else {
            login.addClass('active')
        }
}

function goLogin() {
    $(".login-register").fadeOut();
    $(".login-sesion").fadeIn();
}

function goRegister() {
    $(".login-sesion").fadeOut();
    $(".login-register").fadeIn();
}

$(function() {
    $(window).scroll(function() {
        changecolor()
    });
    // initMap();
    openMenuMobile();
    tabColaboradores();
    time_line();
    day_of_week();
    carousel();
    checkBoxes();
    activeItems();
    HorizontalEnum_EntrenaCorrectamente();
})

function submitReuseLogin(){
    if($("#login-form").valid()){
        $("#login-form").submit();
    }
}

function hideNavBar(){
    document.querySelector('nav').classList.add('hide');
}

function validLoginForm(){
        $("#login-form").validate({
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
                username: {
                    required: true,
                },
                password: {
                    required: true,
                    minlength: 5,
                    maxlength: 20
                }
            },

            // Messages for form validation
            messages: {
                username: {
                    required: 'Por favor ingresa tu nombre de usuario',
                },
                password: {
                    required: 'Por favor ingresa tu password'
                }
            },

            // Do not change code below
            errorPlacement: function (error, element) {
                error.insertAfter(element.parent());
            }
        });
}

(function(){
    validLoginForm();
    if(hiddenHeaderBar){
        hideNavBar();
    }
})();
