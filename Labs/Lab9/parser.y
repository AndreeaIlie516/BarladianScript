%{
#include <stdio.h>
#include <stdlib.h>

#define YYDEBUG 1
%}

%token FUNCTION;
%token MAIN;
%token VAR;
%token CONST;
%token READ;
%token PRINT;
%token IF;
%token ELSE;
%token WHILE;
%token FOR;
%token END;
%token RETURN;

%token INT;
%token FLOAT;
%token STRING;
%token CHAR;
%token INTARRAY;
%token FLOATARRAY;
%token STRINGARRAY;
%token CHARARRAY;

%token PLUS;
%token MINUS;
%token TIMES;
%token DIV;
%token MOD;
%token EQ;
%token BIGGER;
%token BIGGEREQ;
%token LESS;
%token LESSEQ;
%token EQQ;
%token NEQ;
%token AND;
%token OR;
%token NOT;

%token COLON;
%token SEMICOLON;
%token ROUNDBRACKETOPEN;
%token ROUNDBRACKETCLOSE;
%token SQUAREBRACKETOPEN;
%token SQUAREBRACKETCLOSE;
%token CURLYBRACKETOPEN;
%token CURLYBRACKETCLOSE;
%token COMMA;

%token IDENTIFIER;
%token INTCONSTANT;
%token STRINGCONSTANT;

%start Program

%%

Program: Statement Program {printf("Program -> Statement  Program\n")} | Statement {printf("Program -> Statement \n")};
Statement: DeclareVariableStatement {printf("Statement -> DeclareVariableStatement\n");} | DeclareArrayStatement {printf("Statement -> DeclareArrayStatement\n");} | AssignStatement {printf("Statement -> DeclareAssignStatement\n");} | IfStatement {printf("Statement -> IfStatement\n");} | WhileStatement {printf("Statement -> WhileStatement\n");} | ForStatement {printf("Statement -> ForStatement\n");} | FunctionDeclarationStatement {printf("Statement -> FunctionDeclarationStatement\n");} | FunctionCallStatement {printf("Statement -> FunctionCallStatement\n");} | ReturnStatement {printf("Statement -> ReturnStatement\n");} ;
DeclareVariableStatement: VAR IdentifierList : Type {printf("DeclareVariableStatement -> incaOVariabilaMaiTaieDinEle IdentifierList : Type\n");} ;
IdentifierList: IDENTIFIER {print("IdentifierList -> IDENTIFIER \n");} | IDENTIFIER COMMA IdentifierList {print("IdentifierList -> IDENTIFIER \n");}
Type: INT {print("Type -> aziMaSimtIntreg \n");} | FLOAT {print("Type -> aziImiDaCuVirgula \n");} | STRING {print("Type -> aziMaSimtText \n");} | CHAR {print("Type -> aziMaSimtCaracter \n");} ;
DeclareArrayStatement: VAR IdentifierList : ArrayType SQUAREBRACKETOPEN SQUAREBRACKETCLOSE {printf("DeclareArrayStatement -> incaOVariabilaMaiTaieDinEle IdentifierList : ArrayType[]\n");} ;
ArrayType: INTARRAY {print("Type -> aziMaSimtCuListeIntregi \n");} | FLOATARRAY {print("Type -> aziImiDaListeCuVirgula \n");} | STRINGARRAY {print("Type -> aziMaSimtCuListeText \n");} | CHARARRAY {print("Type -> aziMaSimtCuListeCaracter \n");} ;
AssignStatement: IDENTIFIER EQ Expression {printf("AssignStatement -> IDENTIFIER = Expression \n");} | ArrayAccessStatement EQ Expression {printf("AssignStatement -> ArrayAccessStatement = Expression \n");};
Expression: NumberExpression {printf("Expression -> NumberExpression \n");} | StringExpression {printf("Expression -> StringExpression \n");} ;
ArithOp: PLUS {printf("ArithOp -> + \n");} | MINUS {printf("ArithOp -> - \n");} | TIMES {printf("Arith -> * \n");} | DIV {printf("ArithOp -> / \n");} | MOD {printf("ArithOp -> % \n");} ;
LogicalOp: AND {printf("LogicalOp -> && \n");} | OR {printf("LogicalOp -> || \n");}
NegationOp: NOT
RelationalOperator : EQQ {printf("RelationalOperator -> ==\n");} | LESS {printf("RelationalOperator -> <\n");} | LESSEQ {printf("RelationalOperator -> <=\n");} | BIGGER {printf("RelationalOperator -> >\n");} | BIGGEREQ {printf("RelationalOperator -> >=\n");} ;
Condition : Expression RelationalOperator Expression {printf("Condition -> Expression RelationalOperator Expression\n");}
			| Expression RelationalOperator Expression AND Expression RelationalOperator Expression {printf("Condition -> Expression RelationalOperator Expression && Expression RelationalOperator Expression\n");}
			| Expression RelationalOperator Expression OR Expression RelationalOperator Expression {printf("Condition -> Expression RelationalOperator Expression || Expression RelationalOperator Expression\n");} ;
