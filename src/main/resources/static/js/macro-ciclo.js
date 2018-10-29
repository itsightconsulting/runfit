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
            MacroValidacion.cleanDistribucion();
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
            if(MacroValidacion.principal() && MacroValidacion.basicos()) {
                blockButton(e.target);

                const contenedorMK = document.querySelector('#PorcentajesKilometraje');//metricas de kilometraje
                const contenedorMK2 = document.querySelector('#PorcentajesIntensidad');//metricas de kilometraje

                if(contenedorMK.children.length == 1) {
                    contenedorMK.children[0].remove();
                }

                if(contenedorMK2.children.length == 1) {
                    contenedorMK2.children[0].remove();
                }
                document.querySelector('#TiempoDesentrControl').value =  String(document.getElementById('TiempoControl').value.toSeconds() + (Number(document.getElementById('TiempoControl').value.toSeconds()) * Number(document.getElementById('FactorDesentrenamientoControl').value).toPercentage())).toHHMMSSM();
                //Actualizando calculos bases en caso se hayan modificado algunos otros parametros
                Calc.setRestantes();
                const ritmosAerobicos = Calc.getRitmosAerobicos();
                $baseAfterComprobacion = FichaGet.obtenerBase();
                instanciarPorcentajesKilometraje($baseAfterComprobacion.distancia).then(porcentajes=>{
                    //Filtrando porcentajes
                    const porcentajesTrainer = [{},{},{},{}];
                    for(let i=0; i<porcentajesTrainer.length;i++){
                        if(i != 3)
                            porcentajesTrainer[i] = porcentajes.porcKiloTipos[i].semanas[$baseAfterComprobacion.periodizacion[i]-2];
                            //Explicacion(-2)
                            // - Uno es por que el indice de todo array comienza en 0
                            // - El último -uno es porque por regla de negocio cada periodo debe tener como mínimo 2 semanas entonces en base de datos se ha guardado como indice 0 la semana 2, indice 1 la semana 3 y así... por ello para evitar un arrayindexoutofboundsexception se resta - 2
                        else
                            porcentajesTrainer[i] = MacroCicloGet.obtenerPorcentajePT($baseAfterComprobacion.distancia);

                    }
                    contenedorMK.appendChild(htmlStringToElement(MacroCiclo.mostrarPorcentajesKilo($baseAfterComprobacion, porcentajesTrainer)));
                    contenedorMK2.appendChild(htmlStringToElement(MacroCiclo.mostrarPorcentajesIntensidad($baseAfterComprobacion, porcentajesTrainer)));
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
                    const arrTiempos = Calc.getDistribucionTiempoPlanificado($baseAfterComprobacion);
                    const copyArrTiempos = JSON.parse(JSON.stringify(arrTiempos));
                    const RCPs = [];
                    //Separando máximo y mínimos por etapa de preparación
                    $baseAfterComprobacion.periodizacion.forEach(v=>{
                        RCPs.push({first: copyArrTiempos[0].factor, last: copyArrTiempos[v-1].factor});
                        copyArrTiempos.splice(0, v);
                    });

                    document.querySelectorAll('#EstadisticasAdicionales .rcps').forEach((v,i)=>{
                        v.value = RCPs[i].last.substring(3);
                    });

                    const ritmosEntreAero = Calc.getRitmosEntrenamientoAerobico(ritmosAerobicos.actual, ritmosAerobicos.preCompetitivo, $baseAfterComprobacion);
                    let acc = 0;
                    document.querySelectorAll('#EstadisticasAdicionales .raps').forEach((v,i)=>{
                        v.value = ritmosEntreAero[(acc+$baseAfterComprobacion.periodizacion[i])-1].factor.substring(3);
                        acc+=$baseAfterComprobacion.periodizacion[i];
                    });

                    const cadenciaActual = document.querySelector('#CadenciaControl').value;
                    const cadenciaCompetencia = document.querySelector('#CadenciaCompetencia').value;
                    const ritmosCadencia = Calc.getRitmosCadenciaCompetencia(cadenciaActual, cadenciaCompetencia, $baseAfterComprobacion);

                    acc = 0;
                    document.querySelectorAll('#EstadisticasAdicionales .cdcs').forEach((v,i)=>{
                        v.value = ritmosCadencia[(acc+$baseAfterComprobacion.periodizacion[i])-1].factor;
                        acc+=$baseAfterComprobacion.periodizacion[i];
                    });

                    acc = 0;
                    const longitudesPaso = Calc.getLongitudesDePaso(arrTiempos, ritmosCadencia);
                    document.querySelectorAll('#EstadisticasAdicionales .lpcs').forEach((v,i)=>{
                        v.value = longitudesPaso[(acc+$baseAfterComprobacion.periodizacion[i])-1];
                        acc+=$baseAfterComprobacion.periodizacion[i];
                    });


                    const tcsActual = document.querySelector('#TcsControl').value;
                    const tcsCompetencia = document.querySelector('#TcsCompetencia').value;
                    const valoresTCSs = Calc.getTCSs(tcsActual, tcsCompetencia, $baseAfterComprobacion);
                    acc = 0;
                    document.querySelectorAll('#EstadisticasAdicionales .cdcs').forEach((v,i)=>{
                        v.value = valoresTCSs[(acc+$baseAfterComprobacion.periodizacion[i])-1].factor;
                        acc+=$baseAfterComprobacion.periodizacion[i];
                    });

                    //Metricas velocidades y factor de mejora
                    const mVC = RitmosSVYC. getMetricasVelocidades();
                    document.querySelector('#FactorMejoria').value = Calc.getFactorMejoria(mVC, $baseAfterComprobacion);

                    //Graficos - Información relacionada
                    MacroCiclo.instanciarInformacionTemporada($baseAfterComprobacion);
                    MCGrafico.temporada(MCGraficoData.paraTemporada($baseAfterComprobacion));
                    document.querySelector('#btnGenerarRutina').classList.remove('disabled');
                    document.querySelector('#btnGenerarRutina').setAttribute('title','Generar rutina');
                    unlockButton(e.target);
                })
            }
        },
        instanciarInformacionTemporada: (base)=>{
            base.periodizacion.push(base.distancia == 10 ? 1 : base.distancia == 21 ? 2 : 3);//42: 3 semanas;

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
        actualizarInformacionKilometrajeTemporada: ()=>{
            const base = $baseAfterComprobacion;
            const allKms = Array.from(document.querySelectorAll('#PorcentajesKilometraje label.kms')).map(v=>{return Number(v.textContent)});
            const sumKms = allKms.reduce((a,b)=>{return a+b});
            const kmsParts = base.periodizacion.map((v)=>{
                return allKms.splice(0, v);//Cada vez el arreglo va perdiendo elementos y por eso siempre hacemos que se corte desde 0
            });
            base.porcentajesKms = [];

            const kiloTotal = document.querySelector('#KilometrajeTotal');
            kiloTotal.querySelector('h1').textContent = parseFloat(sumKms).toFixed(1);
            document.querySelectorAll('#InicialMacro .dist-etapa').forEach((v,i)=>{
                const kmsEsp = parseFloat(kmsParts[i].reduce((a,b)=>{return a+b}))
                v.querySelector('h1').textContent = kmsEsp.toFixed(1);
                v.querySelector('span').textContent = base.periodizacion[i] +" semanas";
                base.porcentajesKms.push(((kmsEsp * 100) / sumKms).toFixed(2));
            });
            document.querySelector('#KilometrajeTotalTemporada').value = parseFloat(sumKms).toFixed(1);
            document.querySelector('#KilometrajePromedioSemanal').value = parseFloat(sumKms/base.numSem).toFixed(1);

            $chartMiniPorc.data.datasets[0].data[0] = base.porcentajesKms[0];
            $chartMiniPorc.data.datasets[0].data[1] = base.porcentajesKms[1];
            $chartMiniPorc.data.datasets[0].data[2] = base.porcentajesKms[2];
            $chartMiniPorc.update();
            MCGrafico.miniPorcentual(MCGraficoData.paraMini(base.porcentajesKms));
        },
        mostrarPorcentajesKilo: (base, pTrainer)=>{
            const nombresEtapa = ["Preparación General", "Preparación Específica", "Preparación Precompetitiva", "P. Tránsito"];
            const periodizacionFinal = JSON.parse(JSON.stringify(base.periodizacion));
            periodizacionFinal.push(MacroCicloGet.obtenerAdicionalSemsPT(base.distancia));//Plus
            let html = `<section class="">`;
            html += periodizacionFinal.map((v,i,k)=>{
                const kmsRef = $kilometrajeBase[i].kilometraje;
                return `<div class="col col-4 padding-0 text-align-center${i == 3 ? " hidden": ""}">
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
        generarRutinaCompleta: (e)=>{//Flujo básico

            if($('#frm_registro').valid() && $('#KilometrajePromedioSemanal').val() != ""){
                const $chelmoMacro = [];
                //Semanas
                const numSemBase = FichaGet.obtenerBase().numSem;
                const numSemBaseIx = numSemBase -1;
                const cantSemExcedentes = ($baseAfterComprobacion.distancia == 10 ? 1 : $baseAfterComprobacion.distancia == 21 ? 2 : 3);
                const totalSemanas = numSemBase + cantSemExcedentes;
                const fIni = parseFromStringToDate(document.querySelector('#MacroFechaInicio').value);
                let fFin = parseFromStringToDate(document.querySelector('#MacroFechaFin').value);
                fFin = parseFromStringToDate2(moment(fFin).add((cantSemExcedentes*7), 'd').format('DD/MM/YYYY'));

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
                let emptyAvanceSemanas = new Array(numSemBase);
                for(let i=0; i<numSemBase;i++){
                    emptyAvanceSemanas[i] = "";
                }
                r.control.avanceSemanas = emptyAvanceSemanas;
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
                        if(fix < numSemBase)
                            return {nombre: v.nombre, min: v.pMin, max: v.pMax, indicadores: {max: v.indicadores[fix].max, min: v.indicadores[fix].min}}
                       else
                            return {nombre: v.nombre, min: v.pMin, max: v.pMax, indicadores: {max: v.indicadores[numSemBaseIx].max, min: v.indicadores[numSemBaseIx].min}}
                    }));
                    r.semanas[fix].metricasVelocidad = JSON.stringify(mVC.map(v=>{
                        if(fix < numSemBase)
                            return {parcial: v.indicadores[fix].p};
                        else
                            return {parcial: v.indicadores[numSemBaseIx].p};
                    }));
                })
                r.totalSemanas = Number(r.totalSemanas)+cantSemExcedentes;
                guardarRutina(r, (btn = e.target));
            } else{
                $.smallBox({color: "alert", content: "Primero debes generar el macro..."});
            }
        },
    }
})();

MacroValidacion = (function(){
    return {
        principal: ()=>{
            let validado = false;
            if(MacroValidacion.proyeccionesCompletas()){
                validado = true;
                const totSem = document.querySelector('#MacroTotalSemanas').textContent.trim();
                const totSemPerio = Number(document.querySelector('#TotalPeriodizacion2').value.trim());
                if(validado && FichaGet.obtenerNivelAtleta() != undefined && totSem == totSemPerio){
                    return true;
                }
                return validado;
            }else{
                $.smallBox({color: "alert", content: "Validación fallida"});
                return false;
            }
        },
        proyeccionesCompletas:()=>{
            const proy = FichaDOMQueries.getProyecciones();
            return proy.querySelector('#TotalPeriodizacion1').parentElement.classList.contains('state-success') && proy.querySelector('#TotalVelocidad1').parentElement.classList.contains('state-success') && proy.querySelector('#TotalCadencia1').parentElement.classList.contains('state-success') && proy.querySelector('#TotalTcs1').parentElement.classList.contains('state-success') ? true : false;
        },
        onEdicionProyecciones:(input, valor, tipo, tipoProy)=>{
            if(TipoDato.PORCENTUAL == tipo){
                if(valor <= 100)
                    return true;
                else
                    MacroValidacion.onEdicProyComun(input, tipo, tipoProy);

            }

            if(TipoDato.NUMERICO == tipo){
                if(valor <= 28)
                    return true;
                else
                    MacroValidacion.onEdicProyComun(input, tipo, tipoProy);
            }
        },
        onEdicProyComun: (input, tipo, tipoProy)=>{
            const ix = Number(input.getAttribute('data-index'));
            input.value = 0;
            if(tipoProy == 2 || tipoProy == 3 || tipoProy == 4){}else{tipo == TipoDato.PORCENTUAL ? input.parentElement.parentElement.nextElementSibling.querySelector('input').value = 0 : input.parentElement.parentElement.previousElementSibling.querySelector('input').value = 0;}
            const msg =  tipo == TipoDato.PORCENTUAL ? htmlStringToElement(`<em>Máximo: <b>100</b></em>`) : htmlStringToElement(`<em>Máximo por etapa: <b>28</b></em>`);
            input.nextElementSibling == undefined ? input.parentElement.append(msg) : '';
            const contProyecciones = FichaDOMQueries.getProyecciones();
            let idsProy = MacroCicloGet.obtenerIdsProyeccionesByTipo(tipoProy);
            const t1 = CalcProyecciones.calcularTotalesDistribucion(contProyecciones, tipoProy, 1);
            const t2 = CalcProyecciones.calcularTotalesDistribucion(contProyecciones, tipoProy, 2);
            const contT1 = contProyecciones.querySelector(`${idsProy[1]}`);
            const contT2 = contProyecciones.querySelector(`${idsProy[2]}`);
            contT1.value = t1;
            if(tipoProy == 2 || tipoProy == 3 || tipoProy == 4){}else{contT2.value = t2;}
            if(t1>=99.99 && t1<=100.1){
                contT1.parentElement.classList.add('state-success');contT1.parentElement.classList.remove('state-error');
                contT2.parentElement.classList.add('state-success');contT2.parentElement.classList.remove('state-error');
            }else{
                contT1.parentElement.classList.add('state-error');contT1.parentElement.classList.remove('state-success');
                contT2.parentElement.classList.add('state-error');contT2.parentElement.classList.remove('state-success');
            }
            if(tipoProy == 2 || tipoProy == 3 || tipoProy == 4){}else{
                contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(2)[0]}[data-index="${ix + (tipo == 1 ? +3:0)}"]`).value = 0;
                contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(3)[0]}[data-index="${ix + (tipo == 1 ? +3:0)}"]`).value = 0;
                contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(4)[0]}[data-index="${ix + (tipo == 1 ? +3:0)}"]`).value = 0;

                contProyecciones.querySelector('#TotalVelocidad2').value = t2;
                contProyecciones.querySelector('#TotalCadencia2').value = t2;
                contProyecciones.querySelector('#TotalTcs2').value = t2;
            }
            setTimeout(() => msg.remove(), 2000);
        },
        basicos: ()=>{
            //Datos Generales: 1era columna
            document.querySelector('#FrecuenciaCardiacaMinima').value;// > 0
            document.querySelector('#FrecuenciaCardiacaMaxima').value;// > 0
            document.querySelector('#NivelAtleta input:checked').value; //validos [1, 2, 3]
            document.querySelector('#DistanciaRutina input:checked').value // validos [10, 21, 42];
            document.querySelector('#MacroFechaInicio').value // > hoy;
            document.querySelector('#MacroFechaFin').value; // > Fecha Inicio;
            document.querySelector('#MacroTotalSemanas').textContent;// Mínimo 6 semanas
            //MacroTotalSemanas tiene que ser mayor o igual al resultado de la siguiente linea
            //Math.ceil(moment(parseFromStringToDate(document.querySelector('#MacroFechaFin').value)).diff(moment(parseFromStringToDate(document.querySelector('#MacroFechaInicio').value)), 'd')/7)-1;

            //Datos Generales-> Distancias base 1: 2da columna
            document.querySelector('#DistanciaControl').value; // validos [2, 4, 10, 21, 42]
            document.querySelector('#TiempoControl').value.toSeconds();// Mayor que 120 seg
            document.querySelector('#CadenciaControl').value; // Mayor que 0
            document.querySelector('#TcsControl').value; //Mayor que 0
            document.querySelector('#FactorDesentrenamientoControl').value;// Mayor que 0
            document.querySelector('#TiempoDesentrControl').value.toSeconds();// Calculado, mayor que #TiempoControl seg
            //Datos Generales-> Distancias base 2: 3da columna
            document.querySelector('#DistanciaCompetencia').value; // validos [10, 21, 42]
            document.querySelector('#TiempoCompetencia').value; // [{10: >25min}, {21: > 01:04:21}, {42: > 02:00:10}]
            document.querySelector('#CadenciaCompetencia').value; // Mayor que 0
            document.querySelector('#TcsCompetencia').value; // Mayor que 0
            document.querySelector('#FactorMejoria').value;//Calculado, mayor que 0

            //Datos Generales->Bloque datos calculados
            document.querySelector('#RitmoXKilometro'); // Mayor que 120 seg
            document.querySelector('#RitmoCompetenciaActual'); //Mayor que 120 seg
            document.querySelector('#RitmoAerobicoActual'); //Mayor que 120 seg
            document.querySelector('#RitmoAerobicoPreComp'); //Mayor que 120 seg
            document.querySelector('#LongitudPasoCA'); // Mayor que 0
            document.querySelector('#KilometrajeTotalTemporada'); // Mayor que 0
            document.querySelector('#KilometrajePromedioSemanal'); // Mayor que 0 && menor que KilometrajeTotalTemporada

            document.querySelectorAll('#EstadisticasAdicionales .rcps'); // Mayor que 0 seg
            document.querySelectorAll('#EstadisticasAdicionales .raps'); // Mayor que 0 seg
            document.querySelectorAll('#EstadisticasAdicionales .cdcs'); // Mayor que 0 seg
            document.querySelectorAll('#EstadisticasAdicionales .lpcs'); // Mayor que 0 seg
            return true;
        },
        cleanDistribucion: ()=>{
            document.querySelectorAll('.periodizacion-calc').forEach(v=>v.value = 0);
            document.querySelectorAll('.velocidad-calc').forEach(v=>v.value = 0);
            document.querySelectorAll('.cadencia-calc').forEach(v=>v.value = 0);
            document.querySelectorAll('.tcs-calc').forEach(v=>v.value = 0);
            //#1
            const t1 = document.querySelector('#TotalPeriodizacion1');
            t1.value = 0;
            t1.parentElement.classList.remove('state-success');
            const t11 =  document.querySelector('#TotalPeriodizacion2');
            t11.value = 0;
            t11.parentElement.classList.remove('state-success');
            //#2
            const t2 = document.querySelector('#TotalVelocidad1');
            t2.value = 0;
            t2.parentElement.classList.remove('state-success');
            const t22 =  document.querySelector('#TotalVelocidad2');
            t22.value = 0;
            t22.parentElement.classList.remove('state-success');
            //#3
            const t3 = document.querySelector('#TotalCadencia1');
            t3.value = 0;
            t3.parentElement.classList.remove('state-success');
            const t33 =  document.querySelector('#TotalCadencia2');
            t33.value = 0;
            t33.parentElement.classList.remove('state-success');
            //#4
            const t4 = document.querySelector('#TotalTcs1');
            t4.value = 0;
            t4.parentElement.classList.remove('state-success');
            const t44 =  document.querySelector('#TotalTcs2');
            t44.value = 0;
            t44.parentElement.classList.remove('state-success');
        },
        formulario: ()=>{
            $.validator.addMethod('validos', function (value, element, param) {//segundos
                return param.includes(Number(value));
            }, '> Valor inválido');

            $.validator.addMethod('csTiempo', function (value, element, param) {//segundos
                return value.toSeconds() > param.toSeconds();
            }, '> {0}');

            $.validator.addMethod("eqGreaterThanToday",
                function(value) {
                    if (!/Invalid|NaN/.test(new Date(value))) {
                        return parseFromStringToDate(value) >= new Date().setHours(0, 0, 0, 0);
                    }
                    return isNaN(value) && isNaN($(value).val());
                },'Debe ser mayor a la fecha de hoy');

            $.validator.addMethod("greaterThanDate",
                function(value, element, params) {
                    if (!/Invalid|NaN/.test(new Date(value))) {
                        return new Date(value) > new Date($(params).val());
                    }
                    return isNaN(value) && isNaN($(params).val())
                        || (Number(value) > Number($(params).val()));
                }, 'Debe ser mayor a la fecha inicio');

            $.validator.addMethod("miniumDifference",
                function(value, element, params) {
                    if (!/Invalid|NaN/.test(new Date(value))) {
                        return moment(parseFromStringToDate(value)).diff(parseFromStringToDate($(params).val()), 'days') > 40;//7 weeks
                    }
                    return isNaN(value) && isNaN($(params).val())
                        || (Number(value) > Number($(params).val()));
                }, 'Rutinas mínimas de 6 semanas, es por ello que se requiere una fecha mayor');

            $("#frm_registro").validate({
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
                    FrecuenciaCardiacaMinima: {
                        required: true,
                        min: 0,
                        maxlength: 3,
                    },
                    FrecuenciaCardiacaMaxima: {
                        required: true,
                        min: 0,
                        maxlength: 3,
                    },
                    NivelAtleta : {
                        required: true,
                    },
                    DistanciaRutina : {
                        required: true,
                    },
                    MacroFechaInicio: {
                        required: true,
                        eqGreaterThanToday: true,
                    },
                    MacroFechaFin: {
                        required: true,
                        miniumDifference: "#MacroFechaInicio",
                        greaterThanDate: "#MacroFechaInicio",
                    },
                    DistanciaControl: {
                        required: true,
                        validos: [2, 4, 10, 21, 42],
                    },
                    TiempoControl: {
                        required: true,
                        csTiempo: "00:01:30",
                    },
                    CadenciaControl: {
                        required: true,
                        min: 1,
                    },
                    TcsControl: {
                        required: true,
                        min: 1
                    },
                    FactorDesentrenamientoControl: {
                        required: true,
                        min: 1,
                    },
                    TiempoDesentrControl: {
                        required: true,
                        csTiempo: function(){
                            return $('#TiempoControl').val();
                        },

                    },
                    DistanciaCompetencia: {
                        required: true,
                        validos: [10, 21, 42],
                    },
                    TiempoCompetencia: {
                        required: true,
                        csTiempo: function(){
                            return $('#DistanciaCompetencia').val() == "10" ? "00:25:00" : $('#DistanciaCompetencia').val() == "21" ? "01:04:21" : "02:00:10";
                        },
                    },
                    CadenciaCompetencia: {
                        required: true,
                        min: 0,
                    },
                    TcsCompetencia: {
                        required: true,
                        min: 0,
                    },
                    FactorMejoria: {
                        required: true,
                        min: 0.00001,
                    }
                },
                messages: {
                    FrecuenciaCardiacaMinima: {
                        required: "El campo es obligatorio",
                        min: $.validator.format("Valor mínimo 0"),
                        maxlength: $.validator.format("Este campo debe de tener como máximo {0} caracteres.")
                    },
                    FrecuenciaCardiacaMaxima: {
                        required: "El campo es obligatorio",
                        min: $.validator.format("Valor mínimo 0"),
                        maxlength: $.validator.format("Este campo debe de tener como máximo {0} caracteres.")
                    },
                    NivelAtleta : {
                        required: "El campo es obligatorio",
                    },
                    DistanciaRutina : {
                        required: "El campo es obligatorio",
                    },
                    MacroFechaInicio: {
                        required: "El campo es obligatorio",
                    },
                    MacroFechaFin: {
                        required: "El campo es obligatorio",
                    },
                    DistanciaControl: {
                        required: "El campo es obligatorio",
                    },
                    TiempoControl: {
                        required: "El campo es obligatorio",
                    },
                    CadenciaControl: {
                        required: "El campo es obligatorio",
                        min: "Mínimo valor {0}",
                    },
                    TcsControl: {
                        required: "El campo es obligatorio",
                        min: "Mínimo valor {0}",
                    },
                    FactorDesentrenamientoControl: {
                        required: "El campo es obligatorio",
                        min: "Mínimo valor {0}",
                    },
                    TiempoDesentrControl: {
                        required: "El campo es obligatorio",
                    },
                    DistanciaCompetencia: {
                        required: "El campo es obligatorio",
                    },
                    TiempoCompetencia: {
                        required: "El campo es obligatorio",
                    },
                    CadenciaCompetencia: {
                        required: "El campo es obligatorio",
                        min: "Mínimo valor {0}",
                    },
                    TcsCompetencia: {
                        required: "El campo es obligatorio",
                        min: "Mínimo valor {0}",
                    },
                    FactorMejoria: {
                        required: "El campo es obligatorio",
                        min: "Mínimo valor {0}",
                    }

                },
                submitHandler: function () {

                }
            });
        }
    }
})();

