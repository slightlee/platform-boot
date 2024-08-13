
浏览器地址栏访问

http://127.0.0.1:9000/oauth2/authorize?client_id=oidc-client&response_type=code&scope=user.info+openid&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/oidc-client

账号密码登录 允许

地址栏code 值 用作 授权码方式登录的 code


## 1、授权码方式

POST http://127.0.0.1:9000/oauth2/token

> Body 请求参数

```yaml
code: Ke2kAW8f-0SO2mhYUJcAqh0YGejoJxoWMAZX3uVhJ4Os7Cog9VP4r7AqqR3dxUD5FAtAmn1G1gDslGPUfDW0Dzc-cP8fCkZGeXLumLtb7GVPVqloRa8T9mI0SF24lsO8
grant_type: authorization_code
redirect_uri: http://127.0.0.1:8080/login/oauth2/code/oidc-client

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» code|body|string| 否 |none|
|» grant_type|body|string| 否 |none|
|» redirect_uri|body|string| 否 |none|

> 返回示例

```json
{
  "access_token": "eyJraWQiOiJmN2U5OTUwYy0wZjU2LTRmNmUtYmE4MC1kNzU3MGMwODljZjkiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXVkIjoib2lkYy1jbGllbnQiLCJuYmYiOjE3MjM2MTUyMzIsInNjb3BlIjpbInVzZXIuaW5mbyIsIm9wZW5pZCJdLCJpc3MiOiJodHRwOi8vMTI3LjAuMC4xOjkwMDAiLCJleHAiOjE3MjM2MjI0MzIsImlhdCI6MTcyMzYxNTIzMiwianRpIjoiYzFkMmY3YmUtNjI2Ny00ZDI1LTgwODEtMzYzMTYyY2RiMzhlIn0.4BDpVLKzHk04zej9cucrSonlM5I1vHuOo2wk1zicOnzHJ3eJckA_Q1Y1KCvX92Z4AYlsjd606V8aJD5MIcoAGrrJG9WGlzQi-xqsLg0RUHEGmjZJ-wsXP8PreKmP7b8vMil6erx8iOtAISjg0Z33RnPCMZFn0BJLbXXgvT30rYhVH7nV09JwSQeYbHH-K9bve3z95EbSDtwvxjYYhtldISMGfK4eES2DY0qx-pyyU_lfLRmnyELS5NzBCU--iVhzi6FKtO5w-FnlbdRXhzQAogbMMWFRcpnVqtG10j0nbw4dOmTX81UgYhB5nahX0DcxuWHHXc6JIboIcNemd6wAeg",
  "refresh_token": "3w-nzUvOA4jPoxh6_xb6tUNpZuDME8_XiJ7A7Sxdv7AvikMkYKmmS7xiZl_LJfBMB2HPdm-4kouyiqCmSVNPqbndSBTMw3mj2guL4c4MVgzklVEvmg6STEQY35m7uO9u",
  "scope": "user.info openid",
  "id_token": "eyJraWQiOiJmN2U5OTUwYy0wZjU2LTRmNmUtYmE4MC1kNzU3MGMwODljZjkiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXVkIjoib2lkYy1jbGllbnQiLCJhenAiOiJvaWRjLWNsaWVudCIsImF1dGhfdGltZSI6MTcyMzYxNTIyMSwiaXNzIjoiaHR0cDovLzEyNy4wLjAuMTo5MDAwIiwiZXhwIjoxNzIzNjE3MDMyLCJpYXQiOjE3MjM2MTUyMzIsImp0aSI6IjIyZWRmOTU1LTJkMzYtNGY3OS05OWYzLTg3YTg4ZTQ5ZmJmMyIsInNpZCI6IlYxT29ReEVJVFBhRE1qaDFfb0lSZS1sY1g3S0tQWUxOMUZCMGJ2c0lHOE0ifQ.0Y5Nvj4fvjrOLTnK9Em5gP3BM0V7G7LbOQ0F9YkVsezyviNOJh6A5Xz6Fdgts7YzFMSwF2KnsEUW2dFVpLGSVwixYlMEnAZhQDz689I-Ro_GHHamMzHqXruxA5K5GWEeC7-0gaJHgtTnrBZJI63ciMqs321PtB5iV0OUfXfTBuZ1RTVWF4QF24rJlAnAERtEdnOdY4S_FQtLOXJcYpC2NbR8L31SAH6dw_cfA3nAehblcpi9ylJ-U2bk6oaccmPIN6BA-2ydVtQYKFg_PTJLHuAEr-udl1d1I2g20hFHjwLem1ZYtModLUP7XcSAAnEWFpJySs8MUbZY0pzbEYJ3VA",
  "token_type": "Bearer",
  "expires_in": 7200
}
```

## 2、获取用户详细信息

POST http://127.0.0.1:9000/userinfo

> 返回示例

```json
{
  "sub": "user"
}
```

## 3、认证服务器详细信息

GET http://127.0.0.1:9000/.well-known/openid-configuration

> 返回示例

```json
{
  "issuer": "http://127.0.0.1:9000",
  "authorization_endpoint": "http://127.0.0.1:9000/oauth2/authorize",
  "device_authorization_endpoint": "http://127.0.0.1:9000/oauth2/device_authorization",
  "token_endpoint": "http://127.0.0.1:9000/oauth2/token",
  "token_endpoint_auth_methods_supported": [
    "client_secret_basic",
    "client_secret_post",
    "client_secret_jwt",
    "private_key_jwt"
  ],
  "jwks_uri": "http://127.0.0.1:9000/oauth2/jwks",
  "userinfo_endpoint": "http://127.0.0.1:9000/userinfo",
  "end_session_endpoint": "http://127.0.0.1:9000/connect/logout",
  "response_types_supported": [
    "code"
  ],
  "grant_types_supported": [
    "authorization_code",
    "client_credentials",
    "refresh_token",
    "urn:ietf:params:oauth:grant-type:device_code"
  ],
  "revocation_endpoint": "http://127.0.0.1:9000/oauth2/revoke",
  "revocation_endpoint_auth_methods_supported": [
    "client_secret_basic",
    "client_secret_post",
    "client_secret_jwt",
    "private_key_jwt"
  ],
  "introspection_endpoint": "http://127.0.0.1:9000/oauth2/introspect",
  "introspection_endpoint_auth_methods_supported": [
    "client_secret_basic",
    "client_secret_post",
    "client_secret_jwt",
    "private_key_jwt"
  ],
  "code_challenge_methods_supported": [
    "S256"
  ],
  "subject_types_supported": [
    "public"
  ],
  "id_token_signing_alg_values_supported": [
    "RS256"
  ],
  "scopes_supported": [
    "openid"
  ]
}
```


