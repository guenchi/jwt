;  MIT License

;  Copyright guenchi (c) 2018 
         
;  Permission is hereby granted, free of charge, to any person obtaining a copy
;  of this software and associated documentation files (the "Software"), to deal
;  in the Software without restriction, including without limitation the rights
;  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
;  copies of the Software, and to permit persons to whom the Software is
;  furnished to do so, subject to the following conditions:
         
;  The above copyright notice and this permission notice shall be included in all
;  copies or substantial portions of the Software.
         
;  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
;  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
;  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
;  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
;  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
;  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
;  SOFTWARE.






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

