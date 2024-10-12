/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.redesocial_livros.dao;

import br.com.redesocial_livros.dto.AutorDTO;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 *
 * @author Barbara
 */
public class AutorDAO {

    public AutorDAO() {
    }

    private ResultSet rs = null; //Atributo para fazer consultas
    private Statement stm = null; //Atributo para manipular o bd
    SimpleDateFormat data_format = new SimpleDateFormat("dd/mm/yyyy");

    public boolean inserir(AutorDTO dto) {
        try {
            ConexaoDAO.ConnectDB(); //Chama o método de abrir o bd
            stm = ConexaoDAO.con.createStatement(); //Instancia o statement para fazer as coisas no bd
            String comand = "insert into autor (nome, genero, nacionalidade, dataNasc) values ("
                    + "'" + dto.getNome() + "', "
                    + "'" + dto.getGenero() + "', "
                    + "'" + dto.getNacionalidade() + "', "
                    + "to_date('" + data_format.format(dto.getDataNasc()) + "', 'dd/mm/yyyy')"
                    + ")";
            stm.execute(comand.toUpperCase()); //Executa o comando no bd
            ConexaoDAO.con.commit(); //Da um commit no bd
            stm.close(); //Fecha o statement
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir (DAO) " + e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB(); //Fecha o bd
        }
    }

    public ResultSet consultar(AutorDTO dto, int opc) {
        try {
            ConexaoDAO.ConnectDB(); //Chama o método de abrir o bd
            stm = ConexaoDAO.con.createStatement(); //Instancia o statement para fazer as coisas no bd
            String comand = "";
            switch (opc) {
                case 1:
                    comand = "select id_autor, nome from autor where nome like '%" + dto.getNome() + "%' "
                            + "order by nome";
                    break;
                case 2:
                    comand = "select id_autor, nome, genero, nacionalidade, to_char(dataNasc, 'dd/mm/yyyy') as dataNasc from autor where id_autor = " + dto.getId_autor();
                    break;
                case 3:
                    comand = "select id_autor, nome from autor";
                    break;
            }
            rs = stm.executeQuery(comand.toUpperCase());
            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return rs;
        }
    }

    public boolean alterar(AutorDTO dto) {
        try {
            ConexaoDAO.ConnectDB(); //Chama o método de abrir o bd
            stm = ConexaoDAO.con.createStatement(); //Instancia o statement para fazer as coisas no bd
            String comand = "update autor set "
                    + "nome = '" + dto.getNome() + "', "
                    + "genero = '" + dto.getGenero() + "', "
                    + "nacionalidade = '" + dto.getNacionalidade() + "', "
                    + "dataNasc = to_date('" + data_format.format(dto.getDataNasc()) + "', 'dd/mm/yyyy') "
                    + "where id_autor = " + dto.getId_autor();
            stm.execute(comand.toUpperCase());
            ConexaoDAO.con.commit();
            stm.close();
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao alterar (DAO) " + e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB();
        }
    }

    public boolean excluir(AutorDTO dto) {
        try {
            ConexaoDAO.ConnectDB(); //Chama o método de abrir o bd
            stm = ConexaoDAO.con.createStatement(); //Instancia o statement para fazer as coisas no bd
            String comand = "delete from autor where id_autor = " + dto.getId_autor();
            stm.execute(comand);
            ConexaoDAO.con.commit();
            stm.close();
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao excluir (DAO) " + e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB();
        }

    }

}
