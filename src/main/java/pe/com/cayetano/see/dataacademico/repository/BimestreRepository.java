package pe.com.cayetano.see.dataacademico.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import pe.com.cayetano.see.dataacademico.model.entity.BimestreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.cayetano.see.dataacademico.model.id.BimestreId;
import pe.com.cayetano.see.dataacademico.model.projection.BimestreProjection;
import pe.com.cayetano.see.dataacademico.model.request.BimestreListRequest;
import pe.com.cayetano.see.dataacademico.model.request.BimestreRequest;


import java.util.List;
import java.util.Optional;


public interface BimestreRepository extends JpaRepository<BimestreEntity, BimestreId> {

  @Query(value = """
      SELECT COALESCE(Max(codbimestre),0)+1
      FROM dev.bimestre
	    WHERE codempresa = :codEmpresa
      """, nativeQuery = true)
  Long obtenerBimestreId(Long codEmpresa);
  Optional<BimestreEntity> findByDesBimestre(String desBimestre);



  @Query(value = """
      SELECT
      	codempresa,
      	codbimestre,
      	desbimestre,
      	descorta,
      	feccreacion,
      	codest,
      	CASE
      		WHEN codest = 1 THEN 'ACTIVO'
      		WHEN codest = 2 THEN 'EN USO'
      		WHEN codest = 3 THEN 'ANULADO'
      	END as estado,
      	activo,
      	CASE
      		WHEN activo THEN 'ACTIVO'
      		ELSE 'INACTIVO'
      	END as desActivo,
      	codusuario,
      	fecact,
      	nomter      	
      FROM dev.bimestre     
      WHERE codempresa = :#{#rq.codempresa}
        AND (CAST(:#{#rq.codbimestre}  AS INTEGER) IS NULL OR codbimestre = :#{#rq.codbimestre})
        AND (CAST(:#{#rq.desbimestre} AS TEXT) IS NULL OR desbimestre LIKE '%'||:#{#rq.desbimestre}||'%')
        AND (CAST(:#{#rq.descorta} AS TEXT) IS NULL OR descorta LIKE '%'||:#{#rq.descorta}||'%' )
        AND (CAST(:#{#rq.codest}  AS INTEGER) IS NULL OR codest = :#{#rq.codest})
      """,
      nativeQuery = true)
  List<BimestreProjection> listarBimestre(
      @Param("rq") BimestreListRequest rq,
      @PageableDefault(page = 0, size = 10) Pageable pageable
  );


  @Query(value =  """
          SELECT COUNT(1)
          FROM dev.bimestre     
          WHERE codempresa = :#{#rq.codempresa}
            AND (CAST(:#{#rq.codbimestre}  AS INTEGER) IS NULL OR codbimestre = :#{#rq.codbimestre})
            AND (CAST(:#{#rq.desbimestre} AS TEXT) IS NULL OR desbimestre LIKE '%'||:#{#rq.desbimestre}||'%')
            AND (CAST(:#{#rq.descorta} AS TEXT) IS NULL OR descorta LIKE '%'||:#{#rq.descorta}||'%' )
            AND (CAST(:#{#rq.codest}  AS INTEGER) IS NULL OR codest = :#{#rq.codest})
          """,
      nativeQuery = true)
  Long  countlistarBimestre(
      @Param("rq") BimestreListRequest rq
  );

}