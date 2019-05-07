package com.itsight.util;

public class Enums {

    public enum ResponseCode {
        REGISTRO(-1),
        ACTUALIZACION(-2),
        ELIMINACION(-3),
        EXITO_GENERICA(-4),
        EX_VALIDATION_FAILED(-5),
        EMPTY_RESPONSE(-6),
        SESSION_VALUE_NOT_FOUND(-7),
        SUCCESS_QUERY(-8),
        EX_GENERIC(-9),
        EX_NULL_POINTER(-10),
        EX_JACKSON_INVALID_FORMAT(-11),
        NOT_FOUND_MATCHES(-12),
        EX_NUMBER_FORMAT(-99),
        EX_MAX_SIZE_MULTIPART(-100),
        EX_MAX_UPLOAD_SIZE(-101),
        EX_ARRAY_INDEX_OUT(-102),
        VF_USUARIO_REPETIDO(-150);

        final int code;

        ResponseCode(int code) {
            this.code = code;
        }

        public String get() {
            return String.valueOf(code);
        }
    }

    public enum EstadoPlan{
        SIN_PLAN(1),
        EN_REVISION(2),
        POR_ENVIAR(3),
        INICIADO(4),
        COLMINADO(5);

        final int id;

        EstadoPlan(int id){
            this.id = id;
        }

        public int get(){
            return id;
        }
    }

    public enum TipoMedia{
        AUDIO(1),
        VIDEO(2),
        TEXTO(3);

        final int id;

        TipoMedia(int id){this.id = id;}

        public int get(){return id;}

    }

    public enum TipoRutina{
        RUNNING(1),
        TRIATLON(2),
        CICLISMO(3),
        OTROS(4);

        final int id;

        TipoRutina(int id){
            this.id = id;
        }

        public int get(){
            return id;
        }
    }

    public enum Decision{
        DESAPROBADO(0),
        APROBADO(1);

        final int id;

        Decision(int id){
            this.id = id;
        }

        public int get(){
            return id;
        }
    }

    public enum TipoUsuario{
        FIXORDER, ADMINISTRADOR, ENTRENADOR, CLIENTE
    }

    public enum Mail{
        POSTULACION_TRAINER(1),
        POSTULANTE_TRAINER_CONFIRMAR_CORREO(2),
        NUEVO_CLIENTE(3);

        final int id;

        Mail(int id){
            this.id = id;
        }

        public int get(){
            return id;
        }
    }

    public enum Msg{
        REGISTRO_EXITOSO("Se ha registrado correctamente"),
        CORREO_REPETIDO("El correo ingresado ya se encuentra registrado en nuestra base de datos"),
        POSTULACION_BLOQUEADA("Usted no puede volver a postular hasta después de la fecha: %s debido a que su última postulación fue rechazada"),
        POSTULACION_EN_PROCESO("Usted ya se encuentra registrado, por favor espere hasta que nuestros gestores puedan aprobar o rechazar su postulación. Será notificado vía correo electrónico en el transcurso del día. Gracias por su espera"),
        POSTULACION_NUEVA("Como último paso, necesitamos que verifique su correo ingresando a su bandeja en donde encontrará un email nuestro. Gracias."),
        POSTULACION_RE_INTENTO("Su nueva postulación se ha realizado con éxito. Pronto recibirá una respuesta nuestra, gracias."),
        RESOURCE_NOT_FOUND("Página no encontrada </br>Error 404"),
        CUENTA_YA_VERIFICADA("La cuenta ya ha sido verificada con anterioridad"),
        CUENTA_VERIFICADA("Su cuenta ha sido verificada satisfactoriamente, pronto recibirá un correo nuestro con el resultado de su postulación. Gracias por su tiempo."),
        POSTULANTE_ACEPTADO_ANT("El postulante ha sido aceptado anteriormente"),
        POSTULANTE_RECHAZADO_ANT("El postulante ha sido rechazado anteriormente"),
        ELECCION_INVALIDA("Ha elegido una opción no válida inválida"),
        VALIDACION_FALLIDA("La validación ha fallado"),
        FAIL_SUBIDA_IMG_PERFIL("La imagen de perfil no ha podido ser cargada, intentelo más tarde"),
        POSTULANTE_MAIL_SC("Postulante no ha confirmado su cuenta de correo electrónico"),
        POSTULANTE_RECH_PV("Postulante ha sido rechazado y aún no vence el plazo para una nueva postulación"),
        POSTULANTE_ACEP("Ha <strong>APROBADO</strong> al candidato con éxito"),
        POSTULANTE_RECH("Ha <strong>RECHAZADO</strong> al candidato con éxito"),
        POSTULANTE_YA_REG("Usted ya ha usado este link para registrarse, sino recuerda sus credenciales puede recuperarlas en la opción olvidó contraseña: <a href='/login'>aquí</a>"),
        POST_LINK_EXP_PR("El vínculo ha expirado, para poder registrarse debe comunicarse con la plataforma, en la parte inferior de la página encontrará un apartado con datos de contacto.");
        final String msg;

        Msg(String msg){
            this.msg = msg;
        }

        public String get(){
            return msg;
        }
    }

    public enum MsgPeticion{
        FICHA_RUNNER("Estimado asesor, en esta ocasión quisiera que use la ficha de runner ya que es la disciplina en la que quiero mejorar. Gracias."),
        FICHA_GENERAL("Estimado asesor, en esta ocasión quisiera que use la ficha general. Gracias.");
        final String msg;

        MsgPeticion(String msg){
            this.msg = msg;
        }

        public String get(){
            return msg;
        }
    }


}
