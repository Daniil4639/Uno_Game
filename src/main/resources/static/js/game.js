var usernamePage = null;
var chatPage = null;
var messageForm = null;
var messageInput = null;
var messageArea = null;

var stompClient = null;
var username = null;
var room = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

window.addEventListener('load', () => {
    usernamePage = document.getElementById('username-page');
    chatPage = document.getElementById('chat-page');
    messageForm = document.getElementById('messageForm');
    messageInput = document.getElementById('message');
    messageArea = document.getElementById('messageArea');
    username = document.getElementById('username_folder').innerHTML;
    room = document.getElementById('room_folder').innerHTML;

    messageForm.addEventListener('click', sendMessage);

    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
});

function onConnected() {
    stompClient.subscribe('/topic/' + room, onMessageReceived);

    stompClient.send('/app/chat/' + room,
        {},
        JSON.stringify({sender: username, type: 'ADD'})
    );
}

function onError(error) {
    alert('Connection error! You will be redirected to the Home page...');

    window.setTimeout(
        function() {
            window.location.href = ('/uno/rooms');
        }, 500
    );
}

function sendMessage() {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            type: 'SEND',
            content: messageInput.value
        };

        stompClient.send('/app/chat/' + room, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'ADD') {
        messageElement.classList.add('event-message');
    } else if (message.type === 'DELETE') {
        messageElement.classList.add('event-message');
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}