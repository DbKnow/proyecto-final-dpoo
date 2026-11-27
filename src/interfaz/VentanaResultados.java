package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

import logica.Articulo;
import logica.CapituloLibro;
import logica.GrupoImpacto;
import logica.IPuntuable;
import logica.Investigador;
import logica.Ponencia;
import logica.Vicedecanato;

/**
 * Ventana de los resultados investigativos (articulos, capitulos de libro y ponencias).
 * Segun el tipo elegido en el combo se muestra un panel con los campos de ese tipo.
 */
public class VentanaResultados extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> cbTipo;

	// Un panel por cada tipo de resultado, todos en la misma posicion
	private JPanel panelArticulo;
	private JPanel panelCapitulo;
	private JPanel panelPonencia;

	// Campos del articulo
	private JTextField txtTituloArt;
	private JTextField txtVolumenArt;
	private JTextField txtNumeroArt;
	private JTextField txtPaginasArt;
	private JTextField txtAnioArt;
	private JComboBox<GrupoImpacto> cbGrupo;

	// Campos del capitulo de libro
	private JTextField txtTituloCap;
	private JTextField txtLibroCap;
	private JTextField txtEditorialCap;
	private JTextField txtIssnCap;
	private JTextField txtVolumenCap;
	private JTextField txtEditoresCap;

	// Campos de la ponencia
	private JTextField txtNombrePon;
	private JTextField txtLugarPon;
	private JTextField txtFechaPon;
	private JTextField txtIsbnPon;

	private JComboBox<String> cbAutor;
	private JLabel lblAutores;
	private JTable tabla;
	private DefaultTableModel modelo;

	private Vicedecanato vicedecanato = Vicedecanato.getInstancia();
	// Resultados del tipo elegido, en el mismo orden que las filas de la tabla
	private ArrayList<IPuntuable> visibles = new ArrayList<IPuntuable>();
	private ArrayList<Investigador> investigadores;
	// Formato en que se escribe la fecha de la ponencia
	private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

	public VentanaResultados() {
		setTitle("Resultados investigativos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 680, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblTipo = new JLabel("Tipo de resultado:");
		lblTipo.setBounds(10, 14, 120, 14);
		contentPane.add(lblTipo);

		cbTipo = new JComboBox<String>(new String[] { "Art\u00edculo", "Cap\u00edtulo de libro", "Ponencia" });
		// Al cambiar el tipo se muestra el panel correspondiente y se recarga la tabla
		cbTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanel();
				cargarTabla();
			}
		});
		cbTipo.setBounds(130, 11, 160, 20);
		contentPane.add(cbTipo);

		crearPanelArticulo();
		crearPanelCapitulo();
		crearPanelPonencia();

		// ---------- Autores ----------
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setBounds(10, 165, 60, 14);
		contentPane.add(lblAutor);

		cbAutor = new JComboBox<String>();
		cbAutor.setBounds(70, 162, 220, 20);
		contentPane.add(cbAutor);

		JButton btnAgregarAutor = new JButton("Agregar autor");
		btnAgregarAutor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Pendiente: Investigador.AgregarResultados() todavia no recibe el resultado
				JOptionPane.showMessageDialog(null, "Funcionalidad no implementada todav\u00eda.");
			}
		});
		btnAgregarAutor.setBounds(296, 161, 130, 23);
		contentPane.add(btnAgregarAutor);

		lblAutores = new JLabel("Autores: -");
		lblAutores.setBounds(10, 190, 644, 14);
		contentPane.add(lblAutores);

		// ---------- Botones ----------
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					IPuntuable r;
					if (cbTipo.getSelectedIndex() == 0) {
						Articulo a = new Articulo();
						a.setId(vicedecanato.generarId());
						llenarArticulo(a);
						r = a;
					} else if (cbTipo.getSelectedIndex() == 1) {
						CapituloLibro c = new CapituloLibro();
						c.setId(vicedecanato.generarId());
						llenarCapitulo(c);
						r = c;
					} else {
						Ponencia p = new Ponencia();
						p.setId(vicedecanato.generarId());
						llenarPonencia(p);
						r = p;
					}
					vicedecanato.agregarResultado(r);
					cargarTabla();
				} catch (NumberFormatException ex) {
					// Se pone antes porque NumberFormatException tambien es IllegalArgumentException
					JOptionPane.showMessageDialog(null, "Revise los campos num\u00e9ricos.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(null, "La fecha debe tener el formato dd/mm/aaaa.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAgregar.setBounds(10, 212, 100, 23);
		contentPane.add(btnAgregar);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tabla.getSelectedRow();
				if (fila == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione un resultado de la tabla.");
					return;
				}
				try {
					IPuntuable r = visibles.get(fila);
					// Se usa instanceof para saber de que tipo es el resultado
					if (r instanceof Articulo) {
						llenarArticulo((Articulo) r);
					} else if (r instanceof CapituloLibro) {
						llenarCapitulo((CapituloLibro) r);
					} else {
						llenarPonencia((Ponencia) r);
					}
					cargarTabla();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Revise los campos num\u00e9ricos.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(null, "La fecha debe tener el formato dd/mm/aaaa.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModificar.setBounds(116, 212, 100, 23);
		contentPane.add(btnModificar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tabla.getSelectedRow();
				if (fila == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione un resultado de la tabla.");
					return;
				}
				int respuesta = JOptionPane.showConfirmDialog(null, "\u00bfDesea eliminar el resultado?",
						"Confirmar", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					vicedecanato.eliminarResultado(visibles.get(fila));
					cargarTabla();
				}
			}
		});
		btnEliminar.setBounds(222, 212, 100, 23);
		contentPane.add(btnEliminar);

		// ---------- Tabla ----------
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 246, 644, 270);
		contentPane.add(scrollPane);

		modelo = new DefaultTableModel(new Object[][] {}, new String[] { "ID", "T\u00edtulo", "Detalle", "Puntos" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabla = new JTable(modelo);
		tabla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int fila = tabla.getSelectedRow();
				if (fila != -1) {
					mostrarDatos(visibles.get(fila));
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
		btnVolver.setBounds(554, 527, 100, 23);
		contentPane.add(btnVolver);

		investigadores = vicedecanato.getInvestigadores();
		for (Investigador i : investigadores) {
			cbAutor.addItem(i.getNombre());
		}
		mostrarPanel();
		cargarTabla();
	}

	// ---------- creacion de los tres paneles ----------

	private void crearPanelArticulo() {
		panelArticulo = new JPanel();
		panelArticulo.setLayout(null);
		panelArticulo.setBounds(10, 40, 644, 115);
		contentPane.add(panelArticulo);

		JLabel lblTitulo = new JLabel("T\u00edtulo:");
		lblTitulo.setBounds(0, 3, 60, 14);
		panelArticulo.add(lblTitulo);
		txtTituloArt = new JTextField();
		txtTituloArt.setBounds(60, 0, 380, 20);
		panelArticulo.add(txtTituloArt);

		JLabel lblVolumen = new JLabel("Volumen:");
		lblVolumen.setBounds(0, 31, 60, 14);
		panelArticulo.add(lblVolumen);
		txtVolumenArt = new JTextField();
		txtVolumenArt.setBounds(60, 28, 60, 20);
		panelArticulo.add(txtVolumenArt);

		JLabel lblNumero = new JLabel("N\u00famero:");
		lblNumero.setBounds(140, 31, 60, 14);
		panelArticulo.add(lblNumero);
		txtNumeroArt = new JTextField();
		txtNumeroArt.setBounds(200, 28, 60, 20);
		panelArticulo.add(txtNumeroArt);

		JLabel lblPaginas = new JLabel("P\u00e1ginas:");
		lblPaginas.setBounds(280, 31, 60, 14);
		panelArticulo.add(lblPaginas);
		txtPaginasArt = new JTextField();
		txtPaginasArt.setBounds(340, 28, 60, 20);
		panelArticulo.add(txtPaginasArt);

		JLabel lblAnio = new JLabel("A\u00f1o:");
		lblAnio.setBounds(0, 59, 60, 14);
		panelArticulo.add(lblAnio);
		txtAnioArt = new JTextField();
		txtAnioArt.setBounds(60, 56, 60, 20);
		panelArticulo.add(txtAnioArt);

		JLabel lblGrupo = new JLabel("Grupo:");
		lblGrupo.setBounds(140, 59, 60, 14);
		panelArticulo.add(lblGrupo);
		cbGrupo = new JComboBox<GrupoImpacto>(GrupoImpacto.values());
		cbGrupo.setBounds(200, 56, 100, 20);
		panelArticulo.add(cbGrupo);
	}

	private void crearPanelCapitulo() {
		panelCapitulo = new JPanel();
		panelCapitulo.setLayout(null);
		panelCapitulo.setBounds(10, 40, 644, 115);
		contentPane.add(panelCapitulo);

		JLabel lblTitulo = new JLabel("T\u00edtulo:");
		lblTitulo.setBounds(0, 3, 70, 14);
		panelCapitulo.add(lblTitulo);
		txtTituloCap = new JTextField();
		txtTituloCap.setBounds(70, 0, 250, 20);
		panelCapitulo.add(txtTituloCap);

		JLabel lblLibro = new JLabel("Libro:");
		lblLibro.setBounds(340, 3, 70, 14);
		panelCapitulo.add(lblLibro);
		txtLibroCap = new JTextField();
		txtLibroCap.setBounds(410, 0, 230, 20);
		panelCapitulo.add(txtLibroCap);

		JLabel lblEditorial = new JLabel("Editorial:");
		lblEditorial.setBounds(0, 31, 70, 14);
		panelCapitulo.add(lblEditorial);
		txtEditorialCap = new JTextField();
		txtEditorialCap.setBounds(70, 28, 250, 20);
		panelCapitulo.add(txtEditorialCap);

		JLabel lblIssn = new JLabel("ISSN:");
		lblIssn.setBounds(340, 31, 70, 14);
		panelCapitulo.add(lblIssn);
		txtIssnCap = new JTextField();
		txtIssnCap.setBounds(410, 28, 100, 20);
		panelCapitulo.add(txtIssnCap);

		JLabel lblVolumen = new JLabel("Volumen:");
		lblVolumen.setBounds(0, 59, 70, 14);
		panelCapitulo.add(lblVolumen);
		txtVolumenCap = new JTextField();
		txtVolumenCap.setBounds(70, 56, 60, 20);
		panelCapitulo.add(txtVolumenCap);

		JLabel lblEditores = new JLabel("Editores (separados por coma):");
		lblEditores.setBounds(0, 87, 190, 14);
		panelCapitulo.add(lblEditores);
		txtEditoresCap = new JTextField();
		txtEditoresCap.setBounds(190, 84, 320, 20);
		panelCapitulo.add(txtEditoresCap);
	}

	private void crearPanelPonencia() {
		panelPonencia = new JPanel();
		panelPonencia.setLayout(null);
		panelPonencia.setBounds(10, 40, 644, 115);
		contentPane.add(panelPonencia);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(0, 3, 110, 14);
		panelPonencia.add(lblNombre);
		txtNombrePon = new JTextField();
		txtNombrePon.setBounds(110, 0, 330, 20);
		panelPonencia.add(txtNombrePon);

		JLabel lblLugar = new JLabel("Lugar:");
		lblLugar.setBounds(0, 31, 110, 14);
		panelPonencia.add(lblLugar);
		txtLugarPon = new JTextField();
		txtLugarPon.setBounds(110, 28, 200, 20);
		panelPonencia.add(txtLugarPon);

		JLabel lblFecha = new JLabel("Fecha (dd/mm/aaaa):");
		lblFecha.setBounds(0, 59, 110, 14);
		panelPonencia.add(lblFecha);
		txtFechaPon = new JTextField();
		txtFechaPon.setBounds(110, 56, 100, 20);
		panelPonencia.add(txtFechaPon);

		JLabel lblIsbn = new JLabel("ISBN:");
		lblIsbn.setBounds(0, 87, 110, 14);
		panelPonencia.add(lblIsbn);
		txtIsbnPon = new JTextField();
		txtIsbnPon.setBounds(110, 84, 150, 20);
		panelPonencia.add(txtIsbnPon);
	}

	// Muestra solo el panel del tipo elegido en el combo
	private void mostrarPanel() {
		panelArticulo.setVisible(cbTipo.getSelectedIndex() == 0);
		panelCapitulo.setVisible(cbTipo.getSelectedIndex() == 1);
		panelPonencia.setVisible(cbTipo.getSelectedIndex() == 2);
	}

	// ---------- pasar los datos de los campos al objeto ----------

	private void llenarArticulo(Articulo a) {
		a.setTitulo(txtTituloArt.getText());
		a.setVolumen(Integer.parseInt(txtVolumenArt.getText().trim()));
		a.setNumero(Integer.parseInt(txtNumeroArt.getText().trim()));
		a.setPaginas(Integer.parseInt(txtPaginasArt.getText().trim()));
		a.setAnioPublicacion(Integer.parseInt(txtAnioArt.getText().trim()));
		a.setGrupoImpacto((GrupoImpacto) cbGrupo.getSelectedItem());
	}

	private void llenarCapitulo(CapituloLibro c) {
		c.setTitulo(txtTituloCap.getText());
		c.setNombreLibro(txtLibroCap.getText());
		c.setEditorial(txtEditorialCap.getText());
		c.setIssn(txtIssnCap.getText());
		c.setVolumen(Integer.parseInt(txtVolumenCap.getText().trim()));
		// Los editores se escriben separados por coma y se convierten en una lista
		ArrayList<String> editores = new ArrayList<String>();
		String[] partes = txtEditoresCap.getText().split(",");
		for (String parte : partes) {
			if (!parte.trim().isEmpty()) {
				editores.add(parte.trim());
			}
		}
		c.setEditores(editores);
	}

	private void llenarPonencia(Ponencia p) throws ParseException {
		p.setNombre(txtNombrePon.getText());
		p.setLugar(txtLugarPon.getText());
		formatoFecha.setLenient(false); // no acepta fechas como 31/02/2025
		Date fecha = formatoFecha.parse(txtFechaPon.getText().trim());
		p.setFecha(fecha);
		p.setIsbn(txtIsbnPon.getText());
	}

	// ---------- pasar los datos del objeto a los campos ----------

	private void mostrarDatos(IPuntuable r) {
		if (r instanceof Articulo) {
			Articulo a = (Articulo) r;
			txtTituloArt.setText(a.getTitulo());
			txtVolumenArt.setText(String.valueOf(a.getVolumen()));
			txtNumeroArt.setText(String.valueOf(a.getNumero()));
			txtPaginasArt.setText(String.valueOf(a.getPaginas()));
			txtAnioArt.setText(String.valueOf(a.getAnioPublicacion()));
			cbGrupo.setSelectedItem(a.getGrupoImpacto());
		} else if (r instanceof CapituloLibro) {
			CapituloLibro c = (CapituloLibro) r;
			txtTituloCap.setText(c.getTitulo());
			txtLibroCap.setText(c.getNombreLibro());
			txtEditorialCap.setText(c.getEditorial());
			txtIssnCap.setText(c.getIssn());
			txtVolumenCap.setText(String.valueOf(c.getVolumen()));
			String editores = "";
			for (int i = 0; i < c.getEditores().size(); i++) {
				if (i > 0) {
					editores += ", ";
				}
				editores += c.getEditores().get(i);
			}
			txtEditoresCap.setText(editores);
		} else {
			Ponencia p = (Ponencia) r;
			txtNombrePon.setText(p.getNombre());
			txtLugarPon.setText(p.getLugar());
			txtFechaPon.setText(formatoFecha.format(p.getFecha()));
			txtIsbnPon.setText(p.getIsbn());
		}

		// Los autores son los investigadores que tienen este resultado en su lista
		String autores = "";
		for (Investigador i : vicedecanato.buscarAutores(r)) {
			if (!autores.isEmpty()) {
				autores += ", ";
			}
			autores += i.getNombre();
		}
		if (autores.isEmpty()) {
			autores = "-";
		}
		lblAutores.setText("Autores: " + autores);
	}

	// Llena la tabla solo con los resultados del tipo elegido
	private void cargarTabla() {
		modelo.setRowCount(0);
		visibles.clear();
		for (IPuntuable r : vicedecanato.getResultados()) {
			if (cbTipo.getSelectedIndex() == 0 && r instanceof Articulo) {
				Articulo a = (Articulo) r;
				visibles.add(r);
				modelo.addRow(new Object[] { a.getId(), a.getTitulo(),
						a.getGrupoImpacto() + ", " + a.getAnioPublicacion(), a.calcularPuntos() });
			} else if (cbTipo.getSelectedIndex() == 1 && r instanceof CapituloLibro) {
				CapituloLibro c = (CapituloLibro) r;
				visibles.add(r);
				modelo.addRow(new Object[] { c.getId(), c.getTitulo(), c.getNombreLibro(), c.calcularPuntos() });
			} else if (cbTipo.getSelectedIndex() == 2 && r instanceof Ponencia) {
				Ponencia p = (Ponencia) r;
				visibles.add(r);
				modelo.addRow(new Object[] { p.getId(), p.getNombre(), p.getLugar(), p.calcularPuntos() });
			}
		}
		lblAutores.setText("Autores: -");
	}
}
