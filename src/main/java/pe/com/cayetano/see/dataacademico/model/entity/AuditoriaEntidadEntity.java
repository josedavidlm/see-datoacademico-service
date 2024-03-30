package pe.com.cayetano.see.dataacademico.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuditoriaEntidadEntity implements Serializable {

  static final long serialVersionUID = -3281394734739387884L;
  @Column(
          name = "codusuariocreacion",
          updatable = false
  )
  @CreatedBy
  private Long codUsuarioCreacion;

  @Column(
      name = "feccreacion",
          updatable = false
  )
  @CreatedDate
  private LocalDateTime fecCreacion;

  @Column(
      name = "nomtercreacion",
          updatable = false
  )
  @CreatedBy
  private  String nomTerCreacion;

  @Column(
          name = "codusuariomodificacion"
  )
  @CreatedBy
  private Long codUsuarioModificacion;

  @Column(
          name = "fecmodificacion"
  )
  @CreatedDate
  private LocalDateTime fecModificacion;

  @Column(
          name = "nomtermodificacion"
  )
  @CreatedBy
  private  String nomTerModificacion;

  @Column(
          name = "codusuarioeliminacion"
  )
  @CreatedBy
  private Long codUsuarioEliminacion;

  @Column(
          name = "feceliminacion"
  )
  @CreatedDate
  private LocalDateTime fecEliminacion;

  @Column(
          name = "nomtereliminacion"
  )
  @CreatedBy
  private  String nomTerEliminacion;


}
