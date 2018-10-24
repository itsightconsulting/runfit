let $porcentajesIntensidad = [];

Ficha = (function(){
    return {
        instanciar: (ficha)=>{
            const comps = ficha.competencias;
            $fechasCompetencia = comps.map(v=>{return {nombre: v.nombre, prioridad: v.prioridad, fecha: parseFromStringToDate(v.fecha)}}).sort((a, b)=>{return a.fecha - b.fecha;});
            $('#Nombres').val(ficha.usuario.nombres);
            $('#ApellidoPaterno').val(ficha.usuario.apellidoPaterno);
            $('#FechaNacimiento').val(ficha.usuario.fechaNacimiento);
            $('#Edad').val(calcularEdadByFechaNacimiento(ficha.usuario.fechaNacimiento));
            $('#FrecuenciaCardiacaMinima').val(ficha.condicionAnatomica.frecuenciaCardiaca);
            $('#FrecuenciaCardiacaMaxima').val(ficha.condicionAnatomica.frecuenciaCardiacaMaxima);
            $('#MacroFechaFin').val(getFechaFormatoString(FichaGet.obtenerMaximaFechaCompeticiones($fechasCompetencia)));
            $('#EstadoCivil').text(ficha.estadoCivil);
            $('#Sexo').val(ficha.sexo == 1 ? "Masculino" : "Femenino");
            $('#Imc').text(ficha.imc);
            FichaSet.nivelAtleta(ficha.nivel);
            FichaSet.setMaximoDistanciaCompetencia(comps);
            FichaSeeder.instanciarValoresDemo();
            Calc.setRestantes();
            //Recreando tabla de listado de competencias
            tbCompetencias.appendChild(FichaSeccion.newListadoCompetencias(comps));//global variable
        }
    }
})();

FichaGet = (function(){
    return {
        obtenerMaximaFechaCompeticiones: (fechas)=>{
            let maxFecha  = fechas[0].fecha;
            fechas.forEach((v,i)=>{
                if(i != 0){
                    maxFecha = v.fecha.getTime() > maxFecha.getTime() ? v.fecha : maxFecha;
                }
            })
            return maxFecha;
        },
        obtenerNivelAtleta:()=>{
            let ix = 0;
            document.querySelectorAll('#NivelAtleta input').forEach((v,i)=>{
                if(v.checked)
                    ix = i;
            })
            return ix;
        },
        obtenerBase: ()=>{
            const basico = {};
            const proyecciones = FichaDOMQueries.getProyecciones();
            basico.numSem = Number(document.querySelector('#MacroTotalSemanas').textContent);
            basico.periodizacion = Array.from(proyecciones.querySelectorAll('.periodizacion-calc[data-type="2"]')).map(v=>{return Number(v.value)});
            basico.distribucionPorcentaje = Array.from(proyecciones.querySelectorAll('.periodizacion-calc[data-type="1"')).map(v=>{return Number(v.value)/100;});
            basico.distancia = Number(document.querySelector('#DistanciaRutina input:checked').value);
            basico.nivelAtleta = Number(document.querySelector('#NivelAtleta input:checked').value);
            basico.fechaInicio = document.querySelector('#MacroFechaInicio').value;
            basico.fechaFin = document.querySelector('#MacroFechaFin').value;
            $kilometrajeBase.length == 0 ? obtenerKilometrajeBaseBD(basico.distancia, basico.nivelAtleta) : "";//En caso no tengamos el kilometrajeMaestro, lo consultamos
            return basico;
        },
    }
})();

