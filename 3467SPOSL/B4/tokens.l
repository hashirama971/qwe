%{
# include<stdio.h>
# include "y.tab.h"
%}

TYPE int|char|bool|float|void|for|do|while|if|else|return|void|boolean
DIGIT [0-9]
letter [a-zA-z]
ASSIGN "="
%%
{TYPE} 				{ return DATATYPE;}
[a-zA-z]+[a-zA-z0-9]*          {return IDENTIFIER;}
[;]				{return SC;}

%%

