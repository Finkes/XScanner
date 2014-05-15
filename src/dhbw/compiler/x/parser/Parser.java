package dhbw.compiler.x.parser;

import java.util.ArrayList;

import dhbw.compiler.x.tokens.Token;
import dhbw.compiler.x.tokens.Token.TokenType;


public class Parser 
{
	protected ArrayList<Token> tokens;
	
	protected int tokenStreamPosition;
	
	public Parser(ArrayList<Token> tokens)
	{
		this.tokens = tokens;
	}
	
	protected Token nextToken()
	{
		int currentToken = tokenStreamPosition;
		tokenStreamPosition++;
		
		return this.tokens.get(currentToken);
	}
	
	protected Tree parseToken(TokenType tokenType)
	{
		int tokenPosition = getTokenStreamPosition();
		Token nextToken = nextToken();
		
		if(nextToken.getType() == tokenType)
			return new Tree(nextToken);
		
		resetTokenStreamToPosition(tokenPosition);
		return null;
	}
	
	protected int getTokenStreamPosition()
	{
		return tokenStreamPosition;
	}
	
	protected void resetTokenStreamToPosition(int position)
	{
		this.tokenStreamPosition = position;
	}
	
	public Tree parse()
	{
		return parseProgram();
	}
	
	private boolean nulltest(Object o)
	{
		if(o == null)
				return false;
		return true;
	}

	//template for a function---------------------------
	protected boolean parseTemplate()
	{
		int tokenPosition = getTokenStreamPosition();
		
		//TODO
		
		resetTokenStreamToPosition(tokenPosition);
		return false;
	}
	//--------------------------------------------------
	
	//program
	protected Tree parseProgram()
	{
		int tokenPosition = getTokenStreamPosition();
		
		Tree nodeA,nodeB,nodeC,nodeD,nodeE,nodeF,program;
		
		if((nodeA = parseToken(TokenType.PROGRAM)) != null 
				&& (nodeB = parseToken(TokenType.ID))!= null 
				&& (nodeC = parseToken(TokenType.SEMICOLON)) != null
				&& (nodeD = parseDeclarations()) != null 
				&& (nodeE = parseBlock()) != null 
				&& (nodeF = parseToken(TokenType.DOT)) != null)
		{
			program = new Tree(new Token(TokenType.NT_PROGRAM));
			program.addChild(nodeA);
			program.addChild(nodeB);
			program.addChild(nodeC);
			program.addChild(nodeD);
			program.addChild(nodeE);
			program.addChild(nodeF);
			return program;
		}
		
		
		resetTokenStreamToPosition(tokenPosition);
		return null;
	}
	
	//block
	private Tree parseBlock() 
	{
		int tokenPosition = getTokenStreamPosition();
		
		Tree nodeA,nodeB,nodeC,block;
		
		if(		(nodeA = parseToken(TokenType.BEGIN)) != null 
				&& (nodeB = parseStatements())!= null 
				&& (nodeC = parseToken(TokenType.END)) != null )
		{
			block = new Tree(new Token(TokenType.NT_BLOCK));
			block.addChild(nodeA);
			block.addChild(nodeB);
			block.addChild(nodeC);
			return block;
		}
		
		resetTokenStreamToPosition(tokenPosition);
		return null;
	}

	//statements
	private Tree parseStatements() 
	{
		Tree statements = new Tree(new Token(TokenType.NT_STATEMENTS));
		
		Tree node;
		
		while((node = parseStatementWithSemi())!=null)
			statements.addChild(node);

		return statements;
	}

	//
	private Tree parseStatementWithSemi() 
	{
		int tokenPosition = getTokenStreamPosition();
		
		Tree a,b,c;
		
		if(nulltest(a = parseStatement()) && nulltest(b = parseToken(TokenType.SEMICOLON)))
		{
			Tree node = new Tree(new Token(TokenType.NT_STATEMENT_WITH_SEMI));
			node.addChild(a);
			node.addChild(b);
			
			return node;
		}
		
		
		resetTokenStreamToPosition(tokenPosition);
		// TODO Auto-generated method stub
		return null;
	}

