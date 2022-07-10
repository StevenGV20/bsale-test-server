package com.bsale.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bsale.server.entity.Product;

public interface ProductService {

	public abstract Page<Product> listAllProducts(Pageable page);
	public abstract Optional<Product> findProductById(Long id);
	public abstract Page<Product> listProductsByCategory(List<Long> cats, Pageable pageable);
	public abstract List<Product> listProductsByName(String name);
	public abstract Page<Product> listProductsByPrice(List<Long> cats, double min_price, double max_price, Pageable pageable);
	public abstract Page<Product> listProductByNameAZ(List<Long> cats, Pageable page);
	public abstract Page<Product> listProductByNameZA(List<Long> cats, Pageable page);
	public abstract Page<Product> listProductByPriceAsc(List<Long> cats, Pageable page);
	public abstract Page<Product> listProductByPriceDesc(List<Long> cats, Pageable page);
	
}
