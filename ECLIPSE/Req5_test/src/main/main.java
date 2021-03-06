package main;

import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class main {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		System.out.println(cgi_lib.Header());
		
		 
		GetFile gf = new GetFile();
		try {
			gf.cloneAndCopyFiles();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	
		
		
		Hashtable form_data = cgi_lib.ReadParse(System.in);
		
		String regiao = (String)(form_data.get("regiao"));
		
		System.out.println("<hr>");
		System.out.println(regiao);
		System.out.println("</hr>");
		
		
		int minInfecoes = (Integer)(form_data.get("infections_min"));
        int maxInfecoes = (Integer)(form_data.get("infections_max"));

        int minInternamentos = (Integer)(form_data.get("hospitalizations_min"));
        int maxInternamentos = (Integer)(form_data.get("hospitalizations_max"));

        int minTestes = (Integer)(form_data.get("testes_min"));
        int maxTestes = (Integer)(form_data.get("testes_max"));
        
        int operator1;
        int operator2;

        if (form_data.get("and_or_non_1").equals("and")) {

            operator1 = 0;
        }

        else if (form_data.get("and_or_non_1").equals("or")) {

            operator1 = 1;
        }

        else if (form_data.get("and_or_non_1").equals("non")) {

            operator1 = 2;
        }

        if (form_data.get("and_or_non_2").equals("and")) {

            operator2 = 0;
        }

        else if (form_data.get("and_or_non_2").equals("or")) {

            operator2 = 1;
        }

        else if (form_data.get("and_or_non_2").equals("non")) {

            operator2 = 2;
        }
        
        File inputFile = new File("ESII1920/covid19spreading.rdf");    	      	  
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();         
        
        String query = "/RDF/NamedIndividual/@*";
        System.out.println("Query para obter a lista das regi�es: " + query);
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        XPathExpression expr = xpath.compile(query);         
        NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nl.getLength(); i++) {
       	 System.out.println(StringUtils.substringAfter(nl.item(i).getNodeValue(), "#"));
        }

	}
	

}
