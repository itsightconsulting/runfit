
let distrito;

(function () {
    init();



})();


function init(){

 cargarDataCliente();

}


function cargarDataCliente(){


  if(!$('#pruebaData').val()){

          $.ajax({
              type: "GET",
              url: _ctx + 'gestion/cliente-fitness/obtener/completo',
              dataType: "json",
              blockLoading: false,
              success:  function(data){
                generarDOMDataCliente(data);
              },
              error:   function(xhr){
                exception(xhr);

              },
              complete: function(){

              }
          })
  }else{
      const dataCliente = JSON.parse($('#pruebaData').val());
      dataCliente ? generarDOMDataCliente(dataCliente) : "";
  }



}


function generarDOMDataCliente(data) {

    const dvDatosPersonales = document.getElementById('dvDatosPersonales');
    const nombres = data.nombres;
    const apellidos = data.apellidos;
    const fechaNacimiento =  data.fechaNacimiento;
    const tlfMovil = data.movil;
    const correo = data.correo;
    const tipoDoc = data.tipoDocumento;
    const numDoc = data.numeroDocumento;
    const fechaCreacion = data.fechaCreacion.slice(0,10);
    const distrito = getDistritobyUbigeo(data.ubigeo);


    dvDatosPersonales.appendChild(htmlStringToElement(`    

            <div class="panel-group">
              <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title" >
                    <img class="svg" src="${_ctx}img/iconos-trainers/icon_perfil.svg">
                     Datos Personales 
                     <a data-toggle="collapse" href="#collapseDatosPersonales">
                       <img class="arrow svg" src="${_ctx}img/iconos-trainers/icon_flecha2.svg">
                     </a></h3>
                </div>

                <div class="panel-collapse collapse" id="collapseDatosPersonales">
                                         
                         <div class="row" id="dvNombres">
                         
                          <div class="col-md-12">
                        
                             <div class="col-md-6">
                                <h4> Nombres</h4>
                                <span>${nombres}</span>
                             </div>

                              <div class="col-md-6">
                                 <h4> Apellidos </h4>
                                 <span>${apellidos}</span>
                              </div>
                                                        
                            </div>
                   
                          </div>
                          
                         <div class="row" id="dvDocumentoTipo">
                         
                           <div class="col-md-12">
                        
                            <div class="col-md-6">
                                  <h4> Tipo de Documento</h4>
                                  <span>${tipoDoc}</span>
                            </div>
                             
                            <div class="col-md-6">
                                  <h4> Número de Documento</h4>
                                  <span>${numDoc}</span>
                            </div>
                            
                           </div>
                         </div>
                            
                         <div class="row" id="dvFechas">
                         
                          <div class="col-md-12">
                        
                            <div class="col-md-6">
                        
                               <h4> Fecha de Nacimiento</h4>
                               <span>${fechaNacimiento}</span>
                             </div>
                             
                           
                             <div class="col-md-6">

                                <h4> Distrito</h4>
                                <span id="spDistrito">${distrito}</span>
                                
                             </div>
                             
                           </div>
                         </div>

                </div>
                
               </div>
              
              
              <div class="panel panel-default">
            
                
                       <div class="panel-heading">
                            <h3 class="panel-title" >
                            <img class="svg" src="${_ctx}img/iconos-trainers/icon_perfil.svg">
                             Datos de contacto 
                             <a data-toggle="collapse" href="#collapseDatosContacto">
                               <img class="arrow svg" src="${_ctx}img/iconos-trainers/icon_flecha2.svg">
                             </a></h3>
                       </div>
                       
                       
                       <div class="panel-collapse collapse" id="collapseDatosContacto">
                    
                               
                                 <div class="row" id="dvTelefonosContacto">
                                  <div class="col-md-12">
                                  
                                    <div class="col-md-6" id="dvTelefonoMovil">
                                     <h4> Télefono Móvil</h4>
                                     <span>${tlfMovil}</span>
                                    </div>
        
                                   <div class="col-md-6" id="dvCorreoElectronico">
                                      <h4> Correo Electrónico</h4>
                                      <span>${correo}</span>
                                   </div>
                                  </div>
                                 </div>
        
                         </div>
                    
                    </div>
            
                </div>
    `));
}
;



function getDistritobyUbigeo(ubigeoId){

   $.ajax({
      type: "GET",
      data: {ubi : ubigeoId},
      dataType: 'text',
      url: _ctx + 'p/ubigeo/get/peru-dis-by-ubi',
      success: function(data){
          distrito = data;
          $('#spDistrito').text(distrito);

      },
      error: function(xhr){


      },
      complete: function(){
      }



   });



}
