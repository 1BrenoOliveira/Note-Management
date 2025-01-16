package com.dev.breno.Note_Management.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dev.breno.Note_Management.models.Produto;

public class ProdutoDto {
	private long id;
	private String codigo;
	private String descricao;
	private BigDecimal valorUnitario;
	
	public ProdutoDto(long id, String codigo, String descricao, BigDecimal valorUnitario) {
		this.id = id;
		this.codigo = codigo;
		this.descricao = descricao;
		this.valorUnitario = valorUnitario;
	}
	
	public ProdutoDto(Produto produto) {
		this.id = produto.getId();
		this.codigo = produto.getCodigo();
		this.descricao = produto.getDescricao();
		this.valorUnitario = produto.getValorUnitario();
	}


	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public long getId() {
		return id;
	}

	public static List<ProdutoDto> converter(List<Produto> produtos) {
		List<ProdutoDto> produtosDto = new ArrayList<>();
		for (Produto produto : produtos) {
			produtosDto.add(new ProdutoDto(produto));
		}
		return produtosDto;
		
		
	}

}
