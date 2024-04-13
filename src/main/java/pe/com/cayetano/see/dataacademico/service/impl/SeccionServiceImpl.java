package pe.com.cayetano.see.dataacademico.service.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.cayetano.see.dataacademico.api.constant.Constantes;
import pe.com.cayetano.see.dataacademico.config.ConfigMessageProperty;
import pe.com.cayetano.see.dataacademico.model.entity.SeccionEntity;
import pe.com.cayetano.see.dataacademico.model.enums.Estado;
import pe.com.cayetano.see.dataacademico.model.enums.EstadoRegistro;
import pe.com.cayetano.see.dataacademico.model.id.SeccionId;
import pe.com.cayetano.see.dataacademico.model.request.SeccionListRequest;
import pe.com.cayetano.see.dataacademico.model.request.SeccionRequest;
import pe.com.cayetano.see.dataacademico.model.response.SeccionResponse;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBasePage;
import pe.com.cayetano.see.dataacademico.repository.SeccionRepository;
import pe.com.cayetano.see.dataacademico.service.SeccionService;
import pe.com.cayetano.see.dataacademico.util.CustomPage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class SeccionServiceImpl implements SeccionService {
    @Autowired
    private SeccionRepository seccionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ConfigMessageProperty config;

    @Transactional
    @Override
    public ResponseBase create(SeccionRequest seccion) {
        var lstResponse = new ArrayList<SeccionResponse>();
        Optional<SeccionEntity> seccionBd = seccionRepository.findByDesSeccion(seccion.getDesSeccion());


        if(seccionBd.isPresent())
        {
            lstResponse.add(modelMapper.map(seccionBd.get(), SeccionResponse.class));
            return new ResponseBase(Constantes.API_STATUS_400,
                config.getMessage(Constantes.EXISTE),
                false,
                lstResponse);
        }
        SeccionEntity entidad = modelMapper.map(seccion, SeccionEntity.class);
        entidad.setCodEmpresa(seccion.getCodEmpresa());
        entidad.setCodSeccion(seccionRepository.obtenerSeccionId(seccion.getCodEmpresa()));
        entidad.setFecCreacion(LocalDateTime.now());
        entidad.setActivo(true);
        entidad.setCodEst(EstadoRegistro.REGISTRADO.getValue());
        SeccionEntity seccionGuardado = seccionRepository.save(entidad);

        var obj = modelMapper.map(seccionGuardado, SeccionResponse.class);

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
    public ResponseBase findById(SeccionId seccionId) {

        var lstResponse = new ArrayList<SeccionResponse>();
        Optional<SeccionEntity> seccionBD = seccionRepository.findById(seccionId);

        var obj = modelMapper.map(seccionBD, SeccionResponse.class);

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

        if(seccionBD.isPresent())
        {
            return new ResponseBase(Constantes.API_STATUS_200, config.getMessage(Constantes.ENCONTRADO), true, lstResponse);
        }
        return new ResponseBase(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, null);
    }

    @Transactional
    @Override
    public ResponseBase deleteById(SeccionRequest seccion) {
        var lstResponse = new ArrayList<SeccionResponse>();
        SeccionId seccionId = new SeccionId();
        seccionId.setCodEmpresa(seccion.getCodEmpresa());
        seccionId.setCodSeccion(seccion.getCodSeccion());

        Optional<SeccionEntity> seccionBD = seccionRepository.findById(seccionId);

        if (seccionBD.isPresent()){

            seccionBD.get().setActivo(false);
            seccionBD.get().setCodUsuarioEliminacion(seccion.getCodUsuarioEliminacion());
            seccionBD.get().setFecEliminacion(LocalDateTime.now());
            seccionBD.get().setNomTerEliminacion(seccion.getNomTerEliminacion());

            SeccionEntity seccionDelete = seccionRepository.save(seccionBD.get());
            var obj = modelMapper.map(seccionDelete, SeccionResponse.class);

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
    public ResponseBase update(SeccionRequest seccion) {
        Optional<SeccionEntity> seccionBd = seccionRepository.findById(new SeccionId(seccion.getCodEmpresa(),seccion.getCodSeccion()) );

        var lstResponse = new ArrayList<SeccionResponse>();

        if(seccionBd.isEmpty())
        {
            return new ResponseBase(Constantes.API_STATUS_404,
                config.getMessage(Constantes.NO_REGISTRO),
                false,
                null);
        }


        SeccionEntity entidad = modelMapper.map(seccionBd.get(), SeccionEntity.class);
        entidad.setDesSeccion(seccion.getDesSeccion());
        entidad.setDesCorta(seccion.getDesCorta());
        entidad.setFecModificacion(LocalDateTime.now());
        entidad.setCodUsuarioModificacion(seccion.getCodUsuarioModificacion());
        entidad.setNomTerModificacion(seccion.getNomTerModificacion());
        SeccionEntity seccionGuardado = seccionRepository.save(entidad);

        var obj = modelMapper.map(seccionGuardado, SeccionResponse.class);

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
        List<SeccionEntity> lista = seccionRepository.findAll();

        var lstResponse = new ArrayList<SeccionResponse>();

        lista.forEach(entidad -> {
            var obj = modelMapper.map(entidad, SeccionResponse.class);
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
    public ResponseBasePage listarSeccion(SeccionListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getPageSize());
        var response =  new CustomPage(seccionRepository.listarSeccion(request, pageable));
        if (response.getData().isEmpty()) {
            return new ResponseBasePage(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, response);
        }
        return new ResponseBasePage(Constantes.API_STATUS_200,  config.getMessage(Constantes.LISTA_ENCONTRADO) , true, response);

    }


}
