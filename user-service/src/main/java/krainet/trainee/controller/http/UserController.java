package krainet.trainee.controller.http;

import krainet.trainee.dao.entity.UserEntity;
import krainet.trainee.service.api.IUserService;
import krainet.trainee.service.core.dto.AboutUserDto;
import krainet.trainee.service.core.dto.UserDto;
import krainet.trainee.service.core.dto.PageOfUserDto;
import krainet.trainee.service.converter.user.ConverterEntityToUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;
    private final ConverterEntityToUserDTO convertToDto;


    public UserController(IUserService userService,
                          ConverterEntityToUserDTO convertToDto) {
        this.userService = userService;
        this.convertToDto = convertToDto;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDto userDTO){
        userService.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PageOfUserDto> get(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return ResponseEntity.ok(userService.findByMail(page, size));
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<AboutUserDto> get(@PathVariable("uuid") UUID uuid){
        Optional<UserEntity> accountEntity = (userService.findByMail(uuid));
        if(accountEntity.isPresent()){
            return ResponseEntity.ok(convertToDto.convertAboutUser(accountEntity.get()));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> put(@PathVariable("uuid") UUID uuid,
                                 @PathVariable("dt_update") Long dtUpdate,
                                 @RequestBody UserDto userDTO){
        userService.update(uuid, dtUpdate, userDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<?> delete(@PathVariable("uuid") UUID uuid){
        userService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
