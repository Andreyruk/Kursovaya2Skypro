package task;

import java.time.LocalDateTime;

public class Task {
    /**
     * Счетчик id
     */
    private static int countId;
    /**
     * Идентификатор заявки
     */
    private int id;
    /**
     * Заголовок задачи
     */
    private String title;
    /**
     * Описание задачи
     */
    private String description;
    /**
     * Тип задачи
     */
    private TypeTask typeTask;
    /**
     * Дата, время создания задачи
     */
    protected LocalDateTime creationDate;
    /**
     * Периодичность
     */
    private Periodicity periodicity;
    /**
     * Признак архивности
     */
    private boolean isArchived;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeTask getTypeTask() {
        return typeTask;
    }

    public void setTypeTask(TypeTask typeTask) {
        this.typeTask = typeTask;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public Task(String title, String description, TypeTask typeTask, Periodicity periodicity) {
//        if (title == null || title.isBlank() || title.isEmpty()) {
//            throw new RuntimeException("Заголовок заполнен не верно");
//        }
//        if (description.isBlank() || description.isEmpty()) {
//            throw new RuntimeException("Описание заполнено не верно");
//        }
        this.id = ++countId;
        this.title = title;
        this.description = description;
        this.typeTask = typeTask;
        this.periodicity = periodicity;
        this.creationDate = LocalDateTime.now();
    }
}
