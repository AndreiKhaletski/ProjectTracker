package krainet.trainee.dao.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projects", schema = "app")
public class ProjectEntity {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @OneToMany(mappedBy = "projectEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserEntity> users;
    @Column(name = "name")
    private String name;
    @CreationTimestamp
    @Column(name = "dt_create")
    private LocalDateTime dt_create;
    @UpdateTimestamp
    @Version
    @Column(name = "dt_update")
    private LocalDateTime dt_update;
    @Column(name = "description")
    private String description;

    public ProjectEntity() {
    }

    public ProjectEntity(UUID uuid,
                         String name,
                         LocalDateTime dt_create,
                         LocalDateTime dt_update,
                         String description,
                         List<UserEntity> users) {
        this.uuid = uuid;
        this.name = name;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.description = description;
        this.users = users;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
