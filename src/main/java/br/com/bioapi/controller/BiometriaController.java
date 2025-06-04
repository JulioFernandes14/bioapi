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

import br.com.bioapi.dto.BiometriaDto;
import br.com.bioapi.model.Biometria;
import br.com.bioapi.service.BiometriaService;

@ResponseBody
@Controller
@RequestMapping("/bioapi/biometria")
@CrossOrigin(origins = "http://localhost:4200")
public class BiometriaController {

	@Autowired
	private BiometriaService biometriaService;
	
	@PostMapping
	public ResponseEntity<Biometria> createController(@RequestBody BiometriaDto biometriaDto) throws Exception{
		return ResponseEntity.ok(biometriaService.createService(biometriaDto));
	}
	
	@GetMapping
	public ResponseEntity<List<Biometria>> findAllController() throws Exception {
		return ResponseEntity.ok(biometriaService.findAllService());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteController(@PathVariable Long id) throws Exception{
		biometriaService.deleteByIdService(id);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Biometria> updateByIdController(@RequestBody BiometriaDto biometriaDto, @PathVariable Long id) throws Exception{
		return ResponseEntity.ok(biometriaService.updateByIdService(biometriaDto, id));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Biometria> findByIdController(@PathVariable Long id) throws Exception{
		return ResponseEntity.ok(biometriaService.findByIdService(id));
	}
	
}
