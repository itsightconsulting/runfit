const tabs = document.querySelector('ul.nav-tabs');
const inpImgPerfil = document.getElementById('InpImgPerfil');
const imgPerfil = document.getElementById('ImagePerfil');
const inpGaleria = document.getElementById('InpGaleria');
const inpMultipleFicha = document.getElementById('InpMultipleFicha');
const btnGuardar = document.getElementById('btnGuardar');
const btnNuevoServicio = document.getElementById('btnNuevoServicio');
const btnNuevaTarifa = document.getElementById('btnNuevaTarifa');
const btnNuevaInfoPago = document.getElementById('btnNuevaInfoPago');
const btnEliminarPaquete = document.getElementById('btnEliminarPaquete');
const tabService = document.getElementById('service');
const inpCondServicio = document.getElementById('inpCondServicio');
const frm = document.getElementById('frm_registro');
const inpNomPag = document.getElementById('NomPag');
const servicios = [];
let ccBancarias = [];
const metodosPago = [];
let termConSvc = [];
const initTabActive = 1;
let selServicioId = -1;
let accServicioId = 0;
let accTarifaId = 0;
let accCuentaId = 0;
let terminosAceptados = false;
//Cropper
let cropper = {};
const actions = document.getElementById('actions');
const body = document.querySelector('body');

(function () {
    uploadAndShow(inpImgPerfil, imgPerfil);
    uploadImgs(inpGaleria, 'ImgsGaleria');
    if(flag_form_populate){setTimeout(()=>showInitTab(initTabActive), 1000);}
    btnGuardar.addEventListener('click', sendMainForm);
    btnNuevoServicio.addEventListener('click', agregarServicio);
    btnNuevaTarifa.addEventListener('click', agregarTarifaAServicio);
    btnNuevaInfoPago.addEventListener('click', agregarNuevaInfoPago);
    btnEliminarPaquete.addEventListener('click', eliminarPaqueteDeServicio);
    tabService.addEventListener('click', clickListenerTabService);
    tabService.addEventListener('change', changeListenerTabService);
    tabs.addEventListener('click', clickListenerTabs);
    inpImgPerfil.addEventListener('click', clickImgPerfiL);
    inpImgPerfil.addEventListener('change', changeImgPerfil);
    body.addEventListener('click', bodyClickEventListener);
    body.addEventListener('focusout', bodyFocusOutEventListener);
    document.querySelectorAll('a[rel="tooltip"]').forEach(e=>{$(e).tooltip();})
    inpNomPag.addEventListener('keyup', (e)=>{
        document.getElementById('NomPagFull').textContent =
            window.location.protocol+"//"+ window.location.host +"/p/trainer/" +e.target.value.trim();
    });
    inpNomPag.addEventListener('focusout', checkSiNomPagExiste);
    init();
})();

function sendMainForm(e){
    e.preventDefault();//To avoid form sended automatically
    const checkList1 = validationByNumSheet(1);
    const checkList2 = validationByNumSheet(2);
    const checkList3 = validationByNumSheet(3);
    if(checkList1.isValid && checkList2.isValid && checkList3.isValid){
        sendForm();
    }else {
        smallBoxAlertValidation(checkList1.inputs.concat(checkList2.inputs).concat(checkList3.inputs));
    }
}

function agregarServicio(){
    const s = getServicio();
    if(s.valid){
        const isNomRepeat = servicios.find(e=>e.nombre===s.nombre);
        if(isNomRepeat){
            $.smallBox({color: "rgb(204, 77, 77)", content: "<i class='fa fa-fw fa-close'></i><em>Nombre del servicio esta repetido, modificarlo</em>" ,timeout: 3500})
            return;
        }
        selServicioId = s.id;
        servicios.push(s);
        addServiceAndcleanCampos(s);
        $.smallBox({color: "black", content: "<i class='fa fa-fw fa-check' style='color: #a8fa00'></i><em style='color: #a8fa00'>Agregado satisfactoriamente</em>" ,timeout: 2000})
        const editButtons = service.querySelectorAll('.edit');
        editButtons.forEach(e=>{
            e.classList.add('hidden');
        });
        service.querySelector(`.ver-servicios .edit[data-id="${s.id}"]`).classList.remove('hidden');
        document.getElementById('Tarifarios').innerHTML = "";
        tabService.querySelector('.servicio.svc-focus').click();
        agregarInputTermCond();
    } else{
        $.smallBox({color: "rgb(204, 77, 77)", content: "<i class='fa fa-fw fa-close'></i><em>Deben completar los campos requeridos</em>" ,timeout: 2500})
    }
}

function agregarTarifaAServicio(){
    const t = getTarifa();
    if(t.valid){
        const s = servicios.find(v=>v.id === selServicioId);
        if(s !== undefined){
            const tarifarios = s.tarifarios;
            const isNomRepeat = tarifarios.find(e=>e.nombre === t.nombre);
            if(isNomRepeat){
                $.smallBox({color: "rgb(204, 77, 77)", content: "<i class='fa fa-fw fa-close'></i><em>Nombre del tarifario esta repetido, modificarlo</em>" ,timeout: 3500});
                return;
            }
            cleanPaqueteCampos();
            s.tarifarios.push(t);
            $.smallBox({color: "black", content: "<i class='fa fa-fw fa-check' style='color: #a8fa00'></i><em style='color: #a8fa00'>Agregado satisfactoriamente</em>" ,timeout: 2000})
            document.getElementById('Tarifarios')
                .appendChild(
                    htmlStringToElement(`${putTarifario(t.id, t.nombre)}`));
        }else
            $.smallBox({color: "rgb(204, 77, 77)", content: "<i class='fa fa-fw fa-close'></i><em>Usted debe primero agregar un servicio ya que los tarifarios deben ir asociados a un servicio</em>" ,timeout: 7500});
    }else{
        $.smallBox({color: "rgb(204, 77, 77)", content: "<i class='fa fa-fw fa-close'></i><em>Los valores del tarifario ingresado son inválidos</em>" ,timeout: 5000});
    }
}

