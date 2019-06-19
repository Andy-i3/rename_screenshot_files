package rushydro.ru;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RenamefilesTest {

    @Test
    public void renameOnefile() {

        String result = Renamefiles.RenameOnefile( "загорская гаэс-2 (строительная площадка 1) 17_05_2019 11.00.00.jpg" );
        String parse = "17-05-2019 11-00 Загорская гаэс-2 строительная площадка 1.jpg";
        assertEquals( result, parse );

    }
}