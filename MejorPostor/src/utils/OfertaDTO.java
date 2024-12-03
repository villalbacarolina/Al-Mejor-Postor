package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OfertaDTO {
    private String oferente;
    private int horaDesde;
    private int horaHasta;
    private double monto;
    private String tipoShow;
    private String fechaManiana;

    public OfertaDTO(String oferente, int horaDesde, int horaHasta, double monto, String tipoShow) {
        this.oferente = oferente;
        this.horaDesde = horaDesde;
        this.horaHasta = horaHasta;
        this.monto = monto;
        this.tipoShow = tipoShow;
        fechaManiana = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getOferente() { return oferente; }
    public int getHoraDesde() { return horaDesde; }
    public int getHoraHasta() { return horaHasta; }
    public double getMonto() { return monto; }
    public String getTipoShow() { return tipoShow; }
    public String getFechaManiana() { return fechaManiana; }
}

