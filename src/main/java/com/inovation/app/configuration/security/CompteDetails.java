package com.inovation.app.configuration.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.inovation.app.entity.Compte;



public class CompteDetails implements UserDetails{

	private Compte compte;
	public CompteDetails(Compte compte) {
		this.compte = compte;		
	}
	
	
	public Compte getCompte() {
		return compte;
	}


	public void setCompte(Compte compte) {
		this.compte = compte;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		System.out.println("they get password from me");
		return compte.getLogin().getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return compte.getLogin().getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
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

}
