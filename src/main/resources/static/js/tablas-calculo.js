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
        }
    }
})();

