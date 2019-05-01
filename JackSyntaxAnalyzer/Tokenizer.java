package JackSyntaxAnalyzer;
import java.util.Scanner;


enum ttypes
{
	KEYWORD, SYMBOL, IDENTIFIER, INT_CONST, STRING_CONST;
}
enum keywords
{
	CLASS, METHOD, FUNCTION, CONSTRUCTOR, INT, BOOLEAN, 
	CHAR, VOID, VAR, STATIC, FIELD, LET, DO, IF, ELSE, 
	WHILE, RETURN, TRUE, FALSE, NULL, THIS;
}

public class Tokenizer 
{
	private Scanner jackFile;
	private String currentToken;
	public Tokenizer (Scanner input) 
	{
		jackFile=input;
		jackFile.useDelimiter("(/\\*(\\r?\\n|\\r|.)*\\*/)|(//.*(\\r?\\n|\\r|$)?)|(\\p{javaWhitespace}|(?=\\W|(?<=\\W)\\w))(?!((?<=\".*).*\"))");//\\{|\\((?=.)|[\\.](?=.)|\\[(?=.)|\\}|\\)|\\]|,(?=.)|;|\\+|\\-|\\*|/|&|\\||\\<|\\>|\\=|~
		currentToken=null;//
	}
	public boolean hasMoreTokens()
	{
		return(jackFile.hasNext());
	}
	public void advance()
	{
		if(hasMoreTokens())
		{
			currentToken=jackFile.next().strip();
			while(currentToken.isBlank() & hasMoreTokens())
				currentToken=jackFile.next().strip();
		}
	}
	public ttypes tokenType()
	{
		if (currentToken.matches
				("class|method|function|constructor|int|boolean|char|void|var|static|field|let|do|if|else|while|return|true|false|null|this"))
			return ttypes.KEYWORD;
		if (currentToken.matches
				("\\{|\\}|\\)|\\(|\\[|\\]|\\.|,|;|\\+|\\-|\\*|/|&|\\||\\<|\\>|\\=|~"))
			return ttypes.SYMBOL;
		if (currentToken.matches(".*\\d.*"))
			return ttypes.INT_CONST;
		if (currentToken.startsWith("\"")) 
			return ttypes.STRING_CONST;
		return ttypes.IDENTIFIER;
	}
	public keywords keyWord()
	{
		String uppercase = currentToken.toUpperCase();
		keywords keywordEnum = keywords.valueOf(uppercase);
		return keywordEnum;
	}
	public char symbol()
	{
		return currentToken.charAt(0);
	}
	public String identifier()
	{
		return currentToken;
	}
	public String getCT()
	{
		if(tokenType().toString().equals("STRING_CONST"))
			return stringVal();
		else if(currentToken.equals("<"))
			return "&lt;";
		else if(currentToken.equals(">"))
			return "&gt;";
		return currentToken;
	}
	public int intVal()
	{
		return Integer.parseInt(currentToken);
	}
	public String stringVal()
	{
		return currentToken.substring(1, currentToken.length()-1);
	}
}
