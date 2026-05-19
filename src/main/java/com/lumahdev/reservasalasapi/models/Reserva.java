package com.lumahdev.reservasalasapi.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table
public class Reserva {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long reservaId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    @ManyToOne private Sala sala;
    @ManyToOne private Usuario usuario;
    private StatusReserva status;

    public Reserva() {
    }

    public Reserva(LocalDate dataInicio, LocalDate dataFim, Sala sala, Usuario usuario) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.sala = sala;
        this.usuario = usuario;
        this.status = StatusReserva.ATIVA;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public Sala getSala() {
        return sala;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void cancelarReserva() {
        this.status = StatusReserva.CANCELADA;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", sala=" + sala +
                ", usuario=" + usuario +
                '}';
    }

    public Reserva reservarSala(Sala sala, Usuario usuario, String inputDtInicio, String inputDtFim) {
        if (sala.getStatus().equals(StatusSala.DISPONIVEL)) {
            DateTimeFormatter formatadorDeData = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate dtInicioFormatada = LocalDate.parse(inputDtInicio, formatadorDeData);
            LocalDate dtFimFormatada = LocalDate.parse(inputDtInicio, formatadorDeData);

            boolean datasValidas = dtInicioFormatada.isBefore(dtFimFormatada);

            if(!datasValidas){
                System.out.println("Data de fim da reserva deve ser após a data de início.");
            }

            // VERIFICAR SE EXISTE RESERVA ATIVA NA DATA INFORMADA

            Reserva reserva = new Reserva(dtInicioFormatada, dtFimFormatada, sala, usuario);

            System.out.println("Sala reservada com sucesso.");

            return reserva;
        } else {
            System.out.println("No momento, a sala encontra-se indisponível.");

            return null;
        }
    }
}