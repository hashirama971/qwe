%{
#include <stdio.h>
%}
DIGIT [0-9]
NUMBER {DIGIT}+
REAL {DIGIT}*"."{DIGIT}+
TEXT [a-zA-Z ]+
TEXT_NUMBERS [a-zA-Z0-9]
CONDITIONALS "if"|"else"|"else if"|"switch"|"case"
KEYWORD "break"|"continue"|"goto"|"return"|"int"|"float"|"char"|"double"|"public"|"class"|"void"
ITERATORS "for"|"while"|"do"
PREPROCESSOR "import"
DELIMITER [;]
IDENTIFIER [a-zA-Z]{TEXT_NUMBERS}*
NON_IDENTIFIER {NUMBER}+[A-Za-z]+
AOPERATOR "+"|"-"|"*"|"/"|"="
BLOCK_BEGINS "{"
BLOCK_ENDS "}"
UNARY "++"|"--"
LOPERATOR "&"|"|"|"&&"|"~"|"||"|">"|"<"|">="|"<="|"=="
FUNCTION {IDENTIFIER}+"("{DELIMITER}*{TEXT}{TEXT_NUMBERS}*{DELIMITER}*")"

%%
{CONDITIONALS} { printf("%s is a conditional\n", yytext); }
{ITERATORS} { printf("%s is an iterator\n", yytext); }
{DIGIT} { printf("%s is a digit\n", yytext); }
{NUMBER} { printf("%s is a number\n", yytext); }
{REAL} { printf("%s is a real number\n", yytext); }
{PREPROCESSOR} { printf("%s is a preprocessor directive\n",yytext); }
{DELIMITER} { printf("%s is a delimiter\n", yytext); }
{KEYWORD} { printf("%s is a keyword\n", yytext); }
{NON_IDENTIFIER} {printf("Could not process %s", yytext); }
{IDENTIFIER} { printf("%s is an identifier\n", yytext); }
{AOPERATOR} { printf("%s is a mathematical operator\n", yytext); }
{LOPERATOR} { printf("%s is a logical operator\n", yytext); }
{BLOCK_BEGINS} { printf("Block begins\n", yytext); }
{BLOCK_ENDS} { printf("Block ends\n", yytext); }
{UNARY} { printf("%s is a unary operator\n", yytext); }
{FUNCTION} { printf("%s is a function\n", yytext); }
%%

int main(int argc, char *argv[]) {

yylex();
return 0;
}

int yywrap()
{
return(1);
}
