import org.apache.commons.lang3.StringUtils;

public class Main {
    public static void main(String[] args) {
        String result = StringUtils.join("üks", "kaks");
        System.out.println(result);
    }
}
