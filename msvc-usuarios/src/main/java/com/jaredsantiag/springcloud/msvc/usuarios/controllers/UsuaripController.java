package com.jaredsantiag.springcloud.msvc.usuarios.controllers;

import com.jaredsantiag.springcloud.msvc.usuarios.moles.entity.Usuario;
import com.jaredsantiag.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuaripController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public List<Usuario> listar(){
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> mostarDetalle(Long id){
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);
        if(usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(@RequestBody Usuario usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Usuario usuario, @PathVariable Long id){
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);

        if(usuarioOptional.isPresent()){
            Usuario usuarioDB = usuarioOptional.get();

            usuarioDB.setNombre(usuario.getNombre());
            usuarioDB.setEmail(usuario.getEmail());
            usuarioDB.setPassword(usuario.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuarioDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);
        if(usuarioOptional.isPresent()){
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();
    }
}
