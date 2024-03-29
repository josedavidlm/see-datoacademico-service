package pe.com.cayetano.see.dataacademico.model.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class BimestreResponse {

  private Long codEmpresa;
  private Long codBimestre;
  private String desBimestre;
  private String desCorta;
  private LocalDateTime fecCreacion;
  private Boolean activo;
  private String desActivo;
  private Long codEst;
  private String Estado;
  private Long codUsuario;
  private LocalDateTime fecAct;
  private  String nomTer;
}
