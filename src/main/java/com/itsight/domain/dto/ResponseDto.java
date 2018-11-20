package com.itsight.domain.dto;

public class ResponseDto {

    /// <summary>
    /// Codigo de la petición
    /// </summary>
    private int ResponseCode;

    /// <summary>
    /// Objeto que contiene toda la data
    /// </summary>
    private Object Data;

    public ResponseDto(){}

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int responseCode) {
        ResponseCode = responseCode;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    public ResponseDto(int responseCode, Object data) {
        ResponseCode = responseCode;
        Data = data;
    }
}

