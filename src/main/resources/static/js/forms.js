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
    var _URL = window.URL || window.webkitURL;

    $(input).change(function () {
        //submit the form here
        var file, imgTemp;
        if ((file = this.files[0])) {
            imgTemp = new Image();
            imgTemp.onload = function () {
                //Previsualizar
                readURL($(input)[0], img);
                cropperDemo();
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

function cropperDemo(){
    setTimeout(()=>{
        const image = document.getElementById('ImagePerfil');
        cropper = new Cropper(image, {
            aspectRatio: 1 / 1,
            crop(event) {
                console.log(event.detail.x);
                console.log(event.detail.y);
                console.log(event.detail.width);
                console.log(event.detail.height);
                console.log(event.detail.rotate);
                console.log(event.detail.scaleX);
                console.log(event.detail.scaleY);
            },
            zoomOnWheel: false,
            viewMode: 1
        });
    }, 2000);
    $('#myModalCropper').modal('show')
}

const imgTemps = [];
const srcs = [];

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

    var _URL = window.URL || window.webkitURL;

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

function agregarTextareaDinamico(e, max, clase){
    if(e.parentElement.children.length === max){
        e.classList.add('hide');
    }
    e.previousElementSibling.insertAdjacentElement('afterend', htmlStringToElement(`<textarea class="form-control mg-bt-10 ${clase}" type="text"></textarea>`));
    return e.previousElementSibling;
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
    return Array.from(document.querySelectorAll(`input.${clase}`)).map(v=>v.value).filter(v=>v.trim().length>5).join('|');
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
        }).filter(x=>x).join();
    }
}



function agregarInputsDinamico(e){
    if(e.parentElement.children.length == 6){
        e.classList.add('hide');
    }
    e.previousElementSibling.insertAdjacentElement('afterend', htmlStringToElement('<input placeholder="Nombre del contacto" class="form-control inp-cont-emer" type="text"/>'));
    e.previousElementSibling.insertAdjacentElement('afterend', htmlStringToElement('<input placeholder="Celular del contacto" class="form-control inp-cont-emer" type="text"/>'));
}
