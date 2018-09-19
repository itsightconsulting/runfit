//Variables requeridas
let indexGlobal = 0;
let $body = $("html,body");
let $programa;
let $inFocus;
let $miniPlantillaRaw = '';
let $listaPlantilla = {};
let $seleccionados = [];
let $yelmo = '';
let $nombreActualizar = '';
let elementoEliminar;
let $memoriaAudio = '';
let $mediaAudio = '';

//Contenedores y constantes
const $rutina = document.querySelector('#RutinaSemana');
const coincidenciasMedia = document.querySelector('#CoincidenciasMedia');
const rutinarioCe = document.querySelector('#tabRutinarioCe');
const cboSubCategoriaId = document.querySelector('#SubCategoriaId');
const btnGuardarMini = document.querySelector('#btnGuardarMini');
const btnCopiarMini = document.querySelector('#btnCopiarMini');
const btnMiniPlantilla = document.querySelector('#btnMiniPlantilla');
const tabRutina = document.querySelector('#myTabRutina');
const semanas = document.querySelector('#Semanas');
const nombreListaMini = document.querySelector('#NombreListaMini');
const btnGuardar = document.querySelector('#btnGuardar');
const tabGrupoAudios = document.querySelector('#tabGrupoAudios');
const tabsButtons = document.querySelector('#myTab1');

$(function () {
    promptInit();
})


function promptInit(validacion, status){
    let hoy = new Date();
    let mes = Number(hoy.getMonth()+1)<10?"0"+Number(hoy.getMonth()+1):Number(hoy.getMonth()+1);
    let dia = hoy.getDate()<10?"0"+hoy.getDate():hoy.getDate();
    let fechaString = hoy.getFullYear() + '-' + mes + '-' + dia;
    $.SmartMessageBox({
        title: "<i class='fa fa-bullhorn'></i> Workout Notification",
        content: "" +
        "<br/><h6 style=\"border: 2px solid"+validacion+"\">Debe seleccionar un plan de entrenamiento:</h6><br/>" +
        "<div class='row txt-color-blueDark prompt-init'><div class='smart-form'><fieldset><section class='col col-2'></section><section class='col col-8'><div class='col col-6'><label class='label'><b>Plan:</b></label><label class=\"input "+status+"\"><select class='prompt-meses' style='width: 100% !important;'><option value=''> --Seleccione-- </option><option selected='selected' value='1'> 1 Mes </option><option value='3'> 3 Mes </option><option value='6'> 6 Mes </option><option value='12'> 12 Mes </option></select></label></div><div class='col col-6'><label class='label'><b>Fecha Inicial:</b></label><label class='input'><i style='color: #67d1ff !important;' class=\"icon-append fa fa-calendar\" for='PromptFecha'></i><input id='PromptFecha' class='prompt-fecha' type='date' name='Demo' min=\""+fechaString+"\" value=\"\"></label></div></section><section class='col col-2'></section></fieldset></div></div>",
        buttons: '[Generar]'
    }, function (ButtonPressed) {
        if (ButtonPressed === "Generar") {
            const prompt = document.querySelectorAll('.prompt-init')[document.querySelectorAll('.prompt-init').length-1];
            let meses = prompt.querySelectorAll('.prompt-meses')[prompt.querySelectorAll('.prompt-meses').length-1];
            meses = meses.options[meses.selectedIndex].value;
            let fechaInit = prompt.querySelectorAll('.prompt-fecha')[prompt.querySelectorAll('.prompt-fecha').length-1].value;
            let parseFechaInit = parseFromStringToDate(fechaInit);
            //Importante setear las horas a 0 pues en caso contrario al momento de restar FechaInicio y Fin podria haber un diferencia de +-1 con el resultado original
            parseFechaInit.setHours(0,0,0,0)
            if(meses.trim().length == 0){
                promptInit("#c0de9a", "state-error");
            }else{
                //Importante mantener el orden para el correcto funcionamiento
                const programa = new ProgramaEntrenamiento(parseFechaInit, meses);
                $programa = programa.init();
                guardarRutina();//Guardamos la rutina plantilla iniciada
                invocarRutinarioCe();
                tabRutina.addEventListener('click', principalesEventosTabRutina)
                $rutina.addEventListener('click', principalesEventosRutina);
                $rutina.addEventListener('paste', pegandoItemsMedia);
                $rutina.addEventListener('focusout', agregarElementos);
                nombreListaMini.addEventListener('focusout', agregarListaMini);
                coincidenciasMedia.addEventListener('click', seleccionandoItemMedia);
                rutinarioCe.addEventListener('click', genericoRutinarioCe);
                cboSubCategoriaId.addEventListener('change', cargarListaSubCategoriaEjercicio);
                btnGuardarMini.addEventListener('click', guardarMiniPlantilla);
                btnCopiarMini.addEventListener('click', copiarMiniPlantilla);
                btnMiniPlantilla.addEventListener('click', guardarMiniPlantillaPrincipal);
                btnGuardar.addEventListener('click', guardarRutina);
                tabGrupoAudios.addEventListener('click', principalesEventosTabGrupoAudios);
                tabsButtons.addEventListener('click', principalesAlCambiarTab);
                semanas.addEventListener('click', semanasPopOver);
                window.addEventListener('scroll', scrollGlobal);//Scroll event para regresar al techo del container
                instanciarMarcoEditor();
                instanciaMediaBD();
                instanciaPerfectScrollBar();
                instanciandoToolTips();
                modificandoSemanasPopOver();
                instanciandoListasDragAndDrop();
                modalEventos();
            }
        }
    })

    setFechaActual(document.querySelectorAll('input[type="date"]'));
    /*$('#PromptFecha').val('2018-07-31');//Temp
    setTimeout(()=>{
        $('#bot1-Msg1').click();
    }, 100);*/
}
function instanciandoListasDragAndDrop(){
    $('.nestable').nestable({group: 1});
}
function modalEventos(){

    $('#myModalMini').on('hidden.bs.modal', ()=> {
        $('#MiniPlantilla').html('');
        nombreListaMini.value = '';
        nombreListaMini.parentElement.classList.remove('hidden');
    })

    $('#mGuardarMini').on('hidden.bs.modal', ()=>{
        $('#SubCategoriaId').val('');
        $('#EspecificacionSubCategoriaId').val('');
        $('#EspecificacionSubCategoriaId').parent().removeClass('state-success');
    })
}
function modificandoSemanasPopOver(){
    let totalSemanasRaw='';
    $programa.semanas.forEach((v,index) => {
        let init = '';
        if(index == 0) init = 'elegido';
        totalSemanasRaw+= `<a href="javascript:void(0);" data-index="${index}" class="padding-5 margin-right-5 font-md btn btn-circle bg-color-grayDark txt-color-white numero-semana ${init}">${index+1}</a>`;
    });
    semanas.setAttribute('data-original-title',`<h6 class="txt-color-white text-align-center">Semanas</h6>`);
    semanas.setAttribute('data-content',`<div class="smart-form" style="width: 300px;">
                                            <section>
                                                <div class="row form-group text-align-center">
                                                    <div class="col col-10">
                                                        ${totalSemanasRaw}
                                                    </div>
                                                </div>
                                            </section>
                                         </div>`);
}

