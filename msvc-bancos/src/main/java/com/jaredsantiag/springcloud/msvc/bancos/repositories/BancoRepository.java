package com.jaredsantiag.springcloud.msvc.bancos.repositories;

import com.jaredsantiag.springcloud.msvc.bancos.models.entity.Banco;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BancoRepository extends CrudRepository<Banco, Long> {

    @Modifying
    @Query("delete from BancoUsuario bu where bu.usuarioId=?1")
    void eliminarBancoUsuarioPorId(Long id);
}
