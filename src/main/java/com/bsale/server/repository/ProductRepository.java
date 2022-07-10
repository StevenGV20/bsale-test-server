package com.bsale.server.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bsale.server.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query("Select p from Product p where p.name like :name")
	public abstract List<Product> listProductByName(@Param("name") String name);
	
	@Query("Select p from Product p where p.categoria.idcategory in :cat")
	public abstract Page<Product> listProductByCategory(@Param("cat") List<Long> cats, Pageable pageable);
	
	@Query("Select p from Product p where p.categoria.idcategory in :cats order by p.name asc")
	public abstract Page<Product> listProductByNameAZ(@Param("cats") List<Long> cats, Pageable pageable);
	
	@Query("Select p from Product p where p.categoria.idcategory in :cats order by p.name desc")
	public abstract Page<Product> listProductByNameZA(@Param("cats") List<Long> cats, Pageable pageable);
	
	@Query("Select p from Product p where p.categoria.idcategory in :cats order by p.price asc")
	public abstract Page<Product> listProductByPriceAsc(@Param("cats") List<Long> cats, Pageable pageable);
	
	@Query("Select p from Product p where p.categoria.idcategory in :cats order by p.price desc")
	public abstract Page<Product> listProductByPriceDesc(@Param("cats") List<Long> cats, Pageable pageable);
	
	@Query("Select p from Product p where p.categoria.idcategory in :cats and p.price between :min and :max")
	public abstract Page<Product> listProductByPriceRange(@Param("cats") List<Long> cats, @Param("min") double min, @Param("max") double max, Pageable pageable);
	
	@Query("Select p from Product p")
	public abstract Page<Product> listAllProduct(Pageable pageable);
}