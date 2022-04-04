/**
 * MenuAdminVista.java
 * Adnana Catrinel Dragut
 * v1.0 26/03/2022.
 * 
 */
package vista;

import control.OyenteVista;
import modelo.clasesProxys.Comms;
import modelo.clasesProxys.ProxySanitario;

/**
 * Clase que contiene los métodos para crear y gestionar 
 * los componentes y los eventos de la pantalla MenúAdmin.
 * 
 */
public class MenuAdminVista extends javax.swing.JFrame {
    private OyenteVista oyenteVista = null;
    private ProxySanitario pxSanitario;
    
    
    /**
     * Crea e inicializa los componentes de MenuAdminVista.
     */
    public MenuAdminVista(OyenteVista _oyenteVista, ProxySanitario _pxSanitario) {
        this.oyenteVista = _oyenteVista;
        this.pxSanitario = _pxSanitario;
        
        initComponents();
        setResizable(false);  //Deshabilita la opción de maximizar-minimizar 
        pack();   // ajusta ventana y sus componentes
        setLocationRelativeTo(null);  // centra en la pantalla
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_principal = new javax.swing.JPanel();
        panel_izqd = new javax.swing.JPanel();
        hospital_icon = new javax.swing.JLabel();
        hospital_label = new javax.swing.JLabel();
        management_label = new javax.swing.JLabel();
        system_label = new javax.swing.JLabel();
        b_NuevoSanitario = new javax.swing.JButton();
        b_EditarSanitario = new javax.swing.JButton();
        b_Salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel_principal.setBackground(new java.awt.Color(255, 255, 255));
        panel_principal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        panel_izqd.setBackground(new java.awt.Color(0, 153, 153));
        panel_izqd.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        hospital_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/imgs/hospital_icon.png"))); // NOI18N

        hospital_label.setFont(new java.awt.Font("Berlin Sans FB", 1, 24)); // NOI18N
        hospital_label.setText("HOSPITAL");

        management_label.setFont(new java.awt.Font("Berlin Sans FB", 1, 24)); // NOI18N
        management_label.setText("MANAGEMENT");

        system_label.setFont(new java.awt.Font("Berlin Sans FB", 1, 24)); // NOI18N
        system_label.setText("SYSTEM");

        javax.swing.GroupLayout panel_izqdLayout = new javax.swing.GroupLayout(panel_izqd);
        panel_izqd.setLayout(panel_izqdLayout);
        panel_izqdLayout.setHorizontalGroup(
            panel_izqdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_izqdLayout.createSequentialGroup()
                .addGroup(panel_izqdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_izqdLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(hospital_icon)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_izqdLayout.createSequentialGroup()
                        .addGap(0, 25, Short.MAX_VALUE)
                        .addGroup(panel_izqdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(system_label)
                            .addComponent(management_label)
                            .addComponent(hospital_label))))
                .addContainerGap())
        );
        panel_izqdLayout.setVerticalGroup(
            panel_izqdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_izqdLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(hospital_icon, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(hospital_label)
                .addGap(33, 33, 33)
                .addComponent(management_label)
                .addGap(28, 28, 28)
                .addComponent(system_label)
                .addGap(48, 48, 48))
        );

        b_NuevoSanitario.setBackground(new java.awt.Color(204, 204, 204));
        b_NuevoSanitario.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        b_NuevoSanitario.setForeground(new java.awt.Color(0, 153, 153));
        b_NuevoSanitario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/imgs/add_user.png"))); // NOI18N
        b_NuevoSanitario.setText("Nuevo Sanitario");
        b_NuevoSanitario.setActionCommand("   Nuevo Sanitario");
        b_NuevoSanitario.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        b_NuevoSanitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_NuevoSanitarioActionPerformed(evt);
            }
        });

        b_EditarSanitario.setBackground(new java.awt.Color(204, 204, 204));
        b_EditarSanitario.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        b_EditarSanitario.setForeground(new java.awt.Color(0, 153, 153));
        b_EditarSanitario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/imgs/edit_user.png"))); // NOI18N
        b_EditarSanitario.setText("Editar Sanitario");
        b_EditarSanitario.setActionCommand("   Nuevo Sanitario");
        b_EditarSanitario.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        b_EditarSanitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_EditarSanitarioActionPerformed(evt);
            }
        });

        b_Salir.setBackground(new java.awt.Color(204, 204, 204));
        b_Salir.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        b_Salir.setForeground(new java.awt.Color(0, 153, 153));
        b_Salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/imgs/exit.png"))); // NOI18N
        b_Salir.setText("Salir");
        b_Salir.setActionCommand("   Nuevo Sanitario");
        b_Salir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        b_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_SalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_principalLayout = new javax.swing.GroupLayout(panel_principal);
        panel_principal.setLayout(panel_principalLayout);
        panel_principalLayout.setHorizontalGroup(
            panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_principalLayout.createSequentialGroup()
                .addComponent(panel_izqd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_principalLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(b_EditarSanitario, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b_NuevoSanitario, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(68, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_principalLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(b_Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        panel_principalLayout.setVerticalGroup(
            panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_izqd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_principalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(b_NuevoSanitario, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(b_EditarSanitario, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(b_Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b_EditarSanitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_EditarSanitarioActionPerformed

    }//GEN-LAST:event_b_EditarSanitarioActionPerformed

    /**
     * Habilita la pantalla que permite añadir un nuevo sanitario al sistema
     * 
     * @param evt 
     */
    private void b_NuevoSanitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_NuevoSanitarioActionPerformed
        setVisible(false);
        new NuevoSanitarioVista(this, oyenteVista, pxSanitario).setVisible(true);
    }//GEN-LAST:event_b_NuevoSanitarioActionPerformed

    /**
     * Provoca el cierre de la IU de la app
     * 
     * @param evt 
     */
    private void b_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_SalirActionPerformed
        oyenteVista.eventoProducido(OyenteVista.Evento.SALIR, null);
    }//GEN-LAST:event_b_SalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_EditarSanitario;
    private javax.swing.JButton b_NuevoSanitario;
    private javax.swing.JButton b_Salir;
    private javax.swing.JLabel hospital_icon;
    private javax.swing.JLabel hospital_label;
    private javax.swing.JLabel management_label;
    private javax.swing.JPanel panel_izqd;
    private javax.swing.JPanel panel_principal;
    private javax.swing.JLabel system_label;
    // End of variables declaration//GEN-END:variables
}
