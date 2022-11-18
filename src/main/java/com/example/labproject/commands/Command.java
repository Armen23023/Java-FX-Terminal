package com.example.labproject.commands;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;


public class Command {

    //● ls - lists all the content if the folder it is inside
    public String ls(String s,String path){
        if (!s.isEmpty()){
            throw new RuntimeException();
        }
        File file = new File(path);
        File[] files = file.listFiles();
        if (files.length==0){
            return "Folder empty";
        }
        for (File f : files) {
            s = s +">"+ f.getName() + "\n";
        }
    return s;
    }



    //● mkdir fld - creates folder named fld
    public String mkdir(String s,String path){
        if (s.isEmpty()|| s.isBlank()){
            return "Please set the name";
        }
        s = s.trim();
        File file = new File(path + "/"+ s);
        if (file.exists() && file.isDirectory()){
            return "Folder with that name exists";
        }else {
            file.mkdir();
        }
        return "";
    }
    //● cd fld - accesses folder named fld
    public String cd(String s,String path){
        if (s.isEmpty()|| s.isBlank()){
            throw new RuntimeException();
        }
        s = s.trim();
        File file = new File(path + "/" + s + "/");
        if (file.exists()){
            return file + "";
        }else {
            throw new NumberFormatException();
        }
    }


    public String touch(String s, String path) {
        if (s.isEmpty()|| s.isBlank()){
            return "Please set the name of file";
        }
        s = s.trim();
        File file = new File(path + "/"+ s);
        if (file.exists() && file.isFile()){
            return "File with that name exists";
        }else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return "File cannot created";
            }
        }
        return "";
    }

    public String rm(String s, String path) {
        if (s.isEmpty()|| s.isBlank()){
            return "Please set the name of file";
        }
        s = s.trim();
        File file = new File(path + "/"+ s);
        if (file.exists() && file.isFile()){
            boolean delete = file.delete();
            if (delete){
                return "File deleted";
            }
        }else {
            return "File not found";
        }
        return "";
    }

    public String rmdir(String s, String path) {
        if (s.isEmpty()|| s.isBlank()){
            return "Please set the name of file";
        }
        s = s.trim();
        File file = new File(path + "/"+ s);
        if (file.exists() && file.isDirectory() ){
            boolean delete = file.delete();
            if (delete){
                return "Folder deleted";
            }
        }else {
            return "Folder not found";
        }
        return "";
    }

    public String cdi(String path) {
        if (path.equals("Root") || path.equals("\\Root")){
            throw new ExceptionInInitializerError();
        }
        path = path.substring(0,path.length());
        path = path.substring(0, (path.lastIndexOf("\\")));

        File file = new File(path);
        return file+"";

    }

    public String pwd(String s, String path) {
        if (!s.isEmpty()){
            throw  new RuntimeException();
        }
        return path;
    }
}
