package pe.com.cayetano.see.dataacademico.model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResponseBase {
  private int codigo;
  private String mensaje;
  private Boolean exito;
  private List<BimestreResponse> data;
}