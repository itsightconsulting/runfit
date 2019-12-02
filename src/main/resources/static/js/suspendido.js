
const btnBuscarPeriodo = document.getElementById('btnBuscarPeriodo');
const dvItems = document.getElementById('dvItemsSuspendidos');
const dvCalendario = document.getElementById('dvCalendario');
const dvClientes = document.getElementById('dvClientes');
const currentDate = new Date();
const currentYear = currentDate.getFullYear();
const currentMonth = currentDate.getMonth()+1;
let currentSelectedYear = currentYear;
let currentSelectedMonth = currentMonth;
let currentPeriodoSeleccionado;
let defaultFilter;
let primerAñoTrainer;
let flagPresenteAñoSuspendido = false;

$(function() {
    init();

})

$( document ).ready(function() {


    let owl = $('.owl-carousel');
    owl.owlCarousel();

    $('.owl-item').removeClass("active");
    $('.owl-dot').each(function(index){

        const num = index +1;
        $(this).attr('value',num);
    })

    $('.owl-dot').removeClass("active");
    $('.owl-prev').removeClass("disabled");
    $('.owl-dot[value='+currentMonth+']').addClass("active");

    $('.owl-dot').click(function(){
        let selectedDate =parseFromStringToDate(`${currentSelectedYear}-${currentSelectedMonth}-01`);
        let incrementador = parseInt($(this).val()) - currentSelectedMonth;
        selectedDate.setMonth(selectedDate.getMonth() + incrementador);

        if(selectedDate > currentDate){
            return false;
        }
        else{
            $('.owl-dot').removeClass('active');
            $(this).addClass('active');
            currentSelectedMonth =parseInt($(this).val());
            //   visualizarMesSeleccion(currentSelectedMonth, currentSelectedYear);
            let formatoMes = ('0' +currentSelectedMonth).slice(-2);
            cargarClientesSuspendidos(`${currentSelectedYear}-${formatoMes}`);

            return false;
        }
    })

    $('.owl-prev').click(function() {


            if (currentSelectedMonth === 1) {

                if(currentSelectedYear - 1 < primerAñoTrainer){
                    return false;
                }else {
                    currentSelectedYear--;
                    currentSelectedMonth = 12;
                }
            } else {
                currentSelectedMonth--;
            }

            $('.owl-dot').removeClass('active');
            $('.owl-dot[value=' + currentSelectedMonth + ']').addClass('active');
            let formatoMes = ('0' +currentSelectedMonth).slice(-2);
            cargarClientesSuspendidos(`${currentSelectedYear}-${formatoMes}`);

        }
    )

    $('.owl-next').click(function(){

            $('.owl-item').removeClass("active");

            let selectedDate =parseFromStringToDate(`${currentSelectedYear}-${currentSelectedMonth}-01`);
            selectedDate.setMonth(selectedDate.getMonth() + 1);

            if(selectedDate > currentDate){
                owl.trigger('prev.owl.carousel');
                $('.owl-dot').removeClass('active');
                $('.owl-dot[value=' + currentSelectedMonth + ']').addClass('active');
                return false
            }
            else {

                if (currentSelectedMonth === 12) {
                    currentSelectedYear++;
                    currentSelectedMonth = 1;
                    //deshabilito la función de avanzar al siguiente slide
                    owl.trigger('prev.owl.carousel');
                    owl.trigger('prev.owl.carousel');

                } else {
                    //deshabilito la función de avanzar al siguiente slide
                    currentSelectedMonth++;
                    owl.trigger('prev.owl.carousel');
                }
            }
            $('.owl-dot').removeClass('active');
            $('.owl-dot[value=' + currentSelectedMonth + ']').addClass('active');
            let formatoMes = ('0' +currentSelectedMonth).slice(-2);
            cargarClientesSuspendidos(`${currentSelectedYear}-${formatoMes}`);

        }
    )
});

function init(){
    defaultFilter = getDefaultDateQuery();
    getPeriodosClientesSuspendidos();
    cargarClientesSuspendidos(defaultFilter);
    btnBuscarPeriodo.addEventListener('click',function(evt) {
        evt.preventDefault();
        busquedaSuspendidosporInput(evt);
    } ,false);
    validacionFiltroPeriodo();

    $('#colores_suspendidos').tooltip({
        selector: "a[rel=tooltip]"
    })

    $("#filtroPeriodo-form").focusout(focusOutInputPeriodoEvent);


}



