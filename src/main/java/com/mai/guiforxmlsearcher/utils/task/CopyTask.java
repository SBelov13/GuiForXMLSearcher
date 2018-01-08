/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mai.guiforxmlsearcher.utils.task;

import java.io.File;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.List;
import javafx.concurrent.Task;
import org.apache.log4j.Logger;

/**
 *
 * @author Sergey
 */
public class CopyTask extends Task<Boolean> {

    final static Logger logger = Logger.getLogger(CopyTask.class);
    private final List<File> files;
    private final String destFolder;

    public CopyTask(List<File> files, String destCatalog) {
        this.files = files;
        this.destFolder = destCatalog;
    }

    @Override
    protected Boolean call() throws Exception {
        logger.info("Начался процесс копирования");
        if (files.isEmpty()) {
            return true;
        }
        long maxCount = files.size();
        long count = 0;
        try {
            for (File file : files) {
                File destFile = new File(destFolder + "/" + file.getName());
                Files.copy(file.toPath(), destFile.toPath(), REPLACE_EXISTING);
                count++;
                this.updateProgress(count, maxCount);
            }
        } catch (RuntimeException ex) {
            logger.info(ex.getStackTrace());
            return false;
        }
        logger.info("Скопированно " + maxCount + "файлов");
        return true;
    }

}
