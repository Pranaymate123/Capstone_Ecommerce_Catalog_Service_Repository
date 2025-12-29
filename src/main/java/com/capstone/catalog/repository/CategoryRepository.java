package com.capstone.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.catalog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
