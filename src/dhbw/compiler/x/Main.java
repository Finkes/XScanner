package dhbw.compiler.x;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import dhbw.compiler.x.tokens.Token;
import dhbw.compiler.x.tokens.Token.TokenType;

public class Main {

	public static void main(String[] args)
	{
//		 InputStream stream = new ByteArrayInputStream("[1,1...a,1.0^223,12,12,1as1,12.12..12.12,null,..,NULL,12..12.....]".getBytes());

		// InputStream stream = new ByteArrayInputStream("[123.314]".getBytes());
		 
		 FileInputStream stream;
		try 
		{
			stream = new FileInputStream(new File("input/test.x"));
			
			Scanner scanner = new Scanner(stream);

	        ArrayList<Token> tokens = new ArrayList<Token>();

	        Token token;

            while ((token = scanner.nextToken()) != null)
            {
                System.out.println(token.toString()+" ");
                tokens.add(token);
                
                if(token.getType() == TokenType.EOF)
                	break;
            }
		        
		} 
		catch (FileNotFoundException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 catch (Exception e) {
	            e.printStackTrace();
	        }

	      
	       
	}
}
