package br.com.bioapi.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bioapi.dto.BiometriaDto;
import br.com.bioapi.model.Biometria;
import br.com.bioapi.model.Funcionario;
import br.com.bioapi.repository.BiometriaRepository;

@Service
public class BiometriaService {

	@Autowired
	private BiometriaRepository biometriaRepository;
	
	@Autowired FuncionarioService funcionarioService;
	
	public Biometria createService(BiometriaDto biometriaDto) throws Exception {
		try {
			Biometria biometria = biometriaDto.toEntity();
			Funcionario funcionario = funcionarioService.findByIdService(biometriaDto.getFuncionarioId());
			
			biometria.setFuncionario(funcionario);
			Biometria resp = biometriaRepository.save(biometria);
			return resp;
		}catch (Exception e) {
			throw new Exception("Erro ao cadastrar biometria: " + e);
		}
	}
	
	public List<Biometria> findAllService() throws Exception {
		try {
			List<Biometria> biometria = biometriaRepository.findAll();
			
			return biometria;
		}catch (Exception e) {
			throw new Exception("Erro ao listar biometrias: " + e);
		}
	}
	
	public Biometria updateByIdService(BiometriaDto biometriaDto, Long biometriaId) throws Exception {
		try {
			Optional<Biometria> biometria = biometriaRepository.findById(biometriaId);
			
			if (biometria.isEmpty()) {
				throw new Exception("Id de biometria inválido");
			}

			biometria.get().setBioTemplate(biometriaDto.getBioTemplate());
			Biometria resp = biometriaRepository.save(biometria.get());
			
			return resp;
		}catch (Exception e) {
			throw new Exception("Erro ao atualizar biometria: " + e);
		}
	}
	
	public void deleteByIdService(Long id) throws Exception {
		try {
			Optional<Biometria> biometria = biometriaRepository.findById(id);
			
			if (biometria.isEmpty()) {
				throw new Exception("Id de biometria inválido");
			}

			biometriaRepository.deleteById(id);
		}catch (Exception e) {
			throw new Exception("Erro ao deletar biometria: " + e);
		}
	}
	
	public Biometria findByIdService(Long Id) throws Exception {
		try {
			Optional<Biometria> biometria = biometriaRepository.findById(Id);
			
			if (biometria.isEmpty()) {
				throw new Exception("Id de biometria inválido");
			}

			return biometria.get();
		}catch (Exception e) {
			throw new Exception("Erro ao buscar biometria: " + e);
		}
	}
	
}
