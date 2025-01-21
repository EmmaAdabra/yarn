<%--
  Created by IntelliJ IDEA.
  User: Viewnet
  Date: 1/21/2025
  Time: 00:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
    <style>
        body{
            text-align: center;
        }

        /*.date{*/
        /*    text-align: right;*/
        /*    margin-bottom: 30px;*/
        /*    padding: 10px;*/
        /*    color: #494848;*/
        /*    font-family: monospace;*/
        /*}*/

        #errors{
            color: darkred;
        }

        section {
            max-width: 600px;
            width: 100%;
            margin: 0 auto;
        }

        label, input{
            display: block;
            width: 100%;
            text-align: left;
        }

        label{
            margin-bottom: 10px;
        }

        input{
            margin-bottom: 20px;
            padding: 5px;
        }
        hr{
            margin: 30px 0;
        }
    </style>
</head>
<body>
    <main>
        <header>
            <h1>Chat Adpp</h1>
            </header>
            <section>
                <div>
                    <h2>Create account</h2>
                    <p>It's quick and easy.</p>
                </div>

                <form method="post" action="/register" >
                <c:if test="${error != null}">
                    <p id="errors">${error}</p>
                </c:if>

                <label for="firstName">First name</label>
                <input type="text" id="firstName" name="firstName"
                       placeholder="First name" pattern="^[A-Za-z]+(['-]?[A-Za-z]*){0,49}$"
                       title="
                       Input must start with a letter, be 2 to 50 characters long, and
                       can optionally contain apostrophes (') or hyphens (-) only after a letter.
                       " maxlength=50 minlength=2 required
                />

                <label for="lastName">Surname</label>
                <input type="text" id="lastName" name="lastName"
                       placeholder="Surname" pattern="^[A-Za-z]+(['-]?[A-Za-z]*){0,49}$"
                       title="
                       Input must start with a letter, be 2 to 50 characters long, and
                       can optionally contain apostrophes (') or hyphens (-) only after a letter.
                       " maxlength="50" minlength="2" required
                />

                <label for="username">Username</label>
                <input type="text" id="username" name="username"
                       pattern="[a-zA-Z][a-zA-Z0-9.]{4,49}(?<!\.)$"
                       title="
                        Input must start with a letter, be 5 to 50 characters long, can contain letters,
                        digits, and dots (.), but cannot end with a dot." minlength=4 maxlength=50
                />

                <label for="email">Email</label>
                <input type="email" id="email" name="email" required />

                <label for="password">Password</label>
                <input type="password" id="password" name="password" pattern=".{4,18}"
                   title="Input must be between 4 and 18 characters long." required
                />

                <label for="confirmPassword">Confirm password</label>
                <input type="password" id="confirmPassword" name="confirmPassword"
                       pattern=".{4,18}"
                       title="Input must be between 4 and 18 characters long." required
                />

                <button>Sign Up</button>
            </form>
        </section>
    </main>
</body>
</html>
