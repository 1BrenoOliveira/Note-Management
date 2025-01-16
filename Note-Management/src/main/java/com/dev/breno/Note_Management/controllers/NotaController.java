package com.dev.breno.Note_Management.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

import com.dev.breno.Note_Management.dtos.ErroDevalidacaoDto;
import com.dev.breno.Note_Management.dtos.NotaDto;
import com.dev.breno.Note_Management.forms.ItemForm;
import com.dev.breno.Note_Management.forms.NotaForm;
import com.dev.breno.Note_Management.models.Cliente;
import com.dev.breno.Note_Management.models.Item;
import com.dev.breno.Note_Management.models.Nota;
import com.dev.breno.Note_Management.repostiories.ClienteRepository;
import com.dev.breno.Note_Management.repostiories.ItemRepository;
import com.dev.breno.Note_Management.repostiories.NotaRepository;
import com.dev.breno.Note_Management.repostiories.ProdutoRepository;
import com.dev.breno.Note_Management.specifications.SpecificationNota;
import com.dev.breno.Note_Management.util.DataUtil;
import com.dev.breno.Note_Management.util.PaginacaoUtil;

@RestController
@RequestMapping("/nota")
public class NotaController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	public ClienteRepository clienteRepository;
	@Autowired
	public NotaRepository notaRepository;
	@Autowired
	public ItemRepository itemRepository;
	
	@GetMapping
	public ResponseEntity<?> listarTodas(String page, String size, String cliente, String dataEmissao){
		Pageable pageable = PaginacaoUtil.gerarPagebale(page, size);
		List<Nota> notas = new ArrayList<>();
		
		try {
			if(cliente==null && dataEmissao==null){
				notas = notaRepository.findAll();
			}else {
				notas = notaRepository.findAll(Specification.where(
						SpecificationNota.clienteNome(cliente))
						.or(SpecificationNota.dataEmissao(DataUtil.converterEmLocalDate(dataEmissao))));
			}
		}catch(DateTimeParseException e) {
			ErroDevalidacaoDto erro = new ErroDevalidacaoDto("Data Emissão", "A data de emissão precisa estar no formato 'dd-MM-yyyy'");
			return ResponseEntity.badRequest().body(erro);
		}
		
		for (Nota nota : notas) {
			nota.setItens(itemRepository.findByNota(nota));
		}
		List<NotaDto> notasDto = NotaDto.converter(notas);
		return ResponseEntity.ok (new PageImpl<>(notasDto, pageable, notasDto.size()));	
	}
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody @Valid  NotaForm form, UriComponentsBuilder uriBuilder){
		Optional<Cliente> optional = clienteRepository.findById(Long.parseLong(form.getCliente()));
		try {
			Nota nota = form.converte(optional.get(), produtoRepository);
			URI uri = uriBuilder.path("/nota/{id}").buildAndExpand(nota.getId()).toUri();
			
			notaRepository.save(nota);
			setarNotaEmItens(nota);
			itemRepository.saveAll(nota.getItens());
			 
			return ResponseEntity.created(uri).body(new NotaDto(nota));
		}catch(NoSuchElementException e){
			ErroDevalidacaoDto erro = new ErroDevalidacaoDto("Cliente", "Este cliente não está registrado");
			return ResponseEntity.badRequest().body(erro);
		}
		catch(NotFoundException e) {
			ErroDevalidacaoDto erro = new ErroDevalidacaoDto("Produto", "Este produto não está registrado");
			return ResponseEntity.badRequest().body(erro);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NotaDto> detalhar(@PathVariable("id")long id){
		Optional<Nota> optional = notaRepository.findById(id);
		if(optional.isPresent()) {
			Nota nota = optional.get();
			nota.setItens(itemRepository.findByNota(nota));
			return ResponseEntity.ok(new NotaDto(nota));	
		}
		return ResponseEntity.notFound().build();	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarNota(@PathVariable("id") long id, @RequestBody NotaForm form){
		Optional<Nota> optional = notaRepository.findById(id);
		if(optional.isPresent()) {
			Nota nota = optional.get();
			if(form.getCliente()!=null) {
				Optional<Cliente> cliente = clienteRepository.findById(Long.parseLong(form.getCliente()));
				if(cliente.isPresent()) {
					nota.setCliente(cliente.get());
				}else {
					ErroDevalidacaoDto erro = new ErroDevalidacaoDto("Clinte", "Este cliente não está registrado");
					return ResponseEntity.badRequest().body(erro);
				}
			}if(form.getDataEmissao()!= null) {
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate novaData =LocalDate.parse(form.getDataEmissao(), formatter);
					nota.setDataEmissao(novaData);
				}catch(Exception e) {
					ErroDevalidacaoDto erro = new ErroDevalidacaoDto("dataEmissao", "A data deve estar no padrao 'dd-MM-yyyy'");
					return ResponseEntity.badRequest().body(erro);
				}
			}
			notaRepository.save(nota);
			return ResponseEntity.ok().body(nota);
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable("id") long id){
		Optional<Nota> optional = notaRepository.findById(id);
		if(optional.isPresent()) {
			List<Item> lista = itemRepository.findByNota(optional.get());
			itemRepository.deleteAll(lista);
			notaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/{idNota}/item/adicionar")
	public ResponseEntity<?> adicionarItem(@PathVariable ("idNota") long id, @RequestBody ItemForm form){
		Optional<Nota> optional = notaRepository.findById(id);
		if(optional.isPresent()) {
			Nota nota = optional.get();
			try {
				Item item = form.converterEmItem(produtoRepository);
				List<Item> lista = nota.getItens();
				lista.add(item);
				nota.setItens(lista);
				nota.setValorTotal(nota.getValorTotal().add(item.getValorTotalItem()));
				item.setNota(nota);
				
				notaRepository.save(nota);
				itemRepository.save(item);
				 
				return ResponseEntity.ok().body(new NotaDto(nota));
			}catch(NotFoundException e) {
				ErroDevalidacaoDto erro = new ErroDevalidacaoDto("Produto", "Este produto não está registrado");
				return ResponseEntity.badRequest().body(erro);
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{idNota}/item/{idItem}")
	public ResponseEntity<?> deletarItem(@PathVariable("idNota") long idNota,  @PathVariable("idItem")long idItem){
		Optional<Nota> optionalNota = notaRepository.findById(idNota);
		Optional<Item> optionalItem = itemRepository.findById(idItem);
		
		if(optionalItem.isPresent() && optionalNota.isPresent()) {
			Item item = optionalItem.get();
			if(idNota==item.getNota().getId()) {
				itemRepository.deleteById(idItem);
				return ResponseEntity.ok().build();
			}
			ErroDevalidacaoDto erro = new ErroDevalidacaoDto("NOTA e ITEM", "Este item não faz parte desta nota");
			return ResponseEntity.badRequest().body(erro);
		}
		return ResponseEntity.notFound().build();
	}

	
	private List<Item> setarNotaEmItens( Nota nota) {
		List<Item> lista = nota.getItens();
		for (Item item: lista) item.setNota(nota);
		return lista;
	}
	
}
