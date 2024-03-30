package pe.com.cayetano.see.dataacademico.util;

import lombok.Generated;
import org.springframework.http.HttpStatus;
import pe.com.cayetano.see.dataacademico.model.enums.TypeMessage;

import java.util.List;

public class ApiException extends RuntimeException {
  private final transient ErrorGenerico errorGenerico;

  public ApiException(TypeMessage tipoMensaje, String message, HttpStatus status) {
    super(message);
    this.errorGenerico = new ErrorGenerico(tipoMensaje, message, status);
  }

  public ApiException(TypeMessage tipoMensaje, String message, HttpStatus status, String logerror) {
    super(message);
    this.errorGenerico = new ErrorGenerico(tipoMensaje, message, status, logerror);
  }

  public ApiException(String message, HttpStatus status) {
    super(message);
    this.errorGenerico = new ErrorGenerico(TypeMessage.DANGER, message, status);
  }

  public ApiException(TypeMessage tipoMensaje, String message, HttpStatus status, List<ErrorCampo> errores) {
    super(message);
    this.errorGenerico = new ErrorGenerico(tipoMensaje, message, status, errores);
  }

  public ApiException(String message, HttpStatus status, List<ErrorCampo> errores) {
    super(message);
    this.errorGenerico = new ErrorGenerico(TypeMessage.DANGER, message, status, errores);
  }

  @Generated
  public ErrorGenerico getErrorGenerico() {
    return this.errorGenerico;
  }


}
