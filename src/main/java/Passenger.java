public class Passenger {
    private String passport;
    private String name;
    private String surname;

    public Passenger(String passport, String name, String surname) {
        this.passport = passport;
        this.name = name;
        this.surname = surname;
    }

    public String getPassport() {
        return passport;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "passport= " + passport +
                ", name= " + name +
                ", surname= " + surname;
    }
}
