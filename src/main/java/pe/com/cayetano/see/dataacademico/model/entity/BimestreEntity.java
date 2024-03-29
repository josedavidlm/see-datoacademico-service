package pe.com.cayetano.see.dataacademico.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.com.cayetano.see.dataacademico.model.id.BimestreId;

import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name = "bimestre", schema = "dev")
@IdClass(BimestreId.class)
public class BimestreEntity extends  AuditoriaEntidadEntity {

  @Id
  @Column(name = "codempresa")
  private Long codEmpresa;

  @Id
  @Column(name = "codbimestre")
  private Long codBimestre;

  @Column(name = "desbimestre")
  private String desBimestre;

  @Column(name = "descorta")
  private String desCorta;

  @Column(name = "feccreacion")
  private LocalDateTime fecCreacion;

  @Column(name = "activo")
  private Boolean activo;

  @Column(name = "codest")
  private Long codEst;


}
