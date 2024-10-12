/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.redesocial_livros.view;

import br.com.redesocial_livros.ctr.AvaliarCTR;
import br.com.redesocial_livros.ctr.LivroCTR;
import br.com.redesocial_livros.ctr.UsuarioCTR;
import br.com.redesocial_livros.dto.AvaliarDTO;
import br.com.redesocial_livros.dto.LivroDTO;
import br.com.redesocial_livros.dto.UsuarioDTO;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Barbara
 */
public class AvaliarVIEW extends javax.swing.JInternalFrame {

    LivroDTO l_dto = new LivroDTO();
    LivroCTR l_ctr = new LivroCTR();
    UsuarioDTO u_dto = new UsuarioDTO();
    UsuarioCTR u_ctr = new UsuarioCTR();
    AvaliarDTO av_dto = new AvaliarDTO();
    AvaliarCTR av_ctr = new AvaliarCTR();
    int grav_alt, id_usuario;
    ResultSet rs;
    DefaultTableModel tabela_consulta_av; //Variavel para guardar o modelo da tabela
    ArrayList<LivroDTO> livros = new ArrayList<>();

    /**
     * Creates new form AvaliarVIEW
     *
     * @param udto_on
     */
    public AvaliarVIEW(UsuarioDTO udto_on) {
        initComponents();
        grupo.add(lendo);
        grupo.add(queroLer);
        grupo.add(lido);
        usuario.setText(udto_on.getNome());
        id_usuario = udto_on.getId_usuario();
        usuario.setEnabled(false);
        liberaCampos(false);
        liberaBotoes(true, false, false, false, true);
        limpaCampos();
        tabela_consulta_av = (DefaultTableModel) jtl_consulta_avaliacao.getModel();
        preencheComboLivro();
    }

    public void setPosition() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    private void liberaCampos(boolean a) {
        combo_livro.setEnabled(a);
        lido.setEnabled(a);
        queroLer.setEnabled(a);
        lendo.setEnabled(a);
        comentario.setEnabled(a);
    }

    private void liberaBotoes(boolean a, boolean b, boolean c, boolean d, boolean e) {
        btnNovo.setEnabled(a);
        btnSalvar.setEnabled(b);
        btnCancelar.setEnabled(c);
        btnExcluir.setEnabled(d);
        btnSair.setEnabled(e);
    }

    private void limpaCampos() {
        combo_livro.removeAllItems();
        lido.setSelected(false);
        queroLer.setSelected(false);
        lendo.setSelected(false);
        comentario.setText("");
    }

    private void preencheComboLivro() {
        try {
            rs = l_ctr.consultar(l_dto, 3);
            combo_livro.removeAllItems();
            while (rs.next()) {
                l_dto.setId_livro(rs.getInt("id_livro"));
                l_dto.setTitulo(rs.getString("titulo"));
                combo_livro.addItem(l_dto.getTitulo());
                livros.add(new LivroDTO(rs.getInt("id_livro"), rs.getString("titulo")));
            }
        } catch (Exception e) {
            System.out.println("Erro ao preencher combobox " + e.getMessage());
        }
    }

    public void salvar() {
        try {
            u_dto.setId_usuario(id_usuario);
            l_dto.setId_livro(livros.get(combo_livro.getSelectedIndex()).getId_livro());
            if (lido.isSelected()) {
                av_dto.setLido(1);
            } else {
                av_dto.setLido(0);
            }
            if (queroLer.isSelected()) {
                av_dto.setQueroLer(1);
            } else {
                av_dto.setQueroLer(0);
            }
            if (lendo.isSelected()) {
                av_dto.setLendo(1);
            } else {
                av_dto.setLendo(0);
            }
            av_dto.setComentario(comentario.getText());

            JOptionPane.showMessageDialog(null, av_ctr.inserir(l_dto, u_dto, av_dto));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println("ERRO NO MÉTODO SALVAR: " + e.getMessage());
        }
    }

