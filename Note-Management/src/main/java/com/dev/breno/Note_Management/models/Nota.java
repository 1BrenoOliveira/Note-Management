package com.dev.breno.Note_Management.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Nota {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Cliente cliente;
	private LocalDate dataEmissao;
	private BigDecimal valorTotal;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id") 
	private List<Item> itens = new ArrayList<>();
	
	public Nota() {
		this.dataEmissao = LocalDate.now();
	}
	public Nota( Cliente cliente,String dataEmissao,  BigDecimal valorTotal, List<Item> itens) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		this.dataEmissao =LocalDate.parse(dataEmissao, formatter);
		this.cliente = cliente;
		this.dataEmissao = LocalDate.now();
		this.valorTotal = valorTotal;
		this.itens = itens;
	}

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public LocalDate getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public List<Item> getItens() {
		return itens;
	}
	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
	
	
}
