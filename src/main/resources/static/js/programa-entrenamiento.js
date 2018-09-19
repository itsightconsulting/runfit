class ProgramaEntrenamiento {

    constructor(fechaInicio, meses) {
        this.fechaInicio = fechaInicio;
        this.fechaFin;
        this.meses = meses;
        this.anios;
        this.totalSemanas;
        this.dias;
        this.diasExcedentes;
        this.semanas = [];
    }

    init(tipo) {
        if(tipo == undefined){//Rutina usando el primer constructor
            this.calcularDatosIniciales();
            this.construirPrograma(this.semanas[0], 0);
        }else{//Rutina usando el segundo contructor
            this.generarRutinaBase();
        }
        return this;
    }

    calcularDatosIniciales() {
        let cantMeses = Number(this.meses);
        let fechaInicio = this.fechaInicio;
        //Devuelve YYYY-MM-DD
        let fechaArray = dateToArrayFormat(fechaInicio);
        fechaArray.forEach((v, i) => {
            fechaArray[i] = Number(fechaArray[i])
        });
        let totalMeses = fechaArray[1] + cantMeses;

        if (totalMeses < 12) {
            fechaArray[1] = fechaArray[1] + cantMeses;
        } else {
            if (totalMeses == 12) {
                fechaArray[0] += 1;
                fechaArray[1] = 0;
            } else {
                if (cantMeses < 13) {
                    fechaArray[0] += 1;
                    fechaArray[1] == (fechaArray[1] + cantMeses) % 11;
                } else {
                    let sumaAnios = Math.floor(cantMeses / 12);
                    let mesesExcedentes = cantMeses % 12;
                    fechaArray[0] += sumaAnios;
                    if (mesesExcedentes != 0) {
                        if (fechaArray[1] + mesesExcedentes < 12) {
                            fechaArray[1] = fechaArray[1] + mesesExcedentes;
                        } else {
                            if (fechaArray[1] + mesesExcedentes == 12) {
                                fechaArray[0] += 1;
                                fechaArray[1] = (fechaArray[1] + mesesExcedentes) % 12;
                            } else {
                                fechaArray[0] += 1;
                                fechaArray[1] == (fechaArray[1] + mesesExcedentes) % 12;
                            }
                        }
                    }

                }
            }
        }

        this.fechaFin = new Date(fechaArray[0], fechaArray[1], fechaArray[2]);
        this.anios = Math.floor(cantMeses / 12);
        this.dias = moment(this.fechaFin).diff(this.fechaInicio, 'days') + 1;
        let diaSemana = this.fechaInicio.getDay();
        let uDiaSemana = this.fechaFin.getDay();
        //Se suma mas 3 por los días domingo,lunes y martes que tiene un valor de 0,1,2 respectivamente
        this.diasExcedentes = diaSemana > 2 ? (6 - diaSemana)+3: diaSemana < 2 ? 2 - diaSemana : 0;
        let excedentesIniciales = this.diasExcedentes;
        this.diasExcedentes += uDiaSemana > 1 ? (uDiaSemana - 1) : uDiaSemana < 1 ? 6 - uDiaSemana : 0;
        let excedentesFinales = this.diasExcedentes - excedentesIniciales;
        let fechaTemp;
        //Calculando las semanas esta vez descontando los dias totales excedentes que haiga habido según sea el caso
        this.totalSemanas = (this.dias-this.diasExcedentes)/7;
        if (excedentesIniciales > 0){
        //Se resta uno para no interferir con el día inicial de la proxima semana
            this.semanas.push(new Semana(this.fechaInicio, sumarDiasAespecificoDia(this.fechaInicio, excedentesIniciales - 1), false, true));
        }

        for (let i = 0; i < this.totalSemanas; i++) {
            if (i == 0)//Con esto nos aseguramos que solo en la primera iteración se añada los dias excedentesIniciales
                fechaTemp = sumarDiasAespecificoDia(this.fechaInicio, excedentesIniciales);
            let week = new Object();
            week.fechaInicio = fechaTemp;
            //En este punto le sumamos la variable temporal de fecha es modificada con el fin de obtener la fecha fin de la semana
            fechaTemp = sumarDiasAespecificoDia(fechaTemp, 6);
            week.fechaFin = fechaTemp;
            //Aquí le sumamos un día a la fechaTemporal para que cuando comience la nueva iteración esta reinicie su ciclo donde empezo(día martes)
            fechaTemp = sumarDiasAespecificoDia(fechaTemp, 1);
            this.semanas.push(new Semana(week.fechaInicio, week.fechaFin, true, false));
        }

        if (excedentesFinales > 0) {
            let week = new Object();
            week.fechaInicio = sumarDiasAespecificoDia(this.semanas[this.semanas.length - 1].fechaFin, 1);
            //Se resta uno para no interferir con el día inicial de la proxima semana
            week.fechaFin = sumarDiasAespecificoDia(week.fechaInicio, excedentesFinales - 1);
            //Esta semana tambien es una semana con días excedentes pero por conveniencia le colocamos el último flag en false
            this.semanas.push(new Semana(week.fechaInicio, week.fechaFin, false, false));
            this.totalSemanas +=1;//Representa una semana incompleta
        }

        //No se le añade en el if de arriba para evitar que el for solo itere con las semanas full y no con las incompletas(Como es el caso)
        if(excedentesIniciales>0){
            this.totalSemanas +=1;//Representa una semana incompleta
        }
    }

    generarRutinaBase() {
        this.meses = 0;
        this.semanas = [];
        this.anios = 0;
        this.fechaInicio = parseFromStringToDate(moment(this.fechaInicio).add(0, 'd').format('YYYY-MM-DD'));
        let diaSemana = this.fechaInicio.getDay();
        //dias para Completar Semana
        let dCS = (diaSemana > 1 ? (6 - diaSemana)+1: diaSemana < 1 ? 1 - diaSemana : 0);
        if(dCS == 0 && diaSemana == 1)
            dCS = 6;
        else if(dCS == 1 && diaSemana == 0)
            dCS = 0;
        this.fechaFin = parseFromStringToDate(moment(this.fechaInicio).add(dCS, 'd').format('YYYY-MM-DD'))
        // +1 por el día que no se conto(fechaInicio)
        this.dias = dCS+1;
        //Se suma mas 3 por los días domingo,lunes y martes que tiene un valor de 0,1,2 respectivamente
        this.diasExcedentes = diaSemana > 1 ? (6 - diaSemana)+2: diaSemana < 1 ? 1 - diaSemana : 0;
        this.totalSemanas = 1;
        if (this.diasExcedentes > 0){
            //Se resta uno para no interferir con el día inicial de la proxima semana
            this.semanas.push(new Semana(this.fechaInicio, this.fechaFin, false, true));
        }else{
            this.semanas.push(new Semana(this.fechaInicio, this.fechaFin, true, false));
        }
    }

    construirPrograma(sem, sIndex) {
        //Inicialmente generamos solo la primera semana de todo el plan de entrenamiento
        let semana = sem;
        let rawDias = '';
        let diasRestantes = [];
        let rawDiasRestantes = '';
        let flagSemanaInicialNoFull = false;
        //Generando dtoHHMMSSe acuerdo si la rutina consta de 7 dias o de solo 1 día

        //Obteniendo días totales de la semana(simulacion)
        //Primer día al momento de genrar la rutina semanal será martes por regla de negocio

        if (!semana.flagFull) {

            let vacios = 7 - semana.dias.length;
            if(semana.flagSemanaExcedente){
                flagSemanaInicialNoFull = true;
                for (let i = 0; i < vacios; i++) {
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
                v.literal = dias[v.getDay()];
                v.dia = v.getDate();
                rawDiasRestantes +=
                    `<article class="col-xs-12 col-sm-3 col-md-3 col-lg-3 rf-dia-off" >            
									<div class="jarviswidget jarviswidget-color-blueDark">
										<header role="heading">
								            <h2>${v.literal} ${v.dia}</h2>
								            ${flagSemanaInicialNoFull ?
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
								                    <h1 class="text-align-center">${!flagSemanaInicialNoFull ? `<img class="max-wd" src="${_ctx}img/finish_strong.png"/>` : `<img class="max-wd" src="${_ctx}img/empieza_reto.jpg"/>`}</h1>
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
            rawDias +=
                `<article class="col-xs-12 col-sm-3 col-md-3 col-lg-3 rf-dia" data-index="${i}" data-fecha="${v.fechaCorta()}" >            
									<div class="jarviswidget jarviswidget-color-blueDark" tabindex='${i}'>
										<header role="heading">
								            <span class="widget-icon"> <i class="fa fa-child fa-15x"></i> </span>
								            <h2>${v.literal} ${v.dia}</h2>
								            <div class="widget-toolbar hidden-phone borderless">
								            <div class="widget-toolbar borderless"><a href="javascript:void(0);" style="display: none;"  rel="tooltip" data-placement="bottom" data-original-title="Pegar mini plantillas"><i  class="fa fa-refresh text-warning fa-15x"></i></a><a href="javascript:void(0);" rel="tooltip" data-placement="bottom" data-original-title="Pegar mini Plantilla"><i class="fa fa-paste text-primary fa-15x pegar-mini" data-index="${i}"></i></a></div>
								            <div class="widget-toolbar borderless"><a href="javascript:void(0);" style="display: none;"  rel="tooltip" data-placement="bottom" data-original-title="Actualizar rutina del día como culminada"><i  class="fa fa-refresh text-warning fa-15x"></i></a><a href="javascript:void(0);" rel="tooltip" data-placement="bottom" data-original-title="Marca el día como día de descanso"><i class="fa fa-refresh text-warning fa-15x marcar-descanso" data-index="${i}"></i></a></div>
								            </div>
								        </header>
								        <div role="heading" style="background: white !important;">
								            <div class="col-sm-12">
								            	<span class="pull-left"><i class="fa fa-clock-o fa-fw"></i>Tiempo</span>
								            	<span class="pull-right">Kilómetros</span>
								            </div>					    		
								        </div>
								        <div role="heading" style="color:white;background: #2C3742!important;border-color: #2C3742!important;">
								            <div class="col-sm-12">
								            	<span class="pull-left">00:00:00</span>
								            	<span class="pull-right">00.00</span>
								            </div>					    		
								        </div>
								        <!-- widget div-->
								        <div class="padding-0">
								            <!-- widget content -->	
								            <div class="widget-body">
								                <div class="form-group">
                                                    <div class="smart-form">
                                                            <label class="input padding-5">
                                                                <input class="lst-dia" type="text" maxlength="121" placeholder="Ingresa nombre de lista"  data-dia-index='${i}'>
                                                                <em class="txt-color-redLight" id="msg-val-${i}" class="invalid"></em>
                                                            </label>
                                                    </div>
                                                </div>
								                <!-- smart-accordion-default -->
								                <div class="panel-group smart-accordion-default rf-listas padding-5">
								                    <!-- LISTAS DEL DIA DE SEMANA -->
								                    <!-- END LISTAS DEL DIA DE SEMANA -->
								                </div>													
								            </div>
								            <!-- end widget content -->											
								        </div>
								        <!-- end widget div -->
									</div>
								</article>`;
        });

        let RutinaSemanaDiv = document.createElement('div');
        //Ccn esta condición nos aseguramos que siempre que la primera semana de toda la rutina empiece por un día diferente a martes, los primeros días vacíos vayan al inicio
        if (!semana.flagFull && semana.flagSemanaExcedente){
            rawDiasRestantes += rawDias;
            RutinaSemanaDiv.innerHTML = rawDiasRestantes;
        }else{
            rawDias += rawDiasRestantes;
            RutinaSemanaDiv.innerHTML = rawDias;
        }

        $rutina.innerHTML = '';
        $rutina.append(RutinaSemanaDiv);
        //Seteamos un ancho especifico de acuerdo a los 7 días de la semana para poder hacer un scrolling en X
        $(".of_carousel").width($(".of_carousel article").length * $(".of_carousel article")[0].clientWidth) + $(".of_carousel article").length * $(".of_carousel article")[0].clientWidth * 2;
        $('#RutinaSemana').addClass('rf-semana');
        $('#RutinaSemana').attr('data-index', sIndex);

        //Seteando el mes y el año en el encabezado de la rutina
        $('#MesAnio').text(meses[semana.dias[0].fecha.getMonth()] +" - "+ semana.dias[0].fecha.getFullYear());
    }

    construirSemanaConDataAlmacenada(sem, sIndex){
        let semana = sem;
        let rawDias = '';
        let diasRestantes = [];
        let rawDiasRestantes = '';
        let flagSemanaInicialNoFull = false;
        //Generando dtoHHMMSSe acuerdo si la rutina consta de 7 dias o de solo 1 día

        //Obteniendo días totales de la semana(simulacion)
        //Primer día al momento de genrar la rutina semanal será martes por regla de negocio

        if (!semana.flagFull) {

            let vacios = 7 - semana.dias.length;
            if(semana.flagSemanaExcedente){
                flagSemanaInicialNoFull = true;
                for (let i = 0; i < vacios; i++) {
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
                v.literal = dias[v.getDay()];
                v.dia = v.getDate();
                rawDiasRestantes +=
                                `<article class="col-xs-12 col-sm-3 col-md-3 col-lg-3 rf-dia-off" >            
									<div class="jarviswidget jarviswidget-color-blueDark">
										<header role="heading">
								            <h2>${v.literal} ${v.dia}</h2>
								            ${flagSemanaInicialNoFull ?
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
								                    <h1 class="text-align-center">${!flagSemanaInicialNoFull ? `<img class="max-wd" src="${_ctx}img/finish_strong.png"/>` : `<img class="max-wd" src="${_ctx}img/empieza_reto.jpg"/>`}</h1>
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

        $programa.semanas[sIndex].dias.forEach((v, i) => {
            rawDias +=
                `<article class="col-xs-12 col-sm-3 col-md-3 col-lg-3 rf-dia" data-index="${i}" data-fecha="${v.fechaCorta()}" >            
									<div class="jarviswidget jarviswidget-color-blueDark" tabindex='${i}'>
										<header role="heading">
								            <span class="widget-icon"> <i class="fa fa-child fa-15x"></i> </span>
								            <h2>${v.literal} ${v.dia}</h2>
								            <div class="widget-toolbar hidden-phone borderless">
								            <div class="widget-toolbar borderless"><a href="javascript:void(0);" style="display: none;"  rel="tooltip" data-placement="bottom" data-original-title="Pegar mini plantillas"><i  class="fa fa-refresh text-warning fa-15x"></i></a><a href="javascript:void(0);" rel="tooltip" data-placement="bottom" data-original-title="Pegar mini Plantilla"><i class="fa fa-paste text-primary fa-15x pegar-mini" data-index="${i}"></i></a></div>
								            <div class="widget-toolbar borderless"><a href="javascript:void(0);" style="display: none;"  rel="tooltip" data-placement="bottom" data-original-title="Actualizar rutina del día como culminada"><i  class="fa fa-refresh text-warning fa-15x"></i></a><a href="javascript:void(0);" rel="tooltip" data-placement="bottom" data-original-title="Marca el día como día de descanso"><i class="fa fa-refresh text-warning fa-15x marcar-descanso" data-index="${i}"></i></a></div>
								            </div>
								        </header>
								        <div role="heading" style="background: white !important;">
								            <div class="col-sm-12">
								            	<span class="pull-left"><i class="fa fa-clock-o fa-fw"></i>Tiempo</span>
								            	<span class="pull-right">Kilómetros</span>
								            </div>					    		
								        </div>
								        <div role="heading" style="color:white;background: #2C3742!important;border-color: #2C3742!important;">
								            <div class="col-sm-12">
								            	<span class="pull-left">00:00:00</span>
								            	<span class="pull-right">00.00</span>
								            </div>					    		
								        </div>
								        <!-- widget div-->
								        <div class="padding-0">
								            <!-- widget content -->	
								            <div class="widget-body">
								                <div class="form-group">
                                                    <div class="smart-form">
                                                            <label class="input padding-5">
                                                                <input class="lst-dia" type="text" maxlength="121" placeholder="Ingresa nombre de lista"  data-dia-index='${i}'>
                                                                <em class="txt-color-redLight" id="msg-val-${i}" class="invalid"></em>
                                                            </label>
                                                    </div>
                                                </div>
								                <!-- smart-accordion-default -->
								                <div class="panel-group smart-accordion-default rf-listas padding-5">
								                    <!-- LISTAS DEL DIA DE SEMANA -->
								                        ${v.listas.map(l=>`
								                            ${obtenerSimpleListaRaw(l.nombre, l.elementos, i).outerHTML}
								                        `.trim()).join('')}
								                        
								                    <!-- END LISTAS DEL DIA DE SEMANA -->
								                </div>													
								            </div>
								            <!-- end widget content -->											
								        </div>
								        <!-- end widget div -->
									</div>
								</article>`;
        });

        let RutinaSemanaDiv = document.createElement('div');
        //Ccn esta condición nos aseguramos que siempre que la primera semana de toda la rutina empiece por un día diferente a martes, los primeros días vacíos vayan al inicio
        if (!semana.flagFull && semana.flagSemanaExcedente){
            rawDiasRestantes += rawDias;
            RutinaSemanaDiv.innerHTML = rawDiasRestantes;
        }else{
            rawDias += rawDiasRestantes;
            RutinaSemanaDiv.innerHTML = rawDias;
        }

        $rutina.innerHTML = '';
        $rutina.append(RutinaSemanaDiv);
        //Seteamos un ancho especifico de acuerdo a los 7 días de la semana para poder hacer un scrolling en X
        $(".of_carousel").width($(".of_carousel article").length * $(".of_carousel article")[0].clientWidth) + $(".of_carousel article").length * $(".of_carousel article")[0].clientWidth * 2;
        $('#RutinaSemana').addClass('rf-semana');
        $('#RutinaSemana').attr('data-index', sIndex);

        //Seteando el mes y el año en el encabezado de la rutina
        $('#MesAnio').text(meses[semana.dias[0].fecha.getMonth()] +" - "+ semana.dias[0].fecha.getFullYear());
    }
}

class Semana{
    constructor(fechaInicio, fechaFin, flagFull, flagSemanaExcedente){
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.flagFull = flagFull;
        this.flagSemanaExcedente = flagSemanaExcedente;
        this.dias = this.instanciarDias();
    }

    instanciarDias(){
        let dias = [];
        let iteraciones = moment(this.fechaFin).diff(this.fechaInicio, 'days') + 1;
        for (let i = 0; i < iteraciones; i++) {
            let d  = new Dia(sumarDiasAespecificoDia(this.fechaInicio, i));
            dias.push(d);
        }
        return dias;
    }
}

class Dia{
    constructor(fechaDia){
        this.fecha = fechaDia;
        this.dia = fechaDia.getDate();
        this.literal = this.determinarDiaLiteral();
        this.flagDescanso = false;
        this.listas = [];
    }

    determinarDiaLiteral(){
        return dias[this.fecha.getDay()];
    }

    fechaCorta(){
        return moment(this.fecha).format('YYYY-MM-DD');
    }
}

class DiaLista{
    constructor(nombre, index, dia){
        this.nombre = nombre;
        this.id = index;
        this.fechaDia = dia;
        this.elementos = [];
        this.estilos = [];
    }
}

class ElementoLista{
    constructor(nombre, duracion, media, tipo){
        this.nombre = nombre;
        this.duracion = duracion;
        this.media = media;
        this.estilos = [];
        this.tipo = tipo;
    }
}

class ElementoEstilo{
    constructor(propiedad, valor){
        this.propiedad = propiedad;
        this.valor = valor;
    }
}