	//statement
	private Tree parseStatement() 
	{
		int tokenPosition = getTokenStreamPosition();
		
		Tree node, statement;
		
		//assignstat
		if((node = parseAssignStat()) != null)
		{
			statement = new Tree(new Token(TokenType.STATEMENT));
			statement.addChild(node);
			return statement;
		}
		
		resetTokenStreamToPosition(tokenPosition);
		
		
		//condstat
		if((node = parseCondStat()) != null)
		{
			statement = new Tree(new Token(TokenType.STATEMENT));
			statement.addChild(node);
			return statement;
		}
		
		resetTokenStreamToPosition(tokenPosition);
		

		//whilestat
		if((node = parseWhileStat()) != null)
		{
			statement = new Tree(new Token(TokenType.STATEMENT));
			statement.addChild(node);
			return statement;
		}
		
		resetTokenStreamToPosition(tokenPosition);
		
		//forstat
		if((node = parseForStat()) != null)
		{
			statement = new Tree(new Token(TokenType.STATEMENT));
			statement.addChild(node);
			return statement;
		}
		
		resetTokenStreamToPosition(tokenPosition);
		
		//block
		if((node = parseBlock()) != null)
		{
			statement = new Tree(new Token(TokenType.STATEMENT));
			statement.addChild(node);
			return statement;
		}
		resetTokenStreamToPosition(tokenPosition);
		return null;
	}

	//assignstat
	private Tree parseAssignStat() 
	{
		int tokenPosition = getTokenStreamPosition();
		
		Tree a,b,c,d;
		
		if(nulltest(a = parseToken(TokenType.ID)) 
				&& nulltest(b = parseToken(TokenType.ASSIGN)) 
				&& nulltest(c = parseExpression())
				)
		{
			Tree node = new Tree(new Token(TokenType.NT_ASSIGN_STATEMENT));
			node.addChild(a);
			node.addChild(b);
			node.addChild(c);
			
			return node;
		}
		
		resetTokenStreamToPosition(tokenPosition);
		return null;
	}

	//expression
	private Tree parseExpression() {
		int tokenPosition = getTokenStreamPosition();
		Tree a,b,c,d,e,f,g;
		
		
		//expression2 + expression
		if(
				nulltest(a = parseExpression2())
				&&nulltest(b = parseToken(TokenType.PLUS))
				&&nulltest(c = parseExpression())
				)
		{
			Tree node = new Tree(new Token(TokenType.NT_EXPRESSION));
			node.addChild(a);
			node.addChild(b);
			node.addChild(c);
			
			return node;
		}
		
		resetTokenStreamToPosition(tokenPosition);
		
		//expression2 - expression
		if(
				nulltest(a = parseExpression2())
				&&nulltest(b = parseToken(TokenType.MINUS))
				&&nulltest(c = parseExpression())
				)
		{
			Tree node = new Tree(new Token(TokenType.NT_EXPRESSION));
			node.addChild(a);
			node.addChild(b);
			node.addChild(c);
			
			return node;
		}
		
		resetTokenStreamToPosition(tokenPosition);
		

		//expression2
		if(
				nulltest(a = parseExpression2())
				)
		{
			Tree node = new Tree(new Token(TokenType.NT_EXPRESSION));
			node.addChild(a);
			
			return node;
		}
		
		
		resetTokenStreamToPosition(tokenPosition);
		// TODO Auto-generated method stub
		return null;
	}

