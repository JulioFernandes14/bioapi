package br.com.bioapi.dto;

import java.time.LocalDateTime;

import br.com.bioapi.model.RegistroPonto;

public class RegistroPontoDto {

	private String hora;
	private Long funcionarioId;
	
	public RegistroPontoDto() {
		// TODO Auto-generated constructor stub
	}


	public RegistroPontoDto(String hora, Long funcionarioId) {
		this.hora = hora;
		this.funcionarioId = funcionarioId;
	}


	public String getHora() {
		return hora;
	}


	public void setHora(String hora) {
		this.hora = hora;
	}
	
	public Long getFuncionarioId() {
		return funcionarioId;
	}
	
	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}
	
	public RegistroPonto toEntity() {
	
		RegistroPonto registroPonto = new RegistroPonto();
		
		registroPonto.setHora(LocalDateTime.parse(hora));
		
		return registroPonto;
		
	}
}
