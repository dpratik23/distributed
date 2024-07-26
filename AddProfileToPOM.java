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
            File inputFile = new File("/mnt/data/file-aHvE62u5cRIeHVBpuHTQhsTJ");
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
            profileId.appendChild(doc.createTextNode("coverage"));
            profile.appendChild(profileId);

            Element activation = doc.createElement("activation");
            Element activeByDefault = doc.createElement("activeByDefault");
            activeByDefault.appendChild(doc.createTextNode("true"));
            activation.appendChild(activeByDefault);
            profile.appendChild(activation);

            Element build = doc.createElement("build");
            Element plugins = doc.createElement("plugins");

            // Plugin definition
            Element plugin = doc.createElement("plugin");

            Element groupId = doc.createElement("groupId");
            groupId.appendChild(doc.createTextNode("org.jacoco"));
            plugin.appendChild(groupId);

            Element artifactId = doc.createElement("artifactId");
            artifactId.appendChild(doc.createTextNode("jacoco-maven-plugin"));
            plugin.appendChild(artifactId);

            Element executions = doc.createElement("executions");

            // Execution 1
            Element execution1 = doc.createElement("execution");
            Element execId1 = doc.createElement("id");
            execId1.appendChild(doc.createTextNode("prepare-agent"));
            execution1.appendChild(execId1);

            Element goals1 = doc.createElement("goals");
            Element goal1 = doc.createElement("goal");
            goal1.appendChild(doc.createTextNode("prepare-agent"));
            goals1.appendChild(goal1);
            execution1.appendChild(goals1);

            // Execution 2
            Element execution2 = doc.createElement("execution");
            Element execId2 = doc.createElement("id");
            execId2.appendChild(doc.createTextNode("report"));
            execution2.appendChild(execId2);

            Element goals2 = doc.createElement("goals");
            Element goal2 = doc.createElement("goal");
            goal2.appendChild(doc.createTextNode("report"));
            goals2.appendChild(goal2);
            execution2.appendChild(goals2);

            // Add executions to plugin
            executions.appendChild(execution1);
            executions.appendChild(execution2);
            plugin.appendChild(executions);

            // Add plugin to plugins
            plugins.appendChild(plugin);

            // Add plugins to build
            build.appendChild(plugins);

            // Add build to profile
            profile.appendChild(build);

            // Add profile to profiles
            profiles.appendChild(profile);

            // Write the updated document to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("/mnt/data/file-aHvE62u5cRIeHVBpuHTQhsTJ"));
            transformer.transform(source, result);

            System.out.println("Profile added to POM file successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
