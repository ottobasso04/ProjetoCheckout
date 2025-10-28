package org.example;

import org.example.Cupom;

public class Pedido {

    private double Valor;
    private String Categoria;
    private String Regiao;
    private int Qtd;
    private int Estoque;
    private boolean AtividadePedido;
    private boolean Cupom;

    public Pedido(double valor, String categoria, String regiao,
                  int qtd, int estoque, boolean atividadePedido, boolean cupom) {
        this.Valor = valor;
        this.Categoria = categoria;
        this.Regiao = regiao;
        this.Qtd = qtd;
        this.Estoque = estoque;
        this.AtividadePedido = atividadePedido;
        this.Cupom = cupom;
    }

    public double getValor() {
        return Valor;
    }

    public void setValor(double valor) {
        Valor = valor;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getRegiao() {
        return Regiao;
    }

    public void setRegiao(String regiao) {
        Regiao = regiao;
    }

    public int getQtd() {
        return Qtd;
    }

    public void setQtd(int qtd) {
        Qtd = qtd;
    }

    public boolean isAtividadePedido() {
        return AtividadePedido;
    }

    public void setAtividadePedido(boolean atividadePedido) {
        AtividadePedido = atividadePedido;
    }

    public boolean isCupom() {
        return Cupom;
    }

    public void setCupom(boolean cupom) {
        Cupom = cupom;
    }

    public int getEstoque() {
        return Estoque;
    }

    public void setEstoque(int estoque) {
        Estoque = estoque;
    }
}
