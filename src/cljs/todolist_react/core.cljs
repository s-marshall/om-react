(ns ^:figwheel-always todolist-react.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

; Hello world
(def hello-app-state (atom {:text "Hello "}))

(defn hello-view [data owner]
  (reify
    om/IInitState
    (init-state [_]
                {:hello (str (:text data) "World" "!!!")})

    om/IRenderState
    (render-state [_ state]
                  (dom/h3 nil (:hello state)))))
; Timer

(def app-state (atom {:secondsElapsed 0}))

(defn tick [owner]
  (let [value (om/get-state owner :secondsElapsed)]
    (om/set-state! owner :secondsElapsed (inc value))))

(defn seconds-view [seconds owner]
  (reify
    om/IRender
    (render [this]
             (dom/h2 nil (str "Elapsed time: " seconds)))))

(defn timer-view [data owner]
  (reify
    om/IInitState
    (init-state [_]
                {:secondsElapsed 0})

    om/IDidMount
    (did-mount [_]
                (js/setInterval #(tick owner) 1000))

    om/IRenderState
    (render-state [_ state]
            (dom/div #js {:className "timer"}
            (dom/h1 nil "The Timer")
            (dom/h2 nil (om/build seconds-view (:secondsElapsed state) {:init-state state}))))))

; TODO list

(def list-app-state (atom {:items [] :index 1 :text ""}))

(defn add-item [data owner]
  (let [new-item (-> (om/get-node owner "new-item")
                        .-value)]
    (when (not= "" new-item)
      (om/transact! data :items #(conj % new-item))
      (om/transact! data :index inc)
      (om/set-state! owner :text ""))))

(defn item-view [item owner]
  (reify
    om/IRender
    (render [this]
      (dom/li nil item))))

(defn todo-list-view [data owner]
  (reify
    om/IInitState
    (init-state [_]
      {:items [] :index 1 :text ""})

    om/IRenderState
    (render-state [this state]
      (dom/div nil
        (dom/h1 nil "TODO")

        (apply dom/ul nil
          (om/build-all item-view (:items data)
            {:init-state state}))

        (dom/div nil
          (dom/input #js {:type "text" :ref "new-item" :value (:text state)
                          :onChange (fn [x])})

          (dom/button #js {:onClick #(add-item data owner)}
                      (str "Add item #" (:index data))))))))

(defn main []
  (om/root hello-view hello-app-state
    {:target (. js/document (getElementById "hello"))})

  (om/root timer-view timer-app-state
    {:target (. js/document (getElementById "timer"))})

  (om/root todo-list-view list-app-state
    {:target (. js/document (getElementById "todoList"))}))

