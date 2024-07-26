import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AddProfileToPOM {
    public static void main(String[] args) {
        try {
            // Load the existing pom.xml file
            File inputFile = new File("path/to/pom.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Create the profiles element if it doesn't exist
            Element profiles = null;
            if (doc.getElementsByTagName("profiles").getLength() == 0) {
                profiles = doc.createElement("profiles");
                doc.getDocumentElement().appendChild(profiles);
            } else {
                profiles = (Element) doc.getElementsByTagName("profiles").item(0);
            }

            // Create a new profile
            Element profile = doc.createElement("profile");
            Element profileId = doc.createElement("id");
            profileId.appendChild(doc.createTextNode("new-profile"));
            profile.appendChild(profileId);

            // Add build section to the profile
            Element build = doc.createElement("build");
            Element plugins = doc.createElement("plugins");

            // Example plugin in the profile
            Element plugin = doc.createElement("plugin");
            Element groupId = doc.createElement("groupId");
            groupId.appendChild(doc.createTextNode("org.example"));
            plugin.appendChild(groupId);

            Element artifactId = doc.createElement("artifactId");
            artifactId.appendChild(doc.createTextNode("example-plugin"));
            plugin.appendChild(artifactId);

            Element version = doc.createElement("version");
            version.appendChild(doc.createTextNode("1.0.0"));
            plugin.appendChild(version);

            plugins.appendChild(plugin);
            build.appendChild(plugins);
            profile.appendChild(build);

            profiles.appendChild(profile);

            // Write the updated document to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("path/to/pom.xml"));
            transformer.transform(source, result);

            System.out.println("Profile added to POM file successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
