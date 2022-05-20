import models.Person;
import models.RecordBook;
import models.Student;
import org.hibernate.Session;
import utils.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List<String> firstNames = new ArrayList<String>();
        firstNames.add("Иван");
        firstNames.add("Петр");
        firstNames.add("Никита");
        firstNames.add("Дима");
        List<String> middleNames = new ArrayList<String>();
        middleNames.add("Иванович");
        middleNames.add("Петрович");
        middleNames.add("Никитич");
        middleNames.add("Дмитривич");
        List<String> lastNames = new ArrayList<String>();
        lastNames.add("Иванов");
        lastNames.add("Петров");
        lastNames.add("Сидоров");
        lastNames.add("Романов");
        List<String> groups = new ArrayList<String>();
        groups.add("РИ-170002");
        groups.add("РИМ-110971");

        Random random = new Random();

        List<Student> students = new ArrayList<Student>();

        for (int i = 0; i < 10; i++) {
            String firstName = firstNames.get(random.nextInt(firstNames.size()));
            String middleName = middleNames.get(random.nextInt(middleNames.size()));
            String lastName = lastNames.get(random.nextInt(lastNames.size()));
            String group = groups.get(random.nextInt(groups.size()));
            Person person = new Person(
                    getRandomInt(6000, 6999),
                    getRandomInt(0, 999999),
                    firstName,
                    middleName,
                    lastName
            );

            RecordBook recordBook = random.nextBoolean() ? new RecordBook(getRandomInt(0, 999999)) : null;
            Student student = new Student(person, recordBook, group);
            students.add(student);
        }

        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        session.beginTransaction();

        try {
            for (Student student : students) {
                session.save(student);
            }
            session.getTransaction().commit();
        }
        catch (Exception e) {
            session.getTransaction().rollback();
        }

        session.close();
    }

    private static int getRandomInt(int minValue, int maxValue) {
        return (int) (Math.random() * (maxValue - minValue + 1) + minValue);
    }
}

