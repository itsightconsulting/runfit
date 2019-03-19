package com.itsight.domain.dto;

public class PasswordDTO {

    private String nuevaPassword;
    private String nuevaPasswordRe;
    private String userId;
    private String username;
    private int tipoUsuario;

    public String getNuevaPassword() {
        return nuevaPassword;
    }

    public void setNuevaPassword(String nuevaPassword) {
        this.nuevaPassword = nuevaPassword;
    }

    public String getNuevaPasswordRe() {
        return nuevaPasswordRe;
    }

    public void setNuevaPasswordRe(String nuevaPasswordRe) {
        this.nuevaPasswordRe = nuevaPasswordRe;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
