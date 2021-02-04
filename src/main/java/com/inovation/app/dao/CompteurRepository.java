package com.inovation.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.inovation.app.entity.Address;
import com.inovation.app.entity.Compteur;


@RepositoryRestResource
public interface CompteurRepository extends JpaRepository<Compteur,Long> {

}
