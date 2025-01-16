package com.dev.breno.Note_Management.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.dev.breno.Note_Management.dtos.ClienteDto;
import com.dev.breno.Note_Management.forms.ClienteForm;
import com.dev.breno.Note_Management.models.Cliente;
import com.dev.breno.Note_Management.repostiories.ClienteRepository;
import com.dev.breno.Note_Management.specifications.SpecificationCliente;
import com.dev.breno.Note_Management.util.PaginacaoUtil;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@GetMapping
	public ResponseEntity<Page<ClienteDto>> buscarTodos(String page, String size, String nome, String codigo){
		Pageable pageable = PaginacaoUtil.gerarPagebale(page, size);
		List<Cliente> clientes ;
		if(nome==null&&codigo==null) {
			clientes = clienteRepository.findAll();
		}else {
			clientes = clienteRepository.findAll(Specification.where(
					SpecificationCliente.nome(nome)
					.or(SpecificationCliente.codigo(codigo))));
		}
		
		Page<ClienteDto> pageClientes = new PageImpl<>(ClienteDto.converter(clientes), pageable, clientes.size());
		return  ResponseEntity.ok().body(pageClientes);
	}
	
	@PostMapping
	public ResponseEntity<ClienteDto> cadastrar(@RequestBody @Valid ClienteForm form, UriComponentsBuilder uriBuilder){
		Cliente cliente = form.converteEmCliente();
		clienteRepository.save(cliente);
		
		URI uri = uriBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new ClienteDto(cliente));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDto> detalhes(@PathVariable("id") long id){
		Optional<Cliente> optional = clienteRepository.findById(id);
		if(optional.isPresent())return ResponseEntity.ok(new ClienteDto(optional.get()));
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ClienteDto> alterar(@PathVariable("id") long id, @RequestBody  ClienteForm form){
		Optional<Cliente> optional = clienteRepository.findById(id);
		if(optional.isPresent()){
			Cliente cliente = optional.get();
			if(form.getCodigo()!=null )cliente.setCodigo(form.getCodigo());
			if(form.getNome()!=null )cliente.setNome(form.getNome());
			clienteRepository.save(cliente);
			return ResponseEntity.ok(new ClienteDto(cliente));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable("id") long id){
		clienteRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	
	
}
