package com.pictimegroupe.FrontVendeur.testWebservice.services;

import com.pictimegroupe.FrontVendeur.testWebservice.Delta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompareImpl implements Compare {
    @Autowired
    DeltaServices deltaServices;
    @Autowired
    ServiceRecordServices serviceRecordServices;
    private Dates date = new Dates();
    String txtWservice = "";

    public CompareImpl() {
    }
    @Override
    public boolean isNoeud(String ch)
    {
        return ch.contains("\": {") || ch.contains("\": [");
    }

    @Override
    public boolean isWebService(String ch) {
        return ch.contains("*****************************");
    }
    @Override
    public boolean isEndWebService(String ch) {
        return ch.contains("################################################################");
    }
    @Override
    public void writeOnFile(String path, String txt) throws IOException {
        FileWriter fw = new FileWriter(new File(path),true);
        fw.write(txt);
        fw.close();
    }
    @Override
    public void reWriteOnFile(String path, String txt) throws IOException {
        FileWriter fw = new FileWriter(new File(path),true);
        //fw.write(txt);

        fw.write(txt+"\n");

        fw.close();
    }
    @Override
    public int nbWebService(String path)throws IOException{
        BufferedReader reader1 = new BufferedReader(new FileReader(new File(path)));
        String line1 = reader1.readLine();
        int nb=0;
        while ((line1 != null)){
            if (isWebService(line1))
                nb++;
            line1 = reader1.readLine();
        }
        return nb;
    }

    @Override
    public void clearFile(String path )throws IOException{
        PrintWriter writer = new PrintWriter(new File(path));
        writer.print("");
        writer.close();
    }

    @Override
    public void deleteComparedWs(String path, int nb)throws  IOException{
        BufferedReader  br1 = new BufferedReader(new FileReader(path));
        String line = br1.readLine();
        List<String> list1 = new ArrayList<String>();
        List<String> listtmp = new ArrayList<String>();
         while ((line != null)){
            //list1.add(line);
             if (line!=null){
                 list1.add(line);
             }
            line = br1.readLine();
        }
        for (int i = nb+1; i < list1.size(); i++) {
            listtmp.add(list1.get(i));
        }
        clearFile(path);
        for (int i = 0; i < listtmp.size(); i++) {
           reWriteOnFile(path,listtmp.get(i));
        }
     }

   @Override
   public int chargerParagraphe(String path1, String ftmp1) throws IOException {
       int nbLigne=0;
        BufferedReader reader1 = new BufferedReader(new FileReader(path1));
        String line1 = reader1.readLine();
        while ((line1 != null)|| isEndWebService(line1)){
          if (line1!=null){
                writeOnFile(ftmp1, line1+"\n");
           }
            if( isEndWebService(line1)){
               break;
            }
            line1 = reader1.readLine();
            nbLigne++;
        }
        reader1.close();
        return nbLigne;
    }

    @Override
    public void  simpleCompare(String path1, String path2) throws IOException {
        String pathResult="/home/front-vendeur/Bureau/tesWs/webservice/resultat1"+date.date1;
        BufferedReader reader1 = new BufferedReader(new FileReader(new File(path1)));
        BufferedReader reader2 = new BufferedReader(new FileReader(new File(path2)));
        String line1 = reader1.readLine();
        String line2 = reader2.readLine();
        String noeud="";
        String txtresultat="";
        String txtresultatNeoud= "";
        String txtresultatLine="";
        String txtresultatOk="Pas de modification dans le ws.\n";
        boolean areEqual = true;
        int lineNum = 1;
        while (line1 != null || line2 != null){
            if(line1!=null) {
                if (isNoeud(line1)){
                    noeud = line1;
                }
                if(isWebService(line1)){
                    txtWservice="le web service modifié est :"+line1+"\n";
                }
            }
            if(line1 == null || line2 == null){
                areEqual = false;
                break;
            }
            else if(! line1.equalsIgnoreCase(line2)){
                areEqual = false;
                txtresultatNeoud= "le noeud modifié est "+noeud.substring(0,noeud.length())+"  la diférence est à la ligne "+lineNum+"\n";
                txtresultatLine="la différence:     "+line1+" ==========> "+line2+"\n";
                txtresultat=txtWservice+txtresultatNeoud+txtresultatLine;
                writeOnFile(pathResult, txtresultat);
            }
            line1 = reader1.readLine();
             line2 = reader2.readLine();
            lineNum++;
        }
        if(areEqual){
            writeOnFile(path1, txtresultatOk);
        }
        reader1.close();
        reader2.close();
    }

   @Override
    public void CompareWs(String path1, String path2) throws IOException {
        String ftmp1 = "/home/front-vendeur/Bureau/tesWs/webservice/ftmp01";
        String ftmp2 = "/home/front-vendeur/Bureau/tesWs/webservice/ftmp02";
        int nbligne1=0;
        int nbLigne2=0;
        int nbWs=nbWebService(path1);
        for(int i=0; i<nbWs;i++) {
          nbligne1 = chargerParagraphe(path1, ftmp1);
           nbLigne2 = chargerParagraphe(path2, ftmp2);
            simpleCompare(ftmp1, ftmp2);
           // deleteComparedWs(path1, nbligne1);
           // deleteComparedWs(path2, nbLigne2);
           // clearFile(ftmp1);
            //clearFile(ftmp2);
    System.out.println("yes");
        }
    }


    @Override
    public void comparaison(String path1,String path2,String idServiceRecord) throws IOException {
        Delta delta= new Delta();
        delta.setServiceRecord(serviceRecordServices.getServiceRecord(idServiceRecord));
        BufferedReader reader1 = new BufferedReader(new FileReader(path1));
        BufferedReader reader2 = new BufferedReader(new FileReader(path2));
        String line1 = reader1.readLine();
        String line2 = reader2.readLine();
        String noeud="";

        boolean areEqual = true;

        while (line1 != null || line2 != null){
            if(line1!=null) {
                if (isNoeud(line1)){
                    noeud = line1;
                }

            }
            if(line1 == null || line2 == null){
                areEqual = false;
                break;
            }
            else if(! line1.equalsIgnoreCase(line2)){
                areEqual = false;
                delta.setNode(noeud);
                delta.setExpctedValue(line1);
                delta.setRegisteedValue(line2);
                deltaServices.addDelta(delta);
                System.out.println("je trouvé une diférence "+delta.getRegisteedValue() +"     "+delta.getExpctedValue());
            }
            line1 = reader1.readLine();
            line2 = reader2.readLine();

        }
        if(areEqual){
           // writeOnFile(path1, txtresultatOk);
            System.out.println("pas de changement dans les webs services");
        }

        reader1.close();
        reader2.close();

        System.out.println("je rajoute une différence");

        //afficher toutes les deltas  // todo




    }



    }



