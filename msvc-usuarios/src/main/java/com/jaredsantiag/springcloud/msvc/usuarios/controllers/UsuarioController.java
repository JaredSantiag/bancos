package com.jaredsantiag.springcloud.msvc.usuarios.controllers;

import com.jaredsantiag.springcloud.msvc.usuarios.moles.entity.Usuario;
import com.jaredsantiag.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Environment environment;

    @GetMapping("/crash")
    public void crash(){
        ((ConfigurableApplicationContext) context).close();
    }

    @GetMapping("/")
    public ResponseEntity<?> listar(){
        Map<String, Object> body = new HashMap<>();
        body.put("users", usuarioService.listar());
        body.put("pod_info", environment.getProperty("MY_POD_NAME") + ": " + environment.getProperty("MY_POD_IP"));
        body.put("texto", environment.getProperty("config.texto"));

        //return Collections.singletonMap("users", usuarioService.listar());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> mostarDetalle(@PathVariable Long id){
        System.out.printf("El Id es: "+String.valueOf(id));
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);
        if(usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result){

        if(result.hasErrors()){
            return validar(result);
        }

        if(!usuario.getEmail().isEmpty() && usuarioService.porEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("mensajes","Ya existe un usuario con ese correo electronico"));
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){

        if(result.hasErrors()){
            return validar(result);
        }

        Optional<Usuario> usuarioOptional = usuarioService.porId(id);

        if(usuarioOptional.isPresent()){
            Usuario usuarioDB = usuarioOptional.get();

            if(!usuario.getEmail().isEmpty() && !usuario.getEmail().equalsIgnoreCase(usuarioDB.getEmail()) && usuarioService.porEmail(usuario.getEmail()).isPresent()){
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("mensajes","Ya existe un usuario con ese correo electronico"));
            }

            usuarioDB.setNombre(usuario.getNombre());
            usuarioDB.setEmail(usuario.getEmail());
            usuarioDB.setPassword(passwordEncoder.encode(usuario.getPassword()));

            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuarioDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);
        if(usuarioOptional.isPresent()){
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> obtenerUsuariosPorBanco(@RequestParam List<Long> ids){
        return ResponseEntity.ok(usuarioService.listarPorIds(ids));
    }

    @GetMapping("/authorized")
    public Map<String, Object> authorized(@RequestParam(name = "code") String code){
        return Collections.singletonMap("code", code);
    }

    @GetMapping
    public ResponseEntity<?> loginByEmail(@RequestParam String email){
        Optional o = usuarioService.porEmail(email);
        if(o.isPresent()){
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();

    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(),"El campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
