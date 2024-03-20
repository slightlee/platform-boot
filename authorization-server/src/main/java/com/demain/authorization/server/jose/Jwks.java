package com.demain.authorization.server.jose;

import com.nimbusds.jose.jwk.RSAKey;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * 
 * @author demain_lee
 * @since  2024/3/14
 */
public final class Jwks {

	private Jwks() {
	}

	/**
	 * java.security.KeyPair 实例，其中包含启动时生成的密钥，用于创建上述 JWKSource
	 *
	 * @return
	 */
	public static RSAKey generateRsa() {
		KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// @formatter:off
		return new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		// @formatter:on
	}

}
