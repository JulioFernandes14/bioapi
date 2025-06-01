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

import br.com.bioapi.dto.FuncionarioDto;
import br.com.bioapi.model.Funcionario;
import br.com.bioapi.service.FuncionarioService;

@ResponseBody
@Controller
@RequestMapping("/bioapi/funcionario")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;
	
	@PostMapping
	public ResponseEntity<Funcionario> createController(@RequestBody FuncionarioDto funcionarioDto) throws Exception{
		return ResponseEntity.ok(funcionarioService.createService(funcionarioDto));
	}
	
	@GetMapping
	public ResponseEntity<List<Funcionario>> findAllController() throws Exception {
		return ResponseEntity.ok(funcionarioService.findAllService());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteController(@PathVariable Long id) throws Exception{
		funcionarioService.deleteByIdService(id);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Funcionario> updateByIdController(@RequestBody FuncionarioDto funcionarioDto, @PathVariable Long id) throws Exception{
		return ResponseEntity.ok(funcionarioService.updateByIdService(funcionarioDto, id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Funcionario> findByIdController(@PathVariable Long id) throws Exception{
		return ResponseEntity.ok(funcionarioService.findByIdService(id));
	}
	
	@GetMapping("/ativos")
	public ResponseEntity<Long> countAtivosController() throws Exception {
		return ResponseEntity.ok(funcionarioService.countAtivosService());
	}
	
}
