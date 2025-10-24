// script.js
document.addEventListener('DOMContentLoaded', function() {
    // Elementos do chat
    const chatLink = document.getElementById('chat-link');
    const chatWindow = document.getElementById('chat-window');
    const closeChat = document.getElementById('close-chat');
    const tabButtons = document.querySelectorAll('.tab-button');
    
    // Função para abrir o chat
    function openChat() {
        chatWindow.style.display = 'flex';
    }
    
    // Função para fechar o chat
    function closeChatWindow() {
        chatWindow.style.display = 'none';
    }
    
    // Função para trocar de aba
    function switchTab(event) {
        // Remove a classe active de todos os botões
        tabButtons.forEach(button => {
            button.classList.remove('active');
        });
        
        // Adiciona a classe active ao botão clicado
        event.target.classList.add('active');
        
        // Oculta todos os conteúdos das abas
        document.querySelectorAll('.tab-content').forEach(content => {
            content.classList.remove('active');
        });
        
        // Mostra o conteúdo da aba correspondente
        const tabId = event.target.getAttribute('data-tab') + '-tab';
        document.getElementById(tabId).classList.add('active');
    }
    
    // Event Listeners
    chatLink.addEventListener('click', function(e) {
        e.preventDefault();
        openChat();
    });
    
    closeChat.addEventListener('click', closeChatWindow);
    
    tabButtons.forEach(button => {
        button.addEventListener('click', switchTab);
    });
    
    // Fechar o chat ao clicar fora dele (opcional)
    document.addEventListener('click', function(e) {
        if (chatWindow.style.display === 'flex' && 
            !chatWindow.contains(e.target) && 
            e.target !== chatLink) {
            closeChatWindow();
        }
    });
    
    // Adicionar funcionalidade aos botões de mensagem na aba de contatos
    const messageButtons = document.querySelectorAll('.message-button');
    messageButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Alternar para a aba de conversas
            tabButtons[0].click();
            
            // Aqui você poderia adicionar lógica para iniciar uma nova conversa
            // com o contato específico
            alert('Iniciando conversa com este contato...');
        });
    });
});

document.addEventListener('DOMContentLoaded', function() {
    const botaoHamburguer = document.querySelector('.menu-hamburguer');
    const menuLinks = document.querySelector('#mobile-menu'); // O elemento nav

    if (botaoHamburguer && menuLinks) {
        botaoHamburguer.addEventListener('click', function() {
            // Alterna a classe 'is-open' no menu de links
            menuLinks.classList.toggle('is-open');

            // Alterna o atributo ARIA para acessibilidade
            const isExpanded = this.getAttribute('aria-expanded') === 'true' || false;
            this.setAttribute('aria-expanded', !isExpanded);
        });
    }
});