Ficha = (function(){
    return {
        instanciar: (ficha)=>{
            console.log(ficha);
            $fechasCompetencia = ficha.competencias.map(v=>{return parseFromStringToDate(v.fecha)});
            $('#Nombres').val(ficha.usuario.nombres);
            $('#ApellidoPaterno').val(ficha.usuario.apellidoPaterno);
            $('#FechaNacimiento').val(ficha.usuario.fechaNacimiento);
            $('#Edad').val(calcularEdadPorFechaNacimiento(ficha.usuario.fechaNacimiento));
            $('#FrecuenciaCardiacaMinima').val(ficha.condicionAnatomica.frecuenciaCardiaca);
            $('#FrecuenciaCardiacaMaxima').val(ficha.condicionAnatomica.frecuenciaCardiacaMaxima);
            $('#MacroFechaFin').val(getFechaFormatoString(Ficha.obtenerMaximaFechaCompeticiones()));
            //UsuarioFitness
            $('#EstadoCivil').text(ficha.estadoCivil);
            $('#Sexo').val(ficha.sexo == 1 ? "Masculino" : "Femenino");
            $('#Imc').text(ficha.imc);
            document.querySelectorAll('#NivelAtleta input').forEach((v,i)=>{
                if(i==ficha.nivel-1){
                    v.checked = true;
                }
            })

            const comps = ficha.competencias;
            tablaCompetencias.appendChild(Ficha.instanciarCompetencias(ficha.competencias));
            //
            let distanciaMax = comps[0].distancia;
            comps.forEach((v,i)=>{ if(i!=0) distanciaMax = v.distancia > distanciaMax ? v.distancia: distanciaMax;})
            document.querySelectorAll('#DistanciaMainCompetencia input').forEach(v=>{v.value == distanciaMax ? v.checked=true : '';})
            document.querySelectorAll('#DistanciaRutina input').forEach(v=>{v.value == distanciaMax ? v.checked=true : '';})
        }
        ,instanciarCompetencias : (comp)=>{
            comp = comp.sort((a, b) => parseFromStringToDate(b.fecha).getTime() - parseFromStringToDate(a.fecha).getTime());
            return htmlStringToElement('<tbody>'+comp.map((v,i) => {
                return `<tr>
                            <td class="pt-3-half">${i+1}</td>
                            <td class="padding-7">${v.fecha}</td>
                            <td class="padding-7">${v.distancia}</td>
                            <td class="padding-7">${v.nombre}</td>
                            <td class="padding-7">${v.tiempoObjetivo}</td>
                            <td class="padding-7">${v.prioridad == 1 ? '<span class="label label-success text-align-center">Prioritario</span>' : '<span class="label label-default text-align-center">Secundario</span>'}</td>
                         </tr>`
            }).join('')+'</tbody>');
            return comp;
        },
        obtenerMaximaFechaCompeticiones: ()=>{
            let maxFecha  = $fechasCompetencia[0];
            $fechasCompetencia.forEach((v,i)=>{
                if(i != 0){
                    maxFecha = v.getTime() > maxFecha.getTime() ? v : maxFecha;
                }
            })
            return maxFecha;
        },
        obtenerNivelAtleta:()=>{
            let ix;
            document.querySelectorAll('#NivelAtleta input').forEach((v,i)=>{
                if(v.checked)
                    ix = i;
            })
            return ix;
        }
    }
})();

FichaDOMQueries = (function(){
    return {
        getProyecciones: ()=>{
            return document.querySelector('#tabFichaTecnica #Proyecciones');
        },
    }
})();

