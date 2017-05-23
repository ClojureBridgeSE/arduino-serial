(ns test-penta.core  
  (:require [clojure.math.numeric-tower :as math]   
            [overtone.live :as live])   
            ;;[overtone.inst.piano :as piano]) 
  (:use [overtone.synth.stringed]   
        [overtone.live]))
(defn midifreq [midi] ; a4 = 69  
  (* 440 (math/expt 2 (/ (- midi 69) 12))))

(definst piano [freq 440
                gate 1
                vel 100
                decay 0.8
                release 0.8
                hard 0.8
                velhard 0.8
                muffle 0.8
                velmuff 0.8
                velcurve 0.8
                stereo 0.2
                tune 0.5
                random 0.1
                stretch 0.1
                sustain 0.1]  
  (let [snd (mda-piano {:freq freq
                        :gate gate
                        :vel vel
                        :decay decay
                        :release release
                        :hard hard
                        :velhard velhard
                        :muffle muffle
                        :velmuff velmuff
                        :velcurve velcurve
                        :stereo stereo
                        :tune tune
                        :random random
                        :stretch stretch
                        :sustain sustain})]
    (detect-silence snd 0.005 :action FREE)
    (* 1 snd))) ;;TODO: figure out why this mul is required



(def penny  [[[:c4 :e4 :g4] 3/16]
             [[:c4 :e4 :g4] 1/16] 
             [[:c4 :e4 :a4] 1/4] 
             [[:c4 :e4 :g4] 1/2]   
             [[:e4 :g4 :c5] 1/4] 
             [[:d4 :f4 :g4 :b4] 3/4]   
             [[:b3 :d4 :g4] 3/16]
             [[:b3 :d4 :g4] 1/16] 
             [[:d4 :f4 :a4] 1/4] 
             [[:b3 :d4 :g4] 1/2]   
             [[:f4 :g4 :b4 :d5] 1/4] 
             [[:e4 :g4 :c4] 3/4]   
             [[:c4 :e4 :g4] 3/16]
             [[:c4 :e4 :g4] 1/16] 
             [[:c5 :e5 :g5] 1/4] 
             [[:g4 :c4 :e5] 1/2]   
             [[:e4 :g4 :c5] 1/4] 
             [[:d4 :f4 :g4 :b4] 3/16] 
             [[:d4 :f4 :a4] 1/16] 
             [[:d4 :f4 :a4] 3/4];; (fermata)   
             [[:b4 :d5 :f5] 3/16] 
             [[:b4 :d5 :f5] 1/16] 
             [[:g4 :c4 :e5] 1/4] 
             [[:e4 :g4 :c5] 1/2]   
             [[:f4 :g4 :b4 :d5] 1/4] 
             [[:e4 :g4 :c4] 3/4]])

(defn play-note-or-notes [n-or-ns]  
  (if (sequential? n-or-ns)    
    (doseq [n n-or-ns] (piano (midifreq (note n))))    
    (play-note-or-notes (list n-or-ns))))

(defn play-phrase2  [start base-duration phrase]  
  (loop [time start notes phrase]    
    (when (seq notes)      
      (let [[[note duration] & more] notes]        
        (println (format "%s" [note duration]))        
        (at time (play-note-or-notes note))        
        (recur (+ time (* base-duration duration)) more)))))

(defn pp [x] (play-phrase2 (now) (* 4 60 1.0e3 1/180) x))

(pp penny)

