//Variables requeridas
let indexGlobal = 0;
let $body = $("html, body");
let $rutina;
let $diaPlantilla;
let $diaPlantillas;
let $mediaAudio = '';
let $mediaNombre = '';
let $mediaVideo = '';
let $kmsActualizar = '';
let $tipoMedia = '';
let $eleListas = [];
let $eleListasElegidos = [];
let $videosElegidos = [];
let $refIxsSemCalendar = [];
let $fechasCompetencia = [];
let $eleGenerico;
let $estilosCopiados = [];
let $statusCopy = false;
let $kilometrajeBase = [];
let $semCalculoMacro = {};
let $edcPorcIntensidad = false;
let $chartTemporada = {};
let $chartMiniPorc = {};
let $chartC1 = {};
let $chartC2 = {};
let $preHtmlButton;
let $idsComp = [];
let $antPorcKilo = "";
let $baseAfterComprobacion;

//Contenedores y constantes
const $semActual = document.querySelector('#SemanaActual');
const $semanario = document.querySelector('#RutinaSemana');
const btnGuardarMini = document.querySelector('#btnGuardarMini');
const tabFichaTecnica = document.querySelector('#tabFichaTecnica');
const catRutinasDiaIndex = document.querySelector('#CatRutinasDiaIndex');
const tbCompetencias = document.querySelector('#TablaCompetencias');
const btnComprobarMacro = document.querySelector('#btnComprobar');
const btnGenerarRutinaCompleta = document.querySelector('#btnGenerarRutina');
const nivelAtletaRdBtn = document.querySelector('#NivelAtleta');
const distAtletaRdBtn = document.querySelector('#DistanciaRutina');
const fInitMacro = document.querySelector('#MacroFechaInicio');
const fFinMacro = document.querySelector('#MacroFechaFin');
const bgBarMainGraph = ["#ed8989c7","#4fd46bc4","#87ceebbd","#519da4a6"];
const bgMantaIntensidad = ["gold", "gray", "skyblue", "gray"];



$(function () {
    init();
})



function cssUpdateGraphBackground(e){
    const hex = e.value;
    const pos = e.getAttribute('data-pos');
    const rgb = hexToRgb("#"+hex).join(', ');
    const lastRGB = $('#GraficoTemporada').css('background-image').split('rgb(').slice(1).map(v=>v.substring(0,v.indexOf(")")));
    lastRGB[pos] = rgb;
    const nuevoRGB = `linear-gradient(to bottom, rgb(${lastRGB[0]}), rgb(${lastRGB[1]}), rgb(${lastRGB[2]})`;
    $('#GraficoTemporada').css('background-image', nuevoRGB);
}

function cssUpdateGraphBackgroundBarras(e){
    const hex = e.value;
    const pos = e.getAttribute('data-pos');
    const rgb = hexToRgb("#"+hex).join(', ') + ', 1';
    const rgba = `rgba(${rgb})`;
    bgBarMainGraph[pos] = rgba;
    document.querySelector('#btnComprobar').click();
}

function cssUpdateGraphBackgroundMantaIntensidad(e){
    const hex = e.value;
    const pos = e.getAttribute('data-pos');
    const rgb = hexToRgb("#"+hex).join(', ');
    const rgba = `rgba(${rgb}, 0.27)`;
    bgMantaIntensidad[pos] = rgba;
    document.querySelector('#btnComprobar').click();
}

const hexToRgb = hex =>
    hex.replace(/^#?([a-f\d])([a-f\d])([a-f\d])$/i
        ,(m, r, g, b) => '#' + r + r + g + g + b + b)
        .substring(1).match(/.{2}/g)
        .map(x => parseInt(x, 16))

function init(){
    MacroValidacion.formulario();
    instanciarDatosFitnessCliente();
    tabFichaTecnica.addEventListener('click', principalesEventosTabFichaTecnica);
    tabFichaTecnica.addEventListener('focusout', principalesEventosFocusOutTabFichaTecnica);
    btnComprobarMacro.addEventListener('click', MacroCiclo.comprobar);
    btnGenerarRutinaCompleta.addEventListener('click', MacroCiclo.generarRutinaCompleta);
    Array.from(nivelAtletaRdBtn.querySelectorAll('.chkNivel')).forEach(v=>v.addEventListener('change', MacroCiclo.instanciarKilometrajeBase));
    Array.from(distAtletaRdBtn.querySelectorAll('.chkDistancia')).forEach(v=>v.addEventListener('change', MacroCiclo.instanciarKilometrajeBase));
    fInitMacro.addEventListener('change', FichaSet.setTotalSemanas);
    fFinMacro.addEventListener('change', FichaSet.setTotalSemanas);
    setFechaActual(document.querySelectorAll('input[type="date"]'));
 /*   setTimeout(() => {
        obtenerKilometrajeBaseBD(Number(document.querySelector('#DistanciaRutina input:checked').value), Number(document.querySelector('#NivelAtleta input:checked').value));
    }, 1100); */
}

