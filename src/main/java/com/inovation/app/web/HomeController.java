package com.inovation.app.web;



import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.inovation.app.configuration.security.CompteDetails;
import com.inovation.app.dao.CompteRepository;
import com.inovation.app.dao.CompteurRepository;
import com.inovation.app.dao.UtilisateurRepository;
import com.inovation.app.entity.Utilisateur;


@Controller
public class HomeController {
	@Autowired
	CompteRepository compteRepository;
	@Autowired
	UtilisateurRepository utilisateurRepository;
	@Autowired
	CompteurRepository compteurRepository;

	@GetMapping("/userlogin")
	public String goLogin(Model model,HttpServletRequest request) {
		String referrer = request.getHeader("Referer");
	    request.getSession().setAttribute("url_prior_login", referrer);

		return "userlogin";
	}
	@GetMapping("/")
	public String home(Model model) {
		CompteDetails compteDetails = (CompteDetails) SecurityContextHolder
		        .getContext()
		        .getAuthentication()
		        .getPrincipal();
		Optional<Utilisateur> user = utilisateurRepository.findById(compteDetails.getCompte().getUser().getId());
		return "redirect:/compteur/" + user.get().getCompteur().get(0).getId();
	}
	
	@GetMapping("/compteur/{id}")
	public String compteur(Model model, @PathVariable Long id) {
		CompteDetails compteDetails = (CompteDetails) SecurityContextHolder
		        .getContext()
		        .getAuthentication()
		        .getPrincipal();
		Optional<Utilisateur> user = utilisateurRepository.findById(compteDetails.getCompte().getUser().getId());
		model.addAttribute("utilisateur", user.get());
		model.addAttribute("compteur", compteurRepository.findById(id).get());
		return "home";
	}
}
