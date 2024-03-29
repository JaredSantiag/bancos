package com.jaredsantiag.springcloud.msvc.usuarios.services;

import com.jaredsantiag.springcloud.msvc.usuarios.clients.BancoClientRest;
import com.jaredsantiag.springcloud.msvc.usuarios.moles.entity.Usuario;
import com.jaredsantiag.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements  UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BancoClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
        client.eliminarBancoUsuarioPorId(id);
    }

    @Override
    @Transactional
    public List<Usuario> listarPorIds(Iterable<Long> ids) {
        return (List<Usuario>) usuarioRepository.findAllById(ids);
    }

    @Override
    public Optional<Usuario> porEmail(String email) {
        return usuarioRepository.porEmail(email);
    }
}
