// jqueryValidation's styles
let errorClass = 'invalid';
let errorElement = 'em';
// !- END var for jqueryValidation's styles
let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");
var _ctx = $('meta[name="_ctx"]').attr('content');
let $buttonName = "";
let $gbInterval = 0;
var _URL = window.URL || window.webkitURL;

$('#view_register').attr('hidden', 'hidden');
const ClaseEditor = Object.freeze([
    {id: 1, clase: 'rf-n', tipo: 1},
    {id: 2, clase: 'rf-k', tipo: 1},
    {id: 3, clase: 'rf-s', tipo: 1},
    {id: 4, clase: 'rf-align-l', tipo: 2},
    {id: 5, clase: 'rf-align-c', tipo: 2},
    {id: 6, clase: 'rf-align-r', tipo: 2},
    {id: 7, clase: 'rf-mg-1', tipo: 3},
    {id: 8, clase: 'rf-mg-2', tipo: 3},
    {id: 9, clase: 'rf-mg-3', tipo: 3},
    {id: 50, clase: 'rf-fs-8', tipo: 1},
    {id: 51, clase: 'rf-fs-9', tipo: 1},
    {id: 52, clase: 'rf-fs-10', tipo: 1},
    {id: 53, clase: 'rf-fs-11', tipo: 1},
    {id: 54, clase: 'rf-fs-12', tipo: 1},
    {id: 55, clase: 'rf-fs-13', tipo: 1},
    {id: 56, clase: 'rf-fs-14', tipo: 1},
    {id: 57, clase: 'rf-fs-15', tipo: 1},
    {id: 58, clase: 'rf-fs-16', tipo: 1},
    {id: 59, clase: 'rf-fs-17', tipo: 1},
    {id: 60, clase: 'rf-fs-18', tipo: 1},
    {id: 100, clase: 'rf-ct-white', tipo: 1},
    {id: 101, clase: 'rf-ct-black', tipo: 1},
    {id: 102, clase: 'rf-ct-gray', tipo: 1},
    {id: 103, clase: 'rf-ct-navyblue', tipo: 1},
    {id: 104, clase: 'rf-ct-skyblue', tipo: 1},
    {id: 105, clase: 'rf-ct-orange', tipo: 1},
    {id: 106, clase: 'rf-ct-yellow', tipo: 1},
    {id: 107, clase: 'rf-ct-green', tipo: 1},
    {id: 108, clase: 'rf-ct-m-pink', tipo: 1},
    {id: 109, clase: 'rf-ct-m-blue', tipo: 1},
    {id: 110, clase: 'rf-ct-m-gray', tipo: 1},
    {id: 111, clase: 'rf-ct-m-navyblue', tipo: 1},
    {id: 112, clase: 'rf-ct-m-skyblue', tipo: 1},
    {id: 113, clase: 'rf-ct-m-orange', tipo: 1},
    {id: 114, clase: 'rf-ct-m-yellow', tipo: 1},
    {id: 115, clase: 'rf-ct-m-green', tipo: 1},
    {id: 200, clase: 'rf-bg-white', tipo: 2},
    {id: 201, clase: 'rf-bg-black', tipo: 2},
    {id: 202, clase: 'rf-bg-gray', tipo: 2},
    {id: 203, clase: 'rf-bg-navyblue', tipo: 2},
    {id: 204, clase: 'rf-bg-skyblue', tipo: 2},
    {id: 205, clase: 'rf-bg-orange', tipo: 2},
    {id: 206, clase: 'rf-bg-yellow', tipo: 2},
    {id: 207, clase: 'rf-bg-green', tipo: 2},
    {id: 208, clase: 'rf-bg-m-pink', tipo: 2},
    {id: 209, clase: 'rf-bg-m-blue', tipo: 2},
    {id: 210, clase: 'rf-bg-m-gray', tipo: 2},
    {id: 211, clase: 'rf-bg-m-navyblue', tipo: 2},
    {id: 212, clase: 'rf-bg-m-skyblue', tipo: 2},
    {id: 213, clase: 'rf-bg-m-orange', tipo: 2},
    {id: 214, clase: 'rf-bg-m-yellow', tipo: 2},
    {id: 215, clase: 'rf-bg-m-green', tipo: 2},
]);
const Estrategia = Object.freeze({
    INSERT_ANTES:   1,
    INSERT_DESPUES:  2,
});
const ElementoTP = Object.freeze({
    SIMPLE:   1,
    COMPUESTO:  2,
    NO_DEFINIDO: 3,
});
const TipoElemento = Object.freeze({
    AUDIO:   1,
    VIDEO:  2,
    TEXTO: 3
});

