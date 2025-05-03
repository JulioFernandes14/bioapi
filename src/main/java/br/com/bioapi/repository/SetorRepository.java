package br.com.bioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bioapi.model.Setor;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {

}
