/* A Bison parser, made by GNU Bison 2.3.  */

/* Skeleton interface for Bison's Yacc-like parsers in C

   Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA 02110-1301, USA.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     FUNCTION = 258,
     MAIN = 259,
     VAR = 260,
     CONST = 261,
     READ = 262,
     PRINT = 263,
     IF = 264,
     ELSE = 265,
     WHILE = 266,
     FOR = 267,
     END = 268,
     RETURN = 269,
     INT = 270,
     FLOAT = 271,
     STRING = 272,
     CHAR = 273,
     INTARRAY = 274,
     FLOATARRAY = 275,
     STRINGARRAY = 276,
     CHARARRAY = 277,
     PLUS = 278,
     MINUS = 279,
     TIMES = 280,
     DIV = 281,
     MOD = 282,
     EQ = 283,
     BIGGER = 284,
     BIGGEREQ = 285,
     LESS = 286,
     LESSEQ = 287,
     EQQ = 288,
     NEQ = 289,
     AND = 290,
     OR = 291,
     NOT = 292,
     COLON = 293,
     SEMICOLON = 294,
     ROUNDBRACKETOPEN = 295,
     ROUNDBRACKETCLOSE = 296,
     SQUAREBRACKETOPEN = 297,
     SQUAREBRACKETCLOSE = 298,
     CURLYBRACKETOPEN = 299,
     CURLYBRACKETCLOSE = 300,
     COMMA = 301,
     IDENTIFIER = 302,
     INTCONSTANT = 303,
     STRINGCONSTANT = 304
   };
#endif
/* Tokens.  */
#define FUNCTION 258
#define MAIN 259
#define VAR 260
#define CONST 261
#define READ 262
#define PRINT 263
#define IF 264
#define ELSE 265
#define WHILE 266
#define FOR 267
#define END 268
#define RETURN 269
#define INT 270
#define FLOAT 271
#define STRING 272
#define CHAR 273
#define INTARRAY 274
#define FLOATARRAY 275
#define STRINGARRAY 276
#define CHARARRAY 277
#define PLUS 278
#define MINUS 279
#define TIMES 280
#define DIV 281
#define MOD 282
#define EQ 283
#define BIGGER 284
#define BIGGEREQ 285
#define LESS 286
#define LESSEQ 287
#define EQQ 288
#define NEQ 289
#define AND 290
#define OR 291
#define NOT 292
#define COLON 293
#define SEMICOLON 294
#define ROUNDBRACKETOPEN 295
#define ROUNDBRACKETCLOSE 296
#define SQUAREBRACKETOPEN 297
#define SQUAREBRACKETCLOSE 298
#define CURLYBRACKETOPEN 299
#define CURLYBRACKETCLOSE 300
#define COMMA 301
#define IDENTIFIER 302
#define INTCONSTANT 303
#define STRINGCONSTANT 304




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif

extern YYSTYPE yylval;

