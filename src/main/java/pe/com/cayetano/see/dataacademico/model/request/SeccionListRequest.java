



package pe.com.cayetano.see.dataacademico.model.request;



import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SeccionListRequest {

  private Long codempresa;
  private Long codSeccion;
  private String desSeccion;
  private String descorta;
  private String feccreacioncadena;
  private Long codest;
  private Integer page;
  private Integer pageSize;
}
