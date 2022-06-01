package dao.listeners;

import models.Student;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;

import java.util.Set;

public class DeleteWithMessage implements PostDeleteEventListener {

    @Override
    public void onPostDelete(PostDeleteEvent postDeleteEvent) {
        if (postDeleteEvent.getEntity() instanceof Student student) {
            System.out.println("Следующий студент был удален из базы данных");
            System.out.printf("%1$s\t%2$s\t%3$s\t%4$s\t%5$s\t%6$s\t%7$s\t%8$s\n",
                    student.getId(),
                    student.getPerson() != null ? student.getPerson().getFirstName() : "NULL",
                    student.getPerson() != null ? student.getPerson().getMiddleName() : "NULL",
                    student.getPerson() != null ? student.getPerson().getLastName() : "NULL",
                    student.getPerson() != null ? student.getPerson().getPassportSerial() : "NULL",
                    student.getPerson() != null ? student.getPerson().getPassportNumber() : "NULL",
                    student.getRecordBook() != null ? student.getRecordBook().getCode() : "NULL",
                    student.getGroup()
            );
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister persister) {
        return PostDeleteEventListener.super.requiresPostCommitHandling(persister);
    }
}