function semanasPopOver(){
    try {
        $('.popover .popover-content a').removeClass('elegido');
        $('.popover .popover-content a.numero-semana')[Number($('#SemanaActual').text())-1].classList.add('elegido');
    }catch (e) {console.log(e);}
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

function instanciandoToolTips(){
    $('[rel="tooltip"]').tooltip();
}

function obtenerObjetoMiniPlantilla(e){
    let index = e.target.getAttribute('data-index');//Indice comun para todas las listas
    const mini = document.querySelector(`#MiniPlantilla .rf-dia-lista[data-index="${index}"]`);

    let nombre = document.querySelector(`.rf-dia-lista[data-index="${index}"] .rf-dia-lista-nombre`).textContent.trim();
    //Instanciamos un objecto DiaLista
    let lista = new DiaLista(nombre, index, '');//3er Param Fecha, solo en caso la lista sea proveniente de un día de rutina

    //Añadiendo a la instancia de diaLista sus elementos(hijos)


    mini.querySelectorAll('ol li').forEach(v=>{
        let tipo = v.getAttribute('data-type')
        //Tipo x representa una lista inicial comodin sin valores
        if(tipo != 'x'){
            let duracion = '', media = '';
            let nombre =  v.querySelector('.rf-elemento-nombre').textContent.trim();
            if(tipo == TipoElemento.AUDIO || tipo == TipoElemento.VIDEO){
                duracion = v.querySelector('.rf-elemento-duracion').textContent.trim();
                media = v.querySelector('.rf-elemento-media').getAttribute('data-id-uuid').trim();
            }
            lista.elementos.push(new ElementoLista(nombre,duracion, media, tipo));
        }
    })
    return lista;
}

function guardarMiniPlantillaPrincipal(e) {

    const lista = obtenerObjetoMiniPlantilla(e);
    $('#spRaw3').html(spinnerHTMLRaw());
    $('#btnMiniPlantilla').attr('disabled', 'disabled');
    $('#btnMiniPlantilla').text('Cargando...');
    lista.especificacionSubCategoriaId = e.target.getAttribute('data-especificacion-id');
    lista.id = 0;
    $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: _ctx + 'gestion/mini-plantilla/agregar/listaPlantillaSimple',
            dataType: "json",
            data: JSON.stringify(lista),
            success: function (data, textStatus) {
                if (textStatus == "success") {
                    if (data == "-9") {
                        $.smallBox({
                            content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                            timeout: 4500,
                            color: "alert",
                        });
                    } else {
                        $.smallBox({content: "<i>La mini plantilla se ha guardado satisfactoriamente...</i>"});
                        generandoNuevaMiniPlantilla(lista.especificacionSubCategoriaId);
                    }
                }
            },
            error: function (xhr) {
                exception(xhr);
            },
            complete: function () {
                $('#btnMiniPlantilla').removeAttr('disabled');
                $('#btnMiniPlantilla').text('Guardar');
                $('#spRaw3').html('');
                $('#myModalMini').modal('hide');
            }
    });
}

function generandoNuevaMiniPlantilla(subCatId){
    let especificacion = document.querySelector(`#ArbolRutinario a[data-especificacion-id="${subCatId}"]`).parentElement;
    let cantHijos = especificacion.children.length;//Como el icon plus representa un hijo ya no le aumentamos +1
    var a = document.createElement('a');
    a.href = 'javascript:void(0)';
    a.innerHTML = `<span onclick="obtenerMiniPlantilla(${subCatId}, ${--cantHijos});" class="badge bg-color-greenLight font-md mini">${++cantHijos}</span>`;
    especificacion.append(a);
    setTimeout(()=>{especificacion.children[cantHijos].children[0].classList.remove('bg-color-greenLight');especificacion.children[cantHijos].children[0].classList.add('bg-color-darken');} ,4000);
}
function guardarMiniPlantilla(){
    $('#spRaw').html(spinnerHTMLRaw());
    $('#btnGuardarMini').attr('disabled', 'disabled');
    $('#btnGuardarMini').text('Cargando...');
    $listaPlantilla.especificacionSubCategoriaId = $('#EspecificacionSubCategoriaId').val();
    $listaPlantilla.id = '';
    //$listaPlantilla.elementos = new Set($listaPlantilla.elementos);
    $.ajax({
        type: 'POST',
        contentType: "application/json",
        url: _ctx + 'gestion/mini-plantilla/agregar/listaPlantillaSimple',
        dataType: "json",
        data: JSON.stringify($listaPlantilla),
        success: function (data, textStatus) {
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                } else {
                    $.smallBox({content: "<i>La mini plantilla se ha guardado satisfactoriamente...</i>"});
                    generandoNuevaMiniPlantilla($listaPlantilla.especificacionSubCategoriaId);
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
            $('#mGuardarMini').modal('hide');
            $('#EspecificacionSubCategoriaId').val('');
        }
    });
}

function cargarListaSubCategoriaEjercicio(e){
    let subCatId = e.target.value;
    let params = {};
    params.id = subCatId;

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
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    });
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
    } else {
        document.getElementById("myBtn").style.display = "none";
    }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
    $body.animate({scrollTop: 125},300);
}

function obtenerIconoRepetido(cant, icon){
    let raw = '';
    for(var i=0; i<cant;i++){
        raw+=icon;
    }
    return raw;
}

function invocarRutinarioCe(){

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
                }else{
                    let rawHTML = '';
                    JSON.parse(data).forEach(cat => {
                        rawHTML +=
                            `<div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                 <h1 class="text-align-center txt-color-white padding-7 bg-color-blue">${cat.nombre}</h1>
                                 ${generandoSubCategoriasRutinarioCe(cat)}
                             </div>`;
                    });
                    $('#ArbolRutinario').html(rawHTML);
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    });
}

