package dhbw.compiler.x.tokens;



public class IntToken extends Token 
{
	protected int value;


	
	public IntToken(TokenType type,  int intValue, int row, int column)
    {
		super(type, row, column);
		
		this.value = intValue;
    }
	
	
	
	@Override
    public String toString() {
        return "("+type.toString()+"|int:"+value+")";
    }

}