package com.dev.breno.Note_Management.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dev.breno.Note_Management.dtos.ErroDevalidacaoDto;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource messageSorce;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDevalidacaoDto> handle(MethodArgumentNotValidException exception){
		
		List<FieldError> fieldEroros =  exception.getBindingResult().getFieldErrors();
		List<ErroDevalidacaoDto> dto = new ArrayList<>();
		
		fieldEroros.forEach(e->{
			String mensagem = messageSorce.getMessage(e, LocaleContextHolder.getLocale());
			ErroDevalidacaoDto erro = new ErroDevalidacaoDto(e.getField(), mensagem);
			dto.add(erro);
		});
		return dto;
	}
	
}
