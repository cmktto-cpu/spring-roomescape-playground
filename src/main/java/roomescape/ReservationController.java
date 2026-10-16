package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private long nextId = 1L;

    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(
            @RequestBody ReservationRequest request) {

        // 필수 입력값 검사
        if (isBlank(request.name())
                || isBlank(request.date())
                || isBlank(request.time())) {
            return ResponseEntity.badRequest().build();
        }

        Reservation reservation = new Reservation(
                nextId++,
                request.name(),
                request.date(),
                request.time()
        );

        reservations.add(reservation);

        return ResponseEntity
                .created(URI.create("/reservations/" + reservation.id()))
                .body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id) {

        boolean removed = reservations.removeIf(
                r -> r.id().equals(id)
        );

        if (!removed) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public record ReservationRequest(
            String name,
            String date,
            String time
    ) {}

    public record Reservation(
            Long id,
            String name,
            String date,
            String time
    ) {}
}