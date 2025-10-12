package be.kdg.keepdishgoing.orders.adapter.out.basket;

import be.kdg.keepdishgoing.orders.domain.basket.Basket;
import be.kdg.keepdishgoing.orders.port.out.basket.LoadBasketPort;
import be.kdg.keepdishgoing.orders.port.out.basket.SaveBasketPort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class BasketJpaAdapter implements LoadBasketPort, SaveBasketPort {

    private final BasketJpaRepository basketJpaRepository;
    private final BasketMapper basketMapper;

    public BasketJpaAdapter(
            BasketJpaRepository basketJpaRepository,
            BasketMapper basketMapper
    ) {
        this.basketJpaRepository = basketJpaRepository;
        this.basketMapper = basketMapper;
    }

    @Override
    public Optional<Basket> loadByCustomerId(UUID customerId) {
        return basketJpaRepository.findByCustomerId(customerId)
                .map(basketMapper::toDomain);
    }

    @Override
    public Optional<Basket> loadById(UUID basketId) {
        return basketJpaRepository.findById(basketId)
                .map(basketMapper::toDomain);
    }

    @Override
    public void save(Basket basket) {
        BasketJpaEntity entity = basketMapper.toJpaEntity(basket);
        basketJpaRepository.save(entity);
    }

    @Override
    public void delete(Basket basket) {
        basketJpaRepository.deleteById(basket.getBasketId().id());
    }
}
