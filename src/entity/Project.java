package entity;

import enums.ProjectStatus;

import java.util.List;

public class Project {
    private int id;
    private int clientId;
    private String project_name;
    private double capacity;
    private double profit_margin;
    private double total_cost;
    private ProjectStatus status;
    private List<Component> components ;
    private List<Quote> quotes ;

    public Project(int clientId, String project_name, double capacity, double profit_margin, double total_cost, ProjectStatus status) {
        this.clientId = clientId;
        this.project_name = project_name;
        this.capacity = capacity;
        this.profit_margin = profit_margin;
        this.total_cost = total_cost;
        this.status = status;
    }

    public Project() {}


    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getProfit_margin() {
        return profit_margin;
    }

    public void setProfit_margin(double profit_margin) {
        this.profit_margin = profit_margin;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }
}

