public class Yytoken {
	public static int Dolar = 0;
	public static int Ap = 1;
	public static int CP = 2;
	public static int Mas = 3;
	public static int Menos = 4;
	public static int Entero = 5;
	public static int Error = 6;


	private int token;
	private String valor;



	public Yytoken(int token, String valor) {
		this.token = token;
		this.valor = valor;
	}


		public int getToken() {
		return token;
	}


	public String getValor() {
		return valor;
	}


	public String toString() {
		return "<"+token+","+valor+">";
   }
}