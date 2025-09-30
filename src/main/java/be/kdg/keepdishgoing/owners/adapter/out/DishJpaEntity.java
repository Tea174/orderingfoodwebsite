package be.kdg.keepdishgoing.owners.adapter.out;


import be.kdg.keepdishgoing.owners.domain.DishType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dishes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DishJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer restaurantId;
    @Column(nullable = false)
    private Integer dishId;
    @Column(nullable = false)
    private String dishName;
    @Column(nullable = false)
    private DishType dishType;

}
