package pe.com.cayetano.see.dataacademico.service.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.cayetano.see.dataacademico.api.constant.Constantes;
import pe.com.cayetano.see.dataacademico.config.ConfigMessageProperty;
import pe.com.cayetano.see.dataacademico.model.entity.NivelEntity;
import pe.com.cayetano.see.dataacademico.model.enums.Estado;
import pe.com.cayetano.see.dataacademico.model.enums.EstadoRegistro;
import pe.com.cayetano.see.dataacademico.model.id.NivelId;
import pe.com.cayetano.see.dataacademico.model.request.NivelListRequest;
import pe.com.cayetano.see.dataacademico.model.request.NivelRequest;
import pe.com.cayetano.see.dataacademico.model.response.NivelResponse;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBasePage;
import pe.com.cayetano.see.dataacademico.repository.NivelRepository;
import pe.com.cayetano.see.dataacademico.service.NivelService;
import pe.com.cayetano.see.dataacademico.util.CustomPage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class NivelServiceImpl implements NivelService {
    @Autowired
    private NivelRepository nivelRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ConfigMessageProperty config;

    @Transactional
    @Override
    public ResponseBase create(NivelRequest nivel) {
        var lstResponse = new ArrayList<NivelResponse>();
        Optional<NivelEntity> nivelBd = nivelRepository.findByDesNivel(nivel.getDesNivel());


        if(nivelBd.isPresent())
        {
            lstResponse.add(modelMapper.map(nivelBd.get(), NivelResponse.class));
            return new ResponseBase(Constantes.API_STATUS_400,
                config.getMessage(Constantes.EXISTE),
                false,
                lstResponse);
        }
        NivelEntity entidad = modelMapper.map(nivel, NivelEntity.class);
        entidad.setCodEmpresa(nivel.getCodEmpresa());
        entidad.setCodNivel(nivelRepository.obtenerNivelId(nivel.getCodEmpresa()));
        entidad.setFecCreacion(LocalDateTime.now());
        entidad.setActivo(true);
        entidad.setCodEst(EstadoRegistro.REGISTRADO.getValue());
        NivelEntity nivelGuardado = nivelRepository.save(entidad);

        var obj = modelMapper.map(nivelGuardado, NivelResponse.class);

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
    public ResponseBase findById(NivelId nivelId) {

        var lstResponse = new ArrayList<NivelResponse>();
        Optional<NivelEntity> nivelBD = nivelRepository.findById(nivelId);

        var obj = modelMapper.map(nivelBD, NivelResponse.class);

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

        if(nivelBD.isPresent())
        {
            return new ResponseBase(Constantes.API_STATUS_200, config.getMessage(Constantes.ENCONTRADO), true, lstResponse);
        }
        return new ResponseBase(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, null);
    }

    @Transactional
    @Override
    public ResponseBase deleteById(NivelRequest nivel) {
        var lstResponse = new ArrayList<NivelResponse>();
        NivelId nivelId = new NivelId();
        nivelId.setCodEmpresa(nivel.getCodEmpresa());
        nivelId.setCodNivel(nivel.getCodNivel());

        Optional<NivelEntity> nivelBD = nivelRepository.findById(nivelId);

        if (nivelBD.isPresent()){

            nivelBD.get().setActivo(false);
            nivelBD.get().setCodUsuarioEliminacion(nivel.getCodUsuarioEliminacion());
            nivelBD.get().setFecEliminacion(LocalDateTime.now());
            nivelBD.get().setNomTerEliminacion(nivel.getNomTerEliminacion());

            NivelEntity nivelDelete = nivelRepository.save(nivelBD.get());
            var obj = modelMapper.map(nivelDelete, NivelResponse.class);

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
    public ResponseBase update(NivelRequest nivel) {
        Optional<NivelEntity> nivelBd = nivelRepository.findById(new NivelId(nivel.getCodEmpresa(),nivel.getCodNivel()) );

        var lstResponse = new ArrayList<NivelResponse>();

        if(nivelBd.isEmpty())
        {
            return new ResponseBase(Constantes.API_STATUS_404,
                config.getMessage(Constantes.NO_REGISTRO),
                false,
                null);
        }


        NivelEntity entidad = modelMapper.map(nivelBd.get(), NivelEntity.class);
        entidad.setDesNivel(nivel.getDesNivel());
        entidad.setDesCorta(nivel.getDesCorta());
        entidad.setFecModificacion(LocalDateTime.now());
        entidad.setCodUsuarioModificacion(nivel.getCodUsuarioModificacion());
        entidad.setNomTerModificacion(nivel.getNomTerModificacion());
        NivelEntity nivelGuardado = nivelRepository.save(entidad);

        var obj = modelMapper.map(nivelGuardado, NivelResponse.class);

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
        List<NivelEntity> lista = nivelRepository.findAll();

        var lstResponse = new ArrayList<NivelResponse>();

        lista.forEach(entidad -> {
            var obj = modelMapper.map(entidad, NivelResponse.class);
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
    public ResponseBasePage listarNivel(NivelListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getPageSize());
        var response =  new CustomPage(nivelRepository.listarNivel(request, pageable));
        if (response.getData().isEmpty()) {
            return new ResponseBasePage(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, response.getData());
        }
        return new ResponseBasePage(Constantes.API_STATUS_200,  config.getMessage(Constantes.LISTA_ENCONTRADO) , true, response.getData());

    }
}
