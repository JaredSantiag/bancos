package com.jaredsantiag.springcloud.msvc.bancos.repositories;

import com.jaredsantiag.springcloud.msvc.bancos.models.entity.Banco;
import org.springframework.data.repository.CrudRepository;

public interface BancoRepository extends CrudRepository<Banco, Long> {

}
