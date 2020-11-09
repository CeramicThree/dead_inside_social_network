'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var messageForm = document.querySelector('#message_form');
var messageInput = document.querySelector('#message_input');
var messageArea = document.querySelector('.chat_messages');
var connectButton = document.querySelector('#connect_button');

var stompClient = null;
var username = null;

function connect(event) {
    username = document.querySelector('.user_name').textContent.trim();
    if (username) {
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function onError(){
    console.log("Connection error")
}

function onConnected() {
    stompClient.subscribe('/topic/public', onMessageReceived);
    // stompClient.send("/app/chat.addUser",
    //     {},
    //     JSON.stringify({senderName: username})
    // )
    connectButton.classList.add('hidden')
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    var messageElement = document.createElement('li');
    messageElement.classList.add('message');

    var messageDiv = document.createElement('div');
    var messageTextElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    messageTextElement.appendChild(messageText);
    messageDiv.appendChild(messageTextElement);

    var messageImg = document.createElement('img');
    messageImg.src = message.senderPic;

    messageElement.appendChild(messageImg);
    messageElement.appendChild(messageDiv);
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            senderName: username,
            content: messageInput.value,
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

// connectButton.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);