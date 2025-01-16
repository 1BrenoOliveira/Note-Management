package com.dev.breno.Note_Management.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.dev.breno.Note_Management.models.Cliente;

public class SpecificationCliente {
	
	public static Specification<Cliente> nome(String nome){
		return (root, criteriaQuery, criteriaBuilder)-> criteriaBuilder.like(root.get("nome"), "%"+nome+"%");
	}
	
	public static Specification<Cliente> codigo(String codigo){
		return (root, criteriaQuery, criteriaBuilder)->criteriaBuilder.like(root.get("codigo"), "%" + codigo + "%");
	}

}