const EstadoPlan = Object.freeze({
    SIN_PLAN:   1,
    EN_REVISION:   2,
    POR_ENVIAR:  3,
    INICIADO: 4,
    COLMINADO: 5,
});

const TipoDato = Object.freeze({
    PORCENTUAL: 1,
    NUMERICO: 2,
})

const TipoRutina = Object.freeze({
    ESPECIFICA:   1,
    GENERAL:   2,
    COMPETITIVA:  3,
});

const ResponseCode = Object.freeze({
    REGISTRO: -1,
    ACTUALIZACION: -2,
    ELIMINACION: -3,
    EX_GENERIC: -9,
    EX_NULL_POINTER: -10,
    EX_JACKSON_INVALID_FORMAT: -11,
    EX_NUMBER_FORMAT: -99,
    EX_MAX_SIZE_MULTIPART: -100,
    EX_MAX_UPLOAD_SIZE: -101,
    VF_USUARIO_REPETIDO: -150,
});

const dias = ["Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado"];
const exceptNames = {"-9": "EX_NULL_POINTER", "-10": "EX_NULL_POINTER", "-99": "EX_NUMBER_FORMAT", "-100": "EX_MAX_SIZE_MULTIPART", "-101": "EX_MAX_UPLOAD_SIZE"};
const meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
const regexMail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const banks = [
    {id: 1,nombre:	"Banco de Comercio"}, {id: 2,nombre:	"Banco de Crédito del Perú"}, {id: 3,nombre:	"Banco Interamericano de Finanzas (BanBif)"}, {id: 4,nombre:	"Banco Pichincha"}, {id: 5,nombre:	"BBVA Continental"}, {id: 6,nombre:	"Citibank Perú"}, {id: 7,nombre:	"Interbank"}, {id: 8,nombre:	"MiBanco"}, {id: 9,nombre:	"Scotiabank Perú"}, {id: 10,nombre:	"Banco GNB Perú"}, {id: 11,nombre:	"Banco Falabella"}, {id: 12,nombre:	"Banco Ripley"}, {id: 13,nombre:	"Banco Santander Perú"}, {id: 14,nombre:	"Banco Azteca"}, {id: 15,nombre:	"Banco Cencosud"}, {id: 16,nombre:	"ICBC PERU BANK"}];

function limpiarMainForm() {
    $('#frm_registro').trigger("reset");
    $("#frm_registro").validate().resetForm();
}

function limpiarFiltros() {
    $("#accordion input").val("");
    $("#accordion select").val(0);
    $("#accordion select").val("");
    $("#accordion #pddlEstado").val("");
}

function detail_show() {
    $(".content-modal").show().removeClass('slideOutLeft animated').addClass('fadeInLeftBig' + ' animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {

    });
}

function detail_hide() {
    $(".content-modal").removeClass('fadeInLeftBig animated').addClass('slideOutLeft' + ' animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
        $(".content-modal").hide();
    });
}

function irRegistro(wildcard) {
    $('#btnNuevo').css('display', 'none');
    $('#view_list').removeClass().addClass('fadeOutRightBig' + ' animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
        $('#view_list').hide();

        $("#view_register").show().removeClass().addClass('fadeInLeftBig' + ' animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
        });
    });
    wildcard != undefined ? "" : limpiarMainForm();
}

