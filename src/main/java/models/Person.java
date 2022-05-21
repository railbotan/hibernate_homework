package models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons", uniqueConstraints = {
        @UniqueConstraint(name = "UK_person", columnNames = {"passport_serial", "passport_number"})
})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "passport_serial")
    private Integer passportSerial;
    @Column(name = "passport_number")
    private Integer passportNumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;

    public Person(Integer passportSerial, Integer passportNumber,
                  String firstName, String middleName, String lastName) {
        this.passportSerial = passportSerial;
        this.passportNumber = passportNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
}
