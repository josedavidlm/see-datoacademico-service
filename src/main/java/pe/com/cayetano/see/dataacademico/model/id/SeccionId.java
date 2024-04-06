package pe.com.cayetano.see.dataacademico.model.id;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeccionId {
  private Long codEmpresa;
  private Long codSeccion;
}