	//expression2
	private Tree parseExpression2() {
		int tokenPosition = getTokenStreamPosition();
		
		Tree a,b,c;
		
		//expression3 * expression2
		if(
				nulltest(a = parseExpression3())
				&& nulltest(b = parseToken(TokenType.MULT))
				&& nulltest(c = parseExpression2())
				)
				{
					Tree node = new Tree(new Token(TokenType.NT_EXPRESSION2));
					node.addChild(a);
					node.addChild(b);
					node.addChild(c);
					
					return node;
				}
		
		resetTokenStreamToPosition(tokenPosition);
		
		//expression3 / expression2
		if(
				nulltest(a = parseExpression3())
				&& nulltest(b = parseToken(TokenType.DIV))
				&& nulltest(c = parseExpression2())
				)
				{
					Tree node = new Tree(new Token(TokenType.NT_EXPRESSION2));
					node.addChild(a);
					node.addChild(b);
					node.addChild(c);
					
					return node;
				}
		
		resetTokenStreamToPosition(tokenPosition);
		
		//expression3
		if(
				nulltest(a = parseExpression3())
				)
				{
					Tree node = new Tree(new Token(TokenType.NT_EXPRESSION2));
					node.addChild(a);
					
					return node;
				}
		
		resetTokenStreamToPosition(tokenPosition);
		
		return null;
	}

	//expression3
	private Tree parseExpression3() {
		int tokenPosition =  getTokenStreamPosition();
		
		Tree a,b,c,d,e,f;
		
		// - INT
		if(
				nulltest(a = parseToken(TokenType.MINUS))
				&& nulltest(b = parseToken(TokenType.INTCONST))
				)
		{
			Tree node = new Tree(new Token(TokenType.NT_EXPRESSION3));
			node.addChild(a);
			node.addChild(b);
			
			return node;
		}
		resetTokenStreamToPosition(tokenPosition);
		
		// INT
		if(
			nulltest(a = parseToken(TokenType.INTCONST))
				)
		{
			Tree node = new Tree(new Token(TokenType.NT_EXPRESSION3));
			node.addChild(a);
			
			return node;
		}
		resetTokenStreamToPosition(tokenPosition);
		
		// - FLAOT
		if(
				nulltest(a = parseToken(TokenType.MINUS))
				&& nulltest(b = parseToken(TokenType.FLOATCONST))
				)
		{
			Tree node = new Tree(new Token(TokenType.NT_EXPRESSION3));
			node.addChild(a);
			node.addChild(b);
			
			return node;
		}
		resetTokenStreamToPosition(tokenPosition);
		
		//  FLAOT
		if(
				nulltest(a = parseToken(TokenType.FLOATCONST))
				)
		{
			Tree node = new Tree(new Token(TokenType.NT_EXPRESSION3));
			node.addChild(a);
			
			return node;
		}
		resetTokenStreamToPosition(tokenPosition);
		
		//  ID
		if(
				nulltest(a = parseToken(TokenType.ID))
				)
		{
			Tree node = new Tree(new Token(TokenType.NT_EXPRESSION3));
			node.addChild(a);
			
			return node;
		}
		resetTokenStreamToPosition(tokenPosition);
		
		//  ( EXPRESSION )
		if(
				nulltest(a = parseToken(TokenType.LBR))
				&& nulltest(b = parseExpression())
				&& nulltest(c = parseToken(TokenType.RBR))
				)
		{
			Tree node = new Tree(new Token(TokenType.NT_EXPRESSION3));
			node.addChild(a);
			node.addChild(b);
			node.addChild(c);
			
			return node;
		}
		resetTokenStreamToPosition(tokenPosition);
		return null;
	}

	//forstat
	private Tree parseForStat() {
		int tokenPosition = getTokenStreamPosition();
		
		Tree nodeA,nodeB,nodeC,nodeD,nodeE,nodeF,nodeG,nodeH,nodeI,forStat;
		
		if((nodeA = parseToken(TokenType.FOR)) != null 
				&& (nodeB = parseToken(TokenType.LBR)) != null
				&& (nodeC = parseAssignStat()) != null
				&& (nodeD = parseToken(TokenType.SEMICOLON)) != null
				&& (nodeE = parseCondition()) != null
				&& (nodeF = parseToken(TokenType.SEMICOLON)) != null
				&& (nodeG = parseAssignStat()) != null
				&& (nodeH = parseToken(TokenType.RBR)) != null
				&& (nodeI = parseStatement()) != null
		)
		{
			forStat = new Tree(new Token(TokenType.FOR_STATEMENT));
			return forStat;
		}
		
		resetTokenStreamToPosition(tokenPosition);
		return null;
	}
	
