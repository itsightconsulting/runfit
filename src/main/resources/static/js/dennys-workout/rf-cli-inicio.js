(function(){

    init();

})();

function init(){
    instanciandoIndicadoresCirculo();
    getSemanasDeLaUltimaRutinaGenerada();
    getDatosDeLaUltimaRutina();
}

function instanciandoIndicadoresCirculo(){
    $("#indicadorCirculoI").knob({
        'format': function (value) {
            return value + '%';
        }
    });
    $("#indicadorCirculoIi").knob({
        'format': function (value) {
            return value + '%';
        }
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
