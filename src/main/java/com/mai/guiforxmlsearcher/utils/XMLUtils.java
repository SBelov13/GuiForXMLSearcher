/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mai.guiforxmlsearcher.utils;

import com.mai.guiforxmlsearcher.operations_type_scene.SerachByTagBahavior;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
import org.apache.log4j.Logger;

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

    public static void getAllAtributeAndValues(File workDirectory, final ProgressBar progressBar, final SerachByTagBahavior bahavior) {
        List<File> files = getListXMLFiles(workDirectory);
        logger.info("Найдено " + files.size() + " файлов");
        final FindTask findTask = new FindTask(files);
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(findTask.progressProperty());

        findTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                bahavior.refrashElement(findTask.getValue());
                logger.info("Cканирование завершено");
                progressBar.progressProperty().unbind();
                progressBar.setProgress(0);
            }
        });
        logger.info("Начался процесс сканирования");
        new Thread(findTask).start();
    }

}
