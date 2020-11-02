(ns brian-penguin.youtube
  "Youtube library wrapper"
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [clojure.walk]))


(def playlists-url
  "https://www.googleapis.com/youtube/v3/playlistItems")

(def default-query-params
  {:key "<REGEN A KEY PLEASE>"
   :part "id,contentDetails"
   :accept :json})

(defn playlist-query-params [playlist-id]
  (merge default-query-params {:playlistId playlist-id}))



;; TESTING
;;
;; api-key can be used as a url param as `key` OR as a Header `X-Goog-Api-Key`

;; My clojure playlist url
;; https://www.youtube.com/playlist?list=PLZcJSDMbOzOXS8isGyfSpskVWw4IflxOv
(def clojure-playlist-id
  "PLZcJSDMbOzOXS8isGyfSpskVWw4IflxOv")

(def playlist-response
  (http/get playlists-url {:query-params (playlist-query-params clojure-playlist-id)}))

;; The parsed response body
(def response-body-example
  {"kind" "youtube#playlistItemListResponse",
   "etag" "hBpITFZnSfUWaHKA4DXEjcovKX0",
   "items" [{"kind" "youtube#playlistItem",
             "etag" "XQMtG15BHAE_RD1bvPJ97IJuPew",
             "id"
             "UExaY0pTRE1iT3pPWFM4aXNHeWZTcHNrVld3NElmbHhPdi41NkI0NEY2RDEwNTU3Q0M2",
             "contentDetails"
             {"videoId" "2tBVMAm0-00",
              "videoPublishedAt" "2018-11-29T17:23:57Z"}}
            {"kind" "youtube#playlistItem",
             "etag" "xcw8tYdnhXLNGQgQsxvSAx2c4Rc",
             "id"
             "UExaY0pTRE1iT3pPWFM4aXNHeWZTcHNrVld3NElmbHhPdi4yODlGNEE0NkRGMEEzMEQy",
             "contentDetails"
             {"videoId" "XwjvEMLdjco",
              "videoPublishedAt" "2018-11-14T15:12:39Z"}}
            {"kind" "youtube#playlistItem",
             "etag" "YHrwACBmS7HtJgFp6Reb0Bi5Gk0",
             "id"
             "UExaY0pTRE1iT3pPWFM4aXNHeWZTcHNrVld3NElmbHhPdi4wMTcyMDhGQUE4NTIzM0Y5",
             "contentDetails"
             {"videoId" "mgSSVTDZvkI",
              "videoPublishedAt" "2018-12-01T16:24:10Z"}}
            {"kind" "youtube#playlistItem",
             "etag" "Q8Gs1jRlZnmikwu58rvBymTXTd8",
             "id"
             "UExaY0pTRE1iT3pPWFM4aXNHeWZTcHNrVld3NElmbHhPdi41MjE1MkI0OTQ2QzJGNzNG",
             "contentDetails"
             {"videoId" "30Odhss1xVA",
              "videoPublishedAt" "2019-05-03T19:29:42Z"}}],
   "pageInfo" {"totalResults" 4, "resultsPerPage" 5}})

(comment
  (defn string-to-symbol [value]
    (if (string? value) (keyword value) value))

  (defn symbolify-keys
    "If you don't have to worry about a nested structure you can just use this!"
    [response-body]
    (into {} (for [[k v] response-body] [(string-to-symbol k) v] ))))

(def items
  "clojure.walk/keywordize-keys will recursively change all strings to symbols if they're keys"
  (map clojure.walk/keywordize-keys (get response-body-example "items")))

(defn video-id-from-item [item]
  (get-in item ["contentDetails" "videoId"]))

(defn video-url [video-id]
  (str "https://www.youtube.com/watch?v=" video-id))

(defn items-to-urls []
  (->>
    items
    (map video-id-from-item)
    (map video-url)))
