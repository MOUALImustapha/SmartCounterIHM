package com.inovation.app.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Compteur {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ToString.Exclude
	@OneToMany(mappedBy = "compteur", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Mesure> mesures;
	@Embedded
	private Address address;
	@ManyToOne
	private Utilisateur utilisateur;
		
	public Compteur(Long id) {
		this.id = id;
	}
}
