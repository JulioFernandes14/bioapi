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
import br.com.bioapi.model.RegistroPonto;
import br.com.bioapi.service.RegistroPontoService;


@ResponseBody
@Controller
@RequestMapping("/bioapi/registro-ponto")
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
	public ResponseEntity<String> getSaldoMensalByFuncId(@RequestBody ResumoPontoRequestDto resumoPontoRequestDto) throws Exception {
		return ResponseEntity.ok(registroPontoService.getSaldoMensalByFuncId(resumoPontoRequestDto.getMes(), resumoPontoRequestDto.getAno(), resumoPontoRequestDto.getFuncionarioId()));
	}
	
	@GetMapping("/media-horas")
	public ResponseEntity<String> getMediaHorasSemanaAtual() throws Exception {
		return ResponseEntity.ok(registroPontoService.getMediaHorasSemanaAtual());
	}
	
}