FichaSeccion = (function(){
    return {
        newListadoCompetencias : (comp)=>{
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
        newAlertaInfoSemanas: () => {
            const info = CalcProyecciones.informacionSemanas();
            const raw = `
                    <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Semanas full: <b>${info.semanas - info.totSemNonFull}</b></label></div>
                    <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Semanas no-full: <b>${info.totSemNonFull == 2 ? info.totSemNonFull + ' (S.1 & S.' + info.semanas + ')' : info.totSemNonFull } </b></label></div>
                    <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Semanas totales: <b>${info.semanas}</b></label></div>
                    <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Días totales de primera semana: <b>${info.diasPrimeraSemana}</b></label></div>
                    <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Días totales de última semana: <b>${info.diasUltimaSemana}</b></label></div>
                    <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Días restantes: <b>${info.totDias - info.diasPrimeraSemana - info.diasUltimaSemana}</b></label></div>
                    <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Días totales: <b>${info.totDias}</b></label></div>`
            $.smallBox({timeout: 15000, content: `${raw}`, color: "#4a4e3b"});
        },
    }
})();

FichaSet = (function(){
    return {
        nivelAtleta: (nivel)=>{
            document.querySelectorAll('#NivelAtleta input').forEach((v,i)=>{
                if(i==nivel-1){
                    v.checked = true;
                }
            })
        },
        setMaximoDistanciaCompetencia: (comps)=>{
            let maxDistancia = comps[0].distancia;
            comps.forEach((v,i)=>{ if(i!=0) maxDistancia = v.distancia > maxDistancia ? v.distancia: maxDistancia;})
            document.querySelector('#DistanciaCompetencia').value = maxDistancia;
            document.querySelectorAll('#DistanciaRutina input').forEach(v=>{v.value == maxDistancia ? v.checked=true : '';})
        },
        setTotalSemanas: () => {
            const fechaInicio = parseFromStringToDate($('#MacroFechaInicio').val());
            const fechaFin = parseFromStringToDate($('#MacroFechaFin').val());
            const totDias = moment(fechaFin).diff(fechaInicio, 'days') + 1;
            const diasPrimeraSemana = fechaInicio.getDay() == 0 ? 1 : 7 - fechaInicio.getDay() + 1;
            document.querySelector('#MacroTotalSemanas').textContent = diasPrimeraSemana == 7 ? Math.ceil(totDias / 7) : 1 + Math.ceil((totDias - diasPrimeraSemana) / 7);
            //document.querySelector('#MacroTotalSemanas').textContent = "56";
        },
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
        comprobar: (e)=>{
            blockButton(e.target);
            const contenedorMK = document.querySelector('#PorcentajesKilometraje');//metricas de kilometraje
            const contenedorMK2 = document.querySelector('#PorcentajesIntensidad');//metricas de kilometraje
            if(MacroCiclo.validacion(contenedorMK, contenedorMK2)) {
                //Actualizando calculos bases en caso se hayan modificado algunos otros parametros
                Calc.setRestantes();
                const ritmosAerobicos = Calc.getRitmosAerobicos();
                const base = FichaGet.obtenerBase();
                instanciarPorcentajesKilometraje(base.distancia).then(porcentajes=>{
                    //Filtrando porcentajes
                    const porcentajesTrainer = [{},{},{}];
                    for(let i=0; i<porcentajesTrainer.length;i++){
                        porcentajesTrainer[i] = porcentajes.porcKiloTipos[i].semanas[base.periodizacion[i]-4];//1+ por que el index comienza en 0
                    }
                    contenedorMK.appendChild(htmlStringToElement(MacroCiclo.mostrarPorcentajesKilo(base, porcentajesTrainer)));
                    contenedorMK2.appendChild(htmlStringToElement(MacroCiclo.mostrarPorcentajesIntensidad(base, porcentajesTrainer)));
                    //ModificandoBase en caso semana inicial y final no esten completas
                    $semCalculoMacro = CalcProyecciones.informacionSemanas();
                    if($semCalculoMacro.diasPrimeraSemana < 7) {
                        const y = document.querySelector('#PorcentajesKilometraje label.kms');
                        const p = Number(document.querySelector('#PorcentajesKilometraje label.perc').textContent.substr(0,2))/100;
                        const t = (($kilometrajeBase[0].kilometraje) * ((($semCalculoMacro.diasPrimeraSemana * 100) / 7)) / 100);
                        y.textContent = parseFloat(t*p, 2).toFixed(1);
                        document.querySelector('#PorcentajesKilometraje input').setAttribute('data-kms', t);
                    }
                    if($semCalculoMacro.diasUltimaSemana < 7) {
                        const y = document.querySelectorAll('#PorcentajesKilometraje label.kms')[document.querySelectorAll('#PorcentajesKilometraje label.kms').length-1];
                        const p = Number(document.querySelectorAll('#PorcentajesKilometraje label.perc')[document.querySelectorAll('#PorcentajesKilometraje label.perc').length-1].textContent.substr(0,2))/100;
                        const t = (($kilometrajeBase[2].kilometraje) * ((($semCalculoMacro.diasUltimaSemana * 100) / 7)) / 100)
                        y.textContent = parseFloat(t*p, 2).toFixed(1);
                        document.querySelectorAll('#PorcentajesKilometraje input')[document.querySelectorAll('#PorcentajesKilometraje input').length-1].setAttribute('data-kms', t);
                    }
                    $('.slider').slider();
                    //Calculando las mejoras de velocidades segun semana
                    const arrTiempos = Calc.getDistribucionTiempoPlanificado(base);
                    const copyArrTiempos = JSON.parse(JSON.stringify(arrTiempos));
                    const RCPs = [];
                    //Separando máximo y mínimos por etapa de preparación
                    base.periodizacion.forEach(v=>{
                        RCPs.push({first: copyArrTiempos[0].factor, last: copyArrTiempos[v-1].factor});
                        copyArrTiempos.splice(0, v);
                    });

                    document.querySelectorAll('#EstadisticasAdicionales .rcps').forEach((v,i)=>{
                        v.value = RCPs[i].last.substring(3);
                    });

                    const ritmosEntreAero = Calc.getRitmosEntrenamientoAerobico(ritmosAerobicos.actual, ritmosAerobicos.preCompetitivo, base);
                    let acc = 0;
                    document.querySelectorAll('#EstadisticasAdicionales .raps').forEach((v,i)=>{
                        v.value = ritmosEntreAero[(acc+base.periodizacion[i])-1].factor.substring(3);
                        acc+=base.periodizacion[i];
                    });

                    const cadenciaActual = document.querySelector('#CadenciaControl').value;
                    const cadenciaCompetencia = document.querySelector('#CadenciaCompetencia').value;
                    const ritmosCadencia = Calc.getRitmosCadenciaCompetencia(cadenciaActual, cadenciaCompetencia, base);

                    acc = 0;
                    document.querySelectorAll('#EstadisticasAdicionales .cdcs').forEach((v,i)=>{
                        v.value = ritmosCadencia[(acc+base.periodizacion[i])-1].factor;
                        acc+=base.periodizacion[i];
                    });

                    acc = 0;
                    const longitudesPaso = Calc.getLongitudesDePaso(arrTiempos, ritmosCadencia);
                    document.querySelectorAll('#EstadisticasAdicionales .lpcs').forEach((v,i)=>{
                        v.value = longitudesPaso[(acc+base.periodizacion[i])-1];
                        acc+=base.periodizacion[i];
                    });

                    //Graficos - Información relacionada
                    MacroCiclo.instanciarInformacionTemporada(base);
                    MCGrafico.temporada(MCGraficoData.paraTemporada(base));
                    document.querySelector('#btnGenerarRutina').classList.remove('disabled');
                    document.querySelector('#btnGenerarRutina').setAttribute('title','Generar rutina');
                    unlockButton(e.target);
                })
            }else{
                $.smallBox({color: "alert", content: "Validación fallida"});
            }
        },
        instanciarInformacionTemporada: (base)=>{
            const allKms = Array.from(document.querySelectorAll('#PorcentajesKilometraje label.kms')).map(v=>{return Number(v.textContent)});
            const sumKms = allKms.reduce((a,b)=>{return a+b});
            const kmsParts = base.periodizacion.map((v)=>{
                return allKms.splice(0, v);//Cada vez el arreglo va perdiendo elementos y por eso siempre hacemos que se corte desde 0
            });
            base.porcentajesKms = [];

            const kiloTotal = document.querySelector('#KilometrajeTotal');
            kiloTotal.querySelector('h1').textContent = parseFloat(sumKms).toFixed(1);
            kiloTotal.querySelector('span').textContent = base.numSem+" semanas";
            document.querySelectorAll('#InicialMacro .dist-etapa').forEach((v,i)=>{
                const kmsEsp = parseFloat(kmsParts[i].reduce((a,b)=>{return a+b}))
                v.querySelector('h1').textContent = kmsEsp.toFixed(1);
                v.querySelector('span').textContent = base.periodizacion[i] +" semanas";
                base.porcentajesKms.push(((kmsEsp * 100) / sumKms).toFixed(2));
            });
            document.querySelector('#KilometrajeTotalTemporada').value = parseFloat(sumKms).toFixed(1);
            document.querySelector('#KilometrajePromedioSemanal').value = parseFloat(sumKms/base.numSem).toFixed(1);
            MCGrafico.miniPorcentual(MCGraficoData.paraMini(base.porcentajesKms));
        },
        mostrarPorcentajesKilo: (base, pTrainer)=>{
            const nombresEtapa = ["Preparación General", "Preparación Específica", "Preparación Precompetitiva"];
            //const colorClass = ["bg-color-teal", "bg-color-redLight", "bg-color-greenLight"];
            let html = `<section class="">`;
            html += base.periodizacion.map((v,i,k)=>{
                const kmsRef = $kilometrajeBase[i].kilometraje;
                return `<div class="col col-4 padding-0 text-align-center">
                            <h6 class="bg-color-white txt-color-gray font-md margin-bottom-10 padding-10 text-align-center">${nombresEtapa[i]}</h6>
                            ${MacroCicloSeccion.bodyPorcentajesKilo(k, pTrainer[i], i, kmsRef)}
                        </div>`
            }).join('');
            html+=`</section>`
            return html;
        },
        mostrarPorcentajesIntensidad: (base, pTrainer)=>{
            const nombresEtapa = ["Preparación General", "Preparación Específica", "Preparación Precompetitiva"];
            let html = `<section class="">`;
            html += base.periodizacion.map((v,i,k)=>{
                return `<div class="col col-4 padding-0 text-align-center">
                            <h6 class="bg-color-white txt-color-gray font-md margin-bottom-10 padding-10 text-align-center">${nombresEtapa[i]}</h6>
                            ${MacroCicloSeccion.bodyPorcentajesIntensidad(k, pTrainer[i], i)}
                        </div>`
            }).join('');
            $porcentajesIntensidad.length==0? MacroCicloGet.obtenerPorcentajesIntens(base.periodizacion) : "";
            html+=`</section>`
            return html;
        },
        instanciarKilometrajeBase: (e)=>{
            const base = FichaGet.obtenerBase();
            obtenerKilometrajeBaseBD(base.distancia, base.nivelAtleta);
            //Cambiando del cbo
            const rdbVal = e.target.value;
            if( rdbVal == 10 || rdbVal == 21 || rdbVal == 42)
                document.querySelector('#DistanciaCompetencia').value = rdbVal;
        },
        generarRutinaCompleta: (e)=>{//Temp
            if($('#KilometrajePromedioSemanal').val() != ""){
                const $chelmoMacro = [];
                //Semanas
                const totalSemanas = FichaGet.obtenerBase().numSem;
                const fIni = parseFromStringToDate(document.querySelector('#MacroFechaInicio').value);
                const fFin = parseFromStringToDate(document.querySelector('#MacroFechaFin').value);
                for(let i=0; i<totalSemanas;i++){
                    const refDia = fIni.getDay();
                    let diasParaFull = refDia==0?0:7-refDia;
                    if(i == 0){
                        const objFirtsWeek = {};
                        objFirtsWeek.fechaInicio = moment(fIni).add((i*7), 'd').format('DD/MM/YYYY');
                        objFirtsWeek.fechaFin = moment(fIni).add((i*7)+diasParaFull, 'd').format('DD/MM/YYYY');
                        objFirtsWeek.flagFull = diasParaFull == 1 ? true : false;
                        const literales = ["Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo"];//Artificio temporal(2 domingos)
                        const dias = [];
                        for(let i=0; i<diasParaFull+1; i++){
                            const fechaParse = parseFromStringToDate2(moment(fIni).add((i), 'd').format('DD/MM/YYYY'));
                            dias.push({fecha: moment(fIni).add((i), 'd').format('DD/MM/YYYY'), dia: fechaParse.getDate(), flagDescanso: false, literal: literales[fechaParse.getDay()], diaLiteral: fechaParse.getDate() + " "+ literales[fechaParse.getDay()]});
                        }
                        objFirtsWeek.lstDia = dias;
                        $chelmoMacro.push(new Semana(objFirtsWeek))
                    }else{
                        $chelmoMacro.push(new Semana(undefined, parseFromStringToDate(moment($chelmoMacro[i-1].fechaFin).add(1, 'd').format('YYYY-MM-DD')),parseFromStringToDate(moment($chelmoMacro[i-1].fechaFin).add(7, 'd').format('YYYY-MM-DD'))));
                    }
                }

                //Modificando fecha fin de la ultima semana en caso esta no se encuentre mapeada con los 7 días calendarios
                $chelmoMacro[totalSemanas-1].fechaFin = fFin;
                //Rutina
                const r = {};
                r.fechaInicio = fIni;
                r.fechaFin = fFin;
                r.meses = 4;
                r.anios = 0;
                r.totalSemanas = $('#MacroTotalSemanas').text().trim();
                r.dias = 200;
                r.semanas = $chelmoMacro;
                r.tipoRutina = 1;
                r.control = {};
                r.control.kilometrajeTotal = document.querySelector('#KilometrajeTotal h1').textContent.trim();
                r.control.kilometrajeActual = 0;
                const baseDistribucion = FichaGet.obtenerBase();
                const dis1 = baseDistribucion.periodizacion[0];
                const dis2 = dis1 + baseDistribucion.periodizacion[1];
                const objs = Array.from(document.querySelectorAll('#PorcentajesKilometraje label.kms')).map((v,i)=>{
                    const c = i < dis1 ? "#83c5ff" : i < dis2 ? "#e86b6b" : "#a4f790";
                    return {numSem: i+1, kms: Number(v.textContent), color: c}
                });
                const mZC = RitmosSZC.getMetricasZonasCardiacas();
                const mVC = RitmosSVYC. getMetricasVelocidades();
                r.semanas.forEach((v,fix)=>{
                    v.kilometrajeTotal = objs[fix].kms;
                    //Modificando indicadores de pulso y de tiempos
                    r.semanas[fix].metricas = JSON.stringify(mZC.map(v=>{
                        return {nombre: v.nombre, min: v.pMin, max: v.pMax, indicadores: {max: v.indicadores[fix].max, min: v.indicadores[fix].min}}
                    }));
                    r.semanas[fix].metricasVelocidad = JSON.stringify(mVC.map(v=>{
                        return {parcial: v.indicadores[fix].p};
                    }));
                })
                guardarRutina(r, (btn = e.target));
            } else{
                $.smallBox({color: "alert", content: "Primero debes generar el macro..."});
            }
        },
        validacion: (contenedorMK, contenedorMK2)=>{//contenedor de metricas de kilometraje
            let validado = false;
            if(FichaDOMQueries.getProyecciones().querySelector('#TotalPeriodizacion2').parentElement.classList.contains('state-success'))
                validado = true;

            if(contenedorMK.children.length == 1) {
                contenedorMK.children[0].remove();
            }

            if(contenedorMK2.children.length == 1) {
                contenedorMK2.children[0].remove();
            }

            const totSem = document.querySelector('#MacroTotalSemanas').textContent.trim();
            const totSemPerio = Number(document.querySelector('#TotalPeriodizacion2').value.trim());
            if(validado && FichaGet.obtenerNivelAtleta() != undefined && totSem == totSemPerio) {
                return true;
            }else{
                return false;
            }
        }
    }
})();

MCGraficoData = (function(){
    return {
        paraTemporada: (objBase)=>{
            const dis1 = objBase.periodizacion[0];
            const dis2 = dis1 + objBase.periodizacion[1];
            //Dis kilometraje
            const data = Array.from(document.querySelectorAll('#PorcentajesKilometraje label.kms')).map((v,i)=>{
                const c = i < dis1 ? "rgba(131, 197, 255, 0.2)" : i < dis2 ? "rgba(232, 107, 10, 0.2)" : "rgba(164, 247, 144, 0.2)";
                return {numSem: i+1, kms: Number(v.textContent), color: c};
            });

            //Intensidad
            document.querySelectorAll('#PorcentajesIntensidad label.perc-ints').forEach((v,i)=>{
                data[i].perc = v.textContent.slice(0,-1);
            })

            //Reconstruyendo Semanas
            RutinaGet.getRegeneracionSemanas(moment(objBase.fechaInicio).format('DD/MM/YYYY'), moment(objBase.fechaFin).format('DD/MM/YYYY'), objBase.numSem).forEach((v,i)=>{
                $fechasCompetencia.forEach(w=>{
                    if(v.fechaInicio<=w.fecha && v.fechaFin>=w.fecha)
                        data[i].bullet = w.prioridad == 1 ? `${_ctx}img/gold-medal.png` : `${_ctx}img/silver-medal.png`;

                })
            })
            return data;
        },
        paraMini: (porcentajes)=>{
            const lenP = porcentajes.length;
            const data = [{title: "General", value: porcentajes[0], color: "#e6f3ff"}, {title: "Específica", value: porcentajes[1], color: "#fae1ce"}, {title: "Precompetitiva", value: porcentajes[2], color: "#edfde9"}];
            const dF = new Array(lenP);
            for(let i=0; i<Object.keys(data).length;i++){
                dF[i] = [];
                data.forEach((v,ii)=>{
                    if(ii<lenP)
                        dF[i].push(v[Object.keys(v)[i]]);
                });
            }
            return dF;
        }
    }
})();
MCGrafico = (function(){
    return {
        temporada: (data)=>{
            document.querySelector('#InicialMacro').classList.remove('hidden');
            document.querySelector('#ContainerVarVolumen').classList.remove('hidden');

            if($chartTemporada.ctx != undefined){
                $chartTemporada.destroy();
            }else{
                Chart.pluginService.register({
                    afterUpdate: function(chart) {
                        $idsComp = [];
                        if(chart.canvas.id === "GraficoTemporada")
                            data.forEach((v,i)=>{
                                if(v.bullet != undefined){
                                    let img = new Image();
                                    img.src = v.bullet;
                                    Object.values(chart.config.data.datasets[1]._meta)[0].data[i]._model.pointStyle = img;
                                    $idsComp.push(i);
                                }
                            })
                    },
                    afterDatasetsDraw: function(chart) {
                        if(chart.canvas.id === "GraficoTemporada"){
                            let ctx = chart.ctx;
                            chart.data.datasets.forEach(function (dataset, i) {
                                if (i == 1) {
                                    let meta = chart.getDatasetMeta(i);
                                    if (!meta.hidden) {
                                        meta.data.forEach(function (element, index) {
                                            // Draw the text in black, with the specified font
                                            ctx.fillStyle = 'rgb(0, 0, 0)';

                                            let fontSize = 11;
                                            let fontStyle = 'normal';
                                            ctx.font = Chart.helpers.fontString(fontSize, fontStyle);
                                            let dataString = "";
                                            // Just naively convert to string for now
                                            $idsComp.filter(v => {
                                                return v == index
                                            }).length == 0 ? dataString = dataset.data[index].toString() + "%" : "";
                                            // Make sure alignment settings are correct
                                            ctx.textAlign = 'center';
                                            ctx.textBaseline = 'middle';
                                            ctx.fillStyle = '#57889c';

                                            let padding = 5;
                                            let position = element.tooltipPosition();
                                            ctx.fillText(dataString, position.x, position.y - (fontSize / 2) - padding);

                                        });
                                    }
                                }
                            });
                        }else{
                            let ctx = chart.ctx;
                            chart.data.datasets.forEach(function (dataset, i) {
                                if (i == 0) {
                                    let meta = chart.getDatasetMeta(i);
                                    if (!meta.hidden) {
                                        meta.data.forEach(function (element, index) {
                                            // Draw the text in black, with the specified font
                                            ctx.fillStyle = 'rgb(0, 0, 0)';

                                            let fontSize = 20;
                                            let fontStyle = 'normal';
                                            ctx.font = Chart.helpers.fontString(fontSize, fontStyle);
                                            let dataString = "";
                                            // Just naively convert to string for now
                                            dataString = dataset.data[index].toString() + "%";
                                            // Make sure alignment settings are correct
                                            ctx.textAlign = 'center';
                                            ctx.textBaseline = 'middle';
                                            ctx.fillStyle = '#4A4E3B ';

                                            let padding = 0;
                                            let position = element.tooltipPosition();
                                            ctx.fillText(dataString, position.x, position.y - (fontSize / 2) - padding);

                                        });
                                    }
                                }
                            });
                        }
                    }
                });
            }

            let ctx = document.getElementById('GraficoTemporada').getContext('2d');

            $chartTemporada = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: data.map(({numSem})=>numSem),
                    datasets: [{
                        label: 'Kilometraje',
                        data: data.map(({kms})=>kms),
                        borderColor: "grey",
                        yAxisID: 'y-axis-1',
                        backgroundColor: data.map(({color})=>color)
                        ,
                    }, {
                        label: 'Intensidad',
                        data: data.map(({perc})=>perc),
                        yAxisID: 'y-axis-2',
                        // Changes this dataset to become a line
                        type: 'line',
                        borderColor: '#73F194',
                        backgroundColor: '#73F194',
                        pointBorderColor: '#73F194',
                        pointBackgroundColor: '#73F194',
                        pointHoverBackgroundColor: '#73F194',
                        pointHoverBorderColor: '#73F194',
                    }]
                },
                borderWidth: 1,
                options: {
                    maintainAspectRatio: false,
                    tooltips: {
                        yAlign: 'bottom',
                        xAlign: 'center',
                        callbacks: {
                            title: function(tooltipItem) {
                                let mIx;
                                return $idsComp.filter((v,i) => {
                                    if(v == tooltipItem[0].index)
                                        return String(mIx = i);//Se convierte en String para evitar problemas cuando el indice sea 0, ya que js considera en el caso de filter el 0 como undef/null
                                }).length == 0 ? this._data.labels[tooltipItem[0].index] : $fechasCompetencia[mIx].nombre;
                            },
                        }
                    },
                    scales: {
                        yAxes: [{
                            id: 'y-axis-1',
                            type: 'linear',
                            position: 'left',
                            gridLines: {
                                display:false
                            },
                            ticks: {
                                suggestedMin: 0,
                                beginAtZero: true
                            }
                        }, {
                            id: 'y-axis-2',
                            type: 'linear',
                            position: 'right',
                            gridLines: {
                                color: "#F1F9FD"
                            },
                            ticks: {
                                suggestedMin: 0,
                                suggestedMax: 100,   // minimum will be 0, unless there is a lower value.
                                // OR //
                                beginAtZero: true,   // minimum value will be 0.
                                callback: function(label) {
                                    return label + "%";
                                }
                            },
                        }]
                    },
                    elements:{
                        line: {
                            fill: false
                        }
                    },
                    legend: {
                        labels: {
                            filter: function(legendItem) {
                                if (legendItem.datasetIndex === 0) {
                                    return false;
                                }
                                return true;
                            }
                        }
                    },
                }
            });
        },
        miniPorcentual: (dF)=>{
            let ctx = document.getElementById('MiniGraficoDistribucion').getContext('2d');

            if($chartMiniPorc.ctx != undefined){
                $chartMiniPorc.destroy();
            }
            $chartMiniPorc = new Chart(ctx, {
                type: 'doughnut',
                data: {
                    labels: dF[0],
                    datasets: [{
                        data: dF[1],
                        backgroundColor: dF[2],
                        hoverBackgroundColor: dF[2]
                    }]
                },
                options: {
                    responsive: false,
                    legend: {
                        display: false
                    }
                }
            });
        }
    }
})();

