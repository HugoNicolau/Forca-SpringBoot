/**
 * Jogo da Forca
 * Script principal para o gerenciamento do jogo
 */
document.addEventListener('DOMContentLoaded', () => {
  // Elementos da UI
  const areas = {
    login: document.getElementById('login-area'),
    jogo: document.getElementById('jogo-area'),
    ranking: document.getElementById('ranking-area'),
    equipes: document.getElementById('equipes-area'),
    perfil: document.getElementById('perfil-area')
  };
  
  const nav = {
    jogar: document.getElementById('nav-jogar'),
    ranking: document.getElementById('nav-ranking'),
    equipes: document.getElementById('nav-equipes'),
    perfil: document.getElementById('nav-perfil')
  };
  
  // Elementos do jogo
  const jogo = {
    teclado: document.getElementById('teclado'),
    palavra: document.getElementById('palavra'),
    dica: document.getElementById('dica').querySelector('span'),
    tentativas: document.getElementById('tentativas').querySelector('span'),
    forcaImg: document.getElementById('forca-img'),
    btnNovaPartida: document.getElementById('nova-partida'),
    btnDesistir: document.getElementById('desistir'),
    opcoesNovaPartida: document.getElementById('nova-partida-opcoes'),
    btnPalavraAleatoria: document.getElementById('palavra-aleatoria'),
    btnPalavraPersonalizada: document.getElementById('palavra-personalizada'),
    opcoesDificuldade: document.getElementById('opcoes-dificuldade'),
    selectDificuldade: document.getElementById('dificuldade'),
    btnIniciarAleatorio: document.getElementById('iniciar-aleatorio'),
    formPersonalizada: document.getElementById('form-personalizada'),
    inputPalavra: document.getElementById('palavra-input'),
    inputDica: document.getElementById('dica-input'),
    selectDificuldadeCustom: document.getElementById('dificuldade-custom'),
    btnIniciarPersonalizado: document.getElementById('iniciar-personalizado'),
    modal: document.getElementById('resultado-modal'),
    modalTitulo: document.getElementById('resultado-titulo'),
    modalMensagem: document.getElementById('resultado-mensagem'),
    modalPalavra: document.getElementById('resultado-palavra'),
    btnJogarNovamente: document.getElementById('jogar-novamente')
  };
  
  // Estado do jogo
  let partidaAtual = null;
  let usuarioLogado = null;
  
  // Verificar se há um usuário logado no localStorage
  function verificarLogin() {
    const usuario = localStorage.getItem('usuario');
    if (usuario) {
      usuarioLogado = JSON.parse(usuario);
      mostrarArea('jogo');
      atualizarInterface();
    } else {
      mostrarArea('login');
    }
  }
  
  // Mostrar uma área específica e esconder as outras
  function mostrarArea(area) {
    Object.keys(areas).forEach(key => {
      if (key === area) {
        areas[key].classList.remove('hidden');
      } else {
        areas[key].classList.add('hidden');
      }
    });
    
    // Atualizar navegação
    Object.keys(nav).forEach(key => {
      if (key === area || (area === 'jogo' && key === 'jogar')) {
        nav[key].classList.add('active');
      } else {
        nav[key].classList.remove('active');
      }
    });
    
    // Ações específicas para cada área
    if (area === 'jogo') {
      if (!partidaAtual) {
        jogo.btnNovaPartida.click();
      }
    } else if (area === 'ranking') {
      carregarRanking();
    } else if (area === 'equipes') {
      carregarEquipes();
    } else if (area === 'perfil') {
      carregarPerfilUsuario();
    }
  }
  
  // Criar o teclado virtual
  function criarTeclado() {
    jogo.teclado.innerHTML = '';
    const letras = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    
    for (let letra of letras) {
      const botao = document.createElement('button');
      botao.textContent = letra;
      botao.dataset.letra = letra;
      botao.addEventListener('click', (e) => tentarLetra(letra));
      jogo.teclado.appendChild(botao);
    }
  }
  
  // Iniciar uma nova partida
  async function iniciarPartida(opcoes = {}) {
    try {
      let resultado;
      
      if (opcoes.palavraCustomizada) {
        resultado = await API.iniciarPartidaComDificuldade({
          usuarioId: usuarioLogado.id,
          palavraCustomizada: opcoes.palavraCustomizada,
          dica: opcoes.dica,
          dificuldade: opcoes.dificuldade
        });
      } else {
        resultado = await API.iniciarPartidaComDificuldade({
          usuarioId: usuarioLogado.id,
          dificuldade: opcoes.dificuldade
        });
      }
      
      partidaAtual = resultado;
      atualizarInterface();
      criarTeclado();
      jogo.opcoesNovaPartida.classList.add('hidden');
      jogo.opcoesDificuldade.classList.add('hidden');
      jogo.formPersonalizada.classList.add('hidden');
      
    } catch (error) {
      alert(`Erro ao iniciar partida: ${error}`);
    }
  }
  
  // Tentar uma letra
  async function tentarLetra(letra) {
    if (!partidaAtual) return;
    
    try {
      // Desabilitar o botão da letra
      const botaoLetra = Array.from(jogo.teclado.children).find(btn => btn.dataset.letra === letra);
      if (botaoLetra) {
        botaoLetra.disabled = true;
      }
      
      const resultado = await API.tentarLetra(partidaAtual.id, letra);
      partidaAtual = resultado;
      
      // Atualizar classes do botão conforme acerto/erro
      if (botaoLetra) {
        if (partidaAtual.letrasCorretas.includes(letra)) {
          botaoLetra.classList.add('acerto');
        } else {
          botaoLetra.classList.add('erro');
        }
      }
      
      atualizarInterface();
      
      // Verificar fim de jogo
      if (partidaAtual.status !== 'EM_ANDAMENTO') {
        mostrarResultado();
      }
      
    } catch (error) {
      alert(`Erro ao tentar letra: ${error}`);
    }
  }
  
  // Desistir da partida atual
  async function desistirPartida() {
    if (!partidaAtual) return;
    
    if (!confirm('Tem certeza que deseja desistir?')) return;
    
    try {
      const resultado = await API.desistirPartida(partidaAtual.id);
      partidaAtual = resultado;
      mostrarResultado();
    } catch (error) {
      alert(`Erro ao desistir: ${error}`);
    }
  }
  
  // Mostrar resultado da partida
  async function mostrarResultado() {
    let titulo, mensagem;
    
    switch (partidaAtual.status) {
      case 'VENCEU':
        titulo = '🎉 Parabéns!';
        mensagem = 'Você acertou a palavra.';
        break;
      case 'PERDEU':
        titulo = '😢 Que pena!';
        mensagem = 'Você não conseguiu acertar a palavra.';
        break;
      case 'DESISTIU':
        titulo = '👋 Partida encerrada';
        mensagem = 'Você desistiu do jogo.';
        break;
    }
    
    jogo.modalTitulo.textContent = titulo;
    jogo.modalMensagem.textContent = mensagem;
    
    // Buscar a palavra completa se necessário
    API.getPartida(partidaAtual.id).then(partida => {
      jogo.modalPalavra.textContent = `A palavra era: ${partida.palavraSecreta || partidaAtual.palavraAtual}`;
      jogo.modal.classList.remove('hidden');
    }).catch(error => {
      jogo.modalPalavra.textContent = `A palavra era: ${partidaAtual.palavraAtual}`;
      jogo.modal.classList.remove('hidden');
    });

    // Atualizar dados do usuário logado após o fim da partida
    if (usuarioLogado) {
        try {
            const usuarioAtualizado = await API.obterUsuario(usuarioLogado.id);
            usuarioLogado = usuarioAtualizado;
            localStorage.setItem('usuario', JSON.stringify(usuarioLogado));
        } catch (error) {
            console.error('Erro ao atualizar dados do usuário:', error);
        }
    }
  }
  
  // Atualizar a interface com os dados da partida atual
  function atualizarInterface() {
    if (!partidaAtual) {
      jogo.palavra.textContent = '';
      jogo.dica.textContent = '';
      jogo.tentativas.textContent = '';
      jogo.forcaImg.src = '/img/forca-estados/forca0.png';
      jogo.btnDesistir.disabled = true;
      return;
    }
    
    jogo.palavra.textContent = partidaAtual.palavraAtual;
    jogo.dica.textContent = partidaAtual.dica;
    jogo.tentativas.textContent = partidaAtual.tentativasRestantes;
    
    // Atualizar imagem da forca (0-6 tentativas)
    const indiceImagem = (6 - partidaAtual.tentativasRestantes) > 0 ? (6 - partidaAtual.tentativasRestantes) : 0;
    jogo.forcaImg.src = `/img/forca-estados/forca${indiceImagem}.png`;
    
    jogo.btnDesistir.disabled = false;
  }
  
  // Função para carregar o ranking - versão corrigida
  async function carregarRanking() {
    const tabelaBody = document.querySelector('#tabela-ranking tbody');
    tabelaBody.innerHTML = '<tr><td colspan="6" class="text-center">Carregando...</td></tr>';
    
    try {
      const response = await API.obterRanking();
      console.log('Resposta do ranking:', response); // Adicionando log para debug
      
      // Verificar se a resposta é um objeto Resultado ou diretamente a lista
      const ranking = response.dados || response;
      
      if (ranking && ranking.length > 0) {
        tabelaBody.innerHTML = '';
        ranking.forEach((jogador, index) => {
          const tr = document.createElement('tr');
          tr.innerHTML = `
            <td>${index + 1}</td>
            <td>${jogador.nome}</td>
            <td>${jogador.vitorias || 0}</td>
            <td>${jogador.derrotas || 0}</td>
            <td>${jogador.totalPartidas || 0}</td>
            <td>${((jogador.winRate || 0) * 100).toFixed(1)}%</td>
          `;
          tabelaBody.appendChild(tr);
        });
      } else {
        tabelaBody.innerHTML = '<tr><td colspan="6" class="text-center">Nenhum jogador encontrado</td></tr>';
      }
    } catch (error) {
      console.error('Erro ao carregar o ranking:', error);
      tabelaBody.innerHTML = `<tr><td colspan="6" class="text-center">Erro ao carregar ranking: ${error}</td></tr>`;
    }
  }
  
  // Função para carregar dados do perfil do usuário - versão atualizada
  async function carregarPerfilUsuario() {
    if (!usuarioLogado) return;
    
    try {
      // Buscar dados atualizados do usuário
      const usuario = await API.obterUsuario(usuarioLogado.id);
      
      // Atualizar o usuário logado com dados mais recentes
      usuarioLogado = usuario;
      localStorage.setItem('usuario', JSON.stringify(usuarioLogado));
      
      // Atualizar a interface
      document.getElementById('perfil-nome').textContent = usuarioLogado.nome;
      document.getElementById('perfil-login').textContent = usuarioLogado.login;
      document.getElementById('perfil-vitorias').textContent = usuarioLogado.vitorias;
      document.getElementById('perfil-derrotas').textContent = usuarioLogado.derrotas;
      document.getElementById('perfil-winrate').textContent = `${(usuarioLogado.winRate * 100).toFixed(1)}%`;
      
      // Carregar histórico de partidas
      const partidas = await API.listarPartidasUsuario(usuarioLogado.id);
      const historicoContainer = document.getElementById('historico-partidas');
      
      if (partidas && partidas.length > 0) {
        historicoContainer.innerHTML = '';
        partidas.forEach(partida => {
          const dataPartida = new Date(partida.dataInicio);
          const formatoData = new Intl.DateTimeFormat('pt-BR', { 
            dateStyle: 'short', 
            timeStyle: 'short' 
          }).format(dataPartida);
          
          const divPartida = document.createElement('div');
          divPartida.className = 'historico-partida';
          
          let statusText = '';
          let statusClass = '';
          
          switch(partida.status) {
            case 'VENCEU':
              statusText = 'Vitória';
              statusClass = 'text-success';
              break;
            case 'PERDEU':
              statusText = 'Derrota';
              statusClass = 'text-danger';
              break;
            case 'DESISTIU':
              statusText = 'Desistência';
              statusClass = 'text-warning';
              break;
            case 'EM_ANDAMENTO':
              statusText = 'Em andamento';
              statusClass = 'text-info';
              break;
          }
          
          divPartida.innerHTML = `
            <p><strong>Data:</strong> ${formatoData}</p>
            <p><strong>Palavra:</strong> ${partida.palavraAtual}</p>
            <p><strong>Status:</strong> <span class="${statusClass}">${statusText}</span></p>
          `;
          
          historicoContainer.appendChild(divPartida);
        });
      } else {
        historicoContainer.innerHTML = '<p class="text-center">Nenhuma partida encontrada</p>';
      }
    } catch (error) {
      console.error('Erro ao carregar perfil:', error);
      alert('Não foi possível carregar os dados do perfil: ' + error);
    }
  }
  
  // Event Listeners
  
  // Navegação
  nav.jogar.addEventListener('click', (e) => {
    e.preventDefault();
    mostrarArea('jogo');
  });
  
  nav.ranking.addEventListener('click', (e) => {
    e.preventDefault();
    mostrarArea('ranking');
  });
  
  nav.equipes.addEventListener('click', (e) => {
    e.preventDefault();
    mostrarArea('equipes');
  });
  
  nav.perfil.addEventListener('click', (e) => {
    e.preventDefault();
    mostrarArea('perfil');
  });
  
  // Login e Cadastro
  document.getElementById('mostrar-cadastro').addEventListener('click', () => {
    document.getElementById('login-form').parentElement.classList.add('hidden');
    document.getElementById('cadastro-card').classList.remove('hidden');
  });
  
  document.getElementById('voltar-login').addEventListener('click', () => {
    document.getElementById('cadastro-card').classList.add('hidden');
    document.getElementById('login-form').parentElement.classList.remove('hidden');
  });
  
  document.getElementById('login-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const login = document.getElementById('login').value;
    const senha = document.getElementById('senha').value;
    
    try {
      const usuario = await API.login({ login, senha });
      usuarioLogado = usuario;
      
      // Salvar no localStorage para persistência
      localStorage.setItem('usuario', JSON.stringify(usuario));
      
      mostrarArea('jogo');
    } catch (error) {
      alert(`Erro no login: ${error}`);
    }
  });
  
  document.getElementById('cadastro-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const nome = document.getElementById('cadastro-nome').value;
    const login = document.getElementById('cadastro-login').value;
    const senha = document.getElementById('cadastro-senha').value;
    
    try {
      await API.cadastrarUsuario({ nome, login, senha });
      alert('Cadastro realizado com sucesso! Faça login para continuar.');
      
      // Voltar para a tela de login
      document.getElementById('cadastro-card').classList.add('hidden');
      document.getElementById('login-form').parentElement.classList.remove('hidden');
    } catch (error) {
      alert(`Erro no cadastro: ${error}`);
    }
  });
  
  // Controles do Jogo
  jogo.btnNovaPartida.addEventListener('click', () => {
    jogo.opcoesNovaPartida.classList.remove('hidden');
  });
  
  jogo.btnPalavraAleatoria.addEventListener('click', () => {
    jogo.opcoesDificuldade.classList.remove('hidden');
    jogo.formPersonalizada.classList.add('hidden');
  });
  
  jogo.btnPalavraPersonalizada.addEventListener('click', () => {
    jogo.formPersonalizada.classList.remove('hidden');
    jogo.opcoesDificuldade.classList.add('hidden');
  });
  
  jogo.btnIniciarAleatorio.addEventListener('click', () => {
    const dificuldade = jogo.selectDificuldade.value;
    iniciarPartida({ dificuldade });
  });
  
  jogo.btnIniciarPersonalizado.addEventListener('click', () => {
    const palavraCustomizada = jogo.inputPalavra.value.trim();
    const dica = jogo.inputDica.value.trim();
    const dificuldade = jogo.selectDificuldadeCustom.value;
    
    if (!palavraCustomizada || !dica) {
      alert('Por favor, preencha todos os campos');
      return;
    }
    
    iniciarPartida({ palavraCustomizada, dica, dificuldade });
  });
  
  jogo.btnDesistir.addEventListener('click', desistirPartida);
  
  jogo.btnJogarNovamente.addEventListener('click', () => {
    jogo.modal.classList.add('hidden');
    partidaAtual = null;
    jogo.btnNovaPartida.click();
  });
  
  // Perfil
  document.getElementById('apagar-historico').addEventListener('click', async () => {
    if (!usuarioLogado) return;
    
    if (!confirm('Tem certeza que deseja apagar seu histórico de palavras? Esta ação não pode ser desfeita.')) {
      return;
    }
    
    try {
      await API.apagarHistoricoPalavras(usuarioLogado.id);
      alert('Histórico apagado com sucesso!');
      carregarPerfilUsuario();
    } catch (error) {
      alert(`Erro ao apagar histórico: ${error}`);
    }
  });
  
  document.getElementById('logout').addEventListener('click', () => {
    if (confirm('Deseja realmente sair?')) {
      localStorage.removeItem('usuario');
      usuarioLogado = null;
      partidaAtual = null;
      mostrarArea('login');
    }
  });
  
  // Inicialização
  verificarLogin();
});