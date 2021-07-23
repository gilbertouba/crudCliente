package com.crudcliente.gilberto.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.crudcliente.gilberto.demo.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository <Client,Long> {
	
}


