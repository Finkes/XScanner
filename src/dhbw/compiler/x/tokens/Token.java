package dhbw.compiler.x.tokens;


/**
 * Created by dominik on 08/04/14.
 */
public class Token {
    public enum TokenType
    {
        WS,
        LBR,
        RBR,
        STRING,
        FLOAT,
        COMMA,
        NULL,
        EOF,
        RANGE,
        INVALID,
        FOR,
        READ,
        FLOATCONST,
        BEGIN, 
        INTCONST,
        MORE,
        LESS, 
        MINUS,
        SEMICOLON, 
        DIV,
        MULT,
        COLON,
        PLUS,
        PRINT,
        END, 
        WHILE,
        ASSIGN,
        DOT,
        EQUALS,
        IF, 
        ELSE,
        INT,
        THEN,
        PROGRAM, 
        ID, 
        STRINGCONST, 
        NT_PROGRAM, 
        NT_DECLARATION, 
        NT_DECLARATIONS, 
        NT_STATEMENTS,
        NT_STATEMENT, 
        NT_STATEMENT_WITH_SEMI,
        NT_NASSIGNSTAT, 
        NT_BLOCK, 
        FOR_STATEMENT, 
        WHILE_STATEMENT,
        NT_CONDSTAT,
        NT_CONDITION,
        NT_SEXPRESSION, 
        NT_FOR_STATEMENT, 
        NT_WHILE_STATEMENT, 
        NT_BLOCK_STATEMENT, 
        NT_COND_STATEMENT, 
        NT_ASSIGN_STATEMENT, STATEMENT, NT_EXPRESSION, NT_EXPRESSION2, NT_EXPRESSION3		

    }

    protected TokenType type;
    protected String data;
    protected int symbol;
    protected int sourceRow;
    protected int sourceColumn;

    public Token(TokenType type)
    {
        this.type = type;
    }
    
    public Token(TokenType type, String data)
    {
        this.data = data;
        //this.symbol = SymbolTable.getInstance().put(data);
        this.type = type;
        //this.sourcePosition = sourcePosition;
    }
    
    public Token(TokenType type, int row, int column)
    {
    	this.type = type;
    }
    
    public Token(TokenType type, String data, int row, int column)
    {
    	this.type = type;
    	this.data = data;
    	this.sourceRow = row;
    	this.sourceColumn = column;
    }

    public String getData() {
        return data;
    }

    public TokenType getType() {
        return type;
    }
/*
    @Override
    public String toString() {
        return "<"+type.toString()+">";
    }
*/
    @Override
    public String toString() {
        return "("+type.toString()+"|"+data+")";
    }
}


