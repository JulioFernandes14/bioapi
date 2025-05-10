package br.com.bioapi.dto;

public class ResumoPontoRequestDto {

	private int ano;
	private int mes;
	private Long funcionarioId;
	
	public ResumoPontoRequestDto() {
		// TODO Auto-generated constructor stub
	}

	public ResumoPontoRequestDto(int ano, int mes, Long funcionarioId) {
		super();
		this.ano = ano;
		this.mes = mes;
		this.funcionarioId = funcionarioId;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}
	
}
