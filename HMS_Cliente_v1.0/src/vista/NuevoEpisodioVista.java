/**
 * NuevoEpisodioVista.java
 * Adnana Catrinel Dragut
 * v1.0 21/04/2022.
 * 
 */
package vista;

import com.google.gson.Gson;
import control.Hospital;
import control.OyenteVista;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import javax.swing.JOptionPane;
import modelo.clasesDTOs.EpisodioDeAtencionDTO;
import modelo.clasesDTOs.PacienteDTO;
import modelo.clasesProxys.Comms;
import modelo.clasesProxys.ProxyEpisodio;

/**
 * Clase que contiene los métodos para crear y gestionar 
 * los componentes y los eventos de la pantalla NuevoEpisodioVista.
 * 
 */
public class NuevoEpisodioVista extends javax.swing.JFrame implements PropertyChangeListener{
    private MenuGestionPacientesVista menuGestionPacientesVista = null;
    private Comms comms = null;
    private Gson gson = null;
    private OyenteVista oyenteVista = null;
    private String idConexion = null;
    private PacienteDTO pacienteSeleccionado = null;
    
    /**
     * Creates new form NuevoEpisodioVista
     */
    public NuevoEpisodioVista(MenuGestionPacientesVista _menuGestionPacientesVista, 
            OyenteVista _oyenteVista, Comms _comms, String _idConexion, PacienteDTO _paciente) {
        this.menuGestionPacientesVista  = _menuGestionPacientesVista;
        this.comms = _comms;
        this.gson = new Gson();
        this.oyenteVista = _oyenteVista;
        this.idConexion = _idConexion;
        this.pacienteSeleccionado = _paciente;
        comms.nuevoObservador(this);
        
        System.out.println("idCone: " + idConexion);
       
        initComponents();
        setResizable(false);  //Deshabilita la opción de maximizar-minimizar 
        pack();   // ajusta ventana y sus componentes
        setLocationRelativeTo(null);  // centra en la pantalla
        habilitarBotonConectado(idConexion);
        copiarInformaciónPacienteEnInputFields();
       
        b_Guardar.setEnabled(false);
    }

