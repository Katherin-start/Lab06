package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Usuario;
import org.bson.Document;
import org.bson.types.ObjectId;

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

    
        if (doc.containsKey("_id")) {
            u.setMongoId(doc.getObjectId("_id")); 
        }

  
        Integer id = doc.getInteger("id", 0);
        u.setId(id);

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
        Document doc = new Document()
                .append("id", u.getId() != 0 ? u.getId() : null)
                .append("nombres", u.getNombres())
                .append("apellidos", u.getApellidos())
                .append("area", u.getArea())
                .append("fechaNacimiento", u.getFechaNacimiento())
                .append("fechaIngreso", u.getFechaIngreso())
                .append("fechaFin", u.getFechaFin());

        collection.insertOne(doc);

     
        ObjectId generatedId = doc.getObjectId("_id");
        u.setMongoId(generatedId);
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

        if (u.getId() != 0) {
            collection.updateOne(eq("id", u.getId()), update);
        } else if (u.getMongoId() != null) {
            collection.updateOne(eq("_id", u.getMongoId()), update);
        }
    }


    @Override
    public void eliminar(int id) {
        collection.deleteOne(eq("id", id));
    }

 
    public void eliminarPorMongoId(ObjectId mongoId) {
        collection.deleteOne(eq("_id", mongoId));
    }


    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        for (Document doc : collection.find()) {
            lista.add(docToUsuario(doc));
        }
        return lista;
    }


    @Override
    public List<Usuario> buscar(String criterio) {
        List<Usuario> lista = new ArrayList<>();
        Document filtro = new Document("$or", List.of(
                new Document("nombres", new Document("$regex", criterio).append("$options", "i")),
                new Document("apellidos", new Document("$regex", criterio).append("$options", "i"))
        ));
        for (Document doc : collection.find(filtro)) {
            lista.add(docToUsuario(doc));
        }
        return lista;
    }

 
    public List<Usuario> filtrarPorArea(String area) {
        List<Usuario> lista = new ArrayList<>();
        for (Document doc : collection.find(eq("area", area))) {
            lista.add(docToUsuario(doc));
        }
        return lista;
    }
}
