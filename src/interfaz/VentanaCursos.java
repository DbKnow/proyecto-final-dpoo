package interfaz;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
import logica.Docente;
import logica.InscripcionCurso;
import logica.Vicedecanato;

/**
 * Ventana de los cursos de posgrado.
 * Arriba se gestionan los cursos y abajo las inscripciones del curso seleccionado.
 */
public class VentanaCursos extends JFrame {

	private JPanel contentPane;
	private JTextField txtTema;
	private JTextField txtObjetivos;
	private JTextField txtCreditos;
	private JTextField txtAnio;
	private JComboBox<String> cbResponsable;
	private JTable tablaCursos;
	private DefaultTableModel modeloCursos;
	private JComboBox<String> cbDocente;
	private JTextField txtNota;
	private JTable tablaInscripciones;
	private DefaultTableModel modeloInscripciones;

	private Vicedecanato vicedecanato = Vicedecanato.getInstancia();
	// Solo los doctores pueden ser responsables de un curso
	private ArrayList<Docente> doctores;
	// Todos los docentes pueden inscribirse
	private ArrayList<Docente> docentes;

	public VentanaCursos() {
		setTitle("Cursos de posgrado");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 680, 640);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// ---------- Parte de arriba: los cursos ----------
		JLabel lblTema = new JLabel("Tema:");
		lblTema.setBounds(10, 14, 80, 14);
		contentPane.add(lblTema);

		txtTema = new JTextField();
		txtTema.setBounds(90, 11, 220, 20);
		contentPane.add(txtTema);

		JLabel lblObjetivos = new JLabel("Objetivos:");
		lblObjetivos.setBounds(330, 14, 70, 14);
		contentPane.add(lblObjetivos);

		txtObjetivos = new JTextField();
		txtObjetivos.setBounds(400, 11, 254, 20);
		contentPane.add(txtObjetivos);

		JLabel lblCreditos = new JLabel("Cr\u00e9ditos:");
		lblCreditos.setBounds(10, 42, 80, 14);
		contentPane.add(lblCreditos);

		txtCreditos = new JTextField();
		txtCreditos.setBounds(90, 39, 60, 20);
		contentPane.add(txtCreditos);

		JLabel lblAnio = new JLabel("A\u00f1o:");
		lblAnio.setBounds(170, 42, 40, 14);
		contentPane.add(lblAnio);

		txtAnio = new JTextField();
		txtAnio.setBounds(210, 39, 60, 20);
		contentPane.add(txtAnio);

		JLabel lblResponsable = new JLabel("Responsable:");
		lblResponsable.setBounds(330, 42, 80, 14);
		contentPane.add(lblResponsable);

