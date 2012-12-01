package pl.waw.mizinski.xml;

import java.util.List;
import java.util.Map;

public interface XMLElement
{
	String getName();
	
	boolean isComplexElement();
	
	boolean isEmty();
	
	String getAttribute(String name);
	
	Map<String, String> getAttrinutes();
	
	List<String> getChildElementsNames();
	
	List<XMLElement> getChildElements();

	int getChildElementsCount(String name);
	
	XMLElement getChildElement (String name);
	
	XMLElement getChildElement (String name, int number);
	
	String getValue();
	
	String getChildElementValue( String attribute );
	
	String getContent();
}
