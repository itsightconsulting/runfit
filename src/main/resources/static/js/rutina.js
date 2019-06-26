class Rutina {

    constructor(obj) {
        this.fechaInicio = parseFromStringToDate2(obj.fechaInicio);
        this.fechaFin = parseFromStringToDate2(obj.fechaFin);
        this.fechaCliAcceso = parseFromStringToDate2(obj.fechaCliAcceso != null ? obj.fechaCliAcceso : "01/01/2000");
        this.meses = obj.meses;
        this.anios = obj.anios;
        this.totalSemanas = obj.totalSemanas;
        this.dias = obj.dias;
        this.semanas = new Array(this.totalSemanas);
        this.tipoRutina = obj.tipoRutina;
        this.control = obj.control;
        this.flagActivo = obj.flagActivo;
    }

    init(primeraSemana) {
        this.semanas[0] = new Semana(primeraSemana);
        this.mostrarSemana(this.semanas[0], 0);
        this.completarFechasSemanas(false);
    }

    initEspecifico(semana, num){
        this.semanas[num] = new Semana(semana);
        this.mostrarSemana(this.semanas[num], num);
        this.completarFechasSemanas(true, num);
    }

    initEspecificoDesdeRutina(num){
        this.mostrarSemana(this.semanas[num], num);
        this.completarFechasSemanas(true, num);
    }

    mostrarSemana(sem, sIndex) {
        //Inicialmente generamos solo la primera semana de todo el plan de entrenamiento
        let semana = sem;
        let rawDias = '';
        let diasRestantes = [];
        let rawDiasRestantes = '';

        if (!semana.flagFull) {
            let iter = 7 - semana.dias.length;
            if(sIndex == 0){
                for (let i = 0; i < iter; i++) {
                    let dt = parseFromStringToDate(moment(new Date(semana.dias[0].fecha)).subtract(i + 1, 'days').format('YYYY-MM-DD'));
                    diasRestantes.push(dt);
                }
                //Invertimos el arreglo para obtenerlo correctamente de menor a mayor fecha
                diasRestantes.reverse();
            }else{
                for (var i = 0; i < vacios; i++) {
                    let dt = parseFromStringToDate(moment(new Date(semana.dias[semana.dias.length - 1].fecha)).add(i + 1, 'd').format('YYYY-MM-DD'));
                    diasRestantes.push(dt);
                }
            }

            diasRestantes.forEach((v) => {
                v.literal = dias[v.getDay()];//Constante from jsfuente.js
                v.dia = v.getDate();
                rawDiasRestantes +=
                    `<article class="col-xs-12 col-sm-3 col-md-3 col-lg-3 rf-dia-off" >            
									<div class="jarviswidget jarviswidget-color-blueLight margin-bottom-0">
										<header role="heading" class="heading-off">
								            <h2>${v.literal} ${v.dia}</h2>
								            ${!semana.flagFull ?
                        `<div class="widget-toolbar borderless" onclick="focoARutina();">
								            			<a href="javascript:void(0);" rel="tooltip" data-placement="bottom" data-original-title="Avanzar hasta la rutina del primer día"><i class="fa fa-arrow-right fa-15x"></i></a>
								            		 </div>` : ''}
                                                     </header>
                                                     <!-- widget div-->
                                                     <div class="rf-colum-diff">
                                                         <!-- widget content -->
                                                         <div class="widget-body">
                                                             <!-- smart-accordion-default -->
                                                             <div class="panel-group smart-accordion-default">
                                                             <!-- DIAS DE SEMANA -->
                                                                 <h1 class="text-align-center">${sIndex != 0 ? `<img class="max-wd" src="${_ctx}img/finish_strong.png"/>` : `<img class="max-wd" src="${_ctx}img/empieza_reto.jpg"/>`}</h1>
                                                                 <!-- END DIAS DE SEMANA -->
                                                             </div>													
                                                         </div>
                                                         <!-- end widget content -->											
                                                     </div>
                                                     <!-- end widget div -->
									</div>
								</article>`;
            });
        }

        semana.dias.forEach((v, i) => {
            const val = v.elementos.length>0?undefined:'showInputsInit';//La validacion requiere un null;
            const flagDescanso = v.flagDescanso;
            const mes = v.fecha.getMonth()+1;
            const checked = v.flagEnvioCliente ? "checked='checked'": "";

            rawDias +=
                `<article class="col-xs-12 col-sm-3 col-md-3 col-lg-3 rf-dia" data-index="${i}" data-fecha="${v.fechaCorta()}">            
									<div class="jarviswidget jarviswidget-color-blueLight margin-bottom-0" tabindex='${i}'>
										<header role="heading" class="heading-dia">
								            <span class="widget-icon"> <a href="#" rel="tooltip" data-placement="right" data-original-title="Copiar día"><i class="fa fa-calendar-o txt-color-blue copiar-dia" data-index="${i}"></i></a></span>
								            <h2 class="titulo-dia">${v.literal} ${v.dia}</h2>
								            <div class="widget-toolbar hidden-phone borderless">
								            <div class="widget-toolbar borderless"><a href="javascript:void(0);" rel="tooltip" data-placement="bottom" data-original-title="Pegar mini Plantilla"><i class="fa fa-paste text-primary pegar-mini" data-index="${i}"></i></a></div>
								            <div class="widget-toolbar borderless"><a href="javascript:void(0);" rel="tooltip" data-placement="bottom" data-original-title="Marca el día como día de descanso"><i class="fa fa-refresh text-warning marcar-descanso" data-index="${i}"></i></a></div>
								            <div class="widget-toolbar borderless"><a href="javascript:void(0);" rel="tooltip" data-placement="bottom" data-original-title="Guardar día en mis plantillas"><i class="fa fa-save txt-color-greenLight pre-guardar-dia" data-index="${i}"></i></a></div>
								            <div class="widget-toolbar borderless"><a href="javascript:void(0);" rel="tooltip" data-placement="bottom" data-original-title="Pegar elementos elegidos"><i class="fa fa-list-alt text-primary pegar-mini-listas" data-index="${i}"></i></a></div>
								            <div class="widget-toolbar borderless"><a href="javascript:void(0);" rel="tooltip" data-placement="bottom" data-original-title="Agregar objetivo"><i class="fa fa-dot-circle-o txt-color agregar-objetivo" data-index="${i}"></i></a></div>
								            <div class="widget-toolbar borderless" style="line-height: 38px;"><input ${checked} id="cdia${v.dia}_${mes}" data-id="${v.dia}" data-mes="${mes}" class="enviar-cliente" type="checkbox" rel="tooltip" data-placement="bottom" data-original-title="Enviar al Cliente" /></div>
								            </div>
								        </header>
								        <div role="heading">
								            <div class="col-sm-12">
								            	<i class="fa fa-clock-o fa-fw pull-left fa-15x"></i><span class="pull-left horas-totales padding-bottom-10">${parseNumberToHours(v.minutos)}</span>
								            	<span class="pull-right fa fa-fw txt-color-blue fa-15x margin-right-13" style="font-family: 'Open Sans',Arial,Helvetica,Sans-Serif">KM</span><span class="pull-right distancia-total padding-bottom-10 margin-right-5">${parseNumberToDecimal(v.distancia, 2)}</span>
								            </div>					    		
								        </div>
								        <!-- widget div-->
								        <div class="padding-0">
								            <!-- widget content -->	
								            ${RutinaDiaHTML.full(v.elementos, i, val, flagDescanso)}
								            <!-- end widget content -->											
								        </div>
								        <!-- end widget div -->
									</div>
								</article>`;
        });

        let RutinaSemanaDiv = document.createElement('div');
        //Ccn esta condición nos aseguramos que siempre que la primera semana de toda la rutina empiece por un día diferente a martes, los primeros días vacíos vayan al inicio
        if (!semana.flagFull && sIndex == 0){
            rawDiasRestantes += rawDias;
            RutinaSemanaDiv.innerHTML = rawDiasRestantes;
        }else{
            rawDias += rawDiasRestantes;
            RutinaSemanaDiv.innerHTML = rawDias;
        }

        $semanario.innerHTML = '';
        $semanario.append(RutinaSemanaDiv);
        //Seteamos un ancho especifico de acuerdo a los 7 días de la semana para poder hacer un scrolling en X
        $(".of_carousel").width($(".of_carousel article").length * $(".of_carousel article")[0].clientWidth) + $(".of_carousel article").length * $(".of_carousel article")[0].clientWidth * 2;
        $('#RutinaSemana').attr('data-index', sIndex);

        //Seteando el mes y el año en el encabezado de la rutina
        $('#MesAnio').text(meses[semana.dias[0].fecha.getMonth()] +" - "+ semana.dias[0].fecha.getFullYear());

        //Modificando selector de semana
        RutinaOpc.iniciarSemanas(this.totalSemanas);
        instanciarPopovers();
        instanciarTooltips();
        RutinaOpc.instanciarCopiarSemanaCompleta();
        RutinaOpc.instanciarFlagActivo();
        if($rutina.tipoRutina !== TipoRutina.GENERAL){
            Indicadores.instanciarIndicador0();
            Indicadores.instanciarIndicadores1();
            Indicadores.instanciarIndicadores2();
            Indicadores.instanciarKilometrajes();
            Indicadores.instanciarPorcentajeAvance();
        }
    }

    agregarNuevaSemana() {
        let fechaInicioNueSem = parseFromStringToDate(moment(this.fechaFin).add(1, 'd').format('YYYY-MM-DD'));
        let diaSemana = this.fechaFin.getDay();
        //dias para Completar Semana
        let fechaFinNueSem = parseFromStringToDate(moment(fechaInicioNueSem).add(6, 'd').format('YYYY-MM-DD'));

        this.semanas.push(new Semana(null, fechaInicioNueSem, fechaFinNueSem));
        this.totalSemanas += 1;
        this.fechaFin = fechaFinNueSem;

        this.semanas[this.semanas.length-1].totalSemanas = this.totalSemanas;
        $('#SemanaActual').text(Number($('#SemanaActual').text())+1);
    }

    //Necesario para el correcto funcionamiento del calendario
    completarFechasSemanas(especifico, ix){

        if(!especifico){
            for(let i=0; i< $rutina.totalSemanas-1;i++){
                $rutina.semanas[i + 1] = new Semana(undefined, parseFromStringToDate(moment($rutina.semanas[i].fechaFin).add(1, 'd').format('YYYY-MM-DD')),parseFromStringToDate(moment($rutina.semanas[i].fechaFin).add(7, 'd').format('YYYY-MM-DD')));
            }
        }else{
            for(let i=0; i<$rutina.totalSemanas;i++){
                if(i != ix){
                    const refDia = $rutina.fechaInicio.getDay();
                    let diasParaFull = refDia==0?0:7-refDia;
                    if(i == 0){
                        $rutina.semanas[i] = new Semana(undefined,
                            parseFromStringToDate(moment($rutina.fechaInicio).add((i*7), 'd').format('YYYY-MM-DD')),
                            parseFromStringToDate(moment($rutina.fechaInicio).add((i*7)+diasParaFull, 'd').format('YYYY-MM-DD')));
                    }else{
                        $rutina.semanas[i] = new Semana(undefined, parseFromStringToDate(moment($rutina.semanas[i-1].fechaFin).add(1, 'd').format('YYYY-MM-DD')),parseFromStringToDate(moment($rutina.semanas[i-1].fechaFin).add(7, 'd').format('YYYY-MM-DD')));
                    }
                }
            }
        }
    }
}

class Semana{
    constructor(obj, fechaInicio, fechaFin){
        if(obj != undefined){
            this.fechaInicio = parseFromStringToDate2(obj.fechaInicio);
            this.fechaFin = parseFromStringToDate2(obj.fechaFin);
            this.flagFull = obj.flagFull;
            this.dias = this.instanciarDias(obj.lstDia);
            this.metricas = obj.metricas != undefined ? obj.metricas != "" ? JSON.parse(obj.metricas) : "" : "";
            this.metricasVelocidad = obj.metricasVelocidad != undefined ? obj.metricasVelocidad != "" ? JSON.parse(obj.metricasVelocidad) : "" : "";
            this.kilometrajeTotal = obj.kilometrajeTotal;
            this.objetivos = obj.objetivos;
            this.flagEnvioCliente = obj.flagEnvioCliente;
        }else{
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
            this.flagFull = true;
            this.dias = this.instanciarDiasNuevaSemana();
            this.metricas = "";
            this.kilometrajeTotal = 0;
            this.flagEnvioCliente = false;
        }
    }

    instanciarDias(dias){
        let arrayDias = new Array(dias.length);
        for (let i = 0; i < arrayDias.length; i++) {
            arrayDias[i]  = new Dia(dias[i]);
        }
        return arrayDias;
    }

    instanciarDiasNuevaSemana(){
        let arrayDias = new Array(7);
        for (let i = 0; i < arrayDias.length; i++) {
            let d  = new Dia(null, sumarDiasAespecificoDia(this.fechaInicio, i));
            arrayDias[i] = d;
        }
        return arrayDias;
    }
}

class Dia{
    constructor(obj, fechaDia){
        if(obj != undefined){
            this.fecha = parseFromStringToDate2(obj.fecha);
            this.dia = obj.dia;
            this.literal = obj.literal;
            this.flagDescanso = obj.flagDescanso;
            this.elementos = this.instanciarElementos(obj.elementos);
            this.diaLiteral = this.literal + " " + this.dia;
            this.minutos = obj.minutos==undefined?0:obj.minutos;
            this.distancia = obj.distancia==undefined?0:obj.distancia;
            this.calorias = obj.calorias==undefined?0:obj.calorias;
            this.flagEnvioCliente = obj.flagEnvioCliente;
        }else{
            this.fecha = fechaDia;
            this.dia = fechaDia.getDate();
            this.literal = this.determinarDiaLiteral();
            this.flagDescanso = false;
            this.elementos = [];
            this.diaLiteral = this.literal + " " + this.dia;
            this.minutos = 0;
            this.distancia = 0;
            this.flagEnvioCliente = false;
        }
    }

    instanciarElementos(elementos){
        if(elementos != undefined){
            let arrayElementos = new Array(elementos.length);
            for (let i = 0; i < arrayElementos.length; i++) {
                arrayElementos[i]  = new Elemento(elementos[i]);
            }
            return arrayElementos;
        }else{
            return [];
        }
    }

    determinarDiaLiteral(){
        return dias[this.fecha.getDay()];
    }

    fechaCorta(){
        return moment(this.fecha).format('YYYY-MM-DD');
    }
}

class Elemento{
    constructor(obj){
        this.nombre= obj.nombre;
        this.nota= obj.nota;
        this.mediaAudio= obj.mediaAudio;
        this.mediaVideo= obj.mediaVideo;
        this.minutos = obj.minutos==undefined?0:obj.minutos;
        this.distancia = obj.distancia==undefined?0:obj.distancia;
        this.estilos = obj.estilos==undefined?[]:obj.estilos;
        this.subElementos = obj.subElementos==undefined?[]:obj.subElementos;
        this.tipo = obj.tipo;
    }
}

class SubElemento{
    constructor(obj){
        this.nombre= obj.nombre;
        this.nota= obj.nota;
        this.mediaAudio= obj.mediaAudio;
        this.mediaVideo= obj.mediaVideo;
        this.estilos = obj.estilos==undefined?[]:obj.estilos;
        this.tipo = obj.tipo;
    }
}

class ElementoLista{
    constructor(nombre, media, tipo){
        this.nombre = nombre;
        this.media = media;
        this.estilos = [];
        this.tipo = tipo;
    }
}

class Estilo{
    constructor(id, clase, tipo){
        this.id = id;
        this.clase = clase;
        this.tipo = tipo;
    }
}

