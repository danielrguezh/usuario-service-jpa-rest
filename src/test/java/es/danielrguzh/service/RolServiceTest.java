package es.danielrguzh.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import es.danielrguezh.entites.Rol;
import es.danielrguezh.repo.RolRepository;
import es.danielrguezh.services.RolService;

import org.assertj.core.api.Assertions;
import org.mockito.Mock;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@MockitoBean
public class RolServiceTest {

    @Mock
    RolRepository rolRepositoryMock;
    Rol rol;
    RolService rolService;

    @BeforeEach
    void beforeEach() {
        rolRepositoryMock = mock(RolRepository.class);
        rol = new Rol();
        rolService = new RolService();
        rolService.setRolRepository(rolRepositoryMock);
        when(rolRepositoryMock.save(any(Rol.class))).thenReturn(rol);
    }

    @Test
    void createRolTest() {
        try {
            rol = new Rol("admin");
            Rol rolSaved = rolService.createRol(rol);
            assertNotNull(rolSaved);
        } catch (Exception e) {
            fail("Se ha producido un error en la ejecución del test");
        }
    }

    @Test
    void getAllRolesTest() {
        try {
            when(rolRepositoryMock.findAll()).thenReturn(Arrays.asList(new Rol(1, "admin"), new Rol(2, "user")));
            List<Rol> roles = rolService.getAllRoles();
            assertNotNull(roles);
            assertEquals(2, roles.size());
        } catch (Exception e) {
            fail("Error en test getAllRolesTest");
        }
    }

    @Test
    void getRolByIdTest() {
        try {
            rol = new Rol(1, "manager");
            when(rolRepositoryMock.findById(1)).thenReturn(Optional.of(rol));

            Rol resultado = rolService.getRolById(1);
            assertNotNull(resultado);
            assertEquals("manager", resultado.getName());
        } catch (Exception e) {
            fail("Error en test getRolByIdTest");
        }
    }

    @Test
    void updateRolTest() {
        try {
            Rol rolExistente = new Rol(1, "editor");
            Rol rolNuevo = new Rol("editor actualizado");

            when(rolRepositoryMock.findById(1)).thenReturn(Optional.of(rolExistente));
            when(rolRepositoryMock.save(any(Rol.class))).thenReturn(rolExistente);

            Rol resultado = rolService.updateRol(1, rolNuevo);
            assertNotNull(resultado);
            assertEquals("editor actualizado", resultado.getName());
        } catch (Exception e) {
            fail("Error en test updateRolTest");
        }
    }

    @Test
    void deleteRolTest() {
        try {
            when(rolRepositoryMock.existsById(1)).thenReturn(true);
            doNothing().when(rolRepositoryMock).deleteById(1);

            rolService.deleteRol(1);
            verify(rolRepositoryMock, times(1)).deleteById(1);
        } catch (Exception e) {
            fail("Error en test deleteRolTest");
        }
    }

    @Test
    void deleteRol_NotFoundTest() {
        try {
            when(rolRepositoryMock.existsById(99)).thenReturn(false);
            rolService.deleteRol(99);
            fail("Se esperaba una excepción por rol inexistente");
        } catch (RuntimeException e) {
            assertEquals("no existe el rol a eliminar", e.getMessage());
        } catch (Exception e) {
            fail("Error inesperado");
        }
    }

    @Test
    void updateRol_NotFoundTest() {
        try {
            when(rolRepositoryMock.findById(99)).thenReturn(Optional.empty());
            rolService.updateRol(99, new Rol("nuevo"));
            fail("Se esperaba una excepción por rol inexistente");
        } catch (RuntimeException e) {
            assertEquals("no existe el rol a actualizar", e.getMessage());
        } catch (Exception e) {
            fail("Error inesperado");
        }
    }
}
