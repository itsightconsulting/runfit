var _ctx = $('meta[name="_ctx"]').attr('content');
var _flag_populate = document.querySelector('meta[name="_auttic_populate"]').content === "true";
var skip_validation = 0 == 1;
var flag_form_populate = _flag_populate;

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

function specificCheckBoxes(id) {
    $("#"+id+" .chk-content").click(function() {
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

function checkBoxes() {
    $(".chk-content").click(function() {
        var _self = $(this).find('input');
        var tipoElemento = _self.attr("type");

        if(tipoElemento !== "radio"){
            var clase = _self.attr("data-body");
             if (_self.is(':checked')) {
                   $("." + clase + "").fadeOut();
                    _self.prop('checked', false);
             } else {
                    _self.prop('checked', true);
                    $("." + clase + "").fadeIn();
                  }
        } else{
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
        loop: false,
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
            center: { lat: -12.046374, lng: -77.042793 },
            zoom: 12,
            gestureHandling: 'greedy'
        });
    }

    if(document.querySelector('#pac-input') !== null){
        initMapBoxSearch();
    }

}

function initMapBoxSearch(){
    // Create the search box and link it to the UI element.
    var input = document.getElementById('pac-input');
    var searchBox = new google.maps.places.SearchBox(input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

    // Bias the SearchBox results towards current map's viewport.
    map.addListener('bounds_changed', function() {
        searchBox.setBounds(map.getBounds());
    });

    var markers = [];
    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.

    searchBox.addListener('places_changed', function() {
        var places = searchBox.getPlaces();

        if (places.length == 0) {
            return;
        }

        // Clear out the old markers.
        markers.forEach(function(marker) {
            marker.setMap(null);
        });
        markers = [];

        // For each place, get the icon, name and location.
        var bounds = new google.maps.LatLngBounds();
        places.forEach(function(place) {
            if (!place.geometry) {
                console.log("Returned place contains no geometry");
                return;
            }

            if (place.geometry.viewport) {
                // Only geocodes have viewport.
                bounds.union(place.geometry.viewport);
            } else {
                bounds.extend(place.geometry.location);
            }
        });
        map.fitBounds(bounds);
    });
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
    $(".deportistas .item").addClass("active");
    $(".deportistas .item").click(function() {
        $(".deportistas .item").removeClass("active");
        $(this).addClass("active");
        var data = $(this).attr('data-info')
        $('.description_collaborator').fadeOut();
        //change image
        var img = $(".deportistas .item");

        img.each(function(x, item) {
            let name = $(this).attr('data-info')
            if(!($(this).hasClass("active"))) {
                $(this).find('img').attr('src', _ctx+'img/public/' + name + '-bn.png')
            }
        })
        $('.' + data + '-tab').fadeIn();
        $(this).find('img').attr('src', _ctx+'img/public/' + data + '.png')
    });

    $(".atleta-nom").click(function() {
        var nm = $(this).attr('data-info');
        $(`.deportistas .item[data-info="${nm}"]`).click();
    });
}

function next_step(sheetNumber, toSheetNumber) {
    const checkList = validationByNumSheet(sheetNumber, toSheetNumber);
    if(checkList.isValid){
        if ($(".ficha-01").hasClass("active")) {
            $(".ficha-01").removeClass("active");
            $('.step-01').removeClass("active");
            $(".ficha-02").addClass("active");
            $('.step-02').addClass("active");
            $(window).scrollTop(200);

            time_line();
        } else if ($(".ficha-02").hasClass("active")) {
            $(".ficha-02").removeClass("active");
            $('.step-02').removeClass("active");
            $(".ficha-03").addClass("active");
            $('.step-03').addClass("active");
            $(window).scrollTop( 200);
            time_line();
        }
    }else{
        smallBoxAlertValidation(checkList.inputs);
    }
}

function smallBoxAlertValidation(inputsNotPassed){
    const tout = (2000*(inputsNotPassed.length)) + 4000;
    let arrCamps = inputsNotPassed.map(v=>{
        const previous = v.previousElementSibling;
        const preText = previous ? previous.textContent : "";
        const nomFinal = !preText ? v.getAttribute('data-aka').toUpperCase() : preText;
        return nomFinal;
    })
    let strCamps = Array.from(new Set(arrCamps)).map(e=>`<i class="fa fa-dot-circle-o fa-fw"></i>${e}<br>`).join('');
    if(strCamps){
        $.smallBox(
            {
                color: '#cc4d4d',
                content: `Aún tiene pendiente completar o validar los siguientes campos:<br>
                      <span style="padding-bottom: 3px"></span>
                      <p style="height: 80px; overflow-y: auto;">${strCamps}</p>`,
                timeout: tout,
                icon: "fa fa-exclamation-circle"
            }
        )
    }
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

function validationByNumSheet(numSheet, sheetNumberTo){
    let continuosValidator = true;
    const inptsNoPassed = [];
    let alls = getAllInputsByNumberSheet(numSheet);
    if(numSheet == "1" && sheetNumberTo === 3){
        alls = alls.concat(getAllInputsByNumberSheet(2));
    }

    if(numSheet == "1" && sheetNumberTo === 2){
        try {
            getCondicionesMejora();
        }catch (e) {

        }

    }
    alls.forEach(v=>{
        const isValid = $("#frm_registro").validate().element(v);
        if(!isValid){
            continuosValidator = false;
            inptsNoPassed.push(v);
        }
    });
    return {isValid: continuosValidator, inputs: inptsNoPassed};
}

function getAllInputsByNumberSheet(numSheet){
    const sheetContainer = document.querySelector(`div.inpts-${numSheet}`);
    const inputs = Array.from(sheetContainer.querySelectorAll(`input.form-control`));
    const rgsInputs = Array.from(sheetContainer.querySelectorAll(`input[type="range"]`));
    const txtAreas = Array.from(sheetContainer.querySelectorAll(`textarea.form-control`));
    const selects = Array.from(sheetContainer.querySelectorAll(`select.form-control`));
    const rdsButtons = Array.from(sheetContainer.querySelectorAll(`input[type="radio"]`));
    const fRdsButtons = Array.from(
        new Set(rdsButtons.map(v=>v.name))
    ).map(v=>document.querySelector(`input[name="${v}"]`)).filter(e=>e!=null);

    const chkbuttons = Array.from(sheetContainer.querySelectorAll(`input[type="checkbox"]`));
    const fChkbuttons = Array.from(
        new Set(chkbuttons.map(v=>v.name))
    ).map(v=>document.querySelector(`input[name="${v}"]`)).filter(e=>e!=null);
    const alls = inputs.concat(selects).concat(fRdsButtons).concat(fChkbuttons).concat(txtAreas).concat(rgsInputs);
    return alls;
}

function next_step_cs(i){
    const activeNumSheet = document.querySelector('.step.active').getAttribute('data-num-sheet');
    const sheetNumberTo = i;
    const checkList = activeNumSheet == 3 || activeNumSheet > i ? {isValid: true} : validationByNumSheet(activeNumSheet, sheetNumberTo);
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

function next_step_cs_rt(i){
    trimAllInputs(frm);
    const activeNumSheet = document.querySelector('.step.active').getAttribute('data-num-sheet');
    const sheetNumberTo = i;
    const checkList = activeNumSheet == 3 || activeNumSheet > i ? {isValid: true} : validationByNumSheet(activeNumSheet, sheetNumberTo);

    if(skip_validation || checkList.isValid){
        const all = document.querySelectorAll('.tab-pane');
        const sels = document.querySelectorAll('.step');
        all.forEach((v,ii)=>{
            v.classList.remove('active');
            v.classList.remove('in');
            sels[ii].parentElement.classList.remove('active');
        });
        all[i-1].classList.add('active');
        all[i-1].classList.add('in');
        sels[i-1].parentElement.classList.add('active');
    }
    else {
        smallBoxAlertValidation(checkList.inputs);
    }
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
    $(".login-register").removeClass('hidden');
}

$(function() {
    remarcarPaginaVisitada();
    if($(window).scrollTop()>0){
        $("nav").css({ "background-color": "#000" });
    }
    $(window).scroll(function() {
        changecolor()
    });
    FullHeightBanner();
    openMenuMobile();
    tabColaboradores();
    time_line();
    day_of_week();
    carousel();
    checkBoxes();
    activeItems();
    HorizontalEnum_EntrenaCorrectamente();
})

function remarcarPaginaVisitada(){
    try {
        //Remarcar página visitada
        const pubMenu = document.querySelector(`a[href="${window.location.pathname}"]`);
        if(pubMenu != undefined){
            const menu = pubMenu.parentElement;
            if(menu.parentElement.classList.contains('dropdown-menu')){
            }else{
                menu.classList.add('active');
            }
        }
    }catch (e) {
        console.log(e);
    }
}

function submitReuseLogin(){
    const frmLogin = document.getElementById('login-form');
    if($(frmLogin).valid()){
        const btnLogin = document.getElementById('btn-login');
        btnLogin.setAttribute('disabled','disabled');
        frmLogin.submit();
    }
}

function trimAllInputs(frm){
    frm.querySelectorAll('input').forEach(e=>{
        if(e.type !== 'file'){
            e.value = e.value.trim();
        }
    });
    frm.querySelectorAll('textarea').forEach(e=>e.value = e.value.trim());
}

function hideNavBar(){
    document.querySelector('nav').classList.add('hide');
}

function validLoginForm(){
        $("#login-form").validate({
            // Rules for form validation}
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

function FullHeightBanner() {
    var altura = window.innerHeight;
    var ancho = window.innerWidth;
    if(ancho > 800) {
        $(".banner .bg-image").height(altura);
    }
};

(function () {
    setFullNameForPublicMenuInMobile();
    validLoginForm();
})();

function setFullNameForPublicMenuInMobile(){
    const authName = $('#AuthName').text();
    const gll_nombre = atob(getCookiePb('GLL_NOMBRE_COMPLETO'));
    if(gll_nombre){
        $('.iniciar-sesion-mobile').addClass('hidden');
        const menu = document.getElementById('FullNameOnlyMenu');
        if(!menu){}else{
            if(gll_nombre.includes(" xxx")){
                menu.textContent = gll_nombre.substr(0, gll_nombre.indexOf(" xxx"));
            } else {
                menu.textContent = gll_nombre;
            }
        }
    } else{//Escondemos el boton de cerrar sesión
        $('.cerrar-sesion-mobile').addClass('hidden');
    }
    if(authName === "anonymousUser" || !authName){
        document.getElementById('FullNameOnlyMenu').textContent = "";
    }
}

function getCookiePb(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}


