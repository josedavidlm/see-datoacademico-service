package pe.com.cayetano.see.dataacademico.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.com.cayetano.see.dataacademico.model.id.CompetenciaId;

import java.io.Serializable;


@Setter
@Getter
@Entity
@Table(name = "Competencia", schema = "dev")
@IdClass(CompetenciaId.class)
public class CompetenciaEntity extends  AuditoriaEntidadEntity implements Serializable {

  @Id
  @Column(name = "codempresa")
  private Long codEmpresa;

  @Id
  @Column(name = "codcompetencia")
  private Long codCompetencia;

  @Column(name = "descompetencia")
  private String desCompetencia;

  @Column(name = "descorta")
  private String desCorta;

  @Column(name = "codest")
  private Long codEst;

  @Column(name = "activo")
  private Boolean activo;




}
