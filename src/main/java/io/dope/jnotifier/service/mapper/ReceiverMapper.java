package io.dope.jnotifier.service.mapper;


import io.dope.jnotifier.domain.*;
import io.dope.jnotifier.service.dto.ReceiverDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Receiver} and its DTO {@link ReceiverDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ReceiverMapper extends EntityMapper<ReceiverDTO, Receiver> {

    @Mapping(source = "id.id", target = "idId")
    ReceiverDTO toDto(Receiver receiver);

    @Mapping(source = "idId", target = "id")
    Receiver toEntity(ReceiverDTO receiverDTO);

    default Receiver fromId(String id) {
        if (id == null) {
            return null;
        }
        Receiver receiver = new Receiver();
        receiver.setId(id);
        return receiver;
    }
}
