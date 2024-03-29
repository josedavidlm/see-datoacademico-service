package pe.com.cayetano.see.dataacademico.model.request;



import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BimestreListRequest {

  private Long codempresa;
  private Long codbimestre;
  private String desbimestre;
  private String descorta;
  private String feccreacioncadena;
  private Long codest;
  private Integer page;
  private Integer pageSize;
}
