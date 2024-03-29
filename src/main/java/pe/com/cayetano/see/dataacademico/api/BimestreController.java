package pe.com.cayetano.see.dataacademico.api;



import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import pe.com.cayetano.see.dataacademico.model.Response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.Response.ResponseBasePage;
import pe.com.cayetano.see.dataacademico.model.id.BimestreId;
import pe.com.cayetano.see.dataacademico.model.projection.BimestreProjection;
import pe.com.cayetano.see.dataacademico.model.request.BimestreListRequest;
import pe.com.cayetano.see.dataacademico.model.request.BimestreRequest;
import pe.com.cayetano.see.dataacademico.service.BimestreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.com.cayetano.see.dataacademico.util.CustomPage;

@RestController
@RequestMapping("bimestre")
public class BimestreController {
    @Autowired
    private BimestreService bimestreService;

    @PostMapping(value ="save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase create(@RequestBody BimestreRequest bimestre)
    {
        bimestre.setCodUsuario(1L);
        bimestre.setNomTer("192.168.1.1");
        return bimestreService.create(bimestre);
    }

    @PutMapping(value ="/update/{empresa_Id}/{bimestre_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase update(@PathVariable("empresa_Id") Long empresaId,@PathVariable("bimestre_Id") Long bimestreId,@RequestBody BimestreRequest bimestre)
    {
        bimestre.setCodUsuario(1L);
        bimestre.setNomTer("192.168.1.1");
        bimestre.setCodEmpresa(empresaId);
        bimestre.setCodBimestre(bimestreId);
        return bimestreService.update(bimestre);
    }
    @DeleteMapping(value ="/delete/{empresa_Id}/{bimestre_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase delete(@PathVariable("empresa_Id") Long empresaId,@PathVariable("bimestre_Id") Long bimestreId)
    {
        return bimestreService.deleteById(new BimestreId(empresaId,bimestreId));
    }
    @GetMapping(value ="/get/{empresa_Id}/{bimestre_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase buscarPorId(@PathVariable("empresa_Id") Long empresaId, @PathVariable("bimestre_Id") Long bimestreId)
    {

        return bimestreService.findById(new BimestreId(empresaId,bimestreId));
    }
    @GetMapping(value ="/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase listar()
    {
        return bimestreService.findAll();
    }


    @GetMapping(value = "listar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar Bimestre",
        description = "Listar Bimestre")
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
