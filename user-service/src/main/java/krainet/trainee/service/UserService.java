package krainet.trainee.service;


import krainet.trainee.dao.api.IUserDao;
import krainet.trainee.dao.entity.ProjectEntity;
import krainet.trainee.dao.entity.UserEntity;
import krainet.trainee.service.api.IProjectService;
import krainet.trainee.service.api.IUserService;
import krainet.trainee.service.api.IVerificationService;
import krainet.trainee.service.core.dto.AboutUserDto;
import krainet.trainee.service.core.dto.UserDto;
import krainet.trainee.service.core.enums.EnumStatusRegistration;
import krainet.trainee.service.core.dto.PageOfUserDto;
import krainet.trainee.service.converter.user.ConverterDTOToUserEntity;
import krainet.trainee.service.converter.user.ConverterEntityToUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {

    private final IUserDao userDao;
    private final ConverterDTOToUserEntity entityConvert;
    private final ConverterEntityToUserDTO dtoConvert;
    private final PasswordEncoder encoder;
    private final IVerificationService verificationService;
    private final IProjectService projectService;

    public UserService(IUserDao userDao,
                       ConverterDTOToUserEntity entityConvert,
                       ConverterEntityToUserDTO dtoConvert,
                       PasswordEncoder encoder,
                       IVerificationService verificationService,
                       IProjectService projectService) {
        this.userDao = userDao;
        this.entityConvert = entityConvert;
        this.dtoConvert = dtoConvert;
        this.encoder = encoder;
        this.verificationService = verificationService;
        this.projectService = projectService;
    }

    @Override
    @Transactional
    public void create(UserDto userDTO) {
        UserEntity userEntity = entityConvert.convert(userDTO);

        if(userDao.findAllByMail(userEntity.getMail()) == null){
            userEntity.setUuid(UUID.randomUUID());
            userEntity.setPassword(encoder.encode(userDTO.getPassword()));

            Optional<ProjectEntity> projectEntity = projectService
                    .findByUuid(userDTO.getUuid_project().getUuid());

            if (projectEntity.isPresent()){
                userEntity.setProjectEntity(projectEntity.get());
            }

            if (userEntity.getStatus() == EnumStatusRegistration.WAITING_ACTIVATION) {
                verificationService.create(userEntity.getMail());
            }

            userDao.save(userEntity);
        }else {
            throw new IllegalArgumentException("Такой пользователь уже зарегистрирован!");
        }
    }

    @Override
    public Optional<UserEntity> findByMail(UUID uuid) {
        return userDao.findByUuid(uuid);
    }

    @Override
    @Transactional
    public void update(UUID uuid, Long dtUpdate, UserDto userDTO) {

        Optional<UserEntity> optional = userDao.findByUuid(uuid);
        UserEntity entity = optional.get();
        updateProperties(entity, userDTO);

        LocalDateTime updatedDateTime = Instant
                .ofEpochMilli(dtUpdate)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        entity.setDt_update(updatedDateTime);
        if(!encoder.matches(userDTO.getPassword(), entity.getPassword())){
            entity.setPassword(encoder.encode(userDTO.getPassword()));
        }
        userDao.saveAndFlush(entity);

    }

    @Override
    @Transactional
    public UserEntity findByMail(String mail) {
        return userDao.findAllByMail(mail);
    }

    @Override
    public void save (UserEntity userEntity){
        userDao.saveAndFlush(userEntity);
    }

    @Override
    public void delete(UUID uuid) {
        if (userDao.existsById(uuid)){
            userDao.deleteById(uuid);
        }else {
            throw new IllegalArgumentException("Такого пользователя нет");
        }
    }

    @Override
    public PageOfUserDto findByMail(Integer page, Integer size) {
        Page<UserEntity> users = userDao.findAll(PageRequest.of(page, size));
        return getPageUser(users);
    }

    private PageOfUserDto getPageUser(Page<UserEntity> objects) {
        PageOfUserDto pageOfUser = new PageOfUserDto();
        pageOfUser.setNumber(objects.getNumber());
        pageOfUser.setSize(objects.getSize());
        pageOfUser.setTotal_pages(objects.getTotalPages());
        pageOfUser.setNumber_of_elements(objects.getNumberOfElements());
        pageOfUser.setFirst(objects.isFirst());
        pageOfUser.setTotal_elements(objects.getTotalElements());
        pageOfUser.setLast(objects.isLast());

        List<AboutUserDto> userDTOList = objects
                .getContent()
                .stream()
                .map(dtoConvert::convertAboutUser)
                .collect(Collectors.toList());

        pageOfUser.setContent(userDTOList);
        return pageOfUser;
    }

    private void updateProperties(UserEntity entity, UserDto dto) {
        entity.setMail(dto.getMail());
        entity.setFio(dto.getFio());
        entity.setRole(dto.getRole());
        entity.setStatus(dto.getStatus());
        entity.setPassword(dto.getPassword());
    }
}
