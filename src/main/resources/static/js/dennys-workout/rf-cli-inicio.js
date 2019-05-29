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
                    $('.datepicker_inline').data("DateTimePicker").date(data[0].fechaInicio);

                    var daysForWeek = data;
                    $.each(daysForWeek, function (i, dato) {
                        var mesActual = parseInt(moment().format('M'));
                        var fecha = dato.fechaInicio.split('/');
                        var mesData = parseInt(fecha[1]);
                        if (mesData == mesActual) {
                            var day = dato.lstDia;
                            $.each(day, function (i, val) {
                                var date = moment(val.fecha, 'DDMMYYYY').format("ddd MMM YYYY DD HH:mm");
                                var dayCurrent = moment(date).format('D');
                                var weekData = moment(date).isoWeek() - moment(date).subtract('days', dayCurrent - 1).isoWeek() + 1;
                                var weekOfMonth = moment().isoWeek() - moment().subtract('days', moment().format('D') - 1).isoWeek() + 1;
                                var data = elementosDia(val);
                                if (weekData == weekOfMonth) {
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
            }
            imgToSvg();
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
                       <p class="title">Zarpaso bajo 49mt<span>Alternar</span></p>
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
function pulsosRitmos() {
    $("#panel_days .panel:first .panel-heading.day a").trigger("click");
}

function getDatosDeLaUltimaRutina(){
    $.ajax({
        type: 'GET',
        url: _ctx + 'cliente/get/ultima-rutina',
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
