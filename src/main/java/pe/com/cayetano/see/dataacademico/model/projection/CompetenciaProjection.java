package pe.com.cayetano.see.dataacademico.model.projection;


import java.util.Date;

public interface CompetenciaProjection {
   Long getCodEmpresa();
   Long getCodCompetencia();
   String getDesCompetencia();
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
