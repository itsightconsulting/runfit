/*An array containing all the country names in the world:*/

const body = document.querySelector('body');
const btnLimpiar = document.getElementById('btnLimpiar');
const btnBuscar = document.getElementById('btnBuscar');
const initLimit = 100;
const initOffset = 0;
let totalRows = 0;
let queryParams = resetPagination();
function resetPagination(){
    return {
        limit: initLimit,
        offset: initOffset
    };
}

(function(){
    btnLimpiar.addEventListener('click', ()=>{
        //All reset
        limpiarMainForm();
        busquedaDinamica();
        document.querySelectorAll('select').forEach(e=>{
            $(e).multiselect('refresh');
        });
    });
    btnBuscar.addEventListener('click', busquedaDinamica);
    document.querySelector('body').addEventListener('focusout', bodyFocusOutEventListener);
    init();
})();

function init(){
    onlyBusquedaDinamica();
    scrollEventBusquedaAutomatica();
    doMultiselectCheckBox();
}

function busquedaDinamica(){
    //Clean resulset
    document.querySelector('#Trainers').innerHTML = "";
    totalRows = 0;
    queryParams = resetPagination();
    onlyBusquedaDinamica();
}

function onlyBusquedaDinamica(noCleanPrevious){
    document.querySelector('.dv-rows').classList.add('hidden');
    const divTrainers = document.getElementById('Trainers');
    if(!noCleanPrevious){
        divTrainers.innerHTML = spinnerLoadingHtmlRaw();
    }

    const d = getFormData($('#frm_registro'));
    d.niveles = Array.from(document.getElementById('Niveles').querySelectorAll("option:checked"),e=>e.value).join('|');
    d.idiomas = Array.from(document.getElementById('Idiomas').querySelectorAll("option:checked"),e=>e.value).join('|');
    d.formasTrabajo = getValuesConcatInpCheckbox('FormaTrabajo');

    Object.keys(d).forEach(key=>{
        if(!d[key]){
            delete d[key];
        }
    });

    d.limit = queryParams.limit;
    d.offset = queryParams.offset;

    $.ajax({
        type: 'GET',
        url: _ctx + 'p/trainer/find/dinamico',
        dataType: 'json',
        data: d,
        blockLoading: false,
        noOne: true,
        success: function (res) {
            if(!noCleanPrevious){
                divTrainers.innerHTML = "";
            }
            if(res.length === 0){
                const noOne = document.createElement('div');
                noOne.className = "";
                noOne.style.fontSize = "1.5em";
                noOne.style.color = "#a8fa00";
                noOne.style.padding = "15px 0px";
                noOne.innerHTML = "<i class='fa fa-info-circle fa-fw'></i>No se ha encontrado ninguna coincidencia. Pruebe nuevamente con otra búsqueda";
                divTrainers.appendChild(noOne);
                document.querySelector('.dv-rows').classList.add('hidden');
            }else{
                recrearListado(res);
                totalRows = res[0].rows;
                if(totalRows > 1){
                    document.querySelector('#Rows').textContent = `Se encontraron ${totalRows} coincidencias`;
                } else {
                    document.querySelector('#Rows').textContent = `Se encontró ${totalRows} coincidencia`;

                }
                document.querySelector('.dv-rows').classList.remove('hidden');
            }
        }, error: (xhr) => {
            exception(xhr);
        }, complete: () => {

        }
    })
}

function recrearListado(arr){
    const divTrainers = document.getElementById('Trainers');
    arr.forEach((e)=>{
        const acLen = e.acerca.length;
        if(acLen > 540){
            e.acerca = e.acerca.slice(0, 540).trim();
            if(e.acerca.lastIndexOf(".") === e.acerca.length-1){
                e.acerca = slice.replace(0,539);
            }
            e.acerca += "...";
        };
        const ttId = e.tipoTrainerId;

        const objValoracion = getValoracion(e.canPerValoracion, e.totalValoracion);
        const nomLink = e.nomPag;
        let nombreFinal = "";
        if(ttId == 1){
            nombreFinal = e.nombreCompleto;
        }else{
            nombreFinal = e.nombreCompleto.includes(' xxx') ? e.nombreCompleto.substring(0, e.nombreCompleto.indexOf(" xxx")) : e.nombreCompleto;
        }
        divTrainers.appendChild(htmlStringToElement(`
          <div class="group-trainer row">
              <div class="col-md-3 pad-0">
              <div class="avatar-form">
              <div class="avatar-form-content"><img src="https://s3-us-west-2.amazonaws.com/rf-profile-imgs/trainer/${e.id}/${e.nomImgPerfil}" style="max-height: 100%;height: 100%; !important;"></div>
              <fieldset class="text-center">
                <div class="rating-holder">
                  <div class="c-rating c-rating--regular" data-rating-value="${objValoracion.aproximadaValoracion}">
                    <button disabled="disabled">1</button>
                    <button disabled="disabled">2</button>
                    <button disabled="disabled">3</button>
                    <button disabled="disabled">4</button>
                    <button disabled="disabled">5</button>
                  </div>
                </div>
              </fieldset>
              <p class="valoration">${objValoracion.exactaValoracion.toFixed(1)} <span>(${objValoracion.valoraciones} valoraciones)</span></p>
      </div>
      </div>
      <div class="col-md-9">
              <h4>${nombreFinal}<span class="title">${ttId == 1 ? e.especialidad : ''}</span><span class="location">${e.nomUbigeo ? e.nomUbigeo.split("|").join(' - ') : 'LIMA - LIMA'}</span></h4>
      <p>${e.acerca}</p>
      <button class="btn btn-default btn-black" type="button" onclick="verPerfil('${nomLink}')">VER PERFIL</button>
      <button class="btn btn-default btn-white" type="button" onclick="verPerfilDirectServices('${nomLink}')">VER SERVICIOS</button>
      </div>
      </div>
          `));
    });
}

