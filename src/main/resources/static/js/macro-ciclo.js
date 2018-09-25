Ficha = (function(){
    return {
        instanciar: (ficha)=>{
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
            tablaCompetencias.appendChild(Ficha.instanciarCompetencias(ficha.competencias));
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
        },
        obtenerMaximaFechaCompeticiones: ()=>{
            let maxFecha  = $fechasCompetencia[0];
            $fechasCompetencia.forEach((v,i)=>{
                if(i != 0){
                    maxFecha = v.getTime() > maxFecha.getTime() ? v : maxFecha;
                }
            })
            return maxFecha;
        }
    }
})();

MacroCiclo = (function(){
    return {
        calcularSemanas: ()=>{
            const fechaInicio = parseFromStringToDate($('#MacroFechaInicio').val()), fechaFin = parseFromStringToDate($('#MacroFechaFin').val());
            const totDias = moment(fechaFin).diff(fechaInicio, 'days') + 1;
            const diasPrimeraSemana = fechaInicio.getDay() == 0? 1 :7 - fechaInicio.getDay() + 1;
            const diasUltimaSemana = (totDias - diasPrimeraSemana) % 7 == 0 ? 7 : (totDias - diasPrimeraSemana) % 7;
            let semanas = diasPrimeraSemana == 7 ? Math.ceil(totDias/7) : 1+Math.ceil((totDias-diasPrimeraSemana)/7);
            let totSemNonFull = diasPrimeraSemana != 7 ? 1 : 0;totSemNonFull += diasUltimaSemana != 7 ? 1 : 0;
            const raw = `
                        <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Semanas full: <b>${semanas - totSemNonFull}</b></label></div>
                        <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Semanas no-full: <b>${totSemNonFull == 2 ? totSemNonFull +' (S.1 & S.'+semanas+')': totSemNonFull } </b></label></div>
                        <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Semanas totales: <b>${semanas}</b></label></div>
                        <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Días totales de primera semana: <b>${diasPrimeraSemana}</b></label></div>
                        <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Días totales de última semana: <b>${diasUltimaSemana}</b></label></div>
                        <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Días restantes: <b>${totDias - diasPrimeraSemana - diasUltimaSemana}</b></label></div>
                        <div class='col-md-12 padding-0'><label class="padding-0"> <i class="fa fa-angle-right font-xs fa-fw"></i>Días totales: <b>${totDias}</b></label></div>`
            $.smallBox({timeout: 15000, content: `${raw}`, color: "#4a4e3b"});
            document.querySelector('#MacroTotalSemanas').textContent = semanas;
        },
        calcularTotalesDistribucion: (tipoCat, tipoCalc)=>{
            let acum = 0;
            switch(tipoCat){
                case 1:
                    Array.from(document.querySelectorAll(`#tabFichaTecnica .periodizacion-calc[data-type="${tipoCalc}"]`)).forEach(v=>{
                        acum+=!isNaN(v.value) ? Number(v.value) : 0;
                    });
                    break;
                case 2:
                    Array.from(document.querySelectorAll(`#tabFichaTecnica .velocidad-calc[data-type="${tipoCalc}"]`)).forEach(v=>{
                        acum+=!isNaN(v.value) ? Number(v.value) : 0;
                    });
                    break;
                case 3:
                    Array.from(document.querySelectorAll(`#tabFichaTecnica .cadencia-calc[data-type="${tipoCalc}"]`)).forEach(v=>{
                        acum+=!isNaN(v.value) ? Number(v.value) : 0;
                    });
                    break;
                default:
                    Array.from(document.querySelectorAll(`#tabFichaTecnica .tcs-calc[data-type="${tipoCalc}"]`)).forEach(v=>{
                        acum+=!isNaN(v.value) ? Number(v.value) : 0;
                    });
            }

            return acum;
        }
    }
})();