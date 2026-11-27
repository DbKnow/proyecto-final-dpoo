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

import logica.Docente;
import logica.Investigador;
import logica.LineaInvestigacion;
import logica.Participacion;
import logica.Vicedecanato;

/**
 * Ventana de las lineas de investigacion.
 * Arriba se gestionan las lineas y abajo los integrantes de la linea seleccionada.
 */
public class VentanaLineas extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JComboBox<String> cbResponsable;
	private JTable tablaLineas;
	private DefaultTableModel modeloLineas;
	private JComboBox<String> cbInvestigador;
	private JTextField txtTema;
	private JTable tablaIntegrantes;
	private DefaultTableModel modeloIntegrantes;

	private Vicedecanato vicedecanato = Vicedecanato.getInstancia();
	// Listas que se usan para llenar los combos (mismo orden que los elementos del combo)
	private ArrayList<Docente> docentes;
	private ArrayList<Investigador> investigadores;

	public VentanaLineas() {
		setTitle("L\u00edneas de investigaci\u00f3n");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// ---------- Parte de arriba: las lineas ----------
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 14, 90, 14);
		contentPane.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(100, 11, 260, 20);
		contentPane.add(txtNombre);

		JLabel lblResponsable = new JLabel("Responsable:");
		lblResponsable.setBounds(10, 42, 90, 14);
		contentPane.add(lblResponsable);

		cbResponsable = new JComboBox<String>();
		cbResponsable.setBounds(100, 39, 260, 20);
		contentPane.add(cbResponsable);

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbResponsable.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "No hay docentes para elegir como responsable.");
					return;
				}
				try {
					LineaInvestigacion l = new LineaInvestigacion();
					l.setId(vicedecanato.generarId());
					l.setNombre(txtNombre.getText());
					l.setResponsable(docentes.get(cbResponsable.getSelectedIndex()));
					vicedecanato.agregarLinea(l);
					cargarTablaLineas();
					txtNombre.setText("");
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
				int fila = tablaLineas.getSelectedRow();
				if (fila == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione una l\u00ednea de la tabla.");
					return;
				}
				try {
					LineaInvestigacion l = vicedecanato.getLineas().get(fila);
					l.setNombre(txtNombre.getText());
					l.setResponsable(docentes.get(cbResponsable.getSelectedIndex()));
					cargarTablaLineas();
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
				int fila = tablaLineas.getSelectedRow();
				if (fila == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione una l\u00ednea de la tabla.");
					return;
				}
				int respuesta = JOptionPane.showConfirmDialog(null, "\u00bfDesea eliminar la l\u00ednea?",
						"Confirmar", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					vicedecanato.eliminarLinea(vicedecanato.getLineas().get(fila));
					cargarTablaLineas();
					modeloIntegrantes.setRowCount(0);
					txtNombre.setText("");
				}
			}
		});
		btnEliminar.setBounds(222, 70, 100, 23);
		contentPane.add(btnEliminar);

		JScrollPane scrollLineas = new JScrollPane();
		scrollLineas.setBounds(10, 104, 604, 110);
		contentPane.add(scrollLineas);

		modeloLineas = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Nombre", "Responsable" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablaLineas = new JTable(modeloLineas);
		// Al seleccionar una linea se cargan sus datos y sus integrantes
		tablaLineas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int fila = tablaLineas.getSelectedRow();
				if (fila != -1) {
					LineaInvestigacion l = vicedecanato.getLineas().get(fila);
					txtNombre.setText(l.getNombre());
					cbResponsable.setSelectedIndex(docentes.indexOf(l.getResponsable()));
					cargarTablaIntegrantes();
				}
			}
		});
		scrollLineas.setViewportView(tablaLineas);

		// ---------- Parte de abajo: integrantes de la linea seleccionada ----------
		JLabel lblIntegrantes = new JLabel("Integrantes de la l\u00ednea seleccionada");
		lblIntegrantes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIntegrantes.setBounds(10, 228, 300, 14);
		contentPane.add(lblIntegrantes);

		JLabel lblInvestigador = new JLabel("Investigador:");
		lblInvestigador.setBounds(10, 256, 90, 14);
		contentPane.add(lblInvestigador);

		cbInvestigador = new JComboBox<String>();
		cbInvestigador.setBounds(100, 253, 260, 20);
		contentPane.add(cbInvestigador);

		JLabel lblTema = new JLabel("Tema:");
		lblTema.setBounds(10, 284, 90, 14);
		contentPane.add(lblTema);

		txtTema = new JTextField();
		txtTema.setBounds(100, 281, 260, 20);
		contentPane.add(txtTema);

		JButton btnAgregarIntegrante = new JButton("Agregar integrante");
		btnAgregarIntegrante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tablaLineas.getSelectedRow();
				if (fila == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione una l\u00ednea de la tabla.");
					return;
				}
				if (cbInvestigador.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "No hay investigadores para agregar.");
					return;
				}
				try {
					LineaInvestigacion l = vicedecanato.getLineas().get(fila);
					l.agregarParticipacion(investigadores.get(cbInvestigador.getSelectedIndex()), txtTema.getText());
					cargarTablaIntegrantes();
					txtTema.setText("");
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAgregarIntegrante.setBounds(10, 312, 150, 23);
		contentPane.add(btnAgregarIntegrante);

		JButton btnQuitarIntegrante = new JButton("Quitar integrante");
		btnQuitarIntegrante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Pendiente: falta el metodo en la logica
				JOptionPane.showMessageDialog(null, "Funcionalidad no implementada todav\u00eda.");
			}
		});
		btnQuitarIntegrante.setBounds(166, 312, 150, 23);
		contentPane.add(btnQuitarIntegrante);

		JScrollPane scrollIntegrantes = new JScrollPane();
		scrollIntegrantes.setBounds(10, 346, 604, 170);
		contentPane.add(scrollIntegrantes);

		modeloIntegrantes = new DefaultTableModel(new Object[][] {}, new String[] { "Investigador", "Tema" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablaIntegrantes = new JTable(modeloIntegrantes);
		scrollIntegrantes.setViewportView(tablaIntegrantes);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVolver.setBounds(514, 527, 100, 23);
		contentPane.add(btnVolver);

		// Se llenan los combos
		docentes = vicedecanato.getDocentes();
		for (Docente d : docentes) {
			cbResponsable.addItem(d.getNombre());
		}
		investigadores = vicedecanato.getInvestigadores();
		for (Investigador i : investigadores) {
			cbInvestigador.addItem(i.getNombre());
		}
		cargarTablaLineas();
	}

	private void cargarTablaLineas() {
		modeloLineas.setRowCount(0);
		for (LineaInvestigacion l : vicedecanato.getLineas()) {
			modeloLineas.addRow(new Object[] { l.getId(), l.getNombre(), l.getResponsable().getNombre() });
		}
	}

	// Muestra los integrantes de la linea seleccionada en la tabla de arriba
	private void cargarTablaIntegrantes() {
		modeloIntegrantes.setRowCount(0);
		int fila = tablaLineas.getSelectedRow();
		if (fila == -1) {
			return;
		}
		LineaInvestigacion l = vicedecanato.getLineas().get(fila);
		for (Participacion p : l.getParticipaciones()) {
			modeloIntegrantes.addRow(new Object[] { p.getInvestigador().getNombre(), p.getTema() });
		}
	}
}
