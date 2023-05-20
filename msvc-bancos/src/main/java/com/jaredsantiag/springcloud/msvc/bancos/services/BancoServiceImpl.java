package com.jaredsantiag.springcloud.msvc.bancos.services;

import com.jaredsantiag.springcloud.msvc.bancos.clients.UsuariosClientRest;
import com.jaredsantiag.springcloud.msvc.bancos.models.Usuario;
import com.jaredsantiag.springcloud.msvc.bancos.models.entity.Banco;
import com.jaredsantiag.springcloud.msvc.bancos.models.entity.BancoUsuario;
import com.jaredsantiag.springcloud.msvc.bancos.repositories.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BancoServiceImpl implements BancoService {

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private UsuariosClientRest client;

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
    @Transactional(readOnly = true)
    public Optional<Banco> porIdConUsuarios(Long id, String token) {
        Optional<Banco> o = bancoRepository.findById(id);
        if(o.isPresent()){
            Banco banco = o.get();
            if(!banco.getBancoUsuarios().isEmpty()){
                List<Long> ids = banco.getBancoUsuarios().stream().map(bu -> bu.getUsuarioId())
                        .collect(Collectors.toList());
                List<Usuario> usuarios = client.obtenerUsuariosPorBanco(ids, token);
                banco.setUsuarios(usuarios);
            }
            return Optional.of(banco);
        }
        return Optional.empty();
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

    @Override
    @Transactional
    public void eliminarBancoUsuarioPorId(Long id) {
        bancoRepository.eliminarBancoUsuarioPorId(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long bancoId, String token) {
        Optional<Banco> o = bancoRepository.findById(bancoId);
        if(o.isPresent()){
            Usuario usuarioMsvc = client.detalle(usuario.getId(), token);

            Banco banco = o.get();
            BancoUsuario bancoUsuario = new BancoUsuario();
            bancoUsuario.setUsuarioId(usuarioMsvc.getId());

            banco.addBancoUsuario(bancoUsuario);
            bancoRepository.save(banco);

            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long bancoId, String token) {
        Optional<Banco> o = bancoRepository.findById(bancoId);
        if(o.isPresent()){
            Usuario usuarioNuevoMsvc = client.crear(usuario, token);

            Banco banco = o.get();
            BancoUsuario bancoUsuario = new BancoUsuario();
            bancoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

            banco.addBancoUsuario(bancoUsuario);
            bancoRepository.save(banco);

            return Optional.of(usuarioNuevoMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long bancoId, String token) {
        Optional<Banco> o = bancoRepository.findById(bancoId);
        if(o.isPresent()){
            Usuario usuarioMsvc = client.detalle(usuario.getId(), token);

            Banco banco = o.get();
            BancoUsuario bancoUsuario = new BancoUsuario();
            bancoUsuario.setUsuarioId(usuarioMsvc.getId());

            banco.removeBancoUsuario(bancoUsuario);
            bancoRepository.save(banco);

            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }
}
