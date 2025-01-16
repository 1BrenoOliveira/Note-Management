package com.dev.breno.Note_Management.specifications;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.dev.breno.Note_Management.models.Nota;

public class SpecificationNota {
	
	
	public static Specification<Nota> clienteNome(String cliente){
		return (root, criteriaQuery, criteriaBuilder)->criteriaBuilder.like(root.join("cliente").get("nome"), "%" + cliente + "%");
	}
	public static Specification<Nota> dataEmissao(LocalDate dataEmissao ){
		return (root, criteriaQuery, criteriaBuilder)->criteriaBuilder.equal(root.get("dataEmissao"), dataEmissao);
	}
}
