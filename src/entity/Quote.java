package entity;

import java.time.LocalDate;

public class Quote {
    private int id;
    private double estimated_amount;
    private LocalDate issue_date;
    private LocalDate validity_date;
    private boolean is_accepted;
    private int project_id ;

    public Quote(double estimated_amount, LocalDate issue_date,  LocalDate validity_date, boolean is_accepted, int project_id) {
        this.issue_date = issue_date;
        this.estimated_amount = estimated_amount;
        this.validity_date = validity_date;
        this.is_accepted = is_accepted;
        this.project_id = project_id;
    }


    public Quote() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getEstimated_amount() {
        return estimated_amount;
    }

    public void setEstimated_amount(double estimated_amount) {
        this.estimated_amount = estimated_amount;
    }

    public LocalDate getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(LocalDate issue_date) {
        this.issue_date = issue_date;
    }

    public LocalDate getValidity_date() {
        return validity_date;
    }

    public void setValidity_date(LocalDate validity_date) {
        this.validity_date = validity_date;
    }

    public boolean getIs_accepted() {
        return is_accepted;
    }

    public void setIs_accepted(boolean is_accepted) {
        this.is_accepted = is_accepted;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }
}
