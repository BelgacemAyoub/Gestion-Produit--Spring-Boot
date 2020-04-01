package org.sid.dao;



import org.sid.entities.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProduitRepository extends JpaRepository<Produit,Long> {
	
	@Query ("select p from Produit p where p.designation like :x")    			  
	public Page<Produit> chercher (@Param("x") String mc, Pageable pageable);     
																	   			   // a chaque fois que vous avez une methode qui retourne une page on va déclarer 
																	   			   // un objet de type pageable (pegeable de springData)
	
												

}
