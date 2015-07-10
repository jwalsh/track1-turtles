(ns welcometoclojurebridge.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def angle-speed 0.05)
(def color-speed 0.15)

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  (q/text-font (q/create-font "resources/SourceCodePro-Regular.ttf" 36 true))

  {:logo (q/load-image "logo.png")
   :background (q/load-image "background.png")
   :color 0
   :angle 0})

(defn update-state [state]
  (-> state
      (update-in [:color] #(mod (+ % color-speed) 255))
      (update-in [:angle] + angle-speed)))

(defn draw-state [state]
  (q/background-image (:background state))

  (let [angle (:angle state)
        x (* 150 (q/cos angle))
        y (* 150 (q/sin angle))]
    (q/fill (mod (:color state) 80) (mod (:color state) 180) (mod (:color state) 150))
    (q/text "Welcome to"     190 (min 180 (+ 190 y)))
    (q/text "You are ready to code!" 80 (max 330 (+ 330 y)))

    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
      (q/image (:logo state) (- x 50) (- y 50) 110 110))))

(q/defsketch welcometoclojurebridge
  :title "Welcome To ClojureBridge!"
  :size [650 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])