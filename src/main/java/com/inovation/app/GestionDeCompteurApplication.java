package com.inovation.app;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.inovation.app.dao.CompteRepository;
import com.inovation.app.dao.CompteurRepository;
import com.inovation.app.dao.UtilisateurRepository;
import com.inovation.app.entity.Address;
import com.inovation.app.entity.Compte;
import com.inovation.app.entity.Compteur;
import com.inovation.app.entity.Login;
import com.inovation.app.entity.Mesure;
import com.inovation.app.entity.Utilisateur;

@SpringBootApplication
public class GestionDeCompteurApplication {
	 @Autowired
	 CompteRepository compteRepository;
	 @Autowired
	 UtilisateurRepository utilisateurRepository;
	 @Autowired
	 CompteurRepository compteurRepository;

	public static void main(String[] args) {
		SpringApplication.run(GestionDeCompteurApplication.class, args);
	}
	/**
	 * 
	 */
	@PostConstruct
	public void init() {
	
		Utilisateur karima = new Utilisateur();
		karima.setCIN("I741011");
		Compte compte = new Compte();
		compte.setLogin(new Login("karima", "karima"));
		compte.setUser(karima);
		karima.setCompte(compte);
		
		Compteur c1 = new Compteur();
		c1.setUtilisateur(karima);
		Address address1 = new Address();
		address1.setCity("Fkih Ben Saleh");
		address1.setState("BeniMellal Khnifra");
		address1.setStreetAddress("Mohammed 5");
		address1.setZipCode("23040");
		c1.setAddress(address1);
		
		Compteur c2 = new Compteur();
		c2.setUtilisateur(karima);
		Address address2 = new Address();
		address2.setCity("Fkih Ben Saleh");
		address2.setState("BeniMellal Khnifra");
		address2.setStreetAddress("Mohammed 5");
		address2.setZipCode("23040");
		c2.setAddress(address2);
		
		List<Compteur> compteurKarima = new ArrayList<Compteur>();
		compteurKarima.add(c1);
		compteurKarima.add(c2);
		karima.setCompteur(compteurKarima);
		
		
		Calendar calendar =  Calendar.getInstance();
		List<Mesure> mesures = new ArrayList<>();
		Random r = new Random();
		calendar.add(Calendar.DAY_OF_MONTH, -200);
		for(int i = 0; i < 200;i++) {
			mesures.add(new Mesure(null, new Timestamp(calendar.getTime().getTime()), new Double(r.nextInt(12)), c1));
			calendar.set(Calendar.HOUR_OF_DAY, r.nextInt(12));
			calendar.set(Calendar.MINUTE, r.nextInt(60));
			calendar.set(Calendar.SECOND, r.nextInt(60));
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			
		}
		c1.setMesures(mesures);
		
		List<Mesure> mesures2 = new ArrayList<>();
		calendar.add(Calendar.DAY_OF_MONTH, -200);
		for(int i = 0; i < 200;i++) {
			mesures2.add(new Mesure(null, new Timestamp(calendar.getTime().getTime()), new Double(r.nextInt(12)), c2));
			calendar.set(Calendar.HOUR_OF_DAY, r.nextInt(12));
			calendar.set(Calendar.MINUTE, r.nextInt(60));
			calendar.set(Calendar.SECOND, r.nextInt(60));
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		c2.setMesures(mesures2);
	
		utilisateurRepository.save(karima);
		
	}
}
