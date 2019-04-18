%{
/* NLP lexer */
#include<stdio.h>
#include"y.tab.h"
%}
%%
[\t ]+ ;
is|am|are|has|have|cannot { return(VERB); }
very|simply|gently { return(ADVERB); }
to|from|behind { return(PREPOSITION);}
if|then|and|but { return(CONJUNCTION);}
their|my|your { return(ADJECTIVE); }
I|you|he|This { return(PRONOUN); }
[a-zA-Z]+ { return(NOUN); }
.|\n { ECHO; }
%%

int yywrap()
{
return(1);
}
