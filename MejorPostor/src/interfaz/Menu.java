package interfaz;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.toedter.calendar.JCalendar;

import logica.ComparadorMonto;
import logica.Empresa;
import logica.Oferta;
import logica.Solucion;
import logica.SolverGoloso;
import utils.ArchivosJson;

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
	private JPanel panelOfertasTotales;
	private JComboBox<String> campoTipoShow;
	private JComboBox<Integer> campoHoraHasta;
	private JComboBox<Integer> campoHoraDesde;
	private JCalendar calendario; 
	private Set<Oferta> ofertasTotales;
	private Set<Oferta> ofertasSeleccionadas;
	private Empresa empresa;
	private Solucion solucion;
	private String fecha;
	private boolean diaTermino;
	private DefaultTableModel model;
	private DefaultTableModel modelSeleccionadas;
	private JButton botonAgregar;
	
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
		inicializarVariables();
		initialize();
	}

	private void inicializarVariables() {
		ofertasTotales = new HashSet<Oferta>();
		ofertasSeleccionadas = new HashSet<Oferta>();
		empresa = new Empresa();
		solucion = new Solucion();
		diaTermino = false;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		obtenerOfertasTotalesJson();
		obtenerOfertasSeleccionadasJson();
		
		verificarDiaTermino();
		
		crearFrame();
		
		crearBotones();
		crearComboBox();
		crearCampoTexto();
		
		crearTextoInformativo();
		crearTituloInformativo();
		
		agregarFondo();
		
		crearPanelOfertasTotales();
		
		crearParteCalendario();
		crearPanelGrafico();
		
	}

	private void verificarDiaTermino() {
		LocalDate ret = LocalDate.now();
		ret = ret.plusDays(1);
		String fechaVerificar = ret.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		for(Oferta of : ofertasSeleccionadas) {
			if(of.getFechaManiana().equals(fechaVerificar)) {
				diaTermino = true;
				return;
			}
		}
	}

	private void crearPanelOfertasTotales() {
		panelOfertasTotales = new JPanel();
		panelOfertasTotales.setBackground(new Color(192, 192, 192));
		panelOfertasTotales.setBounds(241,0,744,561);
		panelOfertasTotales.setVisible(false);
		frame.getContentPane().add(panelOfertasTotales);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(37, 46, 698, 408);
		panelOfertasTotales.add(scrollPane);
		
		//SE CREA LA TABLA DE OFERTAS TOTALES
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
		model.addColumn("Fecha");
		tablaOfertasTotales.setVisible(true);
		scrollPane.setViewportView(tablaOfertasTotales);
		
	}

	private void llenarTablaOfertasTotales() {
		Set<Oferta>listaOfertas = ofertasTotales;
		if (listaOfertas!=null) {
		for(Oferta of : listaOfertas) {
			Object[]fila = new Object[6];
			fila[0]=of.getOferente();
			fila[1]=of.getMonto();
			fila[2]=of.getHoraDesde();
			fila[3]=of.getHoraHasta();
			fila[4]=of.getTipoShow();
			fila[5]=fecha;	
			
			model.addRow(fila);
			}
		}
	}
	
	private void llenarTablaOfertasSeleccionadas() {
		Set<Oferta>listaOfertas = obtenerOfertaDiaSeleccionado(fecha);
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
	
	private void crearPanelGrafico() {
		panelOfertasDia = new JPanel();
		panelOfertasDia.setBackground(new Color(192, 192, 192));
		panelOfertasDia.setBounds(240, 162, 744, 350);
		panelOfertasDia.setVisible(false);
		frame.getContentPane().add(panelOfertasDia);
		
		
		JScrollPane scrollPaneUno = new JScrollPane();
		scrollPaneUno.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneUno.setBounds(37, 46, 698, 408);
		panelOfertasDia.add(scrollPaneUno);
		
		//SE CREA LA TABLA DE OFERTAS TOTALES
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

	private void crearCampoTexto() {
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
	
	private void crearComboBox() {
		String[] shows = {"Comedia", "Musical", "Charla", "Teatro","Magia","Academicos", "Politicos"};
		
		campoTipoShow = new JComboBox<String>(shows);
		campoTipoShow.setMaximumRowCount(7);
		campoTipoShow.setBounds(118, 287, 112, 22);
		frame.getContentPane().add(campoTipoShow);
		
		campoHoraHasta = new JComboBox<Integer>();
		campoHoraHasta.setMaximumRowCount(25);
		campoHoraHasta.setBounds(118, 192, 112, 22);
		frame.getContentPane().add(campoHoraHasta);
		
		campoHoraDesde = new JComboBox<Integer>();
		campoHoraDesde.setMaximumRowCount(25);
		campoHoraDesde.setBounds(118, 141, 112, 22);
		frame.getContentPane().add(campoHoraDesde);
		
		for(int i = 0; i <= 24; i++) {		
			campoHoraDesde.addItem(i);
			campoHoraHasta.addItem(i);
		}
	}

	private void crearBotones() {
		JButton botonTerminar = new JButton("Terminar Dia");
		botonTerminar.setBounds(55, 437, 121, 23);
		frame.getContentPane().add(botonTerminar);
		botonTerminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!diaTermino) {
					ComparadorMonto comparador = new ComparadorMonto();
					SolverGoloso solver = new SolverGoloso(empresa, comparador);
					solucion = solver.resolver();
					
					for(Oferta oferta : solucion.getOfertas()) {
						ofertasSeleccionadas.add(oferta);
					}
					ArchivosJson.guardarComoJSON("ofertasSeleccionadas", ofertasSeleccionadas);
					//BLOQUEA EL BOTON AGREGAR
					botonAgregar.setEnabled(false);
					diaTermino = true;
				}
				else {
					JOptionPane.showMessageDialog(frame, "El dia ya termino, espere hasta mañana para seleccionar nuevas ofertas",
													"Aviso", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		botonAgregar = new JButton("Agregar Oferta");
		botonAgregar.setBounds(55, 368, 121, 23);
		frame.getContentPane().add(botonAgregar);
		botonAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!diaTermino) {
					if(verificarDatos()) {
						String oferente = campoOferente.getText();
						int horaDesde = campoHoraDesde.getSelectedIndex();
						int horaHasta = campoHoraHasta.getSelectedIndex();
						
						double monto = Double.parseDouble(campoMonto.getText());
						String tipoShow = (String) campoTipoShow.getSelectedItem();
						
						Oferta oferta = new Oferta(oferente, horaDesde, horaHasta, monto, tipoShow);
						
						if(!verificarOfertaRepetida(oferta)) {
							empresa.agregar(oferta);
							ofertasTotales.add(oferta);	
							
							ArchivosJson.guardarComoJSON("ofertasTotales", ofertasTotales);
							JOptionPane.showMessageDialog(frame, "Se agrego la oferta correctamente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
							//LIMPIA LOS CAMPOS
							campoOferente.setText("");
							campoMonto.setText("");
							campoHoraDesde.setSelectedIndex(-1);
							campoHoraHasta.setSelectedIndex(-1);
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "El dia ya termino, espere hasta mañana para agregar mas ofertas",
													"Aviso", JOptionPane.INFORMATION_MESSAGE);
				}
			}

			private boolean verificarDatos() {
				if(campoOferente.getText().isEmpty()  || campoOferente.getText().charAt(0) == ' ') {
					JOptionPane.showMessageDialog(frame, "Nombre del oferente invalido", "Aviso", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
				if(!(campoHoraDesde.getSelectedIndex() < campoHoraHasta.getSelectedIndex())) {
					JOptionPane.showMessageDialog(frame, "Horario invalido", "Aviso", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
				if(campoMonto.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Monto Invalido", "Aviso", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
				return true;
			}
			private boolean verificarOfertaRepetida(Oferta oferta) {
				
				for(Oferta of : empresa.getOfertas()) {
					if(oferta.equals(of)) {
						JOptionPane.showMessageDialog(frame, "La oferta ingresada ya existe", "Aviso", JOptionPane.INFORMATION_MESSAGE);
						return true;
					}
				}
				return false;
			}
		});
		
		JButton botonCalendario = new JButton("Calendario");
		botonCalendario.setBounds(664, 516, 100, 23);
		frame.getContentPane().add(botonCalendario);
		botonCalendario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					panelOfertasDia.setVisible(true);
					panelCalendario.setVisible(true);
					panelOfertasTotales.setVisible(false);
			}
		});
		
		JButton botonOfertas = new JButton("Ofertas");
		botonOfertas.setBounds(515, 516, 89, 23);
		frame.getContentPane().add(botonOfertas);
		botonOfertas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					panelOfertasDia.setVisible(false);
					panelCalendario.setVisible(false);
					panelOfertasTotales.setVisible(true);
					model.setRowCount(0);
					llenarTablaOfertasTotales();
			}
		});
	}

	private void agregarFondo() {
		JLabel fondoIzquierda = new JLabel("");
		fondoIzquierda.setIcon(new ImageIcon(Menu.class.getResource("/imagen/fondoTP.png")));
		fondoIzquierda.setBackground(new Color(0, 0, 0));
		fondoIzquierda.setBounds(0, 0, 241, 611);
		frame.getContentPane().add(fondoIzquierda);
		
		JLabel fondoAbajo = new JLabel("");
		fondoAbajo.setIcon(new ImageIcon(Menu.class.getResource("/imagen/fondoTP.png")));
		fondoAbajo.setBounds(151, 503, 833, 58);
		frame.getContentPane().add(fondoAbajo);
	}

	private void crearParteCalendario() {
		panelCalendario = new JPanel();
		panelCalendario.setBackground(new Color(128, 128, 128));
		panelCalendario.setBounds(240, 0, 744, 163);
		panelCalendario.setVisible(false);
		frame.getContentPane().add(panelCalendario);
		panelCalendario.setLayout(null);
		
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
		
		JLabel logotipo = new JLabel("");
		logotipo.setIcon(new ImageIcon(Menu.class.getResource("/imagen/Logotipo.png")));
		logotipo.setBounds(55, 0, 184, 173);
		panelCalendario.add(logotipo);
		
		JLabel textoSeleccioneDia = new JLabel("Seleccione un dia para ver sus ofertas");
		textoSeleccioneDia.setBounds(484, 74, 260, 14);
		panelCalendario.add(textoSeleccioneDia);
		textoSeleccioneDia.setForeground(new Color(255, 255, 255));
		textoSeleccioneDia.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private void crearTituloInformativo() {
		JLabel textoInformacion = new JLabel("Informacion de la oferta");
		textoInformacion.setForeground(new Color(255, 255, 255));
		textoInformacion.setHorizontalAlignment(SwingConstants.CENTER);
		textoInformacion.setBounds(10, 33, 220, 14);
		frame.getContentPane().add(textoInformacion);
		
		JLabel textoPresionarCalendario = new JLabel("Presione para ver ofertas por dia");
		textoPresionarCalendario.setForeground(new Color(255, 255, 255));
		textoPresionarCalendario.setHorizontalAlignment(SwingConstants.CENTER);
		textoPresionarCalendario.setBounds(774, 520, 210, 19);
		frame.getContentPane().add(textoPresionarCalendario);
		
		JLabel textoPresionarOfertas = new JLabel("Presione para ver todas las ofertas");
		textoPresionarOfertas.setHorizontalAlignment(SwingConstants.CENTER);
		textoPresionarOfertas.setBackground(new Color(255, 255, 255));
		textoPresionarOfertas.setForeground(new Color(255, 255, 255));
		textoPresionarOfertas.setBounds(270, 512, 201, 30);
		frame.getContentPane().add(textoPresionarOfertas);
	}

	private void crearTextoInformativo() {
		JLabel textoShow = new JLabel("Tipo de Show");
		textoShow.setBackground(new Color(255, 255, 255));
		textoShow.setHorizontalAlignment(SwingConstants.CENTER);
		textoShow.setForeground(Color.WHITE);
		textoShow.setBounds(10, 291, 89, 14);
		frame.getContentPane().add(textoShow);
		
		JLabel textoMonto = new JLabel("Monto");
		textoMonto.setHorizontalAlignment(SwingConstants.CENTER);
		textoMonto.setForeground(Color.WHITE);
		textoMonto.setBounds(10, 247, 89, 14);
		frame.getContentPane().add(textoMonto);
		
		JLabel textoHoraHasta = new JLabel("Hora Hasta");
		textoHoraHasta.setHorizontalAlignment(SwingConstants.CENTER);
		textoHoraHasta.setForeground(Color.WHITE);
		textoHoraHasta.setBounds(10, 196, 89, 14);
		frame.getContentPane().add(textoHoraHasta);
		
		JLabel textoHoraDesde = new JLabel("Hora Desde");
		textoHoraDesde.setHorizontalAlignment(SwingConstants.CENTER);
		textoHoraDesde.setForeground(Color.WHITE);
		textoHoraDesde.setBounds(10, 149, 89, 14);
		frame.getContentPane().add(textoHoraDesde);
		
		JLabel textoOferente = new JLabel("Oferente");
		textoOferente.setForeground(new Color(255, 255, 255));
		textoOferente.setHorizontalAlignment(SwingConstants.CENTER);
		textoOferente.setBounds(10, 100, 89, 14);
		frame.getContentPane().add(textoOferente);
	}
	
	@SuppressWarnings("deprecation")
	private void obtenerOfertasTotalesJson() {
		Gson gson = new Gson();
        try {
            FileReader reader = new FileReader("src/jsons/ofertasTotales");

            JsonParser parser = new JsonParser();
            
            JsonElement jsonElement = parser.parse(reader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
         
            for (JsonElement element : jsonArray) {
               
                Oferta oferta = gson.fromJson(element, Oferta.class);
                ofertasTotales.add(oferta);
            	}
            
          
            reader.close();
         
        }catch (Exception e) {
            e.printStackTrace();
        }
        
	}
	
	private Set<Oferta> obtenerOfertaDiaSeleccionado(String fecha){
		Set<Oferta> ret = new HashSet<Oferta>();
		for (Oferta of : ofertasSeleccionadas) {
			if (of.getFechaManiana().equals(fecha)) {
				ret.add(of);
			}
		}
		return ret;
	}
	
	@SuppressWarnings("deprecation")
	private void obtenerOfertasSeleccionadasJson() {
		Gson gson = new Gson();
        try {
            FileReader reader = new FileReader("src/jsons/ofertasSeleccionadas");
         
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(reader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            for (JsonElement element : jsonArray) {
               
                Oferta oferta = gson.fromJson(element, Oferta.class);
                ofertasSeleccionadas.add(oferta);
            }
          
            reader.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private void crearFrame() {
		frame = new JFrame();
		frame.setBounds(200, 100, 1000, 600);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Mejor Postor");
		
	}
}
