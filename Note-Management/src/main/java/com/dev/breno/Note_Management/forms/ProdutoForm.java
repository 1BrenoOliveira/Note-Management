package com.dev.breno.Note_Management.forms;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.dev.breno.Note_Management.models.Produto;

public class ProdutoForm {
	
	@NotNull
	@NotEmpty 
	private String codigo;
	@NotNull
	@NotEmpty 
	private String descricao;
	@NotNull
	@NotEmpty 
	private String valorUnitario;
	
	
	public String getCodigo() {
		return codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public String getValorUnitario() {
		return valorUnitario;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	public Produto converter() {
		return new Produto(this.codigo, this.descricao, new BigDecimal(this.valorUnitario));
	}
	
}
