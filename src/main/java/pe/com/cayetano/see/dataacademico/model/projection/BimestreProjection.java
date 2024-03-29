package pe.com.cayetano.see.dataacademico.model.projection;


import java.util.Date;

public interface BimestreProjection {
   Long getCodEmpresa();
   Long getCodBimestre();
   String getDesBimestre();
   String getDesCorta();
   Date getFecCreacion();
   Long getCodEst();
   String getEstado();
   Boolean getActivo();
   String getDesActivo();
   Long getCodUsuario();
   Date getFecAct();
   String getNomTer();
}
