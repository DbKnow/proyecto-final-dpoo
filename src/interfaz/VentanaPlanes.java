package interfaz;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.CursoPosgrado;
import logica.Docente;
import logica.PlanMaestria;
import logica.Vicedecanato;

/**
 * Ventana de los planes de maestria.
 * Se elige un plan en el combo y se le agregan cursos y docentes matriculados.
 */
public class VentanaPlanes extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> cbPlan;
	private JComboBox<String> cbCurso;
	private JComboBox<String> cbDocente;
	private JLabel lblTotalCreditos;
	private DefaultTableModel modeloCursos;
	private DefaultTableModel modeloMatriculados;

	private Vicedecanato vicedecanato = Vicedecanato.getInstancia();
	private ArrayList<Docente> docentes;

	public VentanaPlanes() {
		setTitle("Planes de maestr\u00eda");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 660, 520);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JButton btnCrear = new JButton("Crear plan");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// PlanMaestria solo tiene id, asi que se crea directamente
				PlanMaestria p = new PlanMaestria(vicedecanato.generarId());
				vicedecanato.agregarPlan(p);
				cargarComboPlanes();
				cbPlan.setSelectedIndex(cbPlan.getItemCount() - 1); // se selecciona el plan nuevo
			}
		});
		btnCrear.setBounds(10, 11, 120, 23);
		contentPane.add(btnCrear);

		JButton btnEliminar = new JButton("Eliminar plan");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlanMaestria p = planSeleccionado();
				if (p == null) {
					return;
				}
				int respuesta = JOptionPane.showConfirmDialog(null, "\u00bfDesea eliminar el plan?",
						"Confirmar", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					vicedecanato.eliminarPlan(p);
					cargarComboPlanes();
				}
			}
		});
		btnEliminar.setBounds(136, 11, 120, 23);
		contentPane.add(btnEliminar);

		JLabel lblPlan = new JLabel("Plan:");
		lblPlan.setBounds(10, 48, 60, 14);
		contentPane.add(lblPlan);

		cbPlan = new JComboBox<String>();
		// Al cambiar de plan se actualizan las dos tablas
		cbPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarTablas();
			}
		});
		cbPlan.setBounds(70, 45, 220, 20);
		contentPane.add(cbPlan);

		JLabel lblCurso = new JLabel("Curso:");
		lblCurso.setBounds(10, 79, 60, 14);
		contentPane.add(lblCurso);

		cbCurso = new JComboBox<String>();
		cbCurso.setBounds(70, 76, 220, 20);
		contentPane.add(cbCurso);

		JButton btnAgregarCurso = new JButton("Agregar curso");
		btnAgregarCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlanMaestria p = planSeleccionado();
				if (p == null || cbCurso.getSelectedIndex() == -1) {
					return;
				}
				try {
					p.agregarCurso(vicedecanato.getCursos().get(cbCurso.getSelectedIndex()));
					cargarTablas();
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAgregarCurso.setBounds(300, 75, 130, 23);
		contentPane.add(btnAgregarCurso);

		JLabel lblDocente = new JLabel("Docente:");
		lblDocente.setBounds(10, 110, 60, 14);
		contentPane.add(lblDocente);

		cbDocente = new JComboBox<String>();
		cbDocente.setBounds(70, 107, 220, 20);
		contentPane.add(cbDocente);

		JButton btnMatricular = new JButton("Matricular");
		btnMatricular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlanMaestria p = planSeleccionado();
				if (p == null || cbDocente.getSelectedIndex() == -1) {
					return;
				}
				try {
					p.matricular(docentes.get(cbDocente.getSelectedIndex()));
					cargarTablas();
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnMatricular.setBounds(300, 106, 130, 23);
		contentPane.add(btnMatricular);

		// ---------- Tabla de cursos del plan ----------
		JLabel lblCursosPlan = new JLabel("Cursos del plan");
		lblCursosPlan.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCursosPlan.setBounds(10, 145, 150, 14);
		contentPane.add(lblCursosPlan);

		lblTotalCreditos = new JLabel("Total de cr\u00e9ditos: 0");
		lblTotalCreditos.setBounds(170, 145, 150, 14);
		contentPane.add(lblTotalCreditos);

		JScrollPane scrollCursos = new JScrollPane();
		scrollCursos.setBounds(10, 165, 310, 270);
		contentPane.add(scrollCursos);

		modeloCursos = new DefaultTableModel(new Object[][] {}, new String[] { "Curso", "Cr\u00e9ditos" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollCursos.setViewportView(new JTable(modeloCursos));

		// ---------- Tabla de docentes matriculados ----------
		JLabel lblMatriculados = new JLabel("Docentes matriculados");
		lblMatriculados.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMatriculados.setBounds(330, 145, 200, 14);
		contentPane.add(lblMatriculados);

		JScrollPane scrollMatriculados = new JScrollPane();
		scrollMatriculados.setBounds(330, 165, 304, 270);
		contentPane.add(scrollMatriculados);

		modeloMatriculados = new DefaultTableModel(new Object[][] {},
				new String[] { "Docente", "Puede defender" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollMatriculados.setViewportView(new JTable(modeloMatriculados));

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVolver.setBounds(534, 447, 100, 23);
		contentPane.add(btnVolver);

		// Se llenan los combos
		for (CursoPosgrado c : vicedecanato.getCursos()) {
			cbCurso.addItem(c.getTema());
		}
		docentes = vicedecanato.getDocentes();
		for (Docente d : docentes) {
			cbDocente.addItem(d.getNombre());
		}
		cargarComboPlanes();
	}

	// Como el plan no tiene nombre, en el combo se muestra "Plan " + id
	private void cargarComboPlanes() {
		cbPlan.removeAllItems();
		for (PlanMaestria p : vicedecanato.getPlanes()) {
			cbPlan.addItem("Plan " + p.getId());
		}
		cargarTablas();
	}

	private PlanMaestria planSeleccionado() {
		if (cbPlan.getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(null, "Primero debe crear un plan.");
			return null;
		}
		return vicedecanato.getPlanes().get(cbPlan.getSelectedIndex());
	}

	// Llena las dos tablas con los datos del plan elegido
	private void cargarTablas() {
		modeloCursos.setRowCount(0);
		modeloMatriculados.setRowCount(0);
		lblTotalCreditos.setText("Total de cr\u00e9ditos: 0");
		if (cbPlan.getSelectedIndex() == -1) {
			return;
		}
		PlanMaestria p = vicedecanato.getPlanes().get(cbPlan.getSelectedIndex());
		for (CursoPosgrado c : p.getCursos()) {
			modeloCursos.addRow(new Object[] { c.getTema(), c.getCreditos() });
		}
		for (Docente d : p.getMatriculados()) {
			String puede = "No";
			if (p.puedeDefender(d)) {
				puede = "S\u00ed";
			}
			modeloMatriculados.addRow(new Object[] { d.getNombre(), puede });
		}
		lblTotalCreditos.setText("Total de cr\u00e9ditos: " + p.calcularCreditosTotales());
	}
}
