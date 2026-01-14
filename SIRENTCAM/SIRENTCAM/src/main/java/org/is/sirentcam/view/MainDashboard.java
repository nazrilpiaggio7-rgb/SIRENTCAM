/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package org.is.sirentcam.view;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.is.sirentcam.model.Kamera;
import org.is.sirentcam.model.Peminjaman;
import org.is.sirentcam.model.User;
import org.is.sirentcam.servicedbimpl.ControllerKamera;
import org.is.sirentcam.servicedbimpl.ControllerLogin;
import org.is.sirentcam.servicedbimpl.ControllerPeminjaman;

/**
 *
 * @author Fajar Perdana
 */
public class MainDashboard extends javax.swing.JFrame {
	
	private DefaultTableModel model;
	ControllerKamera conCam = new ControllerKamera();
	ControllerLogin conlogin = new ControllerLogin();
	ControllerPeminjaman conPinjam = new ControllerPeminjaman();
        
        private String selectedUsername = ""; 
	
	private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainDashboard.class.getName());

	/**
	 * Creates new form MainDashboard
	 */
	public MainDashboard() {
		initComponents();
		
		this.setLocationRelativeTo(null);
		
		parent.removeAll();
		parent.add(defaultPanel);
		parent.repaint();
		parent.validate();
		
		model = new DefaultTableModel();
		
		tabelKamera.setModel(model);
		
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		
		btn_updatePinjam.setEnabled(true);
	
		btnClearForm.setEnabled(true);
		fieldTotalHarga.setEditable(false);
		fieldDurasi.setEditable(false);
		
		btnUpdateAkun.setEnabled(false);
		btnDeleteAkun.setEnabled(false);
		
		model.addColumn("ID Kamera");
		model.addColumn("Nama Kamera");
		model.addColumn("Harga Sewa");
		model.addColumn("Status");
		getDataKamera();

		
		DefaultTableModel modelPinjam = new DefaultTableModel();
		tabelPeminjaman.setModel(modelPinjam);
		modelPinjam.addColumn("ID Sewa"); 
		modelPinjam.addColumn("Penyewa");  
		modelPinjam.addColumn("No. Telp"); 
		modelPinjam.addColumn("Alamat"); 
		modelPinjam.addColumn("ID Kamera"); 
		modelPinjam.addColumn("Tgl Sewa");  
		modelPinjam.addColumn("Tgl Kembali");  
		modelPinjam.addColumn("Durasi");  
		modelPinjam.addColumn("Status");    
		modelPinjam.addColumn("Denda");
		modelPinjam.addColumn("Total");     
		
		getDataPeminjaman();
		
		DefaultTableModel modelAkun = new DefaultTableModel();
		tabelAkun.setModel(modelAkun);
		modelAkun.addColumn("Username");
		modelAkun.addColumn("Password");
		
		getDataAkun();
	}

	public final void getDataKamera() {
        DefaultTableModel dtm = (DefaultTableModel) tabelKamera.getModel();
        
        dtm.setRowCount(0);
        
        List<Kamera> listKamera = conCam.findAll();
        
        for (Kamera k : listKamera) {
            Object[] row = {
                k.getId_kamera(),
                k.getNama_kamera(),
                k.getHarga(),
				k.getStatus()
            };
            dtm.addRow(row);
        }
    }
	
	public final void getDataAkun(){
		DefaultTableModel dtm = (DefaultTableModel) tabelAkun.getModel();

		dtm.setRowCount(0);

		List<User> listAkun = conlogin.findAll();

		for (User a : listAkun) {
			Object[] row = {
				a.getUsername(), 
				a.getPassword()  
			};
			dtm.addRow(row);
		}
	}
	
		public final void getDataPeminjaman() {
		DefaultTableModel dtm = (DefaultTableModel) tabelPeminjaman.getModel();
		dtm.setRowCount(0);

		List<Peminjaman> listPinjam = conPinjam.findAll();

		for (Peminjaman p : listPinjam) {
			Object[] row = {
				p.getId_sewa(),
				p.getNama_penyewa(),
				p.getNo_telp(),
				p.getAlamat(),
				p.getId_kamera(),
				p.getTgl_sewa(),
				p.getTgl_kembali(),
				p.getDurasi(),
				p.getStatus(),
				p.getDenda(),     
				p.getTotal_biaya()
			};
			dtm.addRow(row);
		}
	}
		
	private boolean validateFieldsRent() {
    if (fieldNamaPenyewa.getText().trim().isEmpty() || 
        fieldIDCamera.getText().trim().isEmpty() || 
        fieldTanggalSewa.getText().trim().isEmpty() || 
        fieldTanggalPengembalian.getText().trim().isEmpty()) {
        
        JOptionPane.showMessageDialog(this, "Field Nama, ID Kamera, dan Tanggal wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    try {
        java.time.LocalDate mulai = java.time.LocalDate.parse(fieldTanggalSewa.getText());
        java.time.LocalDate selesai = java.time.LocalDate.parse(fieldTanggalPengembalian.getText());

        if (selesai.isBefore(mulai)) {
            JOptionPane.showMessageDialog(this, "Tanggal pengembalian tidak boleh mendahului tanggal sewa!", "Kesalahan Tanggal", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } catch (java.time.format.DateTimeParseException e) {
        JOptionPane.showMessageDialog(this, "Format tanggal salah! Gunakan YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    return true;
}	
		
		
	
	private final void resetForm(){
		fieldNamaKamera.setText("");
		fieldHargaSewa.setText("");
		cmbStatusKamera.setSelectedIndex(0);
		
		btnSubmit.setEnabled(true);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
	}

	
	private final void resetFormPinjaman(){
		fieldNamaPenyewa.setText("");
		fieldNoTelp.setText("");
		fieldAlamat.setText("");
		fieldIDCamera.setText("");
		fieldTanggalSewa.setText("");
		fieldTanggalPengembalian.setText("");
		comboStatus.setSelectedIndex(0);
		fieldTotalHarga.setText("");
		fieldIDSewa.setText("");
		fieldDurasi.setText("");
		fieldDenda.setText("");
		
		btn_submitPinjam.setEnabled(true);
		btn_updatePinjam.setEnabled(false);
		btn_deletePinjam.setEnabled(false);
	}
	
	private void resetAkunForm() {
    fieldUsername.setText("");
    fieldPassword.setText("");
	
    btnSubmitAkun.setEnabled(true);
    btnUpdateAkun.setEnabled(false);
    btnDeleteAkun.setEnabled(false);
}
	
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnKelolaKamera = new javax.swing.JButton();
        btnPinjaman = new javax.swing.JButton();
        btnSignOut = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        btnKelolaAkun = new javax.swing.JToggleButton();
        parent = new javax.swing.JPanel();
        defaultPanel = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        panelKamera = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        fieldNamaKamera = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        fieldHargaSewa = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        cmbStatusKamera = new javax.swing.JComboBox<>();
        btnClearKamera = new javax.swing.JButton();
        label_peminjaman = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelKamera = new javax.swing.JTable();
        panelPinjaman = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fieldNamaPenyewa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        fieldNoTelp = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        fieldAlamat = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        fieldTanggalPengembalian = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        fieldTotalHarga = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btn_submitPinjam = new javax.swing.JButton();
        btn_updatePinjam = new javax.swing.JButton();
        btnClearForm = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        fieldIDSewa = new javax.swing.JTextField();
        fieldIDCamera = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        fieldTanggalSewa = new javax.swing.JTextField();
        comboStatus = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        fieldDenda = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        fieldDurasi = new javax.swing.JTextField();
        btn_deletePinjam = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        label_peminjaman1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelPeminjaman = new javax.swing.JTable();
        panelAkun = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelAkun = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        fieldUsername = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        fieldPassword = new javax.swing.JTextField();
        btnSubmitAkun = new javax.swing.JButton();
        btnUpdateAkun = new javax.swing.JButton();
        btnDeleteAkun = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        label_peminjaman2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnKelolaKamera.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnKelolaKamera.setText("Kelola Kamera");
        btnKelolaKamera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        btnKelolaKamera.setBorderPainted(false);
        btnKelolaKamera.setContentAreaFilled(false);
        btnKelolaKamera.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKelolaKamera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKelolaKameraActionPerformed(evt);
            }
        });

        btnPinjaman.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPinjaman.setText("Pinjaman");
        btnPinjaman.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPinjaman.setBorderPainted(false);
        btnPinjaman.setContentAreaFilled(false);
        btnPinjaman.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPinjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPinjamanActionPerformed(evt);
            }
        });

        btnSignOut.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSignOut.setText("Sign out");
        btnSignOut.setBorder(null);
        btnSignOut.setBorderPainted(false);
        btnSignOut.setContentAreaFilled(false);
        btnSignOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSignOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignOutActionPerformed(evt);
            }
        });

        btnKeluar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnKeluar.setText("Keluar");
        btnKeluar.setBorder(null);
        btnKeluar.setBorderPainted(false);
        btnKeluar.setContentAreaFilled(false);
        btnKeluar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        btnKelolaAkun.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnKelolaAkun.setText("Kelola Akun");
        btnKelolaAkun.setBorder(null);
        btnKelolaAkun.setBorderPainted(false);
        btnKelolaAkun.setContentAreaFilled(false);
        btnKelolaAkun.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKelolaAkun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKelolaAkunActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnKelolaKamera)
                .addGap(14, 14, 14))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnPinjaman))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnKeluar)
                            .addComponent(btnSignOut)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnKelolaAkun)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addComponent(btnKelolaAkun)
                .addGap(18, 18, 18)
                .addComponent(btnKelolaKamera, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnPinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSignOut)
                .addGap(18, 18, 18)
                .addComponent(btnKeluar)
                .addGap(24, 24, 24))
        );

        parent.setPreferredSize(new java.awt.Dimension(740, 642));
        parent.setLayout(new java.awt.CardLayout());

        jLabel18.setIcon(new javax.swing.ImageIcon("C:\\Users\\Nazril\\Downloads\\Untitled.png")); // NOI18N
        jLabel18.setText("jLabel18");

        javax.swing.GroupLayout defaultPanelLayout = new javax.swing.GroupLayout(defaultPanel);
        defaultPanel.setLayout(defaultPanelLayout);
        defaultPanelLayout.setHorizontalGroup(
            defaultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        defaultPanelLayout.setVerticalGroup(
            defaultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        parent.add(defaultPanel, "card2");

        panelKamera.setRequestFocusEnabled(false);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText(" Nama Kamera");

        fieldNamaKamera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldNamaKameraActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Harga Sewa");

        fieldHargaSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldHargaSewaActionPerformed(evt);
            }
        });

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Status");

        cmbStatusKamera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "dipinjam", "tersedia" }));

        btnClearKamera.setText("Clear");
        btnClearKamera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearKameraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnSubmit)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(fieldNamaKamera, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addGap(18, 18, 18)
                        .addComponent(btnClearKamera))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel4)
                        .addGap(30, 30, 30)
                        .addComponent(fieldHargaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbStatusKamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(fieldHargaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)
                        .addComponent(cmbStatusKamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(fieldNamaKamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnClearKamera))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        label_peminjaman.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_peminjaman.setText("STOK KAMERA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        tabelKamera.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Kamera", "Nama Kamera", "Harga Sewa", "Status"
            }
        ));
        tabelKamera.setMinimumSize(new java.awt.Dimension(61, 80));
        tabelKamera.setRequestFocusEnabled(false);
        tabelKamera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelKameraMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelKamera);
        if (tabelKamera.getColumnModel().getColumnCount() > 0) {
            tabelKamera.getColumnModel().getColumn(2).setHeaderValue("Harga Sewa");
            tabelKamera.getColumnModel().getColumn(3).setHeaderValue("Status");
        }

        javax.swing.GroupLayout panelKameraLayout = new javax.swing.GroupLayout(panelKamera);
        panelKamera.setLayout(panelKameraLayout);
        panelKameraLayout.setHorizontalGroup(
            panelKameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKameraLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(panelKameraLayout.createSequentialGroup()
                .addGroup(panelKameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelKameraLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelKameraLayout.createSequentialGroup()
                        .addGap(270, 270, 270)
                        .addComponent(label_peminjaman)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelKameraLayout.setVerticalGroup(
            panelKameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelKameraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_peminjaman)
                .addGap(41, 41, 41)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 271, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        parent.add(panelKamera, "card3");

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setPreferredSize(new java.awt.Dimension(740, 372));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Nama Penyewa");

        fieldNamaPenyewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldNamaPenyewaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("No telp");

        fieldNoTelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldNoTelpActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Tanggal Sewa");

        fieldAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldAlamatActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("ID Camera");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Alamat");

        fieldTanggalPengembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldTanggalPengembalianActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Status");

        fieldTotalHarga.setBackground(new java.awt.Color(204, 204, 204));
        fieldTotalHarga.setText("0");
        fieldTotalHarga.setBorder(null);
        fieldTotalHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldTotalHargaActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Total Harga");

        btn_submitPinjam.setText("Submit");
        btn_submitPinjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submitPinjamActionPerformed(evt);
            }
        });

        btn_updatePinjam.setText("Update");
        btn_updatePinjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updatePinjamActionPerformed(evt);
            }
        });

        btnClearForm.setText("Clear");
        btnClearForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearFormActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("ID Sewa:");

        fieldIDSewa.setBackground(new java.awt.Color(204, 204, 204));
        fieldIDSewa.setText("0");
        fieldIDSewa.setBorder(null);

        fieldIDCamera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldIDCameraActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Tanggal Pengembalian");

        fieldTanggalSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldTanggalSewaActionPerformed(evt);
            }
        });

        comboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "disewa", "dikembalikan" }));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Durasi:");

        fieldDenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldDendaActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Denda");

        fieldDurasi.setBackground(new java.awt.Color(204, 204, 204));
        fieldDurasi.setText("0");
        fieldDurasi.setBorder(null);
        fieldDurasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldDurasiActionPerformed(evt);
            }
        });

        btn_deletePinjam.setText("Delete");
        btn_deletePinjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deletePinjamActionPerformed(evt);
            }
        });

        jLabel1.setText("Hari");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fieldIDSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btn_submitPinjam)
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fieldAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                            .addComponent(fieldNamaPenyewa)
                            .addComponent(fieldNoTelp)
                            .addComponent(fieldIDCamera, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_updatePinjam)
                    .addComponent(jLabel13))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(fieldDurasi, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel9))
                                    .addGap(6, 6, 6))
                                .addComponent(jLabel10))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fieldTanggalPengembalian, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(fieldTanggalSewa, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                                .addGap(83, 83, 83))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addGap(18, 18, 18)
                                        .addComponent(fieldDenda, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(comboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(18, Short.MAX_VALUE))
                            .addComponent(fieldTotalHarga)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(btn_deletePinjam)
                        .addGap(34, 34, 34)
                        .addComponent(btnClearForm)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fieldNamaPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(fieldTanggalSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(fieldNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(fieldTanggalPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(comboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(fieldIDCamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(57, 57, 57)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_submitPinjam)
                    .addComponent(btn_updatePinjam)
                    .addComponent(btnClearForm)
                    .addComponent(btn_deletePinjam))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(fieldDenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(fieldIDSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(fieldDurasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addGap(35, 35, 35))
        );

        label_peminjaman1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_peminjaman1.setText("PEMINJAMAN KAMERA");

        tabelPeminjaman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Sewa", "Nama Penyewa", "No_telp", "Alamat", "ID Kamera", "Tanggal Sewa", "Tanggal Kembali", "Durasi", "Status", "Denda", "Total"
            }
        ));
        tabelPeminjaman.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                tabelPeminjamanComponentRemoved(evt);
            }
        });
        tabelPeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPeminjamanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelPeminjaman);

        javax.swing.GroupLayout panelPinjamanLayout = new javax.swing.GroupLayout(panelPinjaman);
        panelPinjaman.setLayout(panelPinjamanLayout);
        panelPinjamanLayout.setHorizontalGroup(
            panelPinjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPinjamanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE))
            .addGroup(panelPinjamanLayout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(label_peminjaman1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelPinjamanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelPinjamanLayout.setVerticalGroup(
            panelPinjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPinjamanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_peminjaman1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        parent.add(panelPinjaman, "card4");

        tabelAkun.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Username", "Password"
            }
        ));
        tabelAkun.setMinimumSize(new java.awt.Dimension(61, 80));
        tabelAkun.setRequestFocusEnabled(false);
        tabelAkun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelAkunMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabelAkun);

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Username");

        fieldUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldUsernameActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Password");

        fieldPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldPasswordActionPerformed(evt);
            }
        });

        btnSubmitAkun.setText("Submit");
        btnSubmitAkun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitAkunActionPerformed(evt);
            }
        });

        btnUpdateAkun.setText("Update");
        btnUpdateAkun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateAkunActionPerformed(evt);
            }
        });

        btnDeleteAkun.setText("Delete");
        btnDeleteAkun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAkunActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnSubmitAkun)
                        .addGap(55, 55, 55)
                        .addComponent(btnUpdateAkun)
                        .addGap(53, 53, 53)
                        .addComponent(btnDeleteAkun)
                        .addGap(36, 36, 36)
                        .addComponent(btnClear))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(fieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(jLabel17)
                        .addGap(30, 30, 30)
                        .addComponent(fieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(fieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(fieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmitAkun)
                    .addComponent(btnUpdateAkun)
                    .addComponent(btnDeleteAkun)
                    .addComponent(btnClear))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        label_peminjaman2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_peminjaman2.setText("DATA AKUN");

        javax.swing.GroupLayout panelAkunLayout = new javax.swing.GroupLayout(panelAkun);
        panelAkun.setLayout(panelAkunLayout);
        panelAkunLayout.setHorizontalGroup(
            panelAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAkunLayout.createSequentialGroup()
                .addGroup(panelAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAkunLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelAkunLayout.createSequentialGroup()
                        .addGroup(panelAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAkunLayout.createSequentialGroup()
                                .addGap(148, 148, 148)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAkunLayout.createSequentialGroup()
                                .addGap(293, 293, 293)
                                .addComponent(label_peminjaman2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panelAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelAkunLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        panelAkunLayout.setVerticalGroup(
            panelAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAkunLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_peminjaman2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(175, 175, 175))
            .addGroup(panelAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelAkunLayout.createSequentialGroup()
                    .addContainerGap(620, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(39, 39, 39)))
        );

        parent.add(panelAkun, "card5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(parent, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKelolaKameraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelolaKameraActionPerformed
        // TODO add your handling code here:
		parent.removeAll();
		parent.add(panelKamera);
		parent.repaint();
		parent.validate();
    }//GEN-LAST:event_btnKelolaKameraActionPerformed

    private void btnPinjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPinjamanActionPerformed
        // TODO add your handling code here:
		parent.removeAll();
		parent.add(panelPinjaman);
		parent.repaint();
		parent.validate();
    }//GEN-LAST:event_btnPinjamanActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
		System.exit(0);
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnSignOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignOutActionPerformed
        // TODO add your handling code here:
		this.dispose();
		new Login().setVisible(true);
    }//GEN-LAST:event_btnSignOutActionPerformed

    private void fieldNamaKameraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNamaKameraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldNamaKameraActionPerformed

    private void fieldHargaSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldHargaSewaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldHargaSewaActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
		String namaKamera = fieldNamaKamera.getText();
        String hargaSewaStr = fieldHargaSewa.getText();
		String statusKamera = cmbStatusKamera.getSelectedItem().toString();

        if (namaKamera.isEmpty() || hargaSewaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            
            double hargaSewa = Double.parseDouble(hargaSewaStr);
        
            Kamera kamera = new Kamera();
            kamera.setNama_kamera(namaKamera); 
            kamera.setHarga(hargaSewa);     
			kamera.setStatus(statusKamera);
			
            conCam.create(kamera);

            JOptionPane.showMessageDialog(this, "Data Kamera berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            getDataKamera();

            fieldNamaKamera.setText("");
            fieldHargaSewa.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

		int row = tabelKamera.getSelectedRow();
		if (row == -1) return;

		int idKamera = Integer.parseInt(tabelKamera.getValueAt(row, 0).toString());
		String nama = fieldNamaKamera.getText();
		double harga = Double.parseDouble(fieldHargaSewa.getText());
		String status = cmbStatusKamera.getSelectedItem().toString();

		Kamera k = new Kamera();
		k.setNama_kamera(nama);
		k.setHarga(harga);
		k.setStatus(status);

		conCam.updatePs(idKamera, k); 

		JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
		resetForm();
		getDataKamera();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
		int row = tabelKamera.getSelectedRow();
		if (row == -1) return;

		int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			int idKamera = Integer.parseInt(tabelKamera.getValueAt(row, 0).toString());

			boolean status = conCam.delete(idKamera); // Memanggil fungsi delete

			if (status) {
				JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
				resetForm();
				getDataKamera();
			}
		}
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tabelKameraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelKameraMouseClicked
        // TODO add your handling code here:
		int row = tabelKamera.getSelectedRow();
		if (row != -1) {
        fieldNamaKamera.setText(tabelKamera.getValueAt(row, 1).toString());
        fieldHargaSewa.setText(tabelKamera.getValueAt(row, 2).toString());
        
        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);
        btnSubmit.setEnabled(false); 
    }
    }//GEN-LAST:event_tabelKameraMouseClicked

    private void fieldNamaPenyewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNamaPenyewaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldNamaPenyewaActionPerformed

    private void fieldNoTelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNoTelpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldNoTelpActionPerformed

    private void fieldAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldAlamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldAlamatActionPerformed

    private void fieldTanggalPengembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldTanggalPengembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldTanggalPengembalianActionPerformed

    private void fieldTotalHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldTotalHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldTotalHargaActionPerformed

    private void btn_submitPinjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submitPinjamActionPerformed
        // TODO add your handling code here:
if (validateFieldsRent()) {
        try {
            // Ambil tanggal dari field
            java.time.LocalDate mulai = java.time.LocalDate.parse(fieldTanggalSewa.getText());
            java.time.LocalDate selesai = java.time.LocalDate.parse(fieldTanggalPengembalian.getText());

            // --- LOGIKA DURASI (SELISIH HARI) ---
            long lamaHari = java.time.temporal.ChronoUnit.DAYS.between(mulai, selesai);
            if (lamaHari == 0) lamaHari = 1; 

            // Ambil data kamera untuk mendapatkan harga sewa per hari
            int idKamera = Integer.parseInt(fieldIDCamera.getText());
            Kamera kamera = conCam.findOne(idKamera);

            // 1. Validasi Pertama: Apakah ID Kamera ada di database?
            if (kamera != null) {
                
                // 2. Validasi Kedua: Apakah kamera tersedia?
                if (!kamera.getStatus().equalsIgnoreCase("Tersedia")) {
                    JOptionPane.showMessageDialog(this, "Maaf, kamera dengan ID " + idKamera + " sedang " + kamera.getStatus() + "!", "Kamera Tidak Tersedia", JOptionPane.WARNING_MESSAGE);
                    return; 
                }

                // --- LOGIKA TOTAL HARGA ---
                double totalHarga = kamera.getHarga() * lamaHari;

                // Tampilkan hasil perhitungan ke GUI
                fieldDurasi.setText(String.valueOf(lamaHari));
                fieldTotalHarga.setText(String.valueOf(totalHarga));

                Peminjaman p = new Peminjaman();
                p.setNama_penyewa(fieldNamaPenyewa.getText());
                p.setNo_telp(fieldNoTelp.getText());
                p.setAlamat(fieldAlamat.getText());
                p.setId_kamera(idKamera);
                p.setDurasi((int) lamaHari);
                p.setTgl_sewa(mulai.toString());
                p.setTgl_kembali(selesai.toString());
                p.setStatus(comboStatus.getSelectedItem().toString()); 
                p.setTotal_biaya(totalHarga);
                p.setDenda(0.0);

                // 3. Eksekusi simpan transaksi peminjaman
                conPinjam.create(p);

                if (p.getStatus().equalsIgnoreCase("disewa")) {
                    kamera.setStatus("dipinjam");
                    conCam.updatePs(idKamera, kamera); // Menggunakan updatePs yang sudah direvisi sebelumnya
                }
                
                JOptionPane.showMessageDialog(this, "Peminjaman berhasil disimpan!\nStatus Kamera ID " + idKamera + " kini: Dipinjam");
                
                getDataPeminjaman(); 
                resetForm();
				getDataKamera();
                
            } else {
                JOptionPane.showMessageDialog(this, "ID Kamera tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_btn_submitPinjamActionPerformed

    private void btn_updatePinjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updatePinjamActionPerformed
        // TODO add your handling code here:
		if (validateFieldsRent()) {
        try {
            if (fieldIDSewa.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pilih data dari tabel terlebih dahulu!");
                return;
            }

            int idSewa = Integer.parseInt(fieldIDSewa.getText());
            int idKamera = Integer.parseInt(fieldIDCamera.getText());
            String statusBaru = comboStatus.getSelectedItem().toString(); // Ambil status dari JComboBox
            
            java.time.LocalDate mulai = java.time.LocalDate.parse(fieldTanggalSewa.getText());
            java.time.LocalDate selesai = java.time.LocalDate.parse(fieldTanggalPengembalian.getText());
            long selisih = java.time.temporal.ChronoUnit.DAYS.between(mulai, selesai);
            int durasi = (selisih <= 0) ? 1 : (int) selisih;

            Kamera kamera = conCam.findOne(idKamera);
            if (kamera == null) {
                JOptionPane.showMessageDialog(this, "Kamera tidak ditemukan!");
                return;
            }
            double totalHarga = kamera.getHarga() * durasi;

         
            Peminjaman p = new Peminjaman();
            p.setNama_penyewa(fieldNamaPenyewa.getText());
            p.setNo_telp(fieldNoTelp.getText());
            p.setAlamat(fieldAlamat.getText());
            p.setId_kamera(idKamera);
            p.setDurasi(durasi);
            p.setTgl_sewa(mulai.toString());
            p.setTgl_kembali(selesai.toString());
            p.setStatus(statusBaru);
            p.setDenda(Double.parseDouble(fieldDenda.getText()));
            p.setTotal_biaya(totalHarga);

            
            conPinjam.update(idSewa, p);


            if (statusBaru.equalsIgnoreCase("dikembalikan")) {
                kamera.setStatus("tersedia");
                conCam.updatePs(idKamera, kamera); 
                JOptionPane.showMessageDialog(this, "Data diperbarui. Kamera ID " + idKamera + " sekarang Tersedia.");
            } else {
                JOptionPane.showMessageDialog(this, "Data Berhasil Diperbarui!");
            }

            getDataPeminjaman();
            resetForm();
			getDataKamera();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saat update: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_btn_updatePinjamActionPerformed

    private void btnClearFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearFormActionPerformed
        // TODO add your handling code here:
		resetFormPinjaman();
		tabelPeminjaman.clearSelection();
    }//GEN-LAST:event_btnClearFormActionPerformed

    private void fieldIDCameraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldIDCameraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldIDCameraActionPerformed

    private void fieldTanggalSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldTanggalSewaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldTanggalSewaActionPerformed

    private void fieldDendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldDendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldDendaActionPerformed

    private void tabelPeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPeminjamanMouseClicked
        // Mendapatkan baris yang dipilih
    int row = tabelPeminjaman.getSelectedRow();
    
    if (row != -1) {
			fieldIDSewa.setText(tabelPeminjaman.getValueAt(row, 0).toString());
			fieldNamaPenyewa.setText(tabelPeminjaman.getValueAt(row, 1).toString());
			fieldNoTelp.setText(tabelPeminjaman.getValueAt(row, 2).toString());
			fieldAlamat.setText(tabelPeminjaman.getValueAt(row, 3).toString());
			fieldIDCamera.setText(tabelPeminjaman.getValueAt(row, 4).toString());
			fieldTanggalSewa.setText(tabelPeminjaman.getValueAt(row, 5).toString());
			fieldTanggalPengembalian.setText(tabelPeminjaman.getValueAt(row, 6).toString());
			fieldDurasi.setText(tabelPeminjaman.getValueAt(row, 7).toString());

			String status = tabelPeminjaman.getValueAt(row, 8).toString();
			comboStatus.setSelectedItem(status);

			fieldDenda.setText(tabelPeminjaman.getValueAt(row, 9).toString());
			fieldTotalHarga.setText(tabelPeminjaman.getValueAt(row, 10).toString());

			// Mengatur tombol saat mode edit/pilih data
			btn_submitPinjam.setEnabled(false);
			btn_updatePinjam.setEnabled(true);
			btnClearForm.setEnabled(true);
		}
    }//GEN-LAST:event_tabelPeminjamanMouseClicked

    private void btn_deletePinjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deletePinjamActionPerformed
        // TODO add your handling code here:
		String idStr = fieldIDSewa.getText();

		if (idStr.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Pilih data di tabel terlebih dahulu yang ingin dihapus!");
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, 
				"Apakah Anda yakin ingin menghapus data dengan ID " + idStr + "?", 
				"Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			try {
				int id_sewa = Integer.parseInt(idStr);

				if (conPinjam.delete(id_sewa)) {
					JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus!");

					getDataPeminjaman();
					resetFormPinjaman(); 
				} else {
					JOptionPane.showMessageDialog(this, "Gagal menghapus data!");
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "ID tidak valid!");
			}
		}
    }//GEN-LAST:event_btn_deletePinjamActionPerformed

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel4MouseClicked

    private void fieldDurasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldDurasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldDurasiActionPerformed

    private void tabelAkunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelAkunMouseClicked
        // TODO add your handling code here:
		int row = tabelAkun.getSelectedRow();
		if (row != -1) {
                    
        fieldUsername.setText(tabelAkun.getValueAt(row, 0).toString());
        fieldPassword.setText(tabelAkun.getValueAt(row, 1).toString());
         selectedUsername = fieldUsername.getText();

        
        
        btnSubmitAkun.setEnabled(false);
        btnUpdateAkun.setEnabled(true);
        btnDeleteAkun.setEnabled(true);
    }
    }//GEN-LAST:event_tabelAkunMouseClicked

    private void fieldUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldUsernameActionPerformed

    private void fieldPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldPasswordActionPerformed

    private void btnSubmitAkunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitAkunActionPerformed
        // TODO add your handling code here:
		if (fieldUsername.getText().isEmpty() || fieldPassword.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Username dan Password harus diisi!");
        return;
    }
		User a = new User(fieldUsername.getText(), fieldPassword.getText());
		conlogin.regist(a);
		JOptionPane.showMessageDialog(this, "Akun Berhasil Ditambahkan!");
		getDataAkun();
		resetAkunForm();
    }//GEN-LAST:event_btnSubmitAkunActionPerformed

    private void btnUpdateAkunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateAkunActionPerformed
			try {
        // 1. Ambil data BARU dari form
        String userBaru = fieldUsername.getText().trim();
        String passBaru = fieldPassword.getText().trim();

        // 2. Validasi input kosong
        if (userBaru.isEmpty() || passBaru.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan Password tidak boleh kosong!");
            return;
        }
        
        // 3. Validasi apakah user sudah memilih data di tabel
        if (userBaru.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Silakan pilih data di tabel terlebih dahulu!");
            return;
        }

        // 4. Siapkan objek User dengan data baru
        User a = new User();
        a.setUsername(userBaru);
        a.setPassword(passBaru);

        // 5. Eksekusi Update (Parameter: Username Lama, Objek Data Baru)
        int hasil = conlogin.update(selectedUsername, a);

        if (hasil > 0) {
            JOptionPane.showMessageDialog(this, "Data Akun Berhasil Diperbarui!");
            // 6. Refresh tabel dan reset form
            getDataAkun();
            resetAkunForm();
            selectedUsername = ""; // Kosongkan kembali setelah sukses
        } else {
            JOptionPane.showMessageDialog(this, "Update Gagal: Data tidak ditemukan.");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error saat update akun: " + e.getMessage());
    }
    }//GEN-LAST:event_btnUpdateAkunActionPerformed

    private void btnDeleteAkunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAkunActionPerformed
        // TODO add your handling code here:
		String user = fieldUsername.getText().trim();

    if (user.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Pilih akun yang ingin dihapus terlebih dahulu!");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin menghapus akun: " + user + "?", 
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        if (conlogin.delete(user)) {
            JOptionPane.showMessageDialog(this, "Akun Berhasil Dihapus!");
            
            getDataAkun();
            resetAkunForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menghapus akun!");
        }
    }
    }//GEN-LAST:event_btnDeleteAkunActionPerformed

    private void btnKelolaAkunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelolaAkunActionPerformed
        // TODO add your handling code here:
		parent.removeAll();
		parent.add(panelAkun);
		parent.repaint();
		parent.validate();
    }//GEN-LAST:event_btnKelolaAkunActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
		resetAkunForm();
		tabelAkun.clearSelection();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnClearKameraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearKameraActionPerformed
        // TODO add your handling code here:
		resetForm();
		tabelKamera.clearSelection();
    }//GEN-LAST:event_btnClearKameraActionPerformed

    private void tabelPeminjamanComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tabelPeminjamanComponentRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelPeminjamanComponentRemoved

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
		} catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
			logger.log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(() -> new MainDashboard().setVisible(true));
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClearForm;
    private javax.swing.JButton btnClearKamera;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteAkun;
    private javax.swing.JToggleButton btnKelolaAkun;
    private javax.swing.JButton btnKelolaKamera;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPinjaman;
    private javax.swing.JButton btnSignOut;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnSubmitAkun;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdateAkun;
    private javax.swing.JButton btn_deletePinjam;
    private javax.swing.JButton btn_submitPinjam;
    private javax.swing.JButton btn_updatePinjam;
    private javax.swing.JComboBox<String> cmbStatusKamera;
    private javax.swing.JComboBox<String> comboStatus;
    private javax.swing.JPanel defaultPanel;
    private javax.swing.JTextField fieldAlamat;
    private javax.swing.JTextField fieldDenda;
    private javax.swing.JTextField fieldDurasi;
    private javax.swing.JTextField fieldHargaSewa;
    private javax.swing.JTextField fieldIDCamera;
    private javax.swing.JTextField fieldIDSewa;
    private javax.swing.JTextField fieldNamaKamera;
    private javax.swing.JTextField fieldNamaPenyewa;
    private javax.swing.JTextField fieldNoTelp;
    private javax.swing.JTextField fieldPassword;
    private javax.swing.JTextField fieldTanggalPengembalian;
    private javax.swing.JTextField fieldTanggalSewa;
    private javax.swing.JTextField fieldTotalHarga;
    private javax.swing.JTextField fieldUsername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel label_peminjaman;
    private javax.swing.JLabel label_peminjaman1;
    private javax.swing.JLabel label_peminjaman2;
    private javax.swing.JPanel panelAkun;
    private javax.swing.JPanel panelKamera;
    private javax.swing.JPanel panelPinjaman;
    private javax.swing.JPanel parent;
    private javax.swing.JTable tabelAkun;
    private javax.swing.JTable tabelKamera;
    private javax.swing.JTable tabelPeminjaman;
    // End of variables declaration//GEN-END:variables
}
