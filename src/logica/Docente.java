
package logica;

public class Docente extends Investigador {
    private CategoriaDocente categoriaDocente;
    private CategoriaCientifica categoriaCientifica;

    
    public Docente(int id,String nombre, CategoriaDocente categoriaDocente, CategoriaCientifica categoriaCientifica) {
        setId(id);
        super.setNombre(nombre);
        setCategoriaDocente(categoriaDocente);
        setCategoriaCientifica(categoriaCientifica);
        
    }
    
    
    public int getId() {
        return id;
    }
    

    public void setId(int id) {
        //todo
        throw new UnsupportedOperationException("NO IMPLEMENTADO");
    }
    

    public CategoriaDocente getCategoriaDocente() {
        return categoriaDocente;
    }
    

    public void setCategoriaDocente(CategoriaDocente categoriaDocente) {
        //todo
        throw new UnsupportedOperationException("NO IMPLEMENTADO");
    }
    

    public CategoriaCientifica getCategoriaCientifica() {
        return categoriaCientifica;
    }
    

    public void setCategoriaCientifica(CategoriaCientifica categoriaCientifica) {
        //todo
        throw new UnsupportedOperationException("NO IMPLEMENTADO");
    }
    

    public boolean esDoctor() {
        //todo
        throw new UnsupportedOperationException("NO IMPLEMENTADO");
    }
    

    public int getCreditosObtenidos(int anio) {
        //todo
        throw new UnsupportedOperationException("NO IMPLEMENTADO");
    }
}
