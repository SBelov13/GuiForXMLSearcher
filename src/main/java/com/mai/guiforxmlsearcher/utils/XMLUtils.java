/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mai.guiforxmlsearcher.utils;

import com.mai.guiforxmlsearcher.utils.task.DecomposeConvertTask;
import com.mai.guiforxmlsearcher.utils.task.CopyTask;
import com.mai.guiforxmlsearcher.utils.task.FindTask;
import com.mai.guiforxmlsearcher.operations_type_scene.SearchByTagBahavior;
import com.mai.guiforxmlsearcher.utils.task.WriteTask;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import org.apache.log4j.Logger;

/**
 *
 * @author Sergey
 */
public class XMLUtils {

    public final static String TEXT_ATR = "text";
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

    public static void getAllAtributeAndValues(File workDirectory, final ProgressBar progressBar, final SearchByTagBahavior bahavior, final Button startButton) {
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
                startButton.setDisable(false);
            }
        });
        logger.info("Начался процесс сканирования");
        new Thread(findTask).start();
    }

    public static void copyFiles(List<File> files, String saveDirectory, final ProgressBar progressBar, final Button startButton) {
        final CopyTask copyTask = new CopyTask(files, saveDirectory);
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(copyTask.progressProperty());

        copyTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                if (copyTask.getValue()) {
                    logger.info("Копирование завершено успешно");
                } else {
                    logger.info("При копировании произошла ошибка");
                }
                progressBar.progressProperty().unbind();
                progressBar.setProgress(0);
                startButton.setDisable(false);
            }
        });
        new Thread(copyTask).start();
    }

    public static void createTXTFromXMl(Map<String, List<File>> map, String saveDirectory, final ProgressBar progressBar, final Button startButton) {
        final DecomposeConvertTask decomposeConvertTask = new DecomposeConvertTask(map, saveDirectory);
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(decomposeConvertTask.progressProperty());

        decomposeConvertTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                if (decomposeConvertTask.getValue()) {
                    logger.info("Конвертация завершена успешно");
                } else {
                    logger.info("При конвертации произошла ошибка");
                }
                progressBar.progressProperty().unbind();
                progressBar.setProgress(0);
                startButton.setDisable(false);
            }
        });
        new Thread(decomposeConvertTask).start();

    }

    public static void createOneTXTFromAllXMl(File workDirectory, String saveDirectory, final ProgressBar progressBar, final Button startButton) {
        List<File> files = getListXMLFiles(workDirectory);
        final WriteTask writeTask = new WriteTask(files, saveDirectory);
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(writeTask.progressProperty());

        writeTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                if (writeTask.getValue()) {
                    logger.info("Запись в txt завершена успешно");
                } else {
                    logger.info("При записи в txt произошла ошибка");
                }
                progressBar.progressProperty().unbind();
                progressBar.setProgress(0);
                startButton.setDisable(false);
            }
        });
        new Thread(writeTask).start();

    }

}
