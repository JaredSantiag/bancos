package com.jaredsantiag.springcloud.msvc.usuarios.repositories;

import com.jaredsantiag.springcloud.msvc.usuarios.moles.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
