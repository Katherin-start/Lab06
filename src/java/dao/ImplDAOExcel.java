package dao;

import model.Usuario;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;

public class ImplDAOExcel {

    private String excelPath;

    public ImplDAOExcel(String excelPath) {
        this.excelPath = excelPath;
    }

    public List<Usuario> listarHistoricos() {
        List<Usuario> lista = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(excelPath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            rows.next(); // saltar cabecera
            while (rows.hasNext()) {
                Row row = rows.next();
                Usuario u = new Usuario();
                u.setNombres(row.getCell(0).getStringCellValue());
                u.setApellidos(row.getCell(1).getStringCellValue());
                u.setArea(row.getCell(2).getStringCellValue());
                u.setFechaIngreso(row.getCell(3).getDateCellValue());
                u.setFechaFin(row.getCell(4).getDateCellValue());
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
