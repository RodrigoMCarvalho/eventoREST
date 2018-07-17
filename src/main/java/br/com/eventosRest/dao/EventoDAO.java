package br.com.eventosRest.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eventosRest.model.Evento;

@Repository
public interface EventoDAO extends JpaRepository<Evento, Long>{

	Optional<Evento> save(Optional<Evento> eventoAtualiza);

}
