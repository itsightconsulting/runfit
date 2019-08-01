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
        EX_SQL_EXCEPTION(-103),
        EX_SQL_GRAMMAR_EXCEPTION(-104),
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
        GENERAL(2),
        TRIATLON(3),
        CICLISMO(4),
        OTROS(5);

        final int id;

        TipoRutina(int id){
            this.id = id;
        }

        public int get(){
            return id;
        }
    }

    public enum TipoTrainer{
        PARTICULAR(1),
        EMPRESA(2),
        PARA_EMPRESA(3);

        final int id;

        TipoTrainer(int id){
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
        NUEVO_CLIENTE(3),
        CONTACTO_TRAINER(6),
        CONTACTO_CONSULTA(7),
        ULTIMA_ETAPA_POSTULANTE(8),
        PERFIL_POST_OBS(9),
        PERFIL_TRAINER_APROBADO(10),
        PERFIL_CHECK_OBS_SUBS(11),
        ULTIMA_ETAPA_POSTULANTE_EMP(12),
        INIT_CAMBIO_PASSWORD(13),
        FINAL_CAMBIO_PASSWORD(14),
        REGISTRO_VISITANTE_CONFIRMAR_CORREO(15);

        final int id;

        Mail(int id){
            this.id = id;
        }

        public int get(){
            return id;
        }
    }

    public enum Error{
        ARCHIVO_EXCEDE_MAX_PERMITIDO("El archivo que ha intentado subir excede al límite permitido, por favor suba un archivo menor o igual a %s");

        final String msg;

        Error(String msg){
            this.msg = msg;
        }

        public String get(){
            return msg;
        }
    }

    public enum Msg{
        REGISTRO_EXITOSO("Se ha registrado correctamente"),
        CORREO_REPETIDO("El correo ingresado ya se encuentra registrado en nuestra base de datos"),
        POSTULACION_BLOQUEADA("Usted no puede volver a postular hasta después de la fecha: %s debido a que su última postulación fue rechazada"),
        POSTULACION_EN_PROCESO("El correo que esta intentando registrar, ya se encuentra registrado y a espera de nuestros gestores puedan aprobar o rechazar su postulación. Será notificado vía correo electrónico en el transcurso del día. Gracias por su espera"),
        POSTULACION_NUEVA("Como último paso, necesitamos que verifique su correo ingresando a su bandeja en donde encontrará un email nuestro. Gracias."),
        POSTULACION_RE_INTENTO("Su nueva postulación se ha realizado con éxito. Pronto recibirá una respuesta nuestra, gracias."),
        RESOURCE_NOT_FOUND("Página no encontrada </br>Error 404"),
        CUENTA_YA_VERIFICADA("La cuenta ya ha sido verificada con anterioridad"),
        CUENTA_VERIFICADA("Su cuenta ha sido verificada satisfactoriamente, pronto recibirá un correo nuestro con el resultado de su postulación. Gracias por su tiempo."),
        CUENTA_VISITANTE_VERIFICADA("Su cuenta ha sido verificada satisfactoriamente. Bienvenido a la familia RunFit , ya puede acceder a la plataforma. Gracias"),
        POSTULANTE_ACEPTADO_ANT("El postulante ha sido aceptado anteriormente"),
        POSTULANTE_RECHAZADO_ANT("El postulante ha sido rechazado anteriormente"),
        ELECCION_INVALIDA("Ha elegido una opción no válida inválida"),
        VALIDACION_FALLIDA("La validación ha fallado"),
        FAIL_SUBIDA_IMG_PERFIL("La imagen de perfil no ha podido ser cargada, intentelo más tarde"),
        FAIL_SUBIDA_IMG_GENERICA("La imagen no ha podido ser cargada, intentelo más tarde"),
        SUCCESS_SUBIDA_IMG("El registro y la subida de imagen han sido exitosos"),
        POSTULANTE_MAIL_SC("Postulante no ha confirmado su cuenta de correo electrónico"),
        POSTULANTE_RECH_PV("Postulante ha sido rechazado y aún no vence el plazo para una nueva postulación"),
        POSTULANTE_ACEP("Ha <strong>APROBADO</strong> al candidato con éxito"),
        POSTULANTE_RECH("Ha <strong>RECHAZADO</strong> al candidato con éxito"),
        POSTULANTE_ULTIMA_ETAPA("Registro correcto. Pronto revisaremos su perfil para una aprobación final. Usted será notificado a su correo, gracias por su tiempo."),
        POSTULANTE_YA_REG("Usted ya ha usado este link para registrarse, sino recuerda sus credenciales puede recuperarlas en la opción olvidó contraseña: <a href='/p/recuperar/password'>aquí</a>"),
        POST_LINK_EXP_PR("El vínculo ha expirado, para poder registrarse debe comunicarse con la plataforma, en la parte inferior de la página encontrará un apartado con datos de contacto."),
        CONTACTO_TRAINER("El asesor ha sido notificado y pronto se pondrá en contacto. Que tenga usted un buen día!"),
        CONSULTA_CONTACTO_ENVIADA("Su consulta ha sido enviada, tan pronto como podamos, atenderemos su consulta. Gracias por comunicarse con nosotros."),
        APROBACION_FINAL_PERFIL_TRAINER("Usted ha aprobado satisfactoriamente el perfil del trainer. Este último acaba de ser notificado."),
        APROBACION_FINAL_PERFIL_TRAINER_RE("El trainer ya ha sido aprobado con anterioridad."),
        PERFIL_APROBADO_ANTERIORMENTE("El perfil del entrenador que quieres visitar ya ha sido aprobado con anterioridad."),
        PERFIL_EN_REVISION("El perfil del entrenador que quieres visitar ya ha sido observado con anterioridad."),
        OBS_PERFIL_TRAINER("Se ha enviado las observaciones al entrenador satisfactoriamente vía correo."),
        OBS_PERFIL_SUBSANADAS("Éxito, usted actualizó su ficha correctamente, pronto esta volverá a ser revisada. Le estaremos notificando los resultados. Gracias"),
        CHECK_PERFIL_EMPRESA_EN_VISTA_BUSQUEDA("El perfil que ha intentado ver ya no es accesible mediante esta ruta puesto que ya ha sido aprobado. Si desea revisar su perfil encuentrelo en la vista de 'Encuentra tu asesor'"),
        NOTIFICACION_RED_FIT_PERSONAL("El atleta ha sido notificado."),
        NOTIFICACION_RED_FIT_GENERAL("Los atletas han sido notificados."),
        TRAINER_DE_EMPRESA_OBS_YA_ACTUALIZADAS("Usted ya ha actualizado el perfil de este entrenador, si ya corrigió todas las observaciones vuelva al perfil de su empresa y envíe la confirmación desde allí para finalizar el proceso."),
        USUARIO_NO_EXISTE("El usuario que ha ingresado no existe."),
        USUARIO_INACTIVO("El usuario que ha ingresado se encuentra inactivo."),
        ENLACE_CADUCADO("El enlace ha caducado"),
        CAMBIO_PASSWORD_PASADO("Usted ya ha cambiado su contraseña anteriormente por lo que este link ya no funciona."),
        ENLACE_RECUPERACION_PASS_UTILIZADO("El enlace ha ya sido utilizado"),
        POSTULANTE_ULTIMA_ETAPA_EMP("Su ficha ha sido enviada satisfactoriamente. Pronto la revisaremos y le notificaremos del resultado vía correo. Gracias!"),
        FLAG_BLOQUEADO_TIENE_DEPS("No puede actualizar el ESTADO del registro ya que este se encuentra asociado a un tabla hijo");

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

    public enum FileExt{
        JPEG(".jpg"),
        PDF(".pdf"),
        WEBM(".webm");

        final String id;

        FileExt(String id){this.id = id;}

        public String get(){return id;}
    }

    public enum CfsCliente{
        CONTROL_REP_VIDEO, FAV_RUTINA_ID, FAV_TRAINER_ID,FAVS_POST_TRAINER, CONTROL_ENTRENAMIENTO;
    }

    public enum Galletas{
        GLL_NOMBRE_COMPLETO, GLL_IMG_PERFIL, GLL_CONTROL_ENTRENAMIENTO, GLL_CONTROL_REP_VIDEO, GLL_FAV_RUTINA;
    }
}
