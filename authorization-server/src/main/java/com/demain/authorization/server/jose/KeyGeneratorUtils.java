package com.demain.authorization.server.jose;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * 
 * @author demain_lee
 * @since  2024/3/14
 */
final class KeyGeneratorUtils {

	private KeyGeneratorUtils() {
	}

	static KeyPair generateRsaKey() {
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}



}
