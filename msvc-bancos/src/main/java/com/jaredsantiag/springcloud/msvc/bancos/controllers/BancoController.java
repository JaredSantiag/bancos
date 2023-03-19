package com.jaredsantiag.springcloud.msvc.bancos.controllers;

import com.jaredsantiag.springcloud.msvc.bancos.models.Usuario;
import com.jaredsantiag.springcloud.msvc.bancos.models.entity.Banco;
import com.jaredsantiag.springcloud.msvc.bancos.services.BancoService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@Controller
public class BancoController {
    @Autowired
    private BancoService bancoService;

    @GetMapping
    public ResponseEntity<List<Banco>> listar(){
        return ResponseEntity.ok(bancoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Banco> optional = bancoService.porId(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        }
        System.out.println("Here 1");
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Banco banco, BindingResult result){
        if(result.hasErrors()){
            return validar(result);
        }
        Banco bancoDB = bancoService.guardar(banco);
        return ResponseEntity.status(HttpStatus.CREATED).body(bancoDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Banco banco, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validar(result);
        }
        Optional<Banco> bancoOptional = bancoService.porId(id);
        if(bancoOptional.isPresent()){
            Banco bancoDB = bancoOptional.get();
            bancoDB.setNombre(banco.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(bancoService.guardar(bancoDB));
        }
        System.out.println("Here 2");
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Banco> bancoOptional = bancoService.porId(id);
        if(bancoOptional.isPresent()){
            bancoService.eliminar(bancoOptional.get().getId());
            return ResponseEntity.noContent().build();
        }
        System.out.println("Here 3");
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar_usuario/{bancoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long bancoId){
        Optional<Usuario> o;
        try{
            o = bancoService.asignarUsuario(usuario, bancoId);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No exsite el id del usuario " +
                            "o error con la comunicacion: "+e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear_usuario/{bancoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long bancoId){
        Optional<Usuario> o;
        try{
            o = bancoService.crearUsuario(usuario, bancoId);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No se pudo crear el usuario " +
                            "o error con la comunicacion: "+e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar_usuario/{bancoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long bancoId){
        Optional<Usuario> o;
        try{
            o = bancoService.eliminarUsuario(usuario, bancoId);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No exsite el id del usuario " +
                            "o error con la comunicacion: "+e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(),"El campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
