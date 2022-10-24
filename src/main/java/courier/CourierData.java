package courier;
import lombok.Data;
@Data
public class CourierData {
    private String login;
    private String password;
    public CourierData (String login, String password) {
        this.login = login;
        this.password = password;
    }
    public static CourierData from(Courier courier) {
        return new CourierData (courier.getLogin(), courier.getPassword());
    }
    public static CourierData getWithoutLogin (Courier courier)  {
        return new CourierData ("", courier.getPassword());
    }
    public static CourierData getWithoutPassword (Courier courier)  {
        return new CourierData (courier.getLogin(), "");
    }
    public static CourierData getWrongPassword (Courier courier)  {
        return new CourierData(courier.getLogin(), "Pass");
    }
    public static CourierData getWrongLogin (Courier courier)  {
        return new CourierData ("Log", courier.getPassword());
    }
    public static CourierData getWrongCourier (Courier courier) {
        return new CourierData ("Log", "Pass");
    }
}