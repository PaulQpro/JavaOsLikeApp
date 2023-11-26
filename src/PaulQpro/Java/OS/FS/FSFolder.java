package PaulQpro.Java.OS.FS;

import PaulQpro.Java.OS.FS.FSFile.JsonFSFile;
import java.util.ArrayList;

public class FSFolder{
    protected FSFolder(String name, FSFolder parent){
        this.name = name;
        this.parent = parent;
        subFolders = new ArrayList<>();
        files = new ArrayList<>();
        this.path = parent != null ? parent.getPath()+"\\"+name : name;
    }
    private FSFolder(){}

    private String name;
    private FSFolder parent;
    private ArrayList<FSFolder> subFolders;
    private ArrayList<FSFile> files;
    private String path;

    public String getName() {
        return name;
    }

    public void rename(String name) {
        this.name = name;
    }

    public FSFolder getParent() {
        return parent;
    }

    public FSFolder getSubFolder(String name){
        for (FSFolder folder:
             subFolders) {
            if(folder.name.equalsIgnoreCase(name)) return folder;
        }
        return null;
    }

    public boolean addSubFolder(String name){
        if(getSubFolder(name) == null){
            subFolders.add(new FSFolder(name, this));
            return true;
        }
        else return false;
    }

    public FSFile getFile(String name, FSFileType type){
        for (FSFile file:
                files) {
            if(file.getName().equalsIgnoreCase(name) && file.getType().equals(type)) return file;
        }
        return null;
    }

    public boolean addFile(String name, FSFileType type){
        if(getFile(name, type) == null){
            files.add(new FSFile(name, type, this));
            return true;
        }
        else return false;
    }
    public boolean addFile(String name){
        if(getFile(name, FSFileType.TXT) == null){
            files.add(new FSFile(name, this));
            return true;
        }
        else return false;
    }

    public String getPath() {
        return path;
    }

    public ArrayList<FSFolder> getSubFolders() {
        return subFolders;
    }

    public ArrayList<FSFile> getFiles() {
        return files;
    }

    public static class JsonFSFolder{
        public  JsonFSFolder(FSFolder folder){
            name = folder.name;
            subFolders = new ArrayList<>();
            files = new ArrayList<>();
            for (FSFolder subfolder:
                 folder.subFolders) {
                subFolders.add(new JsonFSFolder(subfolder));
            }
            for (FSFile file:
                 folder.files) {
                files.add(new JsonFSFile(file));
            }
        }
        private JsonFSFolder(){}

        public String name;
        public ArrayList<JsonFSFolder> subFolders;
        public ArrayList<JsonFSFile> files;

        public FSFolder toFS(FSFolder parent){
            FSFolder folder = new FSFolder(name,parent);
            for (JsonFSFolder subFolder:
                 subFolders) {
                folder.subFolders.add(subFolder.toFS(folder));
            }
            for (JsonFSFile file:
                 files) {
                folder.files.add(file.toFS(folder));
            }
            return folder;
        }
    }
}
