package rushydro.ru;

//  @author Golubkov Andrey 12-07-2018
//  Переименование файлов
// @version 1.3
// @sin 0.1




import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Renamefiles {

    // Переименование файлов
    private Renamefiles(List <File> collect) {

        // Цикл по коллекции и модефикация названия файлов
        for (File file : collect) {
            String currentNameFile = file.getName();
            // фильтрация и переименование файлов по маске .00. и JPG
            if ((currentNameFile.replaceAll( "^.*\\.(.*)$", "$1" ).equals( "jpg" ))) {
                if ((currentNameFile.substring( currentNameFile.length() - 9, currentNameFile.length() - 7 )).matches( ".*[0-9].*[0-9]" )) {

                    String currentNamePatch = file.getAbsolutePath();
                    currentNamePatch = currentNamePatch.substring( 0, currentNamePatch.length() - currentNameFile.length() - 1 );
                    String[] pars = currentNamePatch.split( "\\\\" );

                    String filename = null;
                    String f1 = pars[pars.length - 1];
                    if ((currentNameFile.substring( 0, f1.length() ).toLowerCase().equals( f1.toLowerCase() ))) {
                         filename = RenameOnefile( currentNameFile, f1.length() );
                    } else {
                         filename = RenameOnefile( currentNameFile, 0 );
                    }

                    try {

                        // boolean b = file.renameTo( new File( currentNamePatch  + "\\" + filename  ) ); // Переименование файлов
                        System.out.println( currentNamePatch + "\\" + filename ); // Переименование файлов
                    }
                    // Обработка ошибок
                    catch (StringIndexOutOfBoundsException sio) {
                        System.out.println( "Invalid file format: " + currentNameFile );
                        System.exit( 0 );
                    } catch (Exception ex) {
                        System.out.println( "Error: " + ex );
                        System.exit( 0 );
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            String str = args[0];
            String pathToDirectory = null;
            String[] pArray = str.split( "\\\\" );
            pathToDirectory = str; // Путь к директории с файлами
            new Renamefiles( scanNameFiles( pathToDirectory, pArray.length ) );
            System.out.println( "Successfully completed" );
        } catch (Exception e) {
            System.out.println( "The path to the file directory is not defined." );
        }
    }

    // Сканирование директории
    // deep_scan; - Глубина сканирования директории
    // @return - Возвращает список файлов с директориями (полный путь)
    public static List <File> scanNameFiles(String path, int deep_scan) {
        List <File> files = null;
        try {
            files = Files.walk( Paths.get( path ), deep_scan ).filter( Files::isRegularFile ).map( Path::toFile ).collect( Collectors.toList() );
        }
        // Обработка ошибок
        catch (StringIndexOutOfBoundsException sio) {
            System.out.println( "Invalid file format: " );
            System.exit( 0 );
        } catch (Exception ex) {
            System.out.println( "Error: " + ex );
            System.exit( 0 );
        }
        return files;
    }

    // Переименование имени файла
    // Input: загорская гаэс-2 (строительная площадка 1) 17_05_2019 11.00.00.jpg
    // Output: 17-05-2019 11-00 Загорская гаэс-2 строительная площадка 1.jpg
    // @return
    public static String RenameOnefile(String nameFile, int ColStr) {
        if (ColStr != 0 && ColStr>5) {
            nameFile = nameFile.substring( 0, ColStr );
            System.out.println( nameFile );
        }
        nameFile = nameFile.replace( "(", "" ); // Удалить скобки
        nameFile = nameFile.replace( ")", "" ); // Удалить скобки
        nameFile = nameFile.replaceAll( "^\\s+", "" ); // Удалить пробелы в начале строки
        nameFile = nameFile.substring( 0, 1 ).toUpperCase() + nameFile.substring( 1 ); // Регистр первой буквы в названии
        String s4 = (nameFile.substring( nameFile.length() - 23 ));
        s4 = s4.replace( ".jpg", "" );
        s4 = s4.replace( ".", "_" );
        s4 = s4.replace( "_", "-" );
        s4 = s4.substring( 0, 16 );
        nameFile = nameFile.substring( 0, nameFile.length() - 23 );
        nameFile = nameFile.replaceAll( "\\s+$", "" );  // Удалить пробелы в конце строки
        return s4 + " " + nameFile + ".jpg";
    }
}
