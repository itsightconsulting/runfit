const initPageActive = 1;
let $oculto = "";

const composedMuscles = [
    ["path62", "path60", "path48", "path46", "path206", "path208", "path210", "path212"],
    ["path338", "path340"],
    ["path418", "path420"],
    ["path346", "path348"],
    ["path426", "path428"],
];
const absIds = ["path62", "path60", "path48", "path46", "path206", "path208", "path210", "path212"];
let selectedMuscles = [];

const stUbicacion = document.getElementById('stUbicacion');
const frm = document.getElementById('frm_registro');
const body = document.querySelector('body');
const verifiedNames = [];
const nowDate = parseFromStringToDate(getFechaFormatoString(new Date()));
let nowDatePlusOne = new Date();
nowDatePlusOne.setDate(nowDatePlusOne.getDate() + 1);
nowDatePlusOne = parseFromStringToDate(getFechaFormatoString(nowDatePlusOne));
let lstCondMejoras = [];


(function () {
    init();
})();

function init() {
    instanceInitTab();
    mainSeeders();
    mainEventListeners();
    tooltipsOn();
    doMultiselectCheckBox();

    //Legacy
    basics();
}

function basics() {
    imgToSvg();
    tooltipsOn();
    time_line();
    number_time_line();
    checkBoxes();
    activeItems();
    $('#TxtMusculoOtro')[0].style.marginTop = ($('#HumanMuscles').height() - 80) + 'px';
}

function instanceInitTab() {
    document.querySelector('.step-0' + initPageActive).classList.toggle('active');
    document.querySelector('.inpts-' + initPageActive).classList.toggle('active');
}

function mainSeeders() {
    cargarDataCliente();
}

function mainEventListeners() {
    eventListenerSexo();
    eventListenerPadeceDolor();
    stUbicacion.addEventListener('change', evtLsChangeUbicacion);
    stUbicacion.addEventListener('change', evtLsChangeUbicacion);

    document.querySelector('body').addEventListener('click', bodyClickEventListener);
    document.querySelector('body').addEventListener('focusout', bodyFocusOutEventListener);
    document.querySelector('body').addEventListener('change', bodyChangeEventListener);
    document.querySelector('body .ficha-02 .rango-sobrepeso').addEventListener('mousemove', (e) => {
        if (e.target.id === 'CatSobrepeso') {
            document.getElementById('SobrePeso').textContent = e.target.value + "KG";
        }
    });
    document.querySelector('body').addEventListener('keydown', (e) => {
        const input = e.target;
        if (input.classList.contains('input-time')) {
            setTimeout(() => {
                $oculto = Array.from(input.parentElement.parentElement.querySelectorAll('input')).map(e => e.value).join(':');
            }, 50);
        }

        if (input.id === 'Username' || input.id === 'Correo') {
            input.previousElementSibling.previousElementSibling.classList.add('hidden');
        }
    });

    document.querySelector('body').addEventListener('change', (e) => {
        const input = e.target;
        if (input.classList.contains('input-time')) {
            setTimeout(() => {
                $oculto = Array.from(input.parentElement.parentElement.querySelectorAll('input')).map(e => e.value).join(':');
            }, 50);
        }
    });

}

function tooltipsOn() {
    $('svg path[data-toggle="tooltip"]').tooltip({trigger: 'hover'});
    activeTooltips();
}

function activeTooltips() {
    const all = document.querySelectorAll('i[rel="tooltip"]');
    all.forEach(e => {
        $(e).tooltip();
    })
}

function eventListenerSexo() {
    document.querySelector('#sxMujer').addEventListener('click', (e) => {
        $('#liFlagEmbarazo').removeClass('hidden');
    })

    document.querySelector('#sxHombre').addEventListener('click', (e) => {
        $('#liFlagEmbarazo').addClass('hidden');
    })
}

function eventListenerPadeceDolor() {
    document.querySelector('#FlagPadeceDolorSi').addEventListener('click', (e) => {
        setTimeout(() => {
            $('#DescripcionDolor').valid();
        }, 100)
    })
    document.querySelector('#FlagPadeceDolorNo').addEventListener('click', (e) => {
        setTimeout(() => {
            $('#DescripcionDolor').valid();
        }, 100)
    })
}

function evtLsChangeUbicacion(e) {
    e.preventDefault();
    const input = e.target;
    const clases = input.classList;
    const value = input.value;

    if (input.id === "Dep") {
        depYprovChange(value, '', 1);
        $('#Pro').multiselect('rebuild');
    } else if (input.id === "Pro") {
        const depId = document.getElementById('Dep').value;
        depYprovChange(depId, value, 2);
        $('#Dis').multiselect('rebuild');
    }
}

function sendMainForm(e) {
    const body = generateBody();

    if ($(frm).valid()) {
        //IN PROGRESS
        $.ajax({
            type: 'PUT',
            contentType: 'application/json',
            url: _ctx + 'gestion/cliente-fitness/actualizar',
            dataType: 'json',
            data: JSON.stringify(body),
            blockLoading: true,
            success: function (res) {
                reqSuccess(res);
            }, complete: () => {
            }, error: (err) => {
                exception(err);
            }
        })
    }
}

