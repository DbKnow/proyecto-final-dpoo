package logica;

import java.util.Date;
import java.time.Year;

public class Articulo implements IPuntuable{
	
	private static final int ANIO_MINIMO = 1900;
	
	private int id;
	private String titulo;
	private int numero;
	private int volumen;
	private int anioPublicacion;
	private int paginas;
	private GrupoImpacto grupoImpacto;
	
	
	
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
	 
		// ---------- numero ----------
		public int getNumero() {
			return numero;
		}
	 
		public void setNumero(int numero) {
			if (numero <= 0) {
				throw new IllegalArgumentException("El número debe ser un entero positivo.");
			}
			this.numero = numero;
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
	 
		// ---------- anioPublicacion ----------
		public int getAnioPublicacion() {
			return anioPublicacion;
		}
	 
		public void setAnioPublicacion(int anioPublicacion) {
			int anioActual = Year.now().getValue();
			if (anioPublicacion < ANIO_MINIMO || anioPublicacion > anioActual) {
				throw new IllegalArgumentException(
						"El año de publicación debe estar entre " + ANIO_MINIMO + " y " + anioActual + ".");
			}
			this.anioPublicacion = anioPublicacion;
		}
	 
		// ---------- paginas ----------
		public int getPaginas() {
			return paginas;
		}
	 
		public void setPaginas(int paginas) {
			if (paginas <= 0) {
				throw new IllegalArgumentException("El número de páginas debe ser un entero positivo.");
			}
			this.paginas = paginas;
		}
	 
		// ---------- grupoImpacto ----------
		public GrupoImpacto getGrupoImpacto() {
			return grupoImpacto;
		}
	 
		public void setGrupoImpacto(GrupoImpacto grupoImpacto) {
			if (grupoImpacto == null) {
				throw new IllegalArgumentException("El grupo de impacto no puede ser nulo.");
			}
			this.grupoImpacto = grupoImpacto;
		}
	
	
	//hacer set y get, y implementar interfaz 
	
	@Override
	public int calcularPuntos() {
		switch (grupoImpacto) {
        case GRUPO1: return 8;
        case GRUPO2: return 6;
        case GRUPO3: return 4;
        case GRUPO4: return 4;
        case WOS: return 10;
        default:      return 0;
    }
	}
	
	
	

	
	
}
