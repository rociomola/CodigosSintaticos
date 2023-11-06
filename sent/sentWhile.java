import java.io.*;
import java.text.ParseException;
import java.util.Object;


public class sentWhile {
    public static PrintStream out;
    private static Yylex lex;
    private static Yytoken actual;
    public static void main(String args[]) {
        try {
            out = System.out;
            Reader in = new InputStreamReader(System.in);
            out = System.out;
            if (args.length > 0) {
                in = new FileReader(args[0]);
            }
            if (args.length > 1) {// hay un segundo argumento (deducimos fichero de guardado)
                out = new PrintStream(new FileOutputStream(args[1]));
            }
            lex = new Yylex(in);// llamada al analizador lexico
            actual = lex.yylex();// esto te da el siguiente token a leer
            axioma();// llamada a la funcion del inicio de la gramatia (se hace el arbol de manera descendente)
            System.err.println("Parser finalizado correctamente");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void checkActual() throws ParseException {
        if(Object.isNull(actual)) {
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

    private static void axioma() throws ParseException {
       checkActual();
       switch (actual.getToken()) {
            case Yytoken.WHILE: {
                listaSent();
                avanza(Yytoken.EOF,"Error EOF" );
                break;
            }
            case Yytoken.DO: {
                listaSent();
                avanza(Yytoken.EOF,"Error EOF" );
                break;
            }
            case Yytoken.IDENT: {
                listaSent();
                avanza(Yytoken.EOF,"Error EOF" );
                break;
            }
            case Yytoken.ALL: {
                listaSent();
                avanza(Yytoken.EOF,"Error EOF" );
                break;
            }
            case Yytoken.CLL: {
                listaSent();
                avanza(Yytoken.EOF,"Error EOF" );
                break;
            }
            case Yytoken.EOF: {
                listaSent();
                avanza(Yytoken.EOF,"Error EOF" );
                break;
            }
           default:{
                throw new ParseException(" Error axioma");
                break;
            }
       }
    }
    private static void listaSent() throws ParseException {
       checkActual();
       switch (actual.getToken()) {
            case Yytoken.WHILE: {
                sent();
                listaSent();
                break;
            }
            case Yytoken.DO: {
                sent();
                listaSent();
                break;
            }
            case Yytoken.IDENT: {
                sent();
                listaSent();
                break;
            }
            case Yytoken.ALL: {
                sent();
                listaSent();
                break;
            }
            case Yytoken.CLL: {
               
                break;
            }
            case Yytoken.EOF: {
                
                break;
            }
           default:{
                throw new ParseException(" Error axioma");
                break;
            }
       }

    }
    private static void sent() throws ParseException {
       checkActual();
       switch (actual.getToken()) {
            case Yytoken.WHILE: {
                avanza(Yytoken.WHILE, "error WHILE");
                avanza(Yytoken.Ap,"Error AP");
                cond();
                avanza(Yytoken.CP, "Error cp");
                sent();
                break;
            }
            case Yytoken.DO: {
                avanza(Yytoken.DO, "Error Do");
                sent();
                avanza(Yytoken.WHILE, "error WHILE");
                avanza(Yytoken.Ap,"Error AP");
                cond();
                avanza(Yytoken.CP, "Error cp");
                avanza(Yytoken.PYC, "Error PYC");
                break;
            }
            case Yytoken.IDENT: {
                avanza(Yytoken.IDENT, "Error IDENT");
                avanza(Yytoken.IGUAL, "Error IGUAL");
                var();
                avanza(Yytoken.PYC,"Error PYC");
                break;
            }
            case Yytoken.Yytoken.ALL: {
                avanza(Yytoken.ALL, "Error ALL");
                listaSent();
                avanza(Yytoken.CLL, null);
                break;
            }
            default:{
                throw new ParseException(" Error axioma");
                break;
            }
        }
    }
    private static void cont() throws ParseException {
       checkActual();
       switch (actual.getToken()) {
            case Yytoken.IDENT: {
                var();
                avanza(Yytoken.MENOR);
                var();
                break;
            }
            case Yytoken.NUMERO: {
                var();
                avanza(Yytoken.MENOR, "Error menor");
                var();
                break;
            }
            default:{
                throw new ParseException(" Error axioma");
                break;
            }

       }
    }
    private static void var() throws ParseException {
       checkActual();
       switch (actual.getToken()) {
            case Yytoken.IDENT: {
                avanza(Yytoken.IDENT, "Error Ident");
                break;
            }
            case Yytoken.NUMERO: {
                avanza(Yytoken.NUMERO, "Error Numero");
                break;
            }
            default:{
                throw new ParseException(" Error axioma", lex.linea);
                break;
            }
        }
    }

}