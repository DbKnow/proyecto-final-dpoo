package interfaz;

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

import logica.CategoriaCientifica;
import logica.CategoriaDocente;
import logica.Departamento;
import logica.Docente;
import logica.Vicedecanato;

/**
 * Ventana para agregar, modificar y eliminar docentes.
 */
public class VentanaDocentes extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JComboBox<String> cbDepartamento;
	private JComboBox<CategoriaDocente> cbCategoriaDocente;
	private JComboBox<String> cbCategoriaCientifica;
	private JTable tabla;
	private DefaultTableModel modelo;

	private Vicedecanato vicedecanato = Vicedecanato.getInstancia();
	// Docentes que se muestran en la tabla, en el mismo orden que las filas
	private ArrayList<Docente> docentes = new ArrayList<Docente>();

	public VentanaDocentes() {
		setTitle("Docentes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 14, 130, 14);
		contentPane.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(140, 11, 220, 20);
		contentPane.add(txtNombre);

		JLabel lblDepartamento = new JLabel("Departamento:");
		lblDepartamento.setBounds(10, 42, 130, 14);
		contentPane.add(lblDepartamento);

		cbDepartamento = new JComboBox<String>();
		cbDepartamento.setBounds(140, 39, 220, 20);
		contentPane.add(cbDepartamento);

		JLabel lblCategoriaDocente = new JLabel("Categor\u00eda docente:");
		lblCategoriaDocente.setBounds(10, 70, 130, 14);
		contentPane.add(lblCategoriaDocente);

		// El combo se llena directamente con los valores del enum
		cbCategoriaDocente = new JComboBox<CategoriaDocente>(CategoriaDocente.values());
		cbCategoriaDocente.setBounds(140, 67, 220, 20);
		contentPane.add(cbCategoriaDocente);

		JLabel lblCategoriaCientifica = new JLabel("Categor\u00eda cient\u00edfica:");
		lblCategoriaCientifica.setBounds(10, 98, 130, 14);
		contentPane.add(lblCategoriaCientifica);

		// La categoria cientifica puede no existir, por eso se agrega la opcion "Ninguna"
		cbCategoriaCientifica = new JComboBox<String>(new String[] { "Ninguna", "MASTER", "DOCTOR" });
		cbCategoriaCientifica.setBounds(140, 95, 220, 20);
		contentPane.add(cbCategoriaCientifica);

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbDepartamento.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Primero debe crear un departamento.");
					return;
				}
				try {
					Docente d = new Docente(vicedecanato.generarId(), txtNombre.getText(),
							(CategoriaDocente) cbCategoriaDocente.getSelectedItem(), leerCategoriaCientifica());
					Departamento dep = vicedecanato.getDepartamentos().get(cbDepartamento.getSelectedIndex());
					dep.agregarDocente(d);
					cargarTabla();
					limpiar();
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAgregar.setBounds(10, 126, 100, 23);
		contentPane.add(btnAgregar);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tabla.getSelectedRow();
				if (fila == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione un docente de la tabla.");
					return;
				}
				try {
					Docente d = docentes.get(fila);
					d.setNombre(txtNombre.getText());
					d.setCategoriaDocente((CategoriaDocente) cbCategoriaDocente.getSelectedItem());
					d.setCategoriaCientifica(leerCategoriaCientifica());

					// Si se cambio el departamento, se saca del viejo y se pone en el nuevo
					Departamento viejo = vicedecanato.buscarDepartamento(d);
					Departamento nuevo = vicedecanato.getDepartamentos().get(cbDepartamento.getSelectedIndex());
					if (viejo != nuevo) {
						viejo.eliminarDocente(d);
						nuevo.agregarDocente(d);
					}
					cargarTabla();
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModificar.setBounds(116, 126, 100, 23);
		contentPane.add(btnModificar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tabla.getSelectedRow();
				if (fila == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione un docente de la tabla.");
					return;
				}
				int respuesta = JOptionPane.showConfirmDialog(null, "\u00bfDesea eliminar el docente?",
						"Confirmar", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					Docente d = docentes.get(fila);
					vicedecanato.buscarDepartamento(d).eliminarDocente(d);
					cargarTabla();
					limpiar();
				}
			}
		});
		btnEliminar.setBounds(222, 126, 100, 23);
		contentPane.add(btnEliminar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 160, 604, 256);
		contentPane.add(scrollPane);

		modelo = new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nombre", "Departamento",
				"Categor\u00eda docente", "Categor\u00eda cient\u00edfica" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabla = new JTable(modelo);
		// Al hacer clic en una fila se cargan sus datos en los campos
		tabla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int fila = tabla.getSelectedRow();
				if (fila != -1) {
					Docente d = docentes.get(fila);
					txtNombre.setText(d.getNombre());
					cbDepartamento.setSelectedIndex(
							vicedecanato.getDepartamentos().indexOf(vicedecanato.buscarDepartamento(d)));
					cbCategoriaDocente.setSelectedItem(d.getCategoriaDocente());
					if (d.getCategoriaCientifica() == null) {
						cbCategoriaCientifica.setSelectedItem("Ninguna");
					} else {
						cbCategoriaCientifica.setSelectedItem(d.getCategoriaCientifica().toString());
					}
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
		btnVolver.setBounds(514, 427, 100, 23);
		contentPane.add(btnVolver);

		// Se llena el combo de departamentos
		for (Departamento dep : vicedecanato.getDepartamentos()) {
			cbDepartamento.addItem(dep.getNombre());
		}
		cargarTabla();
	}

	// Convierte lo que dice el combo en el enum (o null si es "Ninguna")
	private CategoriaCientifica leerCategoriaCientifica() {
		String texto = (String) cbCategoriaCientifica.getSelectedItem();
		if (texto.equals("Ninguna")) {
			return null;
		}
		return CategoriaCientifica.valueOf(texto);
	}

	private void cargarTabla() {
		modelo.setRowCount(0);
		docentes = vicedecanato.getDocentes();
		for (Docente d : docentes) {
			String cientifica = "Ninguna";
			if (d.getCategoriaCientifica() != null) {
				cientifica = d.getCategoriaCientifica().toString();
			}
			modelo.addRow(new Object[] { d.getId(), d.getNombre(), vicedecanato.buscarDepartamento(d).getNombre(),
					d.getCategoriaDocente(), cientifica });
		}
	}

	private void limpiar() {
		txtNombre.setText("");
		cbCategoriaDocente.setSelectedIndex(0);
		cbCategoriaCientifica.setSelectedIndex(0);
	}
}
