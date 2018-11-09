(ns bestmixer.core
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]))

(def api-key (atom ""))
(def api-url "https://bestmixer.io/api/ext")

(defn request
  "Take data and API path and make call return parsed response"
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
  "Return information about an order in progress"
  [order-id]
  (request "/order/info" {:order_id order-id}))

(defn get-code-info
  "Return information about a BestMixer loyalty code"
  [bm-code]
  (request "/code/info" {:bm_code bm-code}))

(defn create-output
  "Create a new output array"
  [address
   percent
   delay]
  {:address address :percent percent :delay delay})

(defn create-order
  "Create a new mix order"
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
  "Helper function to b64decode letter of guarantee wrapping java"
  [log]
  (String. (.decode (java.util.Base64/getDecoder) log)))

(defn set-api-key
  "Set your BestMixer.io API key"
  [new-key]
  (reset! api-key new-key))

(defn -main
  "Skeleton usage of API"
  []
  (set-api-key "replace_with_api_key")
  (println (b64decode (:letter_of_guarantee
                       (:data
                        (let [output (create-output
                                      "replace_with_address"
                                      100
                                      45)]
                          (create-order "" "btc" 0.5001 [output])))))))
