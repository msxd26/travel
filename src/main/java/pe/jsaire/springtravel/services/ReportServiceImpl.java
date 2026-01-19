package pe.jsaire.springtravel.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pe.jsaire.springtravel.models.entity.CustomerEntity;
import pe.jsaire.springtravel.repositories.CustomerRepository;
import pe.jsaire.springtravel.services.abstract_service.IReportService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements IReportService {

    private final CustomerRepository customerRepository;

    /*
    private static final String SHEET_NAME = "Customer total sales";
    private static final String FONT_TYPE = "Arial";
    private static final String COLUMN_CUSTOMER_ID = "id";
    private static final String COLUMN_CUSTOMER_NAME = "name";
    private static final String COLUMN_CUSTOMER_PURCHASES = "purchases";
    private static final String REPORTS_PATH_WITH_NAME = "reports/Sales-%s";
    private static final String REPORTS_PATH = "reports";
    private static final String FILE_TYPE = ".xlsx";
    private static final String FILE_NAME = "Sales-%s.xlsx";*/

    private static final String SHEET_NAME = "Customer total sales";
    private static final String FONT_TYPE = "Arial";
    private static final String[] HEADERS = {"id", "name", "purchases"};
    private static final int[] COLUMN_WIDTHS = {5000, 7000, 3000};

    @Override
    public byte[] getReport() {
        try (var workbook = new XSSFWorkbook();
             var out = new ByteArrayOutputStream()) {

            var sheet = workbook.createSheet(SHEET_NAME);

            // Configurar anchos de columna
            for (int i = 0; i < COLUMN_WIDTHS.length; i++) {
                sheet.setColumnWidth(i, COLUMN_WIDTHS[i]);
            }

            // Crear estilos una sola vez
            var headerStyle = createHeaderStyle(workbook);
            var dataStyle = createDataStyle(workbook);

            // Crear Header
            var headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                var cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
                cell.setCellStyle(headerStyle);
            }

            // Llenar datos
            var customers = this.customerRepository.findAll();
            int rowPos = 1;

            for (CustomerEntity customer : customers) {
                var row = sheet.createRow(rowPos++);
                createCell(row, 0, customer.getDni(), dataStyle);
                createCell(row, 1, customer.getFullName(), dataStyle);
                createCell(row, 2, String.valueOf(getTotalPurchases(customer)), dataStyle);
            }

            // Escribir a memoria
            workbook.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            log.error("Error generating Excel report", e);
            throw new RuntimeException("Error generating report", e);
        }
    }

    // Metodo helper para crear celdas más limpio
    private void createCell(Row row, int colIndex, String value, CellStyle style) {
        var cell = row.createCell(colIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        var style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        var font = workbook.createFont();
        font.setFontName(FONT_TYPE);
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);

        return style;
    }

    private CellStyle createDataStyle(Workbook workbook) {
        var style = workbook.createCellStyle();
        style.setWrapText(true);
        return style;
    }

    private static int getTotalPurchases(CustomerEntity customer) {
        return customer.getTotalFlights() + customer.getTotalLodgings() + customer.getTotalTours();
    }

}
