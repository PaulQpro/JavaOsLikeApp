package PaulQpro.Java.OS;

import PaulQpro.Java.OS.FS.FS;
import PaulQpro.Java.OS.FS.FSFile;
import PaulQpro.Java.OS.FS.FSFileType;
import PaulQpro.Java.OS.FS.FSFolder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Scanner;

public class CLI {
    FS fileSys;
    FSFolder current;
    ObjectMapper mapper = new ObjectMapper();
    public void Run(){
        InitFS();
        Console();
        File fsFile = new File("FileSystem.dat");
        System.out.println("Saving FileSystem.dat");
        try {
            mapper.writeValue(fsFile, new FS.JsonFS(fileSys));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done");
    }
    private void InitFS(){
        File fsFile = new File("FileSystem.dat");
        System.out.println("Trying to load FileSystem.dat from "+fsFile.getAbsoluteFile());
        if(fsFile.exists() && !fsFile.isDirectory()) {
            try {
                fileSys = mapper.readValue(fsFile, FS.JsonFS.class).toFS();
                current = fileSys.getDisk('C').rootFolder();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("FileSystem.dat loaded");
        } else {
            System.out.println("Failed\nCreating FileSystem");
            fileSys = new FS();
            fileSys.plugInDisk('C',"Local Disk");
            current = fileSys.getDisk('C').rootFolder();
            current.addSubFolder("System");
            current = current.getSubFolder("System");
            current.addFile("System", FSFileType.LIB);
            current.getFile("System", FSFileType.LIB).rewrite("Trash file, but without it program crashes");
            current.addFile("CLI",FSFileType.LIB);
            current.getFile("CLI",FSFileType.LIB).rewrite("Console Line Interface, do not delete, code at github.com/PaulQpro/JavaOsLikeApp");
            try {
                mapper.writeValue(fsFile, new FS.JsonFS(fileSys));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Done");
            current = fileSys.getDisk('C').rootFolder();
        }
    }
    private void Console(){
        System.out.println("Hello Java");
        while(true){
            boolean isExiting = false;
            Scanner in = new Scanner(System.in);
            System.out.print(current.getPath()+"\\>");
            String input = in.nextLine().trim().toLowerCase();
            String[] argv = input.split(" ");
            switch (argv[0]){
                case "path":
                    System.out.println("FileSystem.dat path is "+new File("FileSystem.dat").getAbsolutePath());
                    break;
                case "ls":
                case "dir": {
                    System.out.println("Contents of " + current.getPath() + "\\");
                    if (!current.getSubFolders().isEmpty()) {
                        System.out.println("----Folders");
                        for (FSFolder folder :
                                current.getSubFolders()) {
                            System.out.println("--------" + folder.getName() + "\\");
                        }
                    } else if (!current.getFiles().isEmpty()) {
                        System.out.println("----Folders");
                        System.out.println("--------None");
                    }
                    if (!current.getFiles().isEmpty()) {
                        System.out.println("----Files");
                        for (FSFile file :
                                current.getFiles()) {
                            System.out.println("--------" + file.getName() + "." + file.getType().toString().toLowerCase());
                        }
                    } else if (!current.getSubFolders().isEmpty()) {
                        System.out.println("----Files");
                        System.out.println("--------None");
                    }
                    if (current.getSubFolders().isEmpty() && current.getFiles().isEmpty()) {
                        System.out.println("----IsEmpty");
                    }
                    break;
                }
                case "cd..":
                    current = current.getParent() != null ? current.getParent() : current;
                    break;
                case "cd":
                    FSFolder newFolder = current.getSubFolder(argv[1]);
                    if(newFolder != null) current = newFolder;
                    else System.out.println("Folder "+argv[1]+" doesn't exists in " + current.getPath());
                    break;
                case "cat":
                case "view":

                case "":
                    break;
                case "exit":
                    isExiting = true;
                    break;
                default:
                    System.out.println("Wrong or incorrect command");
                    break;
            }
            if(isExiting) break;
        }
    }
}
