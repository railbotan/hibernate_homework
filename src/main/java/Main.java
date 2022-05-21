import dao.StudentDAO;
import dao.StudentDAOImplCriteria;
import dao.StudentDAOImplHQL;
import models.Person;
import models.RecordBook;
import models.Student;
import org.hibernate.Session;
import utils.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Student> students = generateStudentsList();

        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        StudentDAO hqlDAO = new StudentDAOImplHQL(session);
        StudentDAO criteriaDAO = new StudentDAOImplCriteria(session);

        students = hqlDAO.save(students);

        printStudents(students, "Созданые студенты");

        printStudents(hqlDAO.findAllWithNamePattern("%а%"), "Студенты с буквой 'а' в ФИО (HQL)");

        printStudents(criteriaDAO.findAllWithNamePattern("%а%"), "Студенты с буквой 'а' в ФИО (Criteria)");

        printStudents(hqlDAO.findAllWithoutRecordBook(), "Студенты без зачеток (HQL)");

        printStudents(criteriaDAO.findAllWithoutRecordBook(), "Студенты без зачеток (Criteria)");

        session.close();
    }

    private static void printStudents(List<Student> students, String message) {
        System.out.println(message);
        System.out.println("ID\tИмя\tОтчество\tФамилия\tСерия паспорта\tНомер паспорта\tНомер зачетки\tГруппа");
        for (Student student : students) {
            System.out.printf("%1$s\t%2$s\t%3$s\t%4$s\t%5$s\t%6$s\t%7$s\t%8$s\n",
                    student.getId(),
                    student.getPerson() != null ? student.getPerson().getFirstName() : "NULL",
                    student.getPerson() != null ? student.getPerson().getMiddleName() : "NULL",
                    student.getPerson() != null ? student.getPerson().getLastName() : "NULL",
                    student.getPerson() != null ? student.getPerson().getPassportSerial() : "NULL",
                    student.getPerson() != null
                            ? padLeft(String.valueOf(student.getPerson().getPassportNumber()), 6)
                            : "NULL",
                    student.getRecordBook() != null ? student.getRecordBook().getCode() : "NULL",
                    student.getGroup()
            );
        }
        System.out.println();
        System.out.println();
    }

    private static List<Student> generateStudentsList() {
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
        return students;
    }

    private static int getRandomInt(int minValue, int maxValue) {
        return (int) (Math.random() * (maxValue - minValue + 1) + minValue);
    }

    private static String padLeft(String value, int length) {
        return String.format("%1$" + length + "s", value).replace(' ', '0');
    }
}

