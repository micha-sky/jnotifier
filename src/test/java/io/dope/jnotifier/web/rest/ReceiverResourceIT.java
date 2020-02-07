package io.dope.jnotifier.web.rest;

import io.dope.jnotifier.JnotifierApp;
import io.dope.jnotifier.domain.Receiver;
import io.dope.jnotifier.repository.ReceiverRepository;
import io.dope.jnotifier.service.ReceiverService;
import io.dope.jnotifier.service.dto.ReceiverDTO;
import io.dope.jnotifier.service.mapper.ReceiverMapper;
import io.dope.jnotifier.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.util.List;

import static io.dope.jnotifier.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReceiverResource} REST controller.
 */
@SpringBootTest(classes = JnotifierApp.class)
public class ReceiverResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private ReceiverRepository receiverRepository;

    @Autowired
    private ReceiverMapper receiverMapper;

    @Autowired
    private ReceiverService receiverService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restReceiverMockMvc;

    private Receiver receiver;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReceiverResource receiverResource = new ReceiverResource(receiverService);
        this.restReceiverMockMvc = MockMvcBuilders.standaloneSetup(receiverResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receiver createEntity() {
        Receiver receiver = new Receiver()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE);
        return receiver;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receiver createUpdatedEntity() {
        Receiver receiver = new Receiver()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
        return receiver;
    }

    @BeforeEach
    public void initTest() {
        receiverRepository.deleteAll();
        receiver = createEntity();
    }

    @Test
    public void createReceiver() throws Exception {
        int databaseSizeBeforeCreate = receiverRepository.findAll().size();

        // Create the Receiver
        ReceiverDTO receiverDTO = receiverMapper.toDto(receiver);
        restReceiverMockMvc.perform(post("/api/receivers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiverDTO)))
            .andExpect(status().isCreated());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeCreate + 1);
        Receiver testReceiver = receiverList.get(receiverList.size() - 1);
        assertThat(testReceiver.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReceiver.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testReceiver.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    public void createReceiverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = receiverRepository.findAll().size();

        // Create the Receiver with an existing ID
        receiver.setId("existing_id");
        ReceiverDTO receiverDTO = receiverMapper.toDto(receiver);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceiverMockMvc.perform(post("/api/receivers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllReceivers() throws Exception {
        // Initialize the database
        receiverRepository.save(receiver);

        // Get all the receiverList
        restReceiverMockMvc.perform(get("/api/receivers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiver.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    public void getReceiver() throws Exception {
        // Initialize the database
        receiverRepository.save(receiver);

        // Get the receiver
        restReceiverMockMvc.perform(get("/api/receivers/{id}", receiver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(receiver.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    public void getNonExistingReceiver() throws Exception {
        // Get the receiver
        restReceiverMockMvc.perform(get("/api/receivers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateReceiver() throws Exception {
        // Initialize the database
        receiverRepository.save(receiver);

        int databaseSizeBeforeUpdate = receiverRepository.findAll().size();

        // Update the receiver
        Receiver updatedReceiver = receiverRepository.findById(receiver.getId()).get();
        updatedReceiver
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
        ReceiverDTO receiverDTO = receiverMapper.toDto(updatedReceiver);

        restReceiverMockMvc.perform(put("/api/receivers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiverDTO)))
            .andExpect(status().isOk());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeUpdate);
        Receiver testReceiver = receiverList.get(receiverList.size() - 1);
        assertThat(testReceiver.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReceiver.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testReceiver.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    public void updateNonExistingReceiver() throws Exception {
        int databaseSizeBeforeUpdate = receiverRepository.findAll().size();

        // Create the Receiver
        ReceiverDTO receiverDTO = receiverMapper.toDto(receiver);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiverMockMvc.perform(put("/api/receivers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Receiver in the database
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteReceiver() throws Exception {
        // Initialize the database
        receiverRepository.save(receiver);

        int databaseSizeBeforeDelete = receiverRepository.findAll().size();

        // Delete the receiver
        restReceiverMockMvc.perform(delete("/api/receivers/{id}", receiver.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Receiver> receiverList = receiverRepository.findAll();
        assertThat(receiverList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
