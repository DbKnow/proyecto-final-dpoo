package logica;

import java.util.ArrayList;

public class LineaInvestigacion {
	private int id;
	private String nombre;
	private Docente responsable;
	private ArrayList<Participacion> participaciones= new ArrayList<>();
	
	
	public LineaInvestigacion(int id, String nombre, Docente responsable) {
	    setId(id);
	    setNombre(nombre);
	    setResponsable(responsable);
	    this.participaciones = new ArrayList<Participacion>();
	}
	
	
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
	
	
	
		public void agregarParticipacion(Investigador i, String tema) {
		    if (i == null) {
		        throw new IllegalArgumentException("El investigador no puede ser nulo.");
		    }

		    if (tema == null || tema.trim().isEmpty()) {
		        throw new IllegalArgumentException("El tema no puede ser nulo ni vacío.");
		    }

		    for (Participacion participacion : participaciones) {
		        if (participacion.getInvestigador().getId() == i.getId()) {
		            throw new IllegalArgumentException("El investigador ya participa en esta línea.");
		        }
		    }

		    Participacion participacion = new Participacion(
		        Vicedecanato.getInstancia().generarId(), i, tema
		    );

		    participaciones.add(participacion);
		}
	
		public void eliminarParticipacion(Investigador i) {
		    if (i == null) {
		        throw new IllegalArgumentException("El investigador no puede ser nulo.");
		    }

		    Participacion participacionEliminar = null;

		    for (Participacion participacion : participaciones) {
		        if (participacion.getInvestigador().getId() == i.getId()) {
		            participacionEliminar = participacion;
		        }
		    }

		    if (participacionEliminar == null) {
		        throw new IllegalArgumentException("El investigador no participa en esta línea.");
		    }

		    participaciones.remove(participacionEliminar);
		}
	
	
		public int calcularPuntajeLinea(){
		return 0;
	}
}
