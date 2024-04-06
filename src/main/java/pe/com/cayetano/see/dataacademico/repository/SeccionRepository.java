package pe.com.cayetano.see.dataacademico.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import pe.com.cayetano.see.dataacademico.model.entity.SeccionEntity;
import pe.com.cayetano.see.dataacademico.model.id.SeccionId;
import pe.com.cayetano.see.dataacademico.model.projection.SeccionProjection;
import pe.com.cayetano.see.dataacademico.model.request.SeccionListRequest;

import java.util.Optional;


public interface SeccionRepository extends JpaRepository<SeccionEntity, SeccionId> {

  @Query(value = """
        SELECT COALESCE(Max(codSeccion),0)+1
        FROM dev.Seccion
        WHERE codempresa = :codEmpresa
      """, nativeQuery = true)
  Long obtenerSeccionId(Long codEmpresa);
  Optional<SeccionEntity> findByDesSeccion(String desSeccion);


  String QUERY_TABLA = """
  WHERE codempresa = :#{#rq.codempresa}
        AND (CAST(:#{#rq.codSeccion}  AS INTEGER) IS NULL OR codSeccion = :#{#rq.codSeccion})
        AND (CAST(:#{#rq.desSeccion} AS TEXT) IS NULL OR desSeccion LIKE '%'||:#{#rq.desSeccion}||'%')
        AND (CAST(:#{#rq.descorta} AS TEXT) IS NULL OR descorta LIKE '%'||:#{#rq.descorta}||'%' )
        AND (CAST(:#{#rq.codest}  AS INTEGER) IS NULL OR codest = :#{#rq.codest})
  """;
  @Query(value = """
          SELECT 
            codempresa, 
            codSeccion, 
            desSeccion, 
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
          FROM dev.Seccion 
          """ + QUERY_TABLA,
          countQuery = """
          SELECT COUNT(1)
          """ + QUERY_TABLA,
      nativeQuery = true)
  Page<SeccionProjection> listarSeccion(
      @Param("rq") SeccionListRequest rq,
      @PageableDefault(page = 0, size = 10) Pageable pageable
  );



}