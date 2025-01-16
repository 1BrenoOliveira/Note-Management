package com.dev.breno.Note_Management.forms;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.dev.breno.Note_Management.models.Item;
import com.dev.breno.Note_Management.models.Produto;
import com.dev.breno.Note_Management.repostiories.ProdutoRepository;

public class ItemForm {
	@NotNull@NotEmpty
	private String sequencial;
	@NotNull@NotEmpty
	private long produto;
	@NotNull@NotEmpty
	private int quantidade;
	
 
	public String getSequencial() {
		return sequencial;
	}
	public void setSequencial(String sequencial) {
		this.sequencial = sequencial;
	}
	public long getProduto() {
		return produto;
	}
	public void setProduto(long produto) {
		this.produto = produto;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	

	public Item converterEmItem(ProdutoRepository produtoRepository) throws NotFoundException {		
		Optional<Produto> optional = produtoRepository.findById(this.produto);
		if(optional.isPresent()) {
			Produto produto = optional.get();
			Item item = new Item();
			item.setSequencial(this.sequencial);
			item.setProduto(produto);
			item.setQuantidade(this.quantidade);
			item.setValorTotalItem(new BigDecimal(this.quantidade * produto.getValorUnitario().doubleValue() ).setScale(2,RoundingMode.DOWN));
			return item;
		}
			throw new NotFoundException();
	}
	public static List<Item> converteEmListaItens(List<ItemForm> itensForm, ProdutoRepository produtoRepository) throws NotFoundException{
		List<Item> lista = new ArrayList<>();
		if(!itensForm.isEmpty()) {
			for (ItemForm itemNotaForm : itensForm) {
				lista.add(itemNotaForm.converterEmItem(produtoRepository));
			}
		}
		
		return lista;
	}
	
	
}
