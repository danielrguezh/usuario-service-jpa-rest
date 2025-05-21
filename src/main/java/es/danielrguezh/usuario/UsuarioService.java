package es.danielrguezh.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    @Autowired
    public void setUsuarioRepository(UsuarioRepository userRepository) {
        this.usuarioRepository = userRepository;
    }

    /**
     * Metodo que obtiene todos los usuarios de la bbdd
     * @return usuarios
     */
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Metodo que obtiene un usuario segun su edad
     * @param usuarioId
     * @return usuario
     */
    public Usuario getUsuarioById(int usuarioId) {
        return usuarioRepository.findById(usuarioId).orElse(null);
    }

    /**
     * Metodo que crea un usuario en la bbdd
     * @param usuario
     * @return usuario creado
     */
    public Usuario createUsuario(Usuario usuario) {
        if (usuario==null) {
            throw new RuntimeException("no existe el usuario a crear");
        }
        return usuarioRepository.save(usuario);
    }

    /**
     * Metodo que actualiza un usuario de la bbdd
     * @param usuarioId 
     * @param userDetails
     * @return
     */
    public Usuario updateUsuario(int usuarioId, Usuario userDetails) {
        Usuario user = usuarioRepository.findById(usuarioId).orElse(null);
        if (user != null) {
            user.setName(userDetails.getName());
            return usuarioRepository.save(user);
        }
        throw new RuntimeException("no existe el usuario a actualizar");
    }

    /**
     * Metodo que elimina un usuario de la bbdd
     * @param usuarioId
     */
    public void deleteUsuario(int usuarioId) {
        if (usuarioRepository.existsById(usuarioId)) {
            usuarioRepository.deleteById(usuarioId);
        }else{
            throw new RuntimeException("no exite el usuario a eliminar");
        }
    }
}
