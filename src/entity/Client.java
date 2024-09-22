package entity;

import java.util.List;

public class Client {
    private int id;
    private String name;
    private String address;
    private String phone;
    private boolean is_professional;
    private List<Project> projects;

    public Client(String name, String address, String phone, boolean is_professional) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.is_professional = is_professional;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getIs_professional() {
        return is_professional;
    }

    public void setIs_professional(boolean is_professional) {
        this.is_professional = is_professional;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