RutinaOpc = (function(){
    return {
        iniciarSemanas: (totalSem)=>{
            let sems = '';
            const semLineal = document.getElementById('SemanasLineal');
            if(totalSem <=4){
                for(let i=0; i<totalSem;i++){
                    sems += `<a href="javascript:void(0);"><span class="badge bg-color-darken font-md numero-semana" data-index="${i}">${i+1}</span></a>`
                }
            }else{
                sems += `<a href="javascript:void(0);"><i id="CalendarioRf" rel="popover" data-toggle="popover" data-placement="right" data-html="true" data-content="" class="fa fa-calendar fa-2x abrir-calendario"></i></a>`
            }
            semLineal.innerHTML = sems;
        },
        abrirCalendario: (semanasIxs, edicion, mes)=>{
            semanasIxs.sort((a, b) => a - b);
            const primeraSemana = $rutina.semanas[semanasIxs[0]];
            let ixUltimaSemana = semanasIxs[semanasIxs.length-1];
            let ultimaSemana = $rutina.semanas[ixUltimaSemana];
            let fechaReferencial = primeraSemana.fechaInicio.getMonth() == mes ? primeraSemana.fechaInicio: primeraSemana.fechaFin;
            const anioFechaReferencial = fechaReferencial.getFullYear();
            const mesFechaReferencial = fechaReferencial.getMonth();

            const diasMes = DiaFunc.obtenerDiasByMes(anioFechaReferencial, mesFechaReferencial);
            const mesArray = new Array(diasMes);

            const calendarSem = []; let calendarBody = "";
            let diaFor = new Date(anioFechaReferencial, mesFechaReferencial, 1);

            //Obteniendo los dias que tendran cada semana como arreglo(Ejemplo de arreglo mes 31 días: 7|7|7|7|3)
            for(let i=0; i<mesArray.length;i++){
                if(i==0){
                    calendarSem[i] = diaFor.getDay() == 0? 1 :7 - diaFor.getDay() + 1;//Cantidad de iteraciones
                }else{
                    if(diaFor.getDay()==1){//Para considerar unicamente cuando el día en iteración sea Lunes
                        const disRestantes = diasMes - calendarSem[0] - 7 * (calendarSem.length - 1) > 6 ? Math.abs((i%7)-calendarSem[0]) : Math.abs(diasMes - calendarSem[0] - 7*(calendarSem.length-1));
                        calendarSem[calendarSem.length] = disRestantes == 0?7:disRestantes;//Cantidad de iteraciones
                    }
                }
                diaFor = new Date(anioFechaReferencial, mesFechaReferencial, i+2)//+2 para que no se guarde como el valor inicial y aumente siempre en un día
            }

            let min = primeraSemana.fechaInicio.getMonth() != mesFechaReferencial ? 1 : primeraSemana.fechaInicio.getDate();
            let max = 0;
            if (ixUltimaSemana == $rutina.totalSemanas-1) {
                if(ultimaSemana.fechaInicio.getMonth() != ultimaSemana.fechaFin.getMonth()){
                    if(ultimaSemana.fechaInicio.getMonth() == mesFechaReferencial){
                        max = diasMes;
                    }else{
                        max = ultimaSemana.fechaFin.getDate();
                    }
                }else{
                    max = ultimaSemana.fechaFin.getDate();
                }
            }else{
                if(ultimaSemana.fechaInicio.getMonth() != ultimaSemana.fechaFin.getMonth()){
                    if(ultimaSemana.fechaInicio.getMonth() == mesFechaReferencial){
                        max = diasMes;
                    }else{
                        max = ultimaSemana.fechaFin.getDate();
                    }
                }else{
                    max = ultimaSemana.fechaFin.getDate();
                }
            }

            let primSem = 0;
            let d = 1, diaAnteriorAlMes = new Date(anioFechaReferencial, mesFechaReferencial, 0).getDate();
            //RECREACION DE LOS DIAS DEL MES
            //BODY DEL CALENDARIO
            calendarSem.forEach((v,i)=>{
                calendarBody += `<div class="row seven-cols">`;
                //Cuando la semana inicial no es completa y requiere crear dias iniciales para completarla
                if(i==0 && v < 7) {
                    for (let z = 0; z < 7-v; z++) {
                        calendarBody+=`<div class="col-md-1 col-sm-1 col-xs-1 col-lg-1  font-md mini txt-color-grayDark text-align-center">${diaAnteriorAlMes-(6-v)+z}</div>`
                    }
                }
                //Solo cumple para el primer mes/primeras semanas
                if(semanasIxs[0] == 0){
                    for(let x=0; x < v; x++){
                        if(d>= min && d<=max) {
                            calendarBody += `<div class="col-lg-1 col-md-1 col-xs-1 col-sm-1 font-md mini fechas-calendar text-align-center" data-dia="${d}" data-index="${primSem}">${d++}<i class="fa fa-circle event-calendar"></i></div>`;
                        }else{
                            calendarBody+=`<div class="col-lg-1 col-md-1 col-xs-1 col-sm-1 font-md mini txt-color-grayDark text-align-center">${d++}</div>`;
                        }
                    }
                    if(d> min && d<=max)
                        primSem++;
                    //Solo cumple para el último mes/últimas semana
                }else if(semanasIxs[semanasIxs.length-1] == $rutina.totalSemanas-1) {
                    for(let x=0; x < v; x++) {
                        if (d <= max) {
                            calendarBody += `<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 font-md mini fechas-calendar text-align-center" data-dia="${d}" data-index="${i}">${d++}<i class="fa fa-circle event-calendar"></i></div>`;
                        } else {
                            calendarBody += `<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 font-md mini txt-color-grayDark text-align-center" data-index="${i}">${d++}</div>`;
                        }
                    }
                }
                //Los meses intermedios o full
                else{
                    for (let x = 0; x < v; x++) {
                        calendarBody += `<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 font-md mini fechas-calendar text-align-center" data-dia="${d}" data-index="${i}">
                                            ${d++}
                                            <i class="fa fa-circle event-calendar"></i>
                                         </div>`;
                    }
                }

                //Cuando la semana final no esta completa y requiere crear dias iniciales para completarla
                if(i==calendarSem.length-1 && v < 7) {
                    for (let z = 0; z < 7-v; z++) {
                        calendarBody+=`<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1 font-md mini txt-color-grayDark text-align-center">${1+z}</div>`
                    }
                }
                calendarBody += `</div>`;
            })

            const iconCalendar = document.querySelector('#CalendarioRf');
            if(edicion){
                if(iconCalendar.getAttribute('data-content') == ''){
                    iconCalendar.setAttribute('data-content', RutinaOpc.reconstruirCalendario(calendarBody, anioFechaReferencial, mesFechaReferencial, meses[mesFechaReferencial]));
                    $('#CalendarioRf').popover('show');
                }else{
                    const popContent = iconCalendar.nextElementSibling.querySelector('.popover-content');
                    popContent.appendChild(htmlStringToElement(RutinaOpc.reconstruirCalendario(calendarBody, anioFechaReferencial, mesFechaReferencial, meses[mesFechaReferencial])));
                    setTimeout(() => popContent.children[0].remove(), 1);
                }
            }else{
                iconCalendar.setAttribute('data-content', RutinaOpc.reconstruirCalendario(calendarBody, anioFechaReferencial, mesFechaReferencial, meses[mesFechaReferencial]));
                $('#CalendarioRf').popover('show');
            }

            //Guardando las semanasIxs del mes que se muestra en el calendario
            $refIxsSemCalendar = semanasIxs;
            //Pitando en el calendario de otro color los días que el atleta tendrá una competicion
            const matches =  $fechasCompetencia.filter(v=>{
                return v.fecha.getMonth() == mesFechaReferencial && v.fecha.getFullYear() == anioFechaReferencial;
            })
            //Se espera 100ms para que se renderice completamente el calendario y así las queries del for no fallen
            if(matches.length>0)
                setTimeout(() => {
                    matches.forEach(v=>{
                        const diaCalendario = document.querySelector('#CalendarioRf').nextElementSibling.querySelector(`div[data-dia="${v.fecha.getDate()}"]`);
                        if(diaCalendario != undefined){
                            diaCalendario.children[0].classList.toggle('event-calendar');
                            diaCalendario.children[0].classList.toggle('event-calendar-c');
                        }
                    })
                }, 100);
            //seleccionado día de hoy(según se de el caso)
            RutinaOpc.pintarDiaHoyCalendar(anioFechaReferencial, mesFechaReferencial, max);
        },
        reconstruirCalendario: (dias, anio, mesInt, mesString)=>{
            const mesFechaInicio = $rutina.fechaInicio.getMonth(), anioFi = $rutina.fechaInicio.getFullYear();
            const mesFechaFin = $rutina.fechaFin.getMonth(), anioFf = $rutina.fechaFin.getFullYear();
            let classFini = "", classFfin = "";

            //Para la ocultación de las opciones de adelanto y atras en 1 mes del calendario
            if(mesInt == mesFechaInicio && mesInt == mesFechaFin && anio == anioFi && anio == anioFf ){
                classFini = "hidden", classFfin = "hidden";
            }else if(mesInt == mesFechaInicio && anio == anioFi){
                classFini = "hidden"
            }else if(mesInt == mesFechaFin &&  anio == anioFf){
                classFfin = "hidden";
            }

            return `<div class="container-fluid padding-0 its-calendar">
                                    <div class="container-fluid padding-0 padding-bottom-10">
                                        <h6 class="">
                                            <span class="pull-left padding-bottom-10">${mesString} <span>${anio}</span></span>
                                            <i class="fa fa-caret-down fa-fw" style="font-size: 0.8em" data-anio="${anio}" onclick="javascript:RutinaOpc.buscadorCalendario(this)"></i>
                                            <span class="pull-right padding-bottom-10"><i class="fa fa-arrow-circle-left fa-fw cal-retroceder-sem ${classFini}" title="Mes anterior"></i><i class="fa fa-arrow-circle-right fa-fw cal-adelantar-sem ${classFfin}" title="Mes siguiente"></i></span>
                                        </h6>
                                    </div>
                                    <div>
                                        <div class="container-fluid padding-0">
                                            <div class="row seven-cols">
                                                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1 mini cat-video">Lun</div>
                                                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1 mini cat-video">Mar</div>
                                                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1 mini cat-video">Mie</div>
                                                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1 mini cat-video">Jue</div>
                                                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1 mini cat-video">Vie</div>
                                                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1 mini cat-video">Sab</div>
                                                <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1 mini cat-video">Dom</div>
                                            </div>
                                        </div>
                                        <div class="container-fluid padding-0">
                                            ${dias}
                                        </div>
                                    </div>
                            </div>`;
        },
        buscadorCalendario: (input)=>{
            const anioBase = input.getAttribute('data-anio');
            input.classList.toggle('fa-caret-down');
            input.classList.toggle('fa-caret-up');

            const contenedorPadreBase = input.parentElement.parentElement;
            contenedorPadreBase.nextElementSibling.classList.toggle('hidden');
            if(contenedorPadreBase.parentElement.children.length == 3){
                contenedorPadreBase.parentElement.children[2].remove();
            }else{
                //1.
                const y1 = $rutina.fechaInicio.getFullYear(), y2 = $rutina.fechaFin.getFullYear(), f1 = $rutina.fechaInicio, f2 = $rutina.fechaFin;
                const finals = [];
                for(let i=0; i<y2-y1;i++){
                    finals.push([y1 + i, []]);
                }
                finals.push([y2,[]]);
                //2.
                finals.forEach((v,i,t)=>{
                    //Primer y último año
                    if(i== 0 || i==t.length-1)
                        if(i==0)
                            if(y1 == y2)
                                for(let ii=0;ii<f2.getMonth()-f1.getMonth()+1;ii++) {
                                    finals[i][1].push(f1.getMonth() + ii);
                                }
                            else
                                for(let ii=0;ii<11-f1.getMonth()+1;ii++){
                                    finals[i][1].push(f1.getMonth() + ii);
                                }
                        else
                            for(let ii=0;ii<f2.getMonth()+1;ii++){
                                finals[i][1].push(ii);
                            }
                    else//Intermedios
                        finals[i][1] = [0,1,2,3,4,5,6,7,8,9,10,11];
                })

                let filtros =   `<div class="container-fluid padding-0 cal-acordeon-query">
                                    <div class="panel-group smart-accordion-default smart-form" id="accordionX1">
                                    ${finals.map((v,i,k)=>{
                    const claseIn = v[0]==anioBase?'in':'', claseCollap = v[0]==anioBase?'':'class="collapsed"';
                    return `<div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h4 class="panel-title txt-color-blue"><a class="txt-color-blue" data-toggle="collapse" data-parent="#accordionX1" href="#collapCal${i}" ${claseCollap}> <i class="fa fa-lg fa-angle-down pull-right"></i> <i class="fa fa-lg fa-angle-up pull-right"></i> ${v[0]} </a></h4>
                                                    </div>
                                                    <div id="collapCal${i}" class="panel-collapse collapse ${claseIn}">
                                                        <div class="panel-body" data-anio="${Number(k[0][0])+i}" style="padding: 0px !important;">
                                                        ${v[1].map(z=>{
                        return `<div class="col col-md-3 bg-color-white txt-color-blue bordered mes-calendar" data-mes="${z}">${meses[z].substr(0,3).toUpperCase()}</div>`
                    }).join('')}
                                                        </div>
                                                    </div>
                                                </div>`
                }).join('')}
                                    </div>
                                 </div>`;
                contenedorPadreBase.parentElement.appendChild(htmlStringToElement(filtros));
            }
        },
        pintarDiaHoyCalendar: (anio, mes, max)=>{
            const hoy = new Date();
            if(hoy.getMonth() == mes && hoy.getFullYear() == anio && hoy.getDate()<=max){
                setTimeout(() => {
                    document.querySelector('#CalendarioRf').nextElementSibling.querySelector(`.its-calendar div[data-dia="${hoy.getDate()}"]`).classList.add('its-calendar-hoy');
                }, 100);
            }
        },
        colapsarAll: ()=>{
            Array.from(RutinaDOMQueries.getAllElementosCollapse()).forEach(e=>{
                e.classList.remove('collapsed')
                e.setAttribute('aria-expanded', "true");
            });

            Array.from(RutinaDOMQueries.getAllPanelElementosCollapse()).forEach(e=>{
                e.classList.add('in')
                e.setAttribute('aria-expanded', "false");
                e.style = '';
            });
        },
        comprimirAll: ()=>{
            Array.from(RutinaDOMQueries.getAllElementosCollapse()).forEach(e=>{
                e.classList.add('collapsed')
                e.setAttribute('aria-expanded', "false");
            });

            Array.from(RutinaDOMQueries.getAllPanelElementosCollapse()).forEach(e=>{
                e.classList.remove('in')
                e.setAttribute('aria-expanded', "false");
            });
        },
        abrirCopiadorSemana: (input)=>{
            input.setAttribute('data-content', RutinaOpc.bodyCopiarSemana($rutina.totalSemanas));
            $(input).popover('show');
            $('#btnConSemanaCopy').tooltip();
        },
        instanciarCopiarSemanaCompleta: ()=>{
            const cs = document.querySelector('#myTabRutina #CopySemana');
            if(cs != undefined) cs.innerHTML = `<div class="col-md-12 col-xs-2"><a href="javascript:void(0);" rel="tooltip" data-original-title="Copiar semana completa" data-placement="bottom"><i class="fa fa-copyright fa-2x txt-color-redLight copiar-full-semana" id="CopiarSemana" rel="popover" data-placement="right" data-content="" data-html="true"></i></a></div>`;
        },
        instanciarFlagActivo: ()=>{
            const div = document.getElementById('DivEditor');
            if($rutina.flagActivo == true){
                const icon = div.querySelector('.fa-calendar-plus-o');
                icon.classList.add('disabled');
            } else{
                const icon = div.querySelector('.fa-calendar-minus-o');
                icon.classList.add('disabled');
            }
        },
        bodyCopiarSemana: (totalSemanas)=>{
            const semActual = Number($semActual.textContent) -1;
            let ops = "";
            for(let i=0; i<totalSemanas;i++){
                if(i != semActual)
                    ops+= `<option value="${i}">${i+1} | ${$rutina.semanas[i].fechaInicio.toDateString()}</option>`
                else
                    ops+= `<option value="${i}" disabled="disabled">${i+1} | ${$rutina.semanas[i].fechaInicio.toDateString()}</option>`
            }
            return `<form style='min-width:170px' class=''>
                        <div class='input-group input-group-sm'>
                            <select class='form-control'>${ops}</select>
                            <div class="input-group-btn">
                                <button id="btnConSemanaCopy" class="btn btn-default" type="button" rel="tooltip" data-original-title="Proceder a copiar" data-placement="bottom" onclick="javascript:copiarSemanaBD(this)"><strong><i class="fa fa-clipboard text-warning"></i></strong></button>
							</div>
                        </div>
                    </form>`;
        },
        effectImage: ()=>{
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
        },
        cambiarEstado: (e, flag)=>{
            cambiarEstadoBD(flag, e);
        }
    }
})();

RutinaSet = (function(){
    return {
        setDiaClean: (numSem, diaIndex)=>{
            const dia = $rutina.semanas[numSem].dias[diaIndex];
            dia.calorias = 0;
            dia.distancia = 0;
            dia.minutos = 0;
        },
        setDiaDistanciaTotal: (numSem, diaIndex, distancia)=>{
            $rutina.semanas[numSem].dias[diaIndex].distancia = distancia;
        },
        setDiaTiempoTotal:  (numSem, diaIndex, minutos)=>{
            $rutina.semanas[numSem].dias[diaIndex].minutos = minutos;
        },
        setCalorias:  (numSem, diaIndex, calorias)=>{
            $rutina.semanas[numSem].dias[diaIndex].calorias = calorias;
        },
        setElementos: (numSem, diaIndex, elementos)=>{
            $rutina.semanas[numSem].dias[diaIndex].elementos = elementos;
        },
        concatElementos: (numSem, diaIndex, nElementos)=>{
            $rutina.semanas[numSem].dias[diaIndex].elementos = $rutina.semanas[numSem].dias[diaIndex].elementos.concat(nElementos);
        },
        concatSubElementos: (numSem, diaIndex, posEle, nSubElementos)=>{
            $rutina.semanas[numSem].dias[diaIndex].elementos[posEle].subElementos = $rutina.semanas[numSem].dias[diaIndex].elementos[posEle].subElementos.concat(nSubElementos);
        },
        setElementoNombre: (numSem, posDia, posEle, nombre)=>{
            $rutina.semanas[numSem].dias[posDia].elementos[posEle].nombre = nombre;
        },
        setElementoNota: (numSem, posDia, posEle, nota)=>{
            $rutina.semanas[numSem].dias[posDia].elementos[posEle].nota = nota;
        },
        setElementoDistancia: (numSem, posDia, posEle, distancia)=>{
            $rutina.semanas[numSem].dias[posDia].elementos[posEle].distancia =  distancia;
        },
        setElementoMinutos:(numSem, posDia, posEle, minutos)=>{
            $rutina.semanas[numSem].dias[posDia].elementos[posEle].minutos = minutos;
        },
        setElementoMediaAudio: (numSem, posDia, posEle, mAudio, nombre)=>{
            $rutina.semanas[numSem].dias[posDia].elementos[posEle].mediaAudio = mAudio;
            $rutina.semanas[numSem].dias[posDia].elementos[posEle].nombre = nombre;
        },
        setElementoMediaVideo: (numSem, posDia, posEle, mVideo, nombre)=>{
            $rutina.semanas[numSem].dias[posDia].elementos[posEle].mediaVideo = mVideo;
            $rutina.semanas[numSem].dias[posDia].elementos[posEle].nombre = nombre;
        },
        setSubElementoNota: (numSem, posDia, posEle, posSubEle, nota)=>{
            $rutina.semanas[numSem].dias[posDia].elementos[posEle].subElementos[posSubEle].nota = nota;
        },
        setSubElementoMediaAudio: (numSem, posDia, posEle, posSubEle, mAudio, nombre)=>{
            $rutina.semanas[numSem].dias[posDia].elementos[posEle].subElementos[posSubEle].mediaAudio = mAudio;
            $rutina.semanas[numSem].dias[posDia].elementos[posEle].subElementos[posSubEle].nombre = nombre;
        },
        setSubElementoMediaVideo: (numSem, posDia, posEle, posSubEle, mVideo, nombre)=>{
            $rutina.semanas[numSem].dias[posDia].elementos[posEle].subElementos[posSubEle].mediaVideo = mVideo;
            $rutina.semanas[numSem].dias[posDia].elementos[posEle].subElementos[posSubEle].nombre = nombre;
        },
        setSubElementoNombre: (numSem, posDia, posEle, posSubEle, nombre)=>{
            $rutina.semanas[numSem].dias[posDia].elementos[posEle].subElementos[posSubEle].nombre = nombre;
        },
        setDiaAndElemento: (numSem, posDia, posEle, nombre, distancia, distanciaTotal, calorias)=>{
            const dia = $rutina.semanas[numSem].dias[posDia];
            dia.distancia = distanciaTotal;
            dia.calorias += calorias;
            const ele = dia.elementos[posEle];
            ele.nombre = nombre;
            ele.distancia =  distancia;
        },
        setDiaAndSubEle: (numSem, posDia, posEle, posSE, nombre, distancia, distanciaTotal, calorias)=>{
            const dia = $rutina.semanas[numSem].dias[posDia];
            dia.distancia = distanciaTotal;
            dia.calorias += calorias;
            const ele = dia.elementos[posEle];
            ele.distancia =  distancia;
            const subEle = ele.subElementos[posSE];
            subEle.nombre = nombre;
        },
        subtractDiaCalorias: (numSem, diaIndex, calorias)=>{
            $rutina.semanas[numSem].dias[diaIndex].calorias -= calorias;
        },
        setAddDiaCalorias: (numSem, diaIndex, calorias)=>{
            $rutina.semanas[numSem].dias[diaIndex].calorias += calorias;
        },
        setDiaFlagEnvioCliente: (numSem, diaIndex, valor)=>{
            $rutina.semanas[numSem].dias[diaIndex].flagEnvioCliente = valor;
        },

    }
})();

RutinaGet = (function(){
    return{
        dia: (numSem, diaIndex)=>{
            return $rutina.semanas[numSem].dias[diaIndex];
        },
        elemento: (numSem, diaIndex, eleIndex)=>{
            return $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex];
        },
        getFechaInicioSemanaEdicion: ()=>{
            const semIndex = Number($semActual.textContent)-1;
            const objSem = $rutina.semanas[semIndex].fechaInicio;
            return {fechaInicio: objSem, anio: objSem.getFullYear(), mes: objSem.getMonth()}
        },
        getFechaInicioSemanaEspecifica: (semIx)=>{
            const objSem = $rutina.semanas[semIx].fechaInicio;
            return {fechaInicio: objSem, anio: objSem.getFullYear(), mes: objSem.getMonth()}
        },
        getFechaFinSemanaEspecifica: (semIx)=>{
            const objSem = $rutina.semanas[semIx].fechaFin;
            return {fechaFin: objSem, anio: objSem.getFullYear(), mes: objSem.getMonth()}
        },
        getCalendarioSemanaIxs: (anio, mes)=>{
            const coincidencias = $rutina.semanas.map((v,i)=>{
                if ((v.fechaInicio.getFullYear() == anio && v.fechaInicio.getMonth()== mes) || (v.fechaFin.getFullYear()== anio && v.fechaFin.getMonth()== mes))
                    return i;
            })
            return coincidencias.filter(v=>{return v!=undefined});
        },
        getRegeneracionSemanas: (ini, fin, numSemanas)=>{
            const semanas = [];
            const totalSemanas = numSemanas;
            const fIni = parseFromStringToDate2(ini);
            const fFin = parseFromStringToDate2(fin);
            for(let i=0; i<totalSemanas;i++){
                const refDia = fIni.getDay();
                let diasParaFull = refDia==0?0:7-refDia;
                if(i == 0){
                    const objFirtsWeek = {};
                    const semFini = parseFromStringToDate2(moment(fIni).add((i*7), 'd').format('DD/MM/YYYY'));
                    const semFfin = parseFromStringToDate2(moment(fIni).add((i*7)+diasParaFull, 'd').format('DD/MM/YYYY'));

                    objFirtsWeek.lstDia = dias;
                    semanas.push({fechaInicio: semFini, fechaFin: semFfin});
                }else{
                    semanas.push({fechaInicio: parseFromStringToDate(moment(semanas[i-1].fechaFin).add(1, 'd').format('YYYY-MM-DD')), fechaFin: parseFromStringToDate(moment(semanas[i-1].fechaFin).add(7, 'd').format('YYYY-MM-DD'))});
                }
            }
            //Modificando fecha fin de la ultima semana en caso esta no se encuentre mapeada con los 7 días calendarios
            semanas[totalSemanas-1].fechaFin = fFin;
            return semanas;
        },
        getKilometrajes: ()=>{
            const k = {};
            let kcalTotal = 0, kmPlanificado = 0;
            const sIx = Number($semActual.textContent)-1;
            $rutina.semanas[sIx].dias.forEach(v=>{
                kcalTotal+=v.calorias;
            });

            $rutina.semanas[sIx].dias.forEach(v=>{
                kmPlanificado+=v.distancia;
            });

            k.kcal = parseNumberToDecimal(kcalTotal, 2);
            k.kmMacro = $rutina.semanas[sIx].kilometrajeTotal;
            k.kmPlanificado = parseNumberToDecimal(kmPlanificado, 2);

            return k;
        },
        getKilometrajesLessDiaIndex: (dIx)=>{
            const k = {};
            let kcalTotal = 0, kmPlanificado = 0;
            const sIx = Number($semActual.textContent)-1;
            $rutina.semanas[sIx].dias.forEach((v,i)=>{
                if(dIx != i)
                    kcalTotal+=v.calorias;
            });

            $rutina.semanas[sIx].dias.forEach((v,i)=>{
                if(dIx != i)
                    kmPlanificado+=v.distancia;
            });
            k.kcal = parseNumberToDecimal(kcalTotal, 2);
            k.kmMacro = $rutina.semanas[sIx].kilometrajeTotal;
            k.kmPlanificado = parseNumberToDecimal(kmPlanificado, 2);

            return k;
        }
    }
})();

RutinaAdd = (function(){
    return {
        nuevoElemento: (numSem, diaIndex, posRefEle, valor)=>{
            return $rutina.semanas[numSem].dias[diaIndex].elementos.splice(posRefEle+1, 0, new Elemento({nombre: valor}));
        },
        nuevoSubElemento: (numSem, diaIndex, eleIndex, posRefSubEle, valor)=>{
            return $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].subElementos.splice(posRefSubEle+1, 0, new SubElemento({nombre: valor}));
        },
        nuevoSubElementoMedia: (numSem, diaIndex, eleIndex, objSubEle)=>{
            return $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].subElementos.splice(0, 1, new SubElemento(objSubEle));
        },
        nuevoEstilo: (numSem, diaIndex, eleIndex, id, claseCss, tipo)=>{
            return $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].estilos.push(new Estilo(id, claseCss, tipo));
        },
    }
})();

