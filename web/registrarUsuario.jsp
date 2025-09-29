<html>
<head>
    <title>Registrar Usuario</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 flex items-center justify-center min-h-screen">
    <div class="bg-white shadow-md rounded-lg p-8 w-full max-w-md">
        <h2 class="text-2xl font-bold text-center mb-6">Registrar Usuario</h2>
        <form action="UsuarioServlet?action=guardar" method="post" class="space-y-4">
            <div>
                <label class="block font-semibold">Nombres</label>
                <input type="text" name="nombres" class="w-full border border-gray-300 rounded-md p-2">
            </div>
            <div>
                <label class="block font-semibold">Apellidos</label>
                <input type="text" name="apellidos" class="w-full border border-gray-300 rounded-md p-2">
            </div>
            <div>
                <label class="block font-semibold">Área</label>
                <input type="text" name="area" class="w-full border border-gray-300 rounded-md p-2">
            </div>
            <div>
                <label class="block font-semibold">Fecha Nacimiento</label>
                <input type="date" name="fechaNacimiento" class="w-full border border-gray-300 rounded-md p-2">
            </div>
            <div>
                <label class="block font-semibold">Fecha Ingreso</label>
                <input type="date" name="fechaIngreso" class="w-full border border-gray-300 rounded-md p-2">
            </div>
            <div>
                <label class="block font-semibold">Fecha Fin</label>
                <input type="date" name="fechaFin" class="w-full border border-gray-300 rounded-md p-2">
            </div>
            <input type="submit" value="Guardar" class="w-full bg-green-600 text-white py-2 rounded-md hover:bg-green-700">
        </form>
        <a href="UsuarioServlet?action=listar" class="block text-center text-blue-600 mt-4 hover:underline">Volver a lista</a>
    </div>
</body>
</html>
