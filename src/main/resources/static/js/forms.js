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
function readURLCs(input, img, ix, mainDivId, nomImg) {
    nomImgsGaleria.push(nomImg);
    if (input.files && input.files[ix]) {
        var reader = new FileReader();

        reader.onloadend = function (e) {
            $(img).attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[ix]);

        imgTemps.push(img);
        if(imgTemps.length === $galeria.length){
            var dvCarusel =  generarDOMCarousel(imgTemps, nomImgsGaleria);
            const mainDiv = document.querySelector('#'+mainDivId);
            if(mainDiv.children.length == 1){
                mainDiv.children[0].remove();
            }
            mainDiv.appendChild(dvCarusel);
            galeriaPerfilCarousel();

            //EventListener para remover imagenes del carusel
           $('#ImgsGaleria').unbind().on( "click",".boton-remover",function(e){
               const img = e.target;
               const nomImg = img.getAttribute('data-nom');
               nomImgsGaleria.forEach((e, ix)=>{
                   if(e === nomImg){
                       nomImgsGaleria.splice(ix, 1);
                   }
               })
               $galeria.forEach((e, ix)=>{
                   if(!nomImgsGaleria.find(g=>g === e.name)){
                       $galeria.splice(ix, 1);
                   }
               })
             e.preventDefault();

             console.log(img.id);
             var index = parseInt(img.id,10);
             imgTemps.splice(index, 1);

             $('.owl-carousel').remove();

             var dvCarusel = generarDOMCarousel(imgTemps, nomImgsGaleria);

             const mainDiv = document.querySelector('#'+mainDivId);
             mainDiv.appendChild(dvCarusel);
             galeriaPerfilCarousel();
           });
    }
 }
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
        /*for(let i=0; i<this.files.length; i++){
            $galeria.push(this.files[i]);
        }*/
        //submit the form here
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
                        readURLCs($(input)[0], img, i, mainDivId, nameImg);
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
  if($('.owl-carousel .item').size() < 5)
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
      })

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
    $(".owl-next").append('<span class="fa fa-chevron-right"></span>')

  }
}

function generarDOMCarousel(imgTemps, nomImgsGaleria){
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
        document.getElementById('Dep').innerHTML = res.lstDep.map(v=>`<option value="${v.cod}">${v.ubNombre}</option>`).join('');
        document.getElementById('Pro').innerHTML = res.lstPro.map(v=>`<option value="${v.cod}">${v.ubNombre}</option>`).join('');
        document.getElementById('Dis').innerHTML = res.lstDis.map(v=>`<option value="${v.cod}">${v.ubNombre}</option>`).join('');
        $('#Dep').val(15);
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
    const noEdit = cuentas[0].noEdit ? true : false;
    cuentas.forEach((c, ix)=>{
        c.ix = ix;
        modalBody.appendChild(htmlStringToElement(setCuentaBancariaHtmlRaw(c, noEdit)));
    })
}

function setCuentaBancariaHtmlRaw(cc, noEdit){
    return `<div class="col-sm-12 cuenta" data-id="${cc.id}">
            <h4>Cuenta <span class="cuenta-num">${++cc.ix}</span>
                ${!noEdit ? `<img src="${_ctx}img/iconos/icon_trash.svg" onclick="eliminarCuentaBanco(${cc.id})"/>`:''}
            </h4>
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
                    <input class="form-control" readonly="readonly" value="${cc.titularNumDoc}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Número Cuenta Soles
                    </label>
                    <input class="form-control" readonly="readonly" value="${cc.numeroSoles}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Número Cuenta Dólares
                    </label>
                    <input class="form-control" readonly="readonly" value="${cc.numeroDolares}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Número Interbancario Soles
                    </label>
                    <input class="form-control" readonly="readonly" value="${cc.interbancarioSoles}">
                </div>
            </div>
            <div class="col col-md-6 col-xs-12">
                <div class="form-group">
                    <label>
                        Número Interbancario Dólares
                    </label>
                    <input class="form-control" readonly="readonly" value="${cc.interbancarioDolares}">
                </div>
            </div>
        </div>
    `;
}

function verCuentasBancarias(){
    $('#myModalCC').modal();
    mostrarCuentasBancarias(ccBancarias);
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
