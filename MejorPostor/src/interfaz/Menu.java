package interfaz;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JCalendar;

import logica.Empresa;
import logica.Oferta;

import utils.ArchivosJson;
import utils.InfoOfertas;

import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class Menu {

	private JFrame frame;
	private JTextField campoOferente;
	private JTextField campoMonto;
	private JPanel panelOfertasDia;
	private JPanel panelCalendario;
	private JPanel panel;
	private JComboBox<String> campoTipoShow;
	private JComboBox<Integer> campoHoraHasta;
	private JComboBox<Integer> campoHoraDesde;
	private JCalendar calendario; 
	private String fecha;
	private boolean diaTermino;
	private DefaultTableModel model;
	private DefaultTableModel modelSeleccionadas;
	private JButton botonAgregar;	

	
    //_______________________________________________________________//
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		InfoOfertas.obtenerOfertasTotalesJson();
		InfoOfertas.obtenerOfertasSeleccionadasJson();
		diaTermino = Empresa.diaTerminado();
		crearVentana();
		crearBotones();
		crearComboBoxesFormulario();
		crearCamposTextoTabla();		
		crearTablaDeOfertas();
		crearTextoIndicacionesFormulario();		
		agregarFondo();		
		crearPanelOfertas();		
		crearParteCalendario();
		crearPanelGrafico();
		llenarTablaOfertasTotales();
	}
	
	//_________________________VENTANA_______________________________//
	
	private void crearVentana() {
		frame = new JFrame();
		frame.setBounds(200, 100, 1000, 600);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Mejor Postor");
	}
	
	//___________________________FONDO_______________________________//	
	
	private void agregarFondo() {
		JLabel fondoIzquierda = new JLabel("");
		fondoIzquierda.setIcon(new ImageIcon(Menu.class.getResource("/imagen/fondoTP.png")));
		fondoIzquierda.setBackground(new Color(0, 0, 0));
		fondoIzquierda.setBounds(0, 0, 270, 611);
		frame.getContentPane().add(fondoIzquierda);
		
		JLabel fondoAbajo = new JLabel("");
		fondoAbajo.setIcon(new ImageIcon(Menu.class.getResource("/imagen/fondoTP.png")));
		fondoAbajo.setBounds(151, 503, 835, 60);
		frame.getContentPane().add(fondoAbajo);
	}
	
	//________________________FORMULARIO_____________________________//	
	
	private void crearTextoIndicacionesFormulario() {
		JLabel textoInformacion = new JLabel("Informacion de la oferta para mañana");
		textoInformacion.setForeground(new Color(255, 255, 255));
		textoInformacion.setHorizontalAlignment(SwingConstants.CENTER);
		textoInformacion.setBounds(28, 33, 220, 14);
		frame.getContentPane().add(textoInformacion);
	}

	private void crearComboBoxes() {
	    campoTipoShow = crearComboBox(new String[]{"Comedia", "Musical", "Charla", "Teatro", "Magia", "Academicos", "Politicos"}, 
	                                  118, 287, 112, 22, 7);
	    campoHoraDesde = crearComboBox(new Integer[0], 118, 141, 112, 22, 25);
	    campoHoraHasta = crearComboBox(new Integer[0], 118, 192, 112, 22, 25);
	}

	private <T> JComboBox<T> crearComboBox(T[] items, int x, int y, int width, int height, int maxRows) {
	    JComboBox<T> comboBox = new JComboBox<>(items);
	    comboBox.setMaximumRowCount(maxRows);
	    comboBox.setBounds(x, y, width, height);
	    frame.getContentPane().add(comboBox);
	    return comboBox;
	}
	
	private void crearComboBoxesFormulario() {
	    crearComboBoxes();
	    agregarHorasComboBox(campoHoraDesde, 0, 24);
	    agregarHorasComboBox(campoHoraHasta, 0, 24);
	}

	private void agregarHorasComboBox(JComboBox<Integer> comboBox, int inicio, int fin) {
	    for (int i = inicio; i <= fin; i++) 
	        comboBox.addItem(i);
	}
	
	
	// _ _ _ _ _ _ _ _ _ _ _ACCIONES FORMULARIO_ _ _ _ _ _ _ _ _ _ _ //	
	
	private boolean verificarDatosFormulario() {
	    if (campoOferente.getText().isEmpty() || campoOferente.getText().charAt(0) == ' ') {
	        mostrarMensaje("Nombre del oferente inválido.");
	        return false;
	    }
	    if (campoHoraDesde.getSelectedIndex() >= campoHoraHasta.getSelectedIndex()) {
	        mostrarMensaje("Horario inválido.");
	        return false;
	    }
	    if (campoMonto.getText().isEmpty()) {
	        mostrarMensaje("Monto inválido.");
	        return false;
	    }
	    return true;
	}
	
	private void limpiarCamposFormulario() {
	    campoOferente.setText("");
	    campoMonto.setText("");
	    campoHoraDesde.setSelectedIndex(-1);
	    campoHoraHasta.setSelectedIndex(-1);
	    model.setRowCount(0);
	}

	private Oferta crearOfertaDesdeFormulario() {
	    String oferente = campoOferente.getText();
	    int horaDesde = campoHoraDesde.getSelectedIndex();
	    int horaHasta = campoHoraHasta.getSelectedIndex();
	    double monto = Double.parseDouble(campoMonto.getText());
	    String tipoShow = (String) campoTipoShow.getSelectedItem();
	    String fechaManiana = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    return new Oferta(oferente, horaDesde, horaHasta, monto, tipoShow, fechaManiana);
	}
	
	private void mostrarMensaje(String mensaje) {
	    JOptionPane.showMessageDialog(frame, mensaje, "Aviso", JOptionPane.INFORMATION_MESSAGE);
	}
	
	//__________________________TABLAS______________________________//
	
	private void crearPanel(int x, int y, int ancho, int alto, Color color) {
		panel = new JPanel();
		panel.setBackground(color);
		panel.setBounds(x,y,ancho,alto);
		panel.setVisible(true);
		frame.getContentPane().add(panel);
	}
	
	private JScrollPane crearPanelDesplazable(int x, int y, int ancho, int alto) {
		JScrollPane panelDesplazable = new JScrollPane();
		panelDesplazable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelDesplazable.setBounds(x, y, ancho, alto);
		panel.add(panelDesplazable);
		return panelDesplazable;
	}
	
	private void crearPanelOfertas() {
		crearPanel(241, 0, 744, 561, new Color(192, 192, 192));
		JScrollPane panelDesplazable = crearPanelDesplazable(50, 46, 698, 408);
		crearTablaOfertas(panelDesplazable);
	}
	
	
	private void crearTablaOfertas(JScrollPane scrollPane) {
		JTable tablaOfertasTotales = new JTable();
		tablaOfertasTotales.setForeground(new Color(255, 255, 255));
		tablaOfertasTotales.setBackground(new Color(0, 0, 0));
		model = new DefaultTableModel();
		tablaOfertasTotales.setModel(model);
			
		model.addColumn("Oferente");
		model.addColumn("Monto");
		model.addColumn("Desde");
		model.addColumn("Hasta");
		model.addColumn("Categoria");
		scrollPane.setViewportView(tablaOfertasTotales);
	}
	
	private void crearPanelGrafico() {
		
		crearPanelOfertasDia();
		JScrollPane scrollPaneUno = crearScrollPaneUno();
		//SE CREA LA TABLA DE OFERTAS TOTALES
		crearTablaOfertasSeleccionadas(scrollPaneUno);
		
	}

	private void crearTablaOfertasSeleccionadas(JScrollPane scrollPaneUno) {
		JTable tablaOfertasSeleccionadas = new JTable();
		tablaOfertasSeleccionadas.setForeground(new Color(255, 255, 255));
		tablaOfertasSeleccionadas.setBackground(new Color(0, 0, 0));
		modelSeleccionadas = new DefaultTableModel();
		tablaOfertasSeleccionadas.setModel(modelSeleccionadas);
		
		modelSeleccionadas.addColumn("Oferente");
		modelSeleccionadas.addColumn("Monto");
		modelSeleccionadas.addColumn("Desde");
		modelSeleccionadas.addColumn("Hasta");
		modelSeleccionadas.addColumn("Categoria");
		modelSeleccionadas.addColumn("Fecha");
		tablaOfertasSeleccionadas.setVisible(true);
		scrollPaneUno.setViewportView(tablaOfertasSeleccionadas);
	}

	private JScrollPane crearScrollPaneUno() {
		JScrollPane scrollPaneUno = new JScrollPane();
		scrollPaneUno.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneUno.setBounds(37, 46, 698, 408);
		panelOfertasDia.add(scrollPaneUno);
		return scrollPaneUno;
	}

	private void crearPanelOfertasDia() {
		panelOfertasDia = new JPanel();
		panelOfertasDia.setBackground(new Color(192, 192, 192));
		panelOfertasDia.setBounds(240, 162, 744, 350);
		panelOfertasDia.setVisible(false);
		frame.getContentPane().add(panelOfertasDia);
	}
	
	private void crearCamposTextoTabla() {
		
		campoMonto = new JTextField();
		campoMonto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit(e.getKeyChar())) {
					e.consume();
				}
				super.keyTyped(e);
			}
		});
		campoMonto.setColumns(10);
		campoMonto.setBounds(118, 244, 112, 20);
		frame.getContentPane().add(campoMonto);
		
		campoOferente = new JTextField();
		campoOferente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isAlphabetic(e.getKeyChar()) && !Character.isWhitespace(e.getKeyChar())) {
					e.consume();
				}
				super.keyTyped(e);
			}
		});
		campoOferente.setBounds(118, 97, 112, 20);
		frame.getContentPane().add(campoOferente);
		campoOferente.setColumns(10);
	}
	
	private void llenarTablaOfertasTotales() {
		ArrayList<Oferta>listaOfertas = InfoOfertas.getOfertasTotales();
		if (listaOfertas!=null) {
			for(Oferta of : listaOfertas) {
				Object[]fila = new Object[6];
			fila[0]=of.getOferente();
			fila[1]=of.getMonto();
			fila[2]=of.getHoraDesde();
			fila[3]=of.getHoraHasta();
				fila[4]=of.getTipoShow();
			fila[5]=of.getFechaManiana();	
				
			model.addRow(fila);
			}
		}
	}
		
	private void llenarTablaOfertasSeleccionadas() {
		ArrayList<Oferta>listaOfertas = InfoOfertas.obtenerOfertasDelDia(fecha);
		if (listaOfertas!=null) {
		for(Oferta of : listaOfertas) {
			Object[]fila = new Object[6];
			fila[0]=of.getOferente();
			fila[1]=of.getMonto();
			fila[2]=of.getHoraDesde();
			fila[3]=of.getHoraHasta();
			fila[4]=of.getTipoShow();
			fila[5]=of.getFechaManiana();
			
			modelSeleccionadas.addRow(fila);
			}
		}
	}
		
	
	//__________________________BOTONES_____________________________//	
	
	private void crearBotones() {
	    agregarBoton("Terminar día y reservar convenientes", 10, 415, 250, 23, e -> terminarDia());
	    botonAgregar = agregarBoton("Agregar oferta", 74, 368, 121, 23, e -> agregarOferta());
	    agregarBoton("Ver todas las ofertas reservadas por fecha", 630, 522, 275, 23, e -> mostrarPanelCalendario());
	    agregarBoton("Ver todas las posibles ofertas para mañana", 335, 522, 280, 23, e -> mostrarPanelOfertas());
	}

	private JButton agregarBoton(String texto, int x, int y, int ancho, int alto, ActionListener accion) {
	    JButton boton = new JButton(texto);
	    boton.setBounds(x, y, ancho, alto);
	    boton.addActionListener(accion);
	    frame.getContentPane().add(boton);
	    return boton;
	}
	
	//_ _ _ _ _ _ _ _ _ _ _ _ACCIONES BOTONES_ _ _ _ _ _ _ _ _ _ _ _//	

	private void terminarDia() {
	    if (!diaTermino) {
	        Empresa.seleccionarOfertasPorMonto();
	        Empresa.getOfertaSolucion().forEach(oferta -> InfoOfertas.guardarOfertasSeleccionadas(oferta));
	        ArchivosJson.guardarComoJSON("ofertasSeleccionadas", InfoOfertas.getOfertasSeleccionadas());
	        botonAgregar.setEnabled(false);
	        diaTermino = true;
	    } else {
	        mostrarMensaje("El día ya terminó, espere hasta mañana para agregar más ofertas y reservar las más convenientes.");
	    }
	}

	private void agregarOferta() {
	    if (!diaTermino) {
	        if (verificarDatosFormulario()) {
	            Oferta oferta = crearOfertaDesdeFormulario();
	            if (!Empresa.estaRepetida(oferta)) {
	                Empresa.guardar(oferta);
	                InfoOfertas.guardarOfertasTotales(oferta);
	                ArchivosJson.guardarComoJSON("ofertasTotales", InfoOfertas.getOfertasTotales());
	                mostrarMensaje("Se agregó la oferta correctamente.");
	                limpiarCamposFormulario();
	                llenarTablaOfertasTotales();
	            } else {
	                mostrarMensaje("La oferta ingresada ya existe.");
	            }
	        }
	    } else {
	        mostrarMensaje("El día ya terminó, espere hasta mañana para agregar más ofertas.");
	    }
	}
	
	
		
	//________________________CALENDARIO_____________________________//	

	private void crearParteCalendario() {
		crearPanelCalendario();
		crearCalendario();
		JLabel logotipo = new JLabel("");
		logotipo.setIcon(new ImageIcon(Menu.class.getResource("/imagen/Logotipo.png")));
		logotipo.setBounds(55, 0, 184, 173);
		panelCalendario.add(logotipo);
		
		JLabel titulosCeldaeleccioneDia = new JLabel("Seleccione un dia para ver sus ofertas");
		titulosCeldaeleccioneDia.setBounds(484, 74, 260, 14);
		panelCalendario.add(titulosCeldaeleccioneDia);
		titulosCeldaeleccioneDia.setForeground(new Color(255, 255, 255));
		titulosCeldaeleccioneDia.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private void crearCalendario() {
		calendario = new JCalendar();
		calendario.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				
				if (evt.getOldValue() != null) {
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					fecha = format.format(calendario.getCalendar().getTime());
					modelSeleccionadas.setRowCount(0);
					llenarTablaOfertasSeleccionadas();
				}
			}
		});
		
		calendario.setSundayForeground(new Color(255, 0, 0));
		calendario.setWeekdayForeground(new Color(255, 255, 255));
		calendario.getDayChooser().setDecorationBackgroundColor(new Color(255, 255, 255));
		calendario.getDayChooser().setDayBordersVisible(true);
		calendario.getDayChooser().getDayPanel().setForeground(new Color(255, 255, 255));
		calendario.getDayChooser().setWeekdayForeground(new Color(0, 0, 0));
		calendario.setDecorationBackgroundColor(new Color(255, 255, 255));
		calendario.getDayChooser().getDayPanel().setBackground(new Color(0, 0, 0));
		calendario.setBounds(267, 5, 215, 153);
		panelCalendario.add(calendario);
	}

	private void crearPanelCalendario() {
		panelCalendario = new JPanel();
		panelCalendario.setBackground(new Color(128, 128, 128));
		panelCalendario.setBounds(240, 0, 744, 163);
		panelCalendario.setVisible(false);
		frame.getContentPane().add(panelCalendario);
		panelCalendario.setLayout(null);
	}
	
	private void mostrarPanelCalendario() {
	    panelOfertasDia.setVisible(true);
	    panelCalendario.setVisible(true);
	    panel.setVisible(false);
	}

	private void mostrarPanelOfertas() {
	    panelOfertasDia.setVisible(false);
	    panelCalendario.setVisible(false);
	    panel.setVisible(true);
	}
		
	//__________________TABLA CON TODAS LAS OFERTAS__________________//
	
	private void crearTablaDeOfertas() {
		List<String> titulosCelda = Arrays.asList("Tipo de Show", "Monto", "Hora Hasta", "Hora Desde", "Oferente");
		List<Integer> posicionesYTitulosCelda = Arrays.asList(291, 247, 196, 149, 100);
		IntStream.range(0, titulosCelda.size())
	             .forEach(i -> crearCelda(titulosCelda.get(i), 28, posicionesYTitulosCelda.get(i)));
	}
	
	private void crearCelda(String texto, int x, int y) {
	    JLabel label = new JLabel(texto);
	    label.setHorizontalAlignment(SwingConstants.CENTER);
	    label.setForeground(Color.WHITE);
	    label.setBounds(x, y, 89, 14);
	    frame.getContentPane().add(label);
	}
}