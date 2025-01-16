package com.dev.breno.Note_Management.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.dev.breno.Note_Management.models.Produto;

public class SpecificationProduto {

	
	public static Specification<Produto> codigo(String codigo){
		return (root, criteriaQuery, criteriaBuilder)-> criteriaBuilder.equal(root.get("codigo"), codigo);
	}
	
	public static Specification<Produto> descricao(String descricao){
		return (root, criteriaQuery, criteriaBuilder)-> criteriaBuilder.like(root.get("descricao"), "%"+descricao+"%");
	}
}
