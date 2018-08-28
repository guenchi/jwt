# jwt
Json Web Token for Chez Scheme


***Dependent libraries:***

JSON: https://github.com/guenchi/json

BASE64: https://github.com/theschemer/BASE64

cHS256: https://github.com/theschemer/cHS256

or just let Raven do all:

`raven install jwt`


use：


```
payload: Association list
secret: string

(sign payload secret)  => string

(verif token secret)   
                    ok => payload
                    no => #f
```
