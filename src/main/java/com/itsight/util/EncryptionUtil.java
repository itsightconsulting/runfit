package com.itsight.util;

import org.springframework.core.io.ClassPathResource;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class EncryptionUtil {
	

	public static final String ALGORITHM = "RSA";
	public static final String PRIVATE_KEY = "private.key";
	public static final String AES_KEY = "key.txt";
	public static final String AES_VECTOR = "vector.txt";	
	public static String PRIVATE_KEY_FILE = "";
	public static String PUBLIC_KEY_FILE = "";
	public static String AES_KEY_FILE = "";
	public static String AES_VECTOR_FILE = "";

	@SuppressWarnings("resource")
	public static String encrypt(String value) {
		
		ObjectInputStream inputStream;
		String key = getKey();
		byte[] cipherTextKey = Base64.getDecoder().decode(key);
		String initVector = getVector();
		byte[] cipherTextVector = Base64.getDecoder().decode(initVector);
		
		try {
			inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
			final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
			final String plainKey = decrypt(cipherTextKey, privateKey);
			final String plainVector = decrypt(cipherTextVector, privateKey);			
			
			IvParameterSpec iv = new IvParameterSpec(plainVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(plainKey.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public String encrypt_(String value) {
		
		ObjectInputStream inputStream;
		String key = getKey_();
		byte[] cipherTextKey = Base64.getDecoder().decode(key);
		String initVector = getVector_();
		byte[] cipherTextVector = Base64.getDecoder().decode(initVector);
		
		try {

			ClassLoader CLDR = this.getClass().getClassLoader();
			InputStream opcion1 = new ClassPathResource(PRIVATE_KEY).getInputStream();
			
			if (opcion1 != null) {
				inputStream = new ObjectInputStream(opcion1);
			} else {
				opcion1 = CLDR.getResourceAsStream(PRIVATE_KEY);
				inputStream = new ObjectInputStream(opcion1);
			}

			final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
			final String plainKey = decrypt(cipherTextKey, privateKey);
			final String plainVector = decrypt(cipherTextVector, privateKey);			
			
			System.out.println("ENCRIPTAR- KEY: " + plainKey);
            System.out.println("ENCRIPTAR- VECTOR: " + plainVector);
			
			IvParameterSpec iv = new IvParameterSpec(plainVector.getBytes());
			SecretKeySpec skeySpec = new SecretKeySpec(plainKey.getBytes(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public String decrypt_(String encrypted) {
		ObjectInputStream inputStream;
		String key = getKey_();
		byte[] cipherTextKey = Base64.getDecoder().decode(key);
		String initVector = getVector_();
		byte[] cipherTextVector = Base64.getDecoder().decode(initVector);
		
		try {
			
			ClassLoader CLDR = this.getClass().getClassLoader();
			InputStream opcion1 = CLDR.getResourceAsStream(PRIVATE_KEY);
			
			if (opcion1 != null) {
				inputStream = new ObjectInputStream(opcion1);
			} else {
				opcion1 = CLDR.getResourceAsStream(PRIVATE_KEY);
				inputStream = new ObjectInputStream(opcion1);
			}
			
			final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
			final String plainKey = decrypt(cipherTextKey, privateKey);
			final String plainVector = decrypt(cipherTextVector, privateKey);
			
			System.out.println("ENCRIPTAR- KEY: " + plainKey);
            System.out.println("ENCRIPTAR- VECTOR: " + plainVector);
			
			IvParameterSpec iv = new IvParameterSpec(plainVector.getBytes());
			SecretKeySpec skeySpec = new SecretKeySpec(plainKey.getBytes(), "AES");
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
	public static String getVector() {
		ObjectInputStream inputStream = null;
		String vector = "";
		
		try {
			inputStream = new ObjectInputStream(new FileInputStream(AES_VECTOR_FILE));
			vector = (String) inputStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return vector;
	}
	
	public String getVector_() {
		ObjectInputStream inputStream;
		String vector = "";
		
		try {
			ClassLoader CLDR = this.getClass().getClassLoader();
			InputStream opcion1 = new ClassPathResource(AES_VECTOR).getInputStream();

			if (opcion1 != null) {
				inputStream = new ObjectInputStream(opcion1);
			} else {
				opcion1 = CLDR.getResourceAsStream(AES_VECTOR);
				inputStream = new ObjectInputStream(opcion1);
			}
			
			vector = (String) inputStream.readObject();
//			System.out.println(vector);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return vector;
	}
		
	public static String getKey() {
		ObjectInputStream inputStream = null;
		String key = "";
		
		try {
			inputStream = new ObjectInputStream(new FileInputStream(AES_KEY_FILE));
			key = (String) inputStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return key;
	}
	
	public String getKey_() {
		ObjectInputStream inputStream ;
		String key = "";
		try {
			ClassLoader CLDR = this.getClass().getClassLoader();
			InputStream opcion1 = new ClassPathResource(AES_KEY).getInputStream();
			
			if (opcion1 != null) {
				inputStream = new ObjectInputStream(opcion1);
			} else {
				opcion1 = CLDR.getResourceAsStream(AES_KEY);
				inputStream = new ObjectInputStream(opcion1);
			}
			
			key = (String) inputStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		
		return key;
	}

	public static void generateKey() {
		try {
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
			keyGen.initialize(512);
			final KeyPair key = keyGen.generateKeyPair();

			File privateKeyFile = new File(PRIVATE_KEY_FILE);
			File publicKeyFile = new File(PUBLIC_KEY_FILE);

			if (privateKeyFile.getParentFile() != null) {
				privateKeyFile.getParentFile().mkdirs();
			}
			privateKeyFile.createNewFile();

			if (publicKeyFile.getParentFile() != null) {
				publicKeyFile.getParentFile().mkdirs();
			}
			publicKeyFile.createNewFile();

			ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
			publicKeyOS.writeObject(key.getPublic());
			publicKeyOS.close();

			ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
			privateKeyOS.writeObject(key.getPrivate());
			privateKeyOS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static boolean areKeysPresent() {
		File privateKey = new File(PRIVATE_KEY_FILE);
		File publicKey = new File(PUBLIC_KEY_FILE);

		if (privateKey.exists() && publicKey.exists()) {
			return true;
		}
		return false;
	}

	public static byte[] encrypt(String text, PublicKey key) {
		byte[] cipherText = null;
		try {
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherText = cipher.doFinal(text.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherText;
	}

	public static String decrypt(byte[] text, PrivateKey key) {
		byte[] dectyptedText = null;
		try {
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			dectyptedText = cipher.doFinal(text);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new String(dectyptedText);
	}
	
	@SuppressWarnings("resource")
	public static void init(String vectorAES, String llaveAES) {
		if (!areKeysPresent()) {
			generateKey();
		}
		
		try {
			ObjectInputStream inputStream = null;
			File encryptedVector = new File(AES_VECTOR_FILE);
			File encryptedKey = new File(AES_KEY_FILE);
			
			if (encryptedVector.getParentFile() != null) {
				encryptedVector.getParentFile().mkdirs();
			}
			encryptedVector.createNewFile();

			if (encryptedKey.getParentFile() != null) {
				encryptedKey.getParentFile().mkdirs();
			}
			encryptedKey.createNewFile();
			
			inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
			final PublicKey publicKeyVector = (PublicKey) inputStream.readObject();
			final byte[] cipherTextVector = encrypt(vectorAES, publicKeyVector);
			String cipherTextB64Vector = null;
			
//			cipherTextB64Vector = org.apache.commons.codec.binary.Base64.encodeBase64String(cipherTextVector);
			
			cipherTextB64Vector = Base64.getEncoder().encodeToString(cipherTextVector);

			ObjectOutputStream writeVector = new ObjectOutputStream(new FileOutputStream(encryptedVector));
			writeVector.writeObject(cipherTextB64Vector);
			writeVector.close();
						
			inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
			final PublicKey publicKeyKey = (PublicKey) inputStream.readObject();
			final byte[] cipherTextKey = encrypt(llaveAES, publicKeyKey);
			String cipherTextB64Key = null;
			cipherTextB64Key = Base64.getEncoder().encodeToString(cipherTextKey);
			
			ObjectOutputStream writeKey = new ObjectOutputStream(new FileOutputStream(encryptedKey));
			writeKey.writeObject(cipherTextB64Key);
			writeKey.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
