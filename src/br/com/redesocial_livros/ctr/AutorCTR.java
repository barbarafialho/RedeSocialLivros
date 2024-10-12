/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.redesocial_livros.ctr;

import br.com.redesocial_livros.dao.AutorDAO;
import br.com.redesocial_livros.dao.ConexaoDAO;
import br.com.redesocial_livros.dto.AutorDTO;
import java.sql.ResultSet;


/**
 *
 * @author Barbara
 */
public class AutorCTR {
    
    AutorDAO dao = new AutorDAO();

    public AutorCTR() {
    }
    
    public String inserir(AutorDTO dto) {
        try {
            if (dao.inserir(dto)) {
                return "AUTOR CADASTRADO COM SUCESSO.";
            } else {
                return "AUTOR NÃO CADASTRADO.";
            }
        } catch (Exception e) {
            return "AUTOR NÃO CADASTRADO.";
        }
    }

    public ResultSet consultar(AutorDTO dto, int opc) {
        ResultSet rs = null; //Recebe o resultado da consulta
        rs = dao.consultar(dto, opc);
        return rs;
    }

    public String alterar(AutorDTO dto) {
        try {
            if (dao.alterar(dto)) {
                return "AUTOR ALTERADO COM SUCESSO.";
            } else {
                return "ERRO AO ALTERAR.";
            }
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return "ERRO AO ALTERAR.";
        }
    }
    
    public String excluir(AutorDTO dto){
        try {
            if (dao.excluir(dto)) {
                return "AUTOR EXCLUIDO COM SUCESSO.";
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
