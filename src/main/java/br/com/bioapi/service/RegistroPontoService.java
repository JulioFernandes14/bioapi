package br.com.bioapi.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bioapi.dto.FuncionarioHorasExtrasDto;
import br.com.bioapi.dto.HorasPorDiaDto;
import br.com.bioapi.dto.PontosPorSemanaDto;
import br.com.bioapi.dto.RegistroPontoDto;
import br.com.bioapi.dto.RelatorioSaldoMensalDto;
import br.com.bioapi.dto.ResumoPontoDto;
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
	
	public List<ResumoPontoDto> getResumeByFuncId(int mes, int ano, Long funcionarioId) throws Exception{
		try {
			funcionarioService.findByIdService(funcionarioId);
		
			return this.registroPontoRepository.buscarResumoPorMesEAno(mes, ano, funcionarioId);
			
			
		}catch (Exception e) {
			throw new Exception("Erro ao buscar resumo de ponto do funcionário: " + e);
		}
	}
	
	public String getSaldoSemanalByFuncId(Long funcionarioId) throws Exception {
		try {
			funcionarioService.findByIdService(funcionarioId);
			
			// Obtém a data atual
			LocalDate dataAtual = LocalDate.now();
			
			// Encontra a terça-feira da semana atual
			LocalDate tercaFeira = dataAtual;
			while (tercaFeira.getDayOfWeek() != DayOfWeek.TUESDAY) {
				tercaFeira = tercaFeira.minusDays(1);
			}
			
			// Obtém os registros da semana atual
			List<ResumoPontoDto> listaPontos = registroPontoRepository.buscarResumoPorFuncionarioEData(
				funcionarioId, 
				tercaFeira.atStartOfDay(), 
				dataAtual.plusDays(1).atStartOfDay()
			);
			
			Duration saldoTotal = Duration.ZERO;
			
			// Conta os dias úteis da semana (terça a domingo)
			long diasUteis = 0;
			for (LocalDate data = tercaFeira; !data.isAfter(dataAtual); data = data.plusDays(1)) {
				DayOfWeek diaSemana = data.getDayOfWeek();
				if (diaSemana != DayOfWeek.MONDAY) {
					diasUteis++;
				}
			}
			
			// Calcula o total de horas esperadas na semana até a data atual
			Duration cargaHorariaEsperada = Duration.ofHours(8 * diasUteis);
			
			// Calcula as horas trabalhadas
			for (ResumoPontoDto resumoPonto: listaPontos) {
				DayOfWeek diaSemana = resumoPonto.getData().getDayOfWeek();
				if (diaSemana == DayOfWeek.MONDAY) continue;
				
				if (resumoPonto.getHorasTrabalhadas() != null && !resumoPonto.getHorasTrabalhadas().isEmpty()) {
					LocalTime horasTrabalhadas = LocalTime.parse(resumoPonto.getHorasTrabalhadas());
					Duration duracaoTrabalhada = Duration.ofHours(horasTrabalhadas.getHour())
													  .plusMinutes(horasTrabalhadas.getMinute())
													  .plusSeconds(horasTrabalhadas.getSecond());
					saldoTotal = saldoTotal.plus(duracaoTrabalhada);
				}
			}
			
			// Calcula o saldo final (horas trabalhadas - horas esperadas)
			Duration saldoFinal = saldoTotal.minus(cargaHorariaEsperada);
			
			return formatarDuration(saldoFinal);
			
		} catch (Exception e) {
			throw new Exception("Erro ao calcular saldo semanal do funcionário: " + e);
		}
	}
	
	public String getMediaHorasSemanaAtual() throws Exception {
		try {
			List<ResumoPontoDto> listaPontos = registroPontoRepository.buscarResumoDaSemanaAtual();
			Integer totalFuncionarios = funcionarioService.findAllService().size();
			
			if (listaPontos.isEmpty() || totalFuncionarios == 0) {
				return "00:00:00";
			}
			
			Duration totalHoras = Duration.ZERO;
			int diasTrabalhados = 0;
			
			Map<LocalDate, Duration> horasPorDia = new HashMap<>();
			
			for (ResumoPontoDto resumoPonto: listaPontos) {
				DayOfWeek diaSemana = resumoPonto.getData().getDayOfWeek();
				if (diaSemana == DayOfWeek.MONDAY) continue;
				
				if (resumoPonto.getHorasTrabalhadas() != null && !resumoPonto.getHorasTrabalhadas().isEmpty()) {
					LocalTime horasTrabalhadas = LocalTime.parse(resumoPonto.getHorasTrabalhadas());
					Duration duracaoTrabalhada = Duration.ofHours(horasTrabalhadas.getHour())
														.plusMinutes(horasTrabalhadas.getMinute())
														.plusSeconds(horasTrabalhadas.getSecond());
					
					horasPorDia.merge(resumoPonto.getData(), duracaoTrabalhada, Duration::plus);
				}
			}
			
			for (Duration horasDoDia : horasPorDia.values()) {
				totalHoras = totalHoras.plus(horasDoDia);
				diasTrabalhados++;
			}
			
			if (diasTrabalhados == 0) {
				return "00:00:00";
			}
			
		
			Duration mediaDiaria = totalHoras.dividedBy(diasTrabalhados);
			Duration mediaPorFuncionario = mediaDiaria.dividedBy(totalFuncionarios);
			
			return formatarDuration(mediaPorFuncionario);
			
		} catch (Exception e) {
			throw new Exception("Erro ao calcular média de horas da semana: " + e);
		}
	}
	
	public Long contarAtrasosSemanaAtual() throws Exception {
		try {
			return registroPontoRepository.contarAtrasosSemanaAtual();
		} catch (Exception e) {
			throw new Exception("Erro ao contar atrasos da semana: " + e);
		}
	}
	
	private String formatarDuration(Duration duration) {
        long segundosTotais = duration.getSeconds();
        boolean negativo = segundosTotais < 0;
        segundosTotais = Math.abs(segundosTotais);

        long horas = segundosTotais / 3600;
        long minutos = (segundosTotais % 3600) / 60;
        long segundos = segundosTotais % 60;

        return String.format("%s%02d:%02d:%02d",
                negativo ? "-" : "",
                horas, minutos, segundos);
    }
	
	public RelatorioSaldoMensalDto getRelatorioSaldoMensal(int mes, int ano) throws Exception {
		try {
			List<Funcionario> funcionarios = funcionarioService.findAllService();
			RelatorioSaldoMensalDto relatorio = new RelatorioSaldoMensalDto();
			relatorio.setTotalFuncionarios(funcionarios.size());
			
			for (Funcionario funcionario : funcionarios) {
				String saldoMensal = getSaldoSemanalByFuncId(funcionario.getId());
				
				String[] partes = saldoMensal.split(":");
				Duration saldo = Duration.ofHours(Long.parseLong(partes[0]))
									  .plusMinutes(Long.parseLong(partes[1]))
									  .plusSeconds(Long.parseLong(partes[2]));
				
				if (saldo.isNegative()) {
					relatorio.setQuantidadeSaldoNegativo(relatorio.getQuantidadeSaldoNegativo() + 1);
				} else if (saldo.isZero()) {
					relatorio.setQuantidadeSaldoZerado(relatorio.getQuantidadeSaldoZerado() + 1);
				} else {
					relatorio.setQuantidadeSaldoPositivo(relatorio.getQuantidadeSaldoPositivo() + 1);
				}
			}
			
			return relatorio;
		} catch (Exception e) {
			throw new Exception("Erro ao gerar relatório de saldo mensal: " + e);
		}
	}
	
	public String getHorasTrabalhadasSemanaAtual(Long funcionarioId) throws Exception {
		try {
			funcionarioService.findByIdService(funcionarioId);
			
			// Obtém a data atual
			LocalDate dataAtual = LocalDate.now();
			
			// Encontra a terça-feira da semana atual
			LocalDate tercaFeira = dataAtual;
			while (tercaFeira.getDayOfWeek() != DayOfWeek.TUESDAY) {
				tercaFeira = tercaFeira.minusDays(1);
			}
			
			// Obtém os registros da semana atual
			List<ResumoPontoDto> listaPontos = registroPontoRepository.buscarResumoPorFuncionarioEData(
				funcionarioId, 
				tercaFeira.atStartOfDay(), 
				dataAtual.plusDays(1).atStartOfDay()
			);
			
			Duration totalHoras = Duration.ZERO;
			
			// Calcula as horas trabalhadas
			for (ResumoPontoDto resumoPonto: listaPontos) {
				DayOfWeek diaSemana = resumoPonto.getData().getDayOfWeek();
				if (diaSemana == DayOfWeek.MONDAY) continue;
				
				if (resumoPonto.getHorasTrabalhadas() != null && !resumoPonto.getHorasTrabalhadas().isEmpty()) {
					LocalTime horasTrabalhadas = LocalTime.parse(resumoPonto.getHorasTrabalhadas());
					Duration duracaoTrabalhada = Duration.ofHours(horasTrabalhadas.getHour())
													  .plusMinutes(horasTrabalhadas.getMinute())
													  .plusSeconds(horasTrabalhadas.getSecond());
					totalHoras = totalHoras.plus(duracaoTrabalhada);
				}
			}
			
			return formatarDuration(totalHoras);
			
		} catch (Exception e) {
			throw new Exception("Erro ao calcular horas trabalhadas da semana: " + e);
		}
	}
	
	public Long contarFaltasSemanaAtual(Long funcionarioId) throws Exception {
		try {
			funcionarioService.findByIdService(funcionarioId);
			
			// Obtém a data atual
			LocalDate dataAtual = LocalDate.now();
			
			// Encontra a terça-feira da semana atual
			LocalDate tercaFeira = dataAtual;
			while (tercaFeira.getDayOfWeek() != DayOfWeek.TUESDAY) {
				tercaFeira = tercaFeira.minusDays(1);
			}
			
			// Obtém os registros da semana atual
			List<ResumoPontoDto> listaPontos = registroPontoRepository.buscarResumoPorFuncionarioEData(
				funcionarioId, 
				tercaFeira.atStartOfDay(), 
				dataAtual.plusDays(1).atStartOfDay()
			);
			
			// Conta os dias úteis da semana (terça a domingo)
			long diasUteis = 0;
			for (LocalDate data = tercaFeira; !data.isAfter(dataAtual); data = data.plusDays(1)) {
				DayOfWeek diaSemana = data.getDayOfWeek();
				if (diaSemana != DayOfWeek.MONDAY) {
					diasUteis++;
				}
			}
			
			// Conta os dias com registro
			long diasComRegistro = 0;
			for (ResumoPontoDto resumoPonto: listaPontos) {
				DayOfWeek diaSemana = resumoPonto.getData().getDayOfWeek();
				if (diaSemana == DayOfWeek.MONDAY) continue;
				
				if (resumoPonto.getHorasTrabalhadas() != null && !resumoPonto.getHorasTrabalhadas().isEmpty()) {
					diasComRegistro++;
				}
			}
			
			// Retorna a diferença entre dias úteis e dias com registro
			return diasUteis - diasComRegistro;
			
		} catch (Exception e) {
			throw new Exception("Erro ao calcular faltas da semana: " + e);
		}
	}
	
	public List<PontosPorSemanaDto> getPontosPorSemanaMesAtual(Long funcionarioId) throws Exception {
		try {
			funcionarioService.findByIdService(funcionarioId);
			
			LocalDate dataAtual = LocalDate.now();
			LocalDate primeiroDiaMes = dataAtual.withDayOfMonth(1);
			LocalDate ultimoDiaMes = dataAtual.withDayOfMonth(dataAtual.lengthOfMonth());
			
			List<PontosPorSemanaDto> resultado = new ArrayList<>();
			
			// Calcula as 4 semanas do mês
			for (int semana = 1; semana <= 4; semana++) {
				LocalDate inicioSemana = primeiroDiaMes.plusDays((semana - 1) * 7);
				LocalDate fimSemana = inicioSemana.plusDays(6);
				
				// Se a semana começa depois do último dia do mês, não precisa processar
				if (inicioSemana.isAfter(ultimoDiaMes)) {
					resultado.add(new PontosPorSemanaDto("Semana " + semana, 0L));
					continue;
				}
				
				// Ajusta o fim da semana se ultrapassar o último dia do mês
				if (fimSemana.isAfter(ultimoDiaMes)) {
					fimSemana = ultimoDiaMes;
				}
				
				// Se a semana atual ainda não chegou, retorna 0 pontos
				if (inicioSemana.isAfter(dataAtual)) {
					resultado.add(new PontosPorSemanaDto("Semana " + semana, 0L));
					continue;
				}
				
				// Busca os registros da semana
				Long quantidadePontos = registroPontoRepository.contarPontosPorPeriodo(
					funcionarioId,
					inicioSemana.atStartOfDay(),
					fimSemana.plusDays(1).atStartOfDay()
				);
				
				resultado.add(new PontosPorSemanaDto("Semana " + semana, quantidadePontos));
			}
			
			return resultado;
			
		} catch (Exception e) {
			throw new Exception("Erro ao calcular pontos por semana do mês: " + e);
		}
	}
	
	public List<HorasPorDiaDto> getHorasPorDiaSemanaAtual(Long funcionarioId) throws Exception {
		try {
			funcionarioService.findByIdService(funcionarioId);
			
			// Obtém a data atual
			LocalDate dataAtual = LocalDate.now();
			
			// Encontra a terça-feira da semana atual
			LocalDate tercaFeira = dataAtual;
			while (tercaFeira.getDayOfWeek() != DayOfWeek.TUESDAY) {
				tercaFeira = tercaFeira.minusDays(1);
			}
			
			// Obtém os registros da semana atual
			List<ResumoPontoDto> listaPontos = registroPontoRepository.buscarResumoPorFuncionarioEData(
				funcionarioId, 
				tercaFeira.atStartOfDay(), 
				dataAtual.plusDays(1).atStartOfDay()
			);
			
			List<HorasPorDiaDto> resultado = new ArrayList<>();
			Map<DayOfWeek, String> horasPorDia = new HashMap<>();
			
			// Inicializa o mapa com 0 horas para cada dia
			for (DayOfWeek dia : DayOfWeek.values()) {
				if (dia != DayOfWeek.MONDAY) {
					horasPorDia.put(dia, "00:00:00");
				}
			}
			
			// Processa os registros
			for (ResumoPontoDto resumoPonto: listaPontos) {
				DayOfWeek diaSemana = resumoPonto.getData().getDayOfWeek();
				if (diaSemana == DayOfWeek.MONDAY) continue;
				
				if (resumoPonto.getHorasTrabalhadas() != null && !resumoPonto.getHorasTrabalhadas().isEmpty()) {
					horasPorDia.put(diaSemana, resumoPonto.getHorasTrabalhadas());
				}
			}
			
			// Converte o mapa para a lista de DTOs
			for (DayOfWeek dia : DayOfWeek.values()) {
				if (dia != DayOfWeek.MONDAY) {
					String nomeDia = "";
					switch (dia) {
						case TUESDAY: nomeDia = "TERÇA"; break;
						case WEDNESDAY: nomeDia = "QUARTA"; break;
						case THURSDAY: nomeDia = "QUINTA"; break;
						case FRIDAY: nomeDia = "SEXTA"; break;
						case SATURDAY: nomeDia = "SÁBADO"; break;
						case SUNDAY: nomeDia = "DOMINGO"; break;
						default: continue;
					}
					resultado.add(new HorasPorDiaDto(nomeDia, horasPorDia.get(dia)));
				}
			}
			
			return resultado;
			
		} catch (Exception e) {
			throw new Exception("Erro ao calcular horas por dia da semana: " + e);
		}
	}
	
	public List<FuncionarioHorasExtrasDto> getTop5FuncionariosHorasExtras() throws Exception {
		try {
			// Obtém a data atual
			LocalDate dataAtual = LocalDate.now();
			
			// Encontra a terça-feira da semana atual
			LocalDate tercaFeira = dataAtual;
			while (tercaFeira.getDayOfWeek() != DayOfWeek.TUESDAY) {
				tercaFeira = tercaFeira.minusDays(1);
			}
			
			// Obtém todos os funcionários
			List<Funcionario> funcionarios = funcionarioService.findAllService();
			List<FuncionarioHorasExtrasDto> resultado = new ArrayList<>();
			
			// Para cada funcionário, calcula as horas extras
			for (Funcionario funcionario : funcionarios) {
				// Obtém os registros da semana atual
				List<ResumoPontoDto> listaPontos = registroPontoRepository.buscarResumoPorFuncionarioEData(
					funcionario.getId(), 
					tercaFeira.atStartOfDay(), 
					dataAtual.plusDays(1).atStartOfDay()
				);
				
				Duration totalHoras = Duration.ZERO;
				
				// Conta os dias úteis da semana (terça a domingo)
				long diasUteis = 0;
				for (LocalDate data = tercaFeira; !data.isAfter(dataAtual); data = data.plusDays(1)) {
					DayOfWeek diaSemana = data.getDayOfWeek();
					if (diaSemana != DayOfWeek.MONDAY) {
						diasUteis++;
					}
				}
				
				// Calcula o total de horas esperadas na semana até a data atual
				Duration cargaHorariaEsperada = Duration.ofHours(8 * diasUteis);
				
				// Calcula as horas trabalhadas
				for (ResumoPontoDto resumoPonto: listaPontos) {
					DayOfWeek diaSemana = resumoPonto.getData().getDayOfWeek();
					if (diaSemana == DayOfWeek.MONDAY) continue;
					
					if (resumoPonto.getHorasTrabalhadas() != null && !resumoPonto.getHorasTrabalhadas().isEmpty()) {
						LocalTime horasTrabalhadas = LocalTime.parse(resumoPonto.getHorasTrabalhadas());
						Duration duracaoTrabalhada = Duration.ofHours(horasTrabalhadas.getHour())
														  .plusMinutes(horasTrabalhadas.getMinute())
														  .plusSeconds(horasTrabalhadas.getSecond());
						totalHoras = totalHoras.plus(duracaoTrabalhada);
					}
				}
				
				// Calcula as horas extras (horas trabalhadas - horas esperadas)
				Duration horasExtras = totalHoras.minus(cargaHorariaEsperada);
				
				// Só adiciona se tiver horas extras positivas
				if (horasExtras.compareTo(Duration.ZERO) > 0) {
					resultado.add(new FuncionarioHorasExtrasDto(
						funcionario.getId(),
						funcionario.getNome(),
						formatarDuration(horasExtras)
					));
				}
			}
			
			// Ordena por horas extras (maior para menor) e pega os 5 primeiros
			resultado.sort((a, b) -> {
				String[] partesA = a.getHorasExtras().split(":");
				String[] partesB = b.getHorasExtras().split(":");
				
				Duration horasA = Duration.ofHours(Long.parseLong(partesA[0]))
									   .plusMinutes(Long.parseLong(partesA[1]))
									   .plusSeconds(Long.parseLong(partesA[2]));
				
				Duration horasB = Duration.ofHours(Long.parseLong(partesB[0]))
									   .plusMinutes(Long.parseLong(partesB[1]))
									   .plusSeconds(Long.parseLong(partesB[2]));
				
				return horasB.compareTo(horasA);
			});
			
			return resultado.stream().limit(5).collect(Collectors.toList());
			
		} catch (Exception e) {
			throw new Exception("Erro ao calcular top 5 funcionários com horas extras: " + e);
		}
	}
	
}


