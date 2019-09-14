//Variables requeridas
let indexGlobal = 0;
let $body = $("html,body");
let $rutina;
let $inFocus;
let $diaPlantilla;
let $diaPlantillas;
let $yelmo = '';
let $nombreActualizar = '';
let $memoriaAudio = '';
let $mediaAudio = '';
let $mediaNombre = '';
let $memoriaVideo = '';
let $mediaVideo = '';
let $semanaInicial = {};
let $tiempoActualizar = '';
let $kmsActualizar = '';
let $gIndex = '';
let $tipoMedia = '';
let $eleElegidos = [];
let $subEleElegidos = [];
let $eleFromMiniElegidos = [];
let $eleListas = [];
let $eleListasElegidos = [];
let $audiosElegidos = [];
let $videosElegidos = [];
let $refIxsSemCalendar = [];
let $fechasCompetencia = [];
let $eleGenerico;
let $estilosCopiados = [];
let $statusCopy = false;
let $kilometrajeBase = [];
let $semCalculoMacro = {};
let $objetivos = [];
let $ruConsolidado;
let $chartTemporada = {};
let $chartMiniPorc = {};
let $idsComp = [];
let $semanasEnviadas = [];
let $diasSeleccionados = [];
let isDg = "n";//Importante iniciarlo con n

//Contenedores y constantes
const $semActual = document.querySelector('#SemanaActual');
const $semanario = document.querySelector('#RutinaSemana');
const rutinarioCe = document.querySelector('#tabRutinarioCe');
const cboSubCategoriaId = document.querySelector('#SubCategoriaId');
const cboSubCategoriaIdSec = document.querySelector('#SubCategoriaIdSec');
const cboEspSubCategoriaIdSec = document.querySelector('#EspecificacionSubCategoriaIdSec');
const btnGuardarMini = document.querySelector('#btnGuardarMini');
const btnCopiarMini = document.querySelector('#btnCopiarMini');
const tabRutina = document.querySelector('#myTabRutina');
const semLineal = document.querySelector('#SemanasLineal');
const tabGrupoAudios = document.querySelector('#tabGrupoAudios');
const tabGrupoVideos = document.querySelector('#tabGrupoVideos');
const tabFichaTecnica = document.querySelector('#tabFichaTecnica');
const modalMini = document.querySelector('#modalMiniPlantilla');
const catRutinasDiaIndex = document.querySelector('#CatRutinasDiaIndex');
const catMisRutinas = document.querySelector('#CatMisRutinas');
const shortcutRutinario = document.querySelector('#ShortcutRutinario');
const mainTabs = document.querySelector('#PrincipalesTabs');
const divEditor = document.querySelector('#DivEditor');
const selectorFzEditor = document.querySelector('#SelectorFzEditor');
const tbCompetencias = document.querySelector('#TablaCompetencias');
const btnVerDetSemanas = document.querySelector('#btnVerDetalleSemanas');
const btnComprobarMacro = document.querySelector('#btnComprobar');
const btnGenerarRutinaCompleta = document.querySelector('#btnGenerarRutina');
const nivelAtletaRdBtn = document.querySelector('#NivelAtleta');
const distAtletaRdBtn = document.querySelector('#DistanciaRutina');
const fInitMacro = document.querySelector('#MacroFechaInicio');
const fFinMacro = document.querySelector('#MacroFechaFin');
const btnActualizarMvz = document.querySelector('#btnActualizarMvz');
const rutinaStringify = JSON.parse($('#RutinaStringify').val());
//TEMP
const bgMantaIntensidad = ["gold", "gray", "skyblue", "gray"];

$(function () {
    init();
})


function init(){

    let uriParam = getParamFromURL("si");
    obtenerSemanaInicialRutina().then((semana)=>{
        //Esconder la opcion de collapse del menú principal
        document.querySelector('#left-panel .minifyme').classList.toggle('hidden');
        //Importante mantener el orden para el correcto funcionamiento

        $rutina = new Rutina(rutinaStringify);

        let semanaIdx = atob(uriParam);
        if(uriParam){
            $('#SemanaActual').text(Number(semanaIdx)+1);
            $rutina.initEspecifico(semana,semanaIdx)
          //console.log(semanaIdx);
        }else{
            $rutina.init(semana);
        }
        validators();
        instanciarDatosFitnessCliente();
        tabRutina.addEventListener('click', principalesEventosTabRutina);
        tabGrupoAudios.addEventListener('click', principalesEventosTabGrupoAudios);
        tabGrupoVideos.addEventListener('click', principalesEventosTabGrupoVideos);
        tabFichaTecnica.addEventListener('click', principalesEventosTabFichaTecnica);
        tabFichaTecnica.addEventListener('focusout', principalesEventosFocusOutTabFichaTecnica);
        $semanario.addEventListener('click', principalesEventosClickRutina);
        $semanario.addEventListener('focusout', principalesEventosFocusOutSemanario);
        $semanario.addEventListener('focusin', principalEventoFocusIn);
        rutinarioCe.addEventListener('click', genericoRutinarioCe);
        cboSubCategoriaId.addEventListener('change', cargarListaSubCategoriaEjercicio);
        cboSubCategoriaIdSec.addEventListener('change', cargarListaSubCategoriaEjercicio);
        cboEspSubCategoriaIdSec.addEventListener('change', cargarReferenciasMiniPlantilla);
        selectorFzEditor.addEventListener('change', ajustarFuenteElemento);
        btnGuardarMini.addEventListener('click', guardarMiniPlantilla);
        btnActualizarMvz.addEventListener('click', actualizarMetricasVelocidadBD);
        shortcutRutinario.addEventListener('click', abrirAtajoRutinario);
        mainTabs.addEventListener('click', principalesAlCambiarTab);
        divEditor.addEventListener('click', principalesDivEditor);
        btnVerDetSemanas.addEventListener('click', FichaSeccion.newAlertaInfoSemanas);
        btnComprobarMacro.addEventListener('click', MacroCiclo.comprobar);
        btnGenerarRutinaCompleta.addEventListener('click', MacroCiclo.generarRutinaCompleta);
        Array.from(nivelAtletaRdBtn.querySelectorAll('.chkNivel')).forEach(v=>v.addEventListener('change', MacroCiclo.instanciarKilometrajeBase));
        Array.from(distAtletaRdBtn.querySelectorAll('.chkDistancia')).forEach(v=>v.addEventListener('change', MacroCiclo.instanciarKilometrajeBase));
        fInitMacro.addEventListener('change', FichaSet.setTotalSemanas);
        fFinMacro.addEventListener('change', FichaSet.setTotalSemanas);
        //btnCopiarMini.addEventListener('click', copiarMiniPlantilla);
        window.addEventListener('scroll', scrollGlobal);//Scroll event para regresar al techo del container
        instanciarMarcoEditor();
        instanciaMediaBD();
        instanciaPerfectScrollBar();
        instanciarTooltips();
        modalEventos();
        //setFechaActual(document.querySelectorAll('input[type="date"]'));
        //obtenerSemanasEnviadas();
       // calendarioTmp();


    });


}

function avanzarRetrocederSemana(numSem, action, parentDiv){

    obtenerEspecificaSemana(numSem, action).then((semana)=> {
        if(semana != undefined) {
            $('#RutinaSemana').html(`<h1 style="padding-left: 18%; font-size: 5em;">Por favor espere... <i class="fa fa-spinner fa-spin"></i></h1>`);
            //Importante mantener el orden para el correcto funcionamiento
            $rutina = new Rutina(rutinaStringify);
            $rutina.initEspecifico(semana, numSem);
            instanciarTooltips();
            generarDiasEnviados();
            parentDiv.removeAttribute('hidden');
        }
    });
}

async function obtenerSemanaInicialRutina(){
    return new Promise((resolve, reject)=>{
        $.ajax({
            type: 'GET',
            url: _ctx + 'gestion/rutina/primera-semana/edicion',
            dataType: "json",
            success: function (data, textStatus) {
                if (textStatus == "success") {
                    if (data == "-9") {
                        $.smallBox({
                            content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                            timeout: 4500,
                            color: "alert",
                        });
                    } else {
                        //Semana
                        resolve(data);
                        console.log(data);
                    }
                }
            },
            error: function (xhr) {
                reject("-1");
                exception(xhr);
            },
            complete: function () {
            }
        });
    })
}

async function obtenerEspecificaSemana(semanaIndex, action){
    let promesa = new Promise((resolve, reject)=>{
        $.ajax({
            type: 'GET',
            url: _ctx + 'gestion/rutina/semana/obtener/'+semanaIndex,
            dataType: "json",
            success: function (data) {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                } else {
                    if(data.fechaInicio == undefined) {
                        $.smallBox({content: "<i> Usted se encuentra ya en la última semana de la rutina...</i>", timeout: 4500, color: "alert"});
                        resolve(undefined);
                    } else {
                        //Semana
                        if (action == 1)//Avanzar
                            $semActual.textContent = Number($semActual.textContent.trim()) + 1;
                        else if (action == 2)//Semana elegida desde el calendario
                            $semActual.textContent = Number(semanaIndex) + 1;
                        else
                            $semActual.textContent = Number($semActual.textContent.trim()) - 1;
                        resolve(data);
                        const semDiv = document.querySelector('#Semanas');
                        semDiv.style.border = "3px solid gold";
                        setTimeout(() => semDiv.style.border = "", 3000);
                    }
                }
            },
            error: function (xhr) {
                reject("-1");
                exception(xhr);
            },
            complete: function () {
            }
        });
    })
    return promesa;
}

function validators(){
    DiaOpc.validarGuardaMiniPlantilla();
}

function modalEventos(){

    $('#modalCategoriasRutinas').click(e=>{
        e.preventDefault();
        e.stopPropagation();
        const clases = e.target.classList;
        const input = e.target;
        if(clases.contains('img-cat-rutina')){
            DiaOpc.guardarMisPlantillas(input);
        }
    })

    $('#modalPreGuardarMini').click((e)=>{
        const clases = e.target.classList;
        if(clases.contains('elegir-mis-rutinas')){
            $('#modalCategoriasRutinas').modal('show');
            $('#modalPreGuardarMini').modal('hide');
        }else if(clases.contains('elegir-rutinario')){
            $('#modalGuardarMini').modal('show');
            $('#modalPreGuardarMini').modal('hide');
        }
    })

    $('#modalMiniPlantilla').on('show.bs.modal', ()=> {
        $eleListasElegidos = [];
    })

    $('#modalCategoriasRutinas').on('hidden.bs.modal', ()=> {
        document.body.style = "";
    })

    $('#modalGuardarMini').on('hidden.bs.modal', ()=> {
        document.body.style = "";
    })

    $('#modalMiniPlantilla').on('hidden.bs.modal', ()=> {
        document.body.style = "";
        $eleListas = [];
    })

    $('#modalPickRutina').on('hidden.bs.modal', (e)=> {
        $('#SubCategoriaIdSec').val('');
        $('#EspecificacionSubCategoriaIdSec').val('');
        $('#EspecificacionSubCategoriaIdSec').parent().removeClass('state-success');
        const body = e.target.querySelector('fieldset');
        body.children.length==4? body.children[2].remove(): '';
    })



    $('#myModalMini').on('hidden.bs.modal', ()=> {
        $('#MiniPlantilla').html('');
        nombreListaMini.value = '';
        nombreListaMini.parentElement.classList.remove('hidden');
    })



    $('#modalGuardarMini').on('hidden.bs.modal', ()=>{
        $('#SubCategoriaId').val('');
        $('#EspecificacionSubCategoriaId').val('');
        $('#EspecificacionSubCategoriaId').parent().removeClass('state-success');
    })

    $('#myModalVideo').on('hidden.bs.modal', ()=> {
        $("#VideoReproduccion").parent().get(0).pause();
    })

    $('#myTabRutina').on('hidden.bs.popover', function (e) {
        $(e.target).data("bs.popover").inState.click = false;
    });

    modalMini.addEventListener('click', principalesEventosModalMini);
}

function principalesEventosModalMini(e){
    const input = e.target;
    const clases = e.target.classList;
    if(clases.contains('rf-pl-dia-copy')){
        DiaOpc.copiarMiniPlantillaDia(input);
    }
    else if(clases.contains('rf-pl-eles-copy')){
        DiaOpc.copiarElementosCompuestosMiniPlantilla((diaPos = input.getAttribute('data-pos')));
    }
    else if(clases.contains('rf-dia-elemento-nombre')){
        e.preventDefault();
        e.stopPropagation();
        const posDia = Number(input.getAttribute('data-dia-pos'));
        const posDiaEle = Number(input.getAttribute('data-pos'));

        if(clases.contains('txt-color-greenIn')){
            $eleListas = $eleListas.filter(e=> {return !(e[1]==posDiaEle && e[0]==posDia)});
        }else{
            $eleListas.push([posDia, posDiaEle]);
            $eleListas = $eleListas.sort();
        }
        input.classList.toggle('txt-color-greenIn');
    }
}

function instanciarMarcoEditor(){
    $('.summernote').summernote({
        height: 0,
        width: 120,
        toolbar: [
            ['font', ['bold', 'italic']],
        ]
    });
    document.querySelector('.note-frame').addEventListener('click', guardarAccionEditor);
    //ocultarBordeDeEditor
    document.querySelector('.note-editing-area').setAttribute('hidden','hidden');
    document.querySelector('.note-toolbar').classList.add('text-align-center');
    document.querySelector('.note-statusbar').setAttribute('hidden','hidden');
}

function instanciaMediaBD(){
    $("#demo-setting").click(function () {
        $(".demo").toggleClass("activate")
    });
}

function instanciaPerfectScrollBar(){
    new PerfectScrollbar('#scrollRutina');
}

function instanciarTooltips(){
    $('[rel="tooltip"]').tooltip();
}
function instanciarPopAndToolDeMiniPlantillas() {
    document.querySelectorAll('#modalMiniPlantilla #ContenidoMini [rel="tooltip"]').forEach(v => $(v).tooltip());
    document.querySelectorAll('#modalMiniPlantilla #ContenidoMini [data-toggle="popover"]').forEach(v => $(v).popover());
}

function instanciarElementoTooltips(input){
    input.querySelectorAll('i[rel="tooltip"]').forEach(v=>$(v).tooltip());
}

function instanciarElementoPopovers(input){
    input.querySelectorAll('[data-toggle="popover"]').forEach(v=>$(v).popover());
}

