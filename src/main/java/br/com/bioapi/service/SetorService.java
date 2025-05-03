package br.com.bioapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bioapi.dto.SetorDto;
import br.com.bioapi.model.Setor;
import br.com.bioapi.repository.SetorRepository;

@Service
public class SetorService {

	@Autowired
	private SetorRepository setorRepository;
	
		
	public Setor createService(SetorDto setorDto) throws Exception {
		try {
			Setor setor = setorDto.toEntity();
			Setor resp = setorRepository.save(setor);
			
			return resp;
		}catch (Exception e) {
			throw new Exception("Erro ao cadastrar setor: " + e);
		}
	}
	
	public List<Setor> findAllService() throws Exception {
		try {
			List<Setor> setores = setorRepository.findAll();
			
			return setores;
		}catch (Exception e) {
			throw new Exception("Erro ao listar setores: " + e);
		}
	}
	
	public Setor updateByIdService(SetorDto setorDto, Long setorId) throws Exception {
		try {
			Optional<Setor> setor = setorRepository.findById(setorId);
			
			if (setor.isEmpty()) {
				throw new Exception("Id de setor inválido");
			}

			setor.get().setName(setorDto.getName());
			Setor resp = setorRepository.save(setor.get());
			
			return resp;
		}catch (Exception e) {
			throw new Exception("Erro ao atualizar setor: " + e);
		}
	}
	
	public void deleteByIdService(Long id) throws Exception {
		try {
			Optional<Setor> setor = setorRepository.findById(id);
			
			if (setor.isEmpty()) {
				throw new Exception("Id de setor inválido");
			}
			
			setorRepository.deleteById(id);
		}catch (Exception e) {
			throw new Exception("Erro ao deletar setor: " + e);
		}
	}
	
	public Setor findByIdService(Long id) throws Exception {
		try {
			Optional<Setor> setor = setorRepository.findById(id);
			
			if (setor.isEmpty()) {
				throw new Exception("Id de setor inválido");
			}
			
			return setor.get();
		}catch (Exception e) {
			throw new Exception("Erro ao buscar setor: " + e);
		}
	}
	
}
