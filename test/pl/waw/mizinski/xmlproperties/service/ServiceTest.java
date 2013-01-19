package pl.waw.mizinski.xmlproperties.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import pl.waw.mizinski.xmlproperties.properties.PropertiesFile;
import pl.waw.mizinski.xmlproperties.properties.Section;

public class ServiceTest
{
	PropertiesService service = PropertiesService.getInstance();
	PropertiesObjectsFactory factory = PropertiesObjectsFactory.getInstance();

	@Test
	public void shouldReadFromFile() throws Exception
	{
		PropertiesFile propertiesFile = service.getPropertiesFile(new File(
				"test/pl/waw/mizinski/xmlproperties/properties.xml"));
		List<Section> sections = propertiesFile.getSections();
		assertEquals(4, sections.size());
		Section section = sections.get(0);
		assertEquals("section1", section.getName());
		assertEquals(4, section.getProperties().size());
		assertEquals("value1", section.getPropertyValue("name1"));
		assertEquals("value2", section.getPropertyValue("name2"));
		assertNull(section.getPropertyValue("name3"));
		assertNull(section.getPropertyValue("name4"));
		section = sections.get(1);
		assertEquals(2, section.getProperties().size());
		assertEquals("value1", section.getPropertyValue("name1"));
		assertEquals("value5", section.getPropertyValue("name5"));
		assertTrue(sections.get(2).isEmpty());
		assertTrue(sections.get(3).isEmpty());
	}

	@Test
	public void shouldSerializePropertiesFile() throws Exception
	{
		String expectedContent = "<?xml version=\"1.0\" encoding=\"ASCII\" ?><properties ><section name=\"section1\" ><property name=\"name1\" >value1</property><property name=\"name2\" >value2</property><property name=\"name3\" /><property name=\"name4\" /></section><section name=\"section2\" ><property name=\"name1\" >value1</property><property name=\"name5\" >value5</property></section><section name=\"section3\" /><section name=\"section4\" /></properties>";
		PropertiesFile propertiesFile = service.getPropertiesFile(new File(
				"test/pl/waw/mizinski/xmlproperties/properties.xml"));
		String content = service.serailizePropertiesFile(propertiesFile);
		System.out.println(content);
		assertEquals(expectedContent, content);
	}

	@Test
	public void shouldCreateEmptyPrpertiesFile() throws Exception
	{
		String expectedContent = "<?xml version=\"1.0\" encoding=\"ASCII\" ?><properties ><section name=\"sekcja1\" ><property name=\"test\" >true</property></section></properties>";
		PropertiesFile propertiesFile = factory.createEmptyPropertiesFile();
		Section section = factory.createEmptySection("sekcja1");
		section.setProperty("test", "true");
		propertiesFile.addSection(section);
		String content = service.serailizePropertiesFile(propertiesFile);
		System.out.println(content);
		assertEquals(expectedContent, content);
	}

	@Test
	public void shouldWriteToFile() throws Exception
	{
		PropertiesFile propertiesFile = service.getPropertiesFile(new File(
				"test/pl/waw/mizinski/xmlproperties/properties.xml"));
		service.savePropertiesFile(propertiesFile, new File(
				"test/pl/waw/mizinski/xmlproperties/temp.xml"));
		PropertiesFile propertiesFile2 = service.getPropertiesFile(new File(
				"test/pl/waw/mizinski/xmlproperties/properties.xml"));
		assertEquals(service.serailizePropertiesFile(propertiesFile), service.serailizePropertiesFile(propertiesFile2));
	}
}
