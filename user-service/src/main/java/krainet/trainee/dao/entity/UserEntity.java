package krainet.trainee.dao.entity;

import krainet.trainee.service.core.enums.EnumRole;
import krainet.trainee.service.core.enums.EnumStatusRegistration;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "app")
public class UserEntity {
    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_uuid")
    private ProjectEntity projectEntity;

    @CreationTimestamp
    @Column(name = "dt_create")
    private LocalDateTime dt_create;
    @UpdateTimestamp
    @Version
    @Column(name = "dt_update")
    private LocalDateTime dt_update;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecordEntity> records;

    @Column(name = "mail")
    private String mail;
    @Column(name = "fio")
    private String fio;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private EnumRole role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EnumStatusRegistration status;
    @Column(name = "password")
    private String password;

    public UserEntity() {
    }

    public UserEntity(UUID uuid,
                      ProjectEntity projectEntity,
                      LocalDateTime dt_create,
                      LocalDateTime dt_update,
                      String mail,
                      String fio,
                      EnumRole role,
                      EnumStatusRegistration status,
                      String password) {
        this.uuid = uuid;
        this.projectEntity = projectEntity;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public void setProjectEntity(ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
    }

    public LocalDateTime getDt_create() {
        return dt_create;
    }

    public void setDt_create(LocalDateTime dt_create) {
        this.dt_create = dt_create;
    }

    public LocalDateTime getDt_update() {
        return dt_update;
    }

    public void setDt_update(LocalDateTime dt_update) {
        this.dt_update = dt_update;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public EnumRole getRole() {
        return role;
    }

    public void setRole(EnumRole role) {
        this.role = role;
    }

    public EnumStatusRegistration getStatus() {
        return status;
    }

    public void setStatus(EnumStatusRegistration status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
