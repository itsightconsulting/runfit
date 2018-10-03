//C154:O155
OficialesMedidasKms = [
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
]
//D156:O157
FactorVelocidadByKms = [
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
]

FactorRitmosCompetenciaByNivel = [
    {nivel: 1, factor: 1.010},
    {nivel: 2, factor: 1.000},
    {nivel: 3, factor: 0.995},
]

FactorZonasCardiacas = [
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
];

PorcentajesZonaCardiaca = [
    {min: 48, max: 59},
    {min: 60, max: 70},
    {min: 71, max: 80},
    {min: 81, max: 89},
    {min: 90, max: 92},
    {min: 93, max: 95},
    {min: 96, max: 100},
];

Calc = (function(){
    return {
        getAll: ()=>{
            const obj = {};
            const m0 = document.querySelector('#TiempoDesentrControl').value.toSeconds();
            const d1 = Number(document.querySelector('#DistanciaCompetencia').value)
            const m1 = OficialesMedidasKms.filter(v=>{return d1 == v.dist})[0].medida;
            const d2 = Number(document.querySelector('#DistanciaControl').value)
            const m2 = OficialesMedidasKms.filter(v=>{return d2 == v.dist})[0].medida;
            const m3 = FactorRitmosCompetenciaByNivel[Number(document.querySelector('#NivelAtleta input:checked').value)-1].factor
            const m4 = FactorVelocidadByKms.filter(v=>{return d1 == v.dist})[0].factor;
            obj.ritmoCompetenciaActual = String(m0 * Math.pow(
                (m1/m2) * (m3), m4) / m1).toHHMMSSM();
            obj.ritmoXkilometro = String(document.querySelector('#TiempoCompetencia').value.toSeconds()/m1).toHHMMSSM();
            const cadencia = Number(document.querySelector('#CadenciaControl').value);
            obj.longitudPasoCompActual = ((3600*24*1000)/(String(obj.ritmoCompetenciaActual).toSeconds()*24*60*cadencia)).toFixed(2);
            return obj;

            //Logintud de paso de competencia actual

        },
        getDistribucionTiempoPlanificado: (base)=>{
            const meta = document.querySelector('#RitmoXKilometro').value.toSeconds()
            const inicial = document.querySelector('#RitmoCompetenciaActual').value.toSeconds();
            const segundos =  inicial - meta;
            const constantes = base.distribucionPorcentaje.map(v=>{
                return ((Number(v)/100)*segundos).toFixed(2);
            }).map((v,i)=>{
                return {etapa: i, constante: v/base.periodizacion[i]};
            });

            let temp = inicial;
            let finales = [];
            finales[0] = String(temp).toHHMMSSM();
            base.periodizacion.forEach((v,k)=>{
               for(let i=0; i<v;i++){
                if(i== 0 && k==0){}else{
                    temp -=constantes[k].constante;
                    finales.push(String(temp).toHHMMSSM());
                }
               }
            });
            return finales;
        },
        getMetricasBaseZonasCardiacas: ()=>{
            const fcm = Number(document.querySelector('#FrecuenciaCardiacaMaxima').value);
            const fcmin = Number(document.querySelector('#FrecuenciaCardiacaMinima').value);
            const diff = fcm - fcmin;
            return PorcentajesZonaCardiaca.map(v=>{
                return {min: Math.round((diff * (v.min/100)) + fcmin), max: Math.round((diff * (v.max/100)) + fcmin)};
            })
        },
        getParametroInicial: ()=>{//REF MACRO!F161
            const factorManual = 1.03//REF MACRO!E162
            const disControl = document.querySelector('#DistanciaControl').value;
            const metrosDisControl = OficialesMedidasKms.filter(v=>{return v.dist == disControl})[0].medida * 1000;
            const factDesen = document.querySelector('#TiempoDesentrControl').value.toSeconds();
            const vo2max = (0.8+0.1894393 * Math.exp(-0.012778 * factDesen / 60)+0.2989558* Math.exp(-0.1932605*factDesen / 60));//REF T.D!D9
            const p1 = (metrosDisControl/factDesen) * 60;
            const p2 = Math.pow((metrosDisControl/factDesen) * 60, 2);
            const vdot = roundNumber(((-4.6 + 0.182258 * (p1) + 0.000104 *p2)/(vo2max)), 1);
            const millas = (1/(29.54 + 5.000663 * (vdot*0.88) - 0.007546 * Math.pow((vdot*0.88),2))*1609.344*60);
            return String((millas * 0.621371192) * factorManual).toHHMMSSM();//tiempo por kilometro
        },
        getParametroCompetencia: ()=>{//REF MACRO!J161
            const factorManual = 1.03//REF MACRO!E162
            const disControl = document.querySelector('#DistanciaCompetencia').value;
            const metrosDisControl = OficialesMedidasKms.filter(v=>{return v.dist == disControl})[0].medida * 1000;
            const factDesen = document.querySelector('#TiempoCompetencia').value.toSeconds();
            const vo2max = (0.8+0.1894393 * Math.exp(-0.012778 * factDesen / 60)+0.2989558* Math.exp(-0.1932605*factDesen / 60));//REF T.D!D9
            const p1 = (metrosDisControl/factDesen) * 60;
            const p2 = Math.pow((metrosDisControl/factDesen) * 60, 2);
            const vdot = roundNumber(((-4.6 + 0.182258 * (p1) + 0.000104 *p2)/(vo2max)), 1);
            const millas = (1/(29.54 + 5.000663 * (vdot*0.88) - 0.007546 * Math.pow((vdot*0.88),2))*1609.344*60);
            return String((millas * 0.621371192) * factorManual).toHHMMSSM();//tiempo por kilometro
        },
        getMetricasZonasCardiacas: ()=>{
            const base = MacroCiclo.obtenerDatosMacroBase();
            const paramInit = Calc.getParametroInicial().toSeconds();
            const paramComp = Calc.getParametroCompetencia().toSeconds();
            const diffInitComp = paramInit - paramComp;
            const metriBase = Calc.getMetricasBaseZonasCardiacas();
            const porcentajes = base.distribucionPorcentaje;
            const matriz = [];
            //Temp por conveniencia ya que tiene 7 elementos el array
            let artifical = 0;
            metriBase.forEach((v,fix, k)=>{
               matriz.push({nombre: "Z"+(fix+1), indicadores: []});
               let artifical2 = -1;
               base.periodizacion.forEach((p,six)=>{
                    for(let i=0; i<p; i++){
                        artifical2++;
                        if(i==0 && six==0){
                            if(fix == 0)
                                matriz[fix].indicadores.push({max: String(FactorZonasCardiacas[1].factor * paramInit).toHHMMSSM(), preciso: Math.round(FactorZonasCardiacas[1].factor * paramInit)});//+1 desde el inicio
                            else if(fix == k.length-1)
                                matriz[fix].indicadores.push({min: String(FactorZonasCardiacas[FactorZonasCardiacas.length-2].factor * paramInit).toHHMMSSM()});//-1 desde el final(se resta 2 por el comienzo del index en 0)
                            else{
                                matriz[fix].indicadores.push({min: String(FactorZonasCardiacas[artifical].factor * paramInit).toHHMMSSM(),
                                                              max: String(FactorZonasCardiacas[artifical+1].factor * paramInit).toHHMMSSM(),
                                                              preMin:  Math.round(FactorZonasCardiacas[artifical].factor * paramInit),
                                                              preMax:  Math.round(FactorZonasCardiacas[artifical+1].factor * paramInit)});
                                artifical+=2;
                            }
                        }else{
                            let ref;
                            if(fix == 0){
                                ref = matriz[fix].indicadores[artifical2 - 1].preciso;
                                const x1 = (porcentajes[six]/100)/p;
                                const x2 = FactorZonasCardiacas[1].factor;
                                const precisoValor = (ref - ((diffInitComp * x2) * x1));
                                const valor = String(precisoValor).toHHMMSSM();
                                matriz[fix].indicadores.push({max: valor, preciso: precisoValor});
                            } else if(fix == k.length-1){
                                ref = matriz[fix].indicadores[artifical2 - 1].min;
                                matriz[fix].indicadores.push({min: ref});
                            } else {
                                const ref1 = matriz[fix].indicadores[artifical2 - 1].preMin;
                                const ref2 = matriz[fix].indicadores[artifical2 - 1].preMax;
                                const precisoValor =  FactorZonasCardiacas[1].factor;
                                const v1 = String((ref1 - ((diffInitComp) * precisoValor) * (((porcentajes[six])/100))/p)).toHHMMSSM();
                                //const v1 = String((ref1 - ((diffInitComp) * FactorZonasCardiacas[FactorZonasCardiacas.length-2].factor) * (((porcentajes[six])/100))/p)).toHHMMSSM();
                                const v2 = String((ref2 - ((diffInitComp) * FactorZonasCardiacas[1].factor) * (((porcentajes[six])/100))/p)).toHHMMSSM();
                                matriz[fix].indicadores.push({min: v1, max: v2,
                                                preMin: (ref1 - ((diffInitComp) * precisoValor) * (((porcentajes[six])/100))/p),
                                                preMax: (ref2 - ((diffInitComp) * FactorZonasCardiacas[1].factor) * (((porcentajes[six])/100))/p)});
                            }
                        }
                    }
               })
            })
            return matriz;
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

