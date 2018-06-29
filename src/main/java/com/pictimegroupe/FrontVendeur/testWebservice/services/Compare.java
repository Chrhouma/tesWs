package com.pictimegroupe.FrontVendeur.testWebservice.services;

import java.io.File;
import java.io.IOException;

public interface Compare {
    boolean isNoeud(String ch);

    boolean isWebService(String ch);

    boolean isEndWebService(String ch);

    void writeOnFile(String path, String txt) throws IOException;

    void reWriteOnFile(String apth, String txt) throws IOException;

    int nbWebService(String path)throws IOException;

    void clearFile(String path)throws IOException;

    int chargerParagraphe(String path1, String ftmp1) throws IOException;

    void  simpleCompare(String path1, String path2) throws IOException;

    void CompareWs(String path1, String path2) throws IOException;
    void comparaison (String path1,String path2,String idserviceRecord)throws IOException;
}
