/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.dodi.saw.laptop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author dodih
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        
        SAW();
    }
    
    private void SAW() {
        try {
            Connection k = Koneksi.Go();
            Statement st = k.createStatement();
            var q = "SELECT * FROM nilai";
            ResultSet r = st.executeQuery(q);
            while (r.next()) {
                int id = r.getInt("id");
                int altId = r.getInt("alt_id");
                int harga = r .getInt("harga");
                int prosesor = r .getInt("skor_prosesor");
                int ram = r .getInt("ukuran_RAM");
                int penyimpanan = r .getInt("ukuran_penyimpanan");
                int layar = r .getInt("ukuran_layar");
                int baterai = r .getInt("daya_baterai");
                 
                double n_harga = minMax("harga",1)/harga;
                double n_prosesor = prosesor/minMax("skor_prosesor",2);
                double n_ram = ram/minMax("ukuran_RAM",2);
                double n_penyimpanan = penyimpanan/minMax("ukuran_penyimpanan",2);
                double n_layar = layar/minMax("ukuran_layar",2);
                double n_baterai = baterai/minMax("daya_baterai",2);
                
                double hasil = (n_harga*bobot("harga"))
                        + (n_prosesor*bobot("skor_prosesor"))
                        + (n_ram*bobot("ukuran_RAM"))
                        + (n_penyimpanan*bobot("ukuran_penyimpanan"))
                        + (n_layar*bobot("ukuran_layar"))
                        + (n_baterai*bobot("daya_baterai"));
                  
                
                System.out.println(getAlt(altId) + " = " + hasil);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private String getAlt(int altId){
        String alt = "";
        try {
            Connection k = Koneksi.Go();
            Statement st = k.createStatement();
            var q = "SELECT nama_laptop FROM alternatif WHERE id="+ altId;
            ResultSet r = st.executeQuery(q);
            while(r.next()) {
                alt = r.getString("nama_laptop");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return alt;
    }
    
    private double bobot(String kriteria) {
        double bobot = 0;
        try {
            Connection k = Koneksi.Go();
            Statement st = k.createStatement();
            var q = "SELECT bobot FROM kriteria WHERE kriteria='"+ kriteria + "'";
            ResultSet r = st.executeQuery(q);
            while(r.next()) {
                bobot = r.getDouble("bobot");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return bobot;
    }
    
    private String cb(String kriteria){
        String label = "";
        try {
            Connection k = Koneksi.Go();
            Statement st = k.createStatement();
            var q = "SELECT label FROM kriteria WHERE kriteria="+ kriteria;
            ResultSet r = st.executeQuery(q);
            while(r.next()) {
                label = r.getString("label");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return label;
    }
    
    /**
     * 
     * @param kolom
     * @param opsi 1=min, 2=max
     * @return 
     */
    private double minMax(String kolom, int opsi){
        double val = 0;
        try {
            var q="";
            if(opsi == 1){
                q = "SELECT MIN("+kolom+") AS val FROM nilai";
            } else {
                q = "SELECT MAX("+kolom+") AS val FROM nilai";
            }
            Connection k = Koneksi.Go();
            Statement st = k.createStatement();
            ResultSet r = st.executeQuery(q);
            while (r.next()) {                 
                val = r.getDouble("val");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return val;
    }
    
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 890, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 601, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
