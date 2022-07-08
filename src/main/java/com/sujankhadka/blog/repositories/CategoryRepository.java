package com.sujankhadka.blog.repositories;

import com.sujankhadka.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
