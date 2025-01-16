package com.dev.breno.Note_Management.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataUtil {
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	public static LocalDate converterEmLocalDate(String dataEmissao) {
		if(dataEmissao!=null) {
			return LocalDate.parse(dataEmissao, formatter);
		}
		return null;
	}

}
