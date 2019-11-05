package com.itsight.service;

public interface EmailService {

	void enviarCorreoInformativo(String asunto, String receptor, String contenido);

	void enviarCorreoInformativoVariosBbc(String asunto, String receptores, String contenido);

	void enviarCorreoInformativoConUnicoCc(String asunto, String receptorPrincipal, String ccReceptor, String contenido);
}
