package org.example.services;


import org.example.domain.entity.Pedido;
import org.example.domain.entity.StatusPedido;
import org.example.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar (PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
