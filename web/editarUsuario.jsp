<%@ page import="model.Usuario" %>
<html>
<head>
    <title>Editar Usuario</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 flex items-center justify-center min-h-screen">
    <div class="bg-white shadow-md rounded-lg p-8 w-full max-w-md">
        <h2 class="text-2xl font-bold text-center mb-6">Editar Usuario</h2>
        <%
            Usuario u = (Usuario) request.getAttribute("usuario");
        %>
        <form action="UsuarioServlet?action=actualizar" method="post" class="space-y-4">
            <input type="hidden" name="id" value="<%=u.getId()%>">
            <div>
                <label class="block font-semibold">Nombres</label>
                <input type="text" name="nombres" value="<%=u.getNombres()%>" class="w-full border border-gray-300 rounded-md p-2">
            </div>
            <div>
                <label class="block font-semibold">Apellidos</label>
                <input type="text" name="apellidos" value="<%=u.getApellidos()%>" class="w-full border border-gray-300 rounded-md p-2">
            </div>
            <div>
                <label class="block font-semibold">Área</label>
                <input type="text" name="area" value="<%=u.getArea()%>" class="w-full border border-gray-300 rounded-md p-2">
            </div>
            <div>
                <label class="block font-semibold">Fecha Nacimiento</label>
                <input type="date" name="fechaNacimiento" value="<%=u.getFechaNacimiento()%>" class="w-full border border-gray-300 rounded-md p-2">
            </div>
            <div>
                <label class="block font-semibold">Fecha Ingreso</label>
                <input type="date" name="fechaIngreso" value="<%=u.getFechaIngreso()%>" class="w-full border border-gray-300 rounded-md p-2">
            </div>
            <div>
                <label class="block font-semibold">Fecha Fin</label>
                <input type="date" name="fechaFin" value="<%=u.getFechaFin()%>" class="w-full border border-gray-300 rounded-md p-2">
            </div>
            <input type="submit" value="Actualizar" class="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700">
        </form>
        <a href="UsuarioServlet?action=listar" class="block text-center text-blue-600 mt-4 hover:underline">Volver a lista</a>
    </div>
</body>
</html>
