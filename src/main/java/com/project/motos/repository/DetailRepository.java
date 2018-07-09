package com.project.motos.repository;

import com.project.motos.model.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("detail_repository")
public interface DetailRepository extends JpaRepository<Detail,Serializable> {

}
