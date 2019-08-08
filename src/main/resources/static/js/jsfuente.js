// jqueryValidation's styles
let errorClass = 'invalid';
let errorElement = 'em';
// !- END var for jqueryValidation's styles
let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");
var _ctx = $('meta[name="_ctx"]').attr('content');
let $defaulTextButton = "";
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

const TipoNotificacion = Object.freeze({
    PERSONAL:   1,
    GENERAL:  2
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
});

const TipoRutina = Object.freeze({
    ESPECIFICA:   1,
    GENERAL:   2,
    COMPETITIVA:  3,
});

const TipoConfiguracion = Object.freeze({
    CTRL_REP_VIDEO:   1,
    CTRL_ENTRENAMIENTO:   2,
});

const TipoFicha = Object.freeze({
    RUNNING:   1,
    GENERAL:   2,
});

const TipoTrainer = Object.freeze({
    PARTICULAR:   1,
    EMPRESA:   2,
    DE_EMPRESA:   3,
});

const TipoUsuario = Object.freeze({
    ADMINISTRADOR:   1,
    TRAINER:   2,
    CLIENTE:   3,
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
const _idiomas = [{cd: "es", nombre: "Español"},{cd: "en", nombre:	"Inglés"},{cd: "pt", nombre: "Portugués"},{cd: "de", nombre:	"Alemán"},{cd: "ar", nombre:	"Árabe"},{cd: "bn", nombre:	"Bengalí"},{cd: "zh", nombre:	"Chino"},{cd: "ko", nombre:	"Coreano"},{cd: "fr", nombre:	"Francés"},{cd: "hi", nombre:	"Hindi"},{cd: "it", nombre:	"Italiano"},{cd: "ja", nombre:	"Japonés"},{cd: "ru", nombre:	"Ruso"}];
const _niveles = [{cd: 'pr', nombre: 'Principiante'}, {cd: 'in', nombre: 'Intermedio'}, {cd: 'av', nombre: 'Avanzado'}, {cd: 'pf', nombre: 'Profesional'}];
const _forsTrabajo = [{cd: 'ai', nombre: 'Al aire libre'}, {cd: 'pe', nombre: 'Personalizado'}, {cd: 'eg', nombre: 'En grupo'}, {cd: 'on', nombre: 'Online'}];
const _ubigeoPeLi = [{ub:"150101", nom:"Lima"}, {ub:"150102", nom:"Ancon"}, {ub:"150103", nom:"Ate"}, {ub:"150104", nom:"Barranco"}, {ub:"150105", nom:"Breña"}, {ub:"150106", nom:"Carabayllo"}, {ub:"150107", nom:"Chaclacayo"}, {ub:"150108", nom:"Chorrillos"}, {ub:"150109", nom:"Cieneguilla"}, {ub:"150110", nom:"Comas"}, {ub:"150111", nom:"El Agustino"}, {ub:"150112", nom:"Independencia"}, {ub:"150113", nom:"Jesus Maria"}, {ub:"150114", nom:"La Molina"}, {ub:"150115", nom:"La Victoria"}, {ub:"150116", nom:"Lince"}, {ub:"150117", nom:"Los Olivos"}, {ub:"150118", nom:"Lurigancho"}, {ub:"150119", nom:"Lurin"}, {ub:"150120", nom:"Magdalena del Mar"}, {ub:"150121", nom:"Pueblo Libre (Magdalena Vieja)"}, {ub:"150122", nom:"Miraflores"}, {ub:"150123", nom:"Pachacamac"}, {ub:"150124", nom:"Pucusana"}, {ub:"150125", nom:"Puente Piedra"}, {ub:"150126", nom:"Punta Hermosa"}, {ub:"150127", nom:"Punta Negra"}, {ub:"150128", nom:"Rimac"}, {ub:"150129", nom:"San Bartolo"}, {ub:"150130", nom:"San Borja"}, {ub:"150131", nom:"San Isidro"}, {ub:"150132", nom:"San Juan de Lurigancho"}, {ub:"150133", nom:"San Juan de Miraflores"}, {ub:"150134", nom:"San Luis"}, {ub:"150135", nom:"San Martin de Porres"}, {ub:"150136", nom:"San Miguel"}, {ub:"150137", nom:"Santa Anita"}, {ub:"150138", nom:"Santa Maria del Mar"}, {ub:"150139", nom:"Santa Rosa"}, {ub:"150140", nom:"Santiago de Surco"}, {ub:"150141", nom:"Surquillo"}, {ub:"150142", nom:"Villa El Salvador"}, {ub:"150143", nom:"Villa Maria del Triunfo"}];
const regexMail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const banks = [
    {id: 1,nombre:	"Banco de Comercio"}, {id: 2,nombre:	"Banco de Crédito del Perú(BCP)"}, {id: 3,nombre:	"Banco Interamericano de Finanzas (BanBif)"}, {id: 4,nombre:	"Banco Pichincha"}, {id: 5,nombre:	"BBVA Continental"}, {id: 6,nombre:	"Citibank Perú"}, {id: 7,nombre:	"Interbank"}, {id: 8,nombre:	"Mi Banco"}, {id: 9,nombre:	"Scotiabank Perú"}, {id: 10,nombre:	"Banco GNB Perú"}, {id: 11,nombre:	"Banco Falabella"}, {id: 12,nombre:	"Banco Ripley"}, {id: 13,nombre:	"Banco Santander Perú"}, {id: 14,nombre:	"Banco Azteca"}, {id: 15,nombre:	"Banco Cencosud"}, {id: 16,nombre:	"ICBC PERU BANK"}];
const _tpdocs = [
    {id: 1,nombre:	"DNI"}, {id: 2,nombre:	"CE"}, {id: 3,nombre:	"RUC"}];
const _departamentos = ["Amazonas","Ancash","Apurímac","Arequipa","Ayacucho","Cajamarca","Callao","Cusco","Huancavelica","Huanuco","Ica","Junin","La Libertad","Lambayeque","Lima","Loreto","Madre de Dios","Moquegua","Pasco","Piura","Puno","San Martin","Tacna","Tumbes","Ucayali"];


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
    $('#view_list').removeClass().addClass('fadeOutRightBig' + ' animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function (e) {
        const input = e.target;
        if(input.classList.contains('dropdown-menu')){
            return;
        } else {
            $('#view_list').hide();
            $("#view_register").show().removeClass().addClass('fadeInLeftBig' + ' animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function (e) {
                e.stopPropagation();
                e.preventDefault();
                return;
            });
        }
    });
    wildcard != undefined ? "" : limpiarMainForm();
}

