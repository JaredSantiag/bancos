package com.jaredsantiag.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-bancos", url="host.docker.internal:8002")
public interface BancoClientRest {

    @DeleteMapping("/eliminar_curso_usuario/{id}")
    void eliminarBancoUsuarioPorId(@PathVariable Long id);
}