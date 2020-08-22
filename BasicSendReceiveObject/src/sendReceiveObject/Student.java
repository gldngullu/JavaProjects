package sendReceiveObject;

import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private String id;
    private String dept;
    private int addedByServer;

    public Student(String name, String id, String dept) {
        this.name = name;
        this.id = id;
        this.dept = dept;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public void setAddedByServer(int addedByServer) {
        this.addedByServer = addedByServer;
    }

    public int getAddedByServer() {
        return addedByServer;
    }


}


