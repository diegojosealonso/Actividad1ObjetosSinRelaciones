import dao.LibroDAO;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.Libro;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Libros.odb");
        LibroDAO dao = new LibroDAO(emf);

        Libro l = new Libro("Cosita", "Alba G.Mora", " 9791387748289", 2025, "Realismo mágico", 19.99, 44, false);
        dao.insertarLibro(l);
        l.setId(1);
        System.out.println(dao.obtenerLibro(1));
        System.out.println(dao.obtenerTotalidadLibros());
        System.out.println(dao.obtenerLibrosPorGenero("Novela"));
        System.out.println(dao.obtenerLibrosConMenosDe3Ejemplares());
        System.out.println(dao.obtenerLibrosPublicadosDesde2000());
        System.out.println(dao.obtenerLibrosPorAutor("Cervantes"));
        System.out.println(dao.obtener5LibrosMasCaros());
        System.out.println(dao.obtenerNumeroDeLibrosDiferentes());
        System.out.println(dao.obtenerPrecioMedio());
        System.out.println(dao.obtenerLibroMasAntiguo());
        System.out.println(dao.obtenerNumeroLibrosPorGenero());
        System.out.println(dao.obtenerPrecioMedioDeCadaGenero());
        System.out.println(dao.obtenerGenerosConMasDe100Ejemplares());
        emf.close();
    }
}