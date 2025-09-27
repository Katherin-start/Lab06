<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Usuario" %>
<html>
<head>
    <title>Lista de Usuarios</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 p-8">
    <div class="max-w-6xl mx-auto bg-white shadow-md rounded-lg p-6">
        <h2 class="text-2xl font-bold mb-6 text-center">Usuarios Registrados</h2>

        <div class="flex justify-between mb-4">
            <a href="registrarUsuario.jsp" class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">Registrar</a>
            <a href="UsuarioServlet?action=historicos" class="bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700">Ver Históricos</a>
        </div>

        <table class="min-w-full border border-gray-300">
            <thead class="bg-gray-200">
                <tr>
                    <th class="px-4 py-2">ID</th>
                    <th class="px-4 py-2">Nombres</th>
                    <th class="px-4 py-2">Apellidos</th>
                    <th class="px-4 py-2">Área</th>
                    <th class="px-4 py-2">Nacimiento</th>
                    <th class="px-4 py-2">Ingreso</th>
                    <th class="px-4 py-2">Fin</th>
                    <th class="px-4 py-2">Acciones</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<Usuario> lista = (List<Usuario>) request.getAttribute("usuarios");
                if(lista != null){
                    for(Usuario u : lista){
            %>
                <tr class="border-b">
                    <td class="px-4 py-2"><%=u.getId()%></td>
                    <td class="px-4 py-2"><%=u.getNombres()%></td>
                    <td class="px-4 py-2"><%=u.getApellidos()%></td>
                    <td class="px-4 py-2"><%=u.getArea()%></td>
                    <td class="px-4 py-2"><%=u.getFechaNacimiento()%></td>
                    <td class="px-4 py-2"><%=u.getFechaIngreso()%></td>
                    <td class="px-4 py-2"><%=u.getFechaFin()%></td>
                    <td class="px-4 py-2">
                        <a href="UsuarioServlet?action=editar&id=<%=u.getId()%>" class="bg-blue-600 text-white px-2 py-1 rounded hover:bg-blue-700">Editar</a>
                        <a href="UsuarioServlet?action=eliminar&id=<%=u.getId()%>" class="bg-red-600 text-white px-2 py-1 rounded hover:bg-red-700">Eliminar</a>
                    </td>
                </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>
</body>
</html>