RutinaDelete = (function(){
    return {
        eliminarEstiloElemento: (numSem, diaIndex, eleIndex, id)=>{
            const estilos = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].estilos;
            estilos.forEach((v,i,k)=>{v.id==id ? k.splice(i, 1):''});
        },
        eliminarEstilosColor: (numSem, diaIndex, eleIndex, tipo)=>{
            const estilos = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].estilos;
            if(tipo == 1){//font color
                estilos.forEach((v,i,k)=>{if(v.id>=100 && v.id<=199){k.splice(i, 1)}});
            }else{//background color
                estilos.forEach((v,i,k)=>{if(v.id>=200 && v.id<=299){k.splice(i, 1)}});
            }
        },
        eliminarEstilosAlineacion: (numSem, diaIndex, eleIndex)=>{
            const estilos = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].estilos;
            estilos.forEach((v,i,k)=>{if(v.id>=4 && v.id<=7){k.splice(i, 1)}});
        },
        eliminarEstilosFuente: (numSem, diaIndex, eleIndex)=>{
            const estilos = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].estilos;
            estilos.forEach((v,i,k)=>{if(v.id>49 && v.id<100){k.splice(i, 1)}});
        },
        eliminarEstiloMargen:  (numSem, diaIndex, eleIndex)=>{
            const estilos = $rutina.semanas[numSem].dias[diaIndex].elementos[eleIndex].estilos;
            estilos.forEach((v,i,k)=>{if(v.id>=8 && v.id<=10){k.splice(i, 1)}});
        },
    }
})();

RutinaEditor = (function(){
    return {
        agregarOeliminarEstiloToElemento: (estiloId, tipo)=>{
            //1. Agregando a el objeto general de la rutina el nuevo estilo
            if(tipo == 0){
                const ixs = RutinaIx.getIxsForElemento($eleGenerico);
                let tempEle = RutinaDOMQueries.getElementoByIxs(ixs), i=0;
                while((tempEle = tempEle.previousElementSibling) != null) i++;
                const objEditor = RutinaEditor.obtenerEstiloById(estiloId);
                if($eleGenerico.classList.contains(objEditor.clase)){
                    RutinaDelete.eliminarEstiloElemento(ixs.numSem, ixs.diaIndex, (posEle = i), estiloId);
                }else{
                    RutinaAdd.nuevoEstilo(ixs.numSem, ixs.diaIndex, (posEle = i), estiloId, objEditor.clase, objEditor.tipo);
                }
                $eleGenerico.classList.toggle(objEditor.clase);
                guardarEstilosElementoBD(ixs.numSem, ixs.diaIndex, (posEle = i));
            }else if(tipo == 1){
                const ixs = RutinaIx.getIxsForElemento($eleGenerico);
                let tempEle = RutinaDOMQueries.getElementoByIxs(ixs), i=0;
                while((tempEle = tempEle.previousElementSibling) != null) i++;
                const objEditor = RutinaEditor.obtenerEstiloById(estiloId);
                RutinaDelete.eliminarEstilosColor(ixs.numSem, ixs.diaIndex, (posEle = i), 1);
                RutinaAdd.nuevoEstilo(ixs.numSem, ixs.diaIndex, (posEle = i), estiloId, objEditor.clase, objEditor.tipo);
                $eleGenerico.classList.forEach((v,i,k)=>{v.includes('rf-ct')?k.remove(v):''});
                $eleGenerico.classList.add(objEditor.clase);
                guardarEstilosElementoBD(ixs.numSem, ixs.diaIndex, (posEle = i));
            } else if(tipo == 2){
                const ixs = RutinaIx.getIxsForElemento($eleGenerico);
                let tempEle = RutinaDOMQueries.getElementoByIxs(ixs), i=0;
                while((tempEle = tempEle.previousElementSibling) != null) i++;
                const objEditor = RutinaEditor.obtenerEstiloById(estiloId);
                RutinaDelete.eliminarEstilosColor(ixs.numSem, ixs.diaIndex, (posEle = i), 2);
                RutinaAdd.nuevoEstilo(ixs.numSem, ixs.diaIndex, (posEle = i), estiloId, objEditor.clase, objEditor.tipo);
                const headerElemento = $eleGenerico.parentElement.parentElement;
                headerElemento.classList.forEach((v,i,k)=>{v.includes('rf-bg')?k.remove(v):''});
                headerElemento.classList.add(objEditor.clase);
                guardarEstilosElementoBD(ixs.numSem, ixs.diaIndex, (posEle = i));
            } else if(tipo == 3){
                const ixs = RutinaIx.getIxsForElemento($eleGenerico);
                let tempEle = RutinaDOMQueries.getElementoByIxs(ixs), i=0;
                while((tempEle = tempEle.previousElementSibling) != null) i++;
                const objEditor = RutinaEditor.obtenerEstiloById(estiloId);
                RutinaDelete.eliminarEstilosAlineacion(ixs.numSem, ixs.diaIndex, (posEle = i));
                RutinaAdd.nuevoEstilo(ixs.numSem, ixs.diaIndex, (posEle = i), estiloId, objEditor.clase, objEditor.tipo);
                const headerElemento = $eleGenerico.parentElement.parentElement;
                headerElemento.classList.forEach((v,i,k)=>{v.includes('rf-align')?k.remove(v):''});
                headerElemento.classList.add(objEditor.clase);
                guardarEstilosElementoBD(ixs.numSem, ixs.diaIndex, (posEle = i));
            } else if(tipo == 4){
                const ixs = RutinaIx.getIxsForElemento($eleGenerico);
                let tempEle = RutinaDOMQueries.getElementoByIxs(ixs), i=0;
                let initEle = tempEle;
                while((tempEle = tempEle.previousElementSibling) != null) i++;
                if(initEle.classList.contains('rf-mg-2')){
                    RutinaDelete.eliminarEstiloMargen(ixs.numSem, ixs.diaIndex, (posEle = i));
                    initEle.classList.remove('rf-mg-2');
                    guardarEstilosElementoBD(ixs.numSem, ixs.diaIndex, (posEle = i));
                }else{
                    const objEditor = RutinaEditor.obtenerEstiloById(estiloId);
                    RutinaDelete.eliminarEstiloMargen(ixs.numSem, ixs.diaIndex, (posEle = i));
                    RutinaAdd.nuevoEstilo(ixs.numSem, ixs.diaIndex, (posEle = i), estiloId, objEditor.clase, objEditor.tipo);
                    initEle.classList.forEach((v,i,k)=>{v.includes('rf-mg')?k.remove(v):''});
                    initEle.classList.add(objEditor.clase);
                    guardarEstilosElementoBD(ixs.numSem, ixs.diaIndex, (posEle = i));
                }
            }
        },
        instanciarPaletaColores: (input)=>{
            const raw = `
                    <div class="container-fluid padding-0 its-indicador-1">
                        <div class="col-md-6 col-sm-6 col-xs-6">
                            <div class="row padding-5 text-align-center">
                                <div class="btn-group">
                                    <div class="note-palette-title"><b>Color fuente</b></div>
                                        <div>
                                            <button type="button" class="note-color-reset btn btn-default" data-event="removeFormat" data-value="foreColor">Aplicar <i class="fa fa-check-circle-o txt-color-blue"></i></button>
                                        </div>
                                        <div class="note-holder" data-event="foreColor">
                                            <div class="note-color-palette">
                                                <div class="note-color-row">
                                                    <button type="button" class="note-color-fuente" style="background-color:white" data-index="100" data-class="rf-ct-white"></button><!--
                                                 --><button type="button" class="note-color-fuente" style="background-color:black" data-index="101" data-class="rf-ct-black"></button><!--
        git l                                         --><button type="button" class="note-color-fuente" style="background-color:gray" data-index="102" data-class="rf-ct-gray"></button><!--
                                                 --><button type="button" class="note-color-fuente" style="background-color:#003366" data-index="103" data-class="rf-ct-navyblue"></button><!--
                                                 --><button type="button" class="note-color-fuente" style="background-color:skyblue" data-index="104" data-class="rf-ct-skyblue"></button><!--
                                                 --><button type="button" class="note-color-fuente" style="background-color:orange" data-index="105" data-class="rf-ct-orange"></button><!--
                                                 --><button type="button" class="note-color-fuente" style="background-color:yellow" data-index="106" data-class="rf-ct-yellow"></button><!--
                                                 --><button type="button" class="note-color-fuente" style="background-color:green" data-index="107" data-class="rf-ct-green"></button>
                                                </div>
                                                <div class="note-color-row">
                                                    <button type="button" class="note-color-fuente" style="background-color:#F67280" data-index="108" data-class="rf-ct-m-pink"></button><!--
                                                 --><button type="button" class="note-color-fuente" style="background-color:#355C7D" data-index="109" data-class="rf-ct-m-blue"></button><!--
                                                 --><button type="button" class="note-color-fuente" style="background-color:#474747" data-index="110" data-class="rf-ct-m-gray"></button><!--
                                                 --><button type="button" class="note-color-fuente" style="background-color:#2A363B" data-index="111" data-class="rf-ct-m-navyblue"></button><!--
                                                 --><button type="button" class="note-color-fuente" style="background-color:#A8E6CE" data-index="112" data-class="rf-ct-m-skyblue"></button><!--
                                                 --><button type="button" class="note-color-fuente" style="background-color:#FC913A" data-index="113" data-class="rf-ct-m-orange"></button><!--
                                                 --><button type="button" class="note-color-fuente" style="background-color:#F9D423" data-index="114" data-class="rf-ct-m-yellow"></button><!--
                                                 --><button type="button" class="note-color-fuente" style="background-color:#99B898" data-index="115" data-class="rf-ct-m-green"></button>
                                                </div>
                                            </div>
                                        </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-sm-6 col-xs-6">
                            <div class="row padding-5 text-align-center">
                                <div class="btn-group">
                                    <div class="note-palette-title"><b>Color fondo</b></div>
                                        <div>
                                            <button type="button" class="note-color-reset btn btn-default" data-event="removeFormat" data-value="foreColor">Aplicar <i class="fa fa-check-circle-o txt-color-blue"></i></button>
                                        </div>
                                        <div class="note-holder" data-event="foreColor">
                                            <div class="note-color-palette">
                                                <div class="note-color-row">
                                                    <button type="button" class="note-bg-color" style="background-color:white" data-index="200" data-class="rf-ct-white"></button><!--
                                                 --><button type="button" class="note-bg-color" style="background-color:black" data-index="201" data-class="rf-ct-black"></button><!--
                                                 --><button type="button" class="note-bg-color" style="background-color:gray" data-index="202" data-class="rf-ct-gray"></button><!--
                                                 --><button type="button" class="note-bg-color" style="background-color:#003366" data-index="203" data-class="rf-ct-navyblue"></button><!--
                                                 --><button type="button" class="note-bg-color" style="background-color:skyblue" data-index="204" data-class="rf-ct-skyblue"></button><!--
                                                 --><button type="button" class="note-bg-color" style="background-color:orange" data-index="205" data-class="rf-ct-orange"></button><!--
                                                 --><button type="button" class="note-bg-color" style="background-color:yellow" data-index="206" data-class="rf-ct-yellow"></button><!--
                                                 --><button type="button" class="note-bg-color" style="background-color:green" data-index="207" data-class="rf-ct-green"></button>
                                                </div>
                                                <div class="note-color-row">
                                                    <button type="button" class="note-bg-color" style="background-color:#F67280" data-index="208" data-class="rf-ct-m-pink"></button><!--
                                                 --><button type="button" class="note-bg-color" style="background-color:#355C7D" data-index="209" data-class="rf-ct-m-blue"></button><!--
                                                 --><button type="button" class="note-bg-color" style="background-color:#474747" data-index="210" data-class="rf-ct-m-gray"></button><!--
                                                 --><button type="button" class="note-bg-color" style="background-color:#2A363B" data-index="211" data-class="rf-ct-m-navyblue"></button><!--
                                                 --><button type="button" class="note-bg-color" style="background-color:#A8E6CE" data-index="212" data-class="rf-ct-m-skyblue"></button><!--
                                                 --><button type="button" class="note-bg-color" style="background-color:#FC913A" data-index="213" data-class="rf-ct-m-orange"></button><!--
                                                 --><button type="button" class="note-bg-color" style="background-color:#F9D423" data-index="214" data-class="rf-ct-m-yellow"></button><!--
                                                 --><button type="button" class="note-bg-color" style="background-color:#99B898" data-index="215" data-class="rf-ct-m-green"></button>
                                                </div>
                                            </div>
                                        </div>
                                </div>
                            </div>
                        </div>
                    </div>`;
            if(input.tagName == "I") {
                input.parentElement.setAttribute('data-content', raw)
                $(input.parentElement).popover('show');
            } else{
                input.setAttribute('data-content', raw);
                $(input).popover('show');
            }
        },
        instanciarGrupoAlineacion: (input)=>{
            const raw = `<div class="note-btn-group btn-group note-align">
                            <button type="button" data-index="4" class="note-btn btn btn-default btn-sm note-alineacion" title="" data-original-title="Align left (CTRL+SHIFT+L)"><i data-index="4" class="note-icon-align-left note-alineacion"></i></button>
                            <button type="button" data-index="5" class="note-btn btn btn-default btn-sm note-alineacion" title="" data-original-title="Align center (CTRL+SHIFT+E)"><i data-index="5" class="note-icon-align-center note-alineacion"></i></button>
                            <button type="button" data-index="6" class="note-btn btn btn-default btn-sm note-alineacion" title="" data-original-title="Align right (CTRL+SHIFT+R)"><i data-index="6" class="note-icon-align-right note-alineacion"></i></button>
                         </div>`;
            if(input.tagName == "I" || input.tagName == "SPAN") {
                input.parentElement.setAttribute('data-content', raw)
                $(input.parentElement).popover('show');
            } else{
                input.setAttribute('data-content', raw);
                $(input).popover('show');
            }
        },
        copiarFormato: ()=>{
            const ixs = RutinaIx.getIxsForElemento($eleGenerico);
            let tempEle = RutinaDOMQueries.getElementoByIxs(ixs), i=0;
            while((tempEle = tempEle.previousElementSibling) != null) i++;
            $estilosCopiados = RutinaGet.elemento(ixs.numSem, ixs.diaIndex, (eleIndex = i)).estilos;
            $statusCopy = true;
        },
        obtenerEstilos: (ess)=>{
            let  estHeader = "", estElem = "", margen = "";
            for(let i=0; i<ess.length;i++){
                switch(ess[i].tipo) {
                    case 1:
                        estElem += ess[i].clase + " ";
                        break;
                    case 2:
                        estHeader += ess[i].clase + " ";
                        break;
                    default:
                        margen += ess[i].clase + " "
                }
            }
            return {header: estHeader, base: estElem, margen: margen};
        },
        pegarFormato: (input)=>{
            const ixs = RutinaIx.getIxsForElemento(input);
            let eleIndex = 0;

            //1
            Array.from(input.classList).map((v,i)=>{
                if(i>1)
                    return v;
            }).forEach(v=>input.classList.remove(v));
            input.parentElement.parentElement.className = "";
            let tempEle = RutinaDOMQueries.getElementoByIxs(ixs), i=0;
            tempEle.classList.contains('rf-mg-2') ? tempEle.classList.remove('rf-mg-2') : '';

            let margenBottom = '';
            if($estilosCopiados.length > 0){
                $estilosCopiados.forEach(v => {
                    if(v.tipo == 1)
                        input.classList.add(v.clase);
                    else if(v.tipo == 2)
                        input.parentElement.parentElement.classList.add(v.clase);
                    else
                        margenBottom = v.clase;
                });
                //2
                margenBottom != "" ? tempEle.classList.add(margenBottom):'';
                while((tempEle = tempEle.previousElementSibling) != null) i++;
                eleIndex = i;
                RutinaGet.elemento(ixs.numSem, ixs.diaIndex, eleIndex).estilos = JSON.parse(JSON.stringify($estilosCopiados));
                $estilosCopiados = [];
            }else{
                while((tempEle = tempEle.previousElementSibling) != null) i++;
                eleIndex = i;
                RutinaGet.elemento(ixs.numSem, ixs.diaIndex, eleIndex).estilos = [];
            }
            guardarEstilosElementoBD(ixs.numSem, ixs.diaIndex, eleIndex);
            $statusCopy = false;
        },
        obtenerEstiloById: (id) =>{
            return ClaseEditor.filter(v=>{
                if(v.id == id)
                    return v.clase;
            })[0];
        }
    }
})();

RutinaIx = (function(){
    return {
        getIxsForDia: (e)=>{
            let ixs = {};
            ixs.numSem = $semActual.textContent -1;
            ixs.diaIndex = e.getAttribute('data-index')==undefined?e.getAttribute('data-dia-index'):e.getAttribute('data-index');
            return ixs;
        },
        getIxsForElemento: (e)=>{
            let ixs = {};
            ixs.numSem = typeof $semActual === 'undefined' ? '0' : $semActual.textContent -1;
            ixs.diaIndex = e.getAttribute('data-dia-index');
            ixs.eleIndex = e.getAttribute('data-index')==undefined?'':e.getAttribute('data-index');
            ixs.refEleIndex = e.getAttribute('data-ele-ref-index')==undefined?'':e.getAttribute('data-ele-ref-index');
            return ixs;
        },
        getIxsForSubElemento: (e)=>{
            let ixs = {};
            ixs.numSem = $semActual.textContent -1;
            ixs.diaIndex = e.getAttribute('data-dia-index');
            ixs.eleIndex = e.getAttribute('data-ele-index');
            ixs.subEleIndex = e.getAttribute('data-index')==undefined?'':e.getAttribute('data-index');
            ixs.refSubEleIndex = e.getAttribute('data-sub-ele-ref-index')==undefined?'':e.getAttribute('data-sub-ele-ref-index');
            return ixs;
        },
        getPosForElemento: (e)=>{
            let pos = {};
            pos.diaIndex = e.getAttribute('data-pos-dia');
            pos.eleIndex = e.getAttribute('data-pos');
            return pos;
        }
    }
})();

RutinaDOMQueries = (function(){
    return {
        getDiaByIx: (ix)=>{
            return document.querySelector(`#tabRutina #RutinaSemana .rf-dia[data-index="${ix}"]`);
        },
        getDivListaDiaByIxs: (ixs)=>{
            return document.querySelector(`#tabRutina #RutinaSemana .rf-dia[data-index="${ixs.diaIndex}"] .rf-listas`);
        },
        getPreElementoByIxs: (ixs)=>{
            return document.querySelector(`#tabRutina #RutinaSemana .rf-dia[data-index="${ixs.diaIndex}"] .rf-dia-pre-elemento[data-index="${ixs.eleIndex}"]`);
        },
        getElementoByIxs: (ixs)=>{
            return document.querySelector(`#tabRutina #RutinaSemana .rf-dia[data-index="${ixs.diaIndex}"] .rf-dia-elemento[data-index="${ixs.eleIndex}"]`);
        },
        getSubElementoByIxs: (ixs)=>{
            return document.querySelector(`#tabRutina #RutinaSemana .rf-dia[data-index="${ixs.diaIndex}"] .rf-dia-elemento[data-index="${ixs.eleIndex}"] .rf-sub-elemento[data-index="${ixs.subEleIndex}"]`);
        },
        getPreSubElementoByIxs: (ixs)=>{
            return document.querySelector(`#tabRutina #RutinaSemana .rf-dia[data-index="${ixs.diaIndex}"] .rf-dia-elemento[data-index="${ixs.eleIndex}"] .rf-pre-sub-elemento[data-index="${ixs.subEleIndex}"]`);
        },
        getSubElementoRefByIxs: (ixs)=>{
            return document.querySelector(`#tabRutina #RutinaSemana .rf-dia[data-index="${ixs.diaIndex}"] .rf-dia-elemento[data-index="${ixs.eleIndex}"] .rf-sub-elemento[data-index="${ixs.refSubEleIndex}"]`);
        },
        getPanelElementoByIxs: (ixs)=>{
            return document.querySelector(`#tabRutina #RutinaSemana .rf-dia[data-index="${ixs.diaIndex}"] .panel-default[data-index="${ixs.eleIndex}"]`);
        },
        getAllElementosCollapse: ()=>{
            return document.querySelectorAll('#myTabRutina #RutinaSemana a[data-toggle="collapse"]');
        },
        getAllPanelElementosCollapse: ()=>{
            return document.querySelectorAll('#myTabRutina #RutinaSemana .panel-collapse');
        }
    }
})();

CalendarioDOMQueries = (function(){

})();

