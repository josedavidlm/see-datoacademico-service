package pe.com.cayetano.see.dataacademico.service;



import pe.com.cayetano.see.dataacademico.model.id.SeccionId;
import pe.com.cayetano.see.dataacademico.model.request.SeccionListRequest;
import pe.com.cayetano.see.dataacademico.model.request.SeccionRequest;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBasePage;

public interface SeccionService {

  ResponseBase create(SeccionRequest seccion);
  ResponseBase findById(SeccionId seccionId);
  ResponseBase deleteById(SeccionRequest seccion);
  ResponseBase update(SeccionRequest seccion);
  ResponseBase findAll();
  ResponseBasePage listarSeccion(SeccionListRequest request);
}
