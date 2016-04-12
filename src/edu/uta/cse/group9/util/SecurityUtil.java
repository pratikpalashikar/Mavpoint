package edu.uta.cse.group9.util;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

import edu.uta.cse.group9.model.User;

public class SecurityUtil {
	
	private final int ITERATIONS = 1000;
	private final Random RANDOM = new SecureRandom();
	
	public String getHash(String password, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(Base64.getDecoder().decode(salt));
		byte[] input = digest.digest(password.getBytes("UTF-8"));
		for (int i = 0; i < ITERATIONS; i++) {
			digest.reset();
			input = digest.digest(input);
		}
		return Base64.getEncoder().encodeToString(input);
	}
	
	public String generateSalt() {
		byte[] randSalt = new byte[16];
		RANDOM.nextBytes(randSalt);
		String saltString = Base64.getEncoder().encodeToString(randSalt);
		return saltString;
	}
	
	public String generateRandomPassword() {
		// TODO: Implement
		return "";
	}

	public String generatePrivateKey(String password, User user) {
		String key = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(Base64.getDecoder().decode(user.getPasswordSalt()));
			byte[] input = digest.digest(password.getBytes("UTF-8"));
			digest.reset();
			input = digest.digest(input);
			String passwordHash = Base64.getEncoder().encodeToString(input);
			// TODO: generate AES symmetric key from the password Hash
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return key;
	}
}