function irListado(x) {
    x[0]["defaultValue"] = 0;//EQUALS TO HIDDEN ID
    $('#view_register').removeClass().addClass('fadeOutRightBig' + ' animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
        $('#view_register').hide();
        $('#btnNuevo').css('display', 'block');
        $("#view_list").show().removeClass().addClass('fadeInLeft' + ' animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
        });
    });
}

function reqSuccess(r, timeout){
    const miliseconds = timeout !== undefined ? timeout : 10000;
    if(isNaN(r.res)){
        const msg = r.res;
        $("#frm_registro :input").prop("disabled", true);
        $.smallBox({content: "<i class='fa fa-check'></i> "+msg,
                    color: '#111509',
                    timeout: miliseconds
        });
    } else{
        $.smallBox({});
    }
}

function disabledForm(frmId){
    $(`#${frmId} input`).prop('disabled', true);
    $(`#${frmId} textarea`).prop('disabled', true);
    $(`#${frmId} checkbox`).prop('disabled', true);
    $(`#${frmId} radio`).prop('disabled', true);
}

function exception(xhr, errorName) {
    console.log(xhr);
    const sCode = xhr['status'];

    if(typeof xhr == 'number' && xhr < 0){
        exception2(xhr, errorName);
    }else{
        if (xhr["responseJSON"] != null || JSON.parse(xhr["responseText"])["status"] == "401" ) {

            if(sCode != 401 && sCode != 403 && sCode != 400 && sCode !== 409){
                $.smallBox({
                    content: "<i> Comuníquese con el administrador <br/> Code error: " + xhr['status'] + " <br/> Detail: " + xhr['responseJSON']['error'] + "</i>",
                    color: "#8a6d3b",
                    iconSmall: "fa fa-warning fa-2x fadeInRight animated",
                    timeout: 5000,
                });
            }else{
                if(xhr['status'] == 401){
                    $.SmartMessageBox({
                        title: "<i class='fa fa-info fa-2x fa-fw'></i> <b>Workout Notification</b>",
                        content: "<div class='font-md'><i> Su sesión ha expirado, usted será redireccionado a la página de login...</div></i>",
                        buttons: '[OK]'
                    }, function (ButtonPressed) {
                        if(ButtonPressed == 'OK'){
                            window.location.href = _ctx+'login';
                        }
                    })
                }
                else if(xhr['status'] == 403){
                    $.smallBox({
                        content: "<i> No se encuentra autorizado para <br/> ejecutar esta acción...</i>",
                        color: "#8a6d3b",
                        iconSmall: "fa fa-info fa-3x fadeInRight animated",
                        timeout: 5000,
                    });
                } else if(xhr['status'] == 400){
                    if(typeof xhr.responseJSON == 'object' && typeof xhr.responseJSON.message !== 'undefined' && xhr.responseJSON.message === 'Validation has failed'){
                        const errors = [];
                        xhr.responseJSON.subErrors.forEach(v=>{
                            const field = capitalizeFirstLetter(v.object);
                            const ele = document.getElementById(field);
                            ele != undefined ? ele.classList.toggle('state-error') : "";
                            errors.push(`${field}: ${capitalizeFirstLetter(v.message)}`);

                        });
                        smallBoxAlertValidation2(errors);
                    }else{

                            $.smallBox({
                                content: "<i> Usted ha realizado una petición incorrecta...</i>",
                                color: "#8a6d3b",
                                iconSmall: "fa fa-info fa-3x fadeInRight animated",
                                timeout: 5000,
                            });

                    }
                } else if(xhr["status"] === 409){
                    $.smallBox({
                        content: "<i> "+xhr.responseJSON.message+"...</i>",
                        color: "#8a6d3b",
                        iconSmall: "fa fa-info fa-3x fadeInRight animated",
                        timeout: 5000,
                    });
                }
            }

        } else {
            $.smallBox({
                content: "<i> Comuníquese con el administrador <br/> Code error: " + xhr['status'] + " <br/> Detail: " + "Error" + "</i>",
                color: "#8a6d3b",
                iconSmall: "fa fa-warning fa-2x fadeInRight animated",
                timeout: 5000,
            });
        }
    }
}

