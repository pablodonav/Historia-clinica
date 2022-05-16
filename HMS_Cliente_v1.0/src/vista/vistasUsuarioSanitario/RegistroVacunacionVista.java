/**
 * RegistroVacunacionVista.java
 * Adnana Catrinel Dragut
 * v2.0 02/04/2022.
 * 
 */
package vista.vistasUsuarioSanitario;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import control.Hospital;
import control.OyenteVista;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.clasesDTOs.PacienteDTO;
import modelo.clasesDTOs.Tupla;
import modelo.clasesDTOs.VacunaDTO;
import modelo.clasesDTOs.VacunaPacienteDTO;
import modelo.clasesProxys.Comms;
import modelo.clasesProxys.ProxyPaciente;
import modelo.clasesProxys.ProxyVacuna;

/**
 * Clase que contiene los métodos para crear y gestionar 
 * los componentes y los eventos de la pantalla RegistroVacunacionVista.
 * 
 */
public class RegistroVacunacionVista extends javax.swing.JFrame implements PropertyChangeListener{
    private MenuGestionPacientesVista menuGestionPacientesVista = null;
    private Comms comms = null;
    private Gson gson = null;
    private OyenteVista oyenteVista = null;
    private String idConexion = null;
    private ProxyPaciente pxPaciente = null;
    private ProxyVacuna pxVacuna = null;
    private PacienteDTO pacienteSeleccionado = null;
    private List<VacunaPacienteDTO> vacunasPaciente = null;
    private List<VacunaDTO> vacunasDisponibles = null;
    private DefaultTableModel tableModel = null;
    private boolean botonAnyadirVacunaPulsado = false;
    
    private final int INDEX_VACUNA_NO_SELECCIONADA = -1;
    private final int INDEX_CODIGO_VACUNA = 0;
    private final int INDEX_NOMBRE_VACUNA = 1;
    
    /* Mensajes de Error */
    private final String ERROR_OBTENER_VACUNAS = 
            "No se ha podido obtener la lista con vacunas.";
    public final static String ERROR_NUEVA_VACUNA = 
            "No se ha podido realizar la operación para añadir una nueva vacuna al paciente.\n" + 
            "Vuelva a introducir los datos de la vacuna.";
    
