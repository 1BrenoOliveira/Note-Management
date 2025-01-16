package com.dev.breno.Note_Management.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.dev.breno.Note_Management.models.Cliente;

public class ClienteForm {
	@NotNull
	@NotEmpty 
	private String codigo;
	@NotNull
	@NotEmpty 
	private String nome;
	
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
		public String getCodigo() {
		return codigo;
	}
	public String getNome() {
		return nome;
	}
	
	public Cliente converteEmCliente() {
		return new Cliente(this.codigo, this.nome);
	}
	
	
	

}
