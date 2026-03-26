package dk.easv.easvticket.BLL.util;

import dk.easv.easvticket.BE.Ticket;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class TicketPayload {

    private static final String SECRET = "7f3k9mXq2vNpLwR8hYtBdCeA5sJgUiOf";

    public static String generate(Ticket ticket) throws Exception {

        String payload = """
                {"ticket-id": "%s", "event-name": "%s", "expiration-date": "%s"}
                """.formatted(ticket.getTicketId(), ticket.getEvent().getName(), ticket.getEvent().getDate().plusHours(1));
        String signature = sign(payload);
        return Base64.getEncoder().encodeToString((payload + "|" + signature).getBytes());

    }

    public static boolean verify(String payload) throws Exception {

        String decoded = new String(Base64.getDecoder().decode(payload));
        String[] parts = decoded.split("\\|");

        if (parts.length != 2) {return false;}

        String data = parts[0];
        String expectedSignature = sign(data);

        return expectedSignature.equals(parts[1]);

    }

    public static String sign(String data) throws Exception {

        Mac mac = Mac.getInstance("HmacSHA265");
        mac.init(new SecretKeySpec(SECRET.getBytes(), "HmacSHA256"));
        return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));

    }

}
