package eu.hcomb.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import io.jsonwebtoken.impl.compression.CompressionCodecs;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Date;
import java.util.List;

public class TestJWT {

	public static void main(String[] args) {
		
		Key key = MacProvider.generateKey(SignatureAlgorithm.HS512);

		String kStr = new Base64Codec().encode(key.getEncoded());
		
		String s = Jwts
					.builder()
					.setSubject("Joe")
					.setExpiration(new Date(System.currentTimeMillis() + 10000))
					.claim("role", new String[]{"admin","user"})
					//.compressWith(CompressionCodecs.GZIP)
					.signWith(SignatureAlgorithm.HS512, key)
					.compact();
		
		System.out.println("key: " + kStr);
		System.out.println("jwt: "+ s);
		
		Jws<Claims> list = Jwts.parser().setSigningKey(key).parseClaimsJws(s);
		System.out.println("data:"+list.toString());
		
	}
}
