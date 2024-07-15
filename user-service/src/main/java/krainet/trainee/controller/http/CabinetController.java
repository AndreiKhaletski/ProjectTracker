package krainet.trainee.controller.http;

import krainet.trainee.service.api.ICabinetService;
import krainet.trainee.service.core.dto.AboutUserDto;
import krainet.trainee.service.core.dto.AuthorizationDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/cabinet")
public class CabinetController {

    private final ICabinetService cabinetService;

    public CabinetController(ICabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    @GetMapping(value = "/verification")
    public ResponseEntity<?> get(@RequestParam("code") String code,
                    @RequestParam("mail") String email) {
        cabinetService.verification(code, email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> authorization(@RequestBody AuthorizationDto authorizationDTO) {

        String authorization = cabinetService.authorization(authorizationDTO);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .build();
    }

    @PutMapping(value = "/logout/{uuid}")
    public ResponseEntity<?> exit(@PathVariable("uuid") UUID uuid){
        cabinetService.exit(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/me")
    public ResponseEntity get() {
        AboutUserDto aboutUserDTO = cabinetService.getInfoMe();
        return ResponseEntity.status(HttpStatus.CREATED).body(aboutUserDTO);
    }
}
