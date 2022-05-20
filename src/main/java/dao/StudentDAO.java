package dao;

import models.Student;

import java.util.List;

public interface StudentDAO {
    // Найти всех студентов.
    public List<Student> findAll();

    // Найти все студентов, у которых указаны персона и зачетная книжка.
    public List<Student> findAllWithDetail();

    // Найти студента по идентификатору.
    public Student findById(Long id);

    // Вставить или обновить студента.
    public Student save(Student contact);

    // Удалить студента.
    public void delete(String contact);
}
