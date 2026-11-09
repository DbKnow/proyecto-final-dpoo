package logica;

import java.util.ArrayList;

public class CapituloLibro implements IPuntuable{
	private int id;
	private String titulo;
	private ArrayList<String> editores = new ArrayList();
	private String editorial;
	private String issn;
	private String nombreLibro;
	private int volumen;
	
	
	
	// ---------- id ----------
		public int getId() {
			return id;
		}
	 
		public void setId(int id) {
			if (id <= 0) {
				throw new IllegalArgumentException("El id debe ser un entero positivo.");
			}
			this.id = id;
		}
	 
		// ---------- titulo ----------
		public String getTitulo() {
			return titulo;
		}
	 
		public void setTitulo(String titulo) {
			if (titulo == null || titulo.trim().isEmpty()) {
				throw new IllegalArgumentException("El título no puede ser nulo ni vacío.");
			}
			if (titulo.trim().length() > 150) {
				throw new IllegalArgumentException("El título no puede superar los 150 caracteres.");
			}
			this.titulo = titulo.trim();
		}
	 
		// ---------- editores ----------
		public ArrayList<String> getEditores() {
			return editores;
		}
	 
		public void setEditores(ArrayList<String> editores) {
			if (editores == null) {
				throw new IllegalArgumentException("La lista de editores no puede ser nula.");
			}
			ArrayList<String> copia = new ArrayList<>();
			for (String editor : editores) {
				if (editor == null || editor.trim().isEmpty()) {
					throw new IllegalArgumentException("Los editores no pueden ser nulos ni vacíos.");
				}
				copia.add(editor.trim());
			}
			this.editores = copia;
		}
	 
		// ---------- editorial ----------
		public String getEditorial() {
			return editorial;
		}
	 
		public void setEditorial(String editorial) {
			if (editorial == null || editorial.trim().isEmpty()) {
				throw new IllegalArgumentException("La editorial no puede ser nula ni vacía.");
			}
			if (editorial.trim().length() > 100) {
				throw new IllegalArgumentException("La editorial no puede superar los 100 caracteres.");
			}
			this.editorial = editorial.trim();
		}
	 
		// ---------- issn ----------
		public String getIssn() {
			return issn;
		}
	 
		public void setIssn(String issn) {
			if (issn == null || issn.trim().isEmpty()) {
				throw new IllegalArgumentException("El ISSN no puede ser nulo ni vacío.");
			}
			// Acepta "12345679" o "1234-5679"
			String limpio = issn.replaceAll("[-\\s]", "").toUpperCase();
			if (!limpio.matches("\\d{7}[\\dX]")) {
				throw new IllegalArgumentException("El ISSN debe tener 8 caracteres: 7 dígitos y un dígito de control (0-9 o X).");
			}
			if (!esIssnValido(limpio)) {
				throw new IllegalArgumentException("El dígito de control del ISSN no es válido.");
			}
			this.issn = limpio.substring(0, 4) + "-" + limpio.substring(4); // formato NNNN-NNNC
		}
	 
		// ---------- nombreLibro ----------
		public String getNombreLibro() {
			return nombreLibro;
		}
	 
		public void setNombreLibro(String nombreLibro) {
			if (nombreLibro == null || nombreLibro.trim().isEmpty()) {
				throw new IllegalArgumentException("El nombre del libro no puede ser nulo ni vacío.");
			}
			if (nombreLibro.trim().length() > 150) {
				throw new IllegalArgumentException("El nombre del libro no puede superar los 150 caracteres.");
			}
			this.nombreLibro = nombreLibro.trim();
		}
	 
		// ---------- volumen ----------
		public int getVolumen() {
			return volumen;
		}
	 
		public void setVolumen(int volumen) {
			if (volumen <= 0) {
				throw new IllegalArgumentException("El volumen debe ser un entero positivo.");
			}
			this.volumen = volumen;
		}
	 
		// ---------- auxiliar ----------
		private boolean esIssnValido(String s) {
			int suma = 0;
			for (int i = 0; i < 7; i++) {
				suma += (s.charAt(i) - '0') * (8 - i);
			}
			int resto = suma % 11;
			int control = (resto == 0) ? 0 : 11 - resto;
			char esperado = (control == 10) ? 'X' : (char) ('0' + control);
			return s.charAt(7) == esperado;
		}
	
	
	@Override
	public int calcularPuntos() {
		return 3;
	}

	
}
