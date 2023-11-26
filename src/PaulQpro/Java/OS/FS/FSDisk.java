package PaulQpro.Java.OS.FS;

import PaulQpro.Java.OS.FS.FSFolder.JsonFSFolder;

public class FSDisk {

    protected FSDisk(char symbol, String name) {
        this.symbol = symbol;
        this.name = name;
        root = new FSFolder(symbol+":",null);
    }
    private FSDisk(){}

    private char symbol;
    private String name;
    private FSFolder root;

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FSFolder rootFolder() {
        return root;
    }

    public static class JsonFSDisk{
        public JsonFSDisk(FSDisk disk){
            symbol = disk.symbol;
            name = disk.name;
            root = new JsonFSFolder(disk.root);
        }
        private JsonFSDisk(){}

        public char symbol;
        public String name;
        public JsonFSFolder root;

        public FSDisk toFS(){
            FSDisk disk = new FSDisk(symbol, name);
            disk.root = this.root.toFS(null);
            return disk;
        }
    }
}
