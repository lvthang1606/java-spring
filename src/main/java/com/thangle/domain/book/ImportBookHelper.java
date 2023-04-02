package com.thangle.domain.book;

import com.thangle.error.ForbiddenException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;

public class ImportBookHelper {

    // The Microsoft Office MIME type of Excel files
    final public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Book> extractDataFromInputStream(final InputStream inputStream, final UUID userId) {
        List<Book> books = new ArrayList<Book>();

        try {
            XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workBook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();

                // Skip headers
                if (currentRow.getRowNum() == 0) {
                    continue;
                }

                Book book = new Book(null, "", "", "", "", 0L, "", Instant.now(), null, "", 0.0, 0, 0.0, userId);

                book.setTitle(currentRow.getCell(1).getStringCellValue());
                book.setSubtitle(currentRow.getCell(2).getStringCellValue());
                book.setAuthor(currentRow.getCell(3).getStringCellValue());
                book.setPublisher(currentRow.getCell(4).getStringCellValue());
                book.setIsbn13((long) currentRow.getCell(5).getNumericCellValue());
                book.setDescription(currentRow.getCell(6).getStringCellValue());
                book.setImage(currentRow.getCell(9).getStringCellValue());
                book.setPrice(currentRow.getCell(10).getNumericCellValue());
                book.setYear((int) currentRow.getCell(11).getNumericCellValue());
                book.setRating(currentRow.getCell(12).getNumericCellValue());

                books.add(book);
            }
        } catch (IOException exception) {
            throw new ForbiddenException("Fails to read data from the stream");
        }

        return books;
    }
}
