package krainet.trainee.dao.entity;

import krainet.trainee.service.core.enums.EnumStatusUser;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "records", schema = "app")
public class RecordEntity {
    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_user", referencedColumnName = "uuid")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_project", referencedColumnName = "uuid")
    private ProjectEntity projectEntity;

    @Column(name = "start_time")
    private LocalDateTime login;
    @Column(name = "end_time")
    private LocalDateTime logout;
    @Column(name = "duration")
    private Integer duration;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EnumStatusUser status;

    public RecordEntity() {
    }

    public RecordEntity(UUID uuid,
                        UserEntity userEntity,
                        ProjectEntity projectEntity,
                        LocalDateTime login,
                        LocalDateTime logout,
                        Integer duration,
                        EnumStatusUser status) {
        this.uuid = uuid;
        this.userEntity = userEntity;
        this.projectEntity = projectEntity;
        this.login = login;
        this.logout = logout;
        this.duration = duration;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public void setProjectEntity(ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
    }

    public LocalDateTime getLogin() {
        return login;
    }

    public void setLogin(LocalDateTime login) {
        this.login = login;
    }

    public LocalDateTime getLogout() {
        return logout;
    }

    public void setLogout(LocalDateTime logout) {
        this.logout = logout;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public EnumStatusUser getStatus() {
        return status;
    }

    public void setStatus(EnumStatusUser status) {
        this.status = status;
    }
}
