
package dhbw.compiler.x;

import java_cup.runtime.*;
import java.io.IOException;



%%
%type Token
%function getNextToken
%class Scanner

%unicode
%line
%column

// %public
%final
// %abstract

%init{
	// TODO: code that goes to constructor
%init}

%{
	
%}





ANY			=	.

%%

{ANY}		{	return null; }

