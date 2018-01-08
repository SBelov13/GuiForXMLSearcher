/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mai.guiforxmlsearcher.utils.task;

import static com.mai.guiforxmlsearcher.utils.XMLUtils.TEXT_ATR;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javafx.concurrent.Task;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Sergey
 */
public class DecomposeConvertTask extends Task<Boolean> {

    final static Logger logger = Logger.getLogger(DecomposeConvertTask.class);
    private final Map<String, List<File>> map;
    private final String destFolder;

    public DecomposeConvertTask(Map<String, List<File>> map, String destFolder) {
        this.map = map;
        this.destFolder = destFolder;
    }

    @Override
    protected Boolean call() throws Exception {
        logger.info("Начался процесс конвертации XML в TXT");
        if (map.isEmpty()) {
            return true;
        }
        long maxCount = 0;
        for (List<File> list : map.values()) {
            maxCount += list.size();
        }
        long count = 0;
        try {
            for (Entry<String, List<File>> entry : map.entrySet()) {
                for (File file : entry.getValue()) {
                    String text = null;
                    DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    Document document = (Document) documentBuilder.parse(file);
                    Node root = document.getDocumentElement();
                    NodeList atributes = root.getChildNodes();
                    for (int i = 0; i < atributes.getLength(); i++) {
                        Node atribute = atributes.item(i);
                        if (atribute.getNodeType() != Node.TEXT_NODE && TEXT_ATR.equals(atribute.getNodeName())) {
                            String value = atribute.getTextContent();
                            if (value == null || value.isEmpty()) {// пропустим пустые теги
                                continue;
                            }
                            text = value;
                            break;
                        }
                    }
                    if (text == null) {
                        continue;
                    }
                    File directory = new File(destFolder + "\\" + entry.getKey());
                    if (!directory.exists()) {
                        Files.createDirectory(directory.toPath());
                    }
                    File txtFile = new File(destFolder + "\\" + entry.getKey() + "\\" + file.getName() + ".txt");
                    if (!txtFile.exists()) {
                        txtFile.createNewFile();
                    }
                    try {
                        PrintWriter writer = new PrintWriter(txtFile.getAbsolutePath(), "UTF-8");
                        writer.println(text);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    if (!txtFile.exists()) {
//                        txtFile.createNewFile();
//                    }

                    count++;
                    this.updateProgress(count, maxCount);
                }
            }
        } catch (RuntimeException ex) {
            logger.info(ex.getStackTrace());
            return false;
        }
        logger.info("Созданно " + count + " txt файлов");
        return true;
    }

}
