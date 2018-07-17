package br.com.eventosRest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.eventosRest.model.Evento;

@Repository
public interface EventoDAO extends CrudRepository<Evento, Long>{

}
