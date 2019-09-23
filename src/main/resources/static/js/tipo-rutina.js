    var $table = $('#tblRegistros');
    var $index = $('#Id');


    var body = $("body")[0];

    console.log("test ");


    $(function () {

        pageSetUp();
        listarRegistros();

        $('#Operacion').text('Registrar');


        body.addEventListener("focusout", bodyFocusOutEventListener);

        //V A L I D A T E
        validarRegistros();

        $("#btnNuevo").click(function () {
            $('#Operacion').text('Registrar');
            irRegistro();
        });

        $("#btnGuardar").click(function () {
            registro();
        });

        $("#btnCancelar").click(function () {
            irListado($index);
        });

        $("#btnFiltrar").click(function () {
            listarRegistros();
        });

        $('#frm_busqueda').submit(function (e) {
            e.preventDefault();
        });
    });

    function listarRegistros() {

        if ($("#frm_busqueda").valid()) {

            $table.bootstrapTable('destroy');
            var txtFiltro = $("#txtFiltro").val();
            var estado = $("#Estado").val();

            if (txtFiltro == null || txtFiltro == "") {
                txtFiltro = "0";
            }
            if (estado == null) {
                estado = "-1";
            }
            //Demo
            $table.bootstrapTable({
                url: _ctx + 'gestion/tipo-rutina/obtener/' + txtFiltro + '/' + estado ,
                pagination: true,
                sidePagination: 'client',
                pageSize: 10,
                onLoadSuccess: d => {
                },
                onLoadError: function (xhr) {
                    exception(xhr);
                },
            });
        }
    }

    function registro() {
        if ($("#frm_registro").valid()) {
            var params = getFormData($('#frm_registro'));
            params.flagActivo = $('#FlagActivo').is(':checked');


            if($("#Id").val() === "0"){

                      $.ajax({
                                    type: 'POST',
                                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                                    url: _ctx + 'gestion/tipo-rutina/agregar',
                                    dataType: "json",
                                    data: params,
                                    blockLoading: true,
                                    noOne: false,
                                    success: function (data, textStatus) {
                                        if (textStatus == "success") {
                                            if (data == "-9") {
                                                $.smallBox({
                                                    content: "<i> La operación ha fallado debido a que el nombre de tipo de rutina ya existe en el sistema...</i>",
                                                    timeout: 4500,
                                                    color: "alert",
                                                });
                                            }
                                            if (params.id > 0) {
                                                $.smallBox({
                                                    content: "<i class='fa fa-child'></i> <i>Actualización exitosa...!</i>",
                                                });
                                            }
                                        }
                                    },
                                    error: function (xhr) {
                                        exception(xhr);
                                    },
                                    complete: function () {
                                        irListado($index);
                                        listarRegistros();
                                        limpiarBusqueda();
                                    }
                                });
            }else{

                  $.ajax({
                                type: 'POST',
                                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                                url: _ctx + 'gestion/tipo-rutina/actualizar',
                                dataType: "json",
                                data: params,
                                blockLoading: true,
                                noOne: false,
                                success: function (data, textStatus) {
                                    if (textStatus == "success") {
                                        if (data == "-9") {
                                            $.smallBox({
                                                content: "<i> La operación ha fallado debido a que el nombre de tipo de rutina ya existe en el sistema...</i>",
                                                timeout: 4500,
                                                color: "alert",
                                            });
                                        }
                                        if (params.id > 0) {
                                            $.smallBox({
                                                content: "<i class='fa fa-child'></i> <i>Actualización exitosa...!</i>",
                                            });
                                        }
                                    }
                                },
                                error: function (xhr) {
                                    exception(xhr);
                                },
                                complete: function () {
                                    irListado($index);
                                    listarRegistros();
                                    limpiarBusqueda();
                                }
                            });

            }




        }
    }

    function editar(id) {

        irRegistro();
        $('#Operacion').text('Actualizar');

           var param = new Object();
                param.id = id;


        var param = new Object();
        param.id = id;

        $("#load_pace").show();
        $.ajax({
            type: 'GET',
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            url: _ctx + 'gestion/tipo-rutina/consultar',
            dataType: "json",
            data: param,
            blockLoading: true,
            noOne: false,
            success: function (data, textStatus) {

                if (textStatus == "success") {
                    $("#Id").val(data.id);
                    $("#Nombre").val(data.nombre);
                    data.flagActivo === true ?
                        $('#FlagActivo').prop('checked', true)
                        :
                        $('#FlagActivoOff').prop('checked', true);

                }
            },
            error: function (xhr) {
                exception(xhr);
            },
            complete: function () {
                limpiarBusqueda();
            }
        });

    }

    function desactivar(id, ix) {
        var actualStatus = document.querySelector('#tblRegistros tbody tr[data-index=\'' + ix + '\'] .td-status').textContent;

        $.SmartMessageBox({
            title: '<i class="fa fa-exclamation-triangle" style="color: yellow"></i> RUNFIT',
            content: "<br/>¿Quieres activar o desactivar el registro?",
            buttons: '[No][Si]'
        }, function (ButtonPressed) {
            if (ButtonPressed === "Si") {
                var params = {};
                params.id = id;

                $.ajax({
                    type: 'PUT',
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    url: _ctx + 'gestion/tipo-rutina/actualizar-estado',
                    dataType: "json",
                    data: params,
                    blockLoading: true,
                    noOne: false,
                    success: function (data, textStatus) {
                        if (textStatus == "success") {
                            $.smallBox({
                                content: "<i> Se actualizó el registro...</i>",
                            });

                            actualStatus == "Activo" ?
                                document.querySelector('#tblRegistros tbody tr[data-index=\'' + ix + '\'] .td-status').innerHTML = '<span class="label label-danger">Inactivo</span>'
                                :
                                document.querySelector('#tblRegistros tbody tr[data-index=\'' + ix + '\'] .td-status').innerHTML= '<span class="label label-success">Activo</span>'
                        }
                    },
                    error: function (xhr) {
                        exception(xhr);
                    },
                    complete: function () {
                        limpiarBusqueda();
                    }
                });
            } else {
            }
        })
    }

    function linkFormatter(value, row) {
        return `<a href="#" onclick="javascript:editar(${row.id})" title="Editar">${row.nombre}</a>`;
    }


    function Opciones(value, row, index) {
        return  `<a href='#' onclick='javascript:desactivar(${row.id},${index});' title='Actualizar estado'><i class='fa fa-refresh fa-2x'></i></a> &nbsp`;
    }

    function isActived(value, row, index) {
        if (row.flagActivo) {
            return '<span class="label label-success">Activo</span>';
        } else {
            return '<span class="label label-danger">Inactivo</span>';
        }
    }

    function validarRegistros() {

        $.validator.addMethod("maxlength", function ( value, element, len) {
           return value == "" || value.length <= len;
        });


        $.validator.addMethod("minlength", function (value1, element, len) {
           return value1 == "" || value1.length >= len;
         });

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
                Nombre: {
                    required: true,
                    rangelength : [1,20],
                    lettersonly: true
                },
            },
            messages: {
                Nombre: {
                    required: "El campo es obligatorio",

                },
            },
            submitHandler: function () {
                registro();
            }
        });
    }
function bodyFocusOutEventListener(e){
    const input = e.target;
    if(input.tagName === "INPUT"){
        if(input.type==="text"){
            input.value = input.value.trim();
        }
    }
    if(input.tagName === "TEXTAREA"){
        input.value = input.value.trim();
    }
}
