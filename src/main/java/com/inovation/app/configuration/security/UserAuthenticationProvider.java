package com.inovation.app.configuration.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.inovation.app.dao.CompteRepository;
import com.inovation.app.entity.Compte;

public class UserAuthenticationProvider implements AuthenticationProvider{
	 @Autowired
	 CompteRepository compteRepository;
	
	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String user = authentication.getName();
        String password = authentication.getCredentials().toString();
        System.out.println(user + " " + password);
        Compte compte = compteRepository.findByLoginUsernameAndLoginPassword(user, password);
        
        if (compte != null) { 
        	List<GrantedAuthority> authorithies = new ArrayList<GrantedAuthority>();
        	  authorithies.add(new SimpleGrantedAuthority("ROLE_USER"));// replace your custom code here for custom authentication
              return new UsernamePasswordAuthenticationToken(compte.getLogin().getUsername(),compte.getLogin().getPassword(),authorithies);
        } else {
            throw new BadCredentialsException("External system authentication failed");
        }
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
