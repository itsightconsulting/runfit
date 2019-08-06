function uploadAndShow(input, img){
    uploadImg(input, img);
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
const nomImgsGaleria = [];
function readURLCs(input, img, ix, mainDivId, nomImg, last) {
    nomImgsGaleria.push(nomImg);
    if (input.files && input.files[ix]) {
        var reader = new FileReader();

        reader.onloadend = function (e) {
            $(img).attr('src', e.target.result);
        };
        reader.readAsDataURL(input.files[ix]);
        imgTemps.push(img);
        if(imgTemps.length === $galeria.length && last){
            var dvCarusel =  generarDOMCarousel(imgTemps, nomImgsGaleria);
            const mainDiv = document.querySelector('#'+mainDivId);
            if(mainDiv.children.length == 1){
                mainDiv.children[0].remove();
            }
            mainDiv.appendChild(dvCarusel);
            galeriaPerfilCarousel();

            //EventListener para remover imagenes del carusel
           $('#ImgsGaleria').unbind().on( "click",".boton-remover",function(e){
               const imgDeleteId = e.target.id;
               const onClick = `confirmarEliminarDeGaleria(${imgDeleteId});`;
               $.smallBox({
                   content: "¿Estás seguro de eliminar la imagen de la galería? <p class='text-align-right'><a href='javascript:"+onClick+"' class='btn btn-primary btn-sm'>Si</a> <a href='javascript:void(0);' class='btn btn-danger btn-sm'>No</a></p>",
                   color: "#296191",
                   timeout: 10000,
                   icon: "fa fa-bell swing animated",
                   iconSmall: "",
               });
           });
        }
    }
}

function readURLCsEdit(input, img, ix, mainDivId, nomImg) {
    nomImgsGaleria.push(nomImg);
    if (input.files && input.files[ix]) {
        var reader = new FileReader();

        reader.onloadend = function (e) {
            $(img).attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[ix]);

        imgTemps.push(img);
        if(imgTemps.length === $galeria.length){
            var dvCarusel =  generarDOMCarouselEdit(imgTemps, nomImgsGaleria);
            const mainDiv = document.querySelector('#'+mainDivId);
            if(mainDiv.children.length == 1){
                mainDiv.children[0].remove();
            }
            mainDiv.appendChild(dvCarusel);
            galeriaPerfilCarousel();
        }
    }
}

function confirmarEliminarDeGaleriaEdit(id){
    const img = document.querySelector(`img.img-remover[id="${id}"]`);
    const nomImg = img.getAttribute('data-nom');
    nomImgsGaleria.forEach((e, ix)=>{
        if(e === nomImg){
            nomImgsGaleria.splice(ix, 1);
        }
    });
    $galeria.forEach((e, ix)=>{
        if(!nomImgsGaleria.find(g=>g === e.name)){
            $galeria.splice(ix, 1);
        }
    });

    console.log(img.id);
    var index = parseInt(img.id,10);
    imgTemps.splice(index, 1);

    $('.owl-carousel').remove();

    var dvCarusel = generarDOMCarouselEdit(imgTemps, nomImgsGaleria);

    const mainDiv = document.querySelector('#ImgsGaleria');
    mainDiv.appendChild(dvCarusel);
    galeriaPerfilCarousel();
}

function confirmarEliminarDeGaleria(id){
    const img = document.querySelector(`img.img-remover[id="${id}"]`);
    const nomImg = img.getAttribute('data-nom');
    nomImgsGaleria.forEach((e, ix)=>{
        if(e === nomImg){
            nomImgsGaleria.splice(ix, 1);
        }
    });
    $galeria.forEach((e, ix)=>{
        if(!nomImgsGaleria.find(g=>g === e.name)){
            $galeria.splice(ix, 1);
        }
    });

    console.log(img.id);
    var index = parseInt(img.id,10);
    imgTemps.splice(index, 1);

    $('.owl-carousel').remove();

    var dvCarusel = generarDOMCarousel(imgTemps, nomImgsGaleria);

    const mainDiv = document.querySelector('#ImgsGaleria');
    mainDiv.appendChild(dvCarusel);
    galeriaPerfilCarousel();
}

function poblarCarusel(srcs, mainDivId, baseSrc, noOptionDelete) {
    const dvCarusel = document.createElement('div');
    const mainDiv = document.querySelector('#'+mainDivId);
    if(mainDiv.firstElementChild){
        mainDiv.firstElementChild.remove();
    }

    const modal = document.querySelector('#myGallery');
    const galeriaModal = modal.querySelector('.carousel-inner');
    if(galeriaModal.hasChildNodes()){
        galeriaModal.innerHTML = "";
    }
    const indicadoresGaleria = modal.querySelector('.carousel-indicators');

    if(indicadoresGaleria.hasChildNodes()){
        indicadoresGaleria.innerHTML = "";
    }


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
        enlace.appendChild(img);

        //Añadiendo botón eliminar
        let btnEliminarImagenGaleria = '';

        if(!noOptionDelete){
            btnEliminarImagenGaleria = htmlStringToElement(`<a class="boton-remover">
                                             <img data-name="${src}" src="${_ctx}img/remove.png" class="img-remover" data-nom="andy-bn.png">
                                          </a>`);
        }


        dvItem.appendChild(enlace);
        if(!noOptionDelete){
            dvItem.appendChild(btnEliminarImagenGaleria);
        }
        dvCarusel.appendChild(dvItem);

        // ---- MODAL ----
        galeriaModal.appendChild(htmlStringToElement(`
                                                                <div class="item ${i== 0 ? 'active':''}"> 
                                                                    <img src="${baseSrc + src}" style="display: block;margin: auto"/>
                                                                    <div class="carousel-caption">
                                                                    </div>
                                                                </div>`));
        indicadoresGaleria.appendChild(htmlStringToElement(`<li data-target="#myGallery" data-slide-to="${i}" class=""></li>`));
    });

    mainDiv.appendChild(dvCarusel);
    galeriaPerfilCarousel();
}