function generandoSubCategoriasRutinarioCe(cat){

    let rawSubCategoriasHTML = '';
    cat.lstSubCategoriaEjercicio.forEach(subCat=> {
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
    return generandoEspecificacionesPorNiveles(generalNiveles);

}

function generandoEspecificacionesPorNiveles(generalEspSubCat){
        let htmlRaw = '';
        generalEspSubCat.forEach((nivel, i)=>{
            let iconoRepetido = obtenerIconoRepetido(i+1,'<i class="text-warning fa fa-star"></i>');
            //lvl-${i} de acuerdo al nivel cambia el color del icono
            htmlRaw += `
                        <div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12 padding-bottom-10">
                            <span class="pull-left text-align-center lvl-${i+1}" style="margin-left: -40px;padding-left: 20px"><i class="fa fa-child fa-3x"></i><br>${iconoRepetido}</i></span>
                            <div class="row" style="padding-left: 40px;">
                                ${nivel.map(esp=>`
                                    <div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12 linea padding-bottom-10 font-md">
                                        <a href="#">
                                            <strong>${esp.nombre}</strong>
                                        </a>
                                        <span class="pull-right">
                                            <a data-target="#myModalMini" data-toggle="modal" data-especificacion-id="${esp.id}" onclick="setEspecificacionId(${esp.id});" title="Agregar nueva plantilla"><span class="badge bg-color-redLight mini font-md">+</span></a>
                                                ${esp.lstMiniPlantilla[0].listas!=null?esp.lstMiniPlantilla[0].listas.map((v,i)=>`
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

function obtenerMiniPlantilla(subCatId, index){
    $('#mMostrarMini').modal('show');
    let params = {};
    params.subCatId = subCatId;
    params.index = index;
    document.querySelector('#ContenidoMini').innerHTML = spinnerHTMLRawCsMessage('Cargando...');

    $.ajax({
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'gestion/mini-plantilla/obtenerListaPlantillaSimple',
        dataType: "json",
        data: params,
        success: function (data, textStatus) {
            //Guardamos la plantilla obtenida en esta variable global para luego parsearla a una estructura html
            $listaPlantilla = data;
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                }else{
                    let elementosListaRaw = '';
                    data.elementos.forEach(v=>elementosListaRaw+=obtenerCuerpoListaRaw(v));
                    const listaFinal = obtenerCabeceraListaRaw(data.nombre, elementosListaRaw);
                    $('#ContenidoMini').html(listaFinal);
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
    $('#mMostrarMini').modal('hide');
    document.querySelector('a[href="#tabRutina"]').click();
}

function pegandoItemsMedia(e) {
    if (e.target.classList.contains('rf-lst-dia-txt') && $seleccionados.length > 0) {
        e.preventDefault();
        let objIx = todosLosIndex(e.target);
        //Agregando al programa rutina general
        let tempLista = document.querySelector(`.rf-semana .rf-dia[data-index="${objIx.diaIndex}"] .rf-dia-lista[data-index="${objIx.lstIndex}"]`);
        let i=0;while((tempLista = tempLista.previousElementSibling) != null) i++;
        //--
        let lista = document.querySelector(`.rf-dia-lista[data-index="${objIx.lstIndex}"] .dd-list`);
        let duplicados = [];
        let cantNuevos = 0;
        $seleccionados.forEach(v => {
            console.log(v);
            let id = v.getAttribute('data-id'), nombre = v.querySelector('.rf-elemento-nombre').textContent, type = v.getAttribute('data-type'), media = v.querySelector('.rf-elemento-media').getAttribute('data-id-uuid'), duracion = v.querySelector('.rf-elemento-duracion').textContent;
            if (lista.querySelector(`li[data-id="${id}"][data-type="${type}"]`) == undefined){
                v.querySelector('.del-elemento').setAttribute('data-lista-index',objIx.lstIndex);
                v.querySelector('.del-elemento').setAttribute('data-dia-index',objIx.diaIndex);
                lista.append(v);
                //Agregando al programa rutina general

                $programa.semanas[objIx.numSem-1].dias[objIx.diaIndex].listas[i].elementos.push(new ElementoLista(nombre, duracion, media, type));
                ++cantNuevos;
            }else{
                duplicados.push(`<h6 class="text-align-center">- ${v.textContent.trim().slice(0, -8).trim()}</h6>`);
            }
        });

        if(cantNuevos>0){
            //Escenario ideal
            //ListaIndex No Global == i
            let listaIndex = i;
            agregarVariosElementoAListaRutinaBD(objIx.numSem-1, objIx.diaIndex, listaIndex, cantNuevos);
        }

        if (duplicados.length > 0)
            $.smallBox({
                color: "alert",
                content: `<i>No se puede insertar elementos repetidos: ${duplicados.join('')} </i>`,
                timeout: 6000
            })
        $seleccionados = [];
        //Mostramos todas la opcion para eliminar del elemento
        lista.querySelectorAll(`.btn-hidden`).forEach((v) => v.style.display = '');
    }
}

function confirmarBajaListaDia(ix, diaIndex) {
    let numSem = document.querySelector('#SemanaActual').textContent;
    //1. Removemos en el objeto rutina
    let temp = document.querySelector(`.rf-semana .rf-dia .rf-dia-lista[data-index="${ix}"]`);
    let i=0;
    while((temp = temp.previousElementSibling) != null) i++;
    $programa.semanas[--numSem].dias[diaIndex].listas.splice(i, 1);
    //2. Removemos en el dom
    document.querySelector(`.panel-default[data-index="${ix}"]`).remove();

    //3. Eliminando de la BD
    let listaIndex = i;
    removerListaBD(numSem, diaIndex, listaIndex);
}

function principalesEventosRutina(e) {
    console.log(e.target);
    if (e.target.classList.contains('trash-elemento')) {
        e.preventDefault();
        e.stopPropagation();
        const index = e.target.getAttribute('data-index');
        const diaIndex = e.target.getAttribute('data-dia-index');
        $.smallBox({
            content: "¿Estás seguro de querer eliminar esta lista del día? <p class='text-align-right'><a href='javascript:confirmarBajaListaDia(" + index + ","+diaIndex+");' class='btn btn-primary btn-sm'>Si</a> <a href='javascript:void(0);' class='btn btn-danger btn-sm'>No</a></p>",
            color: "#296191",
            timeout: 10000,
            icon: "fa fa-bell swing animated",
            iconSmall: "",
        });
    }

    else if (e.target.classList.contains('guardar-lista')) {
        e.stopPropagation();
        $('#mGuardarMini').modal('show');
        let index = e.target.getAttribute('data-index');//Indice comun para todas las listas
        const lista = document.querySelector(`.rf-dia-lista[data-index="${index}"]`);
        //Indice que indica el día(de 0 a 6|Martes a Lunes
        let diaIndex = e.target.getAttribute('data-dia-index');//Indice del dia
        let fecha = document.querySelector(`.rf-dia[data-index="${diaIndex}"]`).getAttribute('data-fecha');
        let nombre = lista.querySelector(`.rf-dia-lista-nombre`).textContent.trim();

        //Instanciamos un objecto DiaLista
        let diaLista = new DiaLista(nombre, index, fecha);

        //Añadiendo a la instancia de diaLista sus elementos(hijos)
        lista.querySelectorAll('ol li').forEach(v=>{
            let tipo = v.getAttribute('data-type')
            //Tipo x representa una lista inicial comodin sin valores
            if(tipo != 'x'){
                let duracion = '', media = '';
                let nombre =  v.querySelector('.rf-elemento-nombre').textContent.trim();
                if(tipo == TipoElemento.AUDIO || tipo == TipoElemento.VIDEO){
                    duracion = v.querySelector('.rf-elemento-duracion').textContent.trim();
                    media = v.querySelector('.rf-elemento-media').getAttribute('data-id-uuid').trim();
                }
                diaLista.elementos.push(new ElementoLista(nombre,duracion, media, tipo));
            }
        })

        $listaPlantilla = diaLista;
    } else if(e.target.classList.contains('rf-elemento-media')){
        const route = e.target.getAttribute('data-id-uuid');
        const tipoMedia = e.target.getAttribute('data-type');
        if(tipoMedia == TipoElemento.VIDEO){
            $('#VideoReproduccion').get(0).src = `${_ctx}workout/media/file/video/gt/1${route}`;
            $("#VideoReproduccion").parent().get(0).load();
        }else{
            $('#AudioReproduccion').get(0).src = `${_ctx}workout/media/audio${route}`;
            $("#AudioReproduccion").parent().get(0).load();
        }
    } else if(e.target.classList.contains('del-elemento')){
        const index = e.target.getAttribute('data-index');
        eliminarElementoLista(document.querySelector(`.rf-elemento[data-index="${index}"]`));
    } else if(e.target.classList.contains('pegar-mini')){
        const diaIndex = e.target.getAttribute('data-index');
        if($listaPlantilla.nombre != null){
            const listaFinal = obtenerListaRawPegar($listaPlantilla.nombre, $listaPlantilla.elementos, diaIndex);
            document.querySelector(`.rf-dia[data-index="${diaIndex}"] .rf-listas`).appendChild(listaFinal);
            instanciandoListasDragAndDrop();
            //Insertando en BD
            let listaIndex = document.querySelector(`.rf-dia[data-index="${diaIndex}"] .rf-listas`).children.length;
            let numSem = document.querySelector('#SemanaActual').textContent;
            agregarListaConElementosBD(numSem -1, diaIndex);
        }else
            $.smallBox({color: "alert", content: "<i>No ha seleccionado ninguna lista de su catálogo de plantillas...</i>"})
    } else if(e.target.classList.contains('marcar-descanso')){
        const diaIndex = e.target.getAttribute('data-index');
        const semana = Number(document.querySelector('#SemanaActual').textContent)-1;
        const flag = $programa.semanas[semana].dias[diaIndex].flagDescanso = $programa.semanas[semana].dias[diaIndex].flagDescanso?false:true;
        let temp = document.querySelector(`.rf-dia[data-index="${diaIndex}"] div[role="heading"]`);
        if(!flag){//1. Cuando el flag sea cambiado a false: Recrearemos la estructura base
            let base = `
                        <div class="widget-body">
                            <div class="form-group">
                                <div class="smart-form">
                                    <label class="input padding-5">
                                    <input class="lst-dia" type="text" maxlength="121" placeholder="Ingresa nombre de lista" data-dia-index="${diaIndex}">
                                    <em class="txt-color-redLight" id="msg-val-${diaIndex}"></em>
                                    </label>
                                </div>
                            </div>
                            <!-- smart-accordion-default -->
                            <div class="panel-group smart-accordion-default rf-listas padding-5">
                            </div>
                        </div>
                        `
            temp.nextElementSibling.nextElementSibling.innerHTML = base;
        }else{
            let img = document.createElement('img');
            img.src = _ctx+'img/dia-libre.png';
            img.style.maxWidth = '90%';
            img.style.textAlign = 'center';
            temp.nextElementSibling.nextElementSibling.innerHTML = '';
            temp.nextElementSibling.nextElementSibling.appendChild(img);
            //2. Eliminaremos del objeto general todas las listas que se hayan almacenado del día
            $programa.semanas[semana].dias[diaIndex].listas = [];
        }
        modificarDiaFlagDescanso(semana, diaIndex, flag);
    } else if(e.target.classList.contains('rf-dia-lista-nombre')){
        e.preventDefault();
        e.stopPropagation();
        //Sirve para comparar el valor inicial del elemento con el valor que retorna en el evento focusout con el fin de evitar actualizaciones innecesarias
        $nombreActualizar = e.target.textContent.trim();
    } else if(e.target.classList.contains('rf-elemento-nombre')){
        //Sirve para comparar el valor inicial del elemento con el valor que retorna en el evento focusout con el fin de evitar actualizaciones innecesarias
        $nombreActualizar = e.target.textContent.trim();
    }
}

function principalesEventosTabRutina(e){
    if(e.target.classList.contains('numero-semana')){
        e.preventDefault();
        const index = e.target.getAttribute('data-index');
        //Consultamos si es que la semana no ha sido modificada, en caso ya haya sido(modificada), generamos la semana con esa informacion
        let modificado = false;
        $programa.semanas[index].dias.forEach(v=>{
            if(v.listas.length > 0){
                modificado = true;
            }
        });
        if(!modificado)
            $programa.construirPrograma($programa.semanas[index], index);
        else
            $programa.construirSemanaConDataAlmacenada($programa.semanas[index], index);
        e.target.parentElement.querySelector('.elegido').classList.toggle('elegido');
        e.target.classList.add('elegido');
        document.querySelector('#SemanaActual').textContent = 1+Number(index);
        $('[rel="tooltip"]').tooltip();

    }else if(e.target.classList.contains('retroceder-semana')){
        e.preventDefault();
        let numSem = Number(document.querySelector('#SemanaActual').textContent);
        if(numSem != 1){
            //Consultamos si es que la semana no ha sido modificada, en caso ya haya sido(modificada), generamos la semana con esa informacion
            let modificado = false;
            $programa.semanas[numSem-2].dias.forEach(v=>{
                if(v.listas.length > 0){
                    modificado = true;
                }
            });
            //Se restan 2 por que uno es por el indice que comienza en 0 y el otro porque se esta retrocediendo una semana
            if(!modificado)
                $programa.construirPrograma($programa.semanas[numSem-2], numSem-2);
            else
                $programa.construirSemanaConDataAlmacenada($programa.semanas[numSem-2], numSem-2);
            $.smallBox({content: "<i class='fa fa-spinner fa-spin fa-2x fa-fw'></i> <i>Cargando...<i>"})
            document.querySelector('#SemanaActual').textContent = numSem-1;
            $('[rel="tooltip"]').tooltip();
        }else{
            $.smallBox({color: "alert", content: "<i>No hay semana anterior a la actual...<i>"})
        }

    }else if(e.target.classList.contains('adelantar-semana')){
        e.preventDefault();
        let numSem = Number(document.querySelector('#SemanaActual').textContent);
        let totSemanas = $programa.totalSemanas;
        if(numSem != totSemanas){
            //Consultamos si es que la semana no ha sido modificada, en caso ya haya sido(modificada), generamos la semana con esa informacion
            let modificado = false;
            $programa.semanas[numSem].dias.forEach(v=>{
                if(v.listas.length > 0){
                    modificado = true;
                }
            });
            //Se restan 2 por que uno es por el indice que comienza en 0 y el otro porque se esta retrocediendo una semana
            if(!modificado)
                $programa.construirPrograma($programa.semanas[numSem], numSem);
            else
                $programa.construirSemanaConDataAlmacenada($programa.semanas[numSem], numSem);
            $.smallBox({content: "<i class='fa fa-spinner fa-spin fa-2x fa-fw'></i> <i>Cargando...<i>"})
            document.querySelector('#SemanaActual').textContent = numSem+1;
            $('[rel="tooltip"]').tooltip();
        }else{
            $.smallBox({color: "alert", content: "<i>No hay semana posterior a la actual...<i>"})
        }
    }
}

function principalesEventosTabGrupoAudios(e){
    e.preventDefault();
    if(e.target.classList.contains('reprod-audio')){
        if(e.target.classList.contains('fa-pause-circle')){
            document.querySelector('#someaud').pause();
            e.target.setAttribute('data-original-title','Reproducir');
        }else{
            const route = e.target.getAttribute('data-media');
            $('#AudioReproduccion').get(0).src = `${_ctx}workout/media/audio${route}`;
            $("#AudioReproduccion").parent().get(0).load();
            e.target.setAttribute('data-original-title','Pausar');
        }
        e.target.classList.toggle('fa-music');
        e.target.classList.toggle('fa-pause-circle');
    }else if(e.target.classList.contains('elegir-audio')){
        e.stopPropagation();
        $memoriaAudio != ''?$memoriaAudio.classList.remove('txt-color-greenIn'):'';
        e.target.classList.add('txt-color-greenIn');
        $memoriaAudio = e.target;
        $mediaAudio = e.target.children[0].getAttribute('data-media');
        cambiarATabRutina();
    }
}

function principalesAlCambiarTab(e){
    const input = e.target;
    console.log(input);
    $yelmo = input;
    $videosElegidos = [];
    Array.from(document.getElementById('ArbolGrupoVideoDetalle').querySelectorAll('.txt-color-greenIn')).forEach(e=>e.classList.remove('txt-color-greenIn'));
}

function eliminarElementoLista(elemento) {
    elementoEliminar = elemento;
    $.smallBox({
        content: "¿Estás seguro de querer eliminar este elemento de la lista? <p class='text-align-right'><a href='javascript:confirmarEliminacionElemento()' class='btn btn-primary btn-sm'>Si</a> <a href='javascript:void(0);' class='btn btn-danger btn-sm'>No</a></p>",
        color: "#296191",
        timeout: 10000,
        icon: "fa fa-bell swing animated",
        iconSmall: "",
    });
}

function confirmarEliminacionElemento() {
    //1. Removemos del objeto rutina general
    let e = elementoEliminar.querySelector('.del-elemento');
    let objIx = {};
    objIx.numSem = document.querySelector('#SemanaActual').textContent;
    objIx.diaIndex = e.getAttribute('data-dia-index');
    objIx.lstIndex = e.getAttribute('data-lista-index');
    objIx.index = e.getAttribute('data-index');
    let tempLista = document.querySelector(`.rf-semana .rf-dia[data-index="${objIx.diaIndex}"] .rf-dia-lista[data-index="${objIx.lstIndex}"]`);
    let i=0;k=0;
    //Obtenemos la posición de la lista en donde se encuentra el elemento a eliminar
    while((tempLista = tempLista.previousElementSibling) != null) i++;
    let tempElemento = document.querySelector(`.rf-semana .rf-dia[data-index="${objIx.diaIndex}"] .rf-dia-lista[data-index="${objIx.lstIndex}"] .rf-elemento[data-index="${objIx.index}"]`);
    //Obtenemos la posicion del elemento con respecto a sus pares
    while((tempElemento = tempElemento.previousElementSibling) != null) k++;
    $programa.semanas[--objIx.numSem].dias[objIx.diaIndex].listas[i].elementos.splice(--k, 1);//Hay un artificio en la lista que obliga a restar 1(--k) para que cuadre las posiciones
    //2. Removemos del dom
    elementoEliminar.remove();
    elementoEliminar = '';
    console.log($programa.semanas[objIx.numSem].dias[objIx.diaIndex].listas[i].elementos);
    //3. Eliminando de BD
    const elementoIx = k;
    const listaIx = i;
    removerElementoBD(objIx.numSem, objIx.diaIndex, listaIx, elementoIx);
}

function agregarElementos(e) {

    if(e.target.classList.contains('lst-dia')){
        e.preventDefault();
        $inFocus = e.target;
        let fontWeight = $inFocus.style.fontWeight;
        let fontStyle = $inFocus.style.fontStyle;
        $inFocus.style = '';

        let nombre = e.target.value;
        let lstIndex = ++indexGlobal;
        const diaIndex = e.target.getAttribute('data-dia-index');
        if (e.target.value.length > 2 && listaNoRepetida(diaIndex, nombre)) {

            let lista =
                `<div class="panel panel-default rf-dia-lista" data-index="${lstIndex}">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse${lstIndex}">
                                                            <i class="fa fa-lg fa-angle-down pull-right text-primary"></i> 
                                                            <i class="fa fa-lg fa-angle-up pull-right text-primary"></i>
                                                            <span class="txt-color-blue lista-title">
                                                                <i class="fa fa-chevron-right fa-fw text-primary"></i><span class="rf-dia-lista-nombre" style="font-weight: ${fontWeight};font-style: ${fontStyle}" contenteditable="true">${nombre}</span>  
                                                            </span>
                                                            <i class="fa fa-trash pull-right txt-color-redLight trash-elemento"  rel="tooltip" data-placement="bottom" data-original-title="Eliminar lista" data-dia-index="${diaIndex}" data-index="${lstIndex}" style="padding-right:10px;font-size: 1.35em;"></i>
                                                            <i class="fa fa-copy pull-right txt-color-blueLight guardar-lista" rel="tooltip" data-placement="bottom" data-original-title="Guardar como mini plantilla" data-dia-index="${diaIndex}" data-index="${lstIndex}" style="padding-right:10px;font-size: 1.35em;"></i>
                                                        </a>
                                                </h4>
                                            </div>
                                            <div id="collapse${lstIndex}" class="panel-collapse collapse in">
                                                <div class="panel-body">
                                                    <div class="modulo-detalle">
                                                        <div class="smart-form"><label class="input padding-5"><input data-index="${lstIndex}" data-dia-index="${diaIndex}" class="rf-lst-dia-txt" type="text" maxlength="44" placeholder="Ingresa texto"></label></div>
                                                        <div class="dd nestable">
                                                            <ol class="dd-list detalle-lista">
                                                                <li class="dd-item" data-id="0" data-type="x">
                                                                </li>
                                                            </ol>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>`;
            $(`.rf-dia[data-index="${diaIndex}"] .rf-listas`).append(lista);
            instanciandoListasDragAndDrop();
            $('[rel="tooltip"]').tooltip();
            //Agregando al programa
            let numSem = document.querySelector('#SemanaActual').textContent;
            let fecha = document.querySelector(`.rf-dia[data-index="${diaIndex}"]`).getAttribute('data-fecha');
            $programa.semanas[--numSem].dias[diaIndex].listas.push(new DiaLista(nombre,lstIndex, fecha));
            //Escenario ideal
            agregarListaRutinaBD(numSem, diaIndex);
        } else {
            if (e.target.value.length == 0) {
            } else {
                if (e.target.value.length > 2) {
                    $(`#msg-val-${diaIndex}`).text('No puede crear una lista con un nombre repetido');
                } else {
                    $(`#msg-val-${diaIndex}`).text('Se requiere mínimo 3 letras');
                }
                setTimeout(() => $(`#msg-val-${diaIndex}`).text(''), 3500);
            }
        }
        e.target.value = "";
    }else if(e.target.classList.contains('rf-lst-dia-txt')) {
        //Referente a un nuevo elemento de una lista de un día especifico
        $inFocus = e.target;
        let fontWeight = $inFocus.style.fontWeight;
        let fontStyle = $inFocus.style.fontStyle;
        let objIx = todosLosIndex($inFocus);
        let nombre = $inFocus.value;
        $inFocus.style = '';

        if ($inFocus.value.length > 2) {
            let index = ++indexGlobal;
            rawHTML = `<li class="dd-item rf-elemento" data-index="${index}" data-type="3" data-color="" data-negrita="${fontWeight}" data-cursiva="${fontStyle}">
												<div class="dd-handle md-add">
													<span class="dd-nodrag rf-elemento-nombre" style="font-weight: ${fontWeight};font-style: ${fontStyle}" contenteditable="true" onkeydown="editarTextoItemDiaVal(event);" on>${nombre}</span>
													<span class="dd-nodrag pull-right">
														<a onclick="editarTextoItemDia(this);" type="button">
															<i title="Editar" class="fa fa-edit"></i>
														</a>
														<a type="button">
															<i class="fa fa-trash txt-color-redLight del-elemento" data-index="${index}" data-lista-index="${objIx.lstIndex}" data-dia-index="${objIx.diaIndex}"></i>
														</a>
													</span>
												</div>																			
											 </li>`;
            $('#collapse' + objIx.lstIndex).find('.dd-list').append(rawHTML);
            $inFocus.value = "";
            //Agregando al programa rutina general
            let tempLista = document.querySelector(`.rf-semana .rf-dia[data-index="${objIx.diaIndex}"] .rf-dia-lista[data-index="${objIx.lstIndex}"]`);
            let i = 0;
            while ((tempLista = tempLista.previousElementSibling) != null) i++;
            $programa.semanas[--objIx.numSem].dias[objIx.diaIndex].listas[i].elementos.push(new ElementoLista(nombre, '', '', TipoElemento.TEXTO));
            console.log($programa.semanas[objIx.numSem].dias[objIx.diaIndex].listas[i]);
            //Escenario ideal
            //ListaIndex No Global == i
            let listaIndex = i;
            //ElementoLista = Lista.length
            let elementoIndex = $programa.semanas[objIx.numSem].dias[objIx.diaIndex].listas[i].elementos.length;
            agregarElementoAListaRutinaBD(objIx.numSem, objIx.diaIndex, listaIndex, elementoIndex-1);
        } else {
            if ($inFocus.value.length == 0) {
            } else {
                $.smallBox({color: "alert", content: "Debe ingresar más de 2 caracteres..."})
            }
        }
    } else if(e.target.classList.contains('rf-dia-lista-nombre')){
        //$nombreActualizar: Sirve para comparar el valor inicial del elemento con el valor que retorna cuando se activa este evento(focusout) con el fin de evitar actualizaciones innecesarias
        let nombre = e.target.textContent.trim();
        if($nombreActualizar != nombre){
            let objIx = todosLosIndex(e.target.parentElement.parentElement.querySelector('.trash-elemento'));
            //1. Buscamos la posicion de la lista y con ello cambiamos el nombre en el programa general
            let temp = document.querySelector(`.rf-semana .rf-dia .rf-dia-lista[data-index="${objIx.lstIndex}"]`);let i=0;
            while((temp = temp.previousElementSibling) != null) i++;
            $programa.semanas[--objIx.numSem].dias[objIx.diaIndex].listas[i].nombre = nombre;
            console.log($programa.semanas[objIx.numSem].dias[objIx.diaIndex].listas[i]);
            console.log("Actualizando en BD...");
            //Indices con respecto a la posicion en la que se encuentran con respecto a su contenedor padre y sus hermanos
            let listaIndex = i;
            actualizarListaNombreBD(objIx.numSem, objIx.diaIndex, listaIndex);
        }else{
            console.log("No es necesario actualizar");
        }

    } else if(e.target.classList.contains('rf-elemento-nombre')){
        let nombre = e.target.textContent.trim();
        if($nombreActualizar != nombre) {
            //En caso se haya editado el nombre, este lo cambiamos también en el programa general;
            let temp0 = e.target.parentElement.querySelector('.del-elemento');
            let objIx = {};
            objIx.numSem = document.querySelector('#SemanaActual').textContent;
            objIx.diaIndex = temp0.getAttribute('data-dia-index');
            objIx.lstIndex = temp0.getAttribute('data-lista-index');
            objIx.index = temp0.getAttribute('data-index');
            let tempLista = document.querySelector(`.rf-semana .rf-dia[data-index="${objIx.diaIndex}"] .rf-dia-lista[data-index="${objIx.lstIndex}"]`);
            let i = 0;
            let k = 0;
            //Obtenemos la posición de la lista en donde se encuentra el elemento a eliminar
            while ((tempLista = tempLista.previousElementSibling) != null) i++;
            let tempElemento = document.querySelector(`.rf-semana .rf-dia[data-index="${objIx.diaIndex}"] .rf-dia-lista[data-index="${objIx.lstIndex}"] .rf-elemento[data-index="${objIx.index}"]`);
            //Obtenemos la posicion del elemento con respecto a sus pares
            while ((tempElemento = tempElemento.previousElementSibling) != null) k++;
            $programa.semanas[--objIx.numSem].dias[objIx.diaIndex].listas[i].elementos[--k].nombre = nombre;//Hay un artificio en la lista que obliga a restar 1(--k) para que cuadre las posiciones
            console.log($programa.semanas[objIx.numSem].dias[objIx.diaIndex].listas[i].elementos);
            console.log("Actualizando en BD...");
            //Indices con respecto a la posicion en la que se encuentran con respecto a su contenedor padre y sus hermanos
            let listaIndex = i;
            let elementoIndex = k;
            actualizarElementoNombreBD(nombre, objIx.numSem, objIx.diaIndex, listaIndex, elementoIndex);
        }else{
            console.log("No es necesario actualizar");
        }
    }
}

function agregarListaMini(e){
    if(e.target.value.trim().length> 2){
        //Creamos una lista
        const input = e.target.value;
        const cuerpoMiniPlantilla = document.querySelector('#MiniPlantilla');
        let lstIndex = ++indexGlobal;
            let rawMini =
                                        `<div class="panel panel-default rf-dia-lista" data-index="${lstIndex}">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse${lstIndex}">
                                                            <span class="txt-color-blue lista-title">
                                                                <i class="fa fa-chevron-right fa-fw text-primary"></i><span class="rf-dia-lista-nombre" contenteditable="true">${input}</span>  
                                                            </span>
                                                        </a>
                                                </h4>
                                            </div>
                                            <div id="collapse${lstIndex}" class="panel-collapse collapse in">
                                                <div class="panel-body" style="max-height: 400px !important;overflow-y: auto;">
                                                    <div class="alert alert-warning">Tamaño mínimo de 3 caracteres.</div>
                                                    <div class="modulo-detalle">
                                                            <div class="smart-form">
                                                                <label class="input padding-5">
                                                                    <input data-index="${lstIndex}" onfocusout='agregarElementoLista(this)' type="text" maxlength="44" placeholder="Ingresa nuevo elemento">
                                                                </label>
                                                            </div>
                                                            <div class="dd nestable">
                                                                <ol class="dd-list detalle-lista">
                                                                    <li class="dd-item" data-id="0" data-type="x">
                                                                    </li>
                                                                </ol>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>`
            cuerpoMiniPlantilla.innerHTML = rawMini;
            e.target.parentElement.classList.toggle('hidden');
            //Agregando el indexGlobal para posteriormente usarlo para recuperar toda la lista
            document.querySelector('#btnMiniPlantilla').setAttribute('data-index', lstIndex);
    }
}

function agregarElementoLista(input){
    let fontWeight = '';
    let fontStyle = '';
    input.style = '';

    if (input.value.length > 2) {
        let index = ++indexGlobal;
        rawHTML = `<li class="dd-item rf-elemento" data-index="${index}" data-type="3" data-color="" data-negrita="${fontWeight}" data-cursiva="${fontStyle}">
												<div class="dd-handle md-add">
													<span class="dd-nodrag rf-elemento-nombre" style="font-weight: ${fontWeight};font-style: ${fontStyle}" contenteditable="true" onkeydown="editarTextoItemDiaVal(event);" on>${input.value}</span>
													<span class="dd-nodrag pull-right">
														<a onclick="editarTextoItemDia(this);" type="button">
															<i title="Editar" class="fa fa-edit"></i>
														</a>
														<a type="button">
															<i class="fa fa-trash txt-color-redLight del-elemento" onclick="preEliminarElementoLista(this)" data-index="${index}"></i>
														</a>
													</span>
												</div>																			
											 </li>`;
        $('#collapse' + input.getAttribute('data-index')).find('.dd-list').append(rawHTML);
        input.value = '';
        input.focus();
    }
}

function preEliminarElementoLista(e){
    const index = e.getAttribute('data-index');
    eliminarElementoLista(document.querySelector(`.rf-elemento[data-index="${index}"]`));
}

function setEspecificacionId(especificacionId){
    document.querySelector('#btnMiniPlantilla').setAttribute('data-especificacion-id', especificacionId);
}

function editarTextoItemDia(e){
    e.parentElement.parentElement.children[0].focus();
}

function editarTextoItemDiaVal(e){

    var key = e.keyCode || e.charCode;

    let len = e.target.textContent.length;
    if(len>30){
        e.preventDefault();
        $.smallBox({color: "alert", content: "Máximo permitido: 30 caracteres..."});
        e.target.textContent =  e.target.textContent.slice(0,-2);
    }

    if(len<4){
        if (key == 8 || key == 46) {
            e.preventDefault();
        }
        $.smallBox({color: "alert", content: "Mínimo: 3 caracteres..."});
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

function consultarVideos() {
    $('#CoincidenciaMediaElementos').html(spinnerHTMLRaw());
    $('#btnBuscarVideos').attr('disabled', 'disabled');
    $('#btnBuscarVideos').text('Por favor espere...');
    let params = {};
    params.comodin = $('#ComodinVideo').val();
    params.tipo = $('#TipoVideo').val();
    params.subTipo = $('#SubTipoVideo').val();
    $.ajax({
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'gestion/video/obtenerListadoSecundario',
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
                }

                //Listando los videos
                //attributo data-type:v == Video
                let rawHTML = '';
                data.forEach(v => {
                    let index = ++indexGlobal;
                    rawHTML +=
                                            `<li class="dd-item rf-elemento" data-id="${v.id}" data-index="${index}" data-type="2">
												<div class="dd-handle">
													<span class="dd-nodrag rf-elemento-nombre" contenteditable="true">${v.nombre}</span>
													<span class="dd-nodrag pull-right">
														<a data-toggle="modal" data-target="#myModalVideo" type="button">
															<i class="editable-click fa fa-video-camera rf-elemento-media" data-type="2" data-id-uuid="${v.rutaWeb}"></i>
														</a>
														<a class="btn-hidden" style="display: none;" type="button">
															<i class="fa fa-trash txt-color-redLight del-elemento" data-index="${index}"></i>
														</a>
													</span>
													<span class="pull-right rf-elemento-duracion margin-right-5">${v.duracion}</span>
												</div>																								
											 </li>`
                });
                $('#CoincidenciaMediaElementos').html(rawHTML);
            }
        },
        error: function (xhr) {
            $('#CoincidenciaMediaElementos').html('');
            exception(xhr);
        },
        complete: function () {
            $('#ComodinVideo').val('');
            $('#btnBuscarVideos').removeAttr('disabled');
            $('#btnBuscarVideos').html('Buscar <i class="fa fa-fw fa-search"></i>');
        }
    });

}

function consultarAudios() {
    $('#CoincidenciaMediaElementos').html(spinnerHTMLRaw());
    $('#btnBuscarAudios').attr('disabled', 'disabled');
    $('#btnBuscarAudios').text('Por favor espere...');
    let params = {};
    params.comodin = $('#ComodinAudio').val();
    params.tipo = $('#TipoAudio').val();
    $.ajax({
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'gestion/audio/obtenerListadoSecundario',
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
                }

                //Listando los videos
                //attributo data-type:a == Audio
                let rawHTML = '';

                data.forEach(v => {
                    let index = ++indexGlobal;
                    rawHTML +=
                        `<li class="dd-item rf-elemento" data-id="${v.id}" data-type="1" data-index="${index}">
							<div class="dd-handle">
								<span class="dd-nodrag rf-elemento-nombre" contenteditable="true">${v.nombre}</span>
								<span class="dd-nodrag pull-right">
									<a data-toggle="modal" data-target="#myModalAudio" type="button">
										<i class="editable-click fa fa-play rf-elemento-media" data-type="1" data-id-uuid="${v.rutaWeb}"></i>
									</a>
									<a class="btn-hidden" style="display: none;" type="button">
										<i class="fa fa-trash txt-color-redLight del-elemento" data-index="${index}"></i>
									</a>
								</span>
								<span class="pull-right rf-elemento-duracion margin-right-5">${v.duracion}</span>
							</div>																								
						 </li>`
                });
                $('#CoincidenciaMediaElementos').html(rawHTML);
            }
        },
        error: function (xhr) {
            $('#CoincidenciaMediaElementos').html('');
            exception(xhr);
        },
        complete: function () {
            $('#ComodinVideo').val('');
            $('#btnBuscarAudios').removeAttr('disabled');
            $('#btnBuscarAudios').html('Buscar <i class="fa fa-fw fa-search"></i>');
        }
    });
}

