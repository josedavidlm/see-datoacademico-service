package pe.com.cayetano.see.dataacademico.service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import pe.com.cayetano.see.dataacademico.config.ConfigMessageProperty;
import pe.com.cayetano.see.dataacademico.model.Response.BimestreResponse;
import pe.com.cayetano.see.dataacademico.model.Response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.Response.ResponseBasePage;
import pe.com.cayetano.see.dataacademico.model.entity.BimestreEntity;
import pe.com.cayetano.see.dataacademico.model.enums.Estado;
import pe.com.cayetano.see.dataacademico.model.enums.EstadoRegistro;
import pe.com.cayetano.see.dataacademico.model.enums.TypeMessage;
import pe.com.cayetano.see.dataacademico.model.id.BimestreId;
import pe.com.cayetano.see.dataacademico.model.projection.BimestreProjection;
import pe.com.cayetano.see.dataacademico.model.request.BimestreListRequest;
import pe.com.cayetano.see.dataacademico.model.request.BimestreRequest;
import pe.com.cayetano.see.dataacademico.repository.BimestreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.cayetano.see.dataacademico.service.BimestreService;
import pe.com.cayetano.see.dataacademico.util.ApiException;
import pe.com.cayetano.see.dataacademico.util.CustomPage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class BimestreServiceImpl implements BimestreService {
    @Autowired
    private BimestreRepository bimestreRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ConfigMessageProperty config;

    @Override
    public ResponseBase create(BimestreRequest bimestre) {
        var lstResponse = new ArrayList<BimestreResponse>();
        Optional<BimestreEntity> bimestreBd = bimestreRepository.findByDesBimestre(bimestre.getDesBimestre());


        if(bimestreBd.isPresent())
        {
            lstResponse.add(modelMapper.map(bimestreBd.get(), BimestreResponse.class));
            return new ResponseBase(400,
                config.getMessage("bimestre.existe"),
                false,
                lstResponse);
        }
        BimestreEntity entidad = modelMapper.map(bimestre, BimestreEntity.class);
        entidad.setCodEmpresa(bimestre.getCodEmpresa());
        entidad.setCodBimestre(bimestreRepository.obtenerBimestreId(bimestre.getCodEmpresa()));
        entidad.setFecCreacion(LocalDateTime.now());
        entidad.setActivo(true);
        entidad.setCodEst(EstadoRegistro.ACTIVO.getValue());
        entidad.setFecAct(LocalDateTime.now());
        BimestreEntity bimestreGuardado = bimestreRepository.save(entidad);

        var obj = modelMapper.map(bimestreGuardado, BimestreResponse.class);

        if(obj.getActivo()){
            obj.setDesActivo(Estado.ACTIVO.name());
        }else{
            obj.setDesActivo(Estado.INACTIVO.name());
        }

        if(obj.getCodEst() == EstadoRegistro.ACTIVO.getValue()){
            obj.setEstado(EstadoRegistro.ACTIVO.name());
        }else if(obj.getCodEst() == EstadoRegistro.ENUSO.getValue()){
            obj.setEstado(EstadoRegistro.ENUSO.name());
        }else if(obj.getCodEst() == EstadoRegistro.ANULADO.getValue()){
            obj.setEstado(EstadoRegistro.ANULADO.name());
        }

        lstResponse.add(obj);

        return new ResponseBase(201,
            config.getMessage("bimestre.creado") ,
            true,
            lstResponse);
    }

    @Override
    public ResponseBase findById(BimestreId bimestreId) {

        var lstResponse = new ArrayList<BimestreResponse>();
        Optional<BimestreEntity> empresaBd = bimestreRepository.findById(bimestreId);

        var obj = modelMapper.map(empresaBd, BimestreResponse.class);

        if(obj.getActivo()){
            obj.setDesActivo(Estado.ACTIVO.name());
        }else{
            obj.setDesActivo(Estado.INACTIVO.name());
        }

        if(obj.getCodEst() == EstadoRegistro.ACTIVO.getValue()){
            obj.setEstado(EstadoRegistro.ACTIVO.name());
        }else if(obj.getCodEst() == EstadoRegistro.ENUSO.getValue()){
            obj.setEstado(EstadoRegistro.ENUSO.name());
        }else if(obj.getCodEst() == EstadoRegistro.ANULADO.getValue()){
            obj.setEstado(EstadoRegistro.ANULADO.name());
        }

        lstResponse.add(obj);

        if(empresaBd.isPresent())
        {
            return new ResponseBase(200, config.getMessage("bimestre.encontrado"), true, lstResponse);
        }
        return new ResponseBase(404, config.getMessage("bimestre.nodata"), false, null);
    }

    @Override
    public ResponseBase deleteById(BimestreId bimestreId) {
        var lstResponse = new ArrayList<BimestreResponse>();
        Optional<BimestreEntity> empresaBd = bimestreRepository.findById(bimestreId);

        empresaBd.get().setActivo(false);
        empresaBd.get().setFecAct(LocalDateTime.now());

        BimestreEntity empresaDelete = bimestreRepository.save(empresaBd.get());

        var obj = modelMapper.map(empresaDelete, BimestreResponse.class);

        if(obj.getActivo()){
            obj.setDesActivo(Estado.ACTIVO.name());
        }else{
            obj.setDesActivo(Estado.INACTIVO.name());
        }

        if(obj.getCodEst() == EstadoRegistro.ACTIVO.getValue()){
            obj.setEstado(EstadoRegistro.ACTIVO.name());
        }else if(obj.getCodEst() == EstadoRegistro.ENUSO.getValue()){
            obj.setEstado(EstadoRegistro.ENUSO.name());
        }else if(obj.getCodEst() == EstadoRegistro.ANULADO.getValue()){
            obj.setEstado(EstadoRegistro.ANULADO.name());
        }

        lstResponse.add(obj);

        if(empresaBd.isPresent())
        {
            return new ResponseBase(200,  config.getMessage("bimestre.eliminado"), true, lstResponse);
        }
        return new ResponseBase(404, config.getMessage("bimestre.nodata"), false, null);
    }

    @Override
    public ResponseBase update(BimestreRequest bimestre) {
        Optional<BimestreEntity> bimestreBd = bimestreRepository.findById(new BimestreId(bimestre.getCodEmpresa(),bimestre.getCodBimestre()) );

        var lstResponse = new ArrayList<BimestreResponse>();

        if(bimestreBd.isEmpty())
        {
            return new ResponseBase(404,
                config.getMessage("bimestre.noregistro"),
                false,
                null);
        }
        // verificamos que el dni tenga 8 digitos y que el correo tenga @

        BimestreEntity entidad = modelMapper.map(bimestre, BimestreEntity.class);
        entidad.setFecAct(LocalDateTime.now());
        entidad.setActivo(bimestreBd.get().getActivo());

        BimestreEntity empresaGuardado = bimestreRepository.save(entidad);

        var obj = modelMapper.map(empresaGuardado, BimestreResponse.class);

        if(obj.getActivo()){
            obj.setDesActivo(Estado.ACTIVO.name());
        }else{
            obj.setDesActivo(Estado.INACTIVO.name());
        }

        if(obj.getCodEst() == EstadoRegistro.ACTIVO.getValue()){
            obj.setEstado(EstadoRegistro.ACTIVO.name());
        }else if(obj.getCodEst() == EstadoRegistro.ENUSO.getValue()){
            obj.setEstado(EstadoRegistro.ENUSO.name());
        }else if(obj.getCodEst() == EstadoRegistro.ANULADO.getValue()){
            obj.setEstado(EstadoRegistro.ANULADO.name());
        }

        lstResponse.add(obj);

        return new ResponseBase(201,
            config.getMessage("bimestre.actualizado"),
            true,
            lstResponse);
    }

    @Override
    public ResponseBase findAll() {
        List<BimestreEntity> lista = bimestreRepository.findAll();

        var lstResponse = new ArrayList<BimestreResponse>();

        lista.forEach(entidad -> {
            var obj = modelMapper.map(entidad, BimestreResponse.class);
            if (entidad.getCodEmpresa() != null) {

                if(entidad.getActivo()){
                    obj.setDesActivo(Estado.ACTIVO.name());
                }else{
                    obj.setDesActivo(Estado.INACTIVO.name());
                }

                if(obj.getCodEst() == EstadoRegistro.ACTIVO.getValue()){
                    obj.setEstado(EstadoRegistro.ACTIVO.name());
                }else if(obj.getCodEst() == EstadoRegistro.ENUSO.getValue()){
                    obj.setEstado(EstadoRegistro.ENUSO.name());
                }else if(obj.getCodEst() == EstadoRegistro.ANULADO.getValue()){
                    obj.setEstado(EstadoRegistro.ANULADO.name());
                }

            }
            lstResponse.add(obj);
        });

        if(lista.size()>0)
        {
            return new ResponseBase(200,  config.getMessage("bimestre.encontrado") , true, lstResponse);
        }
        return new ResponseBase(404, config.getMessage("bimestre.noregistro"), false, null);
    }

    @Override
    public ResponseBasePage listarBimestre(BimestreListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getPageSize());
        List<BimestreProjection> content = bimestreRepository.listarBimestre(request, pageable);
        long total = bimestreRepository.countlistarBimestre(request);
        var response = new CustomPage(new PageImpl(content, pageable, total));
        if (response.getData().isEmpty()) {
            return new ResponseBasePage(404, config.getMessage("bimestre.noregistro"), false, response);
        }
        return new ResponseBasePage(200,  config.getMessage("bimestre.listaencontrado") , true, response);

    }
}
