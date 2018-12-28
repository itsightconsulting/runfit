package com.itsight.domain.dto;

public class ResponseDto {

    /// <summary>
    /// Codigo de la petición
    /// </summary>
    private int responseCode;

    /// <summary>
    /// Objeto que contiene toda la data
    /// </summary>
    private Object data;

    public ResponseDto(){}

    public ResponseDto(int responseCode, Object data) {
        this.responseCode = responseCode;
        this.data = data;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