DiaOpc = (function(){
    return {
        positionPopoverByDiaIndex: (dIx) => {
            return dIx == "0" ? "right" : "bottom";
        },
        cambiarFlagDescanso: (numSem, diaIndex) => {
            const flagDescanso = $rutina.semanas[numSem].dias[diaIndex].flagDescanso ? false : true;
            let temp = document.querySelector(`.rf-dia[data-index="${diaIndex}"] div[role="heading"]`);
            if (!flagDescanso) {//1. Cuando el flag sea cambiado a false: Recrearemos la estructura base
                let base = `
                        <div class="widget-body padding-o-bottom-40">
								                <div class="container-fluid form-group margin-bottom-10 padding-5 inputs-init">
                                                    <div class="smart-form">
                                                        <div class="form-group">
                                                                <label class="input col-md-6 col-sm-12 col-xs-12">
                                                                    <input class="in-ele-dia-1 in-init-ele" type="text" maxlength="121" placeholder="" data-dia-index="${diaIndex}">
                                                                    </label>
                                                                
                                                                <label class="input col-md-6 col-sm-12 col-xs-12">
                                                                    <input class="in-ele-dia-2 in-init-ele" type="text" maxlength="121" placeholder="" data-dia-index="${diaIndex}">
                                                                    </label>
                                                                <em class="txt-color-redLight" id="msg-val-${diaIndex}"></em>
                                                        </div>
                                                    </div>
                                                </div>
								                <!-- smart-accordion-default -->
								                <div class="panel-group smart-accordion-default rf-listas padding-5">
								                    <!-- LISTAS DEL DIA DE SEMANA -->
								                    <!-- END LISTAS DEL DIA DE SEMANA -->
								                </div>									
								            </div>
                        `
                temp.nextElementSibling.innerHTML = base;
                $rutina.semanas[numSem].dias[diaIndex].flagDescanso = false;
                RutinaSet.setDiaClean(numSem, diaIndex);
                modificarDiaFlagDescanso(numSem, diaIndex, flagDescanso);
            } else {
                $.smallBox({
                    content: "El siguiente procedimiento borrará toda la información ya ingresada del día. ¿Desea aún ajustar el día como día de descanso? <p class='text-align-right'><a href='javascript:DiaOpc.confirmarCambioFlagDescanso(" + numSem + "," + diaIndex + ",\"" + flagDescanso + "\");' class='btn btn-primary btn-sm'>Si</a> <a href='javascript:void(0);' class='btn btn-danger btn-sm'>No</a></p>",
                    color: "#296191",
                    timeout: 10000,
                    icon: "fa fa-bell swing animated",
                    iconSmall: "",
                });
            }
        },
        confirmarCambioFlagDescanso: (numSem, diaIndex, flagDescanso) => {
            let img = document.createElement('img');
            img.src = _ctx + 'img/dia-libre.png';
            img.style.maxWidth = '90%';
            img.style.textAlign = 'center';
            const base = document.querySelector(`#RutinaSemana .rf-dia[data-index="${diaIndex}"]`);
            base.querySelector('.horas-totales').textContent = '0\' 00"';
            base.querySelector('.distancia-total').textContent = '0.00';
            const temp = base.querySelector('.widget-body');
            temp.innerHTML = '';
            temp.appendChild(img);
            $rutina.semanas[numSem].dias[diaIndex].flagDescanso = true;
            $rutina.semanas[numSem].dias[diaIndex].elementos = [];
            modificarDiaFlagDescanso(numSem, diaIndex, flagDescanso);
            Indicadores.actualizarKilometrajesLessDiaIndex(diaIndex);
        },
        preGuardarDiaPlantilla: (ixs)=>{
            const numeroSemana = $semActual.textContent - 1;
            if($rutina.semanas[numeroSemana].dias[ixs.diaIndex].elementos.length>0) {
                if(isDg == "n"){
                    verificarMaxRole().then((res)=>{
                        if(res){
                            isDg = res;
                            $('#modalPreGuardarMini').modal('show');
                            catRutinasDiaIndex.setAttribute('data-dia-index', ixs.diaIndex);
                            btnGuardarMini.setAttribute('data-dia-index', ixs.diaIndex);
                            reconstruirCategoriasMisRutinasDg();
                        }else{
                            isDg = res;
                            $('.elegir-rutinario').click();
                            $('#EspecificacionSubCategoriaId').html('<option value=""> -- Seleccione -- </option>')
                            btnGuardarMini.setAttribute('data-dia-index', ixs.diaIndex);
                        }
                    })
                }else{
                    if(isDg){
                        $('#modalPreGuardarMini').modal('show');
                        catRutinasDiaIndex.setAttribute('data-dia-index', ixs.diaIndex);
                    }else{
                        $('.elegir-rutinario').click();
                        $('#EspecificacionSubCategoriaId').html('<option value=""> -- Seleccione -- </option>')
                        btnGuardarMini.setAttribute('data-dia-index', ixs.diaIndex);
                    }
                }
            }else{
                $.smallBox({color: "alert", content: "<i>No es posible guardar el día pues este no cuenta con<br/> ningún elemento...</i>"});
            }
        },
        actualizarTotalizadosDia: (diaIndex)=>{
            const dia = RutinaDOMQueries.getDiaByIx(diaIndex);
            const elementos = dia.querySelectorAll(`.rf-dia-elemento`);
            if(elementos.length>0){
                const totKms = Array.from(elementos).map(v=>{
                    return Number(v.getAttribute('data-kms'));
                }).reduce((x,y)=>{
                    return x+y;
                });

                const totMinutos = Array.from(dia.querySelectorAll(`.agregar-tiempo`)).map(v=>{
                    return Number(v.value);
                }).reduce((x,y)=>{
                    return x+y;
                });

                dia.querySelector(`.distancia-total`).textContent = parseNumberToDecimal(totKms, 2);
                dia.querySelector(`.horas-totales`).textContent = parseNumberToHours(totMinutos);
            }else{
                dia.querySelector(`.horas-totales`).textContent = "0' 00\"";
                dia.querySelector(`.distancia-total`).textContent = "0.00";
            }
        },
        validPreActualizarFromNomEle: (e, ixs, posEle)=>{
            const nombreOZonaCardiaca = e.toUpperCase();
            const elemento = RutinaDOMQueries.getElementoByIxs(ixs);
            const tiempoAsignado = elemento.querySelector('.agregar-tiempo').value;
            if(!isNaN(tiempoAsignado) && tiempoAsignado > 0) {
                if (RutinaValidator.esZ(nombreOZonaCardiaca)) {
                    if(elemento.getAttribute('data-type') == ElementoTP.COMPUESTO && RutinaValidator.hayZFromEle(elemento)){
                        $.smallBox({color: "alert", content: "<i>Este elemento ya tiene especificada una carrera...</i>"});
                        elemento.querySelector('.rf-dia-elemento-nombre').textContent = $nombreActualizar;
                        RutinaSet.setElementoNombre(ixs.numSem, ixs.diaIndex, posEle, $nombreActualizar);
                    } else {
                        const zonaNum = Number(nombreOZonaCardiaca.substr(1)) - 1;
                        let kilometraje = DiaFunc.obtenerKilometraje(ixs.numSem, zonaNum, tiempoAsignado);
                        let caloriasNuevas = DiaFunc.obtenerGastoCalorico(zonaNum, tiempoAsignado);

                        const nomEleAnterior = $nombreActualizar.toUpperCase();
                        if (RutinaValidator.esZ(nomEleAnterior)) {
                            if (nombreOZonaCardiaca === $nombreActualizar) {
                                caloriasNuevas = 0;//Como son iguales para no añadir calorias de forma incorrecta se envia como 0
                            } else {
                                const zonaNumAnterior = Number(nomEleAnterior.substr(1)) - 1
                                caloriasNuevas = caloriasNuevas - DiaFunc.obtenerGastoCalorico(zonaNumAnterior, tiempoAsignado) //En caso de ser la diferencia negativa, al momento de acumular se solo se restará la diferencia y de igual forma el excedente
                            }
                        }
                        DiaOpc.actualizar(elemento, nombreOZonaCardiaca, kilometraje, caloriasNuevas, posEle, ixs, 1);
                    }
                }else {
                    const nomEleAnterior = $nombreActualizar.toUpperCase();
                    if (RutinaValidator.esZ(nomEleAnterior)) {
                        const zonaNumAnterior = Number(nomEleAnterior.substr(1)) - 1;
                        const caloriasNuevas = -DiaFunc.obtenerGastoCalorico(zonaNumAnterior, tiempoAsignado); //En caso de ser la diferencia negativa, al momento de acumular se solo se restará la diferencia y de igual forma el excedente
                        DiaOpc.actualizar(elemento, e, 0, caloriasNuevas, posEle, ixs, 1);
                    } else
                        actualizarElementoNombreBD(ixs.numSem, ixs.diaIndex, posEle);
                }
            }else{
                actualizarElementoNombreBD(ixs.numSem, ixs.diaIndex, posEle);
            }
        },
        validPreActualizarFromNomSubEle: (elemento, valor, ixs, posEle, posSE)=>{
            const nombreOZonaCardiaca = valor.toUpperCase();
            const tiempoAsignado = elemento.querySelector('.agregar-tiempo').value;
            if(!isNaN(tiempoAsignado) && tiempoAsignado > 0) {
                if (RutinaValidator.esZ(nombreOZonaCardiaca)) {
                    if(RutinaValidator.hayZFromSubEle(elemento)){
                        $.smallBox({color: "alert", content: "<i>Este elemento ya tiene especificada una carrera...</i>"});
                    }else {
                        const nuevoIx = RutinaSeccion.newSubElemento(ixs.diaIndex, ixs.eleIndex, TipoElemento.TEXTO, valor);
                        const nSubEle = elemento.querySelector(`div[data-index="${nuevoIx}"]`);
                        $rutina.semanas[ixs.numSem].dias[ixs.diaIndex].elementos[posEle].subElementos.push(new SubElemento({nombre: valor, tipo: TipoElemento.TEXTO}));
                        elemento.querySelector(`.in-init-sub-ele`).classList.toggle('hidden');
                        instanciarSubElementoTooltip(nSubEle);
                        instanciarSubElementoPopover(nSubEle);

                        const zonaNum = Number(nombreOZonaCardiaca.substr(1)) - 1;
                        let caloriasNuevas = DiaFunc.obtenerGastoCalorico(zonaNum, tiempoAsignado);
                        let kilometraje = DiaFunc.obtenerKilometraje(ixs.numSem, zonaNum, tiempoAsignado);

                        DiaOpc.actualizarFromSE(elemento, nombreOZonaCardiaca, kilometraje, caloriasNuevas, posEle, posSE, ixs);
                    }
                }else {
                    const nuevoIx = RutinaSeccion.newSubElemento(ixs.diaIndex, ixs.eleIndex, TipoElemento.TEXTO, valor);
                    const nSubEle = elemento.querySelector(`div[data-index="${nuevoIx}"]`);
                    $rutina.semanas[ixs.numSem].dias[ixs.diaIndex].elementos[posEle].subElementos.push(new SubElemento({nombre: valor, tipo: TipoElemento.TEXTO}));
                    elemento.querySelector(`.in-init-sub-ele`).classList.toggle('hidden');
                    instanciarSubElementoTooltip(nSubEle);
                    instanciarSubElementoPopover(nSubEle);
                    const nomEleAnterior = $nombreActualizar.toUpperCase();
                    if (RutinaValidator.esZ(nomEleAnterior)) {
                        const zonaNumAnterior = Number(nomEleAnterior.substr(1)) - 1;
                        const caloriasNuevas = -DiaFunc.obtenerGastoCalorico(zonaNumAnterior, tiempoAsignado); //En caso de ser la diferencia negativa, al momento de acumular se solo se restará la diferencia y de igual forma el excedente
                        DiaOpc.actualizarFromSE(elemento, e, 0, caloriasNuevas, posEle, posSE, ixs);
                    } else
                        agregarSubElementoAElementoBD(ixs.numSem, ixs.diaIndex, posEle, posSE);
                }
            }else{
                const nuevoIx = RutinaSeccion.newSubElemento(ixs.diaIndex, ixs.eleIndex, TipoElemento.TEXTO, valor);
                const nSubEle = elemento.querySelector(`div[data-index="${nuevoIx}"]`);
                $rutina.semanas[ixs.numSem].dias[ixs.diaIndex].elementos[posEle].subElementos.push(new SubElemento({nombre: valor, tipo: TipoElemento.TEXTO}));
                elemento.querySelector(`.in-init-sub-ele`).classList.toggle('hidden');
                instanciarSubElementoTooltip(nSubEle);
                instanciarSubElementoPopover(nSubEle);
                agregarSubElementoAElementoBD(ixs.numSem, ixs.diaIndex, posEle, 0);
            }
        },
        validPreActualizarFromNomSubEle2: (elemento, valor, ixs, posEle, posSE)=>{
            const nombreOZonaCardiaca = valor.toUpperCase();
            const tiempoAsignado = elemento.querySelector('.agregar-tiempo').value;
            if(!isNaN(tiempoAsignado) && tiempoAsignado > 0) {
                if (RutinaValidator.esZ(nombreOZonaCardiaca)) {
                    if(RutinaValidator.hayZFromEleNombre(elemento) || RutinaValidator.getZFromSubEle(elemento).length == 2){//Para el caso se evalua si el tamaño es 2 por conveniencia(1 del posible anterior y el otro de el nuevo valor z que se le asigno al sub elemento
                        $.smallBox({color: "alert", content: "<i>Este elemento ya tiene especificada una carrera...</i>"});
                        RutinaSet.setSubElementoNombre(ixs.numSem, ixs.diaIndex, posEle, posSE, $nombreActualizar);
                        RutinaDOMQueries.getSubElementoByIxs(ixs).querySelector('.rf-sub-elemento-nombre').textContent = $nombreActualizar;
                    } else {
                        const zonaNum = Number(nombreOZonaCardiaca.substr(1)) - 1;
                        let caloriasNuevas = DiaFunc.obtenerGastoCalorico(zonaNum, tiempoAsignado);
                        let kilometraje = DiaFunc.obtenerKilometraje(ixs.numSem, zonaNum, tiempoAsignado);

                        const nomSEAnterior = $nombreActualizar.toUpperCase();
                        if (RutinaValidator.esZ(nomSEAnterior)) {
                            if(nombreOZonaCardiaca === $nombreActualizar){
                                caloriasNuevas = 0;//Como son iguales para no añadir calorias de forma incorrecta se envia como 0
                            }else{
                                const zonaNumAnterior = Number(nomSEAnterior.substr(1)) - 1
                                caloriasNuevas = caloriasNuevas - DiaFunc.obtenerGastoCalorico(zonaNumAnterior, tiempoAsignado) //En caso de ser la diferencia negativa, al momento de acumular se solo se restará la diferencia y de igual forma el excedente
                            }
                        }
                        DiaOpc.actualizarFromSE(elemento, nombreOZonaCardiaca, kilometraje, caloriasNuevas, posEle, posSE, ixs);
                    }
                }else {
                    const nomEleAnterior = $nombreActualizar.toUpperCase();
                    if (RutinaValidator.esZ(nomEleAnterior)) {
                        const zonaNumAnterior = Number(nomEleAnterior.substr(1)) - 1;
                        const caloriasNuevas = -DiaFunc.obtenerGastoCalorico(zonaNumAnterior, tiempoAsignado); //En caso de ser la diferencia negativa, al momento de acumular se solo se restará la diferencia y de igual forma el excedente
                        DiaOpc.actualizarFromSE(elemento, valor, 0, caloriasNuevas, posEle, posSE, ixs);
                    } else
                        actualizarSubElementoNombreBD(valor, ixs.numSem, ixs.diaIndex, posEle, posSE);
                }
            } else {
                agregarSubElementoAElementoBD(ixs.numSem, ixs.diaIndex, posEle, 0);
            }
        },
        actualizar: (elemento, nombre, kms, calorias, posEle, ixs, tipo, totalMinutos)=>{
            elemento.setAttribute('data-kms', kms);
            elemento.classList.add('rf-e-race');
            const totalKms = DiaFunc.obtenerTotalKmsDia(ixs.diaIndex);
            RutinaDOMQueries.getDiaByIx(ixs.diaIndex).querySelector(`.distancia-total`).textContent = parseFloat(roundNumber(totalKms, 2)).toFixed(2);
            RutinaSet.setDiaAndElemento(ixs.numSem, ixs.diaIndex, posEle, nombre, kms, totalKms, calorias)
            if(tipo == 1)
                actualizarDiaBD(ixs.numSem, ixs.diaIndex, posEle, totalKms, calorias);//Incluye gasto calorico
            else
                actualizarDiaBD2(ixs.numSem, ixs.diaIndex, posEle, totalKms, calorias, totalMinutos);//Incluye gasto calorico y total minutos
            Indicadores.actualizarKilometrajes();
        },
        actualizarFromSE: (elemento, nombre, kms, calorias, posEle, posSE, ixs)=>{
            elemento.setAttribute('data-kms', kms);
            const totalKms = DiaFunc.obtenerTotalKmsDia(ixs.diaIndex);
            RutinaDOMQueries.getDiaByIx(ixs.diaIndex).querySelector(`.distancia-total`).textContent = parseFloat(roundNumber(totalKms, 2)).toFixed(2);
            RutinaSet.setDiaAndSubEle(ixs.numSem, ixs.diaIndex, posEle, posSE, nombre, kms, totalKms, calorias)
            actualizarDiaBD3(ixs.numSem, ixs.diaIndex, posEle, posSE, kms, totalKms, calorias);//Incluye gasto calorico
            Indicadores.actualizarKilometrajes();
        },
        copiarMiniPlantillaDia: (e)=>{
            const diaPos = e.getAttribute('data-pos');
            $diaPlantilla = $diaPlantillas[diaPos];
            $('#modalMiniPlantilla').modal('hide');
            document.querySelector('#PrincipalesTabs a[href="#tabRutina"]').click();
        },
        pegarMiniPlantillaDia: (diaIndex)=>{
            if($diaPlantilla != undefined && typeof $diaPlantilla == 'object' && $diaPlantilla.elementos != undefined && $diaPlantilla.elementos.length > 0){
                const diaHTML =  RutinaDOMQueries.getDiaByIx(diaIndex);
                diaHTML.querySelector(`.distancia-total`).textContent = parseNumberToDecimal($diaPlantilla.distancia, 2);
                diaHTML.querySelector(`.horas-totales`).textContent = parseNumberToHours($diaPlantilla.minutos);
                const diaBody = diaHTML.querySelector(`.widget-body`);
                diaBody.parentElement.replaceChild(htmlStringToElement(RutinaDiaHTML.full($diaPlantilla.elementos, diaIndex, undefined, false)), diaBody);
                const numSem = $semActual.textContent-1;
                RutinaSet.setDiaDistanciaTotal(numSem, diaIndex, $diaPlantilla.distancia);
                RutinaSet.setDiaTiempoTotal(numSem, diaIndex, $diaPlantilla.minutos);
                RutinaSet.setElementos(numSem, diaIndex, $diaPlantilla.elementos);
                RutinaSet.setCalorias(numSem, diaIndex, $diaPlantilla.calorias);
                actualizarDiaCompletoBD(numSem, diaIndex);
                instanciarElementosDiaTooltip(diaHTML);
                instanciarElementosDiaPopover(diaHTML);
                Indicadores.actualizarKilometrajes();
            }
            $diaPlantilla = {};
        },
        pegarMiniPlantillaDiaListas: (diaIndex)=>{
            if($eleListasElegidos != undefined && typeof $eleListasElegidos == 'object' && $eleListasElegidos.length > 0){
                RutinaElementoHTML.adjuntarElementos(diaIndex, $eleListasElegidos);
                const numSem = $semActual.textContent-1;
                const totDis = DiaFunc.obtenerTotalKmsDia(diaIndex), totMin = DiaFunc.obtenerTotalMinutosDia(diaIndex);
                const nuevasCalorias = DiaFunc.obtenerKilometrajeFromMiniSeleccionados();
                RutinaSet.setDiaDistanciaTotal(numSem, diaIndex, totDis);
                RutinaSet.setDiaTiempoTotal(numSem, diaIndex, totMin);
                RutinaSet.setAddDiaCalorias(numSem, diaIndex, nuevasCalorias);
                const diaHTML =  RutinaDOMQueries.getDiaByIx(diaIndex);
                diaHTML.querySelector(`.distancia-total`).textContent = parseNumberToDecimal(totDis, 2);
                diaHTML.querySelector(`.horas-totales`).textContent = parseNumberToHours(totMin);
                RutinaSet.concatElementos(numSem, diaIndex, $eleListasElegidos);
                actualizarDiaParcialBD(numSem, diaIndex,$eleListasElegidos.length);
                Indicadores.actualizarKilometrajes();
            }
            $eleListasElegidos = [];
        },
        pegarElementosSeleccionados: (diaIndex)=>{
            if($eleElegidos != undefined && typeof $eleElegidos == 'object' && $eleElegidos.length > 0){
                const eleFinales = DiaFunc.getElementosSeleccionadosFull();
                RutinaElementoHTML.adjuntarElementos(diaIndex, eleFinales);
                const numSem = $semActual.textContent-1;
                const totDis = DiaFunc.obtenerTotalKmsDia(diaIndex), totMin = DiaFunc.obtenerTotalMinutosDia(diaIndex);
                const nuevasCalorias = DiaFunc.obtenerKilometrajeFromEleSeleccionados(eleFinales);
                RutinaSet.setDiaDistanciaTotal(numSem, diaIndex, totDis);
                RutinaSet.setDiaTiempoTotal(numSem, diaIndex, totMin);
                RutinaSet.setAddDiaCalorias(numSem, diaIndex, nuevasCalorias);
                const diaHTML =  RutinaDOMQueries.getDiaByIx(diaIndex);
                diaHTML.querySelector(`.distancia-total`).textContent = parseNumberToDecimal(totDis, 2);
                diaHTML.querySelector(`.horas-totales`).textContent = parseNumberToHours(totMin);
                RutinaSet.concatElementos(numSem, diaIndex, eleFinales);
                actualizarDiaParcialBD(numSem, diaIndex,eleFinales.length);
                document.querySelectorAll('#RutinaSemana .rf-dia-elemento-nombre').forEach(v=>{v.classList.remove('rf-semanario-sels');});
                Indicadores.actualizarKilometrajes();
            }
            $eleElegidos = [];
        },
        validarGuardaMiniPlantilla:()=>{
            $("#frm_nueva_mini").validate({
                errorClass: errorClass,
                errorElement: errorElement,
                highlight: function (element) {
                    $(element).parent().removeClass('state-success').addClass("state-error");
                    $(element).removeClass('valid');
                },
                unhighlight: function (element) {
                    $(element).parent().removeClass("state-error").addClass('state-success');
                    $(element).addClass('valid');
                },
                ignore: ".ignore",
                rules: {
                    SubCategoriaId: {
                        required: true,
                        digits: true,
                    },
                    EspecificacionSubCategoriaId: {
                        required: true,
                        digits: true,
                    }
                },
                messages: {
                    SubCategoriaId: {
                        required: "El campo es obligatorio",
                        number: "El campo es obligatorio",
                    },
                    EspecificacionSubCategoriaId: {
                        required: "El campo es obligatorio",
                        number: "El campo es obligatorio",
                    }
                },
                submitHandler: function () {

                }
            })
        },
        copiarElementosCompuestosMiniPlantilla: (posDia)=>{
            $('#modalMiniPlantilla').modal('hide');
            document.querySelector('#PrincipalesTabs a[href="#tabRutina"]').click();
            $eleListasElegidos = $eleListas.filter(e=>{return e[0]==posDia}).map(ix=>{
                return $diaPlantillas[posDia].elementos[ix[1]];
            })
        },
        guardarMisPlantillas: (e)=>{
            guardarEnMisRutinas(e)
        },
        seleccionarElementos: (e)=>{
            $eleListasElegidos = [];//Para no ejecutar ambos copy a la vez(este desde el rutinario)
            const clases = e.classList;
            const diaIndex = Number(e.getAttribute('data-dia-index'));
            const eleIndex = Number(e.getAttribute('data-index'));

            if(clases.contains('rf-semanario-sels')){
                $eleElegidos = $eleElegidos.filter(e=> {return !(e[0]==diaIndex && e[1]==eleIndex)});
            }else{
                $eleElegidos.push([diaIndex, eleIndex]);
                $eleElegidos = $eleElegidos.sort();
            }
            e.classList.toggle('rf-semanario-sels');
        },
        seleccionarSubElementos: (e)=>{
            $videosElegidos = [];//Para no ejecutar ambos copy a la vez(este desde el rutinario)
            const clases = e.classList;
            const diaIndex = Number(e.getAttribute('data-dia-index'));
            const eleIndex = Number(e.getAttribute('data-ele-index'));
            const subEleIndex = Number(e.getAttribute('data-index'));

            if(clases.contains('rf-semanario-sels')){
                $subEleElegidos = $subEleElegidos.filter(e=> {return !(e[0]==diaIndex && e[2]==subEleIndex)});
            }else{
                const ops = e.previousElementSibling;
                const mV = ops.querySelector('.reprod-video') != undefined ? ops.querySelector('.reprod-video').dataset.media : null;
                const mA = ops.querySelector('.reprod-audio') != undefined ? ops.querySelector('.reprod-audio').dataset.media : null;
                const t = e.parentElement.parentElement.dataset.type;
                $subEleElegidos.push([diaIndex, eleIndex, subEleIndex, e.textContent.trim(), e.dataset.content, mV, mA, t]);
                $subEleElegidos = $subEleElegidos.sort();
            }
            e.classList.toggle('rf-semanario-sels');
        },
        actualizarObjetivo: (e)=>{
            const diaIndex = e.getAttribute('dia-index');
            if(e.parentElement.previousElementSibling.value == 0){
                $.smallBox({color: "alert", content: '<i>No ha seleccionado objetivo...</i>'})
            } else{
                $(e.parentElement.parentElement.parentElement).slideUp('slow', ()=>{
                    e.parentElement.parentElement.parentElement.remove();
                    actualizarDiaObjetivoBD(diaIndex, e.parentElement.previousElementSibling.value);
                });
            }
        }
    }
})();

