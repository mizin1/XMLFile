package pl.waw.mizinski.xmlproperties.utils;

public class Utils
{
	public static String formatStringToXML(String input)
	{
		return input.replaceAll("&", "&amp").replaceAll("<", "&lt").
				replaceAll(">", "&gt").replaceAll("\"", "&quot").replaceAll("'", "&apos");
	}
}
