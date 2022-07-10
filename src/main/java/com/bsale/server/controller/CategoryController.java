package com.bsale.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bsale.server.entity.Category;
import com.bsale.server.service.CategoryService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = {"https://bsale-test-svg-client.herokuapp.com","http://localhost:5500"},methods = RequestMethod.GET)
public class CategoryController {
	
	@Autowired
	private CategoryService category;
	
	@GetMapping("/")
	@ApiOperation("Obtener todas las categorias")
	@ApiResponse(code = 200, message = "OK")
	public List<Category> listCategory(){
		return category.listCategory();
	}
	
}
