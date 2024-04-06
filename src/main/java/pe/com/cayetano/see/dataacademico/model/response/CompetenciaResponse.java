package pe.com.cayetano.see.dataacademico.model.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CompetenciaResponse {

  private Long codEmpresa;
  private Long codCompetencia;
  private String desCompetencia;
  private Boolean activo;
  private String desActivo;
  private Long codEst;
  private String estado;
  private LocalDateTime fecCreacion;
  private Long codUsuarioCreacion;
  private  String nomTerCreacion;
  private LocalDateTime fecModificacion;
  private Long codUsuarioModificacion;
  private  String nomTerModificacion;
  private LocalDateTime fecEliminacion;
  private Long codUsuarioEliminacion;
  private  String nomTerEliminacion;

}
