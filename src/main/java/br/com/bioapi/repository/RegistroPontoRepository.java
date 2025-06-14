package br.com.bioapi.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.bioapi.dto.ResumoPontoDto;
import br.com.bioapi.model.RegistroPonto;
import br.com.bioapi.model.TipoPonto;

@Repository
public interface RegistroPontoRepository  extends JpaRepository<RegistroPonto, Long>{
	public Optional<RegistroPonto> findByFuncionarioIdAndTipoAndHoraBetween(Long funcionarioId, TipoPonto tipo, LocalDateTime start, LocalDateTime end);
	
	@Query(value = """
	        SELECT
	            DATE(hora) AS data,
	            MAX(CASE tipo WHEN 'ENTRADA' THEN TIME(hora) END) AS entrada,
	            MAX(CASE tipo WHEN 'PAUSA' THEN TIME(hora) END) AS pausa,
	            MAX(CASE tipo WHEN 'RETORNO' THEN TIME(hora) END) AS retorno,
	            MAX(CASE tipo WHEN 'SAIDA' THEN TIME(hora) END) AS saida,
	            SEC_TO_TIME(
	                TIMESTAMPDIFF(SECOND, 
	                    MAX(CASE tipo WHEN 'ENTRADA' THEN hora END),
	                    MAX(CASE tipo WHEN 'PAUSA' THEN hora END)
	                ) +
	                TIMESTAMPDIFF(SECOND, 
	                    MAX(CASE tipo WHEN 'RETORNO' THEN hora END),
	                    MAX(CASE tipo WHEN 'SAIDA' THEN hora END)
	                )
	            ) AS horasTrabalhadas
	        FROM
	            registro_ponto
	        WHERE
	            MONTH(hora) = :mes AND YEAR(hora) = :ano AND funcionario_id = :funcId
	        GROUP BY
	            DATE(hora)
	        ORDER BY
	            data
	        """, nativeQuery = true)
	List<ResumoPontoDto> buscarResumoPorMesEAno(@Param("mes") int mes, @Param("ano") int ano, @Param("funcId") Long funcionarioId);
	
	@Query(value = """
	        SELECT
	            DATE(hora) AS data,
	            MAX(CASE tipo WHEN 'ENTRADA' THEN TIME(hora) END) AS entrada,
	            MAX(CASE tipo WHEN 'PAUSA' THEN TIME(hora) END) AS pausa,
	            MAX(CASE tipo WHEN 'RETORNO' THEN TIME(hora) END) AS retorno,
	            MAX(CASE tipo WHEN 'SAIDA' THEN TIME(hora) END) AS saida,
	            SEC_TO_TIME(
	                TIMESTAMPDIFF(SECOND, 
	                    MAX(CASE tipo WHEN 'ENTRADA' THEN hora END),
	                    MAX(CASE tipo WHEN 'PAUSA' THEN hora END)
	                ) +
	                TIMESTAMPDIFF(SECOND, 
	                    MAX(CASE tipo WHEN 'RETORNO' THEN hora END),
	                    MAX(CASE tipo WHEN 'SAIDA' THEN hora END)
	                )
	            ) AS horasTrabalhadas
	        FROM
	            registro_ponto
	        WHERE
	            hora >= DATE_SUB(CURRENT_DATE, INTERVAL WEEKDAY(CURRENT_DATE) DAY)
	            AND hora < DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL WEEKDAY(CURRENT_DATE) DAY), INTERVAL 7 DAY)
	        GROUP BY
	            DATE(hora), funcionario_id
	        ORDER BY
	            data
	        """, nativeQuery = true)
	List<ResumoPontoDto> buscarResumoDaSemanaAtual();
	
	@Query(value = """
	        SELECT COUNT(*)
	        FROM registro_ponto
	        WHERE tipo = 'ENTRADA'
	        AND TIME(hora) > '08:10:00'
	        AND hora >= DATE_SUB(CURRENT_DATE, INTERVAL WEEKDAY(CURRENT_DATE) DAY)
	        AND hora < DATE_ADD(DATE_SUB(CURRENT_DATE, INTERVAL WEEKDAY(CURRENT_DATE) DAY), INTERVAL 7 DAY)
	        """, nativeQuery = true)
	Long contarAtrasosSemanaAtual();
	
	@Query(value = """
	        SELECT
	            DATE(hora) AS data,
	            MAX(CASE tipo WHEN 'ENTRADA' THEN TIME(hora) END) AS entrada,
	            MAX(CASE tipo WHEN 'PAUSA' THEN TIME(hora) END) AS pausa,
	            MAX(CASE tipo WHEN 'RETORNO' THEN TIME(hora) END) AS retorno,
	            MAX(CASE tipo WHEN 'SAIDA' THEN TIME(hora) END) AS saida,
	            SEC_TO_TIME(
	                TIMESTAMPDIFF(SECOND, 
	                    MAX(CASE tipo WHEN 'ENTRADA' THEN hora END),
	                    MAX(CASE tipo WHEN 'PAUSA' THEN hora END)
	                ) +
	                TIMESTAMPDIFF(SECOND, 
	                    MAX(CASE tipo WHEN 'RETORNO' THEN hora END),
	                    MAX(CASE tipo WHEN 'SAIDA' THEN hora END)
	                )
	            ) AS horasTrabalhadas
	        FROM
	            registro_ponto
	        WHERE
	            funcionario_id = :funcId
	            AND hora BETWEEN :dataInicio AND :dataFim
	        GROUP BY
	            DATE(hora)
	        ORDER BY
	            data
	        """, nativeQuery = true)
	List<ResumoPontoDto> buscarResumoPorFuncionarioEData(
		@Param("funcId") Long funcionarioId,
		@Param("dataInicio") LocalDateTime dataInicio,
		@Param("dataFim") LocalDateTime dataFim
	);
	
	@Query(value = """
	        SELECT COUNT(*)
	        FROM registro_ponto
	        WHERE funcionario_id = :funcId
	        AND hora BETWEEN :dataInicio AND :dataFim
	        """, nativeQuery = true)
	Long contarPontosPorPeriodo(
		@Param("funcId") Long funcionarioId,
		@Param("dataInicio") LocalDateTime dataInicio,
		@Param("dataFim") LocalDateTime dataFim
	);
}
