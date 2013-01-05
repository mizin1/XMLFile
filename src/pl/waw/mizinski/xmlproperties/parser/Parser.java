package pl.waw.mizinski.xmlproperties.parser;

import java.util.List;

import pl.waw.mizinski.xmlproperties.lexer.token.Token;
import pl.waw.mizinski.xmlproperties.xml.XMLFile;

public class Parser
{
	private List<Token> tokens;
	
	private XMLFile xmlFile;
	
	public Parser(List<Token> tokens)
	{
		this.tokens = tokens;
		parse();
	}
	
	private void parse()
	{
		parseDocument();
		
	}

	private void parseDocument()
	{
		
		
	}

	public XMLFile getXMLFile(){
		return xmlFile;
	}
	
}
