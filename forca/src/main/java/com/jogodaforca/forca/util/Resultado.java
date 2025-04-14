package com.jogodaforca.forca.util;

/**
 * Classe genérica que representa o resultado de uma operação.
 * 
 * CONCEITO: GENERICS
 * - Utiliza tipos genéricos para flexibilidade
 * - Permite criar um container tipado que pode conter qualquer tipo de dado
 * 
 * CONCEITO: CLASSE UTILITÁRIA
 * - Encapsula resultados de operações com informações de sucesso/falha
 * - Padroniza o formato de retorno em toda a aplicação
 * 
 * @param <T> O tipo de dados contido no resultado
 */
public class Resultado<T> {
    
    private final boolean sucesso;
    private final String mensagem;
    private final T dados;
    
    /**
     * Construtor privado para garantir que instâncias sejam criadas apenas
     * através dos métodos de fábrica.
     * 
     * CONCEITO: ENCAPSULAMENTO
     * - Construtor privado para controlar a criação de instâncias
     * - Implementa o padrão de projeto Factory Method
     */
    private Resultado(boolean sucesso, String mensagem, T dados) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.dados = dados;
    }
    
    /**
     * Cria um resultado de sucesso com dados.
     * 
     * CONCEITO: MÉTODO DE FÁBRICA ESTÁTICO
     * - Método estático que cria uma instância da classe
     * - Implementa o padrão Factory Method
     * 
     * @param <T> Tipo de dados do resultado
     * @param dados Os dados a serem incluídos no resultado
     * @return Um resultado de sucesso contendo os dados
     */
    public static <T> Resultado<T> sucesso(T dados) {
        return new Resultado<>(true, "Operação realizada com sucesso", dados);
    }
    
    /**
     * Cria um resultado de sucesso com mensagem e dados.
     * 
     * @param <T> Tipo de dados do resultado
     * @param mensagem A mensagem de sucesso
     * @param dados Os dados a serem incluídos no resultado
     * @return Um resultado de sucesso contendo a mensagem e os dados
     */
    public static <T> Resultado<T> sucesso(String mensagem, T dados) {
        return new Resultado<>(true, mensagem, dados);
    }
    
    /**
     * Cria um resultado de sucesso sem dados (para operações tipo void).
     * 
     * CONCEITO: SOBRECARGA DE MÉTODOS
     * - Várias versões do mesmo método com diferentes parâmetros
     * - Permite flexibilidade na criação de resultados
     * 
     * @return Um resultado de sucesso sem dados
     */
    public static <T> Resultado<T> sucesso() {
        return new Resultado<>(true, "Operação realizada com sucesso", null);
    }
    
    /**
     * Cria um resultado de falha com mensagem.
     * 
     * @param <T> Tipo de dados do resultado
     * @param mensagem A mensagem de erro
     * @return Um resultado de falha contendo a mensagem de erro
     */
    public static <T> Resultado<T> falha(String mensagem) {
        return new Resultado<>(false, mensagem, null);
    }
    
    /**
     * Cria um resultado de falha com mensagem.
     * Alias para o método falha() para manter compatibilidade com código existente.
     * 
     * @param <T> Tipo de dados do resultado
     * @param mensagem A mensagem de erro
     * @return Um resultado de falha contendo a mensagem de erro
     */
    public static <T> Resultado<T> erro(String mensagem) {
        return falha(mensagem);
    }
    
    /**
     * Verifica se o resultado é um sucesso.
     * 
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean isSucesso() {
        return sucesso;
    }
    
    /**
     * Obtém a mensagem associada ao resultado.
     * 
     * @return A mensagem do resultado
     */
    public String getMensagem() {
        return mensagem;
    }
    
    /**
     * Obtém os dados associados ao resultado.
     * 
     * @return Os dados do resultado, ou null se não houver dados
     */
    public T getDados() {
        return dados;
    }
    
    @Override
    public String toString() {
        return "Resultado [sucesso=" + sucesso + ", mensagem=" + mensagem + 
               ", dados=" + (dados != null ? dados.toString() : "null") + "]";
    }
}