function irListado(x) {
    x[0]["defaultValue"] = 0;//EQUALS TO HIDDEN ID
    $('#view_register').removeClass().addClass('fadeOutRightBig' + ' animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
        $('#view_register').hide();
        $('#btnNuevo').css('display', 'block');
        $("#view_list").show().removeClass().addClass('fadeInLeft' + ' animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function (e) {
            e.stopPropagation();
            e.preventDefault();
            return;
        });
    });
}

function reqSuccess(r, timeout, withoutEscape){
    const miliseconds = timeout !== undefined ? timeout : 10000;
    if(isNaN(r.res)){
        const msg = r.res;
        if(withoutEscape){
            setTimeout(()=>{
                $.SmartMessageBox({
                    title: "<i class='fa fa-exclamation-triangle fa-fw' style='color:yellow;'></i> <b>RUNFIT</b>",
                    content: "<div class='font-md' style='font-size: 1.5em'>" +
                        msg+"</div>",
                    buttons: '[OK]'
                }, function (ButtonPressed) {
                    if(ButtonPressed == 'OK'){
                        window.location.href = _ctx+"p/inicio";
                    } else {

                    }
                });
            }, 500);
        } else {
            $("#frm_registro :input").prop("disabled", true);
            $.smallBox({content: "<i class='fa fa-check'></i> "+msg,
                color: '#111509',
                timeout: miliseconds
            });
        }
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
                        title: "<i class='fa fa-exclamation-triangle fa-fw' style='color:yellow;'></i> <b>RUNFIT</b>",
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
                            if(v.message.includes("estar entre")){
                                v.message += " caracteres";
                            }
                            errors.push(`${field}: ${capitalizeFirstLetter(v.message)}`);

                        });
                        smallBoxAlertValidation2(errors);
                    }else{
                        if(typeof xhr.responseJSON == 'object' && typeof xhr.responseJSON.message !== 'undefined'){
                            $.smallBox({
                                content: "<i> "+xhr.responseJSON.message+"</i>",
                                color: "#8a6d3b",
                                iconSmall: "fa fa-info fa-3x fadeInRight animated",
                                timeout: 5000,
                            });
                        }else{
                            $.smallBox({
                                content: "<i> Usted ha realizado una petición incorrecta...</i>",
                                color: "#8a6d3b",
                                iconSmall: "fa fa-info fa-3x fadeInRight animated",
                                timeout: 5000,
                            });
                        }


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

    var modalImagenServer = `<div class="modal fade" id="myModalImage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
    document.body.appendChild(htmlStringToElement(modalImagenServer));
}

function spinnerSwitchTab(effect){
    const demo = [];
    demo
    const gifRunner = _ctx + "img/runner.gif";
    $.SmartMessageBox({
        /*title: "<i style='color: #a8fa00'> RUNFIT</i>",*/
        /*content: "" +
            "<br/><i style='font-size: 1.2em;'>La acción solicitada ha iniciado. Por favor espere...</i><i class='fa fa-spinner fa-spin fa-15x pull-right'></i><br/>" +
            "<div class='row'><img id='ImgLoading' class='pull-left' height='80px' src='"+gifRunner+"'><div class='row text-center'></div>",*/
        content: '<div class="text-center">' +
                     '<div class="half-circle-spinner">' +
                         '<div class="circle circle-1"></div>' +
                         '<div class="circle circle-2"></div>' +
                     '</div>' +
                     '<h3 style="text-align: center">Cargando...</h3>'+
                 '</div>',
        buttons: '[]',
        background: 'transparent'
    }, function (ButtonPressed) {
    })
    return setTimeout(()=>{
        $('.MessageBoxButtonSection button').toggleClass('hidden');
        return effect();}, 100);
}

function spinnerUpload() {
    $.SmartMessageBox({
        title: "",
        /*content: "" +
            "<br/><i>La acción solicitada ha iniciado. Por favor espere...</i><br/>" +
            "<div class='progress' style='width:100%;'><div id='ProgressUpload' class='progress-bar bg-color-teal' data-transitiongoal='0'>0%</div></div>" +
            "<div class='row'><div class='row text-center'><i class='fa fa-spinner fa-spin fa-3x text-center'></i></div>",*/
        content: '<div class="text-center">' +
                     '<div class="half-circle-spinner">' +
                         '<div class="circle circle-1"></div>' +
                         '<div class="circle circle-2"></div>' +
                     '</div>' +
                     '<h3>Cargando...</h3>'+
                 '</div>',
        buttons: '[]',
        background: 'transparent',
    }, function (ButtonPressed) {});

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

function getFechaFormatoString2(d) {
    return `${('00' + d.getDate()).slice(-2)}-${('00' + (d.getMonth() + 1)).slice(-2)}-${d.getFullYear()}`;
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
    return new Date(dt[2].substr(0, 4), Number(dt[1]) - 1, dt[0]);
}

function parseFromStringMonthToDate(dateString) {
    var dt = dateString.split("/");
    return new Date(dt[1], Number(dt[0]) - 1, 1);
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
    //elemento.content.querySelector("*") for Microsoft Edge compatibility
    return elemento.content.firstElementChild ? elemento.content.firstElementChild : elemento.content.querySelector("*");
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

(function modalHideScrollOnY(){
    $('body .modal').on('shown.bs.modal', function () {
        body.parentElement.style.overflowY = "hidden";
    });

    $('body .modal').on('hide.bs.modal', function () {
        body.parentElement.style.overflowY = "auto";
    });
})();

(function ajaxEvents(){
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
        if(options.dataType === "xml") {
            return;
        }
        if (options.processData == false && options.bridgeMultipart === undefined) {
            spinnerUpload();
        } else{
            if((options.type === 'POST' || options.type === 'PUT') && !options.blockLoading){
                if($("#btnGuardar")[0]){
                    $defaulTextButton = $("#btnGuardar")[0].textContent;
                    $("#btnGuardar").attr('disabled','disabled');
                    $("#btnGuardar").html('<i class="fa fa-spinner fa-15x fa-spin fa-fw margin-right-5 txt-color-darken"></i><i>Cargando... Por favor espere...</i>');
                }
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
            if(options.dataType === "xml") {
                return;
            }
            if (options.processData == false) {//WHEN FILES ARE BEING UPLOADED
                $('#bot1-Msg1').click();
            } else {
                if ((options.type === 'POST' || options.type === 'PUT') && !options.blockLoading) {
                    if (!options.bridgeMultipart) {

                    }

                    if($("#btnGuardar")[0]){
                        $('#btnGuardar').removeAttr('disabled');
                        if($defaulTextButton){
                            $("#btnGuardar")[0].textContent = $defaulTextButton;
                        }
                    }
                } else {
                    if (options.type === 'GET' && options.noOne !== undefined) {

                    } else {
                        $('#bot1-Msg1').click();
                    }
                }
            }

            if (options.noOne === undefined) {
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
    /*const imgEffect = document.querySelector('#ImgLoading');
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
    return intervalLoading;*/
}


function getFileExtension(fileName){
    return fileName.slice(-(fileName.length-(fileName.lastIndexOf(".")+1)));
}

function instanceAllTooltip(){
    document.querySelectorAll('i[rel="tooltip"]').forEach(e=>{
        $(e).tooltip();
    })
}

function cerrarSesion(){
    $.SmartMessageBox({
        title: "<i class='fa fa-sign-out txt-color-orangeDark'></i> ¿Está seguro que desea cerrar sesión <span class='txt-color-orangeDark'><strong>" + $("#show-shortcut").text() + "</strong></span> ?",
        buttons: "[No][Si]"
    }, function(e) {
        "Si" == e && $.ajax({
            url: _ctx + "logout",
            type: "POST",
            success: function(e) {
                document.cookie = "GLL_NOMBRE_COMPLETO=; path=/;";
                window.location = _ctx + "login";
            },
            error: function(e) {
                console.log(e)
            }
        })
    })
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function b64DecodeUnicode(str) {
    // Going backwards: from bytestream, to percent-encoding, to original string.
    return decodeURIComponent(atob(str).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
}


(function setUserFullNameToPage(){
    const user = document.querySelector('.user-full-name');
    if(user){
        user.outerHTML = getFullNameFromUser();
    }
})();
function getFullNameFromUser(){
    return b64DecodeUnicode(getCookie("GLL_NOMBRE_COMPLETO")).trim();
}

function getImgUrlFromUser(){
    return getCookie("GLL_IMG_PERFIL").trim();
}

const optCliEnt = document.querySelector('#OptConfCliCtrlEntrenamiento');
const optCliCrlRepVideo = document.querySelector('#OptConfCliCtrlRepVideo');
const optCliEntMob = document.querySelector('#OptConfCliCtrlEntrenamientoMob');
const optCliCrlRepVideoMob = document.querySelector('#OptConfCliCtrlRepVideoMob');


if(optCliEnt){
    optCliEnt.addEventListener('click', actualizarConfiguracionCliente);
    if(getCookie("GLL_CONTROL_ENTRENAMIENTO") === "SEMANAL"){
        optCliEnt.checked = true;
    }
}

if(optCliCrlRepVideo){
    optCliCrlRepVideo.addEventListener('click', actualizarConfiguracionCliente);
    if(getCookie("GLL_CONTROL_REP_VIDEO") === "CONTINUA"){
        optCliCrlRepVideo.checked = true;
    }
}

if(optCliEntMob){
    optCliEntMob.addEventListener('click', actualizarConfiguracionCliente);
    if(getCookie("GLL_CONTROL_ENTRENAMIENTO") === "SEMANAL"){
        optCliEntMob.checked = true;
    }
}

if(optCliCrlRepVideoMob){
    optCliCrlRepVideoMob.addEventListener('click', actualizarConfiguracionCliente);
    if(getCookie("GLL_CONTROL_REP_VIDEO") === "CONTINUA"){
        optCliCrlRepVideoMob.checked = true;
    }
}

function actualizarConfiguracionCliente(e){
    const input = e.target;
    const tipoConfiguracion = Number(input.getAttribute('data-type'));
    let varianteURI = "";
    let valor = "";
    if(tipoConfiguracion === TipoConfiguracion.CTRL_REP_VIDEO){
        varianteURI = "ctrl-rep-video";
        valor = getCookie("GLL_CONTROL_REP_VIDEO");
        valor = valor === "REPETICION" ? "CONTINUA" : "REPETICION";
    } else {
        varianteURI = "ctrl-entrenamiento";
        valor = getCookie("GLL_CONTROL_ENTRENAMIENTO");
        valor = valor === "DIARIA" ? "SEMANAL" : "DIARIA";
    }

    $.ajax({
        type: 'PUT',
        url: _ctx + 'cliente/configuracion/actualizar/'+varianteURI,
        data: {valor: valor},
        dataType: "json",
        success: function () {
            if(tipoConfiguracion === TipoConfiguracion.CTRL_REP_VIDEO){
                document.cookie = "GLL_CONTROL_REP_VIDEO="+valor;
            } else {
                document.cookie = "GLL_CONTROL_ENTRENAMIENTO="+valor;
            }
        },
        error: function (xhr) {
            exception(xhr);
            input.checked = !input.checked;
        },
        complete: function () {}
    });
}


function addYearstoDate(date,years) {

    date.setFullYear(date.getFullYear() + years);
    date.setDate(date.getDate()-1)

    return date;

}

$(document).ready(()=>{
    if(typeof body !== 'undefined'){
        body.addEventListener('click', (e)=>{
            const input = e.target;
            const clases = input.classList;
            if(input.tagName === 'BUTTON' && clases.contains('botTempo')){
                input.setAttribute('disabled', 'disabled');
                if(input.previousElementSibling){
                    input.previousElementSibling.setAttribute('disabled', 'disabled');
                }
                if(input.nextElementSibling){
                    input.nextElementSibling.setAttribute('disabled', 'disabled');
                }
            }
        })
    }
});

function getTimestampUnix(){
    return new Date().getTime();
}

function fromDateToString(d){
    return `${d.getFullYear()}-${('00' + (d.getMonth() + 1)).slice(-2)}-${('00' + d.getDate()).slice(-2)}`;
}

