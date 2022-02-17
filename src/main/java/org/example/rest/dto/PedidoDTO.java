package org.example.rest.dto;

import org.example.validation.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDTO {

    @NotNull(message = "Informe o codigo do cliente")
    private Integer cliente;
    @NotNull(message = "Campo total do pedido é obrigatório")
    private BigDecimal total;
    @NotEmpty(message = "Pedido não pode ser realizado sem itens")
    private List<ItemPedidoDTO> itens;

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }

}