function agregarNuevaInfoPago(){
    const checkOpcion = document.querySelector('input[name="MetodoPago"]:checked');
    if(checkOpcion != null){
        const v = Number(checkOpcion.value);
        const obj = {};
        if(v === 1){
            obj.infoPagoId = 1;
            obj.detalle = "Efectivo";
            checkExistsInfoPago(obj);
            document.getElementById('MetodoEfectivo').classList.add('hidden');
        }
        if(v === 2){
            const c = getCuentaBancaria();
            if(c.valid){
                ccBancarias.push(c);
                const mePagos = document.getElementById('MediosPagosAgregados');
                cleanCuentaBanCampos();
                $.smallBox({color: "black", content: "<i class='fa fa-fw fa-check' style='color: #a8fa00'></i><em style='color: #a8fa00'>Agregado satisfactoriamente</em>" ,timeout: 3000});
                mePagos.appendChild(htmlStringToElement(`<a href="javascript:void(0);" data-id="${c.id}" style="padding-right: 30px"><img class="cc-bancaria" data-id="${c.id}" src="${_ctx}img/bill.png" style="width: 64px;"></a>`));
            }else{
                $.smallBox({color: "rgb(204, 77, 77)", content: "<i class='fa fa-fw fa-close'></i><em>Deben completar los campos requeridos</em>" ,timeout: 3500})
            }
        }
        if(v === 3){
            const ccs = Array.from(document.querySelectorAll('.cc-elegir:not(.img-cc)')).map(e=>e.getAttribute('data-id')).join('|');
            if(ccs.length>0){
                obj.infoPagoId = 3;
                obj.detalle = ccs;
                checkExistsInfoPago(obj);
                $.smallBox({color: "black", content: "<i class='fa fa-fw fa-check' style='color: #a8fa00'></i><em style='color: #a8fa00'>Agregado satisfactoriamente</em>" ,timeout: 3000});
            }else{
                $.smallBox({color: 'alert',content: '<i>Aún no ha seleccionado ninguna tarjeta</i>'});
            }
        }
    }else{
        $.smallBox({color: 'alert',content: '<i>Primero debe seleccionar un tipo de medio de pago</i>'});
    }
    showInfoPagosSelected();
}

function eliminarPaqueteDeServicio(){
    const t = tabService.querySelector('#Tarifarios .tarifa-svc-pick');
    if(t){
        const tId = Number(t.getAttribute('data-id'));
        const s = servicios.find(s=>s.id===selServicioId);
        s.tarifarios = s.tarifarios.filter(t=>t.id!==tId);
        cleanPaqueteCampos();
        t.remove();
    }else{
        $.smallBox({
            color: 'alert',
            content: '<i>No ha seleccionado ningún tarifario a eliminar</i>'});
    }
}



function clickListenerTabService(e) {
    const input = e.target;
    const clases = input.classList;
    if (clases.contains('servicio')) {
        const id = input.getAttribute('data-id');
        selServicioId = Number(id);
        const svcFocus = tabService.querySelector('.svc-focus');
        svcFocus != undefined ? svcFocus.classList.remove('svc-focus') : "";
        clases.add('svc-focus');
        mostrarDetalleServicio(selServicioId);
        const notHidden = tabService.querySelector('.ver-servicios .edit:not(.hidden)');
        if(notHidden){notHidden.classList.add('hidden')}
        tabService.querySelector(`.ver-servicios .edit[data-id="${selServicioId}"]`).classList.remove('hidden');
        agregarInputTermCond();
    } else if(clases.contains('tarifa-svc')) {
        let padre = {};
        if(input.tagName === "IMG" || input.tagName === "H6"){
            padre = input.parentElement.parentElement;
        } else if(input.tagName === "A"){
            padre = input.parentElement;
        }
        const tarifaId = padre.getAttribute('data-id');
        const svcFocus = tabService.querySelector('.tarifa-svc-pick');
        svcFocus != undefined ? svcFocus.classList.remove('tarifa-svc-pick') : "";
        padre.classList.add('tarifa-svc-pick');
        const abuelo = padre.parentElement;
        abuelo.querySelectorAll('.edit').forEach(e=>e.classList.add('hidden'));
        padre.querySelector('.edit').classList.remove('hidden');
        mostrarDetalleTarifaSvc(Number(tarifaId));
    } else if(clases.contains('edit-svc')){
        const svcId = Number(input.getAttribute('data-id'));
        editarServicio(svcId);
    } else if(clases.contains('del-svc')){
        const svcId = Number(input.getAttribute('data-id'));
        eliminarServicio(svcId);
    } else if(clases.contains('edit-tar-svc')){
        const tId = Number(input.getAttribute('data-id'));
        editarTarifa(tId);
    } else if(clases.contains('fa-plus-cs')){
        clases.toggle('fa-chevron-up');
        clases.toggle('fa-chevron-down');
    } else if(clases.contains('info-pago')){
        const parent = input.parentElement;
        const qInput = parent.querySelector('input');
        let nomButton = "Agregar información pago";
        if(qInput.value === "1"){
            tabService.querySelectorAll('.info-cuenta').forEach(e => e.classList.add('hidden'));
            tabService.querySelector('.info-tarjetas').classList.add('hidden');
        }
        if(qInput.value === "2"){
            tabService.querySelector('.info-tarjetas').classList.add('hidden');
            tabService.querySelectorAll('.info-cuenta').forEach(e => e.classList.remove('hidden'));
            nomButton = "AGREGAR NUEVA CUENTA BANCARIA";
        }

        if(qInput.value === "3"){
            const exists = metodosPago.some(e=>e.infoPagoId ===3);
            if(exists){
                nomButton = "MODIFICAR CON NUEVA ELECCIÓN";
            }
            tabService.querySelectorAll('.info-cuenta').forEach(e => e.classList.add('hidden'));
            tabService.querySelector('.info-tarjetas').classList.remove('hidden');
        }
        btnNuevaInfoPago.innerText = nomButton;
    } else if(clases.contains('cc-elegir')){
        input.classList.toggle('img-cc');
    } else if(clases.contains('cc-bancaria')){
        const inputBancoId = document.getElementById('ModalCuentaBancoId');
        const ccId = Number(input.getAttribute('data-id'));
        inputBancoId.value = ccId;
        const d = ccBancarias.find(e => e.id === ccId);
        $('#myModalCC').modal('show');
        const dC = document.getElementById('CuentaBancariaUnica');
        dC.querySelector('.cc-nom-banco').textContent = banks.find(b=>b.id==d.bancoId).nombre;
        dC.querySelector('.cc-titular').textContent = d.titular;
        dC.querySelector('.cc-cs').textContent = d.numeroSoles;
        dC.querySelector('.cc-cis').textContent = d.interbancarioSoles;
    } else if(clases.contains('div-fichas')){
        const btn = document.getElementById('btnElegirFicha');
        const fichaName = input.previousElementSibling.textContent.trim();
        btn.innerText = "Ficha seleccionada: "+fichaName;
        btn.click();
        btn.setAttribute("data-ficha-id", fichaName === "Running" ? "1" : "2");
        inpMultipleFicha.checked = false;
    }
}