MacroCicloSeccion = (function(){
    return {
        bodyPorcentajesKilo: (arrCant, pTrainer, ix, kmsBase)=>{
            const colorClass = ["slider-warning", "slider-danger", "slider-success"];
            let all = `<div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12">`;
            for(let i=0; i<arrCant[ix];i++){
                all += `<div class="col-xs-2 col-sm-2 col-md-1 col-lg-1">`
                const fixVal = 100 - pTrainer.porcentajes[i];
                const windx = ix==0?i+1:ix==1?(i+1)+arrCant[ix-1]:(arrCant.reduce((a, b)=>{return a+b}))-arrCant[ix]+i+1;
                all +=`<div><label class="padding-10">${windx<10 ? '0'+windx : windx}</label></div>`;
                all +=`<div><span class="padding-5 text-align-left"><input type="text" class="slider ${colorClass[ix]}" value="" data-slider-min="0" data-slider-max="100" data-slider-step="2" data-slider-value="${fixVal}" data-slider-orientation="vertical" data-slider-selection="after" data-slider-handle="round" data-slider-tooltip="hide" data-index="${windx}" data-kms="${kmsBase}"></span></div>`;
                all +=`<div><label class="padding-5 perc hidden" data-index="${windx}">${pTrainer.porcentajes[i]}%</label></div>`;
                all +=`<div><label class="padding-5 kms" data-index="${windx}">${((kmsBase*pTrainer.porcentajes[i])/100).toFixed(1)}</label></div>`;
                all+= "</div>";
            }
            all+=`</div>`;
            return all;
        },

        bodyPorcentajesIntensidad: (arrCant, pTrainer, ix)=>{
            const colorClass = ["slider-warning", "slider-danger", "slider-success"];
            let all = `<div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12">`;
            for(let i=0; i<arrCant[ix];i++){
                all += `<div class="col-xs-2 col-sm-2 col-md-1 col-lg-1">`
                const fixVal = 100 - pTrainer.porcentajes[i];
                const windx = ix==0?i+1:ix==1?(i+1)+arrCant[ix-1]:(arrCant.reduce((a, b)=>{return a+b}))-arrCant[ix]+i+1;
                all +=`<div><label class="padding-10">${windx<10 ? '0'+windx : windx}</label></div>`;
                all +=`<div><span class="padding-5 text-align-left"><input type="text" class="slider ${colorClass[ix]}" value="" data-slider-min="0" data-slider-max="100" data-slider-step="2" data-slider-value="${fixVal}" data-slider-orientation="vertical" data-slider-selection="after" data-slider-handle="round" data-slider-tooltip="hide" data-index="${windx}" data-kms="50"></span></div>`;
                all +=`<div><label class="padding-5 perc-ints" data-index="${windx}">${pTrainer.porcentajes[i]}%</label></div>`;
                all+= "</div>";
            }
            all+=`</div>`;
            return all;
        }
    }
})();

