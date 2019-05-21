(function(){

    init();

})();

function init(){
    instanciandoIndicadoresCirculo();
    getMiUltimaRutina();
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

function getMiUltimaRutina(){
    const params = {};
    params.idrutina = 1;
    $.ajax({
        type: 'GET',
        url: _ctx + 'cliente/get/obtenerSemanasPorRutina',
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
                } else {

                    /*//Semana
                    $.each(data,function (i,item) {
                        item.fechaInicio = parseFromStringToDate2(item.fechaInicio);
                        item.fechaFin = parseFromStringToDate2(item.fechaFin);
                        $.each(item.lstDia,function(o,day){  day.fecha = parseFromStringToDate2(day.fecha); });
                        semanas.push(item);
                    });
                    semanas.sort(function(a, b){return a.id - b.id});
                    $rutina.semanas = semanas;*/
                    console.log(data);
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () {
            //generarDias();
        }
    });
}
