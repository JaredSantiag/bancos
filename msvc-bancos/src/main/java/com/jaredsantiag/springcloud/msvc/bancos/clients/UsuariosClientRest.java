package com.jaredsantiag.springcloud.msvc.bancos.clients;

import com.jaredsantiag.springcloud.msvc.bancos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="msvc-usuarios")
public interface UsuariosClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id,  @RequestHeader(value = "Authorization", required = true) String token);

    @PostMapping("/")
    Usuario crear(@RequestBody Usuario usuario, @RequestHeader(value = "Authorization", required = true) String token);

    @GetMapping("/{usuarios}")
    List<Usuario> obtenerUsuariosPorBanco(@RequestParam Iterable<Long> ids, @RequestHeader(value="Authorization", required = true) String token);
}
