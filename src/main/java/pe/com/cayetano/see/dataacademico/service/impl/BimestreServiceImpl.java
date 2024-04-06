package pe.com.cayetano.see.dataacademico.service.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pe.com.cayetano.see.dataacademico.api.constant.Constantes;
import pe.com.cayetano.see.dataacademico.config.ConfigMessageProperty;
import pe.com.cayetano.see.dataacademico.model.response.BimestreResponse;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBasePage;
import pe.com.cayetano.see.dataacademico.model.entity.BimestreEntity;
import pe.com.cayetano.see.dataacademico.model.enums.Estado;
import pe.com.cayetano.see.dataacademico.model.enums.EstadoRegistro;
import pe.com.cayetano.see.dataacademico.model.id.BimestreId;
import pe.com.cayetano.see.dataacademico.model.request.BimestreListRequest;
import pe.com.cayetano.see.dataacademico.model.request.BimestreRequest;
import pe.com.cayetano.see.dataacademico.repository.BimestreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.cayetano.see.dataacademico.service.BimestreService;
import pe.com.cayetano.see.dataacademico.util.CustomPage;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Transactional
    @Override
    public ResponseBase create(BimestreRequest bimestre) {
        var lstResponse = new ArrayList<BimestreResponse>();
        Optional<BimestreEntity> bimestreBd = bimestreRepository.findByDesBimestre(bimestre.getDesBimestre());


        if(bimestreBd.isPresent())
        {
            lstResponse.add(modelMapper.map(bimestreBd.get(), BimestreResponse.class));
            return new ResponseBase(Constantes.API_STATUS_400,
                config.getMessage(Constantes.EXISTE),
                false,
                lstResponse);
        }
        BimestreEntity entidad = modelMapper.map(bimestre, BimestreEntity.class);
        entidad.setCodEmpresa(bimestre.getCodEmpresa());
        entidad.setCodBimestre(bimestreRepository.obtenerBimestreId(bimestre.getCodEmpresa()));
        entidad.setFecCreacion(LocalDateTime.now());
        entidad.setActivo(true);
        entidad.setCodEst(EstadoRegistro.REGISTRADO.getValue());
        BimestreEntity bimestreGuardado = bimestreRepository.save(entidad);

        var obj = modelMapper.map(bimestreGuardado, BimestreResponse.class);

        var estado = obj.getActivo();

        if(Boolean.TRUE.equals(estado)){
            obj.setDesActivo(Estado.ACTIVO.name());
        }else{
            obj.setDesActivo(Estado.INACTIVO.name());
        }

        if(obj.getCodEst().equals(EstadoRegistro.REGISTRADO.getValue())){
            obj.setEstado(EstadoRegistro.REGISTRADO.name());
        }else if(obj.getCodEst().equals(EstadoRegistro.ENUSO.getValue())){
            obj.setEstado(EstadoRegistro.ENUSO.name());
        }else if(obj.getCodEst().equals(EstadoRegistro.ANULADO.getValue())){
            obj.setEstado(EstadoRegistro.ANULADO.name());
        }

        lstResponse.add(obj);

        return new ResponseBase(Constantes.API_STATUS_200,
            config.getMessage(Constantes.CREADO) ,
            true,
            lstResponse);
    }

    @Override
    public ResponseBase findById(BimestreId bimestreId) {

        var lstResponse = new ArrayList<BimestreResponse>();
        Optional<BimestreEntity> bimestreBd = bimestreRepository.findById(bimestreId);

        var obj = modelMapper.map(bimestreBd, BimestreResponse.class);

        var estado = obj.getActivo();

        if( Boolean.TRUE.equals(estado)){
            obj.setDesActivo(Estado.ACTIVO.name());
        }else{
            obj.setDesActivo(Estado.INACTIVO.name());
        }

        if(obj.getCodEst().equals(EstadoRegistro.REGISTRADO.getValue())){
            obj.setEstado(EstadoRegistro.REGISTRADO.name());
        }else if(obj.getCodEst().equals(EstadoRegistro.ENUSO.getValue())){
            obj.setEstado(EstadoRegistro.ENUSO.name());
        }else if(obj.getCodEst().equals(EstadoRegistro.ANULADO.getValue())){
            obj.setEstado(EstadoRegistro.ANULADO.name());
        }

        lstResponse.add(obj);

        if(bimestreBd.isPresent())
        {
            return new ResponseBase(Constantes.API_STATUS_200, config.getMessage(Constantes.ENCONTRADO), true, lstResponse);
        }
        return new ResponseBase(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, null);
    }

    @Transactional
    @Override
    public ResponseBase deleteById(BimestreRequest bimestre) {
        var lstResponse = new ArrayList<BimestreResponse>();
        BimestreId bimestreId = new BimestreId();
        bimestreId.setCodEmpresa(bimestre.getCodEmpresa());
        bimestreId.setCodBimestre(bimestre.getCodBimestre());

        Optional<BimestreEntity> empresaBd = bimestreRepository.findById(bimestreId);

        if (empresaBd.isPresent()){

            empresaBd.get().setActivo(false);
            empresaBd.get().setCodUsuarioEliminacion(bimestre.getCodUsuarioEliminacion());
            empresaBd.get().setFecEliminacion(LocalDateTime.now());
            empresaBd.get().setNomTerEliminacion(bimestre.getNomTerEliminacion());

            BimestreEntity empresaDelete = bimestreRepository.save(empresaBd.get());
            var obj = modelMapper.map(empresaDelete, BimestreResponse.class);

            var estado = obj.getActivo();

            if( Boolean.TRUE.equals(estado)){
                obj.setDesActivo(Estado.ACTIVO.name());
            }else{
                obj.setDesActivo(Estado.INACTIVO.name());
            }

            if(obj.getCodEst().equals(EstadoRegistro.REGISTRADO.getValue())){
                obj.setEstado(EstadoRegistro.REGISTRADO.name());
            }else if(obj.getCodEst().equals(EstadoRegistro.ENUSO.getValue())){
                obj.setEstado(EstadoRegistro.ENUSO.name());
            }else if(obj.getCodEst().equals(EstadoRegistro.ANULADO.getValue())){
                obj.setEstado(EstadoRegistro.ANULADO.name());
            }
            lstResponse.add(obj);
            return new ResponseBase(Constantes.API_STATUS_200,  config.getMessage(Constantes.ELIMINADO), true, lstResponse);
        }
        return new ResponseBase(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, null);
    }

    @Transactional
    @Override
    public ResponseBase update(BimestreRequest bimestre) {
        Optional<BimestreEntity> bimestreBd = bimestreRepository.findById(new BimestreId(bimestre.getCodEmpresa(),bimestre.getCodBimestre()) );

        var lstResponse = new ArrayList<BimestreResponse>();

        if(bimestreBd.isEmpty())
        {
            return new ResponseBase(Constantes.API_STATUS_404,
                config.getMessage(Constantes.NO_REGISTRO),
                false,
                null);
        }


        BimestreEntity entidad = modelMapper.map(bimestreBd.get(), BimestreEntity.class);
        entidad.setDesBimestre(bimestre.getDesBimestre());
        entidad.setDesCorta(bimestre.getDesCorta());
        entidad.setFecModificacion(LocalDateTime.now());
        entidad.setCodUsuarioModificacion(bimestre.getCodUsuarioModificacion());
        entidad.setNomTerModificacion(bimestre.getNomTerModificacion());
        BimestreEntity empresaGuardado = bimestreRepository.save(entidad);

        var obj = modelMapper.map(empresaGuardado, BimestreResponse.class);

        var estado = obj.getActivo();

        if( Boolean.TRUE.equals(estado)){
            obj.setDesActivo(Estado.ACTIVO.name());
        }else{
            obj.setDesActivo(Estado.INACTIVO.name());
        }

        if(obj.getCodEst().equals(EstadoRegistro.REGISTRADO.getValue())){
            obj.setEstado(EstadoRegistro.REGISTRADO.name());
        }else if(obj.getCodEst().equals(EstadoRegistro.ENUSO.getValue())){
            obj.setEstado(EstadoRegistro.ENUSO.name());
        }else if(obj.getCodEst().equals(EstadoRegistro.ANULADO.getValue())){
            obj.setEstado(EstadoRegistro.ANULADO.name());
        }

        lstResponse.add(obj);

        return new ResponseBase(Constantes.API_STATUS_200,
            config.getMessage(Constantes.ACTUALIZADO),
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

                var estadoEntidad = entidad.getActivo();
                if(Boolean.TRUE.equals(estadoEntidad)){
                    obj.setDesActivo(Estado.ACTIVO.name());
                }else{
                    obj.setDesActivo(Estado.INACTIVO.name());
                }

                if(obj.getCodEst().equals(EstadoRegistro.REGISTRADO.getValue())){
                    obj.setEstado(EstadoRegistro.REGISTRADO.name());
                }else if(obj.getCodEst().equals(EstadoRegistro.ENUSO.getValue())){
                    obj.setEstado(EstadoRegistro.ENUSO.name());
                }else if(obj.getCodEst().equals(EstadoRegistro.ANULADO.getValue())){
                    obj.setEstado(EstadoRegistro.ANULADO.name());
                }

            }
            lstResponse.add(obj);
        });

        if(!lista.isEmpty())
        {
            return new ResponseBase(Constantes.API_STATUS_200,  config.getMessage(Constantes.ENCONTRADO) , true, lstResponse);
        }
        return new ResponseBase(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, null);
    }

    @Override
    public ResponseBasePage listarBimestre(BimestreListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getPageSize());
        var response =  new CustomPage(bimestreRepository.listarBimestre(request, pageable));
        if (response.getData().isEmpty()) {
            return new ResponseBasePage(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, response);
        }
        return new ResponseBasePage(Constantes.API_STATUS_200,  config.getMessage(Constantes.LISTA_ENCONTRADO) , true, response);

    }
}
