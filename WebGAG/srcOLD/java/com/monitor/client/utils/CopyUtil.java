package com.monitor.client.utils;

import java.util.ArrayList;
import java.util.List;






public class CopyUtil {
	
//	/*public static EquipementBean listeEquipementToListeEquipementBean(Equipement rs){
//		EquipementBean rbs= new EquipementBean();
//
//				copieEquipementToEquipementBean(rs, rbs);
//			
//		return rbs;		
//}*/
//	
/////////////////////////////////////////////Info EQUIPEMENT/////////////////////////////////////////////////	
//	public static List<Info> listeInfo_EquipementToListeInfo(List<Info_Equipement> rs){
//		List<Info> rbs=new ArrayList<Info>();
//		if(rs!=null){
//		for(int i=0;i<rs.size();i++){
//		rbs.add(new Info());
//		copieInfo_EquipementToInfo(rs.get(i), rbs.get(i));
//		}
//		}
//		return rbs;		
//	}
//
//	public static void copieInfo_EquipementToInfo(Info_Equipement d, Info db){
//	if(d==null)
//	return;
//	db.setDate(d.getdate());
//	db.setIdInfo_Equipement(d.getidInfo_Equipement());
//	//db.setSupp(d.getsupp());
//	//db.setValeur(d.getvaleur());
//	db.setNom(d.getNom());
//	}
//
//	
//	
/////////////////////////////////////////////Info EQUIPEMENT/////////////////////////////////////////////////	
//public static List<Info_EquipementBean> listeInfo_EquipementToListeInfo_EquipementBean(List<Info_Equipement> rs){
//	List<Info_EquipementBean> rbs=new ArrayList<Info_EquipementBean>();
//	if(rs!=null){
//	for(int i=0;i<rs.size();i++){
//	rbs.add(new Info_EquipementBean());
//	copieInfo_EquipementToInfo_EquipementBean(rs.get(i), rbs.get(i));
//	}
//	}
//	return rbs;		
//}
//
//public static void copieInfo_EquipementToInfo_EquipementBean(Info_Equipement d, Info_EquipementBean db){
//if(d==null)
//return;
//db.setDate(d.getdate());
//db.setIdInfo_Equipement(d.getidInfo_Equipement());
//db.setSupp(d.getsupp());
//db.setValeur(d.getvaleur());
//db.setNom(d.getNom());
//}
//	
/////////////////////////////////////////////EQUIPEMENT/////////////////////////////////////////////////	
//	public static List<EquipementBean> listeEquipementToListeEquipementBean(List<Equipement> rs){
//		List<EquipementBean> rbs=new ArrayList<EquipementBean>();
//		if(rs!=null){
//			for(int i=0;i<rs.size();i++){
//				rbs.add(new EquipementBean());
//				copieEquipementToEquipementBean(rs.get(i), rbs.get(i));
//			}
//		}
//		return rbs;		
//	}
//	
//	public static void copieEquipementToEquipementBean(Equipement d, EquipementBean db){
//		if(d==null)
//			return;
//		db.setAdr_ip(d.getadr_ip());
//		db.setIdEquipement(d.getidEquipement());
//		db.setNom(d.getnom());
//		db.setSite(d.getsite());
//		db.setType(d.gettype());
//	}
//
//////////////////////////////////////////PLATFORM////////////////////////////////////////////////////
//	public static List<PlatformBean> listePlatformToListePlatformBean(List<Platform> rs){
//		List<PlatformBean> rbs=new ArrayList<PlatformBean>();
//		if(rs!=null){
//			for(int i=0;i<rs.size();i++){
//				rbs.add(new PlatformBean());
//				copiePlatformToPlatformBean(rs.get(i), rbs.get(i));
//			}
//		}
//		return rbs;		
//	}	
//	public static void copiePlatformToPlatformBean(Platform d, PlatformBean db){
//		if(d==null)
//			return;
//		db.setIdPlatform(d.getidPlatform());
//		db.setNom(d.getnom());
//		db.setType(d.getType());
//		
//		//db.setListEquipement(null);
//		//db.setListEquipement(listeEquipementToListeEquipementBean(d.getequipementlist()));
//		//manque list des platformes
//	}
//	
//	public static PlatformBean platformToPlatformBean(Platform rs){
//		PlatformBean rbs= new PlatformBean();
//
//				copiePlatformToPlatformBean(rs, rbs);
//			
//		return rbs;		
//	}
/////////////////////////////////////////HISTORIQUE///////////////////////////////////////////////////
//	public static List<HistoriqueBean> listeHistoriqueToListeHistoriqueBean(List<Historique> rs){
//		List<HistoriqueBean> rbs=new ArrayList<HistoriqueBean>();
//		if(rs!=null){
//			for(int i=0;i<rs.size();i++){
//				rbs.add(new HistoriqueBean());
//				copieHistoriqueToHistoriqueBean(rs.get(i), rbs.get(i));
//			}
//		}
//		return rbs;		
//	}	
//	public static void copieHistoriqueToHistoriqueBean(Historique d, HistoriqueBean db){
//		if(d==null)
//			return;
//		//db.setIdHistorique(d.getidHistorique());
//		db.setInfo(d.getinfo());
//		db.setDate(d.getdate());
//	}
//	/* public static RoleBean listeRoleToListeRoleBean(Role rs){
//		RoleBean rbs= new RoleBean();
//
//				copieRoleToRoleBean(rs, rbs);
//			
//		return rbs;		
//	}
//	
//	public static void copieRoleToRoleBean(Role d, RoleBean db){
//		if(d==null)
//			return;
//		
//		db.setAdresse(d.getAdresse());
//		db.setCodeRole(d.getCodeRole());
//		db.setDateNaissance(d.getDateNaissance());
//		db.setEmail(d.getEmail());
//		db.setFonction(d.getFonction());
//		db.setGenre(d.getGenre());
//		db.setLieudeNaissance(d.getLieudeNaissance());
//		db.setMatricule(d.getMatricule());
//		db.setNationalite(d.getNationalite());
//		db.setNom(d.getNom());
//		db.setPrenoms(d.getPrenoms());
//		db.setTelephone(d.getTelephone());
//	}
//	
//	public static FicheInscriptionBean listeFicheInscriptionToListeFicheInscriptionBean(FicheInscription rs){
//		FicheInscriptionBean rbs= new FicheInscriptionBean();
//
//				copieFicheInscriptionToFicheInscriptionBean(rs, rbs);
//			
//		return rbs;		
//	}
//	
//	public static void copieFicheInscriptionToFicheInscriptionBean(FicheInscription d, FicheInscriptionBean db){
//		if(d==null)
//			return;
//		
//		db.setAnnee(d.getAnnee());
//		db.setDepartementOrigine(d.getDepartementOrigine());
//		db.setEmployeur(d.getEmployeur());
//		db.setNumFicheInscription(d.getNumFicheInscription());
//		db.setPhoto(d.getPhoto());
//		db.setPremiereLangue(d.getPremiereLangue());
//		db.setProfession(d.getProfession());
//		db.setProvinceOrigine(d.getProvinceOrigine());
//		db.setRedoublant(d.getRedoublant());
//	}
//	
//	
//	public static List<DossierInscriptionBean> listeDossierInscriptionToListeDossierInscriptionBean(List<DossierInscription> rs){
//		List<DossierInscriptionBean> rbs=new ArrayList<DossierInscriptionBean>();
//		if(rs!=null){
//			for(int i=0;i<rs.size();i++){
//				rbs.add(new DossierInscriptionBean());
//				copieDossierInscriptionToDossierInscriptionBean(rs.get(i), rbs.get(i));
//			}
//		}
//		return rbs;		
//	}
//	
//
//	
//	public static DossierMedicalBean listeDossierMedicalToDossierMedicalBean(DossierMedical rs){
//		DossierMedicalBean rbs=new DossierMedicalBean();
//
//				copieDossierMedicalToDossierMedicalBean(rs, rbs);
//			
//		return rbs;		
//	}
//	
//	public static void copieDossierMedicalToDossierMedicalBean(DossierMedical d, DossierMedicalBean db){
//		if(d==null)
//			return;
//
//		db.setDateConsultation(d.getDateConsultation());
//		db.setLieuConsultation(d.getLieuConsultation());
//		db.setNomMedecin(d.getNomMedecin());
//		db.setNumDossierMedical(d.getNumDossierMedical());
//		db.setObservations(d.getObservations());
//		
//	}	
//	
//	public static List<PieceJointeBean> listePieceJointeToListePieceJointeBean(List<PieceJointe> rs){
//		List<PieceJointeBean> rbs=new ArrayList<PieceJointeBean>();
//		if(rs!=null){
//			for(int i=0;i<rs.size();i++){
//				rbs.add(new PieceJointeBean());
//				copiePieceJointeToPieceJointeBean(rs.get(i), rbs.get(i));
//			}
//		}
//		return rbs;		
//	}
//	
//	public static void copiePieceJointeToPieceJointeBean(PieceJointe d, PieceJointeBean db){
//		if(d==null)
//			return;
//
//		db.setNaturePieceJointe(d.getNaturePieceJointe());
//		db.setNumPieceJointe(d.getNumPieceJointe());
//		db.setTypeMine(d.getTypeMine());
//		
//	}	
//	
//	public static FicheSocialeBean listeFicheSocialeToFicheSocialeBean(FicheSociale rs){
//		FicheSocialeBean rbs=new FicheSocialeBean();
//
//				copieFicheSocialeToFicheSocialeBean(rs, rbs);
//			
//		return rbs;		
//	}
//	
//	public static void copieFicheSocialeToFicheSocialeBean(FicheSociale d, FicheSocialeBean db){
//		if(d==null)
//			return;
//
//		db.setNumFicheSociale(d.getNumFicheSociale());
//		db.setCommentaire(d.getCommentaire());
//		db.setDateEtablissement(d.getDateEtablissement());
//		db.setNomSignataire(d.getNomSignataire());
//		
//	}
//	
//	public static DossierPreinscriptionBean listeDossierPreinscriptionToListeDossierPreinscriptionBean(DossierPreinscription rs){
//		DossierPreinscriptionBean rbs=new DossierPreinscriptionBean();
//
//				copieDossierPreinscriptionToDossierPreinscriptionBean(rs, rbs);
//			
//		return rbs;		
//	}
//	
//	public static DossierInscriptionBean listeDossierInscriptionToListeDossierInscriptionBean(DossierInscription rs){
//		DossierInscriptionBean rbs=new DossierInscriptionBean();
//
//				copieDossierInscriptionToDossierInscriptionBean(rs, rbs);
//			
//		return rbs;		
//	}
//	
//	public static void copieDossierInscriptionToDossierInscriptionBean(DossierInscription d, DossierInscriptionBean db){
//		if(d==null)
//			return;
//
//		db.setNumDossierIns(d.getNumDossierIns());
//		db.setCommentaireSCO(d.getCommentaireSCO());
//		db.setDateCreation(d.getDateCreation());
//		db.setDateEnregistrement(d.getDateEnregistrement());
//		db.setDateValidation(d.getDateValidation());
//		db.setEtatDossier(d.getEtatDossier());
//		db.setMatricule(d.getMatricule());
//		
//	}
//	
//	public static List<DossierPreinscriptionBean> listeDossierPreinscriptionToListeDossierPreinscriptionBean(List<DossierPreinscription> rs){
//		List<DossierPreinscriptionBean> rbs=new ArrayList<DossierPreinscriptionBean>();
//		if(rs!=null){
//			for(int i=0;i<rs.size();i++){
//				rbs.add(new DossierPreinscriptionBean());
//				copieDossierPreinscriptionToDossierPreinscriptionBean(rs.get(i), rbs.get(i));
//			}
//		}
//		return rbs;		
//	}
//	
//	public static void copieDossierPreinscriptionToDossierPreinscriptionBean(DossierPreinscription d, DossierPreinscriptionBean db){
//		if(d==null)
//			return;
//		
//		db.setCommentaireCOO(d.getCommentaireCOO());
//		db.setCommentaireSCO(d.getCommentaireSCO());
//		db.setDateCreation(d.getDateCreation());
//		db.setDateEnregistrement(d.getDateEnregistrement());
//		db.setDateValidation(d.getDateValidation());
//		db.setDateVerification(d.getDateVerification());
//		db.setEtatDossier(d.getEtatDossier());
//		db.setFiliere(d.getFiliere());
//		db.setMatricule(d.getMatricule());
//		db.setNumDossierPre(d.getNumDossierPre());
//		
//	}
//	
//	
//	public static FichePreinscriptionBean listeFichePreinscriptionToListeFichePreinscriptionBean(FichePreinscription rs){
//		FichePreinscriptionBean rbs= new FichePreinscriptionBean();
//
//				copieFichePreinscriptionToFichePreinscriptionBean(rs, rbs);
//			
//		return rbs;		
//	}
//	
//	public static void copieFichePreinscriptionToFichePreinscriptionBean(FichePreinscription d, FichePreinscriptionBean db){
//		if(d==null)
//			return;
//		
//		db.setActiviteCulturelle(d.getActiviteCulturelle());
//		db.setActiviteSportive(d.getActiviteSportive());
//		db.setAdresseParent(d.getAdresseParent());
//		db.setAutreActivite(d.getAutreActivite());
//		db.setDateObtentionDiplome(d.getDateObtentionDiplome());
//		db.setDepartementOrigine(d.getDepartementOrigine());
//		db.setDiplomePresente(d.getDiplomePresente());
//		db.setDiplomeVise(d.getDiplomeVise());
//		db.setFaculteSollicite(d.getFaculteSollicite());
//		db.setFiliereChoisie1(d.getFiliereChoisie1());
//		db.setFiliereChoisie2(d.getFiliereChoisie2());
//		db.setFiliereChoisie3(d.getFiliereChoisie3());
//		db.setHandicap(d.getHandicap());
//		db.setLieuObtentionDiplome(d.getLieuObtentionDiplome());
//		db.setMention(d.getMention());
//		db.setNiveauActuel(d.getNiveauActuel());
//		db.setNomMere(d.getNomMere());
//		db.setNomPere(d.getNomPere());
//		db.setNumFichePre(d.getNumFichePre());
//		db.setOptionChoisie(d.getOptionChoisie());
//		db.setPhoto(d.getPhoto());
//		db.setPremiereLangue(d.getPremiereLangue());
//		db.setProfessionMere(d.getProfessionMere());
//		db.setProfessionPere(d.getProfessionPere());
//		db.setProvince(d.getProvince());
//		db.setProvinceOrigine(d.getProvinceOrigine());
//		db.setSituationEmploi(d.getSituationEmploi());
//		db.setSituationFamiliale(d.getSituationFamiliale());
//		db.setVille(d.getVille());
//	}
//	
//	
//	public static List<UEBean> listeUEToListeUEBean(List<UE> rs){
//		List<UEBean> rbs=new ArrayList<UEBean>();
//		if(rs!=null){
//			for(int i=0;i<rs.size();i++){
//				rbs.add(new UEBean());
//				copieUEToUEBean(rs.get(i), rbs.get(i));
//			}
//		}
//		return rbs;		
//	}
//	
//	public static void copieUEToUEBean(UE d, UEBean db){
//		if(d==null)
//			return;
//		
//		db.setCodeUE(d.getCodeUE());
//		db.setCredit(d.getCredit());
//		db.setIntituleUE(d.getIntituleUE());
//		db.setSemestre(d.getSemestre());
//		
//		
//	}*/
	
}
