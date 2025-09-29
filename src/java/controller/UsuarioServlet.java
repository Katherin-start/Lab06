package controller;

import dao.ImplDAOMySQL;
import dao.ImplDAOMongoDB;
import dao.UsuarioDAO;
import model.Usuario;
import config.MySQLConexion;
import config.MongoConexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoDatabase;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    // ✅ Dos DAOs (uno MySQL y otro Mongo)
    private UsuarioDAO usuarioDAOMySQL;
    private UsuarioDAO usuarioDAOMongo;

    @Override
    public void init() throws ServletException {
        try {
            // 🔹 Conexión MySQL
            Connection con = MySQLConexion.getConexion();
            usuarioDAOMySQL = new ImplDAOMySQL(con);
            System.out.println("✅ Conectado a MySQL");

            // 🔹 Conexión MongoDB
            MongoDatabase db = MongoConexion.getConexion();
            usuarioDAOMongo = new ImplDAOMongoDB(db);
            System.out.println("✅ Conectado a MongoDB");

        } catch (Exception e) {
            throw new ServletException("Error iniciando conexiones a BD", e);
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
                case "exportarExcel":
                    exportarHistoricosExcel(request, response);
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

    // 🔹 LISTAR (aquí muestro los datos de MySQL y Mongo juntos)
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<Usuario> usuariosMySQL = usuarioDAOMySQL.listar();
        List<Usuario> usuariosMongo = usuarioDAOMongo.listar();

        // Combino los dos resultados
        usuariosMySQL.addAll(usuariosMongo);

        request.setAttribute("usuarios", usuariosMySQL);
        request.getRequestDispatcher("listarUsuarios.jsp").forward(request, response);
    }

    // 🔹 MOSTRAR FORMULARIO EDITAR
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));

        // Por defecto consulto en MySQL
        Usuario usuario = usuarioDAOMySQL.obtener(id);
        if (usuario == null) {
            usuario = usuarioDAOMongo.obtener(id); // Si no está en MySQL, busco en Mongo
        }

        request.setAttribute("usuario", usuario);
        request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
    }

    // 🔹 GUARDAR NUEVO (guardo en ambos: MySQL y Mongo)
    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Usuario u = new Usuario();
        u.setNombres(request.getParameter("nombres"));
        u.setApellidos(request.getParameter("apellidos"));
        u.setArea(request.getParameter("area"));

        String fnac = request.getParameter("fechaNacimiento");
        if (fnac != null && !fnac.isEmpty()) u.setFechaNacimiento(sdf.parse(fnac));

        String fing = request.getParameter("fechaIngreso");
        if (fing != null && !fing.isEmpty()) u.setFechaIngreso(sdf.parse(fing));

        String ffin = request.getParameter("fechaFin");
        if (ffin != null && !ffin.isEmpty()) u.setFechaFin(sdf.parse(ffin));

        // Guardo en MySQL y Mongo
        usuarioDAOMySQL.guardar(u);
        usuarioDAOMongo.guardar(u);

        response.sendRedirect("UsuarioServlet?action=listar");
    }

    // 🔹 ACTUALIZAR
    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int id = Integer.parseInt(request.getParameter("id"));
        Usuario u = usuarioDAOMySQL.obtener(id);
        if (u == null) {
            u = usuarioDAOMongo.obtener(id);
        }

        u.setNombres(request.getParameter("nombres"));
        u.setApellidos(request.getParameter("apellidos"));
        u.setArea(request.getParameter("area"));

        String fnac = request.getParameter("fechaNacimiento");
        if (fnac != null && !fnac.isEmpty()) u.setFechaNacimiento(sdf.parse(fnac));

        String fing = request.getParameter("fechaIngreso");
        if (fing != null && !fing.isEmpty()) u.setFechaIngreso(sdf.parse(fing));

        String ffin = request.getParameter("fechaFin");
        if (ffin != null && !ffin.isEmpty()) u.setFechaFin(sdf.parse(ffin));

        // Actualizo en ambas BD
        usuarioDAOMySQL.editar(u);
        usuarioDAOMongo.editar(u);

        response.sendRedirect("UsuarioServlet?action=listar");
    }

    // 🔹 ELIMINAR (elimino en ambas)
    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        usuarioDAOMySQL.eliminar(id);
        usuarioDAOMongo.eliminar(id);
        response.sendRedirect("UsuarioServlet?action=listar");
    }

    // 🔹 MOSTRAR HISTÓRICOS EN JSP
    private void mostrarHistoricos(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        List<Usuario> usuariosMySQL = usuarioDAOMySQL.listar();
        List<Usuario> usuariosMongo = usuarioDAOMongo.listar();
        usuariosMySQL.addAll(usuariosMongo);

        // Filtrar solo usuarios con fechaFin != null
        List<Usuario> historicos = new ArrayList<>();
        for (Usuario u : usuariosMySQL) {
            if (u.getFechaFin() != null) {
                historicos.add(u);
            }
        }

        request.setAttribute("historicos", historicos);
        request.getRequestDispatcher("historicos.jsp").forward(request, response);
    }

    // 🔹 EXPORTAR HISTÓRICOS A EXCEL
    private void exportarHistoricosExcel(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        List<Usuario> usuariosMySQL = usuarioDAOMySQL.listar();
        List<Usuario> usuariosMongo = usuarioDAOMongo.listar();
        usuariosMySQL.addAll(usuariosMongo);

        // Filtrar solo históricos
        List<Usuario> historicos = new ArrayList<>();
        for (Usuario u : usuariosMySQL) {
            if (u.getFechaFin() != null) {
                historicos.add(u);
            }
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios Historicos");

        // Encabezados
        String[] columnas = {"ID", "Nombres", "Apellidos", "Área", "Nacimiento", "Ingreso", "Fin"};
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
        }

        // Llenar filas
        int rowNum = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Usuario u : historicos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(u.getId());
            row.createCell(1).setCellValue(u.getNombres());
            row.createCell(2).setCellValue(u.getApellidos());
            row.createCell(3).setCellValue(u.getArea());
            row.createCell(4).setCellValue(u.getFechaNacimiento() != null ? sdf.format(u.getFechaNacimiento()) : "");
            row.createCell(5).setCellValue(u.getFechaIngreso() != null ? sdf.format(u.getFechaIngreso()) : "");
            row.createCell(6).setCellValue(u.getFechaFin() != null ? sdf.format(u.getFechaFin()) : "");
        }

        // Ajustar ancho automático
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Configurar la respuesta HTTP
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios_historicos.xlsx");

        OutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.close();
    }
}