function exception2(codeResponse) {
    const errorName = exceptNames[codeResponse];
    $.smallBox({
        content: "<i> Comuníquese con el administrador <br/> Code error: " + codeResponse + " <br/> Detail: " + errorName + "</i>",
        color: "#8a6d3b",
        iconSmall: "fa fa-warning fa-2x fadeInRight animated",
        timeout: 5000,
    });
}

function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function (n, i) {
        indexed_array[n['name'][0].toLowerCase() + n['name'].slice(1)] = n['value'];
    });

    return indexed_array;
}

function agregarModalParaVisualizacionImagen() {

    var modalImagenServer = document.createElement('div');
    modalImagenServer.innerHTML = `<div class="modal fade" id="myModalImage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					  <div class="modal-dialog modal-lg">
					    <div class="modal-content">
					      <div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" title="Close"> <span class="glyphicon glyphicon-remove"></span></button>
					        <h4 class="modal-title" id="myModalLabel"><strong>Visualización</strong></h4>
					      </div>
					      <div class="modal-body text-center">
					        <img src="" id="ImageVisualization" class="text-center" style="max-width: 100%" alt="image"/>
					      </div>
					      <div class="modal-footer">
					        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
					      </div>
					    </div>
					  </div>
		</div>`;
    document.body.append(modalImagenServer);
}

function spinnerSwitchTab(effect){
    $.SmartMessageBox({
        title: "<i style='color: #a8fa00'> Notificaciones Runfit</i>",
        content: "" +
            "<br/><i style='font-size: 1.2em;'>La acción solicitada ha iniciado. Por favor espere...</i><i class='fa fa-spinner fa-spin fa-15x pull-right'></i><br/>" +
            "<div class='row'><img id='ImgLoading' class='pull-left' height='80px' src='http://4.bp.blogspot.com/-lt--oWaKhWM/UtZTNXQkeYI/AAAAAAAAtsU/8liEKT5YJBw/s1600/deatlet13.gif'><div class='row text-center'></div>",
        buttons: '[]'
    }, function (ButtonPressed) {
        if(ButtonPressed.trim() === ""){
            setTimeout(()=>{
                const msgBox = document.getElementById('MsgBoxBack');
                if(msgBox){
                    if(msgBox.children && msgBox.children.length>0){
                        msgBox.children[0].remove();
                    }
                }
            }, 150)
        }
        if (ButtonPressed === "Cancelar") {
            setTimeout(()=>{
                if(document.querySelector('.divMessageBox') != undefined) document.querySelector('.divMessageBox').classList.add('hidden');
            }, 250);
        }
    })
    return setTimeout(()=>{
        $('.MessageBoxButtonSection button').toggleClass('hidden');
        return effect();}, 100);
}

function spinnerUpload(xhr) {
    $.SmartMessageBox({
        title: "<i class='fa fa-bullhorn'></i> Workout Notification",
        content: "" +
            "<br/><i>La acción solicitada ha iniciado. Por favor espere...</i><br/>" +
            "<div class='progress' style='width:100%;'><div id='ProgressUpload' class='progress-bar bg-color-teal' data-transitiongoal='0'>0%</div></div>" +
            "<div class='row'><div class='row text-center'><i class='fa fa-spinner fa-spin fa-3x text-center'></i></div>",
        buttons: '[Cancelar]'
    }, function (ButtonPressed) {
        if (ButtonPressed === "Cancelar") {
            xhr.abort();
            setTimeout(()=>{
                if(document.querySelector('.divMessageBox') != undefined) document.querySelector('.divMessageBox').classList.add('hidden');
            }, 250);
        }
    })

    //Validamos en caso exista un modal de messageBox adicional borrarlo y solo dejar el último que se genero
    setTimeout(() => {
        if (document.querySelectorAll('.MessageBoxContainer').length > 1) document.querySelectorAll('.MessageBoxContainer')[0].remove();
    }, 100);
}

function formatBytes(a, b) {
    if (0 == a) return "0 Bytes";
    var c = 1024, d = b || 2, e = ["Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"],
        f = Math.floor(Math.log(a) / Math.log(c));
    return parseFloat((a / Math.pow(c, f)).toFixed(d)) + " " + e[f]
}

