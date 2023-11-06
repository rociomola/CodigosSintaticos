import java.io.*;
import java.text.ParseException;
import java.util.Objects;

public class Lips {
    public static PrintStream out;
    private static Yylex lex;
    private static Yytoken actual;

    private static void checkActual() throws ParseException {
        if(Objects.isNull(actual)) {
            throw new ParseException("Fin de fichero inesperado", lex.linea);
        }
    }

    public static void avanza(int token, String mensaje) throws ParseException, IOException {
        if(actual.getToken() == token) {
            actual = lex.yylex();
        } else {
            throw new ParseException(mensaje, lex.linea);
        }
    }

    private static void S() throws ParseException, IOException {
        checkActual();
        switch (actual.getToken()) {
            case Yytoken.AP:
                L();
                avanza(Yytoken.DOLAR, "ERROR");
                break; 
            default:
                throw new ParseException("Error", lex.linea);
        }
        //completo el metodo por cada columna no vacia que haya en la tabla analisis LL(1)
    }

    private static void L() throws ParseException, IOException {
        int valor;
        checkActual();
        switch (actual.getToken()) {
            case Yytoken.AP:
                valor = O();
                out.println(valor);
                E();
                break;
        
            default:
                throw new ParseException("Error", lex.linea);
        }
    }

    private static void E() throws ParseException, IOException {
        checkActual();
        switch (actual.getToken()) {
            case Yytoken.AP:
                O();
                E();
                break;
            case Yytoken.DOLAR:
                break;
            default:
                throw new ParseException("Error", lex.linea);
        }
    }

    private static int O() throws ParseException, IOException {
        int t1, t2, r;
        char op;
        checkActual();
        switch (actual.getToken()) {
            case Yytoken.AP:
                avanza(Yytoken.AP, "ERROR");
                op = OP();
                t1 = T();
                t2 = T();
                avanza(Yytoken.CP, "ERROR");
                switch (op) {
                    case '+':
                        r = t1+t2; 
                        break;
                    case '-':
                        r = t1-t2;
                        break;
                    default:
                    throw new ParseException("Error", lex.linea);
                }
                break;
            default:
                throw new ParseException("Error", lex.linea);
        }
        return r;
    }

    private static char OP() throws ParseException, IOException {
        char op;
        checkActual();
        switch (actual.getToken()) {
            case Yytoken.MAS:
                op = '+';
                avanza(Yytoken.MAS, "ERROR");
                break;
            case Yytoken.MENOS:
                op = '-';
                avanza(Yytoken.MENOS, "ERROR");
                break;
            default:
                throw new ParseException("Error", lex.linea);
        }
        return op;
    }

    private static int T() throws ParseException, IOException {
        int valor;
        checkActual();
        switch (actual.getToken()) {
            case Yytoken.AP:
                valor = O();
                break;
            case Yytoken.ENTERO:
                valor = actual.getValor();
                avanza(Yytoken.ENTERO, "ERROR");
                break;
            default:
                //System.out.println(actual.getToken()+" "+actual.getValor());
                throw new ParseException("Error", lex.linea);
        }
        return valor;
    }

    public static void main(String argv[]) throws ParseException {
        try {
            Reader in = new InputStreamReader(System.in);
            out = System.out;
            if (argv.length > 0) {
                in = new FileReader(argv[0]);
            }
            if (argv.length > 1) {
                out = new PrintStream(new FileOutputStream(argv[1]));
            }

            lex = new Yylex(in);
            actual = lex.yylex();
            S();
            System.out.println("Parser finalizado correctamente");


        } catch (IOException e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
