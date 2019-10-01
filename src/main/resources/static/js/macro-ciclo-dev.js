let $porcentajesIntensidad = [];

Ficha = (function(){
    return {
        instanciar: (ficha)=>{

            const comps =JSON.parse(ficha.competencias);
            const condicionAnatomica = JSON.parse(ficha.condicionAnatomica);
            $fechasCompetencia = comps.map(v=>{return {nombre: v.nombre, prioridad: v.prioridad, fecha: parseFromStringToDate2(v.fecha)}}).sort((a, b)=>{return a.fecha - b.fecha;});

            $('#Nombres').val(atob(getParamFromURL("nm")));
            $('#ApellidoPaterno').val(atob(getParamFromURL("nm")));
            $('#FechaNacimiento').val(atob(getParamFromURL("fn")));
            $('#Edad').val(calcularEdadByFechaNacimiento($('#FechaNacimiento').val()));
            $('#FrecuenciaCardiacaMinima').val(condicionAnatomica.frecuenciaCardiaca);
            $('#FrecuenciaCardiacaMaxima').val(condicionAnatomica.frecuenciaCardiacaMaxima);
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
        },
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
            basico.periodizacion = Array.from(proyecciones.querySelectorAll('.periodizacion-calc[data-type="2"]')).map(v=>{if(v.value>0) return Number(v.value)});
            basico.distribucionPorcentaje = Array.from(proyecciones.querySelectorAll('.periodizacion-calc[data-type="1"]')).map(v=>{if(v.value>0) return Number(v.value)/100;});
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
            console.log(comps);
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
            document.querySelector('#MacroTotalSemanas').textContent = diasPrimeraSemana == 7 ? Math.floor(totDias / 7) : 1 + Math.ceil((totDias - diasPrimeraSemana) / 7);
            MacroValidacion.cleanDistribucion();
        },
        instanciarDatosFicha: (ficha)=>{
            const comps = ficha.competencias;
            $fechasCompetencia = comps.map(v=>{return {nombre: v.nombre, prioridad: v.prioridad, fecha: parseFromStringToDate2(v.fecha)}}).sort((a, b)=>{return a.fecha - b.fecha;});
            document.querySelector('#Nombres').value = atob(getParamFromURL("nm"));
            document.querySelector('#ApellidoPaterno').value = atob(getParamFromURL("nm"));
            document.querySelector('#FechaNacimiento').value = atob(getParamFromURL("fn"));
            document.querySelector('#Edad').value = calcularEdadByFechaNacimiento(document.querySelector('#FechaNacimiento').value);
            document.querySelector('#MacroFechaFin').value = getFechaFormatoString(FichaGet.obtenerMaximaFechaCompeticiones($fechasCompetencia));
            document.querySelector('#Sexo').value = ficha.sexo == 1 ? "Masculino" : "Femenino";
            FichaSet.setMaximoDistanciaCompetencia(comps);
            FichaSet.setTotalSemanas();
            tbCompetencias.appendChild(FichaSeccion.newListadoCompetencias(comps));
        },
        instanciarConsolidado: (consolidado)=>{

            $ruConsolidado = consolidado;
            const general = consolidado.general;
            const stats = consolidado.stats;
            const mejoras = consolidado.mejoras;

            FichaSet.nivelAtleta(general.nvAtleta);

            document.querySelector('#FrecuenciaCardiacaMinima').value = general.freMin;
            document.querySelector('#FrecuenciaCardiacaMaxima').value = general.freMax;

            document.querySelector('#DistanciaControl').value = general.disControl;
            document.querySelector('#TiempoControl').value = general.tieControl;
            document.querySelector('#CadenciaControl').value = general.cadControl;
            document.querySelector('#TcsControl').value = general.tcsControl;
            document.querySelector('#FactorDesentrenamientoControl').value = general.cadControl;
            document.querySelector('#TiempoDesentrControl').value = general.tieDesControl;

            document.querySelector('#TiempoCompetencia').value = general.tieCompetencia;
            document.querySelector('#CadenciaCompetencia').value = general.cadCompetencia;
            document.querySelector('#TcsCompetencia').value = general.tcsCompetencia;
            document.querySelector('#FactorMejoria').value = general.facMejoria;

            document.querySelector('#RitmoXKilometro').value = stats.ritmoXkm;
            document.querySelector('#RitmoCompetenciaActual').value = stats.ritCompActual;
            document.querySelector('#RitmoAerobicoActual').value = stats.ritAerActual;
            document.querySelector('#RitmoAerobicoPreComp').value = stats.ritAerPreComp;

            document.querySelector('#LongitudPasoCA').value = stats.lonPasoCompActual;
            document.querySelector('#KilometrajeTotalTemporada').value = stats.kilometrajeTotal;
            document.querySelector('#KilometrajePromedioSemanal').value = stats.kilometrajeProm;

            document.querySelector('#PasoSubida').value = stats.pasoSubida;
            document.querySelector('#PasoBajada').value = stats.pasoBajada;
            document.querySelector('#PasoPlano').value = stats.pasoPlano;

            const cEstadsAdic = document.querySelector('#EstadisticasAdicionales');
            stats.rcps.split("|").forEach((v,i)=>cEstadsAdic.querySelectorAll('.rcps')[i].value = v== "0" ? "00:00" : v);
            stats.raps.split("|").forEach((v,i)=>cEstadsAdic.querySelectorAll('.raps')[i].value = v== "0" ? "00:00" : v);
            stats.cdcs.split("|").forEach((v,i)=>cEstadsAdic.querySelectorAll('.cdcs')[i].value = v== "0" ? 0 : v);
            stats.lpcs.split("|").forEach((v,i)=>cEstadsAdic.querySelectorAll('.lpcs')[i].value = v== "0" ? 0 : v);

            const proy = FichaDOMQueries.getProyecciones();
            const totSems = mejoras.semGe + mejoras.semEs + mejoras.semPr;
            proy.querySelector('.periodizacion-calc[data-index="0"]').value = mejoras.porcGe;
            proy.querySelector('.periodizacion-calc[data-index="1"]').value = mejoras.porcEs;
            proy.querySelector('.periodizacion-calc[data-index="2"]').value = mejoras.porcPr;
            proy.querySelector('#TotalPeriodizacion1').value = 100;

            proy.querySelector('.periodizacion-calc[data-index="3"]').value = mejoras.semGe;
            proy.querySelector('.periodizacion-calc[data-index="4"]').value = mejoras.semEs;
            proy.querySelector('.periodizacion-calc[data-index="5"]').value = mejoras.semPr;
            proy.querySelector('#TotalPeriodizacion2').value = totSems;

            proy.querySelector('.velocidad-calc[data-index="0"]').value = mejoras.velPorcGe;
            proy.querySelector('.velocidad-calc[data-index="1"]').value = mejoras.velPorcEs;
            proy.querySelector('.velocidad-calc[data-index="2"]').value = mejoras.velPorcPr;
            proy.querySelector('.velocidad-calc[data-index="3"]').value = mejoras.semGe;
            proy.querySelector('.velocidad-calc[data-index="4"]').value = mejoras.semEs;
            proy.querySelector('.velocidad-calc[data-index="5"]').value = mejoras.semPr;
            proy.querySelector('#TotalVelocidad1').value = 100;
            proy.querySelector('#TotalVelocidad2').value = totSems;

            proy.querySelector('.cadencia-calc[data-index="0"]').value = mejoras.cadPorcGe;
            proy.querySelector('.cadencia-calc[data-index="1"]').value = mejoras.cadPorcEs;
            proy.querySelector('.cadencia-calc[data-index="2"]').value = mejoras.cadPorcPr;
            proy.querySelector('.cadencia-calc[data-index="3"]').value = mejoras.semGe;
            proy.querySelector('.cadencia-calc[data-index="4"]').value = mejoras.semEs;
            proy.querySelector('.cadencia-calc[data-index="5"]').value = mejoras.semPr;
            proy.querySelector('#TotalCadencia1').value = 100;
            proy.querySelector('#TotalCadencia2').value = totSems;

            proy.querySelector('.tcs-calc[data-index="0"]').value = mejoras.cadPorcGe;
            proy.querySelector('.tcs-calc[data-index="1"]').value = mejoras.cadPorcEs;
            proy.querySelector('.tcs-calc[data-index="2"]').value = mejoras.cadPorcPr;
            proy.querySelector('.tcs-calc[data-index="3"]').value = mejoras.semGe;
            proy.querySelector('.tcs-calc[data-index="4"]').value = mejoras.semEs;
            proy.querySelector('.tcs-calc[data-index="5"]').value = mejoras.semPr;
            proy.querySelector('#TotalTcs1').value = 100;
            proy.querySelector('#TotalTcs2').value = totSems;

            document.querySelector('#MacroFechaInicio').value = getFechaFormatoString($rutina.fechaInicio);
            document.querySelector('#MacroFechaFin').value = getFechaFormatoString($rutina.fechaFin);
            document.querySelector('#MacroTotalSemanas').textContent = $rutina.totalSemanas;

            const mVC = JSON.parse(consolidado.matrizMejoraVelocidades);
            //Métricas detalladas
            //const pTransito = $baseAfterComprobacion.distancia == 10 ? 1 : $baseAfterComprobacion.distancia == 21 ? 2 : 3;
            const collapseDetallados = document.querySelector('#collapseDetallados');
            const detVeloc = collapseDetallados.querySelector('.detallados-velocidades');
            detVeloc.children.length == 0 ? detVeloc.appendChild(MacroSeccion.velocidadesByDistancia2(mVC)) : detVeloc.children[0].remove() == undefined ? detVeloc.appendChild(MacroSeccion.velocidadesByDistancia2(mVC)) : "";
            const detCaden = collapseDetallados.querySelector('.detallados-cadencia');
            const ritmosCadencia = JSON.parse(consolidado.matrizMejoraCadencia);
            detCaden.children.length == 0 ? detCaden.appendChild(MacroSeccion.cadencia2(ritmosCadencia)) : detCaden.children[0].remove() == undefined ? detCaden.appendChild(MacroSeccion.cadencia2(ritmosCadencia)) : "";
            const detTcs = collapseDetallados.querySelector('.detallados-tcs');
            const valoresTCSs = JSON.parse(consolidado.matrizMejoraTcs);
            detTcs.children.length == 0 ? detTcs.appendChild(MacroSeccion.tcs2(valoresTCSs)) : detTcs.children[0].remove() == undefined ? detTcs.appendChild(MacroSeccion.tcs2(valoresTCSs)) : "";
            const detLongPaso = collapseDetallados.querySelector('.detallados-long-paso');
            const longitudesPaso = JSON.parse(consolidado.matrizMejoraLonPaso);
            detLongPaso.children.length == 0 ? detLongPaso.appendChild(MacroSeccion.longitudPaso2(longitudesPaso)) : detLongPaso.children[0].remove() == undefined ? detLongPaso.appendChild(MacroSeccion.longitudPaso2(longitudesPaso)) : "";
            document.querySelector('#MetricasDetalladas').classList.remove('hidden');
            //Gráficos
            MacroCiclo.instanciarInformacionTemporadaPost();
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
        comprobar: (e)=>{
            if( MacroValidacion.principal() && MacroValidacion.basicos() && $('#frm_registro').valid() ) {

                if(e != null)
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
                const cantPeriodos = $baseAfterComprobacion.periodizacion.length;
                const arrPeriodosOmitidos = $baseAfterComprobacion.periodizacion.map((v,i)=>{if(v == undefined) return i;}).filter(v=>v!=undefined);
                $baseAfterComprobacion.periodizacion.map((v,i)=>{if(v == undefined) return i;}).filter(v=>v!=undefined);

                instanciarPorcentajesKilometraje($baseAfterComprobacion.distancia).then(porcentajes=>{
                    //Filtrando porcentajes
                    const porcentajesTrainer = new Array(cantPeriodos +1);//+1 por el periodo de transito
                    for(let i=0; i<porcentajesTrainer.length;i++){
                            if (i != 3){
                                if ($baseAfterComprobacion.periodizacion[i] != undefined){
                                    porcentajesTrainer[i] = porcentajes.porcKiloTipos[i].semanas[$baseAfterComprobacion.periodizacion[i] - 2];
                                }
                                //Explicacion(-2)
                                // - Uno es por que el indice de todo array comienza en 0
                                // - El último -uno es porque por regla de negocio cada periodo debe tener como mínimo 2 semanas entonces en base de datos se ha guardado como indice 0 la semana 2, indice 1 la semana 3 y así... por ello para evitar un arrayindexoutofboundsexception se resta - 2
                            }else
                                porcentajesTrainer[i] = MacroCicloGet.obtenerPorcentajePT($baseAfterComprobacion.distancia);
                    }
                    contenedorMK.appendChild(htmlStringToElement(MacroCiclo.mostrarPorcentajesKilo($baseAfterComprobacion, porcentajesTrainer, cantPeriodos)));
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
                    $baseAfterComprobacion.periodizacion.forEach((v,i)=>{
                        if(!arrPeriodosOmitidos.includes(i)) {
                            RCPs.push({first: copyArrTiempos[0].factor, last: copyArrTiempos[v - 1].factor});
                            copyArrTiempos.splice(0, v);
                        }else
                            RCPs.push(undefined);
                    });

                    document.querySelectorAll('#EstadisticasAdicionales .rcps').forEach((v,i)=>{
                        if(!arrPeriodosOmitidos.includes(i))
                            v.value = RCPs[i].last.substring(3);
                    });
                    const ritmosEntreAero = Calc.getRitmosEntrenamientoAerobico(ritmosAerobicos.actual, ritmosAerobicos.preCompetitivo, $baseAfterComprobacion);
                    let acc = 0;
                    document.querySelectorAll('#EstadisticasAdicionales .raps').forEach((v,i)=>{
                        if(!arrPeriodosOmitidos.includes(i)) {
                            v.value = ritmosEntreAero[(acc + $baseAfterComprobacion.periodizacion[i]) - 1].factor.substring(3);
                            acc += $baseAfterComprobacion.periodizacion[i];
                        }
                    });

                    const cadenciaActual = document.querySelector('#CadenciaControl').value;
                    const cadenciaCompetencia = document.querySelector('#CadenciaCompetencia').value;
                    const ritmosCadencia = Calc.getRitmosCadenciaCompetencia(cadenciaActual, cadenciaCompetencia, $baseAfterComprobacion);

                    acc = 0;
                    document.querySelectorAll('#EstadisticasAdicionales .cdcs').forEach((v,i)=>{
                        if(!arrPeriodosOmitidos.includes(i)) {
                            v.value = ritmosCadencia[(acc + $baseAfterComprobacion.periodizacion[i]) - 1].factor;
                            acc += $baseAfterComprobacion.periodizacion[i];
                        }
                    });

                    acc = 0;
                    const longitudesPaso = Calc.getLongitudesDePaso(arrTiempos, ritmosCadencia);
                    document.querySelectorAll('#EstadisticasAdicionales .lpcs').forEach((v,i)=>{
                        if(!arrPeriodosOmitidos.includes(i)) {
                            v.value = longitudesPaso[(acc + $baseAfterComprobacion.periodizacion[i]) - 1];
                            acc += $baseAfterComprobacion.periodizacion[i];
                        }
                    });

                    const tcsActual = document.querySelector('#TcsControl').value;
                    const tcsCompetencia = document.querySelector('#TcsCompetencia').value;
                    const valoresTCSs = Calc.getTCSs(tcsActual, tcsCompetencia, $baseAfterComprobacion);
                    acc = 0;
                    document.querySelectorAll('#EstadisticasAdicionales .cdcs').forEach((v,i)=>{
                        if(!arrPeriodosOmitidos.includes(i)){
                            v.value = valoresTCSs[(acc + $baseAfterComprobacion.periodizacion[i]) - 1].factor;
                            acc += $baseAfterComprobacion.periodizacion[i];
                        }
                    });
                    //Metricas velocidades y factor de mejora
                    const mVC = RitmosSVYC.getMetricasVelocidades();
                    document.querySelector('#FactorMejoria').value = Calc.getFactorMejoria(mVC, $baseAfterComprobacion);

                    //Graficos - Información relacionada
                    MacroCiclo.instanciarInformacionTemporada($baseAfterComprobacion);
                    MCGrafico.temporada(MCGraficoData.paraTemporada($baseAfterComprobacion));
                    document.querySelector('#btnGenerarRutina').classList.remove('disabled');
                    document.querySelector('#btnGenerarRutina').setAttribute('title','Generar rutina');
                    //Cuadros HTML RAW
                    const pTransito = $baseAfterComprobacion.distancia == 10 ? 1 : $baseAfterComprobacion.distancia == 21 ? 2 : 3;
                    const collapseDetallados = document.querySelector('#collapseDetallados');
                    const detVeloc = collapseDetallados.querySelector('.detallados-velocidades');
                    detVeloc.children.length == 0 ? detVeloc.appendChild(MacroSeccion.velocidadesByDistancia(mVC)) : detVeloc.children[0].remove() == undefined ? detVeloc.appendChild(MacroSeccion.velocidadesByDistancia(mVC)) : "";
                    const detCaden = collapseDetallados.querySelector('.detallados-cadencia');
                    detCaden.children.length == 0 ? detCaden.appendChild(MacroSeccion.cadencia(ritmosCadencia.slice(0, -pTransito))) : detCaden.children[0].remove() == undefined ? detCaden.appendChild(MacroSeccion.cadencia(ritmosCadencia.slice(0, -pTransito))) : "";
                    const detTcs = collapseDetallados.querySelector('.detallados-tcs');
                    detTcs.children.length == 0 ? detTcs.appendChild(MacroSeccion.tcs(valoresTCSs.slice(0, -pTransito))) : detTcs.children[0].remove() == undefined ? detTcs.appendChild(MacroSeccion.tcs(valoresTCSs.slice(0, -pTransito))) : "";
                    const detLongPaso = collapseDetallados.querySelector('.detallados-long-paso');
                    detLongPaso.children.length == 0 ? detLongPaso.appendChild(MacroSeccion.longitudPaso(longitudesPaso.slice(0, -pTransito))) : detLongPaso.children[0].remove() == undefined ? detLongPaso.appendChild(MacroSeccion.longitudPaso(longitudesPaso.slice(0, -pTransito))) : "";
                    document.querySelector('#MetricasDetalladas').classList.remove('hidden');
                    unlockButton(e.target);
                })
            }
        },
        instanciarInformacionTemporada: (base)=>{
            //base.periodizacion.push(base.distancia == 10 ? 1 : base.distancia == 21 ? 2 : 3);//42: 3 semanas;
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

                console.log(kmsParts[i]);
                if(base.periodizacion[i] != undefined) {
                    const kmsEsp = parseFloat(kmsParts[i].reduce((a, b) => {
                        return a + b
                    }))

                    console.log(kmsEsp);
                    v.querySelector('h1').textContent = kmsEsp.toFixed(1);
                    v.querySelector('span').textContent = base.periodizacion[i] + " semanas";
                    base.porcentajesKms.push(((kmsEsp * 100) / sumKms).toFixed(2));
                }
            });
            document.querySelector('#KilometrajeTotalTemporada').value = parseFloat(sumKms).toFixed(1);
            document.querySelector('#KilometrajePromedioSemanal').value = parseFloat(sumKms/base.numSem).toFixed(1);
            MCGrafico.miniPorcentual(MCGraficoData.paraMini(base.porcentajesKms));
        },
        instanciarInformacionTemporadaPost: ()=>{
            const base = FichaGet.obtenerBase();
            base.periodizacion.push(MacroCicloGet.obtenerAdicionalSemsPT(base.distancia));

            const allKms = $ruConsolidado.dtGrafico.map(({kms})=>kms);
            const sumKms = allKms.reduce((a,b)=>{return a+b});
            const kmsParts = base.periodizacion.map((v)=>{
                return allKms.splice(0, v);//Cada vez el arreglo va perdiendo elementos y por eso siempre hacemos que se corte desde 0
            });
            base.porcentajesKms = [];
            const kiloTotal = document.querySelector('#KilometrajeTotal');
            kiloTotal.querySelector('h1').textContent = parseFloat(sumKms).toFixed(1);
            kiloTotal.querySelector('span').textContent = base.numSem+" semanas";
            document.querySelectorAll('#InicialMacro .dist-etapa').forEach((v,i)=>{
                if(base.periodizacion[i] != undefined) {
                    const kmsEsp = parseFloat(kmsParts[i].reduce((a, b) => {
                        return a + b
                    }))
                    v.querySelector('h1').textContent = kmsEsp.toFixed(1);
                    v.querySelector('span').textContent = base.periodizacion[i] + " semanas";
                    base.porcentajesKms.push(((kmsEsp * 100) / sumKms).toFixed(2));
                }
            });
            document.querySelector('#KilometrajeTotalTemporada').value = parseFloat(sumKms).toFixed(1);
            document.querySelector('#KilometrajePromedioSemanal').value = parseFloat(sumKms/base.numSem).toFixed(1);
            MCGrafico.miniPorcentual(MCGraficoData.paraMini(base.porcentajesKms));
            MCGrafico.temporada(MCGraficoData.paraTemporadaPost(base));

            $slideType = 3;//Para el  correcto funcionamiento del regulador de intensidades/vel
            $('.slider').slider();
            $("div.slider-horizontal > div.slider-track").css("background-color","#1acd49");
            $("#TotalPeriodizacion1").parent().addClass("state-success");
            $("#TotalVelocidad1").parent().addClass("state-success");
            $("#TotalCadencia1").parent().addClass("state-success");
            $("#TotalTcs1").parent().addClass("state-success");
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
                if($baseAfterComprobacion.periodizacion[i] != undefined) {
                    const kmsEsp = kmsParts[i].length == 0 ? 0 : parseFloat(kmsParts[i].reduce((a, b) => {
                        return a + b
                    }));
                    v.querySelector('h1').textContent = kmsEsp.toFixed(1);
                    v.querySelector('span').textContent = base.periodizacion[i] + " semanas";
                    base.porcentajesKms.push(((kmsEsp * 100) / sumKms).toFixed(2));
                }
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
            const periodizacionFinal = base.periodizacion;
            periodizacionFinal.push(MacroCicloGet.obtenerAdicionalSemsPT(base.distancia));//Plus
            let html = `<section class="">`;
            html += periodizacionFinal.map((v,i,k)=>{
                const kmsRef = $kilometrajeBase[i].kilometraje;
                return `<div class="col col-4 padding-0 text-align-center">
                            <h6 class="bg-color-white txt-color-gray font-md margin-bottom-10 padding-10 text-align-center">${nombresEtapa[i]}</h6>
                            ${v != undefined ? MacroCicloSeccion.bodyPorcentajesKilo(k, pTrainer[i], i, kmsRef) : "<i class='fa fa-long-arrow-right' style='font-size: 5em !important;'></i>"}
                        </div>`
            }).join('');
            html+=`</section>`
            return html;
        },
        mostrarPorcentajesIntensidad: (base, pTrainer)=>{
            const nombresEtapa = ["Preparación General", "Preparación Específica", "Preparación Precompetitiva", "P. Tránsito"];
            let html = `<section class="">`;
            html += base.periodizacion.map((v,i,k)=>{
                return `<div class="col col-4 padding-0 text-align-center">
                            <h6 class="bg-color-white txt-color-gray font-md margin-bottom-10 padding-10 text-align-center">${nombresEtapa[i]}</h6>
                            ${v != undefined ? MacroCicloSeccion.bodyPorcentajesIntensidad(k, pTrainer[i], i) : "<i class='fa fa-long-arrow-right' style='font-size: 5em !important;'></i>"}
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
            if($('#KilometrajePromedioSemanal').val() != ""){
                if($('#frm_registro').valid()){
                    const $chelmoMacro = [];
                    //Semanas
                    const numSemBase = FichaGet.obtenerBase().numSem;
                    const numSemBaseIx = numSemBase -1;
                    const cantSemExcedentes = ($baseAfterComprobacion.distancia == 10 ? 1 : $baseAfterComprobacion.distancia == 21 ? 2 : 3);
                    const totalSemanas = numSemBase + cantSemExcedentes;
                    const fIni = parseFromStringToDate(document.querySelector('#MacroFechaInicio').value);
                    const fFin = parseFromStringToDate(document.querySelector('#MacroFechaFin').value);
                    const fFinIncluyendoPc = parseFromStringToDate2(moment(fFin).add((cantSemExcedentes*7), 'd').format('DD/MM/YYYY'));

                    for(let i=0; i<totalSemanas;i++){
                        const refDia = fIni.getDay();
                        let diasParaFull = refDia==0?0:7-refDia;
                        if(i == 0){
                            const objFirtsWeek = {};
                            objFirtsWeek.fechaInicio = moment(fIni).add((i*7), 'd').format('DD/MM/YYYY');
                            objFirtsWeek.fechaFin = moment(fIni).add((i*7)+diasParaFull, 'd').format('DD/MM/YYYY');
                            objFirtsWeek.flagFull = refDia == 1 ? true : false;
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
                    r.fechaFinPt = fFinIncluyendoPc;
                    r.meses = moment(fFin).diff(fIni, 'months');
                    r.anios = moment(fFin).diff(fIni, 'years');
                    r.totalSemanas = $('#MacroTotalSemanas').text().trim();
                    r.dias = totalSemanas * 7;
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
                    r.control.intensidades = Array.from(document.querySelectorAll('#PorcentajesIntensidad label.perc-ints')).map((v,i)=> v.textContent.slice(0,-1));
                    const baseDistribucion = FichaGet.obtenerBase();
                    const dis1 = baseDistribucion.periodizacion[0];
                    const dis2 = dis1 + baseDistribucion.periodizacion[1];
                    const objs = Array.from(document.querySelectorAll('#PorcentajesKilometraje label.kms')).map((v,i)=>{
                        const c = i < dis1 ? "#83c5ff" : i < dis2 ? "#e86b6b" : "#a4f790";
                        return {numSem: i+1, kms: Number(v.textContent), color: c}
                    });
                    const mZC = RitmosSZC.getMetricasZonasCardiacas();
                    const mVC = RitmosSVYC.getMetricasVelocidades();
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

                        r.semanas[fix].prioridad = fix % 2 == 0 ? "1,2,3,1,2,3,1" : "3,2,1,1,2,3,3";
                    })
                    r.totalSemanas = Number(r.totalSemanas)+cantSemExcedentes;
                    const consolidado = MacroCicloGet.consolidado(mVC);
                    r.general = consolidado.general;
                    r.stats = consolidado.stats;
                    r.mejoras = consolidado.mejoras;
                    r.matrizMejoraVelocidades = consolidado.matrizMejoraVelocidades;

                    r.matrizMejoraCadencia = consolidado.matrizMejoraCadencia;
                    r.matrizMejoraTcs = consolidado.matrizMejoraTcs;
                    r.matrizMejoraLonPaso = consolidado.matrizMejoraLonPaso;
                    r.dtGrafico = MCGraficoData.paraTemporada($baseAfterComprobacion).map(v=>{return {kms: v.kms, color: v.color, percInts: v.perc, imgIcon: v.bullet != undefined ? v.bullet.substr(1) : undefined}});
                    for(let i=0; i<cantSemExcedentes;i++){
                        const iBase = r.dtGrafico.length - cantSemExcedentes;
                        r.dtGrafico[iBase+i].percInts = r.control.intensidades[iBase+i];
                    }
                    guardarRutina(r);
                 //   guardarRutina(r, intervalEffect);

                }
            }else{
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
                $.smallBox({color: "alert", content: "<i>Por favor completar los campos vacíos y revisar que las mejoras <br/> y periodización se hayan llenado correctamente.</i>"});
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


            const arrayDay = ["domingo", "lunes", "martes", "miercoles", "jueves", "viernes", "sábado"];

            $("#frm_registro").validate({
                highlight: function (element) {
                    $(element).parent().removeClass('state-success').addClass("state-error");
                    $(element).removeClass('valid');
                },
                unhighlight: function (element) {
                    $(element).parent().removeClass("state-error").addClass('state-success');
                    $(element).addClass('valid');
                },
                rules: {
                    FrecuenciaCardiacaMinima: {
                        required: true,
                        min: 40,
                        max: 130,
                        rangelength:[2  ,3],
                        digits: true
                    },
                    FrecuenciaCardiacaMaxima: {
                        required: true,
                        min: 100,
                        max: 220,
                        maxlength: 3,
                    },
                    NivelAtleta : {
                        required: true
                    },
                    DistanciaRutina : {
                        required: true
                    },
                    MacroFechaInicio: {
                        required: true,
                        greaterThanDate: new Date(),
                        isSpecificDay : 1

                    },
                    MacroFechaFin: {
                        required: true,
                        minimumDifference: function(){
                            return  $('#MacroFechaInicio').val()
                        },
                        maximumDifference: function(){
                            return  $('#MacroFechaInicio').val()
                        },
                        lessThanDate:  function(){
                            return addYearstoDate(new Date(),2);
                        },
                        isSpecificDay : 0
                    },
                    DistanciaControl: {
                        required: true,

                    },
                    TiempoControl: {
                        required: true,
                        greaterThanSeconds: function(){
                            return $('#DistanciaControl').val() == "2" ? "00:05:00" : $('#DistanciaControl').val() == "4" ? "00:10:00" : $('#DistanciaControl').val() == "10" ? "00:25:00" : $('#DistanciaControl').val() == "21" ? "01:04:00" : "02:05:00";
                        }
                    },
                    CadenciaControl: {
                        required: true,
                        min: 50,
                        max: 300
                    },
                    TcsControl: {
                        required: true,
                        min: 110,
                        max: 400
                    },
                    FactorDesentrenamientoControl: {
                        required: true,
                        min: 1,
                        max: 100
                    },
                    TiempoDesentrControl: {
                        required: true
                    },
                    DistanciaCompetencia: {
                        required: true,
                    },
                    TiempoCompetencia: {
                        required: true,
                        greaterThanSeconds: function(){
                            return $('#DistanciaCompetencia').val() == "10" ? "00:25:00" : $('#DistanciaCompetencia').val() == "21" ? "01:04:00" : "02:05:00";
                        },
                    },
                    CadenciaCompetencia: {
                        required: true,
                        min: 0
                    },
                    TcsCompetencia: {
                        required: true,
                        min: 0
                    },
                     TotalPeriodizacion1: {
                        required:true,
                        min: 100,
                        max: 100
                    },
                    TotalVelocidad1: {
                        required:true,
                        min: 100,
                        max: 100
                    },
                    TotalCadencia1: {
                        required:true,
                        min: 100,
                        max: 100
                    },
                    TotalTcs1: {
                        required:true,
                        min: 100,
                        max: 100
                    } ,
                    TotalPeriodizacion2: {
                        min:  function(){
                            return parseInt($('#MacroTotalSemanas').text());
                        },
                        max:  function(){
                            return parseInt($('#MacroTotalSemanas').text());
                        }
                    },
                    TotalVelocidad2: {
                        min:  function(){
                            return parseInt($('#MacroTotalSemanas').text());
                        },
                        max:  function(){
                            return parseInt($('#MacroTotalSemanas').text());
                        }
                    },
                    TotalCadencia2: {
                        min:  function(){
                            return parseInt($('#MacroTotalSemanas').text());
                        },
                        max:  function(){
                            return parseInt($('#MacroTotalSemanas').text());
                        }
                    },
                    TotalTcs2: {
                        min:  function(){
                            return parseInt($('#MacroTotalSemanas').text());
                        },
                        max:  function(){
                            return parseInt($('#MacroTotalSemanas').text());
                        }
                    }
                },
                messages: {
                    FrecuenciaCardiacaMinima: {
                        required: "El campo es obligatorio",
                        min: $.validator.format("Valor mínimo {0}"),
                        max: $.validator.format("Valor máximo {0}"),
                        maxlength: $.validator.format("Este campo debe de tener como máximo {0} caracteres.")
                    },
                    FrecuenciaCardiacaMaxima: {
                        required: "El campo es obligatorio",
                        min: $.validator.format("Valor mínimo {0}"),
                        max: $.validator.format("Valor máximo {0}"),
                        maxlength: $.validator.format("Este campo debe de tener como máximo {0} caracteres.")
                    },
                    NivelAtleta : {
                        required: "El campo es obligatorio",
                    },
                    DistanciaRutina : {
                        required: "El campo es obligatorio"
                    },
                    MacroFechaInicio: {
                        required: "El campo es obligatorio",
                        greaterThanSeconds: "prueba",
                        greaterThanDate : "Ingrese una fecha igual o mayor a la de hoy",
                        isSpecificDay : function (e) {
                            return "Se requiere una fecha que corresponda al día " + arrayDay[e];
                        }
                    },
                    MacroFechaFin: {
                        required: "El campo es obligatorio",
                        isSpecificDay : function (e) {
                            return "Se requiere una fecha que corresponda al día " + arrayDay[e];
                        }

                    },
                    DistanciaControl: {
                        required: "El campo es obligatorio",
                    },
                    TiempoControl: {
                        required: "El campo es obligatorio",
                    },
                    CadenciaControl: {
                        required: "El campo es obligatorio",
                        min: "Mínimo valor {0}"
                    },
                    TcsControl: {
                        required: "El campo es obligatorio",
                        min: "Mínimo valor {0}"
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
                        required: "El campo es obligatorio"
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
                    },
                    TotalPeriodizacion1: {
                        min: "Total debe ser {0}%",
                        max: "Total debe ser {0}%"
                    },
                    TotalVelocidad1: {
                        min: "Total debe ser {0}%",
                        max: "Total debe ser {0}%"
                    },
                    TotalCadencia1: {
                        min: "Total debe ser {0}%",
                        max: "Total debe ser {0}%"
                    },
                    TotalTcs1: {
                        min: "Total debe ser {0}%",
                        max: "Total debe ser {0}%"
                    },
                    TotalPeriodizacion2: {
                        min: "Total debe ser {0}",
                        max: "Total debe ser {0}"
                    },
                    TotalVelocidad2: {
                        min: "Total debe ser {0}",
                        max: "Total debe ser {0}"
                    },
                    TotalCadencia2: {
                        min: "Total debe ser {0}",
                        max: "Total debe ser {0}"
                    },
                    TotalTcs2: {
                        min: "Total debe ser {0}",
                        max: "Total debe ser {0}"
                    }
                },
                submitHandler: function () {

                },
                invalidHandler: function(e, validator) {
                    //validator.errorMap is an object mapping input names -> error messages
                    const cantErrores = validator.errorList.length;
                    const aditionalTime = 1900*cantErrores;
                    $.smallBox({color: "alert", content: '<h6><i class="fa fa-info-circle fa-fw"></i> Verificar</h6>'+Object.keys(validator.errorMap).map(v=>`<b>${v}:</b> ${validator.errorMap[v]}</br>`).join(''), timeout: 4000+(aditionalTime)});
                }
            });
        }
    }
})();

MacroSeccion = (function(){
    return {
        velocidadesByDistancia2: (mVC)=>{
            const semsTransito = $ruConsolidado.distancia == 10 ? 1 : $ruConsolidado.distancia == 21 ? 2 : 3;
            //Calculando el porcentaje de mejora de velocidad y seteandolo
            const porcMejora = parseNumberToDecimal(mVC.map((v,i)=>{
                return Number(parseNumberToDecimal((((((mVC[i].ind[0].toSeconds())*$ruConsolidado.general.distancia)/((mVC[i].ind[(mVC[i].ind.length)-1].toSeconds())*$ruConsolidado.general.distancia)))-1)*100,1))
            }).reduce((a,b)=>a+b, 1)/mVC.length,1);
            document.querySelector('#PorcMejoraVel').textContent = porcMejora + " %";
            const rgs= `<div class="col col-md-1 col-sm-1 sems-o-mes-det-veloc">
                            <div class="container-fluid text-align-center margin-o-bottom-10-w-bb">
                                <div class="padding-bottom-5 hd-column">R</div>
                            </div>
                         </div>
                         <div class="col col-md-11 col-sm-11">
                            <div class="container-fluid text-align-center margin-o-bottom-10-w-bb">
                                ${mVC.map((v,i)=>{
                                    return `${i==0?'<div class="col-md-6 col-sm-6 padding-bottom-5">':''}<div class="col col-md-3 col-sm-3">
                                        <div>
                                            <span class="padding-5 text-align-left">
                                            <input type="text" class="slider metrica slider-success" value="" data-slider-min="0" 
                                                                data-slider-max="100" data-slider-step="0.25" data-slider-value="50" 
                                                                data-slider-orientation="horizontal" data-slider-selection="after" 
                                                                data-slider-handle="round" data-slider-tooltip="hide" data-index="${i+1}"/>
                                            </span>
                                        </div></div>${i==3?'</div><div class="col-md-6 col-sm-6 padding-bottom-5">':''}${i==7?'</div>':''}`;
                                }).join('')}
                            </div>
                         </div>`;
            if(($rutina.totalSemanas-semsTransito) <=12)
                return htmlStringToElement(`<div style="margin-right: 10px;">
                    ${mVC[0].ind.map((v,i)=>{
                        return `<div class="col col-md-1 col-sm-1 sems-o-mes-det-veloc"><div class="container-fluid text-align-center margin-o-bottom-10-w-bb"> <div class="padding-bottom-5 hd-column">${'S '+ (i+1)}</div> </div> </div> <div class="col col-md-11 col-sm-11"> <div class="container-fluid text-align-center margin-o-bottom-10-w-bb">
                                    ${mVC.map((v,ii)=>{
                            return `${ii==0?'<div class="col-md-6 col-sm-6 padding-bottom-5">':''}<div class="col col-md-3 col-sm-3 dt-ix${ii+1}">${v.ind[i]}</div>${ii==3?'</div><div class="col-md-6 col-sm-6 padding-bottom-5">':''}${ii==7?'</div>':''}`;
                        }).join('')}</div></div>`;
                    }).join('')}${rgs}</div>`);
            else
                return htmlStringToElement(`<div style="margin-right: 10px;">
                    ${mVC[0].ind.map((v,i)=>{
                    return (i+1)%4 == 0 ?`<div class="col col-md-1 col-sm-1 sems-o-mes-det-veloc"><div class="container-fluid text-align-center margin-o-bottom-10-w-bb"> <div class="padding-bottom-5 hd-column">${'M '+(i+1)/4}</div> </div> </div> <div class="col col-md-11 col-sm-11"> <div class="container-fluid text-align-center margin-o-bottom-10-w-bb">
                                ${mVC.map((v,ii)=>{
                        return (i+1)%4 == 0 ?`${ii==0?'<div class="col-md-6 col-sm-6 padding-bottom-5">':''}<div class="col col-md-3 col-sm-3 dt-ix${ii+1}">${v.ind[i]}</div>${ii==3?'</div><div class="col-md-6 col-sm-6 padding-bottom-5">':''}${ii==7?'</div>':''}`:'';
                    }).join('')}</div></div>`:`<i class="hidden col-md-11">${mVC.map((v,ii)=>{ return `<i class="col-md-3 dt-ix${ii+1}">${v.ind[i]}</i>`}).join('')}</i>`;
                }).join('')}${rgs}</div>`);


                console.log(rgs);
        },
        velocidadesByDistancia: (mVC)=>{

            //Calculando el porcentaje de mejora de velocidad y seteandolo
            document.querySelector('#PorcMejoraVel').textContent = parseNumberToDecimal((((((mVC[7].indicadores[0].p.toSeconds())*42)/((mVC[7].indicadores[(mVC[7].indicadores.length)-1].p.toSeconds())*42)))-1)*100,1) + " %";

            if($baseAfterComprobacion.numSem <=12)
                return htmlStringToElement(`<div style="margin-right: 10px;">
                    ${mVC[0].indicadores.map((v,i)=>{
                        return `<div class="col col-md-1 col-sm-1 sems-o-mes-det-veloc"><div class="container-fluid text-align-center margin-o-bottom-10-w-bb"> <div class="padding-bottom-5 hd-column">${'S '+ (i+1)}</div> </div> </div> <div class="col col-md-11 col-sm-11"> <div class="container-fluid text-align-center margin-o-bottom-10-w-bb">
                                ${mVC.map((v,ii)=>{
                                    return `${ii==0?'<div class="col-md-6 col-sm-6 padding-bottom-5">':''}<div class="col col-md-3 col-sm-3">${v.indicadores[i].p}</div>${ii==3?'</div><div class="col-md-6 col-sm-6 padding-bottom-5">':''}${ii==7?'</div>':''}`;
                                }).join('')}</div></div>`;
                    }).join('')}</div>`);
            else
                return htmlStringToElement(`<div style="margin-right: 10px;">
                    ${mVC[0].indicadores.map((v,i)=>{
                        return (i+1)%4 == 0 ?`<div class="col col-md-1 col-sm-1 sems-o-mes-det-veloc"><div class="container-fluid text-align-center margin-o-bottom-10-w-bb"> <div class="padding-bottom-5 hd-column">${'M '+(i+1)/4}</div> </div> </div> <div class="col col-md-11 col-sm-11"> <div class="container-fluid text-align-center margin-o-bottom-10-w-bb">
                                ${mVC.map((v,ii)=>{
                                    return (i+1)%4 == 0 ?`${ii==0?'<div class="col-md-6 col-sm-6 padding-bottom-5">':''}<div class="col col-md-3 col-sm-3">${v.indicadores[i].p}</div>${ii==3?'</div><div class="col-md-6 col-sm-6 padding-bottom-5">':''}${ii==7?'</div>':''}`:'';
                                }).join('')}</div></div>`:'';
                    }).join('')}</div>`);

       

        },
        cadencia2: (metricas)=>{
            const porcMejora = parseNumberToDecimal(((((metricas[metricas.length - 1]) / metricas[0])-1)*100), 1) + " %";
            if($rutina.totalSemanas <=12)
                return htmlStringToElement('<div class="container-fluid">'+metricas.map((v,i)=>{
                    return `<div class="container-fluid text-align-center"><div class="col col-md-3 col-sm-3"></div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel">S ${(i+1)}</div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel header-met-vel">${v}</div><div class="col col-md-3 col-sm-3"></div></div>`}).join('') + `<div class="container-fluid text-align-center"><h1 class="padding-10 text-align-right bg-circle-redLight"><span class="badge padding-7 mej-perc font-xs">${porcMejora}</span></h1></div></div>`);
            else
                return htmlStringToElement('<div class="container-fluid">'+metricas.map((v,i)=>{
                    return `<div class="container-fluid text-align-center"><div class="col col-md-3 col-sm-3"></div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel">M ${i+1}</div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel">${v}</div><div class="col col-md-3 col-sm-3"></div></div>`}).join(''));

        },
        cadencia: (metricas)=>{
            const porcMejora = parseNumberToDecimal(((((metricas[metricas.length - 1].factor) / metricas[0].factor)-1)*100), 1) + " %";
            if($baseAfterComprobacion.numSem <=12)
                return htmlStringToElement('<div class="container-fluid">'+metricas.map((v,i)=>{
                    return `<div class="container-fluid text-align-center"><div class="col col-md-3 col-sm-3"></div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel">S ${(i+1)}</div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel mt-perc-cad">${v.factor}</div><div class="col col-md-3 col-sm-3"></div></div>`}).join('') + `<div class="container-fluid text-align-center"><h1 class="padding-10 text-align-right bg-circle-redLight"><span class="badge padding-7 mej-perc font-xs">${porcMejora}</span></h1></div></div>`);
            else
                return htmlStringToElement('<div class="container-fluid">'+metricas.map((v,i)=>{
                    return (i+1)%4 == 0 ? `<div class="container-fluid text-align-center"><div class="col col-md-3 col-sm-3"></div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel">M ${(i+1)/4}</div><div class="col col-md-3 col-sm-3 text-align-center mt-perc-cad">${v.factor}</div><div class="col col-md-3 col-sm-3"></div></div>`:''}).join('') + `<div class="container-fluid text-align-center"><h1 class="padding-10 text-align-right bg-circle-redLight"><span class="badge padding-7 mej-perc font-xs">${porcMejora}</span></h1></div></div>`);

        },
        tcs2: (metricas)=>{
            const porcMejora = parseNumberToDecimal(((((metricas[metricas.length - 1]) / metricas[0])-1)*100), 1) + " %";
            if($rutina.totalSemanas <=12)
                return htmlStringToElement('<div class="container-fluid">'+metricas.map((v,i)=>{
                    return `<div class="container-fluid text-align-center"><div class="col col-md-3 col-sm-3"></div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel">S ${(i+1)}</div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel header-met-vel">${v}</div><div class="col col-md-3 col-sm-3"></div></div>`}).join('') + `<div class="container-fluid text-align-center"><h1 class="padding-10 text-align-right bg-circle-redLight"><span class="badge padding-7 mej-perc font-xs">${porcMejora}</span></h1></div></div>`);
            else
                return htmlStringToElement('<div class="container-fluid">'+metricas.map((v,i)=>{
                    return `<div class="container-fluid text-align-center"><div class="col col-md-3 col-sm-3"></div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel">M ${(i+1)}</div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel">${v}</div><div class="col col-md-3 col-sm-3"></div></div>`}).join(''));
        },
        tcs: (metricas)=>{
            const porcMejora = parseNumberToDecimal(((((metricas[metricas.length - 1].factor) / metricas[0].factor)-1)*100), 1) + " %";
            if($baseAfterComprobacion.numSem <=12)
                return htmlStringToElement('<div class="container-fluid">'+metricas.map((v,i)=>{
                    return `<div class="container-fluid text-align-center"><div class="col col-md-3 col-sm-3"></div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel">S ${(i+1)}</div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel mt-perc-tcs">${v.factor}</div><div class="col col-md-3 col-sm-3"></div></div>`}).join('') + `<div class="container-fluid text-align-center"><h1 class="padding-10 text-align-right bg-circle-redLight"><span class="badge padding-7 mej-perc font-xs">${porcMejora}</span></h1></div></div>`);
            else
                return htmlStringToElement('<div class="container-fluid">'+metricas.map((v,i)=>{
                    return (i+1)%4 == 0 ? `<div class="container-fluid text-align-center"><div class="col col-md-3 col-sm-3"></div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel">M ${(i+1)/4}</div><div class="col col-md-3 col-sm-3 text-align-center mt-perc-tcs">${v.factor}</div><div class="col col-md-3 col-sm-3"></div></div>`:''}).join('') + `<div class="container-fluid text-align-center"><h1 class="padding-10 text-align-right bg-circle-redLight"><span class="badge padding-7 mej-perc font-xs">${porcMejora}</span></h1></div></div>`);
        },

        longitudPaso2:  (metricas)=>{
            const porcMejora = parseNumberToDecimal(((((metricas[metricas.length - 1]) / metricas[0])-1)*100), 1) + " %";
            if($rutina.totalSemanas <=12)
                return htmlStringToElement('<div class="container-fluid">'+metricas.map((v,i)=>{
                    return `<div class="container-fluid text-align-center"><div class="col col-md-3 col-sm-3"></div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel">S ${i+1}</div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel mt-perc-lon">${v}</div><div class="col col-md-3 col-sm-3"></div></div>`}).join('') + `<div class="container-fluid text-align-center"><h1 class="padding-10 text-align-right bg-circle-redLight"><span class="badge padding-7 mej-perc font-xs">${porcMejora}</span></h1></div></div>`);
            else
                return htmlStringToElement('<div class="container-fluid">'+metricas.map((v,i)=>{
                    return `<div class="container-fluid text-align-center"><div class="col col-md-3 col-sm-3"></div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel">M ${i+1}</div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel mt-perc-lon">${v}</div><div class="col col-md-3 col-sm-3"></div></div>`}).join(''));
        },
        longitudPaso:  (metricas)=>{
            const porcMejora = parseNumberToDecimal(((((metricas[metricas.length - 1]) / metricas[0])-1)*100), 1) + " %";
            if($baseAfterComprobacion.numSem <=12)
                return htmlStringToElement('<div class="container-fluid">'+metricas.map((v,i)=>{
                    return `<div class="container-fluid text-align-center"><div class="col col-md-3 col-sm-3"></div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel">S ${(i+1)}</div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel mt-perc-lon">${v}</div><div class="col col-md-3 col-sm-3"></div></div>`}).join('') + `<div class="container-fluid text-align-center"><h1 class="padding-10 text-align-right bg-circle-redLight"><span class="badge padding-7 mej-perc font-xs">${porcMejora}</span></h1></div></div>`);
            else
                return htmlStringToElement('<div class="container-fluid">'+metricas.map((v,i)=>{
                    return (i+1)%4 == 0 ? `<div class="container-fluid text-align-center"><div class="col col-md-3 col-sm-3"></div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel">M ${(i+1)/4}</div><div class="col col-md-3 col-sm-3 text-align-center header-met-vel mt-perc-lon">${v}</div><div class="col col-md-3 col-sm-3"></div></div>`:''}).join('') + `<div class="container-fluid text-align-center"><h1 class="padding-10 text-align-right bg-circle-redLight"><span class="badge padding-7 mej-perc font-xs">${porcMejora}</span></h1></div></div>`);
        }
    }
})();

