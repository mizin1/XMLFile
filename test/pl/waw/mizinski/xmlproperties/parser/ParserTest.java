package pl.waw.mizinski.xmlproperties.parser;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class ParserTest
{
	@Test
	public void test() throws Exception
	{
		List<Integer> list= Arrays.asList(1,2);
		Iterator<Integer> it = list.iterator();
		it.next();
		it.next();
		it.next();
	}
	
}
