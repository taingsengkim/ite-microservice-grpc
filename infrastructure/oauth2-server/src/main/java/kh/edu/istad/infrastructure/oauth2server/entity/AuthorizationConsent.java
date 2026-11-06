package kh.edu.istad.infrastructure.oauth2server.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "`oauth2_authorization_consent`")
@IdClass(AuthorizationConsent.AuthorizationConsentId.class)
@Getter
@Setter
public class AuthorizationConsent {
    @Id
    private String registeredClientId;

    @Id
    private String principalName;

    @Column(length = 1000)
    private String authorities;

    public static class AuthorizationConsentId implements Serializable {
        private String registeredClientId;
        private String principalName;

        public String getRegisteredClientId() {
            return registeredClientId;
        }

        public void setRegisteredClientId(String registeredClientId) {
            this.registeredClientId = registeredClientId;
        }

        public String getPrincipalName() {
            return principalName;
        }

        public void setPrincipalName(String principalName) {
            this.principalName = principalName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AuthorizationConsentId that = (AuthorizationConsentId) o;
            return registeredClientId.equals(that.registeredClientId) && principalName.equals(that.principalName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(registeredClientId, principalName);
        }
    }
}
