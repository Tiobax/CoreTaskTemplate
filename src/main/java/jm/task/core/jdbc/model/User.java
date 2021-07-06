package jm.task.core.jdbc.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table (name = "user")
public class User {
    @Id
    @Getter
    @Setter
    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column (name = "name", nullable = false)
    private String name;

    @Getter
    @Setter
    @Column (name = "lastName", nullable = false)
    private String lastName;


    @Getter
    @Setter
    @Column (name = "age", nullable = false)
    private Byte age;

    public User() {

    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