function instanciarEspecificosTooltip(input){
    input.nextElementSibling.querySelectorAll('i[rel="tooltip"]').forEach(v=>$(v).tooltip());
}

function instanciarSubElementoTooltip(subEle){
    subEle.querySelectorAll('i[rel="tooltip"]').forEach(v=>$(v).tooltip());
}

function instanciarSubElementosTooltip(elemento){
    elemento.querySelectorAll('i[rel="tooltip"]').forEach(v=>$(v).tooltip());
}

function instanciarSubElementosPopover(elemento){
    elemento.querySelectorAll('[data-toggle="popover"]').forEach(v=>$(v).popover());
}

function instanciarEspecificosTooltip2(input){
    input.previousElementSibling.querySelectorAll('i[rel="tooltip"]').forEach(v=>$(v).tooltip());
}

function instanciarSubElementoPopover(input){
    input.querySelectorAll('[data-toggle="popover"]').forEach(v=>$(v).popover());
}

function instanciarElementosDiaTooltip(elementos){
    elementos.querySelectorAll('i[rel="tooltip"]').forEach(v=>$(v).tooltip());
}
function instanciarElementosDiaPopover(elementos){
    elementos.querySelectorAll('[data-toggle="popover"]').forEach(v=>$(v).popover());
}

function generandoNuevaMiniPlantilla(subCatId){
    if(document.querySelector('#ArbolRutinario').children.length > 0) {
        let especificacion = document.querySelector(`#ArbolRutinario a[data-especificacion-id="${subCatId}"]`).parentElement;
        let cantHijos = especificacion.children.length;//Como el icon plus representa un hijo ya no le aumentamos +1
        let a = document.createElement('a');
        a.href = 'javascript:void(0)';
        a.innerHTML = `<span onclick="obtenerMiniPlantilla(${subCatId}, ${--cantHijos});" class="badge bg-color-greenLight font-md mini">${++cantHijos}</span>`;
        especificacion.append(a);
        setTimeout(() => {
            especificacion.children[cantHijos].children[0].classList.remove('bg-color-greenLight');
            especificacion.children[cantHijos].children[0].classList.add('bg-color-darken');
        }, 4000);
    }
}

function guardarMiniPlantilla(e){
    e.preventDefault();
    const params = {};
    params.diaIndice = e.target.getAttribute('data-dia-index');
    params.numeroSemana = $semActual.textContent - 1;
    params.especificacionSubCategoriaId = document.querySelector('#EspecificacionSubCategoriaId').value;
    const valId = Number(params.especificacionSubCategoriaId);
    if($("#frm_nueva_mini").valid() && !isNaN(valId) && valId > 0){
        $('#spRaw').html(spinnerHTMLRaw());
        $('#btnGuardarMini').attr('disabled', 'disabled');
        $('#btnGuardarMini').text('Cargando...');
        $.ajax({
            type: 'POST',
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            url: _ctx + 'gestion/mini-plantilla/agregar/dia-rutinario',
            dataType: "json",
            data: params,
            success: function (data, textStatus) {
                if (textStatus == "success") {
                    if (data == ResponseCode.EX_GENERIC) {
                        $.smallBox({
                            content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                            timeout: 4500,
                            color: "alert",
                        });
                    }
                    if (data == ResponseCode.EX_NUMBER_FORMAT) {
                        $.smallBox({
                            content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                            timeout: 4500,
                            color: "alert",
                        });
                    } else {
                        $.smallBox({content: "<i>La mini plantilla se ha guardado satisfactoriamente...</i>"});
                        generandoNuevaMiniPlantilla(params.especificacionSubCategoriaId);
                    }
                }
            },
            error: function (xhr) {
                exception(xhr);
            },
            complete: function () {
                $('#btnGuardarMini').removeAttr('disabled');
                $('#btnGuardarMini').text('Confirmar');
                $('#spRaw').html('');
                $('#modalGuardarMini').modal('hide');
                $('#EspecificacionSubCategoriaId').val('');
            }
        });
    } else{}
}

function abrirAtajoRutinario(e){
    e.preventDefault();
    e.stopPropagation();
    $('#modalPickRutina').modal('show');
}

function guardarEnMisRutinas(e){
    const params = {};
    params.diaIndice = catRutinasDiaIndex.getAttribute('data-dia-index');
    params.numeroSemana = $semActual.textContent - 1;
    params.categoriaId = e.getAttribute('data-index');

    const valId = Number(params.categoriaId);
    if(!isNaN(valId) && valId > 0){
        $('#modalCategoriasRutinas').modal('hide');
        $.SmartMessageBox({
            title : "Notificación",
            content : "Por favor ingrese un título para la mini rutina",
            buttons : "[Cancelar][Guardar]",
            input : "text",
            placeholder : "Ingrese un título"
        }, function(buttonPress, value) {
            const nivel = document.querySelector('#slctNvlMini').value;
            const btnSave =  document.querySelector('#bot2-Msg1');
            if(buttonPress != "Cancelar" && value != undefined && value.trim().length > 5 && nivel != undefined && Number(nivel)>0 && Number(nivel) <6){
                if(!btnSave.classList.contains('disabled')){
                    btnSave.classList.add('disabled');
                    params.nombre = value.trim();
                    params.nivel = nivel;
                    params.metrica = DiaFunc.obtenerTiempoCarreraReferencia(params.diaIndice);
                    $.ajax({
                        type: 'POST',
                        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                        url: _ctx + 'gestion/mini-rutina/agregar/dia-rutinario',
                        dataType: "json",
                        data: params,
                        success: function (data) {
                            notificacionesRutinaSegunResponseCode(data);
                            if(data == "-1")
                                $.smallBox({content: "<i>El día se ha guardado en mis rutinas satisfactoriamente...</i>"});
                        },
                        error: function (xhr) {
                            exception(xhr);
                        },
                        complete: function () {
                            catRutinasDiaIndex.innerHTML = '';
                            $('#modalCategoriasRutinas').modal('hide');
                        }
                    });
                }
            }else{
                $('#modalCategoriasRutinas').modal('show');
                buttonPress == "Cancelar" ? "" : $.smallBox({color: "alert", content: "<i>La operación no se ha realizado porque o bien el título ingresado no cuenta con un mínimo de 6 letras o no se ha seleccionado un nivel válido...</i>", timeout: 8000});
            }
        });
        let assurance = 0;
        const interval = setInterval(()=>{
            assurance+=1;
            if(document.querySelector('#txt1') != undefined){
                document.querySelector('#txt1').insertAdjacentHTML('afterend', '</br><select class="form-control" id="slctNvlMini"><option value="">-- Selecciona un nivel --</option><option value="1"> -- Nivel 01 -- </option><option value="2"> -- Nivel 02 -- </option><option value="3"> -- Nivel 03 -- </option><option value="4"> -- Nivel 04 -- </option><option value="5"> -- Nivel 05 -- </option></select>');
                window.clearInterval(interval);
            }
            assurance>15 ? window.clearInterval(interval) : "";
        }, 100);
    } else{}
}