function changeListenerTabService(e){
    const input = e.target;
    const clases = input.classList;
    if(clases.contains('inp-svc-tyc')){
        selServicioId = selServicioId === -1 ? 1 : selServicioId;
        if(selServicioId > 0){
            const input = e.target;
            const obj = {};
            obj.servicioId = selServicioId;
            obj.file = input.files[0];
            const exist = termConSvc.find(v=>v.servicioId === obj.servicioId);
            if(exist !== undefined){
                exist.file = obj.file;
            }else{
                termConSvc.push(obj);
            }
            const btnSubirCondServicio = document.getElementById('btnSubirCondServicio');
            let nomFile = obj.file.name;
            if(nomFile.trim().length>19){
                nomFile = nomFile.substring(0, 16).trim()+"...";
            }
            btnSubirCondServicio.innerHTML = "<i class=\"fa fa-file-pdf-o fa-fw\" style='color: rgb(204, 77, 77);'></i>"+nomFile+"<span class=\"obligatorio\">*</span>";
        } else {
            $.smallBox({color: "rgb(204, 77, 77)",
                content: "<i class='fa fa-fw fa-exclamation-circle'></i><em>Ha ocurrido un error al cargar su archivo. Por favor omita este paso pora ahora</em>" ,
                timeout: 3500})
        }
    }
}

function clickListenerTabs(e){
    const input = e.target;
    if (input.tagName==="SPAN" && input.textContent.trim().toLowerCase()==="staff"){
        btnGuardar.classList.add('hidden');
    } else {
        btnGuardar.classList.remove('hidden');
    }
}

function clickImgPerfiL(e){
    if(typeof cropper.canvas === 'object'){
        e.preventDefault();
        $('#myModalCropper').modal('show');
    }
}

function changeImgPerfil(){
    $('#myModalCropper').modal('show');
}

function bodyClickEventListener(e){
    const input = e.target;
    const clases = input.classList;
    checkBoxAndRadioValidationEventListener(e, input, clases);
}

function checkSiNomPagExiste(e){
    const input = e.target;
    setTimeout(() => {
        if($(input).valid()){
            $.ajax({
                type: 'GET',
                url: _ctx+'p/trainer/validacion/nom-pag?nomPag='+input.value,
                blockLoading: false,
                noOne: true,
                dataType: 'json',
                success: function (res) {
                    if(res){
                        input.classList.remove('state-success');
                        input.classList.remove('valid');
                        input.classList.add('state-error');
                        $.smallBox({color: 'alert', content: '<i>El nombre de página elegido ya esta en uso</i>'});
                    }
                },
                error: (xhr)=>{
                    exception(xhr);
                },
                complete: ()=> {}
            });
        }
    }, 100);
}

function clickMultipleFicha(e){
    const input = e.target;
    if(input.checked){
        btnElegirFicha.textContent = "FICHAS SELECCIONADAS: GENERAL Y RUNNING";
    }else{
        btnElegirFicha.innerHTML = "SELECCIONAR FICHA DE INSCRIPCIÓN<span class=\"obligatorio\">*</span>";
    }
    btnElegirFicha.click();
}

