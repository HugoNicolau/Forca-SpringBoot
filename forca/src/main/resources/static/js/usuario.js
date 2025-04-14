/**
 * Gerenciamento de Usuário
 */
document.addEventListener('DOMContentLoaded', () => {
  // Verifica se há informações de usuário armazenadas
  function verificarUsuario() {
    return JSON.parse(localStorage.getItem('usuario') || 'null');
  }
  
  // Atualiza dados do usuário no localStorage
  function atualizarDadosUsuario(usuario) {
    localStorage.setItem('usuario', JSON.stringify(usuario));
  }
  
  // Função para fazer logout
  function logout() {
    localStorage.removeItem('usuario');
    window.location.reload();
  }
  
  // Função para atualizar interface com dados do usuário
  function atualizarInterface() {
    const usuario = verificarUsuario();
    
    if (usuario) {
      // Mostrar nome na interface, se houver um elemento para isso
      const nomeUsuarioElement = document.getElementById('nome-usuario');
      if (nomeUsuarioElement) {
        nomeUsuarioElement.textContent = usuario.nome;
      }
    }
  }
  
  // Inicialização
  window.verificarUsuario = verificarUsuario;
  window.atualizarDadosUsuario = atualizarDadosUsuario;
  window.logout = logout;
  
  // Atualizar interface quando a página carrega
  atualizarInterface();
});