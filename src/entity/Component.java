package entity;

import java.util.ArrayList;
import java.util.List;

public abstract class Component {

    private int id ;
    private String name;
    private Double vat_rate;
    private String component_type ;
    private int project_id;

    public Component(Double vat_rate, String name, String component_type , int project_id) {
        this.vat_rate = vat_rate;
        this.name = name;
        this.component_type = component_type;
        this.project_id = project_id;
    }


    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getVat_rate() {
        return vat_rate;
    }

    public void setVat_rate(Double vat_rate) {
        this.vat_rate = vat_rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponent_type() {
        return component_type;
    }

    public void setComponent_type(String component_type) {
        this.component_type = component_type;
    }
}