function progress(e) {

    if (e.lengthComputable) {
        var max = e.total;
        var current = e.loaded;

        var Percentage = (current * 100) / max;
        percentageParse = Percentage.toFixed(0);
        $('#ProgressUpload').css('width', percentageParse + '%');
        $('#ProgressUpload').text(percentageParse + '%');
        if (Percentage >= 100) {
            // process completed
        }
    }
}

String.prototype.toHHMMSS = function () {
    var sec_num = parseInt(this, 10); // don't forget the second param
    var hours = Math.floor(sec_num / 3600);
    var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
    var seconds = sec_num - (hours * 3600) - (minutes * 60);
    if (hours < 10) {
        hours = "0" + hours;
    }
    if (minutes < 10) {
        minutes = "0" + minutes;
    }
    if (seconds < 10) {
        seconds = "0" + seconds;
    }
    return hours + ':' + minutes + ':' + seconds;
}

String.prototype.toHHMMSSM = function () {
    var sec_num = Math.round(this); // don't forget the second param
    var hours = Math.floor(sec_num / 3600);
    var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
    var seconds = sec_num - (hours * 3600) - (minutes * 60);
    if (hours < 10) {
        hours = "0" + hours;
    }
    if (minutes < 10) {
        minutes = "0" + minutes;
    }
    if (seconds < 10) {
        seconds = "0" + seconds;
    }
    return hours + ':' + minutes + ':' + seconds;
}

String.prototype.toSeconds = function () { if (!this) return null; var hms = this.split(':'); return (+hms[0]) * 60 * 60 + (+hms[1]) * 60 + (+hms[2] || 0); }

String.prototype.replaceAt=function(index, replacement) {
    if(index>-1)
        return this.substr(0, index) + replacement+ this.substr(index + replacement.length);
    else
        return this;
}

Number.prototype.toPercentage = function(){ return this/100;};

function getFechaFormatoString(d) {
    return `${d.getFullYear()}-${('00' + (d.getMonth() + 1)).slice(-2)}-${('00' + d.getDate()).slice(-2)}`;
}

function setFechaActual(array) {
    let dateToString = d => `${d.getFullYear()}-${('00' + (d.getMonth() + 1)).slice(-2)}-${('00' + d.getDate()).slice(-2)}`;
    array.forEach(v => {
        if(v.id == 'MacroFechaInicio'){
            const t = new Date();
            const d = t.getDay();
            if(d == 1)
                v.value = dateToString(t);
            else if(d == 0){
                v.value = moment(t).add(1, 'days').format("YYYY-MM-DD");
            }else {
                v.value = moment(t).add((7-d)+1, 'days').format("YYYY-MM-DD");
            }
        }else
            v.value = dateToString(new Date());
    });
}
function dateToArrayFormat(d){
    return `${d.getFullYear()}-${d.getMonth()}-${(d.getDate())}`.split("-");
}

function obtenerSemanasEntreFechas(d1, d2) {
    return Math.round((d2 - d1) / (7 * 24 * 60 * 60 * 1000));
}

function parseFromStringToDateTime(dateString) {
    var dt = dateString.split("/");
    var time = dateString.split(" ")[1].split(":");
    return new Date(Number(dt[2].substr(0,4)), Number(dt[1]) - 1, dt[0], time[0], time[1], time[2]);
}

function parseFromStringToDate(dateString) {
    var dt = dateString.split("-");
    return new Date(dt[0], Number(dt[1]) - 1, dt[2]);
}

function parseFromStringToDate2(dateString) {
    var dt = dateString.split("/");
    return new Date(dt[2], Number(dt[1]) - 1, dt[0]);
}

function spinnerHTMLRaw() {
    return '<div class="" style="margin-top: 15px;"><div class="text-center"><i class="fa fa-spinner fa-spin fa-3x text-center"></i></div></div>';
}

