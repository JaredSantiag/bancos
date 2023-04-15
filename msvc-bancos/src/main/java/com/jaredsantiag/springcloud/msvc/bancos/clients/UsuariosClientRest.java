package com.jaredsantiag.springcloud.msvc.bancos.clients;

import com.jaredsantiag.springcloud.msvc.bancos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="msvc-usuarios", url="msvc-usuarios:8001")
public interface UsuariosClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping("/")
    Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/{usuarios}")
    List<Usuario> obtenerUsuariosPorBanco(@RequestParam Iterable<Long> ids);
}
