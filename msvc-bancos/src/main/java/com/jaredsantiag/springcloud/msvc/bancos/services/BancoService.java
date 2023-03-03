package com.jaredsantiag.springcloud.msvc.bancos.services;

import com.jaredsantiag.springcloud.msvc.bancos.entity.Banco;

import java.util.List;
import java.util.Optional;

public interface BancoService {
    List<Banco> listar();
    Optional<Banco> porId(Long id);
    Banco guardar(Banco banco);
    void eliminar(Long id);

}
