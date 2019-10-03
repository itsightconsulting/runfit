function imgToSvg () {
    jQuery('img.svg').each(function () {
        var $img = jQuery(this);
        var imgID = $img.attr('id');
        var imgOnClick = $img.attr('onclick');
        var imgClass = $img.attr('class');
        var imgURL = $img.attr('src');
        var rel = $img.attr('rel');
        var title = $img.attr('title');
        var dtPlace = $img.attr('data-placement');

        jQuery.get(imgURL, function (data) {
            var $svg = jQuery(data).find('svg');
            if (typeof imgID !== 'undefined') {
                $svg = $svg.attr('id', imgID);
            }
            if (typeof imgClass !== 'undefined') {
                $svg = $svg.attr('class', imgClass + ' replaced-svg');
            }
            if (typeof imgOnClick !== 'undefined') {
                $svg = $svg.attr('onclick', imgOnClick);
            }
            if (typeof imgOnClick !== 'undefined') {
                $svg = $svg.attr('onclick', imgOnClick);
            }
            if (typeof rel !== 'undefined') {
                $svg = $svg.attr('rel', rel);
            }
            if (typeof title !== 'undefined') {
                $svg = $svg.attr('title', title);
            }
            if (typeof dtPlace !== 'undefined') {
                $svg = $svg.attr('data-placement', dtPlace);
            }
            $svg = $svg.removeAttr('xmlns:a');
            if (!$svg.attr('viewBox') && $svg.attr('height') && $svg.attr('width')) {
                $svg.attr('viewBox', '0 0 ' + $svg.attr('height') + ' ' + $svg.attr('width'));
            }
            $img.replaceWith($svg);
        }, 'xml');
    });
};

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

function carouselPlanElegido() {
  $(document).ready(function() {
    var owl = $("#planElegido"),

        // rangeArr = [],
        inputType =$("input[type=range]");
    //Si no existe ese id no ejecutamos el resto
    if(!owl[0]){
        return;
    }
    owl.owlCarousel({
      'loop': false,
      margin: 10,
      nav: false,
      dots: false,
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
          items: 5,
          slideBy: 1
          
          
        }
      }
    });
        owl.on('changed.owl.carousel', function(event) {
        console.log(event.item.index);
        inputType.val(event.item.index);
         
      });
    
    $(".flechas .pull-left").click(function() {
      owl.trigger("next.owl.carousel");
    });
    $(".flechas .pull-right").on("click", function() {
      owl.trigger("prev.owl.carousel");
    });
    $("input").on("change", function(e) {
      e.preventDefault();

      $('.owl-carousel').trigger('to.owl.carousel', [inputType.val(),1,true]);
  
    });
    $('#planElegido .owl-item .item-body a').click(function(e) {
    
      e.preventDefault(); //prevent the link from being followed
      $('#planElegido .owl-item').removeClass('selected');
      $(this).parents( ".owl-item" ).addClass('selected');
    });
  });

}

function carouselCategoriaPlantilla() {
    $(document).ready(function() {
        var owl = $("#categoriaPlantillaCarousel"),
            // rangeArr = [],
            inputType =$("section#listaCategoria input[type=range]");
        //Si no existe ese id no ejecutamos el resto
        if(!owl[0]){
            return;
        }
        owl.owlCarousel({
          loop: false,
          margin: 10,
          nav:true,
          dots:true,
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
              items: 5,
              slideBy: 1
              
              
            }
          }
        });
      //   function getIndex(event) {
          
      //   }
            owl.on('changed.owl.carousel', function(event) {
            inputType.val(event.item.index);
             
          });


        $("section#listaCategoria .flechas .pull-left").click(function() {
            owl.trigger("next.owl.carousel");
        });
        $("section#listaCategoria .flechas .pull-right").on("click", function() {
            owl.trigger("prev.owl.carousel");
        });

        $("input").on("change", function(e) {
          e.preventDefault();
          console.log(inputType.val());
          // console.log(e.item.index);
          // FIGURE OUT HOW TO GET CAROUSEL INDEX
         
          $('.owl-carousel').trigger('to.owl.carousel', [inputType.val(),1,true]);
      
        });
      });
}

function carouselSubCategoriaPlantilla() {
    $(document).ready(function() {
        var owl = $("#subCategoriaPlantillaCarousel"),
            // rangeArr = [],
            inputType =$("section#listaSubCategoria input[type=range]");
        //Si no existe ese id no ejecutamos el resto
        if(!owl[0]){
            return;
        }
        owl.owlCarousel({
            loop: false,
            margin: 10,
            nav:true,
            dots:true,
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
                    items: 5,
                    slideBy: 1


                }
            }
        });
        //   function getIndex(event) {

        //   }
        owl.on('changed.owl.carousel', function(event) {
            inputType.val(event.item.index);

        });


        $("section#listaSubCategoria .flechas .pull-left").click(function() {
            owl.trigger("next.owl.carousel");
        });
        $("section#listaSubCategoria .flechas .pull-right").on("click", function() {
            owl.trigger("prev.owl.carousel");
        });

        $("input").on("change", function(e) {
            e.preventDefault();
            console.log(inputType.val());
            // console.log(e.item.index);
            // FIGURE OUT HOW TO GET CAROUSEL INDEX

            $('.owl-carousel').trigger('to.owl.carousel', [inputType.val(),1,true]);

        });
    });
}


function carouselSuspendidos() {
  $('#carouselSuspendidos').owlCarousel({
    loop:false,
    //margin:10,
    mouseDrag:false,
    nav:true,
    dots:true,
    touchDrag:false,
    responsive:{
        0:{
            items:1
        },
        600:{
            items:1
        },
        1000:{
            items:1
        }
    }
  })
}

function select_fave () {
  $( "a.fav,a.star" ).click(function() {
    $( this ).toggleClass( "selected" );
  });
}

$(function() {
    imgToSvg();
    openNav();
    carouselCategoriaPlantilla();
    carouselSubCategoriaPlantilla();
    carouselPlanElegido();
    carouselSuspendidos();
    //fancybox();
    select_fave();
});
