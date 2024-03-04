# Azure

Client, Resource servers:
```
Name: web-client
Client ID: ...
```

```
Name: users-resource-server
Client ID: ...
Secret: ...
Scope: https://users-resource-server/fetch.users, https://users-resource-server/delete.users
```

```
Name: settings-resource-server
Client ID: ...
Secret: ...
Scope: https://settings-resource-server/fetch.settings, https://settings-resource-server/delete.settings
```

# Authorization Code

1.
```
https://login.microsoftonline.com/.../oauth2/v2.0/authorize?response_type=code&redirect_uri=http://localhost/callback&client_id=...&scope=https://users-resource-server/fetch.users
```

2.
```
curl --request POST 'https://login.microsoftonline.com/.../oauth2/v2.0/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'redirect_uri=http://localhost/callback' \
--data-urlencode 'scope=https://users-resource-server/fetch.users' \
--data-urlencode 'client_id=...' \
--data-urlencode 'client_secret=...' \
--data-urlencode 'code=...'
```

# Authorization Code PKCE-enhanced

1.
```
https://login.microsoftonline.com/.../oauth2/v2.0/authorize?response_type=code&redirect_uri=http://localhost/callback&client_id=...&code_challenge=...&code_challenge_method=S256&scope=offline_access%20https://users-resource-server/fetch.users%20https://settings-resource-server/fetch.settings
```

2.
```
curl --request POST 'https://login.microsoftonline.com/.../oauth2/v2.0/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Origin: http://localhost' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'redirect_uri=http://localhost/callback' \
--data-urlencode 'scope=offline_access https://users-resource-server/fetch.users' \
--data-urlencode 'client_id=...' \
--data-urlencode 'code=...'
```
# Refresh token

```
curl --request POST 'https://login.microsoftonline.com/.../oauth2/v2.0/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Origin: http://localhost' \
--data-urlencode 'grant_type=refresh_token' \
--data-urlencode 'redirect_uri=http://localhost/callback' \
--data-urlencode 'scope=offline_access https://settings-resource-server/fetch.settings' \
--data-urlencode 'client_id=...' \
--data-urlencode 'refresh_token=...'
```

# Exchange token

```
curl --request POST 'https://login.microsoftonline.com/.../oauth2/v2.0/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Origin: http://localhost' \
--data-urlencode 'grant_type=refresh_token' \
--data-urlencode 'redirect_uri=http://localhost/callback' \
--data-urlencode 'scope=offline_access https://settings-resource-server/fetch.settings' \
--data-urlencode 'client_id=...' \
--data-urlencode 'refresh_token=...'
```

OR

```
curl --request POST 'https://login.microsoftonline.com/.../oauth2/v2.0/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=urn:ietf:params:oauth:grant-type:jwt-bearer' \
--data-urlencode 'requested_token_use=on_behalf_of' \
--data-urlencode 'scope=https://settings-resource-server/fetch.settings' \
--data-urlencode 'client_id=...' \
--data-urlencode 'client_secret=...' \
--data-urlencode 'assertion=...'
```
