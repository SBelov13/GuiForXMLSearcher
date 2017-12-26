/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mai.guiforxmlsearcher.utils;

import com.mai.guiforxmlsearcher.MainFXMLController;
import static com.mai.guiforxmlsearcher.utils.XMLUtils.TEXT_ATR;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.concurrent.Task;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Sergey
 */
public class FindTask extends Task<Map<String, Set<String>>> {

    final static Logger logger = Logger.getLogger(FindTask.class);
    List<File> files;

    public FindTask(List<File> files) {
        this.files = files;
    }

    @Override
    protected Map<String, Set<String>> call() throws Exception {
        Map<String, Set<String>> resultMap = new HashMap<>();
        if (files.isEmpty()) {
            return resultMap;
        }
        long maxCount = files.size();
        long count = 0;
        try {
            for (File file : files) {
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                // Создается дерево DOM документа из файла
                Document document = (Document) documentBuilder.parse(file);
                // Получаем корневой элемент
                Node root = document.getDocumentElement();
                // Просматриваем все подэлементы корневого - т.е. атрибуты
                NodeList atributes = root.getChildNodes();

                for (int i = 0; i < atributes.getLength(); i++) {
                    Node atribute = atributes.item(i);
                    if (atribute.getNodeType() != Node.TEXT_NODE || TEXT_ATR.equals(atribute.getNodeName())) {
                        String value = atribute.getTextContent();
                        if (value == null || value.isEmpty()) {// пропустим пустые теги
                            continue;
                        }
                        String atrName = atribute.getNodeName();
                        Set<String> values = resultMap.get(atrName);
                        if (values == null) {
                            values = new HashSet<>();
                            values.add(value);
                            resultMap.put(atrName, values);
                        } else {
                            values.add(value);
                        }
                    }
                }
                logger.info(count);
                count++;
                this.updateProgress(count, maxCount);

            }
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return resultMap;
    }

}
