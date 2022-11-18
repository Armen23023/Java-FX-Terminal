package com.example.labproject;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.labproject.commands.Command;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    TextArea textArea = new TextArea("");
    static String path = "Root";


    @Override
    public void start(Stage stage) {
        File f = new File(path);
        if (f.mkdir()) {
            textArea.appendText("root created");
        } else {
            System.out.println("Root already created");
        }
        Command command = new Command();

        textArea.setOnKeyPressed((t) -> {
            try {
                if (t.getCode() == KeyCode.ENTER) {
                    String[] splitText = textArea.getText().split("\n");
                    String text = splitText[splitText.length - 1];



                    if (text.startsWith("ls")) {
                        textArea.appendText(command.ls(text.substring(2), path));
                        return;
                    }
                    if (text.substring(0,3).equals("pwd")){
                        textArea.appendText(command.pwd(text.substring(3) ,path));
                        return;
                    }
                    if (text.substring(0, 5).equals("mkdir")) {
                        textArea.appendText(command.mkdir(text.substring(5), path));
                        return;
                    }
                    if (text.substring(0, 5).equals("cd ..") && text.length()==5) {
                        textArea.appendText(path = command.cdi(path));
                        return;
                    }
                    if (text.substring(0, 2).equals("cd")) {
                        textArea.appendText( path =command.cd(text.substring(2), path));
                        return;
                    }
                    if (text.substring(0, 5).equals("touch")) {
                        textArea.appendText(command.touch(text.substring(5), path));
                        return;
                    }
                    if (text.substring(0, 5).equals("rmdir")) {
                        textArea.appendText(command.rmdir(text.substring(5), path));
                        return;
                    }
                    if (text.substring(0, 2).equals("rm")) {
                        textArea.appendText(command.rm(text.substring(2), path));
                        return;
                    }



                    Task<Void> task = printResults(text);
                    task.setOnSucceeded((workerStateEvent) -> {
                        textArea.setEditable(true);
                    });
                    Thread thread = new Thread(task);
                    thread.setDaemon(true);
                    thread.start();
                }
            }catch (ExceptionInInitializerError e) {
                textArea.appendText("You are in root folder" + "\n");
            }
            catch (NumberFormatException e) {
                textArea.appendText(" File not found." + "\n");
            }
            catch (RuntimeException ex) {
                textArea.appendText(" не является внутренней или внешней\n" +
                        "командой, исполняемой программой или пакетным файлом." + "\n");
            }
        });
        var scene = new Scene(new StackPane(textArea), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    Task<Void> printResults(String text) {
        textArea.setEditable(false);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes())));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        updateMessage(line);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        };
        return task;
    }
}



//                    task.messageProperty().addListener((obs, oldText, newText)->{
//                        textArea.appendText(newText + "\n");
//                    });