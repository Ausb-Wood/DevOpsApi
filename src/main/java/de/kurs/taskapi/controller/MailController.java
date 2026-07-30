import org.springframework.web.bind.annotation.GetMapping; // Verknüpft HTTP GET mit einer Methode.
import org.springframework.web.bind.annotation.RequestMapping; // Gemeinsamer URL-Anfang.
import org.springframework.web.bind.annotation.RestController; // Rückgaben werden als JSON gesendet.

import java.util.Map; // Schlüssel-Wert-Sammlung.

/** Liefert den technischen Zustand der Anwendung. */
@RestController
@RequestMapping("/api/mail")
public class MailController {

    @GetMapping // Reagiert auf GET /api/mail.
    public Map<String, String> mail() {
        return Map.of("new mail", "te qiuero"); // Spring wandelt die Map in JSON um.
    }
}