



package pe.com.cayetano.see.dataacademico.model.request;



import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CompetenciaListRequest {

  private Long codempresa;
  private Long codcompetencia;
  private String descompetencia;
  private String descorta;
  private String feccreacioncadena;
  private Long codest;
  private Integer page;
  private Integer pageSize;
}
