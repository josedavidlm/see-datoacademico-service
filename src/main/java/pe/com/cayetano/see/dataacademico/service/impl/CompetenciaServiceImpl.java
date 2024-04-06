package pe.com.cayetano.see.dataacademico.service.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.cayetano.see.dataacademico.api.constant.Constantes;
import pe.com.cayetano.see.dataacademico.config.ConfigMessageProperty;
import pe.com.cayetano.see.dataacademico.model.entity.CompetenciaEntity;
import pe.com.cayetano.see.dataacademico.model.enums.Estado;
import pe.com.cayetano.see.dataacademico.model.enums.EstadoRegistro;
import pe.com.cayetano.see.dataacademico.model.id.CompetenciaId;
import pe.com.cayetano.see.dataacademico.model.request.CompetenciaListRequest;
import pe.com.cayetano.see.dataacademico.model.request.CompetenciaRequest;
import pe.com.cayetano.see.dataacademico.model.response.CompetenciaResponse;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBasePage;
import pe.com.cayetano.see.dataacademico.repository.CompetenciaRepository;
import pe.com.cayetano.see.dataacademico.service.CompetenciaService;
import pe.com.cayetano.see.dataacademico.util.CustomPage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CompetenciaServiceImpl implements CompetenciaService {
    @Autowired
    private CompetenciaRepository competenciaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ConfigMessageProperty config;

    @Transactional
    @Override
    public ResponseBase create(CompetenciaRequest competencia) {
        var lstResponse = new ArrayList<CompetenciaResponse>();
        Optional<CompetenciaEntity> competenciaBd = competenciaRepository.findByDesCompetencia(competencia.getDesCompetencia());


        if(competenciaBd.isPresent())
        {
            lstResponse.add(modelMapper.map(competenciaBd.get(), CompetenciaResponse.class));
            return new ResponseBase(Constantes.API_STATUS_400,
                config.getMessage(Constantes.EXISTE),
                false,
                lstResponse);
        }
        CompetenciaEntity entidad = modelMapper.map(competencia, CompetenciaEntity.class);
        entidad.setCodEmpresa(competencia.getCodEmpresa());
        entidad.setCodCompetencia(competenciaRepository.obtenerCompetenciaId(competencia.getCodEmpresa()));
        entidad.setFecCreacion(LocalDateTime.now());
        entidad.setActivo(true);
        entidad.setCodEst(EstadoRegistro.REGISTRADO.getValue());
        CompetenciaEntity competenciaGuardado = competenciaRepository.save(entidad);

        var obj = modelMapper.map(competenciaGuardado, CompetenciaResponse.class);

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
    public ResponseBase findById(CompetenciaId competenciaId) {

        var lstResponse = new ArrayList<CompetenciaResponse>();
        Optional<CompetenciaEntity> competenciaBD = competenciaRepository.findById(competenciaId);

        var obj = modelMapper.map(competenciaBD, CompetenciaResponse.class);

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

        if(competenciaBD.isPresent())
        {
            return new ResponseBase(Constantes.API_STATUS_200, config.getMessage(Constantes.ENCONTRADO), true, lstResponse);
        }
        return new ResponseBase(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, null);
    }

    @Transactional
    @Override
    public ResponseBase deleteById(CompetenciaRequest competencia) {
        var lstResponse = new ArrayList<CompetenciaResponse>();
        CompetenciaId competenciaId = new CompetenciaId();
        competenciaId.setCodEmpresa(competencia.getCodEmpresa());
        competenciaId.setCodCompetencia(competencia.getCodCompetencia());

        Optional<CompetenciaEntity> competenciaBD = competenciaRepository.findById(competenciaId);

        if (competenciaBD.isPresent()){

            competenciaBD.get().setActivo(false);
            competenciaBD.get().setCodUsuarioEliminacion(competencia.getCodUsuarioEliminacion());
            competenciaBD.get().setFecEliminacion(LocalDateTime.now());
            competenciaBD.get().setNomTerEliminacion(competencia.getNomTerEliminacion());

            CompetenciaEntity competenciaDelete = competenciaRepository.save(competenciaBD.get());
            var obj = modelMapper.map(competenciaDelete, CompetenciaResponse.class);

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
    public ResponseBase update(CompetenciaRequest competencia) {
        Optional<CompetenciaEntity> competenciaBd = competenciaRepository.findById(new CompetenciaId(competencia.getCodEmpresa(),competencia.getCodCompetencia()) );

        var lstResponse = new ArrayList<CompetenciaResponse>();

        if(competenciaBd.isEmpty())
        {
            return new ResponseBase(Constantes.API_STATUS_404,
                config.getMessage(Constantes.NO_REGISTRO),
                false,
                null);
        }


        CompetenciaEntity entidad = modelMapper.map(competenciaBd.get(), CompetenciaEntity.class);
        entidad.setDesCompetencia(competencia.getDesCompetencia());
        entidad.setFecModificacion(LocalDateTime.now());
        entidad.setCodUsuarioModificacion(competencia.getCodUsuarioModificacion());
        entidad.setNomTerModificacion(competencia.getNomTerModificacion());
        CompetenciaEntity competenciaGuardado = competenciaRepository.save(entidad);

        var obj = modelMapper.map(competenciaGuardado, CompetenciaResponse.class);

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
        List<CompetenciaEntity> lista = competenciaRepository.findAll();

        var lstResponse = new ArrayList<CompetenciaResponse>();

        lista.forEach(entidad -> {
            var obj = modelMapper.map(entidad, CompetenciaResponse.class);
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
    public ResponseBasePage listarCompetencia(CompetenciaListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getPageSize());
        var response =  new CustomPage(competenciaRepository.listarCompetencia(request, pageable));
        if (response.getData().isEmpty()) {
            return new ResponseBasePage(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, response);
        }
        return new ResponseBasePage(Constantes.API_STATUS_200,  config.getMessage(Constantes.LISTA_ENCONTRADO) , true, response);

    }
}
