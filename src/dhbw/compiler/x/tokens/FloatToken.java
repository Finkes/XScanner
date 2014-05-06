package dhbw.compiler.x.tokens;

public class FloatToken extends Token{

	protected double value;

	
	public FloatToken(TokenType type, double floatValue, int row, int column)
    {
		super(type, row, column);
		
		this.value = floatValue;
    }
	
	
	
	@Override
    public String toString() {
        return "("+type.toString()+"|float:"+value+")";
    }

}