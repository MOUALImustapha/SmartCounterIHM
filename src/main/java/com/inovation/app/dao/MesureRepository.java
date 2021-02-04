package com.inovation.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.inovation.app.entity.Mesure;

@RepositoryRestResource
public interface MesureRepository extends JpaRepository<Mesure,Long>{
	
	List<Mesure> findTop7ByCompteur_IdOrderByIdDesc(Long id);
	@Query("select m from Mesure m where year(m.date) = ?1 and month(m.date) = ?2 and m.compteur.id= ?3")
	List<Mesure> getByYearAndMonth(int year, int month,Long id);
}
