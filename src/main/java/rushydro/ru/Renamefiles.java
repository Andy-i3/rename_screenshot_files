package rushydro.ru;

//  Author Golubkov Andrey 12-07-2018 ver 1.0 beta
//  Переименование файлов

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Renamefiles {

    public static String s2;
    public static String path;
    public static int i;

    public static void main(String[] args) {

        // Проверка наличия аргументов - путь к папке с картинками

        if (args.length > 0) {
            String[] p = args[0].split( "\\\\" );  // Делим на части путь к файлу
            i = p.length; // Глубина сканирования
            path = args[0];
        } else {
            System.out.println( "No directory with files to process" );
            System.exit( 0 );
        }
        try {

            // Сканирование директории и фильтрация файлов по маске .00. и JPG
            List <File> collect = Files.walk( Paths.get( path ), i ).filter( Files::isRegularFile ).map( Path::toFile ).collect( Collectors.toList() );
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
        // Обработка ошибок
        catch (StringIndexOutOfBoundsException sio) {
            System.out.println( "Invalid file format: " + s2 );
            System.exit( 0 );
        } catch (Exception ex) {
            System.out.println( "Error: " + ex );
            System.exit( 0 );
        }
        System.out.println( "Successfully completed" );
    }
}

