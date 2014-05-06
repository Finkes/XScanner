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
        INTEGER,
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
        STRINGCONST
    }

    protected TokenType type;
    protected String data;
    protected int symbol;
    protected int sourceRow;
    protected int sourceColumn;

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


