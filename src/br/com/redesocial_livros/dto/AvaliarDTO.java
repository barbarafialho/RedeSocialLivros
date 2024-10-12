/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.redesocial_livros.dto;


/**
 *
 * @author Barbara
 */
public class AvaliarDTO {
    private String comentario;
    private int lendo, queroLer, lido;
    
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getLendo() {
        return lendo;
    }

    public void setLendo(int lendo) {
        this.lendo = lendo;
    }

    public int getQueroLer() {
        return queroLer;
    }

    public void setQueroLer(int queroLer) {
        this.queroLer = queroLer;
    }

    public int getLido() {
        return lido;
    }

    public void setLido(int lido) {
        this.lido = lido;
    }
    
}
