# E2C BO FrontEnd
### _v.0.1_

This is the master branch of E2C frontEnd.
This document decribes the steps to follow to build the project in a Debian/Ubuntu architecture.

## Features
- Signup, login and logout
- Account management
- Profile management
- Association management
- AP management : Create, View, submit
- Rapport de démarrage mangement : view, process
- Rapport mi-parcours management : view, process
- Rapport final : view, process
- Soumission management : view, process

This text you see here is *actually- written in Markdown! To get a feel
for Markdown's syntax, type some text into the left window and
watch the results in the right.

## Tech
E2C frontEnd uses a number of open source projects to work properly:
- [Angular CLI v12.0.0]
- [Node.js v16.15.1]
- [npm v8]

## Setup & Getting started

1. Install [NodeJS](http://nodejs.org/) (If you done have)

2. If you have not installed angular yet,

#Install angular globally

```
$ npm install -g @angular/cli@12.0.0-rc.2
```

3. Clone porjet FrontEnd

```
$ ng clone http://54.36.111.28:81/e2c/bo-frontend.git
```

4. navigate to BO-frontend/ directory.

```   
$ cd BO-frontend 
```

5. then install dependencies

```
$ npm install
```

6. Serve the application using (run dev)


6. Getting started

Run `ng serve` for a dev server.
 
```
$ ng serve
```

#Navigate to `http://localhost:4100/`. The app will automatically reload if you change any of the source files.

### Building the project

1. Run `ng build` to build the project.
```
$ ng build --prod --base-href '/admin/’
```
 The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.
 
# !!!! After re-name build folder `E2C-BO` it to `admin`

2. create .htaccess file & add variables:

- create file `Nouveau Document texte.txt` & re-name it to `.htaccess`
- set your desired variable value
```
<IfModule mod_rewrite.c>
    RewriteEngine on
    RewriteCond %{REQUEST_FILENAME} -s [OR]
    RewriteCond %{REQUEST_FILENAME} -l [OR]
    RewriteCond %{REQUEST_FILENAME} -d
    RewriteRule ^.*$ - [NC,L]
    RewriteRule ^(.*) /admin/index.html [NC,L]
</IfModule>
```
3. After copy/paste .htaccess file in build folder(admin)

## Deploy Bo-frontend

# Deploy Angular Bo-frontend App to Apache Server

!. Install Apache2:
Install from here: http://httpd.apache.org/docs/current/install.html

2. Copy the dist/admin folder in /var/www/ folder.



## License

E2C - copyright - 2022-2023