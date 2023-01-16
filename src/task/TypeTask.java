package task;

public enum TypeTask {
    PRIVATE("Личный"),
    WORK("Рабочий");

    private final String typeDescription;


    TypeTask(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    @Override
    public String toString() {
        return typeDescription;
    }
}
