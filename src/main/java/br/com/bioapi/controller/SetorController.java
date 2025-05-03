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

import br.com.bioapi.dto.SetorDto;
import br.com.bioapi.model.Setor;
import br.com.bioapi.service.SetorService;

@ResponseBody
@Controller
@RequestMapping("/bioapi/setor")
public class SetorController {

	@Autowired
	private SetorService setorService;
	
	@PostMapping
	public ResponseEntity<Setor> createController(@RequestBody SetorDto setorDto) throws Exception{
		return ResponseEntity.ok(setorService.createService(setorDto));
	}
	
	@GetMapping
	public ResponseEntity<List<Setor>> findAllController() throws Exception {
		return ResponseEntity.ok(setorService.findAllService());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteController(@PathVariable Long id) throws Exception{
		setorService.deleteByIdService(id);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Setor> updateByIdController(@RequestBody SetorDto setorDto, @PathVariable Long id) throws Exception{
		return ResponseEntity.ok(setorService.updateByIdService(setorDto, id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Setor> findByIdController(@PathVariable Long id) throws Exception{
		return ResponseEntity.ok(setorService.findByIdService(id));
	}
	
}
