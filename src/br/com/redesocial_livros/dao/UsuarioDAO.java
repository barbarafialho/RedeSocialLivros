/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.redesocial_livros.dao;

import br.com.redesocial_livros.dto.UsuarioDTO;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Barbara
 */
public class UsuarioDAO {

    public UsuarioDAO() {
    }

    private ResultSet rs = null;
    private Statement stm = null;
    private static MessageDigest md = null;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static char[] hexCodes(byte[] text) {
        char[] hexOutput = new char[text.length * 3];
        String hexString;

        for (int i = 0; i < text.length; i++) {
            hexString = "00" + Integer.toHexString(text[i]);
            hexString.toUpperCase().getChars(hexString.length() - 3, hexString.length(), hexOutput, i * 3);
        }
        return hexOutput;
    }

    public static String criptografar(String pwd) {
        if (md != null) {
            return new String(hexCodes(md.digest(pwd.getBytes())));
        }
        return null;
    }

    public boolean inserir(UsuarioDTO dto) {
        try {
            ConexaoDAO.ConnectDB(); //Chama o método de abrir o bd
            stm = ConexaoDAO.con.createStatement(); //Instancia o statement para fazer as coisas no bd
            String comand = "insert into usuario (nome, cpf, genero, telefone, email, username, senha) values ("
                    + "'" + dto.getNome().toUpperCase() + "', "
                    + "'" + dto.getCpf() + "', "
                    + "'" + dto.getGenero() + "', "
                    + "'" + dto.getTelefone() + "', "
                    + "'" + dto.getEmail() + "', "
                    + "'" + dto.getUsername() + "', "
                    + "'" + criptografar(dto.getSenha()) + "'"
                    + ")";
            stm.execute(comand); //Executa o comando no bd
            ConexaoDAO.con.commit(); //Da um commit no bd
            stm.close(); //Fecha o statement
            return true;
        } catch (Exception e) {
            System.out.println("erro ao inserir: " + e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB(); //Fecha o bd
        }
    }

    public ResultSet consultar(UsuarioDTO dto, int opc) {
        try {
            ConexaoDAO.ConnectDB(); //Chama o método de abrir o bd
            stm = ConexaoDAO.con.createStatement(); //Instancia o statement para fazer as coisas no bd
            String comand = "";
            switch (opc) {
                case 1:
                    comand = "Select u.* "
                            + "from usuario u "
                            + "where nome like '%" + dto.getNome() + "%' "
                            + "order by u.nome";
                    break;
                case 2:
                    comand = "Select u.* "
                            + "from usuario u "
                            + "where u.id_usuario = " + dto.getId_usuario();
                    break;
                case 3:
                    comand = "select cpf from usuario order by cpf";
                    break;
            }
            rs = stm.executeQuery(comand.toUpperCase());
            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return rs;
        }
    }

    public boolean alterar(UsuarioDTO dto) {
        try {
            ConexaoDAO.ConnectDB(); //Chama o método de abrir o bd
            stm = ConexaoDAO.con.createStatement(); //Instancia o statement para fazer as coisas no bd
            String comand = "";
            comand = "update usuario set "
                    + "nome = '" + dto.getNome().toUpperCase() + "', "
                    + "cpf = '" + dto.getCpf() + "', "
                    + "genero = '" + dto.getGenero() + "', "
                    + "telefone = '" + dto.getTelefone() + "', "
                    + "email = '" + dto.getEmail() + "', "
                    + "username = '" + dto.getUsername() + "' ";
            if (dto.getSenha() != null) {
                comand += ", senha = '" + criptografar(dto.getSenha()) + "' ";
            }
            comand += "where id_usuario = " + dto.getId_usuario();
            stm.execute(comand);
            ConexaoDAO.con.commit();
            stm.close();
            return true;
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB();
        }
    }

    public boolean excluir(UsuarioDTO dto) {
        try {
            ConexaoDAO.ConnectDB(); //Chama o método de abrir o bd
            stm = ConexaoDAO.con.createStatement(); //Instancia o statement para fazer as coisas no bd
            String comand = "delete from usuario where id_usuario = " + dto.getId_usuario();
            stm.execute(comand);
            ConexaoDAO.con.commit();
            stm.close();
            return true;
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return false;
        } finally {
            ConexaoDAO.CloseDB();
        }

    }

    public int logar(UsuarioDTO dto) {
        try {
            ConexaoDAO.ConnectDB();
            stm = ConexaoDAO.con.createStatement();
            String comando = "select * from usuario where username = '" + dto.getUsername() + "'"
                    + "and senha = '" + criptografar(dto.getSenha()) + "'";
            rs = null;
            rs = stm.executeQuery(comando);
            if (rs.next()) {
                dto.setNome(rs.getString("nome"));
                dto.setId_usuario(rs.getInt("id_usuario"));
                return rs.getInt("id_usuario");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("ERRO AO LOGAR (DAO)" + e.getMessage());
            return 0;
        } finally {
            ConexaoDAO.CloseDB();
        }
    }
}
