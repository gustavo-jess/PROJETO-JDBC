package br.unipar;

import java.sql.*;

public class Main {

    private static final String url = "jdbc:postgresql://localhost:5432/Exemplo1";
    private static final String user = "postgres";
    private static final String password = "admin123";

    public static void main(String[] args) {

        criartabelaUsuario();
        inserirUsuario("Guilherme", "1236231725", "Guilherme", "2004-03-06");
        listarTodosUsuario();
        inserirProduto("PC gamer", 3000.00);
        listarTodosProdutos();
        inserirCliente("Guilherme", "196.815.059.05");
        listarTodosClientes();

        excluirTodosClientes();
        excluirTodosProdutos();
        excluirTodosUsuarios();
    }

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void criartabelaUsuario() {
        try {
            Connection conn = connection();
            Statement statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS usuarios( "
                    + " codigo SERIAL PRIMARY KEY,"
                    + " username VARCHAR(50) UNIQUE NOT NULL, "
                    + " password VARCHAR(300) NOT NULL, "
                    + " nome VARCHAR(50) UNIQUE NOT NULL,"
                    + " nascimento DATE )";
            statement.execute(sql);
            System.out.println("TABELA DE USUÁRIO CRIADA COM SUCESSO!");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void inserirUsuario(String username, String password, String nome, String dataNascimento) {
        try {
            Connection conn = connection();
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO usuarios (username, password, nome, nascimento) "
                            + "VALUES (?, ?, ?, ?)"
            );
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nome);
            preparedStatement.setDate(4, java.sql.Date.valueOf(dataNascimento));
            preparedStatement.executeUpdate();
            System.out.println("USUÁRIO INSERIDO COM SUCESSO!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int listarTodosUsuario() {

        int i = 0; // conta quantos usuarios foram listados

        try {
            Connection conn = connection();
            Statement statement = conn.createStatement();
            System.out.println("Listando usuários");
            ResultSet result = statement.executeQuery("SELECT * FROM usuarios");
            while (result.next()) {
                System.out.println(result.getInt("codigo"));
                System.out.println(result.getString("username"));
                i++; // incrementa o contador
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i; // retorna o número total de usuarios listados
    }

    public static void inserirProduto(String descricao, double valor) {
        try {
            Connection conn = connection();
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO produto (descricao, valor) "
                            + "VALUES (?,?)"
            );
            preparedStatement.setString(1, descricao);
            preparedStatement.setDouble(2, valor);
            preparedStatement.executeUpdate();
            System.out.println("PRODUTO INSERIDO COM SUCESSO!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int listarTodosProdutos() {
        int i = 0; // conta quantos produtos foram listados
        try {
            Connection conn = connection();
            Statement statement = conn.createStatement();
            System.out.println("Listando produtos");
            ResultSet result = statement.executeQuery("SELECT * FROM produto");
            while (result.next()) {
                System.out.println(result.getInt("id_produto"));
                System.out.println(result.getString("descricao"));
                System.out.println(result.getDouble("valor"));
                i++; // incrementa o contador
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i; // retorna o número total de produtos listados
    }


    public static void inserirCliente(String nome, String cpf) {
        try {
            Connection conn = connection();
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO cliente (nome, cpf) "
                            + "VALUES (?, ?)"
            );
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, cpf);
            preparedStatement.executeUpdate();
            System.out.println("CLIENTE INSERIDO COM SUCESSO!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int listarTodosClientes() {
        int i = 0; // // conta quantos produtos foram listados
        try {
            Connection conn = connection();
            Statement statement = conn.createStatement();
            System.out.println("Listando clientes");
            ResultSet result = statement.executeQuery("SELECT * FROM cliente");
            while (result.next()) {
                System.out.println(result.getInt("id_cliente"));
                System.out.println(result.getString("nome"));
                System.out.println(result.getString("cpf"));
                i++; // incrementa o contador
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i; // retorna o número total de clientes listados
    }

    public static void excluirTodosClientes() {
        try {
            Connection conn = connection();
            Statement statement = conn.createStatement();
            int rowsAffected = statement.executeUpdate("DELETE FROM cliente");
            System.out.println(rowsAffected + " cliente(s) excluído(s) com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void excluirTodosProdutos() {
        try {
            Connection conn = connection();
            Statement statement = conn.createStatement();
            int rowsAffected = statement.executeUpdate("DELETE FROM produto");
            System.out.println(rowsAffected + " produto(s) excluído(s) com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void excluirTodosUsuarios() {
        try {
            Connection conn = connection();
            Statement statement = conn.createStatement();
            int rowsAffected = statement.executeUpdate("DELETE FROM usuarios");
            System.out.println(rowsAffected + " usuário(s) excluído(s) com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