function spinnerHTMLRawCsMessage(msg){
    return `<div class="" style="margin-top: 15px;"><div class="text-center"><i class="fa fa-spinner fa-spin fa-3x text-center"></i><h3>${msg}</h3></div></div>`;
}
function limpiarBusqueda() {
    document.querySelector('#frm_busqueda').reset();
}

function getResponseCodeWithId(res){
    let t = res.split('|');
    return {code: t[0].trim(), id: t[1].trim()};
}

function getResponseCodeWithErrors(res){
    let t = String(res).split('|');
    return t.length == 1 ? false : {code: t[0].trim(), errors: t[1].trim()};
}
function sumarDiasAespecificoDia(fecha, dias){
    const y = fecha.getFullYear();
    const m = fecha.getMonth()+1<10?"0"+(fecha.getMonth()+1):fecha.getMonth()+1;
    const d = fecha.getDate()<10?"0"+fecha.getDate():fecha.getDate();
    const dd =  `${y}-${m}-${d}`;
    let mm = moment(dd).add(dias, 'days').format("YYYY-MM-DD");
    mm = mm.split("-");
    return new Date(Number(mm[0]),Number(mm[1])-1,Number(mm[2]));
}

function getOnlyDate(){
    let d = new Date();
    d.setHours(0,0,0,0);
    return d;
}

function instanciarPopovers(){
    $('[data-toggle="popover"]').popover();
}

function parseNumberToHours(minutos){
    let h = Math.floor(minutos/60);
    let m = minutos%60;
    return h+ "' "+ (m%60<10?"0"+String(m%60):m%60) + "\"";
}
function parseNumberToDecimal(num, decimals){
    return parseFloat(Number(num)).toFixed(decimals);
}

function validUUID(urlUuid){
    const f = urlUuid.split("/");
    return f[2]==undefined?false:/^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/i.test(f[2].split(".")[0]);
}

function htmlStringToElement(rawHTML){
    const elemento = document.createElement('template');
    elemento.innerHTML = rawHTML;
    return elemento.content.firstElementChild;
}

function notificacionesRutinaSegunResponseCode(resCode, wildcard){
    const code = Number(resCode);
    switch (code){
        case -1:
        case -2:
            return "success";
            break;
        case -3:
            //Eliminación satisfactoria
            break;
        case -4:
            break;
        case -5:
            if(wildcard == undefined)
                $.smallBox({color: "alert",content: "<i> La validación ha fallado... Comuníquese con el administrador o intentelo nuevamente más tarde.</i>"});
            else//wildcard == validation errors
                $.smallBox({color: "alert", content: `Datos enviados inválidos, total: ${wildcard.length}</br>
                ${wildcard.map(v=>'<i class="fa fa-times"></i> <b>'+v.campo + ':</b> '+ v.msg + '</br>').join('')}
            `, timeout: 10000})
            break;
        case -6:
            $.smallBox({color: "alert",content: "<i> La validación ha fallado, respuesta vacía... Comuníquese con el administrador o intentelo nuevamente más tarde.</i>"});
            break;
        case -7:
            $.smallBox({color: "alert",content: "<i> La sessión interna ha fallado... Comuníquese con el administrador o intentelo nuevamente más tarde.</i>"});
            break;
        case -10:
            $.smallBox({color: "alert",content: "<i> La operación ha fallado... Comuníquese con el administrador o intentelo nuevamente más tarde.</i>"});
            break;
        case -11:
            $.smallBox({color: "alert",content: "<i> La operación ha fallado... Comuníquese con el administrador o intentelo nuevamente más tarde.</i>"});
            break;
        case -12:
            $.smallBox({color: "alert",content: "<i> No se ha encontrado coincidencias con los datos enviados... Comuníquese con el administrador o intentelo nuevamente más tarde.</i>"});
            break;
        case -99:
            $.smallBox({color: "alert",content: "<i> La operación ha fallado... Comuníquese con el administrador o intentelo nuevamente más tarde.</i>"});
            break;
        case -100:
            $.smallBox({color: "alert",content: "<i> La operación ha fallado... Comuníquese con el administrador o intentelo nuevamente más tarde.</i>"});
            break;
        case -101:
            $.smallBox({color: "alert",content: "<i> La operación ha fallado... Comuníquese con el administrador o intentelo nuevamente más tarde.</i>"});
            break;
        case -102:
            $.smallBox({color: "alert",content: "<i> La operación ha fallado... Comuníquese con el administrador o intentelo nuevamente más tarde.</i>"});
            break;
        default:
            break;
    }
}

