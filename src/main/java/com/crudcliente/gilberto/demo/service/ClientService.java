package com.crudcliente.gilberto.demo.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crudcliente.gilberto.demo.dto.ClientDto;
import com.crudcliente.gilberto.demo.entities.Client;
import com.crudcliente.gilberto.demo.repository.ClientRepository;
import com.crudcliente.gilberto.demo.service.exception.DatabaseException;
import com.crudcliente.gilberto.demo.service.exception.ResourceNotFoundException;


@Service
public class ClientService {
	
	@Autowired	
	private ClientRepository ClientRepository;
	
	@Transactional(readOnly=true)
	public List<ClientDto> findAll(){		
		List<Client> list =ClientRepository.findAll();		
		return list.stream().map(x -> new ClientDto(x)).collect(Collectors.toList());	
	}
	
	@Transactional(readOnly=true)
	public ClientDto findById(Long id) {
		Optional<Client> obj = ClientRepository.findById(id);
		Client entity = obj.orElseThrow(()->new ResourceNotFoundException("Id não encontrado."));
		return new ClientDto(entity);	
	}

	@Transactional
	public ClientDto insert(ClientDto dto) {
		
    	Client entity = new Client();
    	copiaDtoParaEntity(dto, entity);
		entity = ClientRepository.save(entity);
		return new ClientDto(entity);
	}
    
    @Transactional
	public ClientDto update(Long id,ClientDto dto) {
    	Client entity = ClientRepository.getById(id);
    	try {
	    	copiaDtoParaEntity(dto, entity);
	    	entity = ClientRepository.save(entity);
    	}
    	catch (EntityNotFoundException e) {
    		throw new ResourceNotFoundException("Id não encotrado");	    		
        }
    	
		return new ClientDto(entity);
	}

	public void delete(Long id) {
		try {
			ClientRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encotrado "+id );    		
		}
		catch (DataIntegrityViolationException d) {
			throw new  DatabaseException("Integridade referencial violada ");
		}

	}

	public Page<ClientDto> findAllPaged(PageRequest pageRequest) {
		
		Page<Client> list = ClientRepository.findAll(pageRequest);
		
		return list.map(x->new ClientDto(x));
		
	}

	private void copiaDtoParaEntity(ClientDto dto,Client entity) {
		
		entity.setNome(dto.getNome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		
	}
}
