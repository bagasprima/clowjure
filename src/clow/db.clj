(ns clow.db)

;; An atom holding the list of todo items.
;; Each todo is represented as a map with the following keys:
;;   :id   - unique identifier for the todo (e.g., 1)
;;   :task - description of the todo (e.g., "hello")
;;   :done - boolean indicating completion status (e.g., true)
;; Example:
;;   {:id 1 :task "hello" :done true}
(def todos (atom [])) ; todo list

(def id-counter (atom 0)) ; <-- counter untuk id

; next function
(defn next-id! []
  (swap! id-counter inc))

; add based on task only
(defn add-todo! [task]
  (let [todo {:id (next-id!)
              :task task
              :done false}]
    (swap! todos conj todo)
    todo))

; all todos
(defn get-todos []
  @todos)

; complete and return todo with the same id
(defn complete-todo! [id]
  (let [updated-todo (atom nil)]
    (swap! todos
           (fn [tds]
             (mapv (fn [t]
                     (if (= (:id t) id)
                       (let [t2 (assoc t :done true)]
                         (reset! updated-todo t2)
                         t2)
                       t))
                   tds)))
    @updated-todo))