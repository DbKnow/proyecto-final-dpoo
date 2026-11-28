package logica;



public class Participacion {
	private int id;
	private Investigador investigador;
	private String tema;
	
	
	public Participacion(int id, Investigador investigador, String tema) {
	    setId(id);
	    setInvestigador(investigador);
	    setTema(tema);
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
	 
		// ---------- investigador ----------
		public Investigador getInvestigador() {
			return investigador;
		}
	 
		public void setInvestigador(Investigador investigador) {
			if (investigador == null) {
				throw new IllegalArgumentException("El investigador no puede ser nulo.");
			}
			this.investigador = investigador;
		}
	 
		// ---------- tema ----------
		public String getTema() {
			return tema;
		}
	 
		public void setTema(String tema) {
			if (tema == null || tema.trim().isEmpty()) {
				throw new IllegalArgumentException("El tema no puede ser nulo ni vacío.");
			}
			if (tema.trim().length() > 150) {
				throw new IllegalArgumentException("El tema no puede superar los 150 caracteres.");
			}
			this.tema = tema.trim();
		}
	
}