function bodyClickEventListener(e) {
    const input = e.target;
    const clases = input.classList;

    if (clases.contains('body-muscle')) {
        e.preventDefault();
        e.stopPropagation();
        const id = input.id;

        const isCompMuscle = checkComposedMuscle(composedMuscles, id);
        if (!isCompMuscle.check) {
            if (!clases.contains('muscle-selected')) {
                const muscleName = input.getAttribute('data-original-title').slice(4).slice(0, -5);
                selectedMuscles.push({id: id, name: muscleName});
            } else {
                selectedMuscles = selectedMuscles.filter(v => v.id != id);
            }
            clases.toggle('muscle-selected');
        } else {
            const msclArr = composedMuscles[isCompMuscle.ix];
            const absSelected = clases.contains('muscle-selected');
            const muscleName = input.getAttribute('data-original-title').slice(4).slice(0, -5);
            msclArr.forEach(v => {
                const absEle = document.getElementById(v);
                if (!absSelected) {
                    selectedMuscles.push({id: absEle.id, name: muscleName});
                } else {
                    selectedMuscles = selectedMuscles.filter(v => v.id != absEle.id);
                }
                absEle.classList.toggle('muscle-selected');
            })
        }
        regenerarListaMusculosSeleccionados();
    }
    checkBoxAndRadioValidationEventListener(e, input, clases);
    /* Elementos a disposición */
    if (clases.contains('checkmark') || clases.contains('chk-content')) {
        if (clases.contains('checkmark')) {
            const prev = input.previousElementSibling;
            if (prev) {
                if (prev.name === "ItemsFitness") {
                    validarSeleccionNA(prev, "ItemsFitness", "9");
                }
                if (prev.name === "DesPadVarios") {
                    validarSeleccionNA(prev, "DesPadVarios", "4");
                }
            }
            if (prev.name === "FlagPadeceDolor") {
                const isChecked = document.getElementById('FlagPadeceDolor').checked;
                if (isChecked) {
                    document.getElementById('dvDescDolor').classList.remove('hidden');
                    document.getElementById('dvMomDolor').classList.remove('hidden');
                } else {
                    document.getElementById('dvDescDolor').classList.add('hidden');
                    document.getElementById('dvMomDolor').classList.add('hidden');
                    document.getElementById('DivDolCorrer').classList.add('hidden');

                }
            }

            if (prev.name === "MomDolor") {
                if (prev.value === "1" || prev.value === "2") {
                    $('#DivDolCorrer').removeClass('hidden');
                } else {
                    $('#DivDolCorrer').addClass('hidden');
                }
            }
        }

        if (clases.contains('chk-content')) {
            const inpObjetos = input.querySelector('input[name="ItemsFitness"]');
            const inpPadeceDe = input.querySelector('input[name="DesPadVarios"]');
            if (inpObjetos) {
                validarSeleccionNA(inpObjetos, "ItemsFitness", "9");
            }
            if (inpPadeceDe) {
                validarSeleccionNA(inpPadeceDe, "DesPadVarios", "4");
            }

            if (clases.contains('chk-mom-dolor')) {
                const inp = input.firstElementChild;
                if (inp.value === "1" || inp.value === "2") {
                    $('#DivDolCorrer').removeClass('hidden');
                } else {
                    $('#DivDolCorrer').addClass('hidden');
                }
            }
        }

    }
    /* END elementos a disposición*/

    if (clases.contains('marca-referencial')) {
        body.querySelectorAll('.marca-referencial').forEach(e => {
            if (e.name === input.name) {
                return;
            }
            e.classList.remove('state-error');
            e.classList.remove('state-success');
            const em = e.nextElementSibling;
            if (em && em.tagName === "EM") {
                em.remove();
            }
            e.value = "";
        });
        input.focus();
    }

    if (input.id === "FlagPadeceDolorSi" || input.id === "FlagPadeceDolorNo") {
        const isChecked = document.getElementById('FlagPadeceDolor').checked;
        if (isChecked) {
            document.getElementById('dvDescDolor').classList.remove('hidden');
            document.getElementById('dvMomDolor').classList.remove('hidden');
        } else {
            document.getElementById('dvDescDolor').classList.add('hidden');
            document.getElementById('dvMomDolor').classList.add('hidden');
            document.getElementById('DivDolCorrer').classList.add('hidden');
        }
    }
}

function bodyFocusOutEventListener(e) {
    const input = e.target;
    if (input.tagName === "INPUT") {
        //Generación de IMC
        if (input.id && input.id === "Peso" || input.id && input.id === "Talla") {
            var peso = $('#Peso').val();
            var talla = $('#Talla').val();

            if (!isNaN(parseFloat(peso)) && !isNaN(parseInt(talla))) {

                var tallaKg = talla / 100;
                var imc = peso / Math.pow(tallaKg, 2);
                imc = imc.toFixed(1);
                $('#Imc').val(imc);
            } else {
                $('#Imc').val("");

            }
        }
        //Fin de generación de IMC

        if (input.type === "text") {
            input.value = input.value.trim().replace(/ +/g, " ");
        } else if (input.type === "email") {
            input.value = input.value.trim().replace(/ +/g, "");
        }

        if (input.name === "Correo") {
            if ($(input).valid()) {
                if (!verifiedNames.includes(input.value)) {
                    validUniqueEmailOrUsernameOrNomPag(input, 'correo');
                }
            }
        }
        if (input.name === "Username") {
            if ($(input).valid()) {
                if (!verifiedNames.includes(input.value)) {
                    validUniqueEmailOrUsernameOrNomPag(input, 'username');
                }
            }
        }
    }
    if (input.tagName === "TEXTAREA") {
        input.value = input.value.trim();
    }
}

function checkComposedMuscle(compsMscls, id) {
    let is = false;
    let index = -1;
    compsMscls.forEach((arr, i) => {
        if (!is) {
            is = arr.some((v) => {
                return v === id
            });
            index = is ? i : -1;
        }
    })
    return {check: is, ix: index}
}

function regenerarListaMusculosSeleccionados() {
    const justNames = Array.from(new Set(selectedMuscles.map(v => v.name)));
    const len = justNames.length;
    const mj = Math.ceil(len / 2);
    const mn = len - mj;
    const arrMj = justNames.slice(0, mj);
    const arrMn = mn > 0 ? justNames.slice(-mn) : [];
    const listLeft = document.querySelector('.ul-msc-l');
    const listRight = document.querySelector('.ul-msc-r');
    listLeft.innerHTML = arrMj.map(v => templateMuscleEleList(v)).join('');
    listRight.innerHTML = arrMn.map(v => templateMuscleEleList(v)).join('');
}

function templateMuscleEleList(name) {
    return `<li>
                     <div class="chk-content" style="font-size: 15px !important;">${name}
                         <input data-body="${name}" type="checkbox" name="${name}" checked="checked"/><span class="checkmark checkmark-musculo"></span>
                     </div>
                  </li>`;
}

function bodyChangeEventListener(e) {
    const input = e.target;
    const clases = input.classList;

    if (input.tagName === "INPUT") {
        if (input.type === "date") {
            if ($(input).valid()) {
                if (input.nextElementSibling && input.nextElementSibling.tagName === "EM") {
                    input.nextElementSibling.remove();
                }
            }
        }

        if (input.type === "time") {
            if ($(input).valid()) {
                if (input.nextElementSibling && input.nextElementSibling.tagName === "EM") {
                    input.nextElementSibling.remove();
                }
            }
        }

        if (input.id && input.id === "CatSobrepeso") {
            document.getElementById('SobrePeso').textContent = input.value + "KG";
        }
    }

    if (clases.contains('inp-distancia')) {
        const tr = input.parentElement.parentElement;
        const inpTiempo = tr.querySelector('.inp-tiempo');
        if (inpTiempo.nextElementSibling) {
            inpTiempo.nextElementSibling.remove();
        }
    }
}

function cargarDataCliente() {
    let diffUrl = "obtener/completo";
    if (!$('#pruebaData').val()) {
        $.ajax({
            type: "GET",
            url: _ctx + 'gestion/cliente-fitness/' + diffUrl,
            dataType: "json",
            blockLoading: false,
            success: function (data) {
                populateForm(data);
            },
            error: function (xhr) {
                exception(xhr);

            },
            complete: function () {
                constraintsValidation();
                btnGuardar.addEventListener('click', sendMainForm);
            }
        })
    } else {
        const dataCliente = JSON.parse($('#pruebaData').val());

        dataCliente ? populateForm(dataCliente) : "";

        disableFormElements();

    }
}

