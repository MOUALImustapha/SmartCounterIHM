package com.inovation.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inovation.app.configuration.security.CompteDetails;
import com.inovation.app.dao.CompteRepository;
import com.inovation.app.entity.Compte;

@Service
public class CompteService implements UserDetailsService{

	@Autowired
	CompteRepository compteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("they call me im compte Service");
		Optional<Compte> compte = compteRepository.findByLoginUsername(username);
		Compte c = compte.orElseThrow(() -> new UsernameNotFoundException("Username or password are incorect"));
		System.out.println(c.getLogin().getPassword());
		return new CompteDetails(c);
	}

}
