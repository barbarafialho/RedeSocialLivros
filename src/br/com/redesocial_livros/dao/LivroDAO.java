package br.com.redesocial_livros.dao;

import br.com.redesocial_livros.dto.AutorDTO;
import br.com.redesocial_livros.dto.LivroDTO;
import java.sql.*;

/**
 *
 * @author Barbara
 */
public class LivroDAO {

    public LivroDAO() {
        //Construtor
    }

    private ResultSet rs = null; //Atributo para fazer consultas
    private Statement stm = null; //Atributo para manipular o bd

    public boolean inserir(LivroDTO dto, AutorDTO a_dto) {
        try {
            ConexaoDAO.ConnectDB(); //Chama o método de abrir o bd
            stm = ConexaoDAO.con.createStatement(); //Instancia o statement para fazer as coisas no bd
            String comand = "insert into livro (titulo, id_autor, isbn, ano, editora) values ("
                    + "'" + dto.getTitulo() + "', "
                    + "'" + a_dto.getId_autor() + "', "
                    + "'" + dto.getIsbn() + "', "
                    + dto.getAno() + ", "
                    + "'" + dto.getEditora() + "'"
                    + ")";
            stm.execute(comand.toUpperCase()); //Executa o comando no bd
            ConexaoDAO.con.commit(); //Da um commit no bd
            stm.close(); //Fecha o statement
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB(); //Fecha o bd
        }
    }

    public ResultSet consultar(LivroDTO dto, int opc) {
        try {
            ConexaoDAO.ConnectDB(); //Chama o método de abrir o bd
            stm = ConexaoDAO.con.createStatement(); //Instancia o statement para fazer as coisas no bd
            String comand = "";
            switch (opc) {
                case 1:
                    comand = "select id_livro, titulo from livro where titulo like '%" + dto.getTitulo() + "%' "
                            + "order by titulo";
                    break;
                case 2:
                    comand = "select l.*, a.nome from livro l join autor a on a.id_autor = l.id_autor where l.id_livro = " + dto.getId_livro();
                    break;
                case 3:
                    comand = "select id_livro, titulo from livro";
                    break;
                case 4:
                    comand = "select isbn from livro";
                    break;

            }
            rs = stm.executeQuery(comand.toUpperCase());
            return rs;
        } catch (Exception e) {
            System.out.println("Erro ao inserir (DAO) " + e.getMessage());
            return rs;
        }
    }

    public boolean alterar(LivroDTO dto, AutorDTO a_dto) {
        try {
            ConexaoDAO.ConnectDB(); //Chama o método de abrir o bd
            stm = ConexaoDAO.con.createStatement(); //Instancia o statement para fazer as coisas no bd
            String comand = "update livro set "
                    + "titulo = '" + dto.getTitulo() + "', "
                    + "id_autor = " + a_dto.getId_autor() + ", "
                    + "isbn = '" + dto.getIsbn() + "', "
                    + "ano = " + dto.getAno() + ", "
                    + "editora = '" + dto.getEditora() + "' "
                    + "where id_livro = " + dto.getId_livro();
            stm.execute(comand.toUpperCase());
            ConexaoDAO.con.commit();
            stm.close();
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao alterar (DAO): " + e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB();
        }
    }

    public boolean excluir(LivroDTO dto) {
        try {
            ConexaoDAO.ConnectDB(); //Chama o método de abrir o bd
            stm = ConexaoDAO.con.createStatement(); //Instancia o statement para fazer as coisas no bd
            String comand = "delete from livro where id_livro = " + dto.getId_livro();
            stm.execute(comand);
            ConexaoDAO.con.commit();
            stm.close();
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao excluir (DAO): " + e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB();
        }

    }
}
