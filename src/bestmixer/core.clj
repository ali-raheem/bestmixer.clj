(ns bestmixer.core
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]))

(def api-key (atom ""))
(def api-url "https://bestmixer.io/api/ext")

(defn request
  "Take data and API path and make call return parsed response
  Takes the API `path` and the `data` to send in a map."
  [path
   data]
  (parse-string (:body (client/post
                        (str api-url path)
                        {:form-params
                         (merge {:api_key @api-key} data)
                         :content-type :json})) true))

(defn get-fee-info
  "Return information about minimum miner fee's per address"
  []
  (request "/fee/info" {}))

(defn get-order-info
  "Return information about an order in progress
  Takes the `order-id`"
  [order-id]
  (request "/order/info" {:order_id order-id}))

(defn get-code-info
  "Return information about a BestMixer loyalty code
  Takes `bm-code`"
  [bm-code]
  (request "/code/info" {:bm_code bm-code}))

(defn create-output
  "Create a new output array
  Takes an `address` the `percent` up to 100 output directed to that `address` and the `delay` in minutes"
  [address
   percent
   delay]
  {:address address :percent percent :delay delay})

(defn create-order
  "Create a new mix order
  Takes a `bm-code`, `coin`, `fee` and vector of `output` returns the new order"
  [bm-code
   coin
   fee
   output]
  (request "/order/create" {:bm_code bm-code
                            :coin coin
                            :fee fee
                            :output output
                            }))

(defn b64decode
  "Helper function to b64decode letter of guarantee wrapping java
  Takes the `string` to Base64 decode."
  [string]
  (String. (.decode (java.util.Base64/getDecoder) string)))

(defn set-api-key
  "Set your BestMixer.io API key
  Takes an API key in `new-key` as provided by BestMixer.io"
  [new-key]
  (reset! api-key new-key))

(defn -main
  "Skeleton usage of API"
  []
  (set-api-key "replace_with_api_key")
;; Create order to mix litecoin to 1 output address with 45minute delay
  (->>
   (create-output "LYDp9NWddbzxNfmmNj2tEjdXyDbxRuvgX4" 100 45)
   vector
   (create-order "" "ltc" 0.5001)
   :data
   :letter_of_guarantee
   b64decode
   println))
