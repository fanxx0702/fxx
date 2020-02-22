grammar ruleTest;

line : expr ;
expr :
	 commom            # equal 
	 | '(' expr ')'    # parenExpr
	 | expr 'and' expr # andExpr
	 | expr 'or' expr  # orExpr
;

commom : COL '=' VALUE NEWLINE;

WS : [ \t\n\r]+ -> skip ;
VALUE : [.\n]+ ;
COL : ID+ ;
NEWLINE : '\r'? '\n' ;
fragment ID : [a-z] ;