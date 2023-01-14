package ma.mang.be.api.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ma.mang.be.api.entity.Utilisateur;

/**
 * Object used to perform security 
 * @author achraf
 * @version 0.1
 */

public class AppUser implements UserDetails{
	
	private static final long serialVersionUID = -2329204721618159791L;
	private Utilisateur userMe;
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired
	public AppUser(Utilisateur userMe,BCryptPasswordEncoder passwordEncoder) {
		this.userMe = userMe;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		String role = this.userMe.getRole()!=null?this.userMe.getRole().getCode():"ROLE_USER";
		GrantedAuthority authority = new SimpleGrantedAuthority(role);//+this.userMe.getProfile());
		authorities.add(authority);
		return authorities;
	}

	@Override
	public String getPassword() {
//		return this.passwordEncoder.encode(userMe.getPassword());
		return userMe.getPassword();
	}

	@Override
	public String getUsername() {
		return userMe.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	

	public String getUserId() {
		return userMe.getEmail();
	}
	
	public Utilisateur getUser() {
		return userMe;
	}


}
