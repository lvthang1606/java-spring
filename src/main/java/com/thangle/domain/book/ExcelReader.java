package com.thangle.domain.book;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@UtilityClass
public class ExcelReader {

    // The Microsoft Office MIME type of Excel files
    private static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Object[]> readTable(final XSSFSheet sheet) {
        final Iterator<Row> rowIterator = sheet.rowIterator();
        List<Object[]> objectList = new ArrayList<Object[]>();

        while (rowIterator.hasNext()) {
            final Row currentRow = rowIterator.next();
            final int numberOfCell = currentRow.getLastCellNum();
            final Object[] objects = new Object[numberOfCell];
            final Iterator<Cell> cellIterator = currentRow.cellIterator();

            // Skip headers
            if (currentRow.getRowNum() == 0) {
                continue;
            }

            while (cellIterator.hasNext()) {
                final Cell currentCell = cellIterator.next();

                objects[currentCell.getColumnIndex()] = getCurrentCellValue(currentCell);
            }

            objectList.add(objects);
        }
        return objectList;
    }

    private static Object getCurrentCellValue(final Cell currentCell) {
        return switch (currentCell.getCellType()) {
            case STRING -> currentCell.getStringCellValue();
            case NUMERIC -> BigDecimal.valueOf(currentCell.getNumericCellValue()).toPlainString();
            default -> null;
        };
    }
}
