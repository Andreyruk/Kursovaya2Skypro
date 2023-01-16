package task;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public enum Periodicity {
    SINGLE("однократная") {
        @Override
        public boolean isSuitableDate(LocalDateTime taskDate, LocalDateTime enteredDate) {
            if (taskDate != null && enteredDate != null) {
                return taskDate.toLocalDate().equals(enteredDate.toLocalDate());
            }
            return false;
        }
    },
    DAILY("ежедневная") {
        @Override
        public boolean isSuitableDate(LocalDateTime taskDate, LocalDateTime enteredDate) {
            if (taskDate != null && enteredDate != null) {
                return enteredDate.toLocalDate().equals(taskDate.toLocalDate()) || enteredDate.toLocalDate().isAfter(taskDate.toLocalDate());
            }
            return false;
        }
    },
    WEEKLY("еженедельная") {
        @Override
        public boolean isSuitableDate(LocalDateTime taskDate, LocalDateTime enteredDate) {
            if (taskDate != null && enteredDate != null) {
                long diff = ChronoUnit.WEEKS.between(taskDate, enteredDate.plusDays(1));
                return taskDate.toLocalDate().plusWeeks(diff).equals(enteredDate.toLocalDate());
            }
            return false;
        }
    },
    MONTHLY("ежемесячная") {
        @Override
        public boolean isSuitableDate(LocalDateTime taskDate, LocalDateTime enteredDate) {
            if (taskDate != null && enteredDate != null) {
                long diff = ChronoUnit.MONTHS.between(taskDate, enteredDate);
                return taskDate.toLocalDate().plusMonths(diff).equals(enteredDate.toLocalDate());
            }
            return false;
        }
    },
    ANNUAL("ежегодная") {
        @Override
        public boolean isSuitableDate(LocalDateTime taskDate, LocalDateTime enteredDate) {
            if (taskDate != null && enteredDate != null) {
                long diff = ChronoUnit.YEARS.between(taskDate, enteredDate.plusDays(1));
                return taskDate.toLocalDate().plusYears(diff).equals(enteredDate.toLocalDate());
            }
            return false;
        }
    };

    private final String periodDescription;

    Periodicity(String periodDescription) {
        this.periodDescription = periodDescription;
    }

    public String getPeriodDescription() {
        return periodDescription;
    }

    public abstract boolean isSuitableDate(LocalDateTime taskDate, LocalDateTime enteredDate);

    @Override
    public String toString() {
        return periodDescription;
    }
}