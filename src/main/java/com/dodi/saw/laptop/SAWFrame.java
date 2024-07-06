/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.dodi.saw.laptop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dodih
 */
public class SAWFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form SAWFrame
     */
    public SAWFrame() {
        initComponents();
        this.setBorder(null);
        BasicInternalFrameUI decorate = (BasicInternalFrameUI) this.getUI();
        decorate.setNorthPane(null);
        
        hitungSAW();
    }
    
    public void hitungSAW(){
        try {
            Connection k = Koneksi.Go();
            Statement st = k.createStatement();
            var q = "SELECT * FROM nilai";
            ResultSet r = st.executeQuery(q);
            
            DefaultTableModel modelNormalisasi = new DefaultTableModel();
            DefaultTableModel modelTerbobot = new DefaultTableModel();
            DefaultTableModel modelRank = new DefaultTableModel();

            
            modelNormalisasi.addColumn("ID Alternatif");
            modelNormalisasi.addColumn("harga");
            modelNormalisasi.addColumn("Skor Prosesor");
            modelNormalisasi.addColumn("Ukuran RAM");
            modelNormalisasi.addColumn("Penyimpanan");
            modelNormalisasi.addColumn("Ukuran Layar");
            modelNormalisasi.addColumn("Daya Baterai");
            
            modelTerbobot.addColumn("ID Alternatif");
            modelTerbobot.addColumn("harga");
            modelTerbobot.addColumn("Skor Prosesor");
            modelTerbobot.addColumn("Ukuran RAM");
            modelTerbobot.addColumn("Penyimpanan");
            modelTerbobot.addColumn("Ukuran Layar");
            modelTerbobot.addColumn("Daya Baterai");
            
            
            modelRank.addColumn("ID Alternatif");
            modelRank.addColumn("Total");
            
            tableNormalisasi.setModel(modelNormalisasi);
            tableTerbobot.setModel(modelTerbobot);            
            tablePerangkingan.setModel(modelRank);
            
            
            while (r.next()) {
                int id = r.getInt("id");
                String altId = r.getString("alt_id");
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
                
                Object[] rowDataNormalisasi = new Object[r.getMetaData().getColumnCount()];

                rowDataNormalisasi[0] = altId;
                rowDataNormalisasi[1] = n_harga;
                rowDataNormalisasi[2] = n_prosesor;
                rowDataNormalisasi[3] = n_ram;
                rowDataNormalisasi[4] = n_penyimpanan;
                rowDataNormalisasi[5] = n_layar;
                rowDataNormalisasi[6] = n_baterai;
                
                modelNormalisasi.addRow(rowDataNormalisasi); 
                
                Object[] rowDataTerbobot = new Object[r.getMetaData().getColumnCount()];
                
                double b_harga = n_harga*bobot("harga");
                double b_prosesor = n_prosesor*bobot("skor_prosesor");
                double b_ram = n_ram*bobot("ukuran_RAM");
                double b_penyimpanan = n_penyimpanan*bobot("ukuran_penyimpanan");
                double b_layar = n_layar*bobot("ukuran_layar");
                double b_baterai = n_baterai*bobot("daya_baterai");
                
                rowDataTerbobot[0] = altId;
                rowDataTerbobot[1] = b_harga;
                rowDataTerbobot[2] = b_prosesor;
                rowDataTerbobot[3] = b_ram;
                rowDataTerbobot[4] = b_penyimpanan;
                rowDataTerbobot[5] = b_layar;
                rowDataTerbobot[6] = b_baterai;
                
                modelTerbobot.addRow(rowDataTerbobot); 
                
                Object[] rowDataRank = new Object[r.getMetaData().getColumnCount() - 6];
                
                double total = b_harga + b_prosesor + b_ram + b_penyimpanan + b_layar + b_baterai;
                double totalPersen = (b_harga + b_prosesor + b_ram + b_penyimpanan + b_layar + b_baterai) * 100;
                  
                rowDataRank[0] = altId;
                rowDataRank[1] = (int) Math.floor(totalPersen) + "% (" + total +")";
                
                modelRank.addRow(rowDataRank);
                
            }
            r.close();
            st.close();
        } catch (SQLException e) {
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
    public static double minMax(String kolom, int opsi){
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTerbobot = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableNormalisasi = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablePerangkingan = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Perhitungan SAW");

        tableTerbobot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableTerbobot);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel2.setText("Normalisasi");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel3.setText("Terbobot");

        tableNormalisasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tableNormalisasi);

        tablePerangkingan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tablePerangkingan);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel4.setText("Hasil Akhir");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 837, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTable tableNormalisasi;
    public static javax.swing.JTable tablePerangkingan;
    public static javax.swing.JTable tableTerbobot;
    // End of variables declaration//GEN-END:variables
}
