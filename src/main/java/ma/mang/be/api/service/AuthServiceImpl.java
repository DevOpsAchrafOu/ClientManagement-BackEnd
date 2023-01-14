/**
 * 
 */
package ma.mang.be.api.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import ma.mang.be.api.entity.Utilisateur;
import ma.mang.be.api.repository.UtilisateurRepository;
import ma.mang.be.api.security.jwt.JwtProperties;

/**
 * @author achraf
 * @version v0.1
 */
@Service("AuthService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthServiceImpl implements AuthService {
	
	@Value("${host.dns}")
	private String hostDNS;
	
	@Autowired
	UtilisateurRepository usrRepo;

	@Override
	public String getTokenByUserLogin(String login) {
		Utilisateur usr = usrRepo.findByLogin(login);
		if(usr!=null) {
			return usr.getToken();
		}
		return null;
	}

	@Override
	public void storeTokenForUserLogin(String login, String token) {
		Utilisateur usr = usrRepo.findByLogin(login);
		if(usr!=null) {
			usr.setToken(token);
			usrRepo.save(usr);
		}		
	}
	
	@Override
	public boolean validateEmail(String validationToken) throws TokenExpiredException,Exception{
		boolean result = true;
		try {
			//Decode the received token
			DecodedJWT decodedJWT= JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
		             .build()//JWTVerifier
		             .verify(validationToken);//DecodedJWT
		             
			//Identify the email
			String mailFromToken = decodedJWT.getSubject();
			
			//Get concerned user
			Utilisateur usr = usrRepo.findByEmail(mailFromToken);
			//If found validate email adress
			if(usr!=null) {
				usr.setState(Utilisateur.ACTIVATED_STATE);
				usrRepo.save(usr);
			}else {
				result= false;
			}
		
			}catch(TokenExpiredException e) {
				result=false;
				throw new TokenExpiredException(e.getMessage());
		}
		return result;
	}
	
	
	
}
