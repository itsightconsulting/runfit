function imgToSvg () {
    $('img.svg').each(function () {
        var $img = jQuery(this);
        var imgURL = $img.attr('src');
        var element = $img[0];

        $.get(imgURL, function (data) {
            var $svg = $(data).find('svg');
            $img[0].getAttributeNames().forEach(e=>{
                $svg = $svg.attr(e, element.getAttribute(e));
            })
            $svg = $svg.removeAttr('xmlns:a');
            if (!$svg.attr('viewBox') && $svg.attr('height') && $svg.attr('width')) {
                $svg.attr('viewBox', '0 0 ' + $svg.attr('height') + ' ' + $svg.attr('width'));
            }
            $img.replaceWith($svg);
            if($svg[0].hasAttribute('rel')){
                $($svg[0]).tooltip();
            }

        }, 'xml');
    });
}

function datepicker_init () {
    try {
        $('.datepicker_inline').datetimepicker({
            locale: 'es',
            inline: true,
            sideBySide: true,
            format: 'DD/MM/YYYY',
            //defaultDate: date,
        });
    }catch (e) {
        console.info("/**** SIMPLE INFO WARNING ****/: ", e.message ? e.message : "$(...).datetimepicker is not a function");
    }

}

function openNav() {
    var ancho = window.innerWidth;

    if (ancho > 760) {
        jQuery(document).ready(function(){
            $(".button-slide-menu").on("click", function (){
                $(".slide-menu-style").toggleClass("show");
            });
        });
    } else {
        jQuery(document).ready(function(){
            //CONFIGURACIÓN
            $(".button-slide-menu").on("click", function (){
                $(".sidebar .menu").fadeOut(function() {
                    $(".config_mobile").fadeIn();
                });
            });
            $(".closebtn").on("click", function (){
                $(".config_mobile").fadeOut(function() {
                    $(".sidebar .menu").fadeIn();
                });
            });
        });
    }
}

//SIDEBAR MOBILE
function showMenuMobile(){
    $(".sidebar").addClass("open");
    $("body").addClass("no_scroll");
    $("html,body").css("overflow", "hidden");
}

function hideMenuMobile(){
    $(".sidebar").removeClass("open");
    $("html,body").css("overflow", "auto");
}

function owlCarouselSemanal() {
    $(document).ready(function(){
        $('#carousel-semanal').owlCarousel({
            loop:false,
            margin:30,
            dots:true,
            navigation:true,
            responsive:{
                0:{
                    items:1
                },
                600:{
                    items:2
                },
                1000:{
                    items:3
                }
            }
        })
    });

    $('#carousel-semanal .owl-item.active:first-child .item').addClass('selected');

    $('#carousel-semanal .item').click(function(e) {

        e.preventDefault(); //prevent the link from being followed
        $('#carousel-semanal .item').removeClass('selected');
        $(this).addClass('selected');
    });
}

function owlCarouselVideoteca() {
    $(document).ready(function() {
        var owl = $("#videoteca-carousel"),
            // rangeArr = [],
            inputType =$("input[type=range]");
        owl.owlCarousel({
            'loop': true,
            // 'mouseDrag': false,
            // 'autoplay': true,
            nav:true,
            dots:false,
            center: true,
            stagePadding: 50,
            'responsive': {
                0: {
                    items: 1,
                    slideBy: 1

                },
                600: {
                    items: 3,
                    slideBy: 1

                },
                1280: {
                    items: 4,
                    slideBy: 1
                }
            }
        });
        //   function getIndex(event) {
        //   }
        owl.on('changed.owl.carousel', function(event) {
            console.log(event.item.index);
            inputType.val(event.item.index);

        });

        $(".go-me").click(function() {
            owl.trigger("next.owl.carousel");
        });
        $(".back-me").on("click", function() {
            owl.trigger("prev.owl.carousel");
        });
        $("input").on("change", function(e) {
            e.preventDefault();
            // console.log(e.item.index);
            // FIGURE OUT HOW TO GET CAROUSEL INDEX

            $('.owl-carousel').trigger('to.owl.carousel', [inputType.val(),1,true]);

        });
    });

}

/*function fancybox() {
    $(document).ready(function() {
        $("[data-fancybox]").fancybox();
    });
}*/

function heightCard() {
    var ancho = window.innerWidth;
    var maxH = 0;
    $("#carousel-semanal .item").each(function (i) {
        var actH = $(this).height();
        if (actH > maxH) maxH = actH;
    });
    $("#carousel-semanal .item").height(maxH);
}

function miniPanelActive() {
    $('#carousel-semanal .mini-panel').click(function(e) {
        e.preventDefault(); //prevent the link from being followed
        $('#carousel-semanal .mini-panel').removeClass('active');
        $(this).addClass('active');
    });
}

function select_fave () {
    $( "a.fav" ).click(function() {
        $( this ).toggleClass( "selected" );
    });
}

