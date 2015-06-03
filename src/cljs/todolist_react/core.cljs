(ns ^:figwheel-always todolist-react.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

; Hello world
(def app-state (atom {:greeting "Hello "
                      :secondsElapsed 0
                      :items [] :index 1 :text ""}))

(defn hello-view [cursor owner]
  (reify
    om/IInitState
    (init-state [_]
                {:hello (str (:greeting cursor) "World" "!!!")})

    om/IRenderState
    (render-state [_ state]
                  (dom/h2 nil "A Simple Component"
                          (dom/table nil
                                     (dom/td nil
                          (dom/textarea #js {:disabled true :rows "15" :cols "50"}
                                        "(def app-state (atom {:greeting \"Hello \"
                      :secondsElapsed 0
                      :items [] :index 1 :text \"\"}))

(defn hello-view [cursor owner]
  (reify
    om/IInitState
    (init-state [_]
                {:hello (str (:text cursor) \"World\" \"!!!\")})

    om/IRenderState
    (render-state [_ state]
                  (dom/h3 nil (:hello state)))))"))
                                     (dom/td nil
                          (dom/h3 nil (:hello state))))))))
; Timer
(defn tick [owner]
  (let [value (om/get-state owner :secondsElapsed)]
    (om/set-state! owner :secondsElapsed (inc value))))

(defn seconds-view [seconds owner]
  (reify
    om/IRender
    (render [this]
            (dom/h2 nil (str "Elapsed time: " seconds)))))

(defn timer-view [cursor owner]
  (reify
    om/IInitState
    (init-state [_]
                {:secondsElapsed 0})

    om/IDidMount
    (did-mount [_]
                (js/setInterval #(tick owner) 1000))

    om/IRenderState
    (render-state [_ state]
            (dom/h2 nil "A Stateful Component"
                    (dom/table nil
                               (dom/td nil
                                       (dom/textarea #js {:disabled true :rows "25" :cols "60"}
                                                     "(defn tick [owner]
  (let [value (om/get-state owner :secondsElapsed)]
    (om/set-state! owner :secondsElapsed (inc value))))

(defn seconds-view [seconds owner]
  (reify
    om/IRender
    (render [this]
             (dom/h2 nil (str \"Elapsed time: \" seconds)))))

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
            (dom/h2 nil (om/build seconds-view (:secondsElapsed state) {:init-state state}))))))
"))
                               (dom/td nil
                                       (dom/h2 nil (om/build seconds-view (:secondsElapsed state) {:init-state state}))))))))

; TODO list

(defn add-item [cursor owner]
  (let [new-item (-> (om/get-node owner "new-item")
                        .-value)]
    (when (not= "" new-item)
      (om/transact! cursor :items #(conj % new-item))
      (om/transact! cursor :index inc)
      (om/set-state! owner :text ""))))

(defn item-view [item owner]
  (reify
    om/IRender
    (render [this]
      (dom/li nil item))))

(defn todo-list-view [cursor owner]
  (reify
    om/IInitState
    (init-state [_]
      {:items [] :index 1 :text ""})

    om/IRenderState
    (render-state [this state]
                  (dom/h2 nil "An Application"
                          (dom/table nil
                                     (dom/td nil
                                             (dom/textarea #js {:disabled true :rows "37" :cols "60"} "(defn add-item [cursor owner]
  (let [new-item (-> (om/get-node owner \"new-item\")
                        .-value)]
    (when (not= \"\" new-item)
      (om/transact! cursor :items #(conj % new-item))
      (om/transact! cursor :index inc)
      (om/set-state! owner :text \"\"))))

(defn item-view [item owner]
  (reify
    om/IRender
    (render [this]
      (dom/li nil item))))

(defn todo-list-view [cursor owner]
  (reify
    om/IInitState
    (init-state [_]
      {:items [] :index 1 :text \"\"})

    om/IRenderState
    (render-state [this state]
      (dom/div nil
        (dom/h1 nil \"TODO\")

        (apply dom/ul nil
          (om/build-all item-view (:items cursor)
            {:init-state state}))

        (dom/div nil
          (dom/input #js {:type \"text\" :ref \"new-item\" :value (:text state)
                          :onChange (fn [x])})

          (dom/button #js {:onClick #(add-item cursor owner)}
                      (str \"Add item #\" (:index cursor))))))))"))
                                     (dom/td nil

      (dom/div nil
        (dom/h2 nil "TODO")

        (apply dom/ul nil
          (om/build-all item-view (:items cursor)
            {:init-state state}))

        (dom/div nil
          (dom/input #js {:type "text" :ref "new-item" :value (:text cursor)
                          :onChange (fn [x])})

          (dom/button #js {:onClick #(add-item cursor owner)}
                      (str "Add item #" (:index cursor)))))))
          (dom/h2 nil "The Rest"
                  (dom/p nil
                  (dom/textarea #js {:disabled true :rows "23" :cols "60"}
                                        "(defn all-views
  [cursor owner]
  (reify
    om/IInitState
    (init-state [_]
                {:greeting \"Hello \"
                 :secondsElapsed 0
                 :items [] :index 1 :text \"\"})

    om/IRenderState
    (render-state [_ state]
                            (let [{:keys [greeting] :as hello-state} cursor]
                              (om/build hello-view hello-state))
                            (let [{:keys [secondsElapsed] :as timer-state} cursor]
                              (om/build timer-view timer-state))
                            (let [{:keys [items index text] :as todo-state} cursor]
                              (om/build todo-list-view todo-state))))))

(defn main []
  (om/root all-views app-state
    {:target (. js/document (getElementById \"app\"))}))
")))))))

(defn all-views
  [cursor owner]
  (reify
    om/IInitState
    (init-state [_]
                {:greeting "Hello "
                 :secondsElapsed 0
                 :items [] :index 1 :text ""})

    om/IRenderState
    (render-state [_ state]
                   (dom/div nil
                            (let [{:keys [greeting] :as hello-state} cursor]
                              (om/build hello-view hello-state))
                            (let [{:keys [secondsElapsed] :as timer-state} cursor]
                              (om/build timer-view timer-state))
                            (let [{:keys [items index text] :as todo-state} cursor]
                              (om/build todo-list-view todo-state))))))

(defn main []
  (om/root all-views app-state
    {:target (. js/document (getElementById "app"))}))
