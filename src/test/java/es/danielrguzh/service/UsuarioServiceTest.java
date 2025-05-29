package es.danielrguzh.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import es.danielrguezh.entites.Usuario;
import es.danielrguezh.repo.UsuarioRepository;
import es.danielrguezh.services.UsuarioService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.util.Assert;

//@SpringBootTest
@MockitoBean
public class UsuarioServiceTest {

    @Mock
	UsuarioRepository usuarioRepositoryMock;
	Usuario usuario;
	UsuarioService usuarioService;

	@BeforeEach
	void beforeEach(){
		usuarioRepositoryMock = mock(UsuarioRepository.class);
		usuario = new Usuario();
		usuarioService = new UsuarioService();
		usuarioService.setUsuarioRepository(usuarioRepositoryMock);
		when(usuarioRepositoryMock.save(any(Usuario.class))).thenReturn(usuario);
	}

    

    @Test
	void createUsuarioTest(){
		try {
			usuario = new Usuario("manuel");
			Usuario usuarioSave = usuarioService.createUsuario(usuario);
			assertNotNull(usuarioSave);
		} catch (Exception e) {
			fail("se a producido un error en la ejecucion del test");
		}   
	}

    @Test
    void getAllUsuariosTest() {
        try {
            
            when(usuarioRepositoryMock.findAll()).thenReturn(Arrays.asList(new Usuario(1, "daniel"),new Usuario(2, "lucia")));
            List<Usuario> usuarios = usuarioService.getAllUsuarios();
            assertNotNull(usuarios);
            assertEquals(2, usuarios.size());
        } catch (Exception e) {
            fail("Error en test getAllUsuariosTest");
        }
    }

    @Test
    void getUsuarioByIdTest() {
        try {
            usuario = new Usuario(1, "ana");
            when(usuarioRepositoryMock.findById(1)).thenReturn(Optional.of(usuario));

            Usuario resultado = usuarioService.getUsuarioById(1);
            assertNotNull(resultado);
            assertEquals("ana", resultado.getName());
        } catch (Exception e) {
            fail("Error en test getUsuarioByIdTest");
        }
    }

   @Test
    void updateUsuarioTest() {
        try {
            Usuario usuarioExistente = new Usuario(1, "carlos");
            Usuario usuarioNuevo = new Usuario("carlos actualizado");

            when(usuarioRepositoryMock.findById(1)).thenReturn(Optional.of(usuarioExistente));
            when(usuarioRepositoryMock.save(any(Usuario.class))).thenReturn(usuarioExistente);

            Usuario resultado = usuarioService.updateUsuario(1, usuarioNuevo);
            assertNotNull(resultado);
            assertEquals("carlos actualizado", resultado.getName());
        } catch (Exception e) {
            fail("Error en test updateUsuarioTest");
        }
    }

    @Test
    void deleteUsuarioTest() {
        try {
            when(usuarioRepositoryMock.existsById(1)).thenReturn(true);
            doNothing().when(usuarioRepositoryMock).deleteById(1);

            usuarioService.deleteUsuario(1);
            verify(usuarioRepositoryMock, times(1)).deleteById(1);
        } catch (Exception e) {
            fail("Error en test deleteUsuarioTest");
        }
    }

    @Test
    void deleteUsuario_NotFoundTest() {
        try {
            when(usuarioRepositoryMock.existsById(99)).thenReturn(false);
            usuarioService.deleteUsuario(99);
            fail("Se esperaba una excepción por usuario inexistente");
        } catch (RuntimeException e) {
            assertEquals("no exite el usuario a eliminar", e.getMessage());
        } catch (Exception e) {
            fail("Error inesperado");
        }
    }

    @Test
    void updateUsuario_NotFoundTest() {
        try {
            when(usuarioRepositoryMock.findById(99)).thenReturn(Optional.empty());
            usuarioService.updateUsuario(99, new Usuario("nuevo"));
            fail("Se esperaba una excepción por usuario inexistente");
        } catch (RuntimeException e) {
            assertEquals("no existe el usuario a actualizar", e.getMessage());
        } catch (Exception e) {
            fail("Error inesperado");
        }
    }
}