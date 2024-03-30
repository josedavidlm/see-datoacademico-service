package pe.com.cayetano.see.dataacademico.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBase {
  private Integer codigo;
  private String mensaje;
  private Boolean exito;
  private Object data;


}