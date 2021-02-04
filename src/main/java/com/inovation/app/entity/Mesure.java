package com.inovation.app.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
 @Entity
public class Mesure {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Timestamp date;
	private Double valeur;
	@ManyToOne
	@JoinColumn(name="compteur_id",nullable = false, updatable = false)
	private Compteur compteur;
	
	public String getDayName() {
        return (new SimpleDateFormat("EEEE")).format(date.getTime());
	}
}
