package pe.com.cayetano.see.dataacademico.api;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pe.com.cayetano.see.dataacademico.api.constant.Constantes;
import pe.com.cayetano.see.dataacademico.model.id.CompetenciaId;
import pe.com.cayetano.see.dataacademico.model.request.CompetenciaListRequest;
import pe.com.cayetano.see.dataacademico.model.request.CompetenciaRequest;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBasePage;
import pe.com.cayetano.see.dataacademico.service.CompetenciaService;


@RestController
@RequestMapping("competencia")
public class CompetenciaController {
    @Autowired
    private CompetenciaService competenciaService;



    @PostMapping(value ="save-competencia", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "registrar competencia",
            description = "registrar competencia")
    public ResponseBase create(@RequestBody CompetenciaRequest competencia)
    {
        competencia.setCodUsuarioCreacion(1L);
        competencia.setNomTerCreacion(Constantes.IP_TERMINAL);
        return competenciaService.create(competencia);
    }

    @PutMapping(value ="/update-competencia/{competencia_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "actualización de competencia",
            description = "actualización de competencia")
    public ResponseBase update(@PathVariable("competencia_Id") Long competenciaId,@RequestBody CompetenciaRequest competencia)
    {
        competencia.setCodUsuarioModificacion(1L);
        competencia.setNomTerModificacion(Constantes.IP_TERMINAL);
        competencia.setCodCompetencia(competenciaId);
        return competenciaService.update(competencia);
    }
    @DeleteMapping(value ="/delete-competencia/{empresa_Id}/{competencia_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "eliminación lógica de competencia",
            description = "eliminación lógica de competencia")
    public ResponseBase delete(@PathVariable("empresa_Id") Long empresaId,@PathVariable("competencia_Id") Long competenciaId)
    {
        CompetenciaRequest competencia = new CompetenciaRequest();
        competencia.setCodEmpresa(empresaId);
        competencia.setCodCompetencia(competenciaId);
        competencia.setCodUsuarioEliminacion(1L);
        competencia.setNomTerEliminacion(Constantes.IP_TERMINAL);
        return competenciaService.deleteById(competencia);
    }
    @GetMapping(value ="/get-competencia/{empresa_Id}/{competencia_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "obtener competencia Id",
            description = "obtener competencia Id")
    public ResponseBase buscarPorId(@PathVariable("empresa_Id") Long empresaId, @PathVariable("competencia_Id") Long competenciaId)
    {

        return competenciaService.findById(new CompetenciaId(empresaId,competenciaId));
    }
    @GetMapping(value ="/list-competencia", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista de competencia",
            description = "Lista de competencia")
    public ResponseBase listar()
    {
        return competenciaService.findAll();
    }


    @GetMapping(value = "to-list--competencia", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar competencia",
        description = "Listar competencia")
    public ResponseBasePage listarcompetencia (
        @RequestParam(name="empresa_Id", required = false) Long empresaId,
        @RequestParam(name="competencia_Id", required = false) Long bimestreId,
        @RequestParam(name="descripcion", required = false) String descripcion,
        @RequestParam(name="desCorta", required = false) String desCorta,
        @RequestParam(name="fecCreacion", required = false) String fecCreacion,
        @RequestParam(name="codEstado", required = false) Long codEstado,
        @RequestParam(name="page", defaultValue = "1") Integer page,
        @RequestParam(name="page_size", defaultValue = "10") Integer pageSize
    ) {
        var request = new CompetenciaListRequest();
        request.setCodempresa(empresaId);
        request.setCodcompetencia(bimestreId);
        request.setDescompetencia(descripcion);
        request.setDescorta(desCorta);
        request.setFeccreacioncadena(fecCreacion);
        request.setCodest(codEstado);
        request.setPage(page);
        request.setPageSize(pageSize);

        return competenciaService.listarCompetencia(request);
    }

}
