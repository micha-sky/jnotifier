package io.dope.jnotifier.service;

import io.dope.jnotifier.domain.Receiver;
import io.dope.jnotifier.repository.ReceiverRepository;
import io.dope.jnotifier.service.dto.ReceiverDTO;
import io.dope.jnotifier.service.mapper.ReceiverMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Receiver}.
 */
@Service
public class ReceiverService {

    private final Logger log = LoggerFactory.getLogger(ReceiverService.class);

    private final ReceiverRepository receiverRepository;

    private final ReceiverMapper receiverMapper;

    public ReceiverService(ReceiverRepository receiverRepository, ReceiverMapper receiverMapper) {
        this.receiverRepository = receiverRepository;
        this.receiverMapper = receiverMapper;
    }

    /**
     * Save a receiver.
     *
     * @param receiverDTO the entity to save.
     * @return the persisted entity.
     */
    public ReceiverDTO save(ReceiverDTO receiverDTO) {
        log.debug("Request to save Receiver : {}", receiverDTO);
        Receiver receiver = receiverMapper.toEntity(receiverDTO);
        receiver = receiverRepository.save(receiver);
        return receiverMapper.toDto(receiver);
    }

    /**
     * Get all the receivers.
     *
     * @return the list of entities.
     */
    public List<ReceiverDTO> findAll() {
        log.debug("Request to get all Receivers");
        return receiverRepository.findAll().stream()
            .map(receiverMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one receiver by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ReceiverDTO> findOne(String id) {
        log.debug("Request to get Receiver : {}", id);
        return receiverRepository.findById(id)
            .map(receiverMapper::toDto);
    }

    /**
     * Delete the receiver by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Receiver : {}", id);
        receiverRepository.deleteById(id);
    }
}
