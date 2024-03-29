package pe.com.cayetano.see.dataacademico.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Generated;

import java.time.LocalDateTime;

@MappedSuperclass
public class AuditoriaEntidadEntity {


  @Column(
      name = "codusuario"
  )
  private Long codUsuario;

  @Column(
      name = "fecact"
  )
  private LocalDateTime fecAct;

  @Column(
      name = "nomter"
  )
  private  String nomTer;


  @Generated
  public Long getCodUsuario() {
    return this.codUsuario;
  }

  @Generated
  public void setCodUsuario(final Long codUsuario) {
    this.codUsuario = codUsuario;
  }

  @Generated
  public LocalDateTime getFecAct() {
    return this.fecAct;
  }

  @Generated
  public void setFecAct(final LocalDateTime fecAct) {
    this.fecAct = fecAct;
  }

  @Generated
  public String getNomTer() {
    return this.nomTer;
  }

  @Generated
  public void setNomTer(final String nomTer) {
    this.nomTer = nomTer;
  }

  @Generated
  public AuditoriaEntidadEntity(final Long codUsuario, LocalDateTime fecAct,final String nomTer) {
    this.codUsuario = codUsuario;
    this.fecAct = fecAct;
    this.nomTer = nomTer;
  }
  @Generated
  public AuditoriaEntidadEntity() {
  }

}
