package activeWorkspace.auteur;

import javax.xml.bind.*;

import classeUniverselle.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import generated.*;

public class ZXMLtoJAVA {
	private Artefact artefactComplet = new Artefact();
	
	public Artefact getArtefactComplet() {
		return artefactComplet;
	}

	public void setArtefactComplet(Artefact artefactComplet) {
		this.artefactComplet = artefactComplet;
	}


	public static void main(String[] args) throws Exception{
		ZXMLtoJAVA xml = new ZXMLtoJAVA();
		
		/*JAXBContext jc = JAXBContext.newInstance("generated");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Doc expr =(Doc)unmarshaller.unmarshal(new File("/home/kengne/Downloads/trang-20091111/artefact.xml"));
		*/	
		Doc expr = xml.xmltoJava("/home/kengne/Downloads/trang-20091111/artefact.xml");
		
		//Traitons soient les donnees, soit les Artefacts
		/*Doc res = new Doc();
		res.setArtefact(xml.lireXML(expr.getArtefact()));
				
		JAXBContext jc = JAXBContext.newInstance("generated");
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		marshaller.marshal(res,new FileOutputStream("/home/kengne/Downloads/trang-20091111/jaxbOutput.xml"));*/
		
		Artefact newArt= new Artefact();
		
		Address add = new Address();
		   add.setNom("Accuse_Reception");
		   add.setUrl("www.google.com1");
		   add.setPort(Integer.parseInt("201"));
		newArt.setAddress(add);
		
		Data data = new Data();
			Elt e = new Elt();
			e.setName("NewNewName");
			e.setType("NewNewType");	
		data.setElt(e);
	    
	    List lo1 = new ArrayList<Object>();
	    lo1.add(data);
		newArt.setArtefactOrData(lo1);//Ajout des donnees
		
	    xml.javatoXml(expr, "/home/kengne/Downloads/trang-20091111/jaxbOutput.xml", newArt);
	    
	    
	    xml.recupererUnArtifactComplet(expr.getArtefact());
	    Artefact art = xml.getArtefactComplet();
	    xml.parcoursDunArtefact(art);
	}
	
	

	public Doc xmltoJava(String cheminFichier) throws JAXBException{
		JAXBContext jc = JAXBContext.newInstance("generated");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Doc expr =(Doc)unmarshaller.unmarshal(new File("/home/kengne/Downloads/trang-20091111/artefact.xml"));
	
		return expr;
	}
	
	public void javatoXml(Doc expr,String cheminFichier,Artefact newArt) throws JAXBException, FileNotFoundException{
		ZXMLtoJAVA x = new ZXMLtoJAVA();
		//Traitons soient les donnees, soit les Artefacts
		Doc res = new Doc();
		res.setArtefact(x.parcourDocXML(expr.getArtefact(),newArt));
				
		JAXBContext jc = JAXBContext.newInstance("generated");
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
		marshaller.marshal(res,new FileOutputStream("/home/kengne/Downloads/trang-20091111/jaxbOutput.xml"));
	}
	
	//Ecrire des fonctions generique qui permet de boucler sur les elements.
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public Artefact parcourDocXML(Artefact a, Artefact newArt){
		Artefact art = new Artefact();
		List l = new ArrayList<Object>();
				
		for( Object o1 : a.getArtefactOrData()){
			Data d = null;
			Artefact a1 = null;
			try{
				//traitons les donnees
				d = (Data) o1;		
				////////////////////////////////////////////////////////
				if(a.getAddress() != null){
					List lo = new ArrayList<Object>();
					lo.add(d);
					lo.add(newArt);
					
					Artefact aw = new Artefact();
					aw.setArtefactOrData(lo);
					return aw;
				}
				///////////////////////////////////////////////////
			}catch(ClassCastException e1){
				a1 = (Artefact) o1;
			}finally{
				if(d !=null ){
				    l.add(d);
				}
				if(a1 != null){
				    l.add(parcourDocXML(a1,newArt));
				}
			}	
		}	
		art.setArtefactOrData(l);
		return art;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Artefact recupererUnArtifactComplet(Artefact a){
		Artefact art = new Artefact();
		List l = new ArrayList<Object>();
				
		for( Object o1 : a.getArtefactOrData()){
			Data d = null;
			Artefact a1 = null;
			try{
				//traitons les donnees
				d = (Data) o1;		
				////////////////////////////////////////////////////////
				if(a.getAddress() != null){
					setArtefactComplet(a);
					return a;
				}
				///////////////////////////////////////////////////
			}catch(ClassCastException e1){
				a1 = (Artefact) o1;
			}finally{
				if(d !=null ){
					l.add(d);
				}
				if(a1 != null){
				    l.add(recupererUnArtifactComplet(a1));
				}
			}	
		}	
		art.setArtefactOrData(l);
		return art;
	}
	
	@SuppressWarnings("unused")
	public void parcoursDunArtefact(Artefact art){
		for( Object o1 : art.getArtefactOrData()){
			Data d = null;
			Artefact a1 = null;
			try{
				//traitons les donnees
				d = (Data) o1;		
				
				////////////////////////////////////////////////////////
				if(art.getAddress() != null){
					System.out.println("Artefact");
					System.out.println("Data ");
						System.out.println("--> "+d.getElt().getName());
						System.out.println("--> "+d.getElt().getType());
					System.out.println("/Date");
					
					System.out.println("Address ");
						System.out.println("--> "+art.getAddress().getNom());
						System.out.println("--> "+art.getAddress().getUrl());
					System.out.println("/Address");
				System.out.println("/Artefact"); 
				}
				///////////////////////////////////////////////////
				
			}catch(ClassCastException e1){
				a1 = (Artefact) o1;
			}finally{
							
			}	
		}
	}
	/**
	 * 
	 * @param artefact ne contient pas d'autre artefact, il contient uniquement le data et l'address
	 * @return
	 */
	public Message construirArtifact(Artefact artefact){
		Message message = new Message();
		for( Object o1 : artefact.getArtefactOrData()){
			Data d = null;
			Artefact a1 = null;
			try{
				//traitons les donnees
				d = (Data) o1;		
				
				////////////////////////////////////////////////////////
				if(artefact.getAddress() != null){
					message.setData(null); //Champs a etendre et a definir.
					
					message.setPortLocal(d.getElt().getType()); //Mettons pour l'instant le port local
				
					message.setNom(d.getElt().getName());
						
					message.setPortDistant(artefact.getAddress().getPort()+"");
					
					System.out.println("/Artefact"); 
				}
				///////////////////////////////////////////////////
				
			}catch(ClassCastException e1){
				a1 = (Artefact) o1;
			}finally{
							
			}	
		}
		return message;
	}
}

