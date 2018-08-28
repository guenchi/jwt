(library (jwt jwt)
    (export
        sign
        verif
    )
    (import
        (scheme)
        (chs256 chs256)
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
            b64decode)
        
    )

    (define split
        (lambda (s c)
            (letrec* ((len (string-length s))
                (walk (lambda (str begin end rst)
                        (cond 
                            ((>= begin len) rst)
                            ((or (= end len) (char=? (string-ref str end) c))
                                (walk 
                                    str 
                                    (+ end 1)
                                    (+ end 1)
                                    (if (= begin end) 
                                        rst
                                        (cons (substring str begin end) rst))))
                            (else (walk str begin (+ end 1) rst))))))
            (reverse (walk s 0 0 '())))))

    (define head (b64encode "{\"alg\":\"HS256\",\"typ\":\"JWT\"}"))


    (define sign
        (lambda (payload secret)
            (let ((body (string-append head "." (b64encode (json->string payload)))))
                (string-append body "." (b64encode (hs256 body secret))))))


    (define verif
        (lambda (token secret)
            (let ((payload (split token #\.)))
                (if (equal? (caddr payload) (b64encode (hs256 (string-append (car payload) "." (cadr payload)) secret)))
                    (string->json (b64decode (cadr payload)))
                    #f))))

 
)

