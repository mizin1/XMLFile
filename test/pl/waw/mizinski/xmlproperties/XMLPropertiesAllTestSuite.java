package pl.waw.mizinski.xmlproperties;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import pl.waw.mizinski.xmlproperties.lexer.LexerTest;
import pl.waw.mizinski.xmlproperties.parser.ParserTest;
import pl.waw.mizinski.xmlproperties.service.ServiceTest;
import pl.waw.mizinski.xmlproperties.xml.XMLTest;

@RunWith(Suite.class)
@SuiteClasses(value = { 
		LexerTest.class, 
		ParserTest.class, 
		XMLTest.class, 
		ServiceTest.class })
public class XMLPropertiesAllTestSuite
{
}
