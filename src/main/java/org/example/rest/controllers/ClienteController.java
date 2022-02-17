package org.example.rest.controllers;

import org.example.domain.entity.Cliente;
import org.example.domain.respository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {


    private Clientes clientes;

    public ClienteController(Clientes clientes){ // poderia ter usando o Autowired
        this.clientes = clientes;
    }

    @GetMapping("{id}")
    public Cliente findClienteById(@PathVariable Integer id){
        return clientes.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Missing cliente"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody @Valid Cliente cliente){
        return clientes.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete ( @PathVariable Integer id ){
        clientes.findById(id).map(cliente -> {
            clientes.delete(cliente);
            return cliente;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Missing cliente"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @Valid @RequestBody Cliente cliente){
        clientes.findById(id).map(clienteExistente -> {
            cliente.setId(clienteExistente.getId());
            clientes.save(cliente);
            return clienteExistente;
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Missing cliente"));
    }

    @GetMapping
    public List<Cliente> find(Cliente filtro){
       ExampleMatcher matcher = ExampleMatcher
               .matching()
               .withIgnoreCase()
               .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
       Example example = Example.of(filtro, matcher);
       return clientes.findAll(example);
    }

}
