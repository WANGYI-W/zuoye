import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Db {
    public static void main(String[] args) {
        Database db = new Database();

        Office office = new Office("O100", "08:30-17:30");
        Canteen canteen = new Canteen("C001", "学生食堂");
        Supermarket supermarket = new Supermarket("S001", "校园超市");

        MealCard card1 = new MealCard("M1001", 500.00, office);
        Student student1 = new Student("20241001", "李华", "计算机学院", "软件工程一班", card1);
        db.addStudent(student1);

        card1.addConsumption(new Consumption(canteen, 28.50, LocalDateTime.now()));
        card1.addConsumption(new Consumption(supermarket, 15.20, LocalDateTime.now().minusDays(1)));

        db.printStudentInfo("20241001");
        db.printCardConsumptions("M1001");
    }
}

class Database {
    private final List<Student> students = new ArrayList<>();
    private final List<MealCard> mealCards = new ArrayList<>();
    private final List<Office> offices = new ArrayList<>();
    private final List<Canteen> canteens = new ArrayList<>();
    private final List<Supermarket> supermarkets = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
        if (!mealCards.contains(student.getMealCard())) {
            mealCards.add(student.getMealCard());
        }
        if (!offices.contains(student.getMealCard().getOffice())) {
            offices.add(student.getMealCard().getOffice());
        }
    }

    public Optional<Student> findStudentById(String studentId) {
        return students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst();
    }

    public Optional<MealCard> findMealCardByNumber(String cardNumber) {
        return mealCards.stream()
                .filter(c -> c.getCardNumber().equals(cardNumber))
                .findFirst();
    }

    public void printStudentInfo(String studentId) {
        findStudentById(studentId).ifPresentOrElse(student -> {
            System.out.println("学生信息：");
            System.out.println(student);
            System.out.println("关联饭卡：" + student.getMealCard());
        }, () -> System.out.println("未找到学号为 " + studentId + " 的学生。"));
    }

    public void printCardConsumptions(String cardNumber) {
        findMealCardByNumber(cardNumber).ifPresentOrElse(card -> {
            System.out.println("\n饭卡消费记录：");
            card.getConsumptions().forEach(System.out::println);
        }, () -> System.out.println("未找到卡号为 " + cardNumber + " 的饭卡。"));
    }
}

class Student {
    private final String studentId;
    private final String name;
    private final String college;
    private final String studentClass;
    private final MealCard mealCard;

    public Student(String studentId, String name, String college, String studentClass, MealCard mealCard) {
        this.studentId = studentId;
        this.name = name;
        this.college = college;
        this.studentClass = studentClass;
        this.mealCard = mealCard;
    }

    public String getStudentId() {
        return studentId;
    }

    public MealCard getMealCard() {
        return mealCard;
    }

    @Override
    public String toString() {
        return String.format("学号: %s, 姓名: %s, 学院: %s, 班级: %s", studentId, name, college, studentClass);
    }
}

class MealCard {
    private final String cardNumber;
    private double balance;
    private final Office office;
    private final List<Consumption> consumptions = new ArrayList<>();

    public MealCard(String cardNumber, double balance, Office office) {
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.office = office;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Office getOffice() {
        return office;
    }

    public List<Consumption> getConsumptions() {
        return consumptions;
    }

    public void addConsumption(Consumption consumption) {
        if (consumption.getAmount() <= balance) {
            balance -= consumption.getAmount();
            consumptions.add(consumption);
        } else {
            throw new IllegalArgumentException("余额不足，无法消费。");
        }
    }

    @Override
    public String toString() {
        return String.format("卡号: %s, 余额: %.2f, 管理办公室: %s", cardNumber, balance, office);
    }
}

class Office {
    private final String officeId;
    private final String businessHours;

    public Office(String officeId, String businessHours) {
        this.officeId = officeId;
        this.businessHours = businessHours;
    }

    @Override
    public String toString() {
        return String.format("办公室编号: %s, 营业时间: %s", officeId, businessHours);
    }
}

abstract class Merchant {
    private final String id;
    private final String name;

    protected Merchant(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", id, name);
    }
}

class Canteen extends Merchant {
    public Canteen(String id, String name) {
        super(id, name);
    }
}

class Supermarket extends Merchant {
    public Supermarket(String id, String name) {
        super(id, name);
    }
}

class Consumption {
    private final Merchant merchant;
    private final double amount;
    private final LocalDateTime dateTime;

    public Consumption(Merchant merchant, double amount, LocalDateTime dateTime) {
        this.merchant = merchant;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s 消费: %.2f 元, 时间: %s", merchant instanceof Canteen ? "食堂" : "超市", merchant, amount, dateTime);
    }
}