MacroCicloGet = (function(){
    return {
        obtenerPorcentajesParaActualizacion: (base)=>{
            const distancia = Number(document.querySelector('#DistanciaRutina .chkDistancia:checked').value);
            const porcentajes = Array.from(document.querySelectorAll('.perc')).map(v=>{return Number(v.textContent.slice(0, -1))})
            return base.periodizacion.map((v, i)=>{
                return {indice: v-4, distancia: distancia, etapa:  i+1, porcentajes: porcentajes.splice(0, v).join()}
            })
        },
        obtenerPorcentajesIntens: (periodizacion)=>{
            const totSems = periodizacion.reduce((a, b) => {return a + b;})
            for(let i=0; i<totSems; i++){
                $porcentajesIntensidad.push(50);
            }
        }
    }
})();

FichaSeeder = (function(){
    return {
        instanciarValoresDemo: ()=>{
            const tiempoControl = document.querySelector('#TiempoControl');
            const factorDesentrenamientoControl = document.querySelector('#FactorDesentrenamientoControl');
            document.querySelector('#DistanciaControl').value = 4;
            tiempoControl.value = "00:21:00";
            document.querySelector('#CadenciaControl').value = 174;
            factorDesentrenamientoControl.value = 3;

            document.querySelector('#TiempoCompetencia').value = "04:10:00";
            document.querySelector('#CadenciaCompetencia').value = 185;
            document.querySelector('#TiempoDesentrControl').value =  String(tiempoControl.value.toSeconds() + (Number(tiempoControl.value.toSeconds()) * Number(factorDesentrenamientoControl.value).toPercentage())).toHHMMSSM();
        }
    }
})();

