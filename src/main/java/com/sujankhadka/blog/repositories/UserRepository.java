package com.sujankhadka.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sujankhadka.blog.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer>{

    Optional<User> findByEmail(String email);

}
