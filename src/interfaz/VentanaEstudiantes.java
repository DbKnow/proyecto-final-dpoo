package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.Estudiante;
import logica.Vicedecanato;

/**
 * Ventana para agregar, modificar y eliminar estudiantes.
 */
public class VentanaEstudiantes extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTable tabla;
	private DefaultTableModel modelo;

	private Vicedecanato vicedecanato = Vicedecanato.getInstancia();

	public VentanaEstudiantes() {
		setTitle("Estudiantes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 480, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 14, 70, 14);
		contentPane.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(80, 11, 220, 20);
		contentPane.add(txtNombre);

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Estudiante est = new Estudiante(txtNombre.getText());
					// El constructor de Estudiante no recibe el id, se asigna con el set
					est.setId(vicedecanato.generarId());
					vicedecanato.agregarEstudiante(est);
					cargarTabla();
					txtNombre.setText("");
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAgregar.setBounds(10, 42, 100, 23);
		contentPane.add(btnAgregar);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tabla.getSelectedRow();
				if (fila == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione un estudiante de la tabla.");
					return;
				}
				try {
					vicedecanato.getEstudiantes().get(fila).setNombre(txtNombre.getText());
					cargarTabla();
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModificar.setBounds(116, 42, 100, 23);
		contentPane.add(btnModificar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tabla.getSelectedRow();
				if (fila == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione un estudiante de la tabla.");
					return;
				}
				int respuesta = JOptionPane.showConfirmDialog(null, "\u00bfDesea eliminar el estudiante?",
						"Confirmar", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					vicedecanato.eliminarEstudiante(vicedecanato.getEstudiantes().get(fila));
					cargarTabla();
					txtNombre.setText("");
				}
			}
		});
		btnEliminar.setBounds(222, 42, 100, 23);
		contentPane.add(btnEliminar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 76, 444, 240);
		contentPane.add(scrollPane);

		modelo = new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nombre" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabla = new JTable(modelo);
		tabla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int fila = tabla.getSelectedRow();
				if (fila != -1) {
					txtNombre.setText(vicedecanato.getEstudiantes().get(fila).getNombre());
				}
			}
		});
		scrollPane.setViewportView(tabla);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVolver.setBounds(354, 327, 100, 23);
		contentPane.add(btnVolver);

		cargarTabla();
	}

	private void cargarTabla() {
		modelo.setRowCount(0);
		for (Estudiante est : vicedecanato.getEstudiantes()) {
			modelo.addRow(new Object[] { est.getId(), est.getNombre() });
		}
	}
}
