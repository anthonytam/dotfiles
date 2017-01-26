;;      __________.__         ___________             _________                _____.__        
;;      \______   \  | _______\_   _____/____  ___  __\_   ___ \  ____   _____/ ____\__| ____  
;;       |     ___/  | \___   /|    __) \__  \ \  \/  /    \  \/ /  _ \ /    \   __\|  |/ ___\ 
;;       |    |   |  |__/    / |     \   / __ \_>    <\     \___(  <_> )   |  \  |  |  / /_/  >
;;       |____|   |____/_____ \\___  /  (____  /__/\_ \\______  /\____/|___|  /__|  |__\___  / 
;;                           \/    \/        \/      \/       \/            \/        /_____/  
;;
;;
;; P A C K A G E   M A N A G E M E N T
;;
(require 'package)
(add-to-list 'package-archives '("org" . "http://orgmode.org/elpa/"))
(add-to-list 'package-archives '("melpa" . "http://melpa.org/packages/"))
(add-to-list 'package-archives '("melpa-stable" . "http://stable.melpa.org/packages/"))
(setq package-enable-at-startup nil)
(package-initialize)

;; Have use-package auto download
(unless (package-installed-p 'use-package)
  (package-refresh-contents)
  (package-install 'use-package))
(require 'use-package)
(setq use-package-always-ensure t)

;;
;; G E N E R A L   S E T T I N G S
;;
(setq inhibit-splash-screen t
      inhibit-startup-message t)
(setq initial-scratch-message "") ; No scratch text
(fset 'yes-or-no-p 'y-or-n-p) ; y/n instead of yes/no
(column-number-mode t) ; show column number in mode line
(delete-selection-mode 1) ; Replace selection on insert
(setq vc-follow-symlinks t) ; Always follow symlinks
(setq custom-file "~/.emacs.d/custom.el") ; Set custom file
(load custom-file 'noerror) ; Load custom file
(setq org-pretty-entities t) ; Live LaTeX in org mode

;; 
;; O R G   M O D E
;;

(require 'org-agenda)

(setq org-agenda-prefix-format
      '((agenda . " %i %-12t% s %-9(car (last (org-get-outline-path)))")
        (timeline . "  % s")
        (todo . " %i %-12:c")
        (tags . " %i %-12:c")
        (search . " %i %-12:c")))

(setq org-agenda-files '("~/Documents/TODO.org"))
(global-set-key "\C-ca" 'org-agenda)
(define-key org-agenda-mode-map "j" 'org-agenda-next-item)
(define-key org-agenda-mode-map "k" 'org-agenda-previous-item)

(setq org-agenda-use-time-grid nil)
(setq org-src-fontify-natively t
      org-src-tab-acts-natively t)
;;
;; P A C K A G E S
;;
(use-package gruvbox-theme)

;; Saner defaults for emacs
(use-package better-defaults)

;; Better M-x
(use-package smex
  :demand
  :bind ("M-x" . smex))  

;; Better looking org headers
(use-package org-bullets
  :config
  (add-hook 'org-mode-hook (lambda () (org-bullets-mode 1))))

;; Keypress suggestions
(use-package which-key
  :config
  (which-key-mode))

(use-package magit
  :config
  (global-set-key "\C-x\g" 'magit-status))

(use-package php-mode)
;;
;; G O D   M O D E
;;
(use-package god-mode
  :config
  (global-set-key (kbd "<escape>") 'god-mode-all)
  (setq god-exempt-major-modes nil)
  (setq god-exempt-predicates nil))

;;
;; B A C K U P S
;;
(setq backup-by-copying t) ; Stop shinanigans with links
(setq backup-directory-alist '((".*" . "~/.bak.emacs/backup/")))
;; Creates directory if it doesn't already exist
(if (eq nil (file-exists-p "~/.bak.emacs/")) 
    (make-directory "~/.bak.emacs/"))
;; Creates auto directory if it doesn't already exist
(if (eq nil (file-exists-p "~/.bak.emacs/auto"))
    (make-directory "~/.bak.emacs/auto"))
;; backup in one place. flat, no tree structure
(setq auto-save-file-name-transforms '((".*" "~/.bak.emacs/auto/" t)))
