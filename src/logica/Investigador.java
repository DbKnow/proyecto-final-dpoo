package logica;

import java.util.ArrayList;

public abstract class Investigador {
	protected int id;
	protected String nombre;
	protected ArrayList<IPuntuable> resultados = new ArrayList<>();
	
	
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
	 
		// ---------- resultados ----------
		public ArrayList<IPuntuable> getResultados() {
			return resultados;
		}
	 
		public void setResultados(ArrayList<IPuntuable> resultados) {
			if (resultados == null) {
				throw new IllegalArgumentException("La lista de resultados no puede ser nula.");
			}
			if (resultados.contains(null)) {
				throw new IllegalArgumentException("La lista de resultados no puede contener elementos nulos.");
			}
			this.resultados = new ArrayList<>(resultados); // copia defensiva
		}
	
	
		public void agregarResultado(IPuntuable resultado) {
		    if (resultado == null) {
		        throw new IllegalArgumentException("El resultado no puede ser nulo.");
		    }

		    if (resultados.contains(resultado)) {
		        throw new IllegalArgumentException("El investigador ya tiene asociado este resultado.");
		    }

		    resultados.add(resultado);
		}
		
		
		public void eliminarResultado(IPuntuable resultado) {
		    if (resultado == null) {
		        throw new IllegalArgumentException("El resultado no puede ser nulo.");
		    }

		    if (!resultados.remove(resultado)) {
		        throw new IllegalArgumentException("El resultado no está asociado a este investigador.");
		    }
		}
		
	
	public int calcularPuntaje(){
		return 0;
		
	}
}
