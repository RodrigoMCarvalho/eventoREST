package br.com.eventosRest.resource;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public Evento cadastraEvento(@RequestBody @Valid Evento evento) {
		return dao.save(evento);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Evento> buscaPorCodigo(@PathVariable("codigo") Long codigo){
		Evento evento = dao.getOne(codigo);
		if (evento == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(evento);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Evento> atualizaEvento(@PathVariable("codigo") Long codigo, 
			@RequestBody @Valid Evento evento){
		Evento eventoAtualiza = dao.getOne(codigo);
		if(eventoAtualiza == null) {
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(evento, eventoAtualiza, "codigo"); //source = evento, target = objeto que buscou do BD
		return ResponseEntity.ok(dao.save(eventoAtualiza));			// e ignora o "codigo"
	}
	
	@DeleteMapping
	public Evento excluiEvento (@RequestBody Evento evento) {
		dao.delete(evento);
		return evento;
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> excluiEventoPorCodigo(@PathVariable("codigo") Long codigo) {
		Evento evento = dao.getOne(codigo);
		if (evento == null) {
			return ResponseEntity.notFound().build();
		}
		dao.delete(evento);
		return ResponseEntity.noContent().build();
	}

	
	
	
	
}
