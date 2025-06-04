package br.com.bioapi.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bioapi.model.Administrador;
import br.com.bioapi.service.AdministradorService;

@RestController
@RequestMapping("/bioapi/administradores")
@CrossOrigin(origins = "http://localhost:4200")
public class AdministradorController {

    @Autowired
    private AdministradorService service;
    
    @GetMapping
    public List<Administrador> listarTodos() {
        return service.listarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Administrador> buscarPorId(@PathVariable Long id) {
        Optional<Administrador> admin = service.buscarPorId(id);
        return admin.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Administrador criar(@RequestBody Administrador administrador) {
        return service.salvar(administrador);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Administrador> atualizar(@PathVariable Long id, 
                                                 @RequestBody Administrador administrador) {
        if (!service.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        administrador.setId(id);
        return ResponseEntity.ok(service.salvar(administrador));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!service.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Administrador administrador) {
        String resultado = service.realizarLogin(administrador.getUsuario(), administrador.getSenha());
        return Map.of("login", resultado);
    }
} 