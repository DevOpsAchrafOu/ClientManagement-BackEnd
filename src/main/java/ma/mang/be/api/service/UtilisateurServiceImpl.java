/**
 * 
 */
package ma.mang.be.api.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import ma.mang.be.api.dto.UtilisateurDto;
import ma.mang.be.api.entity.Role;
import ma.mang.be.api.entity.Utilisateur;
import ma.mang.be.api.exception.NotEqualsException;
import ma.mang.be.api.exception.NotFoundElementException;
import ma.mang.be.api.exception.NotValidException;
import ma.mang.be.api.repository.RoleRepository;
import ma.mang.be.api.repository.UtilisateurRepository;
import ma.mang.be.api.security.jwt.JwtProperties;
import ma.mang.be.api.security.model.AppUser;

/**
 * @author achraf
 * @version v0.1
 *
 */
@Service("UtilisateurService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UtilisateurServiceImpl implements UtilisateurService, UserDetailsService {

	@Autowired
	UtilisateurRepository usrRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


	@Override
	//predefined in UserDetailsService interface
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Utilisateur usr = usrRepo.findByLogin(userName);

		if (usr == null) {
			throw new NotFoundElementException(String.format("User not found with userName : %s", userName));
		}

		Utilisateur userDto = usr;
		UserDetails userDetails = new AppUser(userDto, passwordEncoder);
		return userDetails;
	}

	@Override
	public List<Utilisateur> getAllUsers() {
		return usrRepo.findAll();
	}

	@Override
	public Utilisateur getUserById(Long id) throws NotFoundElementException{
		try {
			return usrRepo.findById(id) != null ? usrRepo.findById(id).get() : null;
		} catch (Exception e) {
			throw new NotFoundElementException("No user is found for this id :: " + id);
		}
	}

	@Override
	public Utilisateur save(Utilisateur usr) {
		return usrRepo.save(usr);
	}

	@Override
//	@Transactional(rollbackOn = {RuntimeException.class})
	public void delete(Long userId) {
		usrRepo.deleteById(userId);
		
	}

	// Create user for entry in the database (ex: by postman)
	@Override
	@Transactional
	public String createAccount(Utilisateur usr) {
		Utilisateur acc = usrRepo.findByLogin(usr.getLogin());
		String token =null;
		if (acc != null) {
			return "EXIST";
		}
		try {
			//Get role 
			Role role = roleRepo.findByCode(usr.getRole().getCode());
			// Create JWT connection Token
			token = createJWT(usr, role != null ? role.getCode() : "ROLE_USER");

			// Create an entry in the database
			Utilisateur newColl = usrRepo
					.save(new Utilisateur(
							0,
							usr.getLogin(),
							usr.getEmail(),
							passwordEncoder.encode(usr.getPassword()),
							token,
							usr.getNom(),
							usr.getPrenom(),
							usr.getPhone(),
							new Date(),
							Utilisateur.ACTIVATED_STATE,
							role));
			

		} catch (Exception e) {
			e.printStackTrace();
			return "KO";
		}
		return token;
	}

	// user by login
	@Override
	public Utilisateur findByLogin(String login) {

		return usrRepo.findByLogin(login);
	}

	// Change Status user
	@Override
	public String setAccountState(String status) throws Exception, NotFoundElementException {
		// Get the concerned account
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = (String) auth.getPrincipal();
		Utilisateur acc = usrRepo.findByLogin(login);
		if (acc != null) {
			try {
				// Change Status account
				acc.setState(status);
				usrRepo.save(acc);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		} else {
			throw new NotFoundElementException("User not found for this id :: " + login);
		}

		return "OK";
	}
	
	@Override
	public String setPassword(String oldPwd, String newPwd) throws Exception, NotFoundElementException,NotEqualsException,NotValidException {
		String token = null;
		// Get the concerned account
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = (String) auth.getPrincipal();
		Utilisateur acc = usrRepo.findByLogin(login);
		if (acc != null) {
				// Check the old pwd
				if (passwordEncoder.matches(oldPwd, acc.getPassword())) {
					//in End
//					if(!Utils.isValidPassword(newPwd)) {
//						throw new NotValidPasswordException("The password is not a valid password. please try to give a secure password!");
//					}
					acc.setPassword(passwordEncoder.encode(newPwd));

					// Create new JWT Token
					token = createJWT(acc, "ROLE_USER");
					
					acc.setToken(token);
					usrRepo.save(acc);
				} else {
					throw new NotEqualsException("Old and new passwords do not match.");
				}
		} else {
			throw new NotFoundElementException("User not found for this id :: " + login);
		}

		return token;
	}

	
	@Override
	public Utilisateur update(UtilisateurDto usr) {
		Utilisateur colFromDb = usrRepo.findByLogin(usr.getLogin());
		if(colFromDb==null) {
			throw new NotFoundElementException("No user is found");
		}
		try {
			colFromDb.setNom(usr.getNom());
			colFromDb.setPrenom(usr.getPrenom());
			colFromDb.setPhone(usr.getPhone());
			colFromDb.setEmail(usr.getEmail());
			Role r = roleRepo.findByCode(usr.getRole());
			colFromDb.setRole(r);
			usrRepo.save(colFromDb);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return colFromDb;
	}
	
	private String createJWT(Utilisateur usr, String role) {
		String token;
		token = JWT.create().withSubject(usr.getLogin())
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
				.withClaim("role", role).sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));
		return token;
	}

}
