package br.com.bioapi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;

@Entity
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String cargo;
	
	@Column(name = "url_foto")
	private String urlFoto;
	
	@ManyToOne
	@JoinColumn(name = "setor_id")
	private Setor setor;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@JsonIgnore
	@OneToMany(mappedBy = "funcionario")
	private List<Biometria> biometrias;
	
	@JsonIgnore
	@OneToMany(mappedBy = "funcionario")
	private List<RegistroPonto> registrosP;
		
	public Funcionario() {
		// TODO Auto-generated constructor stub
	}

	public Funcionario(Long id, String nome, String cargo, Setor setor, Status status, List<Biometria> biometrias, List<RegistroPonto> registrosP) {
		super();
		this.id = id;
		this.nome = nome;
		this.cargo = cargo;
		this.setor = setor;
		this.status = status;
		this.biometrias = biometrias;
		this.registrosP = registrosP;
	}

	public Funcionario(Long id, String nome, String cargo, Setor setor, Status status) {
		super();
		this.id = id;
		this.nome = nome;
		this.cargo = cargo;
		this.setor = setor;
		this.status = status;
		this.biometrias = new ArrayList<>();
		this.registrosP = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public List<Biometria> getBiometrias() {
		return biometrias;
	}
	
	public void setBiometrias(List<Biometria> biometrias) {
		this.biometrias = biometrias;
	}
	
	public List<RegistroPonto> getRegistrosP() {
		return registrosP;
	}
	
	public void setRegistrosP(List<RegistroPonto> registrosP) {
		this.registrosP = registrosP;
	}
	
	public String getUrlFoto() {
		return urlFoto;
	}
	
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
}
