package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {
    private int id;
    private String nombres;
    private String apellidos;
    private String area;
    private Date fechaNacimiento;
    private Date fechaIngreso;
    private Date fechaFin;

    // Constructor vacío
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(int id, String nombres, String apellidos, String area,
                   Date fechaNacimiento, Date fechaIngreso, Date fechaFin) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.area = area;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaIngreso = fechaIngreso;
        this.fechaFin = fechaFin;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Date getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Date fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    // Método para depuración
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", area='" + area + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaIngreso=" + fechaIngreso +
                ", fechaFin=" + fechaFin +
                '}';
    }

    // 🔎 Método buscador en lista de usuarios
    public static List<Usuario> buscarEnLista(List<Usuario> usuarios, String criterio) {
        List<Usuario> resultado = new ArrayList<>();
        String criterioLower = criterio.toLowerCase();

        for (Usuario u : usuarios) {
            if (u.getNombres().toLowerCase().contains(criterioLower) ||
                u.getApellidos().toLowerCase().contains(criterioLower) ||
                u.getArea().toLowerCase().contains(criterioLower)) {
                resultado.add(u);
            }
        }
        return resultado;
    }
}
