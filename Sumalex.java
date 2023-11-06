public class Sumalex {
    private static Yytoken actual = "";// esto guarda el tokens
    public static PrintStream out;
    private static Yylex lex;
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
            actual = lex.Yylex();// esto te da el siguiente token a leer
            S();// llamada a la funcion del inicio de la gramatia (se hace el arbol de manera descendente)
            System.err.println("Parser finalizado correctamente");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void avanza(int token, String mensaje) throws ParseException {
        if (actual.getToken() == token) {
            actual = lex.Yylex();
        } else {
            throw new ParseException("Error", Lex.linea);
        }
    }
    private static void checkActual() throws ParseException {// miramos si hay mas tokens en la lista
        if (ObjectIsNull(actual)){
        throw new ParseException("Fin de fichero inesperado", lex.linea);
    }
    }

    private static void S() throws ParseException {
       checkActual();
       switch (actual.getToken()) {
            case YyToken.Ap: {
                L();
                avanza(Yytoken.Dolar, "Error Dolar");                
                break;
            }
           default:
                throw new ParseException(" Error S", Lex.linea);
        }
    }
    private static void L() throws ParseException {
       int valor;
       checkActual();
       switch (actual.getToken()) {
            case YyToken.Ap: {
                valor=O();
                System.err.println(valor);
                E();
                break;
            }
           default:
                throw new ParseException(" Error L", Lex.linea);
        }
    }
    private static void E() throws ParseException {
       int valor;
        checkActual();
       switch (actual.getToken()) {
            case YyToken.Ap: {
                valor=O();
                System.err.println(valor);
                E();                
                break;
            }
            case YyToken.Dolar: {
                break;
            }
           default:
                throw new ParseException(" Error E", Lex.linea);
        }
    }
    private static int  O() throws ParseException {
        int valor1;
        int valor2;
        int valor;
        char op;
        checkActual();
        switch (actual.getToken()) {
            case YyToken.Ap: {
                avanza(Yytoken.Ap, "Error ap");
                op=OP();
                valor1=T();
                valor2=T();
                switch (op) {
                    case "+":{
                        valor= valor1+valor2;      
                        break;
                    }
                    case "-":{
                        valor=valor1-valor2;
                        break;
                    }
                    default:{
                        System.err.println("Error de operacion");
                        break;
                    }
                }
                avanza(Yytoken.CP,  "Error cp");
                break;
            }
        default:
            throw new ParseException(" Error O", Lex.linea);
        }
        return valor;
    }
    private static chat OP() throws ParseException {
        char op;
       checkActual();
       switch (actual.getToken()){
            case YyToken.Mas: {
                avanza(Yytoken.Mas, "Error mas");
                op="+";                
                break;
            }
            case YyToken.Menos: {
                avanza(Yytoken.Menos, "Error Menos"); 
                op="-";           
                break;
        }
           default:
                throw new ParseException(" Error OP", Lex.linea);
        }
        return op;
    }
    private static int T() throws ParseException {
       int valor;
        checkActual();
       switch (actual.getToken()) {
            case YyToken.Ap: {
                valor=O();
                break;
            }
            case YyToken.Entero: {
                valor=actual.getValor();
                avanza(Yytoken.Entero, "Error Entero");                
                break;
            }
           default:
                throw new ParseException(" Error T", Lex.linea);
        }
        return valor;
    }
}
       