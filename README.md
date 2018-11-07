# bestmixer

A Clojure library designed to handle API calls to BextMixer.io

## Usage

Review API information here https://bestmixer.io/en/api

First set your api key

```
(set-api-key "api_key_here")
```

Then you can start making calls like

```
(get-fee-info)
```

Or create an order and view it's letter of guarantee.

```
(println (b64decode (:letter_of_guarantee
                       (:data
                        (let [output (create-output
                                      "1xxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
                                      100
                                      45)]
(create-order "" "btc" 0.5001 output)))))))
```

## License

Copyright © 2018 Ali Raheem

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