RutinaParsers = (function(){
    return {
        constructorIndexObject: (diaIndex, eleIndex, subEleIndex)=>{
            const ixs = {};
            ixs.diaIndex = diaIndex;
            ixs.eleIndex = eleIndex;
            ixs.subEleIndex = subEleIndex;
            return ixs;
        },
        obtenerErroresValidacion(errors){
            return errors.split("default message").filter(v=>v.trim().startsWith("[")).map((v,i,k)=>{if(i%2==0) return {"campo": v.slice(2, v.indexOf("]")).replace(/^./, str => str.toUpperCase()), "msg": k[i+1].slice(2, k[i+1].indexOf("]")).replace(/^./, str => str.toUpperCase())}}).filter(x=>x!=undefined)
        }
    }
})();

ElementoOpc = (function(){
    return {
        eliminarElemento: (numSem, diaIndex, eleIndex)=>{
            $.smallBox({
                content: "¿Estás seguro de querer eliminar el elemento del día? <p class='text-align-right'><a onclick='ElementoOpc.confirmarEliminarElemento("+ numSem +","+ diaIndex +","+ eleIndex +")' href='javascript:void(0);' class='btn btn-primary btn-sm'>Si</a> <a href='javascript:void(0);' class='btn btn-danger btn-sm'>No</a></p>",
                color: "#296191",
                timeout: 10000,
                icon: "fa fa-bell swing animated",
                iconSmall: "",
            });
        },
        confirmarEliminarElemento: (numSem, diaIndex, eleIndex)=>{
            const ixs = RutinaParsers.constructorIndexObject(diaIndex, eleIndex);
            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
            let initEle = tempElemento;
            let eleType = tempElemento.getAttribute('data-type');
            let i=0;
            while((tempElemento = tempElemento.previousElementSibling) != null) i++;
            const dia = RutinaGet.dia(numSem, diaIndex);
            const elemento = RutinaGet.elemento(numSem, diaIndex, (posEle=i));
            let minutos = Number(elemento.minutos);dia.minutos -= minutos;
            let distancia = Number(elemento.distancia);dia.distancia -= distancia;
            dia.elementos.splice(i, 1);
            const finalHTML = RutinaDOMQueries.getPanelElementoByIxs(ixs);
            //3. Eliminando de la BD
            let calorias = 0;
            if(RutinaValidator.esZ($nombreActualizar.toUpperCase())){
                calorias = DiaFunc.obtenerGastoCalorico(Number($nombreActualizar.substr(1)) - 1, minutos);
            }else if(ElementoTP.COMPUESTO == eleType){
                const eleZ = RutinaValidator.getZFromSubEle(initEle);
                if(eleZ.length == 1){
                    calorias = DiaFunc.obtenerGastoCalorico(Number(eleZ[0].textContent.trim().substr(1)) - 1, minutos);
                }
            }
            RutinaSet.subtractDiaCalorias(numSem, diaIndex, calorias);
            Indicadores.actualizarKilometrajes();
            removerElementoBD(numSem, diaIndex, (eleIndex = i), minutos, distancia, calorias);

            $(finalHTML).slideUp('slow', ()=>{
                finalHTML.remove();
                const diaContainer = RutinaDOMQueries.getDiaByIx(diaIndex);
                const containerElementosDia = diaContainer.querySelector(`.rf-listas`);
                if(containerElementosDia.children.length == 0){
                    diaContainer.querySelector(`.inputs-init`).classList.toggle('hidden');
                    diaContainer.querySelector(`.distancia-total`).textContent = parseNumberToDecimal(0, 2);
                    diaContainer.querySelector(`.horas-totales`).textContent = parseNumberToHours(0);
                    RutinaSet.setDiaClean(numSem, diaIndex);
                }
                //4. Totalizados
                DiaOpc.actualizarTotalizadosDia(diaIndex);
            });
        },
        eliminarMediaAudio: (ixs)=>{
            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
            const iconoMedia = tempElemento.querySelector('.rf-media');
            if(iconoMedia.className.includes('fa-play') || iconoMedia.className.includes('fa-pause')){
                let i=0;
                while((tempElemento = tempElemento.previousElementSibling) != null) i++;
                RutinaSet.setElementoMediaAudio(ixs.numSem, ixs.diaIndex, (eleIndex = i), '');
                iconoMedia.remove();
                eliminarMediaElementoBD(ixs.numSem, ixs.diaIndex, (eleIndex = i), TipoElemento.AUDIO);
            }
        },
        eliminarMediaVideo: (ixs)=>{
            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
            const iconoMedia = tempElemento.querySelector('.rf-media');
            if(iconoMedia.className.includes('fa-video-camera')){
                let i=0;
                while((tempElemento = tempElemento.previousElementSibling) != null) i++;
                RutinaSet.setElementoMediaVideo(ixs.numSem, ixs.diaIndex, (eleIndex = i), '');
                iconoMedia.remove();
                eliminarMediaElementoBD(ixs.numSem, ixs.diaIndex, (eleIndex = i), TipoElemento.VIDEO);
            }
        },
        verDistanciaElemento: (ixs, input)=>{
            const elemento = RutinaDOMQueries.getElementoByIxs(ixs);
            input.textContent = elemento.getAttribute('data-kms')!=undefined?elemento.getAttribute('data-kms').trim():'';
            $kmsActualizar = input.textContent.trim();
        },
        actualizarTiempoElemento: (ixs, minutos)=>{
            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs), i=0;

            let initTempElemento = tempElemento;
            const eleType = initTempElemento.getAttribute('data-type');
            const nombreEle = initTempElemento.querySelector('.rf-dia-elemento-nombre').textContent.trim().toUpperCase();

            while((tempElemento = tempElemento.previousElementSibling) != null) i++;
            RutinaSet.setElementoMinutos(ixs.numSem, ixs.diaIndex, (posEle = i), minutos);
            const totalMin = DiaFunc.obtenerTotalMinutosDia(ixs.diaIndex);
            RutinaSet.setDiaTiempoTotal(ixs.numSem, ixs.diaIndex, totalMin);
            RutinaDOMQueries.getDiaByIx(ixs.diaIndex).querySelector(`.horas-totales`).textContent = parseNumberToHours(totalMin);

            if(RutinaValidator.esZ(nombreEle)){
                let anteriorMinutos = $tiempoActualizar;
                let caloriasNuevas = 0;
                const zonaNum = Number(nombreEle.substr(1)) - 1;
                let kilometraje = DiaFunc.obtenerKilometraje(ixs.numSem, zonaNum, minutos);

                if(minutos>0 && anteriorMinutos == 0){
                    caloriasNuevas = DiaFunc.obtenerGastoCalorico(zonaNum, minutos);
                }else if(minutos == 0 && anteriorMinutos > 0){
                    caloriasNuevas = -DiaFunc.obtenerGastoCalorico(zonaNum, anteriorMinutos) //En este caso colocamos el número negativo ya que se esta cambiando el tiempo a 0min
                }else{
                    caloriasNuevas = DiaFunc.obtenerGastoCalorico(zonaNum, minutos) - DiaFunc.obtenerGastoCalorico(zonaNum, anteriorMinutos);
                }
                DiaOpc.actualizar(initTempElemento, nombreEle, kilometraje, caloriasNuevas, i, ixs, 2, totalMin);//Plus
            }else if(ElementoTP.COMPUESTO == eleType){
                const eleZ = RutinaValidator.getZFromSubEle(initTempElemento);
                if(eleZ.length == 1){
                    let anteriorMinutos = $tiempoActualizar;
                    let caloriasNuevas = 0;
                    const zonaNum = Number(eleZ[0].textContent.trim().toUpperCase().substr(1)) - 1;
                    let kilometraje = DiaFunc.obtenerKilometraje(ixs.numSem, zonaNum, minutos);

                    if(minutos>0 && anteriorMinutos == 0){
                        caloriasNuevas = DiaFunc.obtenerGastoCalorico(zonaNum, minutos);
                    }else if(minutos == 0 && anteriorMinutos > 0){
                        caloriasNuevas = -DiaFunc.obtenerGastoCalorico(zonaNum, anteriorMinutos) //En este caso colocamos el número negativo ya que se esta cambiando el tiempo a 0min
                    }else{
                        caloriasNuevas = DiaFunc.obtenerGastoCalorico(zonaNum, minutos) - DiaFunc.obtenerGastoCalorico(zonaNum, anteriorMinutos);
                    }
                    DiaOpc.actualizar(initTempElemento, nombreEle, kilometraje, caloriasNuevas, i, ixs, 2, totalMin);//Plus
                }else{
                    actualizarTiempoElementoBD(ixs.numSem, ixs.diaIndex, (eleIndex = i), totalMin);
                }
            }else{
                actualizarTiempoElementoBD(ixs.numSem, ixs.diaIndex, (eleIndex = i), totalMin);
            }
        },
        agregarMediaToElemento: (ixs, input)=>{
            const assetsElemento = input.parentElement;
            const iconoOpc = assetsElemento.querySelector('.ele-ops');
            const iconoMedia = assetsElemento.querySelector('.rf-media');

            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs), i=0;
            while((tempElemento = tempElemento.previousElementSibling) != null) i++;
            if($tipoMedia == TipoElemento.AUDIO){
                RutinaSet.setElementoMediaAudio(ixs.numSem, ixs.diaIndex, (eleIndex=i), $mediaAudio);
                //Primer if para actualizar
                if(iconoMedia != undefined && (iconoMedia.className.includes('fa-play') || iconoMedia.className.includes('fa-pause'))){
                    iconoMedia.setAttribute('data-media', $mediaAudio);
                }else{//Para registrar
                    iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoAudio($mediaAudio));
                }
                actualizarMediaElementoBD(ixs.numSem, ixs.diaIndex, (eleIndex = i), TipoElemento.AUDIO);
                $mediaAudio = '';
            }else if ($tipoMedia == TipoElemento.VIDEO) {
                RutinaSet.setElementoMediaVideo(ixs.numSem, ixs.diaIndex, (eleIndex=i), $mediaVideo);
                if(iconoMedia != undefined && iconoMedia.className.includes('fa-video-camera')){//Primer if para actualizar
                    iconoMedia.setAttribute('data-media', $mediaVideo);
                }else{//Para registrar
                    iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoVideo($mediaVideo));
                }
                actualizarMediaElementoBD(ixs.numSem, ixs.diaIndex, (eleIndex = i), TipoElemento.VIDEO);
                $mediaVideo = '';
            }
        },
        agregarMediaToElemento2: (ixs, input)=>{
            const assetsElemento = input.parentElement;
            const iconoOpc = assetsElemento.querySelector('.ele-ops');
            const iconoMedia = assetsElemento.querySelector('.rf-media');

            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs), i=0;
            let initTempElemento = tempElemento;
            while((tempElemento = tempElemento.previousElementSibling) != null) i++;
            if($tipoMedia == TipoElemento.AUDIO){
                RutinaSet.setElementoMediaAudio(ixs.numSem, ixs.diaIndex, (eleIndex=i), $mediaAudio, $mediaNombre);
                //Primer if para actualizar
                if(iconoMedia != undefined && (iconoMedia.className.includes('fa-play') || iconoMedia.className.includes('fa-pause'))){
                    iconoMedia.setAttribute('data-media', $mediaAudio);
                }else{//Para registrar
                    iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoAudio($mediaAudio));
                }
                actualizarMediaElementoBD(ixs.numSem, ixs.diaIndex, (eleIndex = i), TipoElemento.AUDIO, $mediaNombre);
                $mediaAudio = '';
            }else if ($tipoMedia == TipoElemento.VIDEO) {
                RutinaSet.setElementoMediaVideo(ixs.numSem, ixs.diaIndex, (eleIndex=i), $mediaVideo, $mediaNombre);
                if(iconoMedia != undefined && iconoMedia.className.includes('fa-video-camera')){//Primer if para actualizar
                    iconoMedia.setAttribute('data-media', $mediaVideo);
                }else{//Para registrar
                    iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoVideo($mediaVideo));
                }
                actualizarMediaElementoBD(ixs.numSem, ixs.diaIndex, (eleIndex = i), TipoElemento.VIDEO, $mediaNombre);
                $mediaVideo = '';
            }
            initTempElemento.querySelector('.rf-dia-elemento-nombre').textContent = $mediaNombre;
        },
        agregarMediaElemento: (ixs, input, tipoEle, posEle)=>{
            const assetsElemento = input.parentElement;
            const iconoOpc = assetsElemento.querySelector('.ele-ops');
            const iconoMedia = assetsElemento.querySelector('.rf-media');

            if($tipoMedia == TipoElemento.AUDIO){
                RutinaSet.setElementoMediaAudio(ixs.numSem, ixs.diaIndex, posEle, $mediaAudio, $mediaNombre);
                //Primer if para actualizar
                if(iconoMedia != undefined && (iconoMedia.className.includes('fa-play') || iconoMedia.className.includes('fa-pause'))){
                    iconoMedia.setAttribute('data-media', $mediaAudio);
                }else{//Para registrar
                    iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoAudio($mediaAudio));
                }
                actualizarElementoStrategyBD(ixs.numSem, ixs.diaIndex, posEle, TipoElemento.AUDIO, tipoEle);
                $mediaAudio = '';
            }else if ($tipoMedia == TipoElemento.VIDEO) {
                RutinaSet.setElementoMediaVideo(ixs.numSem, ixs.diaIndex, posEle, $mediaVideo, $mediaNombre);
                if(iconoMedia != undefined && iconoMedia.className.includes('fa-video-camera')){//Primer if para actualizar
                    iconoMedia.setAttribute('data-media', $mediaVideo);
                }else{//Para registrar
                    iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoVideo($mediaVideo));
                }
                actualizarElementoStrategyBD(ixs.numSem, ixs.diaIndex, (posEle), TipoElemento.VIDEO, tipoEle);
                $mediaVideo = '';
            }
            instanciarEspecificosTooltip2(input);
            instanciarElementoPopovers(assetsElemento);
        },
        agregarInitMediaElemento: (ixs, tipo)=>{
            const nuevoIx = ElementoTP.SIMPLE == tipo? RutinaSeccion.newElementoSimple(ixs.diaIndex, tipo, $mediaNombre) : RutinaSeccion.newElementoLista(ixs.diaIndex, tipo, $mediaNombre);
            ixs.eleIndex = nuevoIx;
            const elemento = RutinaDOMQueries.getElementoByIxs(ixs);
            const iconoOpc = elemento.querySelector('.ele-ops');
            const ele = {};
            ele.nombre = $mediaNombre;
            if($tipoMedia == TipoElemento.AUDIO){
                ele.mediaAudio = $mediaAudio;
                iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoAudio($mediaAudio));
            }else{
                ele.mediaVideo = $mediaVideo;
                iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoVideo($mediaVideo));
            }

            $rutina.semanas[ixs.numSem].dias[ixs.diaIndex].elementos.push(new Elemento(ele));
            agregarElementoBD(ixs.numSem, ixs.diaIndex, tipo);
            const nueEle = RutinaDOMQueries.getElementoByIxs(ixs);
            instanciarElementoTooltips(nueEle);
            instanciarElementoPopovers(nueEle);
            $mediaAudio = "";
            $mediaVideo = "";
        },
        reproducirVideo: (input)=>{
            const mediaVideo = input.getAttribute('data-media');
            input.classList.toggle('fa-video-camera');
            input.classList.toggle('fa-close');
            input.classList.toggle('txt-color-redLight');

            let mainContainer = input.parentElement.parentElement;
            if(mainContainer.tagName === "DIV"){
                //SUB ELEMENTOS
                if(mainContainer.children[2] != undefined){
                    if(mainContainer.children[3] != undefined) {
                        if(mainContainer.children[3].tagName === "DIV") {
                            $("#VideoReproduccion").parent().get(0).pause();
                            $(mainContainer.children[3]).slideUp('slow', ()=> {
                                mainContainer.children[3].remove();
                            });
                        }else {}
                    }else{
                        if(mainContainer.children[2].tagName === "DIV"){
                            $("#VideoReproduccion").parent().get(0).pause();
                            $(mainContainer.children[2]).slideUp('slow', ()=> {
                                mainContainer.children[2].remove();
                            });
                        }else{
                            mainContainer.append(htmlStringToElement(RutinaPS.videoMiniatura(mediaVideo)));
                        }
                    }
                }else {
                    mainContainer.append(htmlStringToElement(RutinaPS.videoMiniatura(mediaVideo)));
                }
            }else{
                //ELEMENTOS(Para Simple && Compuesta)
                const mainContainerEle = mainContainer.parentElement;
                //const tipoContent = mainContainerEle.parentElement.parentElement.parentElement.getAttribute('data-type');
                /*if(tipoContent == 1){*///Simple
                if(mainContainerEle.nodeName == "A"){
                    if(mainContainerEle.children.length == 5){
                        $(mainContainerEle.children[4].children[0]).slideUp('slow', ()=> {
                            mainContainerEle.children[4].remove();
                        });
                    }else{
                        mainContainerEle.append(htmlStringToElement(RutinaPS.videoMiniatura(mediaVideo)));
                    }
                }
                /*}else{//Compuesto(Lista)
                    if(mainContainerEle.nodeName == "A"){
                        if(mainContainerEle.children.length == 5){
                            $(mainContainerEle.children[4].children[0]).slideUp('slow', ()=> {
                                mainContainerEle.children[4].remove();
                            });
                        }else{
                            mainContainerEle.append(htmlStringToElement(RutinaPS.videoMiniatura(mediaVideo)));
                        }
                    }
                }*/
            }
        },
        descomprimirDetalle: (ele)=>{
            const type = ele.getAttribute('data-type');
            if(type == 2) {
                const collapsable = ele.querySelector('a[data-toggle="collapse"]');
                const panelCollapsable = ele.querySelector('.panel-collapse');
                collapsable.classList.remove('collapsed');
                collapsable.setAttribute('aria-expanded', "true");
                panelCollapsable.classList.add('in');
                panelCollapsable.setAttribute('aria-expanded', "false");
                panelCollapsable.style = '';
            }
        }
    }
})();

