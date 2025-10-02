package be.kdg.keepdishgoing.owners.adapter.out.owner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "owners", schema = "kdg_owners")
@AllArgsConstructor
public class OwnerJpaEntity {

    @Id
    private UUID ownerId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private Integer phoneNumber;

    @Column
    private String address;

    public OwnerJpaEntity() {
        this.ownerId = UUID.randomUUID();
    }
}