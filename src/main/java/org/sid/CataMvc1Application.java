package org.sid;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CataMvc1Application {

	public static void main(String[] args) {
		ApplicationContext ctx=SpringApplication.run(CataMvc1Application.class, args);
		
		
		/*
		 * ProduitRepository produitRepository=ctx.getBean(ProduitRepository.class); //
		 * donne moi un objet produitRepository qui implemente cette interface
		 * 
		 * produitRepository.save(new Produit("L 235", 250, 10));
		 * produitRepository.save(new Produit("K 360", 125, 15));
		 * produitRepository.save(new Produit("M 4", 320, 20));
		 * produitRepository.save(new Produit("F 16", 600, 5));
		 * 
		 * 
		 * produitRepository.findAll().forEach(p->System.out.println(p.getDesignation())
		 * );
		 */
		
	}
	

}



