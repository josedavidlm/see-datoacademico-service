package pe.com.cayetano.see.dataacademico.service;


import pe.com.cayetano.see.dataacademico.model.id.NivelId;
import pe.com.cayetano.see.dataacademico.model.request.NivelListRequest;
import pe.com.cayetano.see.dataacademico.model.request.NivelRequest;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBasePage;

public interface NivelService {

  ResponseBase create(NivelRequest nivel);
  ResponseBase findById(NivelId nivelId);
  ResponseBase deleteById(NivelRequest nivel);
  ResponseBase update(NivelRequest nivel);
  ResponseBase findAll();
  ResponseBasePage listarNivel(NivelListRequest request);
}
