package com.lumahdev.reservasalasapi.Reserva;

import com.lumahdev.reservasalasapi.Sala.SalaModel;
import com.lumahdev.reservasalasapi.Usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class ReservaModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long reservaId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    @ManyToOne private SalaModel sala;
    @ManyToOne private Usuario usuario;
    private ReservaStatusEnum status;

    public ReservaModel() {
    }

    public ReservaModel(LocalDate dataInicio, LocalDate dataFim, SalaModel sala, Usuario usuario) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.sala = sala;
        this.usuario = usuario;
        this.status = ReservaStatusEnum.ATIVA;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public SalaModel getSala() {
        return sala;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void cancelarReserva() {
        this.status = ReservaStatusEnum.CANCELADA;
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

//    public ReservaModel reservarSala(SalaModel sala, UsuarioModel usuario, String inputDtInicio, String inputDtFim) {
//        if (sala.getStatus().equals(SalaStatusEnum.DISPONIVEL)) {
//            DateTimeFormatter formatadorDeData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//            LocalDate dtInicioFormatada = LocalDate.parse(inputDtInicio, formatadorDeData);
//            LocalDate dtFimFormatada = LocalDate.parse(inputDtInicio, formatadorDeData);
//
//            boolean datasValidas = dtInicioFormatada.isBefore(dtFimFormatada);
//
//            if(!datasValidas){
//                System.out.println("Data de fim da reserva deve ser após a data de início.");
//            }
//
//            // VERIFICAR SE EXISTE RESERVA ATIVA NA DATA INFORMADA
//
//            ReservaModel reserva = new ReservaModel(dtInicioFormatada, dtFimFormatada, sala, usuario);
//
//            System.out.println("Sala reservada com sucesso.");
//
//            return reserva;
//        } else {
//            System.out.println("No momento, a sala encontra-se indisponível.");
//
//            return null;
//        }
//    }
}