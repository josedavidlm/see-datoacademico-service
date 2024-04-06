package pe.com.cayetano.see.dataacademico.api;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pe.com.cayetano.see.dataacademico.api.constant.Constantes;
import pe.com.cayetano.see.dataacademico.model.id.SeccionId;
import pe.com.cayetano.see.dataacademico.model.request.SeccionListRequest;
import pe.com.cayetano.see.dataacademico.model.request.SeccionRequest;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBase;
import pe.com.cayetano.see.dataacademico.model.response.ResponseBasePage;
import pe.com.cayetano.see.dataacademico.service.SeccionService;


@RestController
@RequestMapping("seccion")
public class SeccionController {
    @Autowired
    private SeccionService seccionService;



    @PostMapping(value ="save-seccion", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "registrar seccion",
            description = "registrar seccion")
    public ResponseBase create(@RequestBody SeccionRequest seccion)
    {
        seccion.setCodUsuarioCreacion(1L);
        seccion.setNomTerCreacion(Constantes.IP_TERMINAL);
        return seccionService.create(seccion);
    }

    @PutMapping(value ="/update-seccion/{seccion_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "actualización de seccion",
            description = "actualización de seccion")
    public ResponseBase update(@PathVariable("seccion_Id") Long seccionId,@RequestBody SeccionRequest seccion)
    {
        seccion.setCodUsuarioModificacion(1L);
        seccion.setNomTerModificacion(Constantes.IP_TERMINAL);
        seccion.setCodSeccion(seccionId);
        return seccionService.update(seccion);
    }
    @DeleteMapping(value ="/delete-seccion/{empresa_Id}/{seccion_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "eliminación lógica de seccion",
            description = "eliminación lógica de seccion")
    public ResponseBase delete(@PathVariable("empresa_Id") Long empresaId,@PathVariable("seccion_Id") Long seccionId)
    {
        SeccionRequest seccion = new SeccionRequest();
        seccion.setCodEmpresa(empresaId);
        seccion.setCodSeccion(seccionId);
        seccion.setCodUsuarioEliminacion(1L);
        seccion.setNomTerEliminacion(Constantes.IP_TERMINAL);
        return seccionService.deleteById(seccion);
    }
    @GetMapping(value ="/get-seccion/{empresa_Id}/{seccion_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "obtener seccion Id",
            description = "obtener seccion Id")
    public ResponseBase buscarPorId(@PathVariable("empresa_Id") Long empresaId, @PathVariable("seccion_Id") Long seccionId)
    {

        return seccionService.findById(new SeccionId(empresaId,seccionId));
    }
    @GetMapping(value ="/list-seccion", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista de seccion",
            description = "Lista de seccion")
    public ResponseBase listar()
    {
        return seccionService.findAll();
    }


    @GetMapping(value = "to-list--seccion", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar seccion",
        description = "Listar seccion")
    public ResponseBasePage listarseccion (
        @RequestParam(name="empresa_Id", required = false) Long empresaId,
        @RequestParam(name="seccion_Id", required = false) Long bimestreId,
        @RequestParam(name="descripcion", required = false) String descripcion,
        @RequestParam(name="desCorta", required = false) String desCorta,
        @RequestParam(name="fecCreacion", required = false) String fecCreacion,
        @RequestParam(name="codEstado", required = false) Long codEstado,
        @RequestParam(name="page", defaultValue = "1") Integer page,
        @RequestParam(name="page_size", defaultValue = "10") Integer pageSize
    ) {
        var request = new SeccionListRequest();
        request.setCodempresa(empresaId);
        request.setCodSeccion(bimestreId);
        request.setDesSeccion(descripcion);
        request.setDescorta(desCorta);
        request.setFeccreacioncadena(fecCreacion);
        request.setCodest(codEstado);
        request.setPage(page);
        request.setPageSize(pageSize);

        return seccionService.listarSeccion(request);
    }

}
