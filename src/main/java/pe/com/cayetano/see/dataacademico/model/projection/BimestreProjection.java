package pe.com.cayetano.see.dataacademico.model.projection;


import java.util.Date;

public interface BimestreProjection {
   Long getCodEmpresa();
   Long getCodBimestre();
   String getDesBimestre();
   String getDesCorta();
   Long getCodEst();
   String getEstado();
   Boolean getActivo();
   String getDesActivo();
   Date getFecCreacion();
   Long getCodUsuarioCreacion();
   String getNomTerCreacion();
   Date getFecModificacion();
   Long getCodUsuarioModificacion();
   String getNomTerModificacion();
   Date getFecEliminacion();
   Long getCodUsuarioEliminacion();
   String getNomTerEliminacion();

}
