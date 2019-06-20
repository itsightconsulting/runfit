function uploadAndShow(input, img){
    uploadImg(input, img)
}

function readURL(input, img) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $(img).attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}

function uploadImg(input, img) {

    $(input).change(function () {
        //submit the form here
        var file, imgTemp;
        if ((file = this.files[0])) {
            imgTemp = new Image();
            imgTemp.onload = function () {
                //Previsualizar
                readURL($(input)[0], img);
            };
            imgTemp.onerror = function () {
                $(input).val("");
                $.smallBox({
                    content: "<i> No se ha seleccionado una imagen válida!</i>",
                    color: "#8a6d3b",
                    iconSmall: "fa fa-warning fa-2x fadeInRight animated",
                    timeout: 3500,
                });
            };
            imgTemp.src = _URL.createObjectURL(file);
        }
    });
}

const imgTemps = [];
//const srcs = [];

function readURLCs(input, img, ix, mainDivId) {
    if (input.files && input.files[ix]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $(img).attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[ix]);
        imgTemps.push(img);
        if(input.files.length == ix+1){

            var dvCarusel =  generarDOMCarousel(imgTemps);
            const mainDiv = document.querySelector('#'+mainDivId);
            if(mainDiv.children.length == 1){
                mainDiv.children[0].remove();
            }
            mainDiv.appendChild(dvCarusel);
           galeriaPerfilCarousel();


           $('#ImgsGaleria').unbind().on( "click",".boton-remover",function(e){

             console.log("test");
             e.preventDefault();

             console.log(e.target.id);
                 var index = parseInt(e.target.id,10);
                 var divValue = 'img'+ (index+1);
                 imgTemps.splice(index, 1);

                 $('.owl-carousel').remove();

                 var dvCarusel = generarDOMCarousel(imgTemps);

                  const mainDiv = document.querySelector('#'+mainDivId);
                   mainDiv.appendChild(dvCarusel);
                  galeriaPerfilCarousel();

            });





    }



 }

}

function poblarCarusel(srcs, mainDivId, baseSrc) {
    const dvCarusel = document.createElement('div');
    const mainDiv = document.querySelector('#'+mainDivId);
    Array.from(srcs).forEach((src,i)=>{
        let img = document.createElement('img');
        img.src = baseSrc + src;
        img.setAttribute('data-toggle', 'modal');
        img.setAttribute('data-target', '#myModal');
        img.classList.add('img-gal');
        dvCarusel.className = 'owl-carousel owl-theme';
        dvCarusel.id="fotos-carousel";
        const dvItem = document.createElement('div');
        dvItem.classList.add('item');

        let enlace = document.createElement('a');
        enlace.href="#myGallery";
        enlace.setAttribute('data-slide-to', i);
        enlace.appendChild(img)

        dvItem.appendChild(enlace);
        dvCarusel.appendChild(dvItem);
        const modal = document.querySelector('#myGallery');
        const galeriaModal = modal.querySelector('.carousel-inner');
        galeriaModal.appendChild(htmlStringToElement(`
                                                                <div class="item ${i== 0 ? 'active':''}"> 
                                                                    <img src="${baseSrc + src}" style="display: block;margin: auto"/>
                                                                    <div class="carousel-caption">
                                                                    </div>
                                                                </div>`));
        const indicadoresGaleria = modal.querySelector('.carousel-indicators');
        indicadoresGaleria.appendChild(htmlStringToElement(`<li data-target="#myGallery" data-slide-to="${i}" class=""></li>`));
    })

    mainDiv.appendChild(dvCarusel);
    galeriaPerfilCarousel();
}
function uploadImgs(input, mainDivId) {

    $(input).change(function () {
        //submit the form here
        var file, imgTemp;
        for(let i=0; i<input.files.length;i++){
            let img = document.createElement('img');
            if ((file = this.files[i])) {
                imgTemp = new Image();
                imgTemp.onload = function () {
                    //Previsualizar
                    readURLCs($(input)[0], img, i, mainDivId);
                };



                imgTemp.onerror = function () {
                    $(input).val("");
                    $.smallBox({
                        content: "<i> No se ha seleccionado una imagen válida!</i>",
                        color: "#8a6d3b",
                        iconSmall: "fa fa-warning fa-2x fadeInRight animated",
                        timeout: 3500,
                    });
                };
                imgTemp.src = _URL.createObjectURL(file);
            }
        }
    });
}

