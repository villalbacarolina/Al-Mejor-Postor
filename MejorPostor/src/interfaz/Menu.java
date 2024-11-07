package interfaz;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;

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
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;



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
	private String fechaa;

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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		obtenerOfertasTotalesJson();
		obtenerOfertasSeleccionadasJson();
		//antes de todo, el programa va a leer los dos JSON parar guardar las ofertas en
		//ofertasTotales y ofertasSeleccionadas para luego mostrarlo por pantalla.
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

	private void crearPanelOfertasTotales() {
		panelOfertasTotales = new JPanel();
		panelOfertasTotales.setBounds(241,0,744,561);
		panelOfertasTotales.setVisible(false);
		frame.getContentPane().add(panelOfertasTotales);
		
		//se creara un JTable que mostrara las ofertas de ofertasTotales.
	}

	private void crearPanelGrafico() {
		panelOfertasDia = new JPanel();
		panelOfertasDia.setBounds(240, 162, 744, 342);
		panelOfertasDia.setVisible(false);
		frame.getContentPane().add(panelOfertasDia);
		
		crearLineaDeTiempo(panelOfertasDia);
	}

	private void crearLineaDeTiempo(JPanel panelOfertasDia) {
		
		//un metodo que obtendra los datos de las ofertas de la variable OfertasSeleccionadas
		//para luego mostrarlo por la linea de tiempo.
		
		JFreeChart ofertasDia = ChartFactory.createTimeSeriesChart("Ofertas del dia", "Horas", "Monto", null);
		
		ChartPanel cPanel = new ChartPanel(ofertasDia);
		cPanel.setMinimumDrawHeight(100);
		cPanel.setMaximumDrawHeight(800);
		cPanel.setPreferredSize(new Dimension(744, 332));
		panelOfertasDia.add(cPanel);
		cPanel.setLayout(null);
	}

	private void crearCampoTexto() {
		campoMonto = new JTextField();
		campoMonto.setColumns(10);
		campoMonto.setBounds(118, 244, 112, 20);
		frame.getContentPane().add(campoMonto);
		
		campoOferente = new JTextField();
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
				
				ComparadorMonto comparador = new ComparadorMonto();
				SolverGoloso solver = new SolverGoloso(empresa, comparador);
				solucion = solver.resolver();
				
				for(Oferta oferta : solucion.getOfertas()) {
					ofertasSeleccionadas.add(oferta);
				}
				ArchivosJson.guardarComoJSON("ofertasSeleccionadas", ofertasSeleccionadas);
			}
		});
		
		
		JButton botonAgregar = new JButton("Agregar Oferta");
		botonAgregar.setBounds(55, 368, 121, 23);
		frame.getContentPane().add(botonAgregar);
		botonAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String oferente = campoOferente.getText();
				int horaDesde = campoHoraDesde.getSelectedIndex();
				int horaHasta = campoHoraHasta.getSelectedIndex();
				
				//antes de hacer el parse, chequear que lo ingresado sea un double.
				double monto = Double.parseDouble(campoMonto.getText());
				String tipoShow = (String) campoTipoShow.getSelectedItem();
				
				Oferta oferta = new Oferta(oferente, horaDesde, horaHasta, monto, tipoShow);
				
				empresa.agregar(oferta);
				ofertasTotales.add(oferta);			//ofertasTotales.add(oferta);
				//aca llamo al escribirJSONTotales para guardar la info de esta oferta.
				ArchivosJson.guardarComoJSON("ofertasTotales", ofertasTotales);
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
					fechaa = format.format(calendario.getCalendar().getTime());
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
	
	private void obtenerOfertasTotalesJson() {
		Gson gson = new Gson();
        try {
        	if (ArchivosJson.getCarpeta()!=null) {
        	//Ejemplo de ruta de archivo valida: "src/espias.json"
            FileReader reader = new FileReader("/jsons/ofertasTotales.json");

            // Utiliza JsonParser para analizar el JSON
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(reader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            // Itera sobre cada elemento del array JSON
            for (JsonElement element : jsonArray) {
                // Convierte cada elemento en un objeto Coordenada
                Oferta oferta = gson.fromJson(element, Oferta.class);
                ofertasTotales.add(oferta);
            	}
            
            // Cierra el FileReader
            reader.close();
        } 
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
	
	private void obtenerOfertasSeleccionadasJson() {
		Gson gson = new Gson();
        try {
        	if (ArchivosJson.getCarpeta() !=null) {
        	//Ejemplo de ruta de archivo valida: "src/espias.json"
            FileReader reader = new FileReader("/jsons/ofertasSeleccionadas.json");

            // Utiliza JsonParser para analizar el JSON
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(reader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            // Itera sobre cada elemento del array JSON
            for (JsonElement element : jsonArray) {
                // Convierte cada elemento en un objeto Coordenada
                Oferta oferta = gson.fromJson(element, Oferta.class);
                ofertasSeleccionadas.add(oferta);
            }
            // Cierra el FileReader
            reader.close();
        } 
        }catch (Exception e) {
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
		
	}
}