package pl.waw.mizinski.xml;

import java.util.List;
import java.util.Map;

public interface Element
{
	boolean isComplexElement();
	
	String getAttribute(String name);
	
	Map<String, String> getAttrinutes();
	
	List<String> getChildElementsNames();

	int getChildElementsCount(String name);
	
	Element getChildElement (String name);
	
	Element getChildElement (String name, int number);
	
	String getValue();
	
	String getElementValue( String attribute );
	
	String getContent();
}
