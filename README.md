# jwt
Json Web Token for Chez Scheme


***Dependent libraries:***

core: https://github.com/guenchi/core

JSON: https://github.com/guenchi/json

BASE64: https://github.com/theschemer/BASE64

cHS256: https://github.com/theschemer/cHS256

or ***just let Raven do all:***

`raven install jwt`


***use：***

only HS256 signature for now.

```
payload: association-list
secret: string

(sign payload secret)  => string

(verif token secret)   
                    ok => payload
                    no => #f
```

exemple:

```
(define payload `(("foo" . "bar")("baz" . "foo")))
(define secret "PrivateKey")

(sign payload secret)
=> "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmb28iOiJiYXIiLCJiYXoiOiJmb28ifQ.MTZmYTdmYmMzZjQ4YTRkZTYxMzE3NmUyMzk2MGFmNjI"

(define token "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmb28iOiJiYXIiLCJiYXoiOiJmb28ifQ.MTZmYTdmYmMzZjQ4YTRkZTYxMzE3NmUyMzk2MGFmNjI")

(verif token secret)
=> (("foo" . "bar") ("baz" . "foo"))

(define token "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmb28iOiJiYXIiLCJiYXoiOiJiYXIifQ.MTZmYTdmYmMzZjQ4YTRkZTYxMzE3NmUyMzk2MGFmNjI")
; the value of "baz" has been modife from "foo" to "bar" without sigature.

(verif token secret)                               
=> #f        ; the payload's signature is not match

(verif "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmb28iOiJiYXIiLCJiYXoiOiJmb28ifQ" secret)
=> #f        ; the token have less than 3 "."
```
