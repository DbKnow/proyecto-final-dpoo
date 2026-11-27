package interfaz;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import logica.Articulo;
import logica.CapituloLibro;
import logica.CategoriaCientifica;
import logica.CategoriaDocente;
import logica.CursoPosgrado;
import logica.Departamento;
import logica.Docente;
import logica.Estudiante;
import logica.GrupoImpacto;
import logica.LineaInvestigacion;
import logica.PlanMaestria;
import logica.Ponencia;
import logica.Vicedecanato;

/**
 * Carga unos pocos datos de ejemplo al abrir la aplicacion,
 * para poder probar las ventanas sin tener que introducir todo a mano.
 */
public class DatosPrueba {

	public static void cargar() {
		Vicedecanato v = Vicedecanato.getInstancia();

		// ---------- Departamentos ----------
		Departamento informatica = new Departamento(v.generarId(), "Ingeniería Informática");
		Departamento matematica = new Departamento(v.generarId(), "Matemática");
		Departamento fisica = new Departamento(v.generarId(), "Física");
		v.agregarDepartamento(informatica);
		v.agregarDepartamento(matematica);
		v.agregarDepartamento(fisica);

		// ---------- Docentes ----------
		Docente ana = new Docente(v.generarId(), "Ana María Pérez Díaz",
				CategoriaDocente.PROFESOR_TITULAR, CategoriaCientifica.DOCTOR);
		Docente carlos = new Docente(v.generarId(), "Carlos Hernández Ruiz",
				CategoriaDocente.PROFESOR_AUXILIAR, CategoriaCientifica.MASTER);
		Docente lidia = new Docente(v.generarId(), "Lidia Gómez Valdés",
				CategoriaDocente.ASISTENTE, null); // sin categoria cientifica
		Docente roberto = new Docente(v.generarId(), "Roberto Fernández Soto",
				CategoriaDocente.PROFESOR_TITULAR, CategoriaCientifica.DOCTOR);
		informatica.agregarDocente(ana);
		informatica.agregarDocente(carlos);
		matematica.agregarDocente(lidia);
		fisica.agregarDocente(roberto);

		// ---------- Estudiantes ----------
		// El constructor de Estudiante no recibe id, por eso se le pone despues
		Estudiante daniela = new Estudiante("Daniela Ríos Acosta");
		daniela.setId(v.generarId());
		Estudiante kevin = new Estudiante("Kevin Morales Peña");
		kevin.setId(v.generarId());
		v.agregarEstudiante(daniela);
		v.agregarEstudiante(kevin);

		// ---------- Lineas de investigacion ----------
		LineaInvestigacion ia = new LineaInvestigacion();
		ia.setId(v.generarId());
		ia.setNombre("Inteligencia artificial aplicada");
		ia.setResponsable(ana);
		ia.agregarParticipacion(carlos, "Redes neuronales para diagnóstico");
		ia.agregarParticipacion(daniela, "Clasificación de imágenes médicas");
		v.agregarLinea(ia);

		LineaInvestigacion modelacion = new LineaInvestigacion();
		modelacion.setId(v.generarId());
		modelacion.setNombre("Modelación matemática");
		modelacion.setResponsable(lidia);
		v.agregarLinea(modelacion);

		// ---------- Resultados investigativos ----------
		Articulo articulo = new Articulo();
		articulo.setId(v.generarId());
		articulo.setTitulo("Detección temprana con redes convolucionales");
		articulo.setVolumen(19);
		articulo.setNumero(2);
		articulo.setPaginas(14);
		articulo.setAnioPublicacion(2025);
		articulo.setGrupoImpacto(GrupoImpacto.GRUPO2);
		v.agregarResultado(articulo);

		CapituloLibro capitulo = new CapituloLibro();
		capitulo.setId(v.generarId());
		capitulo.setTitulo("Métodos numéricos en la ingeniería");
		capitulo.setNombreLibro("Avances en matemática aplicada");
		capitulo.setEditorial("Editorial Universitaria");
		capitulo.setIssn("0317-8471");
		capitulo.setVolumen(3);
		ArrayList<String> editores = new ArrayList<String>();
		editores.add("Pedro Álvarez");
		capitulo.setEditores(editores);
		v.agregarResultado(capitulo);

		Ponencia ponencia = new Ponencia();
		ponencia.setId(v.generarId());
		ponencia.setNombre("Aplicaciones de la IA en la agricultura");
		ponencia.setLugar("La Habana");
		ponencia.setFecha(new GregorianCalendar(2025, 2, 15).getTime()); // 15 de marzo de 2025
		ponencia.setIsbn("978-0-306-40615-7");
		v.agregarResultado(ponencia);

		// ---------- Cursos de posgrado ----------
		CursoPosgrado metodologia = new CursoPosgrado(v.generarId(), "Metodología de la investigación",
				"Formar habilidades para el diseño de proyectos de investigación.", 4, 2025, ana);
		CursoPosgrado estadistica = new CursoPosgrado(v.generarId(), "Estadística aplicada",
				"Aplicar métodos estadísticos al análisis de datos.", 3, 2025, roberto);
		v.agregarCurso(metodologia);
		v.agregarCurso(estadistica);

		metodologia.inscribir(carlos);
		metodologia.inscribir(lidia);
		metodologia.asignarNota(carlos, 5);
		metodologia.asignarNota(lidia, 2);
		estadistica.inscribir(carlos);
		estadistica.asignarNota(carlos, 4);

		// ---------- Plan de maestria ----------
		PlanMaestria plan = new PlanMaestria(v.generarId());
		plan.agregarCurso(metodologia);
		plan.agregarCurso(estadistica);
		plan.matricular(carlos);
		plan.matricular(lidia);
		v.agregarPlan(plan);
	}
}
