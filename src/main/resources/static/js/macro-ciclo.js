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

            const comps = ficha.competencias;
            tablaCompetencias.appendChild(Ficha.instanciarCompetencias(ficha.competencias));
            //
            let distanciaMax = comps[0].distancia;
            comps.forEach((v,i)=>{ if(i!=0) distanciaMax = v.distancia > distanciaMax ? v.distancia: distanciaMax;})
            document.querySelectorAll('#DistanciaMainCompetencia input').forEach(v=>{v.value == distanciaMax ? v.checked=true : '';})

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
            mainContainer.appendChild(htmlStringToElement(`<input id="W" type="text" class="slider slider-danger padding-5" value=""
                                                               data-slider-min="100"
                                                               data-slider-max="0"
                                                               data-slider-step="2"
                                                               data-slider-value="50"
                                                               data-slider-orientation="vertical"
                                                               data-slider-selection="after"
                                                               data-slider-handle="square"
                                                               data-slider-tooltip="hide" data-index="KT3"/>`));
                $('#W').slider();

            if(validado && Ficha.obtenerNivelAtleta() != undefined) {
                $.smallBox({color: "success", content: "<b>Se ha generado con éxito</b>", color: "#73f194"});
            }else{
                $.smallBox({color: "alert", content: "Error..."});
            }
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
        }
        , calcularSemanas: () => {
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
        }
    }
})();