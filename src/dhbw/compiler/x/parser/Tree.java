package dhbw.compiler.x.parser;

import java.util.ArrayList;

import dhbw.compiler.x.tokens.Token;



public class Tree 
{
	protected Token nodeToken;
	
	protected ArrayList<Tree> children;
	
	public Tree(Token token)
	{
		this.nodeToken = token;
		this.children = new ArrayList<Tree>();
	}
	
	public void addChild(Tree child)
	{
		this.children.add(child);
	}
	
	public Token getNode()
	{
		return this.nodeToken;
	}
	
	public ArrayList<Tree> getChildren()
	{
		return this.children;
	}
	
	public String toGraphvizDotEdge(Tree parent)
	{

		return parent.getNode().hashCode()+"->"+this.getNode().hashCode()+";\n";

	}
	
	public String getId()
	{
		return ""+this.hashCode();
	}
	
	public String toGraphviz()
	{
		String pre = "digraph graphname\n{\n";
		String post = "}";
		return pre+allNodes()+"\n"+toGraphvizDotString(true)+post;
	}
	
	private String allNodes()
	{
		String decl = this.getId()+"\t[label="+this.nodeToken.getType().toString()+"];\n";
		
		for(Tree node : this.children)
			decl += node.allNodes();
		
		return decl;
	}
	
	
	private String toGraphvizDotString( boolean root)
	{
		String graph = "";
		
		for(Tree node : this.children)
		{
			graph+= this.getId() +"\t->\t"+ node.getId()+";\n";
			
			graph+= node.toGraphvizDotString(false);
		}
		
		return graph;
		
		
	}
}
