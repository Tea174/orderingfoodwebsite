package be.kdg.keepdishgoing.owners.adapter.out;

import be.kdg.keepdishgoing.owners.domain.Dish;
import be.kdg.keepdishgoing.owners.port.out.LoadDishesPort;
import be.kdg.keepdishgoing.owners.port.out.UpdateDishesPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DishesJpaAdapter implements UpdateDishesPort, LoadDishesPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(DishesJpaAdapter.class);
    private DishJpaRepository dishJpaRepository;

    public DishesJpaAdapter(DishJpaRepository dishJpaRepository) {
        this.dishJpaRepository = dishJpaRepository;
    }

}
