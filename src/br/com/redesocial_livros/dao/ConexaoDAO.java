package br.com.redesocial_livros.dao;

import java.sql.*;

/**
 *
 * @author Barbara
 */
public class ConexaoDAO {

    public static Connection con = null; //Atributo que guarda a conexão com o db

    public ConexaoDAO() {
        //Construtor
    }

    public static void ConnectDB() {
        try {
            String dsn = "redesocial_livros";
            String user = "postgres";
            String senha = "postdba";

            DriverManager.registerDriver(new org.postgresql.Driver());
            String url = "jdbc:postgresql://localhost:5432/" + dsn;
            con = DriverManager.getConnection(url, user, senha);
            con.setAutoCommit(false);

            if (con == null) {
                System.out.println("erro ao abrir o banco");
            }
        } catch (Exception e) {
            System.out.println("Problema ao abrir a base de dados\n" + e.getMessage());
        }
    }

    public static void CloseDB() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.println("Erro ao fechar a base de dados. " + e.getMessage());
        }

    }
}