function cargarListaSubCategoriaEjercicio(e){
    let subCatId = e.target.value;
    let params = {};
    params.subCatId = subCatId;

    $.ajax({
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'gestion/especificacion-sub-categoria-ejercicio/listarPorSubCategoria',
        dataType: "json",
        data: params,
        success: function (data, textStatus) {
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                }else{
                    $('#EspecificacionSubCategoriaId').html('');
                    //El 3 de la divisón representa los niveles fijos que tiene cada especificación de alguna sub categoría
                    len = data.length/3;
                    nData = [data.slice(0, data.length/3),data.slice(len, len*2),data.slice(len*2, data.length)];//Guardo el arreglo inicial como uno multidimensional
                    niveles = ["Básico", "Intermedio", "Avanzado"];
                    let htmlRaw = '<option value="">-- Seleccione --</option>';
                    //Genero un for con la cantidad de iteraciones igual a los niveles( para el caso 3)
                    for(let i=0;i<3;i++) {
                        let nv = niveles[i];
                        htmlRaw += `<optgroup label="${nv}">`
                        nData[i].forEach(v => {
                            htmlRaw +=`<option value="${v.id}">${v.nombre}</option>`;
                        });
                        htmlRaw += `</optgroup>`
                    }
                    $('#EspecificacionSubCategoriaId').html(htmlRaw);
                    $('#EspecificacionSubCategoriaId').parent().addClass('state-success');
                    $('#EspecificacionSubCategoriaIdSec').html(htmlRaw);
                    $('#EspecificacionSubCategoriaIdSec').parent().addClass('state-success');
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    });
}

function cargarReferenciasMiniPlantilla(e){
    let params = {espSubCatId: e.target.value};

    $('#spRaw4').html(spinnerHTMLRaw());
    $.ajax({
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'gestion/mini-plantilla/obtener/referencias',
        dataType: "json",
        data: params,
        success: function (data, textStatus) {
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                }else{
                    if(data != "0"){
                        let raw = '<div class="text-align-center">';
                        for(let i=0;i<data;i++){
                            raw+=`<a href="javascript:void(0);"><span onclick="obtenerMiniPlantilla(${params.espSubCatId}, ${i}, ${data});" class="badge bg-color-darken font-md mini">${i+1}</span></a>`;
                        }
                        raw+= '</div>';
                        const sameLevel = e.target.parentElement;
                        sameLevel.parentElement.children.length == 3 ? sameLevel.insertAdjacentElement('afterend', htmlStringToElement(raw)): sameLevel.parentElement.replaceChild(htmlStringToElement(raw), sameLevel.nextElementSibling);
                    }else{
                        $.smallBox({color: "info", content: "<i>No se encontraron concidencias...</i>"})
                    }
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
            $('#spRaw4').html('');
        }
    });
}

function ajustarFuenteElemento(e){
    const input = e.target;
    const estiloId = input.value;
    const ixs = RutinaIx.getIxsForElemento($eleGenerico);
    let tempEle = RutinaDOMQueries.getElementoByIxs(ixs), i=0;
    while((tempEle = tempEle.previousElementSibling) != null) i++;
    const objEditor = RutinaEditor.obtenerEstiloById(estiloId);
    RutinaDelete.eliminarEstilosFuente(ixs.numSem, ixs.diaIndex, (posEle = i), 1);
    RutinaAdd.nuevoEstilo(ixs.numSem, ixs.diaIndex, (posEle = i), estiloId, objEditor.clase, objEditor.tipo);
    $eleGenerico.classList.forEach((v,i,k)=>{v.includes('rf-fs')?k.remove(v):''});
    $eleGenerico.classList.add(objEditor.clase);
    guardarEstilosElementoBD(ixs.numSem, ixs.diaIndex, (posEle = i));
}

function guardarAccionEditor(e){
    e.preventDefault();
    if($inFocus != undefined) {
        if (e.target.classList.contains('note-btn-italic') || e.target.classList.contains('note-icon-italic')) {
            $inFocus.focus();
            $inFocus.style.fontStyle = 'italic';
        }
        if (e.target.classList.contains('note-btn-bold') || e.target.classList.contains('note-icon-bold')) {
            $inFocus.focus();
            $inFocus.style.fontWeight = 'bold';
        }
    }
}

function scrollGlobal() {
    //Se evidencia en la pestaña Rutinario C
    if (document.body.scrollTop > 1000 || document.documentElement.scrollTop > 1000) {
        document.getElementById("myBtn").style.display = "block";
        document.getElementById("myBtn2").style.display = "block";
    } else {
        document.getElementById("myBtn").style.display = "none";
        document.getElementById("myBtn2").style.display = "none";

    }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
    $body.animate({scrollTop: 0},300);
}

function obtenerIconoRepetido(cant, icon){
    let raw = '';
    for(var i=0; i<cant;i++){
        raw+=icon;
    }
    return raw;
}

function instanciarDatosFitnessCliente(){
    $.ajax({
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'gestion/cliente-fitness/obtener/secundario/'+ getParamFromURL('rn'),
        noOne: true,
        dataType: "json",
        success: function (data, textStatus) {
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                } else {
                    if(data.id != 0) {
                        if($rutina.tipoRutina !== TipoRutina.GENERAL){

                            FichaSet.instanciarDatosFicha(data);
                        }else{
                            FichaSet.setTotalSemanas();
                        }
                    }
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    });
}


function instanciarGrupoVideos(){

    $.ajax({
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'gestion/video/obtener/arbol',
        blockLoading: true,
        noOne: false,
        dataType: "json",
        success: function (data, textStatus) {
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                }else{
                    let rawHTMLCabecera = '';
                    rawHTMLCabecera +='<div class="container-fluid padding-0">'
                    data.forEach(grupoVideo => {
                        const rrWeb = grupoVideo.id+"/"+grupoVideo.uuid + grupoVideo.extImg;
                        rawHTMLCabecera +=
                            `<div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    <div class="container-fluid padding-0">
                                        <h1 class="text-align-center txt-color-white padding-7 bg-color-blue-sl"><img class="pull-left" height="80px" src="https://s3-us-west-2.amazonaws.com/rf-media-rutina/grupo-video/${rrWeb}">${grupoVideo.nombre}</h1>
                                    </div>
                                    ${generandoCategoriaVideos(grupoVideo)}
                                 </div>`;


                    });
                    rawHTMLCabecera +='</div>';

                    document.querySelector('#ArbolGrupoVideo').appendChild(htmlStringToElement(rawHTMLCabecera));
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
            updateAudioFavoritos();
        }
    });
}

function instanciarGrupoAudios(){

    $.ajax({
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'gestion/tipo-audio/obtener/arbol',
        blockLoading: true,
        noOne: false,
        dataType: "json",
        success: function (data, textStatus) {
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                }else{
                    let rawHTMLCabecera = '';
                    rawHTMLCabecera +='<div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12">'
                    data.forEach((grupoAudio, i) => {
                        rawHTMLCabecera +=
                           `${(i % 4) === 0 ? '<div><div class="row"></div></div>':''}                          
                            <div class="padding-bottom-10 col col-xs-12 col-sm-3 col-md-3 col-lg-3">
                                <h6 class="font-lg">${grupoAudio.nombre}</h6>
                                ${grupoAudio.lstAudio.map(a=>
                                    `<ul class="ul-audio">
                                        <li class="font-md">
                                            <a class="" href="javascript:void(0);">
                                            <i data-placement="bottom" rel="tooltip" data-original-title="Favorito" class="ck-favorito-audio fa fa-star fa-fw"
                                                data-id=${a.id} id='liaudio${a.id}' data-selected="0"></i>
                                            </a>
                                            <a class="elegir-audio" href="javascript:void(0);">
                                            <i data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-audio fa fa-music fa-fw" data-media=${a.rutaWeb} data-index=${a.id}></i>
                                                ${a.nombre}
                                            </a>
                                        </li>
                                    </ul>`).join('')}
                            </div>`;
                    });
                    rawHTMLCabecera +='</div>';
                    document.querySelector('#ArbolGrupoAudio').appendChild(htmlStringToElement(rawHTMLCabecera));
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
            updateAudioFavoritos();
        }
    });
}

async function instanciarPorcentajesKilometraje(distancia){
    return new Promise((res, rej)=>{
        $.ajax({
            type: 'GET',
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            url: _ctx + 'calculo/porcentajes-kilo/obtener/'+distancia,
            dataType: "json",
            success: function (data, textStatus) {
                if (textStatus == "success") {
                    notificacionesRutinaSegunResponseCode(data);
                    res(data);
                }
            },
            error: function (xhr) {
                rej("fail");
                exception(xhr);
            },
            complete: function () {}
        });
    })
}

function copiarSemanaBD(e){
    const sA = Number($semActual.textContent) - 1;
    const sP = Number(e.parentElement.parentElement.children[0].value);
    e.setAttribute('disabled', 'disabled');
    $.ajax({
        type: 'PUT',
        contentType: "application/json",
        url: _ctx + 'gestion/rutina/semana-completa/actualizar/'+sA+'/'+sP,
        dataType: "json",
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
            if(data == "-2") $.smallBox({content: "<i>La semana ha sido copiada correctamente</i>"});
            setTimeout(()=>{e.removeAttribute('disabled', 'disabled')}, 1000)
        },
        error: function (xhr) {
            setTimeout(()=>{e.removeAttribute('disabled', 'disabled')}, 1000)
            exception(xhr);
        },
        complete: function () {}
    });
}

function actualizarPorcentajesKilometrajeBD(porcentajes){
    $.ajax({
        type: 'PUT',
        contentType: "application/json",
        url: _ctx + 'calculo/porcentajes-kilo/actualizar',
        dataType: "json",
        data: JSON.stringify(porcentajes),
        success: function (data) {
            if(notificacionesRutinaSegunResponseCode(data) != undefined){

            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    });
}

function obtenerKilometrajeBaseBD(distancia, nivel){
    const o  = {};
    o.distancia = distancia;
    o.nivelAtleta = nivel;
    $.ajax({
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'calculo/kilometraje/base/obtener',
        dataType: "json",
        noOne: true,
        data: o,
        success: function (data, textStatus) {
            if (textStatus == "success") {
                $kilometrajeBase = data;
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    });
}

function generandoCategoriaVideos(catsVideo){
    let rawSubCategoriasHTML = '',
        rawGruposAElegirHTML = '<div class="container-fluid">';

    catsVideo.lstCategoriaVideo.forEach(catVideo=> {
        rawSubCategoriasHTML += `<div class="col col-xs-6 col-sm-3 col-md-2 col-lg-2 padding-10">
                                    <h6 class="txt-color-grayDark font-md"><a href="javascript:void(0);" class="cat-video" data-id="${catVideo.id}">${catVideo.nombre}</a></h6>
                                    <div class="container-fluid padding-0">
                                        ${generandoSubCategoriaVideos(catVideo)}
                                    </div>  
                                </div>`;

        rawGruposAElegirHTML +=`<div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12 cat-video${catVideo.id} padding-0">
                                    <h1 class="text-align-center txt-color-white bg-color-blue-sl padding-10">${catVideo.nombre}</h1>
                                    <div class="container-fluid">
                                        ${generandoSubCategoriaVideosCuerpo(catVideo)}
                                    </div>
                                </div>`;

    })

    rawGruposAElegirHTML+='</div>';

    document.querySelector('#ArbolGrupoVideoDetalle').appendChild(htmlStringToElement(rawGruposAElegirHTML));
    return rawSubCategoriasHTML;
}

function generandoSubCategoriaVideos(catVideo){
    let rawSubCategoriasHTML = '';
    catVideo.subCategoriasVideo.forEach(subCatVideo=> {
        rawSubCategoriasHTML += `<span style="display: block">${subCatVideo.nombre}</span>`;
    })
    return rawSubCategoriasHTML;
}

function generandoSubCategoriaVideosCuerpo(catVideo){
    let rawHTML = ''

    catVideo.subCategoriasVideo.forEach(subCatVideo=> {
        rawHTML += `
                   <div class="col col-xs-6 col-sm-3 col-md-2 col-lg-2">
                       <h6 class="txt-color-grayDark font-md" data-id="${subCatVideo.id}">${subCatVideo.nombre}</h6>
                            ${generandoVideosCuerpo(subCatVideo)}
                   </div>
                  `;
    })
    return rawHTML;
}

function generandoVideosCuerpo(subCatVideo){
    let rawVideosHTML = '';
    subCatVideo.videos.forEach(v=> {
        rawVideosHTML += `<a class="elegir-video padding-7-no-left" href="javascript:void(0);">
                          <i id="livideo${v.id}" title="Agregar a favoritos" class="fa fa-star fa-fw ck-favorito-video padding-top-3" data-selected="0" data-id="${v.id}"></i>
                          <i class="fa fa-arrow-circle-left fa-fw ck-video padding-top-3"></i>
                          <i data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-video fa fa-video-camera fa-fw" data-media="/${v.id+'/'+v.rutaWeb+'?v'+v.version +'&tn='+v.thumbnail+'.jpg'}" data-index="${v.id}">
                          </i>${v.nombre}</a>`;
    })
    return rawVideosHTML;
}

function instanciarMiniPlantillas(){

    $.ajax({
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'gestion/especificacion-sub-categoria-ejercicio/v2',
        dataType: "json",
        success: function (data, textStatus) {
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                } else {
                    let rawHTML = '';
                    rawHTML = '<div class="container-fluid padding-0">';
                    data.forEach(cat => {
                        rawHTML +=
                                        `<div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                             <h1 class="text-align-center txt-color-white padding-7 bg-color-blue-sl">${cat.nombre}</h1>
                                             ${generandoSubCategoriasRutinarioCe(cat)}
                                         </div>`;
                    });
                    rawHTML += '</div>'
                    document.querySelector('#ArbolRutinario').appendChild(htmlStringToElement(rawHTML));
                }
            }
        },
        error: function (xhr) {
            window.setInterval(effImg);
            exception(xhr);
        },
        complete: function () {}
    });
}

function generandoSubCategoriasRutinarioCe(cat){
    let rawSubCategoriasHTML = '';
    cat.lstSubCategoriaEjercicio.forEach(subCat=>{
        rawSubCategoriasHTML += `<h6 class="txt-color-grayDark font-lg" style="padding-left: 67px;" > ${subCat.nombre}</h6>
                                <div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12 sub-cat${subCat.id}">
                                    ${separandoEspecificacionesSubCategoriaPorNivel(subCat)}
                                </div>`;
    })
    return rawSubCategoriasHTML;
}

function separandoEspecificacionesSubCategoriaPorNivel(subCat){
    let n1=[],n2=[],n3=[];//Niveles de especificacion
    let generalNiveles = [];
    subCat.especificacionSubCategorias.forEach(esp=>{
        switch (esp.nivel){case 1:n1.push(esp);break;case 2:n2.push(esp);break;default:n3.push(esp);break;}
    });
    generalNiveles.push(n1, n2, n3);
    if(subCat.id !== 10){
        return generandoEspecificacionesPorNiveles(generalNiveles);
    }
    return generandoEspecificacionesPorNiveles(generalNiveles, subCat.id);

}

function generandoEspecificacionesPorNiveles(generalEspSubCat, subCatId){
    let htmlRaw = '';
    if(subCatId){
        console.log(generalEspSubCat);
        generalEspSubCat = generalEspSubCat.slice(0, 1);
        generalEspSubCat.forEach(e=>e.forEach((x,i)=>i!==0 ? delete e[i] : ""));
        generalEspSubCat[0][0].nombre = "Glúteos";
    }
    generalEspSubCat.forEach((nivel, i)=>{
        let iconoRepetido = obtenerIconoRepetido(i+1,'<i class="text-warning fa fa-star"></i>');
        //lvl-${i} de acuerdo al nivel cambia el color del icono
        htmlRaw += `
                        <div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12 padding-bottom-10">
                            <span class="pull-left text-align-center lvl-${i+1}" style="margin-left: -40px;padding-left: 20px"><i class="fa fa-child fa-3x"></i><br>${iconoRepetido}</i></span>
                            <div class="row" style="padding-left: 40px;">
                                ${nivel.map(esp=>`
                                    <div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12 linea padding-bottom-10 font-md">
                                        <a class="pull-left" href="#">
                                            <strong>${esp.nombre}</strong>
                                        </a>
                                        <span class="pull-left padding-left-10">
                                            <a data-target="#myModalMini" data-toggle="modal" data-especificacion-id="${esp.id}" onclick="setEspecificacionId(${esp.id});" title="Agregar nueva plantilla"><span class="badge bg-color-redLight mini font-md">+</span></a>
                                                ${esp.lstMiniPlantilla[0].diaRutinarioIds!=null?esp.lstMiniPlantilla[0].diaRutinarioIds.map((v,i)=>`
                                                    <a href="javascript:void(0);"><span onclick="obtenerMiniPlantilla('${esp.id}',${i});" class="badge bg-color-darken font-md mini">${++i}</span></a>
                                                `).join(''):''}
                                        </span>
                                    </div>
                                `).join('')}
                            </div>
                        </div>
                      `;
    })
    return htmlRaw;
}

function obtenerMiniPlantilla(subCatId, index, sizeFromMainTab){
    //Sirve cuando se busca desde shortcut rutinario
    $('#modalPickRutina').modal('hide');

    $('#modalMiniPlantilla').modal('show');
    let params = {};
    params.subCatId = subCatId;
    params.index = index;
    if(sizeFromMainTab == undefined) {
        let numAdicionales = 1;
        ++numAdicionales;//Le sumamos uno para obviar el icono fa fa plus que se encuentra como un hijo más
        if (document.querySelector(`#ArbolRutinario a[data-especificacion-id="${subCatId}"]`).parentElement.children[Number(index) + numAdicionales] != undefined)
            params.subCatId += "|1";
        else params.subCatId += "|0";

    }else{
        if(sizeFromMainTab-1 == index)
            params.subCatId += "|0";
        else params.subCatId += "|1";
    }
    const contenidoMini = document.querySelector('#ContenidoMini');
    contenidoMini.innerHTML = spinnerHTMLRawCsMessage('Cargando...');
    $.ajax({
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'gestion/mini-plantilla/obtener/dia-rutinario',
        dataType: "json",
        data: params,
        success: function (data, textStatus) {
            $diaPlantillas = data;
            if (textStatus == "success") {
                if (data == ResponseCode.EX_GENERIC) {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                }
                else if (data == ResponseCode.EX_NUMBER_FORMAT) {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                }else {
                    contenidoMini.innerHTML = '';
                    const onlyOne = data.length==1?true:false;
                    data.forEach((dia, ix)=>{
                        dia.arrIx = ++index;
                        document.querySelector('#ContenidoMini').appendChild(htmlStringToElement(RutinaSeccion.newDiaPlantilla(dia, ix, onlyOne)));
                    })
                    Array.from(contenidoMini.querySelectorAll('.widget-body .rf-listas')).forEach(c=>{
                        c.style.height = window.outerHeight * 0.665 + 'px';
                        c.style.overflowY = 'auto';
                    })
                    instanciarPopAndToolDeMiniPlantillas();
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    });
}

function copiarMiniPlantilla(){
    $('#modalMiniPlantilla').modal('hide');
    document.querySelector('a[href="#tabRutina"]').click();
}

function principalesEventosClickRutina(e) {
    const clases = e.target.classList;
    let input = e.target;
    if(e.ctrlKey){
        if(clases.contains('rf-dia-elemento-nombre')){
            DiaOpc.seleccionarElementos(input);
        }else if(clases.contains('rf-sub-elemento-nombre')){
            DiaOpc.seleccionarSubElementos(input);
        }
    }

    if(clases.contains('in-ele-dia-1')){
        if(validUUID($mediaAudio) || validUUID($mediaVideo)){
            const inp = input.parentElement.parentElement.parentElement.parentElement;
            inp.classList.toggle('hidden');
            const ixs = RutinaIx.getIxsForDia(input);
           ElementoOpc.agregarInitMediaElemento(ixs, ElementoTP.SIMPLE);
        }
    }
    else if(clases.contains('in-ele-dia-2')){
        if(validUUID($mediaAudio) || validUUID($mediaVideo)){
            input.parentElement.parentElement.parentElement.parentElement.classList.toggle('hidden');
            const ixs = RutinaIx.getIxsForDia(input);
            ElementoOpc.agregarInitMediaElemento(ixs, ElementoTP.COMPUESTO);
        }
    }
    else if(clases.contains('in-sub-elemento')){
        if(validUUID($mediaAudio) || validUUID($mediaVideo)){
            clases.toggle('hidden');
            const obj = {};
            obj.nombre = $mediaNombre;
            $tipoMedia == TipoElemento.AUDIO?obj.mediaAudio = $mediaAudio : obj.mediaVideo = $mediaVideo;
            obj.tipo = $tipoMedia;
            let ixs = RutinaIx.getIxsForSubElemento(input);
            const nuevoIx = RutinaSeccion.newSubElemento(ixs.diaIndex, ixs.eleIndex, $tipoMedia, obj.nombre);
            ixs.subEleIndex = nuevoIx;
            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
            let nSubEle = tempElemento.querySelector(`div[data-index="${nuevoIx}"]`);
            let i = 0;
            while ((tempElemento = tempElemento.previousElementSibling) != null) i++;
            RutinaAdd.nuevoSubElementoMedia(ixs.numSem, ixs.diaIndex, i, obj);
            agregarSubElementoAElementoBD(ixs.numSem, ixs.diaIndex, i, 0);//Siempre va ser el primero por eso se deja la posicion como 0*/
            const iconoOpc = RutinaDOMQueries.getSubElementoByIxs(ixs).querySelector('.sub-ele-ops');
            if($tipoMedia == TipoElemento.AUDIO){
                iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoAudio($mediaAudio));
            }else{
                iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoVideo($mediaVideo));
            }

            instanciarSubElementoTooltip(nSubEle);
            instanciarSubElementoPopover(nSubEle);

            $mediaAudio = '';
            $mediaVideo = '';
        }
    }
    else if (clases.contains('trash-elemento')) {
        e.preventDefault();
        e.stopPropagation();
        const ixs = RutinaIx.getIxsForElemento(input);
        ElementoOpc.eliminarElemento(ixs.numSem, ixs.diaIndex, ixs.eleIndex);
    }
    else if(clases.contains('trash-audio')) {
        e.preventDefault();
        e.stopPropagation();
        const ixs = RutinaIx.getIxsForElemento(input);
        ElementoOpc.eliminarMediaAudio(ixs);
    }
    else if(clases.contains('trash-video')) {
        e.preventDefault();
        e.stopPropagation();
        const ixs = RutinaIx.getIxsForElemento(input);
        ElementoOpc.eliminarMediaVideo(ixs);
    }
    else if(clases.contains('trash-audio-sub')) {
        e.preventDefault();
        e.stopPropagation();
        const ixs = RutinaIx.getIxsForSubElemento(input);
        SubEleOpc.eliminarMediaAudio(ixs);
    }
    else if(clases.contains('trash-video-sub')) {
        e.preventDefault();
        e.stopPropagation();
        const ixs = RutinaIx.getIxsForSubElemento(input);
        SubEleOpc.eliminarMediaVideo(ixs);
    }
    else if(clases.contains('trash-sub-elemento')){
        const ixs = RutinaIx.getIxsForSubElemento(input);
        RutinaDOMQueries.getSubElementoByIxs(ixs);
        SubEleOpc.eliminarSubElemento(ixs.numSem, ixs.diaIndex, ixs.eleIndex, ixs.subEleIndex);
    }
    else if(clases.contains('agregar-tiempo')){
        //Sirve para comparar el valor inicial del elemento con el valor que retorna en el evento focusout con el fin de evitar actualizaciones innecesarias
        e.preventDefault();
        e.stopPropagation();
        e.target.select();
        $tiempoActualizar = Number(e.target.value.trim());
        $gIndex = e.target.getAttribute('data-index');
    }
    else if(clases.contains('pre-guardar-dia')) {
        e.stopPropagation();
        const ixs = RutinaIx.getIxsForDia(input);
        DiaOpc.preGuardarDiaPlantilla(ixs);
    }
    else if(clases.contains('marcar-descanso')){
        const ixs = RutinaIx.getIxsForDia(input);
        DiaOpc.cambiarFlagDescanso(ixs.numSem, ixs.diaIndex);
    }
    else if(clases.contains('rf-sub-elemento-media')){
        const route = e.target.getAttribute('data-id-uuid');
        const tipoMedia = e.target.getAttribute('data-type');
        if(tipoMedia == TipoElemento.VIDEO){
            $('#VideoReproduccion').get(0).src = `https://s3-us-west-2.amazonaws.com/rf-media-rutina/video${route}`;
            $("#VideoReproduccion").parent().get(0).load();
        }else{
            $('#AudioReproduccion').get(0).src = `${_ctx}workout/media/audio${route}`;
            $("#AudioReproduccion").parent().get(0).load();
        }
    }
    else if(clases.contains('rf-dia-elemento-nombre')){
        e.preventDefault();
        e.stopPropagation();

        if(validUUID($mediaAudio) || validUUID($mediaVideo)){
            const ixs = RutinaIx.getIxsForElemento(input);
            ElementoOpc.agregarMediaToElemento2(ixs, input);
        }
        //Sirve para despues de guardar el valor del input en el onclick validar que este haya sido o no modificado para conforme a eso actualizar en el focusout
        //o evitar actualizaciones innecesarias
        $nombreActualizar = input.textContent.trim();
        $eleGenerico = input;
        if($estilosCopiados != undefined && $statusCopy){
            RutinaEditor.pegarFormato(input);
        }
    }
    else if(clases.contains('rf-sub-elemento-nombre')){
        e.preventDefault();
        e.stopPropagation();
        if(validUUID($mediaAudio) || validUUID($mediaVideo)){
            const ixs = RutinaIx.getIxsForSubElemento(input);
            SubEleOpc.agregarMediaToSubElemento2(ixs, input);
        }
        //Sirve para despues de guardar el valor del input en el onclick validar que este haya sido o no modificado para conforme a eso actualizar en el focusout
        //o evitar actualizaciones innecesarias
        $nombreActualizar = e.target.textContent.trim();
    }
    else if(clases.contains('reprod-audio')){
        e.preventDefault();
        e.stopPropagation();
        if(clases.contains('fa-pause-circle')){
            document.querySelector('#someaud').pause();
            e.target.setAttribute('data-original-title','Reproducir');
        }else{
            const route = e.target.getAttribute('data-media');
            $('#AudioReproduccion').get(0).src = `${_ctx}workout/media/audio${route}`;
            $("#AudioReproduccion").parent().get(0).load();
            e.target.setAttribute('data-original-title','Pausar');
        }
        clases.toggle('fa-music');
        clases.toggle('fa-pause-circle');
    }
    else if(clases.contains('reprod-video')){
        e.stopPropagation();
        e.preventDefault();
        ElementoOpc.reproducirVideo(input);
    }
    else if(clases.contains('ele-ops')){
        e.preventDefault();
        e.stopPropagation();
        instanciarEspecificosTooltip(input);

    }
    else if(clases.contains('sub-ele-ops')){
        e.stopPropagation();
        instanciarEspecificosTooltip(input);
    }
    else if(clases.contains('agregar-nota')) {
        e.stopPropagation();
        const ixs = RutinaIx.getIxsForElemento(input);
        let elemento = RutinaDOMQueries.getElementoByIxs(ixs);
        const type = elemento.getAttribute('data-type');
        if(type == 2) {
            const collapsable = elemento.querySelector('a[data-toggle="collapse"]');
            const panelCollapsable = elemento.querySelector('.panel-collapse');
            collapsable.classList.add('collapsed');
            collapsable.setAttribute('aria-expanded', "false");
            panelCollapsable.classList.remove('in');
            panelCollapsable.setAttribute('aria-expanded', "false");
        }

        if(elemento.children.length != 3) {
            let elementoNota = elemento.querySelector('.rf-dia-elemento-nombre').getAttribute('data-content');
            let notaInput = document.createElement('div');
            notaInput.className = 'panel-heading';
            notaInput.backgroundColor = '#ebf1fd';
            notaInput.innerHTML = `
                    <div class="container-fluid">
                        <textarea class="nueva-nota w-100" data-index="${ixs.eleIndex}" data-dia-index="${ixs.diaIndex}" type="text" rows="2">${elementoNota==undefined?'':elementoNota}</textarea>
                    </div >`
            elemento.append(notaInput);
        }
    }
    else if(clases.contains('agregar-nota-sbe')) {
        const ixs = RutinaIx.getIxsForSubElemento(input);
        let subEle = RutinaDOMQueries.getSubElementoByIxs(ixs);
        if(subEle.children.length != 1) {
            let subEleNota = subEle.querySelector('.rf-sub-elemento-nombre').getAttribute('data-content');
            let notaInput = document.createElement('div');
            notaInput.className = 'panel-heading';
            notaInput.backgroundColor = '#ebf1fd';
            notaInput.innerHTML = `
                    <div class="container-fluid">
                        <textarea class="nueva-nota-sbe w-100" data-index="${ixs.subEleIndex}" data-ele-index="${ixs.eleIndex}" data-dia-index="${ixs.diaIndex}" type="text" rows="2">${subEleNota!=undefined?subEleNota:''}</textarea>
                    </div >`
            subEle.append(notaInput);
        }
    }
    else if(clases.contains('insertar-encima')){
        e.stopPropagation();
        let ixs = RutinaIx.getIxsForElemento(input);
        let elemento = RutinaDOMQueries.getElementoByIxs(ixs);
        let ix = ++indexGlobal;
        let refIndex = ixs.eleIndex;

        if(elemento.nextElementSibling != undefined){
            if(elemento.nextElementSibling.classList.contains('ne-esp')){
                elemento.nextElementSibling.remove();
            }
        }

        if(elemento.previousElementSibling != undefined){
            if(elemento.previousElementSibling.classList.contains('ne-esp')) {

            }else{
                elemento.insertAdjacentHTML('beforebegin', `<div class="container-fluid padding-o-bottom-10 ne-esp"><div class="col-md-6 padding-0"><input type="text" class="w-100 border-rg-no cs-input in-ele-dia-esp-pos" data-ele-tipo="${ElementoTP.SIMPLE}" data-index="${ix}" data-dia-index="${ixs.diaIndex}" data-ele-ref-index="${refIndex}" data-strategy="beforebegin"/></div><div class="col-md-6 padding-0"><input type="text" class="w-100 border-lf-no cs-input in-ele-dia-esp-pos" data-ele-tipo="${ElementoTP.COMPUESTO}" data-index="${ix}" data-dia-index="${ixs.diaIndex}" data-ele-ref-index="${refIndex}" data-strategy="beforebegin"/></div></div>`);
            }
        }else{
            elemento.insertAdjacentHTML('beforebegin', `<div class="container-fluid padding-o-bottom-10 ne-esp"><div class="col-md-6 padding-0"><input type="text" class="w-100 border-rg-no cs-input in-ele-dia-esp-pos" data-ele-tipo="${ElementoTP.SIMPLE}" data-index="${ix}" data-dia-index="${ixs.diaIndex}" data-ele-ref-index="${refIndex}" data-strategy="beforebegin"/></div><div class="col-md-6 padding-0"><input type="text" class="w-100 border-lf-no cs-input in-ele-dia-esp-pos" data-ele-tipo="${ElementoTP.COMPUESTO}" data-index="${ix}" data-dia-index="${ixs.diaIndex}" data-ele-ref-index="${refIndex}" data-strategy="beforebegin"/></div></div>`);
        }
    }
    else if(clases.contains('insertar-debajo')){
        e.preventDefault();
        e.stopPropagation();
        let ixs = RutinaIx.getIxsForElemento(input);
        let elemento = RutinaDOMQueries.getElementoByIxs(ixs);
        let ix = ++indexGlobal;
        let refIndex = ixs.eleIndex;
        elemento.insertAdjacentHTML('afterend', `<div class="container-fluid padding-o-top-1 ne-esp rf-dia-pre-elemento" data-dia-index="${ixs.diaIndex}" data-index="${ix}"><div class="col-md-6 padding-0"><input type="text" class="w-100 border-rg-no cs-input in-ele-dia-esp-pos" data-ele-tipo="${ElementoTP.SIMPLE}" data-index="${ix}" data-dia-index="${ixs.diaIndex}" data-ele-ref-index="${refIndex}" data-strategy="afterend"/></div><div class="col-md-6 padding-0" data-ele-tipo="${ElementoTP.COMPUESTO}" data-index="${ix}"><input type="text" class="w-100 border-lf-no cs-input in-ele-dia-esp-pos" data-ele-tipo="${ElementoTP.COMPUESTO}" data-index="${ix}" data-dia-index="${ixs.diaIndex}" data-ele-ref-index="${refIndex}" data-strategy="afterend"/></div></div>`);
        let i=0;
        while((elemento = elemento.previousElementSibling))i++;
        RutinaAdd.nuevoElemento(ixs.numSem, ixs.diaIndex, i, '');
        agregarElementoEnBlancoBD(ixs.numSem, ixs.diaIndex, ElementoTP.NO_DEFINIDO, (posRefElemento = i), Estrategia.INSERT_DESPUES);
    }
    else if(clases.contains('insertar-encima-sub')){
        e.stopPropagation();
        let ixs = RutinaIx.getIxsForSubElemento(input);
        let subElemento = RutinaDOMQueries.getSubElementoByIxs(ixs);

        if(subElemento.nextElementSibling != undefined){
            if(subElemento.nextElementSibling.classList.contains('ne-esp')){
                subElemento.nextElementSibling.remove();
            }
        }

        if(subElemento.previousElementSibling != undefined){
            if(subElemento.previousElementSibling.classList.contains('ne-esp')) {

            }else{
                subElemento.insertAdjacentHTML('beforebegin', `<div class="container-fluid padding-o-bottom-10 ne-esp"><div class="col-md-12 padding-0"><input type="text" class="w-100 cs-input in-sub-ele-esp-pos" data-dia-index="${ixs.diaIndex}" data-ele-index="${ixs.eleIndex}" data-sub-ele-ref-index="${ixs.subEleIndex}" data-strategy="beforebegin"/></div></div>`);
            }
        }else{
            subElemento.insertAdjacentHTML('beforebegin', `<div class="container-fluid padding-o-bottom-10 ne-esp"><div class="col-md-12 padding-0"><input type="text" class="w-100 cs-input in-sub-ele-esp-pos" data-dia-index="${ixs.diaIndex}" data-ele-index="${ixs.eleIndex}" data-sub-ele-ref-index="${ixs.subEleIndex}" data-strategy="beforebegin"/></div></div>`);
        }

    }
    else if(clases.contains('insertar-debajo-sub')){
        e.stopPropagation();
        let ixs = RutinaIx.getIxsForSubElemento(input);
        let elemento = RutinaDOMQueries.getElementoByIxs(ixs);
        let subElemento = RutinaDOMQueries.getSubElementoByIxs(ixs);
        let ix = ++indexGlobal;
        subElemento.insertAdjacentHTML('afterend', `<div class="container-fluid padding-0 ne-esp rf-pre-sub-elemento" data-dia-index="${ixs.diaIndex}" data-ele-index="${ixs.eleIndex}" data-index="${ix}"><div class="col-md-12 padding-0"><input type="text" class="w-100 cs-input in-sub-ele-esp-pos" data-dia-index="${ixs.diaIndex}" data-ele-index="${ixs.eleIndex}" data-index="${ix}" data-strategy="afterend"/></div></div>`);
        let i=0,k=0;
        while((elemento = elemento.previousElementSibling))i++;
        while((subElemento = subElemento.previousElementSibling))k++;
        RutinaAdd.nuevoSubElemento(ixs.numSem, ixs.diaIndex, i, k, '');
        agregarSubElementoEnBlancoBD(ixs.numSem, ixs.diaIndex, ElementoTP.NO_DEFINIDO, i, (posRefSubEle = k), Estrategia.INSERT_DESPUES);
    }
    else if(clases.contains('pegar-mini')) {
        const diaIndex = input.getAttribute('data-index');
        DiaOpc.pegarMiniPlantillaDia(diaIndex);
    }
    else if(clases.contains('copiar-dia')){
        e.preventDefault();
        const ixs = RutinaIx.getIxsForDia(input);
        $diaPlantilla = $rutina.semanas[ixs.numSem].dias[ixs.diaIndex];
    }
    else if(clases.contains('pegar-mini-listas')) {
        const diaIndex = input.getAttribute('data-index');
        DiaOpc.pegarMiniPlantillaDiaListas(diaIndex);
        DiaOpc.pegarElementosSeleccionados(diaIndex);
    }
    else if(clases.contains('agregar-objetivo')) {
        
        const parent = input.parentElement.parentElement.parentElement.parentElement.nextElementSibling;
        if (parent.children.length < 2) {
            const diaIndex = input.getAttribute('data-index');
            const lastObjetivo = $rutina.semanas[Number($semActual.textContent) - 1].objetivos.split(",")[diaIndex];
            if($objetivos.length==0){
                obtenerObjetivosDiaBD().then(objs=>{
                    const objetivos = objs.map(v=>`<option value="${v.id}">${v.nombre}</option>`).join('');
                    parent.insertBefore(htmlStringToElement(RutinaSeccion.newDiaObjetivo(objetivos,diaIndex)), parent.children[0]);
                    parent.querySelector('.list-desp-objetivo').value = lastObjetivo;
                    $objetivos = objs;
                })
            }else{
                const objetivos = $objetivos.map(v=>`<option value="${v.id}">${v.nombre}</option>`).join('');
                parent.insertBefore(htmlStringToElement(RutinaSeccion.newDiaObjetivo(objetivos,diaIndex)), parent.children[0]);
                parent.querySelector('.list-desp-objetivo').value = lastObjetivo;
            }
        }else{
            input.classList.toggle('hidden');
            $(parent.children[0]).slideUp('slow', ()=>{
                parent.children[0].remove();
                input.classList.toggle('hidden');
            })
        }
    }
    else if(clases.contains('in-ele-dia-esp-pos')){
        if(validUUID($mediaAudio) || validUUID($mediaVideo)){
            alert("xd");
            const valor = $mediaNombre;
            let ixs = RutinaIx.getIxsForElemento(input);
            let tempElemento = RutinaDOMQueries.getPreElementoByIxs(ixs);
            let tipo = input.getAttribute('data-ele-tipo');
            let initTempElementoRef = tempElemento;
            let i = 0;
            while ((tempElemento = tempElemento.previousElementSibling) != null) i++;
            const nuevoIx = RutinaSeccion.newElementoPosEspecifica(ixs.diaIndex, tipo, valor, 'afterend', initTempElementoRef);
            ixs.eleIndex = nuevoIx;
            ElementoOpc.agregarMediaElemento(ixs, RutinaDOMQueries.getElementoByIxs(ixs).querySelector('.rf-dia-elemento-nombre'), tipo, (posEle = i));
            initTempElementoRef.remove();
        }
    }
    else if(clases.contains('in-sub-ele-esp-pos')){
        if(validUUID($mediaAudio) || validUUID($mediaVideo)){
            const valor = $mediaNombre;
            let ixs = RutinaIx.getIxsForSubElemento(input);
            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
            let tempSubEle = RutinaDOMQueries.getPreSubElementoByIxs(ixs);
            let initTempSubEleRef = tempSubEle;
            let i = 0, k=0;
            while ((tempElemento = tempElemento.previousElementSibling) != null) i++;
            while((tempSubEle = tempSubEle.previousElementSibling))k++;
            const nuevoIx = RutinaSeccion.newSubElementoPosEspecifica(ixs.diaIndex, ixs.eleIndex, validUUID($mediaAudio) ? TipoElemento.AUDIO : TipoElemento.VIDEO, valor, 'afterend', initTempSubEleRef);
            ixs.subEleIndex = nuevoIx;
            SubEleOpc.agregarMediaSubElemento(ixs, RutinaDOMQueries.getSubElementoByIxs(ixs).querySelector('.rf-sub-elemento-nombre'), i, k);
            initTempSubEleRef.remove();
        }
    }
    else if(clases.contains('varios-media')){
        e.preventDefault();
        e.stopPropagation();

        if($videosElegidos != undefined && typeof $videosElegidos == 'object' && $videosElegidos.length >0){
            const ixs = RutinaIx.getIxsForElemento(input);
            RutinaElementoHTML.adjuntarSubElementos(ixs, 1);
        }

        if($subEleElegidos != undefined && typeof $subEleElegidos == 'object' && $subEleElegidos.length >0){
            const ixs = RutinaIx.getIxsForElemento(input);
            RutinaElementoHTML.adjuntarSubElementos(ixs, 2);
        }
    }
    else if(clases.contains('enviar-cliente')){
        e.preventDefault();
        e.stopPropagation();

        $(".span-setting").click();

        checkCalendarAccesso();
        /*
        var cantidad = $(".enviar-cliente:checked").length;
        var valordiaseleccionado = input.getAttribute('data-id');
        var valormesseleccionado = input.getAttribute('data-mes');

        if(cantidad == 1 && input.checked){
            AgregarQuitarDiaSeleccionado(input.getAttribute('data-id'),input.getAttribute('data-mes'),input.checked);
        }else {
            if (input.checked) {

                var ultimodia = 0;

                $.each($(".enviar-cliente:checked"), function (i, item) {
                    if(valordiaseleccionado != parseInt(item.getAttribute("data-id"))) {
                        ultimodia = parseInt(item.getAttribute("data-id"));
                    }
                });

                if ((ultimodia + 1) == valordiaseleccionado) {
                    AgregarQuitarDiaSeleccionado(valordiaseleccionado,valormesseleccionado,true);
                }else{
                    input.checked = false;
                }

            } else {
                AgregarQuitarDiaSeleccionado(valordiaseleccionado ,valormesseleccionado ,input.checked);

                for (let i = 0; i < cantidad ; i++) {
                    var diatmp = 0;
                    var mestmp = 0;
                    diatmp = $("#cdia"+(parseInt(valordiaseleccionado)+(i+1))).attr('data-id');
                    mestmp = $("#cdia"+(parseInt(valordiaseleccionado)+(i+1))).attr('data-mes');
                    AgregarQuitarDiaSeleccionado(diatmp, mestmp ,false);
                    $("#cdia"+(diatmp)).prop("checked",false);
                }
            }
        }*/

    }
}

function principalesEventosFocusOutSemanario(e) {
    const clases = e.target.classList;
    let input = e.target;

    if(clases.contains('in-ele-dia-1')){
        e.preventDefault();
        const valor = input.value.trim();
        const ixs = RutinaIx.getIxsForElemento(input);
        const diaIndex = input.getAttribute('data-dia-index');
        if (valor.length > 1 && listaNoRepetida(ixs.diaIndex, valor)) {
            const nuevoIx = RutinaSeccion.newElementoSimple(ixs.diaIndex, ElementoTP.SIMPLE, valor);
            ixs.eleIndex = nuevoIx;
            $rutina.semanas[ixs.numSem].dias[ixs.diaIndex].elementos.push(new Elemento({nombre: valor}));
            agregarElementoBD(ixs.numSem, ixs.diaIndex, ElementoTP.SIMPLE);
            RutinaDOMQueries.getDiaByIx(ixs.diaIndex).querySelector('.inputs-init').classList.toggle('hidden');
            const nuevoEle = RutinaDOMQueries.getElementoByIxs(ixs);
            instanciarElementoTooltips(nuevoEle);
            instanciarElementoPopovers(nuevoEle);
        } else {
            if (e.target.value.length == 0) {
            } else {
                if (e.target.value.length > 1) {
                    $(`#msg-val-${diaIndex}`).text('No puede crear una lista con un nombre repetido');
                } else {
                    $(`#msg-val-${diaIndex}`).text('Se requiere mínimo 2 letras');
                }
                setTimeout(() => $(`#msg-val-${diaIndex}`).text(''), 3500);
            }
        }
        input.value = "";
    }
    else if(clases.contains('in-ele-dia-2')){
        e.preventDefault();
        const valor = input.value.trim();
        const ixs = RutinaIx.getIxsForElemento(input);
        const diaIndex = input.getAttribute('data-dia-index');
        if (valor.length > 1 && listaNoRepetida(ixs.diaIndex, valor)) {
            const nuevoIx = RutinaSeccion.newElementoLista(ixs.diaIndex, ElementoTP.COMPUESTO, valor);
            ixs.eleIndex = nuevoIx;
            $rutina.semanas[ixs.numSem].dias[ixs.diaIndex].elementos.push(new Elemento({nombre: valor}));
            agregarElementoBD(ixs.numSem, ixs.diaIndex, ElementoTP.COMPUESTO);
            RutinaDOMQueries.getDiaByIx(ixs.diaIndex).querySelector('.inputs-init').classList.toggle('hidden');
            const nuevoEle = RutinaDOMQueries.getElementoByIxs(ixs);
            instanciarElementoTooltips(nuevoEle);
            instanciarElementoPopovers(nuevoEle);
        } else {
            if (valor.length == 0) {
            } else {
                if (valor.length > 1) {
                    $(`#msg-val-${diaIndex}`).text('No puede crear una lista con un nombre repetido');
                } else {
                    $(`#msg-val-${diaIndex}`).text('Se requiere mínimo 2 letras');
                }
                setTimeout(() => $(`#msg-val-${diaIndex}`).text(''), 3500);
            }
        }
        input.value = "";
    }
    else if(clases.contains('in-ele-dia-esp-pos')){
        const valor = input.value.trim();
        if(valor.length > 1){
            let ixs = RutinaIx.getIxsForElemento(input);
            let tempElemento = RutinaDOMQueries.getPreElementoByIxs(ixs);
            let tipo = input.getAttribute('data-ele-tipo');
            let initTempElementoRef = tempElemento;
            let i = 0;
            while ((tempElemento = tempElemento.previousElementSibling) != null) i++;
            const nuevoIx = RutinaSeccion.newElementoPosEspecifica(ixs.diaIndex, tipo, valor, 'afterend', initTempElementoRef);
            ixs.eleIndex = nuevoIx;
            RutinaSet.setElementoNombre(ixs.numSem, ixs.diaIndex, (posEle = i), valor, tipo);
            actualizarElementoStrategyBD2(ixs.numSem, ixs.diaIndex, (posEle = i), tipo);
            initTempElementoRef.remove();
            const nueEle = RutinaDOMQueries.getElementoByIxs(ixs).querySelector('.panel-heading').children[0];
            instanciarElementoTooltips(nueEle);
            instanciarElementoPopovers(nueEle);
        }
    }
    else if(clases.contains('in-sub-elemento')) {
        
        const valor = input.value.trim();
        if (valor.length >= 1) {
            let ixs = RutinaIx.getIxsForSubElemento(input);
            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
            let initEle = tempElemento;
            let i = 0;
            while ((tempElemento = tempElemento.previousElementSibling) != null) i++;
            DiaOpc.validPreActualizarFromNomSubEle(initEle, valor, ixs, i, 0);//Siempre va ser el primero por eso se deja la posicion como 0
            input.value = '';
        } else {
            if (valor.length == 0) {
            } else {
                $.smallBox({color: "alert", content: "Debe ingresar más de 1 caracter..."})
            }
        }
    }
    else if(clases.contains('in-sub-ele-esp-pos')) {
        const valor = input.value.trim();
        if (valor.length >= 1) {
            let ixs = RutinaIx.getIxsForSubElemento(input);
            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
            let tempSubEle = RutinaDOMQueries.getPreSubElementoByIxs(ixs);
            let initTempSubEleRef = tempSubEle;
            let i = 0, k=0;
            while ((tempElemento = tempElemento.previousElementSibling) != null) i++;
            while((tempSubEle = tempSubEle.previousElementSibling))k++;

            const nuevoIx = RutinaSeccion.newSubElementoPosEspecifica(ixs.diaIndex, ixs.eleIndex, TipoElemento.TEXTO, valor, 'afterend', initTempSubEleRef);
            ixs.subEleIndex = nuevoIx;
            RutinaSet.setSubElementoNombre(ixs.numSem, ixs.diaIndex, i, k, valor);
            const subEle = RutinaDOMQueries.getSubElementoByIxs(ixs);
            instanciarSubElementoTooltip(subEle);
            instanciarSubElementoPopover(subEle);
            actualizarSubElementoNombreBD(valor, ixs.numSem, ixs.diaIndex, (posElemento = i), (postSubElemento = k));
            initTempSubEleRef.remove();
        }
    }
    else if(clases.contains('rf-dia-elemento-nombre')){
        const valor = input.textContent.trim();
        if($nombreActualizar != valor){
            const ixs = RutinaIx.getIxsForElemento(input);
            if(valor.length == 0){
                ElementoOpc.confirmarEliminarElemento(ixs.numSem, ixs.diaIndex, ixs.eleIndex);
            }else{
                //1. Buscamos la posicion de la lista y con ello cambiamos el nombre en el programa general
                let tempEle = RutinaDOMQueries.getElementoByIxs(ixs), i=0;
                while((tempEle = tempEle.previousElementSibling) != null) i++;
                RutinaSet.setElementoNombre(ixs.numSem, ixs.diaIndex, (posEle = i), valor);
                //Indices con respecto a la posicion en la que se encuentran con respecto a su contenedor padre y sus hermanos
                DiaOpc.validPreActualizarFromNomEle(valor, ixs, (posElemento = i));
            }
        }
        $nombreActualizar = valor;
    }
    else if(clases.contains('rf-sub-elemento-nombre')){
        const valor = input.textContent.trim();
        if($nombreActualizar != valor) {
            let ixs = RutinaIx.getIxsForSubElemento(input);
            if(valor.length == 0){
                SubEleOpc.eliminarSubElemento(ixs.numSem, ixs.diaIndex, ixs.eleIndex, ixs.subEleIndex);
            }else {
                let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
                let initEle = tempElemento;
                let i = 0, k = 0;
                //
                while ((tempElemento = tempElemento.previousElementSibling) != null) i++;
                let tempSubElemento = RutinaDOMQueries.getSubElementoByIxs(ixs);

                while ((tempSubElemento = tempSubElemento.previousElementSibling) != null) k++;
                RutinaSet.setSubElementoNombre(ixs.numSem, ixs.diaIndex, i, k, valor);
                DiaOpc.validPreActualizarFromNomSubEle2(initEle, valor, ixs, i, k);//Siempre va ser el primero por eso se deja la posicion como 0
            }
        }
    }
    else if(clases.contains('nueva-nota')){
        const nota = input.value.trim();
        const ixs = RutinaIx.getIxsForElemento(input);
        let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
        const divNota = e.target.parentElement.parentElement;

        let anteriorNota = tempElemento.querySelector(`.rf-dia-elemento-nombre`).getAttribute('data-content');anteriorNota = anteriorNota == undefined?'':anteriorNota;
        if(anteriorNota.trim() != nota){

            if(tempElemento.querySelector('i.check-nota')==undefined){
                tempElemento.querySelector('.panel-title').appendChild(htmlStringToElement(RutinaElementoHTML.iconoNotaT()));
            }else{
                if(nota.length == 0){
                    tempElemento.querySelector('i.check-nota').remove();
                    tempElemento.querySelector(`.rf-dia-elemento-nombre`).setAttribute('data-content', '');
                }
            }
            tempElemento.querySelector(`.rf-dia-elemento-nombre`).setAttribute('data-content', nota);
            let i=0;
            ElementoOpc.descomprimirDetalle(tempElemento);
            while((tempElemento = tempElemento.previousElementSibling) != null) i++;
            RutinaSet.setElementoNota(ixs.numSem, ixs.diaIndex, (posEle = i), nota);
            actualizarNotaElementoBD(ixs.numSem, ixs.diaIndex, (eleIndex = i));
            divNota.remove();
        }else{
            divNota.remove();
            ElementoOpc.descomprimirDetalle(tempElemento);
        }
    }
    else if(clases.contains('nueva-nota-sbe')){
        const nota = input.value.trim();
        const ixs = RutinaIx.getIxsForSubElemento(input);
        let tempSubEle = RutinaDOMQueries.getSubElementoByIxs(ixs);
        const divNota = e.target.parentElement.parentElement;

        let anteriorNota = tempSubEle.querySelector(`.rf-sub-elemento-nombre`).getAttribute('data-content');anteriorNota = anteriorNota == undefined?'':anteriorNota;
        if(anteriorNota.trim() != nota){

            if(tempSubEle.querySelector('i.check-nota')==undefined){
                tempSubEle.appendChild(htmlStringToElement(RutinaElementoHTML.iconoNotaT()));
            }else{
                if(nota.length == 0){
                    tempSubEle.querySelector('i.check-nota').remove();
                    tempSubEle.querySelector(`.rf-sub-elemento-nombre`).setAttribute('data-content', '');
                }
            }
            tempSubEle.querySelector(`.rf-sub-elemento-nombre`).setAttribute('data-content', nota);

            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
            let i = 0, k = 0;
            while ((tempElemento = tempElemento.previousElementSibling) != null) i++;

            while ((tempSubEle = tempSubEle.previousElementSibling) != null) k++;
            RutinaSet.setSubElementoNota(ixs.numSem, ixs.diaIndex, i, k, nota);
            actualizarSubElementoNotaBD(nota, ixs.numSem, ixs.diaIndex, (posElemento = i), (postSubElemento = k));
            divNota.remove();
        }else{
            divNota.remove();
        }
    }
    else if(clases.contains('agregar-tiempo')){
        //$tiempoActualizar: Sirve para comparar el valor inicial del elemento con el valor que retorna cuando se activa este evento(focusout) con el fin de evitar actualizaciones innecesarias
        const tiempo = Number(e.target.value.trim());
        let gIx = e.target.getAttribute('data-index');
        if($tiempoActualizar != tiempo && gIx == $gIndex && !isNaN(tiempo)){
            let ixs = RutinaIx.getIxsForElemento(input)
            ElementoOpc.actualizarTiempoElemento(ixs, tiempo);
        }
        $tiempoActualizar = tiempo;
    }
}


function principalEventoFocusIn(e){
    $nombreActualizar = e.target.textContent;
}

function principalesEventosTabRutina(e){

    const input = e.target;
    const clases = input.classList;

    if(clases.contains('copiar-full-semana')) {
        e.preventDefault();
        RutinaOpc.abrirCopiadorSemana(input);
    }
    else if(clases.contains('numero-semana')){
        e.preventDefault();
        const index = e.target.getAttribute('data-index');
        avanzarRetrocederSemana(Number(index));
        $semActual.textContent = 2+Number(index);
    }
    else if(clases.contains('retroceder-semana')){
        e.preventDefault();
        let numSem = Number($semActual.textContent);
        if(numSem != 1){
            const parentDiv = input.tagName == "I" ? input.parentElement.parentElement : input.parentElement;
            parentDiv.setAttribute('hidden','hidden');
            avanzarRetrocederSemana(numSem-2, 2, parentDiv);
        }else{
            $.smallBox({color: "alert", content: "<i>No existe semana anterior a la actual...<i>"})
        }
    }
    else if(clases.contains('adelantar-semana')){
        e.preventDefault();
        const numSem = $('#SemanaActual').text();
        if($rutina.totalSemanas == numSem){
            //Se agregará una semana nueva en BD
            $.smallBox({content: "<i class='fa fa-spinner fa-spin fa-2x fa-fw'></i> <i>Cargando...<i>", timeout: 10000});
            $rutina.agregarNuevaSemana();
            const promesa = new Promise((res,rej)=>{
                $.ajax({
                    type: 'POST',
                    contentType: "application/json",
                    url: _ctx + 'gestion/rutina/agregar/semana',
                    data: JSON.stringify($rutina.semanas[$rutina.semanas.length-1]),
                    dataType: "json",
                    success: function (data, textStatus) {
                        if (textStatus == "success") {
                            if (data == ResponseCode.EX_GENERIC) {
                                rej(ResponseCode.EX_GENERIC);
                            }else if(data == ResponseCode.EX_NULL_POINTER){
                                rej(ResponseCode.EX_NULL_POINTER);
                            } else {
                                res(numSem);
                                if($rutina.totalSemanas < 5)
                                    document.querySelector('#SemanasLineal').appendChild(htmlStringToElement(`<a href="javascript:void(0);"><span class="badge bg-color-darken font-md numero-semana" data-index="${numSem}">${Number(numSem)+1}</span></a>`))
                                else{
                                    document.querySelector('#SemanasLineal').innerHTML = `<a href="javascript:void(0);"><i id="CalendarioRf" rel="popover" data-toggle="popover" data-placement="right" data-html="true" data-content="" class="fa fa-calendar abrir-calendario" data-original-title="" title=""></i></a>`;
                                }
                            }
                        }
                    },
                    error: function (xhr) {
                        rej(xhr);
                        $rutina.semanas.splice($rutina.semanas.length-1, 1);
                        --$rutina.totalSemanas;
                        $('#SemanaActual').text(Number($('#SemanaActual').text())-1);
                    },
                    complete: function () {
                        document.querySelectorAll('#divSmallBoxes')[0].innerHTML = '';
                    }
                });
            })

            promesa.then((numSem)=>{
                $rutina.initEspecificoDesdeRutina(numSem);
            }).catch((ex)=>{
                exception(ex);
            })
        }else{
            //No es necesario crear la semana, pues no es una nueva semana
            const parentDiv = input.tagName == "I" ? input.parentElement.parentElement : input.parentElement;
            parentDiv.setAttribute('hidden','hidden');
            avanzarRetrocederSemana(numSem, 1, parentDiv);
        }
    }
    else if(clases.contains('abrir-calendario')){
        e.preventDefault();
        e.stopPropagation();
        const qFechaInicio = RutinaGet.getFechaInicioSemanaEdicion();
        RutinaOpc.abrirCalendario(RutinaGet.getCalendarioSemanaIxs(qFechaInicio.anio, qFechaInicio.mes), false, qFechaInicio.mes);
    }
    else if(clases.contains('mes-calendar')){
        const mes = input.getAttribute('data-mes');
        const anio = input.parentElement.getAttribute('data-anio');
        RutinaOpc.abrirCalendario(RutinaGet.getCalendarioSemanaIxs(anio, mes), true);
    }
    else if(clases.contains('cal-retroceder-sem')){
        const qFechaInicio = RutinaGet.getFechaInicioSemanaEspecifica($refIxsSemCalendar[0] - 1);
        RutinaOpc.abrirCalendario(RutinaGet.getCalendarioSemanaIxs(qFechaInicio.anio, qFechaInicio.mes), true, qFechaInicio.mes);
    }
    else if(clases.contains('cal-adelantar-sem')){
        const semIxRef = $refIxsSemCalendar[$refIxsSemCalendar.length-1];
        let qFecha;
        if($rutina.semanas[semIxRef+1] != undefined)
            qFecha = RutinaGet.getFechaInicioSemanaEspecifica(semIxRef + 1);
        else
            qFecha = RutinaGet.getFechaFinSemanaEspecifica(semIxRef);
        RutinaOpc.abrirCalendario(RutinaGet.getCalendarioSemanaIxs(qFecha.anio, qFecha.mes), true, qFecha.mes);
    }
    else if(clases.contains('fechas-calendar')){
        const semActual = Number(document.querySelector('#SemanaActual').textContent)-1;
        if(semActual != $refIxsSemCalendar[input.getAttribute('data-index')])
            avanzarRetrocederSemana($refIxsSemCalendar[input.getAttribute('data-index')], 2);
        else
            $.smallBox({color: "grey", content: "<i>El día que acaba de seleccionar pertenece a la semana<br/> que ya esta visualizando...</i>"});
        $('#CalendarioRf').popover('show');
    }
    else if(clases.contains('abrir-indicador-1')){
        const semIndex = Number(document.querySelector('#SemanaActual').textContent)-1;
        const metricas = $rutina.semanas[semIndex].metricas;
        Indicadores.abrirIndicador1(metricas);
    }
    else if(clases.contains('abrir-indicador-2')){
        const semIndex = Number(document.querySelector('#SemanaActual').textContent)-1;
        const metricas = $rutina.semanas[semIndex].metricasVelocidad;
        Indicadores.abrirIndicador2(metricas);
    }
}

function principalesEventosTabGrupoVideos(e){
    const input = e.target;
    const clases = input.classList;

    if(clases.contains('reprod-video')){
        e.preventDefault();
        $('#myModalVideo').modal('show');
        const route = input.getAttribute('data-media');
        $('#VideoReproduccion').get(0).src = `https://s3-us-west-2.amazonaws.com/rf-media-rutina/video${route}`;
        $("#VideoReproduccion").parent().get(0).load();
    }
    else if(clases.contains('elegir-video')){
        e.stopPropagation();
        const li = input;
        const eleVideo = li.querySelector('.reprod-video');
        const ix = eleVideo.getAttribute('data-index');
        if(!clases.contains('txt-color-greenIn')){
            clases.add('txt-color-greenIn');
            const media = eleVideo.getAttribute('data-media');
            const nombreMedia = li.textContent.trim();
            $videosElegidos.push([ix, nombreMedia, media]);
        }else{
            clases.remove('txt-color-greenIn');
            $videosElegidos = $videosElegidos.filter(e=>{return e[0]!=ix});
        }
    }
    else if(clases.contains('cat-video')){
        
        e.preventDefault();
        const clase = '' +
            '.cat-video'+ input.getAttribute('data-id');
        const div = document.querySelector(clase);
        $body.animate({scrollTop: $(clase).offset().top - 40, scrollLeft: 0}, 300);
        if(div != undefined){
            div.classList.toggle('rut-ce-separador');
            setTimeout(()=>{div.classList.toggle('rut-ce-separador');},4000)
            $body.animate({scrollTop: $(clase).offset().top - 40, scrollLeft: 0},300);
        }else{

        }
    }
    else if(clases.contains('ck-video')){
        e.preventDefault();
        e.stopPropagation();
        parent = input.parentElement;
        $memoriaVideo = input;
        $mediaVideo = parent.querySelector('.reprod-video').getAttribute('data-media');
        $mediaNombre = parent.textContent.trim();
        $mediaAudio = '';
        $tipoMedia = TipoElemento.VIDEO;
        cambiarATabRutina();
    }
    else if(clases.contains('ck-favorito-video')){
        e.preventDefault();
        e.stopPropagation();
        parent = input.parentElement;
        var idvideo = parent.querySelector('.ck-favorito-video').getAttribute('data-id');
        //console.log(id);
        var selected = $(parent.querySelector('.ck-favorito-video')).attr("data-selected");
        var editaragregar = 0;
        if(selected == "1"){
            $(parent.querySelector('.ck-favorito-video')).css("color","#3276b1");
            $(parent.querySelector('.ck-favorito-video')).attr("data-selected", "0");
            editaragregar = 0;
        }else {
            $(parent.querySelector('.ck-favorito-video')).attr("data-selected", "1");
            $(parent.querySelector('.ck-favorito-video')).css("color","#d8d807");
            editaragregar = 1;
        }
        agregarEliminarFavorito(idvideo,0,editaragregar);
    }
}

function principalesEventosTabGrupoAudios(e){
    const input = e.target;
    const clases = input.classList;

    if(clases.contains('reprod-audio')){
        e.preventDefault();
        if(clases.contains('fa-pause-circle')){
            document.querySelector('#someaud').pause();
            input.setAttribute('data-original-title','Reproducir');
        }else{
            const route = e.target.getAttribute('data-media');
            $('#AudioReproduccion').get(0).src = `${_ctx}workout/media/audio${route}`;
            $("#AudioReproduccion").parent().get(0).load();
            input.setAttribute('data-original-title','Pausar');
        }
        clases.toggle('fa-music');
        clases.toggle('fa-pause-circle');
    }
    else if(clases.contains('elegir-audio')){
        e.preventDefault();
        e.stopPropagation();
        $memoriaAudio != ''?$memoriaAudio.classList.remove('txt-color-greenIn'):'';
        clases.add('txt-color-greenIn');
        $memoriaAudio = input;
        $mediaNombre = input.textContent.trim();
        $mediaAudio = input.querySelector('.reprod-audio').getAttribute('data-media');
        $mediaVideo = '';
        $tipoMedia = TipoElemento.AUDIO;
        cambiarATabRutina();
    }
    else if(clases.contains('ck')){
        const li = input.parentElement;
        const eleAudio = li.querySelector('.reprod-audio');
        const ix = eleAudio.getAttribute('data-index');
        const media = eleAudio.getAttribute('data-media');
        const nombreMedia = li.textContent.trim();
        $audiosElegidos.push([ix, media, nombreMedia]);
    }
    else if(clases.contains('ck-favorito-audio')){
        e.preventDefault();
        e.stopPropagation();
        parent = input.parentElement;
        var idaudio = parent.querySelector('.ck-favorito-audio').getAttribute('data-id');
        //console.log(id);
        var selected = $(parent.querySelector('.ck-favorito-audio')).attr("data-selected");
        var editaragregar = 0;
        if(selected == "1"){
            $(parent.querySelector('.ck-favorito-audio')).css("color","#3276b1");
            $(parent.querySelector('.ck-favorito-audio')).attr("data-selected", "0");
            editaragregar = 0;
        }else {
            $(parent.querySelector('.ck-favorito-audio')).attr("data-selected", "1");
            $(parent.querySelector('.ck-favorito-audio')).css("color","#d8d807");
            editaragregar = 1;
        }
        agregarEliminarFavorito(0,idaudio,editaragregar);
    }

}

function agregarEliminarFavorito(idvideo,idaudio,selected) {
    let params ={};
    params.videoid = parseInt(idvideo);
    params.audioid = parseInt(idaudio);
    params.addedit = selected;

        $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina/elemento/adddeletefavorito",
        dataType: "json",
        data: params,
        success: function (data, textStatus) {
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    });
}

function principalesEventosTabFichaTecnica(e){
    const input = e.target;
    const clases = input.classList;

    if(clases.contains('refrescar-grafico')){
        e.preventDefault();
        e.stopPropagation();
        const base = FichaGet.obtenerBase();
        MCGrafico.temporada(MCGraficoData.paraTemporada(base));
        MacroCiclo.instanciarInformacionTemporada(base);
        actualizarPorcentajesKilometrajeBD(MacroCicloGet.obtenerPorcentajesParaActualizacion(base));
    }
}
function principalesEventosFocusOutTabFichaTecnica(e){
    const input = e.target;
    const clases = input.classList;

    if(clases.contains('periodizacion-calc')){
        CalcProyecciones.calcular(input, 1);
    }
    else if(clases.contains('velocidad-calc')){
        CalcProyecciones.calcular(input, 2);
    }
    else if(clases.contains('cadencia-calc')){
        CalcProyecciones.calcular(input, 3);
    }
    else if(clases.contains('tcs-calc')){
        CalcProyecciones.calcular(input, 4);
    }
}

function listaNoRepetida(ix, nombre) {
    let val = true;
    document.querySelectorAll(`.rf-dia[data-index="${ix}"] .panel-default .lista-title`).forEach((v) => {
        if (v.textContent.trim() === nombre) {
            val = false;
        }
    });
    return val;
}

function genericoRutinarioCe(e){
    e.preventDefault();
    //Útil para mover el scroll a una sub categoría específica
    if(e.target.classList.contains('sub-categoria')){
        const clase = '.sub-cat'+ e.target.getAttribute('data-id');
        const div = document.querySelector(clase);
        if(div != undefined){
            div.classList.toggle('rut-ce-separador');
            setTimeout(()=>{div.classList.toggle('rut-ce-separador');},4000)
            $body.animate({scrollTop: $(clase).offset().top - 40, scrollLeft: 0},300);
        }else {
            $.smallBox({color: "info", content: "<i>La sub categoría elegida aún no cuenta <br/> con especificaciones. Para mayor información <br/> comuníquese con el administrador.</i>"})
        }
    }
}

function focoARutina() {
    $('#scrollRutina').animate({
        scrollLeft: $('.jarviswidget[tabindex="0"]').parent().offset().left - 40
    }, 500);
}

function moverAFinal(){
    $('#scrollRutina').animate({
        scrollLeft: $('.jarviswidget[tabindex]').last().parent().offset().left
    }, 500);
}

function moverAInicio(){
    $('#scrollRutina').animate({
        scrollLeft: $('.jarviswidget[tabindex]').parent().offset().left
    }, 500);
}

function agregarElementoBD(numSem, diaIndex, tipoElemento){
    const listaLenght = $rutina.semanas[numSem].dias[diaIndex].elementos.length -1;
    let params = $rutina.semanas[numSem].dias[diaIndex].elementos[listaLenght];
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.tipo = tipoElemento;
    tipoElemento==2?params.subElementos = []:'';//Importante para la actualizacion de los subElementos jsonb
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/elemento/agregar",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarElementoStrategyBD2(numSem, diaIndex, eleIndex, tipoElemento){
    let params = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex];

    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = eleIndex;
    params.tipo = tipoElemento;

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/elemento/actualizar/2",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarElementoStrategyBD(numSem, diaIndex, eleIndex, tipoMedia, tipoElemento){
    let params = {};
    params.nombre = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].nombre;
    if(tipoMedia == TipoElemento.AUDIO){
        params.mediaAudio = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].mediaAudio;
    }else{//VIDEO
        params.mediaVideo = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].mediaVideo;
    }
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = eleIndex;
    params.tipo = tipoElemento;
    params.tipoMedia = tipoMedia;

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/elemento/media/agregar",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarSubElementoStrategyBD(numSem, diaIndex, eleIndex, subEleIndex, tipoMedia){
    let params = {};
    params.nombre = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].subElementos[subEleIndex].nombre;
    if(tipoMedia == TipoElemento.AUDIO){
        params.mediaAudio = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].subElementos[subEleIndex].mediaAudio;
    }else{//VIDEO
        params.mediaVideo = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].subElementos[subEleIndex].mediaVideo;
    }
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = eleIndex;
    params.subElementoIndice = subEleIndex;
    params.tipo = tipoMedia;
    params.tipoMedia = tipoMedia;

    $.ajax({
        type: "PUT",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina/sub-elemento/media/actualizar",
        dataType: "json",
        data: params,
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function agregarElementoEnBlancoBD(numSem, diaIndex, tipoElemento, posRefElemento, strategy){
    const listaLenght = (strategy==Estrategia.INSERT_DESPUES?posRefElemento+1:posRefElemento);
    let params = $rutina.semanas[numSem].dias[diaIndex].elementos[listaLenght];
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.tipo = tipoElemento;
    params.refElementoIndice = posRefElemento;
    params.insertarDespues = strategy==Estrategia.INSERT_DESPUES?true:false;
    tipoElemento==2?params.subElementos = []:'';//Importante para la actualizacion de los subElementos jsonb
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/elemento/agregar/pos-especifica",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function agregarSubElementoEnBlancoBD(numSem, diaIndex, tipoSubEle, eleIndex, posRefSubEle, strategy){
    const listaLenght = (strategy==Estrategia.INSERT_DESPUES?posRefSubEle+1:posRefSubEle);
    let params = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].subElementos[listaLenght];
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = eleIndex;
    params.tipo = tipoSubEle;
    params.refSubElementoIndice = posRefSubEle;
    params.insertarDespues = strategy==Estrategia.INSERT_DESPUES?true:false;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/sub-elemento/agregar/pos-especifica",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function removerElementoBD(numSem, diaIndex, elementoIndex, minutos, distancia, calorias){
    let params = {}
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = elementoIndex;
    params.minutos = minutos;
    params.distancia = distancia;
    params.calorias = calorias;

    $.ajax({
        type: "PUT",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina/elemento/eliminar",
        dataType: "json",
        data: params,
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function removerSubElementoBD(numSem, diaIndex, eleIndex, subEleIndex, distancia, calorias){;
    let params = {}
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = eleIndex;
    params.subElementoIndice = subEleIndex;
    params.distancia = distancia;
    params.calorias = calorias;
    $.ajax({
        type: "PUT",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina/sub-elemento/eliminar",
        dataType: "json",
        data: params,
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarDiaCompletoBD(numSem, diaIndex){
    let params = RutinaGet.dia(numSem, diaIndex);
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/dia/from-plantilla/actualizar/full",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarDiaParcialBD(numSem, diaIndex, cantUltimos){
    let refDia = RutinaGet.dia(numSem, diaIndex);
    let params = {};
    params.calorias = refDia.calorias;
    params.minutos = refDia.minutos;
    params.distancia = refDia.distancia;
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementos = refDia.elementos.filter((e,i,k)=>{return i>=k.length-cantUltimos});
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/dia/from-plantilla/actualizar",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarElementoParcialBD(numSem, diaIndex, eleIndex, cantUltimos){
    let refEle = RutinaGet.elemento(numSem, diaIndex, eleIndex);
    let params = {};
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = eleIndex;
    params.subElementos = refEle.subElementos.filter((e,i,k)=>{return i>=k.length-cantUltimos});

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/sub-elemento/multiple/agregar",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function agregarSubElementoAElementoBD(numSem, diaIndex, listaIndex , elementoIndex){
    let params = $rutina.semanas[numSem].dias[diaIndex].elementos[listaIndex].subElementos[elementoIndex];
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = listaIndex;
    params.subElementoIndice = elementoIndex;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/sub-elemento/agregar",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarElementoNombreBD(numSem, diaIndex, eleIndex) {
    let params = {};
    params.nombre = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].nombre;
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = eleIndex;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/elemento/actualizar",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarTiempoElementoBD(numSem, diaIndex, elementoIndice, totalMin) {
    let params = {};
    params.minutos = $rutina.semanas[numSem].dias[diaIndex].elementos[elementoIndice].minutos
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = elementoIndice;
    params.minutosDia = totalMin;
    $.ajax({
        type: "PUT",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina/elemento/tiempo/actualizar",
        dataType: "json",
        data: params,
        success: function (data) {
            const resWithErrors = getResponseCodeWithErrors(data);
            resWithErrors != false ? notificacionesRutinaSegunResponseCode(resWithErrors.code, RutinaParsers.obtenerErroresValidacion(resWithErrors.errors)) : notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarDiaBD(numSem, diaIndex, elementoIndice, totalKms, calorias) {
    const ele = $rutina.semanas[numSem].dias[diaIndex].elementos[elementoIndice];
    let params = {};
    params.nombre = ele.nombre;
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = elementoIndice;
    params.distancia = ele.distancia;
    params.distanciaDia = totalKms;
    params.calorias = calorias;

    $.ajax({
        type: "PUT",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina/dia/actualizar",
        dataType: "json",
        data: params,
        success: function (data) {
                const resWithErrors = getResponseCodeWithErrors(data);
                resWithErrors != false ? notificacionesRutinaSegunResponseCode(resWithErrors.code, RutinaParsers.obtenerErroresValidacion(resWithErrors.errors)) : notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarDiaBD2(numSem, diaIndex, elementoIndice, totalKms, calorias, totalMinutos) {
    const ele = $rutina.semanas[numSem].dias[diaIndex].elementos[elementoIndice];
    let params = {};
    params.nombre = ele.nombre;
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = elementoIndice;
    params.distancia = ele.distancia;
    params.distanciaDia = totalKms;
    params.calorias = calorias;
    params.minutos = ele.minutos;
    params.minutosDia = totalMinutos;
    $.ajax({
        type: "PUT",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina/dia/actualizar2",
        dataType: "json",
        data: params,
        success: function (data) {
            const resWithErrors = getResponseCodeWithErrors(data);
            resWithErrors != false ? notificacionesRutinaSegunResponseCode(resWithErrors.code, RutinaParsers.obtenerErroresValidacion(resWithErrors.errors)) : notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}


function actualizarDiaBD3(numSem, diaIndex, elementoIndice, subEleIndice, kms, totalKms, calorias) {
    const ele = $rutina.semanas[numSem].dias[diaIndex].elementos[elementoIndice].subElementos[subEleIndice];
    let params = {};
    params.nombre = ele.nombre;
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = elementoIndice;
    params.subElementoIndice = subEleIndice;
    params.distancia = kms;
    params.distanciaDia = totalKms;
    params.calorias = calorias;
    params.tipo = ele.tipo;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/dia/actualizar3",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarNotaElementoBD(numSem, diaIndex, elementoIndice) {
    let params = {};
    params.nota = $rutina.semanas[numSem].dias[diaIndex].elementos[elementoIndice].nota;
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = elementoIndice;
    $.ajax({
        type: "PUT",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina/elemento/nota/actualizar",
        dataType: "json",
        data: params,
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function eliminarMediaElementoBD(numSem, diaIndex, elementoIndice, tipoMedia){
    let params = {};
    if(tipoMedia == TipoElemento.AUDIO){
        params.mediaAudio = $rutina.semanas[numSem].dias[diaIndex].elementos[elementoIndice].mediaAudio;
    }else{//VIDEO
        params.mediaVideo = $rutina.semanas[numSem].dias[diaIndex].elementos[elementoIndice].mediaVideo;
    }
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = elementoIndice;
    params.tipoMedia = tipoMedia;

    $.ajax({
        type: "PUT",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina/elemento/media/eliminar",
        dataType: "json",
        data: params,
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarMediaElementoBD(numSem, diaIndex, elementoIndice, tipoMedia, nombre){
    let params = {};
    if(tipoMedia == TipoElemento.AUDIO){
        params.mediaAudio = $rutina.semanas[numSem].dias[diaIndex].elementos[elementoIndice].mediaAudio;
    }else{//VIDEO
        params.mediaVideo = $rutina.semanas[numSem].dias[diaIndex].elementos[elementoIndice].mediaVideo;
    }

    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = elementoIndice;
    params.tipoMedia = tipoMedia;
    params.nombre = nombre;

    $.ajax({
        type: "PUT",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina/elemento/media/actualizar",
        dataType: "json",
        data: params,
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function eliminarMediaSubElementoBD(numSem, diaIndex, elementoIndice, subEleIndice, tipoMedia){
    let params = $rutina.semanas[numSem].dias[diaIndex].elementos[elementoIndice].subElementos[subEleIndice];
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = elementoIndice;
    params.subElementoIndice = subEleIndice;
    params.tipoMedia = tipoMedia;
    params.tipo = 3;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/sub-elemento/media/eliminar",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
            instanciarTooltips();
        }
    })
}

function actualizarMediaSubElementoBD2(numSem, diaIndex, elementoIndice, subEleIndice, tipoMedia){
    let params = $rutina.semanas[numSem].dias[diaIndex].elementos[elementoIndice].subElementos[subEleIndice];
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = elementoIndice;
    params.subElementoIndice = subEleIndice;
    params.tipo = 3;
    params.tipoMedia = tipoMedia;
    $.ajax({
        type: "PUT",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina/sub-elemento/media/actualizar",
        dataType: "json",
        data: params,
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarSubElementoNombreBD(nuevoNombre, numSem, diaIndex, posElemento, postSubElemento) {
    let params = {};
    params.nombre = nuevoNombre;
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = posElemento;
    params.subElementoIndice = postSubElemento;
    console.log("papu", params);
    $.ajax({
        type: "PUT",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        url: _ctx + "gestion/rutina/sub-elemento/actualizar",
        dataType: "json",
        data: params,
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarSubElementoNotaBD(nota, numSem, diaIndex, posElemento, postSubElemento) {
    let params = {};
    params.nota = nota;
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = posElemento;
    params.subElementoIndice = postSubElemento;

    $.ajax({
        type: "PUT",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        url: _ctx + "gestion/rutina/sub-elemento/nota/actualizar",
        dataType: "json",
        data: params,
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
        }
    })
}

function modificarDiaFlagDescanso(numSem, diaIndex, flagDescanso){
    let params = {};
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.flagDescanso = flagDescanso;

    $.ajax({
        type: 'PUT',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        url: _ctx + 'gestion/rutina/dia/actualizar/flag-descanso',
        data: params,
        dataType: 'json',
        success: function(data){
            notificacionesRutinaSegunResponseCode(data);
        }, error: function(xhr){
            exception(xhr);
        }, complete: function(){}
    })
}

function guardarEstilosElementoBD(numSem, diaIndex, eleIndex){
    let params = {};
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.elementoIndice = eleIndex;
    params.estilos = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].estilos;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/elemento/estilos/actualizar",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function cambiarATabRutina(){
    document.querySelector('a[href="#tabRutina"]').click();
}

function principalesAlCambiarTab(e){
    const input = e.target;
    if(e.target.classList.contains('main-tab')){
        document.querySelector('#OpsAdic').classList.remove('hidden');
        document.querySelector('#DivEditor').classList.remove('hidden');
    }
    else if(input.nodeName == "A" && input.getAttribute('href') == '#tabGrupoVideos') {
        e.preventDefault();
        document.querySelector('#OpsAdic').classList.add('hidden');
        document.querySelector('#DivEditor').classList.add('hidden');
        $videosElegidos = [];
        $subEleElegidos = [];
        Array.from(document.getElementById('ArbolGrupoVideoDetalle').querySelectorAll('.txt-color-greenIn')).forEach(e => e.classList.remove('txt-color-greenIn'));
        if (document.querySelector('#ArbolGrupoVideo').children.length == 0) {
            instanciarGrupoVideos();
        }
    }
    else if(input.nodeName == "A" && input.getAttribute('href') == '#tabRutinarioCe') {
        document.querySelector('#DivEditor').classList.add('hidden');
        if(document.querySelector('#ArbolRutinario').children.length == 0) {
            instanciarMiniPlantillas();
        }
    }
    else if(input.nodeName == "A" && input.getAttribute('href') == '#tabGrupoAudios') {
        e.preventDefault();
        document.querySelector('#OpsAdic').classList.add('hidden');
        document.querySelector('#DivEditor').classList.add('hidden');
        if (document.querySelector('#ArbolGrupoAudio').children.length == 0) {
            instanciarGrupoAudios();
        }
    }
    else if(e.target.tagName === "A"){
        document.querySelector('#OpsAdic').classList.add('hidden');
        document.querySelector('#DivEditor').classList.add('hidden');
        if(input.getAttribute('href') == '#tabFichaTecnica'){
            if($ruConsolidado == undefined){
                obtenerRutinaConsolidadoBD();
            }
        }
    }
}

function principalesDivEditor(e){
    const input = e.target;
    const clases = input.classList;
    const ix = e.target.getAttribute('data-index');
    

    if(clases.contains('btn-bold')){
        if($eleGenerico.classList.contains('rf-dia-elemento-nombre')){
            RutinaEditor.agregarOeliminarEstiloToElemento(ix, 0);
        }else{

        }
    }else if(clases.contains('btn-italic')){
        if($eleGenerico.classList.contains('rf-dia-elemento-nombre')){
            RutinaEditor.agregarOeliminarEstiloToElemento(ix, 0);
        }else{

        }
    }else if(clases.contains('btn-underline')){
        if($eleGenerico.classList.contains('rf-dia-elemento-nombre')){
            RutinaEditor.agregarOeliminarEstiloToElemento(ix, 0);
        }else{

        }
    }
    else if(clases.contains('note-btn-copy-format')){
        if($eleGenerico.classList.contains('rf-dia-elemento-nombre')){
            RutinaEditor.copiarFormato();
        }else{}
    }
    else if(clases.contains('note-color-fuente')){
        if($eleGenerico.classList.contains('rf-dia-elemento-nombre')){
            RutinaEditor.agregarOeliminarEstiloToElemento(ix, 1);
        }else{}
    }else if(clases.contains('note-bg-color')){
        if($eleGenerico.classList.contains('rf-dia-elemento-nombre')){
            RutinaEditor.agregarOeliminarEstiloToElemento(ix, 2);
        }else{}
    }else if(clases.contains('note-alineacion')){
        if($eleGenerico.classList.contains('rf-dia-elemento-nombre')){
            RutinaEditor.agregarOeliminarEstiloToElemento(ix, 3);
        }else{}
    }else if(clases.contains('note-btn-font')){
        e.preventDefault();
        e.stopPropagation();
        RutinaEditor.instanciarPaletaColores(input);
    }
    else if(clases.contains('note-btn-alinea')){
        e.preventDefault();
        e.stopPropagation();
        RutinaEditor.instanciarGrupoAlineacion(input);
    }
    else if(clases.contains('note-btn-margen')){
        e.preventDefault();
        e.stopPropagation();
        RutinaEditor.agregarOeliminarEstiloToElemento(ix, 4);
    }
    else if(clases.contains('aumentar-zoom')){
        e.stopPropagation();
        let zm = window.parent.document.body.style.zoom;
        window.parent.document.body.style.zoom = zm == "" ? 1.1 : zm == "1.2" ? 1.2 : Number(zm) + 0.1;
        if(zm == "1.1") {
            input.classList.add('disabled');
        }else{
            input.parentElement.querySelector('.reducir-zoom').classList.remove('disabled');
            if(zm != "1.2") input.classList.remove('disabled');
        }
    }
    else if(clases.contains('reducir-zoom')){
        e.stopPropagation();
        let zm = window.parent.document.body.style.zoom;
        window.parent.document.body.style.zoom = zm == "" ? 0.9 : zm == "0.8" ? 0.8 : Number(zm) - 0.1;
        if(zm == "0.9") {
            input.classList.add('disabled');
        }else{
            input.parentElement.querySelector('.aumentar-zoom').classList.remove('disabled');
            if(zm != "0.8") input.classList.remove('disabled');
        }
    }
}

function updateAudioFavoritos() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/elemento/obtenermisfavoritos",
        dataType: "json",
        blockLoading: false,
        noOne: true,
        success: function (data) {
            if(data != null){
                let listaAudios = [];
                let listaVideos = [];
                $.each(data,function (i,item) {
                    if(item.audio != null){
                        listaAudios.push(item.audio);
                    }else{
                        listaVideos.push(item.video);
                    }
                });

                $.each(listaAudios,function (i,item) {
                    $("#liaudio"+item.id).attr("data-selected", "1");
                    $("#liaudio"+item.id).css("color","#d8d807");
                });

                $.each(listaVideos,function (i,item) {
                    $("#livideo"+item.id).attr("data-selected", "1");
                    $("#livideo"+item.id).css("color","#d8d807");
                });

            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
        }
    })
}

async function obtenerObjetivosDiaBD() {
    return new Promise((res, rej)=>{
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: _ctx + "gestion/objetivo/obtenerListado/0/true",
            dataType: "json",
            success: function (data) {
                if(data != null){
                    res(data);
                }
            },
            error: function (xhr) {
                rej("fail");
                exception(xhr);
            },
            complete: function () {
            }
        })
    })
}

function actualizarMetricasVelocidadBD(e){
    if(!e.target.parentElement.hasAttribute("disabled")){
        //FALTA ACTUALIZAR LAS VELOCIDADES POR CADA SEMANA DE RUTINA Y FALTA VER EL FLUJO ALTERNO CUANDO LAS METRICAS SON AGRUPADAS POR MESES
        const contBase = document.querySelector('#MetricasDetalladas .detallados-velocidades');
        const sliders = contBase.querySelectorAll('.slider-handle.round:not(.hide)');
        const countVal = Array.from(sliders).reduce((a,b)=> a+= b.style.left == "50%" ? 1 : 0, 0);
        if(countVal < sliders.length) {
            e.target.parentElement.setAttribute("disabled", "disabled");
            const id = getParamFromURL('key');
            const rn = getParamFromURL('rn');
            const nVelsArr = [{dist: "200 m", ind: []}, {dist: "400 m", ind: []}, {dist: "800 m", ind: []}, {dist: "1 KM", ind: []}, {dist: "10 KM", ind: []}, {dist: "15 KM", ind: []}, {dist: "21 KM", ind: []}, {dist: "42 KM", ind: []}];
            Array.from(contBase.firstElementChild.querySelectorAll('.col-md-11')).slice(0,-1).forEach(v=>{v.querySelectorAll('.col-md-3').forEach((v,i)=>{ nVelsArr[i].ind.push(v.textContent.trim()); }) })
            $.ajax({
                type: "PUT",
                contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                url: _ctx + "gestion/rutina/metricas/velocidad/actualizar?key="+id + "&rn="+rn,
                data: {mVz: JSON.stringify(nVelsArr)},
                dataType: "json",
                success: function (data) {
                    if(data == "-2"){
                        const porcMejora = parseNumberToDecimal(nVelsArr.map((v,i)=>{
                            return Number(parseNumberToDecimal((((((nVelsArr[i].ind[0].toSeconds())*$ruConsolidado.general.distancia)/((nVelsArr[i].ind[(nVelsArr[i].ind.length)-1].toSeconds())*$ruConsolidado.general.distancia)))-1)*100,1))
                        }).reduce((a,b)=>a+b, 1)/nVelsArr.length,1);
                        document.querySelector('#PorcMejoraVel').textContent = porcMejora + " %";
                        $ruConsolidado.matrizMejoraVelocidades = JSON.stringify(nVelsArr);
                        contBase.querySelectorAll('.slider-handle.round').forEach(v=>{v.style.left = "50%";});
                        contBase.querySelectorAll('.slider-track .slider-selection').forEach(v=>{v.style.left = "50%";v.style.width = "50%";});
                        $.smallBox({content: '<i>Las métricas de velocidades han sido actualizadas satisfactoriamente...</i>'});
                    }else{
                        notificacionesRutinaSegunResponseCode(data);
                    }
                },
                error: function (xhr) {
                    exception(xhr);
                },
                complete: function () {
                    setTimeout(()=>e.target.parentElement.removeAttribute("disabled"), 1000);
                }
            })
        }else
            $.smallBox({color: 'info', content: '<i>Las métricas no han sido modificadas no es necasaria su actualización...</i>'});
    }
}

function actualizarDiaObjetivoBD(a, b){
    const numSem = Number($semActual.textContent) -1;
    const output = $rutina.semanas[numSem].objetivos.split(",");
    output[a] = b;
    $rutina.semanas[numSem].objetivos = output.toString();
    $.ajax({
        type: "PUT",
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        url: _ctx + "gestion/rutina/objetivo/dia/actualizar",
        data: {objetivos: output.toString(), numSem: numSem},
        dataType: "json",
        success: function (data) {
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function obtenerRutinaConsolidadoBD(){
    const id = getParamFromURL('key');
    const rn = getParamFromURL('rn');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: _ctx + "rutina/obtenerConsolidado?key="+id + "&rn="+rn,
        blockLoading: true,
        noOne: false,
        dataType: "json",
        success: function (d) {
            notificacionesRutinaSegunResponseCode(d.responseCode);
            if(d.data !== null){
                FichaSet.instanciarConsolidado(d.data);
            } else {
                $.smallBox({color: "alert", content: "Este tipo de rutina no tiene ficha técnica"});
                document.querySelector('a[href="#tabRutina"]').click();
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {

        }
    })
}


function obtenerSemanasEnviadas() {

    $.ajax({
        type: 'GET',
        url: _ctx + 'gestion/rutina/get/obtenerSemanasPorRutina',
        dataType: "json",
        success: function (data, textStatus) {
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                } else {
                    $.each(data,function (i,item) {
                        item.fechaInicio = parseFromStringToDate2(item.fechaInicio);
                        item.fechaFin = parseFromStringToDate2(item.fechaFin);
                        $.each(item.lstDia,function(o,day){
                            day.fecha = parseFromStringToDate2(day.fecha);
                            $diasSeleccionados.push(day.fecha);
                        });
                        $semanasEnviadas.push(item);
                    });
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
        }
    });
}

function cambiarEstadoBD(flag, e){
    if(!e.classList.contains('disabled')){
        const id = getParamFromURL('key');
        const rn = getParamFromURL('rn');
        $.ajax({
            type: "PUT",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            url: _ctx + "rutina/actualizarEstado/"+flag+"?key="+id + "&rn="+rn,
            dataType: "json",
            success: function (d) {
                notificacionesRutinaSegunResponseCode(d);
                if(d == "-2") {
                    if (flag == "1") {
                        e.classList.add('disabled');
                        e.parentElement.parentElement.querySelector('.fa-calendar-minus-o').classList.remove('disabled');
                        $rutina.flagActivo = true;
                    } else {
                        e.classList.add('disabled');
                        e.parentElement.previousElementSibling.querySelector('.fa-calendar-plus-o').classList.remove('disabled');
                        $rutina.flagActivo = false;
                    }
                }
                $.smallBox({content: "<i>Actualización exitosa...</i>"});
            },
            error: function (xhr) {
                exception(xhr);
            },
            complete: function () {}
        })
    }
}

async function verificarMaxRole(){
    return new Promise((res, rej)=>{
        $.ajax({
            type: "GET",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            url: _ctx + "gestion/usuario/verificar/max-role",
            dataType: "json",
            success: function (d) {
                res(d);
            },
            error: function (xhr) {
                exception(xhr);
            },
            complete: function () {}
        })
    })
}

function reconstruirCategoriasMisRutinasDg(){
    $.ajax({
        type: "GET",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/categoria/listarTodos",
        dataType: "json",
        success: function (res) {
            const cats = res;
            document.querySelector('#CatMisRutinas').innerHTML = cats.map(c => `
                    <div class="col col-md-3">
                        <h1 class="padding-bottom-10 cat-mi-rutina">${c.nombre}</h1>
                        <a href="javascript:void(0);"><img src="${_ctx}workout/media/image/categoria/gt/0${c.rutaWeb}" height="150" width="100%" data-index="${c.id}" class="img-cat-rutina"/></a>
                    </div>`
            ).join('');
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}
