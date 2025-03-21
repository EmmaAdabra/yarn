# Yarn

## Overview

Any Post is a Java-based microblogging web application built using Servlets, JSP, and PostgreSQL. It allows users to create posts, comment, like/unlike posts, and manage their profiles in a simple and efficient manner.

## Features

- **User Authentication:** Register, login, and logout functionality.
- **Post Management:** Users can create, fetch, and delete posts.
- **Comment System:** Ability to add and delete comments on posts.
- **Like System:** Users can like and unlike posts.
- **Profile Management:** Update bio, personal details, and profile picture.
- **Dashboard :** From dashboard login users can create post, delete post, comment, delete comment, like, unlike and edit profile.
- **Home:** Frome guest users can comment on post as anonymous, like and unlike post.
- **Security:** Uses OWASP Java HTML Sanitizer to prevent XSS attacks.
- **Logging:** Integrated with SLF4J and Logback for error tracking.

## Technologies Used

- **Backend:** Java Servlets, JSP
- **Database:** PostgreSQL
- **Logging:** SLF4J, Logback
- **Security:** OWASP Java HTML Sanitizer
- **Build System:** Maven
- **Frontend: Tailwind CSS, JavaScript (with AJAX for asynchronous requests).**

## Setup & Installation

### Prerequisites

- JDK 11+
- Maven
- PostgreSQL
- Docker (optional for containerized deployment)

### Steps

1. Clone the repository:
   ```sh
   git clone <repo-url>
   cd any_post
   ```
2. Configure the database:
    - Import `anypost.sql` into PostgreSQL.
    - Set up `.env` with database credentials.
3. Build and run:
   ```sh
   mvn clean install
   mvn tomcat9:run
   ```

## API Endpoints

### Authentication

- `POST /login` - User login
- `POST /register` - User registration
- `POST /logout` - User logout



### Posts

- `POST /submit_post` - Create a post
- `GET /fetch_post` - Retrieve posts
- `DELETE /delete_post` - Delete a post

### Comments & Likes

- `POST /add_comment` - Add comment
- `/fetch_post_comment` - Fetch post comments
- `DELETE /delete_comment` - Remove comment
- `POST /likePost` - Like a post
- `DELETE /deleteLike` - Unlike a post

### User Profile

- `POST /editPersonalData`  - Edit user personal data (name and email)
- `POST /updateBio` - Update user bio text
- `post /uploadPfp` - Upload user profile picture

## For Demo

1. Run with Docker Compose:
   ```sh
   docker-compose up -d
   ```

NB: Docker should be intalled on your machine

## Contributors

- **Author:** [Your Name]
- **Contact:** [Your Email or GitHub]

## License

This project is licensed under the MIT License.
