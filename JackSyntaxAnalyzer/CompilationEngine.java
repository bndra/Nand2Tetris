package JackSyntaxAnalyzer;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class CompilationEngine 
{
	private Tokenizer tokenizer;
	private BufferedWriter tags;
	private String whiteSpace;
	
	public CompilationEngine(Scanner input, BufferedWriter output) throws IOException
	{
		tokenizer =  new Tokenizer(input);
		tags = output;
		whiteSpace = "";
	}
	
	public void CompileClass() throws IOException
	{
		writeOpenning("class");
		for(int i=0;i<3;i++)
		{
			tokenizer.advance();
			writeCurrent();
		}
		tokenizer.advance();
		while(tokenizer.getCT().equalsIgnoreCase("static")|
					tokenizer.getCT().equalsIgnoreCase("field"))
		{
			CompileClassVarDec();
		}
		while(tokenizer.getCT().equalsIgnoreCase("constructor")|
				tokenizer.getCT().equalsIgnoreCase("function")|
				tokenizer.getCT().equalsIgnoreCase("method"))
		{
			CompileSubroutine();
		}
		writeCurrent();
		tokenizer.advance();
		writeClosing("class");
	}
	
	public void CompileClassVarDec() throws IOException
	{
		writeOpenning("classVarDec");
		while(!tokenizer.getCT().equals(";"))
		{
			writeCurrent();
			tokenizer.advance();
		}
		writeCurrent();
		tokenizer.advance();
		writeClosing("classVarDec");
	}
	
	public void CompileSubroutine() throws IOException
	{
		writeOpenning("subroutineDec");
		
		writeCurrent();
		for(int i=0;i<3;i++)
		{
			tokenizer.advance();
			writeCurrent();
		}
		tokenizer.advance(); //the token after (
		
		CompileParameterList();
		writeCurrent(); //writes: )
		
		
		writeOpenning("subroutineBody");
		tokenizer.advance();
		writeCurrent(); //writes: {
		tokenizer.advance();
		
		if(tokenizer.getCT().equals("var"))
		{
			while (tokenizer.getCT().equals("var"))
				CompileVarDec();
		}
		
		CompileStatements();
		
		writeCurrent(); //writes: }
		tokenizer.advance();
		writeClosing("subroutineBody");
		
		writeClosing("subroutineDec");
	}
	
	public void CompileParameterList() throws IOException
	{
		writeOpenning("parameterList");
		while(!tokenizer.getCT().equalsIgnoreCase(")"))
		{
			writeCurrent();
			tokenizer.advance();
		}
		writeClosing("parameterList");
	}
	public void CompileVarDec() throws IOException
	{
		writeOpenning("varDec");
		while(!tokenizer.getCT().equals(";"))
		{
			writeCurrent();
			tokenizer.advance();
		}
		writeCurrent();
		tokenizer.advance();
		writeClosing("varDec");
	}
	public void CompileStatements() throws IOException
	{
		writeOpenning("statements");
		String statement;
		do 
		{
			statement = tokenizer.getCT();
			if(statement.equalsIgnoreCase("do"))
			{
				CompileDo();
			}
			else if(statement.equalsIgnoreCase("let"))
			{
				CompileLet();
			}
			else if(statement.equalsIgnoreCase("while"))
			{
				CompileWhile();
			}
			else if(statement.equalsIgnoreCase("return"))
			{
				CompileReturn();
			}
			else if(statement.equalsIgnoreCase("if"))
			{
				CompileIf();
			}
		} while (!tokenizer.getCT().equals("}"));
		writeClosing("statements");
	}
	public void CompileDo() throws IOException
	{
		writeOpenning("doStatement");
		writeCurrent(); //writes: do
		tokenizer.advance();
		
		CompileSubroutineCall();
		
		writeCurrent(); //writes: ;
		tokenizer.advance(); 
		writeClosing("doStatement");
	}
	public void CompileLet() throws IOException
	{
		writeOpenning("letStatement");
		writeCurrent(); //writes: let
		tokenizer.advance();
		writeCurrent(); //writes a variable name
		tokenizer.advance();
		
		if (tokenizer.getCT().equals("["))
		{
			writeCurrent();//writes [
			tokenizer.advance();
			
			CompileExpression();
			
			writeCurrent();//writes ]
			tokenizer.advance();
		}
		
		writeCurrent();//writes =
		tokenizer.advance();
		
		CompileExpression();

		writeCurrent();//writes ;
		tokenizer.advance();
		writeClosing("letStatement");
	}
	public void CompileWhile() throws IOException
	{
		writeOpenning("whileStatement");
		writeCurrent();//writes while
		tokenizer.advance();
		
		writeCurrent();//writes (
		tokenizer.advance();
		
		CompileExpression();
		
		writeCurrent();//writes )
		tokenizer.advance();
		
		writeCurrent();//writes {
		tokenizer.advance();
		
		CompileStatements();
		
		writeCurrent();//writes}
		tokenizer.advance();
		writeClosing("whileStatement");
	}
	public void CompileReturn() throws IOException
	{
		writeOpenning("returnStatement");
		writeCurrent();//writes return
		tokenizer.advance();
		
		if (!tokenizer.getCT().equals(";"))
		{
			CompileExpression();
		}
		
		writeCurrent();//writes ;
		tokenizer.advance();
		writeClosing("returnStatement");
	}
	public void CompileIf() throws IOException
	{
		writeOpenning("ifStatement");
		writeCurrent();//writes if
		tokenizer.advance();
		
		writeCurrent();//writes (
		tokenizer.advance();
		
		CompileExpression();
		
		writeCurrent();//writes )
		tokenizer.advance();
		
		writeCurrent();//writes {
		tokenizer.advance();
		
		CompileStatements();
		
		writeCurrent();//writes}
		tokenizer.advance();
		
		if(tokenizer.getCT().equalsIgnoreCase("else"))
		{
			writeCurrent();//writes else
			tokenizer.advance();
			
			writeCurrent();//writes {
			tokenizer.advance();
			
			CompileStatements();
			
			writeCurrent();//writes}
			tokenizer.advance();
		}
		writeClosing("ifStatement");
	}
	public void CompileSubroutineCall() throws IOException
	{
		while(!tokenizer.getCT().equals("("))
		{
			writeCurrent();
			tokenizer.advance();
		}
		writeCurrent(); //writes: (
		tokenizer.advance();
		
		CompileExpressionList();
		
		writeCurrent();	//writes: )
		tokenizer.advance();
	}
	public void CompileExpression() throws IOException
	{
		writeOpenning("expression");
		CompileTerm();
		String token = tokenizer.getCT();
		while (!token.equals(")") &
				!token.equals("]") &
				!token.equals(",") &
				!token.equals(";"))
		{
			writeCurrent(); //writes an operator: + - * / & | etc
			tokenizer.advance();
			CompileTerm();
			token = tokenizer.getCT();
		}
		writeClosing("expression");
	}
	public void CompileTerm() throws IOException
	{
		writeOpenning("term");
		if(tokenizer.getCT().equalsIgnoreCase("(")) //expression within parenthesis
		{
			writeCurrent();
			tokenizer.advance();
			
			CompileExpression();
			
			writeCurrent();
			tokenizer.advance();
		}
		else if(tokenizer.getCT().matches("\\-|~")) //-, ~
		{
			writeCurrent();
			tokenizer.advance();
			
			CompileTerm();
		}
		else
		{
			writeCurrent();
			tokenizer.advance();
			if(tokenizer.getCT().equalsIgnoreCase("[")) //array access
			{
				writeCurrent(); //writes [
				tokenizer.advance();
				
				CompileExpression();
				
				writeCurrent(); //writes ]
				tokenizer.advance();
			}
			else if(tokenizer.getCT().matches("\\(|\\.")) //subroutine call
			{
				CompileSubroutineCall();
			}
		}
		writeClosing("term");
	}
	public void CompileExpressionList() throws IOException
	{
		writeOpenning("expressionList");
		if(!tokenizer.getCT().equals(")"))
		{
			CompileExpression();
			while(!tokenizer.getCT().equals(")"))
			{
				writeCurrent(); //writes: ,
				tokenizer.advance();
				CompileExpression();
			}
		}
		writeClosing("expressionList");
	}
	
	public void writeCurrent() throws IOException
	{
		ttypes t =  tokenizer.tokenType();
		String type;
		if (t.equals(ttypes.STRING_CONST))
			type = "stringConstant";
		else if(t.equals(ttypes.INT_CONST))
			type = "integerConstant";
		else
			type = t.toString().toLowerCase();
		String token = tokenizer.getCT();
		tags.write(whiteSpace + "<"+type+"> " + token + " </"+type+">");
		tags.newLine();
		tags.flush();
	}
	public void writeOpenning(String label) throws IOException
	{
		tags.write(whiteSpace+"<"+label+">");
		tags.newLine();
		whiteSpace += "  ";
		tags.flush();
	}
	public void writeClosing(String label) throws IOException
	{
		whiteSpace = whiteSpace.substring(2);
		tags.write(whiteSpace+"</"+label+">");
		tags.newLine();
		tags.flush();
	}
}
