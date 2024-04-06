package pe.com.cayetano.see.dataacademico.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import pe.com.cayetano.see.dataacademico.model.entity.CompetenciaEntity;
import pe.com.cayetano.see.dataacademico.model.id.CompetenciaId;
import pe.com.cayetano.see.dataacademico.model.projection.CompetenciaProjection;
import pe.com.cayetano.see.dataacademico.model.request.CompetenciaListRequest;

import java.util.Optional;


public interface CompetenciaRepository extends JpaRepository<CompetenciaEntity, CompetenciaId> {

  @Query(value = """
        SELECT COALESCE(Max(codCompetencia),0)+1
        FROM dev.competencia
        WHERE codempresa = :codEmpresa
      """, nativeQuery = true)
  Long obtenerCompetenciaId(Long codEmpresa);
  Optional<CompetenciaEntity> findByDesCompetencia(String desCompetencia);


  String QUERY_TABLA = """
  WHERE codempresa = :#{#rq.codempresa}
        AND (CAST(:#{#rq.codCompetencia}  AS INTEGER) IS NULL OR codCompetencia = :#{#rq.codCompetencia})
        AND (CAST(:#{#rq.desCompetencia} AS TEXT) IS NULL OR desCompetencia LIKE '%'||:#{#rq.desCompetencia}||'%')        
        AND (CAST(:#{#rq.codest}  AS INTEGER) IS NULL OR codest = :#{#rq.codest})
  """;
  @Query(value = """
          SELECT 
            codempresa, 
            codCompetencia, 
            desCompetencia,                
            codest, 
            CASE WHEN codest = 1 THEN 'REGISTRADO' WHEN codest = 2 THEN 'EN USO' WHEN codest = 3 THEN 'ANULADO' END as estado, 
            activo, 
            CASE WHEN activo THEN 'ACTIVO' ELSE 'INACTIVO' END as desActivo,
            feccreacion,
            codusuariocreacion,
            nomtercreacion,
            codusuariomodificacion,
            fecmodificacion,
            nomtermodificacion,
            codusuarioeliminacion,
            feceliminacion,
            nomtereliminacion            
          FROM dev.Competencia 
          """ + QUERY_TABLA,
          countQuery = """
          SELECT COUNT(1)
          """ + QUERY_TABLA,
      nativeQuery = true)
  Page<CompetenciaProjection> listarCompetencia(
      @Param("rq") CompetenciaListRequest rq,
      @PageableDefault(page = 0, size = 10) Pageable pageable
  );



}