MacroCiclo = (function(){
    return {
        generar: ()=>{
            let validado = true;
            FichaDOMQueries.getProyecciones().querySelectorAll('div>label>input[readonly]').forEach(v=>{
                if(!v.parentElement.classList.contains('state-success'))
                    validado = false;
            })

            const mainContainer = document.querySelector('#PorcentajesKilometraje');
            if(mainContainer.children.length == 1){
                mainContainer.children[0].remove();
            }
            const base = MacroCiclo.obtenerDatosMacroBase();
                instanciarPorcentajesKilometraje(base.distancia).then(porcentajes=>{
                    //Filtrando porcentajes
                    const porcentajesTrainer = [{},{},{}];
                    for(let i=0; i<porcentajesTrainer.length;i++){
                        porcentajesTrainer[i] = porcentajes.porcKiloTipos[i].semanas[base.periodizacion[i]-4];//1+ por que el index comienza en 0
                    }
                    mainContainer.appendChild(htmlStringToElement(MacroCiclo.mostrarPorcentajesKilo(base, porcentajesTrainer)));
                    //ModificandoBase en caso semana inicial y final no esten completas
                    $semCalculoMacro = MacroCiclo.obtenerObjSemana();
                    if(!$semCalculoMacro.fwFull) {
                        const y = document.querySelector('#PorcentajesKilometraje label.kms');
                        const p = Number(document.querySelector('#PorcentajesKilometraje label.perc').textContent.substr(0,2))/100;
                        const t = (($kilometrajeBase[0].kilometraje) * ((($semCalculoMacro.diasPrimeraSemana * 100) / 7)) / 100);
                        y.textContent = parseFloat(t*p, 2).toFixed(1);
                        document.querySelector('#PorcentajesKilometraje input').setAttribute('data-kms', t);
                    }
                    if(!$semCalculoMacro.lwFull){
                        const y = document.querySelectorAll('#PorcentajesKilometraje label.kms')[document.querySelectorAll('#PorcentajesKilometraje label.kms').length-1];
                        const p = Number(document.querySelectorAll('#PorcentajesKilometraje label.perc')[document.querySelectorAll('#PorcentajesKilometraje label.perc').length-1].textContent.substr(0,2))/100;
                        const t = (($kilometrajeBase[2].kilometraje) * ((($semCalculoMacro.diasUltimaSemana * 100) / 7)) / 100)
                        console.log(y, p, t);
                        y.textContent = parseFloat(t*p, 2).toFixed(1);
                        document.querySelectorAll('#PorcentajesKilometraje input')[document.querySelectorAll('#PorcentajesKilometraje input').length-1].setAttribute('data-kms', t);
                    }
                    $('.slider').slider();
                    MacroCiclo.instanciarGraficoTemporada(MacroCiclo.getObjParaGraficoTemporada(base));
                })

            /*if(validado && Ficha.obtenerNivelAtleta() != undefined) {
                $.smallBox({color: "success", content: "<b>Se ha generado con éxito</b>", color: "#73f194"});
            }else{
                $.smallBox({color: "alert", content: "Error..."});
            }*/
        },
        getObjParaGraficoTemporada: (baseDistribucion)=>{
            const dis1 = baseDistribucion.periodizacion[0];
            const dis2 = dis1 + baseDistribucion.periodizacion[1];
            return Array.from(document.querySelectorAll('#PorcentajesKilometraje label.kms')).map((v,i)=>{
                const c = i < dis1 ? "#83c5ff" : i < dis2 ? "#e86b6b" : "#a4f790";
                return {numSem: i+1, kms: Number(v.textContent), color: c}
            });
        },
        instanciarGraficoTemporada: (temporada)=>{

            var chart = AmCharts.makeChart( "GraficoTemporada", {
                "type": "serial",
                "theme": "light",
                "dataProvider": temporada,
                "valueAxes": [ {
                    "gridColor": "#FFFFFF",
                    "minimum": 0,
                    "gridAlpha": 0.2,
                    "dashLength": 0,
                } ],
                "gridAboveGraphs": true,
                "startDuration": 1,
                "graphs": [ {
                    "balloonText": "[[category]]: <b>[[value]]</b>",
                    "fillColorsField": "color",
                    "fillAlphas": 0.8,
                    "lineAlpha": 0.2,
                    "type": "column",
                    "valueField": "kms"
                } ],
                "chartCursor": {
                    "categoryBalloonEnabled": false,
                    "cursorAlpha": 0,
                    "zoomable": false
                },
                "categoryField": "numSem",
                "categoryAxis": {
                    "gridPosition": "start",
                    "gridAlpha": 0,
                    "tickPosition": "start",
                    "tickLength": 20
                },
            })
            document.querySelector('#InicialMacro').classList.remove('hidden');
        },
        calcularSimulacionSemanas: () => {
            const obj = {};
            obj.fechaInicio = parseFromStringToDate($('#MacroFechaInicio').val());
            obj.fechaFin = parseFromStringToDate($('#MacroFechaFin').val());
            obj.totDias = moment(obj.fechaFin).diff(obj.fechaInicio, 'days') + 1;
            obj.diasPrimeraSemana = obj.fechaInicio.getDay() == 0 ? 1 : 7 - obj.fechaInicio.getDay() + 1;
            obj.diasUltimaSemana = (obj.totDias - obj.diasPrimeraSemana) % 7 == 0 ? 7 : (obj.totDias - obj.diasPrimeraSemana) % 7;
            obj.semanas = obj.diasPrimeraSemana == 7 ? Math.ceil(obj.totDias / 7) : 1 + Math.ceil((obj.totDias - obj.diasPrimeraSemana) / 7);
            obj.totSemNonFull = obj.diasPrimeraSemana != 7 ? 1 : 0;
            obj.totSemNonFull += obj.diasUltimaSemana != 7 ? 1 : 0;
            if(obj.diasPrimeraSemana != 7 && obj.diasUltimaSemana == 7)
                obj.tipoCalculo = 1;
            else if(obj.totSemNonFull == 0)
                obj.tipoCalculo = 2;
            else if(obj.diasPrimeraSemana == 7 && obj.diasUltimaSemana != 7)
                obj.tipoCalculo = 3;
            else
                obj.tipoCalculo = 4;
            return obj;
        },
        obtenerDatosMacroBase: ()=>{
            const basico = {};
            basico.numSem = Number(document.querySelector('#MacroTotalSemanas').textContent);
            const proy = FichaDOMQueries.getProyecciones();
            basico.periodizacion = Array.from(proy.querySelectorAll('.periodizacion-calc[data-type="2"]')).map(v=>{return Number(v.value)});
            //basico.periodizacion = [10, 8, 4];
            basico.distancia = Number(document.querySelector('#DistanciaRutina input:checked').value);
            basico.nivelAtleta = Number(document.querySelector('#NivelAtleta input:checked').value);
            return basico;
        },
        obtenerObjSemana:()=>{
            const o = {};
            o.fechaInicio = parseFromStringToDate($('#MacroFechaInicio').val());
            o.fechaFin = parseFromStringToDate($('#MacroFechaFin').val());
            o.totDias = moment(o.fechaFin).diff(o.fechaInicio, 'days') + 1;
            o.diasPrimeraSemana = o.fechaInicio.getDay() == 0 ? 1 : 7 - o.fechaInicio.getDay() + 1;
            o.diasUltimaSemana = (o.totDias - o.diasPrimeraSemana) % 7 == 0 ? 7 : (o.totDias - o.diasPrimeraSemana) % 7;
            o.fwFull = o.diasPrimeraSemana == 7? true:false;
            o.lwFull = o.diasUltimaSemana == 7? true:false;
            return o;
        },
        calcularSemanas: () => {
            const fechaInicio = parseFromStringToDate($('#MacroFechaInicio').val()),
                fechaFin = parseFromStringToDate($('#MacroFechaFin').val());
            const totDias = moment(fechaFin).diff(fechaInicio, 'days') + 1;
            const diasPrimeraSemana = fechaInicio.getDay() == 0 ? 1 : 7 - fechaInicio.getDay() + 1;
            const diasUltimaSemana = (totDias - diasPrimeraSemana) % 7 == 0 ? 7 : (totDias - diasPrimeraSemana) % 7;
            let semanas = diasPrimeraSemana == 7 ? Math.ceil(totDias / 7) : 1 + Math.ceil((totDias - diasPrimeraSemana) / 7);
            let totSemNonFull = diasPrimeraSemana != 7 ? 1 : 0;
            totSemNonFull += diasUltimaSemana != 7 ? 1 : 0;
            const raw = `
                        <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Semanas full: <b>${semanas - totSemNonFull}</b></label></div>
                        <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Semanas no-full: <b>${totSemNonFull == 2 ? totSemNonFull + ' (S.1 & S.' + semanas + ')' : totSemNonFull } </b></label></div>
                        <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Semanas totales: <b>${semanas}</b></label></div>
                        <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Días totales de primera semana: <b>${diasPrimeraSemana}</b></label></div>
                        <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Días totales de última semana: <b>${diasUltimaSemana}</b></label></div>
                        <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Días restantes: <b>${totDias - diasPrimeraSemana - diasUltimaSemana}</b></label></div>
                        <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Días totales: <b>${totDias}</b></label></div>`
            $.smallBox({timeout: 15000, content: `${raw}`, color: "#4a4e3b"});
            document.querySelector('#MacroTotalSemanas').textContent = semanas;
        },
        calcularProyecciones: (input, tipoProyeccion) => {
            const ix = Number(input.getAttribute('data-index')), tipo = input.getAttribute('data-type'),
                valor = input.value;
            const contProyecciones = FichaDOMQueries.getProyecciones();
            const totSem = document.querySelector('#MacroTotalSemanas').textContent;
            let identificadores;
            switch (tipoProyeccion) {
                case 1:
                    identificadores = [".periodizacion-calc", "#TotalPeriodizacion1", "#TotalPeriodizacion2"];
                    break;
                case 2:
                    identificadores = [".velocidad-calc", "#TotalVelocidad1", "#TotalVelocidad2"];
                    break;
                case 3:
                    identificadores = [".cadencia-calc", "#TotalCadencia1", "#TotalCadencia2"];
                    break;
                default:
                    identificadores = [".tcs-calc", "#TotalTcs1", "#TotalTcs2"];
            }

            if (tipo == 1) {
                contProyecciones.querySelector(`${identificadores[0]}[data-index="${ix + 3}"]`).value = MacroCiclo.calcularNumSemByPorcentaje(valor, ix);
            } else {
                contProyecciones.querySelector(`${identificadores[0]}[data-index="${ix - 3}"]`).value = MacroCiclo.calcularPorcentajeByNumSem(valor, ix);
            }
            const tot1 = MacroCiclo.calcularTotalesDistribucion(tipoProyeccion, 1), tot2 = MacroCiclo.calcularTotalesDistribucion(tipoProyeccion, 2);
            const eleTot1 = contProyecciones.querySelector(` ${identificadores[1]}`), eleTot2 = contProyecciones.querySelector(` ${identificadores[2]}`);
            eleTot1.value = tot1;
            eleTot2.value = tot2;
            if(tot1>=99.99 && tot1<=100.01){
                eleTot1.parentElement.classList.add('state-success');eleTot1.parentElement.classList.remove('state-error');
            }else{
                eleTot1.parentElement.classList.add('state-error');eleTot1.parentElement.classList.remove('state-success');
            }
            if(Number(tot2)==Number(totSem)){
                eleTot2.parentElement.classList.add('state-success');eleTot2.parentElement.classList.remove('state-error');
            }else{
                eleTot2.parentElement.classList.add('state-error');eleTot2.parentElement.classList.remove('state-success');
            }
        },
        calcularTotalesDistribucion: (tipoCat, tipoCalc) => {
            let acum = 0;
            switch (tipoCat) {
                case 1:
                    Array.from(document.querySelectorAll(`#tabFichaTecnica .periodizacion-calc[data-type="${tipoCalc}"]`)).forEach(v => {
                        acum += !isNaN(v.value) ? Number(v.value) : 0;
                    });
                    break;
                case 2:
                    Array.from(document.querySelectorAll(`#tabFichaTecnica .velocidad-calc[data-type="${tipoCalc}"]`)).forEach(v => {
                        acum += !isNaN(v.value) ? Number(v.value) : 0;
                    });
                    break;
                case 3:
                    Array.from(document.querySelectorAll(`#tabFichaTecnica .cadencia-calc[data-type="${tipoCalc}"]`)).forEach(v => {
                        acum += !isNaN(v.value) ? Number(v.value) : 0;
                    });
                    break;
                default:
                    Array.from(document.querySelectorAll(`#tabFichaTecnica .tcs-calc[data-type="${tipoCalc}"]`)).forEach(v => {
                        acum += !isNaN(v.value) ? Number(v.value) : 0;
                    });
            }

            return acum.toFixed(2);
        },
        calcularPorcentajeByNumSem: (numSem, index) => {
            const estadisticas = MacroCiclo.calcularSimulacionSemanas();
            let situacion = estadisticas.tipoCalculo;
            if (situacion == 1) {
                if(index == 3){
                    --numSem;
                    return ((((numSem*7)+estadisticas.diasPrimeraSemana)*100)/estadisticas.totDias).toFixed(2);
                }else
                    return (((numSem*7)*100)/estadisticas.totDias).toFixed(2);
            } else if (situacion == 2){
                return (numSem * 100 / estadisticas.semanas).toFixed(2);
            } else if (situacion == 3) {
                if(index == 5){
                    --numSem;
                    return ((((numSem*7)+estadisticas.diasUltimaSemana)*100)/estadisticas.totDias).toFixed(2);
                }else
                    return (((numSem*7)*100)/estadisticas.totDias).toFixed(2);
            } else if (situacion == 4) {
                if(index == 3){
                    --numSem;
                    return ((((numSem*7)+estadisticas.diasPrimeraSemana)*100)/estadisticas.totDias).toFixed(2);
                }else if(index == 5){
                    --numSem;
                    return ((((numSem*7)+estadisticas.diasUltimaSemana)*100)/estadisticas.totDias).toFixed(2);
                }else
                    return (((numSem*7)*100)/estadisticas.totDias).toFixed(2);
            }
        },
        calcularNumSemByPorcentaje: (porcentaje, index)=>{
            const estadisticas = MacroCiclo.calcularSimulacionSemanas();
            let situacion = estadisticas.tipoCalculo;
            if (situacion == 1) {
                if(index == 0){
                    return (
                        ((porcentaje * estadisticas.totDias / 100) / 7) + 1 - estadisticas.diasPrimeraSemana/7
                    ).toFixed(2);
                }else
                    return (((porcentaje * estadisticas.totDias) / 100) / 7).toFixed(2);
            } else if (situacion == 2){
                return (((porcentaje * estadisticas.totDias) / 100) / 7).toFixed(2);
            } else if (situacion == 3) {
                if(index == 2){
                    return (
                        ((porcentaje * estadisticas.totDias / 100) / 7) + 1 - estadisticas.diasUltimaSemana/7
                    ).toFixed(2);
                }else
                    return (((porcentaje * estadisticas.totDias) / 100) / 7).toFixed(2);
            } else if (situacion == 4) {
                if(index == 0){
                    return (
                        ((porcentaje * estadisticas.totDias / 100) / 7) + 1 - estadisticas.diasPrimeraSemana/7
                    ).toFixed(2);
                }
                else if(index == 2){
                    return (
                        ((porcentaje * estadisticas.totDias / 100) / 7) + 1 - estadisticas.diasUltimaSemana/7
                    ).toFixed(2);
                }else
                    return (((porcentaje * estadisticas.totDias) / 100) / 7).toFixed(2);
            }
        },
        mostrarPorcentajesKilo: (base, pTrainer)=>{
            //HTML R A W
            const nombresEtapa = ["Preparación General", "Preparación Específica", "Preparación Precompetitiva"];
            const colorClass = ["bg-color-teal", "bg-color-redLight", "bg-color-greenLight"];

            let html = `<section class="">`;
            html += base.periodizacion.map((v,i,k)=>{
                const kmsRef = $kilometrajeBase[i].kilometraje;
                return `<div class="col col-4 padding-0 text-align-center">
                            <h6 class="${colorClass[i]} txt-color-white font-md margin-bottom-10 padding-10 text-align-center">${nombresEtapa[i]}</h6>
                            ${MacroCicloSeccion.bodyPorcentajesKilo(k, pTrainer[i], i, kmsRef)}
                        </div>`
            }).join('');
            html+=`</section>`
            return html;
        },
        instanciarKilometrajeBase: ()=>{
            const base = MacroCiclo.obtenerDatosMacroBase();
            instanciarKilometrajeBase(base.distancia, base.nivelAtleta);
        }
    }
})();