function seleccionandoItemMedia(e) {
    if (e.target.classList.contains('dd-handle')) {
            e.target.classList.toggle('md-add');
            //Consultados al div contenedor principal para obtener todos sus elementos
            Array.from(document.querySelector('#CoincidenciaMediaElementos').children).forEach((v) => {
                if (v.children[0].classList.contains('md-add')){
                    $seleccionados.push(v);
                }
            });
            // Antes de guardar el arreglo, lo filtramos e instanciamos una lista tipo set para conservar elementos unicos
            $seleccionados = $seleccionados.filter(ele => ele.children[0].classList.contains('md-add'));
            $seleccionados = Array.from(new Set($seleccionados));
    }
}

function obtenerCabeceraListaRaw(nombre, elementos){

//  Recreamos la cabecera con el objeto y con los elementos
    let lstIndex   =  ++indexGlobal;
    let fontWeight = '', fontStyle='';//Temporal
    const lista =
                                    `<div class="panel panel-default rf-dia-lista" data-index="${lstIndex}">
                                        <div class="panel-heading">
                                            <h4 class="panel-title font-md padding-5">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${lstIndex}">
                                                        <span class="txt-color-blue lista-title">
                                                            <i  class="fa fa-chevron-right fa-fw text-primary"></i><span class="rf-dia-lista-nombre" style="font-weight: ${fontWeight};font-style: ${fontStyle}" contenteditable="true">${nombre}</span>
                                                        </span>
                                                    </a>
                                            </h4>
                                        </div>
                                        <div id="collapse${lstIndex}" class="panel-collapse collapse in">
                                            <div class="panel-body">
                                                <div class="modulo-detalle">
                                                    <div class="smart-form"></div>
                                                        <div class="dd nestable">
                                                            <ol class="dd-list detalle-lista padding-10">
                                                                <li class="dd-item" data-id="0" data-type="x">
                                                                </li>
                                                                ${elementos}
                                                            </ol>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>`
    return lista;
}

