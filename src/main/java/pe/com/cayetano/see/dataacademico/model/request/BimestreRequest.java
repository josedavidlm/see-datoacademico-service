package pe.com.cayetano.see.dataacademico.model.request;


import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BimestreRequest {


  private Long codEmpresa;
  @JsonIgnore
  private Long codBimestre;
  private String desBimestre;
  private String desCorta;
  @JsonIgnore
  private LocalDateTime fecCreacion;
  @JsonIgnore
  private Boolean activo;
  @JsonIgnore
  private Long codEst;
  @JsonIgnore
  private Long codUsuario;
  @JsonIgnore
  private LocalDateTime fecAct;
  @JsonIgnore
  private  String nomTer;
  @JsonIgnore
  private String fecCreacionCadena;
  @JsonIgnore
  private Integer page;
  @JsonIgnore
  private Integer pageSize;
}
