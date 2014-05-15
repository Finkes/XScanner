

package dhbw.compiler.x;

import java_cup.runtime.*;
import java.io.IOException;
import dhbw.compiler.x.tokens.*;
import dhbw.compiler.x.tokens.Token.TokenType;

%%
%type Token
%function nextToken
%class Scanner


%unicode
%line
%column

%public
%final

%xstate INT, FRACTION, EXPVZ, EXP, STRING

%{
	private int intvalue=0;
	private String textvalue ="";
	private int fracvalue=0;
	private int fraclength=0;
	private int expvalue=0;
	private int expsign=1;
	private int startline =0;
	private int startcolumn =0;
%}



%%

read					{ return new KeyWordToken(TokenType.READ, yyline+1, yycolumn+1); }
print					{ return new KeyWordToken(TokenType.PRINT, yyline+1, yycolumn+1); }
int						{ return new KeyWordToken(TokenType.INT, yyline+1, yycolumn+1); }
float					{ return new KeyWordToken(TokenType.FLOAT, yyline+1, yycolumn+1); }
string					{ return new KeyWordToken(TokenType.STRING, yyline+1, yycolumn+1); }
\+						{ return new KeyWordToken(TokenType.PLUS, yyline+1, yycolumn+1); }
\-						{ return new KeyWordToken(TokenType.MINUS, yyline+1, yycolumn+1); }
\*						{ return new KeyWordToken(TokenType.MULT, yyline+1, yycolumn+1); }
\/						{ return new KeyWordToken(TokenType.DIV, yyline+1, yycolumn+1); }
:=						{ return new KeyWordToken(TokenType.ASSIGN, yyline+1, yycolumn+1); }
\(						{ return new KeyWordToken(TokenType.LBR, yyline+1, yycolumn+1); }
\)						{ return new KeyWordToken(TokenType.RBR, yyline+1, yycolumn+1); }
\<						{ return new KeyWordToken(TokenType.LESS, yyline+1, yycolumn+1); }
>						{ return new KeyWordToken(TokenType.MORE, yyline+1, yycolumn+1); }
=						{ return new KeyWordToken(TokenType.EQUALS, yyline+1, yycolumn+1); }
if						{ return new KeyWordToken(TokenType.IF, yyline+1, yycolumn+1); }
then					{ return new KeyWordToken(TokenType.THEN, yyline+1, yycolumn+1); }
else					{ return new KeyWordToken(TokenType.ELSE, yyline+1, yycolumn+1); }
while					{ return new KeyWordToken(TokenType.WHILE, yyline+1, yycolumn+1); }
for						{ return new KeyWordToken(TokenType.FOR, yyline+1, yycolumn+1); }
;						{ return new KeyWordToken(TokenType.SEMICOLON, yyline+1, yycolumn+1); }
begin					{ return new KeyWordToken(TokenType.BEGIN, yyline+1, yycolumn+1); }
end						{ return new KeyWordToken(TokenType.END, yyline+1, yycolumn+1); }
program					{ return new KeyWordToken(TokenType.PROGRAM, yyline+1, yycolumn+1); }
\.						{ return new KeyWordToken(TokenType.DOT, yyline+1, yycolumn+1); }
:						{ return new KeyWordToken(TokenType.COLON, yyline+1, yycolumn+1); }


[a-zA-Z][a-zA-Z0-9]*	{ return new IdToken(TokenType.ID, yytext(), yyline+1, yycolumn+1); }

0						{ return new IntToken(TokenType.INTCONST, 0, yyline+1, yycolumn+1); }
[1-9]					{ yybegin(INT); textvalue=yytext(); startline=yyline+1; startcolumn=yycolumn+1;
						  intvalue=yytext().charAt(0)-'0'; fracvalue=0; fraclength=0; expvalue=0; }

\"						{ yybegin(STRING); textvalue=""; startline=yyline+1; startcolumn=yycolumn+1; }

(\/\*)~(\*\/)				{ /* eat comments */ }
[\ \t\b\f\r\n]+			{ /* eat whitespace */ }
<<EOF>>					{ return new KeyWordToken(TokenType.EOF, yyline+1, yycolumn+1); }
[^]						{ return new InvalidToken(TokenType.INVALID,  yytext(), yyline+1, yycolumn+1); }

<STRING> {
	[a-zA-Z \.:]		{ textvalue+=yytext(); }
	\\\"				{ textvalue+="\""; }
	\"					{ yybegin(YYINITIAL); return new StringToken(TokenType.STRINGCONST, textvalue, startline, startcolumn); }
	[^]					{ yybegin(YYINITIAL); return new InvalidToken(TokenType.INVALID, "\""+textvalue, startline, startcolumn); }
	<<EOF>>				{ yybegin(YYINITIAL); return new InvalidToken(TokenType.INVALID, "\""+textvalue, startline, startcolumn); } 
}

<INT> {
	[0-9]					{ intvalue = 10*intvalue + yytext().charAt(0)-'0'; 
							  textvalue+=yytext();}
	\.						{ yybegin(FRACTION); 
							  textvalue+=yytext();}
	[eE]/[\-0-9]			{ yybegin(EXPVZ); }
	[^]						{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new IntToken(TokenType.INTCONST, intvalue, startline, startcolumn); }
	<<EOF>>					{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new IntToken(TokenType.INTCONST, intvalue, startline, startcolumn);}
}

<FRACTION> {
	[0-9]					{ fracvalue= 10*fracvalue+ yytext().charAt(0)-'0'; 
							  fraclength++;
							  textvalue+=yytext();}
	[eE]					{ yybegin(EXPVZ); 
							  textvalue+=yytext();}
	[^]						{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new FloatToken(TokenType.FLOATCONST,intvalue+fracvalue/Math.pow(10,fraclength), startline, startcolumn); }
	<<EOF>>					{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new FloatToken(TokenType.FLOATCONST,intvalue+fracvalue/Math.pow(10,fraclength), startline, startcolumn); }	
}

<EXPVZ> {
	-0|0					{ yybegin(YYINITIAL);
							  return new FloatToken(TokenType.FLOATCONST,intvalue+fracvalue/Math.pow(10,fraclength), startline, startcolumn); }
	-[1-9]					{ yybegin(EXP); expvalue=yytext().charAt(1)-'0'; expsign=-1; }
	[1-9]					{ yybegin(EXP); expvalue=yytext().charAt(0)-'0'; expsign=1; }
	[^]						{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new InvalidToken(TokenType.INVALID, textvalue, startline, startcolumn); }
	<<EOF>>					{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new InvalidToken(TokenType.INVALID, textvalue, startline, startcolumn); }
}

<EXP> {
	[0-9]					{ expvalue = 10* expvalue + yytext().charAt(0)-'0'; }
	[^]						{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new FloatToken(TokenType.FLOATCONST,(intvalue+fracvalue/Math.pow(10,fraclength))*Math.pow(10,expvalue*expsign), startline, startcolumn); }
	<<EOF>>					{ yybegin(YYINITIAL); 
					  		  yypushback(1); 
					  		  return new FloatToken(TokenType.FLOATCONST,(intvalue+fracvalue/Math.pow(10,fraclength))*Math.pow(10,expvalue*expsign), startline, startcolumn); } 
}



