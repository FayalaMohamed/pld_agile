package com.hexa.model;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;

public class XMLfileOpener extends FileFilter {// Singleton

  private static XMLfileOpener instance = null;
  private static String dossierMapDefaut = "C:\\Martin\\INSA\\pld_agile\\Map_XML";
  private static String dossierRequetesDefaut = "C:\\Martin\\INSA\\pld_agile\\Req_Saved";
  private static String cheminDefaut;

  private XMLfileOpener() {
  }

  
  /** Retourne la seule instance de la classe
   * @return XMLfileOpener
   */
  public static XMLfileOpener getInstance(String action) {

    //chemin par défaut selon l'action voulue
    switch (action) {
      case "map":
        cheminDefaut = dossierMapDefaut;
        break;

      case "requete":
        cheminDefaut = dossierRequetesDefaut;
        break;
    
      default:
        break;
    }

    if (instance == null)
      instance = new XMLfileOpener();
    return instance;
  }

  
  /** Créer un explorateur de fichiers et permet de sélectionner un fichier à ouvrir
   * @param read
   * @return File
   * @throws Exception
   */
  public File open(boolean read) throws Exception {
    int returnVal;
    JFileChooser jFileChooserXML = new JFileChooser();
    jFileChooserXML.setFileFilter(this);
    jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);
    jFileChooserXML.setCurrentDirectory( new File(cheminDefaut));
    if (read)
      returnVal = jFileChooserXML.showOpenDialog(null);
    else
      returnVal = jFileChooserXML.showSaveDialog(null);
      
    if (returnVal != JFileChooser.APPROVE_OPTION)
      return null;
    // throw new Exception("Problem when opening file");
    return new File(jFileChooserXML.getSelectedFile().getAbsolutePath());
  }

  
  /** 
   * @param f
   * @return boolean
   */
  @Override
  public boolean accept(File f) {
    if (f == null)
      return false;
    if (f.isDirectory())
      return true;
    String extension = getExtension(f);
    if (extension == null)
      return false;
    return extension.contentEquals("xml");
  }

  
  /** 
   * @return String
   */
  @Override
  public String getDescription() {
    return "XML file";
  }

  
  /** 
   * @param f
   * @return String
   */
  private String getExtension(File f) {
    String filename = f.getName();
    int i = filename.lastIndexOf('.');
    if (i > 0 && i < filename.length() - 1)
      return filename.substring(i + 1).toLowerCase();
    return null;
  }
}
