package com.dev.breno.Note_Management.dtos;

import java.util.ArrayList;
import java.util.List;

import com.dev.breno.Note_Management.models.Cliente;

public class ClienteDto {
	
	private long id;
	private String codigo;
	private String nome;
	
	public ClienteDto(long id, String codigo, String nome) {
		this.id = id;
		this.codigo =codigo;
		this.nome = nome;
	}
	
	public ClienteDto(Cliente cliente) {
		this.id = cliente.getId();
		this.codigo = cliente.getCodigo();
		this.nome = cliente.getNome();
	}
	
	public String getCodigo() {
		return codigo;
	}
	public String getNome() {
		return nome;
	}
	 public long getId() {
		return id;
	}
	public static List<ClienteDto> converter(List<Cliente> clientes){
		List<ClienteDto> clientesDto = new ArrayList<>();
		for (Cliente cliente : clientes) {
			clientesDto.add(new ClienteDto(cliente));
		}
		return clientesDto;
	}
}