function obtenerCuerpoListaRaw(elemento){
    const v = elemento;
    let fontWeight = '', fontStyle='';
    let index = ++indexGlobal;
    let rawHTML = '';
    switch(Number(v.tipo)){
        case TipoElemento.AUDIO:
        case TipoElemento.VIDEO:
            const modal = v.tipo == 1 ? 'Audio' : 'Video';
            const icon = v.tipo == 1?'fa-play':'fa-video-camera';
            rawHTML =   `<li class="dd-item rf-elemento" data-index="${index}" data-type="${v.tipo}" data-color="" data-negrita="${fontWeight}" data-cursiva="${fontStyle}">
                            <div class="dd-handle md-add">
                                <span class="dd-nodrag rf-elemento-nombre" style="font-weight: ${fontWeight};font-style: ${fontStyle}" onkeydown="editarTextoItemDiaVal(event);" on>${v.nombre}</span>
                                <span class="dd-nodrag pull-right">
                                    <a data-toggle="modal" data-target="#myModal${modal}" type="button">
                                        <i class="editable-click fa ${icon} rf-elemento-media" data-type="${v.tipo}" data-id-uuid="${v.media}"></i>
                                    </a>
                                </span>
                                <span class="pull-right rf-elemento-duracion margin-right-5">${v.duracion}</span>
                            </div>																			
					    </li>
                        `;
            break;
        default:
            rawHTML =  `<li class="dd-item rf-elemento" data-index="${index}" data-type="${v.tipo}" data-color="" data-negrita="${fontWeight}" data-cursiva="${fontStyle}">
                            <div class="dd-handle md-add">
                                <span class="dd-nodrag rf-elemento-nombre" style="font-weight: ${fontWeight};font-style: ${fontStyle}" onkeydown="editarTextoItemDiaVal(event);" on>${v.nombre}</span>
                            </div>																			
                         </li>`;
    }
    return rawHTML;
}