	//condition
	private Tree parseCondition() {
		
		int tokenPosition = getTokenStreamPosition();
		
		Tree a,b,c;
		
		//NEXPRESSION > NEXPRESSION
		if(nulltest(a = parseExpression()) 
				&& nulltest(b = parseToken(TokenType.MORE))
				&& nulltest(c = parseExpression())
				)
		{
			Tree tree = new Tree(new Token(TokenType.NT_CONDITION));
			tree.addChild(a);
			tree.addChild(b);
			tree.addChild(c);
		
			return tree;
		}
		resetTokenStreamToPosition(tokenPosition);
		
		//NEXPRESSION < NEXPRESSION
		if(nulltest(a = parseExpression()) 
				&& nulltest(b = parseToken(TokenType.LESS))
				&& nulltest(c = parseExpression())
				)
		{
			Tree tree = new Tree(new Token(TokenType.NT_CONDITION));
			tree.addChild(a);
			tree.addChild(b);
			tree.addChild(c);
		
			return tree;
		}
		
		resetTokenStreamToPosition(tokenPosition);
		
		//NEXPRESSION = NEXPRESSION
		if(nulltest(a = parseExpression()) 
				&& nulltest(b = parseToken(TokenType.EQUALS))
				&& nulltest(c = parseExpression())
				)
		{
			Tree tree = new Tree(new Token(TokenType.NT_CONDITION));
			tree.addChild(a);
			tree.addChild(b);
			tree.addChild(c);
		
			return tree;
		}
		
		resetTokenStreamToPosition(tokenPosition);
		
		return null;
	}

	//whilestat
	private Tree parseWhileStat() 
	{
		int tokenPosition = getTokenStreamPosition();
		
		Tree nodeA,nodeB,nodeC,nodeD,nodeE,whileStat;
		
		//WHILE (CONDITION) STATEMENT
		if((nodeA = parseToken(TokenType.WHILE)) != null 
				&& (nodeB = parseToken(TokenType.LBR)) != null
				&& (nodeC = parseCondition()) != null
				&& (nodeD = parseToken(TokenType.RBR)) != null
				&& (nodeE = parseStatement()) != null
		)
		{
			whileStat = new Tree(new Token(TokenType.WHILE_STATEMENT));
			
			whileStat.addChild(nodeA);
			whileStat.addChild(nodeB);
			whileStat.addChild(nodeC);
			whileStat.addChild(nodeD);
			whileStat.addChild(nodeE);
			
			return whileStat;
		}
		
		resetTokenStreamToPosition(tokenPosition);
		return null;
	}

	//condstat
	private Tree parseCondStat() {
		
		int tokenPosition = getTokenStreamPosition();
		
		Tree a,b,c,d,e,f;
		
		if((a = parseToken(TokenType.IF)) != null &&
			(b = parseCondition()) != null &&
			(c = parseToken(TokenType.THEN)) != null &&
			(d = parseStatement()) != null && 
			(e = parseToken(TokenType.ELSE)) != null &&
			(f = parseStatement()) != null)
			{
				Tree tree = new Tree(new Token(TokenType.NT_CONDSTAT));
				tree.addChild(a);
				tree.addChild(b);
				tree.addChild(c);
				tree.addChild(d);
				tree.addChild(e);
				tree.addChild(f);
				
				return tree;
			}
		
		resetTokenStreamToPosition(tokenPosition);
		
		if((a = parseToken(TokenType.IF)) != null &&
				(b = parseCondition()) != null &&
				(c = parseToken(TokenType.THEN)) != null &&
				(d = parseStatement()) != null)
				{
					Tree tree = new Tree(new Token(TokenType.NT_CONDSTAT));
					tree.addChild(a);
					tree.addChild(b);
					tree.addChild(c);
					tree.addChild(d);
					
					return tree;
				}
		resetTokenStreamToPosition(tokenPosition);
		
		return null;
	}

	//declarations
	protected Tree parseDeclarations()
	{

		Tree declarations = new Tree(new Token(TokenType.NT_DECLARATIONS));
		
		Tree node;
		
		while((node = parseDeclaration())!=null)
			declarations.addChild(node);
	
		return declarations;
	}
	
