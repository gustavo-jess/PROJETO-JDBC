package br.unipar;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        criarTabelaUsuario();

       inserirUsuario("Jesss", "741963", "Gustavo","2005-05-02");

        listarTodosUsuario();

    }

    private static final String url = "jdbc:postgresql://localhost:5432/Exemplo1";
    private static final String user = "postgres";
    private static final String password = "admin123";


    public static Connection connection() throws SQLException {
        //localhost -> Onde esta o banco
        //5432 -> porta padrão do banco
        return DriverManager.getConnection(url, user, password);


    }

    public static void criarTabelaUsuario() throws SQLException {
        try {
            Connection conn = connection();

            Statement statement = conn.createStatement();
            String sql = " CREATE TABLE IF NOT EXISTS usuarios ("
                    + " codigo SERIAL PRIMARY KEY,"
                    + " username VARCHAR(50) UNIQUE NOT NULL,"
                    + " password VARCHAR(300) NOT NULL,"
                    + " nome VARCHAR(50) NOT NULL,"
                    + " nascimento DATE)";

            statement.executeUpdate(sql);
            System.out.println("TABELA CRIADA!");


        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void inserirUsuario(String username, String password, String nome, String datanascimento) {
        try {
            Connection conn = connection();

            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO usuarios (username, password, nome, nascimento)"
                            + " VALUES (?, ?, ?, ?)"
            );

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nome);
            preparedStatement.setDate(4, java.sql.Date.valueOf(datanascimento));

            preparedStatement.executeUpdate();

            System.out.println("usuario inserido!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarTodosUsuario() {

        try {
            Connection conn = connection();

            Statement statement = conn.createStatement();
            statement.executeQuery("SELECT * FROM usuarios");

            ResultSet rs = statement.executeQuery("SELECT * FROM usuarios");
            while (rs.next()) {

                System.out.println(rs.getInt("codigo") + " " + rs.getString("username") + " ");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
