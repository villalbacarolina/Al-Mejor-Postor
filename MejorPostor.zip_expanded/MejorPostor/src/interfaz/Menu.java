package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.toedter.calendar.JCalendar;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;



public class Menu {

	private JFrame frame;

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
		crearFrame();
		
		JButton btnNewButton_1 = new JButton("Calendario");
		btnNewButton_1.setBounds(442, 565, 100, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Ofertas");
		btnNewButton.setBounds(234, 565, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/fondoTP.png")));
		lblNewLabel.setBackground(new Color(0, 0, 0));
		lblNewLabel.setBounds(0, 0, 162, 611);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Menu.class.getResource("/imagenes/fondoTP.png")));
		lblNewLabel_1.setBounds(161, 536, 473, 75);
		frame.getContentPane().add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(161, 0, 473, 163);
		frame.getContentPane().add(panel);
		
		JCalendar cal = new JCalendar();
		panel.add(cal);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(161, 162, 473, 375);
		frame.getContentPane().add(panel_1);
		
		
		JFreeChart linea = ChartFactory.createTimeSeriesChart("Linea de tiempo", "Horas", "Monto", null);
		
		ChartPanel cPanel = new ChartPanel(linea);
		cPanel.setPreferredSize(new Dimension(473,375));
		panel_1.add(cPanel);
		
	}
	
	
	private void crearFrame() {
		frame = new JFrame();
		frame.setBounds(425, 100, 650, 650);   //probar en la notebook para modificar el tamanio de la pantalla adecuadamente.
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
	}
}
