package com.inovation.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.inovation.app.entity.Utilisateur;

@RepositoryRestResource
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

}
