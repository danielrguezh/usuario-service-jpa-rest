package es.danielrguezh.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.danielrguezh.entites.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

}
