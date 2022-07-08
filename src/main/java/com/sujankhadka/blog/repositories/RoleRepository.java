package com.sujankhadka.blog.repositories;

import com.sujankhadka.blog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
