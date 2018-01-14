/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mai.guiforxmlsearcher.utils.task;

import static com.mai.guiforxmlsearcher.utils.XMLUtils.TEXT_ATR;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
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
public class WriteTask extends Task<Boolean> {

    final static Logger logger = Logger.getLogger(WriteTask.class);
    private final List<File> files;
    private final String destFolder;

    public WriteTask(List<File> files, String saveFolder) {
        this.files = files;
        this.destFolder = saveFolder;
    }

    @Override
    protected Boolean call() throws Exception {
        logger.info("Начался процесс записи тегов text всех XML в один TXT");
        if (files.isEmpty()) {
            return true;
        }
        long maxCount = files.size();
        long count = 0;
        File txtFile = new File(destFolder + "\\dictionary.txt");
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(txtFile), "UTF-8"));
            for (File file : files) {
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
                bw.write(text);
                count++;
                this.updateProgress(count, maxCount);
            }
            bw.flush();
            bw.close();
        } catch (RuntimeException ex) {
            logger.info(ex.getStackTrace());
            return false;
        }
        return true;
    }

}
