package com.dev.breno.Note_Management.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dev.breno.Note_Management.models.Item;

public class ItemDto {
	private long id;
	private String sequencial;
	private ProdutoDto produto;
	private int quantidade;
	private BigDecimal valorTotalItem;
	
	public ItemDto(Item item) {
		this.id = item.getId();
		this.sequencial = item.getSequencial();
		this.produto = new ProdutoDto(item.getProduto());
		this.quantidade = item.getQuantidade();
		this.valorTotalItem = item.getValorTotalItem();
		
	}

	public String getSequencial() {
		return sequencial;
	}

	public ProdutoDto getProduto() {
		return produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public BigDecimal getValorTotalItem() {
		return valorTotalItem;
	}
	public long getId() {
		return id;
	}

	public static List<ItemDto> converteEmListaItemDto(List<Item> itens) {
		List<ItemDto> lista = new ArrayList<>();
		for (Item item : itens) {
			lista.add(new ItemDto(item));
		}
		return lista;
	}
}
