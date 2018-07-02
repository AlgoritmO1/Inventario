package com.project.motos.repository;

import com.project.motos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("user_repository")
public interface UserRepository extends JpaRepository<User,Serializable> {
}