    /* Mensajes de Éxito */
    public final static String EXITO_NUEVA_VACUNA = 
            "Se ha añadido una nueva vacuna con código: ";
    
    
    /**
     * Crea e inicializa los componentes de RegistroVacunacionVista.
     * 
     * @param _menuGestionPacientesVista
     * @param _oyenteVista
     * @param _comms
     * @param _idConexion
     * @param _paciente 
     */
    public RegistroVacunacionVista(MenuGestionPacientesVista _menuGestionPacientesVista, 
            OyenteVista _oyenteVista, Comms _comms, String _idConexion, PacienteDTO _paciente) {
        
        this.menuGestionPacientesVista  = _menuGestionPacientesVista;
        this.comms = _comms;
        this.gson = new Gson();
        this.oyenteVista = _oyenteVista;
        this.idConexion = _idConexion;
        this.pxPaciente = ProxyPaciente.getInstance();
        this.pxVacuna = ProxyVacuna.getInstance();
        this.pacienteSeleccionado = _paciente;
        this.vacunasPaciente = obtenerListaConVacunasPaciente();
        this.vacunasDisponibles = obtenerListaConVacunasDisponibles();
        comms.nuevoObservador(this);
       
        initComponents();
        setResizable(false);  //Deshabilita la opción de maximizar-minimizar 
        pack();   // ajusta ventana y sus componentes
        setLocationRelativeTo(null);  // centra en la pantalla
        habilitarBotonConectado(idConexion);
        copiarInformaciónPacienteEnInputFields();
        cargarTablaConVacunas();
        cargarVacunasEnComboBox();
        
        /* Subraya el texto "Datos Paciente" */
        datos_paciente_label.setText("<HTML><U>Datos Paciente</U></HTML>");
        
        b_AnyadirVacuna.setEnabled(false);
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
        paner_superior = new javax.swing.JPanel();
        registroVacunacion_label = new javax.swing.JLabel();
        hospital_icon = new javax.swing.JLabel();
        b_connected = new javax.swing.JButton();
        datos_paciente_label = new javax.swing.JLabel();
        nombre_label = new javax.swing.JLabel();
        nombre_input_field = new javax.swing.JTextField();
        apellido1_label = new javax.swing.JLabel();
        apellido1_input_field = new javax.swing.JTextField();
        nss_label = new javax.swing.JLabel();
        nss_input_field = new javax.swing.JTextField();
        edad_label = new javax.swing.JLabel();
        edad_input_field = new javax.swing.JTextField();
        lista_de_vacunas_label1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla_con_vacunas = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        nueva_vacuna_label = new javax.swing.JLabel();
        vacuna_label = new javax.swing.JLabel();
        fecha_label = new javax.swing.JLabel();
        b_AnyadirVacuna = new javax.swing.JButton();
        b_Atrás = new javax.swing.JButton();
        asterisco_label_vacuna = new javax.swing.JLabel();
        asterisco_label_fecha = new javax.swing.JLabel();
        vacuna_comboBox = new javax.swing.JComboBox<>();
        campo_obligatorio_label = new javax.swing.JLabel();
        fecha_chooser = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panel_principal.setBackground(new java.awt.Color(255, 255, 255));

        paner_superior.setBackground(new java.awt.Color(0, 153, 153));
        paner_superior.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        registroVacunacion_label.setFont(new java.awt.Font("Berlin Sans FB", 1, 24)); // NOI18N
        registroVacunacion_label.setText("Registro Vacunación");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(b_connected, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(paner_superiorLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(registroVacunacion_label)
                        .addGap(0, 215, Short.MAX_VALUE))))
        );
        paner_superiorLayout.setVerticalGroup(
            paner_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hospital_icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(paner_superiorLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(registroVacunacion_label)
                .addGap(16, 16, 16)
                .addComponent(b_connected))
        );

        datos_paciente_label.setFont(new java.awt.Font("Berlin Sans FB", 1, 18)); // NOI18N
        datos_paciente_label.setForeground(new java.awt.Color(0, 153, 153));
        datos_paciente_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        datos_paciente_label.setText("Datos Paciente ");
        datos_paciente_label.setVerticalAlignment(javax.swing.SwingConstants.TOP);

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

        lista_de_vacunas_label1.setFont(new java.awt.Font("Berlin Sans FB", 1, 18)); // NOI18N
        lista_de_vacunas_label1.setForeground(new java.awt.Color(0, 153, 153));
        lista_de_vacunas_label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lista_de_vacunas_label1.setText("Lista de Vacunas");
        lista_de_vacunas_label1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        tabla_con_vacunas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_con_vacunas.setFocusable(false);
        tabla_con_vacunas.setGridColor(new java.awt.Color(0, 153, 153));
        tabla_con_vacunas.setRowHeight(20);
        tabla_con_vacunas.setRowSelectionAllowed(false);
        tabla_con_vacunas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabla_con_vacunas.setShowGrid(true);
        jScrollPane2.setViewportView(tabla_con_vacunas);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        nueva_vacuna_label.setFont(new java.awt.Font("Berlin Sans FB", 1, 18)); // NOI18N
        nueva_vacuna_label.setForeground(new java.awt.Color(0, 153, 153));
        nueva_vacuna_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nueva_vacuna_label.setText("Nueva Vacuna");
        nueva_vacuna_label.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        vacuna_label.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        vacuna_label.setText("Vacuna");

        fecha_label.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        fecha_label.setText("Fecha");

        b_AnyadirVacuna.setBackground(new java.awt.Color(204, 204, 204));
        b_AnyadirVacuna.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        b_AnyadirVacuna.setForeground(new java.awt.Color(0, 153, 153));
        b_AnyadirVacuna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/imgs/plus_icon.png"))); // NOI18N
        b_AnyadirVacuna.setText("Añadir Vacuna");
        b_AnyadirVacuna.setActionCommand("   Nuevo Sanitario");
        b_AnyadirVacuna.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        b_AnyadirVacuna.setEnabled(false);
        b_AnyadirVacuna.setFocusable(false);
        b_AnyadirVacuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_AnyadirVacunaActionPerformed(evt);
            }
        });

        b_Atrás.setBackground(new java.awt.Color(204, 204, 204));
        b_Atrás.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        b_Atrás.setForeground(new java.awt.Color(0, 153, 153));
        b_Atrás.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/imgs/atras_icon.png"))); // NOI18N
        b_Atrás.setText("   Atrás");
        b_Atrás.setActionCommand("   Nuevo Sanitario");
        b_Atrás.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        b_Atrás.setFocusable(false);
        b_Atrás.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_AtrásActionPerformed(evt);
            }
        });

        asterisco_label_vacuna.setFont(new java.awt.Font("Berlin Sans FB", 1, 18)); // NOI18N
        asterisco_label_vacuna.setForeground(new java.awt.Color(255, 0, 0));
        asterisco_label_vacuna.setText("*");

        asterisco_label_fecha.setFont(new java.awt.Font("Berlin Sans FB", 1, 18)); // NOI18N
        asterisco_label_fecha.setForeground(new java.awt.Color(255, 0, 0));
        asterisco_label_fecha.setText("*");

        vacuna_comboBox.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                vacuna_comboBoxPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        campo_obligatorio_label.setFont(new java.awt.Font("Berlin Sans FB", 2, 13)); // NOI18N
        campo_obligatorio_label.setForeground(new java.awt.Color(255, 51, 0));
        campo_obligatorio_label.setText("* Campos obligatorios");

        fecha_chooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                fecha_chooserPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout panel_principalLayout = new javax.swing.GroupLayout(panel_principal);
        panel_principal.setLayout(panel_principalLayout);
        panel_principalLayout.setHorizontalGroup(
            panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paner_superior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_principalLayout.createSequentialGroup()
                .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_principalLayout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(datos_paciente_label))
                    .addGroup(panel_principalLayout.createSequentialGroup()
                        .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_principalLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_principalLayout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(lista_de_vacunas_label1)))
                        .addGap(138, 138, 138)
                        .addComponent(nueva_vacuna_label))
                    .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_principalLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(b_Atrás, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b_AnyadirVacuna, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_principalLayout.createSequentialGroup()
                            .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(panel_principalLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_principalLayout.createSequentialGroup()
                                    .addGap(22, 22, 22)
                                    .addComponent(nombre_label)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(nombre_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(apellido1_label)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(apellido1_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(nss_label)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panel_principalLayout.createSequentialGroup()
                                        .addComponent(nss_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(edad_label)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(edad_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(campo_obligatorio_label, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panel_principalLayout.createSequentialGroup()
                                    .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_principalLayout.createSequentialGroup()
                                            .addComponent(vacuna_label)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(asterisco_label_vacuna))
                                        .addGroup(panel_principalLayout.createSequentialGroup()
                                            .addComponent(fecha_label)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(asterisco_label_fecha)))
                                    .addGap(18, 18, 18)
                                    .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(vacuna_comboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panel_principalLayout.createSequentialGroup()
                                            .addComponent(fecha_chooser, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE))))))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panel_principalLayout.setVerticalGroup(
            panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_principalLayout.createSequentialGroup()
                .addComponent(paner_superior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(datos_paciente_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombre_label)
                    .addComponent(apellido1_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apellido1_label)
                    .addComponent(nss_label)
                    .addComponent(nss_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edad_label)
                    .addComponent(nombre_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edad_input_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_principalLayout.createSequentialGroup()
                        .addComponent(lista_de_vacunas_label1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_principalLayout.createSequentialGroup()
                        .addComponent(nueva_vacuna_label)
                        .addGap(12, 12, 12)
                        .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(vacuna_label)
                            .addComponent(asterisco_label_vacuna, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vacuna_comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_principalLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(fecha_label)
                                    .addComponent(asterisco_label_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel_principalLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(fecha_chooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)))
                        .addComponent(campo_obligatorio_label, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(b_Atrás, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_AnyadirVacuna, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
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
     * Obtiene la lista con las vacunas de un paciente
     * 
     * @return List<VacunaPacienteDTO>
     * @throws Exception 
     */
    private List<VacunaPacienteDTO> obtenerListaConVacunasPaciente(){
        Gson gson = new Gson();
        List<VacunaPacienteDTO> vacunas = null;
        String vacunasToReceive;
        
        try {
            vacunasToReceive = pxPaciente.obtenerVacunasPaciente(pacienteSeleccionado.getNss());
            
            if(vacunasToReceive != null){
                /* Permite obtener las vacunas en un List con VacunaPacienteDTO */
                java.lang.reflect.Type listType = new TypeToken<List<VacunaPacienteDTO>>(){}.getType(); 
                vacunas = gson.fromJson(vacunasToReceive, listType);
            } else{
                throw new Exception(ERROR_OBTENER_VACUNAS);
            }
        } catch (Exception ex) {
            mensajeDialogo(ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        
        return vacunas;
    }
    
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
            b_connected.setEnabled(true);
            b_connected.setText("Connected with id " + _idConexion);
            b_connected.setBackground(Color.GREEN);
        }
    }

    /**
     * Escribe mensaje con diálogo modal.
     * 
     * @param _mensaje
     * @param _messageType
     */    
    public void mensajeDialogo(String _mensaje, int _messageType) {
        JOptionPane.showMessageDialog(this, _mensaje, 
            Hospital.TITULO + " " + Hospital.VERSION, 
            _messageType,  null);    
    } 
    
    /**
     * Rellena la tabla con la información de las vacunas administradas 
     * al paciente seleccionado.
     * 
     */
    private void cargarTablaConVacunas(){
        tableModel = (DefaultTableModel) tabla_con_vacunas.getModel();

        for (int i = 0; i < vacunasPaciente.size(); i++){
            VacunaPacienteDTO vacuna = vacunasPaciente.get(i);
            tableModel.addRow(vacuna.toArray());
        }
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
     * Obtiene la lista con las vacunas disponibles en el sistema para
     * ser administradas
     * 
     * @return List<VacunaDTO>
     * @throws Exception 
     */
    private List<VacunaDTO> obtenerListaConVacunasDisponibles(){
        Gson gson = new Gson();
        List<VacunaDTO> vacunasDisponibles = null;
        String vacunasToReceive = null;
        
        try {
            vacunasToReceive = pxVacuna.obtenerVacunasDisponibles();
            
            if (vacunasToReceive != null){
                /* Permite obtener las vacunas disponibles en un List con VacunaDTO*/
                java.lang.reflect.Type listType = new TypeToken<List<VacunaDTO>>(){}.getType(); 
                vacunasDisponibles = gson.fromJson(vacunasToReceive, listType);
            } else{
                throw new Exception(ERROR_OBTENER_VACUNAS);
            }
        } catch (Exception ex) {
            mensajeDialogo(ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        
        return vacunasDisponibles;
    }
    
    /**
     * Introduce en el comboBox las vacunas disponibles en el sistema.
     * 
     */
    private void cargarVacunasEnComboBox(){
        vacuna_comboBox.setEditable(true);
        for(VacunaDTO vacuna: vacunasDisponibles){
            vacuna_comboBox.addItem(vacuna.getCodigo()+ "-" + vacuna.getNombre());
        }
        vacuna_comboBox.setEditable(false);
        
        /* No muestra ninguna selección en el comboBox sin entrada del usuario */
        vacuna_comboBox.setSelectedIndex(INDEX_VACUNA_NO_SELECCIONADA);
        vacuna_comboBox.setSelectedItem(null);
    }
    
    /**
     * Habilita el botón Guardar si todos los campos 
     * han sido completados y no poseen solo valores en blanco.
     * 
     */
    private void changed(){  
        if (vacuna_comboBox.getSelectedItem() == null ||
            fecha_chooser.getDate() == null){
            
            b_AnyadirVacuna.setEnabled(false);
        }
        else {
            b_AnyadirVacuna.setEnabled(true);
        }
    }
    
    /**
     * Obtiene la vacunaDTO de la vacuna seleccionada
     * 
     * @param _codigo
     * @param _nombre
     * @return VacunaDTO
     */
    private VacunaDTO obtenerVacunaDeComboBox(String _codigo, String _nombre){
        for(VacunaDTO vacuna: vacunasDisponibles){
            if(vacuna.getCodigo() == Integer.parseInt(_codigo) &&
               vacuna.getNombre().equals(_nombre)){
                
                return vacuna;
            }
        }
        return null;
    }
    
    /**
     * Captura la información de los campos rellenados por el usuario y
     * crea el json de una vacuna nueva
     * 
     * @return String
     */
    private String crearJsonVacunaNueva() throws ParseException{
        String datosVacunaComboBox[] = String.valueOf(vacuna_comboBox.getSelectedItem()).split("-");
        VacunaDTO vacunaAdministrada  = obtenerVacunaDeComboBox(datosVacunaComboBox[INDEX_CODIGO_VACUNA], 
                                                                datosVacunaComboBox[INDEX_NOMBRE_VACUNA]);
        Date fecha = fecha_chooser.getDate();
        
        VacunaPacienteDTO vacunaPaciente = new VacunaPacienteDTO(vacunaAdministrada, fecha);
        
        return vacunaPaciente.toJson();
    }
    
    /**
     * Envía una solicitud a la capa control para añadir una nueva vacuna a un paciente
     * 
     * @param evt 
     */
    private void b_AnyadirVacunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_AnyadirVacunaActionPerformed
        try {
            String vacunaJsonToSend = crearJsonVacunaNueva();
            
            if (vacunaJsonToSend != null){
                /* Permite saber si el usuario actual es el que ha solicitado la operación de añadir una nueva vacuna */
                botonAnyadirVacunaPulsado = true;
                
                oyenteVista.eventoProducido(OyenteVista.Evento.NUEVA_VACUNA, 
                    new Tupla <String, String>(vacunaJsonToSend,
                                           pacienteSeleccionado.getNss()));
                
                /* Desactivar botón hasta la próxima selección */
                b_AnyadirVacuna.setEnabled(false);
            } else{
                throw new Exception(ERROR_NUEVA_VACUNA);
            }
        } catch (Exception ex) {
            mensajeDialogo(ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_b_AnyadirVacunaActionPerformed

    /**
     * Cierra la ventana actual y regresa a la ventana MenuGestionPacientesVista
     * 
     * @param evt 
     */
    private void b_AtrásActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_AtrásActionPerformed
        comms.eliminarObservador(this);
        this.dispose();
        menuGestionPacientesVista.setVisible(true);
    }//GEN-LAST:event_b_AtrásActionPerformed

    /**
     * Captura el evento relacionado con el cierre de la ventana, y 
     * envía el evento a la capa control para realizar las acciones
     * de finalización necesarias para la appCliente.
     * 
     * @param evt
     */  
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        oyenteVista.eventoProducido(OyenteVista.Evento.SALIR, null);
    }//GEN-LAST:event_formWindowClosing

    /**
     * Captura los eventos relacionados con la modificación del campo "vacuna".
     * 
     * @param evt
     */
    private void vacuna_comboBoxPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_vacuna_comboBoxPopupMenuWillBecomeInvisible
        changed();
    }//GEN-LAST:event_vacuna_comboBoxPopupMenuWillBecomeInvisible

    /**
     * Captura los eventos relacionados con la modificación del campo "fecha".
     * 
     * @param evt
     */
    private void fecha_chooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_fecha_chooserPropertyChange
        changed();
    }//GEN-LAST:event_fecha_chooserPropertyChange

    /**
     * Recibe evento añadir nueva vacuna
     * 
     * @param _evt 
     */
    private void propiedadNuevaVacuna(PropertyChangeEvent _evt){
        String vacunaJsonToReceive = (String)_evt.getNewValue();
        VacunaPacienteDTO vacunaDTOReceived = gson.fromJson(vacunaJsonToReceive, VacunaPacienteDTO.class);
        
        if(botonAnyadirVacunaPulsado){
            mensajeDialogo(EXITO_NUEVA_VACUNA + vacunaDTOReceived.getVacuna().getCodigo(),
                JOptionPane.INFORMATION_MESSAGE);
            botonAnyadirVacunaPulsado = false;
        }
       
        vacunasPaciente.add(vacunaDTOReceived);
        tableModel.addRow(vacunaDTOReceived.toArray());     
    }  
    
    /**
     * Sobreescribe propertyChange para recibir cambios de modelo.
     * 
     * @param _evt 
     */
    @Override
    public void propertyChange(PropertyChangeEvent _evt) {
        if (_evt.getPropertyName().equals(
            ProxyVacuna.PROPIEDAD_NUEVA_VACUNA)) {
            
            propiedadNuevaVacuna(_evt);
        } 
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellido1_input_field;
    private javax.swing.JLabel apellido1_label;
    private javax.swing.JLabel asterisco_label_fecha;
    private javax.swing.JLabel asterisco_label_vacuna;
    private javax.swing.JButton b_AnyadirVacuna;
    private javax.swing.JButton b_Atrás;
    private javax.swing.JButton b_connected;
    private javax.swing.JLabel campo_obligatorio_label;
    private javax.swing.JLabel datos_paciente_label;
    private javax.swing.JTextField edad_input_field;
    private javax.swing.JLabel edad_label;
    private com.toedter.calendar.JDateChooser fecha_chooser;
    private javax.swing.JLabel fecha_label;
    private javax.swing.JLabel hospital_icon;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lista_de_vacunas_label1;
    private javax.swing.JTextField nombre_input_field;
    private javax.swing.JLabel nombre_label;
    private javax.swing.JTextField nss_input_field;
    private javax.swing.JLabel nss_label;
    private javax.swing.JLabel nueva_vacuna_label;
    private javax.swing.JPanel panel_principal;
    private javax.swing.JPanel paner_superior;
    private javax.swing.JLabel registroVacunacion_label;
    private javax.swing.JTable tabla_con_vacunas;
    private javax.swing.JComboBox<String> vacuna_comboBox;
    private javax.swing.JLabel vacuna_label;
    // End of variables declaration//GEN-END:variables
}
