package com.org.testar_final;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
		System.out.println(cgi_lib.Header());
		    	
    	GetFile gf = new GetFile();
		try {
			gf.cloneAndCopyFiles();
		} catch (SAXException e) {
			e.printStackTrace();
		}
    	
    	File inputFile = new File("ESII1920/covid19spreading.rdf");    	      	  
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Document doc = null;
		try {
			doc = dBuilder.parse(inputFile);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        doc.getDocumentElement().normalize();   
    	
    	
    	ArrayList<String> regioes = new ArrayList<String>();
    	
		
		Hashtable form_data = cgi_lib.ReadParse(System.in);
		
		if(form_data.get("lisboa")!=null) {
			regioes.add("Lisboa");
		}
		if(form_data.get("centro")!=null) {
			regioes.add("Centro");
		}
		if(form_data.get("norte")!=null) {
			regioes.add("Norte");
		}
		if(form_data.get("algarve")!=null) {
			regioes.add("Algarve");
		}
		if(form_data.get("alentejo")!=null) {
			regioes.add("Alentejo");
		}
		
        String and1 = (String)(form_data.get("and1"));
        String or1 = (String)(form_data.get("or1"));
        String non1 = (String)(form_data.get("non1"));
        
		
		String minInfecoes = (String)(form_data.get("infections_min"));
		String maxInfecoes = (String)(form_data.get("infections_max"));

		String minInternamentos = (String)(form_data.get("hospitalizations_min"));
		String maxInternamentos = (String)(form_data.get("hospitalizations_max"));

		String minTestes = (String)(form_data.get("tests_min"));
		String maxTestes = (String)(form_data.get("tests_max"));
		
		if(regioes.size()!=0) {
			String pagHTML = array2HTML(regioes, doc);
			createHTMLFile(pagHTML);
		}
		
		System.out.println(regioes.size());
		
    }
    
private static void createTable_regioes(ArrayList<String> res, Document doc) throws FileNotFoundException, XPathExpressionException {
		int i = 0;
		int j = 0;
        PrintWriter pw = new PrintWriter (new File ("../../wp-content/html/req5.html"));
        
		pw.print("<html>" +
			       "<body>" +
			       "<table border ='1'>" +
			       "<tr>" +
			       "<td>Region</td>" +
			       "<td>Infections</td>" +
			       "<td>Hospitalizations</td>" +
			       "<td>Tests</td>" +
				"</tr>");
		
		while (i<res.size()) {
			j=0;
			pw.print("<tr>");
			while(j<4) {
				if(j==0) {
				pw.print("<td>");
				pw.print(res.get(i));
				pw.print("</td>");
				}
				else if(j==1) {
					String query = "//*[contains(@about,'Algarve')]/Infecoes/text()";
					XPathFactory xpathFactory = XPathFactory.newInstance();
			        XPath xpath = xpathFactory.newXPath();
			        XPathExpression expr = xpath.compile(query);
			        expr = xpath.compile(query);
			        System.out.println(expr.evaluate(doc, XPathConstants.STRING));
				}
				else if(j==2) {
					
				}
				else if(j==3) {
					
				}
				j++;

			}
			pw.print("</tr>");
			i++;
		}
		
		pw.print("</table>" +
				"</body>" +
				"</html>"); 

		pw.close(); 
	}

public static String array2HTML(ArrayList<String> res, Document doc){
    StringBuilder html = new StringBuilder("<!DOCTYPE html>\r\n" +
            "<html>\r\n" +
            "<head>\r\n" +
            "<meta charset=\"ISO-8859-1\">\r\n" +
            "<title>Results</title>\r\n" +
            "</head>\r\n" +
            "<body>\r\n"  +
            "<table>\r\n");

    html.append("<th>" + "Region" + "</th>\r\n");
    html.append("<th>" + "Infections" + "</th>\r\n");
    html.append("<th>" + "Hospitalizations" + "</th>\r\n");
    html.append("<th>" + "Tests" + "</th>\r\n");
    String resultadoTeste;
    for(int i = 0; i < res.size(); i++){
        html.append("<tr>");
        html.append("<td>" + res.get(i) + "</td>\r\n");
        
        String query = "//*[contains(@about,'Algarve')]/Infecoes/text()";
		XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        XPathExpression expr = null;
		try {
			expr = xpath.compile(query);
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			expr = xpath.compile(query);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			html.append("<td>" + expr.evaluate(doc, XPathConstants.STRING) + "</td>\r\n");
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        html.append("</tr>");
    }
    html.append("</table>\r\n" +
            "</body>\r\n" +
            "</html>");
    return html.toString();

}


public static void createHTMLFile(String pagHTML) {
    String Path = "../../wp-content/html/req5.html";
    try {
        File myObj = new File(Path);
        if (myObj.createNewFile()) {
             writeToHTMLFile(pagHTML, Path);
        } else {
            myObj.delete();
            writeToHTMLFile(pagHTML, Path);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


public static void writeToHTMLFile(String pagHTML, String Path) {
    try {
          FileWriter myWriter = new FileWriter(Path);
          myWriter.write(pagHTML);
          myWriter.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
}
}
