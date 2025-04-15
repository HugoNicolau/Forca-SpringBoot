package com.jogodaforca.forca.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.jogodaforca.forca.model.Partida;
import com.jogodaforca.forca.model.StatusPartida;

/**
 * CONCEITO: HERANÇA (ESPECIALIZAÇÃO ADICIONAL)
 * - PartidaDetalhadaDTO estende PartidaDTO, adicionando informações detalhadas
 * - Contém informações completas da partida incluindo histórico de tentativas
 */
public class PartidaDetalhadaDTO extends PartidaDTO {
    private List<TentativaDTO> historicoTentativas;
    private String palavraSecreta; // Só é revelada se a partida terminou

    /**
     * CONCEITO: POLIMORFISMO (SOBRESCRITA DE MÉTODO)
     * - Sobrescreve o método da classe pai para implementar comportamento
     * específico
     */
    public static PartidaDetalhadaDTO fromPartida(Partida partida) {
        // Utiliza o DTO base como ponto de partida
        PartidaDetalhadaDTO dto = new PartidaDetalhadaDTO(PartidaDTO.fromPartida(partida));
        
        // Adiciona propriedades específicas
        dto.setHistoricoTentativas(
                partida.getLetrasTentadas().stream()
                        .map(TentativaDTO::fromLetraTentada)
                        .collect(Collectors.toList()));

        // Só revela a palavra secreta se a partida terminou
        if (partida.getStatus() != StatusPartida.EM_ANDAMENTO) {
            dto.setPalavraSecreta(partida.getPalavra().getPalavraSecreta());
        }

        return dto;
    }

    /**
     * CONCEITO: REUSO DE CÓDIGO
     * - Construtor que permite criar um DTO detalhado a partir de um DTO base
     * - Evita duplicação de código ao copiar automaticamente todos os campos do pai
     */
    public PartidaDetalhadaDTO(PartidaDTO baseDto) {
        // Copia todas as propriedades da classe pai
        this.setId(baseDto.getId());
        this.setUsuarioId(baseDto.getUsuarioId());
        this.setNomeUsuario(baseDto.getNomeUsuario());
        this.setPalavraAtual(baseDto.getPalavraAtual());
        this.setDica(baseDto.getDica());
        this.setTentativasRestantes(baseDto.getTentativasRestantes());
        this.setLetrasCorretas(baseDto.getLetrasCorretas());
        this.setLetrasErradas(baseDto.getLetrasErradas());
        this.setStatus(baseDto.getStatus());
        this.setDataInicio(baseDto.getDataInicio());
        this.setDataFim(baseDto.getDataFim());
    }

    // Getters e Setters para os novos campos
    public List<TentativaDTO> getHistoricoTentativas() {
        return historicoTentativas;
    }

    public void setHistoricoTentativas(List<TentativaDTO> historicoTentativas) {
        this.historicoTentativas = historicoTentativas;
    }

    public String getPalavraSecreta() {
        return palavraSecreta;
    }

    public void setPalavraSecreta(String palavraSecreta) {
        this.palavraSecreta = palavraSecreta;
    }
}