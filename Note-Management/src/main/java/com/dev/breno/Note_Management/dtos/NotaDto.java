package com.dev.breno.Note_Management.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.dev.breno.Note_Management.models.Nota;

public class NotaDto {
	private long id;
	private ClienteDto cliente;
	private LocalDate dataEmissao;
	private BigDecimal valorTotal;
	private List<ItemDto> itens;
	
	public NotaDto(long id, ClienteDto cliente) {
		this.id = id;
		this.cliente = cliente;
	}

	public NotaDto(Nota nota) {
		this.id = nota.getId();
		this.cliente = new ClienteDto(nota.getCliente());
		this.dataEmissao = nota.getDataEmissao();
		this.valorTotal = nota.getValorTotal();
		this.itens = ItemDto.converteEmListaItemDto(nota.getItens());
	}
	
	public ClienteDto getCliente() {
		return cliente;
	}

	public String getDataEmissao() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return dataEmissao.format(formatter);
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public List<ItemDto> getItens() {
		return itens;
	}
	public long getId() {
		return id;
	}

	public static List<NotaDto> converter(List<Nota> notas){
		List<NotaDto> lista = new ArrayList<>() ;
		for (Nota nota : notas) {
			lista.add(new NotaDto(nota));
		}
		return lista;
	}
}
