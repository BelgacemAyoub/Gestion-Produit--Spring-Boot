package org.sid.web;

import javax.validation.Valid;

import org.sid.dao.ProduitRepository;
import org.sid.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // pour dire que ça c'est un controller Spring MVC

public class ProduitController {
	@Autowired 										// pour faire l'injection de dépendances
	private ProduitRepository produitRepository; 	// un objet de type produitRepository (notre controller a besoin
													// d'utiliser l'interface produitRepository)

	
	
//	@RequestMapping(value = "/index") 									
//	public String index(Model model, 									
//			@RequestParam(name = "page", defaultValue = "0") int p, 	
//			@RequestParam(name = "size", defaultValue = "5") int s) {	
	
	
	/*
	 * quand je tape /index la methode index qui va s'exécuter il suffit de déclarer
	 * un objet qui s'appelle model, avec Requestparam je suis entrain de dire à
	 * DispatcherServlet tu vas utiliser l'objet( request.getParameter => tu vas
	 * chercher un parameter qui s'appel page et tu l'affecte à la variable p
	 */

	
//		Page<Produit> pageProduits = produitRepository.findAll(new PageRequest(p, s));  // le controlleur il va faire
//																						// appele à la couche métier, il
//																						// va récupérer la liste des produits
	
//		model.addAttribute("listProduits", pageProduits.getContent());  // il va la stocké dans le model avec le nom
//																		// listProduits (la méthode getContent permet de
//																		// retourner la liste des produits)
//		int[] pages = new int[pageProduits.getTotalPages()];
//		model.addAttribute("pages", pages); // dans le modél j'ai également un tableau qui contient le nombre de pages,
//											// il va la stocker dans le modél avec le nom pages.
//		return "produits"; // produit represente le nom de la vue
//
//	}
	
	
	
	
	
	
	  @RequestMapping("/user/index") 
	  public String index (Model model,
	  @RequestParam(name = "page", defaultValue = "0") int p,
	  @RequestParam(name = "size", defaultValue = "5") int s,
	  @RequestParam(name = "motCle", defaultValue = "") String mc){    // on va lire le paramétre motCle et on va l'affecter à mc  
	  Page<Produit>pageProduits = produitRepository.chercher("%"+mc+"%",new PageRequest(p, s));
	  model.addAttribute("listProduits", pageProduits.getContent()); 
	  int[] pages = new int[pageProduits.getTotalPages()]; 
	  model.addAttribute("pages", pages);
	  model.addAttribute("size", s);             
	  model.addAttribute("pageCourante", p);  							/* la pages qui est affiché */
	  model.addAttribute("motCle", mc); 								/* tous ce qui est affiché dans la vue doit est stocké dans le modél */  
	  return "produits"; 
	  }
	  
	@RequestMapping(value = "/admin/delete", method = RequestMethod.GET)  // pour accéder à delete il faut absolument envoyer une requete avec GET 
	
	/*
	 * RequestMethod: c'est une entité, pour accéder à delete il foudrait
	 * envoyer absolument une requete avec GET vers /delete
	 */	 
	
	public String delete(Long id, String motCle, int page, int size) {
		  produitRepository.delete(id);
		  return "redirect:/user/index?page="+page+"&sizee="+size+"&motCle="+motCle;
	  }

	 
	
	/*
	 * @GetMapping("/index") public String index (Model
	 * model,@RequestParam(defaultValue="0") int page) { model.addAttribute("data",
	 * produitRepository.findAll(new PageRequest(page, 5)));
	 * model.addAttribute("pages",page); return "produits";
	 * }
	 */
	 
	
	@RequestMapping(value = "/admin/form", method = RequestMethod.GET)   // l'orsque si je tappe http localhost/form, je vais resevoir une vue 
	public String formProduit(Model model) {							 // qui s'appele FormProduit.html.																	
	model.addAttribute("produit", new Produit());						 // quand j'affiche un formulaire chaque controlleur du formulaire il
	return "FormProduit";                       						 // affiche la donnée d'un champ de produit, dans le modél j'ai un objet 
																		 // produit   																		
	}
	
	@RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
	public String edit(Model model,Long id) {
	Produit p=produitRepository.findOne(id);	                         // je vais recupérer le produit
	model.addAttribute("produit", p);									 // je vais stocker dans le model
	return "EditProduit";
	
	}
		
	@RequestMapping(value = "/admin/save", method = RequestMethod.POST)   // je vais appeler la methode (save), il faut déclarer un paramétre de type 
	public String save(Model model, @Valid Produit produit, 			  // produit tous ce que je récupére à partir de la requete http il va le stoker 
			BindingResult bindingResult) {	          					  // dans un objet de type Produit
	if (bindingResult.hasErrors())  									  // @Valid: je entrain a demandé à spring MVC quand tu vas lire les données
		return "FormProduit";											  // quand tu vas le stocker dans l'entité tu dois faire la validation (la validation des test sur les champ (le size et ett )
																	  	  // BindingResult: les erreurs sont stocke dans une collection qui est de type
		produitRepository.save(produit);                       			  // BindingResult
	return "Confirmation";												  
																		  
	}
	
	@RequestMapping(value="/")
	public String home() {
		
		return "redirect:/user/index";
	}
	
	@RequestMapping(value="/403")
	public String accesDneied() {
		
		return "403";
	}
	
	@RequestMapping(value="/login")
	public String login() {
		
		return "login";
	}
	
	
	
	
	
	
	
}

