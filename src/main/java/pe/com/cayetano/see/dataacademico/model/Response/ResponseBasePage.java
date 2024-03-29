package pe.com.cayetano.see.dataacademico.model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pe.com.cayetano.see.dataacademico.model.projection.BimestreProjection;
import pe.com.cayetano.see.dataacademico.util.CustomPage;


@Getter
@Setter
@AllArgsConstructor
public class ResponseBasePage {
  private int codigo;
  private String mensaje;
  private Boolean exito;
  private CustomPage<BimestreProjection> data;
}