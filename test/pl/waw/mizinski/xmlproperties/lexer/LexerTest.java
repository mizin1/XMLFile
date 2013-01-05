package pl.waw.mizinski.xmlproperties.lexer;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;

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

public class LexerTest
{
	@Test
	public void testLexer() throws XMLParseException
	{
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><element name=\"value\">text</element>";
		Lexer lexer = new Lexer(xml);
		List<Token> tokens = lexer.getTokens();
		assertEquals(9, tokens.size());
		assertEquals(Prolog.class, tokens.get(0).getClass());
		assertEquals(OpenStartTag.class, tokens.get(1).getClass());
		assertEquals("element", tokens.get(1).getValue());
		assertEquals(AttributeName.class, tokens.get(2).getClass());
		assertEquals("name", tokens.get(2).getValue());
		assertEquals(AttributeValue.class, tokens.get(4).getClass());
		assertEquals(Equals.class, tokens.get(3).getClass());
		assertEquals("value", tokens.get(4).getValue());
		assertEquals(CloseTag.class, tokens.get(5).getClass());
		assertEquals(Text.class, tokens.get(6).getClass());
		assertEquals("text", tokens.get(6).getValue());
		assertEquals(OpenEndTag.class, tokens.get(7).getClass());
		assertEquals("element", tokens.get(7).getValue());
		assertEquals(CloseTag.class, tokens.get(8).getClass());
	}

	@Test
	public void shouldRaiseException(){
		try{
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>rihgofeiuhvnuieh";
			Lexer lexer = new Lexer(xml);
			fail();
		}
		catch (XMLParseException e)
		{
			e.printStackTrace();
		}
	}
}