MacroCicloSeccion = (function(){
    return {
        bodyPorcentajesKilo: (arrCant, pTrainer, ix, kmsBase)=>{
            const colorClass = ["slider-warning", "slider-danger", "slider-success"];
            let prefix = `<div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12">`
            let html = `<div class="col <col>-xs-12 col-sm-12 col-md-12 col-lg-12">`;
            let porcents = `<div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12">`
            let kms = `<div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12">`
            for(let i=0; i<arrCant[ix];i++){
                const fixVal = 100 - pTrainer.porcentajes[i];
                const windx = ix==0?i+1:ix==1?(i+1)+arrCant[ix-1]:(arrCant.reduce((a, b)=>{return a+b}))-arrCant[ix]+i+1;
                prefix+= `<label class="padding-10">${windx<10 ? '0'+windx : windx}</label>`;
                html+= `<span class="padding-5 text-align-left"><input type="text" class="slider ${colorClass[ix]}" value="" data-slider-min="0" data-slider-max="100" data-slider-step="2" data-slider-value="${fixVal}" data-slider-orientation="vertical" data-slider-selection="after" data-slider-handle="round" data-slider-tooltip="hide" data-index="${windx}" data-kms="${kmsBase}"></span>`;
                porcents+=`<label class="padding-5 perc" data-index="${windx}">${pTrainer.porcentajes[i]}%</label>`;
                kms+=`<label class="padding-5 kms" data-index="${windx}">${((kmsBase*pTrainer.porcentajes[i])/100).toFixed(1)}</label>`;
            }
            prefix+=`</div>`,porcents+=`</div>`, html+=`</div>`, kms+=`</div>`;
            return prefix+html+porcents+kms;
        }
    }
})();