MCGraficoData = (function(){
    return {
        paraTemporada: (objBase)=>{
            const dis1 = objBase.periodizacion[0];
            const dis2 = dis1 + objBase.periodizacion[1];
            const dis3 = dis2 + objBase.periodizacion[2];

            //Dis kilometraje
            const data = Array.from(document.querySelectorAll('#PorcentajesKilometraje label.kms')).map((v,i)=>{
                const c = i < dis1 ? "rgba(131, 197, 255, 0.2)" : i < dis2 ? "rgba(232, 107, 10, 0.2)" : i < dis3 ? "rgba(164, 247, 144, 0.2)" : "rgba(74, 78, 59, 0.2)";
                return {numSem: i+1, kms: Number(v.textContent), color: c};
            });

            //Intensidad
            document.querySelectorAll('#PorcentajesIntensidad label.perc-ints').forEach((v,i)=>{
                data[i].perc = v.textContent.slice(0,-1);
            })
            for(let i=dis3; i<(dis3+objBase.periodizacion[3]); i++){
                data[i].perc = 0;
            }

            //Reconstruyendo Semanas
            const t = getOnlyDate();//Pendiente
            RutinaGet.getRegeneracionSemanas(moment(objBase.fechaInicio).format('DD/MM/YYYY'), moment(objBase.fechaFin).format('DD/MM/YYYY'), objBase.numSem).forEach((v, i, k)=>{
                $fechasCompetencia.forEach(w=>{
                    if(v.fechaInicio<=w.fecha && v.fechaFin>=w.fecha)
                        data[i].bullet = w.prioridad == 1 ? `${_ctx}img/gold-medal.png` : `${_ctx}img/silver-medal.png`;
                })

                if(t>=v.fechaFin)
                    data[i].avance=0;
                else if(t>=v.fechaInicio)
                    data[i].avance=0;
            })
            return data;
        },
        paraMini: (porcentajes)=>{
            const lenP = porcentajes.length;
            const data = [{title: "General", value: porcentajes[0], color: "#E6F3FF"}, {title: "Específica", value: porcentajes[1], color: "#FAE1CE"}, {title: "Precompetitiva", value: porcentajes[2], color: "#EDFDE9"}, {title: "Tránsito", value: porcentajes[3], color: "#4A4E3B5E"}];
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
            const avances =  data.filter(v=>{//Provisional
                return (v.avance != undefined)
            }).map(({avance})=>avance);
            avances.push(0);//Por conveniencia(estética)

            document.querySelector('#InicialMacro').classList.remove('hidden');
            document.querySelector('#ContainerVarVolumen').classList.remove('hidden');
            Chart.controllers.LineNoOffset = Chart.controllers.line.extend({
                updateElement: function(point, index, reset) {
                    Chart.controllers.line.prototype.updateElement.call(this, point, index, reset);
                    const meta = this.getMeta();
                    const xScale = this.getScaleForId(meta.xAxisID);
                    point._model.x = xScale.getPixelForValue(undefined, index-0.5);
                },
            });

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
                    },
                    beforeDraw: function(chartInstance) {
                        // check and see if the plugin is active (its active if the option exists)
                        if (chartInstance.config.options.tooltips.onlyShowForDatasetIndex) {
                            // get the plugin configuration
                            var tooltipsToDisplay = chartInstance.config.options.tooltips.onlyShowForDatasetIndex;

                            // get the active tooltip (if there is one)
                            var active = chartInstance.tooltip._active || [];

                            // only manipulate the tooltip if its just about to be drawn
                            if (active.length > 0) {
                                // first check if the tooltip relates to a dataset index we don't want to show
                                if (tooltipsToDisplay.indexOf(active[0]._datasetIndex) === -1) {
                                    // we don't want to show this tooltip so set it's opacity back to 0
                                    // which causes the tooltip draw method to do nothing
                                    chartInstance.tooltip._model.opacity = 0;
                                }
                            }
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
                    }, {
                        label: 'Progreso',
                        data: avances,
                        yAxisID: 'y-axis-3',
                        // Changes this dataset to become a line
                        type: 'LineNoOffset',
                        borderWidth: 3,
                        borderColor: '#f0da1c',
                        backgroundColor: '#f0da1c',
                        pointBorderColor: '#f0da1c',
                        pointBackgroundColor: '#f0da1c',
                        pointHoverBackgroundColor: 'transparent',
                        pointHoverBorderColor: 'transparent',
                        pointRadius: 0,
                    }]
                },
                borderWidth: 1,
                options: {
                    maintainAspectRatio: false,
                    tooltips: {
                        yAlign: 'bottom',
                        xAlign: 'center',
                        onlyShowForDatasetIndex: [0, 1],
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
                                beginAtZero: true,
                            }
                        }, {
                            id: 'y-axis-2',
                            type: 'linear',
                            position: 'right',
                            gridLines: {
                                color: "#F1F9FD",
                                /*display: false,*/

                            },
                            ticks: {
                                display: false,
                                suggestedMin: 0,
                                suggestedMax: 100,   // minimum will be 0, unless there is a lower value.
                                // OR //
                                beginAtZero: true,   // minimum value will be 0.
                                fontColor: "rgba(74, 78, 59, 1)",
                                callback: function(label) {
                                    return label + "%";
                                }
                            },
                        }, {
                            id: 'y-axis-3',
                            type: 'linear',
                            position: 'right',
                            gridLines: {
                                display: false,
                            },
                            ticks: {
                                display: false,
                                suggestedMin: 0,
                                beginAtZero: true,   // minimum value will be 0.
                            },
                        }],
                        xAxes: [{
                            ticks: {
                                userCallback: function(item, index) {
                                    if (!(index % 4)) return item;
                                },
                                autoSkip: false,
                                fontColor: "rgba(74, 78, 59, 1)"
                            },
                        }],
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
                all +=`<div><label class="padding-5 kms" data-index="${windx}">${((kmsBase*pTrainer.porcentajes[i])/100).toFixed(1)}</label></div>`;
                all +=`<div><span class="padding-5 text-align-left"><input type="text" class="slider ${colorClass[ix]}" value="" data-slider-min="0" data-slider-max="100" data-slider-step="2" data-slider-value="${fixVal}" data-slider-orientation="vertical" data-slider-selection="after" data-slider-handle="round" data-slider-tooltip="hide" data-index="${windx}" data-kms="${kmsBase}"></span></div>`;
                all +=`<div><label class="padding-5 perc hidden" data-index="${windx}">${pTrainer.porcentajes[i]}%</label></div>`
                all +=`<div><label class="padding-10">${windx<10 ? '0'+windx : windx}</label></div>`;
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
                all +=`<div><label class="padding-5 perc-ints" data-index="${windx}">${pTrainer.porcentajes[i]}%</label></div>`;
                all +=`<div><span class="padding-5 text-align-left"><input type="text" class="slider ${colorClass[ix]}" value="" data-slider-min="0" data-slider-max="100" data-slider-step="2" data-slider-value="${fixVal}" data-slider-orientation="vertical" data-slider-selection="after" data-slider-handle="round" data-slider-tooltip="hide" data-index="${windx}" data-kms="50"></span></div>`;
                all +=`<div><label class="padding-10">${windx<10 ? '0'+windx : windx}</label></div>`;
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
                return {indice: v-2, distancia: distancia, etapa:  i+1, porcentajes: porcentajes.splice(0, v).join()}
                //Explicacion(-2)
                // - Uno es por que el indice de todo array comienza en 0
                // - El último -uno es porque por regla de negocio cada periodo debe tener como mínimo 2 semanas entonces en base de datos se ha guardado como indice 0 la semana 2, indice 1 la semana 3 y así... por ello para evitar un arrayindexoutofboundsexception se resta - 2
            })
        },
        obtenerPorcentajesIntens: (periodizacion)=>{
            const totSems = periodizacion.reduce((a, b) => {return a + b;})
            for(let i=0; i<totSems; i++){
                $porcentajesIntensidad.push(50);
            }
        },
        obtenerPorcentajePT: (dis)=>{
            return BaseCalculo.porcentajesPeriodoTransito[dis == 10 ? 0 : dis == 21 ? 2 : 3]
        },
        obtenerAdicionalSemsPT: (dis)=>{
            return dis == 10 ? 1 : dis == 21 ? 2 : 3;
        },
        obtenerIdsProyeccionesByTipo: (tipoProyeccion)=>{
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
            return identificadores;
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
            document.querySelector('#TcsControl').value = 182;


            document.querySelector('#TiempoCompetencia').value = "04:10:00";
            document.querySelector('#CadenciaCompetencia').value = 185;
            document.querySelector('#TiempoDesentrControl').value =  String(tiempoControl.value.toSeconds() + (Number(tiempoControl.value.toSeconds()) * Number(factorDesentrenamientoControl.value).toPercentage())).toHHMMSSM();
            document.querySelector('#TcsCompetencia').value = 191;
        }
    }
})();

