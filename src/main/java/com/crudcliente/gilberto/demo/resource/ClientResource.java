package com.crudcliente.gilberto.demo.resource;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.crudcliente.gilberto.demo.dto.ClientDto;
import com.crudcliente.gilberto.demo.service.ClientService;


@RestController
@RequestMapping(value ="/clients")
public class ClientResource {
	
	@Autowired
	private ClientService clientService;

	@GetMapping
	public ResponseEntity<Page<ClientDto>> finddAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy);
		
		Page<ClientDto> list = clientService.findAllPaged(pageRequest);		
		return ResponseEntity.ok().body(list);				
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ClientDto> findById(@PathVariable Long id){
		ClientDto dto = clientService.findById(id);		
		return ResponseEntity.ok().body(dto);				
	} 
	
	@PostMapping
	public ResponseEntity<ClientDto> insert(@RequestBody ClientDto dto){
		
		dto = clientService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{d}")
				  .buildAndExpand(dto.getId()).toUri();		
		return ResponseEntity.created(uri).body(dto);				
	}  
	@PutMapping (value = "/{id}")
	public ResponseEntity<ClientDto> update(@PathVariable Long id,@RequestBody ClientDto dto){
		
		dto = clientService.update(id,dto);
		return ResponseEntity.ok().body(dto);				
	}  
	
	@DeleteMapping (value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id){
		
		clientService.delete(id);
		return ResponseEntity.noContent().build();				
	}  
	
}	