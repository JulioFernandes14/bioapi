package br.com.bioapi.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ResumoPontoDto {
	LocalDate getData();
    LocalTime getEntrada();
    LocalTime getPausa();
    LocalTime getRetorno();
    LocalTime getSaida();
    String getHorasTrabalhadas();
}
