package org.example;

import java.time.LocalDate;

public class Cupom {

    private LocalDate DataValidade;
    private double Desconto;

    public Cupom(LocalDate dataValidade, double desconto) {
        this.DataValidade = dataValidade;
        this.Desconto = desconto;
    }

    public LocalDate getDataValidade() {
        return DataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        DataValidade = dataValidade;
    }

    public double getDesconto() {
        return Desconto;
    }

    public void setDesconto(double desconto) {
        Desconto = desconto;
    }
}
