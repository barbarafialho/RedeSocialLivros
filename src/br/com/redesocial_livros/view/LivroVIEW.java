package br.com.redesocial_livros.view;

import br.com.redesocial_livros.ctr.AutorCTR;
import br.com.redesocial_livros.ctr.LivroCTR;
import br.com.redesocial_livros.dto.AutorDTO;
import br.com.redesocial_livros.dto.LivroDTO;
import java.awt.Dimension;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 *
 * @author Barbara
 */
public class LivroVIEW extends javax.swing.JInternalFrame {

    LivroDTO dto = new LivroDTO();
    LivroCTR ctr = new LivroCTR();
    AutorDTO a_dto = new AutorDTO();
    AutorCTR a_ctr = new AutorCTR();
    int grav_alt; //Variavel usada para saber se está sendo alterado ou gravado
    ResultSet rs; //Variavel para preencher a tabela e os campos
    DefaultTableModel tabela_consulta_livro; //Variavel para guardar o modelo da tabela
    ArrayList<AutorDTO> autores = new ArrayList<AutorDTO>();

    /**
     * Creates new form LivroVIEW
     */
    public LivroVIEW() {
        initComponents();
        liberaCampos(false);
        liberaBotoes(true, false, false, false, true);
        tabela_consulta_livro = (DefaultTableModel) jtl_consulta_livro.getModel(); //"jtl_consulta_livro" é o nome da tabela no layout
        preencheComboAutor();
    }

    public void setPosition() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    private void liberaCampos(boolean a) {
        titulo.setEnabled(a);
        combo_autor.setEnabled(a);
        ano.setEnabled(a);
        editora.setEnabled(a);
        isbn.setEnabled(a);
    }

    private void liberaBotoes(boolean a, boolean b, boolean c, boolean d, boolean e) {
        btnNovo.setEnabled(a);
        btnSalvar.setEnabled(b);
        btnCancelar.setEnabled(c);
        btnExcluir.setEnabled(d);
        btnSair.setEnabled(e);
    }

    private void limpaCampos() {
        titulo.setText("");
        combo_autor.removeAllItems();
        ano.setText("");
        editora.setText("");
        isbn.setText("");

    }

    private void preencheComboAutor() {
        try {
            rs = a_ctr.consultar(a_dto, 3);
            combo_autor.removeAllItems();
            while (rs.next()) {
                a_dto.setId_autor(rs.getInt("id_autor"));
                a_dto.setNome(rs.getString("nome"));
                combo_autor.addItem(a_dto.getNome());
                autores.add(new AutorDTO(rs.getInt("id_autor"), rs.getString("nome")));
            }
        } catch (Exception e) {
            System.out.println("Erro ao preencher combobox " + e.getMessage());
        }
    }

