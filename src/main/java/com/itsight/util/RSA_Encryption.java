package com.itsight.util;

public class RSA_Encryption {

	public RSA_Encryption() throws Exception {
	}
	
	public String encrypted(String plainText) {
		return (EncryptionUtil.encrypt(plainText));
	}

	public String encrypted_(String plainText) {
		EncryptionUtil util = new EncryptionUtil();
		return (util.encrypt_(plainText));
	}
	
	public String decrypted_(String plainText) {
		EncryptionUtil util = new EncryptionUtil();
		return (util.decrypt_(plainText));
	}
}
