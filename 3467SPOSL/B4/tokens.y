%{
# include <stdio.h>
%}

%token DATATYPE IDENTIFIER SC
%%
statement: DATATYPE IDENTIFIER SC {	printf(" valid variable declaration");}
	;
%%
void yyerror(const char *str){printf("error");}

int yywrap()
{
	return 0;
}

int main()
{
	yyparse();
}