function getCondicionesMejora(setMejoras) {
    $.ajax({
        type: 'GET',
        url: _ctx + 'p/condicion-mejora/listar/todos',
        dataType: 'json',
        blockLoading: false,
        noOne: true,
        success: function (res) {
            lstCondMejoras = res;
            const divConMej = document.getElementById('CondicionesMejora');
            const f4 = '<div class="col-md-4"><ul class="checks">' + res.slice(0, 4).map(e => `
                            <li>
                              <div class="chk-content">${e.nombre}
                                <input name="CondicionMejora" data-aka="Aspectos a mejorar" value="${e.id}" type="checkbox"><span class="checkmark"></span>
                              </div>
                            </li>
                  `).join('') + '</ul></div>';
            const s4 = '<div class="col-md-8"><ul class="checks">' + res.slice(4, 8).map(e => `
                                <li>
                                  <div class="chk-content">${e.nombre}
                                    <input name="CondicionMejora" data-aka="Aspectos a mejorar" value="${e.id}" type="checkbox"><span class="checkmark"></span>
                                  </div>
                                </li>
                  `).join('') + '</ul></div>';
            const ls3 = '<div class="col-md-12"><br><ul class="checks">' + res.slice(8, 11).map(e => `
                                <li>
                                  <div class="chk-content">${e.nombre}
                                    <input name="CondicionMejora" data-aka="Aspectos a mejorar" value="${e.id}" type="checkbox"><span class="checkmark"></span>
                                  </div>
                                </li>
                  `).join('') + '</ul></div>';
            divConMej.innerHTML = `${f4}${s4}${ls3}`;
            specificCheckBoxes('CondicionesMejora');
        }, complete: () => {
            if (setMejoras) {
                setMejoras.forEach(e => {
                    document.querySelector('input[name="CondicionMejora"][value="' + e.id + '"]').checked = true;
                });
            }
        }
    })
}

function seleccionarMusculosSensibles(musculosSeleccionados) {
    if (!musculosSeleccionados) {
        return;
    }
    musculosSeleccionados.forEach(function (element) {
        const id = "path" + element.id;
        const path = document.getElementById(id);
        path.classList.toggle('muscle-selected');
    })
}

function next_step(sheetNumber, toSheetNumber) {
    if ($(".ficha-01").hasClass("active")) {
        $(".ficha-01").removeClass("active");
        $('.step-01').removeClass("active");
        $(".ficha-02").addClass("active");
        $('.step-02').addClass("active");
        $(window).scrollTop(200);
        time_line();
    } else if ($(".ficha-02").hasClass("active")) {
        $(".ficha-02").removeClass("active");
        $('.step-02').removeClass("active");
        $(".ficha-03").addClass("active");
        $('.step-03').addClass("active");
        $(window).scrollTop(200);
        time_line();
    }
}

function next_step_cs(i) {
    const all = document.querySelectorAll('.fade-ficha');
    const sels = document.querySelectorAll('.step');
    all.forEach((v, ii) => {
        v.classList.remove('active');
        sels[ii].classList.remove('active')
    });
    all[i - 1].classList.add('active');
    sels[i - 1].classList.add('active');
    time_line();
}

function time_line() {
    let total = $(".steps ol").length;
    let estilos;

    for (let i = 0; i < total; i++) {
        let altura_total = $(".steps ol")[i].clientHeight;
        let altura_rest = $(".steps ol > li:last-child")[i].clientHeight;
        $(".steps ol")[i].className += " cbp_tmtimeline_" + i + "";
        estilos = ".cbp_tmtimeline_" + i + ":before{height: " + (altura_total - altura_rest) + "px}";
        $("html").append("<style>" + estilos + "</style>");
    }
}

function number_time_line() {
    let total = $(".steps ol>li").length;
    let estilos;
    for (let i = 0; i < total; i++) {
        $(".steps ol>li")[i].className += " cbp_tmtimeline_number_" + (i) + "";
        estilos = ".cbp_tmtimeline_number_" + i + ":before{content: '" + (i + 1) + "'}";
        $("html").append("<style>" + estilos + "</style>");
    }
}

function checkBoxes() {
    $(".chk-content").click(function () {
        var _self = $(this).find('input');
        var tipoElemento = _self.attr("type");

        if (tipoElemento !== "radio") {
            var clase = _self.attr("data-body");
            if (_self.is(':checked')) {
                $("." + clase + "").fadeOut();
                _self.prop('checked', false);
            } else {
                _self.prop('checked', true);
                $("." + clase + "").fadeIn();
            }
        } else {
            _self.prop('checked', true)
            $("." + clase + "").fadeIn();
        }
    })
}

function activeItems() {
    $(".list_items .chk-content").click(function () {
        var _self = $(this).find('input')
        var parent = $(this).parent().parent()
        if (_self.is(':checked')) {
            parent.addClass('active')
        } else {
            parent.removeClass('active')
        }
    })
}

function imgToSvg() {
    $('img.svg').each(function () {
        var $img = jQuery(this);
        var imgURL = $img.attr('src');
        var element = $img[0];

        $.get(imgURL, function (data) {
            var $svg = $(data).find('svg');
            Array.from(element.attributes).forEach(e => {
                $svg.attr(e.nodeName, e.nodeValue);
            });
            $svg.removeAttr('xmlns:a');
            if (!$svg.attr('viewBox') && $svg.attr('height') && $svg.attr('width')) {
                $svg.attr('viewBox', '0 0 ' + $svg.attr('height') + ' ' + $svg.attr('width'));
            }
            $img.replaceWith($svg);
            if ($svg[0].hasAttribute('rel')) {
                $($svg[0]).tooltip();
            }

        }, 'xml');
    });
}

