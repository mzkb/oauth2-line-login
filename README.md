# Line OAuth2 Demo Application
This is a basic demo application that showcases OAuth2 integration with Line's authentication system. It demonstrates the fundamental aspects of authenticating a user via Line and fetching their profile details.

## Features
* Line OAuth2 authentication.
* Fetching user profile details upon successful authentication.
* Graceful handling of authentication errors.

## Prerequisites
Ensure you have the following prerequisites before setting up the project:

* Java 11 or higher
* A Line Developer account and a Line Login channel (for obtaining OAuth2 credentials)

## Configuration
The application uses environment variables for its configuration. Before running the application, ensure that you set the following environment variables:

LINE_CLIENT_ID: Your Line Login Client ID.  
LINE_CLIENT_SECRET: Your Line Login Client Secret.  
LINE_REDIRECT_URI: Your callback URL for Line authentication.  

## Setting Environment Variables
Linux/Mac:
```shell
export LINE_CLIENT_ID=your_client_id
export LINE_CLIENT_SECRET=your_client_secret
export LINE_REDIRECT_URI=your_callback_url
```

Windows:
Using Command Prompt:

```shell
set LINE_CLIENT_ID=your_client_id
set LINE_CLIENT_SECRET=your_client_secret
set LINE_REDIRECT_URI=your_callback_url
```

Using PowerShell:
```shell
$env:LINE_CLIENT_ID="your_client_id"
$env:LINE_CLIENT_SECRET="your_client_secret"
$env:LINE_REDIRECT_URI="your_callback_url"
```