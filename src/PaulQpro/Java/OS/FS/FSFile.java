package PaulQpro.Java.OS.FS;

public class FSFile {
    protected FSFile(String name, FSFolder parent){
        this.name = name;
        this.parent = parent;
        this.type = FSFileType.TXT;
        this.path = parent.getPath()+name+"."+FSFileType.TXT.toString().toLowerCase();
    }
    protected FSFile(String name, FSFileType type, FSFolder parent){
        this.name = name;
        this.parent = parent;
        this.type = type;
        this.path = parent.getPath()+"\\"+name+"."+type.toString().toLowerCase();
    }
    private FSFile(){}

    private String name;
    private FSFileType type;
    private FSFolder parent;
    private String path;
    private String content;

    public String getName() {
        return name;
    }

    public void rename(String name) {
        this.name = name;
    }

    public FSFileType getType(){
        return type;
    }

    public void changeType(FSFileType type){
        this.type = type;
    }

    public FSFolder getParent() {
        return parent;
    }

    public String getPath() {
        return path;
    }

    public String read() {
        return content;
    }

    public void rewrite(String content) {
        this.content = content;
    }
    public void write(String content){
        this.content+=content;
    }

    public static class JsonFSFile {
        public  JsonFSFile(FSFile file){
            name = file.name;
            type = file.type.toString();
        }
        private JsonFSFile(){}

        public String name;
        public String type;

        public FSFile toFS(FSFolder parent){
            return new FSFile(name, FSFileType.valueOf(type), parent);
        }
    }
}
