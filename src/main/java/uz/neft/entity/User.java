package uz.neft.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Audited
@Entity(name = "users")
//@AuditOverride(forClass = Auditable.class)
//@AuditTable("office_aud")
//@EntityListeners(AuditingEntityListener.class)
public class User extends AbsEntityInteger implements UserDetails {
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    private String email;
    private String phone;
    @NotNull
    private String fio;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    private boolean active=true;


//    @OrderBy
//    @CreationTimestamp
//    @Column(nullable = false, updatable = false)
//    private Timestamp createdAt;
//
//    @LastModifiedDate
//    protected Timestamp updatedAt;
//
//    @CreatedBy
//    private Integer createdBy;
//
//    @LastModifiedBy
//    private Integer updatedBy;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
