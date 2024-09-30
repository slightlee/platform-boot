package com.demain.authorization.server.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

/**
 * PKCE加密工具类
 *
 * @author demain_lee
 * @since 2024/09/20
 */
public class PKCEUtil {
    
    // 生成 Code Verifier（随机字符串）
    public static String generateCodeVerifier() {
        int length = 64; // PKCE 规范要求 43 到 128 个字符之间，这里使用 64
        Random random = new Random();
        StringBuilder codeVerifier = new StringBuilder(length);
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~";
        
        for (int i = 0; i < length; i++) {
            codeVerifier.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        return codeVerifier.toString();
    }
    
    // 生成 Code Challenge 使用 SHA-256 (S256) 方法
    public static String generateCodeChallenge(String codeVerifier) {
        try {
            // 使用 SHA-256 对 Code Verifier 进行哈希
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
            
            // 将哈希结果进行 Base64 URL 编码
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
    
    public static void main(String[] args) {
        // 生成 Code Verifier
        String codeVerifier = generateCodeVerifier();
        System.out.println("Code Verifier: " + codeVerifier);
        
        // 使用 S256 生成 Code Challenge
        String codeChallenge = generateCodeChallenge(codeVerifier);
        System.out.println("Code Challenge (S256): " + codeChallenge);
    }
}
