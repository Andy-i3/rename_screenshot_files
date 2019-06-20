package rushydro.ru;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RenamefilesTest {

    @Test
    public void renameOnefile_FullPatch() {
        String result = Renamefiles.RenameOnefile( "загорская гаэс-2 (строительная площадка 2) 30_11_2018 09.23.12.jpg", 16 );
        String parse = "30-11-2018 09-23 Строительная площадка 2.jpg";
        assertEquals( result, parse );
    }

    @Test
    public void renameOnefileShortPatch() {
        String result = Renamefiles.RenameOnefile( "загорская гаэс-2 (строительная площадка 1) 17_05_2019 11.00.00.jpg", 0 );
        String parse = "17-05-2019 11-00 Загорская гаэс-2 строительная площадка 1.jpg";
        assertEquals( result, parse );
    }
}