# Keycloak

Install:
```
docker run -d -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:21.1.2 start-dev --features=preview
```

Client, Resource servers:
```
Name: web-client
Client ID: ...
```

```
Name: users-resource-server
Client ID: ...
Secret: ...
Scope: users-resource-server/fetch.users, users-resource-server/delete.users
```

```
Name: settings-resource-server
Client ID: ...
Secret: ...
Scope: settings-resource-server/fetch.settings, settings-resource-server/delete.settings
```

# Authorization Code

1.
```
http://localhost:8080/realms/master/protocol/openid-connect/auth?response_type=code&redirect_uri=http://localhost/callback&client_id=users-resource-server&scope=users-resource-server/fetch.users
```

2.
```
curl --request POST 'http://localhost:8080/realms/master/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'redirect_uri=http://localhost/callback' \
--data-urlencode 'scope=users-resource-server/fetch.users' \
--data-urlencode 'client_id=users-resource-server' \
--data-urlencode 'client_secret=...' \
--data-urlencode 'code=...'
```

# Authorization Code PKCE-enhanced

1.
```
http://localhost:8080/realms/master/protocol/openid-connect/auth?response_type=code&redirect_uri=http://localhost/callback&client_id=web-client&code_challenge=...&code_challenge_method=S256&scope=users-resource-server/fetch.users%20settings-resource-server/fetch.settings
```

2.
```
curl --request POST 'http://localhost:8080/realms/master/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'redirect_uri=http://localhost/callback' \
--data-urlencode 'scope=users-resource-server/fetch.users settings-resource-server/fetch.settings' \
--data-urlencode 'client_id=web-client' \
--data-urlencode 'code=...' \
--data-urlencode 'code_verifier=...'
```

# Client credentials

```
curl --request POST 'http://localhost:8080/realms/master/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'redirect_uri=http://localhost/callback' \
--data-urlencode 'scope=profile' \
--data-urlencode 'client_id=users-resource-server' \
--data-urlencode 'client_secret=...'
```

# Refresh token

```
curl --request POST 'http://localhost:8080/realms/master/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=refresh_token' \
--data-urlencode 'redirect_uri=http://localhost/callback' \
--data-urlencode 'scope=profile' \
--data-urlencode 'client_id=users-resource-server' \
--data-urlencode 'client_secret=...' \
--data-urlencode 'refresh_token=...'
```

# Exchange token

```
curl --request POST 'http://localhost:8080/realms/master/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=urn:ietf:params:oauth:grant-type:token-exchange' \
--data-urlencode 'audience=settings-resource-server' \
--data-urlencode 'scope=settings-resource-server/fetch.settings' \
--data-urlencode 'client_id=users-resource-server' \
--data-urlencode 'client_secret=...' \
--data-urlencode 'subject_token=...'
```

# Revoke token

```
curl --request POST 'http://localhost:8080/realms/master/protocol/openid-connect/revoke' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=users-resource-server' \
--data-urlencode 'client_secret=...' \
--data-urlencode 'token_type_hint=access_token' \
--data-urlencode 'token=...'
```
