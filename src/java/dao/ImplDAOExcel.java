package dao;

import model.Usuario;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ImplDAOExcel {

    private final String excelPath;

    public ImplDAOExcel(String excelPath) {
        this.excelPath = excelPath;
    }

    public List<Usuario> listarHistoricos() {
        List<Usuario> lista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(excelPath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

           
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Usuario u = new Usuario();

                u.setId((int) row.getCell(0).getNumericCellValue());
                u.setNombres(row.getCell(1).getStringCellValue());
                u.setApellidos(row.getCell(2).getStringCellValue());
                u.setArea(row.getCell(3).getStringCellValue());

   
                Cell cellNacimiento = row.getCell(4);
                if (cellNacimiento != null && cellNacimiento.getCellType() == CellType.NUMERIC) {
                    u.setFechaNacimiento(cellNacimiento.getDateCellValue());
                }

                Cell cellIngreso = row.getCell(5);
                if (cellIngreso != null && cellIngreso.getCellType() == CellType.NUMERIC) {
                    u.setFechaIngreso(cellIngreso.getDateCellValue());
                }

                Cell cellFin = row.getCell(6);
                if (cellFin != null && cellFin.getCellType() == CellType.NUMERIC) {
                    u.setFechaFin(cellFin.getDateCellValue());
                }

                lista.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
