/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.redesocial_livros.ctr;

import br.com.redesocial_livros.dao.AvaliarDAO;
import br.com.redesocial_livros.dao.ConexaoDAO;
import br.com.redesocial_livros.dto.AvaliarDTO;
import br.com.redesocial_livros.dto.LivroDTO;
import br.com.redesocial_livros.dto.UsuarioDTO;
import java.sql.ResultSet;

/**
 *
 * @author Barbara
 */
public class AvaliarCTR {

    AvaliarDAO dao = new AvaliarDAO();

    public AvaliarCTR() {
    }

    public String inserir(LivroDTO l_dto, UsuarioDTO u_dto, AvaliarDTO a_dto) {
        try {
            if (dao.inserir(l_dto, u_dto, a_dto)) {
                return "AVALIAÇÃO CADASTRADA COM SUCESSO.";
            } else {
                return "AVALIAÇÃO NÃO CADASTRADA.";
            }
        } catch (Exception e) {
            return "AVALIAÇÃO NÃO CADASTRADA.";
        }
    }

    public String alterar(LivroDTO l_dto, UsuarioDTO u_dto, AvaliarDTO a_dto) {
        try {
            if (dao.alterar(l_dto, u_dto, a_dto)) {
                return "AVALIAÇÃO ALTERADA COM SUCESSO.";
            } else {
                return "AVALIAÇÃO NÃO ALTERADA.";
            }
        } catch (Exception e) {
            return "AVALIAÇÃO NÃO ALTERADA.";
        }
    }    
    
    public String excluir(UsuarioDTO u_dto, LivroDTO l_dto){
        try {
            if (dao.excluir(u_dto, l_dto)) {
                return "AVALIAÇÃO EXCLUIDA COM SUCESSO.";
            } else {
                return "ERRO AO EXCLUIR.";
            }
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return "ERRO AO EXCLUIR.";
        }
    }

    public ResultSet consultar(UsuarioDTO u_dto, LivroDTO l_dto, int opc) {
        ResultSet rs = null; //Recebe o resultado da consulta
        rs = dao.consultar(u_dto, l_dto, opc);
        return rs;
    }

    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }
}
