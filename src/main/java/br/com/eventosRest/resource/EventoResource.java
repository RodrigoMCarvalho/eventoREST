package br.com.eventosRest.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eventosRest.dao.EventoDAO;
import br.com.eventosRest.model.Evento;

@RestController
@RequestMapping("/eventoRest")
public class EventoResource {
	
	@Autowired
	private EventoDAO dao;
	
	@GetMapping(produces="application/json")
	public Iterable<Evento> listEventos() {
		Iterable<Evento> eventos = dao.findAll();
		return eventos;
	}
	
	@PostMapping
	public Evento cadastrarEvento(@RequestBody @Valid Evento evento) {
		return dao.save(evento);
	}
	
	@DeleteMapping
	public Evento excluirEvento (@RequestBody Evento evento) {
		dao.delete(evento);
		return evento;
	}

}
