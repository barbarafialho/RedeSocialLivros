/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.redesocial_livros.ctr;

import br.com.redesocial_livros.dao.ConexaoDAO;
import br.com.redesocial_livros.dao.UsuarioDAO;
import br.com.redesocial_livros.dto.UsuarioDTO;
import java.sql.ResultSet;


/**
 *
 * @author Barbara
 */
public class UsuarioCTR {
    
    UsuarioDAO dao = new UsuarioDAO();

    public UsuarioCTR() {
    }
    
    public String inserir(UsuarioDTO dto) {
        try {
            if (dao.inserir(dto)) {
                return "USUÁRIO CADASTRADO COM SUCESSO.";
            } else {
                return "USUÁRIO NÃO CADASTRADO.";
            }
        } catch (Exception e) {
            return "LIVRO NÃO CADASTRADO.";
        }
    }

    public ResultSet consultar(UsuarioDTO dto, int opc) {
        ResultSet rs = null; //Recebe o resultado da consulta
        rs = dao.consultar(dto, opc);
        return rs;
    }

    public String alterar(UsuarioDTO dto) {
        try {
            if (dao.alterar(dto)) {
                return "USUÁRIO ALTERADO COM SUCESSO.";
            } else {
                return "ERRO AO ALTERAR.";
            }
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return "ERRO AO ALTERAR.";
        }
    }
    
    public String excluir(UsuarioDTO dto){
        try {
            if (dao.excluir(dto)) {
                return "USUÁRIO EXCLUIDO COM SUCESSO.";
            } else {
                return "ERRO AO EXCLUIR.";
            }
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return "ERRO AO EXCLUIR.";
        }
    }
    
    public int logar(UsuarioDTO dto){
        return dao.logar(dto);
    }

    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }
    
}
