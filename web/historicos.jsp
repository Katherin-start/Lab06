<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Usuario" %>
<html>
<head>
    <title>Usuarios Históricos</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 p-8">
    <div class="max-w-4xl mx-auto bg-white shadow-md rounded-lg p-6">
        <h2 class="text-2xl font-bold mb-6 text-center">Usuarios Históricos (Excel)</h2>
        <a href="UsuarioServlet?action=listar" class="bg-gray-600 text-white px-4 py-2 rounded hover:bg-gray-700 mb-4 inline-block">Volver a lista</a>

        <table class="min-w-full border border-gray-300">
            <thead class="bg-gray-200">
                <tr>
                    <th class="px-4 py-2">Nombres</th>
                    <th class="px-4 py-2">Apellidos</th>
                    <th class="px-4 py-2">Área</th>
                    <th class="px-4 py-2">Ingreso</th>
                    <th class="px-4 py-2">Fin</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<Usuario> historicos = (List<Usuario>) request.getAttribute("historicos");
                if(historicos != null){
                    for(Usuario u : historicos){
            %>
                <tr class="border-b">
                    <td class="px-4 py-2"><%=u.getNombres()%></td>
                    <td class="px-4 py-2"><%=u.getApellidos()%></td>
                    <td class="px-4 py-2"><%=u.getArea()%></td>
                    <td class="px-4 py-2"><%=u.getFechaIngreso()%></td>
                    <td class="px-4 py-2"><%=u.getFechaFin()%></td>
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
