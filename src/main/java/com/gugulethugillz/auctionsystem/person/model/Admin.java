package com.gugulethugillz.auctionsystem.person.model;

import com.gugulethugillz.auctionsystem.common.enums.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import java.util.Arrays;
import java.util.Collection;

@Data
@NoArgsConstructor
@Entity
public class Admin extends Person {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(
                new SimpleGrantedAuthority("ROLE_" + UserRole.ADMIN.name()),
                new SimpleGrantedAuthority("ROLE_" + UserRole.USER.name())
        );
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
