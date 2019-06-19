package rushydro.ru;

//  @author Golubkov Andrey 12-07-2018 ver 1.0 beta
//  Переименование файлов
// @version 0.2
// @sin 0.1

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Renamefiles {

    private static String currentNameFile;
    private static int deep_scan;
    private static String path = null;


    public static void main(String[] args) {
        String str = args[0];
        if (str.length() > 0) {
            String[] pArray = str.split("\\\\");
            path = str;
            deep_scan = pArray.length; // Глубина сканирования
        } else {
            System.out.println( "No directory with files to process" );
            System.exit( 0 );
        }
        new Renamefiles( scanNameFiles( path ));
        System.out.println( "Successfully completed" );
    }

    // Сканирование директории
    // @return - Возвращает список файлов
    private static List<File> scanNameFiles (String path) {
        List <File> files = null;
        try {
            files = Files.walk( Paths.get( path ), deep_scan ).filter( Files::isRegularFile ).map( Path::toFile ).collect( Collectors.toList() );
        }
        // Обработка ошибок
        catch (StringIndexOutOfBoundsException sio) {
            System.out.println( "Invalid file format: " + currentNameFile );
            System.exit( 0 );
        } catch (Exception ex) {
            System.out.println( "Error: " + ex );
            System.exit( 0 );
        }
        return files;
    }


    private static String Renamefile (String s, String currentNameFile, int pars_length) {

                String s3 = currentNameFile.substring( pars_length );
                s3 = s3.replace( "(", "" );
                s3 = s3.replace( ")", "" );
                s3 = s3.replaceAll( "^\\s+", "" ); // Удалить пробелы в начале строки
                s3 = s3.substring( 0, 1 ).toUpperCase() + s3.substring( 1 );
                String s4 = (s3.substring( s3.length() - 23 ));
                s4 = s4.replace( ".jpg", "" );
                s4 = s4.replace( ".", "_" );
                s4 = s4.replace( "_", "-" );
                s4 = s4.substring( 0, 16 );
                s3 = s3.substring( 0, s3.length() - 23 );
                s3 = s3.replaceAll( "\\s+$", "" );  // Удалить пробелы в конце строки
                return s4 + " " + s3 + ".jpg";;

    }

    // фильтрация и переименование файлов по маске .00. и JPG
    private Renamefiles(List <File> collect ) {

        // Цикл по коллекции и модефикация названия файлов
        for (File file : collect) {
            String s = file.getAbsolutePath();
            currentNameFile = file.getName();
            String[] pars = s.split( "\\\\" );

        if((currentNameFile.replaceAll("^.*\\.(.*)$","$1").equals("jpg"))){
         if((currentNameFile.substring(currentNameFile.length()-10,currentNameFile.length()-6)).equals(".00.")){
                 String filename=Renamefile(s,currentNameFile,pars[deep_scan].length());
             }
        }


            System.out.println( path + "\\" + pars[deep_scan] + "\\" + filename );
            try {
                boolean b = file.renameTo( new File( path + "\\" + pars[deep_scan] + "\\" + filename ) ); // Переименование файлов
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

