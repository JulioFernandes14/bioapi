package br.com.bioapi.service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bioapi.model.Administrador;
import br.com.bioapi.repository.AdministradorRepository;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository repository;
    
    public List<Administrador> listarTodos() {
        return repository.findAll();
    }
    
    public Optional<Administrador> buscarPorId(Long id) {
        return repository.findById(id);
    }
    
    public Administrador salvar(Administrador administrador) {
        // Criptografa a senha antes de salvar
        administrador.setSenha(criptografarSenha(administrador.getSenha()));
        return repository.save(administrador);
    }
    
    public void excluir(Long id) {
        repository.deleteById(id);
    }
    
    public String realizarLogin(String usuario, String senha) {
        String senhaCriptografada = criptografarSenha(senha);
        Administrador admin = repository.findByUsuarioAndSenha(usuario, senhaCriptografada);
        
        if (admin != null) {
            return "login realizado";
        }
        return "Usuário e/ou senha inválidos";
    }
    
    private String criptografarSenha(String senha) {
        return Base64.getEncoder().encodeToString(senha.getBytes());
    }
} 