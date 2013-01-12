package pl.waw.mizinski.xmlproperties.xml;

public class XMLFileImpl implements XMLFile
{
	XMLElement rootElement;
	
	@Override
	public XMLElement getRootElement()
	{
		return rootElement;
	}
	
	public void setRootElement (XMLElement rootElement)
	{
		this.rootElement = rootElement;
	}

	@Override
	public String getContent()
	{
		return "<?xml version=\"1.0\" encoding=\"ASCII\" ?>"+rootElement;
	}
	
	@Override
	public String toString()
	{
		return getContent();
	}

}
