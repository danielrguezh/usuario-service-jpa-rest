package es.danielrguezh.usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

    private UsuarioService usuarioService;

    @Autowired
    public void setUsuarioRepository(UsuarioService userService) {
        this.usuarioService = userService;
    }

    @GetMapping("/users/")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable(value = "id") int userId) {
        Usuario user = usuarioService.getUsuarioById(userId);
        return ResponseEntity.ok().body(user);
    }


    @PostMapping("/add/user/")
    public Usuario createUsuario(@Valid @RequestBody Usuario user) {
        if (user==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no puede ser nulo"); 
        }
        if (user.getId() <1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ide del usuario no puede ser nulo");
        }
        return usuarioService.createUsuario(user);
    }


    @PutMapping("/update/user/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable(value = "id") int userId, @Valid @RequestBody Usuario userDetails) {
        final Usuario updatedUsuario = usuarioService.updateUsuario(userId, userDetails);
        return ResponseEntity.ok(updatedUsuario);
    }


    @DeleteMapping("/delete/user/{id}")
    public Map<String, Boolean> deleteUsuario(@PathVariable(value = "id") int userId) {
        usuarioService.deleteUsuario(userId); 
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
