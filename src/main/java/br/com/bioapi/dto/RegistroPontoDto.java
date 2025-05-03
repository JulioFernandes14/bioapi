package br.com.bioapi.dto;

import java.time.LocalDateTime;

import br.com.bioapi.model.RegistroPonto;
import br.com.bioapi.model.TipoPonto;

public class RegistroPontoDto {

	private TipoPonto tipo;
	private LocalDateTime hora;
	
	
	public RegistroPontoDto() {
		// TODO Auto-generated constructor stub
	}


	public RegistroPontoDto(TipoPonto tipo, LocalDateTime hora) {
		super();
		this.tipo = tipo;
		this.hora = hora;
	}


	public TipoPonto getTipo() {
		return tipo;
	}


	public void setTipo(TipoPonto tipo) {
		this.tipo = tipo;
	}


	public LocalDateTime getHora() {
		return hora;
	}


	public void setHora(LocalDateTime hora) {
		this.hora = hora;
	}
	
	public RegistroPonto toEntity() {
	
		RegistroPonto registroPonto = new RegistroPonto();
		
		registroPonto.setHora(hora);
		registroPonto.setTipo(tipo);
		
		return registroPonto;
		
	}
}