function agregarInputDinamico(e, max, clase){
    if(e.parentElement.children.length === max){
        e.classList.add('hidden');
    }
    e.previousElementSibling.insertAdjacentElement('afterend', htmlStringToElement(`<input class="form-control mg-bt-10 ${clase}" type="text"/>`));
    return e.previousElementSibling;
}

function agregarInputDinamico(e, max, clase, maxlength){
    if(e.parentElement.children.length === max){
        e.classList.add('hidden');
    }
    e.previousElementSibling.insertAdjacentElement('afterend', htmlStringToElement(`<input class="form-control mg-bt-10 ${clase}" type="text" maxlength="${maxlength}"/>`));
    return e.previousElementSibling;
}

function agregarTextareaDinamico(e, max, clase){
    if(e.parentElement.children.length === max+1){
        e.classList.add('hidden');
    }
    e.previousElementSibling.insertAdjacentElement('afterend', htmlStringToElement(`<li><textarea class="form-control mg-bt-10 ${clase}" type="text"></textarea></li>`));
    return e.previousElementSibling;
}

function agregarTextareaDinamico(e, max, clase, maxlength){
    const len = e.parentElement.children.length;
    const previous = e.previousElementSibling;
    previous.insertAdjacentElement('afterend', htmlStringToElement(`<li><textarea class="form-control mg-bt-10 ${clase}" type="text" maxlength="${maxlength}"></textarea></li>`));
    const textarea = previous.firstElementChild;
    textarea.setAttribute('name', `${clase+len}`);
    $(textarea).rules("add",{rangelength: [8, 500]});
    return previous.nextElementSibling;
}

function acumuladorMas(id){
    const e = document.getElementById(id);
    const value = Number(e.value);
    if(value > -1){
        e.value = value + 1;
    }
    try {
        $(e).valid();
    }catch (e) {}
}

function acumuladorMenos(id){
    const e = document.getElementById(id);
    const value = Number(e.value);
    if(value > 0){
        e.value = value - 1;
    }
    try {
        $(e).valid();
    }catch (e) {}
}

function getValuesByClass(clase){
    return Array.from(document.querySelectorAll(`input.${clase}`)).map(v=>v.value).filter(v=>v.trim().length>4).join('|');
}

function getValuesConcatInpCheckbox(name){
    const inpts = document.querySelectorAll(`input[name="${name}"]:checked`);
    if(inpts.length > 0){
        return Array.from(inpts).map((v)=>
        {
            if(v.value === "Otro"){
                const txtOtro = document.getElementById(`${name}Otro`);
                if(txtOtro.value.trim().length>0) {
                    return txtOtro.value;
                }
            }else{
                return v.value;
            }
        }).filter(x=>x).join('|');
    }
}

function agregarInputsDinamico(e){
    if(e.parentElement.children.length == 6){
        e.classList.add('hide');
    }
    e.previousElementSibling.insertAdjacentElement('afterend', htmlStringToElement('<input placeholder="Nombre del contacto" class="form-control inp-cont-emer" type="text"/>'));
    e.previousElementSibling.insertAdjacentElement('afterend', htmlStringToElement('<input placeholder="Celular del contacto" class="form-control inp-cont-emer" type="text"/>'));
}