function constraintsValidation() {
    const validUsername = document.getElementById('Username') ? true : false;
    $(frm).validate({
        rules: {
            Nombres: {
                required: true,
                rangelength: [3, 30],
                lettersonly: true
            },
            Apellidos: {
                required: true,
                rangelength: [3, 30],
                lettersonly: true
            },
            FechaNacimiento: {
                lessThanDate: nowDate
            },
            TipoDocumentoId: {
                required: true
            },
            NumeroDocumento: {
                required: true,
                digits: true,
                customnumdoc: function () {
                    const val = $('#TipoDocumentoId').val();
                    return val;
                },
                rangelength: [8, 12],
            },
            Correo: {
                required: true,
                emailValid: true,
                rangelength: [7, 40],
            },
            Movil: {
                required: true,
                digits: true,
                rangelength: [9, 11]
            },
            PaisId: {
                required: true,
            },
            Dep: {
                required: true
            },
            Pro: {
                required: true
            },
            Dis: {
                required: true
            },
            EstadoCivil: {
                required: true
            },
            Sexo: {
                required: true,
                digits: true,
                min: 1
            },
            Peso: {
                required: true,
                number: true,
                max: 300,
                rangelength: [2, 5],
            },
            Talla: {
                required: true,
                number: true,
                rangelength: [2, 3],
                max: 280
            },
            Imc: {
                required: true,
                number: true,
                range: [1, 300]
            },
            FlagPadeceDolor: {
                required: true
            },
            DescripcionDolor: {
                required: function () {
                    return document.querySelector('#FlagPadeceDolor').checked;
                },
                rangelength: [5, 500]
            },
            MomDolor: {},
            MomDolorIni: {},
            DesLesion: {
                rangelength: [2, 500],
            },
            RecomMedica: {
                rangelength: [2, 500],
            },
            FlagEmbarazo: {
                required: function () {
                    return document.querySelector('input[name="Sexo"][value="2"]').checked;
                }
            },
            FlagHiperTensionArt: {
                required: true
            },
            FlagFumador: {
                required: true
            },
            DesFumador: {
                required: () => {
                    return cxRq('#FlagFumador')
                },
                rangelength: [4, 30]
            },
            FlagAntFamInfOrHiper: {
                required: true
            },
            FamInfOrHiper: {
                required: () => {
                    return cxRq('#FlagAntFamInfOrHiper')
                }
            },
            FlagResInsulina: {
                required: true
            },
            FlagAntFamDiabetes: {
                required: true
            },
            FamDiabetico: {
                required: () => {
                    return cxRq('#FlagAntFamDiabetes')
                }
            },
            FlagSobrepeso: {
                required: true
            },
            CatSobrepeso: {
                required: () => {
                    return cxRq('#FlagSobrepeso')
                }
            },
            CatAlimentacion: {
                required: true
            },
            NivelEstres: {
                required: true
            },
            FlagEnfermedad: {
                required: true
            },
            DesEnfermedad: {
                required: () => {
                    return cxRq('#FlagEnfermedad')
                },
                rangelength: [1, 60]
            },
            FlagAleMed: {
                required: true
            },
            DesAleMed: {
                required: () => {
                    return cxRq('#FlagAleMed')
                },
                rangelength: [1, 60]
            },
            FlagOperacion: {
                required: true
            },
            DesOperacion: {
                required: () => {
                    return cxRq('#FlagOperacion')
                },
                rangelength: [1, 60]
            },
            FlagHerDisOrCondCol: {
                required: true
            },
            DesHerDisOrCondCol: {
                required: () => {
                    return cxRq('#FlagHerDisOrCondCol')
                },
                rangelength: [5, 100]
            },
            DesPadVarios: {
                required: true
            },
            ContactoEmergenciaNombre: {
                required: true,
                lettersonly: true,
                rangelength: [3, 60]
            },
            ContactoEmergenciaMovil: {
                required: true,
                digits: true,
                rangelength: [9, 11]
            },
            HistorialDeportivoAnt: {
                required: true,
                rangelength: [10, 500]
            },
            HistorialDeportivoAct: {
                required: true,
                rangelength: [10, 500]
            },
            NivelAtleta: {
                required: true
            },
            FrecuenciaCardiaca: {
                min: 40,
                max: 130,
                rangelength: [2, 3],
                digits: true
            },
            FrecuenciaCardiacaMaxima: {
                min: 100,
                max: 220,
                rangelength: [2, 3],
                digits: true
            },
            FormaInicial: {
                required: true,
                digits: true,
                min: 0
            },
            TiempoUnKilometro: {
                required: (e) => e.value ? true : false,
                timing: true,
                greaterThanSeconds: "00:02:00",
                rangelength: [7, 8]
            },
            KilometrajePromedioSemana: {
                required: true
            },
            DiasSemanaCorriendo: {
                required: true
            },
            FlagCalentamiento: {
                required: true
            },
            FlagEstiramiento: {
                required: true
            },
            DesgasteZapatilla: {
                required: true
            },
            DesTerPredom: {
                required: true
            },
            DesObjetivos: {
                required: true,
                rangelength: [10, 1000]
            },
            DesComp: {
                required: true,
                rangelength: [5, 40]
            },
            ItemsFitness: {
                required: true
            },
            ViaConexion: {
                required: true
            },
            Username: {
                required: validUsername,
                usernameOrEmail: validUsername,
                rangelength: [!validUsername ? 0 : 7, !validUsername ? 0 : 30],
            },
            Password: {
                required: validUsername,
                rangelength: [!validUsername ? 0 : 8, !validUsername ? 0 : 30],
                pwcheck: validUsername
            },
            InpFechaCompetencia: {
                greaterThanDate: nowDatePlusOne,
            },
            InpDistancia: {
                required: true,
                number: true,
                rangelength: [1, 5]
            },
            TiempoComp: {
                required: true,
                timing: true,
            },
            TwoKm: {
                timing: true,
                rangelength: [7, 8],
                greaterThanSeconds: "00:04:00",
            },
            FourKm: {
                timing: true,
                rangelength: [7, 8],
                greaterThanSeconds: "00:10:00",
            },
            TweOneKm: {
                timing: true,
                rangelength: [7, 8],
                greaterThanSeconds: "00:58:00",
            },
            FouTwoKm: {
                timing: true,
                rangelength: [7, 8],
                greaterThanSeconds: "02:21:00",
            },
            MarcaReferencial: {
                required: function () {
                    if ($('#TwoKm').val().length > 7 ||
                        $('#FourKm').val().length > 7 ||
                        $('#TweOneKm').val().length > 7 ||
                        $('#FouTwoKm').val().length > 7) {
                        return false;
                    }
                    return true;
                }
            },
        },
        messages: {
            Sexo: {
                required: "El campo es obligatorio",
                min: "El tamaño minimo debe ser 4"
            },
            MomDolorIni: {
                required: "El campo es obligatorio"
            },
            FlagHiperTensionArt: {
                required: "El campo es obligatorio"
            },
            FlagFumador: {
                required: "El campo es obligatorio",
            },
            Mejoras: {
                required: "El campo es obligatorio"
            },
            MusculoSensible: {
                required: "El campo es obligatorio"
            },
            ViaConexion: {
                required: "El campo es obligatorio"
            },
            KilometrajePromedioSemana: {
                required: "El campo es obligatorio"
            },
            Username: {
                pattern: "Solo se admiten letras mínusculas y números, además el nombre debe empezar con 3 letras"
            },
            MarcaReferencial: {
                required: "Debe completar su marca en cualquiera de las distancias"
            }
        }
    });

    //Maxlength
    const rules = $(frm).validate().settings.rules;
    Object.keys(rules).filter(e => rules[e].hasOwnProperty('rangelength')).forEach(e => {
        if (e === "Username" || e === "Password" && rules[e].rangelength[1] === 0) {
            //Nothing
        } else {
            document.getElementById(e).setAttribute('maxlength', rules[e].rangelength[1]);
        }
    });
}

