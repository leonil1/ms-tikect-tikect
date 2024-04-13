package com.codigo.msticket.service.impl;

import com.codigo.msticket.aggregates.response.ResponseBase;
import com.codigo.msticket.client.UsuarioClient;
import com.codigo.msticket.entity.TipoPagoEntity;
import com.codigo.msticket.model.Evento;
import com.codigo.msticket.model.SectorAsiento;
import com.codigo.msticket.model.Usuario;
import com.codigo.msticket.repository.TipoPagoRepository;
import com.codigo.msticket.request.TicketResponse;
import com.codigo.msticket.service.TipoPagoService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;


class TipoPagoServiceImplTest {
//    @Mock
//    private TicketRepository ticketRepository;

    @Mock
    private TipoPagoRepository tipoPagoRepository;

    @Mock
    private UsuarioClient usuarioClient;

//    @Mock
//    private EventoClient eventoClient;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    void save() {
//        when(request.getHeader("Authorization")).thenReturn("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXBlIiwiaWF0IjoxNzEzMDExMjcyLCJleHAiOjE3MTMwMTg0NzJ9.RxZuNGl38B7Tkb_lI_Y1bowftaQ71uVZxq6u5clgYmE");
//
//        // Arrange
//        TicketResponse ticketResponse = new TicketResponse();
//        ticketResponse.setTipoPagoId(1L);
//        ticketResponse.setEventoId(1L);
//        ticketResponse.setNumeroAsiento(1);
//        ticketResponse.setCantidad(1);
//        ticketResponse.setObservaciones("Observaciones");
//
//        TipoPagoEntity tipoPago = new TipoPagoEntity();
//        tipoPago.setTipoPagoId(1L);
//        tipoPago.setMetodo("Efectivo");
//
//        when(tipoPagoRepository.findById(1L)).thenReturn(Optional.of(tipoPago));
//
//        Evento evento = new Evento();
//        evento.setId(1L);
//        evento.setSector(Collections.singletonList(new SectorAsiento()));
//
//        //when(eventoClient.(1L, "token")).thenReturn(Optional.of(evento));
//
//        Usuario usuario = new Usuario();
//        usuario.setIdUsuario(1L);
//        usuario.setUsername("username");
//
//        when(usuarioClient.getUsuarioAutenticado("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXBlIiwiaWF0IjoxNzEzMDExMjcyLCJleHAiOjE3MTMwMTg0NzJ9.RxZuNGl38B7Tkb_lI_Y1bowftaQ71uVZxq6u5clgYmE")).thenReturn(usuario);
//
//
//        // Act
//        ResponseBase responseBase = ticketService.save(ticketResponse);
//
//        // Assert
//        assertEquals(200, responseBase.getCode());
//        //assertEquals("Ticket creado correctamente", responseBase.getMessage());
//        //verify(ticketRepository, times(1)).save(any(TicketEntity.class));
//    }

}