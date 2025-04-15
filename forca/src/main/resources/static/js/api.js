/**
 * API Client para comunicação com o backend
 */
const API = {
  BASE_URL: '/api',
  
  // Autenticação
  async cadastrarUsuario(usuario) {
    return this.post(`${this.BASE_URL}/usuarios/cadastrar`, usuario);
  },
  
  async login(credentials) {
    return this.post(`${this.BASE_URL}/usuarios/login`, credentials);
  },
  
  // Partidas
  async iniciarPartida(data) {
    return this.post(`${this.BASE_URL}/partidas/iniciar`, data);
  },
  
  async iniciarPartidaComDificuldade(data) {
    return this.post(`${this.BASE_URL}/partidas/iniciar/dificuldade`, data);
  },
  
  async tentarLetra(partidaId, letra) {
    return this.post(`${this.BASE_URL}/partidas/${partidaId}/tentar`, { letra });
  },
  
  async desistirPartida(partidaId) {
    return this.post(`${this.BASE_URL}/partidas/${partidaId}/desistir`);
  },
  
  async listarPartidasUsuario(usuarioId) {
    return this.get(`${this.BASE_URL}/partidas/usuario/${usuarioId}`);
  },
  
  async listarPartidasDetalhadasUsuario(usuarioId) {
    return this.get(`${this.BASE_URL}/partidas/usuario/${usuarioId}/detalhadas`);
  },
  
  async getPartida(partidaId) {
    return this.get(`${this.BASE_URL}/partidas/${partidaId}`);
  },
  
  async getPartidaDetalhada(partidaId) {
    return this.get(`${this.BASE_URL}/partidas/${partidaId}/detalhada`);
  },
  
  // Ranking
  async obterRanking() {
    console.log('Chamando obterRanking()');
    const url = `${this.BASE_URL}/usuarios/ranking`;
    console.log('URL do ranking:', url);
    try {
      const response = await fetch(url);
      console.log('Resposta bruta:', response);
      if (!response.ok) {
        const erro = await this._handleError(response);
        console.error('Erro na API:', erro);
        throw erro;
      }
      const dados = await response.json();
      console.log('Dados do ranking:', dados);
      return dados;
    } catch (error) {
      console.error('Exceção ao obter ranking:', error);
      throw error;
    }
  },
  
  // Perfil
  async apagarHistoricoPalavras(usuarioId) {
    return this.delete(`${this.BASE_URL}/usuarios/${usuarioId}/historico-palavras`);
  },
  
  async obterUsuario(usuarioId) {
    return this.get(`${this.BASE_URL}/usuarios/${usuarioId}`);
  },
  
  // Equipes
  async listarEquipes() {
    return this.get(`${this.BASE_URL}/equipes`);
  },
  
  async buscarEquipe(id) {
    return this.get(`${this.BASE_URL}/equipes/${id}`);
  },
  
  async criarEquipe(equipe) {
    return this.post(`${this.BASE_URL}/equipes`, equipe);
  },
  
  async atualizarEquipe(id, equipe) {
    return this.put(`${this.BASE_URL}/equipes/${id}`, equipe);
  },
  
  async excluirEquipe(id) {
    return this.delete(`${this.BASE_URL}/equipes/${id}`);
  },
  
  async adicionarJogadorEquipe(equipeId, jogadorId) {
    return this.post(`${this.BASE_URL}/equipes/${equipeId}/jogadores/${jogadorId}`);
  },
  
  async removerJogadorEquipe(equipeId, jogadorId) {
    return this.delete(`${this.BASE_URL}/equipes/${equipeId}/jogadores/${jogadorId}`);
  },

  async listarJogadoresEquipe(equipeId) {
    return this.get(`${this.BASE_URL}/equipes/${equipeId}/jogadores`);
  },
  
  async obterRankingEquipes() {
    return this.get(`${this.BASE_URL}/equipes/ranking`);
  },
  
  async buscarEquipesPorNome(nome) {
    return this.get(`${this.BASE_URL}/equipes/busca?nome=${encodeURIComponent(nome)}`);
  },

  async obterJogadorPorUsuario(usuarioId) {
    return this.get(`${this.BASE_URL}/jogadores/usuario/${usuarioId}`);
  },
  
  // Métodos HTTP genéricos
  async get(url) {
    const response = await fetch(url);
    if (!response.ok) {
      throw await this._handleError(response);
    }
    return response.json();
  },
  
  async post(url, data) {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    });
    
    if (!response.ok) {
      throw await this._handleError(response);
    }
    
    return response.json();
  },
  
  async put(url, data) {
    const response = await fetch(url, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    });
    
    if (!response.ok) {
      throw await this._handleError(response);
    }
    
    return response.json();
  },
  
  async delete(url) {
    const response = await fetch(url, {
      method: 'DELETE'
    });
    
    if (!response.ok) {
      throw await this._handleError(response);
    }
    
    return response.json();
  },
  
  async _handleError(response) {
    try {
      const error = await response.json();
      return error.mensagem || 'Erro desconhecido';
    } catch (e) {
      return `Erro ${response.status}: ${response.statusText}`;
    }
  }
};