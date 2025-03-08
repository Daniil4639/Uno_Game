window.addEventListener('load', () => {
    const form = document.getElementById("username-form");

	form.addEventListener('submit', (e) => {
		e.preventDefault();

        getRoomsList();
	});

	document.getElementById('update-rooms').click();
});

function getRoomsList() {
    fetch('/api/rooms')
        .then(response => response.json())
        .then(data => addRoomsToList(data))
        .catch(error => console.error(error));
}

function addRoomsToList(rooms) {
    console.log(rooms);

    const list_el = document.getElementById("rooms");

    list_el.innerHTML = '';

    rooms.forEach(room => {
            const room_el = document.createElement('div');
            room_el.classList.add('room');

            const room_content_el = document.createElement('div');
            room_content_el.classList.add('content');

            room_el.appendChild(room_content_el);

            const room_input_el = document.createElement('input');
            room_input_el.classList.add('text');
            room_input_el.type = 'text';
            room_input_el.value = room.roomName;
            room_input_el.setAttribute('readonly', 'readonly');

            room_content_el.appendChild(room_input_el);

            const room_actions_el = document.createElement('div');
            room_actions_el.classList.add('actions');

            const room_count_el = document.createElement('input');
            room_count_el.classList.add('text');
            room_count_el.type = 'text';
            room_count_el.value = (room.userCount + '/12');
            room_count_el.setAttribute('readonly', 'readonly');

            room_actions_el.appendChild(room_count_el);

            const room_join_el = document.createElement('button');
            room_join_el.classList.add('join');
            room_join_el.innerText = 'Join';

            room_actions_el.appendChild(room_join_el);

            room_el.appendChild(room_actions_el);

            list_el.appendChild(room_el);

            room_join_el.addEventListener('click', (e) => {
                console.log('Join clicked!')

                window.location.href = ('/uno/game?room_id=' + room.roomId);
            });
    });
}