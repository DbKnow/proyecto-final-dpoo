package logica;

import java.util.ArrayList;
import java.util.List;

public class Departamento {

    private int id;
    private String nombre;
    private ArrayList<Docente> docentes = new ArrayList<>();


    public Departamento(int id, String nombre) {
        setId(id);
        setNombre(nombre);
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


    // ---------- docentes ----------
    public List<Docente> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<Docente> docentes) {
        if (docentes == null) {
            throw new IllegalArgumentException("La lista de docentes no puede ser nula.");
        }
        if (docentes.contains(null)) {
            throw new IllegalArgumentException("La lista de docentes no puede contener elementos nulos.");
        }
        this.docentes = new ArrayList<>(docentes);
    }


    public void agregarDocente(Docente docente) {
        if (docente == null) {
            throw new IllegalArgumentException("El docente no puede ser nulo.");
        }

        boolean existe = false;

        for (Docente d : docentes) {
            if (d.getId() == docente.getId()) {
                existe = true;
            }
        }

        if (existe) {
            throw new IllegalArgumentException("El docente ya pertenece a este departamento.");
        }

        docentes.add(docente);
    }


    public void eliminarDocente(Docente docente) {
        if (docente == null) {
            throw new IllegalArgumentException("El docente no puede ser nulo.");
        }

        boolean eliminado = false;

        for (int i = 0; i < docentes.size(); i++) {
            if (docentes.get(i).getId() == docente.getId()) {
                docentes.remove(i);
                eliminado = true;
                break;
            }
        }

        if (!eliminado) {
            throw new IllegalArgumentException("El docente no pertenece a este departamento.");
        }
    }


    public int calcularPuntaje(int anio) {
        //todo
        throw new UnsupportedOperationException("NO IMPLEMENTADO");
    }
}
