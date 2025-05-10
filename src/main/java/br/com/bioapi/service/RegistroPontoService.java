package br.com.bioapi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bioapi.dto.RegistroPontoDto;
import br.com.bioapi.model.Funcionario;
import br.com.bioapi.model.RegistroPonto;
import br.com.bioapi.model.Status;
import br.com.bioapi.model.TipoPonto;
import br.com.bioapi.repository.RegistroPontoRepository;

@Service
public class RegistroPontoService {

	@Autowired
	private RegistroPontoRepository registroPontoRepository;
	
	@Autowired FuncionarioService funcionarioService;
	
	public RegistroPonto createService(RegistroPontoDto registroPontoDto) throws Exception {
		try {
			RegistroPonto registroPonto = registroPontoDto.toEntity();
			Funcionario funcionario = funcionarioService.findByIdService(registroPontoDto.getFuncionarioId());
			
			LocalDate dataPonto = LocalDateTime.parse(registroPontoDto.getHora()).toLocalDate();
	        Optional<RegistroPonto> pontoSaidaExistente = registroPontoRepository
	            .findByFuncionarioIdAndTipoAndHoraBetween(funcionario.getId(), TipoPonto.SAIDA, 
	                                                      dataPonto.atStartOfDay(), dataPonto.plusDays(1).atStartOfDay());
	        
	        if (pontoSaidaExistente.isPresent()) {
	            throw new Exception("Erro: O expediente já foi finalizado em " + dataPonto.toString());
	        }
			
			if(funcionario.getStatus() == Status.AUSENTE) {
				funcionario = funcionarioService.updateStatusById(funcionario.getId(), Status.SERVICO_INICIO);
				registroPonto.setTipo(TipoPonto.ENTRADA);
			}
			
			else if (funcionario.getStatus() == Status.SERVICO_INICIO) {
				funcionario = funcionarioService.updateStatusById(funcionario.getId(), Status.PAUSA);
				registroPonto.setTipo(TipoPonto.PAUSA);
			}
			
			else if (funcionario.getStatus() == Status.PAUSA) {
				funcionario = funcionarioService.updateStatusById(funcionario.getId(), Status.SERVICO_TERMINO);
				registroPonto.setTipo(TipoPonto.RETORNO);
			}
			
			else if (funcionario.getStatus() == Status.SERVICO_TERMINO) {
				funcionario = funcionarioService.updateStatusById(funcionario.getId(), Status.AUSENTE);
				registroPonto.setTipo(TipoPonto.SAIDA);
			}
			
			registroPonto.setFuncionario(funcionario);
			
			RegistroPonto resp = registroPontoRepository.save(registroPonto);
			return resp;
		}catch (Exception e) {
			throw new Exception("Erro ao cadastrar registro de ponto: " + e);
		}
	}
	
	public List<RegistroPonto> findAllService() throws Exception {
		try {
			List<RegistroPonto> registroPonto = registroPontoRepository.findAll();
			
			return registroPonto;
		}catch (Exception e) {
			throw new Exception("Erro ao listar registro de pontos: " + e);
		}
	}
	
	public RegistroPonto updateByIdService(RegistroPontoDto registroPontoDto, Long registroPontoId) throws Exception {
		try {
			Optional<RegistroPonto> registroPonto = registroPontoRepository.findById(registroPontoId);
			
			if (registroPonto.isEmpty()) {
				throw new Exception("Id de registro de ponto inválido");
			}

			registroPonto.get().setHora(LocalDateTime.parse(registroPontoDto.getHora()));
			RegistroPonto resp = registroPontoRepository.save(registroPonto.get());
			
			return resp;
		}catch (Exception e) {
			throw new Exception("Erro ao atualizar registro de ponto: " + e);
		}
	}
	
	public void deleteByIdService(Long id) throws Exception {
		try {
			Optional<RegistroPonto> registroPonto = registroPontoRepository.findById(id);
			
			if (registroPonto.isEmpty()) {
				throw new Exception("Id de registro de ponto inválido");
			}

			registroPontoRepository.deleteById(id);
		}catch (Exception e) {
			throw new Exception("Erro ao deletar registro de ponto: " + e);
		}
	}
	
	public RegistroPonto findByIdService(Long Id) throws Exception {
		try {
			Optional<RegistroPonto> registroPonto = registroPontoRepository.findById(Id);
			
			if (registroPonto.isEmpty()) {
				throw new Exception("Id de registro de ponto inválido");
			}

			return registroPonto.get();
		}catch (Exception e) {
			throw new Exception("Erro ao buscar registro de ponto: " + e);
		}
	}
	
}
