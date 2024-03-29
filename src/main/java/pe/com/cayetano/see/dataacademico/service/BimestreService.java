package pe.com.cayetano.see.dataacademico.service;

import pe.com.cayetano.see.dataacademico.model.Response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.Response.ResponseBasePage;
import pe.com.cayetano.see.dataacademico.model.id.BimestreId;
import pe.com.cayetano.see.dataacademico.model.projection.BimestreProjection;
import pe.com.cayetano.see.dataacademico.model.request.BimestreListRequest;
import pe.com.cayetano.see.dataacademico.model.request.BimestreRequest;
import pe.com.cayetano.see.dataacademico.util.CustomPage;

public interface BimestreService {

  ResponseBase create(BimestreRequest bimestre);
  ResponseBase findById(BimestreId bimestreId);
  ResponseBase deleteById(BimestreId bimestreId);
  ResponseBase update(BimestreRequest bimestre);
  ResponseBase findAll();
  ResponseBasePage listarBimestre(BimestreListRequest request);
}
