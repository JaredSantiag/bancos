package com.jaredsantiag.springcloud.msvc.bancos.models.entity;

import com.jaredsantiag.springcloud.msvc.bancos.models.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="bancos")
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "banco_id")
    private List<BancoUsuario> bancoUsuarios;

    @Transient //Este atributo no esta mapeado a tablas, no es contexto de hibérnate
    private List<Usuario> usuarios;

    public Banco() {
        bancoUsuarios = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<BancoUsuario> getBancoUsuarios() {
        return bancoUsuarios;
    }

    public void setBancoUsuarios(List<BancoUsuario> bancoUsuarios) {
        this.bancoUsuarios = bancoUsuarios;
    }

    public void addBancoUsuario(BancoUsuario bancoUsuario){
        bancoUsuarios.add(bancoUsuario);
    }

    public void removeBancoUsuario(BancoUsuario bancoUsuario){
        bancoUsuarios.remove(bancoUsuario);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
