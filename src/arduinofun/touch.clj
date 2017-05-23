(ns arduinofun.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:use [overtone.live]
        [overtone.inst.piano]
        [serial.core :as serial]
        [serial.util :as sutil]))

(defn do-stuff [note]  (println note)   (demo 0.2 (pan2 (sin-osc note)))) 

(defn funfun [x]
  (if (and x (not(= x "")))
    (let [note (Integer/parseInt x)
          scaled (* note 50)]
      (if (> note 0)
        (do-stuff scaled)))))

(defn play-me! [something-something]
  (let [one (first something-something)
        another (second something-something)
        third (nth something-something 2)
        fourth (nth something-something 3)
        fifth (nth something-something 4)
        sixth (nth something-something 5)]
    (funfun one)
    (funfun another)
    (funfun third)
    (funfun fourth)
    (funfun fifth)
    (funfun sixth)))

(defn autobahn [x]  
  (if (and x (not(= x "")))
    (do 
      (println x)
      (let [baby (str/split x #"\t+")]
        (println baby)
        (play-me! baby)))))
        
(def usb-tty-m "tty.usbmodem1421")
(def usb-tty-c "tty.usbmodem1411")
(defn do! [] 
  (let [port (serial/open usb-tty-m :baud-rate 9600)]
    (serial/listen! port (fn [stream] (autobahn (.readLine (io/reader stream))))))) 
