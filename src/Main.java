import service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserService();
        service.register("Sako", "1234");
    }
}