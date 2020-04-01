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

@Controller 

public class ProduitController {
	@Autowired 										
	private ProduitRepository produitRepository; 	
	
	
	/*
	 * quand je tape /index la methode index qui va s'exécuter il suffit de déclarer
	 * un objet qui s'appelle model, avec Requestparam je suis entrain de dire à
	 * DispatcherServlet tu vas utiliser l'objet( request.getParameter => tu vas
	 * chercher un parameter qui s'appel page et tu l'affecte à la variable p
	 */
	
	
	  @RequestMapping("/user/index") 
	  public String index (Model model,
	  @RequestParam(name = "page", defaultValue = "0") int p,
	  @RequestParam(name = "size", defaultValue = "5") int s,
	  @RequestParam(name = "motCle", defaultValue = "") String mc){     
	  Page<Produit>pageProduits = produitRepository.chercher("%"+mc+"%",new PageRequest(p, s));
	  model.addAttribute("listProduits", pageProduits.getContent()); 
	  int[] pages = new int[pageProduits.getTotalPages()]; 
	  model.addAttribute("pages", pages);
	  model.addAttribute("size", s);             
	  model.addAttribute("pageCourante", p);  							
	  model.addAttribute("motCle", mc); 								  
	  return "produits"; 
	  }
	  
	@RequestMapping(value = "/admin/delete", method = RequestMethod.GET)  	 
	
	public String delete(Long id, String motCle, int page, int size) {
		  produitRepository.delete(id);
		  return "redirect:/user/index?page="+page+"&sizee="+size+"&motCle="+motCle;
	  }	 
	
	@RequestMapping(value = "/admin/form", method = RequestMethod.GET)   
	public String formProduit(Model model) {							 																	
	model.addAttribute("produit", new Produit());						
	return "FormProduit";                       						
																		 																		
	}
	
	@RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
	public String edit(Model model,Long id) {
	Produit p=produitRepository.findOne(id);	                         
	model.addAttribute("produit", p);									
	return "EditProduit";
	
	}
		
	@RequestMapping(value = "/admin/save", method = RequestMethod.POST)   
	public String save(Model model, @Valid Produit produit, 			  
			BindingResult bindingResult) {	          					  
	if (bindingResult.hasErrors())  									  
		return "FormProduit";											 																  	  
		produitRepository.save(produit);    
		
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

