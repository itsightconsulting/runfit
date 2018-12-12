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
        EX_GENERIC(-9),
        EX_NULL_POINTER(-10),
        EX_JACKSON_INVALID_FORMAT(-11),
        EX_NUMBER_FORMAT(-99),
        EX_MAX_SIZE_MULTIPART(-100),
        EX_MAX_UPLOAD_SIZE(-101),
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


}