SubEleOpc = (function(){
    return {
        eliminarSubElemento: (numSem, diaIndex, eleIndex, subEleIndex)=>{
            const dia = RutinaGet.dia(numSem, diaIndex);
            const ixs = RutinaParsers.constructorIndexObject(diaIndex, eleIndex, subEleIndex);
            let i=0, k=0;
            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
            let eleInit = tempElemento;
            while((tempElemento = tempElemento.previousElementSibling) != null) i++;
            const elemento = RutinaGet.elemento(numSem, diaIndex, (posEle=i));
            let tempSubEle = RutinaDOMQueries.getSubElementoByIxs(ixs);
            let subEleInit = tempSubEle;
            while((tempSubEle = tempSubEle.previousElementSibling) != null) k++;
            $rutina.semanas[numSem].dias[diaIndex].elementos[i].subElementos.splice(k, 1);
            let distancia = 0;
            let calorias = 0;
            const minutos = eleInit.querySelector('.agregar-tiempo').value;
            if(RutinaValidator.esZ($nombreActualizar.toUpperCase()) && minutos > 0){
                distancia = DiaFunc.obtenerKilometraje(numSem, Number($nombreActualizar.substr(1)) - 1, minutos);
                dia.distancia -= distancia;
                calorias = DiaFunc.obtenerGastoCalorico(Number($nombreActualizar.substr(1)) - 1, minutos);
                RutinaSet.subtractDiaCalorias(numSem, diaIndex, calorias);
                Indicadores.actualizarKilometrajes();
                eleInit.setAttribute('data-kms', '0');
            }
            removerSubElementoBD(numSem, diaIndex, (elemIndex = i), (subElemIndex = k), distancia, calorias);
            $(subEleInit).slideUp('fast', ()=>{
                subEleInit.remove();
                if(eleInit.querySelector(`.detalle-lista`).children.length==0){
                    eleInit.querySelector(`.in-init-sub-ele`).classList.toggle('hidden');
                    RutinaSet.setDiaClean(numSem, diaIndex);
                }
            });
            //4. Totalizados
            DiaOpc.actualizarTotalizadosDia(diaIndex);
        },
        eliminarMediaAudio: (ixs)=>{
            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
            const iconoMedia = tempElemento.querySelector('.rf-media');
            if(iconoMedia.className.includes('fa-play') || iconoMedia.className.includes('fa-pause')){
                let i=0, k=0;
                while((tempElemento = tempElemento.previousElementSibling) != null) i++;
                let tempSubEle = RutinaDOMQueries.getSubElementoByIxs(ixs)
                while((tempSubEle = tempSubEle.previousElementSibling) != null) k++;
                RutinaSet.setSubElementoMediaAudio(ixs.numSem, ixs.diaIndex, (eleIndex = i), (subEleIndex = k), '');
                iconoMedia.remove();
                eliminarMediaSubElementoBD(ixs.numSem, ixs.diaIndex, (eleIndex = i), (subEleIndex = k), TipoElemento.AUDIO);
            }
        },
        eliminarMediaVideo: (ixs)=>{
            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs);
            const iconoMedia = tempElemento.querySelector('.rf-media');
            if(iconoMedia.className.includes('fa-video-camera')){
                let i=0, k=0;
                while((tempElemento = tempElemento.previousElementSibling) != null) i++;
                let tempSubEle = RutinaDOMQueries.getSubElementoByIxs(ixs)
                while((tempSubEle = tempSubEle.previousElementSibling) != null) k++;
                RutinaSet.setSubElementoMediaVideo(ixs.numSem, ixs.diaIndex, (eleIndex = i), (subEleIndex = k), '');
                iconoMedia.remove();
                eliminarMediaSubElementoBD(ixs.numSem, ixs.diaIndex, (eleIndex = i), (subEleIndex = k), TipoElemento.VIDEO);
            }
        },
        agregarMediaToSubElemento: (ixs, input)=>{
            console.log('deprecated...');
            /*const assetsElemento = input.parentElement;
            const iconoOpc = assetsElemento.querySelector('.sub-ele-ops');
            const iconoMedia = assetsElemento.querySelector('.rf-media');

            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs), i=0, k=0;
            while((tempElemento = tempElemento.previousElementSibling) != null) i++;
            let tempSubEle = RutinaDOMQueries.getSubElementoByIxs(ixs)
            while((tempSubEle = tempSubEle.previousElementSibling) != null) k++;
            if($tipoMedia == TipoElemento.AUDIO){
                RutinaSet.setSubElementoMediaAudio(ixs.numSem, ixs.diaIndex, (eleIndex=i), (subEleIndex=k), $mediaAudio);
                //Primer if para actualizar
                if(iconoMedia != undefined && (iconoMedia.className.includes('fa-play') || iconoMedia.className.includes('fa-pause'))){
                    iconoMedia.setAttribute('data-media', $mediaAudio);
                }else{//Para registrar
                    iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoAudio($mediaAudio));
                }
                eliminarMediaSubElementoBD(ixs.numSem, ixs.diaIndex, (eleIndex = i), (subEleIndex = k), TipoElemento.AUDIO);
                $mediaAudio = '';
            }else if ($tipoMedia == TipoElemento.VIDEO) {
                RutinaSet.setSubElementoMediaVideo(ixs.numSem, ixs.diaIndex, (eleIndex=i), (subEleIndex=k), $mediaVideo);
                if(iconoMedia != undefined && iconoMedia.className.includes('fa-video-camera')){//Primer if para actualizar
                    iconoMedia.setAttribute('data-media', $mediaVideo);
                }else{//Para registrar
                    iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoVideo($mediaVideo));
                }
                eliminarMediaSubElementoBD(ixs.numSem, ixs.diaIndex, (eleIndex = i), (subEleIndex = k), TipoElemento.VIDEO);
                $mediaVideo = '';
            }*/
        },
        agregarMediaToSubElemento2: (ixs, input)=>{
            const assetsElemento = input.parentElement;
            const iconoOpc = assetsElemento.querySelector('.sub-ele-ops');
            const iconoMedia = assetsElemento.querySelector('.rf-media');

            let tempElemento = RutinaDOMQueries.getElementoByIxs(ixs), i=0, k=0;
            while((tempElemento = tempElemento.previousElementSibling) != null) i++;
            let tempSubEle = RutinaDOMQueries.getSubElementoByIxs(ixs)
            let initTempSubElemento = tempSubEle;
            while((tempSubEle = tempSubEle.previousElementSibling) != null) k++;
            if($tipoMedia == TipoElemento.AUDIO){
                RutinaSet.setSubElementoMediaAudio(ixs.numSem, ixs.diaIndex, (eleIndex=i), (subEleIndex=k), $mediaAudio, $mediaNombre);
                //Primer if para actualizar
                if(iconoMedia != undefined && (iconoMedia.className.includes('fa-play') || iconoMedia.className.includes('fa-pause'))){
                    iconoMedia.setAttribute('data-media', $mediaAudio);
                }else{//Para registrar
                    iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoAudio($mediaAudio));
                }
                actualizarMediaSubElementoBD2(ixs.numSem, ixs.diaIndex, (eleIndex = i), (subEleIndex = k), TipoElemento.AUDIO);
                $mediaAudio = '';
            }else if ($tipoMedia == TipoElemento.VIDEO) {
                RutinaSet.setSubElementoMediaVideo(ixs.numSem, ixs.diaIndex, (eleIndex=i), (subEleIndex=k), $mediaVideo, $mediaNombre);
                if(iconoMedia != undefined && iconoMedia.className.includes('fa-video-camera')){//Primer if para actualizar
                    iconoMedia.setAttribute('data-media', $mediaVideo);
                }else{//Para registrar
                    iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoVideo($mediaVideo));
                }
                actualizarMediaSubElementoBD2(ixs.numSem, ixs.diaIndex, (eleIndex = i), (subEleIndex = k), TipoElemento.VIDEO);
                $mediaVideo = '';
            }
            initTempSubElemento.querySelector('.rf-sub-elemento-nombre').textContent = $mediaNombre;
            instanciarEspecificosTooltip2(input);
        },
        agregarMediaSubElemento: (ixs, input, eleIndex, subEleIndex)=>{
            const assetsElemento = input.parentElement;
            const iconoOpc = assetsElemento.querySelector('.sub-ele-ops');
            const iconoMedia = assetsElemento.querySelector('.rf-media');

            if($tipoMedia == TipoElemento.AUDIO){
                RutinaSet.setSubElementoMediaAudio(ixs.numSem, ixs.diaIndex, eleIndex, subEleIndex, $mediaAudio, $mediaNombre);
                //Primer if para actualizar
                if(iconoMedia != undefined && (iconoMedia.className.includes('fa-play') || iconoMedia.className.includes('fa-pause'))){
                    iconoMedia.setAttribute('data-media', $mediaAudio);
                }else{//Para registrar
                    iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoAudioLp($mediaAudio));
                }
                actualizarSubElementoStrategyBD(ixs.numSem, ixs.diaIndex,  eleIndex, subEleIndex, TipoElemento.AUDIO);
                $mediaAudio = '';
            }else if ($tipoMedia == TipoElemento.VIDEO) {
                RutinaSet.setSubElementoMediaVideo(ixs.numSem, ixs.diaIndex, eleIndex, subEleIndex, $mediaVideo, $mediaNombre);
                if(iconoMedia != undefined && iconoMedia.className.includes('fa-video-camera')){//Primer if para actualizar
                    iconoMedia.setAttribute('data-media', $mediaVideo);
                }else{//Para registrar
                    iconoOpc.insertAdjacentHTML('beforebegin', RutinaElementoHTML.iconoVideoLp($mediaVideo));
                }
                actualizarSubElementoStrategyBD(ixs.numSem, ixs.diaIndex, eleIndex, subEleIndex, TipoElemento.VIDEO);
                $mediaVideo = '';
            }
            instanciarSubElementoTooltip(assetsElemento);
            instanciarSubElementoPopover(assetsElemento);
        },
    }
})();

DiaFunc = (function(){
    return {
        obtenerTotalKmsDia: (diaIndex)=>{
            return Array.from(document.querySelectorAll(`#RutinaSemana .rf-dia[data-index="${diaIndex}"] .rf-dia-elemento`)).map(v=>{
                return Number(v.getAttribute('data-kms'));
            }).reduce((x,y)=>{
                return x+y;
            });
        },
        obtenerTotalMinutosDia: (diaIndex)=>{
            return Array.from(document.querySelectorAll(`#RutinaSemana .rf-dia[data-index="${diaIndex}"] .agregar-tiempo`)).map(v=>{
                return Number(v.value);
            }).reduce((x,y)=>{
                return x+y;
            });
        },
        // Month here is 1-indexed (January is 1, February is 2, etc). This is because we're using 0 as the day so that it returns the last day
        // of the last month, so you have to add 1 to the month number so it returns the correct amount of days
        obtenerDiasByMes: (y, m)=>{
            return new Date(y, m+1, 0).getDate();
        },
        obtenerGastoCalorico: (nivelZIndex, tiempoMinutos)=>{
            const semActualIx = Number($('#SemanaActual').text())-1;
            let factorTiempo;
            if(nivelZIndex == 0){
                factorTiempo = $rutina.semanas[semActualIx].metricas[nivelZIndex].indicadores.max.toSeconds();
            }else
                factorTiempo = $rutina.semanas[semActualIx].metricas[nivelZIndex].indicadores.min.toSeconds();
            let factorCalorico = BaseCalculo.factoresCaloricos[nivelZIndex].factor;
            return roundNumber((roundNumber((tiempoMinutos / ((factorTiempo*factorCalorico)/60)),2)*75),1);
        },
        obtenerKilometraje: (numSem, zonaNum, tiempoAsignado)=>{
            const indicadores = $rutina.semanas[numSem].metricas[zonaNum].indicadores;
            let kilometraje = 0;
            if(zonaNum == 0){
                kilometraje = roundNumber((tiempoAsignado*60) / indicadores.max.toSeconds(), 1);
            }else if(zonaNum == 6){
                kilometraje = roundNumber((tiempoAsignado*60) / indicadores.min.toSeconds(), 1);
            }else {
                kilometraje = roundNumber((tiempoAsignado*60) / ((indicadores.min.toSeconds() + indicadores.max.toSeconds()) / 2), 1);
            }
            return kilometraje;
        },
        obtenerKilometrajeFromMiniSeleccionados: ()=>{
            let calorias = 0;
            $eleListasElegidos.forEach(v=>{
                if(v.tipo == ElementoTP.SIMPLE && v.minutos > 0){
                    if(RutinaValidator.esZ(v.nombre))
                        calorias += DiaFunc.obtenerGastoCalorico(Number(v.nombre.substr(1)) - 1, v.minutos);
                }else if(v.tipo == ElementoTP.COMPUESTO && v.minutos > 0) {
                    if(v.distancia>0)
                        if(RutinaValidator.esZ(v.nombre))
                            calorias += DiaFunc.obtenerGastoCalorico(Number(v.nombre.substr(1)) - 1, v.minutos);
                        else{
                            calorias += DiaFunc.obtenerGastoCalorico(Number(v.subElementos.filter(w=>{return RutinaValidator.esZ(w.nombre)})[0].nombre.substr(1)) - 1, v.minutos);
                        }
                }
            });
            return calorias;
        },
        obtenerKilometrajeFromEleSeleccionados: (eleFinales)=>{
            let calorias = 0;
            eleFinales.forEach(v=>{
                if(v.tipo == ElementoTP.SIMPLE && v.minutos > 0){
                    if(RutinaValidator.esZ(v.nombre))
                        calorias += DiaFunc.obtenerGastoCalorico(Number(v.nombre.substr(1)) - 1, v.minutos);
                }else if(v.tipo == ElementoTP.COMPUESTO && v.minutos > 0) {
                    if(v.distancia>0)
                        if(RutinaValidator.esZ(v.nombre))
                            calorias += DiaFunc.obtenerGastoCalorico(Number(v.nombre.substr(1)) - 1, v.minutos);
                        else{
                            calorias += DiaFunc.obtenerGastoCalorico(Number(v.subElementos.filter(w=>{return RutinaValidator.esZ(w.nombre)})[0].nombre.substr(1)) - 1, v.minutos);
                        }

                }
            });
            return calorias;
        },
        getElementosSeleccionadosFull: ()=>{
            const numSem = $semActual.textContent -1
            return $eleElegidos.map(v=>{
                const ixs = {numSem: numSem, diaIndex: v[0], eleIndex: v[1]};
                let tempEle = RutinaDOMQueries.getElementoByIxs(ixs), i=0;
                while((tempEle = tempEle.previousElementSibling) != null) i++;
                return $rutina.semanas[ixs.numSem].dias[v[0]].elementos[i]
            });
        },
        obtenerTiempoCarreraReferencia: (diaIndex)=>{
            const firstElementoCarrera = Array.from(RutinaDOMQueries.getDiaByIx(diaIndex).querySelectorAll('.rf-dia-elemento')).find(v=>Number(v.getAttribute('data-kms'))>0);
            return firstElementoCarrera != undefined ? String(60/(Number(firstElementoCarrera.getAttribute('data-kms')) / firstElementoCarrera.querySelector('input.agregar-tiempo').value)).toHHMMSSM() : "00:06:20";
        }
    }
})();

CabeceraOpc = (function(){
    return {
        positionPopoverByDiaIndex: (dIx)=>{
            return dIx == "0"?"right":"right";
        }
    }
})();

