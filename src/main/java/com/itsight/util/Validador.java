package com.itsight.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validador {

    public static final String velMetricaPattern = "(?:[0]{2}):(?:[01]\\d):(?:[012345]\\d)";//00:19:59

    public static boolean esListaVacia(List<?> lista) {
        if (lista == null || lista.size() == 0)
            return true;
        return false;
    }

    public static boolean esNulo(Object objeto) {
        return objeto == null ? true : false;
    }

    public static boolean esNuloVacio(String variable) {
        if (variable == null || variable.length() == 0)
            return true;

        return false;
    }

    public static boolean estaVacio(String valor) {
        if (valor != null)
            if (!valor.trim().equals(""))
                return false;

        return true;
    }

    public static boolean validarCorreo(String correo) {
        Pattern pat;
        Matcher mat;
        if(!correo.equals("")) {
            pat = Pattern
                    .compile("[a-zA-Z0-9]+[.[a-zA-Z0-9_-]+]*@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");

            mat = pat.matcher(correo);

            return mat.find();
        }
        return false;
    }



    public static boolean esCero(Integer numero) {
        if (esNulo(numero))
            return false;

        if (numero == 0)
            return true;

        return false;
    }

    public static boolean esCero(BigDecimal numero) {
        if (esNulo(numero))
            return false;

        if (numero.compareTo(BigDecimal.ZERO) == 0)
            return true;

        return false;
    }

    public static boolean isValidJSON(final String json) throws IOException {
        boolean valid = true;
        try{
            new ObjectMapper().readTree(json);
        } catch(JsonProcessingException e){
            valid = false;
        }
        return valid;
    }
}
