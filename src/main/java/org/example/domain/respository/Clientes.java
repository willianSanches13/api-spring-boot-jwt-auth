package org.example.domain.respository;

import org.example.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Clientes extends JpaRepository<Cliente, Integer> {

}
