package dao;
import model.Usuario;
import java.sql.*;
import java.util.*;

public class ImplDAOMySQL implements UsuarioDAO {
    private Connection con;

    public ImplDAOMySQL(Connection con) {
        this.con = con;
    }

    @Override
    public void guardar(Usuario u) {
        try {
            String sql = "INSERT INTO usuarios(nombres, apellidos, area, fechaNacimiento, fechaIngreso, fechaFin) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombres());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getArea());
            ps.setDate(4, new java.sql.Date(u.getFechaNacimiento().getTime()));
            ps.setDate(5, new java.sql.Date(u.getFechaIngreso().getTime()));
            if (u.getFechaFin() != null) {
                ps.setDate(6, new java.sql.Date(u.getFechaFin().getTime()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Usuario obtener(int id) {
        Usuario u = null;
        try {
            String sql = "SELECT * FROM usuarios WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("area"),
                        rs.getDate("fechaNacimiento"),
                        rs.getDate("fechaIngreso"),
                        rs.getDate("fechaFin")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public void editar(Usuario u) {
        try {
            String sql = "UPDATE usuarios SET nombres=?, apellidos=?, area=?, fechaNacimiento=?, fechaIngreso=?, fechaFin=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombres());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getArea());
            ps.setDate(4, new java.sql.Date(u.getFechaNacimiento().getTime()));
            ps.setDate(5, new java.sql.Date(u.getFechaIngreso().getTime()));
            if (u.getFechaFin() != null) {
                ps.setDate(6, new java.sql.Date(u.getFechaFin().getTime()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            ps.setInt(7, u.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        try {
            String sql = "DELETE FROM usuarios WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Usuario> buscar(String criterio) {
        List<Usuario> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM usuarios WHERE nombres LIKE ? OR apellidos LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + criterio + "%");
            ps.setString(2, "%" + criterio + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("area"),
                        rs.getDate("fechaNacimiento"),
                        rs.getDate("fechaIngreso"),
                        rs.getDate("fechaFin")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM usuarios";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("area"),
                        rs.getDate("fechaNacimiento"),
                        rs.getDate("fechaIngreso"),
                        rs.getDate("fechaFin")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
