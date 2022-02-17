package org.example.rest.controllers;

import org.example.domain.entity.Produto;
import org.example.domain.respository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {


    private Produtos produtos;

    public ProdutoController(Produtos produtos){ // poderia ter usando o Autowired
        this.produtos = produtos;
    }

    @GetMapping("{id}")
    public Produto findProdutoById(@PathVariable Integer id){
        return produtos.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Missing Produto"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody @Valid Produto Produto){
        return produtos.save(Produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete ( @PathVariable Integer id ){
        produtos.findById(id).map(Produto -> {
            produtos.delete(Produto);
            return Produto;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Missing Produto"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @Valid @RequestBody Produto Produto){
        produtos.findById(id).map(ProdutoExistente -> {
            Produto.setId(ProdutoExistente.getId());
            produtos.save(Produto);
            return ProdutoExistente;
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Missing Produto"));
    }

    @GetMapping
    public List<Produto> find(Produto filtro){
       ExampleMatcher matcher = ExampleMatcher
               .matching()
               .withIgnoreCase()
               .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
       Example example = Example.of(filtro, matcher);
       return produtos.findAll(example);
    }

}