CalcProyecciones = (function(){
    return {
        calcular: (input, tipoProyeccion) => {
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
                const totSems = CalcProyecciones.calcularNumSemByPorcentaje(valor, ix);
                contProyecciones.querySelector(`${identificadores[0]}[data-index="${ix + 3}"]`).value = totSems;
            } else {
                const totPorc = CalcProyecciones.calcularPorcentajeByNumSem(valor, ix);
                contProyecciones.querySelector(`${identificadores[0]}[data-index="${ix - 3}"]`).value = totPorc;
            }

            const tot1 = CalcProyecciones.calcularTotalesDistribucion(tipoProyeccion, 1), tot2 = CalcProyecciones.calcularTotalesDistribucion(tipoProyeccion, 2);
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
        informacionSemanas: () => {
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
                obj.tipoCalculo = 1;//Las semanas 1 y ultima no son full(cuentan con menos de 7 días)
            else if(obj.totSemNonFull == 0)
                obj.tipoCalculo = 2;
            else if(obj.diasPrimeraSemana == 7 && obj.diasUltimaSemana != 7)
                obj.tipoCalculo = 3;
            else
                obj.tipoCalculo = 4;//Semanas regulares, todas con 7 días
            return obj;
        },
        calcularPorcentajeByNumSem: (numSem, index) => {
            const estadisticas = CalcProyecciones.informacionSemanas();
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
            const estadisticas = CalcProyecciones.informacionSemanas();
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
            } else if (situacion == 3){
                if(index == 2){
                    return (
                        ((porcentaje * estadisticas.totDias / 100) / 7) + 1 - estadisticas.diasUltimaSemana/7
                    ).toFixed(2);
                }else
                    return (((porcentaje * estadisticas.totDias) / 100) / 7).toFixed(2);
            } else if (situacion == 4){
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
    }
})();