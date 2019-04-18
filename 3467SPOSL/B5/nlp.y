%{

#include<stdio.h>
int ans = 0;
%}
%token NOUN PRONOUN VERB ADVERB ADJECTIVE PREPOSITION CONJUNCTION
%%
sentence : compound_sentence { ans = 1; } | simple_sentence {ans = 3;} 
;
compound_sentence : subject VERB object CONJUNCTION subject VERB object | subject VERB ADVERB object CONJUNCTION subject VERB ADVERB object
;
simple_sentence : subject VERB ADVERB object | subject VERB object  
;
subject: NOUN | PRONOUN
;
object: NOUN
;



%%
extern FILE *yyin;
main()
{
yyin = fopen("ip.txt","r");

yyparse();
if(ans == 1){ printf("This is compound sentence\n");}
if(ans == 3){ printf("This is simple sentence\n");}



return 0;

}
yyerror(char *s)

{
fprintf(stderr,"%s\n","Error happened");
}


