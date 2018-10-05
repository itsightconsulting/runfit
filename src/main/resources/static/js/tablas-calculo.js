BaseCalculo = (function(){
    return {
        //C154:O155
        oficialesMedidasKms : [
            {dist: 50, medida: 50.000},
            {dist: 42, medida: 42.195},
            {dist: 21, medida: 21.098},
            {dist: 15, medida: 15.000},
            {dist: 10, medida: 10.000},
            {dist: 4, medida: 4.000},
            {dist: 2, medida: 2.000},
            {dist: 1, medida: 1.000},
            {dist: 0.8, medida: .800},
            {dist: 0.4, medida: .400},
            {dist: 0.2, medida: .200},
            {dist: 0.1, medida: .100},
        ],
        //D156:O157
        factorVelocidadByKms: [
            {dist: 50, factor: 1.06},
            {dist: 42, factor: 1.06},
            {dist: 21, factor: 1.06},
            {dist: 15, factor: 1.06},
            {dist: 10, factor: 1.06},
            {dist: 4, factor: 1.06},
            {dist: 2, factor: 1.06},
            {dist: 1, factor: 1.07},
            {dist: 0.8, factor: 1.07},
            {dist: 0.4, factor: 1.08},
            {dist: 0.2, factor: 1.1},
            {dist: 0.1, factor: 1.1},
        ],
        factorRitmosCompetenciaByNivel: [
            {nivel: 1, factor: 1.010},
            {nivel: 2, factor: 1.000},
            {nivel: 3, factor: 0.995},
        ],
        factorZonasCardiacas: [
            {nivel: 1, factor: 1.140},
            {nivel: 2, factor: 1.290},
            {nivel: 3, factor: 1.060},
            {nivel: 4, factor: 1.135},
            {nivel: 5, factor: 0.990},
            {nivel: 6, factor: 1.055},
            {nivel: 7, factor: 0.970},
            {nivel: 8, factor: 0.985},
            {nivel: 9, factor: 0.850},
            {nivel: 10, factor: 0.965},
        ],
        porcentajesZonaCardiaca: [
            {min: .48, max: .59},
            {min: .60, max: .70},
            {min: .71, max: .80},
            {min: .81, max: .89},
            {min: .90, max: .92},
            {min: .93, max: .95},
            {min: .96, max: .100},
        ],
        factoresCaloricos: [
            {zona: "Z1", factor: 1.0700},
            {zona: "Z2", factor: 1.0650},
            {zona: "Z3", factor: 1.0350},
            {zona: "Z4", factor: 1.0300},
            {zona: "Z5", factor: 1.0035},
            {zona: "Z6", factor: 1.0700},
            {zona: "Z7", factor: 0.9700},
        ]
    }
})();

