package br.com.bioapi.dto;

import br.com.bioapi.model.Biometria;

public class BiometriaDto {

	private Long funcionarioId;
	private String bioTemplate;
	
	public BiometriaDto() {
		// TODO Auto-generated constructor stub
	}

	public BiometriaDto(Long funcionarioId, String bioTemplate) {
		super();
		this.funcionarioId = funcionarioId;
		this.bioTemplate = bioTemplate;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public String getBioTemplate() {
		return bioTemplate;
	}

	public void setBioTemplate(String bioTemplate) {
		this.bioTemplate = bioTemplate;
	}
	
	public Biometria toEntity() {
		Biometria biometria = new Biometria();
		biometria.setBioTemplate(bioTemplate);
		return biometria;
	}
	
}
