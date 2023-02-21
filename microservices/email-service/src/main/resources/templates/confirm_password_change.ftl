<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <style>
        .title {
            font-size: 25px;
            margin-bottom: 10px;
        }

        .container {
            margin: 30px;
            border: 2px solid CornflowerBlue;
            align-items: center;
            text-align: center;
        }

        a {
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        a:link {
            color: #497DDD;
            border-bottom: 1px dashed;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="title"> Dear ${user.firstName}! To confirm registration please follow this <a href=${confirmLink}>link</a></div>
</div>
</body>
</html>