function uploadFotoPerfil(d){
    //submit the form here
    const hshId = d.res;
    const rdmUUID = d.rdm;
    let file = inpImgPerfil;
    if (file.files.length > 0) {
        file = file.files[0];
        const data = new FormData();

        const blobBin = atob($('#FinalImagenRecortada')[0].src.split(',')[1]);
        var array = [];
        for(var i = 0; i < blobBin.length; i++) {
            array.push(blobBin.charCodeAt(i));
        }
        var ff=new Blob([new Uint8Array(array)], {type: file.type});
        data.append("file", ff);
        data.append("fileExtension", ".jpg");

        $.ajax({
            type: 'PUT',
            url: _ctx+'p/trainer/subir/foto/perfil/'+hshId+'/'+rdmUUID,
            data : data,
            contentType: false,
            processData: false,
            dataType: 'json',
            xhr: function() {
                const myXhr = $.ajaxSettings.xhr();
                if(myXhr.upload){
                    myXhr.upload.addEventListener('progress', progress, false);
                }
                return myXhr;
            },
            success: function (data) {
                reqSuccess(data, 3600000);
            },
            error: (xhr)=>{
                exception(xhr);
            },
            complete: ()=> {}
        });
    }
}



function init(){
    modalEventos();
    populateBancos();
    activeTooltips();
    constraintsValidation();
    instanceCropper();
    doMultiselectCheckBox();
    if(flag_form_populate){populateForm();}
}

function instanceCropper(){
    actions.querySelector('.docs-buttons').onclick = function (e) {
        let target = e.target;
        let result;
        let data;

        if (!cropper) {
            return;
        }

        while (target !== this) {
            if (target.getAttribute('data-method')) {
                break;
            }

            target = target.parentNode;
        }

        if (target === this || target.disabled || target.className.indexOf('disabled') > -1) {
            return;
        }

        data = {
            method: target.getAttribute('data-method'),
            target: target.getAttribute('data-target'),
            option: target.getAttribute('data-option') || undefined,
            secondOption: target.getAttribute('data-second-option') || undefined
        };

        if (data.method) {
            switch (data.method) {
                case 'getCroppedCanvas':
                    try {
                        data.option = JSON.parse(data.option);
                    } catch (e) {
                        console.log(e.message);
                    }
                    break;
            }


            result = cropper[data.method](data.option, data.secondOption);

            switch (data.method) {
                case 'getCroppedCanvas':
                    if (result) {
                        $('#FinalImagenRecortada').attr('src', result.toDataURL(inpImgPerfil.files[0].type));
                    }
                    break;
            }
        }
    }
}

function modalEventos(){
    $('#myModalTermCond').on('hidden.bs.modal', ()=> {
        if(!terminosAceptados) {
            document.getElementById('inpTermCond').checked = false;
        }
    })

    $('#myModalCropper').on('shown.bs.modal', ()=>{
        if(typeof cropper.canvas !== 'object'){
            const image = document.getElementById('ImagePerfil');
            cropper = new Cropper(image, {
                aspectRatio: 1,
                crop(event) {},
                zoomOnWheel: false,
                viewMode: 1,
                minContainerHeight: image.height > window.innerHeight * 0.8 ? window.innerHeight * 0.8 : image.height
            });
        }
    })
}

function populateBancos(){
    document.getElementById('BancoId').innerHTML = banks.map(e => `<option value="${e.id}">${e.nombre}</option>`).join('');
}

function getAsStringIncluyeServices(){
    return Array.from(document.querySelectorAll('#MainIncluyeServicios textarea')).map(v=>v.value  !==  "" ? v.value : "").filter(v=>v.trim()!=="").join('|');
}

function showInitTab(numTab){
    next_step_cs_rt(Number(numTab));
}

function showInfoPagosSelected(){
    tabService.querySelectorAll('.cc-elegido').forEach(e=>e.classList.add('hidden'));
    if(metodosPago.find(e=>e.infoPagoId===1)){
        tabService.querySelector('.cc-elegido[data-id="0"]').classList.remove('hidden');
    }

    if(metodosPago.find(e=>e.infoPagoId===3)){
        const ccs = metodosPago.find(e => e.infoPagoId === 3).detalle.split("|");
        ccs.forEach(id=>{
            tabService.querySelector('.cc-elegido[data-id="'+id+'"]').classList.remove('hidden');
        })
    }
}

function checkExistsInfoPago(o){
    const exists = metodosPago.some(e=>e.infoPagoId===o.infoPagoId);
    if(exists){
        metodosPago.find(e=>e.infoPagoId===o.infoPagoId).detalle = o.detalle;
    }else{
        metodosPago.push(o);
    }
}

function cleanPaqueteCampos(){
    document.querySelector('#NombreTarifario').value = "";
    document.querySelector('#FrecuenciaPaquete').selectedIndex = 0;
    document.querySelector('#txtCantidadPersonas').value = 0;
    document.querySelector('#txtCantidadMeses').value = 0;
    document.querySelector('#txtCantidadSesiones').value = 0;
    document.querySelector('#PrecioPaquete').value = '0.00';
}

function cleanCuentaBanCampos(){
    document.querySelector('#TitularCuenta').value = "";
    document.querySelector('#NumeroCuentaSoles').value = "";
    document.querySelector('#InterbancarioSoles').value = "";
    document.querySelector('#BancoId').selectedIndex = 0;
}

function abrirModalTermCond(){
    if(!document.getElementById('inpTermCond').checked){
        document.getElementById('inpTermCond').checked=false;
    }
    $('#myModalTermCond').modal('show');
}

function ocultarModalTermCondYAceptar(){
    document.getElementById('inpTermCond').checked=true;
    terminosAceptados = true;
    $('#myModalTermCond').modal('hide');
}



