package pe.com.cayetano.see.dataacademico.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import pe.com.cayetano.see.dataacademico.model.enums.TypeMessage;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ErrorGenerico implements Serializable {

  private static final long serialVersionUID = 2405172041950251807L;
  private Integer tipMen;
  private String mensaje;
  private String codigo;
  private List<ErrorCampo> errores = Collections.emptyList();
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String logerror;

  public ErrorGenerico(TypeMessage tipMen, String message, HttpStatus status) {
    this.tipMen = tipMen.getValue();
    this.mensaje = message;
    this.codigo = String.valueOf(status.value());
  }

  public ErrorGenerico(TypeMessage tipMen, String message, HttpStatus status, String logerror) {
    this.tipMen = tipMen.getValue();
    this.mensaje = message;
    this.codigo = String.valueOf(status.value());
    this.logerror = logerror;
  }

  public ErrorGenerico(TypeMessage tipMen, String message, HttpStatus status, List<ErrorCampo> errores) {
    this.tipMen = tipMen.getValue();
    this.mensaje = message;
    this.codigo = String.valueOf(status.value());
    this.errores = errores;
  }

  @Generated
  public Integer getTipMen() {
    return this.tipMen;
  }

  @Generated
  public String getMensaje() {
    return this.mensaje;
  }

  @Generated
  public String getCodigo() {
    return this.codigo;
  }

  @Generated
  public List<ErrorCampo> getErrores() {
    return this.errores;
  }

  @Generated
  public String getLogerror() {
    return this.logerror;
  }

  @Generated
  public void setTipMen(final Integer tipMen) {
    this.tipMen = tipMen;
  }

  @Generated
  public void setMensaje(final String mensaje) {
    this.mensaje = mensaje;
  }

  @Generated
  public void setCodigo(final String codigo) {
    this.codigo = codigo;
  }

  @Generated
  public void setErrores(final List<ErrorCampo> errores) {
    this.errores = errores;
  }

  @Generated
  public void setLogerror(final String logerror) {
    this.logerror = logerror;
  }
}
