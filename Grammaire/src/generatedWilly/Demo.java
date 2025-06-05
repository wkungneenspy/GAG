package generatedWilly;

import java.io.File;
import javax.xml.bind.*;

public class Demo {

    public static void main(String[] args) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(A.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File xml = new File("/home/kengne/Downloads/trang-20091111/testGenerationXSD/a.xml");
        A a = (A) unmarshaller.unmarshal(xml);

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(a, System.out);
    }

}