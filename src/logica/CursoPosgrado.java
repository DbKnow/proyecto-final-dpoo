package logica;

import java.util.ArrayList;
import java.util.List;

public class CursoPosgrado {

    private int id;
    private String tema;
    private String objetivos;
    private int creditos;
    private int anio;
    private Docente responsable;
    private ArrayList<InscripcionCurso> inscripciones;


    public CursoPosgrado(int id, String tema, String objetivos, int creditos, int anio, Docente responsable) {
        setId(id);
        setTema(tema);
        setObjetivos(objetivos);
        setCreditos(creditos);
        setAnio(anio);
        setResponsable(responsable);
        this.inscripciones = new ArrayList<>();
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


    // ---------- objetivos ----------
    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        if (objetivos == null || objetivos.trim().isEmpty()) {
            throw new IllegalArgumentException("Los objetivos no pueden ser nulos ni vacíos.");
        }
        if (objetivos.trim().length() > 500) {
            throw new IllegalArgumentException("Los objetivos no pueden superar los 500 caracteres.");
        }
        this.objetivos = objetivos.trim();
    }


    // ---------- creditos ----------
    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        if (creditos <= 0) {
            throw new IllegalArgumentException("Los créditos deben ser un entero positivo.");
        }
        this.creditos = creditos;
    }


    // ---------- anio ----------
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        int anioActual = java.time.Year.now().getValue();
        if (anio < 1900 || anio > anioActual) {
            throw new IllegalArgumentException("El año debe estar entre 1900 y " + anioActual + ".");
        }
        this.anio = anio;
    }


    // ---------- responsable ----------
    public Docente getResponsable() {
        return responsable;
    }

    public void setResponsable(Docente responsable) {
        if (responsable == null) {
            throw new IllegalArgumentException("El responsable no puede ser nulo.");
        }

        if (!responsable.esDoctor()) {
            throw new IllegalArgumentException("El responsable debe ser Doctor.");
        }

        this.responsable = responsable;
    }


    // ---------- inscripciones ----------
    public List<InscripcionCurso> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(InscripcionCurso inscripcion) {
        if (inscripcion == null) {
            throw new IllegalArgumentException("La inscripción no puede ser nula.");
        }

        if (inscripcion.getCurso() != this) {
            throw new IllegalArgumentException("La inscripción pertenece a otro curso.");
        }

        inscripciones.add(inscripcion);
    }


    public void inscribir(Docente docente) {
        if (docente == null) {
            throw new IllegalArgumentException("El docente no puede ser nulo.");
        }

        for (InscripcionCurso inscripcion : inscripciones) {
            if (inscripcion.getParticipante().getId() == docente.getId()) {
                throw new IllegalArgumentException("El docente ya está inscrito en este curso.");
            }
        }

        InscripcionCurso inscripcion = new InscripcionCurso(
            inscripciones.size() + 1, docente, 0, this
        );

        inscripciones.add(inscripcion);
        docente.agregarInscripcion(inscripcion);
    }

    
    public void darDeBaja(Docente docente) {
        if (docente == null) {
            throw new IllegalArgumentException("El docente no puede ser nulo.");
        }

        InscripcionCurso inscripcionEliminar = null;

        for (InscripcionCurso inscripcion : inscripciones) {
            if (inscripcion.getParticipante().getId() == docente.getId()) {
                inscripcionEliminar = inscripcion;
            }
        }

        if (inscripcionEliminar == null) {
            throw new IllegalArgumentException("El docente no está inscrito en este curso.");
        }

        inscripciones.remove(inscripcionEliminar);
        docente.eliminarInscripcion(inscripcionEliminar);
    }

    
    public void asignarNota(Docente docente, double nota) {
        if (docente == null) {
            throw new IllegalArgumentException("El docente no puede ser nulo.");
        }

        if (Double.isNaN(nota) || Double.isInfinite(nota) || nota < 0 || nota > 5) {
            throw new IllegalArgumentException("La nota debe estar entre 0 y 5.");
        }

        boolean encontrado = false;

        for (InscripcionCurso inscripcion : inscripciones) {
            if (inscripcion.getParticipante().getId() == docente.getId()) {
                inscripcion.setNota(nota);
                encontrado = true;
            }
        }

        if (!encontrado) {
            throw new IllegalArgumentException("El docente no está inscrito en este curso.");
        }
    }
}
