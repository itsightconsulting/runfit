const rutinaData = JSON.parse($('#rutinaData').val());
const editorRutinaContenido = document.querySelector('#editorContent');
const $semActual = document.querySelector('#nroSemanaActual');
const $semanario = document.querySelector('#rutinaSemana');
const mainTabs = document.querySelector('#principalesTabs');
const $rMenuEleSubele = document.querySelector('#rMenuEleSubele');
const dvEditor = document.querySelector('.editor_text');
let $menuTargetInput = '';
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
let $menuEleSubEle  = document.querySelector('#rmenuEleSubele');

$(function () {
    init();
})

function init(){

    let uriParam = getParamFromURL("si");

    obtenerSemanaInicialRutina().then((semana)=>{
        //Importante mantener el orden para el correcto funcionamiento
        $rutina = new Rutina(rutinaData);
        if(uriParam){
          let semanaIdx = atob(uriParam);
            $('#SemanaActual').text(Number(semanaIdx)+1);
            $rutina.initEspecifico(semana,semanaIdx)
        }else{
             $rutina.init(semana);
        }
         //validators();
        editorRutinaContenido.addEventListener('click', eventosEditorRutina);
        $semanario.addEventListener('focusout', principalesEventosFocusOutSemanario);
        $semanario.addEventListener('click', principalesEventosClickRutina);

        tabGrupoAudios.addEventListener('click', principalesEventosTabGrupoAudios);
        tabGrupoVideos.addEventListener('click', principalesEventosTabGrupoVideos);

        mainTabs.addEventListener('click', principalesAlCambiarTab);

        $semanario.addEventListener('contextmenu', eventosMenuSemanario);
        $rMenuEleSubele.addEventListener('click', eventosClickMenuOptElem);

            $(dvEditor).on('click','.btn' , principalesDivEditor);



        $(document).bind("click", function(event) {
           $rMenuEleSubele.className = "hide";
        });




/*


          instanciarDatosFitnessCliente();


           tabFichaTecnica.addEventListener('click', principalesEventosTabFichaTecnica);
           tabFichaTecnica.addEventListener('focusout', principalesEventosFocusOutTabFichaTecnica);
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
           modalEventos();
           //setFechaActual(document.querySelectorAll('input[type="date"]'));
           //obtenerSemanasEnviadas();
           calendarioTmp();
 */
          instanciarTooltips();

    });

}


function  eventosMenuSemanario(e){

    const input = e.target;
    const inpClasses = input.classList;
    if(inpClasses.contains("rf-dia-elemento-nombre") || inpClasses.contains("rf-sub-elemento-nombre")){

        $menuTargetInput = input;
        $rMenuEleSubele.classList.toggle("hide");
        $($rMenuEleSubele).css(
            {
                position: "absolute",
                top: e.pageY,
                left: e.pageX
            }
        );

        const inpDataIx = input.getAttribute('data-index');

         e.preventDefault();


    }
}

