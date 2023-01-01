package com.codeTest.studentReg.utility;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import com.codeTest.studentReg.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class JwtTokenUtil {
	private static HashMap<String, String> DOMAINS = new HashMap<String, String>();
	
	public static String createToken(User loginData) {
		return Jwts.builder().setSubject("BSS").claim("uid", loginData.getUserId())
				.claim("uname", loginData.getUserName()).claim("upk", loginData.getId())
				.claim("oid", loginData.getOrgId()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 24 * 60 * 60)).setId(UUID.randomUUID().toString())
				.signWith(SignatureAlgorithm.HS256, "B$s@11Gls").compact();
	}
	
	public static void setDomains(HashMap<String, String> domains) {
		DOMAINS = domains;
	}

	public static String getConnectionKey(String domain) {
		if (DOMAINS.containsKey(domain))
			return DOMAINS.get(domain);
		else
			return "";
	}

	public static String getLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("cuser") != null) {
			return ((User) session.getAttribute("cuser")).getOrgId();
		} else {
			try {
				final Claims claims = (Claims) request.getAttribute("claims");
				
				return (String) claims.get("oid");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}