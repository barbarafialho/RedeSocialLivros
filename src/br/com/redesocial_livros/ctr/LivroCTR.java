package br.com.redesocial_livros.ctr;

import br.com.redesocial_livros.dto.LivroDTO;
import br.com.redesocial_livros.dao.ConexaoDAO;
import br.com.redesocial_livros.dao.LivroDAO;
import br.com.redesocial_livros.dto.AutorDTO;
import java.sql.ResultSet;

/**
 *
 * @author Barbara
 */
public class LivroCTR {

    LivroDAO dao = new LivroDAO();

    public LivroCTR() {
        //Construtor
    }

    public String inserir(LivroDTO dto, AutorDTO a_dto) {
        try {
            if (dao.inserir(dto, a_dto)) {
                return "LIVRO CADASTRADO COM SUCESSO.";
            } else {
                return "LIVRO NÃO CADASTRADO.";
            }
        } catch (Exception e) {
            return "LIVRO NÃO CADASTRADO.";
        }
    }

    public ResultSet consultar(LivroDTO dto, int opc) {
        ResultSet rs = null; //Recebe o resultado da consulta
        rs = dao.consultar(dto, opc);
        return rs;
    }

    public String alterar(LivroDTO dto, AutorDTO a_dto) {
        try {
            if (dao.alterar(dto, a_dto)) {
                return "LIVRO ALTERADO COM SUCESSO.";
            } else {
                return "ERRO AO ALTERAR.";
            }
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return "ERRO AO ALTERAR.";
        }
    }
    
    public String excluir(LivroDTO dto){
        try {
            if (dao.excluir(dto)) {
                return "LIVRO EXCLUIDO COM SUCESSO.";
            } else {
                return "ERRO AO EXCLUIR.";
            }
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return "ERRO AO EXCLUIR.";
        }
    }

    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }

}
