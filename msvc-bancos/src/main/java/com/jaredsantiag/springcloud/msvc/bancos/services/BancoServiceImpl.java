package com.jaredsantiag.springcloud.msvc.bancos.services;

import com.jaredsantiag.springcloud.msvc.bancos.entity.Banco;
import com.jaredsantiag.springcloud.msvc.bancos.repositories.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BancoServiceImpl implements BancoService {

    @Autowired
    private BancoRepository bancoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Banco> listar() {
        return (List<Banco>) bancoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Banco> porId(Long id) {
        return bancoRepository.findById(id);
    }

    @Override
    @Transactional
    public Banco guardar(Banco banco) {
        return bancoRepository.save(banco);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        bancoRepository.deleteById(id);
    }
}
