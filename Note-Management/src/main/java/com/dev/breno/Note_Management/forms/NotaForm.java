package com.dev.breno.Note_Management.forms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.dev.breno.Note_Management.models.Cliente;
import com.dev.breno.Note_Management.models.Item;
import com.dev.breno.Note_Management.models.Nota;
import com.dev.breno.Note_Management.repostiories.ProdutoRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

public class NotaForm {
	@NotEmpty @NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private String dataEmissao;
	@NotEmpty @NotNull
	private String cliente;
	private List<ItemForm> itens = new ArrayList<>();
	
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public List<ItemForm> getItens() {
		return itens;
	}
	public void setItens(List<ItemForm> itens) {
		this.itens = itens;
	}
	
	public String getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public Nota converte(Cliente cliente, ProdutoRepository produtoRepository) throws NotFoundException {
		BigDecimal valorTotal = new BigDecimal(0.0);
		List<Item> lista = ItemForm.converteEmListaItens(itens, produtoRepository);
		if(!lista.isEmpty()) {
			for (Item item : lista) {
				valorTotal.add(item.getValorTotalItem());
			}
		}
		return new Nota(cliente,dataEmissao, valorTotal, lista);
	}
}
