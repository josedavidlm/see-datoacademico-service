package pe.com.cayetano.see.dataacademico.model.request;



import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NivelListRequest {

  private Long codempresa;
  private Long codnivel;
  private String desnivel;
  private String descorta;
  private String feccreacioncadena;
  private Long codest;
  private Integer page;
  private Integer pageSize;
}
