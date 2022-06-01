package dao;

import models.Student;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class StudentDAOImplHQL implements StudentDAO {
    private final Session session;

    public StudentDAOImplHQL(Session session) {
        this.session = session;
    }

    @Override
    public List<Student> findAll() throws Exception {
        session.beginTransaction();
        try {
            List<Student> students = session.createQuery("SELECT s FROM Student s", Student.class).getResultList();
            session.getTransaction().commit();
            return students;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error when getting a data from the database" +
                    e.getMessage());
        }
    }

    @Override
    public List<Student> findAllWithDetail() throws Exception {
        session.beginTransaction();
        try {
            List<Student> students = session
                    .createQuery("SELECT s FROM Student s " +
                            "WHERE s.person IS NOT NULL AND s.recordBook IS NOT NULL", Student.class)
                    .getResultList();
            session.getTransaction().commit();
            return students;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error when getting a data from the database." +
                    e.getMessage());
        }
    }

    @Override
    public List<Student> findAllWithoutRecordBook() throws Exception {
        session.beginTransaction();
        try {
            List<Student> students = session
                    .createQuery("SELECT s FROM Student s WHERE s.recordBook IS NULL", Student.class)
                    .getResultList();
            session.getTransaction().commit();
            return students;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error when getting a data from the database." +
                    e.getMessage());
        }
    }

    @Override
    public List<Student> findAllWithNamePattern(String pattern) throws Exception {
        session.beginTransaction();
        try {
            List<Student> students = session
                    .createQuery("SELECT s FROM Student s " +
                            "WHERE s.person.firstName LIKE :pattern OR " +
                            "s.person.middleName LIKE :pattern OR " +
                            "s.person.lastName LIKE :pattern", Student.class)
                    .setParameter("pattern", pattern)
                    .getResultList();
            session.getTransaction().commit();
            return students;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error when getting a data from the database." +
                    e.getMessage());
        }
    }

    @Override
    public Student findById(Long id) throws Exception {
        session.beginTransaction();

        try {
            return session.get(Student.class, id);
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Not found student with ID = " + id + "." +
                    e.getMessage());
        }
    }

    @Override
    public Student save(Student student) throws Exception {
        session.beginTransaction();

        try {
            session.save(student);
            session.getTransaction().commit();
            return student;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error when saving the data into the database. " +
                    e.getMessage());
        }
    }

    @Override
    public List<Student> save(List<Student> students) throws Exception {
        session.beginTransaction();

        try {
            for (Student student : students) {
                session.save(student);
            }
            session.getTransaction().commit();
            return students;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error when saving the data into the database. " +
                    e.getMessage());
        }
    }

    @Override
    public void delete(Student student) throws Exception {
        session.beginTransaction();

        try {
            session.delete(student);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error when deleting the data from the database. " +
                    e.getMessage());
        }
    }
}
