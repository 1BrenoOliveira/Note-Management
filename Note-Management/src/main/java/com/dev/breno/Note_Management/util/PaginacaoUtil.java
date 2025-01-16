package com.dev.breno.Note_Management.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class  PaginacaoUtil {
	public static Pageable gerarPagebale(String page, String size){
		if(page==null)page="0";
		if(size==null)size ="10";
		Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
		return pageable;
}
}
