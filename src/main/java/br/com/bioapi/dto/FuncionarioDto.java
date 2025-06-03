package br.com.bioapi.dto;

import java.util.ArrayList;

import br.com.bioapi.model.Funcionario;
import br.com.bioapi.model.Status;

public class FuncionarioDto {

	private String nome;
	private String cargo;
	private Long setorId;
	private String urlFoto;
	
	public FuncionarioDto() {
		// TODO Auto-generated constructor stub
	}

	public FuncionarioDto(String nome, String cargo, Long setorId, String urlFoto) {
		super();
		this.nome = nome;
		this.cargo = cargo;
		this.setorId = setorId;
		this.urlFoto = urlFoto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Long getSetorId() {
		return setorId;
	}

	public void setSetorId(Long setorId) {
		this.setorId = setorId;
	}
	
	public String getUrlFoto() {
		return urlFoto;
	}
	
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	public Funcionario toEntity() {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCargo(cargo);
		funcionario.setStatus(Status.AUSENTE);
		funcionario.setBiometrias(new ArrayList<>());
		funcionario.setRegistrosP(new ArrayList<>());
		funcionario.setUrlFoto(urlFoto);
		return funcionario;
	}
	
}
