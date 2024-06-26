package kurenkov.tutorservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "house", nullable = false)
    private String house;

    @Column(name = "office", nullable = false)
    private String office;

    /*@Column(name = "is_microrayon", nullable = true)
    private boolean isMicro;*/

    public Address updateAdress(Address newAddress){
        newAddress.setId(this.getId());
        return newAddress;
    }
}
