package logica;


import java.util.Date;

public class Ponencia implements IPuntuable{
	private int id;
	private String nombre;
	private String lugar;
	private Date fecha;
	private String isbn;
	
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
	 
		// ---------- nombre ----------
		public String getNombre() {
			return nombre;
		}
	 
		public void setNombre(String nombre) {
			if (nombre == null || nombre.trim().isEmpty()) {
				throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío.");
			}
			if (nombre.trim().length() > 100) {
				throw new IllegalArgumentException("El nombre no puede superar los 100 caracteres.");
			}
			this.nombre = nombre.trim();
		}
	 
		// ---------- lugar ----------
		public String getLugar() {
			return lugar;
		}
	 
		public void setLugar(String lugar) {
			if (lugar == null || lugar.trim().isEmpty()) {
				throw new IllegalArgumentException("El lugar no puede ser nulo ni vacío.");
			}
			if (lugar.trim().length() > 100) {
				throw new IllegalArgumentException("El lugar no puede superar los 100 caracteres.");
			}
			this.lugar = lugar.trim();
		}
	 
		// ---------- fecha ----------
		public Date getFecha() {
			return fecha == null ? null : new Date(fecha.getTime()); // copia defensiva
		}
	 
		public void setFecha(Date fecha) {
			if (fecha == null) {
				throw new IllegalArgumentException("La fecha no puede ser nula.");
			}
			if (fecha.after(new Date())) {
				throw new IllegalArgumentException("La fecha no puede ser futura.");
			}
			this.fecha = new Date(fecha.getTime()); // copia defensiva
		}
	 
		// ---------- isbn ----------
		public String getIsbn() {
			return isbn;
		}
	 
		public void setIsbn(String isbn) {
			if (isbn == null || isbn.trim().isEmpty()) {
				throw new IllegalArgumentException("El ISBN no puede ser nulo ni vacío.");
			}
			// Se permiten guiones y espacios como separadores
			String limpio = isbn.replaceAll("[-\\s]", "").toUpperCase();
	 
			if (limpio.length() == 10) {
				if (!esIsbn10Valido(limpio)) {
					throw new IllegalArgumentException("El ISBN-10 no es válido.");
				}
			} else if (limpio.length() == 13) {
				if (!esIsbn13Valido(limpio)) {
					throw new IllegalArgumentException("El ISBN-13 no es válido.");
				}
			} else {
				throw new IllegalArgumentException("El ISBN debe tener 10 o 13 dígitos.");
			}
			this.isbn = limpio;
		}
	 
		// ---------- auxiliares ----------
		private boolean esIsbn10Valido(String s) {
			// 9 dígitos + dígito de control (0-9 o X)
			if (!s.matches("\\d{9}[\\dX]")) {
				return false;
			}
			int suma = 0;
			for (int i = 0; i < 9; i++) {
				suma += (s.charAt(i) - '0') * (10 - i);
			}
			char ultimo = s.charAt(9);
			suma += (ultimo == 'X') ? 10 : (ultimo - '0');
			return suma % 11 == 0;
		}
	 
		private boolean esIsbn13Valido(String s) {
			if (!s.matches("\\d{13}")) {
				return false;
			}
			int suma = 0;
			for (int i = 0; i < 13; i++) {
				int digito = s.charAt(i) - '0';
				suma += (i % 2 == 0) ? digito : digito * 3;
			}
			return suma % 10 == 0;
		}
	
	
	
	@Override
	public int calcularPuntos() {
		// TODO Auto-generated method stub
		return 1;
	}
}