RutinaSeccion = (function (){
    return {
        newElementoSimple: (diaIndex, tipo, nombre)=>{
            let posPopover = CabeceraOpc.positionPopoverByDiaIndex(diaIndex);
            let ix = ++indexGlobal;
            let elementoHTML = `
                    <div class="panel panel-default rf-dia-elemento" data-index="${ix}" data-type="${tipo}">
                        <div class="panel-heading">
                            <h4 class="panel-title txt-color-blue">
                                <a href="javascrip:void(0);">
                                    <i class="fa fa-lg fa-angle-down pull-right invisible"></i>
                                    <i class="fa fa-lg fa-angle-up pull-right invisible"></i>  
                                    <input type="number" maxlength="3" class="agregar-tiempo pull-right" data-index="${ix}" data-dia-index="${diaIndex}" data-placement="top" rel="tooltip" data-original-title="Añadir tiempo en minutos"/>
                                    <span class="lista-title">
                                        <span class="pull-left">
                                            <i class="fa fa-plus txt-color-blueLight padding-top-3 insertar-debajo font-xs" rel="tooltip" data-placement="bottom" data-original-title="Agregar pares" data-dia-index="${diaIndex}" data-index="${ix}"></i>
                                            <i class="fa fa-caret-up txt-color-blue ele-ops padding-top-3" rel="popover" data-placement="${posPopover}" data-content="${RutinaPS.opsPopoverElemento(diaIndex, ix)}" data-html="true" data-dia-index="${diaIndex}" data-index="${ix}" data-toggle="popover"></i>
                                        </span>
                                        <span class="rf-dia-elemento-nombre padding-10" data-index="${ix}" data-dia-index="${diaIndex}" contenteditable="true" data-placement="bottom" data-toggle="popover" data-content="" data-trigger="hover">${nombre}</span>                                          
                                    </span>
                                </a>
                            </h4>
                        </div>
                    </div>
                    `;
            RutinaDOMQueries.getDiaByIx(diaIndex).querySelector('.rf-listas').appendChild(htmlStringToElement(elementoHTML));
            return ix;
        },
        newElementoLista: (diaIndex, tipo, nombre)=>{
            let posPopover = CabeceraOpc.positionPopoverByDiaIndex(diaIndex);
            let ix = ++indexGlobal;
            let subEleHTML = `
                    <div class="panel panel-default rf-dia-elemento" data-index="${ix}" data-type="${tipo}">
                                        <div class="panel-heading">
                                            <h4 class="panel-title txt-color-blue">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${ix}">
                                                        <i class="fa fa-lg fa-angle-down pull-right text-primary"></i> 
                                                        <i class="fa fa-lg fa-angle-up pull-right text-primary"></i>
                                                        <input type="number" maxlength="3" class="pull-right agregar-tiempo" data-index="${ix}" data-dia-index="${diaIndex}" contenteditable="true" data-placement="top" rel="tooltip" data-original-title="Añadir tiempo en minutos"/>
                                                        <span class="lista-title">
                                                            <span class="pull-left">
                                                                <i class="fa fa-plus txt-color-blueLight padding-top-3 insertar-debajo font-xs" rel="tooltip" data-placement="bottom" data-original-title="Agregar pares" data-dia-index="${diaIndex}" data-index="${ix}"></i>
                                                                <i class="fa fa-caret-up txt-color-blue ele-ops padding-top-3" rel="popover" data-placement="${posPopover}" data-content="${RutinaPS.opsPopoverElemento2(diaIndex, ix)}" data-html="true" data-dia-index="${diaIndex}" data-index="${ix}" data-toggle="popover"></i>
                                                            </span>
                                                            <span class="rf-dia-elemento-nombre padding-10" data-index="${ix}" data-dia-index="${diaIndex}" contenteditable="true" data-placement="bottom" data-toggle="popover" data-content="" data-trigger="hover">${nombre}</span>
                                                        </span>
                                                    </a>
                                            </h4>
                                        </div>
                                        <div id="collapse${ix}" class="panel-collapse collapse in">
                                            <div class="panel-body">
                                                <div class="modulo-detalle">
                                                    <div class="smart-form"><label class="input padding-5"><input data-ele-index="${ix}" data-dia-index="${diaIndex}" class="in-sub-elemento in-init-sub-ele" type="text" maxlength="44" placeholder=""></label></div>
                                                    <div class="dd nestable">
                                                        <ol class="dd-list detalle-lista">
                                                        </ol>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>`;
            RutinaDOMQueries.getDiaByIx(diaIndex).querySelector('.rf-listas').appendChild(htmlStringToElement(subEleHTML));
            return ix;
        },
        newElementoPosEspecifica: (diaIndex, tipo, nombre, strategy, eleRef)=>{
            let posPopover = CabeceraOpc.positionPopoverByDiaIndex(diaIndex);
            let ix = ++indexGlobal;
            let elementoHTML = '';
            if(tipo == ElementoTP.SIMPLE){
                elementoHTML = `
                         <div class="panel panel-default rf-dia-elemento" data-index="${ix}" data-type="1">
                              <div class="panel-heading">
                                  <h4 class="panel-title txt-color-blue">
                                      <a href="javascrip:void(0);">
                                          <i class="fa fa-lg fa-angle-down pull-right invisible"></i>
                                          <i class="fa fa-lg fa-angle-up pull-right invisible"></i>
                                          <input type="number" maxlength="3" class="pull-right agregar-tiempo" data-index="${ix}" data-dia-index="${diaIndex}" contenteditable="true" data-placement="top" rel="tooltip" data-original-title="Añadir tiempo en minutos"/>  
                                          <span class="lista-title">
                                              <span class="pull-left">
                                                <i class="fa fa-plus txt-color-blueLight padding-top-3 insertar-debajo font-xs" rel="tooltip" data-placement="bottom" data-original-title="Agregar pares" data-dia-index="${diaIndex}" data-index="${ix}"></i>
                                                <i class="fa fa-caret-up txt-color-blue ele-ops padding-top-3" rel="popover" data-placement="${posPopover}" data-content="${RutinaPS.opsPopoverElemento(diaIndex, ix)}" data-html="true" data-dia-index="${diaIndex}" data-index="${ix}" data-toggle="popover"></i>
                                              </span>
                                              <span class="rf-dia-elemento-nombre padding-10" data-index="${ix}" data-dia-index="${diaIndex}" contenteditable="true" data-placement="bottom" data-toggle="popover" data-content="" data-trigger="hover">${nombre}</span>                                                                                                                            
                                          </span>
                                      </a>
                                  </h4>
                              </div>
                         </div>
                         `;
            } else if(tipo == ElementoTP.COMPUESTO){
                elementoHTML =
                    `<div class="panel panel-default rf-dia-elemento" data-index="${ix}" data-type="2">
                                                    <div class="panel-heading">
                                                        <h4 class="panel-title txt-color-blue">
                                                             <a data-toggle="collapse" data-parent="#accordion" href="#collapse${ix}">
                                                                 <i class="fa fa-lg fa-angle-down pull-right text-primary"></i>
                                                                 <i class="fa fa-lg fa-angle-up pull-right text-primary"></i>
                                                                 <input type="number" maxlength="3" class="pull-right agregar-tiempo" data-index="${ix}" data-dia-index="${diaIndex}" contenteditable="true" data-placement="top" rel="tooltip" data-original-title="Añadir tiempo en minutos"/>
                                                                 <span class="lista-title">
                                                                     <span class="pull-left">
                                                                         <i class="fa fa-plus txt-color-blueLight padding-top-3 insertar-debajo font-xs" rel="tooltip" data-placement="bottom" data-original-title="Agregar pares" data-dia-index="${diaIndex}" data-index="${ix}"></i>
                                                                         <i class="fa fa-caret-up txt-color-blue ele-ops padding-top-3" rel="popover" data-placement="${posPopover}" data-content="${RutinaPS.opsPopoverElemento2(diaIndex, ix)}" data-html="true" data-dia-index="${diaIndex}" data-index="${ix}" data-toggle="popover"></i>
                                                                     </span>
                                                                     <span class="rf-dia-elemento-nombre padding-10" contenteditable="true" data-placement="bottom" data-toggle="popover" data-content="" data-trigger="hover" data-index="${ix}" data-dia-index="${diaIndex}">${nombre}</span>
                                                                 </span>
                                                             </a>
                                                        </h4>
                                                    </div>
                                                    <div id="collapse${ix}" class="panel-collapse collapse in">
                                                        <div class="panel-body">
                                                            <div class="modulo-detalle">
                                                                <div class="smart-form"><label class="input padding-5"><input data-ele-index="${ix}" data-dia-index="${diaIndex}" class="in-sub-elemento in-init-sub-ele" type="text" maxlength="44" placeholder="
                                                                "></label></div>
                                                                <div class="dd nestable">
                                                                    <ol class="dd-list detalle-lista">
                                                                    </ol>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>`;
            }
            eleRef.insertAdjacentHTML(strategy, elementoHTML);
            return ix;
        },
        newSubElemento: (diaIndex, eleIndex, tipo, nombre)=>{
            let posPopover = CabeceraOpc.positionPopoverByDiaIndex(diaIndex);
            let ix = ++indexGlobal;
            let subEleHTML = `
                    <div class="col-md-12 rf-sub-elemento pading-hz-6" data-index="${ix}" data-type="${tipo}">
                        <span class="pull-left">
                            <i class="fa fa-plus txt-color-blueLight padding-top-1 insertar-debajo-sub" rel="tooltip" data-placement="bottom" data-original-title="Agregar pares" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}"></i>
                            <i class="fa fa-angle-right txt-color-blue sub-ele-ops padding-top-1" rel="popover" data-placement="${posPopover}" data-content="${RutinaPS.opsPopoverSubElemento(diaIndex, eleIndex, ix)}" data-html="true" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}" data-toggle="popover"></i>
                        </span>
                        <span class="rf-sub-elemento-nombre" contenteditable="true" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}" data-placement="bottom" data-toggle="popover" data-content="" data-trigger="hover">${nombre}</span>
                    </div>
                    `;
            document.querySelector(`#RutinaSemana .rf-dia[data-index="${diaIndex}"] #collapse${eleIndex} .dd-list`).appendChild(htmlStringToElement(subEleHTML));
            return ix;
        },
        newSubElementoPosEspecifica: (diaIndex, eleIndex, tipo, nombre, strategy, subEleRef)=>{
            let posPopover = CabeceraOpc.positionPopoverByDiaIndex(diaIndex);
            let ix = ++indexGlobal;
            let subEleHTML = `
                    <li class="col-md-12 rf-sub-elemento pading-hz-6" data-index="${ix}" data-type="${tipo}">
                        <span class="pull-left">
                             <i class="fa fa-plus txt-color-blueLight padding-top-1 insertar-debajo-sub" rel="tooltip" data-placement="bottom" data-original-title="Agregar pares" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}"></i>
                             <i class="fa fa-angle-right txt-color-blue sub-ele-ops padding-top-1" rel="popover" data-placement="${posPopover}" data-content="${RutinaPS.opsPopoverSubElemento(diaIndex, eleIndex, ix)}" data-html="true" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}" data-toggle="popover"></i>
                        </span>
                        <span class="rf-sub-elemento-nombre" contenteditable="true" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}" data-placement="bottom" data-toggle="popover" data-content="" data-trigger="hover">${nombre}</span>
                    </li>
                    `;
            subEleRef.insertAdjacentHTML(strategy, subEleHTML);
            return ix;
        },
        newDiaPlantilla: (objDia, posDia, onlyOne)=>{
            const resClass = onlyOne ? 'en-progreso' : 'en-progreso';//No afecta en nada
            const ix = ++indexGlobal;
            return `<article class="col-xs-12 col-sm-6 col-md-6 col-lg-6 ${resClass}" data-index="${ix}">   
                      <article class="col-xs-12 col-sm-11 col-md-11 col-lg-11 padding-10" data-index="${ix}">         
                        <div class="jarviswidget jarviswidget-color-blueLight margin-bottom-0">
                            <header role="heading" class="header-visor-dia" style="background: #454d54;">
                                <h2><span class="text-align-center badge bg-color-greenLight font-md txt-color-white padding-5">${objDia.arrIx}</span></h2>
                                    <div class="widget-toolbar hidden-phone borderless">
                                        <div class="widget-toolbar borderless">
                                            <a href="javascript:void(0);" rel="tooltip" data-placement="bottom" data-original-title="Copiar mini plantilla"><i class="fa fa-paste text-primary fa-15x rf-pl-dia-copy padding-7" data-index="${ix}" data-pos="${posDia}"></i></a>
                                        </div>
                                        <div class="widget-toolbar borderless">
                                            <a href="javascript:void(0);" rel="tooltip" data-placement="bottom" data-original-title="Ir a pegar elementos compuestos seleccionados"><i class="fa fa-arrow-circle-left text-primary fa-15x rf-pl-eles-copy padding-7" data-pos="${posDia}"></i></a>
                                        </div>
                                    </div>
					        </header>
					        <div role="heading" class="padding-vertical-10">
					        <div class="col-sm-12">
					        	<i class="fa fa-clock-o fa-fw pull-left fa-15x"></i><span class="pull-left horas-totales padding-bottom-10">${parseNumberToHours(objDia.minutos)}</span>
					        	<span class="pull-right fa fa-fw txt-color-blue fa-15x margin-right-13" style="font-family: 'Open Sans',Arial,Helvetica,Sans-Serif">KM</span><span class="pull-right distancia-total padding-bottom-10 margin-right-5">${parseNumberToDecimal(objDia.distancia, 2)}</span>
					        </div>					    		
                            </div>
                            <!-- widget div-->
                            <div class="padding-0">
                                <div class="widget-body padding-o-bottom-40">
                                    <div class="panel-group smart-accordion-default rf-listas padding-5">
                                        <!-- LISTAS DEL DIA DE SEMANA -->
                                            ${RutinaSeccion.newElementosForDiaPlantilla(objDia.elementos, posDia)}
                                        <!-- END LISTAS DEL DIA DE SEMANA -->
                                    </div>									
                                </div>
                            </div>
                            <!-- end widget div -->
		                </div>
		              </article>
                    </article>`;
        },
        newElementosForDiaPlantilla: (elementos, posDia)=>{
            let elementosHTML = '';
            elementos.forEach((ele, ix)=>{
                elementosHTML+=ele.tipo==1?RutinaElementoHTML.elementoSimple(ele, posDia, ix) : RutinaElementoHTML.elementoCompuesto(ele, posDia, ix);
            });
            return elementosHTML;
        },
        newElementosFromMiniPlantilla: (elementos, diaIndex)=>{
            let elementosHTML = '';
            elementos.forEach(ele=>{
                elementosHTML+=ele.tipo==1?RutinaElementoHTML.elementoSimplePaste(ele, diaIndex) : RutinaElementoHTML.elementoCompuestoPaste(ele, diaIndex);
            });
            return elementosHTML;
        },
        newDiaObjetivo: (objetivos, diaIndex)=>{
            return `
                <div class="col-sm-12 padding-o-bottom-10">
                    <div class="input-group input-group-md">
                        <select class="form-control list-desp-objetivo" style="border-bottom: 2px solid skyblue;border-radius: 10px 0px 0px 10px!important;"><option value="0">-- Objetivo del día -- </option>${objetivos}</select>
                        <div class="input-group-btn">
                            <button onclick="DiaOpc.actualizarObjetivo(this)" class="btn btn-default" dia-index="${diaIndex}" type="button" style="border-bottom: 1px solid skyblue;border-radius: 0px 20px 20px 0px !important;"><strong><i class="fa fa-check text-success"></i></strong></button>
                        </div>
                    </div>
                </div>`
        }

    }
})();

RutinaElementoHTML = (function(){
    return {
        iconoAudio: (mediaAudio)=>{
            return `<i data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-audio fa fa-play fa-fw padding-top-3 rf-media" data-media="${mediaAudio}"></i>`
        },
        iconoAudioLp: (mediaAudio)=>{
            return `<i data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-audio fa fa-play fa-fw padding-top-1 rf-media" data-media="${mediaAudio}"></i>`
        },
        iconoVideo: (mediaVideo)=>{
            return `<i data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-video fa fa-video-camera fa-fw padding-top-3 rf-media" data-media="${mediaVideo}"></i>`
        },
        iconoVideoLp: (mediaVideo)=>{
            return `<i data-placement="bottom" rel="tooltip" data-original-title="Reproducir" class="reprod-video fa fa-video-camera fa-fw padding-top-1 rf-media" data-media="${mediaVideo}"></i>`
        },
        iconoNota: (nota)=>{
            return `${nota != '' && nota != undefined?'<i class="fa fa-minus check-nota agregar-nota" data-index="${ixs.eleIndex}" data-dia-index="${ixs.diaIndex}"></i>':''}`;
        },
        iconoNotaT: ()=>{
            return `<i class="fa fa-minus check-nota agregar-nota" data-index="ixs.eleIndex" data-dia-index="ixs.diaIndex"></i>`;
        },
        adjuntarElementos: (diaIndex, elementos)=>{
            const listaDiv = RutinaDOMQueries.getDivListaDiaByIxs({diaIndex: diaIndex});
            listaDiv.children.length==0?listaDiv.parentElement.querySelector('.inputs-init').classList.toggle('hidden'):'';
            elementos.forEach(e=>{
                listaDiv.appendChild(htmlStringToElement(e.tipo == 1 ? RutinaElementoHTML.elementoSimplePaste(e, diaIndex) : RutinaElementoHTML.elementoCompuestoPaste(e, diaIndex)));
            });
            instanciarElementosDiaTooltip(listaDiv);
            instanciarElementosDiaPopover(listaDiv);
        },
        adjuntarSubElementos: (ixs, via)=>{
            const newSubEleS = [];
            let elemento = RutinaDOMQueries.getElementoByIxs(ixs);
            const divSubEle = elemento.querySelector('.detalle-lista');
            divSubEle.children.length == 0 ? elemento.querySelector('.in-init-sub-ele').classList.toggle('hidden') : '';
            let i = 0;
            while ((elemento = elemento.previousElementSibling) != undefined) ++i;
            if(via == 1) {
                const lenNuevos = $videosElegidos.length;
                $videosElegidos.forEach(e => {
                    const objSubELe = {nombre: e[1], mediaVideo: e[2], tipo: TipoElemento.VIDEO};
                    newSubEleS.push(objSubELe);
                    divSubEle.appendChild(htmlStringToElement(RutinaElementoHTML.subElementoMedia(objSubELe, ixs.diaIndex, ixs.eleIndex)));
                });
                RutinaSet.concatSubElementos(ixs.numSem, ixs.diaIndex, (eleIndex = i), newSubEleS);
                actualizarElementoParcialBD(ixs.numSem, ixs.diaIndex, (eleIndex = i), lenNuevos);
                $videosElegidos = [];
            }else{
                const lenNuevos = $subEleElegidos.length;
                $subEleElegidos.forEach(e => {
                    const objSubELe = {nombre: e[3], nota: e[4], mediaVideo: e[5], mediaAudio: e[6], tipo: e[7]};
                    newSubEleS.push(objSubELe);
                    divSubEle.appendChild(htmlStringToElement(RutinaElementoHTML.subElementoPaste(objSubELe, ixs.diaIndex, ixs.eleIndex)));
                });
                RutinaSet.concatSubElementos(ixs.numSem, ixs.diaIndex, (eleIndex = i), newSubEleS);
                actualizarElementoParcialBD(ixs.numSem, ixs.diaIndex, (eleIndex = i), lenNuevos);
                document.querySelectorAll('#RutinaSemana .rf-sub-elemento-nombre').forEach(v=>{v.classList.remove('rf-semanario-sels');});
                $subEleElegidos = [];
            }

            instanciarSubElementosTooltip(divSubEle);
            instanciarSubElementosPopover(divSubEle);
        },
        elementoSimple:(ele, posDia, posEle)=>{
            const ess = RutinaEditor.obtenerEstilos(ele.estilos);
            let ix = ++indexGlobal;
            return `<div class="panel panel-default rf-dia-elemento ${ess.margen}" data-index="${ix}" data-type="${ele.tipo}">
                        <div class="panel-heading">
                            <h4 class="panel-title txt-color-blue">
                                <a href="javascrip:void(0);" class="${ess.header}">
                                    <i class="fa fa-lg fa-angle-down pull-right invisible"></i>
                                    <i class="fa fa-lg fa-angle-up pull-right invisible"></i>
                                    <input value="${ele.minutos}" readonly="readonly" type="number" maxlength="3" class="pull-right agregar-tiempo" data-index="${ix}" data-dia-index="" data-placement="top" rel="tooltip" data-original-title="Tiempo en minutos"/> 
                                    <span class="lista-title">
                                        <span class="pull-left">
                                            ${ele.mediaVideo != undefined?RutinaElementoHTML.iconoVideo(ele.mediaVideo):''}
                                            ${ele.mediaAudio != undefined?RutinaElementoHTML.iconoAudio(ele.mediaAudio):''}
                                        </span>
                                        <span class="rf-dia-elemento-nombre padding-10 ${ess.base}" data-index="${ix}" data-dia-index="" data-placement="bottom" data-toggle="popover" data-content="${ele.nota != undefined? ele.nota : ''}" data-trigger="hover" data-dia-pos="${posDia}" data-pos="${posEle}">${ele.nombre}</span>  
                                    </span>
                                </a>
                                ${RutinaElementoHTML.iconoNota(ele.nota)}
                            </h4>
                        </div>
                    </div>
                    `;
        },
        elementoCompuesto:(ele, posDia, posEle)=>{
            const ess = RutinaEditor.obtenerEstilos(ele.estilos);
            let ix = ++indexGlobal;
            return `<div class="panel panel-default rf-dia-elemento ${ess.margen}" data-index="${ix}" data-type="${ele.tipo}">
                        <div class="panel-heading">
                            <h4 class="panel-title txt-color-blue">
                                    <a data-toggle="collapse" class="${ess.header}" data-parent="#accordion" href="#collapse${ix}">
                                        <i class="fa fa-lg fa-angle-down pull-right text-primary"></i> 
                                        <i class="fa fa-lg fa-angle-up pull-right text-primary"></i>
                                        <input value="${ele.minutos}" readonly="readonly" type="number" maxlength="3" class="pull-right agregar-tiempo" data-index="${ix}" data-dia-index="" contenteditable="true" data-placement="top" rel="tooltip" data-original-title="Añadir tiempo en minutos"/>
                                        <span class="lista-title">
                                            <span class="pull-left">
                                                ${ele.mediaVideo != undefined?RutinaElementoHTML.iconoVideo(ele.mediaVideo):''}    
                                                ${ele.mediaAudio != undefined?RutinaElementoHTML.iconoAudio(ele.mediaAudio):''}
                                            </span>
                                            <span class="rf-dia-elemento-nombre padding-10 ${ess.base}" data-index="${ix}" data-dia-index="" data-placement="bottom" data-toggle="popover" data-content="${ele.nota != undefined? ele.nota :''}" data-trigger="hover" data-dia-pos="${posDia}" data-pos="${posEle}">${ele.nombre}</span>
                                        </span>
                                    </a>
                                    ${RutinaElementoHTML.iconoNota(ele.nota)}
                            </h4>
                        </div>
                        <div id="collapse${ix}" class="panel-collapse collapse in">
                            <div class="panel-body">
                                <div class="modulo-detalle">
                                    <div class="dd nestable">
                                        <ol class="dd-list detalle-lista">
                                            ${RutinaElementoHTML.subElementos(ele.subElementos, posDia, posEle)}
                                        </ol>
                                    </div>
                                </div>
                            </div>
                        </div>
                     </div>`;
        },
        subElementos: (subElementos, posDia, posEle)=>{
            let subElementosHTML = '';
            subElementos.forEach((sEle, pos)=>{
                let ix = ++indexGlobal;
                subElementosHTML += `
                    <div class="col-md-12 rf-sub-elemento padding-hz-t-13" data-index="${ix}" data-type="1">
                        <span class="pull-left">
                             ${sEle.mediaVideo != undefined?RutinaElementoHTML.iconoVideo(sEle.mediaVideo):''}
                             ${sEle.mediaAudio != undefined?RutinaElementoHTML.iconoAudio(sEle.mediaAudio):''}
                        </span>
                        <span class="rf-sub-elemento-nombre" data-dia-index="" data-ele-index="" data-index="${ix}" data-dia-pos="${posDia}" data-ele-pos="${posEle}" data-pos="${pos}" data-placement="bottom" data-toggle="popover" data-content="${sEle.nota != undefined? sEle.nota :''}" data-trigger="hover">${sEle.nombre}</span>
                        ${RutinaElementoHTML.iconoNota(sEle.nota)}
                    </div>
                    `;
            });
            return subElementosHTML;
        },
        elementoSimplePaste:(ele, diaIndex)=>{
            const ess = RutinaEditor.obtenerEstilos(ele.estilos);
            let posPopover = CabeceraOpc.positionPopoverByDiaIndex(diaIndex);
            let ix = ++indexGlobal;
            return `<div class="panel panel-default rf-dia-elemento ${ess.margen}" data-index="${ix}" data-type="${ele.tipo}" data-kms="${ele.distancia}">
                        <div class="panel-heading">
                            <h4 class="panel-title txt-color-blue">
                                <a href="javascrip:void(0);" class="${ess.header}">
                                    <i class="fa fa-lg fa-angle-down pull-right invisible"></i>
                                    <i class="fa fa-lg fa-angle-up pull-right invisible"></i>
                                    <input value="${ele.minutos}" type="number" maxlength="3" class="pull-right agregar-tiempo show" data-index="${ix}" data-dia-index="${diaIndex}" data-placement="top" rel="tooltip" data-original-title="Añadir tiempo en minutos"/> 
                                    <span class="lista-title">
                                        <span class="pull-left">
                                            ${ele.mediaVideo != undefined && ele.mediaVideo != ''?RutinaElementoHTML.iconoVideo(ele.mediaVideo):''}
                                            ${ele.mediaAudio != undefined && ele.mediaAudio != ''?RutinaElementoHTML.iconoAudio(ele.mediaAudio):''}
                                            <i class="fa fa-plus txt-color-blueLight padding-top-3 insertar-debajo font-xs" rel="tooltip" data-placement="bottom" data-original-title="Agregar pares" data-dia-index="${diaIndex}" data-index="${ix}"></i>
                                            <i class="fa fa-caret-up txt-color-blue ele-ops padding-top-3" rel="popover" data-placement="${posPopover}" data-content="${RutinaPS.opsPopoverElemento(diaIndex, ix)}" data-html="true" data-dia-index="${diaIndex}" data-index="${ix}" data-toggle="popover"></i>
                                        </span>
                                        <span class="rf-dia-elemento-nombre padding-10 ${ess.base}" data-index="${ix}" data-dia-index="${diaIndex}" contenteditable="true" data-placement="bottom" data-toggle="popover" data-content="${ele.nota != undefined? ele.nota : ''}" data-trigger="hover">${ele.nombre}</span>  
                                    </span>
                                </a>
                                ${RutinaElementoHTML.iconoNota(ele.nota)}
                            </h4>
                        </div>
                    </div>
                    `;
        },
        elementoCompuestoPaste:(ele, diaIndex)=>{
            const ess = RutinaEditor.obtenerEstilos(ele.estilos);
            let classInputsInitSubEle = ele.subElementos.length == 0?'':'hidden';
            let posPopover = CabeceraOpc.positionPopoverByDiaIndex(diaIndex);
            let ix = ++indexGlobal;
            return `<div class="panel panel-default rf-dia-elemento ${ess.margen}" data-index="${ix}" data-type="${ele.tipo}" data-kms="${ele.distancia}">
                        <div class="panel-heading">
                            <h4 class="panel-title txt-color-blue">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${ix}" class="${ess.header}">
                                        <i class="fa fa-lg fa-angle-down pull-right text-primary"></i> 
                                        <i class="fa fa-lg fa-angle-up pull-right text-primary"></i>
                                        <input value="${ele.minutos}" type="number" maxlength="3" class="pull-right agregar-tiempo show" data-index="${ix}" data-dia-index="${diaIndex}" contenteditable="true" data-placement="top" rel="tooltip" data-original-title="Añadir tiempo en minutos"/>
                                        <span class="lista-title">
                                            <span class="pull-left">                                         
                                                ${ele.mediaVideo != undefined && ele.mediaVideo != ''?RutinaElementoHTML.iconoVideo(ele.mediaVideo):''}
                                                ${ele.mediaAudio != undefined && ele.mediaAudio != ''?RutinaElementoHTML.iconoAudio(ele.mediaAudio):''}
                                                <i class="fa fa-plus txt-color-blueLight padding-top-3 insertar-debajo font-xs" rel="tooltip" data-placement="bottom" data-original-title="Agregar pares" data-dia-index="${diaIndex}" data-index="${ix}"></i>
                                                <i class="fa fa-caret-up txt-color-blue ele-ops padding-top-3" rel="popover" data-placement="${posPopover}" data-content="${RutinaPS.opsPopoverElemento2(diaIndex, ix)}" data-html="true" data-dia-index="${diaIndex}" data-index="${ix}" data-toggle="popover"></i>
                                            </span>
                                            <span class="rf-dia-elemento-nombre padding-10 ${ess.base}" data-index="${ix}" data-dia-index="${diaIndex}" contenteditable="true" data-placement="bottom" data-toggle="popover" data-content="${ele.nota != undefined? ele.nota :''}" data-trigger="hover">${ele.nombre}</span>
                                        </span>
                                    </a>
                                    ${RutinaElementoHTML.iconoNota(ele.nota)}
                            </h4>
                        </div>
                        <div id="collapse${ix}" class="panel-collapse collapse in">
                            <div class="panel-body">
                                <div class="smart-form"><label class="input padding-5"><input data-ele-index="${ix}" data-dia-index="${diaIndex}" class="in-sub-elemento in-init-sub-ele ${classInputsInitSubEle}" type="text" maxlength="44" placeholder=""></label></div>
                                <div class="modulo-detalle">
                                    <div class="dd nestable">
                                        <ol class="dd-list detalle-lista">
                                            ${RutinaElementoHTML.subElementosPaste(ele.subElementos, diaIndex, ix)}
                                        </ol>
                                    </div>
                                </div>
                            </div>
                        </div>
                     </div>`;
        },
        subElementosPaste: (subElementos, diaIndex, eleIndex)=>{
            let posPopover = CabeceraOpc.positionPopoverByDiaIndex(diaIndex);
            let subElementosHTML = '';
            subElementos.forEach(sEle=>{
                let ix = ++indexGlobal;
                subElementosHTML += `
                    <div class="col-md-12 rf-sub-elemento pading-hz-6" data-index="${ix}" data-type="${sEle.tipo}">
                        <span class="pull-left">
                             ${sEle.mediaVideo != undefined && sEle.mediaVideo != '' ? RutinaElementoHTML.iconoVideo(sEle.mediaVideo):''}
                             ${sEle.mediaAudio != undefined && sEle.mediaAudio != '' ? RutinaElementoHTML.iconoAudio(sEle.mediaAudio):''}
                             <i class="fa fa-plus txt-color-blueLight padding-top-1 insertar-debajo-sub" rel="tooltip" data-placement="bottom" data-original-title="Agregar pares" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}"></i>
                             <i class="fa fa-angle-right txt-color-blue sub-ele-ops padding-top-1" rel="popover" data-placement="${posPopover}" data-content="${RutinaPS.opsPopoverSubElemento(diaIndex, eleIndex, ix)}" data-html="true" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}" data-toggle="popover"></i> 
                        </span>
                        <span class="rf-sub-elemento-nombre" contenteditable="true" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}" data-placement="bottom" data-toggle="popover" data-content="${sEle.nota != undefined? sEle.nota :''}" data-trigger="hover">${sEle.nombre}</span>
                        ${RutinaElementoHTML.iconoNota(sEle.nota)}
                    </div>
                    `;
            });
            return subElementosHTML;
        },
        subElementoPaste: (sEle, diaIndex, eleIndex)=>{
            let posPopover = CabeceraOpc.positionPopoverByDiaIndex(diaIndex);
            let ix = ++indexGlobal;
            return `<div class="col-md-12 rf-sub-elemento pading-hz-6" data-index="${ix}" data-type="${sEle.tipo}">
                                <span class="pull-left">
                                     ${sEle.mediaVideo != undefined?RutinaElementoHTML.iconoVideo(sEle.mediaVideo):''}
                                     ${sEle.mediaAudio != undefined?RutinaElementoHTML.iconoAudio(sEle.mediaAudio):''}
                                     <i class="fa fa-plus txt-color-blueLight padding-top-1 insertar-debajo-sub" rel="tooltip" data-placement="bottom" data-original-title="Agregar pares" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}"></i>
                                     <i class="fa fa-angle-right txt-color-blue sub-ele-ops padding-top-1" rel="popover" data-placement="${posPopover}" data-content="${RutinaPS.opsPopoverSubElemento(diaIndex, eleIndex, ix)}" data-html="true" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}" data-toggle="popover"></i> 
                                </span>
                                <span class="rf-sub-elemento-nombre" contenteditable="true" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}" data-placement="bottom" data-toggle="popover" data-content="${sEle.nota != undefined? sEle.nota :''}" data-trigger="hover">${sEle.nombre}</span>
                                ${RutinaElementoHTML.iconoNota(sEle.nota)}
                            </div>`;
        },
        subElementoMedia: (sEle, diaIndex, eleIndex)=>{
            let posPopover = CabeceraOpc.positionPopoverByDiaIndex(diaIndex);
            let ix = ++indexGlobal;
            return `<div class="col-md-12 rf-sub-elemento pading-hz-6" data-index="${ix}" data-type="${sEle.tipo}">
                        <span class="pull-left">
                             ${sEle.mediaVideo != undefined?RutinaElementoHTML.iconoVideo(sEle.mediaVideo):''}
                             <i class="fa fa-plus txt-color-blueLight padding-top-1 insertar-debajo-sub" rel="tooltip" data-placement="bottom" data-original-title="Agregar pares" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}"></i>
                             <i class="fa fa-angle-right txt-color-blue sub-ele-ops padding-top-1" rel="popover" data-placement="${posPopover}" data-content="${RutinaPS.opsPopoverSubElemento(diaIndex, eleIndex, ix)}" data-html="true" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}" data-toggle="popover"></i> 
                        </span>
                        <span class="rf-sub-elemento-nombre" contenteditable="true" data-dia-index="${diaIndex}" data-ele-index="${eleIndex}" data-index="${ix}" data-placement="bottom" data-toggle="popover" data-content="" data-trigger="hover">${sEle.nombre}</span>
                    </div>
                    `;
        },
    }
})();

