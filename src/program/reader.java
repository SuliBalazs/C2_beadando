package program;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

public class reader {

    public static ArrayList<adatok> readCardFromXml(String filepath) {
        ArrayList<adatok> adat = new ArrayList<>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filepath);

            Element rootElement = document.getDocumentElement();

            NodeList childNodeList = rootElement.getChildNodes();
            Node node;
            for (int i = 0; i < childNodeList.getLength(); i++) {
                node = childNodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList childNodesOfCardTag = node.getChildNodes();
                    String nev = "", memoria = "", ar = "", marka = "";
                    for (int j = 0; j < childNodesOfCardTag.getLength(); j++) {
                        Node childNodeOfCardTag = childNodesOfCardTag.item(j);
                        if (childNodeOfCardTag.getNodeType() == Node.ELEMENT_NODE) {
                            switch (childNodeOfCardTag.getNodeName()) {
                                case "nev" -> nev = childNodeOfCardTag.getTextContent();
                                case "memoria" -> memoria = childNodeOfCardTag.getTextContent();
                                case "ar" -> ar = childNodeOfCardTag.getTextContent();
                                case "marka" -> marka = childNodeOfCardTag.getTextContent();
                            }
                        }
                    }
                    adat.add(new adatok(nev, Integer.parseInt(memoria), Integer.parseInt(ar), Marka.valueOf(marka)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adat;
    }
}
