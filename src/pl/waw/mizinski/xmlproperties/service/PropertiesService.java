package pl.waw.mizinski.xmlproperties.service;

public class PropertiesService
{
	private PropertiesService()
	{
	}

	private static PropertiesService service = new PropertiesService();

	public static PropertiesService getInstance()
	{
		return service;
	}
	//FIXME
}