//RITMOS PARA SESIONES DE ENTRENAMIENTO SEGÚN ZONA CARDIACA (en min/km)
RitmosSZC = (function(){
    return {
        getMetricasZonasCardiacas: ()=>{
            const base = FichaGet.obtenerBase();
            const paramInit = RitmosSZC.getParametroInicial().toSeconds();
            const paramComp = RitmosSZC.getParametroCompetencia().toSeconds();
            const metricasBase = RitmosSZC.getMetricasBaseZonasCardiacas();
            const margen = paramInit - paramComp;
            const porcentajes = base.distribucionPorcentaje;
            const factorPrimerElemento = BaseCalculo.factorZonasCardiacas[1].factor;
            const factorUltimoElemento = BaseCalculo.factorZonasCardiacas[BaseCalculo.factorZonasCardiacas.length-2].factor;
            const matriz = [];
            //Temp por conveniencia ya que tiene 7 elementos el array
            let ix1 = 0;//Se usa cuando el recorrido del segundo forEach se encuentra en indice 0
            metricasBase.forEach((v,fix, k)=>{
                matriz.push({nombre: "Z"+(fix+1), pMin: metricasBase[fix].min, pMax: metricasBase[fix].max, indicadores: []});
                let ix2 = -1;//Se inicia con -1 pues se autoincrementa nada más entrar al for y es donde se regulariza(0)
                base.periodizacion.forEach((p,six)=>{
                    for(let i=0; i<p; i++){ix2++;
                        if(i==0 && six==0){
                            if(fix == 0)
                                matriz[fix].indicadores.push({max: String(factorPrimerElemento * paramInit).toHHMMSSM(), preciso: Math.round(factorPrimerElemento * paramInit)});//+1 desde el inicio
                            else if(fix == k.length-1)
                                matriz[fix].indicadores.push({min: String(factorUltimoElemento * paramInit).toHHMMSSM()});//No es necesario preciso porque el dato siempre es fijo|-1 desde el final(se resta 2 por el comienzo del index en 0)
                            else{
                                matriz[fix].indicadores.push({min: String(BaseCalculo.factorZonasCardiacas[ix1].factor * paramInit).toHHMMSSM(),
                                    max: String(BaseCalculo.factorZonasCardiacas[ix1+1].factor * paramInit).toHHMMSSM(),
                                    preMin:  Math.round(BaseCalculo.factorZonasCardiacas[ix1].factor * paramInit),
                                    preMax:  Math.round(BaseCalculo.factorZonasCardiacas[ix1+1].factor * paramInit)});
                                ix1+=2;
                            }
                        }else{
                            let anterior;
                            if(fix == 0){
                                anterior = matriz[fix].indicadores[ix2 - 1].preciso;
                                const x1 = porcentajes[six]/p;
                                const x2 = factorPrimerElemento;
                                const precisoValor = (anterior - ((margen * x2) * x1));
                                const valor = String(precisoValor).toHHMMSSM();
                                matriz[fix].indicadores.push({max: valor, preciso: precisoValor});
                            } else if(fix == k.length-1){
                                anterior = matriz[fix].indicadores[ix2 - 1].min;
                                matriz[fix].indicadores.push({min: anterior});
                            } else {
                                const anteriorMin = matriz[fix].indicadores[ix2 - 1].preMin;
                                const anteriorMax = matriz[fix].indicadores[ix2 - 1].preMax;
                                const precisoValor =  factorPrimerElemento;
                                const v1 = String((anteriorMin - ((margen) * precisoValor) * ((porcentajes[six]))/p)).toHHMMSSM();
                                //const v1 = String((ref1 - ((margen) * BaseCalculo.factorZonasCardiacas[BaseCalculo.factorZonasCardiacas.length-2].factor) * (((porcentajes[six])/100))/p)).toHHMMSSM();
                                const v2 = String((anteriorMax - ((margen) * factorPrimerElemento) * ((porcentajes[six]))/p)).toHHMMSSM();
                                matriz[fix].indicadores.push({min: v1, max: v2,
                                    preMin: (anteriorMin - ((margen) * precisoValor) * (((porcentajes[six])/100))/p),
                                    preMax: (anteriorMax - ((margen) * factorPrimerElemento) * ((porcentajes[six]))/p)});
                            }
                        }
                    }
                })
            })
            return matriz;
        },
        getParametroInicial: ()=>{//REF MACRO!F161
            const disControl = document.querySelector('#DistanciaControl').value;
            const metrosDisControl = BaseCalculo.oficialesMedidasKms.filter(v=>{return v.dist == disControl})[0].medida * 1000;
            const tiempoControl = document.querySelector('#TiempoDesentrControl').value.toSeconds();
            return RitmosSZC.getTiempoPorKilometro(metrosDisControl, tiempoControl);
        },
        getParametroCompetencia: ()=>{//REF MACRO!J161
            const disControl = document.querySelector('#DistanciaCompetencia').value;
            const metrosDisCompetencia = BaseCalculo.oficialesMedidasKms.filter(v=>{return v.dist == disControl})[0].medida * 1000;
            const tiempoCompetencia = document.querySelector('#TiempoCompetencia').value.toSeconds();
            return RitmosSZC.getTiempoPorKilometro(metrosDisCompetencia, tiempoCompetencia);
        },
        getTiempoPorKilometro: (metros, tiempo)=>{
            const factorManual = 1.03//REF MACRO!E162
            const vo2max = (0.8+0.1894393 * Math.exp(-0.012778 * tiempo / 60)+0.2989558* Math.exp(-0.1932605*tiempo / 60));//REF T.D!D9
            const p1 = (metros/tiempo) * 60;
            const p2 = Math.pow((metros/tiempo) * 60, 2);
            const vdot = roundNumber(((-4.6 + 0.182258 * (p1) + 0.000104 *p2)/(vo2max)), 1);
            const millas = (1/(29.54 + 5.000663 * (vdot*0.88) - 0.007546 * Math.pow((vdot*0.88),2))*1609.344*60);
            return String((millas * 0.621371192) * factorManual).toHHMMSSM();//tiempo por kilometro
        },
        getMetricasBaseZonasCardiacas: ()=>{
            const fcm = Number(document.querySelector('#FrecuenciaCardiacaMaxima').value);
            const fcmin = Number(document.querySelector('#FrecuenciaCardiacaMinima').value);
            const diff = fcm - fcmin;
            return BaseCalculo.porcentajesZonaCardiaca.map(v=>{
                return {min: Math.round((diff * v.min) + fcmin), max: Math.round((diff * v.max) + fcmin)};
            })
        },
    }
})();

