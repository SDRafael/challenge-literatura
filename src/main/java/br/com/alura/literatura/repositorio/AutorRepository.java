package br.com.alura.literatura.repositorio;

import br.com.alura.literatura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String name);
}
