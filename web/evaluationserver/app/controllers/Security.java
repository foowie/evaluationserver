package controllers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import models.*;

public class Security extends Secure.Security {

	static boolean authenticate(String login, String password) {
		User user = User.findActiveByLogin(login);
		if (!user.active) {
			return false;
		}
		if (!user.password.equals(hashPassword(password))) {
			return false;
		}
		return true;
	}

	static void onAuthenticated() {
		Role role = User.getLoggedUser().role;
		if (role.is(Role.Check.ADMIN)) {
			CRUD.index();
		}
		if (role.is(Role.Check.CONTESTANT)) {
			controllers.contestant.Competitions.index();
		}
		throw new IllegalStateException("Invalid role " + role.key);
	}

	static boolean check(String profile) {
		String[] roles = profile.split(",");
		String userRole = User.findActiveByLogin(connected()).role.key;
		for (String role : roles) {
			if (userRole.equals(role)) {
				return true;
			}
		}
		return false;
	}

	public static void onCheckFailed(String profile) {
		forbidden();
	}	
	
	public static void onCheckFailed() {
		onCheckFailed(null);
	}

	public static String hashPassword(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
		String text = "rg$#%dargvbnsf!fw5364dsadfga8w4effs" + password;
		try {
			md.update(text.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
		byte[] digest = md.digest();
		StringBuilder sb = new StringBuilder();
		for (byte b : digest) {
			if (b > 0 && b < 16) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(b & 0xff));
		}
		return sb.toString();
	}

	public static String getUsername() {
		return isConnected() ? connected() : null;
	}
}
