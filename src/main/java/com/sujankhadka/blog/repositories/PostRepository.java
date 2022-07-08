package com.sujankhadka.blog.repositories;

import com.sujankhadka.blog.entities.Category;
import com.sujankhadka.blog.entities.Post;
import com.sujankhadka.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
     List<Post> findByUser(User user);
     List<Post> findByCategory(Category category);

     @Query("Select p from Post p where p.title like :key")
     List<Post> searchByTitle(@Param("key") String title);

}
