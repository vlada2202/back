import com.app.ordering.Ordering;
import com.app.ordering.OrderingController;
import com.app.ordering.OrderingDto;
import com.app.ordering.OrderingService;
import com.app.ordering.converter.OrderingDtoToOrderingConverter;
import com.app.ordering.converter.OrderingToOrderingDtoConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
class OrderingControllerTest {

    @Mock
    private OrderingService service;
    @Mock
    private OrderingToOrderingDtoConverter toDtoConverter;
    @Mock
    private OrderingDtoToOrderingConverter toConverter;

    @InjectMocks
    private OrderingController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAll_shouldReturnResultWithList() throws Exception {
        Ordering ordering = mock(Ordering.class);
        OrderingDto dto = mock(OrderingDto.class);

        when(service.findAll()).thenReturn(Collections.singletonList(ordering));
        when(toDtoConverter.convert(ordering)).thenReturn(dto);

        mockMvc.perform(get("/orderings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success Find All"))
                .andExpect(jsonPath("$.data[0]").exists());

        verify(service, times(1)).findAll();
        verify(toDtoConverter, times(1)).convert(ordering);
    }

    @Test
    void find_shouldReturnResult() throws Exception {
        Ordering ordering = mock(Ordering.class);
        OrderingDto dto = mock(OrderingDto.class);

        when(service.find("1")).thenReturn(ordering);
        when(toDtoConverter.convert(ordering)).thenReturn(dto);

        mockMvc.perform(get("/orderings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success Find"))
                .andExpect(jsonPath("$.data").exists());

        verify(service).find("1");
        verify(toDtoConverter).convert(ordering);
    }

    @Test
    void save_shouldReturnSavedResult() throws Exception {
        OrderingDto dto = mock(OrderingDto.class);
        Ordering ordering = mock(Ordering.class);
        Ordering saved = mock(Ordering.class);
        OrderingDto savedDto = mock(OrderingDto.class);

        when(toConverter.convert(any(OrderingDto.class))).thenReturn(ordering);
        when(service.save(ordering, "v1")).thenReturn(saved);
        when(toDtoConverter.convert(saved)).thenReturn(savedDto);

        mockMvc.perform(post("/orderings")
                        .param("vehicleId", "v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success Save"))
                .andExpect(jsonPath("$.data").exists());

        verify(service).save(ordering, "v1");
        verify(toConverter).convert(any(OrderingDto.class));
        verify(toDtoConverter).convert(saved);
    }

    @Test
    void approved_shouldReturnResult() throws Exception {
        Ordering ordering = mock(Ordering.class);
        OrderingDto dto = mock(OrderingDto.class);

        when(service.approved("1")).thenReturn(ordering);
        when(toDtoConverter.convert(ordering)).thenReturn(dto);

        mockMvc.perform(patch("/orderings/1/approved"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success Approved"))
                .andExpect(jsonPath("$.data").exists());

        verify(service).approved("1");
        verify(toDtoConverter).convert(ordering);
    }

    @Test
    void rejected_shouldReturnResult() throws Exception {
        Ordering ordering = mock(Ordering.class);
        OrderingDto dto = mock(OrderingDto.class);

        when(service.rejected("1")).thenReturn(ordering);
        when(toDtoConverter.convert(ordering)).thenReturn(dto);

        mockMvc.perform(patch("/orderings/1/rejected"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success Rejected"))
                .andExpect(jsonPath("$.data").exists());

        verify(service).rejected("1");
        verify(toDtoConverter).convert(ordering);
    }

    @Test
    void done_shouldReturnResult() throws Exception {
        Ordering ordering = mock(Ordering.class);
        OrderingDto dto = mock(OrderingDto.class);

        when(service.done("1")).thenReturn(ordering);
        when(toDtoConverter.convert(ordering)).thenReturn(dto);

        mockMvc.perform(patch("/orderings/1/done"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success Done"))
                .andExpect(jsonPath("$.data").exists());

        verify(service).done("1");
        verify(toDtoConverter).convert(ordering);
    }
}
