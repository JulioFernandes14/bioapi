package br.com.bioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.bioapi.model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Administrador findByUsuarioAndSenha(String usuario, String senha);
} 