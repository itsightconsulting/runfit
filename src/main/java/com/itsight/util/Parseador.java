package com.itsight.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hashids.Hashids;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

public class Parseador {

	public static final Logger LOGGER = LogManager.getLogger(Parseador.class);
	
	public static Date fromStringToDate(String date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date parseDate = null;
		
		try {
			parseDate = sdf.parse(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		} 
		return parseDate;
	}
	
	public static int fromStringToInt(String cadena) {
		try {
			return Integer.parseInt(cadena);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			return -1;
		}
	}
	
	public static Integer[] stringArrayToIntArray(String[] a){
		Integer[] b = new Integer[a.length];
	    for (int i = 0; i < a.length; i++) {
	    	if(!a[i].equals("")) {
	    		b[i] = Integer.parseInt(a[i]);	
	    	}else {
	    		b[i] = 0;
	    	}
	    }
	    return b;
	}

	public static String getEncodeHash32Id(String schema,  Integer id){
		Hashids rfIdHash = new Hashids(schema, 32);
		return rfIdHash.encode(id);
	}

	public static String getEncodeHash16Id(String schema,  Integer id){
		Hashids rfIdHash = new Hashids(schema, 16);
		return rfIdHash.encode(id);
	}

	public static Integer getDecodeHash32Id(String schema,  String hash){
		Hashids rfIdHash = new Hashids(schema, 32);
		return Integer.parseInt(String.valueOf(rfIdHash.decode(hash).length > 0 ? rfIdHash.decode(hash)[0] : 0));
	}
	public static Integer getDecodeHash16Id(String schema,  String hash){
		Hashids rfIdHash = new Hashids(schema, 16);
		return Integer.parseInt(String.valueOf(rfIdHash.decode(hash).length > 0 ? rfIdHash.decode(hash)[0] : 0));
	}

	public static String getDecodeBase64(String encode){
		return new String(Base64.getDecoder().decode(encode), StandardCharsets.UTF_8);
	}
	public static String getEncodeBase64(String encode){
		return new String(Base64.getEncoder().encode(encode.getBytes()), StandardCharsets.UTF_8);
	}
}
