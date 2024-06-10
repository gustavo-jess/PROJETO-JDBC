import static org.junit.Assert.*;
import br.unipar.Main;
import org.junit.*;
import java.sql.*;
import java.time.LocalDate;

public class MainTest {

    // limpa os daados depois de cada teste
    @After
    public void limparDados() {
        try (Connection conn = Main.connection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate("DELETE FROM usuarios");
            statement.executeUpdate("DELETE FROM produto");
            statement.executeUpdate("DELETE FROM cliente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCriarTabelaUsuario() {
        Main.criartabelaUsuario();
        try (Connection conn = Main.connection();
             Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS count FROM information_schema.tables WHERE table_name = 'usuarios'");
            assertTrue(rs.next());
            assertEquals(1, rs.getInt("count"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInserirUsuario() {
        Main.criartabelaUsuario();
        Main.inserirUsuario("Usuario", "31598", "GUi", LocalDate.now().toString());
        try (Connection conn = Main.connection();
             Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS count FROM usuarios WHERE username = 'Usuario'");
            assertTrue(rs.next());
            assertEquals(1, rs.getInt("count"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testListarTodosUsuarios() {
        Main.criartabelaUsuario();
        Main.inserirUsuario("usuario1", "senha1", "User One", "2000-01-01");
        Main.inserirUsuario("usuario2", "senha2", "User Two", "2000-01-02");
        Main.inserirUsuario("usuario3", "senha1", "User Three", "2000-01-03");
        assertEquals(3, Main.listarTodosUsuario());
    }

    @Test
    public void testInserirProduto() {
        Main.inserirProduto("Mouse", 280.00);
        try (Connection conn = Main.connection();
             Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS count FROM produto WHERE descricao = 'Mouse'");
            assertTrue(rs.next());
            assertEquals(1, rs.getInt("count"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testListarTodosProdutos() {
        Main.inserirProduto("Mouse", 280.00);
        Main.inserirProduto("Teclado", 100.00);
        assertEquals(2, Main.listarTodosProdutos());
    }


    @Test
    public void testInserirCliente() {
        Main.inserirCliente("Teste Cliente", "123.456.789-00");
        try (Connection conn = Main.connection();
             Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS count FROM cliente WHERE nome = 'Teste Cliente'");
            assertTrue(rs.next());
            assertEquals(1, rs.getInt("count"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testListarTodosClientes() {
        Main.inserirCliente("Cliente 1", "111.111.111-11");
        Main.inserirCliente("Cliente 2", "222.222.222-22");
        Main.inserirCliente("Cliente 3", "333.333.333-33");
        assertEquals(3, Main.listarTodosClientes());
    }

    @Test
    public void testExcluirTodosClientes() {
        Main.inserirCliente("Cliente1", "111.111.111-11");
        Main.inserirCliente("Cliente2", "222.222.222-22");

        assertEquals(2, Main.listarTodosClientes());

        Main.excluirTodosClientes();

        assertEquals(0, Main.listarTodosClientes());
    }

    @Test
    public void testExcluirTodosProdutos() {
        Main.inserirProduto("Produto1", 100.00);
        Main.inserirProduto("Produto2", 200.00);

        assertEquals(2, Main.listarTodosProdutos());

        Main.excluirTodosProdutos();

        assertEquals(0, Main.listarTodosProdutos());
    }

    @Test
    public void testExcluirTodosUsuarios() {
        Main.inserirUsuario("Usuario1", "senha1", "Nome1", "2000-01-01");
        Main.inserirUsuario("Usuario2", "senha2", "Nome2", "2000-02-02");

        assertEquals(2, Main.listarTodosUsuario());

        Main.excluirTodosUsuarios();

        assertEquals(0, Main.listarTodosUsuario());
    }
}



