package br.com.redesocial_livros.dto;

/**
 *
 * @author Barbara
 */
public class LivroDTO {

    private String titulo, isbn, editora;
    private int ano, id_livro;

    public LivroDTO(int id_livro, String titulo) {
        this.titulo = titulo;
        this.id_livro = id_livro;
    }

    public LivroDTO() {
    }
    
    
    public int getId_livro() {
        return id_livro;
    }

    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }
}
