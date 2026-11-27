package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.CursoPosgrado;
import logica.LineaInvestigacion;
import logica.PlanMaestria;
import logica.Vicedecanato;

/**
 * Ventana de los reportes R1 a R6.
 * Segun el reporte elegido cambia el dato que se pide y las columnas de la tabla.
 *
 * NOTA: los reportes todavia no estan implementados en la logica,
 * por eso el boton Mostrar solo muestra un aviso.
 */
public class VentanaReportes extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> cbReporte;
	private JLabel lblParametro;
	private JTextField txtParametro;
	private JComboBox<String> cbParametro;
	private JLabel lblHasta;
	private JTextField txtHasta;
	private DefaultTableModel modelo;

	private Vicedecanato vicedecanato = Vicedecanato.getInstancia();

	// Columnas de la tabla para cada reporte (mismo orden que el combo)
	private String[][] columnas = {
			{ "Departamento", "Puntaje", "Cursos impartidos", "Cursos recibidos", "Cr\u00e9ditos obtenidos" },
			{ "Docente", "Cr\u00e9ditos obtenidos", "Cr\u00e9ditos del plan", "Faltantes", "Estado" },
			{ "Investigador", "Art\u00edculos", "Cap\u00edtulos", "Ponencias", "Puntaje total" },
			{ "Promedio de notas", "Aprobados", "Cr\u00e9ditos otorgados" },
			{ "Integrante", "Tema", "Resultados", "Puntaje" },
			{ "A\u00f1o", "WoS", "Grupo 1", "Grupo 2", "Grupo 3", "Grupo 4", "% del total" } };

	public VentanaReportes() {
		setTitle("Reportes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 460);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblReporte = new JLabel("Reporte:");
		lblReporte.setBounds(10, 14, 70, 14);
		contentPane.add(lblReporte);

		cbReporte = new JComboBox<String>(new String[] {
				"R1 - Actividad por departamento en un a\u00f1o",
				"R2 - Docentes habilitados para defender",
				"R3 - Ranking de investigadores por puntaje",
				"R4 - Rendimiento por curso de posgrado",
				"R5 - Resumen de una l\u00ednea de investigaci\u00f3n",
				"R6 - Producci\u00f3n por grupo de impacto y a\u00f1o" });
		cbReporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarReporte();
			}
		});
		cbReporte.setBounds(80, 11, 320, 20);
		contentPane.add(cbReporte);

		// Dato que pide el reporte: segun el caso se usa el campo de texto o el combo
		lblParametro = new JLabel("A\u00f1o:");
		lblParametro.setBounds(10, 45, 70, 14);
		contentPane.add(lblParametro);

		txtParametro = new JTextField();
		txtParametro.setBounds(80, 42, 80, 20);
		contentPane.add(txtParametro);

		cbParametro = new JComboBox<String>();
		cbParametro.setBounds(80, 42, 320, 20);
		contentPane.add(cbParametro);

		// Solo para R6, que pide un rango de anos
		lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(180, 45, 50, 14);
		contentPane.add(lblHasta);

		txtHasta = new JTextField();
		txtHasta.setBounds(230, 42, 80, 20);
		contentPane.add(txtHasta);

		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Pendiente: cuando Vicedecanato tenga los metodos de los reportes,
				// aqui se llama al metodo y se llena la tabla con el resultado
				JOptionPane.showMessageDialog(null, "Funcionalidad no implementada todav\u00eda.");
			}
		});
		btnMostrar.setBounds(410, 41, 100, 23);
		contentPane.add(btnMostrar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 76, 604, 300);
		contentPane.add(scrollPane);

		modelo = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane.setViewportView(new JTable(modelo));

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVolver.setBounds(514, 387, 100, 23);
		contentPane.add(btnVolver);

		cambiarReporte();
	}

	// Ajusta los campos y las columnas segun el reporte elegido
	private void cambiarReporte() {
		int r = cbReporte.getSelectedIndex();

		// Primero se ocultan todos y luego se muestran los que hacen falta
		lblParametro.setVisible(false);
		txtParametro.setVisible(false);
		cbParametro.setVisible(false);
		lblHasta.setVisible(false);
		txtHasta.setVisible(false);
		cbParametro.removeAllItems();
		txtParametro.setText("");
		txtHasta.setText("");

		if (r == 0) { // R1: un ano
			lblParametro.setText("A\u00f1o:");
			lblParametro.setVisible(true);
			txtParametro.setVisible(true);
		} else if (r == 1) { // R2: un plan de maestria
			lblParametro.setText("Plan:");
			lblParametro.setVisible(true);
			for (PlanMaestria p : vicedecanato.getPlanes()) {
				cbParametro.addItem("Plan " + p.getId());
			}
			cbParametro.setVisible(true);
		} else if (r == 3) { // R4: un curso
			lblParametro.setText("Curso:");
			lblParametro.setVisible(true);
			for (CursoPosgrado c : vicedecanato.getCursos()) {
				cbParametro.addItem(c.getTema());
			}
			cbParametro.setVisible(true);
		} else if (r == 4) { // R5: una linea de investigacion
			lblParametro.setText("L\u00ednea:");
			lblParametro.setVisible(true);
			for (LineaInvestigacion l : vicedecanato.getLineas()) {
				cbParametro.addItem(l.getNombre());
			}
			cbParametro.setVisible(true);
		} else if (r == 5) { // R6: rango de anos
			lblParametro.setText("Desde:");
			lblParametro.setVisible(true);
			txtParametro.setVisible(true);
			lblHasta.setVisible(true);
			txtHasta.setVisible(true);
		}
		// R3 (r == 2) no pide ningun dato

		modelo.setRowCount(0);
		modelo.setColumnIdentifiers(columnas[r]);
	}
}
