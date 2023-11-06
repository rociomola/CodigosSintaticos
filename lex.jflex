%%
%{ public int linea=1;
%}
%unicode
Espacio =\s
Retorno =\R
%%
\$  {return new Yytoken(Yytoken.Dolar,0);}
\(  {return new Yytoken(Yytoken.AP,0);}
\)  {return new Yytoken(Yytoken.CP,0);}
\+  {return new Yytoken(Yytoken.Mas,0);}
\-  {return new Yytoken(Yytoken.Menos,0);}
\[+-]?[0-9]+ {return new Yytoken(Yytoken.Entero,Integer.parseInt(yytex()));}
{Retorno} {linea++;}
{Espacio}   { }
[^] {return new Yytoken(Yytoken.Error,linea);} 