package br.com.eventosRest.resource;

import java.util.ArrayList;
import java.util.List;

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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import br.com.eventosRest.dao.EventoDAO;
import br.com.eventosRest.model.Evento;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API REST Eventos")
@RestController
@RequestMapping("/eventosRest")
public class EventoResource {
	
	@Autowired
	private EventoDAO dao;
	
	@ApiOperation(value = "Retorna uma lista de eventos")
	@GetMapping(produces="application/json")
	public Iterable<Evento> listEventos() {
		Iterable<Evento> eventos = dao.findAll();
		List<Evento> listEventos = new ArrayList<>();
		
		for(Evento evento: eventos) {
			long codigo = evento.getCodigo();
			evento.add(linkTo(methodOn(EventoResource.class).evento(codigo)).withSelfRel()); //cria um link para cada um dos eventos	
			listEventos.add(evento);
		}
		
		return listEventos;
	}
	
	@ApiOperation(value = "Retorna um evento específico")
	@GetMapping("/evento/{codigo}" )
	public Evento evento(@PathVariable("codigo") Long codigo) {
		Evento evento = dao.findByCodigo(codigo);
		evento.add(linkTo(methodOn(EventoResource.class).listEventos()).withRel("Lista de Eventos")); //cria um link para a lista de eventos
		return evento;
	}
	
	@ApiOperation(value = "Salva um evento")
	@PostMapping
	public Evento cadastraEvento(@RequestBody @Valid Evento evento) {
		return dao.save(evento);
	}
	
	@ApiOperation(value = "Busca um evento por código")
	@GetMapping("/{codigo}")
	public ResponseEntity<Evento> buscaPorCodigo(@PathVariable("codigo") Long codigo){
		Evento evento = dao.getOne(codigo);
		if (evento == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(evento);
	}
	
	@ApiOperation(value = "Atualiza um evento por código")
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
	
	@ApiOperation(value = "Remove um evento")
	@DeleteMapping
	public Evento excluiEvento (@RequestBody Evento evento) {
		dao.delete(evento);
		return evento;
	}
	
	@ApiOperation(value = "Remove um evento por código")
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
