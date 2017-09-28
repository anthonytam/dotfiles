;;
;;      __________.__         ___________             _________                _____.__
;;      \______   \  | _______\_   _____/____  ___  __\_   ___ \  ____   _____/ ____\__| ____
;;       |     ___/  | \___   /|    __) \__  \ \  \/  /    \  \/ /  _ \ /    \   __\|  |/ ___\
;;       |    |   |  |__/    / |     \   / __ \_>    <\     \___(  <_> )   |  \  |  |  / /_/  >
;;       |____|   |____/_____ \\___  /  (____  /__/\_ \\______  /\____/|___|  /__|  |__\___  /
;;                           \/    \/        \/      \/       \/            \/        /_____/
;;

;; Set garbage collect high to speed up startup
(let ((gc-cons-threshold most-positive-fixnum))
  ;; This stops emacs from appending (package-initialize) to this file
  (setq package--init-file-ensured t)
  (org-babel-load-file "~/.emacs.d/README.org"))
