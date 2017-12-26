/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mai.guiforxmlsearcher.utils;

import com.mai.guiforxmlsearcher.operations_type_scene.SerachByTagBahavior;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
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
public class XMLUtils {

    final static String TEXT_ATR = "text";
    final static Logger logger = Logger.getLogger(XMLUtils.class);

    private static List<File> getListXMLFiles(File workDirectory) {
        List<File> files = new ArrayList<>();
        for (File file : workDirectory.listFiles()) {
            if (file.getPath().endsWith(".xml")) {
                files.add(file);
            }
        }
        return files;
    }

    public static void getAllAtributeAndValues(File workDirectory, ProgressBar progressBar, final SerachByTagBahavior bahavior) {
        List<File> files = getListXMLFiles(workDirectory);
        final FindTask findTask = new FindTask(files);
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(findTask.progressProperty());

        findTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                bahavior.refrashElement(findTask.getValue());
            }
        });
        new Thread(findTask).start();
    }

    public static void testXML(File workDirectory) {
        List<File> files = getListXMLFiles(workDirectory);
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = (Document) documentBuilder.parse(files.iterator().next());
            // Получаем корневой элемент
            Node root = document.getDocumentElement();
            // Просматриваем все подэлементы корневого - т.е. атрибуты
            NodeList atrs = root.getChildNodes();
            for (int i = 0; i < atrs.getLength(); i++) {
                Node atr = atrs.item(i);
                if (atr.getNodeType() != Node.TEXT_NODE) {
                    logger.info("NodeType: " + atr.getNodeType() + " NodeName:" + atr.getNodeName() + " NodeValue:" + atr.getNodeValue() + " TextContent:" + atr.getTextContent());
                }
            }
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
