<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Save Success</title>

    <style>
        .title {
            color: sienna;
            font-size: medium;
        }
        .link {
            color: brown;
            font-size: large;
        }
    </style>

</head>
<body>
<p class="title">Уважаемый, ${user.firstName}!</p>
<p class="title">Пройдите для подтверждения регистрации по ссылке: </p>
<a class="link" href="localhost/confirm/${user.confirmCode}" target="_blank">Ссылка</a>
</body>
</html>
