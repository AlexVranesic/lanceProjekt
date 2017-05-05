package si.triglav.hackathon.product;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import si.triglav.hackathon.team.Team;

@Controller
public class ProductController {

	@Autowired
	private ProductDAO productDAO;

	//List all products
	@RequestMapping( path="/products", method=RequestMethod.GET)
	public @ResponseBody List<Product> getProductList(){
		return productDAO.getProductList();
	}
	
	//List all products with id of Team
	@RequestMapping( path="/products/{id}", method=RequestMethod.GET)
	public @ResponseBody Product getProductById(@PathVariable(name="id_product") Integer id_product){
		return productDAO.getProductById(id_product);
	}
	
	//create new product
	@RequestMapping( path="/products", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createProduct(@RequestBody Product product){
				
		
		Product createdProduct = productDAO.createProduct(product); // this will set the id on the person object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdProduct.getId_product()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	
	
	
	/*
	@RequestMapping( path="/products", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createProduct(@RequestBody Product product){
				
		//optionally validate person
		Product createdProduct = productDAO.createProduct(product); // this will set the id on the person object
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdProduct.getId_product()).toUri();

		//by rest conventions we need to repond with the URI for newly created resource 
		return ResponseEntity.created(location).build();
			
	}
	
	*/
	
	
	@RequestMapping( path="/products/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateProduct(@PathVariable(name="id_product") Integer id_product, @RequestBody Product product){
		product.setId_product(id_product);
		int updatedRows = productDAO.updateProduct(product);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping( path="/products/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteProduct(@PathVariable(name="id_product") Integer id_product){
		
		int updatedRows = productDAO.deleteProduct(id_product);
		
		if(updatedRows == 0 ){
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