ArrayAccessStatement: IDENTIFIER SQUAREBRACKETOPEN IDENTIFIER SQUAREBRACKETCLOSE {printf("ArrayAccessStatement -> IDENTIFICATOR[IDENTIFICATOR]\n");} ;
NumberExpression: INTCONSTANT {printf("NumberExpression -> INTCONSTANT \n");} | IDENTIFIER {printf("NumberExpression -> IDENTIFIER \n");} | ArrayAccessStatement {printf("NumberExpression -> ArrayAccessStatement \n");} | NumberExpression ArithOp NumberExpression {printf("NumberExpression -> NumberExpression ArithOp NumberExpression \n");} | ROUNDBRACKETOPEN NumberExpression ArithOp NumberExpression ROUNDBRACKETCLOSE {printf("NumberExpression -> ( NumberExpression ArithOp NumberExpression ) \n");} | FunctionCallStatement {printf("NumberExpression -> FunctionCallStatement \n");} ;
StringExpression: STRINGCONSTANT {printf("StringExpression -> STRINGCONSTANT \n");} ;
ExpressionList: Expression {printf("ExpressionList -> Expression \n");} | Expression COMMA ExpressionList {printf("ExpressionList -> Expression , ExpressionList \n");} ;
IfStatement: IF ROUNDBRACKETOPEN Condition ROUNDBRACKETCLOSE COLON Program END {printf("IfStatement -> dacaSuntVaiMortiiMei ( Condition ) : Program atatSAPutut \n");} | IF ROUNDBRACKETOPEN Condition ROUNDBRACKETCLOSE COLON Program ELSE COLON Program END {printf("IfStatement -> dacaSuntVaiMortiiMei ( Condition ) : Program sorryIJustWorkHere : Program atatSAPutut \n");};
WhileStatement: WHILE ROUNDBRACKETOPEN Condition ROUNDBRACKETCLOSE COLON Program END {printf("WhileStatement -> catTimpNuAmDraci ( Condition ) : Program atatSAPutut \n");} ;
ForStatement: FOR ROUNDBRACKETOPEN AssignStatement SEMICOLON Condition SEMICOLON NumberExpression ROUNDBRACKETCLOSE COLON Program END {printf("ForStatement -> mergiBineCaTurbosuflanta ( AssignStatement; Condition; NumberExpression ) : Program atatSAPutut \n");} ;
FunctionDeclarationStatement: FUNCTION FunctionName ROUNDBRACKETOPEN ROUNDBRACKETCLOSE COLON Program END {printf("FunctionDeclarationStatement -> mareFunctie FunctionName () : Program atatSAPutut")};
FunctionCallStatement: FunctionName ROUNDBRACKETOPEN IdentifierList ROUNDBRACKETCLOSE {printf("FunctionCallStatement -> FunctionName (IdentifierList)")} ;
FunctionName: MAIN {printf("FunctionName -> bunaDimineataDragiBarladeni\n");} | READ {printf("FunctionName -> readDirectFromTheMysticalLandOfBarladia\n");} | PRINT {printf("FunctionName -> printOffendingCommand\n");} ;
ReturnStatement: RETURN {printf("ReturnStatement -> returneazaBugNou\n");} ;

%%

yyerror(char *s)
{	
	printf("%s\n",s);
}

extern FILE *yyin;

main(int argc, char **argv)
{
	if(argc>1) yyin =  fopen(argv[1],"r");
	if(!yyparse()) fprintf(stderr, "\tOK\n");
} 