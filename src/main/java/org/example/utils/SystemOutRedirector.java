package org.example.utils;

import java.io.OutputStream;
import java.io.PrintStream;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class SystemOutRedirector extends PrintStream {

    private final TextArea textArea;

    // Constructor to accept a TextArea
    public SystemOutRedirector(TextArea textArea) {
        super(new OutputStream() {
            @Override
            public void write(int b) {
                // Do nothing, output goes to the textArea
            }
        });
        this.textArea = textArea;
    }

    @Override
    public void print(String s) {
        Platform.runLater(() -> textArea.appendText(s));
    }

    @Override
    public void println(String x) {
        Platform.runLater(() -> textArea.appendText(x + "\n"));
    }

    @Override
    public void write(byte[] b, int off, int len) {
        String output = new String(b, off, len);
        Platform.runLater(() -> textArea.appendText(output));
    }
}