Calc = (function(){
    return {
        setRestantes: ()=>{
            const tiempoDesentrControl = document.querySelector('#TiempoDesentrControl').value.toSeconds();
            const disCompetencia = Number(document.querySelector('#DistanciaCompetencia').value);
            const medidaDisCompetencia = BaseCalculo.oficialesMedidasKms.filter(v=>{return disCompetencia == v.dist})[0].medida;
            const distanciaControl = Number(document.querySelector('#DistanciaControl').value);
            const medidaDisControl = BaseCalculo.oficialesMedidasKms.filter(v=>{return distanciaControl == v.dist})[0].medida;
            const factorRitmoCompetencia = BaseCalculo.factorRitmosCompetenciaByNivel[Number(document.querySelector('#NivelAtleta input:checked').value)-1].factor;
            const factorDisCompetencia = BaseCalculo.factorVelocidadByKms.filter(v=>{return disCompetencia == v.dist})[0].factor;
            const cadencia = Number(document.querySelector('#CadenciaControl').value);
            $('#RitmoCompetenciaActual').val(String(tiempoDesentrControl * Math.pow((medidaDisCompetencia/medidaDisControl) * (factorRitmoCompetencia), factorDisCompetencia) / medidaDisCompetencia).toHHMMSSM());
            $('#RitmoXKilometro').val(String(document.querySelector('#TiempoCompetencia').value.toSeconds()/medidaDisCompetencia).toHHMMSSM());
            $('#LongitudPasoCA').val(((3600*24*1000)/(String($('#RitmoCompetenciaActual').val()).toSeconds()*24*60*cadencia)).toFixed(2));
        },
        getDistribucionTiempoPlanificado: (base)=>{
            const meta = document.querySelector('#RitmoXKilometro').value.toSeconds();
            const inicial = document.querySelector('#RitmoCompetenciaActual').value.toSeconds();
            const margen =  inicial - meta;
            const finales = [];
            let it = 0;
            base.periodizacion.forEach((v,i)=>{
                for(let k=0; k<v; k++){
                    if(it == 0){
                        finales.push({factor: String(inicial).toHHMMSSM(), preciso: Math.round(inicial)})
                    } else {
                        const preciso = finales[it-1].preciso;
                        const x1 = base.distribucionPorcentaje[i]/v;
                        const final = preciso - margen * x1;
                        finales.push({factor: String(final).toHHMMSSM(), preciso: final})
                    }
                    it++;
                }
            });
            return finales;
        },
        getRitmosEntrenamientoAerobico: (ritmoAerobicoActual, ritmoAerobicoPreComp, base)=>{
            let ritmoBase = ritmoAerobicoActual - ritmoAerobicoPreComp;
            const arrRitmos = [];
            let it = 0;
            base.periodizacion.forEach((v,i)=>{
                for(let k=0; k<v; k++){
                    if(it == 0){
                        arrRitmos.push({factor: String(ritmoAerobicoActual).toHHMMSSM(), preciso: Math.round(ritmoAerobicoActual)})
                    } else {
                        const preciso = arrRitmos[it-1].preciso;
                        const x1 = base.distribucionPorcentaje[i]/v;
                        const final = preciso - ritmoBase * x1;
                        arrRitmos.push({factor: String(final).toHHMMSSM(), preciso: final})
                    }
                    it++;
                }
            });
            return arrRitmos;
        },
        getRitmosCadenciaCompetencia: (cadActual, cadCompetencia, base)=>{
            let ritmoBase = cadCompetencia - cadActual ;
            const arrRitmos = [];
            let it = 0;
            base.periodizacion.forEach((v,i)=>{
                for(let k=0; k<v; k++){
                    if(it == 0){
                        arrRitmos.push({factor: Math.round(cadActual), preciso: Math.round(cadActual)})
                    } else {
                        const anterior = arrRitmos[it-1].preciso;
                        const x1 = base.distribucionPorcentaje[i]/v;
                        const final = anterior + ritmoBase * x1;
                        arrRitmos.push({factor: Math.round(final), preciso: final})
                    }
                    it++;
                }
            });
            return arrRitmos;
        },
        getLongitudesDePaso: (ritmosCompetencia, ritmosCadencia)=>{
            return ritmosCompetencia.map((v,i)=>{
                return roundNumber(1000/((ritmosCompetencia[i].preciso/60)*ritmosCadencia[i].preciso),2);
            })
        },
        getRitmosAerobicos: ()=>{
            const MZC = RitmosSZC.getMetricasZonasCardiacas();
            const Z3 = MZC[2].indicadores;//Se utiliza la zona de intensidad Z3 siempre
            const ZELength = Z3.length - 1;
            const FZ3Max = Z3[0].max;//Primer Z3 Max
            const FZ3Min = Z3[0].min;//Primer Z3 Max
            const LZ3Max = Z3[ZELength].max;
            const LZ3Min = Z3[ZELength].min;
            //Se evalua extremos
            const ZEMaxFinal1 = FZ3Max.toSeconds() > LZ3Max.toSeconds() ? FZ3Max : LZ3Max;
            const ZEMinFinal1 = FZ3Min.toSeconds() > LZ3Min.toSeconds() ? FZ3Min : LZ3Min;

            const ZEMaxFinal2 = FZ3Max.toSeconds() < LZ3Max.toSeconds() ? FZ3Max : LZ3Max;
            const ZEMinFinal2 = FZ3Min.toSeconds() < LZ3Min.toSeconds() ? FZ3Min : LZ3Min;

            const ritmoAerobicoActual = ((ZEMaxFinal1.toSeconds() + ZEMinFinal1.toSeconds())/2);
            const ritmoAerobicoPreComp = ((ZEMaxFinal2.toSeconds() + ZEMinFinal2.toSeconds()) / 2);

            $('#RitmoAerobicoActual').val(String(ritmoAerobicoActual).toHHMMSSM());//Siempre va ser Z3 == index 2
            $('#RitmoAerobicoPreComp').val(String(ritmoAerobicoPreComp).toHHMMSSM());//Siempre va ser Z3 == index 2

            return {actual: ritmoAerobicoActual, preCompetitivo: ritmoAerobicoPreComp};
        }
    }
})();

function roundNumber(num, scale) {
    if(!("" + num).includes("e")) {
        return +(Math.round(num + "e+" + scale)  + "e-" + scale);
    } else {
        var arr = ("" + num).split("e");
        var sig = ""
        if(+arr[1] + scale > 0) {
            sig = "+";
        }
        return +(Math.round(+arr[0] + "e" + sig + (+arr[1] + scale)) + "e-" + scale);
    }
}