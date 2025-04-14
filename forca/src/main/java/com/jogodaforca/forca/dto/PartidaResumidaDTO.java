package com.jogodaforca.forca.dto;

import com.jogodaforca.forca.model.Partida;

/**
 * CONCEITO: HERANÇA (ESPECIALIZAÇÃO ADICIONAL)
 * - PartidaResumidaDTO estende PartidaDTO, herdando seus atributos e comportamentos
 * - Contém apenas informações resumidas da partida para listagens
 * - Demonstra herança em múltiplos níveis (PartidaResumidaDTO -> PartidaDTO -> BaseDTO)
 */
public class PartidaResumidaDTO extends PartidaDTO {
    // Não adiciona novos atributos, mas sobrescreve o método fromPartida
    
    /**
     * CONCEITO: POLIMORFISMO (SOBRESCRITA DE MÉTODO)
     * - Sobrescreve o método da classe pai para implementar comportamento específico
     */
    public static PartidaResumidaDTO fromPartida(Partida partida) {
        PartidaResumidaDTO dto = new PartidaResumidaDTO();
        dto.setId(partida.getId());
        dto.setUsuarioId(partida.getUsuario().getId());
        dto.setNomeUsuario(partida.getUsuario().getNome());
        dto.setStatus(partida.getStatus());
        dto.setDataInicio(partida.getDataInicio());
        dto.setDataFim(partida.getDataFim());
        // Não inclui detalhes da palavra ou letras tentadas
        return dto;
    }
}