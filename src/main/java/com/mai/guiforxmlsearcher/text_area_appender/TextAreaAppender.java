/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mai.guiforxmlsearcher.text_area_appender;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 *
 * @author Sergey
 */
public class TextAreaAppender extends WriterAppender {

    private static volatile TextArea textArea = null;

    /**
     * Set the target TextArea for the logging information to appear.
     *
     * @param textArea
     */
    public static void setTextArea(final TextArea textArea) {
        TextAreaAppender.textArea = textArea;
    }

    @Override
    public void append(LoggingEvent loggingEvent) {
        final String message = this.layout.format(loggingEvent);
        // Append formatted message to text area using the Thread.
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (textArea != null) {
                            if (textArea.getText().length() == 0) {
                                textArea.setText(message);
                            } else {
                                textArea.selectEnd();
                                textArea.insertText(textArea.getText().length(),
                                        message);
                            }
                        }
                    } catch (final Throwable t) {
                        System.out.println("Unable to append log to text area: "
                                + t.getMessage());
                    }
                }
            });
        } catch (final IllegalStateException e) {
            // ignore case when the platform hasn't yet been iniitialized
        }
    }

}
