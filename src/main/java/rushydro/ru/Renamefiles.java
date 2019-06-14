package rushydro.ru;

//  @author Golubkov Andrey 12-07-2018 ver 1.0 beta
//  Переименование файлов
// @version 0.1
// @sin 0.1


// import com.sun.org.apache.xpath.internal.operations.String;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.List;
import java.lang.*;

public class Renamefiles {

    private static String s2;
    private static int i;
    private static String path = null;

    public static void main(String[] args) {

        String str = args[0];

        String[] pArray = null;

        if (str.length() > 0) {
            pArray = str.split("\\\\");
            path = str;
        } else {
            System.out.println( "No directory with files to process" );
            System.exit( 0 );
        }

        List<File> files = scanNameFiles( path , pArray.length);
        new Renamefiles( files );
        System.out.println( "Successfully completed" );
    }

    // Сканирование директории
    // @return - список файлов
     public static List<File> scanNameFiles (String path, int i) {
         List <File> files = null;
         try {
             files = Files.walk( Paths.get( path ), i ).filter( Files::isRegularFile ).map( Path::toFile ).collect( Collectors.toList() );
         }
         // Обработка ошибок
         catch (StringIndexOutOfBoundsException sio) {
             System.out.println( "Invalid file format: " + s2 );
             System.exit( 0 );
         } catch (Exception ex) {
             System.out.println( "Error: " + ex );
             System.exit( 0 );
         }
         return files;
     }

   // фильтрация и переименование файлов по маске .00. и JPG
     private Renamefiles(List <File> collect ) {

       // Цикл по коллекции и модефикация названия файлов
       for (File file : collect) {
           String s = file.getAbsolutePath();
           s2 = file.getName();
           String[] pars = s.split( "\\\\" );
           if ((s2.replaceAll( "^.*\\.(.*)$", "$1" ).equals( "jpg" ))) {
               if ((s2.substring( s2.length() - 10, s2.length() - 6 )).equals( ".00." )) {
                   String s3 = s2.substring( pars[i].length() );
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
                   String s5 = s4 + " " + s3 + ".jpg";
                   System.out.println( path + "\\" + pars[i] + "\\" + s5 );
                   boolean b = file.renameTo( new File( path + "\\" + pars[i] + "\\" + s5 ) ); // Переименование файлов
               }
           }
       }


   }
}

