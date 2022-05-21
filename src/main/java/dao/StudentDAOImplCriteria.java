package dao;

import models.Student;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImplCriteria implements StudentDAO {
    private final Session session;

    public StudentDAOImplCriteria(Session session) {
        this.session = session;
    }

    @Override
    public List<Student> findAll() throws Exception {
        session.beginTransaction();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
            Root<Student> root = query.from(Student.class);
            query.select(root);
            List<Student> students = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return students;
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error when getting a data from the database" +
                    e.getMessage());
        }

    }

    @Override
    public List<Student> findAllWithDetail() throws Exception {
        session.beginTransaction();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
            Root<Student> root = query.from(Student.class);
            Predicate personIsNotNull = criteriaBuilder.isNotNull(root.get("person"));
            Predicate recordBookIsNotNull = criteriaBuilder.isNotNull(root.get("recordBook"));
            query.select(root);
            query.where(criteriaBuilder.and(personIsNotNull, recordBookIsNotNull));
            List<Student> students = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return students;
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error when getting a data from the database." +
                    e.getMessage());
        }
    }

    @Override
    public List<Student> findAllWithoutRecordBook() throws Exception {
        session.beginTransaction();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
            Root<Student> root = query.from(Student.class);
            query.select(root);
            query.where(criteriaBuilder.isNull(root.get("recordBook")));
            List<Student> students = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return students;
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error when getting a data from the database." +
                    e.getMessage());
        }
    }

    @Override
    public List<Student> findAllWithNamePattern(String pattern) throws Exception {
        session.beginTransaction();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
            Root<Student> root = query.from(Student.class);
            Predicate firstNameCheckPattern = criteriaBuilder.like(root.get("person").get("firstName"), pattern);
            Predicate middleNameCheckPattern = criteriaBuilder.like(root.get("person").get("middleName"), pattern);
            Predicate lastNameCheckPattern = criteriaBuilder.like(root.get("person").get("lastName"), pattern);
            query.select(root);
            query.where(criteriaBuilder.or(firstNameCheckPattern, middleNameCheckPattern, lastNameCheckPattern));
            List<Student> students = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return students;
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception("Error when saving the data into the database. " +
                    e.getMessage());
        }
    }

    @Override
    public void delete(String contact) throws Exception {

    }
}
