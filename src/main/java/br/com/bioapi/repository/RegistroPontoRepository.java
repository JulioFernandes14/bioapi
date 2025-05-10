package br.com.bioapi.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bioapi.model.RegistroPonto;
import br.com.bioapi.model.TipoPonto;

@Repository
public interface RegistroPontoRepository  extends JpaRepository<RegistroPonto, Long>{
	public Optional<RegistroPonto> findByFuncionarioIdAndTipoAndHoraBetween(Long funcionarioId, TipoPonto tipo, LocalDateTime start, LocalDateTime end);
}
