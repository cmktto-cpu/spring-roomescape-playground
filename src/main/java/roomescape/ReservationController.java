package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ReservationController {

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return List.of(
                new Reservation(1L, "브라운", "2023-01-01", "10:00"),
                new Reservation(2L, "브라운", "2023-01-02", "11:00"),
                new Reservation(3L, "브라운", "2023-01-03", "12:00")
        );
    }

    public record Reservation(
            Long id,
            String name,
            String date,
            String time
    ) {
    }
}
