package pe.com.cayetano.see.dataacademico.api;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pe.com.cayetano.see.dataacademico.api.constant.Constantes;
import pe.com.cayetano.see.dataacademico.model.id.NivelId;
import pe.com.cayetano.see.dataacademico.model.request.NivelListRequest;
import pe.com.cayetano.see.dataacademico.model.request.NivelRequest;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBasePage;
import pe.com.cayetano.see.dataacademico.service.NivelService;

@RestController
@RequestMapping("nivel")
public class NivelController {
    @Autowired
    private NivelService nivelService;



    @PostMapping(value ="save-nivel", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "registrar nivel",
            description = "registrar nivel")
    public ResponseBase create(@RequestBody NivelRequest nivel)
    {
        nivel.setCodUsuarioCreacion(1L);
        nivel.setNomTerCreacion(Constantes.IP_TERMINAL);
        return nivelService.create(nivel);
    }

    @PutMapping(value ="/update-nivel/{nivel_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "actualización de nivel",
            description = "actualización de nivel")
    public ResponseBase update(@PathVariable("nivel_Id") Long nivelId,@RequestBody NivelRequest nivel)
    {
        nivel.setCodUsuarioModificacion(1L);
        nivel.setNomTerModificacion(Constantes.IP_TERMINAL);
        nivel.setCodNivel(nivelId);
        return nivelService.update(nivel);
    }
    @DeleteMapping(value ="/delete-nivel/{empresa_Id}/{nivel_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "eliminación lógica de nivel",
            description = "eliminación lógica de nivel")
    public ResponseBase delete(@PathVariable("empresa_Id") Long empresaId,@PathVariable("nivel_Id") Long nivelId)
    {
        NivelRequest nivel = new NivelRequest();
        nivel.setCodEmpresa(empresaId);
        nivel.setCodNivel(nivelId);
        nivel.setCodUsuarioEliminacion(1L);
        nivel.setNomTerEliminacion(Constantes.IP_TERMINAL);
        return nivelService.deleteById(nivel);
    }
    @GetMapping(value ="/get-nivel/{empresa_Id}/{nivel_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "obtener Nivel Id",
            description = "obtener Nivel Id")
    public ResponseBase buscarPorId(@PathVariable("empresa_Id") Long empresaId, @PathVariable("nivel_Id") Long nivelId)
    {

        return nivelService.findById(new NivelId(empresaId,nivelId));
    }
    @GetMapping(value ="/list-nivel", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista de Nivel",
            description = "Lista de Nivel")
    public ResponseBase listar()
    {
        return nivelService.findAll();
    }


    @GetMapping(value = "to-list--nivel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar Nivel",
        description = "Listar Nivel")
    public ResponseBasePage listarNivel (
        @RequestParam(name="empresa_Id", required = false) Long empresaId,
        @RequestParam(name="nivel_Id", required = false) Long bimestreId,
        @RequestParam(name="descripcion", required = false) String descripcion,
        @RequestParam(name="desCorta", required = false) String desCorta,
        @RequestParam(name="fecCreacion", required = false) String fecCreacion,
        @RequestParam(name="codEstado", required = false) Long codEstado,
        @RequestParam(name="page", defaultValue = "1") Integer page,
        @RequestParam(name="page_size", defaultValue = "10") Integer pageSize
    ) {
        var request = new NivelListRequest();
        request.setCodempresa(empresaId);
        request.setCodnivel(bimestreId);
        request.setDesnivel(descripcion);
        request.setDescorta(desCorta);
        request.setFeccreacioncadena(fecCreacion);
        request.setCodest(codEstado);
        request.setPage(page);
        request.setPageSize(pageSize);

        return nivelService.listarNivel(request);
    }

}
