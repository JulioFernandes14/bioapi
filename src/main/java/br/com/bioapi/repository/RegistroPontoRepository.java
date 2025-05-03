package br.com.bioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bioapi.model.RegistroPonto;

@Repository
public interface RegistroPontoRepository  extends JpaRepository<RegistroPonto, Long>{

}
