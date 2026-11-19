package logica;

import java.util.ArrayList;

public class LineaInvestigacion {
	private int id;
	private String nombre;
	private Docente responsable;
	private ArrayList<Participacion> participaciones= new ArrayList<>();
	
	
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
	 
		// ---------- responsable ----------
		public Docente getResponsable() {
			return responsable;
		}
	 
		public void setResponsable(Docente responsable) {
			if (responsable == null) {
				throw new IllegalArgumentException("El responsable no puede ser nulo.");
			}
			this.responsable = responsable;
		}
	 
		// ---------- participaciones ----------
		public ArrayList<Participacion> getParticipaciones() {
			return participaciones;
		}
	 
		public void setParticipaciones(ArrayList<Participacion> participaciones) {
			if (participaciones == null) {
				throw new IllegalArgumentException("La lista de participaciones no puede ser nula.");
			}
			if (participaciones.contains(null)) {
				throw new IllegalArgumentException("La lista de participaciones no puede contener elementos nulos.");
			}
			this.participaciones = new ArrayList<>(participaciones); // copia defensiva
		}
	
	
	
	public void agregarParticipacion(Investigador i , String tema){
		
	}
	
	public void eliminarParticipacion(Investigador i){
		
	}
	
	public int calcularPuntajeLinea(){
		return 0;
	}
}
