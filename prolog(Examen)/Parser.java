import java.io.FileReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.Objects;

class Parser {

public final static int EOF = Yylex.EOF;
public final static int NUMERO = Yylex.NUMERO ;         
public final static int COMA = Yylex.COMA;
public final static int AC = Yylex.AC; // Abre corchete
public final static int CC = Yylex.CC; // Cierra corchete
public final static int AP = Yylex.AP; // Abre parentesis
public final static int CP = Yylex.CP; // Cierra parentesis
public final static int NELEM = Yylex.NELEM;         
public final static int MAXLENGTH = Yylex.MAXLENGTH;         
public final static int MAXDEPTH = Yylex.MAXDEPTH;         
	
private static int token;
private static Yylex lex;

private static int yylex() {
	int token = 0;
	try {
		token = lex.yylex();
	} catch (IOException e) {
		System.out.println("ERROR");
		System.exit(0);
	}
	return token;
}

public static void main(String[] arg) {
    if (arg.length>0) {
        try {
            lex = new Yylex(new FileReader(arg[0]));
            token = yylex();

            S();
            if (token==EOF){
                System.out.println("CORRCETO");
            }
        } catch (IOException e) {
        } 
    }
}

 
 public static void avanza(int token, String mensaje) throws IOException  {
    if(token == token) {
        token = lex.yylex();
    } else {
        throw new IOException (mensaje);
    }
 }  
 
 //traemos la tablas.
 private static void S() throws IOException  {
   
    System.out.println(token);
    switch (token) {
        case AC: {
            avanza(AC, " ERROR");
            A();
            avanza(CC, " ERROR");
            avanza(EOF, " ERROR");
            break;
        }
        case CC: {
            break;
        }
        default:
            throw new IOException ("Error S");
    }
 }

 private static void A() throws IOException  {
   
    switch (token) {
        case AC: {
            avanza(AC, " ERROR");
            A();
            avanza(CC, " ERROR");

            break;
        }
        case NUMERO: {
            avanza(NUMERO, " ERROR");
            B();
            break;
        }
         case CC: {
            break;
        }
        default:
            throw new IOException ("Error A");
        }
    }

private static void B() throws IOException  {
   
    switch (token) {
        case COMA: {
            avanza(COMA, " ERROR");
            C();
            break;
        }
         case CC: {
            break;
        }
        default:
            throw new IOException ("Error A");
        }
    }

 private static void C() throws IOException  {
   
    switch (token) {
        case AC: {
            avanza(AC, " ERROR");
            A();
            avanza(CC, "CC");

            break;
        }
        case NUMERO: {
            avanza(NUMERO, " ERROR");
            B();
            break;
        }
        default:
            throw new IOException ("Error A");
        }
    }

}