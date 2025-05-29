package es.danielrguezh.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.message.Message;
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

import es.danielrguezh.entites.Usuario;
import es.danielrguezh.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

    private UsuarioService usuarioService;

    @Autowired
    public void setUsuarioRepository(UsuarioService userService) {
        this.usuarioService = userService;
    }

    @Operation(summary = "Get all usuarios", description = "Returns all usuarios", responses = {
		@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Message.class))) }
	)
    @GetMapping("/users/")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @Operation(summary = "Get a Usuario by id", description = "Return a usuario segun su id", responses = {
		@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Message.class))),
		@ApiResponse(responseCode = "400", description = "Invalid id supplied"),
		@ApiResponse(responseCode = "404", description = "Usuario not found") }
	)
    @GetMapping("/user/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable(value = "id") int userId) {
        Usuario user = usuarioService.getUsuarioById(userId);
        return ResponseEntity.ok().body(user);
    }


    @Operation(summary = "Adds a Usuario", description = "Add a Usuario", responses = {
		@ApiResponse(responseCode = "200", description = "Successful operation"),
		@ApiResponse(responseCode = "400", description = "Invalid data"),
		@ApiResponse(responseCode = "409", description = "Already exists") }
	)
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



    @Operation(summary = "Updates a Usuario by id", description = "Updates a Usuario", responses = {
		@ApiResponse(responseCode = "200", description = "Successful operation"),
		@ApiResponse(responseCode = "400", description = "Invalid id supplied"),
		@ApiResponse(responseCode = "404", description = "Usuario not found") }
	)
    @PutMapping("/update/user/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable(value = "id") int userId, @Valid @RequestBody Usuario userDetails) {
        final Usuario updatedUsuario = usuarioService.updateUsuario(userId, userDetails);
        return ResponseEntity.ok(updatedUsuario);
    }


    @Operation(summary = "Deletes a Usuario by id", description = "Delete a Usuario", responses = {
		@ApiResponse(responseCode = "200", description = "Successful operation"),
		@ApiResponse(responseCode = "400", description = "Invalid id supplied"),
		@ApiResponse(responseCode = "404", description = "Usuario not found") }
	)
    @DeleteMapping("/delete/user/{id}")
    public Map<String, Boolean> deleteUsuario(@PathVariable(value = "id") int userId) {
        usuarioService.deleteUsuario(userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
