/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Conec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author RODRIGO_PC
 */
public class TelaListaOrdemDeServico extends javax.swing.JFrame {

    /**
     * Creates new form TelaLiscatagemdeClientes
     */
    public TelaListaOrdemDeServico() {
        initComponents();
        this.setResizable(false);
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String[] colaborador = new String[1000];
        f_tipoOS.removeAllItems();
        f_tecnicoex.removeAllItems();
        f_tecnicoAbe.removeAllItems();
        f_tipoOS.addItem("Todos");
        f_tecnicoex.addItem("Todos");
        f_tecnicoAbe.addItem("Todos");
        try {
            con = Conec.Conectar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaListaOrdemDeServico.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql1 = ("select os_colaborador.idos_colaborador as indice, os_colaborador.apelido as apelido from os_colaborador");

        try {
            pst = con.prepareStatement(sql1);
            rs = pst.executeQuery();
            while (rs.next()) {
                int i = rs.getInt("indice");
                colaborador[i] = rs.getString("apelido");
                f_tecnicoex.addItem(colaborador[i]);
                f_tecnicoAbe.addItem(colaborador[i]);
            }
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(null, E);
        }
        f_tipoOS.addItem("Interno");
        f_tipoOS.addItem("Externo");

        String sql = ("SELECT OS_OSCABECALHO.IDOS_OSCABECALHO as codos, OS_OSCABECALHO.CODCLIENTE as codcli, "
                + "CLIENTE.NOME as nome, OS_OSCABECALHO.TIPOOS as tipo, OS_OSCABECALHO.DATACHAMADO as datacha,"
                + " OS_OSCABECALHO.HORACHAMADO as horachama, OS_OSCABECALHO.IDOS_COLABORADORABERTURA as codColaAber, "
                + "OS_OSCABECALHO.IDOS_COLABORADOREXECUTOR as codEx, OS_OSCABECALHO.IDOS_SETOR, "
                + "OS_SETOR.DESCRICAO as ativi, OS_OSCABECALHO.IDOS_TIPOATIVIDADE, OS_TIPOATIVIDADE.DESCRICAO , "
                + "OS_OSDEFEITO.DETALHES as defeito, OS_OSCABECALHO.USUARIO, OS_OSCABECALHO.STATUSOS as status, "
                + "OS_OSCABECALHO.STATUSATENDIMENTO, OS_OSCABECALHO.DATAINICIOSERVICOS, "
                + "OS_OSCABECALHO.HORAINICIOSERVICOS, OS_OSCABECALHO.DATAAPROVACAO, "
                + "OS_OSCABECALHO.HORAAPROVACAO, OS_OSCABECALHO.OBSERVACAO, OS_OSCABECALHO.DATAFATURAMENTO, "
                + "OS_OSCABECALHO.DATARECUSA, OS_OSCABECALHO.DATACANCELAMENTO\n"
                + "FROM CLIENTE CLIENTE, OS_OSCABECALHO OS_OSCABECALHO, OS_OSDEFEITO OS_OSDEFEITO, OS_SETOR OS_SETOR, OS_TIPOATIVIDADE OS_TIPOATIVIDADE\n"
                + "WHERE CLIENTE.CODCLIENTE = OS_OSCABECALHO.CODCLIENTE AND OS_SETOR.IDOS_SETOR = OS_OSCABECALHO.IDOS_SETOR AND OS_TIPOATIVIDADE.IDOS_TIPOATIVIDADE = OS_OSCABECALHO.IDOS_TIPOATIVIDADE AND OS_OSDEFEITO.IDOS_OSCABECALHO = OS_OSCABECALHO.IDOS_OSCABECALHO AND ((OS_OSCABECALHO.DATACANCELAMENTO Is Null) AND (OS_OSCABECALHO.DATARECUSA Is Null) AND (OS_OSCABECALHO.DATAFATURAMENTO Is Null))\n"
                + "ORDER BY OS_OSCABECALHO.IDOS_OSCABECALHO");
        DefaultTableModel table = (DefaultTableModel) listaOS.getModel();
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                String tipo = "";
                String status = "";
                tipo = (rs.getString("tipo"));
                if (tipo.equals("0")) {
                    tipo = "Interno";
                } else {
                    tipo = "Externo";
                }
                if (rs.getInt("status") == 0) {
                    status = "Em Orçamento";
                } else if (rs.getInt("status") == 1) {
                    status = "Aguard Aprov Orcamento";
                } else if (rs.getInt("status") == 2) {
                    status = "Aguard Inicio";
                } else if (rs.getInt("status") == 3) {
                    status = "";
                } else if (rs.getInt("status") == 4) {
                    status = "Em Execução";
                } else if (rs.getInt("status") == 5) {
                    status = "";
                } else if (rs.getInt("status") == 10) {
                    status = "O.S. Fechada (Aguardando Faturamento)";
                }
                int j = 0;
                int f = 0;
                String aux = rs.getString("codEx");
                f = Integer.parseInt(rs.getString("codColaAber"));
                if (!(aux == null)) {
                    j = Integer.parseInt(aux);
                }

                table.addRow(new Object[]{rs.getString("codos"), rs.getString("codcli"), rs.getString("nome"), tipo, rs.getDate("datacha"), rs.getString("horachama"),
                    colaborador[f], colaborador[j], rs.getString("ativi"), status, rs.getString("defeito")});

            }
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(null, E);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        listaOS = new javax.swing.JTable();
        B_imprimeEtiqueta = new javax.swing.JButton();
        f_tecnicoAbe = new javax.swing.JComboBox<>();
        L_TecnicoAbertura = new javax.swing.JLabel();
        L_TecnicoExcucao = new javax.swing.JLabel();
        f_tecnicoex = new javax.swing.JComboBox<>();
        f_tipoOS = new javax.swing.JComboBox<>();
        L_TipoOs = new javax.swing.JLabel();
        B_pesquisa = new javax.swing.JToggleButton();
        f_cliente = new javax.swing.JTextField();
        L_nomeCliente = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        listaOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COD OS", "COD CLIENTE", "NOME CLIENTE", "TIPO", "DATA ABER", "DATA ABER", "USUARIO ABER", "USUARIO EXECU", "LOCAL", "STATUS", "DESCRICAO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(listaOS);
        if (listaOS.getColumnModel().getColumnCount() > 0) {
            listaOS.getColumnModel().getColumn(0).setPreferredWidth(1);
            listaOS.getColumnModel().getColumn(1).setResizable(false);
            listaOS.getColumnModel().getColumn(1).setPreferredWidth(10);
            listaOS.getColumnModel().getColumn(2).setResizable(false);
            listaOS.getColumnModel().getColumn(2).setPreferredWidth(200);
            listaOS.getColumnModel().getColumn(3).setResizable(false);
            listaOS.getColumnModel().getColumn(3).setPreferredWidth(8);
            listaOS.getColumnModel().getColumn(4).setResizable(false);
            listaOS.getColumnModel().getColumn(4).setPreferredWidth(20);
            listaOS.getColumnModel().getColumn(5).setResizable(false);
            listaOS.getColumnModel().getColumn(5).setPreferredWidth(1);
            listaOS.getColumnModel().getColumn(6).setResizable(false);
            listaOS.getColumnModel().getColumn(6).setPreferredWidth(30);
            listaOS.getColumnModel().getColumn(7).setResizable(false);
            listaOS.getColumnModel().getColumn(7).setPreferredWidth(30);
            listaOS.getColumnModel().getColumn(8).setResizable(false);
            listaOS.getColumnModel().getColumn(8).setPreferredWidth(30);
            listaOS.getColumnModel().getColumn(9).setResizable(false);
            listaOS.getColumnModel().getColumn(9).setPreferredWidth(40);
            listaOS.getColumnModel().getColumn(10).setResizable(false);
            listaOS.getColumnModel().getColumn(10).setPreferredWidth(300);
        }

        B_imprimeEtiqueta.setText("IMPRIMIR ETIQUETA");
        B_imprimeEtiqueta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_imprimeEtiquetaActionPerformed(evt);
            }
        });

        f_tecnicoAbe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        f_tecnicoAbe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f_tecnicoAbeActionPerformed(evt);
            }
        });

        L_TecnicoAbertura.setText("Tecnico Abertura");

        L_TecnicoExcucao.setText("Tecnico Execução");

        f_tecnicoex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        f_tecnicoex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f_tecnicoexActionPerformed(evt);
            }
        });

        f_tipoOS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        f_tipoOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f_tipoOSActionPerformed(evt);
            }
        });

        L_TipoOs.setText("Tipo de OS");

        B_pesquisa.setText("Pesquisar");
        B_pesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_pesquisaActionPerformed(evt);
            }
        });

        L_nomeCliente.setText("Nome de Cliente");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Filtros");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(L_TecnicoAbertura)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(f_tecnicoAbe, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L_TecnicoExcucao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(f_tecnicoex, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L_TipoOs)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(f_tipoOS, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(L_nomeCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(f_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B_pesquisa))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(B_imprimeEtiqueta))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(f_tecnicoAbe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L_TecnicoAbertura)
                    .addComponent(f_tecnicoex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L_TecnicoExcucao)
                    .addComponent(f_tipoOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(L_TipoOs)
                    .addComponent(B_pesquisa)
                    .addComponent(L_nomeCliente)
                    .addComponent(f_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(B_imprimeEtiqueta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(916, 539));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void B_imprimeEtiquetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_imprimeEtiquetaActionPerformed
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int linhaSelecionada = listaOS.getSelectedRow();

        try {
            con = Conec.Conectar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaImpressaoEtiquetaOs.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = ("select  cabec.idos_oscabecalho as codOS , cabec.codcliente as codcliente,cli.nome as nome,cabec.datachamado as data, cabec.horachamado as hora  , pat.descricao as patri from OS_OSCABECALHO as cabec left join   cliente as cli on cabec.codcliente = cli.codcliente left join  os_ospatrimonoavulso  as pat on   cabec.idos_oscabecalho  =  pat.idos_oscabecalho where cabec.idos_oscabecalho = ? ;");

        try {
            pst = con.prepareStatement(sql);
            int aux = Integer.parseInt((String) listaOS.getValueAt(linhaSelecionada, 0));
            pst.setInt(1, aux);
            rs = pst.executeQuery();
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(null, E);
        }

        try {
            JRResultSetDataSource resultset = new JRResultSetDataSource(rs);

            Map map = null;
            String url = "src\\Relatorios\\etiquetaos.jasper";
            System.out.println(url);
            JasperPrint jasperPrint = JasperFillManager.fillReport(url, map, resultset);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_B_imprimeEtiquetaActionPerformed

    private void f_tecnicoAbeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f_tecnicoAbeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f_tecnicoAbeActionPerformed

    private void f_tecnicoexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f_tecnicoexActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_f_tecnicoexActionPerformed

    private void f_tipoOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f_tipoOSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f_tipoOSActionPerformed

    public void remove_lista() {

        DefaultTableModel tableModel = (DefaultTableModel) listaOS.getModel();
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }

    }

    public String filtroOsTipo() {
        int aux_tipo;
        String aux_tipoF = "";
        aux_tipo = this.f_tipoOS.getSelectedIndex();
        if (aux_tipo == 1) {
            aux_tipoF = "0";
        }
        if (aux_tipo == 2) {
            aux_tipoF = "1";
        } else if (aux_tipo == 0) {
            aux_tipoF = "%%";
        }
        return aux_tipoF;
    }

    public String filtroOsColoAber() {
        String aux_ColoF = "";
        int aux_Colo = this.f_tecnicoAbe.getSelectedIndex();
        if (aux_Colo == 0) {
            aux_ColoF = "%%";
        } else {
            aux_ColoF = Integer.toString(aux_Colo);
        }
        return aux_ColoF;
    }

    public String filtroOsColoEx() {
        String aux_ColoF = "";
        int aux_ColoEx = this.f_tecnicoex.getSelectedIndex();
        if (aux_ColoEx == 0) {
            aux_ColoF = "%%";
        } else {
            aux_ColoF = Integer.toString(aux_ColoEx);
        }
        return aux_ColoF;
    }
        public String filtroOsNomeCli() {
        String aux_ColoC = "";
        String aux_ColoCli = this.f_cliente.getText();
        if (aux_ColoCli.equals("")) {
            aux_ColoC = "%%";
        } else {
            aux_ColoC = "%"+aux_ColoCli+"%";
        }
        return aux_ColoC;
    }
    private void B_pesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_pesquisaActionPerformed

        remove_lista();
        this.setResizable(false);
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String[] colaborador = new String[1000];

        try {
            con = Conec.Conectar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaListaOrdemDeServico.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql1 = ("select os_colaborador.idos_colaborador as indice, os_colaborador.apelido as apelido from os_colaborador");
        try {
            pst = con.prepareStatement(sql1);
            rs = pst.executeQuery();
            while (rs.next()) {
                int i = rs.getInt("indice");
                colaborador[i] = rs.getString("apelido");

            }
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(null, E);
        }
        String aux_tipoF = filtroOsTipo();
        String codColaAber = filtroOsColoAber();
        String codEx = filtroOsColoEx();
        String nome = filtroOsNomeCli();
        String sql = "";
        if (codEx.equals("%%")) {
            sql = ("SELECT OS_OSCABECALHO.IDOS_OSCABECALHO as codos, OS_OSCABECALHO.CODCLIENTE as codcli, "
                    + "CLIENTE.NOME as nome, OS_OSCABECALHO.TIPOOS as tipo, OS_OSCABECALHO.DATACHAMADO as datacha,"
                    + " OS_OSCABECALHO.HORACHAMADO as horachama, OS_OSCABECALHO.IDOS_COLABORADORABERTURA as codColaAber, "
                    + "OS_OSCABECALHO.IDOS_COLABORADOREXECUTOR as codEx, OS_OSCABECALHO.IDOS_SETOR, "
                    + "OS_SETOR.DESCRICAO as ativi, OS_OSCABECALHO.IDOS_TIPOATIVIDADE, OS_TIPOATIVIDADE.DESCRICAO , "
                    + "OS_OSDEFEITO.DETALHES as defeito, OS_OSCABECALHO.USUARIO, OS_OSCABECALHO.STATUSOS as status, "
                    + "OS_OSCABECALHO.STATUSATENDIMENTO, OS_OSCABECALHO.DATAINICIOSERVICOS, "
                    + "OS_OSCABECALHO.HORAINICIOSERVICOS, OS_OSCABECALHO.DATAAPROVACAO, "
                    + "OS_OSCABECALHO.HORAAPROVACAO, OS_OSCABECALHO.OBSERVACAO, OS_OSCABECALHO.DATAFATURAMENTO, "
                    + "OS_OSCABECALHO.DATARECUSA, OS_OSCABECALHO.DATACANCELAMENTO\n"
                    + "FROM CLIENTE CLIENTE, OS_OSCABECALHO OS_OSCABECALHO, OS_OSDEFEITO OS_OSDEFEITO, OS_SETOR OS_SETOR, OS_TIPOATIVIDADE OS_TIPOATIVIDADE\n"
                    + "WHERE OS_OSCABECALHO.TIPOOS like ? AND OS_OSCABECALHO.IDOS_COLABORADORABERTURA like ? AND (OS_OSCABECALHO.IDOS_COLABORADOREXECUTOR like '%%' OR OS_OSCABECALHO.IDOS_COLABORADOREXECUTOR is null) AND CLIENTE.NOME like ? AND CLIENTE.CODCLIENTE = OS_OSCABECALHO.CODCLIENTE AND OS_SETOR.IDOS_SETOR = OS_OSCABECALHO.IDOS_SETOR AND OS_TIPOATIVIDADE.IDOS_TIPOATIVIDADE = OS_OSCABECALHO.IDOS_TIPOATIVIDADE AND OS_OSDEFEITO.IDOS_OSCABECALHO = OS_OSCABECALHO.IDOS_OSCABECALHO AND ((OS_OSCABECALHO.DATACANCELAMENTO Is Null) AND (OS_OSCABECALHO.DATARECUSA Is Null) AND (OS_OSCABECALHO.DATAFATURAMENTO Is Null))\n"
                    + "ORDER BY OS_OSCABECALHO.IDOS_OSCABECALHO");
        } else {
            sql = ("SELECT OS_OSCABECALHO.IDOS_OSCABECALHO as codos, OS_OSCABECALHO.CODCLIENTE as codcli, "
                    + "CLIENTE.NOME as nome, OS_OSCABECALHO.TIPOOS as tipo, OS_OSCABECALHO.DATACHAMADO as datacha,"
                    + " OS_OSCABECALHO.HORACHAMADO as horachama, OS_OSCABECALHO.IDOS_COLABORADORABERTURA as codColaAber, "
                    + "OS_OSCABECALHO.IDOS_COLABORADOREXECUTOR as codEx, OS_OSCABECALHO.IDOS_SETOR, "
                    + "OS_SETOR.DESCRICAO as ativi, OS_OSCABECALHO.IDOS_TIPOATIVIDADE, OS_TIPOATIVIDADE.DESCRICAO , "
                    + "OS_OSDEFEITO.DETALHES as defeito, OS_OSCABECALHO.USUARIO, OS_OSCABECALHO.STATUSOS as status, "
                    + "OS_OSCABECALHO.STATUSATENDIMENTO, OS_OSCABECALHO.DATAINICIOSERVICOS, "
                    + "OS_OSCABECALHO.HORAINICIOSERVICOS, OS_OSCABECALHO.DATAAPROVACAO, "
                    + "OS_OSCABECALHO.HORAAPROVACAO, OS_OSCABECALHO.OBSERVACAO, OS_OSCABECALHO.DATAFATURAMENTO, "
                    + "OS_OSCABECALHO.DATARECUSA, OS_OSCABECALHO.DATACANCELAMENTO\n"
                    + "FROM CLIENTE CLIENTE, OS_OSCABECALHO OS_OSCABECALHO, OS_OSDEFEITO OS_OSDEFEITO, OS_SETOR OS_SETOR, OS_TIPOATIVIDADE OS_TIPOATIVIDADE\n"
                    + "WHERE OS_OSCABECALHO.TIPOOS like ? AND OS_OSCABECALHO.IDOS_COLABORADORABERTURA like ? AND OS_OSCABECALHO.IDOS_COLABORADOREXECUTOR like ?  AND CLIENTE.NOME like ? AND CLIENTE.CODCLIENTE = OS_OSCABECALHO.CODCLIENTE AND OS_SETOR.IDOS_SETOR = OS_OSCABECALHO.IDOS_SETOR AND OS_TIPOATIVIDADE.IDOS_TIPOATIVIDADE = OS_OSCABECALHO.IDOS_TIPOATIVIDADE AND OS_OSDEFEITO.IDOS_OSCABECALHO = OS_OSCABECALHO.IDOS_OSCABECALHO AND ((OS_OSCABECALHO.DATACANCELAMENTO Is Null) AND (OS_OSCABECALHO.DATARECUSA Is Null) AND (OS_OSCABECALHO.DATAFATURAMENTO Is Null))\n"
                    + "ORDER BY OS_OSCABECALHO.IDOS_OSCABECALHO");
        }
        DefaultTableModel table = (DefaultTableModel) listaOS.getModel();
        try {
            pst = con.prepareStatement(sql);
            if (codEx.equals("%%")) {
                pst.setString(1, aux_tipoF);
                pst.setString(2, codColaAber);
                pst.setString(3, nome);
            } else {
                pst.setString(1, aux_tipoF);
                pst.setString(2, codColaAber);
                pst.setString(3, codEx);
                pst.setString(4, nome);
            }
            rs = pst.executeQuery();
            while (rs.next()) {
                String tipo = "";
                String status = "";
                tipo = (rs.getString("tipo"));
                if (tipo.equals("0")) {
                    tipo = "Interno";
                } else {
                    tipo = "Externo";
                }
                if (rs.getInt("status") == 0) {
                    status = "Em Orçamento";
                } else if (rs.getInt("status") == 1) {
                    status = "Aguard Aprov Orcamento";
                } else if (rs.getInt("status") == 2) {
                    status = "Aguard Inicio";
                } else if (rs.getInt("status") == 3) {
                    status = "";
                } else if (rs.getInt("status") == 4) {
                    status = "Em Execução";
                } else if (rs.getInt("status") == 5) {
                    status = "";
                } else if (rs.getInt("status") == 10) {
                    status = "O.S. Fechada (Aguardando Faturamento)";
                }
                int j = 0;
                int f = 0;
                String aux = rs.getString("codEx");
                f = Integer.parseInt(rs.getString("codColaAber"));
                if (!(aux == null)) {
                    j = Integer.parseInt(aux);
                }

                table.addRow(new Object[]{rs.getString("codos"), rs.getString("codcli"), rs.getString("nome"), tipo, rs.getDate("datacha"), rs.getString("horachama"),
                    colaborador[f], colaborador[j], rs.getString("ativi"), status, rs.getString("defeito")});

            }
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(null, E);
        }

    }//GEN-LAST:event_B_pesquisaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaListaOrdemDeServico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaListaOrdemDeServico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaListaOrdemDeServico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaListaOrdemDeServico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaListaOrdemDeServico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B_imprimeEtiqueta;
    private javax.swing.JToggleButton B_pesquisa;
    private javax.swing.JLabel L_TecnicoAbertura;
    private javax.swing.JLabel L_TecnicoExcucao;
    private javax.swing.JLabel L_TipoOs;
    private javax.swing.JLabel L_nomeCliente;
    private javax.swing.JTextField f_cliente;
    private javax.swing.JComboBox<String> f_tecnicoAbe;
    private javax.swing.JComboBox<String> f_tecnicoex;
    private javax.swing.JComboBox<String> f_tipoOS;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable listaOS;
    // End of variables declaration//GEN-END:variables
}
