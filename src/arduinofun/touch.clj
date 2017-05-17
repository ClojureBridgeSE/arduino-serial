(ns arduinofun.core
  (:gen-class)
  (:require [clojure.java.io :as io])
  (:use [overtone.live]
        [overtone.inst.piano]
        [serial.core :as serial]))

  


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def usb-tty "tty.usbmodem1421")
(def usb-t "cu.usbmodem1421")



(def buffers-atom (atom []))
(def n 1024) ; 1 KiB

(defn exhaust-stream
  ([stream n] (exhaust-stream stream n '()))
  ([stream n buf-so-far]
   (let [new-buf (byte-array n)
         read-len (.read stream new-buf)
         buf-with-new (concat buf-so-far (take read-len new-buf))]
     
     (if (< read-len n)
       buf-with-new
       (recur stream n buf-with-new)))))

(defn do-stuff []
  (println 0)
  (piano (note :c3)))

(defn do-stuff [note]
  (println note) 
  (demo 0.2 (pan2 (sin-osc note))))  


(defn funfun [x]
  (if (and x (not(= x "")))
    (let [note (Integer/parseInt x)
          scaled (* note 10)]
          
      (if (> note 0)
        (do-stuff scaled))))) 

(defn do! [] 
  (let [port (serial/open usb-tty :baud-rate 9600)]
    (serial/listen! port (fn [stream] (funfun (.readLine (io/reader stream)))))))   
