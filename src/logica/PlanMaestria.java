package logica;

import java.util.ArrayList;
import java.util.List;

public class PlanMaestria {

    private int id;
    private ArrayList<CursoPosgrado> cursos;
    private List<Docente> matriculados;


    public PlanMaestria(int id) {
        setId(id);
        this.cursos = new ArrayList<>();
        this.matriculados = new ArrayList<>();
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


    // ---------- cursos ----------
    public List<CursoPosgrado> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoPosgrado> cursos) {
        if (cursos == null) {
            throw new IllegalArgumentException("La lista de cursos no puede ser nula.");
        }
        if (cursos.contains(null)) {
            throw new IllegalArgumentException("La lista de cursos no puede contener elementos nulos.");
        }
        this.cursos = new ArrayList<>(cursos);
    }


    // ---------- matriculados ----------
    public List<Docente> getMatriculados() {
        return matriculados;
    }

    public void setMatriculados(List<Docente> matriculados) {
        if (matriculados == null) {
            throw new IllegalArgumentException("La lista de matriculados no puede ser nula.");
        }
        if (matriculados.contains(null)) {
            throw new IllegalArgumentException("La lista de matriculados no puede contener elementos nulos.");
        }
        this.matriculados = new ArrayList<>(matriculados);
    }


    public void agregarCurso(CursoPosgrado curso) {
        if (curso == null) {
            throw new IllegalArgumentException("El curso no puede ser nulo.");
        }

        for (CursoPosgrado c : cursos) {
            if (c.getId() == curso.getId()) {
                throw new IllegalArgumentException("El curso ya está incluido en el plan.");
            }
        }

        cursos.add(curso);
    }


    public void matricular(Docente docente) {
        if (docente == null) {
            throw new IllegalArgumentException("El docente no puede ser nulo.");
        }

        for (Docente d : matriculados) {
            if (d.getId() == docente.getId()) {
                throw new IllegalArgumentException("El docente ya está matriculado.");
            }
        }

        matriculados.add(docente);
    }


    public int calcularCreditosTotales() {
        int total = 0;

        for (CursoPosgrado curso : cursos) {
            total += curso.getCreditos();
        }

        return total;
    }


    public boolean puedeDefender(Docente matriculado) {
        if (matriculado == null) {
            throw new IllegalArgumentException("El docente no puede ser nulo.");
        }

        boolean estaMatriculado = false;

        for (Docente docente : matriculados) {
            if (docente.getId() == matriculado.getId()) {
                estaMatriculado = true;
            }
        }

        int creditosObtenidos = 0;

        if (estaMatriculado) {
            for (CursoPosgrado curso : cursos) {
                for (InscripcionCurso inscripcion : curso.getInscripciones()) {
                    if (inscripcion.getParticipante().getId() == matriculado.getId()) {
                        creditosObtenidos += inscripcion.getCreditosObtenidos();
                    }
                }
            }
        }

        return estaMatriculado && creditosObtenidos >= calcularCreditosTotales();
    }
}
