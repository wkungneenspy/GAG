package activeWorkspace.auteur;

import java.io.File;
import java.io.FileOutputStream;

import generated.Doc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class TestJAXB {
public static void main(String[] args) throws Exception{
	JAXBContext jc = JAXBContext.newInstance("generated");
	Unmarshaller unmarshaller = jc.createUnmarshaller();
	Doc expr = (Doc)unmarshaller.unmarshal(new File("/media/willyk/OS/Users/Kengne willy/Documents/School/These/ImplementationThese/trang/artefact.xml"));
	
	jc = JAXBContext.newInstance("generated");
	Marshaller marshaller = jc.createMarshaller();
	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
	
	marshaller.marshal(expr,new FileOutputStream("/media/willyk/OS/Users/Kengne willy/Documents/School/These/ImplementationThese/trang/jaxbOutput.xml"));
}
}