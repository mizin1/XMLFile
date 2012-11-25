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
	
	Attribute getChildAttribute (String name) throws IllegalArgumentException;
	
	Attribute getChildAttribute (String name, int number) throws IllegalArgumentException;
	
	String getValue() throws NoSuchElementException;
	
	String getValue( String attribute ) throws IllegalArgumentException;
}
