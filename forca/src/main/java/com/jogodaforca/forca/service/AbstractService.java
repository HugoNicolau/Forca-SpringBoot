package com.jogodaforca.forca.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jogodaforca.forca.model.BaseEntity;
import com.jogodaforca.forca.util.Resultado;

/**
 * CONCEITO: CLASSE ABSTRATA
 * - Classe base que não pode ser instanciada diretamente
 * - Fornece implementação comum para serviços
 * - Utiliza tipos genéricos para flexibilidade
 * 
 * CONCEITO: GENERICS
 * - Usa tipos genéricos T e ID para trabalhar com diferentes entidades e tipos de ID
 * - Permite reutilização de código mantendo type-safety
 * 
 * @param <T> O tipo de entidade que este serviço gerencia
 * @param <ID> O tipo do ID da entidade
 */
public abstract class AbstractService<T extends BaseEntity, ID> {
    
    /**
     * CONCEITO: MÉTODO ABSTRATO
     * - Declara assinatura sem implementação
     * - Força subclasses a fornecerem sua própria implementação
     * - Garante acesso ao repositório específico para cada tipo de entidade
     */
    protected abstract JpaRepository<T, ID> getRepository();
    
    /**
     * Busca uma entidade pelo ID.
     * 
     * CONCEITO: GENERALIZAÇÃO DE COMPORTAMENTO
     * - Implementa comportamento comum a todos os serviços
     * - Reutiliza código para operações padronizadas
     * 
     * @param id O ID da entidade a ser buscada
     * @return Um objeto Resultado contendo a entidade ou mensagem de erro
     */
    public Resultado<T> buscarPorId(ID id) {
        return getRepository().findById(id)
                .map(Resultado::sucesso)
                .orElse(Resultado.falha("Entidade não encontrada com ID: " + id));
    }
    
    /**
     * Salva uma entidade no banco de dados.
     * 
     * @param entity A entidade a ser salva
     * @return Um objeto Resultado contendo a entidade salva ou mensagem de erro
     */
    public Resultado<T> salvar(T entity) {
        try {
            T savedEntity = getRepository().save(entity);
            return Resultado.sucesso(savedEntity);
        } catch (Exception e) {
            return Resultado.falha("Erro ao salvar entidade: " + e.getMessage());
        }
    }
    
    /**
     * Exclui uma entidade pelo ID.
     * 
     * @param id O ID da entidade a ser excluída
     * @return Um objeto Resultado indicando sucesso ou falha
     */
    public Resultado<Void> excluir(ID id) {
        try {
            if (getRepository().existsById(id)) {
                getRepository().deleteById(id);
                return Resultado.sucesso();
            } else {
                return Resultado.falha("Entidade não encontrada com ID: " + id);
            }
        } catch (Exception e) {
            return Resultado.falha("Erro ao excluir entidade: " + e.getMessage());
        }
    }
}