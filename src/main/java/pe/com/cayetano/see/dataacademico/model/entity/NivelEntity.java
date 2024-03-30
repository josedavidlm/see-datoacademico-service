package pe.com.cayetano.see.dataacademico.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.com.cayetano.see.dataacademico.model.id.NivelId;

import java.io.Serializable;



@Setter
@Getter
@Entity
@Table(name = "nivel", schema = "dev")
@IdClass(NivelId.class)
public class NivelEntity extends  AuditoriaEntidadEntity implements Serializable {

  @Id
  @Column(name = "codempresa")
  private Long codEmpresa;

  @Id
  @Column(name = "codnivel")
  private Long codNivel;

  @Column(name = "desnivel")
  private String desNivel;

  @Column(name = "descorta")
  private String desCorta;

  @Column(name = "codest")
  private Long codEst;

  @Column(name = "activo")
  private Boolean activo;




}