function addServiceAndcleanCampos(svc){
    const svcFocus = tabService.querySelector('.svc-focus');
    svcFocus != undefined ? svcFocus.classList.remove('svc-focus') : "";

    document.querySelector('#ServicioInfAdic').value = "";
    const element = htmlStringToElement(
        `<div class="form-group editar">
                            <a class="edit hidden" data-id="${svc.id}" href="javascript:void(0);" title="Confirmar modificaciones al servicio">
                                <img class="del-svc" data-id="${svc.id}" src="${_ctx}img/public/garbage.png">
                                <img class="edit-svc" data-id="${svc.id}" src="${_ctx}img/public/edit.png">
                            </a>
                            <label class="servicio svc-focus" data-id="${svc.id}">${svc.nombre}</label>
                        </div>`);
    const resumenServicios = document.querySelector('.ver-servicios');
    resumenServicios.appendChild(element);


    const divCamposBasicos = document.querySelector('#SvcCamposBasicos');
    const htmlBasics = `<div class="form-group">
                                              <label>NOMBRE DEL SERVICIO<span class="obligatorio">*</span></label>
                                              <input class="form-control" id="NombreServicio" name="NombreServicio" maxlength="50">
                                          </div>
                                          <div class="form-group">
                                              <label>DESCRIPCIÓN<span class="obligatorio">*</span></label>
                                              <textarea class="form-control" id="DescripcionServicio" name="DescripcionServicio" maxlength="2000"></textarea>
                                          </div>
                                          <div class="form-group list-counter" id="MainIncluyeServicios">
                                              <label>QUE INCLUYE<span class="obligatorio">*</span></label>
                                              <li><textarea class="form-control mg-bt-10 inp-svc-incluye" id="PrimerIncluyeServicio" name="PrimerIncluyeServicio" placeholder="Ejem: Tendrás cuatro master-class iniciales para mejorar tu técnica de carrera en sesiones grupales." maxlength="500"></textarea></li>
                                              <a href="javascript:void(0);" class="add" onclick="javascript:agregarTextareaDinamico(this, 10, 'inp-svc-incluye', 500)">&nbsp;<i title="Agregar" class="fa fa-15x fa-plus pull-right"></i></a>
                                          </div>
                                          <div class="form-group">
                                              <label>INFORMACIÓN ADICIONAL</label>
                                              <textarea class="form-control" id="ServicioInfAdic" name="ServicioInfAdic" style="height: 56px !important;" maxlength="1000"></textarea>
                                          </div>
                                          `;
    divCamposBasicos.innerHTML = htmlBasics;
}

function getCuentaBancaria(){
    const c = new Object();
    c.titular = document.querySelector('#TitularCuenta').value.trim();
    c.numeroSoles = document.querySelector('#NumeroCuentaSoles').value.trim();
    c.interbancarioSoles = document.querySelector('#InterbancarioSoles').value.trim();
    c.bancoId = document.querySelector('#BancoId').value.trim();
    c.valid = false;
    if(c.titular !== "" && (c.numeroSoles !== "") && (c.interbancarioSoles !== "") && c.bancoId !== ""){
        c.valid = true;
        c.id = ++accCuentaId;
    }
    return c;
}

function getTarifa(){
    const tar = new Object();
    tar.nombre = document.querySelector('#NombreTarifario').value.trim();
    tar.frecuencia = document.querySelector('#FrecuenciaPaquete').value.trim();
    tar.personas = document.querySelector('#txtCantidadPersonas').value.trim();
    tar.meses = document.querySelector('#txtCantidadMeses').value.trim();
    tar.sesiones = document.querySelector('#txtCantidadSesiones').value.trim();
    tar.valid = false;

    const jQvalidate = Array.from(document.querySelectorAll('#contentTarifario input')).filter(e=>!$(e).valid()).length == 0 ? true : false;
    if(jQvalidate && tar.nombre !== "" && tar.frecuencia !== "" && Number(tar.personas)>0 && (Number(tar.meses)>0 || Number(tar.sesiones)>0)){
        tar.precio = document.querySelector('#PrecioPaquete').value;
        tar.valid = true;
        tar.id = ++accTarifaId;
    }
    if(tar.nombre === ""){
        document.querySelector('#NombreTarifario').classList.remove('state-success');
        document.querySelector('#NombreTarifario').classList.add('state-error');
    }
    return tar;
}

function getRedesSociales(){
    let res = {};
    res.nombres = "";
    res.have = false;
    const fb = document.querySelector('#RsFb').value.trim();
    const instagran = document.querySelector('#RsIn').value.trim();
    const youtube = document.querySelector('#RsYo').value.trim();

    if(fb.length > 4){
        res.have = true;
        res.nombres+= "fb:"+fb.trim();
    }

    if(instagran.length > 4){
        res.nombres+= res.have ? "|":"";
        res.have = true;
        res.nombres+= "in:"+instagran.trim();
    }

    if(youtube.length > 4){
        res.nombres+= res.have ? "|":"";
        res.have = true;
        res.nombres+= "yo:"+youtube.trim();
    }
    return res;
}

function elegirOtraImagenCropper(){
    cropper.destroy();
    cropper = {};
    inpImgPerfil.click();
}

function mostrarDetalleServicio(servicioId){
    const svc = servicios.find(e=>e.id===servicioId);
    document.querySelector('#NombreServicio').value = svc.nombre;
    document.querySelector('#DescripcionServicio').value = svc.descripcion;
    document.querySelector('#ServicioInfAdic').value = svc.infoAdicional;
    setIncluyeDelServicio(svc.incluye);
    setFileTermCond();
    setTarifarios(svc.tarifarios);
}

