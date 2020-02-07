package io.dope.jnotifier.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.dope.jnotifier.domain.Receiver} entity.
 */
public class ReceiverDTO implements Serializable {

    private String id;

    private String name;

    private String email;

    private String phone;


    private String idId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdId() {
        return idId;
    }

    public void setIdId(String productId) {
        this.idId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReceiverDTO receiverDTO = (ReceiverDTO) o;
        if (receiverDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), receiverDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReceiverDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", idId=" + getIdId() +
            "}";
    }
}