function getDefaultDateQuery(){

    const filterQuery= currentYear +'-'+('0' + (currentMonth)).slice(-2);


    return filterQuery;
}
function getMonthFilterQuery(filter){

    const month = filter.slice(0,2);
    const year = filter.slice(3,7);

    return year+"-"+month;

}
function getDBMonthQuery(filter){

    const month = filter.slice(0,2);
    const year = filter.slice(2,6);

    return year+"-"+month;


}

function getMonthQuery(filter){
    const month = filter.slice(0,2);
    const year = filter.slice(3,7);

    return year+"-"+month;
}

function cargarClientesSuspendidos(periodoSeleccionado){

    imgtoSvgEvent();
    let month = periodoSeleccionado.slice(5,7);
    const year = periodoSeleccionado.slice(0,4);

    $.ajax({
        type: 'GET',
        url: _ctx + 'gestion/trainer/red/suspendidos/obtener',
        dataType: 'json',
        data:{mes:periodoSeleccionado},
        blockLoading: false,
        noOne: true,
        success: function (res) {

            currentSelectedYear = parseInt(year);
            currentSelectedMonth = parseInt(month);

            $('.owl-dot').removeClass('active');
            $('.owl-dot[value=' + currentSelectedMonth + ']').addClass('active');

            if(res.length === 0){
                limpiarListado();
                const noOne = document.createElement('div');
                noOne.className = "";
                noOne.style.fontSize = "1.3em";
                noOne.style.color = "#a8fa00";
                noOne.style.padding = "15px 0px";
                noOne.style.textAlign = "center";
                noOne.innerHTML = "<i class='fa fa-info-circle fa-fw'></i>No se cuenta con clientes suspendidos para este periodo";
                document.getElementById('listaSuspendidos').appendChild(noOne);
            }else{

                limpiarListado();
                recrearListado(res);
            }
        }, error: (xhr) => {
            exception(xhr);
        }, complete: () => {
            visualizarMesSeleccion(month,year);
            limpiarInputPeriodo();
            $('section#suspendidos .help-block').hide();


            $('.mes').removeClass("seleccionado");
            $(`#d${month}${year}`).addClass("seleccionado");

        }
    })
}

function activarCliente(e){

    const clienteId = e;

    $.ajax({
        type: 'PUT',
        url: _ctx + 'gestion/trainer/red/suspendidos/activar',
        dataType: 'json',
        data: {id: clienteId},
        blockLoading: false,
        noOne: true,
        success: function (res) {
            let formatoMes = ('0' +currentSelectedMonth).slice(-2);
            cargarClientesSuspendidos(`${currentSelectedYear}-${formatoMes}`);

            setTimeout(()=>{
                $.smallBox({content: "<i> Operación exitosa.</i>"});
              }, 1000);
        }
        , error: (xhr) => {
            exception(xhr);
        }, complete: () => {
            getPeriodosClientesSuspendidos();
        }
    })
}

function visualizarMesSeleccion(mes,año){

    const monthNames = ["enero", "febrero", "marzo", "abril", "mayo","junio","julio", "agosto", "septiembre", "octubre", "noviembre","diciembre"];
    $('#tituloGestionSuspendidos').html( "suspendidos - <span>"+monthNames[mes-1]+" "+año+"</span>");
    $('#fechaListaSuspendidos').html( ""+monthNames[mes-1]+" "+año);
}

function busquedaSuspendidosporInput(){


    if ($("#filtroPeriodo-form").valid()) {

        const periodo = $('#inputPeriodo').val();
        const filtro = getMonthFilterQuery(periodo);
        currentPeriodoSeleccionado = filtro;
        getPeriodosClientesSuspendidos();
        cargarClientesSuspendidos(filtro);
    }
}

