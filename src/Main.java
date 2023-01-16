import task.Periodicity;
import task.Task;
import task.TypeTask;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static Map<Integer, Task> taskList;
    private static Map<Integer, Task> archivedTaskList;

    public static void main(String[] args) {

        taskList = new HashMap<>();
        archivedTaskList = new HashMap<>();
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2: //todo: обрабатываем пункт меню 2
                            removeTask(scanner);
                            break;
                        case 3: //todo: обрабатываем пункт меню 3
                            getDayTasks(scanner);
                            break;
                        case 4:
                            getArchivedTasks();
                            break;
                        case 5:
                            editTask(scanner);
                            break;
                        case 6:
                            getTaskList();
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!\n");
                }
            }
        }
    }

    private static void inputTask(Scanner scanner) {
        System.out.print("Введите название задачи: ");
        String taskTitle = scanner.next();
        System.out.print("Введите описание задачи: ");
        String taskDescription = scanner.next();
        System.out.print("Введите дату задачи в формате dd.MM.yyyy: ");
        String taskDate = scanner.next();

        Task newTask = new Task(taskTitle, taskDescription, selectEnum(TypeTask.class, scanner, "Выберите тип задачи: ", "Тип задачи заполнен не верно"),
                selectEnum(Periodicity.class, scanner, "Выберите периодичность задачи: ", "Периодичность задачи заполнена не верно"));
        if (!taskDate.isBlank() && !taskDate.isEmpty()) {
            LocalDateTime day = LocalDateTime.parse(taskDate + " 00:00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            newTask.setCreationDate(day);
        }
        taskList.put(newTask.getId(), newTask);
        System.out.println("Задача " + newTask.getId() + " - добавлена в Список задач.\n");
    }

    private static void printMenu() {
        System.out.println(" 1. Добавить задачу\n 2. Удалить задачу\n 3. Получить задачу на указанный день\n 4. Получить список удаленных задач\n 5. Редактировать заголовок и описание задачи\n 6. Вывести список с группировкой по датам\n 0. Выход ");
    }

    private static <T extends Enum<T>> T selectEnum(Class<T> en, Scanner scanner, String message, String messageError) {
        List<String> types = Arrays.stream(en.getEnumConstants()).map(item -> item.toString()).collect(Collectors.toList());
        for (int i = 0; i < types.size(); i++) {
            System.out.println(i + 1 + ". " + types.get(i));
        }
        System.out.print(message);
        if (scanner.hasNextInt()) {
            int menu = scanner.nextInt();
            if (menu > types.size() || menu == 0) {
                throw new RuntimeException(messageError);
            }
            return en.getEnumConstants()[menu - 1];
        } else {
            throw new RuntimeException(messageError);
        }
    }

    private static void removeTask(Scanner scanner) {
        System.out.print("Введите номер задачи, которую нужно удалить: ");
        if (scanner.hasNextInt()) {
            int menu = scanner.nextInt();
            Task task = taskList.get(menu);
            if (task != null) {
                archivedTaskList.put(task.getId(), task);
                taskList.remove(task.getId());
                System.out.println("Задача удалена!\n");
            } else {
                System.out.println("Такая задача в списке отсутствует!\n");
            }
        }

    }

    private static void getDayTasks(Scanner scanner) {
        System.out.print("Введите дату в формате dd.MM.yyyy: ");
        if (scanner.hasNext()) {
            String datString = scanner.next();
            if (!datString.isBlank() && !datString.isEmpty()) {
                LocalDateTime day = LocalDateTime.parse(datString + " 00:00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
                taskList.entrySet().stream().filter(item -> item.getValue().getPeriodicity().isSuitableDate(item.getValue().getCreationDate(), day)).forEach(item -> {
                    System.out.printf("%s. ЗАДАЧА: %s, описание: %s, периодичность: %s%n", item.getValue().getId(), item.getValue().getTitle(), item.getValue().getDescription(), item.getValue().getPeriodicity());
                });
                System.out.println();
            }
        }
        if (taskList.isEmpty())
            System.out.println("Список задач пуст!\n");
    }

    private static void getArchivedTasks() {
        if (archivedTaskList == null || archivedTaskList.isEmpty()) {
            System.out.println("Удалённых задач пока нет!\n");
        } else
            archivedTaskList.forEach((key, value) -> System.out.printf("%s. ЗАДАЧА: %s, описание: %s%n", value.getId(), value.getTitle(), value.getDescription()));
        System.out.println();
    }

    private static void editTask(Scanner scanner) {
        System.out.print("Введите номер задачи, которую нужно отредактировать: ");
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            Task task = taskList.get(id);
            if (task != null) {
                System.out.print("Введите название задачи: ");
                String taskTitle = scanner.next();
                System.out.print("Введите описание задачи: ");
                String taskDescription = scanner.next();
                task.setTitle(taskTitle);
                task.setDescription(taskDescription);
                System.out.println("Задача отредактирована!\n");
            } else {
                System.out.println("Нет такой задачи!\n");
            }
        }
    }

    private static void getTaskList() {
        if (taskList == null || taskList.isEmpty()) {
            System.out.println("Список пустой, в списке пока нет задач!");
        }
        Set<LocalDate> dateList = taskList.entrySet().stream().map(item -> item.getValue().getCreationDate().toLocalDate()).collect(Collectors.toSet());
        TreeSet<LocalDate> myTreeSet = new TreeSet<>(dateList);
        myTreeSet.forEach(date -> {
            System.out.println(date);
            taskList.entrySet().stream().filter(item -> item.getValue().getCreationDate().toLocalDate().equals(date)).forEach(item -> {
                System.out.printf("%s. ЗАДАЧА: %s, описание: %s, периодичность: %s%n", item.getValue().getId(), item.getValue().getTitle(), item.getValue().getDescription(), item.getValue().getPeriodicity());
            });
        });
        System.out.println();
    }
}