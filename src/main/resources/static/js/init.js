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
            margin:10,
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
    heightCard();
    miniPanelActive();
    //fancybox();
    select_fave();
    $('[rel="tooltip"]').tooltip();
    try{
        leftPanelFocus()
    }catch(e){}
});