function validacionFiltroPeriodo(){



    $("#filtroPeriodo-form").validate({
        // Rules for form validation}
        errorClass: 'help-block',
        highlight: function (element) {
            $(element).parent().removeClass('state-success').addClass("state-error");
            $(element).removeClass('valid');
        },
        unhighlight: function (element) {
            $(element).parent().removeClass("state-error").addClass('state-success');
            $(element).addClass('valid');
        },
        rules: {
            inputPeriodo:{
                required: true,
                monthInputValid: true,
                greaterThanMonth: function() {
                    return new Date(primerAñoTrainer,0,1);
                },
                lessThanMonth: function(){
                    return new Date();
                }
            }
        },
        // Messages for form validation
        messages: {
        },
        // Do not change code below
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }
    });
}

function cargarPeriodosClientesSuspendidos(data){
    const periodoObject = getPeriodoObject(data);
    if(periodoObject[0].year !== "") {

        dvCalendario.style.display ="block";
        dvClientes.style.display ="block";

        cargarTituloCalendario();

        for (let i = 0; i < periodoObject.length; i++) {

            const año = periodoObject[i].year;
            cargarAños(periodoObject[i].year, i + 1);
            cargarMeses(periodoObject[i].year, i + 1);

            for (let j = 0; j < periodoObject[i].meses.length; j++) {

                const mes = periodoObject[i].meses[j];
                const selector = "d" + ("0" + mes).slice(-2) + año;
                const a = document.getElementById(selector);
                a.classList.add("suspension");
            }
        }

        const dataCurrentYear = periodoObject.find(function(element){
            return parseInt(element.year) === currentYear;
        })

        if(!dataCurrentYear){
            cargarAños(currentYear, periodoObject.length+1);
            cargarMeses(currentYear,periodoObject.length+1);
        }

        for (let i = currentMonth + 1; i <= 12; i++) {
            const selector = "d" + ("0" + i).slice(-2) + currentYear;
            const a = document.getElementById(selector);
            a.classList.add("indebido");
        }

    }else{
        cargarTituloCalendario();
        cargarAños(currentYear, 1);
        cargarMeses(currentYear,1);

        for (let i = currentMonth + 1; i <= 12; i++) {
            const selector = "d" + ("0" + i).slice(-2) + currentYear;
            const a = document.getElementById(selector);
            a.classList.add("indebido");
        }
    }

    imgtoSvgEvent();
    mesCalendarioClickEvent();

}

function cargarAños(año,index)
{
    const calendarioSuspendidos = document.getElementById('calendarioSuspendidos');
    calendarioSuspendidos.appendChild(htmlStringToElement(`<div class="panel-heading">
                                                        <h3 class="panel-title" ><img class="svg" src="${_ctx}img/iconos-trainers/icon_calendar2.svg">${año}<a data-toggle="collapse" href="#collapse${index}"><img class="arrow svg" src="${_ctx}img/iconos-trainers/icon_flecha2.svg" onload="imgToSvg()"></a></h3>
                                                          </div>`));
}


function cargarMeses(año,index){

    const calendarioSuspendidos = document.getElementById('calendarioSuspendidos');
    index === 1 ? primerAñoTrainer = parseInt(año):"";
    parseInt(año) === currentYear ?  flagPresenteAñoSuspendido : !flagPresenteAñoSuspendido;

    calendarioSuspendidos.appendChild(htmlStringToElement(`
         <div class="panel-collapse collapse" id="collapse${index}" data-value="${año}">
                <ul class="list-group">
                <li class="list-group-item">
                <div class="row">
                <div class="col-xs-3">
                <div class="mes"  id="d01${año}" >Mes 1</div>
            </div>
            <div class="col-xs-3">
                <div class="mes" id="d02${año}">Mes 2</div>
            </div>
            <div class="col-xs-3">
                <div class="mes" id="d03${año}">Mes 3</div>
            </div>
            <div class="col-xs-3">
                <div class="mes"  id="d04${año}">Mes 4</div>
            </div>
            </div>
            </li>
            <li class="list-group-item">
                <div class="row">
                <div class="col-xs-3">
                <div class="mes" id="d05${año}">Mes 5</div>
            </div>
            <div class="col-xs-3">
                <div class="mes" id="d06${año}"> Mes 6</div>
            </div>
            <div class="col-xs-3">
                <div class="mes" id="d07${año}"> Mes 7</div>
            </div>
            <div class="col-xs-3">
                <div class="mes" id="d08${año}"> Mes 8</div>
            </div>
            </div>
            </li>
            <li class="list-group-item">
                <div class="row">
                <div class="col-xs-3">
                <div class="mes" id="d09${año}">Mes 9</div>
            </div>
            <div class="col-xs-3">
                <div class="mes" id="d10${año}"> Mes 10</div>
            </div>
            <div class="col-xs-3">
                <div class="mes" id="d11${año}"> Mes 11</div>
            </div>
            <div class="col-xs-3">
                <div class="mes" id="d12${año}"> Mes 12</div>
            </div>
            </div>
            </li>
            </ul>
            </div>

       `));


}

