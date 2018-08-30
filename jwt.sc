(library (jwt jwt)
    (export
        sign
        verif)
    (import
        (scheme)
        (chs256 chs256)
        (only
            (core string)
            split)
        (only
            (json json)
            string->json
            json->string)
        (only
            (rename
                (base64 base64)
                (encode-utf8-url b64encode)
                (decode-utf8-url b64decode))
            b64encode
            b64decode))


    (define head (b64encode "{\"alg\":\"HS256\",\"typ\":\"JWT\"}"))


    (define sign
        (lambda (payload secret)
            (let ((body (string-append head "." (b64encode (json->string payload)))))
                (string-append body "." (b64encode (hs256 body secret))))))


    (define verif
        (lambda (token secret)
            (let ((payload (split token #\.)))
                (if 
                    (and
                        (equal? (length payload) 3)
                        (equal? 
                            (caddr payload) 
                            (b64encode
                                (hs256 
                                    (string-append (car payload) "." (cadr payload))
                                    secret))))
                    (string->json (b64decode (cadr payload)))
                    #f))))

 
)