CalcProyecciones = (function(){
    return {
        calcular: (input, tipoProyeccion) => {
            if(!input.hasAttribute('readonly')) {
                const valor = input.value;
                const tipo = input.getAttribute('data-type');
                if (MacroValidacion.onEdicionProyecciones(input, valor, tipo, tipoProyeccion)) {
                    const ix = Number(input.getAttribute('data-index'));
                    const contProyecciones = FichaDOMQueries.getProyecciones();
                    const totSem = document.querySelector('#MacroTotalSemanas').textContent;
                    let identificadores = MacroCicloGet.obtenerIdsProyeccionesByTipo(tipoProyeccion);

                    if (tipo == 1) {
                        const totSems = CalcProyecciones.calcularNumSemByPorcentaje(valor, ix);
                        if (tipoProyeccion == 2 || tipoProyeccion == 3 || tipoProyeccion == 4) {
                        } else {
                            contProyecciones.querySelector(`${identificadores[0]}[data-index="${ix + 3}"]`).value = totSems;
                            contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(2)[0]}[data-index="${ix + 3}"]`).value = totSems;
                            contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(3)[0]}[data-index="${ix + 3}"]`).value = totSems;
                            contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(4)[0]}[data-index="${ix + 3}"]`).value = totSems;
                        }
                    } else {
                        const totPorc = CalcProyecciones.calcularPorcentajeByNumSem(valor, ix);
                        contProyecciones.querySelector(`${identificadores[0]}[data-index="${ix - 3}"]`).value = totPorc;
                        contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(2)[0]}[data-index="${ix}"]`).value = valor;
                        contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(3)[0]}[data-index="${ix}"]`).value = valor;
                        contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(4)[0]}[data-index="${ix}"]`).value = valor;
                    }

                    const tot1 = CalcProyecciones.calcularTotalesDistribucion(contProyecciones, tipoProyeccion, 1),
                        tot2 = CalcProyecciones.calcularTotalesDistribucion(contProyecciones, tipoProyeccion, 2);
                    const eleTot1 = contProyecciones.querySelector(`${identificadores[1]}`),
                        eleTot2 = contProyecciones.querySelector(` ${identificadores[2]}`);

                    contProyecciones.querySelector('#TotalVelocidad2').value = tot2;
                    contProyecciones.querySelector('#TotalCadencia2').value = tot2;
                    contProyecciones.querySelector('#TotalTcs2').value = tot2;

                    eleTot1.value = tot1;
                    eleTot2.value = tot2;
                    if (tot1 >= 99.99 && tot1 <= 100.1) {
                        eleTot1.parentElement.classList.add('state-success');
                        eleTot1.parentElement.classList.remove('state-error');

                    } else {
                        eleTot1.parentElement.classList.add('state-error');
                        eleTot1.parentElement.classList.remove('state-success');
                    }
                    if (Number(tot2) == Number(totSem)) {
                        eleTot2.parentElement.classList.add('state-success');
                        eleTot2.parentElement.classList.remove('state-error');
                    } else {
                        eleTot2.parentElement.classList.add('state-error');
                        eleTot2.parentElement.classList.remove('state-success');
                    }
                }
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
        calcularTotalesDistribucion: (contProy, tipoCat, tipoCalc) => {
            let acum = 0;
            switch (tipoCat) {
                case 1:
                    Array.from(contProy.querySelectorAll(`.periodizacion-calc[data-type="${tipoCalc}"]`)).forEach(v =>{
                        acum += !isNaN(v.value) ? Number(v.value) : 0;
                    });
                    break;
                case 2:
                    Array.from(contProy.querySelectorAll(`.velocidad-calc[data-type="${tipoCalc}"]`)).forEach(v =>{
                        acum += !isNaN(v.value) ? Number(v.value) : 0;
                    });
                    break;
                case 3:
                    Array.from(contProy.querySelectorAll(`.cadencia-calc[data-type="${tipoCalc}"]`)).forEach(v =>{
                        acum += !isNaN(v.value) ? Number(v.value) : 0;
                    });
                    break;
                default:
                    Array.from(contProy.querySelectorAll(`.tcs-calc[data-type="${tipoCalc}"]`)).forEach(v =>{
                        acum += !isNaN(v.value) ? Number(v.value) : 0;
                    });
            }
            return acum.toFixed(2);
        },
    }
})();