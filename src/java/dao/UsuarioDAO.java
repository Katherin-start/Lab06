package dao;

import model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    void guardar(Usuario u);
    Usuario obtener(int id);
    void editar(Usuario u);
    void eliminar(int id);
    List<Usuario> buscar(String criterio);
    List<Usuario> listar();
}
