package io.dope.jnotifier.web.rest;

import io.dope.jnotifier.service.ReceiverService;
import io.dope.jnotifier.web.rest.errors.BadRequestAlertException;
import io.dope.jnotifier.service.dto.ReceiverDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.dope.jnotifier.domain.Receiver}.
 */
@RestController
@RequestMapping("/api")
public class ReceiverResource {

    private final Logger log = LoggerFactory.getLogger(ReceiverResource.class);

    private static final String ENTITY_NAME = "receiver";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReceiverService receiverService;

    public ReceiverResource(ReceiverService receiverService) {
        this.receiverService = receiverService;
    }

    /**
     * {@code POST  /receivers} : Create a new receiver.
     *
     * @param receiverDTO the receiverDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new receiverDTO, or with status {@code 400 (Bad Request)} if the receiver has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/receivers")
    public ResponseEntity<ReceiverDTO> createReceiver(@RequestBody ReceiverDTO receiverDTO) throws URISyntaxException {
        log.debug("REST request to save Receiver : {}", receiverDTO);
        if (receiverDTO.getId() != null) {
            throw new BadRequestAlertException("A new receiver cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReceiverDTO result = receiverService.save(receiverDTO);
        return ResponseEntity.created(new URI("/api/receivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /receivers} : Updates an existing receiver.
     *
     * @param receiverDTO the receiverDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receiverDTO,
     * or with status {@code 400 (Bad Request)} if the receiverDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the receiverDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/receivers")
    public ResponseEntity<ReceiverDTO> updateReceiver(@RequestBody ReceiverDTO receiverDTO) throws URISyntaxException {
        log.debug("REST request to update Receiver : {}", receiverDTO);
        if (receiverDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReceiverDTO result = receiverService.save(receiverDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, receiverDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /receivers} : get all the receivers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of receivers in body.
     */
    @GetMapping("/receivers")
    public List<ReceiverDTO> getAllReceivers() {
        log.debug("REST request to get all Receivers");
        return receiverService.findAll();
    }

    /**
     * {@code GET  /receivers/:id} : get the "id" receiver.
     *
     * @param id the id of the receiverDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the receiverDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/receivers/{id}")
    public ResponseEntity<ReceiverDTO> getReceiver(@PathVariable String id) {
        log.debug("REST request to get Receiver : {}", id);
        Optional<ReceiverDTO> receiverDTO = receiverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(receiverDTO);
    }

    /**
     * {@code DELETE  /receivers/:id} : delete the "id" receiver.
     *
     * @param id the id of the receiverDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/receivers/{id}")
    public ResponseEntity<Void> deleteReceiver(@PathVariable String id) {
        log.debug("REST request to delete Receiver : {}", id);
        receiverService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