function obtenerCuerpoListaRawPegar(elemento, objIx){
    const v = elemento;
    let fontWeight = '', fontStyle='';
    let index = ++indexGlobal;
    let rawHTML = '';
    switch(Number(v.tipo)){
        case TipoElemento.AUDIO:
        case TipoElemento.VIDEO:
            const modal = v.tipo == 1 ? 'Audio' : 'Video';
            const icon = v.tipo == 1?'fa-play':'fa-video-camera';
            rawHTML =   `<li class="dd-item rf-elemento" data-index="${index}" data-type="${v.tipo}" data-color="" data-negrita="${fontWeight}" data-cursiva="${fontStyle}">
                            <div class="dd-handle md-add">
                                <span class="dd-nodrag rf-elemento-nombre" style="font-weight: ${fontWeight};font-style: ${fontStyle}" contenteditable="true" onkeydown="editarTextoItemDiaVal(event);" on>${v.nombre}</span>
                                <span class="dd-nodrag pull-right">
                                    <a data-toggle="modal" data-target="#myModal${modal}" type="button">
                                        <i class="editable-click fa ${icon} rf-elemento-media" data-type="${v.tipo}" data-id-uuid="${v.media}"></i>
                                    </a>
                                    <a class="btn-hidden" type="button">
                                        <i class="fa fa-trash txt-color-redLight del-elemento" data-index="${index}" data-lista-index="${objIx.lstIndex}" data-dia-index="${objIx.diaIndex}"></i>
                                    </a>
                                </span>
                                <span class="pull-right rf-elemento-duracion margin-right-5">${v.duracion}</span>
                            </div>																			
					    </li>
                        `;
            break;
        default:
            rawHTML =  `<li class="dd-item rf-elemento" data-index="${index}" data-type="${v.tipo}" data-color="" data-negrita="${fontWeight}" data-cursiva="${fontStyle}">
                            <div class="dd-handle md-add">
                                <span class="dd-nodrag rf-elemento-nombre" style="font-weight: ${fontWeight};font-style: ${fontStyle}" contenteditable="true" onkeydown="editarTextoItemDiaVal(event);" on>${v.nombre}</span>
                                <span class="dd-nodrag pull-right">
                                    <a onclick="editarTextoItemDia(this);" type="button">
                                        <i title="Editar" class="fa fa-edit"></i>
                                    </a>
                                    <a type="button">
                                        <i class="fa fa-trash txt-color-redLight del-elemento" data-index="${index}" data-lista-index="${objIx.lstIndex}" data-dia-index="${objIx.diaIndex}"></i>
                                    </a>
                                </span>
                            </div>																			
                         </li>`;
    }
    return rawHTML;
}