function instanciarTooltips(){
    $('[rel="tooltip"]').tooltip();
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
    $body.animate({scrollTop: 0},300);
}

function instanciarDatosFitnessCliente(){
    $.ajax({
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: _ctx + 'gestion/cliente-fitness/obtener/secundario/'+ getParamFromURL('rn'),
        dataType: "json",
        blockLoading: false,
        success: function (data, textStatus) {



            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                }else{
                    if(data.id != 0) {
                        Ficha.instanciar(data);
                        FichaSet.setTotalSemanas();
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

async function instanciarPorcentajesKilometraje(distancia){
    return new Promise((res, rej)=>{
        if($antPorcKilo.length == 2 && $antPorcKilo[0] == distancia)
            res($antPorcKilo[1]);
        else
            $.ajax({
                type: 'GET',
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                url: _ctx + 'calculo/porcentajes-kilo/obtener/'+distancia,
                dataType: "json",
                success: function (data, textStatus) {
                    if (textStatus == "success") {
                        notificacionesRutinaSegunResponseCode(data);
                        res(data);
                        $antPorcKilo = [distancia, data];
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

function actualizarPorcentajesKilometrajeBD(porcentajes, e){
    e.classList.toggle('text-primary');
    e.classList.toggle('txt-color-grey');
    e.classList.toggle('actualizar-porcentajes');
    $.ajax({
        type: 'PUT',
        contentType: "application/json",
        url: _ctx + 'calculo/porcentajes-kilo/actualizar',
        dataType: "json",
        data: JSON.stringify(porcentajes),
        success: function (data) {
            if(notificacionesRutinaSegunResponseCode(data) != undefined){
                $.smallBox({content: "<i>Actualización de porcentajes exitosa...</i>"})
                setTimeout(()=>{
                    e.classList.toggle('text-primary');
                    e.classList.toggle('txt-color-grey');
                    e.classList.toggle('actualizar-porcentajes');}, 2000);
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

function principalesEventosTabFichaTecnica(e){



    const input = e.target;
    const clases = input.classList;

    if(clases.contains('actualizar-porcentajes')){
        e.preventDefault();
        e.stopPropagation();
        const base = FichaGet.obtenerBase();
        if(!$edcPorcIntensidad){//Provisional
            actualizarPorcentajesKilometrajeBD(MacroCicloGet.obtenerPorcentajesParaActualizacion(base), input);
        }
    }else if(clases.contains('regular-intensidad')){
        e.preventDefault();
        e.stopPropagation();
        $edcPorcIntensidad = true;
        $slideType = 2;
        document.querySelector('#ContainerVarVolumen .volver-kms-distribucion').classList.toggle('hidden');
        clases.toggle('hidden');
        document.querySelector('#PorcentajesKilometraje').classList.toggle('hidden');
        document.querySelector('#PorcentajesIntensidad').classList.toggle('hidden');
    }else if(clases.contains('volver-kms-distribucion')){
        e.preventDefault();
        e.stopPropagation();
        $edcPorcIntensidad = false;
        $slideType = 1;
        document.querySelector('#ContainerVarVolumen .regular-intensidad').classList.toggle('hidden');
        document.querySelector('#PorcentajesKilometraje').classList.toggle('hidden');
        document.querySelector('#PorcentajesIntensidad').classList.toggle('hidden');
        clases.toggle('hidden');
    }else if(clases.contains('periodizacion-calc')) {

        input.select();
    }else if(clases.contains('velocidad-calc')) {
        input.select();
    }else if(clases.contains('cadencia-calc')) {
        input.select();
    }else if(clases.contains('tcs-calc')) {
        input.select();
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
    else if(clases.contains('tiempo-control')){

    }
    else if(clases.contains('factor-desentrenamiento')){

    }

}

function guardarRutina(rutina){
    const id = getParamFromURL('key');
    const rn = getParamFromURL('rn');
    const fechaNac = getParamFromURL('fn');
    const cliFullNombre = getParamFromURL('nm');
    const datosAsURI = '&fn=' + fechaNac + '&nm=' + cliFullNombre;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: _ctx + "gestion/rutina/nueva?key=" + id + "&rn=" + rn,
        dataType: "json",
        blockLoading: true,
        noOne: false,
        data: JSON.stringify(rutina),
        success: function (data) {
            const resWithErrors = getResponseCodeWithErrors(data);
            resWithErrors != false ? notificacionesRutinaSegunResponseCode(resWithErrors.code, RutinaParsers.obtenerErroresValidacion(resWithErrors.errors)) : notificacionesRutinaSegunResponseCode(data);
            data == "-1" ? window.location.href = _ctx + 'rutina/editor?key=' + id + '&rn=' + rn + datosAsURI: setTimeout(()=>$('#bot1-Msg1').click());//res.id para el caso = redFitnessId;
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {}
    })

}