RutinaDiaHTML = (function(){
    return {
        full: (elementos, diaIndex, init, flagDescanso)=>{//init se usa para cuando se recrea la semana desde la instancia del objeto Rutina
            if(flagDescanso){
                return `<div class="widget-body padding-o-bottom-40"><img src="${_ctx}img/dia-libre.png" style="max-width: 90%; text-align: center;"></div>`;
            }else {
                return `<div class="widget-body padding-o-bottom-40">
                            <div class="container-fluid form-group margin-bottom-10 padding-5 inputs-init ${init == undefined ? 'hidden' : ''}">
                                <div class="smart-form">
                                    <div class="form-group">
                                            <label class="input col-md-6 col-sm-12 col-xs-12">
                                                <input class="in-ele-dia-1 in-init-ele" type="text" maxlength="121" placeholder="" data-dia-index="${diaIndex}">
                                                </label>
                                            <label class="input col-md-6 col-sm-12 col-xs-12">
                                                <input class="in-ele-dia-2 in-init-ele" type="text" maxlength="121" placeholder="" data-dia-index="${diaIndex}">
                                                </label>
                                            <em class="txt-color-redLight" id="msg-val-${diaIndex}"></em>
                                    </div>
                                </div>
                            </div>
                            <!-- smart-accordion-default -->
                            <div class="panel-group smart-accordion-default rf-listas padding-5">
                                <!-- LISTAS DEL DIA DE SEMANA -->
                                    ${RutinaSeccion.newElementosFromMiniPlantilla(elementos, diaIndex)}
                                <!-- END LISTAS DEL DIA DE SEMANA -->
                            </div>
                        </div>`
            }
        },
    }
})();

//RutinaParteSeccion
RutinaPS = (function () {
    return {
        opsPopoverElemento: (diaIndex, eleIndex)=>{
            return `
                <div class='row ops-r'>
                    <i rel='tooltip' data-original-title='Agregar nota' data-trigger='hover' class='fa fa-sticky-note agregar-nota txt-color-yellow padding-5' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Eliminar audio' data-trigger='hover' class='fa fa-music txt-color-red padding-5 trash-audio' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Eliminar video' data-trigger='hover' class='fa fa-video-camera txt-color-red padding-5 trash-video' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Insertar encima del elemento' data-trigger='hover' class='fa fa-arrow-up txt-color-black padding-5 insertar-encima' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Reemplazar elemento' data-trigger='hover' class='fa fa-refresh txt-color-green padding-5' data-index='${eleIndex}'></i>
                    <span rel='tooltip' data-original-title='Agregar KM's data-trigger='hover' class='txt-color-black padding-5 agregar-kms' data-index='${eleIndex}' data-dia-index='${diaIndex}'>KM</span>
                    <i rel='tooltip' data-original-title='Eliminar elemento' data-trigger='hover' class='fa fa-trash-o txt-color-redLight padding-5 trash-elemento' data-dia-index='${diaIndex}' data-index='${eleIndex}'></i>
                </div>
            `;
        },
        opsPopoverElemento2: (diaIndex, eleIndex)=>{
            return `
                <div class='row ops-r'>
                    <i rel='tooltip' data-original-title='Agregar nota' data-trigger='hover' class='fa fa-sticky-note agregar-nota txt-color-yellow padding-5' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Eliminar audio' data-trigger='hover' class='fa fa-music txt-color-red padding-5 trash-audio' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Eliminar video' data-trigger='hover' class='fa fa-video-camera txt-color-red padding-5 trash-video' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Insertar encima del elemento' data-trigger='hover' class='fa fa-arrow-up txt-color-black padding-5 insertar-encima' data-index='${eleIndex}' data-dia-index='${diaIndex}'></i>
                    <i rel='tooltip' data-original-title='Reemplazar elemento' data-trigger='hover' class='fa fa-refresh txt-color-green padding-5' data-index='${eleIndex}'></i>
                    <i rel='tooltip' data-original-title='Agregar varios' data-trigger='hover' class='fa fa-angle-double-down padding-5 varios-media' data-dia-index='${diaIndex}' data-index='${eleIndex}'></i>
                    <span rel='tooltip' data-original-title='Agregar KM's data-trigger='hover' class='txt-color-black padding-5 agregar-kms' data-index='${eleIndex}' data-dia-index='${diaIndex}'>KM</span>
                    <i rel='tooltip' data-original-title='Eliminar elemento' data-placement='top' data-dia-index='${diaIndex}' data-index='${eleIndex}' class='fa fa-trash padding-5 txt-color-redLight trash-elemento'></i>
                </div>
            `;
        },
        opsPopoverSubElemento: (diaIndex, eleIndex, subEleIndex)=>{
            return `
                <div class='row'>
                    <i rel='tooltip' data-original-title='Agregar nota' data-trigger='hover' class='fa fa-sticky-note agregar-nota-sbe txt-color-yellow padding-5' data-dia-index='${diaIndex}' data-ele-index='${eleIndex}' data-index='${subEleIndex}'></i>
                    <i rel='tooltip' data-original-title='Eliminar audio' data-trigger='hover' class='fa fa-music txt-color-red padding-5 trash-audio-sub' data-dia-index='${diaIndex}' data-ele-index='${eleIndex}' data-index='${subEleIndex}'></i>
                    <i rel='tooltip' data-original-title='Eliminar video' data-trigger='hover' class='fa fa-video-camera txt-color-red padding-5 trash-video-sub' data-dia-index='${diaIndex}' data-ele-index='${eleIndex}' data-index='${subEleIndex}'></i>
                    <i rel='tooltip' data-original-title='Insertar encima del sub elemento' data-trigger='hover' class='fa fa-arrow-up txt-color-black padding-5 insertar-encima-sub' data-dia-index='${diaIndex}' data-ele-index='${eleIndex}' data-index='${subEleIndex}'></i>
                    <i rel='tooltip' data-original-title='Reemplazar sub elemento' data-trigger='hover' class='fa fa-refresh txt-color-green padding-5' data-dia-index='${diaIndex}' data-ele-index='${eleIndex}' data-index='${subEleIndex}'></i>
                    <i rel='tooltip' data-original-title='Eliminar sub elemento' data-trigger='hover' class='fa fa-trash-o txt-color-redLight padding-5 trash-sub-elemento' data-dia-index='${diaIndex}' data-ele-index='${eleIndex}' data-index='${subEleIndex}'></i>
                </div>
            `
        },
        videoMiniatura: (mediaVideo)=>{
            return `
                <div class="" style="text-align: center;">
                        <video id="somevid" preload="none" controls="controls" autoplay="" controlslist="nodownload" width="100%" height="100%" style="
                            width: 80%;
                            margin: 15px;
                            border: 5px solid #e8f2f3;
                            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                            <source id="VideoReproduccion" src="https://s3-us-west-2.amazonaws.com/rf-media-rutina/video${mediaVideo}" type="video/mp4">
                        </video>
                </div>`
        }
    }
})();

//Indicadores de tiempo y distancia
Indicadores = (function(){
    return {
        instanciarIndicador0: ()=>{
            const intensidad = $rutina.control.intensidades[Number($semActual.textContent)-1];
            const ind = intensidad < 61 ? "B" : intensidad < 85 ? "M" : "A";
            document.querySelector('#IndicadorIntensidad').innerHTML = `<a href="javascript:void(0);"><i rel="tooltip" class="fa fa-2x txt-color-red" data-original-title="${intensidad} %" style="font-weight: bold;">${ind}</i></a>`;
        },
        instanciarIndicadores1: ()=>{
            const raw = `<a href="javascript:void(0);"><i id="IconIndicador1" rel="popover" data-toggle="popover" data-placement="right" data-html="true" data-content="" class="fa fa-heartbeat txt-color-red fa-2x abrir-indicador-1"></i></a>`;
            document.querySelector('#Indicadores1').innerHTML = raw;
        },
        abrirIndicador1: (metricas)=>{
            const iconIndi1 = document.querySelector('#IconIndicador1');
            const raw = `
                <div class="container-fluid padding-0 its-indicador-1">
                    <div class="col-md-6 col-sm-6 col-xs-6">
                        <div class="row padding-5 text-align-center">
                            <span class="txt-color-blue"><b>Paso</b></span>
                        </div>
                        ${Indicadores.indicador1Body(metricas, 1)}
                    </div>
                    <div class="col-md-6 col-sm-6 col-xs-6">
                        <div class="row padding-5 text-align-center">
                            <span class="txt-color-red"><b>Pulso</b></span>
                        </div>
                        ${Indicadores.indicador1Body(metricas, 2)}
                    </div>
                </div>
            `
            iconIndi1.setAttribute('data-content', raw);
            $('#IconIndicador1').popover('show');
        },
        indicador1Body: (metricas, t)=>{
            const iteraciones = metricas.length;
            let raw = '';
            const claseTipo = t == 2 ? 'txt-color-red':'';
            const bOpacidad = 1/iteraciones;
            for(let i=0; i<iteraciones;i++){
                raw += `<div class="col-md-12 col-sm-12 col-xs-12 padding-o-bottom-5 text-align-center">
                            <div class="col-md-3 col-sm-3 col-xs-3 padding-0 rf-n">
                                <a href="javascript:void(0);">${metricas[i].nombre}</a>
                            </div>
                            <div class="col-md-2 col-sm-2 col-xs-2 padding-0">
                                <a href="javascript:void(0);"><i class="fa fa-long-arrow-up ${claseTipo}" style="opacity: ${bOpacidad*(i+1)}"></i></a>
                            </div>
                            <div class="col-md-7 col-sm-7 col-xs-7 padding-0">
                                ${t == 1 ?
                    `<b> ${i == 0 ? metricas[i].indicadores.max.substr(3) : i == 6 ? metricas[i].indicadores.min.substr(3): metricas[i].indicadores.max.substr(3) +' - '+ metricas[i].indicadores.min.substr(3)}</b>`
                    :`<b> ${metricas[i].min} - ${metricas[i].max}</b>`
                    }
                            </div>                            
                        </div>`
            }
            return raw;
        },
        instanciarIndicadores2: ()=>{
            const raw = `<a href="javascript:void(0);"><i id="IconIndicador2" rel="popover" data-toggle="popover" data-placement="right" data-html="true" data-content="" class="fa fa-bar-chart-o fa-2x txt-color-orange abrir-indicador-2"></i></a>`;
            document.querySelector('#Indicadores2').innerHTML = raw;
        },
        instanciarKilometrajes: ()=>{
            const k = RutinaGet.getKilometrajes();
            const raw = `<hr class="margin-0" style="margin: 0px -10px !important"/>
                        <div>
                            <div class="bg-color-grayDark text-align-center txt-color-white"><h6>K.M.C.</h6></div>
                            <div class="txt-color-blueLight text-align-center"><span><b class="kcal">${k.kcal} kcal</b></span></div>
                            <div class="bg-color-grayDark text-align-center txt-color-white"><h6>K.M.M.</h6></div>
                            <div class="txt-color-blueLight text-align-center"><span><b>${k.kmMacro} K.M.</b></span></div>
                            <div class="bg-color-grayDark text-align-center txt-color-white"><h6>K.M.P.</h6></div>
                            <div class="txt-color-blueLight text-align-center"><span><b class="km-planificado">${k.kmPlanificado} K.M.</b></span></div>
                        </div>
                        <hr class="margin-0" style="margin: 0px -10px !important"/>`;
            document.querySelector('#tabRutina #OpsLaterales #Kilometrajes').innerHTML = raw;
        },
        instanciarPorcentajeAvance: ()=>{
            const porcAvance = document.querySelector('#PorcentajeAvanceSemana');
            const porcActual = Number($rutina.control.avanceSemanas[Number($semActual.textContent)-1]);
            porcAvance.textContent = Number($rutina.control.avanceSemanas[Number($semActual.textContent)-1]) + "%";
            if(porcActual < 80) {
                porcAvance.classList.add('txt-color-redLight');
                porcAvance.classList.remove('txt-color-greenLight');
            }else{
                porcAvance.classList.add('txt-color-greenLight');
                porcAvance.classList.remove('txt-color-redLight');
            }
        },
        abrirIndicador2: (metricas)=>{
            const iconIndi2 = document.querySelector('#IconIndicador2');
            const raw = `
                <div class="container-fluid padding-0 its-indicador-1">
                    ${Indicadores.indicador2Body(Indicadores.calcTiemposTotales(metricas))}
                </div>
            `
            iconIndi2.setAttribute('data-content', raw);
            $('#IconIndicador2').popover('show');
        },
        indicador2Body: (metricas)=>{
            let raw = '<div class="col-md-12 col-sm-12 col-xs-12">';
            for(let i=0; i<metricas.length;i++){
                raw += `<div class="col-md-3 col-sm-3 col-xs-3 padding-7">
                            <div class="row padding-5 text-align-center">
                                <span class="txt-color-blue font-md" style="border-bottom: 2px solid lightgrey; display: block;"><b>${BaseCalculo.ofMetricasBase[i].n}</b></span>
                                <span class="font-md">${metricas[i].p}</span><br/>
                                <span class="txt-color-orange font-md">${metricas[i].tt}</span><br/>
                            </div>
                        </div>`
                if((i+1)%4 == 0 && (i+1) == metricas.length){
                    raw+='</div>';
                }else if((i+1)%4 == 0){
                    raw+='</div><div class="col-md-12 col-sm-12 col-xs-12">';
                }
            }
            return raw;
        },
        calcTiemposTotales: (metricas)=>{
            const wex = ["200m", "400m", "800m", "1KM","10KM","15KM","21KM","42KM"];
            metricas = metricas.map((v,i)=>{return {p: v.parcial, s: v.parcial.toSeconds(), m: wex[i]}});
            metricas.forEach((v,i)=>{
                if(i>3)
                    v.tt = String(v.s*Number(v.m.slice(0, -2))).toHHMMSSM()
                else
                    v.tt = v.p
            });
            return metricas;
        },
        actualizarKilometrajes: ()=>{
            const k = RutinaGet.getKilometrajes();
            const kHTML = document.querySelector('#tabRutina #OpsLaterales #Kilometrajes');
            if(kHTML.querySelector('.kcal') != null){
                kHTML.querySelector('.kcal').textContent = k.kcal+" kcal";
                kHTML.querySelector('.km-planificado').textContent = k.kmPlanificado+" K.M.";
            }
        },
        actualizarKilometrajesLessDiaIndex: (diaIndex)=>{
            const k = RutinaGet.getKilometrajesLessDiaIndex(diaIndex);
            const kHTML = document.querySelector('#tabRutina #OpsLaterales #Kilometrajes');
            kHTML.querySelector('.kcal').textContent = k.kcal+" kcal";
            kHTML.querySelector('.km-planificado').textContent = k.kmPlanificado+" K.M.";
        }
    }
})();

RutinaValidator = (function(){
    return {
        esZ: (nombre)=>{
            return nombre == "Z1" || nombre == "Z2" || nombre == "Z3" || nombre == "Z4" || nombre == "Z5" || nombre == "Z6" || nombre == "Z7";
        },
        hayZFromEle: (elemento)=>{
            let z = false;
            elemento.querySelectorAll('.rf-sub-elemento-nombre').forEach(v=>{
                if(RutinaValidator.esZ(v.textContent.trim().toUpperCase()))
                    z = true;
            });
            return z;
        },
        hayZFromEleNombre: (elemento)=>{
            return RutinaValidator.esZ(elemento.querySelector('.rf-dia-elemento-nombre').textContent.trim().toUpperCase())
        },
        hayZFromSubEle: (elemento)=>{
            let z = false;
            if(RutinaValidator.esZ(elemento.querySelector('.rf-dia-elemento-nombre').textContent)){
                z = true;
            }else{
                elemento.querySelectorAll('.rf-sub-elemento-nombre').forEach(v=>{
                    if(RutinaValidator.esZ(v.textContent.trim().toUpperCase()))
                        z = true;
                });
            }
            return z;
        },
        getZFromSubEle: (elemento)=>{
            return Array.from(elemento.querySelectorAll('.rf-sub-elemento-nombre')).filter(v=>{
                return RutinaValidator.esZ(v.textContent.trim().toUpperCase())
            });
        }
    }
})();