function poblarCaruselAntiguo(srcs, mainDivId, baseSrc, noOptionDelete) {
    const dvCarusel = document.createElement('div');
    const mainDiv = document.querySelector('#'+mainDivId);
    if(mainDiv.firstElementChild){
        mainDiv.firstElementChild.remove();
    }

    const modal = document.querySelector('#myGallery');
    const galeriaModal = modal.querySelector('.carousel-inner');
    if(galeriaModal.hasChildNodes()){
        galeriaModal.innerHTML = "";
    }
    const indicadoresGaleria = modal.querySelector('.carousel-indicators');

    if(indicadoresGaleria.hasChildNodes()){
        indicadoresGaleria.innerHTML = "";
    }


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
        enlace.appendChild(img);

        //Añadiendo botón eliminar
        let btnEliminarImagenGaleria = '';

        if(!noOptionDelete){
            btnEliminarImagenGaleria = htmlStringToElement(`<a class="boton-remover">
                                             <img data-name="${src}" src="${_ctx}img/remove.png" class="img-remover-antiguo" data-nom="andy-bn.png">
                                          </a>`);
        }


        dvItem.appendChild(enlace);
        if(!noOptionDelete){
            dvItem.appendChild(btnEliminarImagenGaleria);
        }
        dvCarusel.appendChild(dvItem);

        // ---- MODAL ----
        galeriaModal.appendChild(htmlStringToElement(`
                                                                <div class="item ${i== 0 ? 'active':''}"> 
                                                                    <img src="${baseSrc + src}" style="display: block;margin: auto"/>
                                                                    <div class="carousel-caption">
                                                                    </div>
                                                                </div>`));
        indicadoresGaleria.appendChild(htmlStringToElement(`<li data-target="#myGallery" data-slide-to="${i}" class=""></li>`));
    });

    mainDiv.appendChild(dvCarusel);
    galeriaPerfilCarousel();
}

function poblarCaruselAlter(srcs, mainDivId, baseSrc) {

    const modal = document.querySelector('#myGallery');
    const galeriaModal = modal.querySelector('.carousel-inner');
    if(galeriaModal.hasChildNodes()){
        galeriaModal.innerHTML = "";
    }
    const indicadoresGaleria = modal.querySelector('.carousel-indicators');

    if(indicadoresGaleria.hasChildNodes()){
        indicadoresGaleria.innerHTML = "";
    }


    Array.from(srcs).forEach((src,i)=>{

        // ---- MODAL ----
        galeriaModal.appendChild(
            htmlStringToElement(`
                <div class="item ${i== 0 ? 'active':''}"> 
                    <img src="${baseSrc + src}" style="display: block;margin: auto"/>
                    <div class="carousel-caption">
                    </div>
                </div>`));
        indicadoresGaleria.appendChild(htmlStringToElement(`<li data-target="#myGallery" data-slide-to="${i}" class=""></li>`));
    });

    document.querySelectorAll('.img-gal').forEach((e, ix)=>{
        e.parentElement.setAttribute('data-slide-to', Number(ix));
    })
    //mainDiv.appendChild(dvCarusel);
    //galeriaPerfilCarousel();
}

