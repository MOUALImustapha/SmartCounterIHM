package com.inovation.app.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Utilisateur {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String CIN;
	@ToString.Exclude
	@OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Compteur> compteur;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "compte_id", referencedColumnName = "id")
	private Compte compte;

}
