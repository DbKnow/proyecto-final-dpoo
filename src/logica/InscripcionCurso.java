package logica;

public class InscripcionCurso {

    private int id;
    private Docente participante;
    private double nota;
    private CursoPosgrado curso;


    public InscripcionCurso(int id, Docente participante, double nota, CursoPosgrado curso) {
        setId(id);
        setParticipante(participante);
        setNota(nota);
        setCurso(curso);
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


    // ---------- participante ----------
    public Docente getParticipante() {
        return participante;
    }

    public void setParticipante(Docente participante) {
        if (participante == null) {
            throw new IllegalArgumentException("El participante no puede ser nulo.");
        }
        this.participante = participante;
    }


    // ---------- nota ----------
    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        if (Double.isNaN(nota) || Double.isInfinite(nota) || nota < 0 || nota > 5) {
            throw new IllegalArgumentException("La nota debe estar entre 0 y 5.");
        }
        this.nota = nota;
    }
    
    
 // ---------- curso ----------
    public CursoPosgrado getCurso() {
        return curso;
    }
    

    public void setCurso(CursoPosgrado curso) {
        if (curso == null) {
            throw new IllegalArgumentException("El curso no puede ser nulo.");
        }
        this.curso = curso;
    }


    public boolean estaAprobada() {
        return nota >= 3;
    }


    public int getCreditosObtenidos() {
        if (estaAprobada()) {
            return curso.getCreditos();
        }

        return 0;
    }
}