function uploadImgs(input, mainDivId) {
    $(input).change(function () {
        //submit the form here
        let nomImgsFailed = '';
        var file;
        var timesonLoad = 0;
        for(let i=0; i<input.files.length;i++){
            let imgTemp;
            let img = document.createElement('img');
            if ((file = this.files[i])) {
                imgTemp = new Image();
                imgTemp.onload = function (e) {
                    const type = input.files[i].type;
                    if(type.includes("svg")){
                        $.smallBox({
                                color: 'alert',
                                content: 'No puede subir imágenes de tipo SVG'});
                        return;
                    }
                    //Previsualizar
                    const nameImg = input.files[i].name;
                    if(imgTemp.height >= 100){
                        $galeria.push(input.files[i]);

                        if(input.files.length-1 === timesonLoad) {
                            readURLCs($(input)[0], img, i, mainDivId, nameImg, true);
                        }else{
                            readURLCs($(input)[0], img, i, mainDivId, nameImg);
                        }
                    } else{
                        if(input.files.length-1 === i){
                        }
                        nomImgsFailed+='<br>'+nameImg;
                        $.smallBox({color: 'alert', content: 'La(s) imagen(es) debe(n) tener un alto mínimo de 300px: '+nomImgsFailed});
                    }
                    ++timesonLoad;
                };
                imgTemp.onerror = function (){
                    ++timesonLoad;
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

function uploadImgsEdit(input, mainDivId) {
    $(input).change(function () {
        let nomImgsFailed = '';
        var file;
        for(let i=0; i<input.files.length;i++){
            let imgTemp;
            let img = document.createElement('img');
            if ((file = this.files[i])) {
                imgTemp = new Image();
                imgTemp.onload = function () {
                    const type = input.files[i].type;
                    if(type.includes("svg")){
                        $.smallBox({
                            color: 'alert',
                            content: 'No puede subir imágenes de tipo SVG'});
                        return;
                    }
                    //Previsualizar
                    const nameImg = input.files[i].name;
                    if(imgTemp.height >= 100){
                        $galeria.push(input.files[i]);
                        readURLCsEdit($(input)[0], img, i, mainDivId, nameImg);
                    } else{
                        nomImgsFailed+='<br>'+nameImg;
                        $.smallBox({color: 'alert', content: 'La(s) imagen(es) debe(n) tener un alto mínimo de 300px: '+nomImgsFailed});
                    }

                };
                imgTemp.onerror = function (){
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
            if(name === "DesPadVarios"){
                return v.parentElement.textContent.trim()
            }
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

    /*

      if(e.parentElement.children.length == 5){

        console.log(e.nextElementSibling);

       // e.nextElementSibling.classList.remove('hide').add('show');

      }



     */
    const childsLen = e.parentElement.children.length;

    if(childsLen === 7){
         e.classList.add('hide');

     }
     const nomContacto = e.previousElementSibling.insertAdjacentElement('afterend', htmlStringToElement(`<div><input placeholder="Nombre del contacto" maxlength="60" class="form-control inp-cont-emer" name="${'ContactoEmergenciaNombre'+childsLen}" type="text"/></div>`));
     const movContacto = e.previousElementSibling.insertAdjacentElement('afterend', htmlStringToElement(`<div><input placeholder="Celular del contacto" maxlength="11" class="form-control inp-cont-emer" name="${'ContactoEmergenciaMovil'+childsLen+1}" type="text"/></div>`));
     if(frm){
         $(nomContacto.firstElementChild).rules('add', {lettersonly: true,
             rangelength: [3, 60]});
         $(movContacto.firstElementChild).rules('add', {digits: true,
             rangelength: [9, 11]});
     }
 }



/*function reducirInputsDinamico(e){

    //Sin contar al botón de agregar contactos

    var inputElementN1 = e.previousElementSibling.previousElementSibling;
    var inputElementN2 = inputElementN1.previousElementSibling;

     inputElementN1.remove();
     inputElementN2.remove();

      if(e.parentElement.children.length == 5){
              e.classList.add('hide');
              e.previousElementSibling.classList.add('show');
          }


}*/



function setEstilosParaModalGaleria(){
    const heightForImages = window.innerHeight * 0.70;
    const style = document.createElement('style');
    style.innerHTML = `
                                .modal .carousel div.item>img {
                                    height: ${heightForImages}px;
                                }
                                .carousel-indicators li{
                                    border: 1px solid #c0c152;
                                }
                                .carousel-indicators .active{
                                    background-color: #a8fa00;
                                }
                            `;
    document.getElementsByTagName('head')[0].appendChild(style);
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
    });

    document.querySelectorAll('select:not([multiple])').forEach(e=>{
        if(e.classList.contains('no-multi')){
            return;
        }
        $(e).multiselect({
            multiple: false,
            enableFiltering: true,
            enableCaseInsensitiveFiltering: true,
            maxHeight: 300,
            onChange: (e, optIsSelected)=>{
                const option = e[0];
                const select = option.parentElement;
                if(select.classList.contains('ubigeo')){
                    if(select.id === "Dep"){
                        depYprovChange(select.value, '', 1);
                    } else if(select.id === "Pro"){
                        const depId = document.getElementById('Dep').value;
                        depYprovChange(depId, select.value , 2);
                    }
                }
                $(select).valid();
            }
        });
    })
}

function bodyFocusOutEventListener(e){

    const input = e.target;
    if(input.tagName === "INPUT"){
        if(input.type==="text") {
            input.value = input.value.trim().replace(/ +/g, " ");
        } else if(input.type==="email"){
            input.value = input.value.trim().replace(/ +/g, "");
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

function searchSvgTraversing(path){
    let svg = path;
    while(svg.tagName.toUpperCase() !== "SVG"){
        svg = svg.parentElement;
    }
    return svg;
}

function  galeriaPerfilCarousel() {
  const totImgsCarousel = $('.owl-carousel .item').size();
  if(totImgsCarousel < 5)
  {
    $('.owl-carousel').owlCarousel({
          loop: false,
          margin: 15,
          nav: true,
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
                  items: 3,

              },
              1000: {
                  items: 4
              }
          }
      });

    $(".owl-prev").empty();
    $(".owl-prev").append('<span class="fa fa-chevron-left"></span>');
    $(".owl-next").empty();
    $(".owl-next").append('<span class="fa fa-chevron-right"></span>');

  } else{
    $('.owl-carousel').owlCarousel({
          loop: false,
          margin: 15,
          nav: true,
          rtl: false,
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
                  items: 4,
                  nav: true
              }
          }
      });

    $(".owl-prev").empty();
    $(".owl-prev").append('<span class="fa fa-chevron-left"></span>');
    $(".owl-next").empty();
    $(".owl-next").append('<span class="fa fa-chevron-right"></span>');
  }
    setTimeout(() => {
        const inpGal = document.querySelector('#InpGaleria');
        if (inpGal) {
            inpGal.value = "";
        }
    }, 2500);

}

function generarDOMCarousel(imgTemps, nomImgsGaleria){
 const dvCarusel = document.createElement('div');
            dvCarusel.className = 'owl-carousel owl-theme carousel-img';
            imgTemps.forEach( (img,index)=>{
                const dvItem = document.createElement('div');
                dvItem.classList.add('item');
                dvItem.setAttribute('value', "img" + (index+1) );
                dvItem.appendChild(img);
                var btCerrar = document.createElement('a');
                btCerrar.classList.add('boton-remover');
                var imgCerrar = document.createElement('img');
                imgCerrar.setAttribute('id', index );
                imgCerrar.setAttribute('src', _ctx+'img/remove.png');
                imgCerrar.classList.add('img-remover');
                imgCerrar.setAttribute('data-nom', nomImgsGaleria[index]);

                btCerrar.appendChild(imgCerrar);
                dvItem.appendChild(btCerrar);
                dvCarusel.appendChild(dvItem);
            });

 return dvCarusel;

}

function generarDOMCarouselEdit(imgTemps, nomImgsGaleria){
    const dvCarusel = document.createElement('div');
    dvCarusel.className = 'owl-carousel owl-theme carousel-img';
    $perfil.miniGaleria.split("|").forEach((v, index)=>{
        const dvItem = document.createElement('div');
        dvItem.classList.add('item');
        dvItem.setAttribute('value', "img" + (index+1) );
        const aGall = document.createElement('a');
        aGall.href = "#myGallery";
        aGall.setAttribute('data-slide-to', index);
        const img = document.createElement('img');
        img.classList.add('img-gal')
        img.src = 'https://s3-us-west-2.amazonaws.com/rf-profile-imgs/trainer/'+$perfil.id+'/'+v;
        aGall.appendChild(img);
        dvItem.appendChild(aGall);
        var btCerrar = document.createElement('a');
        btCerrar.classList.add('boton-remover');
        var imgCerrar = document.createElement('img');
        imgCerrar.setAttribute('id', index);
        imgCerrar.setAttribute('src', _ctx+'img/remove.png');
        imgCerrar.classList.add('img-remover-antiguo');
        btCerrar.setAttribute('data-name', v);

        btCerrar.appendChild(imgCerrar);
        dvItem.appendChild(btCerrar);
        dvCarusel.appendChild(dvItem);
    });
    imgTemps.forEach( (img,index)=>{
        const dvItem = document.createElement('div');
        dvItem.classList.add('item');
        dvItem.setAttribute('value', "img" + (index+1) );
        dvItem.appendChild(img);
        var btCerrar = document.createElement('a');
        btCerrar.classList.add('boton-remover');
        var imgCerrar = document.createElement('img');
        imgCerrar.setAttribute('id', index );
        imgCerrar.setAttribute('src', _ctx+'img/remove.png');
        imgCerrar.classList.add('img-remover');
        imgCerrar.setAttribute('data-nom', nomImgsGaleria[index]);

        btCerrar.appendChild(imgCerrar);
        dvItem.appendChild(btCerrar);
        dvCarusel.appendChild(dvItem);
    });

    return dvCarusel;

}

function hideShowGenericInp(ele){
    const val = ele.value;

    const eleRefId = ele.getAttribute('data-ele-hd');

    let finalElement = document.querySelector(eleRefId);

    if(finalElement.tagName === "SELECT"){
        finalElement = finalElement.parentElement.parentElement;
    }else if(finalElement.tagName ==="INPUT"){
        finalElement = finalElement.parentElement;
    }
    if(val == 0)
        finalElement.classList.add('hidden');
    else
        finalElement.classList.remove('hidden');
}

function recordsRunningValidation(input){
    const distance = Number(input.parentElement.parentElement.querySelector('.inp-distancia').value);
    if(distance === 5){
        return
    }else if(distance === 10){
        return 12*60;
    }else if(distance === 15){
        return 41*60;
    }else if(distance === 21){
        return 58*60;
    }else if(distance === 42){
        return 121*60;
    }
}



function eventListenerSexo(){
    document.querySelector('#sxMujer').addEventListener('click', (e)=>{
        $('#liFlagEmbarazo').removeClass('hidden');
    })
    document.querySelector('#sxHombre').addEventListener('click', (e)=>{
        $('#liFlagEmbarazo').addClass('hidden');
    })
}

function eventListenerPadeceDolor(){
    document.querySelector('#FlagPadeceDolorSi').addEventListener('click', (e)=>{
        setTimeout(()=>{
            $('#DescripcionDolor').valid();
        }, 100)
    })
    document.querySelector('#FlagPadeceDolorNo').addEventListener('click', (e)=>{
        setTimeout(()=>{
            $('#DescripcionDolor').valid();
        }, 100)
    })
}

function cxRq(id){
    return $(id).is(':checked');
}

function validarSeleccionNA ( element,inputName,optionValue){

    if(element.value === optionValue){
        if(element.checked){
            document.querySelectorAll('input[name="'+inputName+'"]').forEach(e => {
                if(e.value !== optionValue){
                    e.checked = false;
                    e.parentElement.parentElement.parentElement.classList.remove('active');
                }
            });
        }
    }else{
        const inpNa = document.querySelector('input[name="'+inputName+'"][value="'+optionValue+'"]');
        inpNa.checked = false;
        inpNa.parentElement.parentElement.parentElement.classList.remove('active');
    }
}

function validUniqueEmailOrUsernameOrNomPag(input, pathURLDiff){
    input.setAttribute('disabled', 'disabled');
    input.previousElementSibling.previousElementSibling.classList.add('hidden');
    input.previousElementSibling.classList.remove('hidden');
    $.ajax({
        type: 'GET',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        url: _ctx+'p/validacion-'+pathURLDiff,
        blockLoading: false,
        noOne: true,
        data: {valor: input.value},
        dataType: 'json',
        success: function(res){
            input.previousElementSibling.classList.add('hidden');
            if(!res){
                if(verifiedNames){
                    verifiedNames.push(input.value);
                }
                input.previousElementSibling.previousElementSibling.classList.remove('hidden');
            }else{
                if(pathURLDiff === 'username'){
                    $(input).rules('add', {dynUnique: input.value, messages:{dynUnique: 'El nombre de usuario ingresado ya se encuentra registrado'}});
                }
                else if(pathURLDiff === 'nompag'){
                    $(input).rules('add', {dynUnique: input.value, messages:{dynUnique: 'El nombre de página ingresado ya se encuentra registrado'}});
                }
                else{
                    $(input).rules('add', {dynUnique: input.value});
                }
                $(input).valid();
            }
            input.removeAttribute('disabled');
        },
        error: (err)=>{
            exception(err);
        }
    })
}


/* UBIGEO */

function depYprovChange(depId, provId, pos){
    const scpY = '&amp;';
    const url = pos == 1 ? `p/ubigeo/get/peru-prov-by-dep?depId=${depId}` : `p/ubigeo/get/peru-dis-by-dep-and-prov?depId=${depId+scpY.substr(0,1)}provId=${provId}`;
    fetch(_ctx+url)
        .then(res=> {
                if(res.ok){
                    return res.json();
                }
            }
        ).then(res=>{
        const lstProOrDis = pos == 1 ? 'lstPro' : 'lstDis';
        const pfSel = pos == 1 ? '<option value="">Seleccione</option>' : '';
        document.getElementById(pos==1 ? 'Pro' : 'Dis').innerHTML = pfSel+res[lstProOrDis].map(v=>`<option value="${v.cod}">${v.ubNombre}</option>`).join('');
        if(pos == 1){
            const dis = document.getElementById('Dis');
            dis.innerHTML = '<option value="">Seleccione provincia</option>';
            $(dis).multiselect('rebuild');
        }
        $(pos == 1 ? '#Pro' : '#Dis').multiselect('rebuild');
    }).catch((err)=>{
        exception(err);
    });
}

function getUbigeoPeruLim(){
    fetch(_ctx+'p/ubigeo/get/peru-lim')
        .then(res=> {
                if(res.ok){
                    return res.json();
                }
            }
        ).then(res=>{
        document.getElementById('Dep').innerHTML = '<option value="">Seleccione</option>'+res.lstDep.map(v=>`<option value="${v.cod}">${v.ubNombre}</option>`).join('');
        document.getElementById('Pro').innerHTML = '<option value="">Seleccione Departamento</option>';
        document.getElementById('Dis').innerHTML = '<option value="">Seleccione Provincia</option>';
        $('#Dep').multiselect('rebuild');
        $('#Dis').multiselect('rebuild');
        $('#Pro').multiselect('rebuild');
    }).catch((err)=>{
        exception(err);
    });
}

function populateUbigeo(){
    fetch(_ctx+'p/ubigeo/get/peru-lim')
        .then(res=> {
                if(res.ok){
                    return res.json();
                }
            }
        ).then(res=>{
        document.getElementById('Dep').innerHTML = res.lstDep.map(v=>`<option value="${v.cod}">${v.ubNombre}</option>`).join('');
        document.getElementById('Pro').innerHTML = res.lstPro.map(v=>`<option value="${v.cod}">${v.ubNombre}</option>`).join('');
        document.getElementById('Dis').innerHTML = res.lstDis.map(v=>`<option value="${v.cod}">${v.ubNombre}</option>`).join('');
        $('#Dep').val('15');
        $('#Dis').val('01');
        $('#Pro').val('01');
        $('#Dep').multiselect('rebuild');
        $('#Dis').multiselect('rebuild');
        $('#Pro').multiselect('rebuild');
    }).catch((err)=>{
        exception(err);
    });
}

function getIdiomas(){
    const idiomas = document.querySelector('#Idiomas');
    _idiomas.forEach(e=>{
        idiomas.appendChild(htmlStringToElement(`<option value="${e.cd}">${e.nombre}</option>`));
    })
}

function mostrarCuentasBancarias(cuentas){
    const modalBody = document.getElementById('ModalCCs');
    modalBody.innerHTML = "";
    if(!cuentas.length){
        modalBody.innerHTML = "<div class='alert alert-info'>Aún no ha agregado ninguna cuenta bancaria</div>";
        return;
    }
    const noDelete = cuentas[0].noDelete ? true : false;
    const rules = getCuentaBancariaRules();
    cuentas.forEach((c, ix)=>{
        c.ix = ix;
        modalBody.appendChild(htmlStringToElement(setCuentaBancariaHtmlRaw(c, noDelete, rules)));
    });
}

function getCuentaBancariaRules(){
    const rules = new Object();
    rules.titularCuenta = 60;
    rules.titularNumDoc = 12;
    rules.numeroSoles = 30;
    rules.numeroDolares = 30;
    rules.interbancarioSoles = 30;
    rules.interbancarioDolares = 30;
    return rules;
}


function populateBancos(){
    document.getElementById('BancoId').innerHTML = banks.map(e => `<option value="${e.id}">${e.nombre}</option>`).join('');
}

function setCuentaBancariaHtmlRaw(cc, noDelete, rules){
    const banco = document.querySelector('#BancoId').cloneNode(true);
    banco.name = "";
    banco.id = "";
    banco.classList.remove('hidden');
    banco.classList.add('cc-banco');
    banco.querySelector('option[value="' + cc.bancoId + '"]').setAttribute("selected", "selected");

    const tipoDoc = document.querySelector('#TitularTipoDoc').cloneNode(true);
    tipoDoc.name = "";
    tipoDoc.id = "";
    tipoDoc.classList.remove('hidden');
    tipoDoc.classList.add('cc-tipo-doc');
    tipoDoc.querySelector('option[value="' + cc.titularTipoDoc + '"]').setAttribute("selected", "selected");

    return `<div class="col-sm-12 cuenta" data-id="${cc.id ? cc.id : cc.ix}">
            <h4>Cuenta <span class="cuenta-num">${cc.ix + 1}</span>
                <img class="${noDelete ? 'hidden' : ''}" src="${_ctx}img/iconos/icon_trash.svg" onclick="eliminarCuentaBanco(${cc.id ? cc.id : cc.ix})" title="Eliminar cuenta bancaria"/>
                <img src="${_ctx}img/iconos/icon_disquete.svg" onclick="editarCuentaBancaria(${cc.id ? cc.id : cc.ix})" title="Guardar cambios a cuenta bancaria"/>
            </h4>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Banco<span class="obligatorio"></span>
                    </label>
                    ${banco.outerHTML}
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Titular<span class="obligatorio"></span>
                    </label>
                    <input name="TitularCuenta" class="form-control cc-titular" value="${cc.titular}" 
                        maxlength="${rules.titularCuenta}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Tipo Documento
                    </label>
                    ${tipoDoc.outerHTML}
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Número Documento
                    </label>
                    <input name="TitularNumDoc" class="form-control cc-num-doc" value="${cc.titularNumDoc ? cc.titularNumDoc : ''}"
                        maxlength="${rules.titularNumDoc}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Número Cuenta Soles
                    </label>
                    <input name="NumeroSoles" class="form-control cc-num-cs" value="${cc.numeroSoles ? cc.numeroSoles : ''}"
                        maxlength="${rules.numeroSoles}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Número Cuenta Dólares
                    </label>
                    <input name="NumeroDolares" class="form-control cc-num-cd" value="${cc.numeroDolares ? cc.numeroDolares : ''}"
                        maxlength="${rules.numeroDolares}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Número Interbancario Soles
                    </label>
                    <input name="InterbancarioSoles" class="form-control cc-num-ccis" value="${cc.interbancarioSoles ? cc.interbancarioSoles : ''}"
                         maxlength="${rules.interbancarioSoles}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Número Interbancario Dólares
                    </label>
                    <input name="InterbancarioDolares" class="form-control cc-num-ccid" value="${cc.interbancarioDolares ? cc.interbancarioDolares : ''}"
                        maxlength="${rules.interbancarioDolares}">
                </div>
            </div>
        </div>
    `;
}

function editarCuentaBancaria(ccId){
    const divCC = document.querySelector('#ModalCCs .cuenta[data-id="'+ccId+'"]');
    const cuenta = {};
    const val = Array.from(divCC.querySelectorAll('input.form-control')).filter(e=>$(e).valid() !== true).length ? false : true;
    if(val){
        cuenta.bancoId = divCC.querySelector('.cc-banco').value;
        cuenta.titular = divCC.querySelector('.cc-titular').value;
        cuenta.titularTipoDoc = divCC.querySelector('.cc-tipo-doc').value;
        cuenta.titular = divCC.querySelector('.cc-titular').value;
        cuenta.titularNumDoc = divCC.querySelector('.cc-num-doc').value;
        cuenta.numeroSoles = divCC.querySelector('.cc-num-cs').value;
        cuenta.numeroDolares = divCC.querySelector('.cc-num-cd').value;
        cuenta.interbancarioSoles = divCC.querySelector('.cc-num-ccis').value;
        cuenta.interbancarioDolares = divCC.querySelector('.cc-num-ccid').value;

        const qCuenta = ccBancarias.find(e=>(e.id ? e.id : e.ix)==ccId);
        qCuenta.bancoId = cuenta.bancoId;
        qCuenta.titular = cuenta.titular;
        qCuenta.titularTipoDoc = cuenta.titularTipoDoc;
        qCuenta.titular = cuenta.titular;
        qCuenta.titularNumDoc = cuenta.titularNumDoc;
        qCuenta.numeroSoles = cuenta.numeroSoles;
        qCuenta.numeroDolares = cuenta.numeroDolares;
        qCuenta.interbancarioDolares = cuenta.interbancarioDolares;
        qCuenta.interbancarioSoles = cuenta.interbancarioSoles;
        $.smallBox({content: 'La cuenta se ha actualizado con éxito'});
    }else{
        $.smallBox({
            color: 'alert',
            content: 'Los nuevos datos de la cuenta son inválidos, revíselos!'}
        );
    }


}

function setCuentaBancariaHtmlRawNoOps(cc){
    return `<div class="col-sm-12 cuenta" data-id="${cc.id}">
            <h4>Cuenta <span class="cuenta-num">${++cc.ix}</span></h4>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Banco
                    </label>
                    <input class="form-control" readonly="readonly" value="${banks.find(b=>b.id===Number(cc.bancoId)).nombre}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Titular
                    </label>
                    <input class="form-control" readonly="readonly" value="${cc.titular}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Tipo Documento
                    </label>
                    <input class="form-control" readonly="readonly" value="${_tpdocs.find(b=>b.id===Number(cc.titularTipoDoc)).nombre}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Número Documento
                    </label>
                    <input class="form-control" readonly="readonly" value="${cc.titularNumDoc ? cc.titularNumDoc : ''}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Número Cuenta Soles
                    </label>
                    <input class="form-control" readonly="readonly" value="${cc.numeroSoles ? cc.numeroSoles : ''}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Número Cuenta Dólares
                    </label>
                    <input class="form-control" readonly="readonly" value="${cc.numeroDolares ? cc.numeroDolares : ''}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Número Interbancario Soles
                    </label>
                    <input class="form-control" readonly="readonly" value="${cc.interbancarioSoles ? cc.interbancarioSoles : ''}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Número Interbancario Dólares
                    </label>
                    <input class="form-control" readonly="readonly" value="${cc.interbancarioDolares ? cc.interbancarioDolares : ''}">
                </div>
            </div>
        </div>
    `;
}

function verCuentasBancarias(){
    $('#myModalCC').modal();
    mostrarCuentasBancarias(ccBancarias);
}

function verCuentasBancariasNoOps(){
    $('#myModalCC').modal();
    const modalBody = document.getElementById('ModalCCs');
    modalBody.innerHTML = "";
    ccBancarias.forEach((c, ix)=>{
        c.ix = ix;
        modalBody.appendChild(htmlStringToElement(setCuentaBancariaHtmlRawNoOps(c)));
    });
}

function setHeightForModals(arrModalIds){
    arrModalIds.forEach(e=>{
        //Modals
        const mdlCcs = document.getElementById(e);
        const body = mdlCcs.querySelector('.modal-body');
        body.style.height = ($(window).height()-220)+"px";
        body.style.maxHeight = ($(window).height()-220)+"px";
        body.style.overflowY ="auto";
        if($(window).width()<720){
            mdlCcs.firstElementChild.style.width = ($(window).width()-15)+"px";
        } else {
            mdlCcs.firstElementChild.style.width = "720px";
        }
    })
}



function checkRedesAndNota(t){
    if(t.redes != undefined && t.redes.length > 4){
        const existsFb = t.redes.includes('fb:');
        const r = document.querySelector('#RsFb');
        if(existsFb){
            r.classList.remove('hidden');
            const pre = t.redes.slice(t.redes.indexOf("fb:")+3);
            const f = pre.includes("|") ? pre.slice(0, pre.indexOf("|")) : pre;
            r.href = f;
        }else{r.classList.add('hidden')}
        const existsIn = t.redes.includes('in:');
        const inst = document.querySelector('#RsIn');
        if(existsIn){
            inst.classList.remove('hidden');
            const pre = t.redes.slice(t.redes.indexOf("in:")+3);
            const f = pre.includes("|") ? pre.slice(0, pre.indexOf("|")) : pre;
            inst.href = f;
        }else{inst.classList.add('hidden')}
        const existsYo = t.redes.includes('yo:');
        const yo = document.querySelector('#RsYo');
        if(existsYo){
            yo.classList.remove('hidden');
            const pre = t.redes.slice(t.redes.indexOf("yo:")+3);
            const f = pre.includes("|") ? pre.slice(0, pre.indexOf("|")) : pre;
            yo.href = f;
        }else{yo.classList.add('hidden')}
        document.getElementById('lblRedes').classList.remove('hidden');
    }

    if(t.nota){
        document.getElementById('divNotaFinal').classList.remove('hidden');
        document.getElementById('NotaFinal').innerHTML =  t.nota.replace(/\r?\n/g, '<br />');
    }
}

function cleanPaqueteCampos(){
    document.querySelector('#NombreTarifario').value = "";
    document.querySelector('#FrecuenciaPaquete').selectedIndex = 0;
    document.querySelector('#txtCantidadPersonas').value = 0;
    document.querySelector('#txtCantidadMeses').value = 0;
    document.querySelector('#txtCantidadSesiones').value = 0;
    document.querySelector('#PrecioPaquete').value = '0.00';
    document.querySelector('#Moneda').selectedIndex = 0;
    const frecuencia = document.querySelector('#FrecuenciaPaquete');
    frecuencia.selectedIndex = 0;
    $(frecuencia).multiselect('rebuild');
}

function checkingValidExtension(input){
    if($(frm).validate().settings.rules[input.name]){
        const extensiones = $(frm).validate().settings.rules[input.name].extension.split("|");
        let fileExt = input.files[0];

        if(!fileExt){
            return false;
        }
        fileExt = fileExt.type.split("/")[1];
        const exists = extensiones.filter(ext => ext === fileExt);
        if(exists.length){
            return true;
        } else {
            const ext = $(frm).validate().settings.rules[input.name].extension.toUpperCase();
            input.value = "";
            $.smallBox({
                color: 'alert',
                content: `<i class="fa fa-fw fa-exclamation-circle"></i>Solo se permite cargar archivos de tipo: ${ext}`
            });
            return false;
        }
    }
}

function imgToSvgForRegistroTrainer() {

    $('#Tarifarios img.tarifa-svc').each(function () {
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