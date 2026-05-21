package application;

import java.sql.Connection;
import db.DB;

public class TesteConexao {
    public static void main(String[] args) {

        System.out.println("A ligar à base de dados através do db.properties...");

        Connection conn = DB.getConnection();

        System.out.println("Conectado com sucesso! Arquitetura JDBC isolada.");


        DB.closeConnection();
        System.out.println("Conexão encerrada.");
    }
}