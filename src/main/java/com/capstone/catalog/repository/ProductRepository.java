package com.capstone.catalog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.catalog.entities.Category;
import com.capstone.catalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
    Page<Product> findByTitleContainingIgnoreCase(String subTitle,Pageable pageable);
      
     Page<Product> findByLiveTrue(Pageable pageable);
     
      Page<Product> findByCategory(Category category , Pageable pageable);
     
}
