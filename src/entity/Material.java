package entity;

public class Material extends Component{
    private int id;
    private Double unit_cost;
    private Double quantity ;
    private double transportCost;
    private double qualityCoefficient;


    public Material( String name,  String component_type, Double vat_rate, int project_id,  Double unit_cost, Double quantity, double transportCost, double qualityCoefficient) {
        super(vat_rate, name, component_type , project_id);
        this.unit_cost = unit_cost;
        this.quantity = quantity;
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
    }

    public Double getUnit_cost() {
        return unit_cost;
    }

    public void setUnit_cost(Double unit_cost) {
        this.unit_cost = unit_cost;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(double transportCost) {
        this.transportCost = transportCost;
    }

    public double getQualityCoefficient() {
        return qualityCoefficient;
    }

    public void setQualityCoefficient(double qualityCoefficient) {
        this.qualityCoefficient = qualityCoefficient;
    }
}
