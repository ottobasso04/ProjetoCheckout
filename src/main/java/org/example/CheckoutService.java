package org.example;
import java.time.LocalDate;
import java.util.Objects;

public class CheckoutService {

//Cupom de desconto
    private static final double CUPOM = 0.10;
    private static final double TETO_DESCONTO_CUPOM = 50;
    private static final LocalDate DATA_VALIDADE_CUPOM =
            LocalDate.of(2025, 5, 15);

//Imposto
    private static final double ICMS = 1.18;

//Frete
    private static final double FRETE_COMUM = 20;
    private static final double FRETE_INTERIOR = 30;
    private static final double FRETE_GRATIS = 0.0;

//Cashback
    private static final double CASHBACK = 0.01;

    public double CheckoutPedido (Pedido pedido){
        validar(pedido);
        double imposto = 0.0;
        double Desconto_Cupom = 0.0;
        double frete = 0.0;
        double cashback = 0.0;
        LocalDate hoje = LocalDate.now();

//Regra 1: Desconto do cupom
        if(pedido.isCupom() && (CUPOM * pedido.getValor()) < TETO_DESCONTO_CUPOM){
            Desconto_Cupom = (CUPOM * pedido.getValor());
        }

//Regra 2: Teto do desconto de cupom
        if(pedido.isCupom() && (CUPOM * pedido.getValor()) > TETO_DESCONTO_CUPOM){
            Desconto_Cupom = TETO_DESCONTO_CUPOM;
        }

//Regra 3: Data do cupom expirou
        if(DATA_VALIDADE_CUPOM.isBefore(hoje))
            Desconto_Cupom = 0;

//Regra 4: ICMS
        if(pedido.getCategoria().equals("eletrônico")){
            imposto += (pedido.getValor() * ICMS) - pedido.getValor();
        }

//Regra 6: Frete convencional
        if(pedido.getRegiao().equals("capital") || pedido.getRegiao().equals("Capital")){
            frete += frete + FRETE_COMUM;
        }

//Regra 7: Frete Interior
        if(pedido.getRegiao().equals("Interior") || pedido.getRegiao().equals("interior")){
            frete += frete + FRETE_INTERIOR;
        }

//Regra 5: Frete grátis
        if(pedido.getValor() >= 200){
            frete = FRETE_GRATIS;
        }

        //Pedido mínimo > 10R$
        if(pedido.getValor() < 10)
            throw new IllegalArgumentException("O valor do está abaixo do mínimo," +
                    " adicione mais itens ao seu pedido!");

        return pedido.getValor() - Desconto_Cupom + frete + imposto;
    }

    private void validar (Pedido pedido){
        if(!(pedido.isAtividadePedido())) throw new
                IllegalArgumentException("O produto está inativo!!!");

        if(pedido.getQtd() > pedido.getEstoque()) throw new
                IllegalArgumentException("O estoque do produto não condiz" +
                "com a quantidade do pedido");
    }

    public static void main(String[] args) {

        Pedido teste = new Pedido(400, "utensílio", "Interior",
                10, 15,true,true);
        Pedido teste2 = new Pedido(600,"eletrônico", "Interior",
                15, 20, true, true);
        Pedido teste3 = new Pedido(700,"eletrônico", "Interior",
                15, 20, true, true);
        Pedido teste4 = new Pedido(100,"eletrônico", "Capital",
                15, 20, true, true);

        CheckoutService cs = new CheckoutService();

        System.out.println("Checkout de teste: " + cs.CheckoutPedido(teste));
        System.out.println("Checkout de teste2: " + cs.CheckoutPedido(teste2));
        System.out.println("Checkout de teste3: " + cs.CheckoutPedido(teste3));
        System.out.println("Checkout de teste4: " + cs.CheckoutPedido(teste4));

        if(!teste2.isCupom()) {
            System.out.println("Seu Cashback é: " + (cs.CheckoutPedido(teste) * CASHBACK));
            System.out.println("Seu Cashback é: " + (cs.CheckoutPedido(teste2) * CASHBACK));
        }
        else{
            System.out.println("\nObrigado por comprar com a gente!");
        }
    }
}