package pe.com.cayetano.see.dataacademico.service;



import pe.com.cayetano.see.dataacademico.model.id.CompetenciaId;
import pe.com.cayetano.see.dataacademico.model.request.CompetenciaListRequest;
import pe.com.cayetano.see.dataacademico.model.request.CompetenciaRequest;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBasePage;

public interface CompetenciaService {

  ResponseBase create(CompetenciaRequest competencia);
  ResponseBase findById(CompetenciaId competenciaId);
  ResponseBase deleteById(CompetenciaRequest competencia);
  ResponseBase update(CompetenciaRequest competencia);
  ResponseBase findAll();
  ResponseBasePage listarCompetencia(CompetenciaListRequest request);
}
