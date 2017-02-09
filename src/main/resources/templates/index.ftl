<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spring5Demo</title>
    <style>
        body {
            font-family: "Helvetica Neue", sans-serif;
            font-size: 13px;
        }

        #messages {
            display: flex;
            flex-direction: column;
            max-width: 600px;
        }

        .message {
            display: flex;
            width: 100%;
            padding-bottom: 2px;
        }

        .message__name {
            flex: 1 0 20%;
            padding: .5em;
            background-color: #fbffed;
        }

        .message__text {
            flex: 1 0 80%;
            padding: .5em;
        }
    </style>
</head>
<body>
<h2>Messages</h2>
<section id="messages">
</section>

<script lang="javascript">
    const messagesBlock = document.querySelector("#messages");
    const sse = new EventSource("/gitter/messages");
    sse.onmessage = (e) => {
        let data = JSON.parse(e.data);
        messagesBlock.appendChild(buildMessage(data.fromUser.displayName, data.text))
    };
    sse.onerror = (ex) => {
        console.error(ex);
    };

    function buildMessage(name, text) {
        let container = document.createElement("div");
        container.className = "message";
        let nameBox = document.createElement("div");
        nameBox.className = "message__name";
        nameBox.textContent = name;
        let textBox = document.createElement("div");
        textBox.className = "message__text";
        textBox.textContent = text;


        container.appendChild(nameBox);
        container.appendChild(textBox);
        return container
    }
</script>
</body>
</html>