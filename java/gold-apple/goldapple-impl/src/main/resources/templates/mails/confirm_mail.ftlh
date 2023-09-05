<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
</head>
<body>
<p class="title">Уважаемый, ${account.firstName}!</p>
<p class="title">Для подтверждения регистрации пройдите по ссылке:</p>
<a class="link" href="http://localhost:80/confirm/${account.confirmCode}" target="_blank">Сюда</a>
</body>
</html>