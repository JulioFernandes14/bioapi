package br.com.bioapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.bioapi.dto.RegistroPontoDto;
import br.com.bioapi.dto.ResumoPontoDto;
import br.com.bioapi.dto.ResumoPontoRequestDto;
import br.com.bioapi.dto.RelatorioSaldoMensalDto;
import br.com.bioapi.dto.PontosPorSemanaDto;
import br.com.bioapi.dto.HorasPorDiaDto;
import br.com.bioapi.dto.FuncionarioHorasExtrasDto;
import br.com.bioapi.model.RegistroPonto;
import br.com.bioapi.service.RegistroPontoService;


@ResponseBody
@Controller
@RequestMapping("/bioapi/registro-ponto")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistroPontoController {

	@Autowired
	private RegistroPontoService registroPontoService;
	
	@PostMapping
	public ResponseEntity<RegistroPonto> createController(@RequestBody RegistroPontoDto registroPontoDto) throws Exception{
		return ResponseEntity.ok(registroPontoService.createService(registroPontoDto));
	}
	
	@GetMapping
	public ResponseEntity<List<RegistroPonto>> findAllController() throws Exception {
		return ResponseEntity.ok(registroPontoService.findAllService());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteController(@PathVariable Long id) throws Exception{
		registroPontoService.deleteByIdService(id);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RegistroPonto> updateByIdController(@RequestBody RegistroPontoDto registroPontoDto, @PathVariable Long id) throws Exception{
		return ResponseEntity.ok(registroPontoService.updateByIdService(registroPontoDto, id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RegistroPonto> findByIdController(@PathVariable Long id) throws Exception{
		return ResponseEntity.ok(registroPontoService.findByIdService(id));
	}
	
	@GetMapping("/resumo")
	public ResponseEntity<List<ResumoPontoDto>> getResumeByFuncId(@RequestBody ResumoPontoRequestDto resumoPontoRequestDto) throws Exception {
		return ResponseEntity.ok(registroPontoService.getResumeByFuncId(resumoPontoRequestDto.getMes(), resumoPontoRequestDto.getAno(), resumoPontoRequestDto.getFuncionarioId()));
	}
	
	@GetMapping("saldo-horas") 
	public ResponseEntity<String> getSaldoSemanalByFuncId(@RequestBody ResumoPontoRequestDto resumoPontoRequestDto) throws Exception {
		return ResponseEntity.ok(registroPontoService.getSaldoSemanalByFuncId(resumoPontoRequestDto.getFuncionarioId()));
	}
	
	@GetMapping("/media-horas")
	public ResponseEntity<String> getMediaHorasSemanaAtual() throws Exception {
		return ResponseEntity.ok(registroPontoService.getMediaHorasSemanaAtual());
	}
	
	@GetMapping("/atrasos")
	public ResponseEntity<Long> contarAtrasosSemanaAtual() throws Exception {
		return ResponseEntity.ok(registroPontoService.contarAtrasosSemanaAtual());
	}
	
	@GetMapping("/relatorio-saldo-mensal/{mes}/{ano}")
	public ResponseEntity<RelatorioSaldoMensalDto> getRelatorioSaldoMensal(
			@PathVariable int mes,
			@PathVariable int ano) throws Exception {
		try {
			RelatorioSaldoMensalDto relatorio = registroPontoService.getRelatorioSaldoMensal(mes, ano);
			return ResponseEntity.ok(relatorio);
		} catch (Exception e) {
			throw new Exception("Erro ao buscar relatório de saldo mensal: " + e);
		}
	}
	
	@GetMapping("/horas-trabalhadas-semana/{funcionarioId}")
	public ResponseEntity<String> getHorasTrabalhadasSemanaAtual(@PathVariable Long funcionarioId) throws Exception {
		return ResponseEntity.ok(registroPontoService.getHorasTrabalhadasSemanaAtual(funcionarioId));
	}
	
	@GetMapping("/faltas-semana/{funcionarioId}")
	public ResponseEntity<Long> contarFaltasSemanaAtual(@PathVariable Long funcionarioId) throws Exception {
		return ResponseEntity.ok(registroPontoService.contarFaltasSemanaAtual(funcionarioId));
	}
	
	@GetMapping("/pontos-por-semana/{funcionarioId}")
	public ResponseEntity<List<PontosPorSemanaDto>> getPontosPorSemanaMesAtual(@PathVariable Long funcionarioId) throws Exception {
		return ResponseEntity.ok(registroPontoService.getPontosPorSemanaMesAtual(funcionarioId));
	}
	
	@GetMapping("/horas-por-dia/{funcionarioId}")
	public ResponseEntity<List<HorasPorDiaDto>> getHorasPorDiaSemanaAtual(@PathVariable Long funcionarioId) throws Exception {
		return ResponseEntity.ok(registroPontoService.getHorasPorDiaSemanaAtual(funcionarioId));
	}
	
	@GetMapping("/top5-horas-extras")
	public ResponseEntity<List<FuncionarioHorasExtrasDto>> getTop5FuncionariosHorasExtras() throws Exception {
		return ResponseEntity.ok(registroPontoService.getTop5FuncionariosHorasExtras());
	}
	
}