function getValoracion(cantPerVal, totalVal){
    const valoraciones = cantPerVal;
    const acumuladoValoracion = totalVal;
    let valoracion = valoraciones === 0 ? 0 : (acumuladoValoracion/valoraciones).toFixed(2);
    valoracion = Number(Number(Number(valoracion)).toFixed(2));
    const exactaValoracion = valoracion;
    let dotFloat = Number((valoracion%1).toFixed(2).slice(-2));
    if(dotFloat<=10){
        dotFloat=0;
    }
    else if(dotFloat<=35){
        dotFloat=0.25;
    }
    else if(dotFloat<=60){
        dotFloat=0.50;
    }
    else if(dotFloat<=85){
        dotFloat=0.75;
    }
    else {
        dotFloat=1;
    }
    const aproximadaValoracion = Math.trunc(valoracion)+dotFloat;
    return {
        aproximadaValoracion: aproximadaValoracion,
        exactaValoracion: exactaValoracion,
        valoraciones: valoraciones,
    }
}

function csBtoa(word){
    return btoa(word).replace(/\=/g,'');
}

function checkBoxAndRadioValidationEventListener(e, input, clases){
    if(clases.contains('chk-content')){
        e.stopPropagation();
        if(input.hasChildNodes()){
            const truly = input.firstElementChild;
            if(truly.tagName === "INPUT"){
                $(truly).valid();
            }
        }
    }
    else if(clases.contains('checkmark')){
        e.stopPropagation();
        if(!input.hasChildNodes()){
            const truly = input.previousElementSibling;
            if(truly.tagName==="INPUT"){
                $(truly).valid();
            }
        }
    }
}

function doMultiselectCheckBox(){
    document.querySelectorAll('select[multiple="multiple"]').forEach(e=>{
        $(e).multiselect({
            enableFiltering: true,
            enableCaseInsensitiveFiltering: true,
            maxHeight: 300,
            onChange: (e, optIsSelected)=>{
                const option = e[0];
                const select = option.parentElement;
                $(select).valid();
            }
        });
    })

    document.querySelectorAll('select:not([multiple])').forEach(e=>{
        $(e).multiselect({
            multiple: false,
            enableFiltering: true,
            enableCaseInsensitiveFiltering: true,
            maxHeight: 300,
            onChange: (e, optIsSelected)=>{
                const option = e[0];
                const select = option.parentElement;
                $(select).valid();
            }
        });
    })
}

function bodyFocusOutEventListener(e){
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

function activeTooltips(){
    const all = document.querySelectorAll('i[rel="tooltip"]');
    all.forEach(e=>{
        $(e).tooltip();
    })
}

function  galeriaPerfilCarousel() {

  if($('.owl-carousel .item').size() < 5)
  {

    $('.owl-carousel').owlCarousel({
          loop: false,
          margin: 15,
          nav: false,
          rtl: false,
          autoWidth: true,
          rewind: true,
          mouseDrag: false,
          touchDrag: true,
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



  }else{

    $('.owl-carousel').owlCarousel({
          loop: false,
          margin: 15,
          nav: true,
          rtl: true,
          autoWidth: true,
          rewind: true,
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
      });

    $(".owl-stage").css({"right":"70px"});
    $(".owl-prev").empty()
    $(".owl-prev").append('<span class="fa fa-chevron-right"></span>')
    $(".owl-next").empty()
    $(".owl-next").append('<span class="fa fa-chevron-left"></span>')

  }
}

function generarDOMCarousel(imgTemps){


 const dvCarusel = document.createElement('div');
            dvCarusel.className = 'owl-carousel owl-theme carousel-img';
            imgTemps.forEach( (v,index)=>{
                const dvItem = document.createElement('div');
                dvItem.classList.add('item');
                dvItem.setAttribute('value', "img" + (index+1) );
                dvItem.appendChild(v);
                var btCerrar = document.createElement('a');
                btCerrar.classList.add('boton-remover');
                var imgCerrar = document.createElement('img');
                imgCerrar.setAttribute('id', index );
                imgCerrar.setAttribute('src', _ctx+'img/remove.png');
                imgCerrar.classList.add('img-remover');



                btCerrar.appendChild(imgCerrar);
                dvItem.appendChild(btCerrar);
                dvCarusel.appendChild(dvItem);

            });

 return dvCarusel;

}
