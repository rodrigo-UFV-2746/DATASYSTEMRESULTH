/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author rodri
 */
public class Conec {

    public static Connection Conectar() throws ClassNotFoundException {
        String nome = "C:\\RESULTH DATASYSTEM\\conexao.txt";
        String linha = ""; 
        try {
            
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(arq);

            linha = lerArq.readLine();
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }
        try {
            String caminho =  "jdbc:firebirdsql://";
            caminho= caminho.concat(linha);
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            Connection con = DriverManager.getConnection(caminho, "SYSDBA", "masterkey");
            return con;
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(null, E);
        }
        return null;
    }

}
