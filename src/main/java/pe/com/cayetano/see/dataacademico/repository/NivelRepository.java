package pe.com.cayetano.see.dataacademico.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import pe.com.cayetano.see.dataacademico.model.entity.BimestreEntity;
import pe.com.cayetano.see.dataacademico.model.entity.NivelEntity;
import pe.com.cayetano.see.dataacademico.model.id.NivelId;
import pe.com.cayetano.see.dataacademico.model.projection.NivelProjection;
import pe.com.cayetano.see.dataacademico.model.request.NivelListRequest;

import java.util.Optional;


public interface NivelRepository extends JpaRepository<NivelEntity, NivelId> {

  @Query(value = """
        SELECT COALESCE(Max(codnivel),0)+1
        FROM dev.nivel
        WHERE codempresa = :codEmpresa
      """, nativeQuery = true)
  Long obtenerNivelId(Long codEmpresa);
  Optional<NivelEntity> findByDesNivel(String desNivel);


  String QUERY_TABLA = """
  WHERE codempresa = :#{#rq.codempresa}
        AND (CAST(:#{#rq.codnivel}  AS INTEGER) IS NULL OR codnivel = :#{#rq.codnivel})
        AND (CAST(:#{#rq.desnivel} AS TEXT) IS NULL OR desnivel LIKE '%'||:#{#rq.desnivel}||'%')
        AND (CAST(:#{#rq.descorta} AS TEXT) IS NULL OR descorta LIKE '%'||:#{#rq.descorta}||'%' )
        AND (CAST(:#{#rq.codest}  AS INTEGER) IS NULL OR codest = :#{#rq.codest})
  """;
  @Query(value = """
          SELECT 
            codempresa, 
            codnivel, 
            desnivel, 
            descorta,            
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
          FROM dev.nivel 
          """ + QUERY_TABLA,
          countQuery = """
          SELECT COUNT(1)
          """ + QUERY_TABLA,
      nativeQuery = true)
  Page<NivelProjection> listarNivel(
      @Param("rq") NivelListRequest rq,
      @PageableDefault(page = 0, size = 10) Pageable pageable
  );



}