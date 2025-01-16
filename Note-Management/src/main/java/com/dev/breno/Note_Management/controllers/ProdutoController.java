package com.dev.breno.Note_Management.controllers;

import java.math.BigDecimal;
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

import com.dev.breno.Note_Management.dtos.ProdutoDto;
import com.dev.breno.Note_Management.forms.ProdutoForm;
import com.dev.breno.Note_Management.models.Produto;
import com.dev.breno.Note_Management.repostiories.ProdutoRepository;
import com.dev.breno.Note_Management.specifications.SpecificationProduto;
import com.dev.breno.Note_Management.util.PaginacaoUtil;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDto>> listarTodos(String page, String size, String codigo, String descricao){
		Pageable pageable = PaginacaoUtil.gerarPagebale(page, size);
		List<Produto> produtos;
		if(codigo==null && descricao==null) {
			produtos = produtoRepository.findAll();
		}else {
			produtos = produtoRepository.findAll(Specification.where(
					SpecificationProduto.codigo(codigo))
					.or(SpecificationProduto.descricao(descricao)));
		}
		Page<ProdutoDto> pageDto = new PageImpl<>( ProdutoDto.converter(produtos), pageable, produtos.size());
		return ResponseEntity.ok(pageDto);
	}
	
	@PostMapping
	public ResponseEntity<ProdutoDto> cadastrar(@RequestBody @Valid ProdutoForm form, UriComponentsBuilder uriBuilder){
		Produto produto = form.converter();
		produtoRepository.save(produto);
		
		URI uri = uriBuilder.path("/produto/{id}").buildAndExpand(produto.getId()).toUri();
		return ResponseEntity.created(uri).body(new ProdutoDto(produto));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDto> detalhar(@PathVariable ("id") long id){
		Optional<Produto> optional = produtoRepository.findById(id);
		if(optional.isPresent()) {
			return ResponseEntity.ok(new ProdutoDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoDto> atualizar(@PathVariable("id") long id, @RequestBody ProdutoForm form){
		Optional<Produto> optional = produtoRepository.findById(id);
		if(optional.isPresent()) {
			Produto produto = optional.get();
			if(form.getCodigo()!=null) produto.setCodigo(form.getCodigo());
			if(form.getDescricao()!=null)produto.setDescricao(form.getDescricao());
			if(form.getValorUnitario()!=null) produto.setValorUnitario(new BigDecimal(form.getValorUnitario()));
			produtoRepository.save(produto);
			return ResponseEntity.ok(new ProdutoDto(produto));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") long id){
		produtoRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
