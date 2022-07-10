package com.bsale.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsale.server.entity.Category;
import com.bsale.server.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository repository;
	
	@Override
	public List<Category> listCategory() {
		return repository.findAll();
	}

}
