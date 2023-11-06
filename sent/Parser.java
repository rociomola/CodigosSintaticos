import java.io.*;
import java.text.ParseException;
import java.util.Objects;


public class Parser {
    public static PrintStream out;
    private static Yylex lex;
    private static int actual;
    public static void main(String args[]) {
        try {
            out = System.out;
            Reader in = new InputStreamReader(System.in);
            if (args.length > 0) {
                in = new FileReader(args[0]);
            }
            if (args.length > 1) {// hay un segundo argumento (deducimos fichero de guardado)
                out = new PrintStream(new FileOutputStream(args[1]));
            }
            lex = new Yylex(in);// llamada al analizador lexico
            actual = lex.yylex();
            axioma();
            //System.err.println("Parser finalizado correctamente");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Error token = " + e.getMessage());
        }
    }
    private static void checkActual() throws ParseException, IOException {
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
            case Yytoken.WHILE:
            case Yytoken.DO:
            case Yytoken.IDENT:
            case Yytoken.ALL:
            case Yytoken.CLL:
            case Yytoken.EOF: {
                Yytoken.regla(0);
                listaSent();
                avanza(Yytoken.EOF,"EOF" );
                break;
            }
           default:{
                throw new ParseException(" axioma",1);
            }
       }
    }
    private static void listaSent() throws ParseException, IOException {
       checkActual();
       switch (actual) {
            case Yytoken.WHILE:
            case Yytoken.DO:
            case Yytoken.IDENT:
            case Yytoken.ALL: {
                Yytoken.regla(1);
                sent();
                listaSent();
                break;
            }
            case Yytoken.CLL:
            case Yytoken.EOF: {
                Yytoken.regla(2);
                break;
            }
           default:{
                throw new ParseException(" axioma", 1);
            }
       }

    }
    private static void sent() throws ParseException, IOException {
       checkActual();
       switch (actual) {
            case Yytoken.WHILE: {
                Yytoken.regla(3);
                avanza(Yytoken.WHILE, "WHILE");
                avanza(Yytoken.AP,"AP");
                cond();
                avanza(Yytoken.CP, "CP");
                sent();
                break;
            }
            case Yytoken.DO: {
                Yytoken.regla(4);
                avanza(Yytoken.DO, "DO");
                sent();
                avanza(Yytoken.WHILE, "WHILE");
                avanza(Yytoken.AP,"AP");
                cond();
                avanza(Yytoken.CP, "CP");
                avanza(Yytoken.PYC, "PYC1");
                break;
            }
            case Yytoken.IDENT: {
                Yytoken.regla(5);
                avanza(Yytoken.IDENT, "IDENT");
                avanza(Yytoken.IGUAL, "IGUAL");
                var();
                avanza(Yytoken.PYC,"PYC2");
                break;
            }
            case Yytoken.ALL: {
                Yytoken.regla(6);
                avanza(Yytoken.ALL, "ALL");
                listaSent();
                avanza(Yytoken.CLL, null);
                break;
            }
            default:{
                throw new ParseException(" axioma", 1);
            }
        }
    }
    private static void cond() throws ParseException, IOException {
       checkActual();
       switch (actual) {
            case Yytoken.IDENT:
            case Yytoken.NUMERO: {
                Yytoken.regla(7);
                var();
                avanza(Yytoken.MENOR,"MENOR");
                var();
                break;
            }
             
            default:{
                throw new ParseException(" axioma",1);

            }

       }
    }
    private static void var() throws ParseException, IOException {
       checkActual();
       switch (actual) {
            case Yytoken.IDENT: {
                Yytoken.regla(8);
                avanza(Yytoken.IDENT, "IDENT");
                break;
            }
            case Yytoken.NUMERO: {
                Yytoken.regla(9);
                avanza(Yytoken.NUMERO, "NUMERO");
                break;
            }
            default:{
                throw new ParseException(" axioma", 1);

            }
        }
    }

}