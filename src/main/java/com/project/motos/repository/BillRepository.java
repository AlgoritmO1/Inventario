package com.project.motos.repository;

import com.project.motos.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("billRepository")
public interface BillRepository extends JpaRepository<Bill,Serializable> {

}
