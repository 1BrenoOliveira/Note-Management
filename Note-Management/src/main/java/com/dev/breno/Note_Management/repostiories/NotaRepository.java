package com.dev.breno.Note_Management.repostiories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dev.breno.Note_Management.models.Nota;

public interface NotaRepository extends JpaRepository<Nota, Long>, JpaSpecificationExecutor<Nota>{
	
	Page<Nota> findAll(Pageable pageable);
}
