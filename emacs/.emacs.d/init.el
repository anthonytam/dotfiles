;;
;;      __________.__         ___________             _________                _____.__
;;      \______   \  | _______\_   _____/____  ___  __\_   ___ \  ____   _____/ ____\__| ____
;;       |     ___/  | \___   /|    __) \__  \ \  \/  /    \  \/ /  _ \ /    \   __\|  |/ ___\
;;       |    |   |  |__/    / |     \   / __ \_>    <\     \___(  <_> )   |  \  |  |  / /_/  >
;;       |____|   |____/_____ \\___  /  (____  /__/\_ \\______  /\____/|___|  /__|  |__\___  /
;;                           \/    \/        \/      \/       \/            \/        /_____/
;;

;; Set garbage collect high to speed up startup

;; Added by Package.el.  This must come before configurations of
;; installed packages.  Don't delete this line.  If you don't want it,
;; just comment it out by adding a semicolon to the start of the line.
;; You may delete these explanatory comments.
(package-initialize)

(let ((gc-cons-threshold most-positive-fixnum))
  (org-babel-load-file "~/.emacs.d/README.org"))