		cbResponsable = new JComboBox<String>();
		cbResponsable.setBounds(415, 39, 239, 20);
		contentPane.add(cbResponsable);

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbResponsable.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "No hay docentes con categor\u00eda Doctor para ser responsables.");
					return;
				}
				try {
					CursoPosgrado c = new CursoPosgrado(vicedecanato.generarId(), txtTema.getText(),
							txtObjetivos.getText(), Integer.parseInt(txtCreditos.getText().trim()),
							Integer.parseInt(txtAnio.getText().trim()),
							doctores.get(cbResponsable.getSelectedIndex()));
					vicedecanato.agregarCurso(c);
					cargarTablaCursos();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Los cr\u00e9ditos y el a\u00f1o deben ser n\u00fameros enteros.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAgregar.setBounds(10, 70, 100, 23);
		contentPane.add(btnAgregar);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tablaCursos.getSelectedRow();
				if (fila == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione un curso de la tabla.");
					return;
				}
				try {
					CursoPosgrado c = vicedecanato.getCursos().get(fila);
					c.setTema(txtTema.getText());
					c.setObjetivos(txtObjetivos.getText());
					c.setCreditos(Integer.parseInt(txtCreditos.getText().trim()));
					c.setAnio(Integer.parseInt(txtAnio.getText().trim()));
					c.setResponsable(doctores.get(cbResponsable.getSelectedIndex()));
					cargarTablaCursos();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Los cr\u00e9ditos y el a\u00f1o deben ser n\u00fameros enteros.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModificar.setBounds(116, 70, 100, 23);
		contentPane.add(btnModificar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tablaCursos.getSelectedRow();
				if (fila == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione un curso de la tabla.");
					return;
				}
				int respuesta = JOptionPane.showConfirmDialog(null, "\u00bfDesea eliminar el curso?",
						"Confirmar", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					vicedecanato.eliminarCurso(vicedecanato.getCursos().get(fila));
					cargarTablaCursos();
					modeloInscripciones.setRowCount(0);
				}
			}
		});
		btnEliminar.setBounds(222, 70, 100, 23);
		contentPane.add(btnEliminar);

		JScrollPane scrollCursos = new JScrollPane();
		scrollCursos.setBounds(10, 104, 644, 120);
		contentPane.add(scrollCursos);

		modeloCursos = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Tema", "A\u00f1o", "Cr\u00e9ditos", "Responsable" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablaCursos = new JTable(modeloCursos);
		// Al seleccionar un curso se cargan sus datos y sus inscripciones
		tablaCursos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int fila = tablaCursos.getSelectedRow();
				if (fila != -1) {
					CursoPosgrado c = vicedecanato.getCursos().get(fila);
					txtTema.setText(c.getTema());
					txtObjetivos.setText(c.getObjetivos());
					txtCreditos.setText(String.valueOf(c.getCreditos()));
					txtAnio.setText(String.valueOf(c.getAnio()));
					cbResponsable.setSelectedIndex(doctores.indexOf(c.getResponsable()));
					cargarTablaInscripciones();
				}
			}
		});
		scrollCursos.setViewportView(tablaCursos);

		// ---------- Parte de abajo: inscripciones del curso seleccionado ----------
		JLabel lblInscripciones = new JLabel("Inscripciones del curso seleccionado");
		lblInscripciones.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInscripciones.setBounds(10, 238, 300, 14);
		contentPane.add(lblInscripciones);

		JLabel lblDocente = new JLabel("Docente:");
		lblDocente.setBounds(10, 266, 80, 14);
		contentPane.add(lblDocente);

		cbDocente = new JComboBox<String>();
		cbDocente.setBounds(90, 263, 220, 20);
		contentPane.add(cbDocente);

		JLabel lblNota = new JLabel("Nota (0 a 5):");
		lblNota.setBounds(330, 266, 80, 14);
		contentPane.add(lblNota);

		txtNota = new JTextField();
		txtNota.setBounds(410, 263, 50, 20);
		contentPane.add(txtNota);

		JButton btnInscribir = new JButton("Inscribir");
		btnInscribir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CursoPosgrado c = cursoSeleccionado();
				if (c == null || cbDocente.getSelectedIndex() == -1) {
					return;
				}
				try {
					c.inscribir(docentes.get(cbDocente.getSelectedIndex()));
					cargarTablaInscripciones();
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnInscribir.setBounds(10, 294, 110, 23);
		contentPane.add(btnInscribir);

		JButton btnAsignarNota = new JButton("Asignar nota");
		btnAsignarNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CursoPosgrado c = cursoSeleccionado();
				if (c == null || cbDocente.getSelectedIndex() == -1) {
					return;
				}
				try {
					double nota = Double.parseDouble(txtNota.getText().trim());
					c.asignarNota(docentes.get(cbDocente.getSelectedIndex()), nota);
					cargarTablaInscripciones();
					txtNota.setText("");
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "La nota debe ser un n\u00famero.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAsignarNota.setBounds(126, 294, 110, 23);
		contentPane.add(btnAsignarNota);

		JButton btnDarBaja = new JButton("Dar de baja");
		btnDarBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Pendiente: falta el metodo en la logica
				JOptionPane.showMessageDialog(null, "Funcionalidad no implementada todav\u00eda.");
			}
		});
		btnDarBaja.setBounds(242, 294, 110, 23);
		contentPane.add(btnDarBaja);

		JScrollPane scrollInscripciones = new JScrollPane();
		scrollInscripciones.setBounds(10, 328, 644, 228);
		contentPane.add(scrollInscripciones);

		modeloInscripciones = new DefaultTableModel(new Object[][] {},
				new String[] { "Docente", "Nota", "Aprobado" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablaInscripciones = new JTable(modeloInscripciones);
		// Al hacer clic en una inscripcion se selecciona ese docente en el combo
		tablaInscripciones.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int fila = tablaInscripciones.getSelectedRow();
				CursoPosgrado c = cursoSeleccionado();
				if (fila != -1 && c != null) {
					InscripcionCurso i = c.getInscripciones().get(fila);
					cbDocente.setSelectedIndex(docentes.indexOf(i.getParticipante()));
					txtNota.setText(String.valueOf(i.getNota()));
				}
			}
		});
		scrollInscripciones.setViewportView(tablaInscripciones);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVolver.setBounds(554, 567, 100, 23);
		contentPane.add(btnVolver);

		// Se llenan los combos
		doctores = vicedecanato.getDoctores();
		for (Docente d : doctores) {
			cbResponsable.addItem(d.getNombre());
		}
		docentes = vicedecanato.getDocentes();
		for (Docente d : docentes) {
			cbDocente.addItem(d.getNombre());
		}
		cargarTablaCursos();
	}

	// Devuelve el curso marcado en la tabla, o avisa si no hay ninguno
	private CursoPosgrado cursoSeleccionado() {
		int fila = tablaCursos.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(null, "Seleccione un curso de la tabla.");
			return null;
		}
		return vicedecanato.getCursos().get(fila);
	}

	private void cargarTablaCursos() {
		modeloCursos.setRowCount(0);
		for (CursoPosgrado c : vicedecanato.getCursos()) {
			modeloCursos.addRow(new Object[] { c.getId(), c.getTema(), c.getAnio(), c.getCreditos(),
					c.getResponsable().getNombre() });
		}
	}

	private void cargarTablaInscripciones() {
		modeloInscripciones.setRowCount(0);
		int fila = tablaCursos.getSelectedRow();
		if (fila == -1) {
			return;
		}
		for (InscripcionCurso i : vicedecanato.getCursos().get(fila).getInscripciones()) {
			String aprobado = "No";
			if (i.estaAprobada()) {
				aprobado = "S\u00ed";
			}
			modeloInscripciones.addRow(new Object[] { i.getParticipante().getNombre(), i.getNota(), aprobado });
		}
	}
}
