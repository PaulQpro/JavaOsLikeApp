package PaulQpro.Java.OS.FS;

import PaulQpro.Java.OS.FS.FSDisk.JsonFSDisk;
import java.util.ArrayList;

public class FS {
    public FS(){
        disks = new ArrayList<>();
    }

    ArrayList<FSDisk> disks;

    public void plugInDisk(char symbol,String diskName){
        disks.add(new FSDisk(symbol,diskName));
    }

    public FSDisk getDisk(char symbol){
        for (FSDisk disk:
             disks) {
            if(disk.getSymbol() == symbol) return disk;
        }
        return null;
    }

    public static class JsonFS{
        public JsonFS(FS fs){
            disks = new ArrayList<>();
            for (FSDisk disk:
                 fs.disks) {
                disks.add(new JsonFSDisk(disk));
            }
        }
        private JsonFS(){

        }

        public ArrayList<JsonFSDisk> disks;

        public FS toFS(){
            FS fileSys = new FS();
            for (JsonFSDisk disk:
                 disks) {
                fileSys.disks.add(disk.toFS());
            }
            return fileSys;
        }
    }
}
