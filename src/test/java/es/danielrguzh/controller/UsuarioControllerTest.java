package es.danielrguzh.controller;



import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.danielrguezh.controller.UsuarioController;
import es.danielrguezh.entites.Usuario;
import es.danielrguezh.services.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@MockitoBean
public class UsuarioControllerTest {
    @Mock
	UsuarioService usuarioServiceMock;
	Usuario usuario;
	UsuarioController usuarioController;

	@BeforeEach
	void beforeEach(){
		usuarioServiceMock = mock(UsuarioService.class);
		usuario = new Usuario();
		usuarioController = new UsuarioController();
        when(usuarioServiceMock.createUsuario(any(Usuario.class))).thenReturn(usuario);
	}

    @Test
    public void testGetAllUsuarios() throws Exception {
        List<Usuario> usuarios = Arrays.asList(new Usuario(1, "Daniel"), new Usuario(2, "Lucía"));
        when(usuarioService.getAllUsuarios()).thenReturn(usuarios);

        mockMvc.perform(get("/api/v1/users/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    public void testGetUsuarioById() throws Exception {
        Usuario user = new Usuario(1, "Daniel");
        when(usuarioService.getUsuarioById(1)).thenReturn(user);

        mockMvc.perform(get("/api/v1/user/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Daniel"));
    }

    @Test
	void createUsuarioTest(){
		try {
			usuario = new Usuario("miguel");
			usuarioController.createUsuario(usuario);
			assertNotNull(usuarioController.createUsuario(usuario));
		} catch (Exception e) {
			fail("se a producido un error en la ejecucion del test");
		}
	}

	@Test
	void createUsuarioNoNombreTest() {
		try {
			usuarioController.createUsuario(usuario);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("nombre"));
		}
	}

    @Test
    public void testUpdateUsuario() throws Exception {
        Usuario updatedUser = new Usuario(1, "Actualizado");
        when(usuarioService.updateUsuario(eq(1), any(Usuario.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/v1/update/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Actualizado"));
    }

    @Test
    public void testDeleteUsuario() throws Exception {
        doNothing().when(usuarioService).deleteUsuario(1);

        mockMvc.perform(delete("/api/v1/delete/user/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.deleted").value(true));
    }
}