function getPeriodoObject(data) {

    let infoPeriodos = data;

    infoPeriodos = data.split(',');
    let periodoObject=[];

    for(let i = 0; i<infoPeriodos.length;i++){
        const index =periodoObject.findIndex( x =>  x.year === infoPeriodos[i].slice(0,4));

        if(index === -1){
            let mes = infoPeriodos[i].slice(4,6);
            mes = parseInt(mes);
            const periodo = {year:infoPeriodos[i].slice(0,4), meses :[mes]};
            periodoObject.push(periodo);
        }
        else{
            let mes = infoPeriodos[i].slice(4,6);
            mes = parseInt(mes);
            periodoObject[index].meses.push(mes);
        }
    }

    return periodoObject;
}


function getPeriodosClientesSuspendidos (){

    $.ajax({
        type:"GET",
        url: _ctx + 'gestion/trainer/red/suspendidos/obtener/periodos',
        success: function (data) {
            limpiarCalendario();
            cargarPeriodosClientesSuspendidos(data);

        }
        , error: (xhr) => {
        }, complete: () => {

        }
    });
}


function getClienteSeleccionado(clienteId, redFitnessId){

    return _ctx+"gestion/trainer/red/consultar/cliente?id="+clienteId+"&rfId="+redFitnessId;
}

function limpiarListado(){

    const listaSuspendidos = document.getElementById('listaSuspendidos');

    while (listaSuspendidos.firstChild) {
        listaSuspendidos.removeChild(listaSuspendidos.firstChild);
    }
}

function limpiarCalendario(){
    const calendariosuspendidos = document.getElementById('calendarioSuspendidos');
    while (calendariosuspendidos.firstChild) {
        calendariosuspendidos.removeChild(calendariosuspendidos.firstChild);
    }
}

function recrearListado(arr){

    const listaSuspendidos = document.getElementById('listaSuspendidos');

    arr.forEach((e)=>{
        let date = e.fechaSuspension;
        date = date.slice(0,10);

        listaSuspendidos.appendChild(htmlStringToElement(`<li>
            <div class="row">
                <div class="col-sm-8"><img class="user" src="${_ctx}img/user_avatar.png">
                <h4>${e.cliNombreCompleto}<span> ${date}</span></h4>
            </div>
            <div class="col-sm-4"><a class="btn_Activar" href="javascript:;"  onclick="activarCliente(${e.id}); return false;">activar</a><a href="#" onClick="document.location.href=getClienteSeleccionado(${e.cliId},${e.id});"  >ver perfil</a></div>
            </div>
            </li>`));
    })
}

function cargarTituloCalendario(){
    const calendariosuspendidos = document.getElementById('calendarioSuspendidos');
    calendariosuspendidos.appendChild(htmlStringToElement(`<h4> CALENDARIO ANUAL </h4>`));
}

function mesCalendarioClickEvent(){

    $(".mes").click(function(e){
        let periodo = e.target.id.slice(1);
        periodo = getDBMonthQuery(periodo);
        periodoDate =parseFromStringToDate(periodo+"-01");

        if(periodoDate > currentDate){
            return false;
        }else {
            currentPeriodoSeleccionado = periodo;
            cargarClientesSuspendidos(periodo);
        }
    });

}

function limpiarInputPeriodo(){
    $('#inputPeriodo').val('');
}

function focusOutInputPeriodoEvent(){
    $('.owl-dot').removeClass('active');
    $('.owl-dot[value=' + currentSelectedMonth + ']').addClass('active');
}