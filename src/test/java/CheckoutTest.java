import org.example.CheckoutService;
import org.example.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    private CheckoutService checkoutService;

    @BeforeEach
    void setUp() {
        // Instancia a classe a ser testada antes de cada teste
        checkoutService = new CheckoutService();
    }

    // --- TESTES DE VALIDAÇÃO (MÉTODO validar) ---

    @Test
    @DisplayName("Deve lançar exceção quando o produto está inativo")
    void deveLancarExcecaoParaProdutoInativo() {
        // Arrange (Preparação)
        Pedido pedidoInativo = new Pedido(100, "Livro",
                "Capital", 1, 10, false, false);

        // Act & Assert (Ação & Verificação)
        // Verifica se a exceção correta é lançada
        assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.CheckoutPedido(pedidoInativo);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando o estoque é insuficiente")
    void deveLancarExcecaoParaEstoqueInsuficiente() {
        // Arrange
        Pedido pedidoSemEstoque = new Pedido(100, "utensílio",
                "Capital", 11, 10, false, true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.CheckoutPedido(pedidoSemEstoque);
        });
    }

    // --- TESTES DE REGRAS DE NEGÓCIO (MÉTODO CheckoutPedido) ---

    @Test
    @DisplayName("Não deve aplicar cupom de desconto por estar expirado")
    void naoDeveAplicarCupomPorDataExpirada() {
        // Arrange
        Pedido pedidoComCupom = new Pedido(400, "utensílio",
                "Interior", 1, 10, true, true);

        // Act
        double total = checkoutService.CheckoutPedido(pedidoComCupom);

        // Assert:
        // Subtotal: 400
        // Desconto: 0 (pois o cupom é considerado expirado pela data atual)
        // Imposto: 0
        // Frete: 0 (valor >= 200)
        // Total: 400 - 0 + 0 + 0 = 400
        assertEquals(400.0, total);
    }

    @Test
    @DisplayName("Deve aplicar imposto ICMS para categoria 'eletrônico'")
    void deveAplicarIcmsParaCategoriaEletronico() {
        // Arrange
        Pedido pedidoEletronico = new Pedido(100, "eletrônico",
                "Capital", 10, 20, true, true);

        // Act
        double total = checkoutService.CheckoutPedido(pedidoEletronico);

        // Assert:
        // Subtotal: 100
        // Desconto: 0
        // Imposto: 18 (18% de 100)
        // Frete: 20
        // Total: 100 - 0 + 20 + 18 = 138
        assertEquals(138.0, total);
    }

    @Test
    @DisplayName("Não deve aplicar ICMS para categorias diferentes de 'eletrônico'")
    void naoDeveAplicarIcmsParaOutrasCategorias() {
        // Arrange
        Pedido pedidoNaoEletronico = new Pedido(100, "utensílio",
                "Capital", 1, 20, true, true);

        // Act
        double total = checkoutService.CheckoutPedido(pedidoNaoEletronico);

        // Assert:
        // Subtotal: 100
        // Desconto: 0
        // Imposto: 0
        // Frete: 20
        // Total: 100 - 0 + 20 + 0 = 120
        assertEquals(120.0, total);
    }

    @Test
    @DisplayName("Deve aplicar frete grátis para valor maior ou igual a 200")
    void deveAplicarFreteGratis() {
        // Arrange
        Pedido pedidoComFreteGratis = new Pedido(200, "utensílio",
                "INTERIOR", 1, 20, true, true);

        // Act
        double total = checkoutService.CheckoutPedido(pedidoComFreteGratis);

        // Assert: 200 (subtotal) + 0 (frete) = 200
        assertEquals(200.0, total);
    }

    @Test
    @DisplayName("Deve aplicar frete de R$ 20 para região 'Capital'")
    void deveAplicarFreteParaCapital() {
        // Arrange
        Pedido pedidoCapital = new Pedido(150, "utensílio",
                "Capital", 1, 20, true, true);

        // Act
        double total = checkoutService.CheckoutPedido(pedidoCapital);

        // Assert: 150 (subtotal) + 20 (frete) = 170
        assertEquals(170.0, total);
    }

    @Test
    @DisplayName("Deve aplicar frete de R$ 30 para região 'Interior'")
    void deveAplicarFreteParaInterior() {
        // Arrange
        Pedido pedidoInterior = new Pedido(150, "Roupas",
                "Interior", 1, 20, true, true);

        // Act
        double total = checkoutService.CheckoutPedido(pedidoInterior);

        // Assert: 150 (subtotal) + 30 (frete) = 180
        assertEquals(180.0, total);
    }

    @Test
    @DisplayName("Deve calcular corretamente um pedido com ICMS e Frete para Interior")
    void deveCalcularPedidoComplexoComIcmsEFrete() {
        // Arrange
        Pedido pedido = new Pedido(100, "eletrônico",
                "Interior", 1, 20, true, true);

        // Act
        double total = checkoutService.CheckoutPedido(pedido);

        // Assert:
        // Subtotal: 100
        // Desconto: 0
        // Imposto: 18 (18% de 100)
        // Frete: 30
        // Total: 100 - 0 + 30 + 18 = 148
        assertEquals(148.0, total);
    }

    @Test
    @DisplayName("Deve bloquear pedidos abaixo de 10R$ (antes dos impostos) ")
    void deveBloquearPedidoMínimo() {
        // Arrange
        Pedido pedidoAbaixo = new Pedido(8, "Roupas",
                "Interior", 1, 20, true, true);

        // Act
        double total = checkoutService.CheckoutPedido(pedidoAbaixo);

        assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.CheckoutPedido(pedidoAbaixo);
        });
    }
}