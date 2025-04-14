/**
 * Gerenciamento das Equipes
 */
document.addEventListener('DOMContentLoaded', () => {
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
    btnVoltarEquipes: document.getElementById('voltar-equipes')
  };
  
  let equipeAtual = null;
  
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
      
      // Carregar jogadores da equipe (considerando que o backend fornece essa informação)
      carregarJogadoresDaEquipe(equipeId);
    } catch (error) {
      alert(`Erro ao carregar detalhes da equipe: ${error}`);
    }
  }
  
  // Carregar jogadores de uma equipe
  async function carregarJogadoresDaEquipe(equipeId) {
    equipes.jogadoresLista.innerHTML = '<li>Carregando jogadores...</li>';
    
    try {
      // Supondo que temos esse endpoint que retorna os jogadores de uma equipe
      // const jogadores = await API.listarJogadoresEquipe(equipeId);
      
      // Como pode não existir esse endpoint, vamos simular
      const jogadores = [
        { id: 1, nome: "Jogador 1" },
        { id: 2, nome: "Jogador 2" }
      ];
      
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
  
  // Inicialização
  window.carregarEquipes = carregarEquipes; // Expor para chamada externa
});