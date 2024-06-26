package pe.com.cayetano.see.dataacademico.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.com.cayetano.see.dataacademico.model.id.SeccionId;

import java.io.Serializable;


@Setter
@Getter
@Entity
@Table(name = "seccion", schema = "dev")
@IdClass(SeccionId.class)
public class SeccionEntity extends  AuditoriaEntidadEntity implements Serializable {

  @Id
  @Column(name = "codempresa")
  private Long codEmpresa;

  @Id
  @Column(name = "codseccion")
  private Long codSeccion;

  @Column(name = "desseccion")
  private String desSeccion;

  @Column(name = "descorta")
  private String desCorta;

  @Column(name = "codest")
  private Long codEst;

  @Column(name = "activo")
  private Boolean activo;




}
