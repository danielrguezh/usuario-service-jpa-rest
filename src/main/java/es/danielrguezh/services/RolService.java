package es.danielrguezh.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.danielrguezh.entites.Rol;

@Component
public class RolService {

    private RolRepository rolRepository;

    @Autowired
    public void setRolRepository(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    /**
     * Metodo que obtiene todos los roles de la bbdd
     * @return roles
     */
    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    /**
     * Metodo que obtiene un rol segun su id
     * @param rolId
     * @return rol
     */
    public Rol getRolById(int rolId) {
        return rolRepository.findById(rolId).orElse(null);
    }

    /**
     * Metodo que crea un rol en la bbdd
     * @param rol
     * @return rol creado
     */
    public Rol createRol(Rol rol) {
        if (rol == null) {
            throw new RuntimeException("no existe el rol a crear");
        }
        return rolRepository.save(rol);
    }

    /**
     * Metodo que actualiza un rol de la bbdd
     * @param rolId 
     * @param rolDetails
     * @return
     */
    public Rol updateRol(int rolId, Rol rolDetails) {
        Rol rol = rolRepository.findById(rolId).orElse(null);
        if (rol != null) {
            rol.setName(rolDetails.getName());
            return rolRepository.save(rol);
        }
        throw new RuntimeException("no existe el rol a actualizar");
    }

    /**
     * Metodo que elimina un rol de la bbdd
     * @param rolId
     */
    public void deleteRol(int rolId) {
        if (rolRepository.existsById(rolId)) {
            rolRepository.deleteById(rolId);
        } else {
            throw new RuntimeException("no existe el rol a eliminar");
        }
    }
}