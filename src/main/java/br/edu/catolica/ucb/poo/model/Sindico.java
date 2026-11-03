package br.edu.catolica.ucb.poo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "sindicos")
public class Sindico {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "morador_id", nullable = false)
    private Morador morador;

    @Column(name = "inicio_mandato", nullable = false)
    private LocalDate inicioMandato;

    @Column(name = "fim_mandato", nullable = false)
    private LocalDate fimMandato;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    public Sindico() {
    }

    public Sindico(Morador morador, LocalDate inicioMandato, LocalDate fimMandato) {
        this.morador = morador;
        this.inicioMandato = inicioMandato;
        this.fimMandato = fimMandato;
        this.ativo = true;
    }

    public Integer getId() {
        return id;
    }

    public Morador getMorador() {
        return morador;
    }

    public LocalDate getInicioMandato() {
        return inicioMandato;
    }

    public LocalDate getFimMandato() {
        return fimMandato;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void encerrar(LocalDate data) {
        this.ativo = false;
        if (data.isBefore(fimMandato)) {
            this.fimMandato = data;
        }
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) - mandato %s a %s - %s", id, morador.getNome(),
                morador.getUnidade().getIdentificacao(), inicioMandato.format(FORMATO),
                fimMandato.format(FORMATO), ativo ? "ATIVO" : "encerrado");
    }
}
