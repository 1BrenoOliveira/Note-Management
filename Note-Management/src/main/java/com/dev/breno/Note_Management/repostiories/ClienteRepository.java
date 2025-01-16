package com.dev.breno.Note_Management.repostiories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dev.breno.Note_Management.models.Cliente;

 
public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {

	Optional<Cliente> findByCodigo(String codigo);


}
