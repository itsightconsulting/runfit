package com.itsight.service;

public interface EmailService {

	void enviarCorreoActivarUsuario(String asunto, String receptor, String contenido);

	void enviarCorreoRecuperarContrasena(String asunto, String receptor, String contenido);

	void enviarCorreoInformativo(String asunto, String receptor, String contenido);

	void enviarCorreoInformativoVariosBbc(String asunto, String receptores, String contenido);
}
