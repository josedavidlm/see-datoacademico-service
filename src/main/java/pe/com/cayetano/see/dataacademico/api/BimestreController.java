package pe.com.cayetano.see.dataacademico.api;



import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import pe.com.cayetano.see.dataacademico.api.constant.Constantes;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBasePage;
import pe.com.cayetano.see.dataacademico.model.id.BimestreId;
import pe.com.cayetano.see.dataacademico.model.request.BimestreListRequest;
import pe.com.cayetano.see.dataacademico.model.request.BimestreRequest;
import pe.com.cayetano.see.dataacademico.service.BimestreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bimestre")
public class BimestreController {
    @Autowired
    private BimestreService bimestreService;



    @PostMapping(value ="save-bimestre", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "registrar Bimestre",
            description = "registrar Bimestre")
    public ResponseBase create(@RequestBody BimestreRequest bimestre)
    {
        bimestre.setCodUsuarioCreacion(1L);
        bimestre.setNomTerCreacion(Constantes.IP_TERMINAL);
        return bimestreService.create(bimestre);
    }

    @PutMapping(value ="/update-bimestre/{bimestre_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "actualización de Bimestre",
            description = "actualización de Bimestre")
    public ResponseBase update(@PathVariable("bimestre_Id") Long bimestreId,@RequestBody BimestreRequest bimestre)
    {
        bimestre.setCodUsuarioModificacion(1L);
        bimestre.setNomTerModificacion(Constantes.IP_TERMINAL);
        bimestre.setCodBimestre(bimestreId);
        return bimestreService.update(bimestre);
    }
    @DeleteMapping(value ="/delete-bimestre/{empresa_Id}/{bimestre_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "eliminación lógica de Bimestre",
            description = "eliminación lógica de Bimestre")
    public ResponseBase delete(@PathVariable("empresa_Id") Long empresaId,@PathVariable("bimestre_Id") Long bimestreId)
    {
        BimestreRequest bimestre = new BimestreRequest();
        bimestre.setCodEmpresa(empresaId);
        bimestre.setCodBimestre(bimestreId);
        bimestre.setCodUsuarioEliminacion(1L);
        bimestre.setNomTerEliminacion(Constantes.IP_TERMINAL);
        return bimestreService.deleteById(bimestre);
    }
    @GetMapping(value ="/get-bimestre/{empresa_Id}/{bimestre_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "obtener Bimestre Id",
            description = "obtener Bimestre Id")
    public ResponseBase buscarPorId(@PathVariable("empresa_Id") Long empresaId, @PathVariable("bimestre_Id") Long bimestreId)
    {

        return bimestreService.findById(new BimestreId(empresaId,bimestreId));
    }
    @GetMapping(value ="/list-bimestre", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista de Bimestre",
            description = "Lista de Bimestre")
    public ResponseBase listar()
    {
        return bimestreService.findAll();
    }


    @GetMapping(value = "to-list-bimestre", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista paginable Bimestre",
        description = "Lista paginable Bimestre")
    public ResponseBasePage listarBimestre (
        @RequestParam(name="empresa_Id", required = false) Long empresaId,
        @RequestParam(name="bimestre_Id", required = false) Long bimestreId,
        @RequestParam(name="descripcion", required = false) String descripcion,
        @RequestParam(name="desCorta", required = false) String desCorta,
        @RequestParam(name="fecCreacion", required = false) String fecCreacion,
        @RequestParam(name="codEstado", required = false) Long codEstado,
        @RequestParam(name="page", defaultValue = "1") Integer page,
        @RequestParam(name="page_size", defaultValue = "10") Integer pageSize
    ) {
        var request = new BimestreListRequest();
        request.setCodempresa(empresaId);
        request.setCodbimestre(bimestreId);
        request.setDesbimestre(descripcion);
        request.setDescorta(desCorta);
        request.setFeccreacioncadena(fecCreacion);
        request.setCodest(codEstado);
        request.setPage(page);
        request.setPageSize(pageSize);

        return bimestreService.listarBimestre(request);
    }

}
