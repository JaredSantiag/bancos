package com.jaredsantiag.springcloud.msvc.bancos.services;

import com.jaredsantiag.springcloud.msvc.bancos.models.Usuario;
import com.jaredsantiag.springcloud.msvc.bancos.models.entity.Banco;

import java.util.List;
import java.util.Optional;

public interface BancoService {
    List<Banco> listar();
    Optional<Banco> porId(Long id);
    Optional<Banco> porIdConUsuarios(Long id, String token);
    Banco guardar(Banco banco);
    void eliminar(Long id);


    void eliminarBancoUsuarioPorId(Long id);


    Optional<Usuario> asignarUsuario(Usuario usuario, Long bancoId, String token);
    Optional<Usuario> crearUsuario(Usuario usuario, Long bancoId, String token);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long bancoId, String token);


}
