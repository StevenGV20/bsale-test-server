package com.bsale.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bsale.server.entity.Product;
import com.bsale.server.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository repository;
	
	@Override
	public Optional<Product> findProductById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<Product> listProductsByCategory(Long cat, Pageable pageable) {
		return repository.listProductByCategory(cat, pageable);
	}

	@Override
	public List<Product> listProductsByName(String name) {
		return repository.listProductByName("%"+name+"%");
	}

	@Override
	public Page<Product> listProductsByPrice(List<Long> cats, double min_price, double max_price, Pageable pageable) {
		return repository.listProductByPriceRange(cats,min_price, max_price, pageable);
	}

	@Override
	public Page<Product> listAllProducts(Pageable page) {
		return repository.findAll(page);
	}

	@Override
	public Page<Product> listProductByNameAZ(List<Long> cats, Pageable page) {
		System.out.println("cats sevice: "+cats);
		return repository.listProductByNameAZ(cats,page);
	}

	@Override
	public Page<Product> listProductByNameZA(List<Long> cats, Pageable page) {
		return repository.listProductByNameZA(cats,page);
	}

	@Override
	public Page<Product> listProductByPriceAsc(List<Long> cats, Pageable page) {
		return repository.listProductByPriceAsc(cats, page);
	}

	@Override
	public Page<Product> listProductByPriceDesc(List<Long> cats, Pageable page) {
		return repository.listProductByPriceDesc(cats,page);
	}


}