function spinnerLoadingHtmlRaw(){
    return `<hr>
                <div class="spring-spinner">
                  <div class="spring-spinner-part top">
                    <div class="spring-spinner-rotator"></div>
                  </div>
                  <div class="spring-spinner-part bottom">
                    <div class="spring-spinner-rotator"></div>
                  </div>
                </div>`;
}

function verPerfil(nomLink){
    window.location.href = _ctx + 'p/trainer/'+nomLink;
}

function verPerfilDirectServices(nomLink){
    window.location.href = _ctx + 'p/trainer/'+nomLink+"#service";

}

function scrollEventBusquedaAutomatica(){
    const footer = document.querySelector('.footer');
    const footHeight = footer ? footer.clientHeight + 300 : 100;
    $(window).scroll(function() {
        if($(window).scrollTop() + $(window).height() > $(document).height() - footHeight) {
            if(totalRows> queryParams.offset + queryParams.limit){
                queryParams.offset = queryParams.offset + queryParams.limit;
                const noCleanPrevious = true;
                onlyBusquedaDinamica(noCleanPrevious);
            }
        }
    });
}

function bodyFocusOutEventListener(e){
    const input = e.target;
    if(input.type==="text") {
        input.value = input.value.trim().replace(/ +/g, " ");
    }

    if(input.tagName === "TEXTAREA"){
        input.value = input.value.trim();
    }
}

/* AUTOCOMPLETE */

function autocomplete(inp, arr) {
    /*the autocomplete function takes two arguments,
    the text field element and an array of possible autocompleted values:*/
    var currentFocus;
    /*execute a function when someone writes in the text field:*/
    inp.addEventListener("input", function(e) {
        var a, b, i, val = this.value;
        if(this.value.trim().length<2){
            //Interrumpimos lo demás así como también al listener keydown
            return;
        }

        /*close any already open lists of autocompleted values*/
        closeAllLists();
        if (!val) { return false;}
        currentFocus = -1;
        /*create a DIV element that will contain the items (values):*/
        a = document.createElement("DIV");
        a.setAttribute("id", this.id + "autocomplete-list");
        a.setAttribute("class", "autocomplete-items");
        /*append the DIV element as a child of the autocomplete container:*/
        this.parentNode.appendChild(a);
        /*for each item in the array...*/
        for (i = 0; i < arr.length; i++) {
            /*check if the item starts with the same letters as the text field value:*/
            if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
                /*create a DIV element for each matching element:*/
                b = document.createElement("DIV");
                /*make the matching letters bold:*/
                b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
                b.innerHTML += arr[i].substr(val.length);
                /*insert a input field that will hold the current array item's value:*/
                b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                /*execute a function when someone clicks on the item value (DIV element):*/
                b.addEventListener("click", function(e) {
                    /*insert the value for the autocomplete text field:*/
                    inp.value = this.getElementsByTagName("input")[0].value;
                    /*close the list of autocompleted values,
                    (or any other open lists of autocompleted values:*/
                    closeAllLists();
                });
                a.appendChild(b);
            }
        }
    });
    /*execute a function presses a key on the keyboard:*/
    inp.addEventListener("keydown", function(e) {
        var x = document.getElementById(this.id + "autocomplete-list");
        if (x) x = x.getElementsByTagName("div");
        if (e.keyCode == 40) {
            /*If the arrow DOWN key is pressed,
            increase the currentFocus variable:*/
            currentFocus++;
            /*and and make the current item more visible:*/
            addActive(x);
        } else if (e.keyCode == 38) { //up
            /*If the arrow UP key is pressed,
            decrease the currentFocus variable:*/
            currentFocus--;
            /*and and make the current item more visible:*/
            addActive(x);
        } else if (e.keyCode == 13) {
            /*If the ENTER key is pressed, prevent the form from being submitted,*/
            e.preventDefault();
            if (currentFocus > -1) {
                /*and simulate a click on the "active" item:*/
                if (x) x[currentFocus].click();
            }
        }
    });
    function addActive(x) {
        /*a function to classify an item as "active":*/
        if (!x) return false;
        /*start by removing the "active" class on all items:*/
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        /*add class "autocomplete-active":*/
        x[currentFocus].classList.add("autocomplete-active");
    }
    function removeActive(x) {
        /*a function to remove the "active" class from all autocomplete items:*/
        for (var i = 0; i < x.length; i++) {
            x[i].classList.remove("autocomplete-active");
        }
    }
    function closeAllLists(elmnt) {
        /*close all autocomplete lists in the document,
        except the one passed as an argument:*/
        var x = document.getElementsByClassName("autocomplete-items");
        for (var i = 0; i < x.length; i++) {
            if (elmnt != x[i] && elmnt != inp) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }
    /*execute a function when someone clicks in the document:*/
    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });
}

fetch(_ctx+'p/trainer/get/all/nom-ubigeo')
    .then(res=> {
            if(res.ok){
                return res.text();
            }
        }
    ).then(res=>{
    const arr = Array.from(new Set(res.split("|")));
    autocomplete(document.getElementById("Ubigeo"), arr);
}).catch((err)=>{
    exception(err);
});