function calcularEdadByFechaNacimiento(fechaNac){
    const actual = new Date();
    const atleta = parseFromStringToDate2(fechaNac);
    let edad = actual.getFullYear() - atleta.getFullYear();
    if(actual.getMonth()>atleta.getMonth()){}
    else if(actual.getMonth()==atleta.getMonth()){
        if(actual.getDate()>=atleta.getDate()){}else{--edad;}
    }else{
        --edad;
    }
    return edad;
}

function getHash32Id(schema, id){
    return new Hashids(schema, 32).encode(id)
}

function getHash16Id(schema, id){
    return new Hashids(schema, 16).encode(id)
}

function getParamFromURL(param){
    return new URLSearchParams(window.location.search).get(param);
}

function blockButton(e){
    $preHtmlButton = e.innerHTML;
    e.classList.add('disabled');
    e.innerHTML = '<i class="fa fa-spinner fa-15x fa-spin fa-fw margin-right-5 txt-color-darken"></i><i>Cargando...</i>';
}

function unlockButton(e){
    e.classList.remove('disabled');
    e.innerHTML = $preHtmlButton;
    $preHtmlButton = "";
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function generateRandomMail(){
    const t0 = String(new Date().getTime());
    const t1 = t0.slice(-3);
    const t2 = t0.slice(-6).slice(0, 3);
    return `pedro${t1}infante${t2}@gmail.com`;
}

(function ajaxEvents(){

    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
        if (options.processData == false && options.bridgeMultipart === undefined) {
            spinnerUpload(xhr);
        } else{

            if((options.type === 'POST' || options.type === 'PUT') && !options.blockLoading){
                $("#btnGuardar").attr('disabled','disabled');
                $("#btnGuardar").html('<i class="fa fa-spinner fa-15x fa-spin fa-fw margin-right-5 txt-color-darken"></i><i>Cargando... Por favor espere...</i>');

            } else if(options.type === 'GET' && options.noOne !== undefined){

            } else{
                if(options.bridgeMultipart){
                    spinnerUpload(xhr);
                }else{
                    $gbInterval = spinnerSwitchTab(efectoImagenTransicion);
                }

            }
        }
    });

    $(document).ajaxComplete(function (e, xhr, options) {
        if (options.processData == false) {//WHEN FILES ARE BEING UPLOADED
            $('#bot1-Msg1').click();
        }else{
            if((options.type === 'POST' || options.type === 'PUT') && !options.blockLoading){
                if(!options.bridgeMultipart){
                    $('#btnGuardar').removeAttr('disabled');
                    $('#btnGuardar').text($buttonName);
                }else{
                    $('#btnGuardar').text($buttonName);
                }
            }else{
                $('#bot1-Msg1').click();
            }
        }

        if(options.noOne === undefined) {
            clearInterval($gbInterval);
        }
    });

    $(document).ajaxStart(() => {});

    $(document).ajaxStop(() => {});

    $(document).ajaxError(function (e, xhr, options) {
        $('#btnGuardar').removeAttr('disabled');
    });
})();


function efectoImagenTransicion(){
    const imgEffect = document.querySelector('#ImgLoading');
    imgEffect.style.position = 'relative';
    let refOffSetWidth = imgEffect.parentElement.offsetWidth;
    let s = 8, k = 1;
    const intervalLoading = setInterval(function(){
        imgEffect.style.right = (0 - (k*s)) + 'px';
        k++;
        if(Math.abs(k*s)>refOffSetWidth){
            k=1;
            imgEffect.style.right = (0 - (k*s)) + 'px';
        }
    }, 100);
    return intervalLoading;
}