function setFileTermCond(){
    const btnSubirCondServicio = document.getElementById('btnSubirCondServicio');
    const condSvcFile = termConSvc.find(v=>v.servicioId === selServicioId);
    if(condSvcFile !== undefined){
        let nomFile = condSvcFile.file.name;
        if(nomFile.trim().length>19){
            nomFile = nomFile.substring(0, 16).trim()+"...";
        }
        btnSubirCondServicio.innerHTML = "<i class=\"fa fa-file-pdf-o fa-fw\" style='color: rgb(204, 77, 77);'></i>"+nomFile+"<span class=\"obligatorio\">*</span>";
    }else{
        btnSubirCondServicio.innerHTML = '<i class="fa fa-cloud-upload fa-fw"></i> ADJUNTAR T&C';
    }
    $(btnSubirCondServicio).tooltip();
}

function agregarInputTermCond(){
    const inpFile = document.createElement('input');
    inpFile.type = 'file';
    inpFile.id = "inpCondServicio"+selServicioId;
    inpFile.className = "inp-svc-tyc hidden";
    inpFile.accept = "application/pdf";
    inpCondServicio.insertAdjacentElement('afterend', inpFile);
}

function editarServicio(svcId){
    const svc = servicios.find(s=>s.id===svcId);
    if(svc !== undefined){
        setBasicsServicio(svc);
    }else {
        $.smallBox({color: 'alert', content: '<i class="fa fa-exclamation-circle fa-fw"></i><i>El servicio seleccionado no se ha guardado correctamente</i>'});
    }
}

function editarTarifa(tId){
    const t = getTarifa();
    if(t.valid) {
        const svcAsociado = servicios.find(s=>s.id===selServicioId);
        const tarifarios = svcAsociado.tarifarios;
        const isNomRepeat = tarifarios.filter(e=>e.id!==tId).find(e=>e.nombre === t.nombre);
        if(isNomRepeat){
            $.smallBox({color: "rgb(204, 77, 77)", content: "<i class='fa fa-fw fa-close'></i><em>Nombre del tarifario esta repetido, modificarlo</em>" ,timeout: 3500})
            return;
        }

        let tarifa = tarifarios.find(tar=>tar.id===tId);

        tarifa.nombre = t.nombre;
        tarifa.frecuencia = t.frecuencia;
        tarifa.meses = t.meses;
        tarifa.personas = t.personas;
        tarifa.sesiones = t.sesiones;
        tarifa.precio = t.precio;
        tabService.querySelector('.tarifa-svc-pick h6').textContent = t.nombre.trim();
        $.smallBox({color: "#111509",content: '<i class="fa fa-check"></i> <i>Se modificó satisfactoriamente</i>'});
    } else{
        $.smallBox({color: "rgb(204, 77, 77)", content: "<i class='fa fa-fw fa-close'></i><em>Los valores del tarifario ingresado son inválidos</em>" ,timeout: 5000})
    }
}

function getServicio(){
    const svc = new Object();
    svc.nombre = document.querySelector('#NombreServicio').value.trim();
    svc.descripcion = document.querySelector('#DescripcionServicio').value.trim();
    svc.incluye = getAsStringIncluyeServices();
    svc.valid = false;

    const jQvalidate = Array.from(document.querySelector('#SvcCamposBasicos').querySelectorAll('.form-control')).filter(e=>!$(e).valid()).length == 0 ? true : false;

    if(svc.nombre !== "" && svc.descripcion !== "" && svc.incluye !== "" && jQvalidate){
        svc.valid = true;
        svc.tarifarios = new Array();
        svc.infoAdicional = document.querySelector('#ServicioInfAdic').value.trim();
        svc.id = ++accServicioId;
    }
    if(!svc.nombre){
        document.querySelector('#NombreServicio').classList.remove('state-sucess');
        document.querySelector('#NombreServicio').classList.add('state-error');
    }
    if(!svc.descripcion){
        document.querySelector('#DescripcionServicio').classList.remove('state-sucess');
        document.querySelector('#DescripcionServicio').classList.add('state-error');
    }
    if(!document.querySelector('#PrimerIncluyeServicio').value.trim()){
        document.querySelector('#PrimerIncluyeServicio').classList.remove('state-sucess');
        document.querySelector('#PrimerIncluyeServicio').classList.add('state-error');
    }
    return svc;
}

function setBasicsServicio(svc){
    const nombre = document.querySelector('#NombreServicio').value.trim();
    const descripcion = document.querySelector('#DescripcionServicio').value.trim();
    const incluye = getAsStringIncluyeServices();
    const infoAdicional = document.querySelector('#ServicioInfAdic').value.trim();
    const jQvalidate = Array.from(document.querySelector('#SvcCamposBasicos').querySelectorAll('.form-control')).filter(e=>!$(e).valid()).length == 0 ? true : false;
    if(nombre !== "" && descripcion !== "" && incluye !== "" && jQvalidate){
        const isNomRepeat = servicios.filter(e=>e.id!==svc.id).find(e=>e.nombre === nombre);
        if(isNomRepeat){
            $.smallBox({color: "rgb(204, 77, 77)", content: "<i class='fa fa-fw fa-close'></i><em>Nombre del servicio esta repetido, modificarlo</em>" ,timeout: 3500})
            return;
        }
        svc.nombre = nombre;
        svc.descripcion = descripcion;
        svc.incluye = incluye;
        svc.infoAdicional = infoAdicional;
        service.querySelector('.servicio.svc-focus').textContent = svc.nombre;
        $.smallBox({color: "#111509",content: '<i class="fa fa-check"></i> <i>Se modificó satisfactoriamente</i>'});
    }else{
        $.smallBox({
            color: 'alert',
            content: '<i class="fa fa-exclamation-circle fa-fw"></i><i>Los nuevos datos que editó del servicio son inválidos, verifíquelos y corríjalos</i>'
        });
        if(!svc.nombre){
            document.querySelector('#NombreServicio').classList.remove('state-sucess');
            document.querySelector('#NombreServicio').classList.add('state-error');
        }
        if(!svc.descripcion){
            document.querySelector('#DescripcionServicio').classList.remove('state-sucess');
            document.querySelector('#DescripcionServicio').classList.add('state-error');
        }
        if(!document.querySelector('#PrimerIncluyeServicio').value.trim()){
            document.querySelector('#PrimerIncluyeServicio').classList.remove('state-sucess');
            document.querySelector('#PrimerIncluyeServicio').classList.add('state-error');
        }
    }
}

