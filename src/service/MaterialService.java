package service;

import entity.Material;
import entity.Project;
import repository.MaterialRepository;

import java.util.List;

public class MaterialService {

    public boolean save(Material material) {
        return new MaterialRepository().save(material);
    }

    public List<Material> findAll(int project_id) {
        return new MaterialRepository().findByProjectId(project_id);
    }

    public double calcCost(List<Material> materials ) {
        return materials.stream()
                .mapToDouble(material -> {
                    double baseCost = material.getQuantity() + material.getQualityCoefficient() + material.getTransportCost();
                    if (material.getVat_rate() == 0) {
                        return baseCost;
                    } else {
                        return baseCost * (1 + material.getVat_rate() / 100);
                    }
                })
                .sum();
    }

    public boolean updateVarRat(double vatRat ,int id){
        return new MaterialRepository().updateVatRat(vatRat , id);
    }

}
