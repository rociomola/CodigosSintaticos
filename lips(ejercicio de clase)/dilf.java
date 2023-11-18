import java.io.*;
import java.text.ParseException;
import java.util.Objects;

public class dilf {
    public static PrintStream out;
    private static Yylex lex;
    private static int actual;
    public static void main(String args[]) {
        try {
            Reader in = new InputStreamReader(System.in);
            out = System.out;
            if (args.length > 0) {
                in = new FileReader(args[0]);
            }
            if (args.length > 1) {
                out = new PrintStream(new FileOutputStream(args[1]));
            }
            lex = new Yylex(in);
            actual = lex.yylex();
            axioma();// llamada a la funcion del inicio de la gramatia (se hace el arbol de manera descendente)
            System.err.println("Parser finalizado correctamente");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        } catch (ParseException e) {
        System.err.println("Error de análisis: " + e.getMessage());
       }
    private static void checkActual() throws ParseException, IOException  {
        if(Objects.isNull(actual)) {
            throw new ParseException("Fin de fichero inesperado", 1);
        }
    }
    
    public static void avanza(int token, String mensaje) throws ParseException, IOException {
        if(actual == token) {
            actual = lex.yylex();
        } else {
            throw new ParseException(mensaje, 1);
        }
    }
    private static void axioma() throws ParseException, IOException {
       checkActual();
       switch (actual) {
            case Yytoken.WHILE: {
                Yytoken
                listaSent();
                avanza(Yytoken.EOF, "EOF");
                break;
            }
            case Yytoken.DO: {
                lista();
                break;
            }
           default:
                throw new ParseException("Error axioma", lex.linea);
        }
    }
}
