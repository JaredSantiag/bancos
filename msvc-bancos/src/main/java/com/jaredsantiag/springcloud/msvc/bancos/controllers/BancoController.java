package com.jaredsantiag.springcloud.msvc.bancos.controllers;

import com.jaredsantiag.springcloud.msvc.bancos.entity.Banco;
import com.jaredsantiag.springcloud.msvc.bancos.services.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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
    public ResponseEntity<?> crear(@RequestBody Banco banco){
        Banco bancoDB = bancoService.guardar(banco);
        return ResponseEntity.status(HttpStatus.CREATED).body(bancoDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Banco banco, @PathVariable Long id){
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
}
