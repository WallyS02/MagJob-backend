package com.keepitup.magjobbackend.task.repository.api;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, BigInteger> {
    Optional<Task> findByTitle(String title);

    List<Task> findAllByOrganization(Organization organization);

    List<Task> findAllByDateOfCreation(ZonedDateTime dateOfCreation);

    List<Task> findAllByDateOfCompletion(ZonedDateTime dateOfCompletion);

    List<Task> findAllByIsDone(Boolean isDone);

    List<Task> findAllByDescription(String description);

    List<Task> findAllByIsImportant(Boolean isImportant);

    List<Task> findAllByDeadLine(ZonedDateTime deadLine);
}
