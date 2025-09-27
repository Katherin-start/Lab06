package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Usuario;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class ImplDAOMongoDB implements UsuarioDAO {
    private final MongoCollection<Document> collection;

    public ImplDAOMongoDB(MongoDatabase db) {
        this.collection = db.getCollection("usuarios");
    }

    private Usuario docToUsuario(Document doc) {
        Usuario u = new Usuario();
        u.setId(doc.getInteger("id"));
        u.setNombres(doc.getString("nombres"));
        u.setApellidos(doc.getString("apellidos"));
        u.setArea(doc.getString("area"));
        u.setFechaNacimiento(doc.getDate("fechaNacimiento"));
        u.setFechaIngreso(doc.getDate("fechaIngreso"));
        u.setFechaFin(doc.getDate("fechaFin"));
        return u;
    }

    @Override
    public void guardar(Usuario u) {
        Document doc = new Document("id", u.getId())
                .append("nombres", u.getNombres())
                .append("apellidos", u.getApellidos())
                .append("area", u.getArea())
                .append("fechaNacimiento", u.getFechaNacimiento())
                .append("fechaIngreso", u.getFechaIngreso())
                .append("fechaFin", u.getFechaFin());
        collection.insertOne(doc);
    }

    @Override
    public Usuario obtener(int id) {
        Document doc = collection.find(eq("id", id)).first();
        return doc != null ? docToUsuario(doc) : null;
    }

    @Override
    public void editar(Usuario u) {
        Document update = new Document("$set", new Document()
                .append("nombres", u.getNombres())
                .append("apellidos", u.getApellidos())
                .append("area", u.getArea())
                .append("fechaNacimiento", u.getFechaNacimiento())
                .append("fechaIngreso", u.getFechaIngreso())
                .append("fechaFin", u.getFechaFin()));
        collection.updateOne(eq("id", u.getId()), update);
    }

    @Override
    public void eliminar(int id) {
        collection.deleteOne(eq("id", id));
    }

    @Override
    public List<Usuario> buscar(String criterio) {
        List<Usuario> lista = new ArrayList<>();

        // 🔹 Filtro: buscar en nombres o apellidos, insensible a mayúsculas/minúsculas
        Document filtro = new Document("$or", List.of(
                new Document("nombres", new Document("$regex", criterio).append("$options", "i")),
                new Document("apellidos", new Document("$regex", criterio).append("$options", "i"))
        ));

        for (Document doc : collection.find(filtro)) {
            lista.add(docToUsuario(doc));
        }
        return lista;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        for (Document doc : collection.find()) {
            lista.add(docToUsuario(doc));
        }
        return lista;
    }
}
