package br.com.bioapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Biometria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;
	
	private String bioTemplate;
	
	public Biometria() {
		// TODO Auto-generated constructor stub
	}

	public Biometria(Long id, Funcionario funcionario, String bioTemplate) {
		super();
		this.id = id;
		this.funcionario = funcionario;
		this.bioTemplate = bioTemplate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getBioTemplate() {
		return bioTemplate;
	}

	public void setBioTemplate(String bioTemplate) {
		this.bioTemplate = bioTemplate;
	}
	
}
