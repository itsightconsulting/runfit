let rutinas_ = [];
let indexGlobal = 0;
const $dia = document.querySelector('#rutinaDiaSemana');

$(function () {

    listarMultimediaEntrenadores();

});


function listarMultimediaEntrenadores() {

    $.ajax({
        type: 'GET',
        url: _ctx + 'cliente/get/miniplantillasentrenador',
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
                    rutinas_ = data;

                    $.each(data,function(i,item){
                        item.literal = "";
                        item.dia = "";
                        item.flagDescanso = false;
                    });

                    GenerarDiaRutinaHtml(data);
                }
            }
        },
        error: function (xhr) {
            exception(xhr);
        },
        complete: function () { }
    });
}

function GenerarDiaRutinaHtml(data){

    let rawDias = armarHtmlDayEncontrado(data);

    let RutinaSemanaDiv = document.createElement('div');
    RutinaSemanaDiv.innerHTML = rawDias;

    $dia.innerHTML = '';
    $dia.append(RutinaSemanaDiv);

}


