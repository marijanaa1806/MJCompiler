
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROGRAM, yytext());}
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"const"     { return new_symbol(sym.CONST, yytext());}
"if"        { return new_symbol(sym.IF, yytext()); }
"else"      { return new_symbol(sym.ELSE, yytext()); }
"class"     { return new_symbol(sym.CLASS, yytext()); }
"while"     { return new_symbol(sym.WHILE, yytext()); }
"break"     { return new_symbol(sym.BREAK, yytext()); }
"continue"  { return new_symbol(sym.CONTINUE, yytext()); }
"read"      { return new_symbol(sym.READ, yytext()); }
"new"		{ return new_symbol(sym.NEW, yytext()); }
"extends" 	{ return new_symbol(sym.EXTENDS, yytext()); }
"foreach"      { return new_symbol(sym.FOREACH, yytext()); }

"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"-" 		{ return new_symbol(sym.MINUS, yytext()); }
"*" 		{ return new_symbol(sym.MUL, yytext()); }
"/" 		{ return new_symbol(sym.DIV, yytext()); }
"=" 		{ return new_symbol(sym.EQUAL, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"["			{ return new_symbol(sym.LSQUARE, yytext()); }
"]"			{ return new_symbol(sym.RSQUARE, yytext()); }
"%"			{ return new_symbol(sym.MOD, yytext()); }
"=="		{ return new_symbol(sym.DOUBLEQUAL, yytext()); }
"!="		{ return new_symbol(sym.NOTEQUAL, yytext()); }
">"		    { return new_symbol(sym.GREATER, yytext()); }
">="		{ return new_symbol(sym.GREATEREQUAL, yytext()); }
"<"		    { return new_symbol(sym.SMALLER, yytext()); }
"<="		{ return new_symbol(sym.SMALLEREQUAL, yytext()); }
"&&"		{ return new_symbol(sym.AND, yytext()); }
"||"		{ return new_symbol(sym.OR, yytext()); }
"++"		{ return new_symbol(sym.INC, yytext()); }
"--"		{ return new_symbol(sym.DEC, yytext()); }
":"			{ return new_symbol(sym.COLON, yytext()); }
"."			{ return new_symbol(sym.POINT, yytext()); }
"=>"		{ return new_symbol(sym.SLEDI, yytext()); }



"//" {yybegin(COMMENT);}
<COMMENT> . {yybegin(COMMENT);}
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

("true" | "false") { return new_symbol (sym.BOOL, new Boolean(yytext())); }
"." { return new_symbol (sym.CHAR, new Character (yytext().charAt(1))); }
[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }

. { System.err.println("Leksicka greska ("+yytext()+") na liniji "+(yyline+1)+" na poziciji "+(yycolumn+1)); }






