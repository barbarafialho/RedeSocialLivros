/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.redesocial_livros.dao;

import br.com.redesocial_livros.dto.AvaliarDTO;
import br.com.redesocial_livros.dto.LivroDTO;
import br.com.redesocial_livros.dto.UsuarioDTO;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Barbara
 */
public class AvaliarDAO {

    public AvaliarDAO() {
    }

    private ResultSet rs = null;
    private Statement stmt = null;

//        id_usuario integer,
//	id_livro integer,
//	lendo integer,
//	queroLer integer,
//	lido integer,
//	comentario varchar
    public boolean inserir(LivroDTO l_dto, UsuarioDTO u_dto, AvaliarDTO a_dto) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();
            String comando = "";
            comando = "insert into avaliar (id_usuario, id_livro, lendo, queroLer, lido, comentario) values ("
                    + u_dto.getId_usuario() + ", "
                    + l_dto.getId_livro() + ", "
                    + a_dto.getLendo() + ", "
                    + a_dto.getQueroLer() + ", "
                    + a_dto.getLido() + ", "
                    + "'" + a_dto.getComentario() + "')";
            stmt.execute(comando); //Executa o comando no bd
            ConexaoDAO.con.commit(); //Da um commit no bd
            stmt.close(); //Fecha o statement
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir (DAO) " + e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB(); //Fecha o bd
        }
    }

    public boolean alterar(LivroDTO l_dto, UsuarioDTO u_dto, AvaliarDTO a_dto) {
        try {
            ConexaoDAO.ConnectDB();
            stmt = ConexaoDAO.con.createStatement();
            String comando = "update avaliar set "
                    + "lendo = " + a_dto.getLendo() + ", "
                    + "queroLer = " + a_dto.getQueroLer() + ", "
                    + "lido = " + a_dto.getLido() + ", "
                    + "comentario = '" + a_dto.getComentario() + "' "
                    + "where id_livro = " + l_dto.getId_livro() + " and id_usuario = " + u_dto.getId_usuario();
            stmt.execute(comando); //Executa o comando no bd
            ConexaoDAO.con.commit(); //Da um commit no bd
            stmt.close(); //Fecha o statement
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao alterar (DAO) " + e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB(); //Fecha o bd
        }
    }

    public boolean excluir(UsuarioDTO u_dto, LivroDTO l_dto) {
        try {
            ConexaoDAO.ConnectDB(); //Chama o método de abrir o bd
            stmt = ConexaoDAO.con.createStatement(); //Instancia o statement para fazer as coisas no bd
            String comand = "delete from avaliar where id_usuario = " + u_dto.getId_usuario() + " and id_livro = " + l_dto.getId_livro();
            stmt.execute(comand);
            ConexaoDAO.con.commit();
            stmt.close();
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao excluir (DAO) " + e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB();
        }

    }

    public ResultSet consultar(UsuarioDTO u_dto, LivroDTO l_dto, int opc) {
        try {
            ConexaoDAO.ConnectDB(); //Chama o método de abrir o bd
            stmt = ConexaoDAO.con.createStatement(); //Instancia o statement para fazer as coisas no bd
            String comando = "";
            switch (opc) {
                case 1: //por TITULO
                    comando = "select u.nome, l.titulo, av.* from livro l join avaliar av on l.id_livro = av.id_livro join usuario u on u.id_usuario = av.id_usuario "
                            + "where av.id_usuario = " + u_dto.getId_usuario() + " and l.titulo ilike '%" + l_dto.getTitulo() + "%' order by titulo";
                    break;
                case 2: //por ID
                    comando = "select u.nome as usuario_nome, l.titulo, av.* from livro l join avaliar av on l.id_livro = av.id_livro join usuario u on u.id_usuario = av.id_usuario "
                            + "where av.id_usuario = " + u_dto.getId_usuario() + " and l.id_livro = " + l_dto.getId_livro();
                    break;
                case 3: //para a tela ver todas as avaliações
                    comando = "select u.nome as usuario_nome, l.titulo, a.nome as autor_nome, av.* from usuario u join avaliar av on av.id_usuario = u.id_usuario inner join livro l \n"
                            + "	on av.id_livro = l.id_livro inner join autor a on a.id_autor = l.id_autor where l.titulo ilike '%" + l_dto.getTitulo() + "%' and lido = 1"
                            + " order by titulo";
                    break;
            }

            rs = stmt.executeQuery(comando);
            return rs;
        } catch (Exception e) {
            System.out.println("erro" + e.getMessage());
            return rs;
        }
    }
}