function populateForm(d) {
    //POPULATE UBIGEO LIMA - LIMA - LIMA
    setUbigeo(d.ubigeo);
    //Page 1
    document.querySelector('#Nombres').value = d.nombres;
    document.querySelector('#Apellidos').value = d.apellidos;
    document.querySelector('#FechaNacimiento').value = fromDateToString(parseFromStringToDate2(d.fechaNacimiento));
    document.querySelector('#TipoDocumentoId').value = d.tipoDocumentoId;
    $('#TipoDocumentoId').multiselect('refresh');
    document.querySelector('#NumeroDocumento').value = d.numeroDocumento;
    document.querySelector('#Correo').value = d.correo;
    document.querySelector('#Movil').value = d.movil;
    document.querySelector('#Peso').value = d.peso;
    document.querySelector('#Talla').value = d.talla;
    document.querySelector('#Imc').value = d.imc;
    document.querySelector('input[name="Sexo"][value="' + d.sexo + '"]').checked = true;
    if (d.sexo === 1) {
        document.getElementById('liFlagEmbarazo').classList.add('hidden');
    }
    document.querySelector('#EstadoCivil').selectedIndex = d.estadoCivil;
    $('#EstadoCivil').multiselect('refresh');
    //Condicion Anatomica
    const ca = JSON.parse(d.condicionAnatomica);
    document.querySelectorAll('input[name="FlagPadeceDolor"]')[ca.flagPadeceDolor ? 0 : 1].checked = true;
    if (ca.flagPadeceDolor) {
        document.querySelector('#DescripcionDolor').value = ca.descripcionDolor;
        if (ca.momDolor && (ca.momDolor == "1" || ca.momDolor == "2")) {
            document.getElementById('dvDescDolor').classList.remove('hidden');
            document.getElementById('dvMomDolor').classList.remove('hidden');
            document.querySelector('input[name="MomDolor"][value="' + ca.momDolor + '"]').checked = true;
            document.querySelector('#DivDolCorrer').classList.remove('hidden');
            const momDolorIni = ca.momDolorIni.split("|");
            momDolorIni.forEach(e => {
                document.querySelector('input[name="MomDolorIni"][value="' + e.trim() + '"]').checked = true;
            });
        }
    }

    document.querySelector('#DesLesion').value = ca.desLesion;
    document.querySelector('#RecomMedica').value = ca.recomMedica;
    document.querySelectorAll('input[name="FlagEmbarazo"]')[ca.flagEmbarazo ? 0 : 1].checked = true;
    //Page 2
    //SALUD
    const sa = JSON.parse(d.salud);
    document.querySelectorAll('input[name="FlagHiperTensionArt"]')[sa.flagHiperTensionArt ? 0 : 1].checked = true;
    if (sa.flagFumador) {
        $('#divDesFumador').removeClass('hidden');
        $('#DesFumador').val(sa.desFumador);
    }
    document.querySelectorAll('input[name="FlagFumador"]')[sa.flagFumador ? 0 : 1].checked = true;
    document.querySelectorAll('input[name="FlagAntFamInfOrHiper"]')[sa.flagAntFamInfOrHiper ? 0 : 1].checked = true;
    if (sa.flagAntFamInfOrHiper) {
        document.querySelector('#FamInfOrHiper').parentElement.parentElement.classList.remove('hidden');
        document.querySelector('#FamInfOrHiper').value = sa.famInfOrHiper;
        $('#FamInfOrHiper').multiselect('refresh');
    }
    document.querySelectorAll('input[name="FlagResInsulina"]')[sa.flagResInsulina ? 0 : 1].checked = true;
    document.querySelectorAll('input[name="FlagAntFamDiabetes"]')[sa.flagAntFamDiabetes ? 0 : 1].checked = true;
    if (sa.flagAntFamDiabetes) {
        document.querySelector('#FamDiabetico').parentElement.parentElement.classList.remove('hidden');
        document.querySelector('#FamDiabetico').value = sa.famDiabetico;
        $('#FamDiabetico').multiselect('refresh');
    }
    document.querySelectorAll('input[name="FlagSobrepeso"]')[sa.flagSobrepeso ? 0 : 1].checked = true;
    if (sa.flagSobrepeso) {
        document.querySelector('#CatSobrepeso').parentElement.classList.remove('hidden');
        $('#CatSobrepeso').val(sa.catSobrepeso);
        $('#SobrePeso').removeClass('hidden');
        $('#SobrePeso').text(sa.catSobrepeso + 'KG');
    }
    document.querySelector('#CatAlimentacion').selectedIndex = sa.catAlimentacion;
    $('#CatAlimentacion').multiselect('refresh');
    document.querySelector('#NivelEstres').selectedIndex = sa.nivelEstres;
    $('#NivelEstres').multiselect('refresh');
    document.querySelectorAll('input[name="FlagEnfermedad"]')[sa.flagEnfermedad ? 0 : 1].checked = true;
    if (sa.flagEnfermedad) {
        document.querySelector('#DesEnfermedad').parentElement.classList.remove('hidden');
        $('#DesEnfermedad').val(sa.desEnfermedad);
    }
    document.querySelectorAll('input[name="FlagAleMed"]')[sa.flagAleMed ? 0 : 1].checked = true;
    if (sa.flagAleMed) {
        document.querySelector('#DesAleMed').parentElement.classList.remove('hidden');
        $('#DesAleMed').val(sa.desAleMed);
    }
    document.querySelectorAll('input[name="FlagOperacion"]')[sa.flagOperacion ? 0 : 1].checked = true;
    if (sa.flagOperacion) {
        document.querySelector('#DesOperacion').parentElement.classList.remove('hidden');
        $('#DesOperacion').val(sa.desOperacion);
    }
    document.querySelectorAll('input[name="FlagHerDisOrCondCol"]')[sa.flagHerDisOrCondCol ? 0 : 1].checked = true;
    if (sa.flagHerDisOrCondCol) {
        document.querySelector('#DesHerDisOrCondCol').parentElement.classList.remove('hidden');
        $('#DesHerDisOrCondCol').val(sa.desHerDisOrCondCol);
    }

    if (sa.desPadVarios) {

        const padVarios = sa.desPadVarios.split("|");

        padVarios.forEach(e => {
            document.querySelector('input[name="DesPadVarios"][value="' + e.trim() + '"]').checked = true;
        });
    }
    if (sa.contactosEmergencia) {
        const arrContactosEmergencia = (sa.contactosEmergencia).split("|");
        arrContactosEmergencia.forEach((e, ix) => {
            if (arrContactosEmergencia.length > 1 && ix > 0) {
                $('#NuevoContacto').click();
            }
            const arrNomMov = e.split("_");
            const nombre = arrNomMov[0], movil = arrNomMov[1];
            document.querySelectorAll('.inp-cont-emer')[ix + (ix)].value = nombre;
            document.querySelectorAll('.inp-cont-emer')[ix + ix + 1].value = movil;
        });
    }
    document.querySelector('#HistorialDeportivoAnt').value = sa.historialDeportivoAnt;
    document.querySelector('#HistorialDeportivoAct').value = sa.historialDeportivoAct;
    document.querySelector('#FrecuenciaCardiaca').value = ca.frecuenciaCardiaca;
    document.querySelector('#FrecuenciaCardiacaMaxima').value = ca.frecuenciaCardiacaMaxima;
    document.querySelector('#FormaInicial').value = ca.formaInicial;
    document.querySelector('#NivelAtleta').selectedIndex = d.nivel;
    $('#NivelAtleta').multiselect('refresh');
    const mejoras = JSON.parse(d.mejoras);
    if (mejoras.length) {
        getCondicionesMejora(mejoras);
    }
    //Page 3
    if (ca.musculosSensibles) {
        selectedMuscles = ca.musculosSensibles.map((e) => {
            return {id: 'path' + e.id, name: e.nombre}
        });
        regenerarListaMusculosSeleccionados();
        seleccionarMusculosSensibles(ca.musculosSensibles);
    }

    document.querySelector('#TiempoUnKilometro').value = d.tiempoUnKilometro;
    document.querySelector('#KilometrajePromedioSemana').value = d.kilometrajePromedioSemana;
    document.querySelector('#DiasSemanaCorriendo').value = d.diasSemanaCorriendo;
    $('#DiasSemanaCorriendo').multiselect('refresh');
    document.querySelector('#DesObjetivos').value = d.desObjetivos;
    document.querySelectorAll('input[name="FlagCalentamiento"')[d.flagCalentamiento ? 0 : 1].checked = true;
    document.querySelectorAll('input[name="FlagEstiramiento"')[d.flagEstiramiento ? 0 : 1].checked = true;
    if (d.desgasteZapatilla) {
        const desZapatilla = d.desgasteZapatilla.split("|");
        desZapatilla.forEach(e => {
            document.querySelector('input[name="DesgasteZapatilla"][value="' + e + '"]').checked = true;
        });
    }

    if (d.desgasteZapatillaOtro) {
        document.getElementById('DesgasteZapatillaOtro').value = d.desgasteZapatillaOtro;
    }

    if (d.desTerPredom) {
        const arrDesTerPredom = d.desTerPredom.split("|");
        arrDesTerPredom.forEach(e => {
            document.querySelector('input[name="DesTerPredom"][value="' + e + '"]').checked = true;
        });
    }

    if (d.desTerPredomOtro) {
        document.getElementById('DesTerPredomOtro').value = d.desTerPredomOtro;
    }

    if (d.tiempoDistancia) {
        const tiempoDis = JSON.parse(d.tiempoDistancia);
        if (tiempoDis['2']) {
            document.querySelector('#TwoKm').value = tiempoDis['2'];
        }
        if (tiempoDis['4']) {
            document.querySelector('#FourKm').value = tiempoDis['4'];
        }
        if (tiempoDis['21']) {
            document.querySelector('#TweOneKm').value = tiempoDis['21'];
        }
        if (tiempoDis['42']) {
            document.querySelector('#FouTwoKm').value = tiempoDis['42'];
        }
    }

    if (d.fitElementos) {
        const elementosFit = d.fitElementos.split("|");
        elementosFit.forEach(e => {
            const input = document.querySelector('input[name="ItemsFitness"][value="' + e + '"]');
            input.checked = true;
            input.parentElement.parentElement.parentElement.classList.add('active');
        });
    }

    if (d.competencias) {
        const comps = JSON.parse(d.competencias);
        if (comps.length) {
            comps.forEach((e, ix) => {
                if (ix > 0 && comps.length > 1) {
                    const nwTableRow = agregarFilaCompetencia();
                    nwTableRow.querySelector('.inp-fecha').value = fromDateToString(parseFromStringToDate2(e.fecha.slice(0, 10)));
                    nwTableRow.querySelector('.inp-distancia').value = e.distancia;
                    nwTableRow.querySelector('.inp-nombre').value = e.nombre;
                    nwTableRow.querySelector('.inp-tiempo').value = e.tiempoObjetivo;
                    nwTableRow.querySelector('.inp-prioridad').value = e.prioridad;
                } else {
                    document.querySelector('.inp-fecha').value = fromDateToString(parseFromStringToDate2(e.fecha.slice(0, 10)));
                    document.querySelector('.inp-distancia').value = e.distancia;
                    document.querySelector('.inp-nombre').value = e.nombre;
                    document.querySelector('.inp-tiempo').value = e.tiempoObjetivo;
                    document.querySelector('.inp-prioridad').value = e.prioridad;
                }
            })
        }
    }

    //Check full profile or just runner with general profile
    if (!d.tiempoUnKilometro) {
        document.querySelectorAll('#OlCamposRunning>li').forEach(e => {
            if (!e.classList.contains('input-general')) {
                e.classList.add('hidden');
            }
        });
        document.getElementById('OlEsqueleto').innerHTML = "";
        document.getElementById('OlEsqueleto').appendChild(document.querySelector('.input-general'));
        document.getElementById('InpMarcaRunner').classList.add('hidden');
        document.querySelector('.display-table').classList.add('hidden');
    }
}