function eventosClickMenuOptElem(e) {

    const input = e.target;
    const inpClasses = input.classList;
    const inpTargetClasses = $menuTargetInput.classList;

    e.preventDefault();
    e.stopPropagation();

    if (inpClasses.contains('agregar-nota')) {

        if (inpTargetClasses.contains("rf-dia-elemento-nombre")) {

            const ixs = RutinaIx.getIxsForElemento($menuTargetInput);
            let elemento = RutinaDOMQueries.getElementoByIxs(ixs);
            const type = elemento.getAttribute('data-type');
            if (type == 2) {
                ;
                const collapsable = elemento.querySelector('a[data-toggle="collapse"]');
                const panelCollapsable = elemento.querySelector('.panel-collapse');
                collapsable.classList.add('collapsed');
                collapsable.setAttribute('aria-expanded', "false");
                panelCollapsable.classList.remove('in');
                panelCollapsable.setAttribute('aria-expanded', "false");
            }

            if (elemento.children.length != 3) {
                let elementoNota = elemento.querySelector('.rf-dia-elemento-nombre').getAttribute('data-content');
                let notaInput = document.createElement('div');
                notaInput.className = 'panel-heading';
                notaInput.innerHTML = `
                    <div class="dv-nota">
                        <textarea class="nueva-nota w-100" data-index="${ixs.eleIndex}" data-dia-index="${ixs.diaIndex}" type="text" rows="2">${elementoNota == undefined ? '' : elementoNota}</textarea>
                    </div >`
                elemento.append(notaInput);
            }

            $rMenuEleSubele.classList.toggle("hide");

        } else if (inpTargetClasses.contains("rf-sub-elemento-nombre")) {
            
            const ixs = RutinaIx.getIxsForSubElemento($menuTargetInput);
            let subEle = RutinaDOMQueries.getSubElementoByIxs(ixs);
            //  if(subEle.children.length != 1) {
            let subEleNota = subEle.querySelector('.rf-sub-elemento-nombre').getAttribute('data-content');
            let notaInput = document.createElement('div');
            notaInput.className = 'panel-heading';
            notaInput.style.marginTop = '15px';
            notaInput.style.marginLeft = '15px';
            notaInput.innerHTML = `
                    <div class="dv-nota">
                        <textarea class="nueva-nota-sbe w-100" data-index="${ixs.subEleIndex}" data-ele-index="${ixs.eleIndex}" data-dia-index="${ixs.diaIndex}" type="text" rows="2">${subEleNota != undefined ? subEleNota : ''}</textarea>
                    </div >`;
            subEle.append(notaInput);
            //    }
            $rMenuEleSubele.classList.toggle("hide");
        }


    } else if (inpClasses.contains('trash-audio')) {

        if (inpTargetClasses.contains("rf-dia-elemento-nombre")) {
            const ixs = RutinaIx.getIxsForElemento($menuTargetInput);
            ElementoOpc.eliminarMediaAudio(ixs);
        } else if (inpTargetClasses.contains("rf-sub-elemento-nombre")) {
            const ixs = RutinaIx.getIxsForSubElemento($menuTargetInput);
            console.log(ixs);
            SubEleOpc.eliminarMediaAudio(ixs);
        }
    }  else if(inpClasses.contains('trash-video')) {

        if (inpTargetClasses.contains("rf-dia-elemento-nombre")) {
            const ixs = RutinaIx.getIxsForElemento($menuTargetInput);
            ElementoOpc.eliminarMediaVideo(ixs);
        } else if (inpTargetClasses.contains("rf-sub-elemento-nombre")) {
            const ixs = RutinaIx.getIxsForSubElemento($menuTargetInput);
            SubEleOpc.eliminarMediaVideo(ixs);
        }

    }

     else if(inpClasses.contains('insertar-encima')) {

        if (inpTargetClasses.contains("rf-dia-elemento-nombre")) {

            let ixs = RutinaIx.getIxsForElemento($menuTargetInput);
            let elemento = RutinaDOMQueries.getElementoByIxs(ixs);
            let ix = ++indexGlobal;
            let refIndex = ixs.eleIndex;
            if (elemento.nextElementSibling != undefined) {
                if (elemento.nextElementSibling.classList.contains('ne-esp')) {
                    elemento.nextElementSibling.remove();
                }
            }
            if (elemento.previousElementSibling != undefined) {
                if (elemento.previousElementSibling.classList.contains('ne-esp')) {

                } else {
                    elemento.insertAdjacentHTML('beforebegin', `
                                                    <div class="panel-group elem ne-esp rf-dia-pre-elemento" data-dia-index="${ixs.diaIndex}"
                                                           data-index="${ix}">
                                                           <div class="row">
                                                            <div class="col-xs-6">
                                                                    <input type="text" class="cs-input in-ele-dia-esp-pos" maxlength="121" placeholder=""
                                                                      data-dia-index="${ixs.diaIndex}" data-ele-tipo="${ElementoTP.SIMPLE}" data-index="${ix}"
                                                                      data-ele-ref-index="${refIndex}"  data-strategy="beforebegin"/>
                                                            </div>
                                                            <div class="col-xs-6" data-ele-tipo="${ElementoTP.COMPUESTO}" data-index="${ix}">
                                                                      <input type="text" class="cs-input in-ele-dia-esp-pos" maxlength="121" placeholder="" data-dia-index="${ixs.diaIndex}"
                                                                       data-ele-tipo="${ElementoTP.COMPUESTO}" data-index="${ix}" data-ele-ref-index="${refIndex}"
                                                             data-strategy="beforebegin"/>
                                                            </div>
                                                           </div>
                                                          </div> `);
                }
            } else {
                elemento.insertAdjacentHTML('beforebegin', `
                                                    <div class="panel-group elem ne-esp rf-dia-pre-elemento" data-dia-index="${ixs.diaIndex}"
                                                           data-index="${ix}">
                                                           <div class="row">
                                                            <div class="col-xs-6">
                                                                    <input type="text" class="cs-input in-ele-dia-esp-pos" maxlength="121" placeholder=""
                                                                      data-dia-index="${ixs.diaIndex}" data-ele-tipo="${ElementoTP.SIMPLE}" data-index="${ix}"
                                                                      data-ele-ref-index="${refIndex}"  data-strategy="beforebegin"/>
                                                            </div>
                                                            <div class="col-xs-6" data-ele-tipo="${ElementoTP.COMPUESTO}" data-index="${ix}">
                                                                      <input type="text" class="cs-input in-ele-dia-esp-pos" maxlength="121" placeholder="" data-dia-index="${ixs.diaIndex}"
                                                                       data-ele-tipo="${ElementoTP.COMPUESTO}" data-index="${ix}" data-ele-ref-index="${refIndex}"
                                                             data-strategy="beforebegin"/>
                                                            </div>
                                                           </div>
                                                          </div>`);
            }
        } else if (inpTargetClasses.contains("rf-sub-elemento-nombre")) {

            let ixs = RutinaIx.getIxsForSubElemento($menuTargetInput);
            let subElemento = RutinaDOMQueries.getSubElementoByIxs(ixs).parentElement;
            let ix = ++indexGlobal;

            if (subElemento.nextElementSibling != undefined) {
                if (subElemento.nextElementSibling.classList.contains('ne-esp')) {
                    subElemento.nextElementSibling.remove();
                }
            }

            if (subElemento.previousElementSibling != undefined) {
                if (subElemento.previousElementSibling.classList.contains('ne-esp')) {

                } else {
                    subElemento.insertAdjacentHTML('beforebegin', `
                                                                  <li>
                                                                      <div class="row rf-pre-sub-elemento" data-dia-index="${ixs.diaIndex}"
                                                                            data-ele-index="${ixs.eleIndex}" data-index="${ix}">
                                                                        <div class="col-xs-10">
                                                                          <input type="text" class="cs-input in-sub-ele-esp-pos" data-dia-index="${ixs.diaIndex}"
                                                                            data-index="${ix}" data-ele-index="${ixs.eleIndex}" data-sub-ele-ref-index="${ixs.subEleIndex}"  data-strategy="beforebegin"/>    
                                                                        </div>
                                                                      </div>  
                                                                  </li>
                      `);
                }
            } else {
                subElemento.insertAdjacentHTML('beforebegin', `     <li>
                                                                      <div class="row rf-pre-sub-elemento" data-dia-index="${ixs.diaIndex}"
                                                                            data-ele-index="${ixs.eleIndex}" data-index="${ix}">
                                                                        <div class="col-xs-10">
                                                                          <input type="text" class="cs-input in-sub-ele-esp-pos" data-dia-index="${ixs.diaIndex}"
                                                                            data-index="${ix}" data-ele-index="${ixs.eleIndex}"  data-sub-ele-ref-index="${ixs.subEleIndex}" data-strategy="beforebegin"/>    
                                                                        </div>
                                                                      </div>  
                                                                  </li>`);
            }


        }
    }
     else if (inpClasses.contains('trash-elemento')) {
            
            if (inpTargetClasses.contains("rf-dia-elemento-nombre")) {

                const ixs = RutinaIx.getIxsForElemento($menuTargetInput);
                console.log("xd", ixs);
                ElementoOpc.eliminarElemento(ixs.numSem, ixs.diaIndex, ixs.eleIndex);

            } else if (inpTargetClasses.contains("rf-sub-elemento-nombre")) {

                const ixs = RutinaIx.getIxsForSubElemento($menuTargetInput);
                RutinaDOMQueries.getSubElementoByIxs(ixs);
                SubEleOpc.eliminarSubElemento(ixs.numSem, ixs.diaIndex, ixs.eleIndex, ixs.subEleIndex);
            }

        }

}