    /**
     * Crea e inicializa los componentes de NuevoEpisodioVista.
     * 
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_principal = new javax.swing.JPanel();
        paner_superior = new javax.swing.JPanel();
        nuevoEpisodio_label = new javax.swing.JLabel();
        hospital_icon = new javax.swing.JLabel();
        b_connected = new javax.swing.JButton();
        lista_de_sanitarios_label = new javax.swing.JLabel();
        nombre_label = new javax.swing.JLabel();
        nombre_input_field = new javax.swing.JTextField();
        apellido1_label = new javax.swing.JLabel();
        apellido1_input_field = new javax.swing.JTextField();
        nss_label = new javax.swing.JLabel();
        nss_input_field = new javax.swing.JTextField();
        edad_label = new javax.swing.JLabel();
        edad_input_field = new javax.swing.JTextField();
        lista_de_sanitarios_label1 = new javax.swing.JLabel();
        motivo_label = new javax.swing.JLabel();
        asterisco_label_motivo = new javax.swing.JLabel();
        motivo_input_field = new javax.swing.JTextField();
        diagnostico_label = new javax.swing.JLabel();
        diagnostico_input_field = new javax.swing.JTextField();
        fecha_label = new javax.swing.JLabel();
        asterisco_label_fecha = new javax.swing.JLabel();
        fecha_chooser = new com.toedter.calendar.JDateChooser();
        b_Cancelar = new javax.swing.JButton();
        b_Guardar = new javax.swing.JButton();
        campo_obligatorio_label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panel_principal.setBackground(new java.awt.Color(255, 255, 255));

        paner_superior.setBackground(new java.awt.Color(0, 153, 153));
        paner_superior.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        nuevoEpisodio_label.setFont(new java.awt.Font("Berlin Sans FB", 1, 24)); // NOI18N
        nuevoEpisodio_label.setText("Nuevo Episodio");

        hospital_icon.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        hospital_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/imgs/hospital_icon.png"))); // NOI18N
        hospital_icon.setMaximumSize(new java.awt.Dimension(70, 70));
        hospital_icon.setMinimumSize(new java.awt.Dimension(70, 70));
        hospital_icon.setPreferredSize(new java.awt.Dimension(70, 70));

        b_connected.setBackground(new java.awt.Color(204, 204, 204));
        b_connected.setText("   ");
        b_connected.setBorder(null);
        b_connected.setEnabled(false);
        b_connected.setFocusable(false);
        b_connected.setSelected(true);

        javax.swing.GroupLayout paner_superiorLayout = new javax.swing.GroupLayout(paner_superior);
        paner_superior.setLayout(paner_superiorLayout);
        paner_superiorLayout.setHorizontalGroup(
            paner_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paner_superiorLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(hospital_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(paner_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paner_superiorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 406, Short.MAX_VALUE)
                        .addComponent(b_connected, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(paner_superiorLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(nuevoEpisodio_label)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        paner_superiorLayout.setVerticalGroup(
            paner_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hospital_icon, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
            .addGroup(paner_superiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nuevoEpisodio_label)
                .addGap(12, 12, 12)
                .addComponent(b_connected))
        );

        lista_de_sanitarios_label.setFont(new java.awt.Font("Berlin Sans FB", 1, 18)); // NOI18N
        lista_de_sanitarios_label.setForeground(new java.awt.Color(0, 153, 153));
        lista_de_sanitarios_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lista_de_sanitarios_label.setText("Datos Paciente ");
        lista_de_sanitarios_label.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        nombre_label.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        nombre_label.setText("Nombre");

        nombre_input_field.setEditable(false);

        apellido1_label.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        apellido1_label.setText("Apellido 1");

        apellido1_input_field.setEditable(false);

        nss_label.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        nss_label.setText("NSS");

        nss_input_field.setEditable(false);

        edad_label.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        edad_label.setText("Edad");

        edad_input_field.setEditable(false);

        lista_de_sanitarios_label1.setFont(new java.awt.Font("Berlin Sans FB", 1, 18)); // NOI18N
        lista_de_sanitarios_label1.setForeground(new java.awt.Color(0, 153, 153));
        lista_de_sanitarios_label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lista_de_sanitarios_label1.setText("Datos Episodio");
        lista_de_sanitarios_label1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        motivo_label.setFont(new java.awt.Font("Berlin Sans FB", 1, 18)); // NOI18N
        motivo_label.setText("Motivo");

        asterisco_label_motivo.setFont(new java.awt.Font("Berlin Sans FB", 1, 18)); // NOI18N
        asterisco_label_motivo.setForeground(new java.awt.Color(255, 0, 0));
        asterisco_label_motivo.setText("*");

        motivo_input_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                motivo_input_fieldKeyTyped(evt);
            }
        });

        diagnostico_label.setFont(new java.awt.Font("Berlin Sans FB", 1, 18)); // NOI18N
        diagnostico_label.setText("Diagnóstico");

        diagnostico_input_field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                diagnostico_input_fieldKeyTyped(evt);
            }
        });

        fecha_label.setFont(new java.awt.Font("Berlin Sans FB", 1, 18)); // NOI18N
        fecha_label.setText("Fecha");

        asterisco_label_fecha.setFont(new java.awt.Font("Berlin Sans FB", 1, 18)); // NOI18N
        asterisco_label_fecha.setForeground(new java.awt.Color(255, 0, 0));
        asterisco_label_fecha.setText("*");

        fecha_chooser.setDateFormatString("dd/MM/yyyy");
        fecha_chooser.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                fecha_chooserComponentAdded(evt);
            }
        });

        b_Cancelar.setBackground(new java.awt.Color(204, 204, 204));
        b_Cancelar.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        b_Cancelar.setForeground(new java.awt.Color(0, 153, 153));
        b_Cancelar.setText("Cancelar");
        b_Cancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        b_Cancelar.setFocusable(false);
        b_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_CancelarActionPerformed(evt);
            }
        });

        b_Guardar.setBackground(new java.awt.Color(204, 204, 204));
        b_Guardar.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        b_Guardar.setForeground(new java.awt.Color(0, 153, 153));
        b_Guardar.setText("Guardar");
        b_Guardar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        b_Guardar.setFocusable(false);
        b_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_GuardarActionPerformed(evt);
            }
        });

        campo_obligatorio_label.setFont(new java.awt.Font("Berlin Sans FB", 2, 13)); // NOI18N
        campo_obligatorio_label.setForeground(new java.awt.Color(255, 51, 0));
        campo_obligatorio_label.setText("* Campos obligatorios");

        javax.swing.GroupLayout panel_principalLayout = new javax.swing.GroupLayout(panel_principal);
        panel_principal.setLayout(panel_principalLayout);
        panel_principalLayout.setHorizontalGroup(
            panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paner_superior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_principalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_principalLayout.createSequentialGroup()
                        .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panel_principalLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lista_de_sanitarios_label))
                            .addGroup(panel_principalLayout.createSequentialGroup()
                                .addComponent(nombre_label)
                                .addGap(18, 18, 18)
                                .addComponent(nombre_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(apellido1_label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(apellido1_input_field)
                                .addGap(12, 12, 12)
                                .addComponent(nss_label)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nss_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(edad_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(edad_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_principalLayout.createSequentialGroup()
                        .addComponent(motivo_label)
                        .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_principalLayout.createSequentialGroup()
                                .addGap(271, 271, 271)
                                .addComponent(lista_de_sanitarios_label1))
                            .addGroup(panel_principalLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(asterisco_label_motivo)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(asterisco_label_fecha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fecha_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fecha_chooser, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_principalLayout.createSequentialGroup()
                        .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panel_principalLayout.createSequentialGroup()
                                .addComponent(b_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(b_Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(motivo_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(diagnostico_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(diagnostico_label))
                            .addComponent(campo_obligatorio_label, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_principalLayout.setVerticalGroup(
            panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_principalLayout.createSequentialGroup()
                .addComponent(paner_superior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lista_de_sanitarios_label)
                .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_principalLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nombre_label)
                            .addComponent(apellido1_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(apellido1_label)
                            .addComponent(nss_label)
                            .addComponent(nss_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(edad_label)
                            .addComponent(edad_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombre_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel_principalLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lista_de_sanitarios_label1)
                                .addGap(1, 1, 1)
                                .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(motivo_label)
                                    .addComponent(asterisco_label_motivo, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_principalLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fecha_chooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11))))
                    .addGroup(panel_principalLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fecha_label)
                            .addComponent(asterisco_label_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(motivo_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(diagnostico_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diagnostico_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campo_obligatorio_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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
    
    /**
     * Muestra un botón con el estado de la appCliente,
     * se mostrará el color verde si la conexión con el servidor
     * ha sido exitosa o el color amarillo en caso contrario. 
     * 
     * @param _idConexion 
     */
    private void habilitarBotonConectado(String _idConexion){
        if (idConexion.equals("0")){
            b_connected.setEnabled(false);
            b_connected.setText("Disconnected");
            b_connected.setBackground(Color.YELLOW);
        } else{
            System.out.println("id " + _idConexion);
            b_connected.setEnabled(true);
            b_connected.setText("Connected with id " + _idConexion);
            b_connected.setBackground(Color.GREEN);
        }
    }
    