function obtenerListaRawPegar(nombre, elementos, diaIndex){
    
//  Recreamos la cabecera con el objeto y con los elementos
    let lstIndex   =  ++indexGlobal;
    let objIx = {};
    objIx.lstIndex = lstIndex;
    objIx.diaIndex = diaIndex;
    let fontWeight = '', fontStyle='';//Temporal
    const div = document.createElement('div');
    div.className = "panel panel-default rf-dia-lista";
    div.setAttribute('data-index', lstIndex);
    const lista =
                                    `
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${lstIndex}">
                                                        <i class="fa fa-lg fa-angle-down pull-right text-primary"></i>
                                                        <i class="fa fa-lg fa-angle-up pull-right text-primary"></i>
                                                        <span class="txt-color-blue lista-title">
                                                            <i class="fa fa-chevron-right fa-fw text-primary"></i><span class="rf-dia-lista-nombre" style="font-weight: ${fontWeight};font-style: ${fontStyle}" contenteditable="true">${nombre}</span>
                                                        </span>
                                                        <i class="fa fa-trash pull-right txt-color-redLight trash-elemento"  rel="tooltip" data-placement="bottom" data-original-title="Eliminar lista" data-dia-index="${diaIndex}" data-index="${lstIndex}" style="padding-right:10px;font-size: 1.35em;"></i>
                                                        <i class="fa fa-copy pull-right txt-color-blueLight guardar-lista" rel="tooltip" data-placement="bottom" data-original-title="Guardar como mini plantilla" data-dia-index="${diaIndex}" data-index="${lstIndex}" style="padding-right:10px;font-size: 1.35em;"></i>
                                                    </a>
                                            </h4>
                                        </div>
                                        <div id="collapse${lstIndex}" class="panel-collapse collapse in">
                                            <div class="panel-body">
                                                <div class="modulo-detalle">
                                                    <div class="smart-form"><label class="input padding-5"><input data-index="${lstIndex}" data-dia-index="${objIx.diaIndex}" class="rf-lst-dia-txt" type="text" maxlength="44" placeholder="Ingresa texto"></label></div>
                                                        <div class="dd nestable">
                                                            <ol class="dd-list detalle-lista">
                                                                <li class="dd-item" data-id="0" data-type="x">
                                                                </li>
                                                                ${elementos.map(e=>`
                                                                    ${obtenerCuerpoListaRawPegar(e, objIx)}
                                                                `.trim()).join('')}
                                                            </ol>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    `
    //Antes de retornar la lista la agregamos al programa general
    let numSem = document.querySelector('#SemanaActual').textContent;
    let fecha = document.querySelector(`.rf-dia[data-index="${diaIndex}"]`).getAttribute('data-fecha');
    const diaLista = new DiaLista(nombre,lstIndex, fecha);
    //Agregando los elementos
    diaLista.elementos = elementos;
    //Agregando la lista con los elementos
    $programa.semanas[--numSem].dias[diaIndex].listas.push(diaLista);
    div.innerHTML = lista;
    return div;
}

