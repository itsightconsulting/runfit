(function(){

    init();

})();

function init(){
    instanciandoIndicadoresCirculo();
    getSemanasDeLaUltimaRutinaGenerada();
    getDatosDeLaUltimaRutina();
    eventTab();
    pulsosRitmos();
}

function instanciandoIndicadoresCirculo(){
    $("#indicadorCirculo1").knob({
        'format': function (value) {
            return value + '%';
        }
    });
    $("#indicadorCirculo2").knob({
        'format': function (value) {
            return value;
        }
    });

}

function eventTab() {
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        $("#indicadorCirculo3").knob({
            'format': function (value) {
                return value + '%';
            }
        });
        $("#indicadorCirculo4").knob({
            'format': function (value) {
                return value;
            }
        });
    });
}

function getSemanasDeLaUltimaRutinaGenerada(){
    $.ajax({
        type: 'GET',
        url: _ctx + 'cliente/get/obtenerSemanasPorRutina',
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
                    console.log(data);
                    //$('.datepicker_inline').data("DateTimePicker").date(data[0].fechaInicio);

                    vistaDia(data);
                    vistaMes(data);
                }
                setTimeout(  () => {
                    imgToSvg();
                }, 500);
            }

        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {

        }
    });

}
function elementosDia(val) {
    var elementoDia = val.elementos;
    var texto = "";
    if (elementoDia != null && elementoDia.length > 0){
       $.each(elementoDia, function (i, dato) {
           var subElements = showSubElementos(dato);
           texto= texto.concat(`
            <div class="panel panel-default">
              <div class="panel-heading">
                <h3>${dato.nombre}<div class="mas_menos"><img class="svg" src="img/iconos/icon_menos.svg"><img class="svg" src="img/iconos/icon_mas.svg"></div></h3>
                <div class="icons">
                  <img class="svg" src="img/iconos/icon_microfono.svg"><img class="svg" src="img/iconos/icon_leyenda.svg">
                  <span><img class="svg" src="img/iconos/icon_tiempo2.svg">${dato.minutos}</span>
                  <a data-toggle="collapse" href="#elemento-${i}"><img class="svg arrow" src="img/iconos/icon_flecha2.svg"></a>
                </div>
              </div>
              <div class="panel-collapse collapse" id="elemento-${i}">
                <div class="panel-body"><span class="text_green">1 serie x 20" de recuperación</span>
                  <ul class="principal">
                       ${subElements}
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            `);
       });
   }
   return texto;
}

function vistaDia(data) {
    var date = data;
    $.each(date, function (i, dato) {
        var fecha = dato.fechaInicio.split('/');
        var firstFecha = parseInt(date[0].fechaInicio.split('/')[1]);
        var week = parseInt(fecha[1]);
        if (week <= firstFecha) {
            var day = dato.lstDia;
            $.each(day, function (i, val) {
                var firstDate = moment(date[0].lstDia[0].fecha, "DD-MM-YYYY").week();
                var weeknumber = moment(val.fecha, "DD-MM-YYYY").week();
                var data = elementosDia(val);
                if (firstDate == weeknumber) {
                    $("#panel_days").append(
                        `<div class="panel panel-default">
                          <div class="panel-heading day"><a data-toggle="collapse" data-parent="#panel_days" href="#dia${i}">
                            <h3>${val.diaLiteral}</h3></a>
                            <div class="icons">
                              <img class="svg" src="img/iconos/icon_microfono.svg"><img class="svg" src="img/iconos/icon_leyenda.svg">
                              <span><img class="svg" src="img/iconos/icon_tiempo2.svg">${val.minutos}</span>
                              <a data-toggle="collapse" data-parent="#panel_days" href="#dia${i}"><img class="svg arrow" src="img/iconos/icon_flecha2.svg"></a>
                            </div>
                          </div>
                          <div class="panel-collapse collapse" id="dia${i}">
                            <div class="panel-body">
                              <ul class="datos">
                                <li><img class="svg" src="img/iconos/icon_programas.svg">Carrera<img class="svg help" src="img/iconos/icon_ayuda.svg"></li>
                                <li><img class="svg" src="img/iconos/icon_temporada.svg">Fuerza<img class="svg help" src="img/iconos/icon_ayuda.svg"></li>
                                <li><img class="svg" src="img/iconos/icon_cronometro.svg">${val.minutos}<img class="svg help" src="img/iconos/icon_ayuda.svg"></li>
                                <li><img class="svg" src="img/iconos/icon_km.svg">${val.distancia}<img class="svg help" src="img/iconos/icon_ayuda.svg"></li>
                              </ul>
                            <div class="panel-group elementos">
                              ${data}
                            </div>
                          </div>
                        </div>
                      </div>
                    `);
                }
            });
        }
    });
}

