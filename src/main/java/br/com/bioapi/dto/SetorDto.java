package br.com.bioapi.dto;

import java.util.ArrayList;

import br.com.bioapi.model.Setor;

public class SetorDto {

	private String name;
	
	public SetorDto() {
		// TODO Auto-generated constructor stub
	}

	public SetorDto(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Setor toEntity() {
		Setor setor = new Setor();
		setor.setName(name);
		setor.setFuncionarios(new ArrayList<>());
		return setor;
	}
	
}