function obtenerSimpleListaRaw(nombre, elementos, diaIndex){

//  Recreamos la cabecera con el objeto y con los elementos
    let lstIndex   =  ++indexGlobal;
    let objIx = {};
    objIx.lstIndex = lstIndex;
    objIx.diaIndex = diaIndex;
    let fontWeight = '', fontStyle='';//Temporal
    const div = document.createElement('div');
    div.className = "panel panel-default rf-dia-lista";
    div.setAttribute('data-index', lstIndex);
    const lista =
        `
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${lstIndex}">
                                                        <i class="fa fa-lg fa-angle-down pull-right text-primary"></i>
                                                        <i class="fa fa-lg fa-angle-up pull-right text-primary"></i>
                                                        <span class="txt-color-blue lista-title">
                                                            <i class="fa fa-chevron-right fa-fw text-primary"></i><span class="rf-dia-lista-nombre" style="font-weight: ${fontWeight};font-style: ${fontStyle}" contenteditable="true">${nombre}</span>
                                                        </span>
                                                        <i class="fa fa-trash pull-right txt-color-redLight trash-elemento"  rel="tooltip" data-placement="bottom" data-original-title="Eliminar lista" data-dia-index="${diaIndex}" data-index="${lstIndex}" style="padding-right:10px;font-size: 1.35em;"></i>
                                                        <i class="fa fa-copy pull-right txt-color-blueLight guardar-lista" rel="tooltip" data-placement="bottom" data-original-title="Guardar como mini plantilla" data-dia-index="${diaIndex}" data-index="${lstIndex}" style="padding-right:10px;font-size: 1.35em;"></i>
                                                    </a>
                                            </h4>
                                        </div>
                                        <div id="collapse${lstIndex}" class="panel-collapse collapse in">
                                            <div class="panel-body">
                                                <div class="modulo-detalle">
                                                    <div class="smart-form"><label class="input padding-5"><input data-index="${lstIndex}" data-dia-index="${objIx.diaIndex}" class="rf-lst-dia-txt" type="text" maxlength="44" placeholder="Ingresa texto"></label></div>
                                                        <div class="dd nestable">
                                                            <ol class="dd-list detalle-lista">
                                                                <li class="dd-item" data-id="0" data-type="x">
                                                                </li>
                                                                ${elementos.map(e=>`
                                                                    ${obtenerCuerpoListaRawPegar(e, objIx)}
                                                                `.trim()).join('')}
                                                            </ol>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    `;

    div.innerHTML = lista;
    return div;
}

function todosLosIndex(e){
    let indxs = {};
    indxs.numSem = document.querySelector('#SemanaActual').textContent;
    indxs.diaIndex = e.getAttribute('data-dia-index');
    indxs.lstIndex = e.getAttribute('data-index');;
    return indxs;
}

function guardarRutina(){
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: _ctx + "gestion/rutina-plantilla/agregar",
        dataType: "json",
        data: JSON.stringify($programa),
        success: function (data) {
             if (data == "-150") {
                 $.smallBox({color  : "alert", content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",});
             } else if (data == "-1") {
                     $.smallBox({color: "rgb(131, 186, 40)", content: "<i>La rutina plantilla ha sido creada <br/> y esta lista para ser modificada...</i>"});
             } else {
                     $.smallBox({color  : "alert", content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",});
             }

        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function agregarListaRutinaBD(numSem, diaIndex){
    const listaLenght = $programa.semanas[numSem].dias[diaIndex].listas.length -1;
    let params = $programa.semanas[numSem].dias[diaIndex].listas[listaLenght];
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: _ctx + "gestion/rutina-plantilla/lista/agregar",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            if (data == "-150") {
                $.smallBox({color  : "alert", content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",});
            } else if (data == "-1") {
                console.log("Lista agregada");
            } else {
                $.smallBox({color  : "alert", content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",});
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function removerListaBD(numSem, diaIndex, lstIndex){
    let params = {}
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.listaIndice = lstIndex;

    $.ajax({
        type: "PUT",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina-plantilla/lista/eliminar",
        dataType: "json",
        data: params,
        success: function (data) {
            if (data == "-150") {
                $.smallBox({color  : "alert", content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",});
            } else if (data == "-1") {
                console.log("Lista agregada");
            } else if(data == "-3"){
                $.smallBox({color  : "success", content: "<i> Eliminación exitosa...</i>",});
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function removerElementoBD(numSem, diaIndex, lstIndex, elementoIndex){
    let params = {}
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.listaIndice = lstIndex;
    params.elementoIndice = elementoIndex;

    $.ajax({
        type: "PUT",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina-plantilla/elemento/eliminar",
        dataType: "json",
        data: params,
        success: function (data) {
            if (data == "-150") {
                $.smallBox({color  : "alert", content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",});
            } else if (data == "-1") {
                console.log("Lista agregada");
            } else if(data == "-3"){
                $.smallBox({color  : "success", content: "<i> Eliminación exitosa...</i>",});
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function agregarElementoAListaRutinaBD(numSem, diaIndex, listaIndex , elementoIndex){
    let params = $programa.semanas[numSem].dias[diaIndex].listas[listaIndex].elementos[elementoIndex]
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.listaIndice = listaIndex;
    params.elementoIndice = elementoIndex;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: _ctx + "gestion/rutina-plantilla/lista/elemento/agregar",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            if (data == "-150") {
                $.smallBox({color  : "alert", content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",});
            } else if (data == "-1") {
                console.log("Lista agregada");
                $.smallBox({content: "success"});
            } else {
                $.smallBox({color  : "alert", content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",});
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function agregarVariosElementoAListaRutinaBD(numSem, diaIndex, listaIndex , numElementos){
    let elementos = new Array(numElementos);
    let lenInit = $programa.semanas[numSem].dias[diaIndex].listas[listaIndex].elementos.length - numElementos;
    for (let i=0; i < elementos.length;i++){
        elementos[i] = $programa.semanas[numSem].dias[diaIndex].listas[listaIndex].elementos[lenInit+i];
    }

    elementos.forEach(v=>{
        v.numeroSemana = numSem;
        v.diaIndice = diaIndex;
        v.listaIndice = listaIndex;
        v.elementoIndice = 0;
    });
    console.log(elementos);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: _ctx + "gestion/rutina-plantilla/lista/elemento/agregar/v2",
        dataType: "json",
        data: JSON.stringify(elementos),
        success: function (data) {
            if (data == "-150") {
                $.smallBox({color  : "alert", content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",});
            } else if (data == "-1") {
                console.log("Lista agregada");
                $.smallBox({content: "success"});
            } else {
                $.smallBox({color  : "alert", content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",});
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function agregarListaConElementosBD(numSem, diaIndex){
    let params = {};
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    console.log(params);
    $.ajax({
        type: "PUT",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + "gestion/rutina-plantilla/lista-elementos/agregar",
        dataType: "json",
        data: params,
        success: function (data) {
            if (data == "-150") {
                $.smallBox({color  : "alert", content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",});
            } else if (data == "-1") {
                console.log("Lista agregada");
                $.smallBox({content: "success"});
            } else {
                $.smallBox({color  : "alert", content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",});
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })
}

function actualizarListaNombreBD(numSem, diaIndex, listaIndex) {
    let params = $programa.semanas[numSem].dias[diaIndex].listas[listaIndex];
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.listaIndice = listaIndex;
    console.log(params);
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: _ctx + "gestion/rutina-plantilla/lista/modificar",
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data) {
            if (data == "-150") {
                $.smallBox({
                    color: "alert",
                    content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",
                });
            } else if (data == "-2") {
                $.smallBox({content: "success"});
            } else {
                $.smallBox({
                    color: "alert",
                    content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",
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

function actualizarElementoNombreBD(nuevoNombre, numSem, diaIndex, listaIndex, elementoIndex) {
    let params = {};
    params.nombre = nuevoNombre;
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.listaIndice = listaIndex;
    params.elementoIndice = elementoIndex;
    console.log(params);
    $.ajax({
        type: "PUT",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        url: _ctx + "gestion/rutina-plantilla/elemento/modificar",
        dataType: "json",
        data: params,
        success: function (data) {
            if (data == "-150") {
                $.smallBox({
                    color: "alert",
                    content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",
                });
            } else if (data == "-2") {
                $.smallBox({content: "success"});
            } else {
                $.smallBox({
                    color: "alert",
                    content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",
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

function modificarDiaFlagDescanso(numSem, diaIndex, flagDescanso){
    let params = {};
    params.numeroSemana = numSem;
    params.diaIndice = diaIndex;
    params.flagDescanso = flagDescanso;
    console.log(params);
    $.ajax({
        type: 'PUT',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        url: _ctx + 'gestion/rutina-plantilla/dia-plantilla/actualizarFlagDescanso',
        data: params,
        dataType: 'json',
        success: function(data){
            if (data == "-150") {
                $.smallBox({
                    color: "alert",
                    content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",
                });
            } else if (data == "-2") {
                $.smallBox({content: "El flag ha sido actualizado satisfactoriamente"});
            } else {
                $.smallBox({
                    color: "alert",
                    content: "<i> La operación ha fallado... Comuníquese con el administrador</i>",
                });
            }
        }, error: function(xhr){
            exception(xhr);
        }, complete: function(){}
    })
}

function cambiarATabRutina(){
    document.querySelector('a[href="#tabRutina"]').click();
}