function eliminarServicio(svcId){
    const attrOnClick = `onclick='confirmarEliminarServicicio(${svcId})'`;
    $.smallBox({
        color: "rgb(204, 77, 77)",
        content: "<i class='fa fa-fw fa-exclamation-circle'></i><em>¿Estás seguro de eliminar este servicio?</em><br><br>"+
            "<div class='text-center'><button type='button' "+attrOnClick+" class='btn btn-danger' style='margin: 10px'>SI</button><button type='button' class='btn btn-primary' style='margin: 10px'>NO</button></div>" ,
        timeout: 12000});
}

function confirmarEliminarServicicio(svcId){
    let svcIx = 0;
    servicios.forEach((s,ix)=>{if(s.id===Number(svcId)){svcIx = ix; return;}});
    servicios.splice(svcIx, 1);
    service.querySelector('.svc-focus').parentElement.remove();
    if(servicios.length){
        service.querySelector('.ver-servicios .servicio').click();
    }else{
        resetServicios();
    }
    setTimeout(()=>{$.smallBox({content: "<i class='fa fa-fw fa-check-circle'></i>Se ha eliminado satisfactoriamente"});},200)
}

function eliminarCuentaBanco(){
    $.smallBox({
        color: "rgb(204, 77, 77)",
        content: "<i class='fa fa-fw fa-exclamation-circle'></i><em>¿Estás seguro de eliminar esta cuenta bancaria?</em><br><br>" +
            "<div class='text-center'><button type='button' onclick='confirmarEliminarCB();' class='btn btn-danger' style='margin: 10px'>SI</button><button type='button' class='btn btn-primary' style='margin: 10px'>NO</button></div>" ,
        timeout: 12000});
}

function confirmarEliminarCB(){
    const cbId = Number(document.getElementById('ModalCuentaBancoId').value);
    ccBancarias = ccBancarias.filter(c => c.id !== cbId);
    tabService.querySelector(`.cc-bancaria[data-id="${cbId}"]`).parentElement.remove();
    $('#myModalCC').modal('hide');
}

function mostrarDetalleTarifaSvc(tarifaId){
    const t = servicios.find(v => v.id === selServicioId).tarifarios.find(t=>t.id === tarifaId);
    document.querySelector('#NombreTarifario').value = t.nombre;
    const slcFre = document.querySelector('#FrecuenciaPaquete');
    Array.from(slcFre.options).forEach(v=>{
        if(v.text === t.frecuencia){
            document.querySelector('#FrecuenciaPaquete').selectedIndex = v.index;
        }
    });
    document.querySelector('#txtCantidadPersonas').value = t.personas;
    document.querySelector('#txtCantidadMeses').value = t.meses;
    document.querySelector('#txtCantidadSesiones').value = t.sesiones;
    document.querySelector('#PrecioPaquete').value = t.precio;
    $('#FrecuenciaPaquete').multiselect('refresh');
}

function setIncluyeDelServicio(incluidos){
    const arrIncluidos = incluidos.split("|");
    const mainIncluidos = document.querySelector('#MainIncluyeServicios');
    if(arrIncluidos < 2){
        mainIncluidos.querySelector('input').value = arrIncluidos[0];
    } else {
        mainIncluidos.innerHTML =
            `<label>QUE INCLUYE<span class="obligatorio">*</span></label>
                     <li><textarea class="form-control mg-bt-10 inp-svc-incluye" id="PrimerIncluyeServicio" name="PrimerIncluyeServicio" maxlength="500" placeholder="Ejem: Tendrás cuatro master-class iniciales para mejorar tu técnica de carrera en sesiones grupales."></textarea></li>
                     <a href="javascript:void(0);" class="add" onclick="javascript:agregarTextareaDinamico(this, 10, 'inp-svc-incluye', 500)">&nbsp;<i title="Agregar" class="fa fa-15x fa-plus pull-right"></i></a>`;
        const firstInput = mainIncluidos.querySelector('textarea');
        const btnPlus = mainIncluidos.querySelector('a');

        firstInput.value = arrIncluidos[0];
        arrIncluidos.slice(1).forEach((e,i)=>{
            const ele = agregarTextareaDinamico(btnPlus, 10, 'inp-svc-incluye', 500).firstElementChild;
            ele.name = "inp-svc-incluye"+i;
            ele.value = e;
            $(ele).rules("add",{rangelength: [8, 500]})
        })
    }
}

function setTarifarios(tarifarios){
    const dt = document.getElementById('Tarifarios');
    dt.innerHTML = "";
    tarifarios.forEach(t=>{
        dt.appendChild(
            htmlStringToElement(`${putTarifario(t.id, t.nombre)}`));
    })
}

