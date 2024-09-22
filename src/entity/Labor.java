package entity;

public class Labor extends Component {

    private int id ;
    private  Double work_hours ;
    private Double hourly_rate;
    private Double worker_productivity ;

    public Labor(String name, Double vat_rate, int project_id ,   String component_type, Double work_hours, Double hourly_rate, Double worker_productivity) {
        super(vat_rate, name, component_type , project_id);
        this.work_hours = work_hours;
        this.hourly_rate = hourly_rate;
        this.worker_productivity = worker_productivity;
    }

    public Double getWork_hours() {
        return work_hours;
    }

    public void setWork_hours(Double work_hours) {
        this.work_hours = work_hours;
    }

    public Double getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(Double hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Double getWorker_productivity() {
        return worker_productivity;
    }

    public void setWorker_productivity(Double worker_productivity) {
        this.worker_productivity = worker_productivity;
    }
}
