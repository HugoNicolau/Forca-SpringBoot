package com.jogodaforca.forca.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * CONCEITO: CLASSE GENÉRICA
 * - Implementa um repositório de dados flexível que pode armazenar qualquer tipo
 * - Demonstra como criar classes parametrizadas por tipos
 * - O tipo T representa o tipo de objeto armazenado
 * - O tipo ID representa o tipo de identificador usado
 * 
 * @param <T> O tipo de objetos armazenados no repositório
 * @param <ID> O tipo de identificador usado para acessar os objetos
 */
public class Repositorio<T, ID> {
    
    private final Map<ID, T> elementos = new HashMap<>();
    
    /**
     * Salva um elemento no repositório.
     * 
     * @param id O identificador do elemento
     * @param elemento O elemento a ser armazenado
     * @return O elemento armazenado
     */
    public T salvar(ID id, T elemento) {
        elementos.put(id, elemento);
        return elemento;
    }
    
    /**
     * Busca um elemento pelo id.
     * 
     * @param id O identificador do elemento
     * @return Um Optional contendo o elemento, se encontrado
     */
    public Optional<T> buscarPorId(ID id) {
        return Optional.ofNullable(elementos.get(id));
    }
    
    /**
     * Lista todos os elementos armazenados.
     * 
     * @return Uma lista contendo todos os elementos
     */
    public List<T> listarTodos() {
        return new ArrayList<>(elementos.values());
    }
    
    /**
     * Remove um elemento do repositório.
     * 
     * @param id O identificador do elemento a ser removido
     * @return true se o elemento foi removido, false se não existia
     */
    public boolean remover(ID id) {
        return elementos.remove(id) != null;
    }
    
    /**
     * Retorna a quantidade de elementos armazenados.
     * 
     * @return O número de elementos
     */
    public int tamanho() {
        return elementos.size();
    }
    
    /**
     * CONCEITO: MÉTODO GENÉRICO
     * - Método parametrizado por um tipo adicional V
     * - Demonstra como métodos individuais também podem ser genéricos
     * 
     * @param <V> O tipo de valor a ser processado
     * @param id O identificador do elemento
     * @param processador Uma função que processa o elemento em um valor do tipo V
     * @return Um Optional contendo o resultado processado, se o elemento existir
     */
    public <V> Optional<V> processar(ID id, Processador<T, V> processador) {
        T elemento = elementos.get(id);
        if (elemento != null) {
            return Optional.ofNullable(processador.processar(elemento));
        }
        return Optional.empty();
    }
    
    /**
     * Interface funcional para processar elementos.
     * 
     * CONCEITO: INTERFACE GENÉRICA
     * - Interface parametrizada por tipos
     * - Usada como callback para operações genéricas
     * 
     * @param <T> O tipo do elemento a ser processado
     * @param <R> O tipo do resultado após processamento
     */
    @FunctionalInterface
    public interface Processador<T, R> {
        R processar(T elemento);
    }
}