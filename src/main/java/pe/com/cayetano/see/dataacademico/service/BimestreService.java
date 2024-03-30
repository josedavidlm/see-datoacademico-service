package pe.com.cayetano.see.dataacademico.service;

import pe.com.cayetano.see.dataacademico.model.response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBasePage;
import pe.com.cayetano.see.dataacademico.model.id.BimestreId;
import pe.com.cayetano.see.dataacademico.model.request.BimestreListRequest;
import pe.com.cayetano.see.dataacademico.model.request.BimestreRequest;

public interface BimestreService {

  ResponseBase create(BimestreRequest bimestre);
  ResponseBase findById(BimestreId bimestreId);
  ResponseBase deleteById(BimestreRequest bimestre);
  ResponseBase update(BimestreRequest bimestre);
  ResponseBase findAll();
  ResponseBasePage listarBimestre(BimestreListRequest request);
}