MCGraficoData = (function(){
    return {
        paraTemporada: (objBase)=>{
            const dis1 = objBase.periodizacion[0] == undefined ? 0 : objBase.periodizacion[0];
            const dis2 = dis1 + (objBase.periodizacion[1] == undefined ? 0 : objBase.periodizacion[1]);
            const dis3 = dis2 + objBase.periodizacion[2];
            //Dis kilometraje // Color
            const data = Array.from(document.querySelectorAll('#PorcentajesKilometraje label.kms')).map((v,i)=>{
                const c = i < dis1 ? bgBarMainGraph[0] : i < dis2 ? bgBarMainGraph[1] : i < dis3 ? bgBarMainGraph[2] : bgBarMainGraph[3];
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
        paraTemporadaPost: (objBase)=>{
            const dis1 = objBase.periodizacion[0] == undefined ? 0 : objBase.periodizacion[0];
            const dis2 = dis1 + (objBase.periodizacion[1] == undefined ? 0 : objBase.periodizacion[1]);
            const dis3 = dis2 + objBase.periodizacion[2];
            //Dis kilometraje // Color
            const data = $ruConsolidado.dtGrafico.map((v,i)=>{
                const c = i < dis1 ? "rgba(131, 197, 255, 0.2)" : i < dis2 ? "rgba(232, 107, 10, 0.2)" : i < dis3 ? "rgba(164, 247, 144, 0.2)" : "rgba(74, 78, 59, 0.2)";
                return {numSem: i+1, perc: v.percInts,kms: v.kms, color: v.color};
            });

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
        paraMini: (porcentajes)=> {
            const difVal = 4 - porcentajes.length;
            const lenP = porcentajes.length;
            const data = [{title: "General", value: "", color: "#E6F3FF"}, {title: "Específica", value: "", color: "#FAE1CE"}, {title: "Precompetitiva", value: "", color: "#EDFDE9"}, {title: "Tránsito", value: "", color: "#4A4E3B5E"}];
            data.splice(0, difVal);
            data.forEach((v,i)=>v.value = porcentajes[i]);
            const dF = new Array(lenP-difVal);
            for(let i=0; i<Object.keys(data[0]).length;i++){
                dF[i] = [];
                data.forEach((v, ii) => {
                    if (ii < lenP)
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
            console.log(data);
            data = data.map(v=>{return {kms: v.kms, color: v.color, perc: v.perc, bullet: v.bullet, avance: v.avance}});

            console.log(data);

            const avances =  data.filter(v=>{//Provisional
                return (v.avance != undefined)
            }).map(({avance})=>avance);
            avances.push(0);//Por conveniencia(estética)
            document.querySelector('#ContainerVarVolumen').classList.remove('hidden');
            document.querySelector('#InicialMacro').classList.remove('hidden');
            let draw = Chart.controllers.line.prototype.draw;
            Chart.controllers.LineNoOffset = Chart.controllers.line.extend({
                updateElement: function(point, index, reset) {
                    Chart.controllers.line.prototype.updateElement.call(this, point, index, reset);
                    const meta = this.getMeta();
                    const xScale = this.getScaleForId(meta.xAxisID);
                    point._model.x = xScale.getPixelForValue(undefined, index-0.5);
                }, draw: function() {
                    draw.apply(this, arguments);
                    let ctx = this.chart.chart.ctx;
                    let _stroke = ctx.stroke;
                    ctx.stroke = function() {
                        ctx.save();
                        ctx.shadowColor = '#23314591';
                        ctx.shadowBlur = 10;
                        ctx.shadowOffsetX = 0;
                        ctx.shadowOffsetY = 4;
                        _stroke.apply(this, arguments)
                        ctx.restore();
                    }
                }
            });
            if($chartTemporada.ctx != undefined){
                $chartTemporada.destroy();
            }else{
                Chart.pluginService.register({
                    /*afterUpdate: function(chart) {
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
                    },*/
                    afterDatasetsDraw: function(chart) {
                        if(chart.canvas.id === "GraficoTemporada"){
                            let ctx = chart.ctx;
                            chart.data.datasets.forEach(function (dataset, i) {
                                if (i == 1) {
                                    let meta = chart.getDatasetMeta(i);
                                    if (!meta.hidden) {
                                        meta.data.forEach(function (element, index) {
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
                                            ctx.fillStyle = '#f3eeee94';// Draw the specified text color, with the specified font for data in line

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

                                            let fontSize = 14;
                                            let fontStyle = 'normal';
                                            ctx.font = Chart.helpers.fontString(fontSize, fontStyle);
                                            let dataString;
                                            // Just naively convert to string for now
                                            dataString = dataset.data[index].toString() + "%";
                                            // Make sure alignment settings are correct
                                            ctx.textAlign = 'center';
                                            ctx.textBaseline = 'middle';
                                            ctx.fillStyle = 'white';

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

            Chart.Legend.prototype.afterFit = function() {
                this.height = this.height + 12;
                //this.height = this.height + 35;
            };

            let ctx = document.getElementById('GraficoTemporada').getContext('2d');
            console.log(Math.round(data.length*0.75)*100);
            /*var gradientFill = ctx.createLinearGradient((Math.round(data.length*0.75)*100), 0, 100, 0);
            gradientFill.addColorStop(0, "#4e4a6d");//Last
            gradientFill.addColorStop(0.13, "gray");
            gradientFill.addColorStop(0.46, "#4e4a6d");
            gradientFill.addColorStop(1, "gray");//First*/

            var gradientFill = ctx.createLinearGradient(0, 0, 0, (Math.round(data.length*0.75)*100));
            gradientFill.addColorStop(0, bgMantaIntensidad[0]);//Last
            gradientFill.addColorStop(0.13, bgMantaIntensidad[1]);
            gradientFill.addColorStop(0.46, bgMantaIntensidad[2]);
            gradientFill.addColorStop(0.76, bgMantaIntensidad[3]);//First
            //Temp
            const len = data.length;
            data[len-1].perc = "100";
            data[len-1].kms = 40;
            data[len-2].perc = "90";
            data[len-2].kms = 40;
            data[len-3].perc = "80";
            data[len-3].kms = 40;

            //End temp
            $chartTemporada = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: data.map((v, i)=>i+1),
                    datasets: [{
                        label: 'Kilometraje',
                        data: data.map(({kms})=>kms),
                        borderColor: "grey",
                        hoverBackgroundColor: 'rgba(57, 255, 163, 0.7)',
                        yAxisID: 'y-axis-1',
                        backgroundColor: data.map(({color})=>color)
                        ,
                    }, {
                        label: 'Intensidad',
                        data: data.map(({perc})=>perc),
                        yAxisID: 'y-axis-2',
                        // Changes this dataset to become a line
                        type: 'line',
                        borderColor: '#a8fa00',
                        backgroundColor: gradientFill,
                        pointBorderColor: '#a8fa00',
                        pointBackgroundColor: '#a8fa00',
                        pointHoverBackgroundColor: 'a8fa00',
                        pointHoverBorderColor: '#a8fa00',
                        borderWidth: 1,
                        fill: true
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
                        /*callbacks: {
                            title: function(tooltipItem) {
                                const fix = $fechasCompetencia.length - $idsComp.length;
                                let mIx;
                                return $idsComp.filter((v,i) => {
                                    if(v == tooltipItem[0].index)
                                        return String(mIx = i);//Se convierte en String para evitar problemas cuando el indice sea 0, ya que js considera en el caso de filter el 0 como undef/null
                                }).length == 0 ? this._data.labels[tooltipItem[0].index] : $fechasCompetencia[mIx+fix].nombre;
                            },
                        }*/
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
                                fontColor: "#f3eeee94",
                            },
                        }, {
                            id: 'y-axis-2',
                            type: 'linear',
                            position: 'right',
                            gridLines: {
                                color: "rgba(72,68,118,0.2)",
                                display: false,
                            },
                            ticks: {
                                display: false,
                                suggestedMin: 0,
                                suggestedMax: 100,   // minimum will be 0, unless there is a lower value.
                                // OR //
                                beginAtZero: true,   // minimum value will be 0.
                                fontColor: "rgba(74, 78, 59, 1)",
                                /*callback: function(label) {
                                    return label + "%";
                                }*/
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
                                /*userCallback: function(item, index) {
                                    if (!(index % 4)) return item;
                                },*/
                                autoSkip: false,
                                fontColor: "#f3eeee94"
                            },
                            barPercentage: 0.5
                        }],
                    },
                    elements:{
                        line: {
                            beginAtZero: true
                        }
                    },
                    legend: {/*
                        labels: {
                            filter: function(legendItem) {
                                if (legendItem.datasetIndex === 0) {
                                    return false;
                                }
                                return true;
                            },
                            fontColor: 'transparent'
                        },*/
                        display: true
                    },
                }
            });

            Chart.elements.Rectangle.prototype.draw = function() {

                var ctx = this._chart.ctx;
                var vm = this._view;
                var left, right, top, bottom, signX, signY, borderSkipped, radius;
                var borderWidth = vm.borderWidth;
                // Set Radius Here
                // If radius is large enough to cause drawing errors a max radius is imposed
                var cornerRadius = 20;

                if (!vm.horizontal) {
                    // bar
                    left = vm.x - vm.width / 2;
                    right = vm.x + vm.width / 2;
                    top = vm.y;
                    bottom = vm.base;
                    signX = 1;
                    signY = bottom > top? 1: -1;
                    borderSkipped = vm.borderSkipped || 'bottom';
                } else {
                    // horizontal bar
                    left = vm.base;
                    right = vm.x;
                    top = vm.y - vm.height / 2;
                    bottom = vm.y + vm.height / 2;
                    signX = right > left? 1: -1;
                    signY = 1;
                    borderSkipped = vm.borderSkipped || 'left';
                }

                // Canvas doesn't allow us to stroke inside the width so we can
                // adjust the sizes to fit if we're setting a stroke on the line
                if (borderWidth) {
                    // borderWidth shold be less than bar width and bar height.
                    var barSize = Math.min(Math.abs(left - right), Math.abs(top - bottom));
                    borderWidth = borderWidth > barSize? barSize: borderWidth;
                    var halfStroke = borderWidth / 2;
                    // Adjust borderWidth when bar top position is near vm.base(zero).
                    var borderLeft = left + (borderSkipped !== 'left'? halfStroke * signX: 0);
                    var borderRight = right + (borderSkipped !== 'right'? -halfStroke * signX: 0);
                    var borderTop = top + (borderSkipped !== 'top'? halfStroke * signY: 0);
                    var borderBottom = bottom + (borderSkipped !== 'bottom'? -halfStroke * signY: 0);
                    // not become a vertical line?
                    if (borderLeft !== borderRight) {
                        top = borderTop;
                        bottom = borderBottom;
                    }
                    // not become a horizontal line?
                    if (borderTop !== borderBottom) {
                        left = borderLeft;
                        right = borderRight;
                    }
                }

                ctx.beginPath();
                ctx.fillStyle = vm.backgroundColor;
                ctx.strokeStyle = vm.borderColor;
                ctx.lineWidth = borderWidth;

                // Corner points, from bottom-left to bottom-right clockwise
                // | 1 2 |
                // | 0 3 |
                //bottom = bottom + 20;
                var corners = [
                    [left, bottom],
                    [left, top],
                    [right, top],
                    [right, bottom]
                ];

                // Find first (starting) corner with fallback to 'bottom'
                var borders = ['bottom', 'left', 'top', 'right'];
                var startCorner = borders.indexOf(borderSkipped, 0);
                if (startCorner === -1) {
                    startCorner = 0;
                }

                function cornerAt(index) {
                    return corners[(startCorner + index) % 4];
                }

                // Draw rectangle from 'startCorner'
                var corner = cornerAt(0);
                ctx.moveTo(corner[0], corner[1]);

                for (var i = 1; i < 4; i++) {
                    corner = cornerAt(i);
                    nextCornerId = i+1;
                    if(nextCornerId == 4){
                        nextCornerId = 0
                    }

                    nextCorner = cornerAt(nextCornerId);

                    width = corners[2][0] - corners[1][0];
                    height = corners[0][1] - corners[1][1];
                    x = corners[1][0];
                    y = corners[1][1];

                    var radius = cornerRadius;

                    // Fix radius being too large
                    if(radius > height/2){
                        radius = height/2;
                    }if(radius > width/2){
                        radius = width/2;
                    }

                    ctx.moveTo(x + radius, y);
                    ctx.lineTo(x + width - radius, y);
                    ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
                    ctx.lineTo(x + width, y + height);
                    ctx.quadraticCurveTo(x + width, y + height, x + width, y + height);
                    ctx.lineTo(x, y + height);
                    ctx.quadraticCurveTo(x, y + height, x, y + height);
                    ctx.lineTo(x, y + radius);
                    ctx.quadraticCurveTo(x, y, x + radius, y);

                }

                ctx.fill();
                if (borderWidth) {
                    ctx.stroke();
                }
            };
        },
        miniPorcentual: (dF)=>{


            console.log(dF);
            //MCGrafico.cesDemoCircularGraphs();

            let ctx = document.getElementById('MiniGraficoDistribucion').getContext('2d');

            if($chartMiniPorc.ctx != undefined){
                $chartMiniPorc.destroy();
            }

            /*$chartTemporada = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: data.map((v, i)=>i+1),
                    datasets: [{
                        label: 'Kilometraje',
                        data: data.map(({kms})=>kms),
                        borderColor: "grey",
                        hoverBackgroundColor: 'rgba(57, 255, 163, 0.7)',
                        yAxisID: 'y-axis-1',
                        backgroundColor: data.map(({color})=>color)
                        ,
                    }, {
                        label: 'Intensidad',
                        data: data.map(({perc})=>perc),
                        yAxisID: 'y-axis-2',
                        // Changes this dataset to become a line
                        type: 'line',
                        borderColor: '#a8fa00',
                        backgroundColor: gradientFill,
                        pointBorderColor: '#a8fa00',
                        pointBackgroundColor: '#a8fa00',
                        pointHoverBackgroundColor: 'a8fa00',
                        pointHoverBorderColor: '#a8fa00',
                        borderWidth: 1,
                        fill: true*/
            $chartMiniPorc = new Chart(ctx, {
                type: 'doughnut',
                data: {
                    labels: dF[0],
                    datasets: [{
                        data: dF[1],
                        backgroundColor: ['rgba(168, 250, 01, 0.8)', 'rgba(168, 250, 01, 0.6)', 'rgba(168, 250, 01, 0.4)', 'rgba(168, 250, 01, 0.2)'],
                        hoverBackgroundColor: dF[2],
                        borderColor: 'transparent',
                    }],
                },
                options: {
                    responsive: false,
                    legend: {
                        display: false
                    },
                    /*segmentShowStroke: false*/
                    //Boolean - Whether we should show a stroke on each segment
                    // set to false to hide the space/line between segments

                }
            });
        },
        cesDemoCircularGraphs: ()=>{
            const canvas = document.getElementById('c1');
            if(canvas != null) {//Validacion provisional para que solo sea invocado desde el formulario de creación de rutinas más no en el editor de rutinas
                let ctx = document.getElementById('c1').getContext('2d');
                let ctx1 = document.getElementById('c2').getContext('2d');

                $chartC1 = new Chart(ctx, {
                    type: 'pie',
                    data: {
                        labels: ['13-17', '18-24', '25-34', '35-44', '54-45', '55-64', '65+'],
                        datasets: [{
                            data: [5, 7, 7, 12, 16, 28, 25],
                            backgroundColor: ['#2d142c', '#8fb9a8', '#765d69', '#fcbb6d', '#2d6072', '#efca58'],
                            borderColor: 'white',
                            borderWidth: 0.4,
                        }],
                    },
                    options: {
                        segmentShowStroke: false,
                        title: {
                            display: true,
                            text: '👩 Mujeres 43%',
                            fontColor: 'white',
                            fontSize: 15
                        },
                        responsive: false,
                        legend: {
                            display: false,
                            position: 'right',
                        }

                    }
                });

                $chartC2 = new Chart(ctx1, {
                    type: 'pie',
                    data: {
                        labels: ['13-17', '18-24', '25-34', '35-44', '54-45', '55-64', '65+'],
                        datasets: [{
                            data: [5, 7, 7, 12, 16, 28, 25],
                            backgroundColor: ['#2d142c', '#8fb9a8', '#765d69', '#fcbb6d', '#2d6072', '#efca58'],
                            borderColor: 'white',
                            borderWidth: 0.4,
                        }],
                    },
                    options: {
                        title: {
                            display: true,
                            text: '👨 Hombres 57%',
                            fontColor: 'white',
                            fontSize: 15
                        },
                        responsive: false,
                        legend: {
                            display: false,
                            position: 'right',
                        }

                    }
                });
            }
        }
    }
})();

MacroCicloSeccion = (function(){
    return {
        bodyPorcentajesKilo: (arrCant, pTrainer, ix, kmsBase)=>{
            const colorClass = ["slider-warning", "slider-danger", "slider-success"];
            let all = `<div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12">`;

            const arrIxReferencia = arrCant.filter((v,i)=> v!=undefined && i<ix);
            let windx = arrIxReferencia.length == 0 ? 1 : (arrIxReferencia.reduce((a,b)=>a+b))+1;
            for(let i=0; i<arrCant[ix];i++){
                const fIx = windx + i;
                all += `<div class="col-xs-2 col-sm-2 col-md-1 col-lg-1">`
                const fixVal = 100 - pTrainer.porcentajes[i];
                all +=`<div><label class="padding-5 kms" data-index="${fIx}">${((kmsBase*pTrainer.porcentajes[i])/100).toFixed(1)}</label></div>`;
                all +=`<div><span class="padding-5 text-align-left"><input type="text" class="slider ${colorClass[ix]}" value="" data-slider-min="0" data-slider-max="100" data-slider-step="2" data-slider-value="${fixVal}" data-slider-orientation="vertical" data-slider-selection="after" data-slider-handle="round" data-slider-tooltip="hide" data-index="${fIx}" data-kms="${kmsBase}"></span></div>`;
                all +=`<div><label class="padding-5 perc hidden" data-index="${fIx}">${pTrainer.porcentajes[i]}%</label></div>`
                all +=`<div><label class="padding-10">${fIx<10 ? '0'+fIx : fIx}</label></div>`;
                all+= "</div>";
            }
            all+=`</div>`;
            return all;
        },
        bodyPorcentajesIntensidad: (arrCant, pTrainer, ix)=>{
            const colorClass = ["slider-warning", "slider-danger", "slider-success"];
            let all = `<div class="col col-xs-12 col-sm-12 col-md-12 col-lg-12">`;

            const arrIxReferencia = arrCant.filter((v,i)=> v!=undefined && i<ix);
            let windx = arrIxReferencia.length == 0 ? 1 : (arrIxReferencia.reduce((a,b)=>a+b))+1;

            for(let i=0; i<arrCant[ix];i++){
                const fIx = windx + i;
                all += `<div class="col-xs-2 col-sm-2 col-md-1 col-lg-1">`
                const fixVal = 100 - pTrainer.porcentajes[i];
                all +=`<div><label class="padding-5 perc-ints" data-index="${fIx}">${pTrainer.porcentajes[i]}%</label></div>`;
                all +=`<div><span class="padding-5 text-align-left"><input type="text" class="slider ${colorClass[ix]}" value="" data-slider-min="0" data-slider-max="100" data-slider-step="2" data-slider-value="${fixVal}" data-slider-orientation="vertical" data-slider-selection="after" data-slider-handle="round" data-slider-tooltip="hide" data-index="${fIx}" data-kms="50"></span></div>`;
                all +=`<div><label class="padding-10">${fIx<10 ? '0'+fIx : fIx}</label></div>`;
                all+= "</div>";
            }
            all+=`</div>`;
            return all;
        },
    }
})();

MacroCicloGet = (function(){
    return {
        consolidado: (mVC)=>{
            const rutinaData = {};
            rutinaData.general= {};
            rutinaData.general.freMin = document.querySelector('#FrecuenciaCardiacaMaxima').value;
            rutinaData.general.freMax = document.querySelector('#FrecuenciaCardiacaMinima').value;
            rutinaData.general.nvAtleta = Number(document.querySelector('#NivelAtleta input:checked').value);
            rutinaData.general.distancia = document.querySelector('#DistanciaRutina input:checked').value;
            //GEN - METRICAS CONTROL
            rutinaData.general.disControl = Number(document.querySelector('#DistanciaControl').value);
            rutinaData.general.tieControl = document.querySelector('#TiempoControl').value;
            rutinaData.general.cadControl = document.querySelector('#CadenciaControl').value;
            rutinaData.general.tcsControl = document.querySelector('#TcsControl').value;
            rutinaData.general.facDesControl = document.querySelector('#FactorDesentrenamientoControl').value;
            rutinaData.general.tieDesControl = document.querySelector('#TiempoDesentrControl').value;
            //GEN - METRICAS CONTROL
            rutinaData.general.disCompetencia = Number(document.querySelector('#DistanciaCompetencia').value);
            rutinaData.general.tieCompetencia = document.querySelector('#TiempoCompetencia').value;
            rutinaData.general.cadCompetencia = document.querySelector('#CadenciaCompetencia').value;
            rutinaData.general.tcsCompetencia = document.querySelector('#TcsCompetencia').value;
            rutinaData.general.facMejoria = document.querySelector('#FactorMejoria').value;

            //ESTADISTICAS GENERADAS SEGÚN INFORMACIÓN GENERAL
            rutinaData.stats = {};
            rutinaData.stats.ritmoXkm = document.querySelector('#RitmoXKilometro').value;
            rutinaData.stats.ritCompActual = document.querySelector('#RitmoCompetenciaActual').value;
            rutinaData.stats.ritAerActual = document.querySelector('#RitmoAerobicoActual').value;
            rutinaData.stats.ritAerPreComp = document.querySelector('#RitmoAerobicoPreComp').value;
            rutinaData.stats.lonPasoCompActual = Number(document.querySelector('#LongitudPasoCA').value);
            rutinaData.stats.kilometrajeTotal = Number(document.querySelector('#KilometrajeTotalTemporada').value);
            rutinaData.stats.kilometrajeProm = Number(document.querySelector('#KilometrajePromedioSemanal').value);
            rutinaData.stats.pasoSubida = document.querySelector('#PasoSubida').value;
            rutinaData.stats.pasoBajada = document.querySelector('#PasoBajada').value;
            rutinaData.stats.pasoPlano = document.querySelector('#PasoPlano').value;
            const cEstadsAdic = document.querySelector('#EstadisticasAdicionales');
            rutinaData.stats.rcps = Array.from(cEstadsAdic.querySelectorAll('.rcps')).map(v=>v.value == "" ? 0 : v.value).join('|');
            rutinaData.stats.raps = Array.from(cEstadsAdic.querySelectorAll('.raps')).map(v=>v.value == "" ? 0 : v.value).join('|');
            rutinaData.stats.cdcs = Array.from(cEstadsAdic.querySelectorAll('.cdcs')).map(v=>v.value == "" ? 0 : v.value).join('|');
            rutinaData.stats.lpcs = Array.from(cEstadsAdic.querySelectorAll('.lpcs')).map(v=>v.value == "" ? 0 : v.value).join('|');
            //MEJORAS Y DISTRIBUCION DE KMS
            const cProy = document.querySelector('#Proyecciones');

            rutinaData.mejoras = {};
            rutinaData.mejoras.porcGe = cProy.querySelector('.periodizacion-calc[data-type="1"][data-index="0"]').value;
            rutinaData.mejoras.semGe = cProy.querySelector('.periodizacion-calc[data-type="2"][data-index="3"]').value;
            rutinaData.mejoras.porcEs = cProy.querySelector('.periodizacion-calc[data-type="1"][data-index="1"]').value;
            rutinaData.mejoras.semEs = cProy.querySelector('.periodizacion-calc[data-type="2"][data-index="4"]').value;
            rutinaData.mejoras.porcPr = cProy.querySelector('.periodizacion-calc[data-type="1"][data-index="2"]').value;
            rutinaData.mejoras.semPr = cProy.querySelector('.periodizacion-calc[data-type="2"][data-index="5"]').value;

            rutinaData.mejoras.velPorcGe = cProy.querySelector('.velocidad-calc[data-type="1"][data-index="0"]').value;
            rutinaData.mejoras.velPorcEs = cProy.querySelector('.velocidad-calc[data-type="1"][data-index="1"]').value;
            rutinaData.mejoras.velPorcPr = cProy.querySelector('.velocidad-calc[data-type="1"][data-index="2"]').value;

            rutinaData.mejoras.cadPorcGe = cProy.querySelector('.cadencia-calc[data-type="1"][data-index="0"]').value;
            rutinaData.mejoras.cadPorcEs = cProy.querySelector('.cadencia-calc[data-type="1"][data-index="1"]').value;
            rutinaData.mejoras.cadPorcPr = cProy.querySelector('.cadencia-calc[data-type="1"][data-index="2"]').value;

            rutinaData.mejoras.tcsPorcGe = cProy.querySelector('.tcs-calc[data-type="1"][data-index="0"]').value;
            rutinaData.mejoras.tcsPorcEs = cProy.querySelector('.tcs-calc[data-type="1"][data-index="1"]').value;
            rutinaData.mejoras.tcsPorcPr = cProy.querySelector('.tcs-calc[data-type="1"][data-index="2"]').value;

            rutinaData.grafTemporada = MCGraficoData.paraTemporada($baseAfterComprobacion);
            //Métricas detalladas
            rutinaData.matrizMejoraVelocidades = JSON.stringify(mVC.map(v=>{return {dist: v.nombre, ind: v.indicadores.map(w=>w.p)}}));
            const collapseDetallados = document.querySelector('#collapseDetallados');
            const detCaden = collapseDetallados.querySelector('.detallados-cadencia');
            rutinaData.matrizMejoraCadencia = JSON.stringify(Array.from(detCaden.querySelectorAll('.mt-perc-cad')).map(v=>v.textContent));
            const detTcs = collapseDetallados.querySelector('.detallados-tcs');
            rutinaData.matrizMejoraTcs = JSON.stringify(Array.from(detTcs.querySelectorAll('.mt-perc-tcs')).map(v=>v.textContent));
            const detLongPaso = collapseDetallados.querySelector('.detallados-long-paso');
            rutinaData.matrizMejoraLonPaso = JSON.stringify(Array.from(detLongPaso.querySelectorAll('.mt-perc-lon')).map(v=>v.textContent));

            return rutinaData;
        },
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
                        const totSems = roundNumber(CalcProyecciones.calcularNumSemByPorcentaje(valor, ix), 0);
                        if (tipoProyeccion == 2 || tipoProyeccion == 3 || tipoProyeccion == 4) {
                        } else {
                            contProyecciones.querySelector(`${identificadores[0]}[data-index="${ix + 3}"]`).value = totSems;
                            contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(2)[0]}[data-index="${ix + 3}"]`).value = totSems;
                            contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(3)[0]}[data-index="${ix + 3}"]`).value = totSems;
                            contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(4)[0]}[data-index="${ix + 3}"]`).value = totSems;
                        }
                    } else {
                        const totPorc = roundNumber(CalcProyecciones.calcularPorcentajeByNumSem(valor, ix), 0);
                        contProyecciones.querySelector(`${identificadores[0]}[data-index="${ix - 3}"]`).value = totPorc;
                        contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(2)[0]}[data-index="${ix}"]`).value = valor;
                        contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(3)[0]}[data-index="${ix}"]`).value = valor;
                        contProyecciones.querySelector(`${MacroCicloGet.obtenerIdsProyeccionesByTipo(4)[0]}[data-index="${ix}"]`).value = valor;
                    }

                    const tot1 = CalcProyecciones.calcularTotalesDistribucion(contProyecciones, tipoProyeccion, 1),
                        tot2 = roundNumber(CalcProyecciones.calcularTotalesDistribucion(contProyecciones, tipoProyeccion, 2), 0);
                    const eleTot1 = contProyecciones.querySelector(`${identificadores[1]}`),
                        eleTot2 = contProyecciones.querySelector(` ${identificadores[2]}`);

                    contProyecciones.querySelector('#TotalVelocidad2').value = tot2;
                    contProyecciones.querySelector('#TotalCadencia2').value = tot2;
                    contProyecciones.querySelector('#TotalTcs2').value = tot2;

                    eleTot1.value = roundNumber(tot1, 0);
                    eleTot2.value = roundNumber(tot2, 0);

                    if(tot1 == 100){
                        eleTot1.parentElement.classList.add('state-success');
                        eleTot1.parentElement.classList.remove('state-error');
                    } else if (tot1 == 99 || tot1 == 101) {
                        eleTot1.parentElement.classList.add('state-success');
                        eleTot1.parentElement.classList.remove('state-error');
                        if(tot1 == 99){
                            eleTot1.value = 100;
                            contProyecciones.querySelector(`${identificadores[0]}[data-index="${2}"]`).value = 1+Number(contProyecciones.querySelector(`${identificadores[0]}[data-index="${2}"]`).value);
                        }else if(tot1 == 101){
                            eleTot1.value = 100;
                            contProyecciones.querySelector(`${identificadores[0]}[data-index="${2}"]`).value = -1+Number(contProyecciones.querySelector(`${identificadores[0]}[data-index="${2}"]`).value);
                        }

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
        calcularPorcentajeByNumSem: (cant, index) => {
            if(cant == 0) return 0;
            const estadisticas = CalcProyecciones.informacionSemanas();
            if(cant == estadisticas.semanas) return 100;
            let situacion = estadisticas.tipoCalculo;


            if (situacion == 1) {
                if(index == 3){
                    --cant;
                    return ((((cant*7)+estadisticas.diasPrimeraSemana)*100)/estadisticas.totDias).toFixed(2);
                }else
                    return (((cant*7)*100)/estadisticas.totDias).toFixed(2);
            } else if (situacion == 2){
                return (cant * 100 / estadisticas.semanas).toFixed(2);
            } else if (situacion == 3) {
                if(index == 5){
                    --cant;
                    return ((((cant*7)+estadisticas.diasUltimaSemana)*100)/estadisticas.totDias).toFixed(2);
                }else
                    return (((cant*7)*100)/estadisticas.totDias).toFixed(2);
            } else if (situacion == 4) {
                if(index == 3){
                    --cant;
                    return ((((cant*7)+estadisticas.diasPrimeraSemana)*100)/estadisticas.totDias).toFixed(2);
                }else if(index == 5){
                    --cant;
                    return ((((cant*7)+estadisticas.diasUltimaSemana)*100)/estadisticas.totDias).toFixed(2);
                }else
                    return (((cant*7)*100)/estadisticas.totDias).toFixed(2);
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