    public void salvar() {
        try {
            dto.setTitulo(titulo.getText());
            a_dto.setId_autor(autores.get(combo_autor.getSelectedIndex()).getId_autor());
            dto.setAno(Integer.valueOf(ano.getText()));
            dto.setEditora(editora.getText());
            dto.setIsbn(isbn.getText());

            JOptionPane.showMessageDialog(null, ctr.inserir(dto, a_dto));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void preencheTabela(String titulo) {
        try {
            tabela_consulta_livro.setNumRows(0); //Limpa as linhas
            dto.setTitulo(titulo);
            rs = ctr.consultar(dto, 1); //1 é a pesquisa por nome
            while (rs.next()) {
                tabela_consulta_livro.addRow(new Object[]{rs.getString("id_livro"), rs.getString("titulo")});
            }
        } catch (Exception e) {
            System.out.println("ERRO NA TABELA: " + e.getMessage());
        } finally {
            ctr.CloseDB();
        }
    }

    private void preencheCampos(int id_livro) {
        try {
            dto.setId_livro(id_livro);
            rs = ctr.consultar(dto, 2); //2 é a pesquisa por ID
            if (rs.next()) {
                limpaCampos();
                titulo.setText(rs.getString("titulo"));
                combo_autor.removeAllItems();
                a_dto.setId_autor(rs.getInt("id_autor"));
                a_dto.setNome(rs.getString("nome"));
                combo_autor.addItem(a_dto.getNome());
                ano.setText(rs.getString("ano"));
                editora.setText(rs.getString("editora"));
                isbn.setText(rs.getString("isbn"));
                grav_alt = 2;
                liberaCampos(true);
            }
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        } finally {
            ctr.CloseDB();
        }
    }

    private void alterar() {
        try {
            dto.setTitulo(titulo.getText());
            a_dto.setId_autor(autores.get(combo_autor.getSelectedIndex()).getId_autor());
            dto.setAno(Integer.valueOf(ano.getText()));
            dto.setEditora(editora.getText());
            dto.setIsbn(isbn.getText());

            JOptionPane.showMessageDialog(null, ctr.alterar(dto, a_dto));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void excluir() {
        if (JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "AVISO", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, ctr.excluir(dto));
        }
    }

    private boolean validaISBN() {
        try {
            rs = ctr.consultar(dto, 4);
            while (rs.next()) {
                if (rs.getString("isbn").equals(isbn.getText())) {
                    JOptionPane.showMessageDialog(null, "Esse ISBN já foi cadastrado!");
                    isbn.requestFocus();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Erro ao validar ISBN: " + e.getMessage());
            return false;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        titulo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ano = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        editora = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        isbn = new javax.swing.JTextField();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnPesquisar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        pesquisa_titulo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtl_consulta_livro = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        combo_autor = new javax.swing.JComboBox<>();

        jLabel3.setText("jLabel3");

        jLabel8.setText("Título:");

        jLabel1.setText("Autor:");

        jLabel2.setText("Ano:");

        ano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anoActionPerformed(evt);
            }
        });

        jLabel5.setText("Editora:");

        jLabel6.setText("ISBN:");

        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/redesocial_livros/view/imagens/pesquisar.png"))); // NOI18N
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Consultar livros");

        jLabel9.setText("Pesquisar por título do livro:");

        jtl_consulta_livro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Título"
            }
        ));
        jtl_consulta_livro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtl_consulta_livroMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtl_consulta_livro);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pesquisa_titulo)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(0, 120, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisar)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(89, 89, 89))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pesquisa_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnPesquisar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("LIVRO");

        combo_autor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(titulo)
                                    .addComponent(editora)
                                    .addComponent(isbn, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ano, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(combo_autor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnNovo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExcluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSair)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(310, 310, 310)
                        .addComponent(jLabel10)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(combo_autor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(isbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(editora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar)
                    .addComponent(btnExcluir)
                    .addComponent(btnSair))
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void anoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_anoActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        liberaCampos(true);
        liberaBotoes(false, true, true, false, true);
        preencheComboAutor();
        grav_alt = 1;
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (!validaISBN()) {
            if (grav_alt == 1) {
                salvar();
                grav_alt = 0;
            } else {
                if (grav_alt == 2) {
                    alterar();
                    grav_alt = 0;
                } else {
                    JOptionPane.showMessageDialog(null, "ERRO NO SISTEMA");
                }
            }
            liberaCampos(false);
            liberaBotoes(true, false, false, false, true);
            limpaCampos();
            tabela_consulta_livro.setNumRows(0);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        preencheTabela(pesquisa_titulo.getText());
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void jtl_consulta_livroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtl_consulta_livroMouseClicked
        preencheCampos(Integer.valueOf(String.valueOf(jtl_consulta_livro.getValueAt(jtl_consulta_livro.getSelectedRow(), 0))));
        liberaBotoes(false, true, true, true, true);
    }//GEN-LAST:event_jtl_consulta_livroMouseClicked

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluir();
        limpaCampos();
        liberaCampos(false);
        liberaBotoes(true, false, false, false, true);
        tabela_consulta_livro.setNumRows(0);
        pesquisa_titulo.setText("");
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpaCampos();
        liberaCampos(false);
        tabela_consulta_livro.setNumRows(0);
        liberaBotoes(true, false, false, false, true);
        grav_alt = 0;
        pesquisa_titulo.setText("");
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ano;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> combo_autor;
    private javax.swing.JTextField editora;
    private javax.swing.JTextField isbn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtl_consulta_livro;
    private javax.swing.JTextField pesquisa_titulo;
    private javax.swing.JTextField titulo;
    // End of variables declaration//GEN-END:variables
}
