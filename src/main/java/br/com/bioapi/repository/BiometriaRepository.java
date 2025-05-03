package br.com.bioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bioapi.model.Biometria;

@Repository
public interface BiometriaRepository extends JpaRepository<Biometria, Long> {

}