function vistaMes(data) {
    var date = data;
    $.each(date, function (i, dato) {
        var fecha = dato.fechaInicio.split('/');
        var firstFecha = parseInt(date[0].fechaInicio.split('/')[1]);
        var week = parseInt(fecha[1]);
        if(week <= firstFecha) {
            var day = dato.lstDia;
            $.each(day, function (i, dato) {
                var firstDate = moment(date[0].lstDia[0].fecha, "DD-MM-YYYY").week();
                var weeknumber = moment(dato.fecha, "DD-MM-YYYY").week();
                var data = elementosWeek(dato);
                if (firstDate == weeknumber) {
                    $("#carousel-semanal").append(
                        `<div class="item">
                      <div class="title">${dato.diaLiteral}</div>
                      <div class="body-card">
                        <div class="dias">Faltan 73 días<img class="svg" src="img/iconos/icon_ayuda.svg"></div>
                        <ul>
                          <li><img class="svg" src="img/iconos/icon_programas.svg">Carrera<img class="svg help" src="img/iconos/icon_ayuda.svg"></li>
                          <li><img class="svg" src="img/iconos/icon_temporada.svg">Fuerza<img class="svg help" src="img/iconos/icon_ayuda.svg"></li>
                          <li><img class="svg" src="img/iconos/icon_cronometro.svg">${dato.minutos}<img class="svg help" src="img/iconos/icon_ayuda.svg"></li>
                          <li><img class="svg" src="img/iconos/icon_km.svg">${dato.distancia}<img class="svg help" src="img/iconos/icon_ayuda.svg"></li>
                        </ul>
                        
                        ${data}
                      </div>
                    </div>`
                    );
                };

            });
        }

    });
    $(document).ready(function () {
        var json = jQuery.parseJSON(data[0].metricas);
        var tr;
        for (var i = 0; i < json.length; i++) {
            tr = $('<tr/>');
            tr.append("<td>" + json[i].nombre + "</td>");
            tr.append("<td>" + json[i].min + "</td>");
            tr.append("<td>" + json[i].max + "</td>");
            $('table.pulsos,table.ritmos').append(tr);
        }
    });
    owlCarouselSemanal();
    miniPanelActive();
}

function elementosWeek(dato) {
    var elementoDia = dato.elementos;
    var texto = "";

    if (elementoDia != null && elementoDia.length > 0){
        $.each(elementoDia, function (i, dato) {
            texto= texto.concat(`
            <div class="mini-panel"><span>${dato.nombre}<img class="svg help" src="img/iconos/icon_flecha2.svg"></span>
              <div class="time"><img class="svg help" src="img/iconos/icon_horas.svg">${dato.minutos}</div>
            </div>
            `);
        });
    }
    return texto;
}

function showSubElementos(dato) {
    var subElemento = dato.subElementos;
    var texto = "";

    if (subElemento != null && subElemento.length > 0){
        $.each(subElemento, function (i, dato) {

            if(dato.mediaVideo != null) {
                texto= texto.concat(`
                    <li>
                        <img class="svg" src="img/iconos/icon_videoteca.svg">
                      <a data-fancybox="gallery" href="https://s3-us-west-2.amazonaws.com/rf-media-rutina/video${dato.mediaVideo}">
                        <p class="title">${dato.nombre}<span>${dato.nota}</span></p>
                      </a>
                    </li>
                `);
            } else {
                texto= texto.concat(`
                    <li>
                        <img class="svg" src="img/iconos/icon_microfono.svg">
                      <p class="title">${dato.nombre}<span>${dato.nota}</span></p>
                    </li>
                `);
            }
        });
    }
    return texto;
}

function pulsosRitmos() {
    $("#panel_days .panel:first .panel-heading.day a").trigger("click");
}

function getDatosDeLaUltimaRutina(){
    $.ajax({
        type: 'GET',
        url: _ctx + 'cliente/get/ultima-rutina',
        dataType: "json",
        success: function (data, textStatus) {
            if(data.totalSemanas === 0){
                $.smallBox({content: "<span><i class='fa fa-exclamation-circle fa-fw'></i> ¡Usted aún no cuenta con alguna rutina asignada!</span>",
                                timeout: 10000});
                return;
            }
            if (textStatus == "success") {
                if (data == "-9") {
                    $.smallBox({
                        content: "<i> La operación ha fallado, comuníquese con el administrador...</i>",
                        timeout: 4500,
                        color: "alert",
                    });
                } else {
                    console.log(data);
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
