package pl.waw.mizinski.xml;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public interface Attribute
{
	boolean isComplexAttribute();
	
	String getTag(String name);
	
	Map<String, String> getTags();
	
	List<String> getChildAttributesNames();

	int childAttributesCount(String name);
	
	Attribute getChildAttribute (String name) ;
	
	Attribute getChildAttribute (String name, int number);
	
	String getValue();
	
	String getValue( String attribute );
}