function putTarifario(id, nombre){
    setTimeout(()=>{
        $(tabService.querySelector(`#Tarifarios a.edit[data-id="${id}"]`)).tooltip();
    }, 500);
    return `<div class="col-md-3 col-xs-4 mg-bt-10" data-id="${id}">
                        <a href="javascript:void(0)" class="tarifa-svc">
                            <img class="tarifa-svc" src="${_ctx}img/purchase.png" style="width:64px"/>
                            <h6 class="tarifa-svc">${nombre}</h6>
                        </a>
                        <a data-placement="bottom" rel="tooltip" class="edit hidden" data-id="${id}" href="javascript:void(0);" title="Confirmar modificaciones al tarifario seleccionado">
                            <img class="del-svc" data-id="${id}" src="${_ctx}img/public/garbage.png">
                            <img style="margin: 0px 0px 5px" class="edit-tar-svc" data-id="${id}" src="${_ctx}img/public/edit.png">
                        </a>
                    </div>`
}

function irAformParaNuevoTrainerDeEmp(){
    const hshEmpTrainerId = document.getElementById('HshEmpTrainerId').value;
    const arrUrl = window.location.href.split("/");
    const hshPostTrainerId = arrUrl[arrUrl.length - 1];
    if(hshEmpTrainerId){
        window.location.href = _ctx+'p/trainer/empresa/agregar/sub/'+hshEmpTrainerId+'/'+hshPostTrainerId;
    }else{
        $.smallBox({color: 'alert', content: '<span><i class="fa fa-exclamation-circle fa-fw"></i> Debe primero registrarse para poder agregar un asesor</span>'});
    }
}



function uploadFotosPerfil(d){
    //submit the form here
    const hshId = d.res;
    const rdmsUUIDs = getImgUuids(d.rdm);
    let file = inpImgPerfil;
    if (file.files.length > 0) {
        file = file.files[0];
        const data = new FormData();
        data.append("files", file);
        data.append("fileExtension", ".jpg");
        Array.from(inpGaleria.files).forEach(f=>{
            data.append("files", f);
        });
        termConSvc.forEach(v=>{
            if(v.file){
                data.append("files", v.file);
            }
        })

        $.ajax({
            type: 'PUT',
            url: _ctx+'p/trainer/subir/fotos/perfil/'+hshId+'/'+rdmsUUIDs,
            data : data,
            contentType: false,
            processData: false,
            dataType: 'json',
            xhr: function() {
                const myXhr = $.ajaxSettings.xhr();
                if(myXhr.upload){
                    myXhr.upload.addEventListener('progress', progress, false);
                }
                return myXhr;
            },
            success: function (data) {
                reqSuccess(data, 3600000);
            },
            error: (xhr)=>{
                exception(xhr);
            },
            complete: ()=> {}
        });
    }
}

function getImgUuids(rdms){
    const all = rdms.split("@");
    let imgPerfil = all[0];

    let galeria ="";

    if(all.length == 2){
        let imgsGaleria = all[1];
        galeria = imgsGaleria.split("|").filter(v=>v!=="").map(e=>e.slice(0,e.lastIndexOf("."))).join('|');//Solo para este caso se necesita el .filter(v=>!=="")
    }
    let flCondSvcs="";

    if(all.length == 3){
        let imgsGaleria = all[1];
        galeria = imgsGaleria.split("|").map(e=>e.slice(0,e.lastIndexOf("."))).join('|');
        let condSvcs = all[2];
        flCondSvcs = condSvcs.split("|").filter(v=>v!=="").map(e=>e.slice(0,e.lastIndexOf("."))).join('|');
    }
    return imgPerfil + "|" + galeria + "|" + flCondSvcs;
}

function resetServicios(){
    const divCamposBasicos = document.querySelector('#SvcCamposBasicos');
    const htmlBasics = `<div class="form-group">
                                              <label>NOMBRE DEL SERVICIO<span class="obligatorio">*</span></label>
                                              <input class="form-control" id="NombreServicio" name="NombreServicio" maxlength="50">
                                          </div>
                                          <div class="form-group">
                                              <label>DESCRIPCIÓN<span class="obligatorio">*</span></label>
                                              <textarea class="form-control" id="DescripcionServicio" name="DescripcionServicio" maxlength="2000"></textarea>
                                          </div>
                                          <div class="form-group list-counter" id="MainIncluyeServicios">
                                              <label>QUE INCLUYE<span class="obligatorio">*</span></label>
                                              <li><textarea class="form-control mg-bt-10 inp-svc-incluye" id="PrimerIncluyeServicio" name="PrimerIncluyeServicio" placeholder="Ejem: Tendrás cuatro master-class iniciales para mejorar tu técnica de carrera en sesiones grupales." maxlength="500"></textarea></li>
                                              <a href="javascript:void(0);" class="add" onclick="javascript:agregarTextareaDinamico(this, 10, 'inp-svc-incluye', 500)">&nbsp;<i title="Agregar" class="fa fa-15x fa-plus pull-right"></i></a>
                                          </div>
                                          <div class="form-group">
                                              <label>INFORMACIÓN ADICIONAL</label>
                                              <textarea class="form-control" id="ServicioInfAdic" name="ServicioInfAdic" style="height: 56px !important;" maxlength="1000"></textarea>
                                          </div>
                                          `;
    divCamposBasicos.innerHTML = htmlBasics;

    document.querySelectorAll('input.inp-svc-tyc').forEach(e=>{
        if(e.id !== 'inpCondServicio'){
            e.remove();
        }
    });
    $('#btnSubirCondServicio').attr('onclick', 'javascript:document.getElementById(\'inpCondServicio\').click()');
    $('#btnSubirCondServicio').html('<i class="fa fa-cloud-upload fa-fw"></i> ADJUNTAR T&C')
    termConSvc = [];
    selServicioId = -1;
    accServicioId = 0;
    accTarifaId = 0;
    accCuentaId = 0;
}
