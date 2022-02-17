package org.example.services.imp;

import lombok.RequiredArgsConstructor;
import org.example.domain.entity.*;
import org.example.domain.respository.Clientes;
import org.example.domain.respository.ItensPedido;
import org.example.domain.respository.Pedidos;
import org.example.domain.respository.Produtos;
import org.example.exception.PedidoNaoEncontradoException;
import org.example.exception.RegraNegocioException;
import org.example.rest.dto.ItemPedidoDTO;
import org.example.rest.dto.PedidoDTO;
import org.example.services.PedidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private Pedidos pedidosRepository;
    private Clientes clientesRepository;
    private Produtos produtosRepository;
    private ItensPedido itensPedido;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
      Integer idCliente = dto.getCliente();
      Cliente cliente = clientesRepository.findById(idCliente).orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));
        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItens(pedido, dto.getItens());
        pedidosRepository.save(pedido);
        itensPedido.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidosRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidosRepository.findById(id).map( pedido -> {
            pedido.setStatus(statusPedido);
            pedidosRepository.save(pedido);
            return pedido;
        }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }


    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> items){

        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar pedido sem itens");
        }

        return items.stream().map(dto -> {
            Produto produto = produtosRepository.findById(dto.getProduto())
                    .orElseThrow(() -> new RegraNegocioException("Codigo de produto invalido"));
           ItemPedido itemPedido = new ItemPedido();
           itemPedido.setQuantidade(dto.getQuantidade());
           itemPedido.setPedido(pedido);
           itemPedido.setProduto(produto);
           return itemPedido;
        }).collect(Collectors.toList());
    }
}