function specificCheckBoxes(id) {
    $("#" + id + " .chk-content").click(function () {
        var _self = $(this).find('input');
        var clase = _self.attr("data-body");
        if (_self.is(':checked')) {
            $("." + clase + "").fadeOut();
            _self.prop('checked', false)
        } else {
            _self.prop('checked', true);
            $("." + clase + "").fadeIn();
        }
    })
}

function agregarFilaCompetencia() {
    const tblCompetencia = document.getElementById('tblCompetencia');
    const fileRef = tblCompetencia.querySelector('.hide');
    const filaClonada = fileRef.cloneNode(true);
    filaClonada.classList.remove('hide');
    let rowNum = Number(filaClonada.getAttribute('data-acc'));
    filaClonada.setAttribute('data-acc', ++rowNum);
    filaClonada.classList.add('nuevo');
    fileRef.setAttribute('data-acc', rowNum);
    const tbody = tblCompetencia.querySelector('tbody');
    filaClonada.querySelector('img').setAttribute('data-acc', rowNum);
    tbody.append(filaClonada);
    //Agregando al obj validación el nuevo input fecha
    /* */
    const nuevaAgregada = tbody.lastElementChild;
    const inpFecha = nuevaAgregada.querySelector('.inp-fecha');
    inpFecha.setAttribute('name', "InpFechaCompetencia" + rowNum);
    $(inpFecha).rules("add", {greaterThanDate: nowDatePlusOne});

    const inpDesComp = nuevaAgregada.querySelector('.inp-nombre');
    inpDesComp.setAttribute('name', "DesComp" + rowNum);
    inpDesComp.setAttribute('data-aka', "NOMBRE DE COMPETICIÓN A PARTICIPAR");
    $(inpDesComp).rules("add", {required: true, rangelength: [5, 40]});

    const inpDistancia


        = nuevaAgregada.querySelector('.inp-distancia');
    inpDistancia.setAttribute('name', "InpDistancia" + rowNum);
    inpDistancia.setAttribute('data-aka', "DISTANCIA DE COMPETENCIA");
    $(inpDistancia).rules("add", {required: true, number: true});

    const inpTiempoComp = nuevaAgregada.querySelector('.inp-tiempo');
    inpTiempoComp.setAttribute('name', "TiempoComp" + rowNum);
    inpTiempoComp.setAttribute('data-aka', "TIEMPO OBJETIVO DE COMPETENCIA");
    $(inpTiempoComp).rules("add", {required: true, timing: true});
    if (tbody.querySelectorAll('tr').length >= 3) {
        $('input[name="DesComp"]').rules('add', {required: true});
    }
    //Popover al input de marca para la competencia
    $(inpTiempoComp.parentElement).popover({
        html: true,
        placement: "bottom"
    }).data("bs.popover").tip().addClass('popover-picker-time');
    return filaClonada;
}