    private void alterar() {
        try {
            l_dto.setId_livro(Integer.parseInt(String.valueOf(jtl_consulta_avaliacao.getValueAt(jtl_consulta_avaliacao.getSelectedRow(), 0))));
            if (lendo.isSelected()) {
                av_dto.setLendo(1);
            } else {
                av_dto.setLendo(0);
            }
            if (queroLer.isSelected()) {
                av_dto.setQueroLer(1);
            } else {
                av_dto.setQueroLer(0);
            }
            if (lido.isSelected()) {
                av_dto.setLido(1);
            } else {
                av_dto.setLido(0);
            }
            av_dto.setComentario(comentario.getText());
            JOptionPane.showMessageDialog(null, av_ctr.alterar(l_dto, u_dto, av_dto));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void excluir() {
        if (JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "AVISO", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, av_ctr.excluir(u_dto, l_dto));
        }
    }

    private void preencheCampos(int id_livro) {
        try {
            l_dto.setId_livro(id_livro);
            u_dto.setId_usuario(id_usuario);
            rs = av_ctr.consultar(u_dto, l_dto, 2);
            while (rs.next()) {
                limpaCampos();
                combo_livro.removeAllItems();
                l_dto.setId_livro(rs.getInt("id_livro"));
                l_dto.setTitulo(rs.getString("titulo"));
                combo_livro.addItem(l_dto.getTitulo());
                if (rs.getInt("lido") == 1) {
                    lido.setSelected(true);
                } else {
                    lido.setSelected(false);
                }
                if (rs.getInt("queroLer") == 1) {
                    queroLer.setSelected(true);
                } else {
                    queroLer.setSelected(false);
                }
                if (rs.getInt("lendo") == 1) {
                    lendo.setSelected(true);
                } else {
                    lendo.setSelected(false);
                }
                comentario.setText(rs.getString("comentario"));
                grav_alt = 2;
                liberaCampos(true);
            }
        } catch (Exception e) {
            System.out.println("Erro SQL: " + e.getMessage());
        }
    }

    private void preencheTabela(String titulo) {
        try {
            tabela_consulta_av.setNumRows(0);
            u_dto.setId_usuario(id_usuario);
            l_dto.setTitulo(titulo);
            rs = av_ctr.consultar(u_dto, l_dto, 1);

            while (rs.next()) {
                tabela_consulta_av.addRow(new Object[]{
                    rs.getString("id_livro"),
                    rs.getString("titulo"),
                    rs.getString("nome")
                });
            }
        } catch (Exception e) {
            System.out.println("ERRO NA TABELA: " + e.getMessage());
        } finally {
            av_ctr.CloseDB();
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

        grupo = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        usuario = new javax.swing.JTextField();
        combo_livro = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        comentario = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtl_consulta_avaliacao = new javax.swing.JTable();
        btnPesquisar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        pesquisa_titulo = new javax.swing.JTextField();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        lendo = new javax.swing.JRadioButton();
        queroLer = new javax.swing.JRadioButton();
        lido = new javax.swing.JRadioButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("AVALIAR LIVRO");

        jLabel2.setText("Usuário:");

        jLabel3.setText("Livro:");

        jLabel4.setText("Comentários:");

        comentario.setColumns(20);
        comentario.setRows(5);
        jScrollPane1.setViewportView(comentario);

        jLabel9.setText("Pesquisar por título do livro:");

        jtl_consulta_avaliacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Livro", "Título", "Usuário"
            }
        ));
        jtl_consulta_avaliacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtl_consulta_avaliacaoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtl_consulta_avaliacao);
        if (jtl_consulta_avaliacao.getColumnModel().getColumnCount() > 0) {
            jtl_consulta_avaliacao.getColumnModel().getColumn(0).setMinWidth(0);
            jtl_consulta_avaliacao.getColumnModel().getColumn(0).setPreferredWidth(0);
            jtl_consulta_avaliacao.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/redesocial_livros/view/imagens/pesquisar.png"))); // NOI18N
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Consultar suas avaliações");

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

        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        lendo.setText("Lendo");

        queroLer.setText("Quero Ler");

        lido.setText("Lido");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(usuario)
                    .addComponent(combo_livro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lendo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(queroLer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lido)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pesquisa_titulo)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 208, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesquisar))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(108, 108, 108)))
                .addGap(23, 23, 23))
            .addGroup(layout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(btnNovo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSair)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(349, 349, 349))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(combo_livro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lendo)
                            .addComponent(queroLer)
                            .addComponent(lido))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pesquisa_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnPesquisar))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar)
                    .addComponent(btnSair)
                    .addComponent(btnExcluir))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtl_consulta_avaliacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtl_consulta_avaliacaoMouseClicked
        preencheCampos(Integer.valueOf(String.valueOf(jtl_consulta_avaliacao.getValueAt(jtl_consulta_avaliacao.getSelectedRow(), 0))));
        liberaBotoes(false, true, true, true, true);
    }//GEN-LAST:event_jtl_consulta_avaliacaoMouseClicked

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        preencheTabela(pesquisa_titulo.getText());
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        liberaCampos(true);
        liberaBotoes(false, true, true, false, true);
        preencheComboLivro();
        grav_alt = 1;
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
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
        tabela_consulta_av.setNumRows(0);
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpaCampos();
        liberaCampos(false);
        tabela_consulta_av.setNumRows(0);
        liberaBotoes(true, false, false, false, true);
        grav_alt = 0;
        pesquisa_titulo.setText("");
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluir();
        limpaCampos();
        liberaCampos(false);
        liberaBotoes(true, false, false, false, true);
        tabela_consulta_av.setNumRows(0);
        pesquisa_titulo.setText("");
    }//GEN-LAST:event_btnExcluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> combo_livro;
    private javax.swing.JTextArea comentario;
    private javax.swing.ButtonGroup grupo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtl_consulta_avaliacao;
    private javax.swing.JRadioButton lendo;
    private javax.swing.JRadioButton lido;
    private javax.swing.JTextField pesquisa_titulo;
    private javax.swing.JRadioButton queroLer;
    private javax.swing.JTextField usuario;
    // End of variables declaration//GEN-END:variables
}
