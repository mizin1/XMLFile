package pl.waw.mizinski.xmlproperties.parser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.waw.mizinski.xmlproperties.exceptions.XMLParseException;
import pl.waw.mizinski.xmlproperties.lexer.token.AttributeName;
import pl.waw.mizinski.xmlproperties.lexer.token.AttributeValue;
import pl.waw.mizinski.xmlproperties.lexer.token.CloseTag;
import pl.waw.mizinski.xmlproperties.lexer.token.Equals;
import pl.waw.mizinski.xmlproperties.lexer.token.OpenEndTag;
import pl.waw.mizinski.xmlproperties.lexer.token.OpenStartTag;
import pl.waw.mizinski.xmlproperties.lexer.token.Prolog;
import pl.waw.mizinski.xmlproperties.lexer.token.Text;
import pl.waw.mizinski.xmlproperties.lexer.token.Token;
import pl.waw.mizinski.xmlproperties.xml.XMLFile;

public class ParserTest
{
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void testParser() throws Exception
	{
		String expectedContent = "<?xml version=\"1.0\" encoding=\"ASCII\" ?><element name=\"value\" >text</element>";
		Prolog t1 = new Prolog();
		OpenStartTag t2 = new OpenStartTag();
		t2.setValue("element");
		AttributeName t3 = new AttributeName();
		t3.setValue("name");
		Equals t4 = new Equals();
		AttributeValue t5 = new AttributeValue();
		t5.setValue("value");
		CloseTag t6 = new CloseTag();
		Text t7 = new Text();
		t7.setValue("text");
		OpenEndTag t8 = new OpenEndTag();
		t8.setValue("element");
		CloseTag t9 = new CloseTag();
		List<Token> tokens = Arrays.asList(t1, t2, t3, t4, t5, t6,t7,t8,t9);
		Parser parser = new Parser(tokens);
		XMLFile xml = parser.getXMLFile();
		System.out.println(xml);
		assertEquals(expectedContent, xml.getContent());
	}
	
	@Test
	public void shouldRaiseException() throws Exception
	{
		expectedException.expect(XMLParseException.class);
		Prolog t1 = new Prolog();
		OpenStartTag t2 = new OpenStartTag();
		t2.setValue("element");
		AttributeName t3 = new AttributeName();
		t3.setValue("name");
		Equals t4 = new Equals();
		AttributeValue t5 = new AttributeValue();
		t5.setValue("value");
		CloseTag t6 = new CloseTag();
		Text t7 = new Text();
		t7.setValue("text");
		OpenEndTag t8 = new OpenEndTag();
		t8.setValue("element");
		List<Token> tokens = Arrays.asList(t1, t2, t3, t4, t5, t6,t7,t8);
		new Parser(tokens);
	}
	
}
