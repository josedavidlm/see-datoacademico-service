



package pe.com.cayetano.see.dataacademico.model.request;



import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CompetenciaListRequest {

  private Long codempresa;
  private Long codCompetencia;
  private String desCompetencia;
  private String fecCreacion;
  private Long codest;
  private Integer page;
  private Integer pageSize;
}
