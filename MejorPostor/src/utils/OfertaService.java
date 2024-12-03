package utils;

import logica.Empresa;
import logica.Oferta;

public class OfertaService {
    private Empresa empresa;
    private InfoOfertas infoOfertas;
    private ArchivosJson archivosJson;

    public OfertaService() {
        this.empresa = new Empresa();
        this.infoOfertas = new InfoOfertas();
        this.archivosJson = new ArchivosJson();
    }

    @SuppressWarnings("static-access")
	public boolean agregarOferta(OfertaDTO ofertaDTO) {
        Oferta oferta = new Oferta(ofertaDTO.getOferente(), ofertaDTO.getHoraDesde(), ofertaDTO.getHoraHasta(), 
                                  ofertaDTO.getMonto(), ofertaDTO.getTipoShow());

        if (!empresa.estaRepetida(oferta)) {
            empresa.guardar(oferta);
            infoOfertas.guardarOfertasTotales(oferta);
            ArchivosJson.guardarComoJSON("ofertasTotales", infoOfertas.getOfertasTotales());
            return true;
        }
        return false;
    }

    @SuppressWarnings("static-access")
    public void terminarDia() {
        Empresa.seleccionarOfertasPorMonto();
        Empresa.getOfertaSolucion().forEach(oferta -> infoOfertas.guardarOfertasSeleccionadas(oferta));
        archivosJson.guardarComoJSON("ofertasSeleccionadas", infoOfertas.getOfertasSeleccionadas());
    }
}
