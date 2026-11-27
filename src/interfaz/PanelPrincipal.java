package interfaz;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Menu principal de la aplicacion.
 * Cada boton abre la ventana de una parte del sistema.
 */
public class PanelPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Inicia la aplicacion.
	 */
	public static void main(String[] args) {
		// Se cargan los datos de ejemplo antes de mostrar el menu
		DatosPrueba.cargar();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelPrincipal frame = new PanelPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea la ventana.
	 */
	public PanelPrincipal() {
		setTitle("Men\u00fa principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 470);
		setResizable(false);
		setLocationRelativeTo(null); // centrada en la pantalla
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null); // posiciones fijas con setBounds
		setContentPane(contentPane);

		JLabel lblTitulo = new JLabel("Control de la Actividad Investigativa");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(10, 11, 324, 25);
		contentPane.add(lblTitulo);

		JLabel lblSubtitulo = new JLabel("Vicedecanato de Investigaci\u00f3n y Posgrado");
		lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubtitulo.setBounds(10, 36, 324, 20);
		contentPane.add(lblSubtitulo);

		JButton btnDepartamentos = new JButton("Departamentos");
		btnDepartamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaDepartamentos().setVisible(true);
			}
		});
		btnDepartamentos.setBounds(62, 70, 220, 25);
		contentPane.add(btnDepartamentos);

		JButton btnDocentes = new JButton("Docentes");
		btnDocentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaDocentes().setVisible(true);
			}
		});
		btnDocentes.setBounds(62, 103, 220, 25);
		contentPane.add(btnDocentes);

		JButton btnEstudiantes = new JButton("Estudiantes");
		btnEstudiantes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaEstudiantes().setVisible(true);
			}
		});
		btnEstudiantes.setBounds(62, 136, 220, 25);
		contentPane.add(btnEstudiantes);

		JButton btnLineas = new JButton("L\u00edneas de investigaci\u00f3n");
		btnLineas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaLineas().setVisible(true);
			}
		});
		btnLineas.setBounds(62, 169, 220, 25);
		contentPane.add(btnLineas);

		JButton btnResultados = new JButton("Resultados investigativos");
		btnResultados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaResultados().setVisible(true);
			}
		});
		btnResultados.setBounds(62, 202, 220, 25);
		contentPane.add(btnResultados);

		JButton btnCursos = new JButton("Cursos de posgrado");
		btnCursos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaCursos().setVisible(true);
			}
		});
		btnCursos.setBounds(62, 235, 220, 25);
		contentPane.add(btnCursos);

		JButton btnPlanes = new JButton("Planes de maestr\u00eda");
		btnPlanes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaPlanes().setVisible(true);
			}
		});
		btnPlanes.setBounds(62, 268, 220, 25);
		contentPane.add(btnPlanes);

		JButton btnReportes = new JButton("Reportes");
		btnReportes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaReportes().setVisible(true);
			}
		});
		btnReportes.setBounds(62, 301, 220, 25);
		contentPane.add(btnReportes);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir.setBounds(62, 360, 220, 25);
		contentPane.add(btnSalir);
	}
}