function leftPanelFocus(){
    var e=window.location.pathname;
    if(e != "/"){
        e.includes("bienvenido")
        || (document.querySelector("#SideBar1").querySelector("a[href*='"+e+"']").parentElement.className="active")
        || (document.querySelector("#SideBar2").querySelector("a[href*='"+e+"']").parentElement.className="active");
    }
}

$(function() {
    imgToSvg();
    datepicker_init();
    openNav();
    owlCarouselSemanal();
    owlCarouselVideoteca();
    //heightCard();
    miniPanelActive();
    //fancybox();
    select_fave();
    $('[rel="tooltip"]').tooltip();
    try{
        leftPanelFocus()
    }catch(e){}
});

function verTycServicios(){

    new Promise((resolve, reject)=>{
        $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: _ctx + 'gestion/cliente/get/tyc/servicios',
            blockLoading: true,
            noOne: false,
            dataType: 'json',
            success: (res)=>{
                resolve(res)
            },
            error: (xhr)=>{
                reject(xhr)
            }
        })

    }).then(res=>{
        const hasTycs = res.length;
        const genericModalId = "mdl"+new Date().getTime();
        const awsS3URLBase = "https://s3-us-west-2.amazonaws.com/rf-profile-imgs/trainer/";
        const modal = `
        <div class="modal fade" id="${genericModalId}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header" style="background: black">
                        <button type="button " class="close btn-white" data-dismiss="modal" title="Close"> <span class="glyphicon glyphicon-remove"></span></button>
                        <h4 class="modal-title text-align-center"><strong>TÉRMINOS Y CONDICIONES</strong></h4>
                    </div>
                    <div class="modal-body" style="max-height: 500px; overflow-y: auto;">
                        <div class="col col-md-12">
                            ${!hasTycs ? '<h1>Los servicios a los que se ha suscrito no cuentan con términos y condiciones</h1>':
                            `<div class="col col-md-12" style="padding-bottom: 15px">
                                <div class="col-md-5 col-xs-5"><b>Nombre Asesor</b></div>
                                <div class="col-md-5 col-xs-5"><b>Nombre Servicio</b></div>
                                <div class="col-md-2 col-xs-2 text-center"><b>T&C</b></div>
                            </div>`+
                            res.map((tyc, ix)=>`
                                <div class="col col-md-12" style="padding-bottom: 10px">
                                    <div class="col-md-5 col-xs-5">${tyc.trainer.replace(" xxx", "")}</div>
                                    <div class="col-md-5 col-xs-5">${tyc.nombreServicio}</div>
                                    <div class="col-md-2 col-xs-2 text-center">
                                        <a class="refTycUrl" data-index="${ix}" onclick="setIndexTycFile(${ix})" 
                                            href="javascript:void(0)" data-tyc-url="${tyc.tycUrl}" title="Ver">
                                            <i data-target="#modalTYC" data-toggle="modal" 
                                            class="fa fa-file-pdf-o fa-fw" style='color: rgb(204, 77, 77);'></i>
                                        </a>
                                    </div>
                                </div>
                            `).join('')}
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-md" data-dismiss="modal">CERRAR</button>
                    </div>
                </div>
            </div>
        </div>`;

        const modalElement = htmlStringToElement(modal);
        const body = document.body;
        body.appendChild(modalElement);
        const checkModalTyc = document.getElementById('modalTYC');
        if(!checkModalTyc){
            body.appendChild(instanceModalTYC());
        }
        $('#'+genericModalId).modal('show');
        $('#'+genericModalId).on('hidden.bs.modal', ()=>{
            modalElement.remove();
        });
        $('#modalTYC').on('shown.bs.modal', (e)=>{
            const doc = document.createElement('embed');
            doc.type = "application/pdf";
            doc.style = "width: 100%; height: 100%";
            const ix = document.getElementById('TycIndex').value;
            const relativeTycUrl = document.querySelector(`.refTycUrl[data-index="${ix}"]`).getAttribute('data-tyc-url');
            doc.src = `${awsS3URLBase + relativeTycUrl}`;
            document.getElementById('TYC').innerHTML = "";
            document.getElementById('TYC').appendChild(doc);
        })
    }).catch(err=> exception(err));

}

function instanceModalTYC(){
    return htmlStringToElement(`<div class="modal fade slide in" id="modalTYC" data-backdrop="static">
        <div class="modal-dialog modal-lg" style="height: 85%;">
            <div class="modal-content">
                <div class="modal-header text-right" style="background: #323639;">
                    <span class="" data-dismiss="modal" style="color: white;font-size: 1.4em;cursor: pointer"><i style="font-size: 1.3em !important;" class="fa-fw fa fa-close"></i></span>
                </div>
                <input type="hidden" id="TycIndex">
                <div class="modal-body" style="padding: 0px !important; height: 550px;" id="TYC">
                </div>
            </div>
        </div>
    </div>`);
}

function setIndexTycFile(ix){
    $('#TycIndex').val(ix);
}


