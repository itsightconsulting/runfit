(function () {
    init();



})();


function init(){

 cargarDataCliente();

}


function cargarDataCliente(){

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
}


function generarDOMDataCliente(data) {

    const dvDatosPersonales = document.getElementById('dvDatosPersonales');
    const nombres = data.cliente.nombres;
    const apellidos = data.cliente.apellidos;
    const fechaNacimiento = data.cliente.fechaNacimiento;
    const tlfMovil = data.cliente.movil;
    const tlfFijo = data.cliente.telefono;
    const correo = data.cliente.correo;
    const tipoDoc = data.cliente.tipoDocumento;
    const numDoc = data.cliente.numeroDocumento;
    const fechaCreacion = data.cliente.fechaCreacion;

    dvDatosPersonales.appendChild(htmlStringToElement(`    

                    <div class="panel-body">

                         <div class="panel-heading">
                                <h3>Datos Personales
                                 </h3>
                         </div>
                   
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
                          <div>                             
                        </div>
                        
                        </div>
                        
                        <div class="row" id="dvFechas">
                         
                          <div class="col-md-12">
                        
                            <div class="col-md-6">
                        
                               <h4> Fecha de Nacimiento</h4>
                               <span>${fechaNacimiento}</span>
                             </div>
                             
                           
                             <div class="col-md-6">

                                <h4> Fecha de creación de cuenta</h4>
                                <span>${fechaCreacion}</span>
                             </div>
                             
                           </div>
                         </div>


                         <div class="panel-heading">
                            <h3>Datos de contacto</h3>
                         </div>
                         
                         <div class="row" id="dvTelefonosContacto">
                           <div class="col-md-6" id="dvTelefonoMovil">
                             <h4> Télefono Móvil</h4>
                             <span>${tlfMovil}</span>
                            </div>

                           <div class="col-md-6" id="dvTelefonoFijo">
                              <h4> Télefono Fijo</h4>
                              <span>${tlfFijo}</span>
                           </div>
                         </div>

            
                        <div class="row" id="dvCorreoElectronico">
                   
                          <div class="col-md-6">
                          
                             <h4> Correo Electrónico</h4>
                             <span>${correo}</span>
                         </div>
                     
                        </div>
                </div>
    
    
`));
}
;
