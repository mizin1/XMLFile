package pl.waw.mizinski.xmlproperties.main;

import pl.waw.mizinski.xmlproperties.properties.PropertiesFile;
import pl.waw.mizinski.xmlproperties.properties.Section;

public class Main
{
	public static void main(String[] args)
	{
		System.out.println("Hello world!");
	}
	
	void test(PropertiesFile file, Section section)
	{
		file.removeSection(section);
	}
}
