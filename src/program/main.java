package program;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String filepath = "src/main/resources/adatok.xml";
        ArrayList<adatok> adatlist = reader.readCardFromXml(filepath);
        menuProcessing(adatlist);
        saveCardToXml(adatlist, filepath);
    }

    private static void menuProcessing(ArrayList<adatok> adatlist) {
        int choice = -1;
        while (choice != 0) {
            System.out.println("1 - Adat/ok listázása\r\n2 - Új kártya felvétle\r\n3 - Kártya adatainak módosítása\r\n" +
                    "4 - Kártya törlése\r\n0 - Kilépés");
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Not valid option.");
            }
            scanner.nextLine();
            switch (choice) {
                case 1 -> listCard(adatlist);
                case 2 -> addNewCard(adatlist);
                case 3 -> modifyCard(adatlist);
                case 4 -> deleteCard(adatlist);
            }
        }
    }

    private static void deleteCard(ArrayList<adatok> adatlist) {
        System.out.print("Adja meg a kártya nevét amit törölni seretne : ");
        try {
            adatlist.remove(findCard(adatlist, scanner.nextLine()));
            System.out.println("Az elem törlése sikeresen megtörtént");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void modifyCard(ArrayList<adatok> adatlist) {
        System.out.print("Adja meg a kártya nevét amit módosítani szeretne: ");
        try {
            adatok adat = findCard(adatlist, scanner.nextLine());
            adatlist.set(adatlist.indexOf(adat), new adatok(adat.getNev(), inputMemoria(), inputAr(), inputMarka()));
            System.out.println("Az adatmódosítás skikeresen megtörtént");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static adatok findCard(ArrayList<adatok> adatlist, String nev)
            throws IllegalArgumentException {
        for (adatok adat : adatlist) {
            if (adat.getNev().equals(nev)) {
                return adat;
            }
        }
        throw new IllegalArgumentException("nincs ilyen nevű kártya: " + nev);
    }

    private static void addNewCard(ArrayList<adatok> adatlist) {
        adatlist.add(new adatok(inputName(), inputMemoria(), inputAr(), inputMarka()));
        System.out.println(" ");
        System.out.println("Az új Kártyafelvétel sikeres volt.");
    }

    private static String inputName() {
        System.out.print("Adja meg a kártya nevét: ");
        return scanner.nextLine();
    }

    private static int inputAr() {
        int ar=-3;
        while (ar <= -1) {
            System.out.print("Adja meg a kártya Árát: ");
            try {
                ar = scanner.nextInt();
                if (ar <= -1) {
                    System.out.println("nem valós adatot adott meg (nem lehet negatív).");
                }
            } catch (Exception e) {
                System.out.println("Az Árnak Integer típusunak(egész számnak) kell lennie.");
            }
            scanner.nextLine();
        }


        return ar;
    }

    private static int inputMemoria() {
        int memoria = 0;
        while (memoria <= 0 || memoria > 100) {
            System.out.print("Adja meg a kártya memóriájának méretét GB-ban: ");
            try {
                memoria = scanner.nextInt();
                if (memoria < 0 || memoria > 100) {
                    System.out.println("nem valós adatot adott meg (0-100GB között próbálja).");
                }
            } catch (Exception e) {
                System.out.println("Memoriának Integer(egész számank) típusunak kell lennie.");
            }
            scanner.nextLine();
        }
        return memoria;
    }

    private static Marka inputMarka() {


        boolean vane=true;
        while (vane) {
            Marka marka = null;
            try {
                String markaString;
                System.out.print("üsse be a vidókártya márkáját : ");
                markaString = scanner.nextLine().toUpperCase();
                marka = Marka.valueOf(markaString);
                vane=false;

            } catch (IllegalArgumentException e) {
                System.out.println("Nincsen ilyen márka a rendszerben.(válasszon: Nvidia/Geforce/Egyeb)");
            }
            if(marka!=null){
                return marka;
            }
        }
        return Marka.NVIDIA;
    }

    private static void listCard(ArrayList<adatok> adatlist) {
        System.out.println(adatlist);
    }

    public static void saveCardToXml(ArrayList<adatok> adatlist, String filepath) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element rootElement = document.createElement("adatok");
            document.appendChild(rootElement);

            for (adatok adat : adatlist) {
                Element cardElement = document.createElement("adat");
                rootElement.appendChild(cardElement);
                createChildElement(document, cardElement, "nev", adat.getNev());
                createChildElement(document, cardElement, "memoria", String.valueOf(adat.getMemoria()));
                createChildElement(document, cardElement, "ar", String.valueOf(adat.getAr()));
                createChildElement(document, cardElement, "marka", adat.getMarka());
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(filepath));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createChildElement(Document document, Element parent, String tagName, String text) {
        Element element = document.createElement(tagName);
        element.setTextContent(text);
        parent.appendChild(element);
    }
}