    /**
     * Escribe mensaje con diálogo modal.
     * 
     * @param mensaje
     */    
    public void mensajeDialogo(String mensaje, int messageType) {
        JOptionPane.showMessageDialog(this, mensaje, 
            Hospital.TITULO + " " + Hospital.VERSION, 
            messageType,  null);    
    } 
    
    /**
     * Introduce la información del paciente seleccionado en los input fields
     * del apartado correspondiente a "Datos Paciente".
     * 
     */
    private void copiarInformaciónPacienteEnInputFields(){
        nombre_input_field.setText(pacienteSeleccionado.getNombre());
        apellido1_input_field.setText(pacienteSeleccionado.getApellido1());
        nss_input_field.setText(pacienteSeleccionado.getNss());
        edad_input_field.setText(String.valueOf(pacienteSeleccionado.getEdad()));
    }
    
    /**
     * Habilita el botón de Guardar si todos los campos 
     * han sido completados y no poseen solo valores en blanco.
     */
    private void changed(){  
        if (motivo_input_field.getText().isBlank() ||
            fecha_chooser.getDate() == null){
            
            b_Guardar.setEnabled(false);
        }
        else {
            b_Guardar.setEnabled(true);
        }
    }
    
    private void motivo_input_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_motivo_input_fieldKeyTyped
        changed();
    }//GEN-LAST:event_motivo_input_fieldKeyTyped

    private void b_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_CancelarActionPerformed
        this.dispose();
        menuGestionPacientesVista.setVisible(true);
    }//GEN-LAST:event_b_CancelarActionPerformed

    /**
     * Captura la información de los campos rellenados por el usuario y
     * crea el json del paciente con un episodio nuevo
     * 
     * @return String
     */
    private String crearJsonPacienteConEpisodioNuevo(){
        String motivo = motivo_input_field.getText();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(fecha_chooser.getDate());
        java.sql.Date fechaSqlDate = java.sql.Date.valueOf(formattedDate);
        
        EpisodioDeAtencionDTO episodio = new EpisodioDeAtencionDTO(fechaSqlDate, motivo);
        
        if (! diagnostico_input_field.getText().isBlank()){            
            String diagnostico = diagnostico_input_field.getText();
            episodio.setDiagnostico(diagnostico);
        } 
               
        pacienteSeleccionado.anyadirEpisodio(episodio);
        return pacienteSeleccionado.toJson();
    }
    
    private void b_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_GuardarActionPerformed
        String pacienteConEpisodioJsonToSend = crearJsonPacienteConEpisodioNuevo();

        if (pacienteConEpisodioJsonToSend != null){
            System.out.println("Paciente json a enviar:" + pacienteConEpisodioJsonToSend);
            oyenteVista.eventoProducido(OyenteVista.Evento.NUEVO_EPISODIO, pacienteConEpisodioJsonToSend);
        } else{
            mensajeDialogo("Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }//GEN-LAST:event_b_GuardarActionPerformed

    private void diagnostico_input_fieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diagnostico_input_fieldKeyTyped
        changed();
    }//GEN-LAST:event_diagnostico_input_fieldKeyTyped

    /**
     * Captura el evento relacionado con el cierre de la ventana, y 
     * envía el evento a la capa control para realizar las acciones
     * de finalización necesarias para la appCliente.
     * 
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        oyenteVista.eventoProducido(OyenteVista.Evento.SALIR, null);
    }//GEN-LAST:event_formWindowClosing

    private void fecha_chooserComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_fecha_chooserComponentAdded
       changed();
    }//GEN-LAST:event_fecha_chooserComponentAdded
  
    /**
     * Recibe evento nuevo episodio
     * 
     * @param evt 
     */
    private void propiedadNuevoEpisodio(PropertyChangeEvent evt){
        String pacienteConEpisodioJsonToReceive = (String)evt.getNewValue();
        PacienteDTO pacienteConEpisodioDTOReceived = gson.fromJson(
           pacienteConEpisodioJsonToReceive, PacienteDTO.class);

        mensajeDialogo("Se ha añadido un nuevo episodio correctamente al paciente con nss: " + 
           pacienteConEpisodioDTOReceived.getNss(), JOptionPane.INFORMATION_MESSAGE);

        comms.eliminarObservador(this);
        this.dispose();
        menuGestionPacientesVista.setVisible(true);      
    }
    
    /**
     * Sobreescribe propertyChange para recibir cambios de modelo.
     * 
     * @param evt 
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(
            ProxyEpisodio.PROPIEDAD_NUEVO_EPISODIO)) {
            
            propiedadNuevoEpisodio(evt);
        } 
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellido1_input_field;
    private javax.swing.JLabel apellido1_label;
    private javax.swing.JLabel asterisco_label_fecha;
    private javax.swing.JLabel asterisco_label_motivo;
    private javax.swing.JButton b_Cancelar;
    private javax.swing.JButton b_Guardar;
    private javax.swing.JButton b_connected;
    private javax.swing.JLabel campo_obligatorio_label;
    private javax.swing.JTextField diagnostico_input_field;
    private javax.swing.JLabel diagnostico_label;
    private javax.swing.JTextField edad_input_field;
    private javax.swing.JLabel edad_label;
    private com.toedter.calendar.JDateChooser fecha_chooser;
    private javax.swing.JLabel fecha_label;
    private javax.swing.JLabel hospital_icon;
    private javax.swing.JLabel lista_de_sanitarios_label;
    private javax.swing.JLabel lista_de_sanitarios_label1;
    private javax.swing.JTextField motivo_input_field;
    private javax.swing.JLabel motivo_label;
    private javax.swing.JTextField nombre_input_field;
    private javax.swing.JLabel nombre_label;
    private javax.swing.JTextField nss_input_field;
    private javax.swing.JLabel nss_label;
    private javax.swing.JLabel nuevoEpisodio_label;
    private javax.swing.JPanel panel_principal;
    private javax.swing.JPanel paner_superior;
    // End of variables declaration//GEN-END:variables
}
