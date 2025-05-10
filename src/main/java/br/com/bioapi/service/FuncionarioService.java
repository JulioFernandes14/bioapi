package br.com.bioapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bioapi.dto.FuncionarioDto;
import br.com.bioapi.model.Funcionario;
import br.com.bioapi.model.Setor;
import br.com.bioapi.model.Status;
import br.com.bioapi.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired SetorService setorService;
	
	public Funcionario createService(FuncionarioDto funcionarioDto) throws Exception {
		try {
			Funcionario funcionario = funcionarioDto.toEntity();
			Setor setor = setorService.findByIdService(funcionarioDto.getSetorId());
			
			funcionario.setSetor(setor);
			funcionario.setStatus(Status.AUSENTE);
			Funcionario resp = funcionarioRepository.save(funcionario);
			return resp;
		}catch (Exception e) {
			throw new Exception("Erro ao cadastrar funcionário: " + e);
		}
	}
	
	public List<Funcionario> findAllService() throws Exception {
		try {
			List<Funcionario> funcionario = funcionarioRepository.findAll();
			
			return funcionario;
		}catch (Exception e) {
			throw new Exception("Erro ao listar funcionários: " + e);
		}
	}
	
	public Funcionario updateByIdService(FuncionarioDto funcionarioDto, Long funcionarioId) throws Exception {
		try {
			Optional<Funcionario> funcionario = funcionarioRepository.findById(funcionarioId);
			
			if (funcionario.isEmpty()) {
				throw new Exception("Id de funcionário inválido");
			}

			funcionario.get().setNome(funcionarioDto.getNome());
			funcionario.get().setCargo(funcionarioDto.getCargo());
			Funcionario resp = funcionarioRepository.save(funcionario.get());
			
			return resp;
		}catch (Exception e) {
			throw new Exception("Erro ao atualizar funcionário: " + e);
		}
	}
	
	public void deleteByIdService(Long id) throws Exception {
		try {
			Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
			
			if (funcionario.isEmpty()) {
				throw new Exception("Id de funcionário inválido");
			}

			funcionarioRepository.deleteById(id);
		}catch (Exception e) {
			throw new Exception("Erro ao deletar funcionário: " + e);
		}
	}
	
	public Funcionario findByIdService(Long Id) throws Exception {
		try {
			Optional<Funcionario> funcionario = funcionarioRepository.findById(Id);
			
			if (funcionario.isEmpty()) {
				throw new Exception("Id de funcionário inválido");
			}

			return funcionario.get();
		}catch (Exception e) {
			throw new Exception("Erro ao buscar funcionário: " + e);
		}
	}
	
	public Funcionario updateStatusById(Long id, Status status) throws Exception {
		
		try {
			Funcionario funcionario = this.findByIdService(id);
			
			funcionario.setStatus(status);
			
			return funcionarioRepository.save(funcionario);
		}catch(Exception e) {
			throw new Exception("Erro ao atualizar status do funcionario: " + e);
		}
		
	}
	
}
