package dao;

import models.Student;

import java.util.List;

public interface StudentDAO {
    // Найти всех студентов.
    List<Student> findAll() throws Exception;

    // Найти все студентов, у которых указаны персона и зачетная книжка.
    List<Student> findAllWithDetail() throws Exception;

    List<Student> findAllWithNamePattern(String pattern) throws Exception;

    List<Student> findAllWithoutRecordBook() throws Exception;

    // Найти студента по идентификатору.
    Student findById(Long id) throws Exception;

    // Вставить или обновить студента.
    Student save(Student student) throws Exception;

    List<Student> save(List<Student> students) throws Exception;

    // Удалить студента.
    void delete(Student student) throws Exception;
}
