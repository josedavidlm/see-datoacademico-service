package pe.com.cayetano.see.dataacademico.util;

import lombok.Generated;

import java.io.Serializable;

public class ErrorCampo implements Serializable {
  private String campo;
  private String mensaje;

  @Generated
  public static ErrorCampoBuilder builder() {
    return new ErrorCampoBuilder();
  }

  @Generated
  public ErrorCampo(final String campo, final String mensaje) {
    this.campo = campo;
    this.mensaje = mensaje;
  }

  @Generated
  public void setCampo(final String campo) {
    this.campo = campo;
  }

  @Generated
  public void setMensaje(final String mensaje) {
    this.mensaje = mensaje;
  }

  @Generated
  public String getCampo() {
    return this.campo;
  }

  @Generated
  public String getMensaje() {
    return this.mensaje;
  }

  @Generated
  public ErrorCampo() {
  }

  @Generated
  public static class ErrorCampoBuilder {
    @Generated
    private String campo;
    @Generated
    private String mensaje;

    @Generated
    ErrorCampoBuilder() {
    }

    @Generated
    public ErrorCampoBuilder campo(final String campo) {
      this.campo = campo;
      return this;
    }

    @Generated
    public ErrorCampoBuilder mensaje(final String mensaje) {
      this.mensaje = mensaje;
      return this;
    }

    @Generated
    public ErrorCampo build() {
      return new ErrorCampo(this.campo, this.mensaje);
    }

    @Generated
    public String toString() {
      return "ErrorCampo.ErrorCampoBuilder(campo=" + this.campo + ", mensaje=" + this.mensaje + ")";
    }
  }
}
