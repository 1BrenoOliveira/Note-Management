package com.dev.breno.Note_Management.dtos;

public class ErroDevalidacaoDto {

	private String campo;
	private String erro;
	
	public ErroDevalidacaoDto(String campo, String erro) {
		super();
		this.campo = campo;
		this.erro = erro;
	}
	public String getCampo() {
		return campo;
	}
	public String getErro() {
		return erro;
	}
	
}
