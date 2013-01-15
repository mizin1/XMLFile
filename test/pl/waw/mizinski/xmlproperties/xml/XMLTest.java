package pl.waw.mizinski.xmlproperties.xml;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import pl.waw.mizinski.xmlproperties.lexer.Lexer;
import pl.waw.mizinski.xmlproperties.lexer.token.Token;
import pl.waw.mizinski.xmlproperties.parser.Parser;

public class XMLTest
{
	@Test
	public void shoudCreateXMLFileFromDiskFile() throws Exception
	{
		String xml = getFileContent("test/pl/waw/mizinski/xmlproperties/example2.xml");
		Lexer lexer = new Lexer(xml) ;
		List<Token> tokens = lexer.getTokens();
		Parser parser = new Parser(tokens);
		XMLFile file = parser.getXMLFile();
		System.out.println(file.getContent());
		assertEquals(136,file.getContent().length());
	}

	@Test
	public void shoudProcessSpecialCharacters() throws Exception
	{
		String xml = getFileContent("test/pl/waw/mizinski/xmlproperties/example1.xml");
		Lexer lexer = new Lexer(xml) ;
		List<Token> tokens = lexer.getTokens();
		Parser parser = new Parser(tokens);
		XMLFile file = parser.getXMLFile();
		System.out.println(file.getContent());
		assertEquals(97,file.getContent().length());
	}
	
	
	private String getFileContent(String fileName) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File(fileName));
		StringBuilder builder = new StringBuilder();
		while(scanner.hasNextLine())
		{
			builder.append(scanner.nextLine());
		}
		return builder.toString();
	}
}
