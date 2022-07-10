package com.bsale.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bsale.server.entity.Product;
import com.bsale.server.service.CategoryService;
import com.bsale.server.service.ProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = {"https://bsale-test-server.herokuapp.com/","http://localhost:5500"},methods = RequestMethod.GET)
public class ProductController {

	@Autowired
	private ProductService product;
	
	@Autowired
	private CategoryService category;
	
	@GetMapping("/listAll")
	@ApiOperation("Obtener todos los productos")
	@ApiResponse(code = 200, message = "OK")
	public ResponseEntity<Object> listProduct(@ApiParam(value = "El numero de pagina a filtrar", required = true, example = "1")Integer page){
		
		try {
			if(page == null || page < 0) {
				Map<String, Object> response = new HashMap<String, Object>();
				response.put("error", "Ingrese una categoria o numero de pagina mayor o igual a 0.");
				return new ResponseEntity<Object>(response,new HttpHeaders(),HttpStatus.BAD_REQUEST);
			}
			else {
				Pageable pageable = PageRequest.of(page, 8);
				return new ResponseEntity<Object>(product.listAllProducts(pageable),new HttpHeaders(),HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("error", "Hubo un error en el proceso, verifica bien tus datos");
			return new ResponseEntity<Object>(response,new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/listByCategory")
	@ApiOperation("Obtener todos los productos segun la categoria")
	@ApiResponse(code = 200, message = "OK")
	public ResponseEntity<Object> listByCategory(@ApiParam(value = "El id de la categoria a filtrar", required = true, example = "1") Long cat, 
			@ApiParam(value = "El numero de pagina a filtrar", required = true, example = "1", defaultValue = "0")Integer page){
		try {
			if(cat<0 || page < 0) {
				Map<String, Object> response = new HashMap<String, Object>();
				response.put("error", "Ingrese numero de pagina mayor o igual a 0.");
				return new ResponseEntity<Object>(response,new HttpHeaders(),HttpStatus.BAD_REQUEST);
			}
			else {
				Pageable pageable = PageRequest.of(page, 8);
				List<Long> listCat = new ArrayList<Long>();
				if(cat > 0) {
					listCat.add(cat);
				}else {
					category.listCategory().forEach(c -> {
						listCat.add(c.getIdcategory());
					});
				}
				return new ResponseEntity<Object>(product.listProductsByCategory(listCat, pageable),new HttpHeaders(),HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("error", "Hubo un error en el proceso, verifica bien tus datos");
			return new ResponseEntity<Object>(response,new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/listByName")
	@ApiOperation("Obtener todos los productos segun su nombre")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "Producto no encontrado")
	})
	public ResponseEntity<Object> listByName(@ApiParam(value = "El nombre del producto", required = true, example = "Energetica")String name){
		if(name == null || name.isEmpty()) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("error", "No has ingresado ningun nombre");
			return new ResponseEntity<Object>(response,new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<Object>(product.listProductsByName(name),new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/filter")
	@ApiOperation("Obtener todos los productos segun el filtro")
	@ApiResponse(code = 200, message = "OK")
	public ResponseEntity<Object> listProductByFilter(
			@ApiParam(value = "El tipo de filtro", required = true, example = "NameASC",defaultValue = "All",allowableValues =  "All, NameASC, NameDESC, PriceASC, PriceDESC") String type, 
			@ApiParam(value = "El id de la categoria a filtrar", required = true, example = "1", defaultValue = "0") Long cat, 
			@ApiParam(value = "El numero de pagina a filtrar", required = true, example = "1", defaultValue = "0")Integer page){
		try {
			if(cat<0 || page < 0) {
				Map<String, Object> response = new HashMap<String, Object>();
				response.put("error", "Hubo un error en el proceso verifica bien tus datos");
				return new ResponseEntity<Object>(response,new HttpHeaders(),HttpStatus.BAD_REQUEST);
			}else {
				Pageable pageable = PageRequest.of(page, 8);
				Page<Product> lista = null;
				List<Long> listCat = new ArrayList<Long>();
				if(cat > 0) {
					listCat.add(cat);
				}else {
					category.listCategory().forEach(c -> {
						listCat.add(c.getIdcategory());
					});
				}
				switch (type) {
					case "NameASC":
						lista = product.listProductByNameAZ(listCat,pageable);
						break;
					case "NameDESC":
						lista = product.listProductByNameZA(listCat,pageable);
						break;
					case "PriceASC":
						lista = product.listProductByPriceAsc(listCat,pageable);
						break;
					case "PriceDESC":
						lista = product.listProductByPriceDesc(listCat,pageable);
						break;
					default:
						lista = product.listProductsByCategory(listCat,pageable);
				}
				
				return new ResponseEntity<Object>(lista,new HttpHeaders(),HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("error", "Hubo un error en el proceso, verifica bien tus datos");
			return new ResponseEntity<Object>(response,new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}
	}
}
