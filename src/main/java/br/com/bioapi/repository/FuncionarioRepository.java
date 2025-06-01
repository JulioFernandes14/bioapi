package br.com.bioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.bioapi.model.Funcionario;
import br.com.bioapi.model.Status;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    @Query("SELECT COUNT(f) FROM Funcionario f WHERE f.status IN :statuses")
    Long countByStatusIn(@Param("statuses") Status... statuses);
}
