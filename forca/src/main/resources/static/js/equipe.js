/**
 * Gerenciamento das Equipes
 */
document.addEventListener('DOMContentLoaded', () => {
  // Primeiro, vamos garantir que temos acesso ao usuário logado
  let usuarioLogado = null;
  
  // Função para carregar o usuário logado do localStorage
  function carregarUsuarioLogado() {
    const usuarioString = localStorage.getItem('usuario');
    if (usuarioString) {
      try {
        usuarioLogado = JSON.parse(usuarioString);
        console.log('Usuário logado carregado:', usuarioLogado);
      } catch (e) {
        console.error('Erro ao carregar usuário do localStorage:', e);
      }
    }
  }
  
  // Carregar o usuário no início
  carregarUsuarioLogado();
  
  const equipes = {
    lista: document.getElementById('equipes-lista'),
    modal: document.getElementById('equipe-modal'),
    form: document.getElementById('equipe-form'),
    inputNome: document.getElementById('equipe-nome'),
    btnCancelar: document.getElementById('cancelar-equipe'),
    btnNovaEquipe: document.getElementById('nova-equipe'),
    detalhe: document.getElementById('equipe-detalhe'),
    detalheNome: document.getElementById('equipe-detalhe-nome'),
    detalhePontuacao: document.getElementById('equipe-detalhe-pontuacao'),
    detalheQtdJogadores: document.getElementById('equipe-detalhe-qtd-jogadores'),
    jogadoresLista: document.getElementById('equipe-jogadores-lista'),
    btnVoltarEquipes: document.getElementById('voltar-equipes'),
    btnEntrarEquipe: document.getElementById('entrar-equipe'),
    btnSairEquipe: document.getElementById('sair-equipe'),
  };
  
  let equipeAtual = null;
  let pertenceEquipeAtual = false;
  
  // Carregar lista de equipes
  async function carregarEquipes() {
    equipes.lista.innerHTML = '<p class="text-center">Carregando equipes...</p>';
    
    try {
      const listaEquipes = await API.listarEquipes();
      
      if (listaEquipes && listaEquipes.length > 0) {
        equipes.lista.innerHTML = '';
        
        listaEquipes.forEach(equipe => {
          const card = document.createElement('div');
          card.className = 'equipe-card';
          card.dataset.id = equipe.id;
          card.innerHTML = `
            <h3>${equipe.nome}</h3>
            <p>Pontuação: ${equipe.pontuacaoTotal}</p>
            <p>Jogadores: ${equipe.quantidadeJogadores}</p>
          `;
          
          card.addEventListener('click', () => {
            mostrarDetalheEquipe(equipe.id);
          });
          
          equipes.lista.appendChild(card);
        });
      } else {
        equipes.lista.innerHTML = '<p class="text-center">Nenhuma equipe encontrada</p>';
      }
    } catch (error) {
      equipes.lista.innerHTML = `<p class="text-center">Erro ao carregar equipes: ${error}</p>`;
    }
  }
  
  // Mostrar detalhes de uma equipe
  async function mostrarDetalheEquipe(equipeId) {
    try {
      const equipe = await API.buscarEquipe(equipeId);
      equipeAtual = equipe;
      
      equipes.detalheNome.textContent = equipe.nome;
      equipes.detalhePontuacao.textContent = equipe.pontuacaoTotal;
      equipes.detalheQtdJogadores.textContent = equipe.quantidadeJogadores;
      
      // Mostrar a seção de detalhes
      equipes.lista.classList.add('hidden');
      equipes.detalhe.classList.remove('hidden');
      equipes.btnNovaEquipe.classList.add('hidden');
      
      // Verificar se o usuário logado já pertence a esta equipe
      pertenceEquipeAtual = await verificarPertencimentoEquipe(equipeId);
      
      // Atualizar visibilidade dos botões baseado na verificação
      if (pertenceEquipeAtual) {
        equipes.btnEntrarEquipe.classList.add('hidden');
        equipes.btnSairEquipe.classList.remove('hidden');
      } else {
        equipes.btnEntrarEquipe.classList.remove('hidden');
        equipes.btnSairEquipe.classList.add('hidden');
      }
      
      // Carregar jogadores da equipe
      carregarJogadoresDaEquipe(equipeId);
    } catch (error) {
      alert(`Erro ao carregar detalhes da equipe: ${error}`);
    }
  }
  
  // Verifica se o usuário logado pertence à equipe
  async function verificarPertencimentoEquipe(equipeId) {
    carregarUsuarioLogado(); // Recarregar usuário logado para ter certeza
    
    try {
      if (!usuarioLogado) return false;
      
      // Obter jogador associado ao usuário
      const jogador = await API.obterJogadorPorUsuario(usuarioLogado.id);
      if (!jogador) return false;
      
      // Verificar se este jogador está na lista de jogadores da equipe
      const jogadores = await API.listarJogadoresEquipe(equipeId);
      return jogadores.some(j => j.id === jogador.id);
    } catch (error) {
      console.error('Erro ao verificar pertencimento à equipe:', error);
      return false;
    }
  }
  
  // Entrar em uma equipe
  async function entrarEquipe() {
    // Verificar novamente se o usuário está logado
    carregarUsuarioLogado();
    
    if (!usuarioLogado) {
      alert('Você precisa estar logado para entrar em uma equipe');
      return;
    }
    
    console.log('Tentando entrar na equipe. Usuário:', usuarioLogado.id, 'Equipe:', equipeAtual.id);
    
    try {
      // Primeiro precisamos obter o ID do JogadorHumano associado ao usuário logado
      const response = await API.obterJogadorPorUsuario(usuarioLogado.id);
      const jogadorId = response.id;
      
      if (!jogadorId) {
        alert('Não foi possível encontrar seu perfil de jogador.');
        return;
      }
      
      console.log('JogadorID obtido:', jogadorId);
      
      // Agora sim adicionamos o jogador à equipe
      const resultado = await API.adicionarJogadorEquipe(equipeAtual.id, jogadorId);
      
      alert('Você entrou na equipe com sucesso!');
      pertenceEquipeAtual = true;
      equipes.btnEntrarEquipe.classList.add('hidden');
      equipes.btnSairEquipe.classList.remove('hidden');
      
      // Recarregar a lista de jogadores
      carregarJogadoresDaEquipe(equipeAtual.id);
    } catch (error) {
      console.error('Erro ao entrar na equipe:', error);
      alert(`Erro ao entrar na equipe: ${error.message || error}`);
    }
  }
  
  // Sair de uma equipe
  async function sairEquipe() {
    // Verificar novamente se o usuário está logado
    carregarUsuarioLogado();
    
    if (!usuarioLogado) {
      return;
    }
    
    try {
      // Primeiro precisamos obter o ID do JogadorHumano associado ao usuário logado
      const response = await API.obterJogadorPorUsuario(usuarioLogado.id);
      const jogadorId = response.id;
      
      if (!jogadorId) {
        alert('Não foi possível encontrar seu perfil de jogador.');
        return;
      }
      
      // Agora removemos o jogador da equipe
      await API.removerJogadorEquipe(equipeAtual.id, jogadorId);
      
      alert('Você saiu da equipe com sucesso');
      pertenceEquipeAtual = false;
      equipes.btnEntrarEquipe.classList.remove('hidden');
      equipes.btnSairEquipe.classList.add('hidden');
      
      // Recarregar a lista de jogadores
      carregarJogadoresDaEquipe(equipeAtual.id);
    } catch (error) {
      console.error('Erro ao sair da equipe:', error);
      alert(`Erro ao sair da equipe: ${error.message || error}`);
    }
  }
  
  // Carregar jogadores de uma equipe
  async function carregarJogadoresDaEquipe(equipeId) {
    equipes.jogadoresLista.innerHTML = '<li>Carregando jogadores...</li>';
    
    try {
      // Agora vamos realmente buscar os jogadores da equipe
      const jogadores = await API.listarJogadoresEquipe(equipeId);
      
      if (jogadores && jogadores.length > 0) {
        equipes.jogadoresLista.innerHTML = '';
        
        jogadores.forEach(jogador => {
          const li = document.createElement('li');
          li.textContent = jogador.nome;
          equipes.jogadoresLista.appendChild(li);
        });
      } else {
        equipes.jogadoresLista.innerHTML = '<li>Nenhum jogador nesta equipe</li>';
      }
    } catch (error) {
      equipes.jogadoresLista.innerHTML = `<li>Erro ao carregar jogadores: ${error}</li>`;
    }
  }
  
  // Event Listeners
  equipes.btnNovaEquipe.addEventListener('click', () => {
    equipes.inputNome.value = '';
    equipes.modal.classList.remove('hidden');
  });
  
  equipes.btnCancelar.addEventListener('click', () => {
    equipes.modal.classList.add('hidden');
  });
  
  equipes.form.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const nome = equipes.inputNome.value.trim();
    
    if (!nome) {
      alert('Por favor, informe o nome da equipe');
      return;
    }
    
    try {
      await API.criarEquipe({ nome });
      equipes.modal.classList.add('hidden');
      carregarEquipes();
    } catch (error) {
      alert(`Erro ao criar equipe: ${error}`);
    }
  });
  
  equipes.btnVoltarEquipes.addEventListener('click', () => {
    equipes.detalhe.classList.add('hidden');
    equipes.lista.classList.remove('hidden');
    equipes.btnNovaEquipe.classList.remove('hidden');
    equipeAtual = null;
  });
  
  equipes.btnEntrarEquipe.addEventListener('click', entrarEquipe);
  equipes.btnSairEquipe.addEventListener('click', sairEquipe);
  
  // Inicialização
  window.carregarEquipes = carregarEquipes; // Expor para chamada externa
});