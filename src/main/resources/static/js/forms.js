function uploadAndShow(input, img, ){
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
            const dvCarusel = document.createElement('div');
            dvCarusel.className = 'owl-carousel owl-theme';
            imgTemps.forEach(v=>{
                const dvItem = document.createElement('div');
                dvItem.classList.add('item');
                dvItem.appendChild(v);
                dvCarusel.appendChild(dvItem);
            });
            const mainDiv = document.querySelector('#'+mainDivId);
            if(mainDiv.children.length == 1){
                mainDiv.children[0].remove();
            }
            mainDiv.appendChild(dvCarusel);
            carousel();
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
    carousel();
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
        e.classList.add('hide');
    }
    e.previousElementSibling.insertAdjacentElement('afterend', htmlStringToElement(`<input class="form-control mg-bt-10 ${clase}" type="text"/>`));
    return e.previousElementSibling;
}

function agregarInputDinamico(e, max, clase, maxlength){
    if(e.parentElement.children.length === max){
        e.classList.add('hide');
    }
    e.previousElementSibling.insertAdjacentElement('afterend', htmlStringToElement(`<input class="form-control mg-bt-10 ${clase}" type="text" maxlength="${maxlength}"/>`));
    return e.previousElementSibling;
}

function agregarTextareaDinamico(e, max, clase){
    if(e.parentElement.children.length === max+1){
        e.classList.add('hide');
    }
    e.previousElementSibling.insertAdjacentElement('afterend', htmlStringToElement(`<li><textarea class="form-control mg-bt-10 ${clase}" type="text"></textarea></li>`));
    return e.previousElementSibling;
}

function agregarTextareaDinamico(e, max, clase, maxlength){
    const len = e.parentElement.children.length;
    if(len === max+1){
        e.classList.add('hide');
    }
    const previous = e.previousElementSibling;
    previous.insertAdjacentElement('afterend', htmlStringToElement(`<li><textarea class="form-control mg-bt-10 ${clase}" type="text" maxlength="${maxlength}"></textarea></li>`));
    const textarea = e.previousElementSibling.firstElementChild;
    textarea.setAttribute('name', `${clase+len}`);
    $(textarea).rules("add",{rangelength: [8, 500]});
    return previous;
}

function acumuladorMas(id){
    const e = document.getElementById(id);
    const value = Number(e.value);
    if(value > -1){
        e.value = value + 1;
    }
}

function acumuladorMenos(id){
    const e = document.getElementById(id);
    const value = Number(e.value);
    if(value > 0){
        e.value = value - 1;
    }
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
