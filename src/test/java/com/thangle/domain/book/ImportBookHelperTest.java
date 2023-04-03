package com.thangle.domain.book;

import com.thangle.error.ForbiddenException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.io.InputStream;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ImportBookHelperTest {

    @Test
    void shouldExtractDataFromInputStream_ThrowsForbiddenException() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("import.xlsx");

        assert inputStream != null;
        XSSFWorkbook workBook = new XSSFWorkbook(inputStream);

        workBook.close();

        assertThrows(ForbiddenException.class, () -> ImportBookHelper.extractDataFromInputStream(inputStream, randomUUID()));
    }
}
