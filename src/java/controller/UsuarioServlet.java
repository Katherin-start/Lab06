package controller;

import dao.ImplDAOMySQL;
import dao.UsuarioDAO;
import model.Usuario;
import config.MySQLConexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        try {
            // ✅ Conexión a MySQL usando la clase de config
            Connection con = MySQLConexion.getConexion();
            usuarioDAO = new ImplDAOMySQL(con);
        } catch (Exception e) {
            throw new ServletException("Error iniciando conexión a BD", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "listar";

        try {
            switch (action) {
                case "listar":
                    listar(request, response);
                    break;
                case "editar":
                    mostrarFormularioEditar(request, response);
                    break;
                case "eliminar":
                    eliminar(request, response);
                    break;
                case "historicos":
                    mostrarHistoricos(request, response);
                    break;
                default:
                    listar(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "guardar":
                    guardar(request, response);
                    break;
                case "actualizar":
                    actualizar(request, response);
                    break;
                default:
                    listar(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // 🔹 LISTAR
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<Usuario> usuarios = usuarioDAO.listar();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("listarUsuarios.jsp").forward(request, response);
    }

    // 🔹 MOSTRAR FORMULARIO EDITAR
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        Usuario usuario = usuarioDAO.obtener(id);
        request.setAttribute("usuario", usuario);
        request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
    }

    // 🔹 GUARDAR NUEVO
    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Usuario u = new Usuario();
        u.setNombres(request.getParameter("nombres"));
        u.setApellidos(request.getParameter("apellidos"));
        u.setArea(request.getParameter("area"));
        u.setFechaNacimiento(sdf.parse(request.getParameter("fechaNacimiento")));
        u.setFechaIngreso(sdf.parse(request.getParameter("fechaIngreso")));

        String fechaFin = request.getParameter("fechaFin");
        if (fechaFin != null && !fechaFin.isEmpty()) {
            u.setFechaFin(sdf.parse(fechaFin));
        }

        usuarioDAO.guardar(u);
        response.sendRedirect("UsuarioServlet?action=listar");
    }

    // 🔹 ACTUALIZAR
    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int id = Integer.parseInt(request.getParameter("id"));
        Usuario u = usuarioDAO.obtener(id);

        u.setNombres(request.getParameter("nombres"));
        u.setApellidos(request.getParameter("apellidos"));
        u.setArea(request.getParameter("area"));
        u.setFechaNacimiento(sdf.parse(request.getParameter("fechaNacimiento")));
        u.setFechaIngreso(sdf.parse(request.getParameter("fechaIngreso")));

        String fechaFin = request.getParameter("fechaFin");
        if (fechaFin != null && !fechaFin.isEmpty()) {
            u.setFechaFin(sdf.parse(fechaFin));
        } else {
            u.setFechaFin(null);
        }

        usuarioDAO.editar(u);
        response.sendRedirect("UsuarioServlet?action=listar");
    }

    // 🔹 ELIMINAR
    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        usuarioDAO.eliminar(id);
        response.sendRedirect("UsuarioServlet?action=listar");
    }

    // 🔹 HISTÓRICOS (Excel)
    private void mostrarHistoricos(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // Aquí llamarías a tu ImplDAOExcel para leer datos históricos con Apache POI
        // Ejemplo:
        // ImplDAOExcel daoExcel = new ImplDAOExcel("C:/ruta/usuarios_historicos.xlsx");
        // List<Usuario> historicos = daoExcel.listarHistoricos();
        // request.setAttribute("historicos", historicos);

        request.getRequestDispatcher("historicos.jsp").forward(request, response);
    }
}
