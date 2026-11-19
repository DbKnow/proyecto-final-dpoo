package logica;

import java.util.ArrayList;
import java.util.List;

public class Docente extends Investigador {

    private CategoriaDocente categoriaDocente;
    private CategoriaCientifica categoriaCientifica;
    private ArrayList<InscripcionCurso> inscripciones;


    public Docente(int id, String nombre, CategoriaDocente categoriaDocente, CategoriaCientifica categoriaCientifica) {
        setId(id);
        super.setNombre(nombre);
        setCategoriaDocente(categoriaDocente);
        setCategoriaCientifica(categoriaCientifica);
        this.inscripciones = new ArrayList<InscripcionCurso>();
    }


    // ---------- id ----------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        super.setId(id);
    }


    // ---------- categoriaDocente ----------
    public CategoriaDocente getCategoriaDocente() {
        return categoriaDocente;
    }

    public void setCategoriaDocente(CategoriaDocente categoriaDocente) {
        if (categoriaDocente == null) {
            throw new IllegalArgumentException("La categoría docente no puede ser nula.");
        }
        this.categoriaDocente = categoriaDocente;
    }


    // ---------- categoriaCientifica ----------
    public CategoriaCientifica getCategoriaCientifica() {
        return categoriaCientifica;
    }

    public void setCategoriaCientifica(CategoriaCientifica categoriaCientifica) {
        this.categoriaCientifica = categoriaCientifica;
    }
    
    
    //---------- inscripciones ----------
    public List<InscripcionCurso> getInscripciones() {
        return inscripciones;
    }


    public void setInscripciones(List<InscripcionCurso> inscripciones) {
        if (inscripciones == null) {
            throw new IllegalArgumentException("La lista de inscripciones no puede ser nula.");
        }
        this.inscripciones = new ArrayList<InscripcionCurso>(inscripciones);
    }
    
    
    public void agregarInscripcion(InscripcionCurso inscripcion) {
        if (inscripcion == null) {
            throw new IllegalArgumentException("La inscripción no puede ser nula.");
        }
        inscripciones.add(inscripcion);
    }


    public boolean esDoctor() {
        return categoriaCientifica == CategoriaCientifica.DOCTOR;
    }


    public int getCreditosObtenidos(int anio) {
        int total = 0;

        for (InscripcionCurso inscripcion : inscripciones) {
            if (inscripcion.getCurso().getAnio() == anio) {
                total += inscripcion.getCreditosObtenidos();
            }
        }

        return total;
    }
}