function eventosEditorRutina(e){

    const input = e.target;
    const inputParent =  input.parentElement;
    const clases = input.classList;
    const parentClases = inputParent ? inputParent.classList : '';
    if(clases.contains('adelantar-semana') || parentClases.contains('adelantar-semana')){
        e.preventDefault();
        const numSem = Number($semActual.textContent);
        avanzarRetrocederSemana(numSem, 1);
    }
    else if(clases.contains('retroceder-semana') || parentClases.contains('retroceder-semana')){

        e.preventDefault();
        let numSem = Number($semActual.textContent);
        if(numSem != 1){
          //  const parentDiv = input.tagName == "I" ? input.parentElement.parentElement : input.parentElement;
          //  parentDiv.setAttribute('hidden','hidden');
            avanzarRetrocederSemana(numSem-2, 2);
        }else{
            $.smallBox({color: "alert", content: "<i>No existe semana anterior a la actual...<i>"})
        }

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
           // instanciarElementoTooltips(nuevoEle);
         //   instanciarElementoPopovers(nuevoEle);
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
         //   instanciarElementoTooltips(nuevoEle);
         //   instanciarElementoPopovers(nuevoEle);

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
    } else if(clases.contains('in-sub-elemento')) {
        
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

           /* const nueEle = RutinaDOMQueries.getElementoByIxs(ixs).querySelector('.panel-heading').children[0];
            instanciarElementoTooltips(nueEle);
            instanciarElementoPopovers(nueEle);
           */
        }
    }

    else if(clases.contains('in-sub-ele-esp-pos')) {
        
        const valor = input.value.trim();
        if (valor.length >= 1) {
            let ixs = RutinaIx.getIxsForSubElemento(input);
            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
            console.log("elemento", tempElemento);
            let tempSubEle = RutinaDOMQueries.getPreSubElementoByIxs(ixs);
            tempSubEle = tempSubEle.parentElement;
            console.log("subelemento" , tempSubEle);
            let initTempSubEleRef = tempSubEle;
            let i = 0, k=0;
            while ((tempElemento = tempElemento.previousElementSibling) != null) i++;
            while((tempSubEle = tempSubEle.previousElementSibling))k++;
            const nuevoIx = RutinaSeccion.newSubElementoPosEspecifica(ixs.diaIndex, ixs.eleIndex, TipoElemento.TEXTO, valor, 'afterend', initTempSubEleRef);
            ixs.subEleIndex = nuevoIx;
            RutinaSet.setSubElementoNombre(ixs.numSem, ixs.diaIndex, i, k, valor);
            const subEle = RutinaDOMQueries.getSubElementoByIxs(ixs);
            //instanciarSubElementoTooltip(subEle);
            //instanciarSubElementoPopover(subEle);
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

                while ((tempElemento = tempElemento.previousElementSibling) != null) i++;
                let tempSubElemento = RutinaDOMQueries.getSubElementoByIxs(ixs).parentElement;

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

            if(tempElemento.querySelector('.notes .gr')==undefined){
                tempElemento.querySelector('.notes').appendChild(htmlStringToElement(RutinaElementoHTML.iconoNotaT(nota)));
            }else{
                if(nota.length == 0){
                    tempElemento.querySelector(`.rf-dia-elemento-nombre`).setAttribute('data-content', '');
                    tempElemento.querySelector(`.notes .gr`).setAttribute('data-content', '');

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
        let tempSubEle = RutinaDOMQueries.getSubElementoByIxs(ixs).parentElement;
        const divNota = e.target.parentElement.parentElement;

        let anteriorNota = tempSubEle.querySelector(`.rf-sub-elemento-nombre`).getAttribute('data-content');anteriorNota = anteriorNota == undefined?'':anteriorNota;
        if(anteriorNota.trim() != nota){

            if(tempSubEle.querySelector('.notes .gr')==undefined){
                tempSubEle.querySelector('.notes').appendChild(htmlStringToElement(RutinaElementoHTML.iconoNotaT(nota)));
            }else{
                if(nota.length == 0){
                    tempSubEle.querySelector('.notes .gr').remove();
                    tempSubEle.querySelector(`.rf-sub-elemento-nombre`).setAttribute('data-content', '');
                    tempSubEle.querySelector(`.notes .gr`).setAttribute('data-content', '');
                }
            }
            tempSubEle.querySelector(`.rf-sub-elemento-nombre`).setAttribute('data-content', nota);

            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
            let i = 0, k = 0;
            while ((tempElemento = tempElemento.previousElementSibling) != null) i++;
            while ((tempSubEle = tempSubEle.previousElementSibling) != null) k++;
            RutinaSet.setSubElementoNota(ixs.numSem, ixs.diaIndex, i, k, nota);

            actualizarSubElementoNotaBD(nota, ixs.numSem, ixs.diaIndex, (posElemento = i), (postSubElemento = k));
         //   divNota.remove();
        }else{
        //    divNota.remove();
        }
    }
    /*




     else if(clases.contains('agregar-kms')){}

 */
}


function principalesEventosClickRutina(e) {
    const clases = e.target.classList;
    let input = e.target;

    
    if(clases.contains('in-ele-dia-1')){
        if(validUUID($mediaAudio) || validUUID($mediaVideo)){
            const dvInputsInit = input.parentElement.parentElement.parentElement;
            dvInputsInit.classList.toggle('hidden');
            const ixs = RutinaIx.getIxsForDia(input);
            ElementoOpc.agregarInitMediaElemento(ixs, ElementoTP.SIMPLE);
        }
    }

    else if(clases.contains('in-ele-dia-2')){
        if(validUUID($mediaAudio) || validUUID($mediaVideo)){
            const dvInputsInit = input.parentElement.parentElement.parentElement;
            dvInputsInit.classList.toggle('hidden');
            const ixs = RutinaIx.getIxsForDia(input);
            ElementoOpc.agregarInitMediaElemento(ixs, ElementoTP.COMPUESTO);
        }
    }
    else if(clases.contains('in-ele-dia-esp-pos')){
        if(validUUID($mediaAudio) || validUUID($mediaVideo)){
            
            const valor = $mediaNombre;
            let ixs = RutinaIx.getIxsForElemento(input);
            let tempElemento = RutinaDOMQueries.getPreElementoByIxs(ixs);
            let tipo = input.getAttribute('data-ele-tipo');
            let initTempElementoRef = tempElemento;
            let i = 0;
            while ((tempElemento = tempElemento.previousElementSibling) != null) i++;
            const nuevoIx = RutinaSeccion.newElementoPosEspecifica(ixs.diaIndex, tipo, valor, 'afterend', initTempElementoRef);
            ixs.eleIndex = nuevoIx;
            const nuevoElemento = RutinaDOMQueries.getElementoByIxs(ixs).querySelector('.rf-dia-elemento-nombre');
            ElementoOpc.agregarMediaElemento(ixs, nuevoElemento , tipo, (posEle = i));
            initTempElementoRef.remove();
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
            let initElemento = tempElemento;
            let nSubEle = tempElemento.querySelector(`div[data-index="${nuevoIx}"]`);
            let i = 0;
            while ((tempElemento = tempElemento.previousElementSibling) != null) i++;
            RutinaAdd.nuevoSubElementoMedia(ixs.numSem, ixs.diaIndex, i, obj);
            alert("cuack");
            agregarSubElementoAElementoBD(ixs.numSem, ixs.diaIndex, i, 0); //Siempre va ser el primero por eso se deja la posicion como 0
            const iconoAdd = RutinaDOMQueries.getSubElementoByIxs(ixs).querySelector('.ele-add');
            const dvMediaElements =  RutinaDOMQueries.getSubElementoByIxs(ixs).querySelector('.notes');;

            
            if($tipoMedia == TipoElemento.AUDIO){
                dvMediaElements.appendChild(htmlStringToElement(`<div class="ong" rel="tooltip" data-media="${$mediaAudio}" data-original-title="Audio"></div>`));
            }else{
                iconoAdd.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoVideo($mediaVideo));
            }


            $(initElemento.querySelector(`.in-init-sub-ele`)).closest('li').remove();  //toggleClass('hidden');


            //  instanciarSubElementoTooltip(nSubEle);
          //  instanciarSubElementoPopover(nSubEle);

            $mediaAudio = '';
            $mediaVideo = '';
        }
    }



    else if(clases.contains('in-sub-ele-esp-pos')){
        if(validUUID($mediaAudio) || validUUID($mediaVideo)){
            
            const valor = $mediaNombre;
            let ixs = RutinaIx.getIxsForSubElemento(input);
            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
            let tempSubEle = RutinaDOMQueries.getPreSubElementoByIxs(ixs);
            let initTempSubEleRef = tempSubEle.parentElement;

            let i = 0, k=0;
            while ((tempElemento = tempElemento.previousElementSibling) != null) i++;
            while((tempSubEle = tempSubEle.previousElementSibling))k++;
            const nuevoIx = RutinaSeccion.newSubElementoPosEspecifica(ixs.diaIndex, ixs.eleIndex, validUUID($mediaAudio) ? TipoElemento.AUDIO : TipoElemento.VIDEO, valor, 'afterend', initTempSubEleRef);
            ixs.subEleIndex = nuevoIx;
            SubEleOpc.agregarMediaSubElemento(ixs, RutinaDOMQueries.getSubElementoByIxs(ixs).querySelector('.rf-sub-elemento-nombre'), i, k);
            initTempSubEleRef.remove();
        }
    }
  /*
    if(e.ctrlKey){
        if(clases.contains('rf-dia-elemento-nombre')){
            DiaOpc.seleccionarElementos(input);
        }else if(clases.contains('rf-sub-elemento-nombre')){
            DiaOpc.seleccionarSubElementos(input);
        }
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
    else if(clases.contains('agregar-kms')){
        e.preventDefault();
        e.stopPropagation();
        const ixs = RutinaIx.getIxsForElemento(input);
        ElementoOpc.verDistanciaElemento(ixs, input);
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

    */
    else if(clases.contains('insertar-debajo')) {

         
         e.preventDefault();
         e.stopPropagation();
         let ixs = RutinaIx.getIxsForElemento(input);
         let elemento = RutinaDOMQueries.getElementoByIxs(ixs);
         let ix = ++indexGlobal;
         let refIndex = ixs.eleIndex;

         elemento.insertAdjacentHTML('afterend', `<div class="panel-group elem ne-esp rf-dia-pre-elemento" data-dia-index="${ixs.diaIndex}"
                                                           data-index="${ix}">
                                                           <div class="row">
                                                            <div class="col-xs-6">
                                                                    <input type="text" class="cs-input in-ele-dia-esp-pos" maxlength="121" placeholder=""
                                                                      data-dia-index="${ixs.diaIndex}" data-ele-tipo="${ElementoTP.SIMPLE}" data-index="${ix}"
                                                                      data-ele-ref-index="${refIndex}"  data-strategy="afterend"/>
                                                            </div>
                                                            <div class="col-xs-6" data-ele-tipo="${ElementoTP.COMPUESTO}" data-index="${ix}">
                                                                      <input type="text" class="cs-input in-ele-dia-esp-pos" maxlength="121" placeholder="" data-dia-index="${ixs.diaIndex}"
                                                                       data-ele-tipo="${ElementoTP.COMPUESTO}" data-index="${ix}" data-ele-ref-index="${refIndex}"
                                                             data-strategy="afterend"/>
                                                            </div>
                                                           </div>
                                                          </div> 
                                                          `);

         let i=0;
         while((elemento = elemento.previousElementSibling))i++;
         RutinaAdd.nuevoElemento(ixs.numSem, ixs.diaIndex, i, '');
         agregarElementoEnBlancoBD(ixs.numSem, ixs.diaIndex, ElementoTP.NO_DEFINIDO, (posRefElemento = i), Estrategia.INSERT_DESPUES);
     }


     else if(clases.contains('insertar-debajo-sub')){
         ;
         e.stopPropagation();
         let ixs = RutinaIx.getIxsForSubElemento(input);
         let elemento = RutinaDOMQueries.getElementoByIxs(ixs);
         let subElemento = RutinaDOMQueries.getSubElementoByIxs(ixs).parentElement;
         let ix = ++indexGlobal;
         console.log("subelemento",subElemento);
         subElemento.insertAdjacentHTML('afterend', `<li>
                                                                      <div class="row rf-pre-sub-elemento" data-dia-index="${ixs.diaIndex}"
                                                                            data-ele-index="${ixs.eleIndex}" data-index="${ix}">
                                                                        <div class="col-xs-10">
                                                                          <input type="text" class="cs-input in-sub-ele-esp-pos" data-dia-index="${ixs.diaIndex}"
                                                                            data-ele-index="${ixs.eleIndex}" data-index="${ix}" data-strategy="afterend"/>    
                                                                        </div>
                                                                      </div>  
                                                                  </li>
                                                                `);
         let i=0,k=0;
         while((elemento = elemento.previousElementSibling))i++;
         while((subElemento = subElemento.previousElementSibling))k++;
         RutinaAdd.nuevoSubElemento(ixs.numSem, ixs.diaIndex, i, k, '');
         agregarSubElementoEnBlancoBD(ixs.numSem, ixs.diaIndex, ElementoTP.NO_DEFINIDO, i, (posRefSubEle = k), Estrategia.INSERT_DESPUES);
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

     else if(clases.contains('agregar-tiempo')){
         //Sirve para comparar el valor inicial del elemento con el valor que retorna en el evento focusout con el fin de evitar actualizaciones innecesarias
         
         e.preventDefault();
         e.stopPropagation();
         e.target.select();
         $tiempoActualizar = Number(e.target.value.trim());
         $gIndex = e.target.getAttribute('data-index');
     }

     else if(clases.contains('rf-sub-elemento-nombre')){
         e.preventDefault();
         e.stopPropagation();
         if(validUUID($mediaAudio) || validUUID($mediaVideo)){
             const ixs = RutinaIx.getIxsForSubElemento(input);
             alert("xdd");
             SubEleOpc.agregarMediaToSubElemento2(ixs, input);
         }
         //Sirve para despues de guardar el valor del input en el onclick validar que este haya sido o no modificado para conforme a eso actualizar en el focusout
         //o evitar actualizaciones innecesarias
         $nombreActualizar = e.target.textContent.trim();
     }
    /*


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

  /*  else if(clases.contains('pegar-mini')) {
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
        }

    }*/
}

function principalesAlCambiarTab(e){
    const input = e.target;
   /* if(e.target.classList.contains('main-tab')){
        document.querySelector('#OpsAdic').classList.remove('hidden');
        document.querySelector('#DivEditor').classList.remove('hidden');
    }

    else if(input.nodeName == "A" && input.getAttribute('href') == '#tabRutinarioCe') {
        document.querySelector('#DivEditor').classList.add('hidden');
        if(document.querySelector('#ArbolRutinario').children.length == 0) {
            instanciarMiniPlantillas();
        }
    }
    else*/

    if(input.nodeName == "A" && input.getAttribute('href') == '#tabGrupoAudios') {
        e.preventDefault();
      //  document.querySelector('#OpsAdic').classList.add('hidden');
      //  document.querySelector('#DivEditor').classList.add('hidden');
        if (document.querySelector('#ArbolGrupoAudio').children.length == 0) {
            instanciarGrupoAudios();
        }


    }

    else if(input.nodeName == "A" && input.getAttribute('href') == '#tabGrupoVideos') {
        e.preventDefault();
     //   document.querySelector('#OpsAdic').classList.add('hidden');
     //   document.querySelector('#DivEditor').classList.add('hidden');
        $videosElegidos = [];
        $subEleElegidos = [];
        Array.from(document.getElementById('ArbolGrupoVideoDetalle').querySelectorAll('.txt-color-greenIn')).forEach(e => e.classList.remove('txt-color-greenIn'));
        if (document.querySelector('#ArbolGrupoVideo').children.length == 0) {
            instanciarGrupoVideos();
        }
    }
    /*   else if(e.target.tagName === "A"){
           document.querySelector('#OpsAdic').classList.add('hidden');
           document.querySelector('#DivEditor').classList.add('hidden');
           if(input.getAttribute('href') == '#tabFichaTecnica'){
               if($ruConsolidado == undefined){
                   obtenerRutinaConsolidadoBD();
               }
           }


       }  */
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



   /*
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
    }*/
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
        clases.add('txt-color-greenIn'); //cambiar
        $memoriaAudio = input;
        $mediaNombre = input.textContent.trim();
        $mediaAudio = input.querySelector('.reprod-audio').getAttribute('data-media');
        $mediaVideo = '';
        $tipoMedia = TipoElemento.AUDIO;
        cambiarATabRutina();
    }
    else if(clases.contains('ck')){
        alert("eureka");
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
        var idAudio = parent.querySelector('.ck-favorito-audio').getAttribute('data-id');
        //console.log(id);
        var selected = $(parent.querySelector('.ck-favorito-audio')).attr("data-selected");
        var editarAgregar = 0;
        if(selected == "1"){
            $(parent.querySelector('.ck-favorito-audio')).css("color","#3276b1");
            $(parent.querySelector('.ck-favorito-audio')).attr("data-selected", "0");
            editarAgregar = 0;
        }else {
            $(parent.querySelector('.ck-favorito-audio')).attr("data-selected", "1");
            $(parent.querySelector('.ck-favorito-audio')).css("color","#d8d807");
            editarAgregar = 1;
        }

       agregarEliminarFavorito(0,idAudio,editarAgregar);
    }

}


function principalesDivEditor(e){
    const input = e.currentTarget;
    const clases = input.classList;

    console.log(input);
    const ix = input.getAttribute('data-index');
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

function avanzarRetrocederSemana(numSem, action){

    obtenerEspecificaSemana(numSem, action).then((semana)=> {
       if(semana != undefined) {
           // $('#rutinaSemana').html(`<h1 style="padding-left: 18%; font-size: 5em;">Por favor espere... <i class="fa fa-spinner fa-spin"></i></h1>`);
            //Importante mantener el orden para el correcto funcionamiento
            $rutina = new Rutina(rutinaData);
            $rutina.initEspecifico(semana, numSem);
            instanciarTooltips();
       //   generarDiasEnviados();
       //   parentDiv.removeAttribute('hidden');
        }
    });
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

function agregarSubElementoAElementoBD(numSem, diaIndex, listaIndex , elementoIndex){
   // alert("xd");
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

    console.log("papu",params);
    $.ajax({
        type: "PUT",
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        url: _ctx + "gestion/rutina/sub-elemento/actualizar",
        dataType: "json",
        data: params,
        success: function (data) {
           console.log("success",data);
            notificacionesRutinaSegunResponseCode(data);
        },
        error: function (xhr) {
            exception(xhr);
            console.log("xhr",xhr);
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

                 console.log("especifica",data);
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

function listaNoRepetida(ix, nombre) {
    let val = true;
    document.querySelectorAll(`.rf-dia[data-index="${ix}"] .panel-default .lista-title`).forEach((v) => {
        if (v.textContent.trim() === nombre) {
            val = false;
        }
    });
    return val;
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



function instanciarGrupoAudios(){

    $.ajax({
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'gestion/tipo-audio/obtener/arbol',
        blockLoading: true,
        noOne: false,
        dataType: "json",
        success: function (data, textStatus) {
            console.log("audio", data);
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


function instanciarGrupoVideos(){

    $.ajax({
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'gestion/video/obtener/arbol',
        blockLoading: true,
        noOne: false,
        dataType: "json",
        success: function (data, textStatus) {

         console.log("videoos",data);
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
                                        <h1 class="text-center txt-color-white padding-7 bg-color-blue-sl"><img class="pull-left" height="80px" src="https://s3-us-west-2.amazonaws.com/rf-media-rutina/grupo-video/${grupoVideo.id}/${grupoVideo.rutaWeb}">${grupoVideo.nombre}</h1>
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
                                    <h1 class="text-center txt-color-white bg-color-blue-sl padding-10">${catVideo.nombre}</h1>
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
                          <i data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-video fa fa-video-camera fa-fw" data-media="/${v.id+'/'+v.rutaWeb}" data-index="${v.id}">
                          </i>${v.nombre}</a>`;
    })
    return rawVideosHTML;

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

function removerSubElementoBD(numSem, diaIndex, eleIndex, subEleIndex, distancia, calorias){
    let params = {};
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



function instanciarTooltips(){
    $('[rel="tooltip"]').tooltip();
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
    $body.animate({scrollTop: 0},300);
}