	//declaration
	private Tree parseDeclaration() 
	{
		int tokenPosition = getTokenStreamPosition();
		
		Tree nodeA,nodeB,nodeC,nodeD,nodeE,nodeF,nodeG,declaration;
		
		
		//READ & PRINT
		if(		(nodeA = parseToken(TokenType.READ)) 	!= null && 
				(nodeB = parseToken(TokenType.PRINT))	!= null && 
				(nodeC = parseToken(TokenType.ID)) 		!= null && 
				(nodeD = parseToken(TokenType.COLON)) 	!= null && 
				(
						(nodeF = parseToken(TokenType.FLOAT)) != null ||
						(nodeF = parseToken(TokenType.INT)) != null ||
						(nodeF = parseToken(TokenType.STRING)) != null
				)&&
				(nodeG = parseToken(TokenType.SEMICOLON) ) != null)
		{
			declaration = new Tree(new Token(TokenType.NT_DECLARATION));
			
			declaration.addChild(nodeA);
			declaration.addChild(nodeB);
			declaration.addChild(nodeC);
			declaration.addChild(nodeD);
			declaration.addChild(nodeF);
			declaration.addChild(nodeG);
			
			return declaration;
		}
		
		resetTokenStreamToPosition(tokenPosition);
		
		//READ ONLY
		if(		(nodeA = parseToken(TokenType.READ)) 	!= null && 
				(nodeB = parseToken(TokenType.ID)) 		!= null && 
				(nodeC = parseToken(TokenType.COLON)) 	!= null && 
				(
						(nodeD = parseToken(TokenType.FLOAT)) != null ||
						(nodeD = parseToken(TokenType.INT)) != null ||
						(nodeD = parseToken(TokenType.STRING)) != null
				) &&
				(nodeE = parseToken(TokenType.SEMICOLON) ) != null)
		{
			declaration = new Tree(new Token(TokenType.NT_DECLARATION));
			
			declaration.addChild(nodeA);
			declaration.addChild(nodeB);
			declaration.addChild(nodeC);
			declaration.addChild(nodeD);
			declaration.addChild(nodeE);
			
			return declaration;
		}
		
		resetTokenStreamToPosition(tokenPosition);

		//PRINT ONLY
		if(		(nodeA = parseToken(TokenType.PRINT))	!= null && 
				(nodeB = parseToken(TokenType.ID)) 		!= null && 
				(nodeC = parseToken(TokenType.COLON)) 	!= null && 
				(
						(nodeD = parseToken(TokenType.FLOAT)) != null ||
						(nodeD = parseToken(TokenType.INT)) != null ||
						(nodeD = parseToken(TokenType.STRING)) != null
				) &&
				(nodeE = parseToken(TokenType.SEMICOLON) ) != null)
			
		{
			declaration = new Tree(new Token(TokenType.NT_DECLARATION));
			
			declaration.addChild(nodeA);
			declaration.addChild(nodeB);
			declaration.addChild(nodeC);
			declaration.addChild(nodeD);
			declaration.addChild(nodeE);
			
			return declaration;
		}
		
		resetTokenStreamToPosition(tokenPosition);
		
		//NO PRINT AND NO READ
		//READ & PRINT
		if(		(nodeA = parseToken(TokenType.ID)) 		!= null && 
				(nodeB = parseToken(TokenType.COLON)) 	!= null && 
				(
						(nodeC = parseToken(TokenType.FLOAT)) != null ||
						(nodeC = parseToken(TokenType.INT)) != null ||
						(nodeC = parseToken(TokenType.STRING)) != null
				)&&
				(nodeD = parseToken(TokenType.SEMICOLON) ) != null)
		{
			declaration = new Tree(new Token(TokenType.NT_DECLARATION));
			
			declaration.addChild(nodeA);
			declaration.addChild(nodeB);
			declaration.addChild(nodeC);
			declaration.addChild(nodeD);
			
			return declaration;
		}
		
		
		resetTokenStreamToPosition(tokenPosition);
		return null;
	}
	



}
