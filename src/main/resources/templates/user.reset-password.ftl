<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reset your password</title>
    <style>
    @import url('https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;1,300;1,400;1,500;1,600;1,700;1,800&display=swap');

      body {
        font-family: 'Open Sans', sans-serif;
      }

      .container {
        width: 500px;
        margin: 100px auto;
        text-align: center;
        background-color: #fff;
        padding: 30px 40px 60px 40px;
        border-radius: 10px;
        box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.1), -2px -2px 8px rgba(255, 255, 255, 0.3);
        transition: all 0.3s ease-in-out;
      }
      h1 {
        font-size: 36px;
        color: #333;
        margin-bottom: 20px;
        text-transform: uppercase;
        letter-spacing: 2px;
      }
      p {
        font-size: 18px;
        color: #555;
        margin-bottom: 30px;
        line-height: 1.5;
      }

      .token {
        font-size: 24px;
        color: #ff5a5f;
        margin-bottom: 30px;
        font-weight: 600;
      }

      .btn {
        background-color: #ff5a5f;
        color: #fff;
        padding: 12px 20px;
        border-radius: 5px;
        text-decoration: none;
        letter-spacing: 2px;
        transition: all 0.3s ease-in-out;
      }
      .container:hover {
        box-shadow: 4px 4px 12px rgba(0, 0, 0, 0.2), -4px -4px 12px rgba(255, 255, 255, 0.4);
      }
      .btn:hover {
        box-shadow: 4px 4px 12px rgba(0, 0, 0, 0.2), -4px -4px 12px rgba(255, 255, 255, 0.4);
      }


    </style>
</head>
<body>
<div class="container">
    <h1>Reset your password</h1>
    <p>If you requested a password reset for ${user}, use the token below to change it. If not, just ignore this email.</p>
    <p class="token">${token}</p>
</div>
</body>
</html>
