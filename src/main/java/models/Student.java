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
@Table(name = "students")
public class Student{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "record_book_id", referencedColumnName = "id")
    private RecordBook recordBook;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
    @Column(name = "student_group")
    private String group;

    public Student(Person person, RecordBook recordBook, String group) {
        this.person = person;
        this.recordBook = recordBook;
        this.group = group;
    }
}

