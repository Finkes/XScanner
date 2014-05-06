package dhbw.compiler.x.tokens;

public class StringToken extends Token
{
	protected String value;
	
	public StringToken(TokenType type,String value,  int row, int column)
    {
		super(type, row, column);
		
		this.value = value;

    }
	

}