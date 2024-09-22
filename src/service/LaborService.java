package service;

import entity.Labor;
import entity.Material;
import repository.LaborRepository;

import java.util.List;

public class LaborService {

    public boolean save(Labor labor) {
        return new LaborRepository().save(labor);
    }

    public List<Labor> findAll(int project_id) {
        return new LaborRepository().findByProjectId(project_id);
    }


    public double calcCost(List<Labor> labors ) {
        return labors.stream()
                .mapToDouble(labor -> {
                    double baseCost = (labor.getHourly_rate() * labor.getWork_hours()) * labor.getWorker_productivity();
                    if (labor.getVat_rate() == 0) {
                        return baseCost;
                    } else {
                        return baseCost * (1 + labor.getVat_rate() / 100);
                    }
                })
                .sum();
    }


}
