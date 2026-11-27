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

import logica.Departamento;
import logica.Vicedecanato;

/**
 * Ventana para agregar, modificar y eliminar departamentos.
 */
public class VentanaDepartamentos extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTable tabla;
	private DefaultTableModel modelo;

	private Vicedecanato vicedecanato = Vicedecanato.getInstancia();

	public VentanaDepartamentos() {
		setTitle("Departamentos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 560, 420);
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
					Departamento d = new Departamento(vicedecanato.generarId(), txtNombre.getText());
					vicedecanato.agregarDepartamento(d);
					cargarTabla();
					txtNombre.setText("");
				} catch (IllegalArgumentException ex) {
					// Los set de la logica lanzan esta excepcion cuando un dato no es valido
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
					JOptionPane.showMessageDialog(null, "Seleccione un departamento de la tabla.");
					return;
				}
				try {
					// La fila de la tabla coincide con la posicion en la lista
					Departamento d = vicedecanato.getDepartamentos().get(fila);
					d.setNombre(txtNombre.getText());
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
					JOptionPane.showMessageDialog(null, "Seleccione un departamento de la tabla.");
					return;
				}
				int respuesta = JOptionPane.showConfirmDialog(null, "\u00bfDesea eliminar el departamento?",
						"Confirmar", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					try {
						vicedecanato.eliminarDepartamento(vicedecanato.getDepartamentos().get(fila));
						cargarTabla();
						txtNombre.setText("");
					} catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnEliminar.setBounds(222, 42, 100, 23);
		contentPane.add(btnEliminar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 76, 524, 260);
		contentPane.add(scrollPane);

		// Modelo de la tabla: se sobrescribe isCellEditable para que no se pueda escribir en las celdas
		modelo = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Nombre", "Cantidad de docentes" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabla = new JTable(modelo);
		// Al hacer clic en una fila se copian sus datos al campo de texto
		tabla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int fila = tabla.getSelectedRow();
				if (fila != -1) {
					txtNombre.setText(vicedecanato.getDepartamentos().get(fila).getNombre());
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
		btnVolver.setBounds(434, 347, 100, 23);
		contentPane.add(btnVolver);

		cargarTabla();
	}

	// Vuelve a llenar la tabla con los departamentos de la lista
	private void cargarTabla() {
		modelo.setRowCount(0);
		for (Departamento d : vicedecanato.getDepartamentos()) {
			modelo.addRow(new Object[] { d.getId(), d.getNombre(), d.getDocentes().size() });
		}
	}
}
