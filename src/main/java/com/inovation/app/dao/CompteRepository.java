package com.inovation.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inovation.app.entity.Compte;

public interface CompteRepository extends JpaRepository<Compte, Long>{
	Optional<Compte> findByLoginUsername(String username);
	Compte findByLoginUsernameAndLoginPassword(String username, String password);
}
