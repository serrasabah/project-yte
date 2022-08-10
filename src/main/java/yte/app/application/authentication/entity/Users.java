package yte.app.application.authentication.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import yte.app.application.Job.Entity.Job;

import yte.app.application.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users extends BaseEntity implements UserDetails {

    private String username;
    private String password;

    private String role;

    public Users(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id")

    )
    private List<Authority> authorities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs = new ArrayList<>();

    //  @OneToMany(mappedBy = "users")
    // private List<Job> jobs = new ArrayList<>();


    public <E> Users(String username, String password, List<E> authority) {
        super();
        this.username = username;
        this.password = password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void update(Users updatedStudent) {
        this.username = updatedStudent.username;
        this.password = updatedStudent.password;
    }

    public void addJob(Job job) {
        this.jobs.add(job);
        job.setUser(this);
    }
}