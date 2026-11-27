package logica;

import java.util.ArrayList;

/**
 * Clase de control del sistema. Guarda todas las listas generales
 * y es la que usa la interfaz para obtener y guardar los datos.
 *
 * Se usa una sola instancia para toda la aplicacion (patron Singleton),
 * asi todas las ventanas trabajan con los mismos datos.
 *
 * NOTA: los metodos de los reportes (R1 a R6) todavia no estan hechos.
 */
public class Vicedecanato {

	private static Vicedecanato instancia;

	private ArrayList<Departamento> departamentos;
	private ArrayList<LineaInvestigacion> lineas;
	private ArrayList<CursoPosgrado> cursos;
	private ArrayList<PlanMaestria> planes;

	// No aparecen en el UML, pero la interfaz necesita guardarlos en algun lugar
	private ArrayList<Estudiante> estudiantes;
	private ArrayList<IPuntuable> resultados;

	// Contador para generar los id automaticamente
	private int ultimoId;


	private Vicedecanato() {
		departamentos = new ArrayList<Departamento>();
		lineas = new ArrayList<LineaInvestigacion>();
		cursos = new ArrayList<CursoPosgrado>();
		planes = new ArrayList<PlanMaestria>();
		estudiantes = new ArrayList<Estudiante>();
		resultados = new ArrayList<IPuntuable>();
		ultimoId = 0;
	}


	public static Vicedecanato getInstancia() {
		if (instancia == null) {
			instancia = new Vicedecanato();
		}
		return instancia;
	}


	// Devuelve un id nuevo cada vez que se llama (1, 2, 3, ...)
	public int generarId() {
		ultimoId++;
		return ultimoId;
	}


	// ---------- getters de las listas ----------
	public ArrayList<Departamento> getDepartamentos() {
		return departamentos;
	}

	public ArrayList<LineaInvestigacion> getLineas() {
		return lineas;
	}

	public ArrayList<CursoPosgrado> getCursos() {
		return cursos;
	}

	public ArrayList<PlanMaestria> getPlanes() {
		return planes;
	}

	public ArrayList<Estudiante> getEstudiantes() {
		return estudiantes;
	}

	public ArrayList<IPuntuable> getResultados() {
		return resultados;
	}


	// ---------- agregar ----------
	public void agregarDepartamento(Departamento d) {
		if (d == null) {
			throw new IllegalArgumentException("El departamento no puede ser nulo.");
		}
		departamentos.add(d);
	}

	public void agregarLinea(LineaInvestigacion l) {
		if (l == null) {
			throw new IllegalArgumentException("La l\u00ednea no puede ser nula.");
		}
		lineas.add(l);
	}

	public void agregarCurso(CursoPosgrado c) {
		if (c == null) {
			throw new IllegalArgumentException("El curso no puede ser nulo.");
		}
		cursos.add(c);
	}

	public void agregarPlan(PlanMaestria p) {
		if (p == null) {
			throw new IllegalArgumentException("El plan no puede ser nulo.");
		}
		planes.add(p);
	}

	public void agregarEstudiante(Estudiante e) {
		if (e == null) {
			throw new IllegalArgumentException("El estudiante no puede ser nulo.");
		}
		estudiantes.add(e);
	}

	public void agregarResultado(IPuntuable r) {
		if (r == null) {
			throw new IllegalArgumentException("El resultado no puede ser nulo.");
		}
		resultados.add(r);
	}


	// ---------- eliminar ----------
	public void eliminarDepartamento(Departamento d) {
		if (!d.getDocentes().isEmpty()) {
			throw new IllegalArgumentException("No se puede eliminar un departamento que tiene docentes.");
		}
		departamentos.remove(d);
	}

	public void eliminarLinea(LineaInvestigacion l) {
		lineas.remove(l);
	}

	public void eliminarCurso(CursoPosgrado c) {
		// Si el curso estaba en algun plan de maestria, se quita tambien de ahi
		for (PlanMaestria p : planes) {
			p.getCursos().remove(c);
		}
		cursos.remove(c);
	}

	public void eliminarPlan(PlanMaestria p) {
		planes.remove(p);
	}

	public void eliminarEstudiante(Estudiante e) {
		estudiantes.remove(e);
	}

	public void eliminarResultado(IPuntuable r) {
		resultados.remove(r);
	}


	// ---------- busquedas que necesita la interfaz ----------

	// Todos los docentes de todos los departamentos
	public ArrayList<Docente> getDocentes() {
		ArrayList<Docente> lista = new ArrayList<Docente>();
		for (Departamento d : departamentos) {
			lista.addAll(d.getDocentes());
		}
		return lista;
	}

	// Solo los docentes con categoria cientifica Doctor (pueden ser responsables de un curso)
	public ArrayList<Docente> getDoctores() {
		ArrayList<Docente> lista = new ArrayList<Docente>();
		for (Docente d : getDocentes()) {
			if (d.esDoctor()) {
				lista.add(d);
			}
		}
		return lista;
	}

	// Docentes y estudiantes juntos
	public ArrayList<Investigador> getInvestigadores() {
		ArrayList<Investigador> lista = new ArrayList<Investigador>();
		lista.addAll(getDocentes());
		lista.addAll(estudiantes);
		return lista;
	}

	// Como Docente no guarda su departamento, se busca en que departamento esta
	public Departamento buscarDepartamento(Docente docente) {
		for (Departamento d : departamentos) {
			if (d.getDocentes().contains(docente)) {
				return d;
			}
		}
		return null;
	}

	// Devuelve los investigadores que tienen ese resultado en su lista (los autores)
	public ArrayList<Investigador> buscarAutores(IPuntuable resultado) {
		ArrayList<Investigador> lista = new ArrayList<Investigador>();
		for (Investigador i : getInvestigadores()) {
			if (i.getResultados().contains(resultado)) {
				lista.add(i);
			}
		}
		return lista;
	}
}
