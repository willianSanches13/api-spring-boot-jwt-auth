package org.example.domain.respository;

import org.example.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepositry extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);

}
