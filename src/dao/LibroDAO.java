package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import modelo.Libro;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibroDAO {
    private EntityManagerFactory emf;
    public LibroDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void insertarLibro (Libro l) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();
        em.close();
    }

    public void borrarLibro (int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Libro l = em.find(Libro.class, id);
        if (l != null) {
            em.remove(l);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Libro obtenerLibro(int id) {
        EntityManager em = emf.createEntityManager();
        Libro l = em.find(Libro.class, id);
        em.close();
        return l;
    }

    public List<Libro> obtenerTotalidadLibros() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l", Libro.class);
        List<Libro> res = query.getResultList();
        em.close();
        return res;
    }

    public List<Libro> obtenerLibrosPorGenero(String genero) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l WHERE l.genero = :genero", Libro.class);
        query.setParameter("genero", genero);
        List<Libro> res = query.getResultList();
        em.close();
        return res;
    }

    public List<Libro> obtenerLibrosConMenosDe3Ejemplares() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l WHERE l.ejemplaresDisponibles < 3", Libro.class);
        List<Libro> res = query.getResultList();
        em.close();
        return res;
    }

    public List<Libro> obtenerLibrosPublicadosDesde2000() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l WHERE l.anyoPublicacion >= 2000", Libro.class);
        List<Libro> res = query.getResultList();
        em.close();
        return res;
    }

    public List<Libro> obtenerLibrosPorAutor(String texto) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l WHERE l.autor LIKE :texto", Libro.class);
        query.setParameter("texto", "%" + texto + "%");
        List<Libro> res = query.getResultList();
        em.close();
        return res;
    }

    public List<Libro> obtener5LibrosMasCaros() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l ORDER BY l.precio DESC", Libro.class);
        query.setMaxResults(5);
        List<Libro> res = query.getResultList();
        em.close();
        return res;
    }

    public long obtenerNumeroDeLibrosDiferentes() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(l) FROM Libro l", Long.class);
        long total = query.getSingleResult();
        em.close();
        return total;
    }

    public double obtenerPrecioMedio() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Double> query = em.createQuery("SELECT AVG(l.precio) FROM Libro l", Double.class);
        double media = query.getSingleResult();
        em.close();
        return media;
    }

    public Libro obtenerLibroMasAntiguo() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l ORDER BY l.anyoPublicacion ASC", Libro.class);
        query.setMaxResults(1);
        Libro l = query.getSingleResult();
        em.close();
        return l;
    }

    public Map<String, Long> obtenerNumeroLibrosPorGenero() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Object[]> query = em.createQuery("SELECT l.genero, COUNT(l) FROM Libro l GROUP BY l.genero", Object[].class);
        List<Object[]> resultados = query.getResultList();
        Map<String, Long> generoMap = new HashMap<>();
        for (Object[] fila : resultados) {
            String genero = (String) fila[0];
            long cuenta = (long) fila[1];
            generoMap.put(genero, cuenta);
        }
        em.close();
        return generoMap;
    }

    public Map<String, Double> obtenerPrecioMedioDeCadaGenero() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Object[]> query = em.createQuery("SELECT l.genero, AVG(l.precio) FROM Libro l GROUP BY l.genero", Object[].class);
        List<Object[]> resultados = query.getResultList();
        Map<String, Double> generoMap = new HashMap<>();
        for (Object[] fila : resultados) {
            String genero = (String) fila[0];
            double media = (double) fila[1];
            generoMap.put(genero, media);
        }
        em.close();
        return generoMap;
    }

    public List<String> obtenerGenerosConMasDe100Ejemplares() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<String> query = em.createQuery("SELECT l.genero FROM Libro l GROUP BY l.genero HAVING SUM(l.ejemplaresDisponibles) > 100", String.class);
        List<String> res = query.getResultList();
        em.close();
        return res;
    }
}