function eliminarFila(e) {
    const tblCompetencia = document.getElementById('tblCompetencia');
    const tbody = tblCompetencia.querySelector('tbody');
    const delFila = tbody.querySelector(`tr.nuevo[data-acc="${Number(e.getAttribute('data-acc'))}"]`);
    delFila.remove();

    if (tbody.querySelectorAll('tr').length < 3) {
        $('input[name="DesComp"]').rules('add', {required: false});
    }
}

function generateBody() {
    const obj = getFormData($('#frm_registro'));
    //NECESARIO PARA QUE EN LA SERIALIZACIÓN NO SE LE RESTE A LA FECHA UN DÍA
    let parseFecha = parseFromStringToDate(obj.fechaNacimiento);
    obj.fechaNacimiento = parseFecha.getDate() + "/" + (parseFecha.getMonth() + 1) + "/" + parseFecha.getFullYear();
    //Ubigeo
    obj.paisId = 604;//PERU
    obj.ubigeo = $('#Dep').val() + $('#Pro').val() + $('#Dis').val();
    obj.cliFit = {};
    obj.cliFit.correoSecundario = document.getElementById('Correo').value;
    obj.cliFit.estadoCivil = document.getElementById('EstadoCivil').value;
    obj.cliFit.sexo = document.querySelector('input[name="Sexo"]:checked').value;
    obj.cliFit.peso = document.getElementById('Peso').value;
    obj.cliFit.talla = document.getElementById('Talla').value;
    obj.cliFit.imc = document.getElementById('Imc').value;
    obj.cliFit.nivel = document.getElementById('NivelAtleta').value;
    //private CondicionMejora mejoras;
    obj.cliFit.tiempoUnKilometro = document.getElementById('TiempoUnKilometro').value;
    obj.cliFit.kilometrajePromedioSemana = document.getElementById('KilometrajePromedioSemana').value;
    obj.cliFit.diasSemanaCorriendo = document.getElementById('DiasSemanaCorriendo').value;
    obj.cliFit.flagCalentamiento = document.getElementById('FlagCalentamiento').checked;
    obj.cliFit.flagEstiramiento = document.getElementById('FlagEstiramiento').checked;
    obj.cliFit.desgasteZapatilla = getValuesConcatInpCheckbox('DesgasteZapatilla');
    obj.cliFit.desgasteZapatillaOtro = document.getElementById('DesgasteZapatillaOtro').value;
    obj.cliFit.desObjetivos = document.getElementById('DesObjetivos').value;
    obj.cliFit.desTerPredom = getValuesConcatInpCheckbox('DesTerPredom');
    obj.cliFit.desTerPredomOtro = document.getElementById('DesTerPredomOtro').value;
    obj.cliFit.mejoras = getValuesCondicionMejora();
    obj.cliFit.frecuenciaComunicacion = 1;
    obj.cliFit.tiempoDistancia = getTiemposDistancias();
    const condAnatomica = obj.cliFit.condicionAnatomica = new Object();
    condAnatomica.flagPadeceDolor = document.querySelector('#FlagPadeceDolor').checked;
    condAnatomica.descripcionDolor = obj.descripcionDolor;
    condAnatomica.momDolor = obj.momDolor;
    condAnatomica.momDolorIni = obj.momDolorIni;
    condAnatomica.desLesion = obj.desLesion;
    condAnatomica.recomMedica = obj.recomMedica;
    condAnatomica.flagEmbarazo = document.querySelector('#FlagEmbarazo').checked;
    condAnatomica.frecuenciaCardiaca = obj.frecuenciaCardiaca;
    condAnatomica.frecuenciaCardiacaMaxima = obj.frecuenciaCardiacaMaxima;
    condAnatomica.formaInicial = obj.formaInicial;
    condAnatomica.musculosSensibles = selectedMuscles.map(v => {
        return {id: v.id.slice(4), nombre: v.name}
    });
    condAnatomica.musculoSensiblesOtros = obj.txtMusculoOtro;
    const salud = obj.cliFit.salud = new Object();
    salud.flagEnfermedad = document.querySelector('#FlagEnfermedad').checked;
    salud.flagFumador = document.querySelector('#FlagFumador').checked;
    salud.flagHiperTensionArt = document.querySelector('#FlagHiperTensionArt').checked;
    salud.flagAntFamInfOrHiper = document.querySelector('#FlagAntFamInfOrHiper').checked;
    salud.flagResInsulina = document.querySelector('#FlagResInsulina').checked;
    salud.flagAntFamDiabetes = document.querySelector('#FlagAntFamDiabetes').checked;
    salud.flagSobrepeso = document.querySelector('#FlagSobrepeso').checked;
    salud.flagAleMed = document.querySelector('#FlagAleMed').checked;
    salud.flagOperacion = document.querySelector('#FlagOperacion').checked;
    salud.flagHerDisOrCondCol = document.querySelector('#FlagHerDisOrCondCol').checked;
    salud.famDiabetico = obj.famDiabetico;
    salud.famInfOrHiper = obj.famInfOrHiper;
    salud.catSobrepeso = obj.catSobrepeso;
    salud.catAlimentacion = obj.catAlimentacion;
    salud.nivelEstres = obj.nivelEstres;
    salud.desEnfermedad = obj.desEnfermedad;
    salud.desFumador = obj.desFumador;
    salud.desAleMed = obj.desAleMed;
    salud.desOperacion = obj.desOperacion;
    salud.desHerDisOrCondCol = obj.desHerDisOrCondCol;
    salud.desPadVarios = getValuesConcatInpCheckbox('DesPadVarios');
    salud.contactosEmergencia = getValuesEmergencyContact();
    salud.historialDeportivoAnt = obj.historialDeportivoAnt;
    salud.historialDeportivoAct = obj.historialDeportivoAct;
    //Competencias
    obj.cliFit.competencias = getCompetenciasParticipar();
    obj.cliFit.fitElementos = getValuesConcatInpCheckbox('ItemsFitness');
    return obj;
}

