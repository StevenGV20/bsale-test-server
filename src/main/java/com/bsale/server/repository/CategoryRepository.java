package com.bsale.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsale.server.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
