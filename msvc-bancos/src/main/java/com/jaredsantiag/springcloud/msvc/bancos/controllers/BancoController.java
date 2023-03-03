package com.jaredsantiag.springcloud.msvc.bancos.controllers;

import com.jaredsantiag.springcloud.msvc.bancos.entity.Banco;
import com.jaredsantiag.springcloud.msvc.bancos.services.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

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
        return ResponseEntity.notFound().build();
    }
}