function getTiemposDistancias() {
    const obj = {};
    obj["2"] = document.querySelector('#TwoKm').value;
    obj["4"] = document.querySelector('#FourKm').value;
    obj["21"] = document.querySelector('#TweOneKm').value;
    obj["42"] = document.querySelector('#FouTwoKm').value;
    return JSON.stringify(obj);
}

function getCompetenciasParticipar() {
    const rows = Array.from(document.querySelector('#tblCompetencia tbody').querySelectorAll('tr:not(.hide)'));
    return rows.map(v => {
        const obj = new Object();
        obj.fecha = v.querySelector('.inp-fecha').value;
        let parseFecha = parseFromStringToDate(obj.fecha);
        obj.fecha = parseFecha.getDate() + "/" + (parseFecha.getMonth() + 1) + "/" + parseFecha.getFullYear();
        obj.distancia = v.querySelector('.inp-distancia').value;
        obj.nombre = v.querySelector('.inp-nombre').value;
        obj.tiempoObjetivo = v.querySelector('.inp-tiempo').value;
        obj.prioridad = v.querySelector('.inp-prioridad').value;
        return obj;
    }).filter(c => c.distancia);
}

function getValuesCondicionMejora() {
    return Array.from(document.querySelectorAll('input[name="CondicionMejora"]:checked')).map(
        v => {
            return {id: v.value, nombre: v.parentElement.textContent.trim()}
        }
    );
}

function getValuesEmergencyContact() {
    const prContNombre = document.querySelector('#ContactoEmergenciaNombre').value;
    const prContMovil = document.querySelector('#ContactoEmergenciaMovil').value;
    if (prContNombre.trim().length > 2 && prContNombre.trim().length > 6) {
        let others = Array.from(document.querySelectorAll('.inp-cont-emer')).slice(2).map((e, i, k) => {
            if (i % 2 == 0) {
                if (e.value.trim().length > 2 && k[i + 1].value.trim().length > 6) {
                    return e.value.trim() + "_" + k[i + 1].value.trim();
                }
            }
        }).filter(v => v !== undefined).join('|');
        if (others.length > 0) {
            others = "|" + others;
        }
        return prContNombre + "_" + prContMovil + others;
    } else {
        return "";
    }
    return Array.from(document.querySelectorAll('input.inp-cont-emer')).map(v => v.value).filter(v => v.trim().length > 10).join('|');
}

function disableFormElements() {
    $("input[type='text']").attr('readonly', 'readonly');
    $("input[type='date']").attr('readonly', 'readonly');
    $("textarea").attr('readonly', 'readonly');
    $("select").attr('disabled', 'disabled');
    //$("input[type='radio']").attr('disabled','disabled');
    $("input[type='range']").attr('disabled', 'disabled');
    $("input[type='checkbox']").attr('disabled', 'disabled');
    $("div button.multiselect").attr('disabled', 'disabled');
    $(':radio:not(:checked)').attr('disabled', true);
    $('svg#HumanMuscles').attr('class', 'disabled');
    $('#frm_registro a.add').attr('class', 'hidden');
    $('#frm_registro a.btn').attr('class', 'hidden');
    $('#btnGuardar').remove();
}

//TODO LO REFERENTE A LOS POPOVER PARA SELECCION DE TIEMPO
$(function () {
    const content = `<div class='row'>
                            <div class="col-md-12 text-center">
                              <div class="col col-md-3 col-xs-3 dv-input-time"><input type="number" max="23" placeholder="HH" class="form-control text-center input-time" maxlength="2" oninput="this.value=this.value.slice(0,this.maxLength)"/></div>
                              <div class="col col-md-3 col-xs-3 dv-input-time"><input type="number" max="59" placeholder="MM" class="form-control text-center input-time" maxlength="2"/></div>
                              <div class="col col-md-3 col-xs-3 dv-l-input-time"><input type="number" max="59" placeholder="SS" class="form-control text-center input-time" maxlength="2"/></div>
                              <div class="col col-md-3 col-xs-3 dv-picker-time-button"><i class="ok-time-selector fa fa-15px fa-check-circle-o"></i></div>
                            </div>
                         </div>`;
    $('.input-time').attr('data-content', content);

    /*data-html="true" data-placement="bottom" data-toggle="tooltip"*/
    document.querySelectorAll('.input-time').forEach(e => {
        $(e).popover({html: true, placement: "bottom"}).data("bs.popover").tip().addClass('popover-picker-time');
    })

    $('body').on('hidden.bs.popover', function (e) {
        $(e.target).data("bs.popover").inState.click = false;
    });

});
$(body).on('shown.bs.popover', function (e) {
    const input = e.target;
    const clases = input.classList;
    if (clases.contains('input-time')) {
        if (/^[0-9]{1,2}:[0-5][0-9]:[0-5][0-9]$/i.test(input.firstElementChild.value)) {
            const popover = input.nextElementSibling;
            const arrTime = input.firstElementChild.value.split(":");
            popover.querySelectorAll('input').forEach((e, ix) => {
                e.value = arrTime[ix];
            })
        }

        input.nextElementSibling.querySelector('i.ok-time-selector').addEventListener('click', () => {
            $(input).popover('hide');
        })
    }

    $(body).on('hidden.bs.popover', function (e) {
        debugger;
        const input = e.target;
        const clases = input.classList;
        if (clases.contains('input-time')) {

            if (/^[0-9]{1,2}:[0-5]{0,1}[0-9]:[0-5]{0,1}[0-9]$/i.test($oculto)) {
                const reallyInput = input.firstElementChild;
                const arrTime = $oculto.split(":");
                $oculto = ("0" + arrTime[0]).slice(-2) + ":" + ("0" + arrTime[1]).slice(-2) + ":" + ("0" + arrTime[2]).slice(-2);
                const clases = reallyInput.classList;
                reallyInput.value = $oculto;
                $(reallyInput).valid();
                if (clases.contains('marca-referencial')) {
                    $('#MarcaReferencial').valid();
                }
            }
            //Para que no intervenga si se abre en otro input
            $oculto = "";
        }
    })

    //Oculta todos los popover al quitarle el foco
    $('html').on('mouseup', function (e) {
        if (!$(e.target).closest('.popover').length) {
            $('.popover').each(function () {
                $(this.previousSibling).popover('hide');
            });
        }
    });
})